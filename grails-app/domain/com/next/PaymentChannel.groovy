package com.next

class PaymentChannel
{
    String code
    // 渠道代码
    String name
    // 渠道名称
    String description
    // 描述
    Boolean active = true
    // 是否可用
    BigDecimal minLimit
    // 最低限额
    BigDecimal maxLimit
    // 最高限额
    String type
    // 支付渠道 允许的交易手段

    static constraints = {
        code size: 0..32, unique: true
        name size: 0..128, nullable: true, blank: true
        description size: 0..1024, nullable: true, blank: true
    }
}
