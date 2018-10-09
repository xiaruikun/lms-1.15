package com.next

class OpportunityFlexFieldValue implements Comparable
{
    String value
    Integer displayOrder = 0

    static belongsTo = [field: OpportunityFlexField]

    static constraints = {
        value maxSize: 128, unique: ['field']
    }

    static mapping = {
        cache true
    }

    @Override
    int compareTo(Object OpportunityFlexFieldValue)
    {

        def opFlexFieldValue = (OpportunityFlexFieldValue) OpportunityFlexFieldValue
        if (displayOrder == opFlexFieldValue.displayOrder)
        {
            return 0
        }
        else if (displayOrder > opFlexFieldValue.displayOrder)
        {
            return 1
        }
        else
        {
            return -1
        }
    }
}
