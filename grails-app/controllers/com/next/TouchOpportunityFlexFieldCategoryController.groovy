package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
@Transactional(readOnly = true)
class TouchOpportunityFlexFieldCategoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def propertyValuationProviderService

    @Secured(['permitAll'])
    def appQueryByOpportunityIdAndCategoryName()
    {
        //助手端查询下户结论
        def json = request.JSON
        println "******************* appQueryByOpportunityIdAndCategoryName ********************"
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
