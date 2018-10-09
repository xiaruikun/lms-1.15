package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class CollateralController
{
    def mailService
    def propertyValuationProviderService
    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Collateral.list(params), model: [collateralCount: Collateral.count()]
    }

    def show(Collateral collateral)
    {
        def photoes = Attachments.findAllByOpportunityAndType(collateral?.opportunity, AttachmentType.findByName("估值附回材料"))
        respond collateral, model: [photoes: photoes]
    }

    def create()
    {
        respond new Collateral(params)
    }

    @Transactional
    def save(Collateral collateral)
    {
        if (collateral == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (collateral.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond collateral.errors, view: 'create'
            return
        }
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        collateral.createdBy = user
        collateral.modifiedBy = user

        collateral.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'collateral.label', default: 'Collateral'), collateral.id])
                // redirect collateral
                redirect(controller: "opportunity", action: "show", params: [id: collateral?.opportunity?.id])
            }
            '*' { respond collateral, [status: CREATED] }
        }
    }

    def edit(Collateral collateral)
    {
        respond collateral
    }

    @Transactional
    def update(Collateral collateral)
    {
        if (collateral == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (collateral.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond collateral.errors, view: 'edit'
            return
        }
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        collateral.modifiedBy = user

        collateral.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'collateral.label', default: 'Collateral'), collateral.id])
                // redirect collateral
                redirect(controller: "opportunity", action: "show", params: [id: collateral?.opportunity?.id])
            }
            '*' { respond collateral, [status: OK] }
        }
    }

    @Transactional
    def ajaxUpdate(Collateral collateral)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        collateral.modifiedBy = user

        if (collateral.validate())
        {
            collateral.save flush: true
            render([status: "success"] as JSON)
            return
        }
        else
        {
            render([status: "fail", errorMessage: collateral.errors] as JSON)
            return
        }
    }

    @Transactional
    def delete(Collateral collateral)
    {

        if (collateral == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        collateral.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'collateral.label', default: 'Collateral'), collateral.id])
                // redirect action: "index", method: "GET"
                redirect(controller: "opportunity", action: "show", params: [id: collateral?.opportunity?.id])
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'collateral.label', default: 'Collateral'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Transactional
    def pvQuery(Collateral collateral)
    {
        def result = propertyValuationProviderService.queryPrice(collateral)
        def opportunity = collateral?.opportunity
        println result
        if (result != 0)
        {
            def unitPrice = result['unitPrice']?.toString()
            def externalId = result['externalId']?.toString()
            def status = result['status']?.toString()

            if (opportunity?.stage?.code == '01')
            {
                if (status == "Completed")
                {
                    opportunity.stage = OpportunityStage.findByCode("02")
                }
                else
                {
                    opportunity.stage = OpportunityStage.findByCode("15")
                }
                if (opportunity.validate())
                {
                    opportunity.save flush: true
                }
                else
                {
                    println opportunity.errors
                }
            }

            if (status == "Completed")
            {
                collateral.requestEndTime = new Date()
            }

            collateral.unitPrice = Double.parseDouble(unitPrice)
            collateral.totalPrice = Double.parseDouble(unitPrice) * (collateral?.area) / 10000
            collateral.externalId = externalId
            collateral.status = status
            if (collateral.validate())
            {
                collateral.save flush: true
            }
            else
            {
                println collateral.errors
            }

            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        }
        // if (result != 0)
        // {
        //     def unitPrice = result['unitPrice']?.toString()
        //     def externalId = result['externalId']?.toString()
        //     def status = result['status']?.toString()
        //     def loanAmount = Double.parseDouble(unitPrice) * collateral.area / 10000
        //
        //     collateral.unitPrice = Double.parseDouble(unitPrice)
        //     collateral.totalPrice = Double.parseDouble(unitPrice) * collateral.area / 10000
        //     collateral.externalId = externalId
        //     collateral.status = status
        //     collateral.save flush: true
        //
        //     redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        //
        // }
        else
        {
            flash.message = "估值失败"
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
        }

    }

    @Transactional
    def recoveryCollateral()
    {
        def opportunity = Opportunity.findById(params['opportunity'])
        def collateral
        if (opportunity?.collaterals?.size() <= 0 && opportunity?.city)
        {
            collateral = new Collateral()
            if (opportunity?.assetType?.name)
            {
                collateral.assetType = opportunity?.assetType?.name
            }

            collateral.city = opportunity?.city
            collateral.floor = opportunity?.floor
            collateral.orientation = opportunity?.orientation
            collateral.area = opportunity?.area
            collateral.totalFloor = opportunity?.totalFloor
            collateral.roomNumber = opportunity?.roomNumber
            collateral.houseType = opportunity?.houseType?.name
            collateral.specialFactors = opportunity?.specialFactors
            collateral.unitPrice = opportunity?.unitPrice
            collateral.totalPrice = opportunity?.loanAmount
            collateral.status = 'Completed'
            collateral.opportunity = opportunity
            collateral?.building = 0
            collateral.unit = 0
            if (opportunity?.district)
            {
                collateral.projectName = opportunity?.district
                collateral.district = opportunity?.district
            }
            else if (opportunity?.address)
            {
                collateral.projectName = opportunity?.address
                collateral.district = opportunity?.address
            }

            if (opportunity?.address)
            {
                collateral.address = opportunity?.address
            }
            else if (opportunity?.district)
            {
                collateral.address = opportunity?.district
            }

            if (collateral.validate())
            {
                collateral.save flush: true
            }
            else
            {
                println collateral.errors
            }
        }
        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
    }

    def queryValuationReliability(Collateral collateral)
    {
        def externalId = params["externalId"]
        def pvCollateral = [:]
        if (externalId)
        {
            def pvJson = propertyValuationProviderService.queryByExternalId(externalId)
            if (pvJson)
            {
                def valuationReliability = pvJson['valuationReliability']
                def unitPrice = pvJson['unitPrice']
                pvCollateral['valuationReliability'] = valuationReliability
                pvCollateral['unitPrice'] = unitPrice
            }
            else
            {
                pvCollateral['valuationReliability'] = ""
                pvCollateral['unitPrice'] = 0
            }
        }
        else
        {
            pvCollateral['valuationReliability'] = ""
            pvCollateral['unitPrice'] = 0
        }
        render([status: "success", pvCollateral: pvCollateral] as JSON)
    }

    @Secured('permitAll')
    @Transactional
    def queryByExternalId()
    {
        def id = params['externalId']
        id = String.valueOf(id)
        def collateral = Collateral.findByExternalId(id)
        println collateral
        def opportunity = collateral?.opportunity
        def serialNumber = opportunity?.serialNumber
        def json = [:]
        if (opportunity)
        {
            json['serialNumber'] = serialNumber

        }
        else
        {
            println "22"
        }
        render json as JSON
    }

    def modifyCollateralInformation(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def applyReasonAndContent = params.applyReasonAndContent
        def collateral = opportunity?.collaterals.getAt(0)
        if (applyReasonAndContent && collateral)
        {
            try
            {
                mailService.sendMail {
                    to "<qutingting@zhongjiaxin.com>"
                    // cc  "<caoxiuran@126.com>"
                    from "<lms@zhongjiaxin.com>" //"昵称<邮箱>"
                    subject "修改房产信息申请"
                    html "<p>曲婷婷：</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！麻烦修改一下以下问题。</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + applyReasonAndContent + "</p>" + "<p>房产ID为:" + collateral?.externalId + "</p>" + "<p>订单号为:" + opportunity?.serialNumber + "</p>" + "<p>借款人为:" + opportunity?.fullName + "</p>" + "<p style='text-algin:right'>申请人：" + user?.fullName + "</p>"
                }
                println "邮件发送成功"
            }
            catch (Exception e)
            {
                e.printStackTrace()
                println("邮件发送失败，错误信息：<br/>" + e.getMessage())
            }
        }
        redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
    }

    @Transactional
    def searchCollateral()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        String sql = "from Collateral as o where 1 = 1"
        def projectName = params["projectName"]
        if (projectName)
        {
            sql += " and o.projectName like '%${projectName}%'"
        }

        def externalId = params["externalId"]
        if (externalId)
        {
            sql += " and o.externalId = '${externalId}'"
        }

        def serialNumber = params["serialNumber"]
        if (serialNumber)
        {
            sql += " and o.opportunity.serialNumber = '${serialNumber}'"
        }

        def startTime = params["startTime"]
        def endTime = params["endTime"]
        if (startTime && endTime)
        {
            sql += " and o.createdDate between '${startTime}' and '${endTime}'"
        }

        def city = params["city"]
        if (city && city != "null")
        {
            sql += " and o.city.name = '${city}'"
        }

        sql += " order by o.createdDate desc"
        println sql
        def list = Collateral.findAll(sql, [max: max, offset: offset])
        def list1 = Collateral.findAll(sql)
        Integer count = list1.size()

        respond list, model: [collateralCount: count, params: params], view: 'index'
    }

    @Transactional
    def searchOldOpportunityNumbers(){
        def oid = params.opportunityId
        if (oid){
            def map = [:]
            def map1
            def list = []
            def oids = []
            def size = 0
            def collateral = Collateral.findByOpportunity(Opportunity.findById(oid))
            def oldCollaterals = Collateral.findAllByPropertySerialNumberAndOpportunityNotEqual(collateral?.propertySerialNumber,collateral?.opportunity)
            oldCollaterals.each {
                def opportunity = it?.opportunity
                if (opportunity?.isTest!=true&&opportunity?.status!="Failed"&&opportunity?.createdDate?.format("yyyy-MM-dd")>"2017-04-01"){
                    if (OpportunityFlow.findByOpportunityAndStage(opportunity,OpportunityStage.findByName("基础材料已提供"))?.endTime){
                        oids.add(opportunity?.id)
                    }
                }
            }
            if (oids.size()>0){
                def opportunitys = Opportunity.findAllByIdInList(oids)
                opportunitys.each {
                    map1 = [:]
                    map1["serialNumber"] = it?.serialNumber
                    map1["fullName"] = it?.fullName
                    list.add(map1)
                }
                size = opportunitys?.size()
            }
            map["size"] = size
            map["list"] = list
            render map as JSON
        }
    }
}
