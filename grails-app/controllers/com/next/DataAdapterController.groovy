package com.next

import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

class DataAdapterController
{

    def opportunityService

    @Transactional
    @Secured(['permitAll'])
    def receive()
    {
        println "##############京北方数据返回############"
        def json = request.JSON
        println json

        def serialNumber = json['serialNumber']

        def opportunity = Opportunity.findBySerialNumber(serialNumber)
        if (opportunity?.stage?.code == '18' || opportunity?.stage?.code == '22')
        {
            def status = json['status']
            if (status == "Completed")
            {
                opportunityService.approve(opportunity)
            }
            else if (status == "Cancel")
            {
                //暂无回退工作流状态
                def message = json['responseMessage']
                OpportunityAutitTrail opportunityAutitTrail = new OpportunityAutitTrail()
                opportunityAutitTrail.opportunity = opportunity
                opportunityAutitTrail.log = message
                opportunityAutitTrail.stage = opportunity.stage
                opportunityAutitTrail.save flush: true
                opportunityService.reject(opportunity)
            }
        }

        def result = [code: 4700, message: "接收成功"]
        render JsonOutput.toJson(result), status: 200
    }

}
