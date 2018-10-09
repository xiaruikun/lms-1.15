/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    def opportunityProductList = com.next.OpportunityProduct.findAllByProductAndOpportunity(opportunity?.productAccount, opportunity)
    opportunity.monthlyInterest = 0
    double service = 0.0
    double commission = 0.0
    java.math.BigDecimal b1
    java.math.BigDecimal monthlyInterest = new java.math.BigDecimal(Double.toString(opportunity?.monthlyInterest))
    java.math.BigDecimal serviceCharge = new java.math.BigDecimal(Double.toString(service))
    java.math.BigDecimal commissionRate = new java.math.BigDecimal(Double.toString(commission))
    java.math.BigDecimal actualLoanDuration = new java.math.BigDecimal(Integer.toString(opportunity?.actualLoanDuration))
    if (opportunityProductList && opportunityProductList.size() > 0)
    {
        println opportunityProductList
        for (
            def opportunityProduct :
                opportunityProductList)
        {
            b1 = new BigDecimal(Double.toString(opportunityProduct.rate))
            if (opportunityProduct?.productInterestType?.name == "基本费率")
            {
                if (opportunityProduct.installments)
                {
                    monthlyInterest = monthlyInterest.add(b1)
                }
                else
                {
                    monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                }
            }
            if (opportunityProduct?.productInterestType?.name == "郊县" || opportunityProduct?.productInterestType?.name == "大头小尾" || opportunityProduct?.productInterestType?.name == "信用调整" || opportunityProduct?.productInterestType?.name == "二抵加收费率" || opportunityProduct?.productInterestType?.name == "老人房（65周岁以上）" || opportunityProduct?.productInterestType?.name == "老龄房（房龄35年以上）" || opportunityProduct?.productInterestType?.name == "非7成区域" || opportunityProduct?.productInterestType?.name == "大额（单套大于1200万）")
            //所有加息项按照合同类型加到月息或服务费上
            {
                if (opportunityProduct?.contractType)
                {
                    if (opportunityProduct?.contractType?.name == '借款合同')
                    {
                        if (opportunityProduct.installments)
                        {
                            monthlyInterest = monthlyInterest.add(b1)
                        }
                        else
                        {
                            monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                        }
                    }
                    else if (opportunityProduct?.contractType?.name == '委托借款代理服务合同')
                    {
                        if (opportunityProduct.installments)
                        {
                            serviceCharge = serviceCharge.add(b1)
                        }
                        else
                        {
                            serviceCharge = serviceCharge.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                        }
                    }
                }
                else
                {
                    if (opportunityProduct.installments)
                    {
                        monthlyInterest = monthlyInterest.add(b1)
                    }
                    else
                    {
                        monthlyInterest = monthlyInterest.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                    }
                }
            }
            if (opportunityProduct?.productInterestType?.name == "渠道返费费率")
            {
                if (opportunityProduct.installments)
                {
                    commissionRate = commissionRate.add(b1)
                    //渠道服务费
                }
                else
                {
                    commissionRate = commissionRate.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                }
            }
            if (opportunityProduct?.productInterestType?.name == "服务费费率")
            {
                //借款服务费
                if (opportunityProduct.installments)
                {
                    serviceCharge = serviceCharge.add(b1)
                }
                else
                {
                    serviceCharge = serviceCharge.add(b1.divide(actualLoanDuration, 10, BigDecimal.ROUND_HALF_UP))
                }
            }
        }
        opportunity.monthlyInterest = monthlyInterest.doubleValue()

        opportunity.ompositeMonthlyInterest = monthlyInterest.add(serviceCharge).doubleValue()
        opportunity.save()
    }
}