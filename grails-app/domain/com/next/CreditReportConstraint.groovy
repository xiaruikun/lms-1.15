package com.next

class CreditReportConstraint
{
    String name

    //    static hasMany = [items: CreditReportConstraintItem]

    static constraints = {
        name unique: true, maxSize: 128
    }
}
