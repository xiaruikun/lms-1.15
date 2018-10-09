package com.next

class Company
{
    Industry industry
    //行业
    String company
    //公司
    String companyCode
    //工商&机构代码

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static belongsTo = [contact: Contact]

    static constraints = {
        industry nullable: true, blank: true
        company maxSize: 128, nullable: true, blank: true
        companyCode maxSize: 128, nullable: true, blank: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
