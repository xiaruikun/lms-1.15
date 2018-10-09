package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class LeadsController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def contactService
    def propertyValuationProviderService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Leads.list(params), model: [leadsCount: Leads.count()]
    }

    def show(Leads leads)
    {
        respond leads
    }

    def create()
    {
        respond new Leads(params)
    }

    @Transactional
    def save(Leads leads)
    {
        if (leads == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (leads.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond leads.errors, view: 'create'
            return
        }

        leads.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'leads.label', default: 'Leads'), leads.id])
                redirect leads
            }
            '*' { respond leads, [status: CREATED] }
        }
    }

    def edit(Leads leads)
    {
        respond leads
    }

    @Transactional
    def update(Leads leads)
    {
        if (leads == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (leads.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond leads.errors, view: 'edit'
            return
        }

        leads.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'leads.label', default: 'Leads'), leads.id])
                redirect leads
            }
            '*' { respond leads, [status: OK] }
        }
    }

    @Transactional
    def delete(Leads leads)
    {

        if (leads == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        leads.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'leads.label', default: 'Leads'), leads.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'leads.label',
                                                                                          default: 'Leads'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def searchLeads()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def city = params["city"]
        def district = params["district"]
        def houseType = params["houseType"]
        def specialFactors = params["specialFactors"]

        String sql = "from Leads as u where 1=1"
        if (city)
        {
            sql += " and u.city.name = '${city}'"
        }
        if (district)
        {
            sql += " and u.district like '%${district}%'"
        }
        if (houseType)
        {
            sql += " and u.houseType.name = '${houseType}'"
        }
        if (specialFactors)
        {
            sql += " and u.specialFactors.name = '${specialFactors}'"
        }

        sql += ' order by createdDate desc'

        println "sql:" + sql

        def max = params.max
        def offset = params.offset

        def list = Leads.findAll(sql, [max: max, offset: offset])
        def list1 = Leads.findAll(sql)
        def count = list1.size()

        def leads = new Leads(params)
        respond list, model: [leadsCount: count, leads: leads, params: params], view: 'index'
    }

    // *********************************************************************************************************

    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step1(String code, String state)
    {
        def openId = session.openId
        if (!openId)
        {
            openId = contactService.setRequest(code, state)
            session.openId = openId
        }

        def leads = new Leads(params)
        leads.openId = openId

        def cityList = []
        def accountCityList = AccountCity.findAll("from AccountCity as a where a.account.id = 1")
        if (accountCityList && accountCityList.size() == 0)
        {
            flash.message = message(code: "暂无开放城市")
        }
        else
        {
            accountCityList.each {
                cityList.add(it.city)
            }
        }

        respond leads, model: [cityList: cityList]
    }

    // @Secured(['permitAll'])
    // @Transactional
    // def wxCreate2Step1()
    // {
    //     def leads = new Leads(params)
    //     leads.openId = "123456"

    //     def cityList = []
    //     def accountCityList = AccountCity.findAll("from AccountCity as a where a.account.id = 1")
    //     if (accountCityList && accountCityList.size() == 0)
    //     {
    //         flash.message = message(code: "暂无开放城市")
    //     }
    //     else
    //     {
    //         accountCityList.each {
    //             cityList.add(it.city)
    //         }
    //     }

    //     respond leads, model: [cityList: cityList]
    // }

    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step2(Leads leads)
    {
        def projectNameList = []
        def result = propertyValuationProviderService.searchProjectName(leads?.city?.name, leads?.projectName)
        if (result != 0)
        {
            projectNameList = result.getAt("body")
        }
        def newList = []
        if (projectNameList.size() > 8)
        {
            for (
                i in
                    0..7)
            {
                newList.add(projectNameList.get(i));
            }
        }
        else
        {
            newList = projectNameList
        }

        //        def newList = [[address: "北京是昌平区黄平路北郊医院对面", projectName: "华龙苑南里"],
        //            [address: "北京是昌平区黄平路北郊医院对面", projectName: "华龙苑南里"],
        //            [address: "北京是昌平区黄平路北郊医院对面", projectName: "华龙苑南里"],
        //            [address: "北京是昌平区黄平路北郊医院对面", projectName: "华龙苑南里"],
        //            [address: "北京是昌平区黄平路北郊医院对面", projectName: "华龙苑南里"]]
        respond leads, model: [projectNameList: newList], view: "wxCreate2Step2"
    }

    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step3(Leads leads)
    {
        respond leads
    }

    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step4Save(Leads leads)
    {
        if (leads == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (leads.hasErrors())
        {
            println leads.errors
            transactionStatus.setRollbackOnly()
            respond leads.errors, view: 'wxCreate2Step3'
            return
        }

        leads.unitPrice = Math.floor(Double.parseDouble(params["unitPrice"]))
        leads.loanAmount = Math.floor(Double.parseDouble(params["unitPrice"]) * leads.area / 10000)


        if (leads.validate())
        {
            leads.save flush: true
        }
        else
        {
            println leads.errors
        }

        redirect(action: "wxCreate2Step4", id: leads.id, params: [status: params["status"]])
    }

    @Secured(['permitAll'])
    @Transactional
    def wxCreate2Step4(Leads leads)
    {
        respond leads
    }

    @Secured(['permitAll'])
    @Transactional
    def wxQueryPrice()
    {
        println "==================leads 询值==========================="
        def collateral = new Collateral()
        collateral.city = City.findById(params['city'])
        collateral.district = params['district']
        collateral.projectName = params['projectName']
        collateral.building = Integer.parseInt(params['building'])
        collateral.unit = Integer.parseInt(params['unit'])
        collateral.floor = params['floor']
        collateral.roomNumber = params['roomNumber']
        collateral.totalFloor = params['totalFloor']
        collateral.area = Double.parseDouble(params['area'])
        collateral.address = params['address']
        collateral.orientation = params['orientation']
        collateral.houseType = HouseType.findById(params['houseType'])?.name
        collateral.specialFactors = SpecialFactors.findById(params['specialFactors'])?.name
        collateral.assetType = AssetType.findById(params['assetType'])?.name
        collateral.appliedTotalPrice = Double.parseDouble(params['appliedTotalPrice'])

        def price = propertyValuationProviderService.queryPrice(collateral)
        if (price != 0)
        {
            println "price:" + price
            render([status: "success", price: price] as JSON)
        }
        else
        {
            render([status: "error", errorMsg: "询价失败，请稍后重试"] as JSON)
        }
    }
}
