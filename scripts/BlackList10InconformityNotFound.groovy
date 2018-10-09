/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    def num = 0
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    opportunityContacts?.each {
        def opportunityContact = it
        def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
        def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
        itemList?.each {
            if (it.name == '身份认证')
            {
                num += 1
            }
        }
    }
    if (num == opportunity?.contacts.size())
    {
        return true
    }
    else
    {
        return false
    }
}