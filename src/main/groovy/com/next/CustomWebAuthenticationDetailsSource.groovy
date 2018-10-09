package com.next

import org.springframework.security.authentication.AuthenticationDetailsSource

import javax.servlet.http.HttpServletRequest

public class CustomWebAuthenticationDetailsSource
    implements AuthenticationDetailsSource<HttpServletRequest, CustomWebAuthenticationDetails>
{

    /**
     * @see org.springframework.security.web.authentication.WebAuthenticationDetailsSource#buildDetails(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public CustomWebAuthenticationDetails buildDetails(HttpServletRequest context)
    {
        return new CustomWebAuthenticationDetails(context)
    }
}
