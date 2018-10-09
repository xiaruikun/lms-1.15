package com.next

import groovy.transform.CompileStatic
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails

@CompileStatic
public class CustomAuthenticationProvider extends DaoAuthenticationProvider
{
    protected void additionalAuthenticationChecks(UserDetails userDetails,
        UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
    {
        println "provider in"
        super.additionalAuthenticationChecks(userDetails, authentication)
        Object details = authentication.details
        if (!(userDetails instanceof CustomUserDetails))
        {
            println "provider not customUserDetails"
        }
        if (!(details instanceof CustomWebAuthenticationDetails))
        {
            println "provider not customAuthenticationDetails"
        }
        def customWebAuthenticationDetails = details as CustomWebAuthenticationDetails
        def user = userDetails as CustomUserDetails
        println "provider remoteAddress: " + customWebAuthenticationDetails.getRemoteAddr()
        //TODO
        if (user.fixedIp)
        {
            println "user fixedIp"
            if (user.ip != customWebAuthenticationDetails.getRemoteAddr())
            {
                println "not equal ip"
                throw new CustomIpFixedException("User ip fiexed")
            }
        }
    }

}
