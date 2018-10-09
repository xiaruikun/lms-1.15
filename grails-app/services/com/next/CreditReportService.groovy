package com.next

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class CreditReportService
{
    /*
    *调用个人征信接口
    *@author xiaruikun
    * @ModifiedDate 2017-5-5
    */

    def generateReportAll(CreditReportProvider provider, OpportunityContact opportunityContact)
    {
        def br
        String response = ""
        String params
        def contact = opportunityContact?.contact
        if (contact?.cellphone)
        {
            params = "cellphone=" + URLEncoder.encode(contact?.cellphone, "UTF-8") + "&fullName=" + URLEncoder.encode(contact?.fullName, "UTF-8") + "&idNumber=" + URLEncoder.encode(contact?.idNumber, "UTF-8")
        }
        else
        {
            params = "fullName=" + URLEncoder.encode(contact?.fullName, "UTF-8") + "&idNumber=" + URLEncoder.encode(contact?.idNumber, "UTF-8")
        }
        if (provider?.code == 'FOTIC')
        {
            params += "&serialNumber=" + URLEncoder.encode(opportunityContact?.opportunity?.serialNumber, "UTF-8") + "&idType=" + URLEncoder.encode(opportunityContact?.contact?.identityType?.name, "UTF-8")
        }
        String apiUrl = provider?.apiUrl + "?" + params
        URL url = new URL(apiUrl)
        println "url:" + url
        try
        {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("GET")
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = br.readLine()) != null)
            {
                response += line
            }
            if (response)
            {
                println "征信查询成功"
                def json = JSON.parse(response)
                return json

            }
        }
        catch (Exception e)
        {
            println "征信查询失败"
            println e.printStackTrace()
        }
    }

    /*
    *调用企业征信接口
    *author xiaruikun
    */

    def generatePengyuan(CreditReportProvider provider, Company company)
    {
        def br
        String response = ""
        String params
        params = "company=" + URLEncoder.encode(company?.company, "UTF-8")
        if (company?.companyCode)
        {
            params = params + "&companyCode=" + URLEncoder.encode(company?.companyCode, "UTF-8")
        }
        else
        {
            params = params + "&companyCode="
        }
        if (company?.industry)
        {
            params = params + "&industry=" + URLEncoder.encode(company?.industry?.name, "UTF-8")
        }
        else
        {
            params = params + "&industry="
        }
        String apiUrl = provider?.apiUrl + "?" + params
        URL url = new URL(apiUrl)
        println "url:" + url
        try
        {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("GET")
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = br.readLine()) != null)
            {
                response += line
            }
            if (response)
            {
                println "征信查询成功"
                def json = JSON.parse(response)
                return json

            }
        }
        catch (Exception e)
        {
            println "征信查询失败"
            println e.printStackTrace()
        }
    }

    /*
    *author xiaruikun
    */

    def queryBlackList(CreditReportProvider provider, def params)
    {
        def br
        String response = ""
        String apiUrl = provider?.apiUrl + "?" + params
        URL url = new URL(apiUrl)
        println "url:" + url
        try
        {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setRequestMethod("GET")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "*/*")
            connection.setRequestProperty("Connection", "Keep-Alive")
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible MSIE 8.0 Windows NT 6.1)")
            connection.connect()
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
            def line
            while ((line = br.readLine()) != null)
            {
                response += line
            }
            if (response)
            {
                println "中佳信黑名单查询成功"
                def json = JSON.parse(response)
                return json
            }
        }
        catch (Exception e)
        {
            println "获取中佳信黑名单失败"
            println e.printStackTrace()
        }
    }

    def isNumber(String score)
    {
        try
        {
            if (score)
            {
                score.toInteger()
                return true
            }
            else
            {
                return false
            }
        }
        catch (NumberFormatException e)
        {
            return false
        }
    }

    /*
    *author xiaruikun
    *20170331
    * *大数据event
    * 调取大数据provider将数据存入ExternalDataset
    */

    def index = { opportunity ->
        def opportunityContacts = com.next.OpportunityContact.findAllByOpportunity(opportunity)
        def providerList = com.next.CreditReportProvider.findAll("from CreditReportProvider where code in ('BAIRONG','HUIFA')")
        //调用查询contact
        def generateReportAll = { com.next.CreditReportProvider provider, com.next.Contact contact ->
            String response = ""
            String params
            if (contact?.cellphone)
            {
                params = "cellphone=" + URLEncoder.encode(contact?.cellphone, "UTF-8") + "&fullName=" + URLEncoder.encode(contact?.fullName, "UTF-8") + "&idNumber=" + URLEncoder.encode(contact?.idNumber, "UTF-8")
            }
            else
            {
                params = "fullName=" + URLEncoder.encode(contact?.fullName, "UTF-8") + "&idNumber=" + URLEncoder.encode(contact?.idNumber, "UTF-8")
            }
            String apiUrl = provider?.apiUrl + "?" + params
            URL url = new java.net.URL(apiUrl)
            println "url:" + url
            try
            {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("GET")
                //          def response = connection.inputStream.withReader { Reader reader -> reader.text }
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
                def line
                while ((line = br.readLine()) != null)
                {
                    response += line
                }
                if (response)
                {
                    println "征信查询成功"
                    def json = grails.converters.JSON.parse(response)
                    return json

                }
            }
            catch (java.lang.Exception e)
            {
                println "征信查询失败"
                println e.printStackTrace()
            }
        }
        //调用查询company
        def generatePengyuan = { com.next.CreditReportProvider provider, com.next.Company company ->
            String response = ""
            String params
            params = "company=" + URLEncoder.encode(company?.company, "UTF-8")
            if (company?.companyCode)
            {
                params = params + "&companyCode=" + URLEncoder.encode(company?.companyCode, "UTF-8")
            }
            else
            {
                params = params + "&companyCode="
            }
            if (company?.industry)
            {
                params = params + "&industry=" + URLEncoder.encode(company?.industry?.name, "UTF-8")
            }
            else
            {
                params = params + "&industry="
            }
            String apiUrl = provider?.apiUrl + "?" + params
            URL url = new java.net.URL(apiUrl)
            println "url:" + url
            try
            {
                def connection = (java.net.HttpURLConnection) url.openConnection()
                connection.setDoOutput(true)
                connection.setRequestMethod("GET")
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))
                def line
                while ((line = br.readLine()) != null)
                {
                    response += line
                }
                if (response)
                {
                    println "征信查询成功"
                    def json = grails.converters.JSON.parse(response)
                    return json

                }
            }
            catch (java.lang.Exception e)
            {
                println "征信查询失败"
                println e.printStackTrace()
            }
        }
        //存储个人征信
        def personalCredit = { def json, com.next.Contact contact, def provider ->
            com.next.ExternalDataset externalDataset = new com.next.ExternalDataset()
            externalDataset.provider = com.next.ExternalDataProvider.findByName(provider?.name)
            externalDataset.save()

            com.next.ContactExternalDataset contactExternalDataset = new com.next.ContactExternalDataset()
            contactExternalDataset.contact = contact
            contactExternalDataset.dataset = externalDataset
            contactExternalDataset.save()

            com.next.OpportunityExternalDataset opportunityExternalDataset = new com.next.OpportunityExternalDataset()
            opportunityExternalDataset.opportunity = opportunity
            opportunityExternalDataset.dataset = externalDataset
            opportunityExternalDataset.save()
            json["items"]?.each {
                com.next.ExternalDatasetItem externalDatasetItem = new com.next.ExternalDatasetItem()
                if (it["type"])
                {
                    externalDatasetItem.name = it["type"]
                    externalDatasetItem.value = it["name"]
                }
                else
                {
                    externalDatasetItem.name = it["name"]
                    externalDatasetItem.value = it["value"]
                }
                externalDatasetItem.dataset = externalDataset
                externalDatasetItem.save()
                it["items"]?.each {
                    com.next.ExternalDatasetItem externalDatasetChildItem = new com.next.ExternalDatasetItem(dataset: externalDataset, parent: externalDatasetItem)
                    externalDatasetChildItem.name = it["name"]
                    externalDatasetChildItem.value = it["value"]
                    externalDatasetChildItem.level = 1
                    externalDatasetChildItem.save()
                }
            }
        }
        //存储企业征信
        def enterpriseCredit = { def json, com.next.Company company, def provider ->
            com.next.ExternalDataset externalDataset = new com.next.ExternalDataset()
            externalDataset.provider = com.next.ExternalDataProvider.findByName(provider?.name)
            externalDataset.save()

            com.next.CompanyExternalDataset companyExternalDataset = new com.next.CompanyExternalDataset()
            companyExternalDataset.company = company
            companyExternalDataset.dataset = externalDataset
            companyExternalDataset.save()

            com.next.OpportunityExternalDataset opportunityExternalDataset = new com.next.OpportunityExternalDataset()
            opportunityExternalDataset.opportunity = opportunity
            opportunityExternalDataset.dataset = externalDataset
            opportunityExternalDataset.save()
            json["items"]?.each {
                com.next.ExternalDatasetItem externalDatasetItem = new com.next.ExternalDatasetItem()
                externalDatasetItem.name = it["name"]
                externalDatasetItem.value = it["value"]
                externalDatasetItem.dataset = externalDataset
                externalDatasetItem.save()
                if (it["items"])
                {
                    it["items"].each {
                        com.next.ExternalDatasetItem externalDatasetChildItem = new com.next.ExternalDatasetItem(dataset: externalDataset, parent: externalDatasetItem)
                        externalDatasetChildItem.name = it["name"]
                        externalDatasetChildItem.value = it["value"]
                        externalDatasetChildItem.level = 1
                        externalDatasetChildItem.save()
                    }
                }
            }
        }
        opportunityContacts?.each {
            def opportunityContact = it
            providerList?.each {
                def json
                if (it?.code == "PENGYUAN" || it?.code == "COMPANY")
                {
                    if (opportunityContact?.contact?.companies)
                    {
                        def provider = it
                        opportunityContact?.contact?.companies?.each {
                            boolean judge = true
                            def companyExternalDataset1 = com.next.CompanyExternalDataset.find("from CompanyExternalDataset where company.id = ${it?.id} and dataset.provider.name = '${provider?.name}' order by dataset.createdDate desc")
                            if (companyExternalDataset1)
                            {
                                def date = new Date()
                                def days = date - companyExternalDataset1?.dataset?.createdDate
                                if (days <= 7)
                                {
                                    judge = false
                                }
                            }
                            if (judge)
                            {
                                if (it?.company)
                                {
                                    json = generatePengyuan(provider, it)
                                }
                                enterpriseCredit(json, it, provider)
                            }
                            else
                            {
                                println "this company query already in seven days"
                            }
                        }
                    }
                }
                else if (it?.code == "BAIRONG")
                {
                    //                    boolean judge = true
                    //                    def contactExternalDataset1 = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '${it?.name}' order by dataset.createdDate desc")
                    //                    if (contactExternalDataset1)
                    //                    {
                    //                        def date = new Date()
                    //                        def days = date - contactExternalDataset1?.dataset?.createdDate
                    //                        if (days <= 7)
                    //                        {
                    //                            judge = false
                    //                        }
                    //                    }
                    //                    if (judge)
                    //                    {
                    json = generateReportAll(it, opportunityContact?.contact)
                    if (json && json["items"])
                    {
                        if (it?.code == "BAIRONG")
                        {
                            if (json["company"])
                            {
                                for (
                                    def name in
                                        json["company"])
                                {
                                    if (!com.next.Company.findByCompanyAndContact(name, opportunityContact?.contact))
                                    {
                                        com.next.Company company = new com.next.Company()
                                        company.company = name
                                        company.contact = opportunityContact?.contact
                                        if (company.validate())
                                        {
                                            company.save flush: true
                                        }
                                        else
                                        {
                                            println company.errors
                                        }
                                    }
                                }
                            }
                        }
                        personalCredit(json, opportunityContact?.contact, it)
                    }
                    //                    }
                    //                    else
                    //                    {
                    //                        println "this contact query already in seven days"
                    //                    }
                }
            }
        }
    }

    //百融
    //法院执行人、失信人
    def index1 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it?.name == '通过身份证号查询法院失信人' || it?.name == '通过身份证号查询法院被执行人')
                {
                    bool = false
                }
                if (it.name == '被执行人信息')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it.name == '案件状态' && !(it.value == '已结案'))
                        {
                            bool = false
                        }
                    }
                }
                if (it.name == '失信被执行人')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it.name == '未履行' && !(it.value == '是'))
                        {
                            bool = false
                        }
                    }
                }
            }
        }
        return bool
    }
    //不良信息:在逃、吸毒、涉毒
    def index2 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '个人不良信息')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it.name == '案件来源' && it.value in ['在逃', '吸毒', '涉毒'])
                        {
                            bool = false
                        }
                    }
                }
            }
        }
        return bool
    }
    //命中前科（除忽略项）
    def index3 = { opportunity ->
        boolean bool = true
        boolean adjust = false
        opportunity?.contacts?.each {
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
                        if (adjust && it.name == '案件类别' && it.value in ['其他毒品刑事案件', '容留他人吸毒案', '抢劫其他汽车案', '抢劫摩托车案', '抢劫案', '拦路抢劫案', '入户抢劫案', '敲诈勒索案', '当前在逃', '诈骗案', '招摇撞骗案', '赌博案', '抢夺案', '组织卖淫案', '赌博案聚众斗殴案', '故意杀人案', '贩卖毒品案', '金融诈骗案', '合同诈骗案', '非法持有毒品案', '持枪抢劫案', '强迫卖淫案', '故意伤害案', '绑架案', '赌博案', '运输毒品案', '非法买卖枪支弹药案', '集资诈骗案', '拒不执行判决', '非法吸收公众存款案', '制造毒品案', '强奸案', '抢劫出租汽车案', '拐卖妇女儿童案'])
                        {
                            bool = false
                        }
                    }
                }
            }
        }
        return bool
    }
    //银行不良或欺诈
    def index4 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name in ['通过身份证号查询银行不良', '通过身份证号查询银行欺诈', '通过手机号查询银行不良', '通过手机号查询银行欺诈', '通过联系人手机查询银行不良', '通过联系人手机查询银行欺诈', '通过百融标识查询银行不良', '通过百融标识查询银行欺诈'])
                {
                    bool = false
                }
            }
        }
        return bool
    }
    //身份认证
    def index5 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
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

    //身份认证核实出错
    def index6 = { opportunity ->
        def num = 0
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '百融' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it?.name == '身份证查询照片')
                {
                    num += 1
                }
            }
        }
        if (num == opportunity?.contacts.size())
        {
            return true
        }
        else
        {
            return false
        }
    }

    //鹏元
    //法院执行人、失信人（未结案）
    def pengyuan1 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '风险信息')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it?.name == '司法执行信息条数' || it?.name == '司法失信信息条数')
                        {
                            if (it?.value)
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
    //身份信息命中高危人员名单
    def pengyuan2 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
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
    //信贷逾期名单（证件号码/手机号码命中>20次）
    def pengyuan3 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '风险信息')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it?.name == '网贷逾期信息条数')
                        {
                            if (it?.value)
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
    //网贷逾期名单、催收公告信息
    def pengyuan4 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '风险信息')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it?.name in ['网贷逾期信息条数', '催欠公告信息条数'])
                        {
                            if (it?.value)
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
    //身份认证返回不一致
    def pengyuan5 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '身份认证')
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it.name == '结果')
                        {
                            if (!(it.value == '一致'))
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
    //身份认证核实出错
    def pengyuan6 = { opportunity ->
        def num = 0
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '鹏元个人' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '身份认证')
                {
                    num += 1
                }
            }
        }
        if (num == opportunity?.contacts.size())
        {
            return true
        }
        else
        {
            return false
        }
    }
    //汇法
    //法院执行人、失信人（未结案）
    def huifa1 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '汇法' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name in ['执行公开信息', '失信老赖名单'])
                {
                    def detailItem = com.next.ExternalDatasetItem.findAll("from ExternalDatasetItem where parent.id=? and dataset.id=? and level=? order by id", [it?.id, contactExternalDataset?.dataset?.id, 1])
                    detailItem?.each {
                        if (it.name == '执行状态' && !(it.value == '已结案'))
                        {
                            bool = false
                        }
                    }
                }
            }
        }
        return bool
    }
    //催收信息:网贷逾期名单、催收函
    def huifa2 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
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
    //同盾
    //涉及法院执行、失信类（含模糊、未结案）
    def tongdun1 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '同盾' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name in ['身份证命中法院失信名单', '身份证命中法院执行名单', '身份证_姓名命中法院失信模糊名单', '身份证_姓名命中法院执行模糊名单'])
                {
                    bool = false
                }
            }
        }
        return bool
    }
    //涉及失联、犯罪通缉的
    def tongdun2 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '同盾' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '身份证命中犯罪通缉名单')
                {
                    bool = false
                }
            }
        }
        return bool
    }
    //涉及黑名单类的
    def tongdun3 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '同盾' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name in ['身份证命中信贷逾期名单', '身份证_姓名命中信贷逾期模糊名单', '手机号命中信贷逾期名单'])
                {
                    bool = false
                }
            }
        }
        return bool
    }
    //手机号命中贷款黑中介库
    def tongdun4 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '同盾' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name == '手机号命中贷款黑中介库')
                {
                    bool = false
                }
            }
        }
        return bool
    }
    //身份证/手机号命中失联名单
    def tongdun5 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '同盾' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name in ['身份证命中失联名单', '手机号命中失联名单'])
                {
                    bool = false
                }
            }
        }
        return bool
    }
    //手机号疑似乱填、身份证格式校验错误、手机号命中虚假号码库
    def tongdun6 = { opportunity ->
        boolean bool = true
        opportunity?.contacts?.each {
            def opportunityContact = it
            def contactExternalDataset = com.next.ContactExternalDataset.find("from ContactExternalDataset where contact.id = ${opportunityContact?.contact?.id} and dataset.provider.name = '同盾' order by dataset.createdDate desc")
            def itemList = com.next.ExternalDatasetItem.findAllByDatasetAndLevel(contactExternalDataset?.dataset, 0)
            itemList?.each {
                if (it.name in ['手机号疑似乱填', '身份证格式校验错误', '手机号命中虚假号码库'])
                {
                    bool = false
                }
            }
        }
        return bool
    }
}
