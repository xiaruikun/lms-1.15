package com.next

class InterestOverdue
{
	//城市
    String cityName

    //合同编号
    String contractNumber

	//姓名
	String name

	//放款金额
	String loanAmount

	//欠缴金额
	String arrearsAmount

	//放款日期
	Date loanDate

	//应还本日期
	Date shouldPrincipleDate

	//逾期开始日期
	Date overdueStartDate

	//统计日期
	Date calculatedDate

	static constraints = {
        arrearsAmount nullable: true, blank: true
    }
}