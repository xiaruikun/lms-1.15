package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @CreatedDate 2017-5-8
 */
@Transactional
class ComponentService
{

    static scope = "singleton"

    def evaluate(Component component, Object object)
    {
        def shell = new GroovyShell()
        def closure
        def result

        try
        {
            closure = shell.evaluate(component?.script)
            if (!object)
            {
                result = closure
            }
            else
            {
                result = closure(object)
            }
        }
        catch (Exception e)
        {
            log.error e
            result = e
        }

        return result
    }
}
