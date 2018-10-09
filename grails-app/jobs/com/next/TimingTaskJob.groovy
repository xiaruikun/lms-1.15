package com.next

/**
 * @Author 班旭娟
 * @CreatedDate 2017-5-4
 */

class TimingTaskJob
{
    def timingTaskService
    def jieqingHFHService
    static triggers = {
        cron name: 'everydayTrigger', startDelay: 10000, cronExpression: '0 0 0 * * ?'
        cron name: 'weeklyTrigger', startDelay: 10000, cronExpression: '0 0 0 ? * 2'
        cron name: 'monthlyTrigger', startDelay: 10000, cronExpression: '0 0 0 1 * ?'
        cron name: 'lmsToHuofh', startDelay: 10000, cronExpression: '0 30 0 * * ?'
    }

    def execute(context)
    {
        // execute job
        def currentTrigger = context.trigger.key.name
        if (currentTrigger == 'everydayTrigger')
        {
            timingTaskService.execute "Everyday"
        }
        else if (currentTrigger == 'weeklyTrigger')
        {
            timingTaskService.execute "Weekly"
        }
        else if (currentTrigger == 'monthlyTrigger')
        {

            timingTaskService.execute "Monthly"
        }
        else if (currentTrigger == 'lmsToHuofh')
        {

            jieqingHFHService.sendDataTohuo()
        }
    }
}
