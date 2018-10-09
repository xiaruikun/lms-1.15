package com.next

class City
{
    String name
    String telephone

    Boolean invitationCode = true
    String defaultInvitationCode = '18513603065'
    LoanApplicationProcessType loanApplicationProcessType

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static hasMany = [districts: District]

    static constraints = {
        name unqiue: true, size: 2..16
        telephone size: 10..13
        defaultInvitationCode maxSize: 16, nullable: true, blank: false
        loanApplicationProcessType nullable: true, blank: false
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }

    String toString()
    {
        name
    }

    def beforeInsert()
    {
        if (!loanApplicationProcessType)
        {
            loanApplicationProcessType = LoanApplicationProcessType.findByName('先评房再报单')
        }
    }
}
