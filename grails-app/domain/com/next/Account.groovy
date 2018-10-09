package com.next

class Account
{
    String code
    String name

    AccountType type

    Boolean active = true

    String externalId

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [parent: Account]

    static constraints = {
        code maxSize: 128 //, unique: true
        code blank: true, nullable: true
        name maxSize: 64, unique: true

        parent blank: true, nullable: true

        externalId maxSize: 32, blank: true, nullable: true //, unique: true
    }

    def beforeInsert()
    {
        if (!code)
        {
            code = UUID.randomUUID().toString()
        }
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }

    String toString()
    {
        name
    }
}
