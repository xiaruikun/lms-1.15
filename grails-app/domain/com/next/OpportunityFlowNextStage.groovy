package com.next

class OpportunityFlowNextStage
{

    OpportunityFlow nextStage
    static belongsTo = [flow: OpportunityFlow]

    static constraints = {
        nextStage unique: ['flow']
    }
}
