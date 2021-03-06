package com.next

class ContactBankAccount
{
    static belongsTo = [contact: Contact, bankAccount: BankAccount]

    Date createdDate = new Date()
    //记录创建时间
    Date modifiedDate = new Date()
    //记录修改时间    

    static constraints = {}

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
