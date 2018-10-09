package com.next

import grails.converters.JSON
import grails.transaction.Transactional

import java.security.Timestamp
import java.text.SimpleDateFormat

@Transactional
class JieqingHFHService {
    OpportunityService opportunityService
    BillsService billsService
    def initJieQingOpportunity(Opportunity parentOpportunity)
    {
        print "开始初始化订单"
        def opportunity = new Opportunity(parent: parentOpportunity)
        opportunity.user =  opportunity?.parent?.user
        opportunity.account =  opportunity?.parent?.account
        opportunity.type  =  OpportunityType.findByName("抵押贷款结清")
        opportunity.actuaRepaymentDate = parentOpportunity.bills[0].endTime
        opportunity.contact = opportunity?.parent?.contact
        opportunity.lender = opportunity?.parent?.lender
        opportunity.fullName = opportunity?.parent?.fullName
        opportunity.idNumber = opportunity?.parent?.idNumber
        opportunity.maritalStatus = opportunity?.parent?.maritalStatus
        opportunity.product = opportunity?.parent?.product
        opportunity.interestPaymentMethod = opportunity?.parent?.interestPaymentMethod
        opportunity.monthOfAdvancePaymentOfInterest = opportunity?.parent?.monthOfAdvancePaymentOfInterest
        opportunity.actualAmountOfCredit = opportunity?.parent?.actualAmountOfCredit
        opportunity.actualLoanDuration = opportunity?.parent?.actualLoanDuration
        opportunity.save flush: true
        print "保存基本信息"
        opportunityService.initOpportunity(opportunity)
        print "初始化成功"
        def opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? order by executionSequence ASC", [opportunity?.id])
        opportunity.stage = opportunityFlow?.stage
        opportunity.save flush: true
        //房产(去价格)、联系人、附件、下户、账户、合同、息费
        def parent = opportunity?.parent
        println "collaterals"
        def collaterals = Collateral.findAllByOpportunity(parent)
        collaterals?.each {
            def collateral = new Collateral()
            collateral.opportunity = opportunity
            collateral.assetType = it?.assetType
            collateral.projectName = it?.projectName
            collateral.city = it?.city
            collateral.district = it?.district
            collateral.address = it?.address
            collateral.floor = it?.floor
            collateral.orientation = it?.orientation
            collateral.area = it?.area
            collateral.building = it?.building
            collateral.unit = it?.unit
            collateral.totalFloor = it?.totalFloor
            collateral.roomNumber = it?.roomNumber
            collateral.houseType = it?.houseType
            collateral.specialFactors = it?.specialFactors
            collateral.reasonOfPriceAdjustment = it?.reasonOfPriceAdjustment
            collateral.appliedTotalPrice = it?.appliedTotalPrice
            collateral.requestStartTime = it?.requestStartTime
            collateral.requestEndTime = it?.requestEndTime
            collateral.projectType = it?.projectType
            collateral.buildTime = it?.buildTime
            collateral.loanToValue = it?.loanToValue
            collateral.propertySerialNumber = it?.propertySerialNumber
            collateral.typeOfFirstMortgage = it?.typeOfFirstMortgage
            collateral.firstMortgageAmount = it?.firstMortgageAmount
            collateral.secondMortgageAmount = it?.secondMortgageAmount
            collateral.mortgageType = it?.mortgageType
            collateral.region = it?.region
            collateral.atticArea = it?.atticArea
            collateral.status = 'Pending'
            if (collateral.validate())
            {
                collateral.save flush: true
            }
            else
            {
                log.info collateral.errors
            }
        }
        def opportunityContacts = OpportunityContact.findAllByOpportunity(parent)
        opportunityContacts?.each {
            def opportunityContact = new OpportunityContact()
            opportunityContact.opportunity = opportunity
            opportunityContact.type = it?.type
            opportunityContact.contact = it?.contact
            opportunityContact.connectedContact = it?.connectedContact
            opportunityContact.connectedType = it?.connectedType
            if (opportunityContact.validate())
            {
                opportunityContact.save flush: true
            }
            else
            {
                log.info opportunityContact.errors
            }
        }
        def attachments = Attachments.findAllByOpportunity(parent)
        attachments?.each {
            def attachment = new Attachments()
            attachment.opportunity = opportunity
            attachment.type = it?.type
            attachment.fileName = it?.fileName
            attachment.description = it?.description
            attachment.displayOrder = it?.displayOrder
            attachment.activity = it?.activity
            attachment.contact = it?.contact
            if (attachment.validate())
            {
                attachment.save flush: true
            }
            else
            {
                log.info attachment.errors
            }
        }
        println "activities"
        def activities = Activity.findAllByOpportunity(parent)
        activities?.each {
            def activity = new Activity()
            activity.opportunity = opportunity
            activity.startTime = it?.startTime
            activity.endTime = it?.endTime
            activity.actualStartTime = it?.actualStartTime
            activity.actualEndTime = it?.actualEndTime
            activity.description = it?.description
            activity.type = it?.type
            activity.subtype = it?.subtype
            activity.status = it?.status
            activity.longitude = it?.longitude
            activity.latitude = it?.latitude
            activity.city = it?.city
            activity.address = it?.address
            activity.name = it?.name
            activity.user = it?.user
            activity.contact = it?.contact
            activity.assignedTo = it?.assignedTo
            activity.parent = it?.parent
            if (activity.validate())
            {
                activity.save flush: true
            }
            else
            {
                log.info activity.errors
            }
        }
        def opportunityBankAccounts = OpportunityBankAccount.findAllByOpportunity(parent)
        opportunityBankAccounts?.each {
            def opportunityBankAccount = new OpportunityBankAccount()
            opportunityBankAccount.opportunity = opportunity
            opportunityBankAccount.type = it?.type
            opportunityBankAccount.bankAccount = it?.bankAccount
            if (opportunityBankAccount.validate())
            {
                opportunityBankAccount.save flush: true
            }
            else
            {
                log.info opportunityBankAccount.errors
            }
        }
        def opportunityContracts = OpportunityContract.findAllByOpportunity(parent)
        opportunityContracts?.each {
            def opportunityContract = new OpportunityContract()
            opportunityContract.opportunity = opportunity
            opportunityContract.contract = it?.contract
            if (opportunityContract.validate())
            {
                opportunityContract.save flush: true
            }
            else
            {
                log.info opportunityContract.errors
            }
        }
        def opportunityProducts = OpportunityProduct.findAllByOpportunity(parent)
        opportunityProducts?.each {
            def opportunityProduct = new OpportunityProduct()
            opportunityProduct.opportunity = opportunity
            opportunityProduct.productInterestType = it?.productInterestType
            opportunityProduct.fixedRate = it?.fixedRate
            opportunityProduct.installments = it?.installments
            opportunityProduct.rate = it?.rate
            opportunityProduct.monthes = it?.monthes
            opportunityProduct.firstPayMonthes = it?.firstPayMonthes
            opportunityProduct.product = it?.product
            opportunityProduct.createBy = it?.createBy
            opportunityProduct.modifyBy = it?.modifyBy
            if (opportunityProduct.validate())
            {
                opportunityProduct.save flush: true
            }
            else
            {
                log.info opportunityProduct.errors
            }
        }

        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(parent)
        opportunityFlexFieldCategorys?.each {
            def opportunityFlexFieldCategory = new OpportunityFlexFieldCategory()
            opportunityFlexFieldCategory.opportunity = opportunity
            opportunityFlexFieldCategory.flexFieldCategory = it?.flexFieldCategory
            opportunityFlexFieldCategory.save flush: true

            it?.fields?.each {
                def opportunityflexField = new OpportunityFlexField()
                opportunityflexField.category = opportunityFlexFieldCategory
                opportunityflexField.name = it?.name
                opportunityflexField.description = it?.description
                opportunityflexField.dataType = it?.dataType
                opportunityflexField.defaultValue = it?.defaultValue
                opportunityflexField.value = it?.value
                opportunityflexField.displayOrder = it?.displayOrder
                opportunityflexField.valueConstraints = it?.valueConstraints
                opportunityflexField.save flush: true

                it?.values?.each {
                    def opportunityFlexFieldValue = new OpportunityFlexFieldValue()
                    opportunityFlexFieldValue.field = opportunityflexField
                    opportunityFlexFieldValue.value = it?.value
                    opportunityFlexFieldValue.displayOrder = it?.displayOrder
                    opportunityFlexFieldValue.save flush: true
                }
            }
        }
    println "opportunity+"+opportunity.serialNumber+"结清订单创建成功"
    }

