package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class OpportunityCsvFileController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def opportunityService
    def componentService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond OpportunityCsvFile.list(params), model: [opportunityCsvFileCount: OpportunityCsvFile.count()]
    }

    def show(OpportunityCsvFile opportunityCsvFile)
    {
        respond opportunityCsvFile
    }

    def create()
    {
        respond new OpportunityCsvFile(params)
    }

    @Transactional
    def uploadBaseInfo()
    {
        def csvFile = new OpportunityCsvFile()
        def url = params.myFile
        log.info url.toString()

        def f = request.getFile('myFile')
        def file1 = new OpportunityCsvFile()
        file1.setFilename(f.originalFilename)
        def file1s = csvFile.find(file1)
        if (!f.empty && file1s == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            log.info webRootDir.toString()
            def csvDir = new File(webRootDir, "/upload/")
            csvDir.mkdirs()
            def fileSave = new File(csvDir, f.originalFilename)
            f.transferTo(fileSave)
            csvFile.filetype = '基础信息'
            csvFile.filename = f.originalFilename
            csvFile.filepath = webRootDir + "upload/" + f.originalFilename
            csvFile.save flush: true
            //read file and save datas
            //def br = new BufferedReader(new FileReader(fileSave))
            FileInputStream fr = new FileInputStream(fileSave);
            InputStreamReader isr = new InputStreamReader(fr, "GBK");
            BufferedReader br = new BufferedReader(isr);
            br.readLine()
            //def line = ""
            def line = null
            def index = 1
            def count = 0
            def count1 = 0
            def count2 = 0
            def count3 = 0
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "upload/" + csvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
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

                    index++
                    def arry = line.split(",")
                    for (
                        def i = 0;
                            i < arry.length;
                            i++)
                    {
                        if (arry[i])
                        {
                            arry[i] = arry[i].trim()
                        }
                    }
                    //p.println(line + "......" + arry.length)
                    if (arry[51] || arry[52] || arry[54]) // || !arry[61])
                    {
                        continue
                    }

                    //if (arry[1] == "北京" || arry[1] == "机构北京")
                    //{
                    //p.println(line + "......" + arry.length)
                    count++

                    // 规范化城市名称
                    def cityName = arry[1]
                    if (arry[1].indexOf("机构") >= 0)
                    {
                        cityName = arry[1].substring(2, arry[1].length())
                    }
                    def city = City.findByName(cityName)

                    // 规范化借款人姓名
                    if (arry[7].charAt(0) == '"')
                    {
                        arry[7] = arry[7].substring(1, arry[7].length() - 1)
                    }
                    def fullName = arry[7].split(";")
                    for (
                        def i = 0;
                            i < fullName.length;
                            i++)
                    {
                        if (fullName[i])
                        {
                            fullName[i] = fullName[i].trim()
                            if (fullName[i].indexOf("（转单）") >= 0)
                            {
                                fullName[i] = fullName[i].replaceAll("（转单）", "")
                            }
                        }
                    }

                    def oldOpportunity = Opportunity.findByExternalId(arry[0])
                    //def oldOpportunity = Opportunity.findByImportDateAndExternalModifiedDateAndExternalId(null, null, arry[0])
                    if (oldOpportunity)
                    {
                        if (oldOpportunity.importDate != null || oldOpportunity.externalModifiedDate != null)
                        {
                            p.println("此订单已经导入过系统！！！")
                            continue
                        }

                        def nameEqual = false
                        for (
                            def i = 0;
                                i < fullName.length;
                                i++)
                        {
                            if (oldOpportunity.fullName == fullName[i])
                            {
                                nameEqual = true
                                break
                            }
                        }
                        if (!nameEqual)
                        {
                            p.println("请注意：系统中订单" + oldOpportunity.serialNumber + "的合同编号可能有误!!!")
                            oldOpportunity = null
                        }
                    }

                    if (oldOpportunity == null)
                    {
                        for (
                            def i = 0;
                                i < fullName.length;
                                i++)
                        {
                            def opportunities = Opportunity.findAllByImportDateAndExternalModifiedDateAndFullName(null, null, fullName[i])
                            //def opportunities = Opportunity.findAllByFullName(fullName[i])

                            if (opportunities.size() > 0)
                            {
                                for (
                                    def j = 0;
                                        j < opportunities.size();
                                        j++)
                                {
                                    def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunities[j]?.id} order by id asc")
                                    if (collaterals.size() > 0)
                                    {
                                        def ocity = collaterals[0].city?.name
                                        p.println("城市：" + ocity)
                                        if (ocity != cityName)
                                        {
                                            continue
                                        }
                                    }
                                    else
                                    {
                                        continue
                                    }

                                    //if (it?.stage == OpportunityStage.findByCode("08") || it?.stage == OpportunityStage.findByCode("21") || !nameEqual)
                                    //{
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
                                    Date tempTime1 = sdf.parse(arry[19])
                                    Date tempTime2 = OpportunityFlow.findByOpportunityAndStage(opportunities[j], OpportunityStage.findByCode("08"))?.startTime
                                    if (tempTime2)
                                    {
                                        def tempDuration = Math.abs(tempTime1.getTime() - tempTime2.getTime()) / (24.0 * 3600 * 1000)
                                        p.println("审批日期差: " + tempDuration)
                                        if (tempDuration < 5.0)
                                        {
                                            oldOpportunity = opportunities[j]
                                            break
                                        }
                                    }
                                    //}
                                }
                            }
                        }
                    }

                    if (oldOpportunity == null)
                    {
                        //p.println(line + "......" + arry.length)
                        def newOpportunity = new Opportunity()
                        newOpportunity.stage = OpportunityStage.findByCode("20")
                        newOpportunity.setExternalId(arry[0])
                        newOpportunity.city = city
                        def agent = Contact.findByCityAndFullName(city, arry[4])
                        def user
                        if (agent)
                        {
                            newOpportunity.contact = agent
                            user = agent.user
                        }

                        if (user == null)
                        {
                            user = User.findByFullNameAndCity(arry[4], city)
                        }

                        if (user)
                        {
                            p.println("序号: " + index + ", " + "该订单已分配给支持经理: " + user?.username)
                        }
                        else
                        {
                            def department = Department.findByName("销售组")
                            def position = Position.findByName("总经理")
                            user = User.findByCityAndDepartmentAndPosition(city, department, position)
                            if (user)
                            {
                                p.println("序号: " + index + ", " + "该在途订单无法找到当前支持经理，已放在分公司销售总经理" + user?.username + "名下")
                            }
                            else
                            {
                                p.println("序号: " + index + ", " + "该在途订单无法找到当前支持经理，请在文件中添加销售员后再上传")
                                //p.println("文件导入已被终止")
                                count2++
                                continue
                            }
                        }
                        newOpportunity.user = user
                        newOpportunity.account = user?.account

                        newOpportunity.setFullName(fullName[0])
                        def idNumber = null
                        if (arry[8])
                        {
                            if (arry[8].charAt(0) == '"')
                            {
                                arry[8] = arry[8].substring(1, arry[8].length() - 1)
                            }
                            idNumber = arry[8].split(";")
                            for (
                                def i = 0;
                                    i < idNumber.length;
                                    i++)
                            {
                                if (idNumber[i])
                                {
                                    idNumber[i] = idNumber[i].trim()
                                }
                            }
                            if (idNumber.length > 0)
                            {
                                newOpportunity.setIdNumber(idNumber[0])
                            }
                        }
                        def cellphone = null
                        if (arry[9])
                        {
                            if (arry[9].charAt(0) == '"')
                            {
                                arry[9] = arry[9].substring(1, arry[9].length() - 1)
                            }
                            cellphone = arry[9].split(";")
                            for (
                                def i = 0;
                                    i < cellphone.length;
                                    i++)
                            {
                                if (cellphone[i])
                                {
                                    cellphone[i] = cellphone[i].trim()
                                    if (cellphone[i] != "无" && cellphone[i].length() < 11)
                                    {
                                        StringBuilder preCellphone = new StringBuilder(cellphone[i])
                                        StringBuilder baseNumber = new StringBuilder("00000000000")
                                        cellphone[i] = preCellphone + baseNumber.substring(preCellphone.length(), 11)
                                    }
                                    else if (cellphone[i].length() > 11)
                                    {
                                        cellphone[i] = cellphone[i].substring(0, 11)
                                    }

                                    // 导入固定电话号码会出错，临时解决方案，待完善
                                    if (i < cellphone.length - 1 && cellphone[i].length() == 11 && cellphone[i].charAt(0) != "1")
                                    {
                                        cellphone[i] = cellphone[i + 1]
                                    }
                                }
                            }
                            if (cellphone.length > 0)
                            {
                                newOpportunity.setCellphone(cellphone[0])
                            }
                        }
                        def maritalStatus = null
                        if (arry[12])
                        {
                            if (arry[12].charAt(0) == '"')
                            {
                                arry[12] = arry[12].substring(1, arry[12].length() - 1)
                            }
                            maritalStatus = arry[12].split(";")
                            for (
                                def i = 0;
                                    i < maritalStatus.length;
                                    i++)
                            {
                                if (maritalStatus[i])
                                {
                                    maritalStatus[i] = maritalStatus[i].trim()
                                }
                            }
                            if (maritalStatus.length > 0)
                            {
                                newOpportunity.setMaritalStatus(maritalStatus[0])
                            }
                        }

                        p.println("1. 订单基本信息导入完成")
                        def monthlyInterest = arry[21]
                        //.substring(0, arry[21].length() - 1)
                        newOpportunity.setMonthlyInterest(Double.parseDouble(monthlyInterest) * 100)
                        def serviceCharge = arry[22]
                        //.substring(0, arry[22].length() - 1)
                        if (arry[63].indexOf("按月支付") >= 0)
                        {
                            newOpportunity.setServiceCharge(Double.parseDouble(serviceCharge) * 100)
                        }
                        else
                        {
                            newOpportunity.setServiceCharge(Double.parseDouble(arry[66]) * 100)
                        }
                        def dealRate = arry[24]
                        //.substring(0, arry[24].length() - 1)
                        newOpportunity.setDealRate(Double.parseDouble(dealRate) * 100)
                        def interestPaymentMethodName = arry[25]
                        if (arry[25] == "一次性收息")
                        {
                            interestPaymentMethodName = "一次性付息"
                        }
                        def interestPaymentMethod = InterestPaymentMethod.findByName(interestPaymentMethodName)
                        if (interestPaymentMethod)
                        {
                            //if (arry[26].charAt(0) == '"')
                            //    arry[26] = arry[26].substring(1, arry[26].length() - 1)
                            //interestPaymentMethod.desription = arry[26]
                            newOpportunity.setInterestPaymentMethod(interestPaymentMethod)
                        }

                        if (arry[64].indexOf("按月支付") >= 0)
                        {
                            newOpportunity.commissionPaymentMethod = CommissionPaymentMethod.findByName("月月返")
                        }
                        else
                        {
                            newOpportunity.commissionPaymentMethod = CommissionPaymentMethod.findByName("一次性返")
                        }
                        if (arry[27] == "一押")
                        {
                            arry[27] = "一抵"
                        }
                        else
                        {
                            arry[27] = "二抵"
                        }
                        def mortgageType = MortgageType.findByName(arry[27])
                        if (mortgageType)
                        {
                            newOpportunity.mortgageType = mortgageType
                        }

                        newOpportunity.setRequestedAmount(Double.parseDouble(arry[28]) / 10000)
                        newOpportunity.setActualAmountOfCredit(Double.parseDouble(arry[28]) / 10000)
                        newOpportunity.setActualLoanAmount(Double.parseDouble(arry[28]) / 10000)
                        newOpportunity.setCommission(Double.parseDouble(arry[29]) / 10000)
                        newOpportunity.setAdvancePayment(Double.parseDouble(arry[65]))
                        def commissionRate = arry[30]
                        //.substring(0, arry[30].length() - 1)
                        if (arry[64].indexOf("按月支付") >= 0)
                        {
                            newOpportunity.setCommissionRate(Double.parseDouble(commissionRate) * 100)
                        }
                        else
                        {
                            newOpportunity.setCommissionRate(Double.parseDouble(arry[29]) / Double.parseDouble(arry[28]) * 100)
                        }

                        //if (arry[61])
                        //{
                        //    newOpportunity.setLoanAmount(Double.parseDouble(arry[31]) / 10000)
                        //    def firstMortgageRate = Double.parseDouble(arry[34]) * 100
                        //    if (firstMortgageRate > 0.0)
                        //    {
                        //        newOpportunity.setFirstMortgageAmount(newOpportunity.loanAmount * firstMortgageRate / 100)
                        //        newOpportunity.setSecondMortgageAmount(Double.parseDouble(arry[28]) / 10000)
                        //        newOpportunity.typeOfFirstMortgage = TypeOfFirstMortgage.findByName("银行类")
                        //    }
                        //    else
                        //    {
                        //        newOpportunity.setFirstMortgageAmount(Double.parseDouble(arry[28]) / 10000)
                        //        newOpportunity.typeOfFirstMortgage = TypeOfFirstMortgage.findByName("非银行类")
                        //    }
                        //}

                        if (!arry[40])
                        {
                            arry[40] = "速E贷"
                        }
                        def product = Product.findByName(arry[40])
                        if (product)
                        {
                            newOpportunity.product = product
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
                        newOpportunity.setActualLendingDate(sdf.parse(arry[44]))
                        newOpportunity.setLoanDuration(Integer.parseInt(arry[46]))
                        newOpportunity.setActualLoanDuration(Integer.parseInt(arry[46]))
                        Date importDate = new java.util.Date()
                        newOpportunity.setImportDate(importDate)
                        newOpportunity.save flush: true
                        count1++
                        //opportunityService.initOpportunity(newOpportunity)
                        //println newOpportunity.fullName + newOpportunity.city?.name + newOpportunity.serialNumber

                        p.println("2. 订单贷款信息导入完成")
                        for (
                            def i = 0;
                                i < fullName.length;
                                i++)
                        {
                            def client = new Contact()
                            client.type = "Client"
                            client.fullName = fullName[i]
                            if (cellphone && cellphone.length > i && cellphone[i] != "无")
                            {
                                client.cellphone = cellphone[i]
                            }
                            if (idNumber && idNumber.length > i && idNumber[i] != "无")
                            {
                                client.idNumber = idNumber[i]
                            }
                            if (maritalStatus && maritalStatus.length > i && maritalStatus[i] != "无")
                            {
                                client.maritalStatus = maritalStatus[i]
                            }
                            client.save flush: true
                            def opportunityContact = new OpportunityContact()
                            opportunityContact.opportunity = newOpportunity
                            opportunityContact.contact = client
                            if (i == 0)
                            {
                                opportunityContact.type = OpportunityContactType.findByName("借款人")
                                if (arry[42] == "优质")
                                {
                                    arry[42] = "A"
                                }
                                else if (arry[42] == "次优")
                                {
                                    arry[42] = "B"
                                }
                                else if (arry[42] == "疑难")
                                {
                                    arry[42] = "C"
                                }
                                else if (arry[42] == "不良")
                                {
                                    arry[42] = "D"
                                }
                                else
                                {
                                    arry[42] = "待评级"
                                }
                                def level = ContactLevel.findByName(arry[42])
                                if (level)
                                {
                                    client.level = level
                                }
                                newOpportunity.lender = client
                            }
                            else
                            {
                                opportunityContact.type = OpportunityContactType.findByName("其它借款人")
                            }
                            opportunityContact.save flush: true
                        }

                        p.println("3. 借款人信息导入完成")
                        if (arry[14])
                        {
                            def district = arry[13].split(";")
                            def addressDetails = arry[14].split(";")
                            def propertySerialNumber = arry[15].split(";")
                            def area = arry[16].split(";")
                            def assetType = arry[17].split(";")
                            def houseType = arry[18].split(";")

                            for (
                                def i = 0;
                                    i < addressDetails.length;
                                    i++)
                            {
                                def collateral = new Collateral()
                                collateral.opportunity = newOpportunity
                                //collateral.externalId = externalId
                                collateral.city = city
                                if (district[i])
                                {
                                    collateral.district = district[i]
                                }

                                def address = addressDetails[i].split("、")
                                if (address.length > 0)
                                {
                                    collateral.projectName = address[address.length - 1]
                                    collateral.address = address[address.length - 1]
                                }
                                collateral.orientation = "东"
                                if (propertySerialNumber[i])
                                {
                                    collateral.propertySerialNumber = propertySerialNumber[i]
                                }
                                if (area[i])
                                {
                                    collateral.area = Double.parseDouble(area[i])
                                }
                                else
                                {
                                    collateral.area = 0.0
                                }
                                collateral.building = 0
                                collateral.unit = 1
                                collateral.roomNumber = 0
                                collateral.floor = 0
                                collateral.totalFloor = 0

                                if (assetType[i])
                                {
                                    collateral.assetType = assetType[i]
                                }
                                else
                                {
                                    collateral.assetType = '其他'
                                }
                                if (houseType[i])
                                {
                                    collateral.houseType = houseType[i]
                                }
                                else
                                {
                                    collateral.houseType = '其他'
                                }
                                collateral.specialFactors = '无'
                                collateral.status = 'Pending'
                                collateral.requestStartTime = null
                                collateral.requestEndTime = null

                                def totalPrice = Double.parseDouble(arry[31]) / addressDetails.length
                                collateral.totalPrice = totalPrice / 10000
                                if (collateral.area > 0)
                                {
                                    collateral.unitPrice = totalPrice / collateral.area
                                }

                                def firstMortgageRate = Double.parseDouble(arry[34])
                                //.substring(0, arry[34].length() - 1))
                                if (firstMortgageRate > 0.0)
                                {
                                    //if (arry[32])
                                    //{
                                    //    collateral.firstMortgageAmount = Double.parseDouble(arry[32]) / addressDetails.length / 10000
                                    //}
                                    //else
                                    //{
                                    double firstMortgageAmount = collateral.totalPrice * firstMortgageRate
                                    collateral.firstMortgageAmount = (double) (Math.round(firstMortgageAmount * 10) / 10)
                                    //}
                                    def secondMortgageRate = Double.parseDouble(arry[33])
                                    double secondMortgageAmount = collateral.totalPrice * secondMortgageRate
                                    collateral.secondMortgageAmount = (double) (Math.round(secondMortgageAmount * 10) / 10)
                                    collateral.typeOfFirstMortgage = TypeOfFirstMortgage.findByName("银行类")
                                }
                                else
                                {
                                    double firstMortgageAmount = collateral.totalPrice * Double.parseDouble(arry[33])
                                    collateral.firstMortgageAmount = (double) (Math.round(firstMortgageAmount * 10) / 10)
                                    collateral.typeOfFirstMortgage = TypeOfFirstMortgage.findByName("非银行类")
                                }
                                def loanToValue = arry[35]
                                //.substring(0, arry[35].length() - 1)
                                collateral.loanToValue = Double.parseDouble(loanToValue) * 100
                                collateral.mortgageType = newOpportunity.mortgageType

                                if (collateral.validate())
                                {
                                    collateral.save flush: true
                                }
                                else
                                {
                                    println collateral.errors
                                }
                            }
                        }

                        p.println("4. 房产信息导入完成")
                        /*def bankAccount = new BankAccount()
                        bankAccount.name = arry[48]
                        bankAccount.numberOfAccount = arry[49].substring(0, arry[49].length() - 1)
                        for (
                            def i = 0;
                                i < fullName.length;
                                i++)
                        {
                            if (fullName[i] == arry[48])
                            {
                                if (cellphone && cellphone.length > i && cellphone[i] != "无")
                                {
                                    bankAccount.cellphone = cellphone[i]
                                }
                                if (idNumber && idNumber.length > i && idNumber[i] != "无")
                                {
                                    bankAccount.certificateType = ContactIdentityType.findByName("身份证")
                                    bankAccount.numberOfCertificate = idNumber[i]
                                }
                            }
                        }
                        bankAccount.paymentChannel = PaymentChannel.findByName("广银联")
                        if (arry[50])
                        {
                            bankAccount.bankBranch = arry[50]
                            def bankNameIndex = arry[50].indexOf("银行")
                            def bankName = arry[50].substring(0, bankNameIndex + 2)
                            bankAccount.bank = Bank.findByName(bankName)
                        }
                        bankAccount.save flush: true
                        def bankAccount1 = new OpportunityBankAccount()
                        bankAccount1.opportunity = newOpportunity
                        bankAccount1.bankAccount = bankAccount
                        bankAccount1.type = OpportunityBankAccountType.findByName("还款账号")
                        bankAccount1.save flush: true
                        def bankAccount2 = new OpportunityBankAccount()
                        bankAccount2.opportunity = newOpportunity
                        bankAccount2.bankAccount = bankAccount
                        bankAccount2.type = OpportunityBankAccountType.findByName("收款账号")
                        bankAccount2.save flush: true
                        p.println("5. 账户信息导入完成")*/

                        def calendar = arry[44].split("/")
                        def day = Double.parseDouble(calendar[2])
                        def dayFirst = Double.parseDouble(arry[62])
                        def firstPayOfMonthes = 0
                        if (dayFirst > 0)
                        {
                            if (day < 15)
                            {
                                firstPayOfMonthes = (day + dayFirst - 20) / 30 + 1
                            }
                            else if (day >= 15 && day < 20)
                            {
                                firstPayOfMonthes = (day + dayFirst - 50) / 30 + 2
                            }
                            else
                            {
                                firstPayOfMonthes = (day + dayFirst - 50) / 30 + 1
                            }
                        }
                        newOpportunity.setMonthOfAdvancePaymentOfInterest(firstPayOfMonthes)

                        def temp = newOpportunity?.account
                        def productAccount
                        while (temp)
                        {
                            productAccount = ProductAccount.findByAccountAndProduct(temp, product)
                            if (productAccount)
                            {
                                break
                            }
                            else
                            {
                                temp = temp?.parent
                            }
                        }

                        if (productAccount)
                        {
                            newOpportunity.productAccount = productAccount
                        }
                        else
                        {
                            def productName = arry[40] + cityName
                            p.println("产品名称: " + productName)
                            newOpportunity.productAccount = ProductAccount.findByName(productName)
                        }

                        def interest1 = new OpportunityProduct()
                        // 月基础利率
                        interest1.opportunity = newOpportunity
                        interest1.product = newOpportunity.productAccount
                        interest1.productInterestType = ProductInterestType.findByName("基本费率")
                        def rate1 = arry[21]
                        //.substring(0, arry[21].length() - 1)
                        interest1.rate = Double.parseDouble(rate1) * 100
                        interest1.monthes = newOpportunity.actualLoanDuration
                        if (arry[25] != "一次性收息")
                        {
                            interest1.installments = true
                            interest1.firstPayMonthes = firstPayOfMonthes
                        }
                        else
                        {
                            interest1.rate = interest1.rate * interest1.monthes
                        }
                        interest1.save flush: true
                        def interest2 = new OpportunityProduct()
                        // 月服务费率
                        interest2.opportunity = newOpportunity
                        interest2.product = newOpportunity.productAccount
                        interest2.productInterestType = ProductInterestType.findByName("服务费费率")
                        def rate2 = arry[22]
                        //.substring(0, arry[22].length() - 1)
                        interest2.rate = Double.parseDouble(rate2) * 100
                        interest2.monthes = newOpportunity.actualLoanDuration
                        if (arry[63].indexOf("按月支付") > -1)
                        {
                            interest2.installments = true
                            interest2.firstPayMonthes = firstPayOfMonthes
                        }
                        else
                        {
                            rate2 = arry[66]
                            interest2.rate = Double.parseDouble(rate2) * 100
                        }
                        interest2.save flush: true
                        def interest3 = new OpportunityProduct()
                        // 代收渠道月利率
                        interest3.opportunity = newOpportunity
                        interest3.product = newOpportunity.productAccount
                        interest3.productInterestType = ProductInterestType.findByName("渠道返费费率")
                        def rate3 = arry[30]
                        //.substring(0, arry[30].length() - 1)
                        interest3.rate = Double.parseDouble(rate3) * 100
                        interest3.monthes = newOpportunity.actualLoanDuration
                        if (arry[64].indexOf("按月支付") > -1)
                        {
                            interest3.installments = true
                            interest3.firstPayMonthes = firstPayOfMonthes
                        }
                        else
                        {
                            interest3.rate = Double.parseDouble(arry[29]) / Double.parseDouble(arry[28]) * 100
                        }
                        interest3.save flush: true
                        def interest4 = new OpportunityProduct()
                        // 意向金
                        interest4.opportunity = newOpportunity
                        interest4.product = newOpportunity.productAccount
                        interest4.productInterestType = ProductInterestType.findByName("意向金")
                        def rate4 = arry[65]
                        //.substring(0, arry[30].length() - 1)
                        interest4.rate = Double.parseDouble(rate4)
                        interest4.save flush: true

                        p.println("6. 息费信息导入完成")
                        if (arry[37] && arry[37].charAt(0) == '"')
                        {
                            arry[37] = arry[37].substring(1, arry[37].length() - 1)
                        }
                        def flexFieldCategory1 = FlexFieldCategory.findByName("借款用途")
                        def opportunityFlexFieldCategory1 = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(newOpportunity, flexFieldCategory1)
                        if (opportunityFlexFieldCategory1 == null)
                        {
                            opportunityFlexFieldCategory1 = new OpportunityFlexFieldCategory()
                            opportunityFlexFieldCategory1.opportunity = newOpportunity
                            opportunityFlexFieldCategory1.flexFieldCategory = flexFieldCategory1
                            opportunityFlexFieldCategory1.save flush: true
                        }
                        def fields = OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory1)
                        //def fields = opportunityFlexFieldCategory1?.fields
                        if (fields)
                        {
                            fields?.each {
                                if (it?.name == "借款用途")
                                {
                                    if (arry[37])
                                    {
                                        it.value = arry[37]
                                    }
                                    else
                                    {
                                        it.value = arry[36]
                                    }
                                }
                            }
                        }
                        else
                        {
                            def flexField = new OpportunityFlexField()
                            flexField.category = opportunityFlexFieldCategory1
                            flexField.name = "借款用途"
                            flexField.description = "借款用途"
                            flexField.dataType = "String"
                            if (arry[37])
                            {
                                flexField.value = arry[37]
                            }
                            else
                            {
                                flexField.value = arry[36]
                            }
                            flexField.save flush: true
                        }
                        def flexField1 = new OpportunityFlexField()
                        flexField1.category = opportunityFlexFieldCategory1
                        flexField1.name = "用途类别"
                        flexField1.description = "用途类别"
                        flexField1.dataType = "String"
                        flexField1.value = arry[36]
                        flexField1.displayOrder = 1
                        flexField1.save flush: true

                        def flexFieldCategory2 = FlexFieldCategory.findByName("还款来源")
                        def opportunityFlexFieldCategory2 = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(newOpportunity, flexFieldCategory2)
                        if (opportunityFlexFieldCategory2 == null)
                        {
                            opportunityFlexFieldCategory2 = new OpportunityFlexFieldCategory()
                            opportunityFlexFieldCategory2.opportunity = newOpportunity
                            opportunityFlexFieldCategory2.flexFieldCategory = flexFieldCategory2
                            opportunityFlexFieldCategory2.save flush: true
                        }
                        fields = OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory2)
                        //fields = opportunityFlexFieldCategory2?.fields
                        if (fields)
                        {
                            fields?.each {
                                if (it?.name == "还款来源")
                                {
                                    it.value = arry[20]
                                }
                            }
                        }
                        else
                        {
                            def flexField2 = new OpportunityFlexField()
                            flexField2.category = opportunityFlexFieldCategory2
                            flexField2.name = "还款来源"
                            flexField2.description = "还款来源"
                            flexField2.dataType = "String"
                            flexField2.value = arry[20]
                            flexField2.save flush: true
                        }

                        def flexFieldCategory3 = FlexFieldCategory.findByName("资金渠道")
                        def opportunityFlexFieldCategory3 = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(newOpportunity, flexFieldCategory3)
                        if (opportunityFlexFieldCategory3 == null)
                        {
                            opportunityFlexFieldCategory3 = new OpportunityFlexFieldCategory()
                            opportunityFlexFieldCategory3.opportunity = newOpportunity
                            opportunityFlexFieldCategory3.flexFieldCategory = flexFieldCategory3
                            opportunityFlexFieldCategory3.save flush: true
                        }
                        fields = OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory3)
                        //fields = opportunityFlexFieldCategory2?.fields
                        if (fields)
                        {
                            fields?.each {
                                if (it?.name == "放款账号")
                                {
                                    it.value = arry[39]
                                }
                                if (it?.name == "放款通道")
                                {
                                    it.value = arry[41]
                                }
                            }
                        }
                        else
                        {
                            def flexField3 = new OpportunityFlexField()
                            flexField3.category = opportunityFlexFieldCategory3
                            flexField3.name = "放款通道"
                            flexField3.description = "放款通道"
                            flexField3.dataType = "String"
                            flexField3.value = arry[41]
                            flexField3.save flush: true
                            def flexField4 = new OpportunityFlexField()
                            flexField4.category = opportunityFlexFieldCategory3
                            flexField4.name = "放款账号"
                            flexField4.description = "放款账号"
                            flexField4.dataType = "String"
                            flexField4.value = arry[39]
                            flexField4.displayOrder = 1
                            flexField4.save flush: true
                        }
                        p.println("7. 弹性字段信息导入完成")

                        def contract = new Contract()
                        contract.serialNumber = arry[0]
                        contract.type = ContractType.findByName("借款合同")
                        contract.save flush: true
                        def opportunityContract = new OpportunityContract()
                        opportunityContract.opportunity = newOpportunity
                        opportunityContract.contract = contract
                        opportunityContract.save flush: true
                        p.println("8. 合同信息导入完成")

                        /*def result
                        def component = Component.findByName("生成还款计划")
                        if (component)
                        {
                            result = componentService.evaluate component, newOpportunity
                        }
                        if (result instanceof Exception)
                        {
                            p.println("还款计划生成失败！！！")
                            log.error "SystemComponent error!"
                        }

                        component = Component.findByName("生成交易记录")
                        if (component)
                        {
                            //p.println(component.type?.name)
                            result = componentService.evaluate component, newOpportunity
                            //p.println("22222")
                        }
                        if (result instanceof Exception)
                        {
                            p.println("交易记录生成失败！！！")
                            log.error "SystemComponent error!"
                        }
                        p.println("9. 还款计划和交易记录生成完成")*/
                        p.println("序号: " + index + ", " + "合同编号为" + arry[0] + "的在途订单导入成功，订单编号为: " + newOpportunity.serialNumber)
                    }
                    //else if (oldOpportunity.stage != OpportunityStage.findByCode("20"))
                    else
                    {
                        p.println("序号: " + index + ", " + "合同编号为" + arry[0] + "的在途订单需更新，订单编号为: " + oldOpportunity?.serialNumber)
                        if (oldOpportunity.externalId == null)
                        {
                            oldOpportunity.setExternalId(arry[0])
                        }
                        else if (oldOpportunity.externalId != arry[0])
                        {
                            p.println("请注意：此在途订单合同编号有误，已更新！！！")
                            oldOpportunity.setExternalId(arry[0])
                        }

                        def monthlyInterest = arry[21]
                        oldOpportunity.setMonthlyInterest(Double.parseDouble(monthlyInterest) * 100)
                        def serviceCharge = arry[22]
                        if (arry[63].indexOf("按月支付") >= 0)
                        {
                            oldOpportunity.setServiceCharge(Double.parseDouble(serviceCharge) * 100)
                        }
                        else
                        {
                            oldOpportunity.setServiceCharge(Double.parseDouble(arry[66]) * 100)
                        }
                        def dealRate = arry[24]
                        oldOpportunity.setDealRate(Double.parseDouble(dealRate) * 100)

                        def interestPaymentMethodName = arry[25]
                        if (arry[25] == "一次性收息")
                        {
                            interestPaymentMethodName = "一次性付息"
                        }
                        def interestPaymentMethod = InterestPaymentMethod.findByName(interestPaymentMethodName)
                        if (interestPaymentMethod)
                        {
                            oldOpportunity.setInterestPaymentMethod(interestPaymentMethod)
                        }

                        if (arry[64].indexOf("按月支付") >= 0)
                        {
                            oldOpportunity.commissionPaymentMethod = CommissionPaymentMethod.findByName("月月返")
                        }
                        else
                        {
                            oldOpportunity.commissionPaymentMethod = CommissionPaymentMethod.findByName("一次性返")
                        }

                        if (oldOpportunity.mortgageType == null)
                        {
                            if (arry[27] == "一押")
                            {
                                arry[27] = "一抵"
                            }
                            else
                            {
                                arry[27] = "二抵"
                            }
                            def mortgageType = MortgageType.findByName(arry[27])
                            if (mortgageType)
                            {
                                oldOpportunity.mortgageType = mortgageType
                            }
                        }

                        oldOpportunity.setActualLoanAmount(Double.parseDouble(arry[28]) / 10000)
                        oldOpportunity.setCommission(Double.parseDouble(arry[29]) / 10000)
                        def commissionRate = arry[30]
                        //.substring(0, arry[30].length() - 1)
                        if (arry[64].indexOf("按月支付") >= 0)
                        {
                            oldOpportunity.setCommissionRate(Double.parseDouble(commissionRate) * 100)
                        }
                        else
                        {
                            oldOpportunity.setCommissionRate(Double.parseDouble(arry[29]) / Double.parseDouble(arry[28]) * 100)
                        }
                        oldOpportunity.setAdvancePayment(Double.parseDouble(arry[65]))

                        if (oldOpportunity.product == null)
                        {
                            if (!arry[40])
                            {
                                arry[40] = "速E贷"
                            }
                            def product = Product.findByName(arry[40])
                            if (product)
                            {
                                oldOpportunity.product = product
                            }
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
                        oldOpportunity.setActualLendingDate(sdf.parse(arry[44]))
                        oldOpportunity.setActualLoanDuration(Integer.parseInt(arry[46]))
                        Date externalModifiedDate = new java.util.Date()
                        oldOpportunity.setExternalModifiedDate(externalModifiedDate)
                        p.println("1. 订单贷款信息更新完成")

                        /*def bankAccounts = OpportunityBankAccount.findAllByOpportunity(oldOpportunity)
                        //def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
                        if (bankAccounts.size() < 1)
                        {
                            def bankAccount = new BankAccount()
                            bankAccount.name = arry[48]
                            bankAccount.numberOfAccount = arry[49].substring(0, arry[49].length() - 1)

                            def opportunityContacts = OpportunityContact.findAllByOpportunity(oldOpportunity)
                            opportunityContacts?.each {
                                if (it?.contact?.fullName == arry[48])
                                {
                                    bankAccount.cellphone = it?.contact?.cellphone
                                    bankAccount.certificateType = it?.contact?.identityType
                                    bankAccount.numberOfCertificate = it?.contact?.idNumber
                                }
                            }
                            bankAccount.paymentChannel = PaymentChannel.findByName("广银联")
                            if (arry[50])
                            {
                                bankAccount.bankBranch = arry[50]
                                def bankNameIndex = arry[50].indexOf("银行")
                                def bankName = arry[50].substring(0, bankNameIndex + 2)
                                bankAccount.bank = Bank.findByName(bankName)
                            }
                            bankAccount.save flush: true
                            def bankAccount1 = new OpportunityBankAccount()
                            bankAccount1.opportunity = oldOpportunity
                            bankAccount1.bankAccount = bankAccount
                            bankAccount1.type = OpportunityBankAccountType.findByName("还款账号")
                            bankAccount1.save flush: true
                            def bankAccount2 = new OpportunityBankAccount()
                            bankAccount2.opportunity = oldOpportunity
                            bankAccount2.bankAccount = bankAccount
                            bankAccount2.type = OpportunityBankAccountType.findByName("收款账号")
                            bankAccount2.save flush: true
                        }
                        else if (bankAccounts.size() == 1)
                        {
                            def bankAccount = new OpportunityBankAccount()
                            bankAccount.opportunity = oldOpportunity
                            bankAccount.bankAccount = bankAccounts[0].bankAccount
                            if (bankAccounts[0].type.name == "收款账号")
                            {
                                bankAccount.type = OpportunityBankAccountType.findByName("还款账号")
                            }
                            else
                            {
                                bankAccount.type = OpportunityBankAccountType.findByName("收款账号")
                            }
                            bankAccount.save flush: true
                        }
                        p.println("2. 账户信息更新完成")*/

                        def calendar = arry[44].split("/")
                        def day = Double.parseDouble(calendar[2])
                        def dayFirst = Double.parseDouble(arry[62])
                        def firstPayOfMonthes = 0
                        if (dayFirst > 0)
                        {
                            if (day < 15)
                            {
                                firstPayOfMonthes = (day + dayFirst - 20) / 30 + 1
                            }
                            else if (day >= 15 && day < 20)
                            {
                                firstPayOfMonthes = (day + dayFirst - 50) / 30 + 2
                            }
                            else
                            {
                                firstPayOfMonthes = (day + dayFirst - 50) / 30 + 1
                            }
                        }
                        if (oldOpportunity.monthOfAdvancePaymentOfInterest < 0.1 && firstPayOfMonthes > 0)
                        {
                            oldOpportunity.setMonthOfAdvancePaymentOfInterest(firstPayOfMonthes)
                        }

                        def interests = OpportunityProduct.findAllByOpportunity(oldOpportunity)
                        def baseRateExist = false
                        def serviceRateExist = false
                        def commissionRateExist = false
                        def advancePaymentExist = false
                        if (interests)
                        {
                            interests?.each {
                                if (it.productInterestType == ProductInterestType.findByName("基本费率"))
                                {
                                    baseRateExist = true
                                    def rate1 = arry[21]
                                    it.rate = Double.parseDouble(rate1) * 100
                                    it.monthes = oldOpportunity.actualLoanDuration
                                    if (arry[25] != "一次性收息")
                                    {
                                        it.installments = true
                                        if (it.firstPayMonthes == 0 && firstPayOfMonthes > 0)
                                        {
                                            it.firstPayMonthes = firstPayOfMonthes
                                        }
                                    }
                                    else
                                    {
                                        it.rate = it.rate * it.monthes
                                    }
                                }
                                if (it.productInterestType == ProductInterestType.findByName("服务费费率"))
                                {
                                    serviceRateExist = true
                                    def rate2 = arry[22]
                                    it.rate = Double.parseDouble(rate2) * 100
                                    it.monthes = oldOpportunity.actualLoanDuration
                                    if (arry[63].indexOf("按月支付") > -1)
                                    {
                                        it.installments = true
                                        if (it.firstPayMonthes == 0 && firstPayOfMonthes > 0)
                                        {
                                            it.firstPayMonthes = firstPayOfMonthes
                                        }
                                    }
                                    else
                                    {
                                        it.rate = Double.parseDouble(arry[66]) * 100
                                    }
                                }
                                if (it.productInterestType == ProductInterestType.findByName("渠道返费费率"))
                                {
                                    commissionRateExist = true
                                    def rate3 = arry[30]
                                    it.rate = Double.parseDouble(rate3) * 100
                                    it.monthes = oldOpportunity.actualLoanDuration
                                    if (arry[64].indexOf("按月支付") > -1)
                                    {
                                        it.installments = true
                                        if (it.firstPayMonthes == 0 && firstPayMonthes > 0)
                                        {
                                            it.firstPayMonthes = firstPayOfMonthes
                                        }
                                    }
                                    else
                                    {
                                        it.rate = Double.parseDouble(arry[29]) / Double.parseDouble(arry[28]) * 100
                                    }
                                }
                                if (it.productInterestType == ProductInterestType.findByName("意向金"))
                                {
                                    commissionRateExist = true
                                    def rate3 = arry[65]
                                    it.rate = Double.parseDouble(rate3)
                                }
                            }
                        }

                        if (!baseRateExist)
                        {
                            def interest1 = new OpportunityProduct()
                            // 月基础利率
                            interest1.opportunity = oldOpportunity
                            interest1.product = oldOpportunity.productAccount
                            interest1.productInterestType = ProductInterestType.findByName("基本费率")
                            def rate1 = arry[21]
                            interest1.rate = Double.parseDouble(rate1) * 100
                            interest1.monthes = oldOpportunity.actualLoanDuration
                            if (arry[25] != "一次性收息")
                            {
                                interest1.installments = true
                                interest1.firstPayMonthes = firstPayOfMonthes
                            }
                            else
                            {
                                interest1.rate = interest1.rate * interest1.monthes
                            }
                            interest1.save flush: true
                        }
                        if (!serviceRateExist)
                        {
                            def interest2 = new OpportunityProduct()
                            // 月服务费率
                            interest2.opportunity = oldOpportunity
                            interest2.product = oldOpportunity.productAccount
                            interest2.productInterestType = ProductInterestType.findByName("服务费费率")
                            def rate2 = arry[22]
                            interest2.rate = Double.parseDouble(rate2) * 100
                            interest2.monthes = oldOpportunity.actualLoanDuration
                            if (arry[63].indexOf("按月支付") > -1)
                            {
                                interest2.installments = true
                                interest2.firstPayMonthes = firstPayOfMonthes
                            }
                            else
                            {
                                interest2.rate = Double.parseDouble(arry[66]) * 100
                            }
                            interest2.save flush: true
                        }
                        if (!commissionRateExist)
                        {
                            def interest3 = new OpportunityProduct()
                            // 代收渠道月利率
                            interest3.opportunity = oldOpportunity
                            interest3.product = oldOpportunity.productAccount
                            interest3.productInterestType = ProductInterestType.findByName("渠道返费费率")
                            def rate3 = arry[30]
                            interest3.rate = Double.parseDouble(rate3) * 100
                            interest3.monthes = oldOpportunity.actualLoanDuration
                            if (arry[64].indexOf("按月支付") > -1)
                            {
                                interest3.installments = true
                                interest3.firstPayMonthes = firstPayOfMonthes
                            }
                            else
                            {
                                interest3.rate = Double.parseDouble(arry[29]) / Double.parseDouble(arry[28]) * 100
                            }
                            interest3.save flush: true
                        }
                        if (!commissionRateExist)
                        {
                            def interest4 = new OpportunityProduct()
                            // 意向金
                            interest4.opportunity = oldOpportunity
                            interest4.product = oldOpportunity.productAccount
                            interest4.productInterestType = ProductInterestType.findByName("意向金")
                            def rate4 = arry[65]
                            interest4.rate = Double.parseDouble(rate4)
                            interest4.save flush: true
                        }
                        p.println("3. 息费信息更新完成")

                        def flexFieldCategory = FlexFieldCategory.findByName("资金渠道")
                        def opportunityFlexFieldCategory = OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(oldOpportunity, flexFieldCategory)
                        if (opportunityFlexFieldCategory == null)
                        {
                            opportunityFlexFieldCategory = new OpportunityFlexFieldCategory()
                            opportunityFlexFieldCategory.opportunity = oldOpportunity
                            opportunityFlexFieldCategory.flexFieldCategory = flexFieldCategory
                            opportunityFlexFieldCategory.save flush: true
                        }
                        def fields = OpportunityFlexField.findAllByCategory(opportunityFlexFieldCategory)
                        //fields = opportunityFlexFieldCategory2?.fields
                        if (fields)
                        {
                            fields?.each {
                                if (it?.name == "放款账号")
                                {
                                    it.value = arry[39]
                                }
                                if (it?.name == "放款通道")
                                {
                                    it.value = arry[41]
                                }
                            }
                        }
                        else
                        {
                            def flexField1 = new OpportunityFlexField()
                            flexField1.category = opportunityFlexFieldCategory
                            flexField1.name = "放款通道"
                            flexField1.description = "放款通道"
                            flexField1.dataType = "String"
                            flexField1.value = arry[41]
                            flexField1.save flush: true
                            def flexField2 = new OpportunityFlexField()
                            flexField2.category = opportunityFlexFieldCategory
                            flexField2.name = "放款账号"
                            flexField2.description = "放款账号"
                            flexField2.dataType = "String"
                            flexField2.value = arry[39]
                            flexField2.displayOrder = 1
                            flexField2.save flush: true
                        }
                        p.println("4. 资金渠道信息更新完成")

                        def collaterals = Collateral.findAllByOpportunity(oldOpportunity)
                        if (arry[61])
                        {
                            p.println("已有房产数: " + collaterals.size())
                        }
                        if (arry[61] && collaterals.size() == 1)
                        {
                            def district = arry[13].split(";")
                            def addressDetails = arry[14].split(";")
                            def propertySerialNumber = arry[15].split(";")
                            def area = arry[16].split(";")
                            def assetType = arry[17].split(";")
                            def houseType = arry[18].split(";")

                            for (
                                def i = 0;
                                    i < addressDetails.length;
                                    i++)
                            {
                                if (collaterals[0]?.area == Double.parseDouble(area[i]))
                                {
                                    continue
                                }

                                def collateral = new Collateral()
                                collateral.opportunity = oldOpportunity
                                //collateral.externalId = externalId
                                collateral.city = city
                                if (district[i])
                                {
                                    collateral.district = district[i]
                                }

                                def address = addressDetails[i].split("、")
                                if (address.length > 0)
                                {
                                    collateral.projectName = address[address.length - 1]
                                    collateral.address = address[address.length - 1]
                                }
                                collateral.orientation = "东"
                                if (propertySerialNumber[i])
                                {
                                    collateral.propertySerialNumber = propertySerialNumber[i]
                                }
                                if (area[i])
                                {
                                    collateral.area = Double.parseDouble(area[i])
                                }
                                else
                                {
                                    collateral.area = 0.0
                                }
                                collateral.building = 0
                                collateral.unit = 1
                                collateral.roomNumber = 0
                                collateral.floor = 0
                                collateral.totalFloor = 0

                                if (assetType[i])
                                {
                                    collateral.assetType = assetType[i]
                                }
                                else
                                {
                                    collateral.assetType = '其他'
                                }
                                if (houseType[i])
                                {
                                    collateral.houseType = houseType[i]
                                }
                                else
                                {
                                    collateral.houseType = '其他'
                                }
                                collateral.specialFactors = '无'
                                collateral.status = 'Pending'
                                collateral.requestStartTime = null
                                collateral.requestEndTime = null

                                def totalPrice = Double.parseDouble(arry[31]) / addressDetails.length
                                collateral.totalPrice = totalPrice / 10000
                                if (collateral.area > 0)
                                {
                                    collateral.unitPrice = totalPrice / collateral.area
                                }

                                def firstMortgageRate = Double.parseDouble(arry[34])
                                //.substring(0, arry[34].length() - 1))
                                if (firstMortgageRate > 0.0)
                                {
                                    //if (arry[32])
                                    //{
                                    //    collateral.firstMortgageAmount = Double.parseDouble(arry[32]) / addressDetails.length / 10000
                                    //}
                                    //else
                                    //{
                                    double firstMortgageAmount = collateral.totalPrice * firstMortgageRate
                                    collateral.firstMortgageAmount = (double) (Math.round(firstMortgageAmount * 10) / 10)
                                    //}
                                    def secondMortgageRate = Double.parseDouble(arry[33])
                                    double secondMortgageAmount = collateral.totalPrice * secondMortgageRate
                                    collateral.secondMortgageAmount = (double) (Math.round(secondMortgageAmount * 10) / 10)
                                    collateral.typeOfFirstMortgage = TypeOfFirstMortgage.findByName("银行类")
                                }
                                else
                                {
                                    double firstMortgageAmount = collateral.totalPrice * Double.parseDouble(arry[33])
                                    collateral.firstMortgageAmount = (double) (Math.round(firstMortgageAmount * 10) / 10)
                                    collateral.typeOfFirstMortgage = TypeOfFirstMortgage.findByName("非银行类")
                                }
                                def loanToValue = arry[35]
                                //.substring(0, arry[35].length() - 1)
                                collateral.loanToValue = Double.parseDouble(loanToValue) * 100
                                collateral.mortgageType = oldOpportunity.mortgageType

                                if (collateral.validate())
                                {
                                    collateral.save flush: true
                                    p.println("此订单新增了一套房产")
                                }
                                else
                                {
                                    println collateral.errors
                                }
                            }
                        }
                        p.println("5. 房产信息更新完成")

                        def contracts = OpportunityContract.findAllByOpportunity(oldOpportunity)
                        if (contracts.size() < 1)
                        {
                            def contract = new Contract()
                            contract.serialNumber = oldOpportunity.externalId
                            contract.type = ContractType.findByName("借款合同")
                            contract.save flush: true
                            def opportunityContract = new OpportunityContract()
                            opportunityContract.opportunity = oldOpportunity
                            opportunityContract.contract = contract
                            opportunityContract.save flush: true
                        }
                        else
                        {
                            contracts?.each {
                                if (it?.contract?.type?.name == "借款合同")
                                {
                                    if (it?.contract?.serialNumber != oldOpportunity.externalId)
                                    {
                                        it?.contract?.serialNumber = oldOpportunity.externalId
                                    }
                                }
                            }
                        }
                        p.println("6. 合同信息更新完成")

                        //println oldOpportunity?.serialNumber + oldOpportunity?.fullName + oldOpportunity?.externalId
                        if (oldOpportunity.stage.name != "抵押品已入库")
                        {
                            oldOpportunity.stage = OpportunityStage.findByCode("20")
                            p.println("此订单阶段已修改为：" + oldOpportunity.stage.name)
                        }

                        /*def result
                        def component = Component.findByName("生成还款计划")
                        if (component)
                        {
                            result = componentService.evaluate component, oldOpportunity
                        }
                        if (result instanceof Exception)
                        {
                            p.println("还款计划生成失败！！！")
                            log.error "SystemComponent error!"
                        }

                        component = Component.findByName("生成交易记录")
                        if (component)
                        {
                            result = componentService.evaluate component, oldOpportunity
                        }
                        if (result instanceof Exception)
                        {
                            p.println("交易记录生成失败！！！")
                            log.error "SystemComponent error!"
                        }
                        p.println("7. 还款计划和交易记录生成完成") */
                        count3++
                        p.println("序号: " + index + ", " + "合同编号为" + arry[0] + "的在途订单已更新，订单编号为: " + oldOpportunity?.serialNumber)
                    }
                    //else
                    //{
                    //    p.println("序号: " + index + ", " + "合同编号为" + arry[0] + "的在途订单已存在，订单编号为: " + oldOpportunity?.serialNumber)
                    //}
                    //}
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
                p.println("导入完成，本次读取订单总数: " + count + ", 本次新生成订单数: " + count1 + ", 本次导入失败订单数: " + count2 + ", 本次更新订单数: " + count3)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        csvFile.errors.each {
            log.info it.toString()
        }
        if (!csvFile.hasErrors() && csvFile.save())
        {
            flash.message = "上传成功"
            /*redirect(action:show,id:documentInstance.id)*/
        }
        render(view: 'create', model: [documentInstance: csvFile])
    }

    @Transactional
    def uploadCashFlow()
    {
        def csvFile = new OpportunityCsvFile()
        def url = params.myFile
        log.info url.toString()

        def f = request.getFile('myFile')
        def file1 = new OpportunityCsvFile()
        file1.setFilename(f.originalFilename)
        def file1s = csvFile.find(file1)
        if (!f.empty && file1s == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            log.info webRootDir.toString()
            def csvDir = new File(webRootDir, "/upload/")
            csvDir.mkdirs()
            def fileSave = new File(csvDir, f.originalFilename)
            f.transferTo(fileSave)
            csvFile.filetype = '银行流水'
            csvFile.filename = f.originalFilename
            csvFile.filepath = webRootDir + "upload/" + f.originalFilename
            csvFile.save flush: true
            //read file and save datas
            //def br = new BufferedReader(new FileReader(fileSave))
            FileInputStream fr = new FileInputStream(fileSave);
            InputStreamReader isr = new InputStreamReader(fr, "GBK");
            BufferedReader br = new BufferedReader(isr);
            br.readLine()
            //def line = ""
            def line = null
            def index = 1
            def count = 0
            def preExternalId = null
            def opportunity = null
            def bills = null
            def preEndTime = null
            def period = 0
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "upload/" + csvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            java.util.Calendar calendar = java.util.Calendar.getInstance()
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

                    index++
                    def arry = line.split(",")
                    for (
                        def i = 0;
                            i < arry.length;
                            i++)
                    {
                        if (arry[i])
                        {
                            arry[i] = arry[i].trim()
                        }
                    }
                    p.println(line + "......" + arry.length)

                    if (arry[1] && arry[4])
                    {
                        if (arry[1] != preExternalId)
                        {
                            opportunity = Opportunity.findByExternalId(arry[1])
                            preExternalId = arry[1]
                            period = 0

                            bills = Bills.findByOpportunity(opportunity)
                            if (!bills)
                            {
                                def status = BillsStatus.findByName("测算")
                                bills = new Bills(opportunity: opportunity, status: status)
                                p.println("订单" + opportunity.serialNumber + "需要生成还款计划")
                                bills.save flash: true
                            }
                        }
                        def billsItem = new BillsItem()
                        billsItem.bills = bills

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
                        def principalPayMethod = opportunity?.productAccount?.principalPaymentMethod?.name
                        def firstPayOfMonthes = opportunity?.monthOfAdvancePaymentOfInterest?.intValue()
                        if (firstPayOfMonthes == 0)
                        {
                            firstPayOfMonthes = 1
                        }
                        if (arry[3] != preEndTime)
                        {
                            period++
                            preEndTime = arry[3]
                        }
                        billsItem.period = period - 1
                        billsItem.setStartTime(sdf.parse(arry[3]))

                        calendar.clear()
                        calendar.setTime(billsItem.startTime)
                        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
                        if (principalPayMethod == "等额本息")
                        {
                            calendar.add(java.util.Calendar.MONTH, 1)
                            calendar.set(java.util.Calendar.DAY_OF_MONTH, day - 1)
                            billsItem.setEndTime(calendar.getTime())
                        }
                        else
                        {
                            if (billsItem.period == 0)
                            {
                                if (day < 20)
                                {
                                    calendar.add(java.util.Calendar.MONTH, firstPayOfMonthes - 1)
                                }
                                else
                                {
                                    calendar.add(java.util.Calendar.MONTH, firstPayOfMonthes)
                                }
                                calendar.set(java.util.Calendar.DAY_OF_MONTH, 19)
                                billsItem.setEndTime(calendar.getTime())
                            }
                            else if (billsItem.period == (opportunity?.actualLoanDuration == 0 ? 1 : opportunity?.actualLoanDuration) - firstPayOfMonthes + 1)
                            {
                                if (day < 20)
                                {
                                    calendar.add(java.util.Calendar.MONTH, 1)
                                    calendar.set(java.util.Calendar.DAY_OF_MONTH, day - 1)
                                }
                                else
                                {
                                    calendar.set(java.util.Calendar.DAY_OF_MONTH, day - 1)
                                }
                                billsItem.setEndTime(calendar.getTime())
                            }
                            else
                            {
                                calendar.add(java.util.Calendar.MONTH, 1)
                                calendar.set(java.util.Calendar.DAY_OF_MONTH, 19)
                                billsItem.setEndTime(calendar.getTime())
                            }
                        }
                        //billsItem.setActualStartTime(sdf.parse(arry[3]))
                        billsItem.setActualEndTime(sdf.parse(arry[4]))
                        billsItem.setReceivable(Double.parseDouble(arry[5]))
                        billsItem.setReceipts(Double.parseDouble(arry[5]))

                        def credit = OpportunityBankAccount.findByOpportunityAndType(opportunity, OpportunityBankAccountType.findByName("还款账号"))?.bankAccount
                        def debit = OpportunityBankAccount.findByOpportunityAndType(opportunity, OpportunityBankAccountType.findByName("收款账号"))?.bankAccount
                        billsItem.setCredit(credit)
                        billsItem.setDebit(debit)

                        billsItem.setStatus("已结清账单")
                        if (arry[6].indexOf("本金") > -1)
                        {
                            billsItem.type = BillsItemType.findByName("本金")
                        }
                        else
                        {
                            billsItem.type = BillsItemType.findByName("基本费率")
                        }
                        billsItem.save flush: true
                        count++
                        p.println("序号: " + index + ", " + "订单" + opportunity.serialNumber + "在还款计划中增加一条第" + (period - 1) + "期的还款记录")
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
                p.println("导入完成，本次新生成还款记录数: " + count)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        csvFile.errors.each {
            log.info it.toString()
        }
        if (!csvFile.hasErrors() && csvFile.save())
        {
            flash.message = "上传成功"
            /*redirect(action:show,id:documentInstance.id)*/
        }
        render(view: 'create', model: [documentInstance: csvFile])
    }

    @Transactional
    def uploadChargeback()
    {
        def csvFile = new OpportunityCsvFile()
        def url = params.myFile
        log.info url.toString()
        def f = request.getFile('myFile')
        def file1 = new OpportunityCsvFile()
        file1.setFilename(f.originalFilename)
        def file1s = csvFile.find(file1)
        if (!f.empty && file1s == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            log.info webRootDir.toString()
            def csvDir = new File(webRootDir, "/upload/")
            csvDir.mkdirs()
            def fileSave = new File(csvDir, f.originalFilename)
            f.transferTo(fileSave)
            csvFile.filetype = '退单说明'
            csvFile.filename = f.originalFilename
            csvFile.filepath = webRootDir + "upload/" + f.originalFilename
            csvFile.save flush: true
            //read file and save datas
            //def br = new BufferedReader(new FileReader(fileSave))
            FileInputStream fr = new FileInputStream(fileSave)
            InputStreamReader isr = new InputStreamReader(fr, "GBK")
            BufferedReader br = new BufferedReader(isr)
            br.readLine()
            //def line = ""
            def line = null
            def chargeback = null
            def index = 1
            def count = 0
            def period = 0
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "upload/" + csvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            java.util.Calendar calendar = java.util.Calendar.getInstance()
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

                    index++
                    def arry = line.split(",")
                    for (
                        def i = 0;
                            i < arry.length;
                            i++)
                    {
                        if (arry[i])
                        {
                            arry[i] = arry[i].trim()
                        }
                    }
                    p.println(line + "......" + arry.length)

                    //退单说明流程
                    chargeback = new Chargeback()
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd") 
                    chargeback.date=sdf.parse(arry[0])
                    p.println("date....."+chargeback.date)

                    chargeback.serialNumber = arry[1]
                    p.println("serialNumber....."+chargeback.serialNumber)

                    chargeback.city = arry[2]
                    p.println("city....."+chargeback.city)

                    chargeback.liablePerson = arry[3]
                    p.println("liablePerson....."+chargeback.liablePerson)

                    chargeback.abutmentPerson = arry[4]
                    p.println("abutmentPerson....."+chargeback.abutmentPerson)

                    chargeback.detentionCredential = arry[5]
                    p.println("detentionCredential....."+chargeback.detentionCredential)

                    chargeback.loses = arry[6]
                    p.println("loses....."+chargeback.loses)

                    chargeback.vagues = arry[7]
                    p.println("vagues....."+chargeback.vagues)
                    
                    chargeback.defects = arry[8]
                    p.println("defects....." +chargeback.defects)

                    chargeback.contracts = arry[9]
                    p.println("contracts....." +chargeback.contracts)

                    chargeback.waifangs = arry[10]
                    p.println("waifangs....." +chargeback.waifangs)

                    chargeback.systems = arry[11]
                    p.println("systems....." +chargeback.systems)

                    chargeback.reason = arry[12]
                    p.println("reason....."+chargeback.reason)

                    // chargeback.serialNumber=arry[1]
                    // println chargeback.serialNumber + "sdjfhsdjhf"
                    chargeback.save flush: true
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
                p.println("导入完成，本次新生成退单说明记录数: " + count)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        csvFile.errors.each {
            log.info it.toString()
        }
        if (!csvFile.hasErrors() && csvFile.save())
        {
            flash.message = "上传成功"
            /*redirect(action:show,id:documentInstance.id)*/
        }
        render(view: 'create', model: [documentInstance: csvFile])

    }

    @Transactional
    def uploadPrincipalOverdue()
    {
        def csvFile = new OpportunityCsvFile()
        def url = params.myFile
        log.info url.toString()
        def f = request.getFile('myFile')
        def file1 = new OpportunityCsvFile()
        file1.setFilename(f.originalFilename)
        def file1s = csvFile.find(file1)
        if (!f.empty && file1s == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            log.info webRootDir.toString()
            def csvDir = new File(webRootDir, "/upload/")
            csvDir.mkdirs()
            def fileSave = new File(csvDir, f.originalFilename)
            f.transferTo(fileSave)
            csvFile.filetype = '本金逾期'
            csvFile.filename = f.originalFilename
            csvFile.filepath = webRootDir + "upload/" + f.originalFilename
            csvFile.save flush: true
            //read file and save datas
            //def br = new BufferedReader(new FileReader(fileSave))
            FileInputStream fr = new FileInputStream(fileSave)
            InputStreamReader isr = new InputStreamReader(fr, "GBK")
            BufferedReader br = new BufferedReader(isr)
            br.readLine()
            //def line = ""
            def line = null
            def principalOverdue = null
            def index = 1
            def count = 0
            def period = 0
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "upload/" + csvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            java.util.Calendar calendar = java.util.Calendar.getInstance()
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

                    index++
                    def arry = line.split(",")
                    for (
                        def i = 0;
                            i < arry.length;
                            i++)
                    {
                        if (arry[i])
                        {
                            arry[i] = arry[i].trim()
                        }
                    }
                    p.println(line + "......" + arry.length)

                    // 本金逾期流程
                    principalOverdue = new PrincipalOverdue()

                    //城市
                    principalOverdue.cityName = arry[0]
                    p.println("cityName....." +principalOverdue.cityName)

                    //合同编号
                    principalOverdue.contractNumber = arry[1]
                    p.println("contractNumber....." +principalOverdue.contractNumber)

                    //姓名
                    principalOverdue.name = arry[2]
                    p.println("name....." +principalOverdue.name)

                    //放款金额
                    if (arry[3].charAt(0) == '"')
                    {
                        arry[3] = arry[3].substring(1, arry[3].length() - 1)
                    }
                    if (arry[3].indexOf("，") >= 0)
                    {
                        arry[3] = arry[3].replace("，", "")
                    }
                    principalOverdue.loanAmount = arry[3]
                    p.println("loanAmount....." +principalOverdue.loanAmount)

                    //欠缴金额
                    if (arry[4].charAt(0) == '"')
                    {
                        arry[4] = arry[4].substring(1, arry[4].length() - 1)
                    }
                    if (arry[4].indexOf("，") >= 0)
                    {
                        arry[4] = arry[4].replace("，", "")
                    }
                    principalOverdue.arrearsAmount = arry[4]
                    p.println("arrearsAmount....." +principalOverdue.arrearsAmount)

                    //放款日期
                    //if (arry[5]!="") {
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd")
                        principalOverdue.loanDate = sdf1.parse(arry[5])
                        p.println("loanDate....." +principalOverdue.loanDate)
                    //}
                    
                    //应还本日期
                    //if (arry[6]!="") {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd")
                        principalOverdue.shouldPrincipleDate = sdf2.parse(arry[6])
                        p.println("shouldPrincipleDate....." +principalOverdue.shouldPrincipleDate)
                    //}
                    
                    //逾期开始日期
                    //if (arry[7]!="") {
                        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd")
                        principalOverdue.overdueStartDate = sdf3.parse(arry[7])
                        p.println("overdueStartDate....." +principalOverdue.overdueStartDate)   
                    //}

                    //统计日期
                    //if (arry[8]!="") {
                        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd")
                        principalOverdue.calculatedDate = sdf4.parse(arry[8])
                        p.println("calculatedDate....." +principalOverdue.calculatedDate) 
                    //}
                    
                    principalOverdue.save flush: true
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
                p.println("导入完成，本次新生成本金逾期记录数: " + count)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        csvFile.errors.each {
            log.info it.toString()
        }
        if (!csvFile.hasErrors() && csvFile.save())
        {
            flash.message = "上传成功"
            /*redirect(action:show,id:documentInstance.id)*/
        }
        render(view: 'create', model: [documentInstance: csvFile])

    }

    @Transactional
    def uploadInterestOverdue()
    {
        def csvFile = new OpportunityCsvFile()
        def url = params.myFile
        log.info url.toString()
        def f = request.getFile('myFile')
        def file1 = new OpportunityCsvFile()
        file1.setFilename(f.originalFilename)
        def file1s = csvFile.find(file1)
        if (!f.empty && file1s == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            log.info webRootDir.toString()
            def csvDir = new File(webRootDir, "/upload/")
            csvDir.mkdirs()
            def fileSave = new File(csvDir, f.originalFilename)
            f.transferTo(fileSave)
            csvFile.filetype = '利息逾期'
            csvFile.filename = f.originalFilename
            csvFile.filepath = webRootDir + "upload/" + f.originalFilename
            csvFile.save flush: true
            //read file and save datas
            //def br = new BufferedReader(new FileReader(fileSave))
            FileInputStream fr = new FileInputStream(fileSave)
            InputStreamReader isr = new InputStreamReader(fr, "GBK")
            BufferedReader br = new BufferedReader(isr)
            br.readLine()
            //def line = ""
            def line = null
            def interestOverdue = null
            def index = 1
            def count = 0
            def period = 0
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "upload/" + csvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            java.util.Calendar calendar = java.util.Calendar.getInstance()
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

                    index++
                    def arry = line.split(",")
                    for (
                        def i = 0;
                            i < arry.length;
                            i++)
                    {
                        if (arry[i])
                        {
                            arry[i] = arry[i].trim()
                        }
                    }
                    p.println(line + "......" + arry.length)

                    // 本金逾期流程
                    interestOverdue = new InterestOverdue()

                    //城市
                    interestOverdue.cityName = arry[0]
                    p.println("cityName....." +interestOverdue.cityName)

                    //合同编号
                    interestOverdue.contractNumber = arry[1]
                    p.println("contractNumber....." +interestOverdue.contractNumber)

                    //姓名
                    interestOverdue.name = arry[2]
                    p.println("name....." +interestOverdue.name)

                    //放款金额
                    if (arry[3].charAt(0) == '"')
                    {
                        arry[3] = arry[3].substring(1, arry[3].length() - 1)
                    }
                    if (arry[3].indexOf("，") >= 0)
                    {
                        arry[3] = arry[3].replace("，", "")
                    }
                    interestOverdue.loanAmount = arry[3]
                    p.println("loanAmount....." +interestOverdue.loanAmount)

                    //欠缴金额
                    if (arry[4].charAt(0) == '"')
                    {
                        arry[4] = arry[4].substring(1, arry[4].length() - 1)
                    }
                    if (arry[4].indexOf("，") >= 0)
                    {
                        arry[4] = arry[4].replace("，", "")
                    }
                    interestOverdue.arrearsAmount = arry[4]
                    p.println("arrearsAmount....." +interestOverdue.arrearsAmount)

                    //放款日期
                    //if (arry[5]!="") {
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd")
                        interestOverdue.loanDate = sdf1.parse(arry[5])
                        p.println("loanDate....." +interestOverdue.loanDate)
                    //}
                    
                    //应还本日期
                    //if (arry[6]!="") {
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd")
                        interestOverdue.shouldPrincipleDate = sdf2.parse(arry[6])
                        p.println("shouldPrincipleDate....." +interestOverdue.shouldPrincipleDate)
                    //}
                    
                    //逾期开始日期
                    //if (arry[7]!="") {
                        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd")
                        interestOverdue.overdueStartDate = sdf3.parse(arry[7])
                        p.println("overdueStartDate....." +interestOverdue.overdueStartDate)   
                    //}

                    //统计日期
                    //if (arry[8]!="") {
                        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy/MM/dd")
                        interestOverdue.calculatedDate = sdf4.parse(arry[8])
                        p.println("calculatedDate....." +interestOverdue.calculatedDate) 
                    //}
                    
                    interestOverdue.save flush: true
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
                p.println("导入完成，本次新生成利息逾期记录数: " + count)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        csvFile.errors.each {
            log.info it.toString()
        }
        if (!csvFile.hasErrors() && csvFile.save())
        {
            flash.message = "上传成功"
            /*redirect(action:show,id:documentInstance.id)*/
        }
        render(view: 'create', model: [documentInstance: csvFile])

    }

    @Transactional
    def uploadFinalRepaymentDate()
    {
        def csvFile = new OpportunityCsvFile()
        def url = params.myFile
        log.info url.toString()
        def f = request.getFile('myFile')
        def file1 = new OpportunityCsvFile()
        file1.setFilename(f.originalFilename)
        def file1s = csvFile.find(file1)
        if (!f.empty && file1s == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            log.info webRootDir.toString()
            def csvDir = new File(webRootDir, "/upload/")
            csvDir.mkdirs()
            def fileSave = new File(csvDir, f.originalFilename)
            f.transferTo(fileSave)
            csvFile.filetype = '最终还款'
            csvFile.filename = f.originalFilename
            csvFile.filepath = webRootDir + "upload/" + f.originalFilename
            csvFile.save flush: true
            //read file and save datas
            //def br = new BufferedReader(new FileReader(fileSave))
            FileInputStream fr = new FileInputStream(fileSave)
            InputStreamReader isr = new InputStreamReader(fr, "GBK")
            BufferedReader br = new BufferedReader(isr)
            br.readLine()
            //def line = ""
            def line = null
            def index = 1
            def count = 0
            def period = 0
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "upload/" + csvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            java.util.Calendar calendar = java.util.Calendar.getInstance()
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

                    index++
                    def arry = line.split(",")
                    for (
                        def i = 0;
                            i < arry.length;
                            i++)
                    {
                        if (arry[i])
                        {
                            arry[i] = arry[i].trim()
                        }
                    }
                    p.println(line + "......" + arry.length)

                    // 规范化城市名称
                    def cityName = arry[0]

                    if (arry[0].indexOf("-机构") >= 0)
                    {
                        cityName = arry[0].substring(0, arry[0].length() - 4)
                    }
                    else if (arry[0].indexOf("-机构渠道") >= 0) {
                        cityName = arry[0].substring(0, arry[0].length() - 6)
                    }

                    p.println("cityName....." +cityName)

                    def city = City.findByName(cityName)

                    // 规范化借款人姓名
                    
                    if (arry[4].charAt(0) == '"')
                    {
                        arry[4] = arry[4].substring(1, arry[4].length() - 1)
                    }

                    def fullName = arry[4].split("、")

                    for (
                        def i = 0;
                            i < fullName.length;
                            i++)
                    {
                        if (fullName[i])
                        {
                            fullName[i] = fullName[i].trim()
                            if (fullName[i].indexOf("（转单）") >= 0)
                            {
                                fullName[i] = fullName[i].replaceAll("（转单）", "")
                            }
                            else if (fullName[i].indexOf("1") >= 0)
                            {
                                fullName[i] = fullName[i].replaceAll("1", "")
                            }
                            else if (fullName[i].indexOf("2") >= 0)
                            {
                                fullName[i] = fullName[i].replaceAll("2", "")
                            }
                        }
                    }
                    p.println("fullName" +fullName)   
                    
                    //放款金额
                    if (arry[5].charAt(0) == '"')
                    {
                        arry[5] = arry[5].substring(1, arry[5].length() - 1)
                        p.println("arry[5].substring....." +arry[5])
                    }

                    if (arry[5].indexOf("，") >= 0)
                    {
                        arry[5] = arry[5].replace("，", "")
                        p.println("arry[5].replace....." +arry[5])
                    }

                    // if(arry[5]!=""){
                        def actualLoanAmount = Double.parseDouble(arry[5]) / 10000
                    // }
                    // if(arry[5]==""){
                    //     def actualLoanAmount = 0
                    // }
                    
                    p.println("actualLoanAmount....." +actualLoanAmount)

                    def opportunity = null
                    for (
                        def i = 0;
                            i < fullName.length;
                            i++)
                    {
                        def opportunities = Opportunity.findAllByFullNameAndActualLoanAmount(fullName[i], actualLoanAmount)
                        p.println("opportunities....." +opportunities.size()) 
                        if (opportunities.size() > 0)
                        {
                            for (
                                def j = 0;
                                    j < opportunities.size();
                                    j++)
                            {
                                def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunities[j]?.id} order by id asc")
                                if (collaterals.size() > 0)
                                {
                                    def ocity = collaterals[0].city?.name
                                    p.println("城市：" + ocity)
                                    if (ocity != cityName)
                                    {
                                        continue
                                    }
                                }
                                else
                                {
                                    continue
                                }

                                //if (it?.stage == OpportunityStage.findByCode("08") || it?.stage == OpportunityStage.findByCode("21") || !nameEqual)
                                //{
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
                                Date tempTime1 = sdf.parse(arry[16])
                                p.println("tempTime1....." +tempTime1)
                                Date tempTime2 = opportunities[j].actualLendingDate
                                p.println("tempTime2....." +tempTime2)
                                if (tempTime2)
                                {
                                    def tempDuration = Math.abs(tempTime1.getTime() - tempTime2.getTime()) / (24.0 * 3600 * 1000)
                                    p.println("放款日期差: " + tempDuration)
                                    if (tempDuration <= 1.0)
                                    {
                                        opportunity = opportunities[j]
                                        break
                                    }
                                }
                                //}
                            }
                        }
                    }
                    
                    if (opportunity == null)
                    {
                        p.println("找不到订单，模式为线下。")
                    }
                    else
                    {
                        p.println("找到订单，编号为："+opportunity.serialNumber)
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
                        Date finalDate = sdf.parse(arry[18])
                        opportunity.finalRepaymentDate = finalDate
                        p.println("finalRepaymentDate....."+opportunity.finalRepaymentDate)

                        // opportunity.save flush: true
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
                p.println("导入完成，本次新生成结清记录数: " + count)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        csvFile.errors.each {
            log.info it.toString()
        }
        if (!csvFile.hasErrors() && csvFile.save())
        {
            flash.message = "上传成功"
            /*redirect(action:show,id:documentInstance.id)*/
        }
        render(view: 'create', model: [documentInstance: csvFile])

    }

    @Transactional
    def save(OpportunityCsvFile opportunityCsvFile)
    {
        if (opportunityCsvFile == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityCsvFile.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityCsvFile.errors, view: 'create'
            return
        }

        opportunityCsvFile.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile'), opportunityCsvFile.id])
                redirect opportunityCsvFile
            }
            '*' { respond opportunityCsvFile, [status: CREATED] }
        }
    }

    def edit(OpportunityCsvFile opportunityCsvFile)
    {
        respond opportunityCsvFile
    }

    @Transactional
    def update(OpportunityCsvFile opportunityCsvFile)
    {
        if (opportunityCsvFile == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (opportunityCsvFile.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond opportunityCsvFile.errors, view: 'edit'
            return
        }

        opportunityCsvFile.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile'), opportunityCsvFile.id])
                redirect opportunityCsvFile
            }
            '*' { respond opportunityCsvFile, [status: OK] }
        }
    }

    @Transactional
    def delete(OpportunityCsvFile opportunityCsvFile)
    {

        if (opportunityCsvFile == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        opportunityCsvFile.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile'), opportunityCsvFile.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
