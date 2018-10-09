/**
 * create by 孙建岗 2017-6-28*/
2017 / 7 / 3
号修改 { opportunity ->
    //def springSecurityService
    def sw = new java.io.StringWriter()
    def xml = new groovy.xml.MarkupBuilder(sw)
    xml.REQ {
        HEAD {
            //申请方渠道码
            ReqCode('WBJFZJXF')
            //交易代码
            MsgNo('1001')
            //报文标识号
            MsgId("WBJFZJXF" + new Date().format("yyyyMMdd") + "000001")
            //工作日期
            WorkData(new Date().format("yyyyMMdd"))
            //工作时间
            WorkTime(new Date().format("HHmmss"))
        }
    }
    def callAvicWs = { String xml2 ->
        String retMsg = null;
        try
        {
            javax.xml.namespace.QName SERVICE_NAME = new javax.xml.namespace.QName("CMISChannelService", "CMISChannelService");

            URL wsdlURL = com.zjx.service.webservice.avic.CMISChannelService.WSDL_LOCATION;

            com.zjx.service.webservice.avic.CMISChannelService ss = new com.zjx.service.webservice.avic.CMISChannelService(wsdlURL, SERVICE_NAME);
            com.zjx.service.webservice.avic.CMISChannelServicePortType port = ss.getCMISChannelServiceHttpPort();

            retMsg = port.input(xml2);
        }
        catch (java.lang.Exception e)
        {
            org.apache.commons.logging.Log.logger.error(e.getMessage(), e);
        }

        return retMsg;
    }
    def channelRecord = new com.next.ChannelRecord()
    channelRecord.startTime = new Date()
    def retXml = callAvicWs(sw.toString())
    println retXml
    def resps = new XmlParser().parseText(retXml)
    println resps.text()
    channelRecord.interfaceCode = '1001'
    channelRecord.applyCode = resps.MSG.ApplCde.text()
    channelRecord.applySeq = resps.MSG.ApplSeq.text()
    channelRecord.createdBy = com.next.User.findByUsername("zz")
    //def username = springSecurityService.getPrincipal().username
    // def user = com.next.User.findByUsername(username)
    //channelRecord.createdBy = user
    println resps.MSG.ApplSeq.text()
    channelRecord.status = resps.HEAD.ErrorMsg.text()
    channelRecord.opportunity = opportunity
    channelRecord.endTime = new Date()
    if (channelRecord.validate())
    {
        channelRecord.save flush: true
    }
    else
    {
        println channelRecord.errors
        println("cuowu")
    }
    return retXml
}