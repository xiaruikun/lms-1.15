package com.next

class ContractTemplate
{
    String name

    static belongsTo = [type: ContractType]
    static hasMany = [options: ContractTemplateOptions]
    SortedSet options
    static constraints = {
        name unique: ['type'], maxSize: 256
    }
    static mapping = {
        options(lazy: false)
    }
}
