//除武汉分公司、武汉机构、北京3.0、上海3.0外的所有在途订单
Date date = new Date()
println "定时任务开始时间startTime:" + date
def cityList = com.next.City.findAll()
cityList.each {
    def sql = "from Opportunity where status = 'Pending' and account.id not in (221, 343, 351, 357, 359) and territory.id not in (41, 167, 177, 183,185) and stage.id not in (1,2,3,12) and createdDate >= '20170403' and contact.city.name = '${it?.name}'"
    def opportunityList = com.next.Opportunity.findAll(sql)
    def causeOfFailure = com.next.CauseOfFailure.findByName("超时")
    Date today = new Date()
    def daysBetween = { Date startDate, Date endDate ->
        if (startDate && endDate)
        {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            startDate = sdf.parse(sdf.format(startDate))
            endDate = sdf.parse(sdf.format(endDate))
            java.util.Calendar cal = java.util.Calendar.getInstance()
            cal.setTime(startDate)
            long time1 = cal.getTimeInMillis()
            cal.setTime(endDate)
            long time2 = cal.getTimeInMillis()
            long days = (time2 - time1) / (1000 * 3600 * 24)

            return Integer.parseInt(String.valueOf(days))
        }
        else
        {
            return 0
        }

    }
    println "-------------------------------------------------------"
    println "${it?.name}共有" + opportunityList.size() + "个单子查询出来"
    println "-------------------------------------------------------"
    int i = 0
    for (
        def opportunity in
            opportunityList)
    {
        def currentFlow = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)

        def opportunityStage = com.next.OpportunityStage.findByCode("04")
        //基础材料已提供
        def opportunityFirstFlow = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)

        opportunityStage = com.next.OpportunityStage.findByCode("08") //面谈已完成->审批已完成
        def opportunitySecondFlow = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)

        opportunityStage = com.next.OpportunityStage.findByCode("19") //合同已签署
        def opportunityThirdFlow = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)

        opportunityStage = com.next.OpportunityStage.findByCode("09") //抵押已完成
        def opportunityFinalFlow = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)

        //合肥在风控审批结果阶段
        opportunityStage = com.next.OpportunityStage.findByCode("08") //审批已完成
        def opportunityHefeiFlow = com.next.OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)

        if (opportunity?.contact?.city?.name == '合肥')
        {
            if (opportunityFirstFlow && opportunityHefeiFlow && opportunityFirstFlow?.executionSequence <= currentFlow?.executionSequence && currentFlow?.executionSequence < opportunityHefeiFlow?.executionSequence)
            {
                Date startTime = opportunityFirstFlow?.startTime ? opportunityFirstFlow?.startTime : opportunityFirstFlow?.endTime
                def days = daysBetween(startTime, today)
                if (days >= 15)
                {
                    opportunity.causeOfFailure = causeOfFailure
                    opportunity.status = "Failed"
                    opportunity.descriptionOfFailure = "合肥审批超时订单"
                    opportunity.save flush: true
                    i++
                    println opportunity?.serialNumber
                }
                continue
            }

            if (opportunityHefeiFlow && opportunityThirdFlow && opportunityHefeiFlow?.executionSequence <= currentFlow?.executionSequence && currentFlow?.executionSequence < opportunityThirdFlow?.executionSequence)
            {
                Date startTime = opportunityHefeiFlow?.startTime ? opportunityHefeiFlow?.startTime : opportunityHefeiFlow?.endTime
                def days = daysBetween(startTime, today)
                if (days >= 30)
                {
                    opportunity.causeOfFailure = causeOfFailure
                    opportunity.status = "Failed"
                    opportunity.descriptionOfFailure = "合肥合同签署超时订单"
                    opportunity.save flush: true
                    i++
                    println opportunity?.serialNumber
                }
                continue
            }

            if (opportunityThirdFlow && opportunityFinalFlow && opportunityThirdFlow?.executionSequence <= currentFlow?.executionSequence && currentFlow?.executionSequence < opportunityFinalFlow?.executionSequence)
            {
                Date startTime = opportunityThirdFlow?.startTime ? opportunityThirdFlow?.startTime : opportunityThirdFlow?.endTime
                def days = daysBetween(startTime, today)
                if (days >= 2)
                {
                    opportunity.causeOfFailure = causeOfFailure
                    opportunity.status = "Failed"
                    opportunity.descriptionOfFailure = "合肥进抵超时订单"
                    opportunity.save flush: true
                    i++
                    println opportunity?.serialNumber
                }
                continue
            }
        }
        else
        {
            if (opportunityFirstFlow && opportunitySecondFlow && opportunityFirstFlow?.executionSequence <= currentFlow?.executionSequence && currentFlow?.executionSequence < opportunitySecondFlow?.executionSequence)
            {
                Date startTime = opportunityFirstFlow?.startTime ? opportunityFirstFlow?.startTime : opportunityFirstFlow?.endTime
                def days = daysBetween(startTime, today)
                if (days >= 15)
                {
                    opportunity.causeOfFailure = causeOfFailure
                    opportunity.status = "Failed"
                    opportunity.descriptionOfFailure = "审批超时订单"
                    opportunity.save flush: true
                    i++
                    println opportunity?.serialNumber
                }
                continue
            }

            if (opportunitySecondFlow && opportunityThirdFlow && opportunitySecondFlow?.executionSequence <= currentFlow?.executionSequence && currentFlow?.executionSequence < opportunityThirdFlow?.executionSequence)
            {
                Date startTime = opportunitySecondFlow?.startTime ? opportunitySecondFlow?.startTime : opportunitySecondFlow?.endTime
                def days = daysBetween(startTime, today)
                if (days >= 15)
                {
                    opportunity.causeOfFailure = causeOfFailure
                    opportunity.status = "Failed"
                    opportunity.descriptionOfFailure = "合同签署超时订单"
                    opportunity.save flush: true
                    i++
                    println opportunity?.serialNumber
                }
                continue
            }

            if (opportunityThirdFlow && opportunityFinalFlow && opportunityThirdFlow?.executionSequence <= currentFlow?.executionSequence && currentFlow?.executionSequence < opportunityFinalFlow?.executionSequence)
            {
                Date startTime = opportunityThirdFlow?.startTime ? opportunityThirdFlow?.startTime : opportunityThirdFlow?.endTime
                def days = daysBetween(startTime, today)
                if (days >= 30)
                {
                    opportunity.causeOfFailure = causeOfFailure
                    opportunity.status = "Failed"
                    opportunity.descriptionOfFailure = "进抵超时订单"
                    opportunity.save flush: true
                    i++
                    println opportunity?.serialNumber
                }
                continue
            }
        }
    }
    println "-------------------------------------------------------"
    println "${it?.name}共有" + i + "个单子超时"
    println "-------------------------------------------------------"
}
date = new Date()
println "定时任务结束时间endTime:" + date