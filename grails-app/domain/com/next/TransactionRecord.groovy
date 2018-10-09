package com.next

//debit 借方资产增加 credit 贷方资产减产

class TransactionRecord
{
    BankAccount debit
    //收款帐户：信托／电子支付
    // String debit
    BankAccount credit
    //借款人账户

    Double amount = 0

    TransactionType type

    String debitAccount
    //清分账户

    Date plannedStartTime
    Date plannedEndTime

    Date startTime
    Date endTime

    Date createdDate = new Date()
    // 请求日期
    Date modifiedDate = new Date()
    // 修改日期

    TransactionRecordStatus status

    RepaymentMethod repaymentMethod
    //广银联代扣 富友代扣 自主还款

    String memo

    static belongsTo = [createdBy: User, modifiedBy: User, opportunity: Opportunity, contact: Contact]

    static constraints = {
        amount nullable: true, blank: true
        type nullable: true, blank: true
        debitAccount maxSize: 64, nullable: true, blank: true
        plannedStartTime nullable: true, blank: true
        plannedEndTime nullable: true, blank: true
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        createdDate nullable: true, blank: true
        modifiedDate nullable: true, blank: true
        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true
        contact nullable: true, blank: true
        memo maxSize: 128, nullable: true, blank: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
