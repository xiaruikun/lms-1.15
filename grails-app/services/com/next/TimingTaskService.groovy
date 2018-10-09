package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-5-5
 */

@Transactional
class TimingTaskService
{
    ComponentService componentService

    def execute(String duration)
    {
        def result
        def timingTaskList
        if (duration == 'Everyday')
        {
            timingTaskList = TimingTask.findAll("from TimingTask where duration is null or duration = '' or duration = '${duration}' order by id asc")
        }
        else
        {
            timingTaskList = TimingTask.findAll("from TimingTask where duration = '${duration}' order by id asc")
        }
        timingTaskList?.each {
            result = componentService.evaluate it?.component, null
            it.log = result
            if (!it?.start)
            {
                it.start = 1
            }
            else
            {
                it.start += 1
            }
            it.save flush: true
        }
    }
}
