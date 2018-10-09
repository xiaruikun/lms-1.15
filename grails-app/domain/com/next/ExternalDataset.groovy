package com.next

class ExternalDataset
{
    Date createdDate = new Date()
    Date modifiedDate = new Date()

    ExternalDatasetType type

    static belongsTo = [provider: ExternalDataProvider]

    static hasMany = [items: ExternalDatasetItem]

    static constraints = {
        type nullable: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
