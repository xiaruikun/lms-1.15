package com.next

/**
 * 渠道信息记录表*/
class ChannelRecord
{
    String interfaceCode
    //接口编码

    String status
    //状态

    String applySeq
    // 中航审批流水号
    String applyCode
    // 中航申请编号

    String loanApplyNo
    //贷款申请编号
    String crtDt
    //处理时间

    User createdBy
    User modifiedBy

    Date startTime = new Date()
    Date endTime = new Date()

    String loanNo
    //借据号

    static belongsTo = [opportunity: Opportunity]

    static constraints = {
        interfaceCode size: 0..16, blank: false, nullable: false
        applySeq size: 0..30, blank: true, nullable: true
        applyCode size: 0..30, blank: true, nullable: true

        //status inList: ['交易成功', '交易失败']
        status maxSize: 20, blank: true, nullable: true

        loanNo size: 0..32, blank: true, nullable: true

        loanApplyNo maxSize: 32, blank: true, nullable: true
        crtDt maxSize: 20, blank: true, nullable: true

        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true
        startTime nullable: false, blank: false
        endTime nullable: false, blank: false
    }
}

