/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    boolean bool = true
    boolean adjust = false
    def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
    opportunityContacts?.each {
        def opportunityContact = it
        def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
        def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
        itemList?.each {
            if (it.name == '个人不良信息')
            {
                def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                detailItem?.each {
                    if (it.name == '案件来源' && it.value == '前科')
                    {
                        adjust = true
                    }
                    if (adjust && it.name == '案件类别' && it.value in ['其他毒品刑事案件', '容留他人吸毒案', '抢劫其他汽车案', '抢劫摩托车案', '抢劫案', '拦路抢劫案', '入户抢劫案', '敲诈勒索案', '当前在逃', '诈骗案', '招摇撞骗案', '赌博案', '抢夺案', '组织卖淫案', '赌博案聚众斗殴案', '故意杀人案', '贩卖毒品案', '金融诈骗案', '合同诈骗案', '非法持有毒品案', '持枪强奸案', '强迫卖淫案', '故意伤害案', '绑架案', '赌博案', '运输毒品案', '非法买卖枪支弹药案', '集资诈骗案', '拒不执行判决', '非法吸收公众存款案', '制造毒品案', '强奸案', '抢劫出租汽车案', '拐卖妇女儿童案'])
                    {
                        bool = false
                    }
                }
            }
        }
    }
    return bool
}