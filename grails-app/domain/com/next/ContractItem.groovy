package com.next

class ContractItem
{
    String name
    String value

    static belongsTo = [contract: Contract]
    static hasMany = [options: ContractItemOptions]
    SortedSet options
    static constraints = {
        name unique: ['contract'], maxSize: 256
        value blank: true, nullable: true, maxSize: 2048
    }
    static mapping = {
        options(lazy: false)
    }
}