    def sendDataTohuo() {
        /*InetAddress address = InetAddress.getByName("s53.zhongjiaxin.com");
        if(address.getHostAddress()!="10.0.8.149" && address.getHostAddress()!="10.0.8.148")
        {
                return false
        }*/
        try{
            def startTime = new Date()
            println "———————————————结清开始时间—"+startTime+"——————————————"
            /*java.util.Calendar calendar1 = java.util.Calendar.getInstance()
            calendar1.clear()
            calendar1.setTime(new java.util.Date())
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,0)
            calendar1.set(java.util.Calendar.MINUTE,0)
            calendar1.set(java.util.Calendar.SECOND,0)
            def today = calendar1.getTime()
            println today.format("yyyy-MM-dd")
            def dayOfWeek = calendar1.get(java.util.Calendar.DAY_OF_WEEK)
            if (dayOfWeek!=7&&dayOfWeek!=1) {
                def bills
                if (dayOfWeek == 2) {
                    bills = com.next.Bills.executeQuery("select id from Bills where CONVERT(varchar(100), endTime, 120) like '${today.format("yyyy-MM-dd")}%' or CONVERT(varchar(100), endTime, 120) like '${(today - 1).format("yyyy-MM-dd")}%' or CONVERT(varchar(100), endTime, 120) like '${(today - 2).format("yyyy-MM-dd")}%'")
                } else {
                    bills = com.next.Bills.executeQuery("select id from Bills where CONVERT(varchar(100), endTime, 120) like '${today.format("yyyy-MM-dd")}%'")
                }
                println '今天的billsId有：' + bills
                //每日执行
                bills.each {
                    print "开始执行" + it
                    def opportunity = com.next.Bills.findById(it)?.opportunity
                    print "当前订单号" + opportunity.serialNumber
                    def o1 = com.next.Opportunity.findAllByParentAndType(opportunity, com.next.OpportunityType.findByName("抵押贷款结清"))
                    if (!o1 && opportunity?.isTest == false && opportunity?.status == 'Pending') {
                        initJieQingOpportunity(opportunity)
                    }

                }
                *//*def jqOpportunitys = Opportunity.executeQuery("select id from Opportunity where type.name = '抵押贷款结清' and  CONVERT(varchar(100), actuaRepaymentDate, 120) like '${today.format("yyyy-MM-dd")}%'")
                jqOpportunitys.each {
                    def opportunity = it?.parent
                    if (opportunity&&opportunity?.isTest == false&&opportunity?.status=="Pending"){
                        def items = BillsItem.findAllByBillsAndStatusNotEqualAndTypeNotInList(Bills.findById(4228),"已收",BillsItemType.findAllByNameInList(["本金","基本费率","服务费费率","渠道返费费率","二抵加收费率","信用调整","郊县","大头小尾","老人房（65周岁以上）","老龄房（房龄35年以上）","非7成区域","大额（单套大于1200万）"]))
                        items.each {
                            it.endTime = opportunity?.actuaRepaymentDate
                            it.payTime = opportunity?.actuaRepaymentDate
                            it.save()
                        }
                    }
                }*//*
            }*/
           /* //在途月结
            def day = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
            if (day>=18&&day<=21) {
                def bills1 = com.next.Bills.findAllByEndTimeGreaterThan(today+(20-day))
                bills1 = com.next.Bills.findAllByOpportunityInList(com.next.Opportunity.findAllBySerialNumberInList(['DYY-XT6-V09','YK2-DGX-N3L','MET-QXM-AG0','CHI-1K0-99R','GRN-3M1-DRM','6WP-5ZU-FAG','QVX-EPI-6N4','J2Z-06M-IJO','2FY-JBJ-XSG']))
                bills1.each {
                    print "月结订单"+it
                        calendar1.set(java.util.Calendar.DAY_OF_MONTH, 19)
                        def opportunity = it?.opportunity
                        java.util.Calendar calendar2 = java.util.Calendar.getInstance()
                        calendar2.clear()
                        calendar2.setTime(new java.util.Date())
                        calendar2.set(java.util.Calendar.HOUR_OF_DAY,0)
                        calendar2.set(java.util.Calendar.MINUTE,0)
                        calendar2.set(java.util.Calendar.SECOND,0)
                        calendar2.set(java.util.Calendar.YEAR,2017)
                        calendar2.set(java.util.Calendar.MONTH,2)
                        calendar2.set(java.util.Calendar.DAY_OF_MONTH,1)

                        loanEvent(opportunity)
                    }


            }*/
           /* def date = new Date().format("yyyy-MM-dd")
            def params1 = ["today":date] as  JSON
            URL url = new java.net.URL("http://10.0.8.165/huofh/jieqing")
            def json
            try
            {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("POST")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write params1.toString() }
                json = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
                println("返回结果" + json)
            }
            catch (java.lang.Exception e)
            {
                e.printStackTrace()
                println e
            }*/
            billsService.lmsToHuofh()
            def endTime = new Date()
            println "———————————————结清结束时间—"+endTime+"——————————————"
            println "----------------一共耗时"+(endTime.getTime()-startTime.getTime())/1000 + "秒"
            return  ([status: "success"] as JSON)
        } catch (Exception e){
            e.printStackTrace(e)
            return ([status: "failed"] as JSON)
        }


    }
}
