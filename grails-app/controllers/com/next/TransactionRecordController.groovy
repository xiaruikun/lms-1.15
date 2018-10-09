package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_EVENT_CONFIGURATION', 'ROLE_COMPLIANCE_MANAGER', 'ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_POST_LOAN_MANAGER', 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])


class TransactionRecordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService
    def billsService
    def opportunityStatisticsService
    def increment = 1

    @Transactional(readOnly = true)
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TransactionRecord.listOrderByCreatedDate(params), model: [transactionRecordCount: TransactionRecord.count()]
    }

    @Transactional(readOnly = true)
    def search(Integer max) {

        params.max = 10
        params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startDate
        def endTime = params.endDate
        def report = params.report
        def type = params.type
        def fullName = params.fullName
        def status = params.status
        def serialNumber = params.serialNumber
        def list
        def count
        if (report == "yes1" || report == "yes2" || report == "yes3") {

            def sql = "from TransactionRecord where 1=1 "
            if (startTime) {
                sql += "and plannedStartTime >= '${startTime}'"
            }
            if (endTime) {
                sql += "and plannedStartTime <= '${endTime}'"
            }
            if (type && type != "null") {
                sql += "and repaymentMethod.name = '${type}'"
            }
            if (status && status != "null") {
                sql += "and status.name = '${status}'"
            }
            if (fullName) {
                sql += "and opportunity.fullName = '${fullName}'"
            }
            if (serialNumber) {
                sql += "and opportunity.serialNumber = '${serialNumber}'"
            }
            sql += "and id in (select transactionRecord.id from BillsItem where period = 0  )"
            list = TransactionRecord.findAll(sql + "order by plannedStartTime , repaymentMethod.id")
            println list.size()

            def StrConnect =
                    { num ->
                        String str = "";
                        String sNum = "" + num;
                        for (
                                int i = 0;
                                i < (5 - sNum.length());
                                i++) {
                            str = str + "0";
                        }
                        str = str + num;
                        return str;
                    }
            def list1 = []
            def list2 = []
            def list3 = []
            def a = ((char) (9)).toString()
            list.each {
                def dataList = []
                dataList.add("")
                dataList.add("")
                def code
                def opportunityBankAccount = OpportunityBankAccount.findByOpportunityAndType(it.opportunity, OpportunityBankAccountType.findByName("还款账号"))
                if (opportunityBankAccount?.bankAccount?.bank?.name == "工商银行") {
                    code = "102"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "建设银行") {
                    code = "105"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "招商银行") {
                    code = "308"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "农业银行") {
                    code = "203"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "光大银行") {
                    code = "303"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "广发银行") {
                    code = "306"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "兴业银行") {
                    code = "309"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "上海浦东发展银行") {
                    code = "310"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "民生银行") {
                    code = "305"
                } else if (opportunityBankAccount?.bankAccount?.bank?.name == "交通银行") {
                    code = "301"
                }

                dataList.add(code)
                dataList.add("")
                dataList.add(a + opportunityBankAccount?.bankAccount?.numberOfAccount?.toString())
                dataList.add(opportunityBankAccount?.bankAccount?.name)
                dataList.add("")
                dataList.add("")
                dataList.add("")
                dataList.add("")
                dataList.add(new java.math.BigDecimal(Double.toString(it.amount)).setScale(6, java.math.BigDecimal.ROUND_HALF_UP).doubleValue() * 1000000)
                dataList.add("")
                dataList.add("")
                dataList.add("")
                dataList.add("0")
                dataList.add(a + opportunityBankAccount.bankAccount.numberOfCertificate.toString())
                dataList.add("")
                dataList.add("")
                def qingfenNo = ""
                def accountName = OpportunityFlexField.findByCategoryAndName(OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.opportunity, FlexFieldCategory.findByName("资金渠道")), "放款账号")?.value
                println accountName
                if (accountName == "中航848") {
                    qingfenNo = "TS02"
                } else if (accountName == "中航324") {
                    qingfenNo = "XTTS0324"
                } else if (accountName == "中航813") {
                    qingfenNo = "TS0813"
                } else if (accountName == "中航146") {
                    qingfenNo = "TS01"
                } else if (accountName == "中航628") {
                    qingfenNo = "TS628"
                } else if (accountName == "中航408") {
                    qingfenNo = "TS408"
                } else if (accountName == "中航506") {
                    qingfenNo = "TS506"
                } else if (accountName == "中航142") {
                    qingfenNo = "TS142"
                } else if (accountName == "中航907") {
                    qingfenNo = "TS907"
                } else if (accountName == "中航662") {
                    qingfenNo = "TS662"
                } else if (accountName == "中航463") {
                    qingfenNo = "TS463"
                } else if (accountName == "中航817") {
                    qingfenNo = "TS817"
                } else if (accountName == "中航889") {
                    qingfenNo = "TS889"
                } else if (accountName == "中航809") {
                    qingfenNo = "TS809"
                } else if (accountName == "中航568") {
                    qingfenNo = "TS568"
                }


                dataList.add(qingfenNo) ////
                dataList.add("")
                def OpportunityContract = OpportunityContract.find("from OpportunityContract where opportunity.id = ${it.opportunity.id}  and contract.type.name = '借款合同'")
                if (OpportunityContract) {
                    dataList.add(OpportunityContract?.contract?.serialNumber)
                } else {
                    dataList.add(it.opportunity.externalId)
                }

                dataList.add("")
                dataList.add("")
                dataList.add("")
                def merchantNo
                def lendingChannel

                def opportunityFlexFieldCategory = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.opportunity, FlexFieldCategory.findByName("资金渠道"))
                if (opportunityFlexFieldCategory) {
                    opportunityFlexFieldCategory?.fields?.each {
                        if (it.name == '放款通道') {
                            if (it.value == '中航信托') {
                                lendingChannel = "中航信托"
                                merchantNo = "000191400205805"
                                dataList[0] = a + StrConnect(list1.size() + 1)
                                list1.add(dataList)
                                //中航信托
                            }
                        } else {
                            if (it.name == '放款账号') {
                                if (it.value == '渤海264') {
                                    lendingChannel = "渤海一期"
                                    merchantNo = "000191400206218"
                                    dataList[0] = a + StrConnect(list2.size() + 1)
                                    list2.add(dataList)
                                    //渤海一期
                                }
                                if (it.value == '渤海295') {
                                    lendingChannel = "渤海二期"
                                    merchantNo = "000191400206347"
                                    dataList[0] = a + StrConnect(list3.size() + 1)
                                    list3.add(dataList)
                                    //渤海而期
                                }
                            }
                        }

                    }
                }

            }




            if (list1.size() > 0 && report == "yes1") {
                response.setContentType("application/csv;charset=GBK")
                def fileName = "000191400205805_S02" + new Date().format("yyyyMMdd") + "_" + StrConnect(increment)
                increment++
                if (increment == 100000) {
                    increment = 1
                }
                response.setHeader("Content-Disposition", "attachment;FileName=${fileName}.csv")
                OutputStream out = null
                try {
                    out = response.getOutputStream()
                }
                catch (Exception e) {
                    e.printStackTrace()
                }
                def amout = 0
                list1.each {
                    amout += it[10]
                }
                def listTitle = new ArrayList<Map>(Arrays.asList("S", a + '000191400205805', new Date().format("yyyyMMdd"), list1.size(), amout, "14901"))
                opportunityStatisticsService.exportExcel(out, listTitle, list1)
            }
            if (list2.size() > 0 && report == "yes2") {
                response.setContentType("application/csv;charset=GBK")
                def fileName = "000191400206218_S02" + new Date().format("yyyyMMdd") + "_" + StrConnect(increment)
                increment++
                if (increment == 100000) {
                    increment = 1
                }
                response.setHeader("Content-Disposition", "attachment;FileName=${fileName}.csv")
                OutputStream out = null
                try {
                    out = response.getOutputStream()
                }
                catch (Exception e) {
                    e.printStackTrace()
                }
                def amout = 0
                list2.each {
                    amout += it[11]
                }
                def listTitle = new ArrayList<Map>(Arrays.asList("S", a + "000191400206218", new Date().format("yyyyMMdd"), list2.size(), amout, "14901"))
                opportunityStatisticsService.exportExcel(out, listTitle, list2)
            }
            if (list3.size() > 0 && report == "yes3") {
                response.setContentType("application/csv;charset=GBK")
                def fileName = "000191400206347_S02" + new Date().format("yyyyMMdd") + "_" + StrConnect(increment)
                increment++
                if (increment == 100000) {
                    increment = 1
                }
                response.setHeader("Content-Disposition", "attachment;FileName=${fileName}.csv")
                OutputStream out = null
                try {
                    out = response.getOutputStream()
                }
                catch (Exception e) {
                    e.printStackTrace()
                }
                def amout = 0
                list3.each {
                    amout += it[11]
                }
                def listTitle = new ArrayList<Map>(Arrays.asList("S", a + "000191400206347", new Date().format("yyyyMMdd"), list3.size(), amout, "14901"))
                opportunityStatisticsService.exportExcel(out, listTitle, list3)
            }


            return null
        }
        else if (report == "yes4") {
            list = []
            def sql = " from TransactionRecord where 1=1  "
            if (startTime && endTime) {
                sql += "and endTime between '${startTime}' and '${endTime}'"
            }
            def transactionRecordList = TransactionRecord.findAll(sql)
            println transactionRecordList
            transactionRecordList.each {
                def dataList = []
                def hetonghao = ""
                OpportunityContract.findByOpportunity(it.opportunity)?.each {
                    if (it.contract.type.name == "借款合同") {
                        hetonghao = it.contract.serialNumber
                    }
                }
                dataList.add(hetonghao)
                def actualLendingDate = it.opportunity.actualLendingDate
                dataList.add(actualLendingDate.format("yyyy-MM-dd"))
                def name = it.credit.name
                dataList.add(name)
                def actualLoanAmount = it.opportunity.actualLoanAmount == 0 ? it.opportunity.actualAmountOfCredit : it.opportunity.actualLoanAmount
                dataList.add(actualLoanAmount)
                def date1 = it.endTime
                dataList.add(date1.format("yyyy-MM-dd"))
                def seriNum = it.opportunity.serialNumber
                dataList.add(seriNum)
                def amount = it.amount
                dataList.add(amount)
                def billsItems = BillsItem.findAllByTransactionRecord(it)
                def benjin = 0
                def jibenfeilv = 0
                def five = 0
                def jiekuanhetong = 0
                def fuwuhetong = 0
                def elsess = 0
                billsItems.each {
                    if (it.type.name == "本金") {
                        benjin = it.receivable
                    } else if (it.type.name == "基本费率") {
                        jibenfeilv = it.receivable
                    } else if (it.type.name == "服务费费率") {
                        five += it.receivable
                    } else if (it.type.name == "渠道返费费率") {
                        five += it.receivable
                    } else if (it.type.name == "利息违约金") {
                        five += it.receivable
                    } else if (it.type.name == "本金违约金") {
                        five += it.receivable
                    } else if (it.type.name == "逾期本金利息") {
                        five += it.receivable
                    } else if (it.type.name == "早偿违约金") {
                        five += it.receivable
                    } else {
                        def productInterestType = OpportunityProduct.find("from OpportunityProduct where opportunity.id = ${it.bills.opportunity.id}  and productInterestType.name = '${it.type.name}'")
                        if (productInterestType?.contractType?.name == "借款合同") {
                            jiekuanhetong += it.receivable
                        } else if (productInterestType?.contractType?.name == "委托借款代理服务合同") {
                            fuwuhetong += it.receivable
                        } else {
                            elsess += it.receivable
                        }

                    }

                }
                dataList.add(benjin)
                dataList.add(jibenfeilv)
                dataList.add(five)
                dataList.add(jiekuanhetong)
                dataList.add(fuwuhetong)
                dataList.add(elsess)
                def bankAccount =  OpportunityFlexField.findByNameAndCategory("放款账号",OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.opportunity,FlexFieldCategory.findByName("资金渠道")))?.value
                dataList.add(bankAccount)
                def bankAccount1 =  OpportunityFlexField.findByNameAndCategory("债转账号",OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(it.opportunity,FlexFieldCategory.findByName("资金渠道")))?.value
                dataList.add(bankAccount1)
                def debitAccount = it.debitAccount
                if (debitAccount == null)
                {
                    debitAccount = "TS621"
                }
                dataList.add(debitAccount)
                def productName = it.opportunity?.product?.name
                dataList.add(productName)
                list.add(dataList)
            }
            def listTitle = new ArrayList<Map>(Arrays.asList("合同号", "放款日期", "借款人姓名", " 放款金额", "客户付息日", "订单号", "总金额", "本金", "利息","服务费+本金、利息违约金、提前还款违约金","借款合同","服务合同","其他","放款账户","债转账户","清分帐号","产品类型"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        } else {
            def sql = "from TransactionRecord where 1=1 "
            if (startTime) {
                sql += "and plannedStartTime >= '${startTime}'"
            }
            if (endTime) {
                sql += "and plannedStartTime <= '${endTime}'"
            }
            if (type && type != "null") {
                sql += "and repaymentMethod.name = '${type}'"
            }
            if (status && status != "null") {
                sql += "and status.name = '${status}'"
            }
            if (fullName) {
                sql += "and opportunity.fullName = '${fullName}'"
            }
            if (serialNumber) {
                sql += "and opportunity.serialNumber = '${serialNumber}'"
            }

            list = TransactionRecord.findAll(sql + "order by plannedStartTime , repaymentMethod.id", [max: max, offset: offset])
            count = TransactionRecord.findAll(sql + "order by plannedStartTime , repaymentMethod.id").size()

        }


        respond list, model: [list: list, count: count, params: params]
    }

    def fuiouPayment(TransactionRecord transactionRecord) {
        def params = [:]
        params["max"] = 10
        params["offset"] = params.offset
        println transactionRecord.status.name
        if (transactionRecord.status.name == "未执行") {
            println "i11n"
            transactionRecord.startTime = new Date()
            transactionRecord.status = TransactionRecordStatus.findByName("待确认")
            transactionRecord.save()
            def param = [:]
            param["fullName"] = transactionRecord?.credit?.name
            param["numberOfAccount"] = transactionRecord?.credit?.numberOfAccount
            param["cellPhone"] = transactionRecord?.credit?.cellphone
            param["idNumber"] = transactionRecord?.credit?.numberOfCertificate
            param["amount"] = transactionRecord?.amount
            param["bankName"] = transactionRecord?.credit?.bank?.name
            def postMessage = JsonOutput.toJson(param).toString()
            def returnDatas
            java.net.HttpURLConnection conn
            try {
                java.net.URL url = new java.net.URL("http://106.3.133.201/payment/signQuery")
                conn = (java.net.HttpURLConnection) url.openConnection()
                conn.setRequestProperty("Content-Type", "application/json")
                conn.setRequestMethod("POST")
                conn.setDoOutput(true)
                conn.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write postMessage }
                conn.setConnectTimeout(10000)
                returnDatas = JSON.parse(conn.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println returnDatas
                if (returnDatas["code"] == 400) {
                    println "success"
                    transactionRecord.status = TransactionRecordStatus.findByName("已成功")
                    transactionRecord.endTime = new Date()
                    transactionRecord.save()
                    billsService.updateBillsFromRecord(transactionRecord.opportunity, transactionRecord)
                    render returnDatas["code"]
                } else {
                    println "feiled"
                    transactionRecord.status = TransactionRecordStatus.findByName("扣款失败")
                    transactionRecord.endTime = new Date()
                    transactionRecord.memo = returnDatas["desc"]
                    transactionRecord.save()
                }
            }
            catch (java.lang.Exception e) {
                e.printStackTrace()
            }
        }
        redirect action: "search", params: [params: params]

    }

    @Transactional(readOnly = true)
    def show(TransactionRecord transactionRecord) {
        def oid = params.opportunity
        respond transactionRecord, model: [oid: oid]
    }

    def create() {
        def oid = params.opportunity
        def opportunity = Opportunity.findById(params['opportunity'])
        if (opportunity?.type != null && opportunity?.type?.name != "抵押贷款") {
            opportunity = opportunity?.parent
        }
        def transactionRecord = new TransactionRecord(params)
        transactionRecord.opportunity = opportunity
        respond transactionRecord, model: [oid: oid]
    }

    @Transactional
    def save(TransactionRecord transactionRecord) {
        def oid = params.oid
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        transactionRecord.createdBy = user

        if (transactionRecord == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionRecord.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionRecord.errors, view: 'create'
            return
        }

        if (transactionRecord.validate()) {
            transactionRecord.save flush: true
        } else {
            println transactionRecord.errors
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transactionRecord.label', default: 'TransactionRecord'), transactionRecord.id])
                //redirect transactionRecord
                redirect(controller: "opportunity", action: "show", method: "GET", id: oid)
            }
            '*' { respond transactionRecord, [status: CREATED] }
        }
    }

    def edit(TransactionRecord transactionRecord) {
        def oid = params.opportunity
        respond transactionRecord, model: [oid: oid]
    }

    @Transactional
    def update(TransactionRecord transactionRecord) {
        def oid = params.oid
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        transactionRecord.modifiedBy = user

        if (transactionRecord == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (transactionRecord.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond transactionRecord.errors, view: 'edit'
            return
        }

        transactionRecord.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'transactionRecord.label', default: 'TransactionRecord'), transactionRecord.id])
                //redirect transactionRecord
                redirect(controller: "opportunity", action: "show", method: "GET", id: oid)
            }
            '*' { respond transactionRecord, [status: OK] }
        }
    }

    @Transactional
    def delete(TransactionRecord transactionRecord) {
        def oid = params.oid
        if (transactionRecord == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def hasBills = false
        def bills = Bills.findByOpportunity(transactionRecord.opportunity)
        if (bills) {
            def billsItems = BillsItem.findAllByBillsAndTransactionRecord(bills, transactionRecord)
            if (billsItems.size() > 0) {
                hasBills = true
            }
        }
        if (!hasBills) {
            transactionRecord.delete flush: true

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'transactionRecord.label', default: 'TransactionRecord'), transactionRecord.id])
                    //redirect action:"index", method:"GET"
                    redirect(controller: "opportunity", action: "show", method: "GET", id: oid)
                }
                '*' { render status: NO_CONTENT }
            }
        } else {
            flash.message = "对不起，请先删除此条记录关联的还款计划"
            redirect(controller: "opportunity", action: "show", method: "GET", id: oid)
        }
    }

    @Transactional
    def deleteAll() {
        def id = params.opportunity
        def opportunity = Opportunity.findById(id)
        if (opportunity?.type != OpportunityType.findByName("抵押贷款")) {
            opportunity = opportunity?.parent
        }
        def bills = opportunity?.bills[0]
        if (bills == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        def items = BillsItem.findAllByBills(bills)
        println items
        if (items.size() == 0) {
            def transactionRecords = TransactionRecord.findAllByOpportunity(opportunity)
            println transactionRecords
            transactionRecords.each {
                it.delete()
            }
        }
        redirect controller: "opportunity", action: "show", method: "GET", id: id
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transactionRecord.label', default: 'TransactionRecord'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
