package com.next

class AssetPool
{
    String name

    static constraints = {
        name maxSize: 32, unique: true
    }

    static mapping = {
        sort 'name'
    }
}
