package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO'])
@Transactional(readOnly = true)
class OpportunityFlexFieldController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService
    def componentService

    def opportunityFlexField01(Opportunity opportunity)
    {
        //外访报告
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def canAttachmentsShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        {
            canAttachmentsShow = true
        }
        else if (currentRole?.teamRole?.name == 'Approval')
        {
            canAttachmentsShow = true
        }

        respond opportunity, model: [opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, canAttachmentsShow: canAttachmentsShow]
    }

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityFlexField.list(params), model: [opportunityFlexFieldCount: OpportunityFlexField.count()]
    }

    def show(OpportunityFlexField opportunityFlexField)
    {
        respond opportunityFlexField
    }

    def showOpportunityFlexField(Opportunity opportunity)
    {
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        render(view: "showOpportunityflexfield", model: [opportunity: opportunity, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys])
    }

    def create()
    {
        respond new OpportunityFlexField(params)
    }

    @Transactional
    def save(OpportunityFlexField opportunityFlexField)
    {
        if (opportunityFlexField == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlexField.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlexField.errors, view: 'create'
            return
        }

        opportunityFlexField.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityFlexField.label', default: 'OpportunityFlexField'), opportunityFlexField.id])
                // redirect opportunityFlexField
                redirect(controller: "opportunityFlexFieldCategory", action: "show", params: [id: opportunityFlexField?.category?.id])

            }
            '*' { respond opportunityFlexField, [status: CREATED] }
        }
    }

    def edit(OpportunityFlexField opportunityFlexField)
    {
        def values = OpportunityFlexFieldValue.findAllByField(opportunityFlexField)
        respond opportunityFlexField, model: [values: values]
    }

    @Transactional
    def batchEdit(OpportunityFlexFieldCategory opportunityFlexFieldCategory)
    {
        def component = Component.findByName("规则引擎（全部符合帐号）")
        if (component&&opportunityFlexFieldCategory.opportunity?.stage == OpportunityStage.findByName("审批已完成"))
        {
            def result = componentService.evaluate component, opportunityFlexFieldCategory.opportunity
            println "规则引擎（全部符合帐号）订单"+opportunityFlexFieldCategory.opportunity.serialNumber+"返回:"+result.toString()
        }
        if (component&&opportunityFlexFieldCategory.opportunity?.stage?.name == "放款审批已完成"){
            def flexFields = OpportunityFlexField.findAllByCategoryAndName(opportunityFlexFieldCategory,"放款通道")
            flexFields.each { field ->
                OpportunityFlexFieldValue.findAllByField(field).each {
                    if (it.value!=field?.value){
                        it.delete flush:true
                    }
                }
            }
            flexFields = OpportunityFlexField.findAllByCategoryAndName(opportunityFlexFieldCategory,"抵押权人")
            flexFields.each { field ->
                OpportunityFlexFieldValue.findAllByField(field).each {
                    if (it.value!=field?.value){
                        it.delete flush:true
                    }
                }
            }
            flexFields = OpportunityFlexField.findAllByCategoryAndName(opportunityFlexFieldCategory,"放款账号")
            flexFields.each { field ->
                OpportunityFlexFieldValue.findAllByField(field).each {
                    if (it.value.contains(OpportunityFlexField.findByCategoryAndName(opportunityFlexFieldCategory,"放款通道")?.value?.substring(0,2))){

                    }else {
                        it.delete flush: true
                    }
                }
            }
        }
        render(view: "batchEdit", model: [opportunityFlexFieldCategory: opportunityFlexFieldCategory])
        return
    }

    @Transactional
    def update(OpportunityFlexField opportunityFlexField)
    {
        if (opportunityFlexField == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityFlexField.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityFlexField.errors, view: 'edit'
            return
        }

        opportunityFlexField.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityFlexField.label', default: 'OpportunityFlexField'), opportunityFlexField.id])
                redirect opportunityFlexField
            }
            '*' { respond opportunityFlexField, [status: OK] }
        }
    }

    @Transactional
    def batchUpdate()
    {
        def opportunityFlexFieldCategoryId = params['opportunityFlexFieldCategory']
        def opportunityFlexFieldCategory = OpportunityFlexFieldCategory.findById(opportunityFlexFieldCategoryId)
        //        println opportunityFlexFieldCategoryId
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        if (params["estimatedLendingDate"])
        {
            def opportunity = opportunityFlexFieldCategory?.opportunity
            //            println "+++++++++++++++++++++++++++---------" + params["estimatedLendingDate"]
            Date date = sdf.parse(params["estimatedLendingDate"]);
            opportunity.estimatedLendingDate = date
            opportunity.save flush: true
        }
        opportunityFlexFieldCategory?.fields?.each {
            //            println "----------------" + it?.value
            it?.value = params[it?.name]
            //            println "==============" + params[it?.name]
            it.save flush: true
            //            println "++++++++++++++++" + it.value
        }
        if (params["outReport"] == "外访报告")
        {
            def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunityFlexFieldCategory?.opportunity)
            render(view: "showOpportunityflexfield", model: [opportunity: opportunityFlexFieldCategory?.opportunity, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys])
        }
        else
        {
            redirect(controller: "opportunity", action: "show", params: [id: opportunityFlexFieldCategory?.opportunity.id])
            return
        }
    }

    @Transactional
    def delete(OpportunityFlexField opportunityFlexField)
    {

        if (opportunityFlexField == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityFlexField.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityFlexField.label', default: 'OpportunityFlexField'), opportunityFlexField.id])
                // redirect action: "index", method: "GET"
                redirect(controller: "opportunityFlexFieldCategory", action: "show", params: [id: opportunityFlexField?.category?.id])

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityFlexField.label', default: 'OpportunityFlexField'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    // def updateFlexField()
    // {

    //     def startTime = params["startTime"]
    //     def endTime = params["endTime"]

    //     println startTime
    //     println endTime

    //     def flexfieldList = ['无','新新卡','K0241','中航621','中航848','中航324','中航813','中航146','中航628','中航125','中航408','中航506','渤海264','渤海295', '中航142', '中航907', '中航662', '中航463', '中诚997', '中航817', '中航889', '中航809', '中航568', '中航477', '中航118']
    //     def sql = "select distinct o.id from OpportunityFlexFieldValue a left join a.field b left join b.category c left join c.opportunity o where b.name = '放款账号' and (b.value is null or b.value = '') and o.status = 'Pending'"
    //     if (startTime && endTime)
    //     {
    //         sql += " and o.createdDate BETWEEN '${startTime} 00:00:00' AND '${endTime} 00:00:00'"
    //     }
    //     else
    //     {
    //         sql += " and o.createdDate BETWEEN '2017-04-29 17:00:00' AND '2017-06-29 17:00:00'"
    //     }

    //     def ccList = OpportunityFlexFieldValue.executeQuery(sql)
    //     println ccList?.size()
    //     ccList?.each
    //     {
    //         def flexFieldValueList = OpportunityFlexFieldValue.executeQuery("select a.value from OpportunityFlexFieldValue a left join a.field b left join b.category c left join c.opportunity o where o.id = ${it} and b.name = '放款账号'")
    //         def list3 = flexfieldList.minus(flexFieldValueList)
    //         println it + " : " + list3
    //         if (list3?.size() > 0) 
    //         {
    //             def flexField = OpportunityFlexFieldValue.executeQuery("select a.field from OpportunityFlexFieldValue a left join a.field b left join b.category c left join c.opportunity o where o.id = ${it} and b.name = '放款账号' and a.value = '中航324'")
    //             list3?.each
    //             {
    //                 OpportunityFlexFieldValue opportunityFlexFieldValue = new OpportunityFlexFieldValue()
    //                 def displayOrder = FlexFieldValue.executeQuery("select displayOrder from FlexFieldValue where field.id = 76 and value = '${it}'")
    //                 opportunityFlexFieldValue.displayOrder = displayOrder[0]
    //                 opportunityFlexFieldValue.field = flexField[0]
    //                 opportunityFlexFieldValue.value = it
    //                 opportunityFlexFieldValue.save flush: true
    //             }
    //         }
    //     }
    //     println ccList?.size() + ' is ok'
    //     render ccList?.size() + ' is ok'
    // }
}