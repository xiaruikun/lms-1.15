/**
 * 基本费率2.0
 */
{opportunity ->
    def productInterestType = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '基本费率' ")
    def productInterestType4 = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '信用调整' ")
    //def productInterestType1 = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '本金违约金' ")
    //def productInterestType2 = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '早偿违约金' ")
    //def productInterestType3 = com.next.OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${opportunity.id} and productInterestType.name = '利息违约金' ")
    if (opportunity?.lender?.level?.name == 'A'){
        if (productInterestType)
        {
            return true
        } else {
            return false
        }
    }else {
        if (productInterestType&&productInterestType4)
        {
            return true
        } else {
            return false
        }
    }
}

/**
 *校验等额本息产品加息项费率
 */
        {opportunity ->
            if (opportunity.product.name == '等额本息'){
                def ops = com.next.OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity?.productAccount)
                for (def it :ops) {
                    def type = it.productInterestType.name
                    if (type=="二抵加收费率"||type=="信用调整"||type=="郊县"||type=="大头小尾"||type=="老人房（65周岁以上）"||type=="老龄房（房龄35年以上）"||type=="非7成区域"){
                        println it.contractType.name
                        if (it.contractType.name != "借款合同"){
                            return false
                        }
                    }
                }
            }
            return true
        }
