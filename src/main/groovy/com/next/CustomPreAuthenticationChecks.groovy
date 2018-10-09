package com.next

import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsChecker

public class CustomPreAuthenticationChecks implements UserDetailsChecker
{

    public void check(UserDetails user)
    {
        if (user.loginBySms)
        {
            def now = new Date().time
            def lastPasswordModifiedDate = user.lastPasswordModifiedDate?.time
            def overdue
            if (!lastPasswordModifiedDate)
            {
                throw new CredentialsExpiredException("The password has expired")
            }
            else
            {
                overdue = ((now - lastPasswordModifiedDate) / 60000) > 10
                if (overdue)
                {
                    throw new CredentialsExpiredException("The verification code has expired")
                }
            }
        }
    }
}
