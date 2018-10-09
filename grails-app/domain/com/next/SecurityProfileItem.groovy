package com.next

class SecurityProfileItem
{
    String controllerName
    String actionName
    Boolean accessDeny = false

    static belongsTo = [profile: SecurityProfile]

    static constraints = {
        controllerName maxSize: 256
        actionName maxSize: 256, unique: ['controllerName']
    }

    static mapping = {
        deny column: 'access_deny'
    }
}
