package com.next

class Component
{
    String name
    Boolean active = true

    ComponentType type

    String script
    String message

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    User createBy
    User modifyBy

    static constraints = {
        name maxSize: 64, unqiue: true
        script type: "text"
        message maxSize: 256, blank: true, nullable: true
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
