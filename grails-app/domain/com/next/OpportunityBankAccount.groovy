package com.next

class OpportunityBankAccount
{
    OpportunityBankAccountType type

    Date createdDate = new Date()
    //记录创建时间
    Date modifiedDate = new Date()
    //记录修改时间    

    static belongsTo = [opportunity: Opportunity, bankAccount: BankAccount]

    static constraints = {
        createdDate nullable: true, blank: true
        modifiedDate nullable: true, blank: true
        type nullable: true, blank: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
