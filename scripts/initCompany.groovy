/**初始化公司信息
 * Created by 夏瑞坤 on 2017/08/04.*/
{ opportunity ->
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    for (
        def opportunityContact in
            opportunityContacts)
    {
        if (!opportunityContact?.contact?.companies)
        {
            def contactList = com.next.Contact.findAllByCellphoneAndFullNameAndIdNumber(opportunityContact?.contact?.cellphone, opportunityContact?.contact?.fullName, opportunityContact?.contact?.idNumber)
            for (
                def contact in
                    contactList)
            {
                if (contact?.companies)
                {
                    contact?.companies.each {
                        if (!com.next.Company.findByCompanyAndContact(it?.company, opportunityContact?.contact))
                        {
                            com.next.Company company = new com.next.Company()
                            company.company = it?.company
                            company.contact = opportunityContact?.contact
                            if (company.validate())
                            {
                                company.save flush: true
                            }
                            else
                            {
                                println company.errors
                            }
                        }
                    }
                }
            }
        }
    }
}