package com.next

class BankAccount
{
    String numberOfAccount
    // 卡号
    String name
    //账号名
    String cellphone
    // 银行预留手机号
    ContactIdentityType certificateType
    //证件类型
    String numberOfCertificate
    //证件号
    Boolean active = true
    // 是否验卡成功
    String bankBranch
    // 支行
    String bankAddress
    // 运行地址
    String externalId
    Date createdDate = new Date()
    //记录创建时间
    Date modifiedDate = new Date()
    //记录修改时间

    static belongsTo = [bank: Bank, paymentChannel: PaymentChannel, createdBy: User, modifiedBy: User]

    static constraints = {
        cellphone matches: /\d{11}/, nullable: true
        // certificateType nullable: 32, blank: true
        certificateType nullable: true, blank: true
        numberOfCertificate maxSize: 20, nullable: true
        numberOfAccount maxSize: 21
        name maxSize: 32
        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true
        paymentChannel nullable: true, blank: true
        active nullable: true, blank: true
        bankBranch nullable: true, blank: true
        bankAddress nullable: true, blank: true
        externalId maxSize: 32, blank: true, nullable: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
