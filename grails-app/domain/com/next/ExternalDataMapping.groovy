package com.next

class ExternalDataMapping
{
    String systemName
    String categoryName
    String value1
    String value2

    static constraints = {
        systemName maxSize: 32
        categoryName maxSize: 32
        value1 maxSize: 128, unique: ['systemName', 'categoryName']
        value2 maxSize: 128
    }
}
