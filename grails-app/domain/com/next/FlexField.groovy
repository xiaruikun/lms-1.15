package com.next

class FlexField implements Comparable
{
    String name
    String description
    String dataType
    String defaultValue
    String valueConstraints
    Boolean active = true
    Integer displayOrder = 0

    SortedSet values
    static belongsTo = [category: FlexFieldCategory]

    static hasMany = [values: FlexFieldValue]

    static constraints = {
        name maxSize: 64, unique: ['category']
        defaultValue maxSize: 64, nullable: true, blank: true
        valueConstraints maxSize: 1024, nullable: true, blank: true
    }
    static mapping = {
        cache true
        values(lazy: false)
    }

    @Override
    int compareTo(Object FlexField)
    {

        def flexField = (FlexField) FlexField
        if (displayOrder == flexField.displayOrder)
        {
            return 0
        }
        else if (displayOrder > flexField.displayOrder)
        {
            return 1
        }
        else
        {
            return -1
        }
    }
}
