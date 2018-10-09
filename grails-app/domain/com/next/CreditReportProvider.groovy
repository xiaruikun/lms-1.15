package com.next

class CreditReportProvider
{
    String code
    String name
    String apiUrl

    static constraints = {
        code maxSize: 16, unique: true
        name maxSize: 32, unqiue: true
        apiUrl maxSize: 256
    }
}
