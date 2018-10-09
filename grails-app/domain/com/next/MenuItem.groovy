package com.next

class MenuItem
{
    String name
    String linkUrl
    Integer displayOrder = 0
    String icon
    String type

    MenuItem parent
    // MenuItem.findAllByType('Folder')

    static belongsTo = [menu: Menu]

    static constraints = {
        name maxSize: 32, unique: ['menu']
        linkUrl maxSize: 256, nullable: true, blank: true
        icon nullable: true, blank: true
        type inList: ['Folder', 'Item']
        parent nullable: true, blank: true
    }
}
