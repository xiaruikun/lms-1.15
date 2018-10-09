package com.next

import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO'])
class BillsImportController
{

    def opportunityStatisticsService
    def billsImportService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('permitAll')
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond BillsImport.list(params), model: [billsImportCount: BillsImport.count()]
    }

    def show(BillsImport billsImport)
    {
        respond billsImport
    }

    def create()
    {
        def billsImport = new BillsImport()
        respond billsImport
    }

    def save(BillsImport billsImport)
    {
        if (billsImport == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (billsImport.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond billsImport.errors, view: 'create'
            return
        }

        billsImport.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'billsImport.label', default: 'BillsImport'), billsImport.id])
                redirect billsImport
            }
            '*' { respond billsImport, [status: CREATED] }
        }
    }

    def edit(BillsImport billsImport)
    {
        respond billsImport
    }

    def update(BillsImport billsImport)
    {
        if (billsImport == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (billsImport.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond billsImport.errors, view: 'edit'
            return
        }

        billsImport.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'billsImport.label', default: 'BillsImport'), billsImport.id])
                redirect billsImport
            }
            '*' { respond billsImport, [status: OK] }
        }
    }

    def delete(BillsImport billsImport)
    {

        if (billsImport == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        billsImport.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'billsImport.label', default: 'BillsImport'), billsImport.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'billsImport.label', default: 'BillsImport'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def download()
    {
        //放款csv导出
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
        def names = params.name
        def serialNumbers = names.toString().split(",")
        println serialNumbers
        def listReport = []
        def count = 1
        def amount = 0
        def listTitle = []
        String s0 = "S"
        String s1 = "000191400205805"
        String s2 = sdf.format(new Date())
        String s3 = "1"
        String s4 = "4272000"
        String s5 = "14901"
        response.setContentType("text/csv")
        response.setContentType("application/csv;charset=GBK")
        serialNumbers.each {
            def opportunity = Opportunity.findBySerialNumber(it)
            def item = []
            //序号000001开始
            String n0 = ""
            String n1 = ""
            String n2 = ""
            String n3 = ""
            String n4 = ""
            String n5 = ""
            String n6 = ""
            String n7 = ""
            String n8 = ""
            String n9 = ""
            String n10 = ""
            String n11 = ""
            String n12 = ""
            String n13 = ""
            String n14 = ""
            String n15 = ""
            String n16 = ""
            String n17 = ""
            String n18 = ""
            String n19 = ""
            String n20 = ""
            String n21 = ""
            String n22 = ""
            String n23 = ""

            for (
                def i = 0;
                    i < 6 - count.toString().length();
                    i++)
            {
                n0 = n0 + "0"
            }
            n0 = n0 + count.toString()
            def bankAccounts = opportunity.bankAccounts
            bankAccounts.each {
                if (it.type.name == "还款账号")
                {
                    def bankAccount = it.bankAccount
                    def bankName = bankAccount.bank.name
                    println "bankName"
                    println bankName
                    if (bankName == "工商银行")
                    {
                        n2 = "102"
                    }
                    if (bankName == "建设银行")
                    {
                        n2 = "105"
                    }
                    if (bankName == "招商银行")
                    {
                        n2 = "308"
                    }
                    if (bankName == "农业银行")
                    {
                        n2 = "103"
                    }
                    if (bankName == "光大银行")
                    {
                        n2 = "303"
                    }
                    if (bankName == "广发银行")
                    {
                        n2 = "306"
                    }
                    if (bankName == "兴业银行")
                    {
                        n2 = "309"
                    }
                    if (bankName == "上海浦东发展银行")
                    {
                        n2 = "310"
                    }
                    n4 = bankAccount.numberOfAccount
                    n5 = bankAccount.name
                    def certificateType = bankAccount.certificateType.name
                    if (certificateType == "身份证")
                    {
                        n14 = "0"
                    }
                    if (certificateType == "临时身份证")
                    {
                        n14 = "7"
                    }
                    if (certificateType == "护照")
                    {
                        n14 = "2"
                    }
                    n15 = bankAccount.numberOfCertificate.toString()
                }
            }
            n10 = (opportunity.actualLoanAmount * 1000000).toString() //分为金额单位
            amount += (opportunity.actualLoanAmount * 1000000)
            item.add(n0)
            item.add(n1)
            item.add(n2)
            item.add(n3)
            item.add(n4)
            item.add(n5)
            item.add(n6)
            item.add(n7)
            item.add(n8)
            item.add(n9)
            item.add(n10)
            item.add(n11)
            item.add(n12)
            item.add(n13)
            item.add(n14)
            item.add(n15)
            item.add(n16)
            item.add(n17)
            item.add(n18)
            item.add(n19)
            item.add(n20)
            item.add(n21)
            item.add(n22)
            item.add(n23)
            listReport.add(item)
            count++
        }
        s3 = (count - 1).toString()
        s4 = amount.toString()
        listTitle.add(s0)
        listTitle.add(s1)
        listTitle.add(s2)
        listTitle.add(s3)
        listTitle.add(s4)
        listTitle.add(s5)

        response.setHeader("Content-Disposition", "attachment;FileName=grep.csv")
        OutputStream out = null
        try
        {
            out = response.getOutputStream()
        }
        catch (Exception e)
        {
            e.printStackTrace()
        }
        println listTitle
        println listReport
        opportunityStatisticsService.exportExcel(out, listTitle, listReport)
        return null

    }

    def searchBills()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def name = params["name"]
        def commitTime = params["commitTime"]
        def serialNumber = params["serialNumber"]
        def debitAccount = params["debitAccount"]
        def resultReason = params["resultReason"]
        def failReason = params["failReason"]
        def status = params["status"]

        String sql = "from BillsImport as u where 1=1"
        if (name)
        {
            sql += " and u.name like '%${name}%'"
        }
        if (commitTime)
        {
            sql += " and u.commitTime = '${commitTime}'"
        }
        if (serialNumber)
        {
            sql += " and u.serialNumber = '${serialNumber}'"
        }
        if (debitAccount)
        {
            sql += " and u.debitAccount = '${debitAccount}'"
        }
        if (resultReason)
        {
            sql += " and u.resultReason = '${resultReason}'"
        }
        if (failReason)
        {
            sql += " and u.failReason = '${failReason}'"
        }
        if (status)
        {
            sql += " and u.status = '${status}'"
        }

        def max = params.max
        def offset = params.offset

        def list = BillsImport.findAll(sql, [max: max, offset: offset])
        def list1 = BillsImport.findAll(sql)
        def count = list1.size()

        def billsImport = new BillsImport(params)

        respond list, model: [billsImportCount: count, billsImport: billsImport, params: params], view: 'index'
    }

    def upload()
    {
        //银行回盘数据csv导入
        def f = request.getFile('csv')
        def opportunityCsvFile = OpportunityCsvFile.findByFilename(f.originalFilename)
        if (!f.empty && opportunityCsvFile == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            println webRootDir
            def userDir = new File(webRootDir, "/bankCsv/")
            userDir.mkdirs()
            def file = new File(userDir, f.originalFilename)
            f.transferTo(file)
            opportunityCsvFile = new OpportunityCsvFile()
            opportunityCsvFile.filename = f.originalFilename
            opportunityCsvFile.filepath = webRootDir + "bankCsv/" + f.originalFilename

            //根据文件创建billsImport对象
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"))
            br.readLine()
            String line = null
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "bankCsv/" + opportunityCsvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            def count = 0
            def count1 = 0
            def count2 = 0
            StringBuffer readLine = new StringBuffer()
            try
            {
                while ((line = br.readLine()) != null)
                {
                    readLine.append(line)
                    // 考虑单元格中有逗号的情况
                    def maxPosition = readLine.length()
                    while (st < maxPosition)
                    {
                        def ch = readLine.charAt(st)
                        if (inquote && ch == ',')
                        {
                            readLine.setCharAt(st, (char) '，')
                        }
                        if (ch == '"')
                        {
                            inquote = !inquote
                        }
                        st++
                    }

                    line = readLine.toString()
                    // 如果双引号是奇数的时候继续读取，考虑单元格中有换行的情况
                    def splitByDoubleQuotes = line.split('"')
                    def countDoubleQuotes = 0
                    if (line.charAt(line.length() - 1) == '"')
                    {
                        countDoubleQuotes = splitByDoubleQuotes.length
                    }
                    else
                    {
                        countDoubleQuotes = splitByDoubleQuotes.length - 1
                    }
                    if (countDoubleQuotes % 2 == 1)
                    {
                        continue
                    }
                    else
                    {
                        readLine.setLength(0)
                        st = 0
                    }

                    def list = line.split(",")
                    count++
                    //p.println(line + "......." + list.length)
                    if (list.size() > 6 && list[11].indexOf("批量代收") >= 0)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
                        def commitTime = sdf.parse(list[3])
                        //commitTime.setHours(0)
                        //commitTime.setMinutes(0)
                        //commitTime.setSeconds(0)
                        p.println("清算时间：" + commitTime)
                        def name = list[9]
                        p.println("账户名：" + name)
                        if (list[8].indexOf(";") >= 0)
                        {
                            list[8] = list[8].substring(0, list[8].length() - 1)
                        }
                        def numberOfAccount = list[8]
                        p.println("银行卡号：" + numberOfAccount)
                        //def numberOfCertificate = list[15]
                        if (list[13].charAt(0) == '"')
                        {
                            list[13] = list[13].substring(1, list[13].length() - 1)
                        }
                        if (list[13].indexOf("，") >= 0)
                        {
                            list[13] = list[13].replace("，", "")
                        }
                        Double actualAmountOfCredit = Double.parseDouble(list[13])
                        p.println("交易金额：" + actualAmountOfCredit)

                        def billsImport = BillsImport.findByNameAndNumberOfAccountAndActualAmountOfCreditAndCommitTime(name, numberOfAccount, actualAmountOfCredit, commitTime)
                        if (!billsImport)
                        {
                            billsImport = new BillsImport()
                            billsImport.createdDate = new Date()
                            billsImport.commitTime = commitTime
                            billsImport.name = list[9]
                            billsImport.numberOfAccount = numberOfAccount
                            billsImport.actualAmountOfCredit = actualAmountOfCredit
                            if (list[12] != null)
                            {
                                billsImport.debitAccount = list[12]
                            }
                            if (list.size() > 18 && list[18] != null)
                            {
                                billsImport.serialNumber = list[18]
                                p.println("合同编号：" + billsImport.serialNumber)
                            }

                            //billsImport.certificateCode = list[14]
                            //billsImport.bankCode = list[2]
                            //billsImport.numberOfCertificate = list[15]
                            billsImport.resultCode = list[15]
                            billsImport.resultReason = list[16]
                            p.println(billsImport.resultCode + billsImport.resultReason)
                            billsImport.modifiedDate = new Date()
                            billsImport.status = "待处理"
                            billsImport.failReason = "无"
                            billsImport.save flush: true
                            count1++
                            p.println("保存成功！！！")
                            billsImport.errors.each {
                                log.info it.toString()
                            }
                        }
                        else
                        {
                            count2++
                            if (list[12] != null)
                            {
                                billsImport.debitAccount = list[12]
                            }
                            p.println(name + "于" + commitTime + "还款" + actualAmountOfCredit + "的流水已存在!")
                        }
                    }
                }
            }
            catch (Exception e)
            {
                log.info "file read wrong"
                p.println("file read wrong")
            }
            finally
            {
                if (br != null)
                {
                    try
                    {
                        br.close()
                        br = null
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace()
                    }
                }
                log.info "file read success!!!"
                p.println("导入完成，本次读取流水总数: " + count + ", 本次新生成流水条数: " + count1 + ", 本次发现已存在流水条数: " + count2)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        opportunityCsvFile.save()
        opportunityCsvFile.errors.each {
            log.info it.toString()
        }

        if (!opportunityCsvFile.hasErrors() && opportunityCsvFile.save())
        {
            flash.message = "上传成功"
        }
        else
        {
            flash.message = "上传失败"
        }
        redirect(controller: "billsImport", action: "index")
    }

    def deal()
    {
        def billsImportList = BillsImport.findAllByStatusAndResultCode("待处理", "成功")
        //billsImportList.each {
        for (
            def i = billsImportList.size();
                i > 0;
                i--)
        {
            BillsImport billsImport = billsImportList[i - 1]
            def name = billsImport.name
            def numberOfAccount = billsImport.numberOfAccount
            def resultReason = billsImport.resultReason
            println "银行帐号：" + numberOfAccount
            Double actualAmountOfCredit = billsImport.actualAmountOfCredit
            Date actualEndTime = billsImport.commitTime
            def debitAccount = billsImport.debitAccount

            Opportunity opportunity = null
            OpportunityBankAccount opportunityBankAccount = null
            OpportunityBankAccountType type = OpportunityBankAccountType.findByName("还款账号")
            // 通过合同编号查找对应的订单
            def serialNumber = billsImport.serialNumber
            if (serialNumber != null)
            {
                opportunity = Opportunity.findByExternalId(serialNumber)
                if (opportunity == null)
                {
                    def contractType = ContractType.findByName("借款合同")
                    def contract = Contract.findBySerialNumberAndType(serialNumber, contractType)
                    if (contract != null)
                    {
                        def opportunityContract = OpportunityContract.findByContract(contract)
                        opportunity = opportunityContract?.opportunity
                    }
                }
                if (opportunity && opportunity.type?.id == OpportunityType.findByCode("10")?.id)
                {
                    opportunityBankAccount = OpportunityBankAccount.findByOpportunityAndType(opportunity, type)
                    if (resultReason.indexOf("自己转账") < 0 && opportunityBankAccount?.bankAccount?.numberOfAccount != numberOfAccount)
                    {
                        billsImport.failReason = "银行卡号不匹配"
                        opportunity = null
                        opportunityBankAccount = null
                    }
                }
                else
                {
                    billsImport.failReason = "合同编号有误"
                    opportunity = null
                }
            }
            else
            {
                billsImport.failReason = "缺少合同编号"
            }

            // 通过银行卡号查找对应的订单
            if (opportunity == null)
            {
                def bankAccounts = BankAccount.findAllByNameAndNumberOfAccount(name, numberOfAccount)
                println "银行帐号个数：" + bankAccounts.size()
                if (bankAccounts.size() > 0)
                {
                    for (
                        def j = 0;
                            j < bankAccounts.size();
                            j++)
                    {
                        opportunityBankAccount = OpportunityBankAccount.findByBankAccountAndType(bankAccounts[j], type)
                        if (opportunityBankAccount)
                        {
                            if (opportunityBankAccount?.opportunity?.type?.id == OpportunityType.findByCode("10")?.id)
                            {
                                println "找到银行帐号关联的订单"
                                break
                            }
                        }
                    }
                    if (!opportunityBankAccount)
                    {
                        billsImport.failReason = "无关联opportunityBankAccount对象"
                        billsImport.status = "失败"
                        billsImport.modifiedDate = new Date()
                        flash.message = "无关联opportunityBankAccount对象"
                    }
                }
                else
                {
                    billsImport.failReason = "无关联bankAccount对象"
                    billsImport.status = "失败"
                    billsImport.modifiedDate = new Date()
                    flash.message = "无关联bankAccount对象"
                }
                if (opportunityBankAccount)
                {
                    opportunity = opportunityBankAccount.opportunity
                }
            }

            if (opportunity)
            {
                println "订单编号：" + opportunity?.serialNumber
                billsImport.opportunityNumber = opportunity?.serialNumber
                //更新billsItem
                Boolean flag = billsImportService.deal(opportunity, actualAmountOfCredit, actualEndTime, debitAccount)

                if (flag)
                {
                    billsImport.failReason = "无"
                    billsImport.status = "成功"
                    billsImport.modifiedDate = new Date()
                    flash.message = "回盘数据处理成功"
                }
                else
                {
                    billsImport.failReason = "流水匹配失败"
                    billsImport.status = "失败"
                    billsImport.modifiedDate = new Date()
                    flash.message = "billsItem对象更新失败"
                }
            }
            else
            {
                billsImport.failReason = "无关联订单"
                billsImport.status = "失败"
                billsImport.modifiedDate = new Date()
                flash.message = "无关联opportunity对象"
            }
            billsImport.save flush: true
        }

        redirect(controller: "billsImport", action: "index")
    }

    def test()
    {
        billsImportService.deal()
        render 1
    }
}
