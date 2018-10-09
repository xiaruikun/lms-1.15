/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
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
            else
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