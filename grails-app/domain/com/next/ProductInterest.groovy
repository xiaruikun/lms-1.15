package com.next

class ProductInterest
{
    ContactLevel contactLevel
    //客户级别
    ProductInterestType productInterestType
    //费率
    Boolean fixedRate = true
    Boolean installments = false
    Double maximumRate
    Double minimumRate
    Integer monthesOfStart = 0
    Integer monthesOfEnd = 0
    Integer firstPayMonthes = 1
    static belongsTo = [product: ProductAccount]
    static constraints = {
        contactLevel nullable: true
        //            rate:unique: ['product', 'productInterestType', 'contactLevel', 'monthesOfStart', 'monthesOfEnd']
    }
}
