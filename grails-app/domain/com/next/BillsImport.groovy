package com.next

class BillsImport
{

    //String bankCode
    //银行代码
    String numberOfAccount
    //银行卡号
    String name
    //银行账号名
    Double actualAmountOfCredit
    //金额分为单位
    //String certificateCode
    //开户证件类型
    String numberOfCertificate
    //证件号
    String serialNumber
    // 订单合同编号
    String resultCode
    //结果反馈码
    String resultReason
    //结果原因
    String failReason
    //处理失败原因
    String opportunityNumber
    // 订单编号
    Date commitTime
    //提交日期
    String debitAccount
    //清分账号

    /*Double totalAmount
    //总金额分为单位
    Integer count
    //总记录数
    String flag
    //代收代付标志
    String companyId
    //商户标志
    String serverCode='04900'
    //业务编号
    */

    String status

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        //bankCode inList: ['102', '105', '308', '103', '303', '306', '309', '310']
        //certificateCode inList: ['0', '2', '7']
        numberOfAccount maxSize: 20
        name maxSize: 32
        serialNumber maxSize: 64, nullable: true
        resultCode maxSize: 32
        resultReason maxSize: 64
        failReason maxSize: 64
        opportunityNumber maxSize: 64, nullable: true
        commitTime nullable: true
        debitAccount maxSize: 64, nullable: true
        numberOfCertificate maxSize: 20, nullable: true
        createdDate nullable: true, blank: true
        modifiedDate nullable: true, blank: true

        status inList: ['成功', '失败', '待处理']
        status maxSize: 12
        /*flag inList: ['S', 'F']
        companyId nullable: true
        serverCode nullable: true*/

    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
