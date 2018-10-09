/**
 * Created by 夏瑞坤 on 2017/06/27.*/
{ opportunity ->
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd")
    //周末为工作日
    def getWeekendIsWorkDateList = { ->
        def list = []
        list.add("20170401")
        list.add("20170527")
        list.add("20170930")
        return list
    }
    //平时是假期
    def getWeekdayIsHolidayList = { ->
        def list = []
        list.add("20170404")
        list.add("20170501")
        list.add("20170529")
        list.add("20170530")
        list.add("20171002")
        list.add("20171003")
        list.add("20171004")
        list.add("20171005")
        list.add("20171006")
        return list
    }
    //是否为工作日
    def isWorkday = { java.util.Calendar calendar ->
        if (calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.SATURDAY && calendar.get(java.util.Calendar.DAY_OF_WEEK) != java.util.Calendar.SUNDAY)
        {
            //平时
            return !getWeekdayIsHolidayList().contains(sdf.format(calendar.getTime()))
        }
        else
        {
            //周末
            return getWeekendIsWorkDateList().contains(sdf.format(calendar.getTime()))
        }
    }

    java.util.Calendar ca = java.util.Calendar.getInstance()
    java.util.Date date = new java.util.Date()
    ca.setTime(sdf.parse(sdf.format(date))) //sdf.format(date)
    for (
        int i = 0;
            i < 3;
            i++)
    {
        ca.add(java.util.Calendar.DAY_OF_MONTH, 1)
        if (!isWorkday(ca))
        {
            i = i - 1
        }
    }
    def opportunityFlexFieldCategory = com.next.OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opportunity, com.next.FlexFieldCategory.findByName("资金渠道"))
    if (opportunityFlexFieldCategory)
    {
        opportunityFlexFieldCategory?.fields?.each {
            if (it.name == '放款帐号有效截止时间')
            {
                if (!it?.value)
                {
                    it.value = sdf.format(ca.getTime())
                    it.save()
                }
            }
        }
    }
    return true
}