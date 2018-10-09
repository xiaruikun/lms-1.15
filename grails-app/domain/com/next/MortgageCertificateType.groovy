package com.next

class MortgageCertificateType
{
    String name

    static constraints = {
        name maxSize: 128, unique: true
    }

    String toString()
    {
        name
    }
}
