package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@Transactional
@CompileStatic
@TypeChecked
class OpportunityFlexFieldCategoryService
{

    Boolean initFlexFieldCategory(Opportunity opportunity, OpportunityWorkflow workflow = null)
    {
        println "initFlexFieldCategory"
        //        Boolean inheritTeam = true
        OpportunityFlexFieldCategory opportunityFlexFieldCategory
        OpportunityFlexField opportunityFlexField
        OpportunityFlexFieldValue opportunityFlexFieldValue
        def territoryFlexFieldCategories
        SortedSet<FlexField> fields
        SortedSet<FlexFieldValue> values
        def territoryAccount
        def territory

        //区域
        if (workflow)
        {
            territory = TerritoryOpportunityWorkflow.find("from TerritoryOpportunityWorkflow where workflow.id = ?", [workflow?.id])?.territory
        }
        else
        {
            territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
            territory = territoryAccount?.territory
        }

        //复制territoryFlexFieldCategory到opportunityFlexFieldCategory
        while (territory)
        {
            territoryFlexFieldCategories = TerritoryFlexFieldCategory.findAll("from TerritoryFlexFieldCategory where territory.id = ? and opportunityType.id = ?", [territory?.id, opportunity?.type?.id])
            if (territoryFlexFieldCategories)
            {
                for (
                    flexFieldCategories in
                        territoryFlexFieldCategories)
                {
                    opportunityFlexFieldCategory = OpportunityFlexFieldCategory.find("from OpportunityFlexFieldCategory where opportunity.id = ? and flexFieldCategory" + ".id=?", [opportunity?.id, flexFieldCategories?.flexFieldCategory?.id])
                    if (!opportunityFlexFieldCategory)
                    {
                        opportunityFlexFieldCategory = new OpportunityFlexFieldCategory()
                        opportunityFlexFieldCategory.flexFieldCategory = flexFieldCategories?.flexFieldCategory
                        opportunityFlexFieldCategory.opportunity = opportunity
                        if (opportunityFlexFieldCategory.validate())
                        {
                            opportunityFlexFieldCategory.save flush: true
                        }
                        else
                        {
                            println opportunityFlexFieldCategory.errors
                        }
                        fields = flexFieldCategories?.flexFieldCategory?.fields
                        if (fields)
                        {
                            for (
                                FlexField field in
                                    fields)
                            {
                                opportunityFlexField = new OpportunityFlexField()
                                opportunityFlexField.category = opportunityFlexFieldCategory
                                opportunityFlexField.dataType = field?.dataType
                                opportunityFlexField.defaultValue = field?.defaultValue
                                opportunityFlexField.description = field?.description
                                opportunityFlexField.name = field?.name
                                opportunityFlexField.displayOrder = field?.displayOrder
                                opportunityFlexField.valueConstraints = field?.valueConstraints
                                if (opportunityFlexField.validate())
                                {
                                    opportunityFlexField.save flush: true
                                }
                                else
                                {
                                    println opportunityFlexField.errors
                                }
                                values = field?.values
                                if (values)
                                {
                                    for (
                                        FlexFieldValue value in
                                            values)
                                    {
                                        opportunityFlexFieldValue = new OpportunityFlexFieldValue()
                                        opportunityFlexFieldValue.field = opportunityFlexField
                                        opportunityFlexFieldValue.value = value?.value
                                        opportunityFlexFieldValue.displayOrder = value?.displayOrder
                                        if (opportunityFlexFieldValue.validate())
                                        {
                                            opportunityFlexFieldValue.save flush: true
                                        }
                                        else
                                        {
                                            println opportunityFlexFieldValue.errors
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
            territory = territory?.parent
        }
    }
}
