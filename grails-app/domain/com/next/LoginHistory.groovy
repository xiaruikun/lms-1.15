package com.next

class LoginHistory
{
    String userName
    String ip
    Date createdDate
    String status

    static constraints = {
        status inList: ['成功', '口令错误', '用户名错误']
        status maxSize: 20
    }
}
