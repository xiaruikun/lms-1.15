package com.next

class ContactJudgementRecord
{

    Date filingTime
    //立案时间
    Integer executionObject
    //执行标的
    String executionStatus
    //执行状态

    static belongsTo = [contact: Contact]

    static constraints = {
        filingTime blank: true, nullable: true
        executionObject blank: true, nullable: true
        executionStatus blank: true, nullable: true, maxSize: 8
        executionStatus inList: ["已结案", "未结案"]
    }
}