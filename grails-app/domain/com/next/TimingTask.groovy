package com.next

class TimingTask
{
    Component component

    String log

    Integer start = 0

    String duration = 'Everyday'

    static constraints = {
        log type: "text", nullable: true, blank: true
        duration inList: ['Everyday', 'Weekly', 'Monthly']
        duration nullable: true, blank: true
        start nullable: true, blank: true
    }
}
