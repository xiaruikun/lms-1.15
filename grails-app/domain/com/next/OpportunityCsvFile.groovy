package com.next

class OpportunityCsvFile
{
    String filetype = '基础信息'
    String filename
    String filepath

    static constraints = {
        filetype inList: ['基础信息', '银行流水', '退单说明','最终还款','本金逾期','利息逾期']
        filetype maxSize: 16
        filename unique: true, nullable: false
        filepath nullable: false, maxSize: 1024
    }

}
