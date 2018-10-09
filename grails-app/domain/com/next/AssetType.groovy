package com.next

class AssetType
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }
}
