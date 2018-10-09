package com.next

class Document
{
    String title
    Boolean active = true
    //    Double version = 1.0
    String document

    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        title maxSize: 128

        document type: "text"
    }

    def beforeUpdate()
    {
        modifiedDate = new Date()
    }
}
