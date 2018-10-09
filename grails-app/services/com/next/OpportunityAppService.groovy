package com.next

/*@Transactional*/
/*@CompileStatic
@TypeChecked*/

class OpportunityAppService
{
    static scope = "singleton"

    OpportunityNotificationService opportunityNotificationService

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def initOpportunity(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        def territory = territoryAccount?.territory
        def territoryOpportunityWorkflows
        def flag = true
        def opportunityFlow

        opportunity.territory = territory
        opportunity.save flush: true

        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id}")
        if (opportunityFlows?.size() == 0)
        {
            initTeam(opportunity, workflow)

            def type = OpportunityType.find("from OpportunityType where code = '10'")

            territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAll("from TerritoryOpportunityWorkflow where territory.id = ${territory?.id}")
            if (territoryOpportunityWorkflows?.size() > 0)
            {
                for (
                    territoryWorkflow in
                        territoryOpportunityWorkflows)
                {
                    if (opportunity?.type?.id == territoryWorkflow?.workflow?.opportunityType?.id && territoryWorkflow?.workflow?.active)
                    {
                        flag = true
                        break
                    }
                    else
                    {

                        if (opportunity?.type == type)
                        {
                            flag = false
                        }

                    }
                }
            }
            else
            {
                flag = false
            }

            if (flag)
            {
                initWorkflowRole(opportunity, workflow)
                initWorkflowNotification(opportunity, workflow)
                initWorkFlow(opportunity, workflow)
            }
            else
            {
                initRole(opportunity)
                initNotification(opportunity)
                initFlow(opportunity)
            }

            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? order by executionSequence asc", [opportunity?.id])
            if (opportunityFlow)
            {
                opportunityFlow.startTime = new Date()
                opportunityFlow.save flush: true
            }

            initFlexFieldCategory(opportunity, workflow)
        }
        opportunityNotificationService.sendNotification(opportunity)
    }

    Boolean initTeam(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        Boolean inheritTeam = true
        OpportunityTeam opportunityTeam
        def territoryTeams
        def territoryAccount
        def territory

        //区域
        if (workflow)
        {
            territory = TerritoryOpportunityWorkflow.find("from TerritoryOpportunityWorkflow where workflow.id = ?", [workflow?.id])?.territory
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
        }
        //添加owner到team
        opportunityTeam = OpportunityTeam.find("from OpportunityTeam where user.id = ? and opportunity.id = ?",
                                               [opportunity?.user?.id, opportunity?.id])
        if (!opportunityTeam)
        {
            opportunityTeam = new OpportunityTeam()
            opportunityTeam.user = opportunity?.user
            opportunityTeam.opportunity = opportunity
            if (opportunityTeam.validate())
            {
                opportunityTeam.save flush: true
            }
            else
            {
                opportunityTeam.errors.each {
                    log.info "${it}"
                }
            }
        }

        //复制territoryTeam到team
        while (territory)
        {
            //依次继承所有父级区域team
            if (inheritTeam)
            {
                territoryTeams = TerritoryTeam.findAll("from TerritoryTeam where territory.id = ?", [territory?.id])
                for (
                    team in
                        territoryTeams)
                {
                    opportunityTeam = OpportunityTeam.find("from OpportunityTeam where opportunity.id = ? and user" + ".id=?", [opportunity?.id, team?.user?.id])
                    if (!opportunityTeam)
                    {
                        opportunityTeam = new OpportunityTeam()
                        opportunityTeam.user = team?.user
                        opportunityTeam.opportunityLayout = team?.opportunityLayout
                        opportunityTeam.opportunity = opportunity
                        if (opportunityTeam.validate())
                        {
                            opportunityTeam.save flush: true
                        }
                        else
                        {
                            opportunityTeam.errors.each {
                                log.info "${it}"
                            }
                        }
                    }
                }

                inheritTeam = territory?.inheritTeam
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
    }

    def initWorkflowNotification(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount
        def territory
        OpportunityNotification opportunityNotification
        def opportunityWorkflow
        def opportunityWorkflowNotifications
        def territoryOpportunityWorkflows

        def stage
        def message
        def opportunityType = OpportunityType.find("from OpportunityType where code = '10'")


        //区域
        if (workflow)
        {
            opportunityWorkflow = workflow
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
            territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAll("from TerritoryOpportunityWorkflow where territory.id = ${territory?.id}")
            territoryOpportunityWorkflows?.each {
                if (opportunity?.type?.id == it?.workflow?.opportunityType?.id && it?.workflow?.active)
                {
                    opportunityWorkflow = it?.workflow
                }
            }
        }

        opportunityWorkflowNotifications = OpportunityWorkflowNotification.findAll("from OpportunityWorkflowNotification where workflow.id = ${opportunityWorkflow?.id}")

        for (
            notification in
                opportunityWorkflowNotifications)
        {
            opportunityNotification = OpportunityNotification.find("from OpportunityNotification where " + "opportunity.id = ? and user.id = ? and " + "stage.id = ? and cellphone = ?", [opportunity?.id,
                notification?.user?.id,
                notification?.stage?.id, notification?.cellphone])
            if (!opportunityNotification)
            {
                opportunityNotification = new OpportunityNotification()
                opportunityNotification.opportunity = opportunity
                opportunityNotification.user = notification?.user
                opportunityNotification.stage = notification?.stage
                opportunityNotification.messageTemplate = notification?.messageTemplate
                opportunityNotification.toManager = notification?.toManager
                opportunityNotification.cellphone = notification?.cellphone
                if (opportunityNotification.validate())
                {
                    opportunityNotification.save flush: true
                }
                else
                {
                    log.info opportunityNotification.errors
                }
            }
        }
    }

    def initWorkflowRole(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount
        def territory
        OpportunityRole opportunityRole
        def opportunityWorkflow
        def opportunityWorkflowRoles
        def territoryOpportunityWorkflows

        //区域
        if (workflow)
        {
            opportunityWorkflow = workflow
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
            territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAll("from TerritoryOpportunityWorkflow where territory.id = ${territory?.id}")
            territoryOpportunityWorkflows?.each {
                if (opportunity?.type?.id == it?.workflow?.opportunityType?.id && it?.workflow?.active)
                {
                    opportunityWorkflow = it?.workflow
                }
            }
        }

        opportunityWorkflowRoles = OpportunityWorkflowRole.findAll("from OpportunityWorkflowRole where workflow.id = ${opportunityWorkflow?.id}")

        for (
            role in
                opportunityWorkflowRoles)
        {
            opportunityRole = OpportunityRole.find("from OpportunityRole where opportunity.id = ? and user.id" + " = ? and stage.id = ?", [opportunity?.id, role?.user?.id,
                role?.stage?.id])
            if (!opportunityRole)
            {
                opportunityRole = new OpportunityRole()
                opportunityRole.opportunity = opportunity
                opportunityRole.user = role?.user
                opportunityRole.teamRole = role?.teamRole
                opportunityRole.stage = role?.stage
                opportunityRole.opportunityLayout = role?.opportunityLayout
                if (opportunityRole.validate())
                {
                    opportunityRole.save flush: true
                }
                else
                {
                    log.info opportunityRole.errors
                }
            }
        }
    }

    def initWorkFlow(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        def territoryAccount
        def territory
        def territoryOpportunityWorkflows
        def opportunityWorkflow
        def opportunityWorkflowStages
        OpportunityFlow opportunityFlow
        def opportunityFlowCondition
        def opportunityFlowNextStage
        // def currentStage
        def nextStage
        def opportunityEvent
        def opportunityWorkflowStageAttachmentTypes
        def opportunityFlowAttachmentType


        //区域
        if (workflow)
        {
            opportunityWorkflow = workflow
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
            territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAll("from TerritoryOpportunityWorkflow where territory.id = ${territory?.id}")
            territoryOpportunityWorkflows?.each {
                if (opportunity?.type?.id == it?.workflow?.opportunityType?.id && it?.workflow?.active)
                {
                    opportunityWorkflow = it?.workflow
                }
            }
        }
        opportunityWorkflowStages = OpportunityWorkflowStage.findAll("from OpportunityWorkflowStage where workflow.id = ${opportunityWorkflow?.id}")

        opportunityWorkflowStages.each {
            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage" + ".id = ?", [opportunity?.id, it?.stage?.id])
            if (!opportunityFlow)
            {
                opportunityFlow = new OpportunityFlow()
                opportunityFlow.opportunity = opportunity
                opportunityFlow.stage = it?.stage
                opportunityFlow.canReject = it?.canReject
                opportunityFlow.executionSequence = it?.executionSequence
                opportunityFlow.opportunityLayout = it?.opportunityLayout
                opportunityFlow.document = it?.document
                if (opportunityFlow.validate())
                {
                    opportunityFlow.save flush: true
                }
                else
                {
                    log.info opportunityFlow.errors
                }
            }
        }

        opportunityWorkflowStages?.each {
            opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage" + ".id = ?", [opportunity?.id, it?.stage?.id])
            //conditions
            if (it?.conditions?.size() > 0)
            {
                it?.conditions?.each {
                    nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.nextStage?.stage?.id])
                    opportunityFlowCondition = OpportunityFlowCondition.find("from " + "OpportunityFlowCondition " + "where flow.id = ? and " + "condition = ? and component.id = ? and nextStage.id = ?", [opportunityFlow?.id, it?.condition, it?.component?.id, nextStage?.id])
                    if (!opportunityFlowCondition)
                    {
                        opportunityFlowCondition = new OpportunityFlowCondition()
                        opportunityFlowCondition.flow = opportunityFlow
                        opportunityFlowCondition.condition = it?.condition
                        opportunityFlowCondition.message = it?.message
                        opportunityFlowCondition.component = it?.component
                        opportunityFlowCondition.executeSequence = it?.executeSequence
                        opportunityFlowCondition.nextStage = nextStage

                        if (opportunityFlowCondition.validate())
                        {
                            opportunityFlowCondition.save flush: true
                        }
                        else
                        {
                            log.info opportunityFlowCondition.errors
                        }
                    }
                }
            }

            //nextStages
            if (it?.nextStages?.size() > 0)
            {
                it?.nextStages?.each {
                    // currentStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.stage?.stage?.id])
                    nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.nextStage?.stage?.id])
                    opportunityFlowNextStage = OpportunityFlowNextStage.find("from " + "OpportunityFlowNextStage " + "where flow.id = ? and " + "nextStage.id = ?", [opportunityFlow?.id, nextStage?.id])
                    if (!opportunityFlowNextStage)
                    {
                        opportunityFlowNextStage = new OpportunityFlowNextStage()
                        opportunityFlowNextStage.flow = opportunityFlow
                        opportunityFlowNextStage.nextStage = nextStage
                        if (opportunityFlowNextStage.validate())
                        {
                            opportunityFlowNextStage.save flush: true
                        }
                        else
                        {
                            log.info opportunityFlowNextStage.errors
                        }
                    }
                }
            }

            //events
            if (it?.events?.size() > 0)
            {
                it?.events?.each {
                    opportunityEvent = OpportunityEvent.find("from " + "OpportunityEvent " + "where stage.id = ? and " + "name = ?", [opportunityFlow?.id, it?.name])
                    if (!opportunityEvent)
                    {
                        opportunityEvent = new OpportunityEvent()
                        opportunityEvent.stage = opportunityFlow
                        opportunityEvent.name = it?.name
                        opportunityEvent.isSynchronous = it?.isSynchronous
                        opportunityEvent.executeSequence = it?.executeSequence
                        opportunityEvent.script = it?.script
                        opportunityEvent.log = it?.log
                        opportunityEvent.startTime = it?.startTime
                        opportunityEvent.endTime = it?.endTime
                        opportunityEvent.component = it?.component

                        if (opportunityEvent.validate())
                        {
                            opportunityEvent.save flush: true
                        }
                        else
                        {
                            log.info opportunityEvent.errors
                        }
                    }
                }
            }

            opportunityWorkflowStageAttachmentTypes = OpportunityWorkflowStageAttachmentType.findAll("from OpportunityWorkflowStageAttachmentType where stage.id = ?", [it?.id])
            if (opportunityWorkflowStageAttachmentTypes?.size() > 0)
            {
                opportunityWorkflowStageAttachmentTypes?.each {
                    opportunityFlowAttachmentType = OpportunityFlowAttachmentType.find("from OpportunityFlowAttachmentType where stage.id = ? and attachmentType.id = ?", [opportunityFlow?.id, it?.attachmentType?.id])
                    if (!opportunityFlowAttachmentType)
                    {
                        opportunityFlowAttachmentType = new OpportunityFlowAttachmentType()
                        opportunityFlowAttachmentType.stage = opportunityFlow
                        opportunityFlowAttachmentType.attachmentType = it?.attachmentType
                        if (opportunityFlowAttachmentType.validate())
                        {
                            opportunityFlowAttachmentType.save flush: true
                        }
                        else
                        {
                            log.info opportunityFlowAttachmentType.errors
                        }
                    }
                }
            }

        }
    }

    def initNotification(Opportunity opportunity)
    {
        Boolean inheritNotification = true
        def territoryAccount
        def territory
        def territoryNotifications
        OpportunityNotification opportunityNotification
        def stage
        def message

        territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        territory = territoryAccount?.territory
        while (territory)
        {
            inheritNotification = territory?.inheritNotification
            if (!inheritNotification)
            {
                territoryNotifications = TerritoryNotification.findAll("from TerritoryNotification where territory.id" + " = ?", [territory?.id])
                for (
                    notification in
                        territoryNotifications)
                {
                    opportunityNotification = OpportunityNotification.find("from OpportunityNotification where " + "opportunity.id = ? and user.id = ? and " + "stage.id = ?", [opportunity?.id,
                        notification?.user?.id,
                        notification?.stage?.id])
                    if (!opportunityNotification)
                    {
                        opportunityNotification = new OpportunityNotification()
                        opportunityNotification.opportunity = opportunity
                        opportunityNotification.user = notification?.user
                        opportunityNotification.stage = notification?.stage
                        opportunityNotification.messageTemplate = notification?.messageTemplate
                        opportunityNotification.toManager = notification?.toManager
                        if (opportunityNotification.validate())
                        {
                            opportunityNotification.save flush: true
                        }
                        else
                        {
                            log.info opportunityNotification.errors
                        }
                    }
                }
                break
            }
            else
            {
                territory = territory?.parent
            }
        }
    }

    def initRole(Opportunity opportunity)
    {
        Boolean inheritRole = true
        OpportunityRole opportunityRole
        def territoryRoles
        def territoryAccount
        def territory

        def stage = OpportunityStage.find("from OpportunityStage where code = ?", ["03"])
        def teamRole = TeamRole.find("from TeamRole where name = ?", ["Approval"])
        opportunityRole = OpportunityRole.find("from OpportunityRole where opportunity.id = ? and user.id = ? and " + "stage.id = ?", [opportunity?.id, opportunity?.user?.id, stage?.id])
        if (!opportunityRole)
        {
            opportunityRole = new OpportunityRole()
            opportunityRole.opportunity = opportunity
            opportunityRole.user = opportunity?.user
            opportunityRole.stage = stage
            opportunityRole.teamRole = teamRole
            if (opportunityRole.validate())
            {
                opportunityRole.save flush: true
            }
            else
            {
                println opportunityRole.errors
            }
        }

        territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        territory = territoryAccount?.territory
        while (territory)
        {
            if (inheritRole)
            {
                territoryRoles = TerritoryRole.findAll("from TerritoryRole where territory.id = ?", [territory?.id])
                for (
                    role in
                        territoryRoles)
                {
                    opportunityRole = OpportunityRole.find("from OpportunityRole where opportunity.id = ? and user.id" + " = ? and stage.id = ?", [opportunity?.id, role?.user?.id,
                        role?.stage?.id])
                    if (!opportunityRole)
                    {
                        opportunityRole = new OpportunityRole()
                        opportunityRole.opportunity = opportunity
                        opportunityRole.user = role?.user
                        opportunityRole.teamRole = role?.teamRole
                        opportunityRole.stage = role?.stage
                        if (opportunityRole.validate())
                        {
                            opportunityRole.save flush: true
                        }
                        else
                        {
                            log.info opportunityRole.errors
                        }
                    }
                }

                inheritRole = territory?.inheritRole
                territory = territory?.parent
            }
            else
            {
                break
            }
        }
    }

    def initFlow(Opportunity opportunity)
    {
        def territoryAccount
        def territory
        def territoryFlows
        OpportunityFlow opportunityFlow
        def opportunityFlowCondition
        def opportunityFlowNextStage
        Boolean inheritFlow = true

        territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        territory = territoryAccount?.territory

        while (territory)
        {
            inheritFlow = territory?.inheritFlow
            if (!inheritFlow)
            {
                territoryFlows = TerritoryFlow.findAll("from TerritoryFlow where territory.id = ?", [territory?.id])
                for (
                    flow in
                        territoryFlows)
                {
                    opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage" + ".id = ?", [opportunity?.id, flow?.stage?.id])
                    if (!opportunityFlow)
                    {
                        opportunityFlow = new OpportunityFlow()
                        opportunityFlow.opportunity = opportunity
                        opportunityFlow.stage = flow?.stage
                        opportunityFlow.canReject = flow?.canReject
                        opportunityFlow.executionSequence = flow?.executionSequence
                        opportunityFlow.opportunityLayout = flow?.opportunityLayout
                        if (opportunityFlow.validate())
                        {
                            opportunityFlow.save flush: true
                            flow?.conditions?.each {
                                opportunityFlowCondition = OpportunityFlowCondition.find("from " + "OpportunityFlowCondition " + "where flow.id = ? and " + "condition = ?", [opportunityFlow?.id, it?.condition])
                                if (!opportunityFlowCondition)
                                {
                                    opportunityFlowCondition = new OpportunityFlowCondition()
                                    opportunityFlowCondition.flow = opportunityFlow
                                    opportunityFlowCondition.condition = it?.condition
                                    opportunityFlowCondition.message = it?.message
                                    if (opportunityFlowCondition.validate())
                                    {
                                        opportunityFlowCondition.save flush: true
                                    }
                                    else
                                    {
                                        log.info opportunityFlowCondition.errors
                                    }
                                }
                            }

                        }
                        else
                        {
                            log.info opportunityFlow.errors
                        }
                    }
                }
                break
            }
            else
            {
                territory = territory?.parent
            }
        }

        def currentStage
        def nextStage
        territoryFlows?.each {
            if (it?.nextStages?.size() > 0)
            {
                it?.nextStages?.each {
                    currentStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.flow?.stage?.id])
                    nextStage = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?", [opportunity?.id, it?.nextStage?.stage?.id])
                    opportunityFlowNextStage = OpportunityFlowNextStage.find("from " + "OpportunityFlowNextStage " + "where flow.id = ? and " + "nextStage.id = ?", [currentStage?.id, nextStage?.id])
                    if (!opportunityFlowNextStage)
                    {
                        opportunityFlowNextStage = new OpportunityFlowNextStage()
                        opportunityFlowNextStage.flow = currentStage
                        opportunityFlowNextStage.nextStage = nextStage
                        if (opportunityFlowNextStage.validate())
                        {
                            opportunityFlowNextStage.save flush: true
                        }
                        else
                        {
                            log.info opportunityFlowNextStage.errors
                        }
                    }
                }
            }
        }

    }

    Boolean initFlexFieldCategory(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        println "initFlexFieldCategory"
        //        Boolean inheritTeam = true
        OpportunityFlexFieldCategory opportunityFlexFieldCategory
        OpportunityFlexField opportunityFlexField
        OpportunityFlexFieldValue opportunityFlexFieldValue
        def territoryFlexFieldCategories
        SortedSet<FlexField> fields
        SortedSet<FlexFieldValue> values
        def territoryAccount
        def territory

        //区域
        if (workflow)
        {
            territory = TerritoryOpportunityWorkflow.find("from TerritoryOpportunityWorkflow where workflow.id = ?", [workflow?.id])?.territory
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
        }

        //复制territoryFlexFieldCategory到opportunityFlexFieldCategory
        while (territory)
        {
            territoryFlexFieldCategories = TerritoryFlexFieldCategory.findAll("from TerritoryFlexFieldCategory where territory.id = ? and opportunityType.id = ?", [territory?.id, opportunity?.type?.id])
            if (territoryFlexFieldCategories)
            {
                for (
                    flexFieldCategories in
                        territoryFlexFieldCategories)
                {
                    opportunityFlexFieldCategory = OpportunityFlexFieldCategory.find("from OpportunityFlexFieldCategory where opportunity.id = ? and flexFieldCategory" + ".id=?", [opportunity?.id, flexFieldCategories?.flexFieldCategory?.id])
                    if (!opportunityFlexFieldCategory)
                    {
                        opportunityFlexFieldCategory = new OpportunityFlexFieldCategory()
                        opportunityFlexFieldCategory.flexFieldCategory = flexFieldCategories?.flexFieldCategory
                        opportunityFlexFieldCategory.opportunity = opportunity
                        if (opportunityFlexFieldCategory.validate())
                        {
                            opportunityFlexFieldCategory.save flush: true
                        }
                        else
                        {
                            println opportunityFlexFieldCategory.errors
                        }
                        fields = flexFieldCategories?.flexFieldCategory?.fields
                        if (fields)
                        {
                            for (
                                FlexField field in
                                    fields)
                            {
                                opportunityFlexField = new OpportunityFlexField()
                                opportunityFlexField.category = opportunityFlexFieldCategory
                                opportunityFlexField.dataType = field?.dataType
                                opportunityFlexField.defaultValue = field?.defaultValue
                                opportunityFlexField.description = field?.description
                                opportunityFlexField.name = field?.name
                                opportunityFlexField.displayOrder = field?.displayOrder
                                opportunityFlexField.valueConstraints = field?.valueConstraints
                                if (opportunityFlexField.validate())
                                {
                                    opportunityFlexField.save flush: true
                                }
                                else
                                {
                                    println opportunityFlexField.errors
                                }
                                values = field?.values
                                if (values)
                                {
                                    for (
                                        FlexFieldValue value in
                                            values)
                                    {
                                        opportunityFlexFieldValue = new OpportunityFlexFieldValue()
                                        opportunityFlexFieldValue.field = opportunityFlexField
                                        opportunityFlexFieldValue.value = value?.value
                                        opportunityFlexFieldValue.displayOrder = value?.displayOrder
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
                            }
                        }
                    }
                }
            }
            territory = territory?.parent
        }
    }
}
