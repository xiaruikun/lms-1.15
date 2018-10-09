/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    boolean bool = true
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    opportunityContacts?.each {
        def opportunityContact = it
        def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
        def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
        itemList?.each {
            if (it?.name == '身份证查询照片')
            {
                def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                detailItem?.each {
                    if (it?.name == '返回信息')
                    {
                        if (!(it?.value == '一致'))
                        {
                            bool = false
                        }
                    }
                }
            }
        }
    }
    return bool
}