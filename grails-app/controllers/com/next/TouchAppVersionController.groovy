package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

@Transactional(readOnly = true)
class TouchAppVersionController
{
    @Secured(['permitAll'])
    def queryCurrentAppVersion()
    {
        println "************** touch queryCurrentAppVersion******************"
        def json = request.JSON
        println json
        def appName = json['appName']
        def appVersion = AppVersion.find("from AppVersion where appName = '${appName}' order by appVersion DESC")
        render appVersion as JSON
        return
    }
}
