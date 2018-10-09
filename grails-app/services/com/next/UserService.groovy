package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@Transactional
@CompileStatic
@TypeChecked
class UserService
{
    static scope = "singleton"

    MessageService messageService

    // String generateCode()
    // {
    //     def charset = (('A'..'Z') + ('0'..'9')).join()
    //     def code = org.apache.commons.lang.RandomStringUtils.random(4, charset)
    //     while (User.findWhere(code: code))
    //     {
    //         code = org.apache.commons.lang.RandomStringUtils.random(4, charset)
    //     }
    //     return code
    // }

    def sendVerificationCode(User user)
    {
        def verifiedCode = ""
        for (
            int i = 0;
                i < 6;
                i++)
        {
            verifiedCode += (int) Math.floor(Math.random() * 10)
        }

        user.verificationCode = verifiedCode
        user.lastPasswordModifiedDate = new Date()
        user.password = verifiedCode
        user.save flush: true

        // 发送短信
        String content = "【中佳信】您的“中佳信”验证码为:${user?.verificationCode}，10分钟内有效"
        messageService.sendMessage3(user.cellphone, content)
    }

    def resetPassword(User user, String verificationCode, String newPassword)
    {

    }
}
