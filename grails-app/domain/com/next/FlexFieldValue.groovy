package com.next

class FlexFieldValue implements Comparable
{
    String value
    Integer displayOrder = 0
    Boolean active = true

    static belongsTo = [field: FlexField]

    static constraints = {
        value maxSize: 64, unique: ['field']
    }

    @Override
    int compareTo(Object FlexFieldValue)
    {

        def flexFieldValue = (FlexFieldValue) FlexFieldValue
        if (displayOrder == flexFieldValue.displayOrder)
        {
            return 0
        }
        else if (displayOrder > flexFieldValue.displayOrder)
        {
            return 1
        }
        else
        {
            return -1
        }
    }
}
