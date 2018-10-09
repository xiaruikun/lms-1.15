package com.next

class TerritoryFlowNextStage
{
    TerritoryFlow nextStage

    static belongsTo = [flow: TerritoryFlow]

    static constraints = {
        nextStage unique: ['flow']
    }
}
