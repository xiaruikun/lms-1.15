/**
 * Created by 夏瑞坤 on 2017/06/27.
 * @ModifiedDate 2017-7-24
 * */
{ opportunity ->
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    boolean bool = true
    opportunityContacts?.each {
        def opportunityContact = it
        def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
        def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
        itemList?.each {
            if (it.name in ['通过身份证号查询银行不良', '通过身份证号查询银行欺诈', '通过手机号查询银行不良', '通过手机号查询银行欺诈', '通过联系人手机查询银行不良', '通过联系人手机查询银行欺诈', '通过百融标识查询银行不良', '通过百融标识查询银行欺诈'])
            {
                if (it?.value == '0')
                {
                    bool = false
                }
            }
        }
    }
    return bool
}