package com.next

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
class OpportunityInterceptor
{

    def springSecurityService

    OpportunityInterceptor()
    {
        match(controller: "opportunity")
    }

    boolean before()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.find("from User where username = '${username}'")
        def id = params['id']
        Boolean flag = true
        user?.securityProfile?.items?.each {
            if (it?.controllerName == params['controller'] && it?.actionName == params['action'] && it?.deny)
            {
                if (it?.actionName == 'show')
                {
                    def opportunity = Opportunity.find("from Opportunity where id = ${id}")
                    if (opportunity?.user != user)
                    {
                        flag = false
                        flash.message = "对不起，您没有此项操作权限权限"
                        redirect(controller: "opportunityTeam", action: "index")
                    }
                }
                else
                {
                    flag = false
                    flash.message = "对不起，您没有此项操作权限权限"
                    redirect(controller: "opportunityTeam", action: "index")
                }
            }
        }
        flag
    }

    boolean after()
    {
        true
    }

    void afterView()
    {
        // no-op
    }
}
