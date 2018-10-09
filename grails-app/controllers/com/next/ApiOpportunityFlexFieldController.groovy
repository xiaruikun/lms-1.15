package com.next

import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

class ApiOpportunityFlexFieldController
{
    static allowedMethods = [save: "POST", delete: "DELETE"]

    @Secured(['permitAll'])
    @Transactional
    def update()
    {
        //修改弹性域详情
        println "******************* update ********************"
        def json = request.JSON
        println json

        String serialNumber = json["serialNumber"]
        String fieldName = json["trustAccount"]
        String fieldValue = json["trustCode"]
        def opportunity = Opportunity.findBySerialNumber(serialNumber)
        def flexFieldCategoryList = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        flexFieldCategoryList.each {
            def fieldList = it?.fields
            fieldList.each {
                if (it?.name == "放款账号")
                {
                    it?.value = fieldName
                    it.save flush: true
                }
                if (it?.name == "放款通道")
                {
                    it?.value = fieldValue
                    it.save flush: true
                }
            }
        }
        def result = [code: 4700, message: "接收成功"]
        render JsonOutput.toJson(result), status: 200
    }
}