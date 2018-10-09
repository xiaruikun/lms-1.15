package com.next

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import groovy.sql.Sql

import java.text.SimpleDateFormat

class JieqingHFHController {
    JieqingHFHService jieqingHFHService
    def dataSource
    @Secured(['permitAll'])
    /**
     * @Description
     * contract_no           合同编号
      borrower              借款人
      period                期数
      re_payment            已还总金额
      pay_status            还款状态(0 失败，1 成功)
      pay_date              还款时间
      remark                描述信息
     * @Author bigyuan
     * @Return
     * @DateTime 2017/9/19 15:04
     */
    def receivePhoenixData() {
        def responseJson = request.JSON
        println "火凤凰发送的报文： "+responseJson
        responseJson.each{
            print "1"
            def loanContranct = it["contract_no"] //合同编号
            def fullName = it["borrower"] //借款人姓名
            def period = it["period"] //期数
            def amount= it["re_payment"] //已还总金额
            def status= it["pay_status"] //还款状态(0 失败，1 成功)
            def remark = it["remark"] //描述信息
            //切割item的id
            String ids = it["item_ids"]
            def idList = ids?.substring(0,ids.lastIndexOf(","))?.split(",")
            //还款时间
            def payDate
            if(it["pay_date"]){
                 payDate= new SimpleDateFormat("yyyy-MM-dd").parse(it["pay_date"])
            }


            //根据合同号查询opportunity
            def ContractId = Contract.executeQuery("select id from Contract where serialNumber = '${loanContranct}' and type.name = '借款合同'")[0]
            def opportunityId = OpportunityContract.executeQuery("select opportunity.id from OpportunityContract where contract.id = ${ContractId} ")[0]
            def billsLog
            //更新或者新建
            billsLog = BillsLog.findByOpportunityId(opportunityId)
            if(!billsLog){
                billsLog= new BillsLog()
            }
            billsLog.period = period
            billsLog.fullName = fullName
            billsLog.json = it.toString()
            billsLog.opportunityId =opportunityId

            if(billsLog.validate()){
                billsLog.save flush:true
            } else {
                println billsLog.errors
            }

//            if(opportunity.fullName!=fullName){
//                render "借款人姓名合同号不相符"
//            }

            idList.each {
                def db = new Sql(dataSource)
                if( status=="1"){
                    db.executeUpdate("UPDATE  bills_item SET  status = '已收' ,receipts = receivable ,actual_end_time = ? WHERE  id = ?",[payDate,it])
                } else if ( status=="0"){
                    db.executeUpdate("UPDATE  bills_item SET  status = '扣款失败'  WHERE  id = ?",[it])
                }

            }

        }
        println "火凤凰回盘成功"
        render "success"


    }
    /*def receivePhoenixData() {
        def responseJson = request.JSON
        println "responseJson+++++"+responseJson
        responseJson.each{
            def loanContranct = it["contract_no"] //合同编号
            def fullName = it["borrower"] //借款人姓名
            def period = it["period"] //期数
            def amount= it["re_payment"] //已还总金额
            def status= it["pay_status"] //还款状态(0 失败，1 成功)
            def payDate
            if(it["pay_date"]){
                payDate= new SimpleDateFormat("yyyy-MM-dd").parse(it["pay_date"])
            }
            //还款时间
            def remark = it["remark"] //描述信息
            def con = Contract.findBySerialNumberAndType(loanContranct,ContractType.findByName("借款合同"))
            def opportunity = OpportunityContract.findByContract(con)?.opportunity
            def billsLog
            if(BillsLog.findByOpportunityId(opportunity.id)){
                billsLog= BillsLog.findByOpportunityId(opportunity.id)
            } else {
                billsLog= new BillsLog()
            }

            billsLog.period = period
            billsLog.fullName = fullName
            billsLog.json = it.toString()




            billsLog.opportunityId =opportunity.id

            if(billsLog.validate()){
                billsLog.save flush:true
            } else {
                println billsLog.errors
            }

            if(opportunity.fullName!=fullName){
                return "借款人姓名合同号不相符"
            }
            def item = BillsItem.findAllByBillsAndPeriod(opportunity.bills[0],period)
            def periodPrincipal = BillsItem.findByBills(opportunity.bills[0],[sort:"period",order:"desc"])?.period
            def interestPaymentMethod = opportunity?.interestPaymentMethod?.name
            if( status=="1"){
                item.each {
                    //todo
                    if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                        it.status = "已收"
                        it.receipts = it.receivable
                        it.actualEndTime = payDate
                        it.save()
                    }else {
                        if (amount>100000){
                            if (it?.type?.name == "本金"){
                                it.status = "已收"
                                it.receipts = it.receivable
                                it.actualEndTime = payDate
                                it.save()
                            }
                        }else {
                            it.status = "已收"
                            it.receipts = it.receivable
                            it.actualEndTime = payDate
                            it.save()
                        }
                    }
                }
            } else if ( status=="0"){
                item.each {
                    //todo
                    if (interestPaymentMethod =="下扣息"||interestPaymentMethod =="等额收息"){
                        it.status = "扣款失败"
                        it.save()
                    }else {
                        if (it?.period == periodPrincipal){
                            if (it?.type?.name == "本金"){
                                it.status = "扣款失败"
                                it.save()
                            }
                        }else {
                            it.status = "扣款失败"
                            it.save()
                        }
                    }
                }
            }
        }

        render "success"


    }*/

    @Secured(['permitAll'])
    def sendDataTohuo(){
        def result = jieqingHFHService.sendDataTohuo()
        render result
    }

}
