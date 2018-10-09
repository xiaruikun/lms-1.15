package com.next

import grails.transaction.Transactional
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@Transactional
@CompileStatic
@TypeChecked
class OpportunityStatisticsService
{
    /**
     * 导出csv文件
     * @author 王超
     * @date 2017/4/21
     * */
    def exportExcel(OutputStream out, List rowMapper, List exportData)
    {
        //写入文件头部
        StringBuffer head = new StringBuffer()
        for (
            Iterator pi = rowMapper.iterator();
                pi.hasNext();)
        {
            def property = pi.next()
            head.append(property.toString())
            if (pi.hasNext())
            {
                head.append(",")
            }
        }
        head.append("\n")
        out.write(head.toString().getBytes("GBK"))
        out.flush()
        //写入文件内容
        def buf = new StringBuffer()
        for (
            Iterator iterator = exportData.iterator();
                iterator.hasNext();)
        {
            def list = iterator.next()
            for (
                Iterator pi = list.iterator();
                    pi.hasNext();)
            {
                def property = pi.next()
                buf.append(property.toString())
                if (pi.hasNext())
                {
                    buf.append(",")
                }
            }
            if (iterator.hasNext())
            {
                buf.append("\n")
            }
        }
        out.write(buf.toString().getBytes("GBK"))
        out.flush()
        out.close()
        return null
    }
}
