package com.next

class OpportunityFlexField implements Comparable
{
    String name
    String description
    String dataType
    String defaultValue
    String valueConstraints
    String value
    Integer displayOrder = 0
    SortedSet values
    static belongsTo = [category: OpportunityFlexFieldCategory]

    static hasMany = [values: OpportunityFlexFieldValue]

    static constraints = {
        defaultValue maxSize: 64, nullable: true, blank: true
        valueConstraints maxSize: 1024, nullable: true, blank: true
        // value nullable: true, blank: true, maxSize: 128
        // value nullable: true, blank: true, maxSize: 1024
        value nullable: true, blank: true, type: "text"
    }

    static mapping = {
        cache true
        values(lazy: false)
    }

    @Override
    int compareTo(Object OpportunityFlexField)
    {

        def opFlexField = (OpportunityFlexField) OpportunityFlexField
        if (displayOrder == opFlexField.displayOrder)
        {
            return 0
        }
        else if (displayOrder > opFlexField.displayOrder)
        {
            return 1
        }
        else
        {
            return -1
        }
    }
}
