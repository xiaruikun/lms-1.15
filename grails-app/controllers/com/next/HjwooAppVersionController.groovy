package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

@Transactional(readOnly = true)
class HjwooAppVersionController
{
    @Secured(['permitAll'])
    def queryCurrentAppVersion()
    {
        println "************** hjwoo queryCurrentAppVersion******************"
        def json = request.JSON
        println json
        def appName = json['appName']
        def appVersion = AppVersion.find("from AppVersion where appName = '${appName}' order by appVersion DESC")
        if (appVersion)
        {
            render appVersion as JSON
            return
        }
        else
        {
            render ""
            return
        }
    }
}
