package com.next

class Menu
{
    String name
    static hasMany = [items: MenuItem]
    static constraints = {
        name maxSize: 32, unqiue: true
    }
}
