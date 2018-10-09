package com.next

import grails.transaction.Transactional

@Transactional
class ActivityService
{

    static scope = "singleton"

    def makeActivity(Opportunity opportunity)
    {
        String stageName = opportunity?.stage?.name
        ActivitySubtype activitySubtype
        if (stageName && stageName == "房产初审已完成")
        {
            activitySubtype = ActivitySubtype.findByName("下户")
        }
        else if (stageName && stageName == "抵押上一步工作流")
        {
            activitySubtype = ActivitySubtype.findByName("抵押")
        }
        else if (stageName && stageName == "公证上一步工作流")
        {
            activitySubtype = ActivitySubtype.findByName("公正")
        }
        else
        {
            return
        }
        ActivityType activityType = ActivityType.findByName("Appointment")
        String sql = "from Activity as a where a.opportunity.id=${opportunity.id} and a.type.id=${activityType.id} " + "and a.subtype.id=${activitySubtype.id} and a.status='Pending'"
        Activity activity = Activity.find(sql)
        if (activity)
        {
            activity.startTime = new Date()
            activity.endTime = new Date()
            activity.contact = opportunity.contact
            if (activity.validate())
            {
                activity.save flush: true
            }
            else
            {
                println activity.errors
            }
        }
        else
        {
            activity = new Activity()
            activity.startTime = new Date()
            activity.endTime = new Date()
            activity.type = activityType
            activity.subtype = activitySubtype
            activity.description = ""
            activity.opportunity = opportunity
            activity.contact = opportunity.contact
            if (activity.validate())
            {
                activity.save flush: true
            }
            else
            {
                println activity.errors
            }
        }
    }

}
