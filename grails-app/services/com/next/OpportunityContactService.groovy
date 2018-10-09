package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@Transactional
@CompileStatic
@TypeChecked
class OpportunityContactService
{
    static scope = "singleton"

    def getContact(Opportunity o, String typeName)
    {
        OpportunityContact opportunityContact = OpportunityContact.find("from OpportunityContact as o where o" + ".opportunity.id = ${o.id} and o.type.name = " + "'${typeName}'")
        if (opportunityContact)
        {
            return opportunityContact.contact
        }
        else
        {
            return null
        }
    }

}
