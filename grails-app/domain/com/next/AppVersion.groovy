package com.next

class AppVersion
{
    String appName
    String appVersion
    Boolean mustUpdate = false
    String description

    static hasMany = [appConfiguration: AppConfiguration]

    static constraints = {
        appName maxSize: 64, unique: ['appVersion']
        appVersion maxSize: 8
        description maxSize: 2048
    }
}
