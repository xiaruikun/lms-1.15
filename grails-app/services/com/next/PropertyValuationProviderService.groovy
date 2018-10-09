package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput

@Transactional
class PropertyValuationProviderService
{
    //pv product
    // def url = "http://10.0.8.168"
    // def url = "http://10.0.8.168"
    // def url = "http://10.33.50.35:7777"
    //pv test
    //    def url = ""

    def evaluate(params)
    {
        def city = params['city']

        city = City.findByName(city)
        if (!city)
        {
            return 0
        }

        def provider = CityPropertyValuationProvider.findByCity(city).provider

        if (!provider)
        {
            provider = PropertyValuationProvider.findByCode("FGG")
        }
        city = URLEncoder.encode(city.name, "UTF-8")
        def urlString = "${provider.apiUrl}?city=${city}"

        // provider.parameters.each
        //     {
        //         if (it.name != "city")
        //         {
        //             def value = params[it.name]
        //             if (value)
        //             {
        //                 if (it.name in ["orientation", "address", "houseType", "projectName"])
        //                 {
        //                     value = URLEncoder.encode(value, "UTF-8")
        //                 }
        //                 urlString += "&${it.name}=${value}"
        //             }
        //         }
        // }

        println urlString

        def url = new URL(urlString)
        def connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        // connection.setRequestProperty("Content-Type", "application/json")
        // connection.outputStream.withWriter { Writer writer -> writer.write}
        println connection.getConnectTimeout()
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            def json = JSON.parse(result)
            println json
            return json
        }
        else
        {
            return 0
        }
    }

    def queryPrice(Collateral collateral)
    {
        println "##############估值service开始:" + new Date() + "########3"
        def params = [:]
        params['city'] = collateral.city.name
        params['district'] = collateral.district
        params['projectName'] = collateral.projectName
        params['building'] = collateral.building
        if (collateral.unit)
        {
            params['unit'] = collateral.unit
        }
        else
        {
            params['unit'] = "0"
        }
        params['floor'] = collateral.floor
        params['roomNumber'] = collateral.roomNumber
        params['totalFloor'] = collateral.totalFloor
        params['area'] = collateral.area
        params['address'] = collateral.address
        params['orientation'] = collateral.orientation
        params['houseType'] = collateral.houseType
        params['assetType'] = collateral.assetType
        println collateral.specialFactors
        if (collateral.specialFactors != "无")
        {
            params['specialFactors'] = collateral.specialFactors
        }
        if (collateral.appliedTotalPrice)
        {
            params['appliedTotalPrice'] = collateral.appliedTotalPrice
        }
        else
        {
            params['appliedTotalPrice'] = 0
        }
        params['atticArea'] = collateral.atticArea
        println "估值参数："
        println params
        params = JsonOutput.toJson(params)
        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/provider/evaluate"
        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { Writer writer -> writer.write params }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            println "估计结果：" + result
            if (!result)
            {
                println "##############估值service结束:" + new Date() + "########3"
                println "######返回为空####"
                return 0
            }
            else
            {
                def json = JSON.parse(result)
                println "##############估值service结束:" + new Date() + "########3"
                return json
            }
        }
        else
        {
            println "估值失败"
            println "##############估值service结束:" + new Date() + "########3"
            return 0
        }
    }

    /**
     * Created by 任晓文
     * app询值接口--世联
     * */
    def queryPrice2(Collateral collateral)
    {
        println "##############估值service开始:" + new Date() + "########3"
        def params = [:]
        params['city'] = collateral.city.name
        params['district'] = collateral.district
        params['projectName'] = collateral.projectName
        params['building'] = collateral.building
        if (collateral.unit)
        {
            params['unit'] = collateral.unit
        }
        else
        {
            params['unit'] = "0"
        }
        // params['unit'] =collateral.unit
        params['floor'] = collateral.floor
        params['roomNumber'] = collateral.roomNumber
        params['totalFloor'] = collateral.totalFloor
        params['area'] = collateral.area
        params['address'] = collateral.address
        params['orientation'] = collateral.orientation
        params['houseType'] = collateral.houseType
        params['assetType'] = collateral.assetType
        println collateral.specialFactors
        if (collateral.specialFactors != "无")
        {
            params['specialFactors'] = collateral.specialFactors
        }
        if (collateral.appliedTotalPrice)
        {
            params['appliedTotalPrice'] = collateral.appliedTotalPrice
        }
        else
        {
            params['appliedTotalPrice'] = 0
        }
        params['atticArea'] = collateral.atticArea
        //add opportunitySerialNumber
        params['opportunity'] = collateral?.opportunity?.serialNumber
        println "估值参数："
        println params
        params = JsonOutput.toJson(params)

        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/provider/evaluate2"
        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { Writer writer -> writer.write params }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            println "估计结果：" + result
            if (!result)
            {
                println "##############估值service结束:" + new Date() + "########3"
                println "######返回为空####"
                return 0
            }
            else
            {
                def json = JSON.parse(result)
                println "##############估值service结束:" + new Date() + "########3"
                return json
            }
        }
        else
        {
            println "估值失败"
            println "##############估值service结束:" + new Date() + "########3"
            return 0
        }
    }

    def searchProjectName(String city, String projectName)
    {
        def result = 0
        try
        {
            city = URLEncoder.encode(city, "UTF-8")
            projectName = URLEncoder.encode(projectName, "UTF-8")
            def urlString = "http://10.0.8.105/ygj-3.0.0/provider/searchProjectName?city=${city}&district=${projectName}"
            println urlString
            def url = new URL(urlString)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("GET")
            println connection.getResponseCode()
            if (connection.getResponseCode() == 200)
            {
                def results = connection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
                result = JSON.parse(results)
            }
        }
        finally
        {
            return result
        }
    }

    def suggestSubmmit(def suggest)
    {
        println suggest
        println "##############估值反馈suggestSubmmit开始########3"
        String param = JsonOutput.toJson(suggest)
        println param
        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/provider/applicationPriceAdjustment"
        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { Writer writer -> writer.write param }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            def json = JSON.parse(result)
            println "#######true#######估值反馈suggestSubmmit结束########3"
            println json
            return json
        }
        else
        {
            println "######false########估值反馈suggestSubmmit结束########3"
            return ""
        }
    }

    def suggestSubmmit1(String param)
    {
        println "##############估值反馈suggestSubmmit开始########3"
        println param
        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/provider/applicationPriceAdjustment"
        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { Writer writer -> writer.write param }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            def json = JSON.parse(result)
            println "#######true#######估值反馈suggestSubmmit结束########3"
            println json
            return json
        }
        else
        {
            println "######false########估值反馈suggestSubmmit结束########3"
            return ""
        }
    }

    def suggestSubmmit2(String param)
    {
        println "##############估值反馈suggestSubmmit开始########3"
        println param

        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/provider/saveImage"
        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter("utf-8") { Writer writer -> writer.write param }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader("utf-8") { Reader reader -> reader.text }
            def json = JSON.parse(result)
            println "#######true#######估值反馈suggestSubmmit2结束########3"
            println json
            return json
        }
        else
        {
            println "######false########估值反馈suggestSubmmit2结束########3"
            return ""
        }
    }

    def createCollateral(Collateral collateral)
    {
        println "aaaaaaaaaaaaaaaaaaa"
        println "##############估值service开始:" + new Date() + "########3"
        def params = [:]
        params['city'] = collateral.city.name
        params['district'] = collateral.district
        params['projectName'] = collateral.projectName
        params['building'] = collateral.building
        if (collateral.unit)
        {
            params['unit'] = collateral.unit
        }
        else
        {
            params['unit'] = "0"
        }
        // params['unit'] =collateral.unit
        params['floor'] = collateral.floor
        params['roomNumber'] = collateral.roomNumber
        params['totalFloor'] = collateral.totalFloor
        params['area'] = collateral.area
        params['address'] = collateral.address
        params['orientation'] = collateral.orientation
        params['houseType'] = collateral.houseType
        params['assetType'] = collateral.assetType
        println collateral.specialFactors
        if (collateral.specialFactors != "无")
        {
            params['specialFactors'] = collateral.specialFactors
        }
        if (collateral.appliedTotalPrice)
        {
            params['appliedTotalPrice'] = collateral.appliedTotalPrice
        }
        else
        {
            params['appliedTotalPrice'] = 0
        }
        params['atticArea'] = collateral.atticArea
        //add opportunitySerialNumber
        params['opportunity'] = collateral?.opportunity?.serialNumber
        println "估值参数："
        println params
        params = JsonOutput.toJson(params)
        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/collateral/createCollateral"

        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { Writer writer -> writer.write params }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            println "估计结果：" + result
            if (!result)
            {
                println "##############估值service结束:" + new Date() + "########3"
                println "######返回为空####"
                return 0
            }
            else
            {
                def json = JSON.parse(result)
                println "##############估值service结束:" + new Date() + "########3"
                return json
            }
        }
        else
        {
            println "估值失败"
            println "##############估值service结束:" + new Date() + "########3"
            return 0
        }
    }

    def createCollateral1(Collateral collateral)
    {
        println "##############估值service开始:" + new Date() + "########3"
        def params = [:]
        params['city'] = collateral.city.name
        params['district'] = collateral.district
        params['projectName'] = collateral.projectName
        params['building'] = collateral.building
        if (collateral.unit)
        {
            params['unit'] = collateral.unit
        }
        else
        {
            params['unit'] = "0"
        }
        // params['unit'] =collateral.unit
        params['floor'] = collateral.floor
        params['roomNumber'] = collateral.roomNumber
        params['totalFloor'] = collateral.totalFloor
        params['area'] = collateral.area
        params['address'] = collateral.address
        params['orientation'] = collateral.orientation
        params['houseType'] = collateral.houseType
        params['assetType'] = collateral.assetType
        println collateral.specialFactors
        if (collateral.specialFactors != "无")
        {
            params['specialFactors'] = collateral.specialFactors
        }
        if (collateral.appliedTotalPrice)
        {
            params['appliedTotalPrice'] = collateral.appliedTotalPrice
        }
        else
        {
            params['appliedTotalPrice'] = 0
        }
        params['atticArea'] = collateral.atticArea
        println "估值参数："
        println params
        params = JsonOutput.toJson(params)

        def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/collateral/createCollateral1"
        def url = new URL(urlString)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.outputStream.withWriter { Writer writer -> writer.write params }
        println connection.getResponseCode()
        if (connection.getResponseCode() == 200)
        {
            def result = connection.inputStream.withReader { Reader reader -> reader.text }
            println "估计结果：" + result
            if (!result)
            {
                println "##############估值service结束:" + new Date() + "########3"
                println "######返回为空####"
                return 0
            }
            else
            {
                def json = JSON.parse(result)
                println "##############估值service结束:" + new Date() + "########3"
                return json
            }
        }
        else
        {
            println "估值失败"
            println "##############估值service结束:" + new Date() + "########3"
            return 0
        }
    }

    def updateFastUnitPrice(String externalId, String fastUnitPrice)
    {
        println "################## updateFastUnitPrice start ###########################"
        def params = "externalId=${externalId}&fastSellPrice=${fastUnitPrice}"
        println params
        def map = [:]

        try
        {
            def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/collateral/updateFastUnitPrice"
            def url = new URL(urlString)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.outputStream.withWriter { Writer writer -> writer.write params }
            println connection.getResponseCode()
            if (connection.getResponseCode() == 200)
            {
                def result = connection.inputStream.withReader { Reader reader -> reader.text }
                def json = JSON.parse(result)
                def errorCode = json['errorCode']

                if ((errorCode == 4003) || (errorCode == 4004))
                {
                    map['flag'] = false
                    map['message'] = json['errorMessage']
                    return map
                }
                else
                {
                    map['flag'] = true
                    map['message'] = json['errorMessage']
                    return map
                }
            }
            else
            {
                log.error "collateral/updateFastUnitPrice interface error"

                map['flag'] = false
                map['message'] = "PV接口异常"
                return map
            }
        }
        catch (Exception e)
        {
            log.error "pv connection error: " + e

            map['flag'] = false
            map['message'] = "PV网络异常"
            return map
        }
    }

    def queryByExternalId(String externalId)
    {
        println "################## queryByExternalId start ###########################"
        def params = "externalId=${externalId}"
        def valuationReliability
        def json
        try
        {
            def urlString = CreditReportProvider.findByCode("PV")?.apiUrl+"/provider/queryByExternalId"
            def url = new URL(urlString)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.outputStream.withWriter("UTF-8") { Writer writer -> writer.write params }
            println connection.getResponseCode()
            if (connection.getResponseCode() == 200)
            {
                def result = connection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
                if (result)
                {
                    json = JSON.parse(result)
                    valuationReliability = json['valuationReliability']
                }
            }
        }
        catch (Exception e)
        {
            log.error "pv connection error: " + e
        }

        return json
    }
}
