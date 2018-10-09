package com.next

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable
{
    private static final long serialVersionUID = 1

    transient springSecurityService

    String code

    String username
    String password
    String fullName

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    String cellphone

    Account account
    Department department
    Position position
    City city

    String externalId

    String appSessionId

    String avatarUrl

    SecurityProfile securityProfile

    //    User reportTo

    String verificationCode

    Date lastPasswordModifiedDate

    Boolean loginBySms = false

    Boolean fixedIp = false

    String ip

    Territory territory

    Boolean dynamicMenu = false

    static hasMany = [teams: UserTeam]

    User(String username, String password)
    {
        this()
        this.username = username
        this.password = password
    }

    Set<Role> getAuthorities()
    {
        UserRole.findAllByUser(this)*.role
    }

    def beforeInsert()
    {
        code = cellphone
        encodePassword()
    }

    def beforeUpdate()
    {
        code = cellphone
        if (isDirty('password'))
        {
            encodePassword()
        }
    }

    protected void encodePassword()
    {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    static transients = ['springSecurityService']

    static constraints = {
        password blank: false, password: true
        //        , matches: "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$"
        username blank: false, unique: true
        code blank: true, nullable: true, size: 11..11 //, unique: true
        department blank: true, nullable: true
        position blank: true, nullable: true

        cellphone size: 11..11, blank: false, nullable: false , unique: true
        city blank: true, nullable: true

        externalId blank: true, nullable: true //, unique: true

        appSessionId blank: true, nullable: true //, unique: true

        avatarUrl nullable: true, blank: true, maxSize: 256

        securityProfile blank: true, nullable: true

        //        reportTo blank: true, nullable: true
        verificationCode blank: true, nullable: true, maxSize: 16

        ip blank: true, nullable: true, maxSize: 64

        lastPasswordModifiedDate blank: true, nullable: true

        territory blank: true, nullable: true
        dynamicMenu blank: true, nullable: true
    }

    static mapping = {
        table 'login_user'
        password column: '`password`'
    }

    String toString()
    {
        String alias

        if (department)
        {
            alias = "${department} ${fullName}"
        }
        else
        {
            alias = fullName
        }

        if (account)
        {
            "${account} ${alias}"
        }
        else
        {
            alias
        }
    }
}
