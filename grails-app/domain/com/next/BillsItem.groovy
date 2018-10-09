package com.next

class BillsItem
{
    Date startTime
    //还款周期开始日
    Date endTime
    //还款周期结束日
    Date payTime
    //还款日
    Integer period
    //期数
    String status
    //还款状态

    Double balance = 0
    //余额 ~ 期末
    Double receivable = 0
    //应付金额
    Double receipts = 0
    //实收

    BankAccount debit
    //借方
    BankAccount credit
    //贷方

    BillsItemType type

    Date actualStartTime
    Date actualEndTime
    Date createdTime = new Date()

    TransactionRecord transactionRecord

    User createdBy
    User modifiedBy

    Boolean split = false
    //拆分

    Boolean prepayment = false
    //早偿

    Boolean overdue = false
    //逾期

    static belongsTo = [bills: Bills]

    static constraints = {
        startTime nullable: false
        endTime nullable: false
        period nullable: false
        status inList: ['待收', '已收', '扣款失败']
        status maxSize: 10
        balance scale: 9
        receivable scale: 9
        receipts scale: 9
        actualStartTime nullable: true, blank: true
        actualEndTime nullable: true, blank: true
        createdTime nullable: true, blank: true
        transactionRecord nullable: true, blank: true
        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true
        split nullable: true, blank: true
        prepayment nullable: true, blank: true
        overdue nullable: true, blank: true
        payTime nullable: true,blank:true
    }
}
