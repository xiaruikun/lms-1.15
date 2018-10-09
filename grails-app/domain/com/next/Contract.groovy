package com.next

class Contract
{
    String serialNumber
    ContractType type
    String externalId
    Date createdDate = new Date()
    Date modifiedDate = new Date()

    static constraints = {
        serialNumber unique: true
        externalId maxSize: 32, blank: true, nullable: true
    }
}
