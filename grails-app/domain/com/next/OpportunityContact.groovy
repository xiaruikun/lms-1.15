package com.next

class OpportunityContact implements Comparable
{
    OpportunityContactType type

    static mapping = {
        cache true
    }

    static belongsTo = [contact: Contact, opportunity: Opportunity, connectedContact: Contact, connectedType: OpportunityContactType]

    static constraints = {
        // contact unique: ['opportunity', 'type']
        connectedContact nullable: true, blank: true
        connectedType nullable: true, blank: true
    }

    @Override
    int compareTo(Object opportunityContact)
    {

        def opContact = (OpportunityContact) opportunityContact
        if (type.id == opContact.type.id)
        {
            return 0
        }
        else if (type.id > opContact.type.id)
        {
            return 1
        }
        else
        {
            return -1
        }
    }
}
