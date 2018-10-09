package com.next

class LoanApplicationProcessType
{
    String name
    static constraints = {
        name maxSize: 32, unique: true
    }
}
