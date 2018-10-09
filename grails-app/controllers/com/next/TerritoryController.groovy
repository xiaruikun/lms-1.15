package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class TerritoryController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        //        params.max = Math.min(max ?: 10, 100)
        def offset = params.offset
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def list = Territory.findAll("from Territory where level = 0", [max: max, offset: offset])
        def count = Territory.findAllByLevel(0)?.size()
        respond list, model: [territoryCount: count]
    }

    def show(Territory territory)
    {
        def territoryAccounts = TerritoryAccount.findAllByTerritory(territory)
        def territoryProducts = TerritoryProduct.findAllByTerritory(territory)
        def territoryCities = TerritoryCity.findAllByTerritory(territory)
        def territoryTeams = TerritoryTeam.findAllByTerritory(territory)
        def territoryRoles = TerritoryRole.findAllByTerritory(territory)
        def territoryNotifications = TerritoryNotification.findAllByTerritory(territory)
        def territoryFlows = TerritoryFlow.findAll("from TerritoryFlow where territory.id = ${territory?.id} order by" + " executionSequence ASC")
        def territoryFlexFieldCategories = TerritoryFlexFieldCategory.findAllByTerritory(territory)
        def subTerritories = Territory.findAllByParent(territory)
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        def territoryWorkflows = TerritoryWorkflow.findAllByTerritory(territory)
        respond territory, model: [territoryAccounts: territoryAccounts, territoryProducts: territoryProducts,
            territoryCities: territoryCities, territoryTeams: territoryTeams, territoryRoles: territoryRoles, territoryNotifications: territoryNotifications,
            territoryFlows: territoryFlows, territoryFlexFieldCategories: territoryFlexFieldCategories, subTerritories: subTerritories, territoryOpportunityWorkflows: territoryOpportunityWorkflows, territoryWorkflows: territoryWorkflows]

    }

    def create()
    {
        respond new Territory(params)
    }

    @Transactional
    def save(Territory territory)
    {
        if (territory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territory.errors, view: 'create'
            return
        }

        if (territory.inheritTeam || territory.inheritRole || territory.inheritNotification || territory.inheritFlow)
        {
            if (!territory?.parent)
            {
                flash.message = message(code: 'territory.parent.nullable.message')
                respond territory.errors, view: 'create'
                return
            }
        }

        territory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'territory.label',
                                                                                        default: 'Territory'),
                    territory.id])
                redirect territory
            }
            '*' { respond territory, [status: CREATED] }
        }
    }

    def edit(Territory territory)
    {
        respond territory
    }

    @Transactional
    def update(Territory territory)
    {
        if (territory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (territory.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond territory.errors, view: 'edit'
            return
        }

        //旧流程
        if (territory.inheritTeam || territory.inheritRole || territory.inheritNotification || territory.inheritFlow)
        {
            if (!territory?.parent)
            {
                flash.message = message(code: 'territory.parent.nullable.message')
                respond territory.errors, view: 'create'
                return
            }
        }
        if (territory.inheritNotification)
        {
            def territoryNotifications = TerritoryNotification.findAllByTerritory(territory)
            if (territoryNotifications.size() > 0)
            {
                flash.message = message(code: 'territory.edit.permission.denied')
                respond territory.errors, view: 'edit'
                return
            }
        }
        if (territory.inheritFlow)
        {
            def territoryFlows = TerritoryFlow.findAllByTerritory(territory)
            if (territoryFlows.size() > 0)
            {
                flash.message = message(code: 'territory.edit.permission.denied')
                respond territory.errors, view: 'edit'
                return
            }
        }

        //新流程
        if (territory.inheritTeam)
        {
            if (!territory?.parent)
            {
                flash.message = message(code: 'territory.parent.nullable.message')
                respond territory.errors, view: 'create'
                return
            }
        }


        territory.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'territory.label',
                                                                                        default: 'Territory'),
                    territory.id])
                redirect territory
            }
            '*' { respond territory, [status: OK] }
        }
    }

    @Transactional
    def delete(Territory territory)
    {

        if (territory == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        territory.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'territory.label',
                                                                                        default: 'Territory'),
                    territory.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'territory.label',
                                                                                          default: 'Territory'),
                    params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR'])
    def searchTerritory()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset
        def name = params['name']
        def territoryList = Territory.findAll("from Territory where name like '%${name}%'", [max: max, offset: offset])
        def list = Territory.findAll("from Territory where name like '%${name}%'")
        def count = list.size()

        respond territoryList, model: [territoryCount: count], view: 'index'
    }

    def prepareCopyFlow()
    {
        def territory = Territory.findById(params['territory'])
        def preOpportunityWorkflow = OpportunityWorkflow.findById(params['preOpportunityWorkflow'])
        def opportunityWorkFlows = OpportunityWorkflow.list()
        def opportunityWorkflowRoles
        def opportunityWorkflowNotifications
        def opportunityWorkflowStages
        opportunityWorkFlows?.each {
            opportunityWorkflowRoles = OpportunityWorkflowRole.findAllByWorkflow(it)
            opportunityWorkflowNotifications = OpportunityWorkflowNotification.findAllByWorkflow(it)
            opportunityWorkflowStages = OpportunityWorkflowStage.findAllByWorkflow(it)
            if (opportunityWorkflowRoles?.size() > 0 || opportunityWorkflowNotifications?.size() > 0 || opportunityWorkflowStages?.size() > 0)
            {
                opportunityWorkFlows -= it
            }
        }

        if (territory)
        {
            def opportunityType = OpportunityType.findByCode("10")
            opportunityWorkFlows?.each {
                if (!(it?.opportunityType == opportunityType))
                {
                    opportunityWorkFlows -= it
                }
            }
            respond territory, model: [opportunityWorkFlows: opportunityWorkFlows], view: 'copyFlow'
        }
        else
        {
            respond preOpportunityWorkflow, model: [preOpportunityWorkflow: preOpportunityWorkflow, opportunityWorkFlows: opportunityWorkFlows], view: 'copyFlow'
        }
    }

    @Transactional
    def copyFlow()
    {
        def territory = Territory.findById(params['territory'])
        def preOpportunityWorkflow = OpportunityWorkflow.findById(params['preOpportunityWorkflow'])
        def opportunityWorkFlow = OpportunityWorkflow.findById(params['opportunityWorkflow'])
        def opportunityWorkflowRole
        def opportunityWorkflowNotification
        def opportunityWorkflowStage
        def opportunityWorkflowStageCondition
        def opportunityWorkflowStageNextStage
        def opportunityWorkflowEvent
        def opportunityWorkflowStageAttachmentType

        if (territory)
        {
            def territoryRoles = TerritoryRole.findAllByTerritory(territory)
            def territoryNotifications = TerritoryNotification.findAllByTerritory(territory)
            def territoryFlows = TerritoryFlow.findAllByTerritory(territory)

            territoryRoles?.each {
                opportunityWorkflowRole = new OpportunityWorkflowRole()
                opportunityWorkflowRole.workflow = opportunityWorkFlow
                opportunityWorkflowRole.user = it?.user
                opportunityWorkflowRole.teamRole = it?.teamRole
                opportunityWorkflowRole.stage = it?.stage
                if (opportunityWorkflowRole.validate())
                {
                    opportunityWorkflowRole.save flush: true
                }
                else
                {
                    log.info opportunityWorkflowRole.errors
                }
            }
            territoryNotifications?.each {
                opportunityWorkflowNotification = new OpportunityWorkflowNotification()
                opportunityWorkflowNotification.workflow = opportunityWorkFlow
                opportunityWorkflowNotification.user = it?.user
                opportunityWorkflowNotification.stage = it?.stage
                opportunityWorkflowNotification.messageTemplate = it?.messageTemplate
                if (opportunityWorkflowNotification.validate())
                {
                    opportunityWorkflowNotification.save flush: true
                }
                else
                {
                    log.info opportunityWorkflowNotification.errors
                }
            }
            territoryFlows?.each {
                opportunityWorkflowStage = new OpportunityWorkflowStage()
                opportunityWorkflowStage.workflow = opportunityWorkFlow
                opportunityWorkflowStage.executionSequence = it?.executionSequence
                opportunityWorkflowStage.stage = it?.stage
                opportunityWorkflowStage.canReject = it?.canReject
                opportunityWorkflowStage.opportunityLayout = it?.opportunityLayout
                if (opportunityWorkflowStage.validate())
                {
                    opportunityWorkflowStage.save flush: true
                }
                else
                {
                    log.info opportunityWorkflowStage.errors
                }
                it?.conditions?.each {
                    opportunityWorkflowStageCondition = new OpportunityWorkflowStageCondition()
                    opportunityWorkflowStageCondition.stage = opportunityWorkflowStage
                    opportunityWorkflowStageCondition.condition = it?.condition
                    opportunityWorkflowStageCondition.message = it?.message
                    if (opportunityWorkflowStageCondition.validate())
                    {
                        opportunityWorkflowStageCondition.save flush: true
                    }
                    else
                    {
                        log.info opportunityWorkflowStageCondition.errors
                    }
                }
            }
            territoryFlows?.each {
                def currentStage = OpportunityWorkflowStage.findByWorkflowAndStage(opportunityWorkFlow, it?.stage)
                it?.nextStages?.each {
                    opportunityWorkflowStageNextStage = new OpportunityWorkflowStageNextStage()
                    opportunityWorkflowStageNextStage.stage = currentStage
                    opportunityWorkflowStageNextStage.nextStage = OpportunityWorkflowStage.findByWorkflowAndStage(opportunityWorkFlow, it?.nextStage?.stage)
                    if (opportunityWorkflowStageNextStage.validate())
                    {
                        opportunityWorkflowStageNextStage.save flush: true
                    }
                    else
                    {
                        log.info opportunityWorkflowStageNextStage.errors
                    }
                }
            }
        }
        else
        {
            def opportunityWorkflowRoles = OpportunityWorkflowRole.findAllByWorkflow(preOpportunityWorkflow)
            def opportunityWorkflowNotifications = OpportunityWorkflowNotification.findAllByWorkflow(preOpportunityWorkflow)
            def opportunityWorkflowStages = OpportunityWorkflowStage.findAllByWorkflow(preOpportunityWorkflow)

            opportunityWorkflowRoles?.each {
                opportunityWorkflowRole = new OpportunityWorkflowRole()
                opportunityWorkflowRole.workflow = opportunityWorkFlow
                opportunityWorkflowRole.user = it?.user
                opportunityWorkflowRole.teamRole = it?.teamRole
                opportunityWorkflowRole.stage = it?.stage
                opportunityWorkflowRole.document = it?.document
                opportunityWorkflowRole.opportunityLayout = it?.opportunityLayout
                if (opportunityWorkflowRole.validate())
                {
                    opportunityWorkflowRole.save flush: true
                }
                else
                {
                    log.info opportunityWorkflowRole.errors
                }
            }
            opportunityWorkflowNotifications?.each {
                opportunityWorkflowNotification = new OpportunityWorkflowNotification()
                opportunityWorkflowNotification.workflow = opportunityWorkFlow
                opportunityWorkflowNotification.user = it?.user
                opportunityWorkflowNotification.stage = it?.stage
                opportunityWorkflowNotification.messageTemplate = it?.messageTemplate
                opportunityWorkflowNotification.cellphone = it?.cellphone
                if (opportunityWorkflowNotification.validate())
                {
                    opportunityWorkflowNotification.save flush: true
                }
                else
                {
                    log.info opportunityWorkflowNotification.errors
                }
            }
            opportunityWorkflowStages?.each {
                opportunityWorkflowStage = new OpportunityWorkflowStage()
                opportunityWorkflowStage.workflow = opportunityWorkFlow
                opportunityWorkflowStage.executionSequence = it?.executionSequence
                opportunityWorkflowStage.stage = it?.stage
                opportunityWorkflowStage.canReject = it?.canReject
                opportunityWorkflowStage.opportunityLayout = it?.opportunityLayout
                opportunityWorkflowStage.document = it?.document
                if (opportunityWorkflowStage.validate())
                {
                    opportunityWorkflowStage.save flush: true
                }
                else
                {
                    log.info opportunityWorkflowStage.errors
                }

            }
            opportunityWorkflowStages?.each {
                def currentStage = OpportunityWorkflowStage.findByWorkflowAndStage(opportunityWorkFlow, it?.stage)
                it?.conditions?.each {
                    opportunityWorkflowStageCondition = new OpportunityWorkflowStageCondition()
                    opportunityWorkflowStageCondition.stage = currentStage
                    opportunityWorkflowStageCondition.condition = it?.condition
                    opportunityWorkflowStageCondition.message = it?.message
                    opportunityWorkflowStageCondition.component = it?.component
                    opportunityWorkflowStageCondition.log = it?.log
                    opportunityWorkflowStageCondition.executeSequence = it?.executeSequence
                    opportunityWorkflowStageCondition.nextStage = OpportunityWorkflowStage.findByWorkflowAndStage(opportunityWorkFlow, it?.nextStage?.stage)
                    if (opportunityWorkflowStageCondition.validate())
                    {
                        opportunityWorkflowStageCondition.save flush: true
                    }
                    else
                    {
                        log.info opportunityWorkflowStageCondition.errors
                    }
                }
                it?.nextStages?.each {
                    opportunityWorkflowStageNextStage = new OpportunityWorkflowStageNextStage()
                    opportunityWorkflowStageNextStage.stage = currentStage
                    opportunityWorkflowStageNextStage.nextStage = OpportunityWorkflowStage.findByWorkflowAndStage(opportunityWorkFlow, it?.nextStage?.stage)
                    if (opportunityWorkflowStageNextStage.validate())
                    {
                        opportunityWorkflowStageNextStage.save flush: true
                    }
                    else
                    {
                        log.info opportunityWorkflowStageNextStage.errors
                    }
                }
                it?.events?.each {
                    opportunityWorkflowEvent = new OpportunityWorkflowEvent()
                    opportunityWorkflowEvent.stage = currentStage
                    opportunityWorkflowEvent.name = it?.name
                    opportunityWorkflowEvent.isSynchronous = it?.isSynchronous
                    opportunityWorkflowEvent.executeSequence = it?.executeSequence
                    opportunityWorkflowEvent.script = it?.script
                    opportunityWorkflowEvent.log = it?.log
                    opportunityWorkflowEvent.component = it?.component
                    opportunityWorkflowEvent.startTime = it?.startTime
                    opportunityWorkflowEvent.endTime = it?.endTime
                    opportunityWorkflowEvent.document = it?.document
                    if (opportunityWorkflowEvent.validate())
                    {
                        opportunityWorkflowEvent.save flush: true
                    }
                    else
                    {
                        log.info opportunityWorkflowEvent.errors
                    }
                }
                def opportunityWorkflowStageAttachmentTypes = OpportunityWorkflowStageAttachmentType.findAllByStage(it)
                opportunityWorkflowStageAttachmentTypes?.each {
                    opportunityWorkflowStageAttachmentType = new OpportunityWorkflowStageAttachmentType()
                    opportunityWorkflowStageAttachmentType.stage = currentStage
                    opportunityWorkflowStageAttachmentType.attachmentType = it?.attachmentType
                    if (opportunityWorkflowStageAttachmentType.validate())
                    {
                        opportunityWorkflowStageAttachmentType.save flush: true
                    }
                    else
                    {
                        log.info opportunityWorkflowStageAttachmentType.errors
                    }
                }
            }
        }
        redirect controller: "opportunityWorkflow", action: "show", id: opportunityWorkFlow?.id
    }
}
