package com.next

class ProductAccount
{
    String name
    Boolean active

    Double maximumAmount
    Double minimumAmount
    Date createdDate = new Date()
    Date modifiedDate = new Date()

    PrincipalPaymentMethod principalPaymentMethod

    String description

    static belongsTo = [product: Product, account: Account]

    static hasMany = [mortgageRates: ProductMortgageRate, extensions: ProductExtension, interests: ProductInterest]

    static constraints = {
        name maxSize: 32, unique: ['product', 'account']
        description maxSize: 1024, nullable: true, blank: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
