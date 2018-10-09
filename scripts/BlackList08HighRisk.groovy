/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    boolean bool = true
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    opportunityContacts?.each {
        def opportunityContact = it
        def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
        def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
        itemList?.each {
            if (it.name == '高危人员名单命中情况')
            {
                def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                detailItem?.each {
                    if (it?.name == '是否高危人群')
                    {
                        if (it?.value == '是')
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