package com.next

import org.springframework.security.core.AuthenticationException

class CustomIpFixedException extends AuthenticationException
{

    public CustomIpFixedException(String message, Throwable t)
    {
        super(message, t)
    }

    public CustomIpFixedException(String message)
    {
        super(message)
    }

}
