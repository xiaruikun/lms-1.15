package com.next

class OpportunityProduct implements Comparable //initProductInterest()
{
    ProductInterestType productInterestType
    Boolean fixedRate = true
    Boolean installments = false
    Double rate
    Integer monthes = 0
    Integer firstPayMonthes = 0
    //上扣息月份数
    Integer lastPayMonthes = 1
    //下扣息月份数

    ProductInterestType radixProductInterestType
    //基数息费类型

    User createBy
    User modifyBy

    ContractType contractType
    //合同类型

    String externalId
    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [opportunity: Opportunity, product: ProductAccount]
    static constraints = {
        createBy nullable: true, blank: true
        modifyBy nullable: true, blank: true
        createdDate nullable: true, blank: true
        modifiedDate nullable: true, blank: true
        fixedRate nullable: true, blank: true
        monthes nullable: true, blank: true
        contractType nullable: true, blank: false
        externalId maxSize: 32, blank: true, nullable: true
        // radix nullable: true, blank: false
        radixProductInterestType nullable: true, blank: false
        lastPayMonthes nullable: true, blank: true
    }

    static mapping = {
        cache true
    }

    @Override
    int compareTo(Object OpportunityProduct)
    {

        def opProduct = (OpportunityProduct) OpportunityProduct
        if (productInterestType?.name == opProduct?.productInterestType?.name)
        {
            return 0
        }
        else if (productInterestType?.name > opProduct?.productInterestType?.name)
        {
            return 1
        }
        else
        {
            return -1
        }
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
