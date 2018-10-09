package com.next

class Territory
{
    String name

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    String externalId

    Boolean inheritNotification = true
    Boolean inheritTeam = true
    Boolean inheritRole = true
    Boolean inheritFlow = true

    //    LiquidityRiskReportTemplate liquidityRiskReportTemplate

    Integer level = 0

    static belongsTo = [parent: Territory]

    static constraints = {
        name maxSize: 64, unique: true
        parent nullable: true, blank: true
        externalId nullable: true, blank: true //, unique: true

        //        liquidityRiskReportTemplate nullable: true, blank: true
    }

    def beforeInsert()
    {
        if (!externalId)
        {
            externalId = UUID.randomUUID().toString()
        }

        if (parent)
        {
            level = parent.level + 1
        }
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()

        if (parent)
        {
            level = parent.level + 1
        }
    }

    String toString()
    {
        name
    }
}
