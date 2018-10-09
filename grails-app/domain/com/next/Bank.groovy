package com.next

class Bank
{
    //String codeOfPay
    //String codeOfCheck     // 银行代码
    String code
    // 银行代码
    String name
    // 银行名称
    String description
    // 描述
    String type
    // 类型： 用于快捷支付和基金支付
    Boolean active = false
    // 有效性
    double singleLimit
    //单笔限额
    double dailyLimit
    //日累计限额
    double monthlyLimit
    //月累计限额

    static constraints = {
        code size: 4..10
        //codeOfPay size: 4..4
        //codeOfCheck size: 10..10
        name size: 0..128, nullable: true, blank: true
        description size: 0..1024, nullable: true, blank: true
        //type inList: ['验卡', '支付'] // modify by zhangtt - 20160105 原因是fuiou那块基金支付和快捷支付的限额不一样
        type inList: ['Quick Pay']
        singleLimit nullable: true
        dailyLimit nullable: true
        monthlyLimit nullable: true
    }
}
