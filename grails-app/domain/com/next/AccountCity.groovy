package com.next

class AccountCity
{
    static belongsTo = [account: Account, city: City]

    static constraints = {}
}
