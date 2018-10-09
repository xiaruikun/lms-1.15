package com.next

class TerritoryProduct
{
    Date startTime
    Date endTime

    Double rate

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    Product product

    static belongsTo = [territory: Territory]

    static constraints = {
        product unqiue: ['territory']
    }

    static mapping = {
        // id composite: ['territory', 'product']
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }

    String toString()
    {
        "${territory} - ${product}"
    }
}
