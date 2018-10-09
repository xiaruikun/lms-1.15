package com.next

class ProductMortgageRate
{
    Double mortgageRate
    AssetType assetType

    static belongsTo = [product: ProductAccount]

    static constraints = {
        mortgageRate:
        unique:
        ['product', 'assetType']
    }
}
