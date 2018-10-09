/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    boolean bool = true
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    opportunityContacts?.each {
        def opportunityContact = it
        def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '汇法' order by dataset.createdDate desc")
        def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
        itemList?.each {
            if (it.name == '欠款欠费名单')
            {
                bool = false
            }
        }
    }
    return bool
}