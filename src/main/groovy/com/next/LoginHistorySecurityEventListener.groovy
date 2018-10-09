package com.next

import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import groovy.transform.CompileStatic
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.access.event.AbstractAuthorizationEvent
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.event.AbstractAuthenticationEvent
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent

@CompileStatic
class LoginHistorySecurityEventListener implements ApplicationListener<ApplicationEvent>, ApplicationContextAware
{

    ApplicationContext applicationContext

    void onApplicationEvent(ApplicationEvent e)
    {

        if (e instanceof AbstractAuthenticationEvent)
        {
            if (e instanceof InteractiveAuthenticationSuccessEvent)
            {
                GrailsUser principal = (GrailsUser) e.authentication.principal
                CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) e.authentication.details
                LoginHistory.withTransaction { status ->
                    def loginHistory = new LoginHistory()
                    loginHistory.userName = principal.username
                    loginHistory.ip = details.remoteAddress
                    println "login remoteAddress:" + details.getRemoteAddr()
                    loginHistory.createdDate = new Date()
                    loginHistory.status = '成功'
                    if (loginHistory.validate())
                    {
                        loginHistory.save flush: true
                    }
                    else
                    {
                        println loginHistory.errors
                    }
                }
            }
            else if (e instanceof AbstractAuthenticationFailureEvent)
            {
                def exception = e.exception
                CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) e.authentication.details
                if (exception instanceof BadCredentialsException)
                {
                    LoginHistory.withTransaction { status ->
                        def loginHistory = new LoginHistory()
                        loginHistory.userName = e.authentication.principal
                        loginHistory.ip = details.remoteAddress
                        loginHistory.createdDate = new Date()
                        loginHistory.status = '口令错误'
                        if (loginHistory.validate())
                        {
                            loginHistory.save flush: true
                        }
                        else
                        {
                            println loginHistory.errors
                        }
                    }
                }
                else if (exception instanceof NoStackUsernameNotFoundException)
                {
                    LoginHistory.withTransaction { status ->
                        def loginHistory = new LoginHistory()
                        loginHistory.userName = e.authentication.principal
                        loginHistory.ip = details.remoteAddress
                        loginHistory.createdDate = new Date()
                        loginHistory.status = '用户名错误'
                        if (loginHistory.validate())
                        {
                            loginHistory.save flush: true
                        }
                        else
                        {
                            println loginHistory.errors
                        }
                    }
                }

            }
            else if (e instanceof AuthenticationSuccessEvent)
            {
                // println "login 1 "
            }
            else if (e instanceof AuthenticationSwitchUserEvent)
            {
                // println "AuthenticationSwitchUserEvent"
            }
        }
        else if (e instanceof AbstractAuthorizationEvent)
        {
            // println "not authorization"
        }
    }
}
