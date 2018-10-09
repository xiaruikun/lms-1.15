package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * @Author 班旭娟
 * @CreatedDate 2017-4-21
 * ModifiedDate 2017-4-28
 */
@Transactional
@CompileStatic
@TypeChecked
class WorkflowInstanceUserService
{

    static scope = "singleton"

    def initWorkflowInstanceUser(WorkflowInstanceStage workflowInstanceStage, Opportunity opportunity, WorkflowStage workflowStage)
    {
        def workflowInstanceUser

        def workflowUsers = WorkflowUser.findAll("from WorkflowUser where stage.id = ${workflowStage?.id}")
        def opportunityTeams = OpportunityTeam.findAll("from OpportunityTeam where opportunity.id = ${opportunity?.id}")
        workflowUsers?.each { workflowUser ->
            opportunityTeams?.each { opportunityTeam ->
                if (opportunityTeam?.user?.position == workflowUser?.position)
                {
                    workflowInstanceUser = WorkflowInstanceUser.find("from WorkflowInstanceUser where user.id = ${opportunityTeam?.user?.id} and stage.id = ${workflowInstanceStage?.id}")
                    if (!workflowInstanceUser)
                    {
                        workflowInstanceUser = new WorkflowInstanceUser()
                        workflowInstanceUser.stage = workflowInstanceStage
                        workflowInstanceUser.user = opportunityTeam?.user
                        workflowInstanceUser.authority = workflowUser?.authority
                        workflowInstanceUser.save flush: true
                    }
                }
            }
        }
    }
}
