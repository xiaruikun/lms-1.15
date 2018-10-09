package com.next

class AssetPoolOpportunity
{
    String opportunitySerialNumber
    String assetPoolName

    static belongsTo = [assetPool: AssetPool, opportunity: Opportunity]
    static constraints = {
        opportunitySerialNumber maxSize: 128, nullable: true, blank: true
        assetPoolName maxSize: 32, nullable: true, blank: true
        opportunity nullable: true, blank: true
        assetPool nullable: true, blank: true
    }

    static mapping = {
        sort 'opportunity'
    }

    def beforeInsert()
    {
        opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
        assetPool = AssetPool.findByName(assetPoolName)
    }

    def beforeUpdate()
    {
        opportunity = Opportunity.findBySerialNumber(opportunitySerialNumber)
        assetPool = AssetPool.findByName(assetPoolName)
    }
}
