package com.next

class Bills
{
    BillsStatus status
    //状态

    Date startTime
    Date endTime

    User createdBy
    User modifiedBy

    Account account
    //信托

    BankAccount bankAccount
    //放款账号

    Double capital = 0
    //当前本金

    static belongsTo = [opportunity: Opportunity]

    static hasMany = [items: BillsItem]

    static constraints = {
        createdBy nullable: true, blank: true
        modifiedBy nullable: true, blank: true
        startTime nullable: true, blank: true
        endTime nullable: true, blank: true
        bankAccount nullable: true, blank: true
        account nullable: true, blank: true
        capital nullable: true, blank: true
    }
}
