package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@Transactional
@CompileStatic
@TypeChecked
class ReportingService
{
    static scope = "singleton"

    def serviceMethod()
    {
    }
}
