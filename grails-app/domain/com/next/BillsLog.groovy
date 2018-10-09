package com.next

class BillsLog {
    String opportunityId
    String fullName
    String period
    String json
    Date createdDate = new Date()
    static constraints = {
        opportunityId maxSize: 64, blank: true, nullable: true
        fullName maxSize: 32, blank: true, nullable: true //, unique: true
        period maxSize: 16, blank: true, nullable: true //, unique: true
        json maxSize: 1024, blank: true, nullable: true //, unique: true

    }
}
