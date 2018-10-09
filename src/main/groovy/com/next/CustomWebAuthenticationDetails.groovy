package com.next

import org.springframework.security.web.authentication.WebAuthenticationDetails

import javax.servlet.http.HttpServletRequest

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails
{

    private final String remoteAddress

    public CustomWebAuthenticationDetails(HttpServletRequest request)
    {

        super(request)
        this.remoteAddress = request.getHeader('X-FORWARDED-FOR') ?: request.getRemoteAddr()

        remoteAddress = request.getHeader("x-forwarded-for")
        if (remoteAddress == null || remoteAddress.length() == 0 || "unknown".equalsIgnoreCase(remoteAddress))
        {
            remoteAddress = request.getHeader("Proxy-Client-IP")
        }
        if (remoteAddress == null || remoteAddress.length() == 0 || "unknown".equalsIgnoreCase(remoteAddress))
        {
            remoteAddress = request.getHeader("WL-Proxy-Client-IP")
        }
        if (remoteAddress == null || remoteAddress.length() == 0 || "unknown".equalsIgnoreCase(remoteAddress))
        {
            remoteAddress = request.getRemoteAddr()
        }

        println "CustomWebAuthenticationDetails：" + remoteAddress
    }

    public String getRemoteAddr()
    {
        return remoteAddress
    }
}
