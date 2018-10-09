package com.next

class ContractItemOptions implements Comparable
{
    String value
    Integer displayOrder = 0

    static belongsTo = [item: ContractItem]

    static constraints = {
        value maxSize: 64, unique: ['item']
    }

    static mapping = {
        cache true
    }

    @Override
    int compareTo(Object ContractItemOptions)
    {

        def contractItemOptions = (ContractItemOptions) ContractItemOptions
        if (displayOrder == contractItemOptions.displayOrder)
        {
            return 0
        }
        else if (displayOrder > contractItemOptions.displayOrder)
        {
            return 1
        }
        else
        {
            return -1
        }
    }

}
