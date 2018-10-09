package com.next

class ContractTemplateOptions implements Comparable
{
    String value
    Integer displayOrder = 0

    static belongsTo = [template: ContractTemplate]

    static constraints = {
        value maxSize: 64, unique: ['template']
    }

    static mapping = {
        cache true
    }

    @Override
    int compareTo(Object ContractTemplateOptions)
    {

        def contractTemplateOption = (ContractTemplateOptions) ContractTemplateOptions
        if (displayOrder == contractTemplateOption.displayOrder)
        {
            return 0
        }
        else if (displayOrder > contractTemplateOption.displayOrder)
        {
            return 1
        }
        else
        {
            return -1
        }
    }

}
