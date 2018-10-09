package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
@Transactional(readOnly = true)
class OpportunityFlexFieldCategoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def propertyValuationProviderService = new PropertyValuationProviderService()

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlexFieldCategory.list(params), model: [opportunityFlexFieldCategoryCount: OpportunityFlexFieldCategory.count()]
    }

    def show(OpportunityFlexFieldCategory opportunityFlexFieldCategory)
    {
        respond opportunityFlexFieldCategory
    }

    def create()
    {
        respond new OpportunityFlexFieldCategory(params)
    }

    @Transactional
    def save(OpportunityFlexFieldCategory opportunityFlexFieldCategory)
    {
        if (opportunityFlexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlexFieldCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlexFieldCategory.errors, view: 'create'
            return
        }

        opportunityFlexFieldCategory.save flush: true
        def opportunityFlexField
        def opportunityFlexFieldValue
        opportunityFlexFieldCategory?.flexFieldCategory?.fields?.each {
            opportunityFlexField = new OpportunityFlexField()
            opportunityFlexField.category = opportunityFlexFieldCategory
            opportunityFlexField.dataType = it?.dataType
            opportunityFlexField.defaultValue = it?.defaultValue
            opportunityFlexField.description = it?.description
            opportunityFlexField.name = it?.name
            opportunityFlexField.displayOrder = it?.displayOrder
            opportunityFlexField.valueConstraints = it?.valueConstraints
            if (opportunityFlexField.validate())
            {
                opportunityFlexField.save flush: true

                it?.values?.each {
                    opportunityFlexFieldValue = new OpportunityFlexFieldValue()
                    opportunityFlexFieldValue.field = opportunityFlexField
                    opportunityFlexFieldValue.value = it?.value
                    opportunityFlexFieldValue.displayOrder = it?.displayOrder
                    if (opportunityFlexFieldValue.validate())
                    {
                        opportunityFlexFieldValue.save flush: true
                    }
                    else
                    {
                        println opportunityFlexFieldValue.errors
                    }
                }
            }
            else
            {
                println opportunityFlexField.errors
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory'), opportunityFlexFieldCategory.id])
                // redirect opportunityFlexFieldCategory
                redirect(controller: "opportunity", action: "show", params: [id: opportunityFlexFieldCategory?.opportunity?.id])

            }
            '*' { respond opportunityFlexFieldCategory, [status: CREATED] }
        }
    }

    def edit(OpportunityFlexFieldCategory opportunityFlexFieldCategory)
    {
        respond opportunityFlexFieldCategory
    }

    @Transactional
    def update(OpportunityFlexFieldCategory opportunityFlexFieldCategory)
    {
        if (opportunityFlexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlexFieldCategory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlexFieldCategory.errors, view: 'edit'
            return
        }

        opportunityFlexFieldCategory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory'), opportunityFlexFieldCategory.id])
                redirect opportunityFlexFieldCategory
            }
            '*' { respond opportunityFlexFieldCategory, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityFlexFieldCategory opportunityFlexFieldCategory)
    {

        if (opportunityFlexFieldCategory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityFlexFieldCategory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory'), opportunityFlexFieldCategory.id])
                // redirect action: "index", method: "GET"
                redirect(controller: "opportunity", action: "show", params: [id: opportunityFlexFieldCategory?.opportunity?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlexFieldCategory.label', default: 'OpportunityFlexFieldCategory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def prepareRiskReport(Opportunity opportunity)
    {
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        respond opportunity, model: [opportunityFlexFieldCategorys: opportunityFlexFieldCategorys]
    }

    @Transactional
    def riskReport()
    {
        def opportunityId = params['opportunity']
        def opportunity = Opportunity.findById(opportunityId)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        opportunityFlexFieldCategorys?.each {
            it?.fields?.each {
                if (params[it?.name])
                {
                    println "category:" + it?.category?.flexFieldCategory?.name + " field:" + it?.name
                    it?.value = params[it?.name]
                    it.save flush: true
                }

            }
        }
        redirect(controller: "opportunity", action: "show", params: [id: opportunity?.id])
        return
    }

    @Transactional
    def ajaxRiskReport()
    {
        def opportunityId = params['opportunity']
        def opportunity = Opportunity.findById(opportunityId)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        opportunityFlexFieldCategorys?.each {
            it?.fields?.each {
                if (params[it?.name])
                {
                    println "category:" + it?.category?.flexFieldCategory?.name + " field:" + it?.name
                    it?.value = params[it?.name]
                    it.save flush: true
                }

            }
        }

        render([status: "success"] as JSON)
        return
    }

    /**********************************助手端接口***********************************************/
    @Secured(['permitAll'])
    def appQueryByOpportunityId()
    {
        //助手端查询下户结论
        def json = request.JSON
        println "******************* appQueryByOpportunityId ********************"
        println json

        def sessionId = json['sessionId']
        def opportunityId = json['opportunityId']?.toInteger()
        def categoryName = json['categoryName']


        if (sessionId == null || sessionId == '')
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        User user = User.findByAppSessionId(sessionId)
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (opportunityId == null || opportunityId == '')
        {
            def errors = [errorCode: 6098, errorMessage: "订单id不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        opportunityId = opportunityId.toInteger()
        def opportunity = Opportunity.get(opportunityId)
        def flexFieldCategory = FlexFieldCategory.findByName("${categoryName}")
        def oppoFlexFieldCategory = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, flexFieldCategory)
        if (!oppoFlexFieldCategory)
        {
            def errors = [errorCode: 6097, errorMessage: "订单的产调结果不存在不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //        println oppoFlexFieldCategory as JSON
        render oppoFlexFieldCategory as JSON
    }

    @Secured(['permitAll'])
    @Transactional
    def appUpdate()
    {
        //助手端修改下户信息
        println "################appUpdate##########################"
        def json = request.JSON
        println json

        def sessionId = json['sessionId']
        //        println "sessionId:" + sessionId
        def reportJson = json['liquidityRiskReport']
        //        println "reportJson:" + reportJson
        if (sessionId == null || sessionId == '')
        {
            def errors = [errorCode: 4300, errorMessage: "请登录"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        User user = User.findByAppSessionId(sessionId)
        if (!user)
        {
            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        if (!reportJson)
        {
            def errors = [errorCode: 6096, errorMessage: "弹性域模块不能为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }

        def id = reportJson['id']
        if (id == null || id == '')
        {
            def errors = [errorCode: 6088, errorMessage: "此弹性域模块id不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        //        def report = LiquidityRiskReport.get(id)
        def report = OpportunityFlexFieldCategory.get(id)
        if (!report)
        {
            def errors = [errorCode: 6095, errorMessage: "此弹性域模块不存在"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def items = reportJson['values']
        if (!items)
        {
            def errors = [errorCode: 6094, errorMessage: "弹性域不能全为空"]
            render JsonOutput.toJson(errors), status: 400
            return
        }
        def fieldPrice
        println "items:" + items
        items.each {
            //            println "it['name']:" + it['name']
            def name = it['name']?.trim()
            def optionName = it['value']?.trim()
            //            def field = report.fields
            def field = OpportunityFlexField.findByCategoryAndName(report, name)
            if (name == null || name == '')
            {
                def errors = [errorCode: 6091, errorMessage: "弹性域名称不能为空"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (!field)
            {
                def errors = [errorCode: 6093, errorMessage: "弹性域不存在，请核对"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            if (optionName == null || optionName == '')
            {
                def errors = [errorCode: 6090, errorMessage: "弹性域子项名称不能为空"]
                render JsonOutput.toJson(errors), status: 400
                return
            }
            //            def itemOption = LiquidityRiskReportItemOptions.findByItemAndName(item, optionName)
            //            if (!itemOption)
            //            {
            //                def errors = [errorCode: 6092, errorMessage: "流动子项不存在，请核对"]
            //                render JsonOutput.toJson(errors), status: 400
            //                return
            //            }
            if (field.validate())
            {
                if (name == '快速成交价（万元）')
                {
                    fieldPrice = optionName
                    //                    def collateral = Collateral.findByOpportunity(report?.opportunity)
                    //                    println "debug1 appUpdate"
                    //                    if (collateral)
                    //                    {
                    //                        println "debug2 appUpdate"
                    //                        collateral.fastUnitPrice = Double.parseDouble(optionName)
                    //                        if (collateral.validate())
                    //                        {
                    //                            println "debug3 appUpdate"
                    //                            // collateral.save()
                    //                            collateral.save flush: true
                    //                            println "debug4 appUpdate"
                    //                            propertyValuationProviderService.updateFastUnitPrice(collateral?.externalId, optionName)
                    //                            println "debug5 appUpdate"
                    //                        }
                    //                        else
                    //                        {
                    //                            def message = [errorCode: 6101, errorMessage: "对不起，抵押物外访值保存失败，请稍后再试"]
                    //                            render JsonOutput.toJson(message), status: 200
                    //                            return
                    //                        }
                    //                    }
                    //                    else
                    //                    {
                    //                        def message = [errorCode: 6100, errorMessage: "抵押物不能为空"]
                    //                        render JsonOutput.toJson(message), status: 200
                    //                        return
                    //                    }
                }
                else
                {
                    field.value = optionName
                    field.save()
                }
            }
            else
            {
                println field.errors
                def message = [errorCode: 6089, errorMessage: "对不起，弹性域保存失败，请稍后再试"]
                render JsonOutput.toJson(message), status: 200
                return
            }
        }
        //        liquidityRiskReportService.scoring(report)
        if (fieldPrice)
        {
            def collateral = Collateral.findByOpportunity(report?.opportunity)
            println "debug1 appUpdate"
            if (collateral)
            {
                println "debug2 appUpdate"
                // propertyValuationProviderService.updateFastUnitPrice(collateral?.externalId, fieldPrice)
                // def result = propertyValuationProviderService.updateFastUnitPrice(collateral?.externalId, fieldPrice)
                // if (!result)
                // {
                //   println "debug5 appUpdate"
                //   def message = [errorCode: 6166, errorMessage: "外访值不能修改"]
                //   render JsonOutput.toJson(message), status: 400
                //   return
                // }
                def result = propertyValuationProviderService.updateFastUnitPrice(collateral?.externalId, fieldPrice)
                if (!result['flag'])
                {
                    println "debug5 appUpdate"
                    def resultMessage = result['message']
                    def message = [errorCode: 6166, errorMessage: resultMessage]
                    render JsonOutput.toJson(message), status: 400
                    return
                }
                else
                {
                    println "debug6 appUpdate"
                    items?.each {
                        def fastUnitPriceName = it['name']?.trim()
                        if (fastUnitPriceName == '快速成交价（万元）')
                        {
                            println "debug7 appUpdate"
                            def fastUnitPricefield = OpportunityFlexField.findByCategoryAndName(report, fastUnitPriceName)
                            fastUnitPricefield.value = fieldPrice
                            fastUnitPricefield.save()
                        }
                    }
                }
            }
            else
            {
                def message = [errorCode: 6100, errorMessage: "抵押物不能为空"]
                render JsonOutput.toJson(message), status: 200
                return
            }
        }
        def message = [successMessage: "弹性域模块信息保存完成"]
        render JsonOutput.toJson(message), status: 200
        return

    }
}
