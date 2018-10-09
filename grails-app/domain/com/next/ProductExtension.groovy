package com.next

class ProductExtension
{
    Integer numberOfMonths
    Double maximumServiceChargePerMonth
    Double totalServiceCharge

    static belongsTo = [product: ProductAccount]

    static constraints = {}
}
