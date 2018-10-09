package com.next

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'authority')
@ToString(includes = 'authority', includeNames = true, includePackage = false)
class Role implements Serializable
{
    private static final long serialVersionUID = 1

    String authority
    String description
    Menu menu

    Role(String authority)
    {
        this()
        this.authority = authority
    }

    static constraints = {
        authority blank: false, unique: true
        menu nullable: true, blnak: true
    }

    static mapping = {
        table 'login_role'
        cache true
    }

    String toString()
    {
        description
    }
}
