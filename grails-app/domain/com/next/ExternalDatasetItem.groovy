package com.next

class ExternalDatasetItem
{
    String name
    String value

    Integer level = 0

    static belongsTo = [dataset: ExternalDataset, parent: ExternalDatasetItem]

    static hasMany = [items: ExternalDatasetItem]

    static constraints = {
        name maxSize: 256
        value maxSize: 4096, nullable: true, blank: true
        parent nullable: true
    }
}
