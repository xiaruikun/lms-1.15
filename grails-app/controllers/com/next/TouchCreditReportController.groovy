package com.next

class TouchCreditReportController
{

    def creditReportService

    //    @Transactional
    //    @Secured(['permitAll'])
    //    def updateCreditReport()
    //    {
    //        //助手端非下户组大数据，同盾评估
    //        def json = request.JSON
    //        println "******************* updateCreditReport ********************"
    //        println json
    //
    //        def sessionId = json['sessionId']?.toString()
    //        def opportunityId = json['opportunityId']?.toString()
    //        def providerCode = json['providerCode']?.toString()
    //
    //        if (!sessionId || sessionId.length() == 0)
    //        {
    //            def errors = [errorCode: 4300, errorMessage: "请登录"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //
    //        User user = User.findByAppSessionId sessionId
    //        if (!user)
    //        {
    //            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        if (!opportunityId || opportunityId.length() == 0)
    //        {
    //            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        if (!providerCode || providerCode.length() == 0)
    //        {
    //            def errors = [errorCode: 4271, errorMessage: "大数据名称缺失"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        def opportunity = Opportunity.findById opportunityId
    //        if (!opportunity)
    //        {
    //            def errors = [errorCode: 4271, errorMessage: "订单不存在"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //
    //        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
    //
    //        if (opportunityRole && opportunityRole?.teamRole?.name == "Approval")
    //        {
    //            def provider = CreditReportProvider.findByCode providerCode
    //            if (!provider)
    //            {
    //                def errors = [errorCode: 4273, errorMessage: "此大数据不存在"]
    //                render JsonOutput.toJson(errors), status: 400
    //                return
    //            }
    //            opportunity.contacts.each {
    //                creditReportService.generateReport(provider, opportunity, it.contact)
    //            }
    //            def message = [successCode: 4274, successMessage: "大数据查询成功"]
    //            render JsonOutput.toJson(message), status: 200
    //        }
    //        else
    //        {
    //            def errors = [errorCode: 4270, errorMessage: "无权限"]
    //            render JsonOutput.toJson(errors), status: 400
    //        }
    //
    //    }
    //
    //    @Transactional
    //    @Secured(['permitAll'])
    //    def query()
    //    {
    //        //助手端非下户组大数据，同盾评估
    //        def json = request.JSON
    //        println "******************* query ********************"
    //        println json
    //
    //        def sessionId = json['sessionId']?.toString()
    //        def opportunityId = json['opportunityId']?.toString()
    //        def providerCode = json['providerCode']?.toString()
    //
    //        if (!sessionId || sessionId.length() == 0)
    //        {
    //            def errors = [errorCode: 4300, errorMessage: "请登录"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //
    //        User user = User.findByAppSessionId sessionId
    //        if (!user)
    //        {
    //            def errors = [errorCode: 4400, errorMessage: "您的账号已在别处登录！如非本人操作，请及时修改密码"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        if (!opportunityId || opportunityId.length() == 0)
    //        {
    //            def errors = [errorCode: 4270, errorMessage: "订单缺失"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        if (!providerCode || providerCode.length() == 0)
    //        {
    //            def errors = [errorCode: 4271, errorMessage: "大数据名称缺失"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        def opportunity = Opportunity.findById opportunityId
    //        if (!opportunity)
    //        {
    //            def errors = [errorCode: 4272, errorMessage: "订单不存在"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        def provider = CreditReportProvider.findByCode providerCode
    //        if (!provider)
    //        {
    //            def errors = [errorCode: 4273, errorMessage: "此大数据不存在"]
    //            render JsonOutput.toJson(errors), status: 400
    //            return
    //        }
    //        String sql = "from CreditReport as c where c.opportunity.id=${opportunity.id} and c.provider.id=${provider.id} order by c.createdDate ASC"
    //        def creditReportList = CreditReport.findAll(sql)
    //        render creditReportList as JSON
    //
    //    }

}
