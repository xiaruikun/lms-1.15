package com.next

class AppConfiguration
{
    AppConfigurationKey key
    String value

    static belongsTo = [appVersion: AppVersion]

    static constraints = {
        key unique: ['appVersion']
        value maxSize: 256
    }
}
