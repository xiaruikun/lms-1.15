package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils
import org.springframework.security.access.annotation.Secured

import java.text.SimpleDateFormat

@Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_PRODUCT_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_BRANCH_GENERAL_MANAGER', 'ROLE_BRANCH_OFFICE_CASHIER','ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'])
@Transactional(readOnly = true)
class OpportunityStatisticsController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def opportunityService
    def contactservice
    def springSecurityService
    def opportunityStatisticsService

    @Transactional
    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_BRANCH_GENERAL_MANAGER'])
    def edit(Opportunity opportunity)
    {
        opportunity = Opportunity.findById(opportunity.id)
        respond opportunity, model: [opportunity: opportunity]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE', 'ROLE_ACCOUNT_MANAGER', 'ROLE_COMPANY_ADMINISTRATOR', 'ROLE_GENERAL_RISK_MANAGER', 'ROLE_BRANCH_OFFICE_RISK_MANAGER', 'ROLE_HEAD_OFFICE_RISK_MANAGER', 'ROLE_GENERAL_MANAGER', 'ROLE_CRO', 'ROLE_CEO', 'ROLE_COO', 'ROLE_BRANCH_GENERAL_MANAGER'])
    @Transactional
    def update(Opportunity opportunity)
    {
        opportunity = Opportunity.findById(params.id)
        opportunity.setMemo(params.memo)
        opportunity.save()
        redirect(controller: "opportunityStatistics", action: "dailyReportList")
        return
    }

    /**
     * 查询询值报单并导出csv
     * @author 王超
     * @date 2017/4/21
     * */
    @Transactional
    def dailyReportList(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def stage = params.stage
        def report = params.report
        def count
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def ids
        def list = []
        def stageList = OpportunityStage.findAll()
        def sql
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")))
        {
            sql = 'select o.serialNumber,u.fullName,o.fullName,uc.fullName,a.name,o.loanAmount,o.requestedAmount,p.name,os.name,cof.description,o.actualAmountOfCredit,o.createdDate,m.name,o.memo,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.causeOfFailure cof LEFT JOIN o.mortgageType m LEFT JOIN u.city ct where 1=1'
            if (city != "--CITY--" && city)
            {
                sql += " and ct.name = '${city}'"
            }
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            sql = "select o.serialNumber,u.fullName,o.fullName,uc.fullName,a.name,o.loanAmount,o.requestedAmount,p.name,os.name,cof.description,o.actualAmountOfCredit,o.createdDate,m.name,o.memo,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.causeOfFailure cof LEFT JOIN o.mortgageType m LEFT JOIN u.city ct where ct.name = '${user?.city?.name}'"
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql = 'select o.serialNumber,u.fullName,o.fullName,uc.fullName,a.name,o.loanAmount,o.requestedAmount,p.name,os.name,cof.description,o.actualAmountOfCredit,o.createdDate,m.name,o.memo,o.id from OpportunityTeam ot left join ot.user ou left join ot.opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.causeOfFailure cof LEFT JOIN o.mortgageType m LEFT JOIN u.city ct where 1=1'
            sql += " and ou.id in (${user.id})"
        }
        if (startTime && endTime)
        {
            sql += " and o.createdDate between '${startTime}' AND '${endTime}'"
        }
        if (stage == '询值记录')
        {
            sql += " and os.code in ('02','15')"
        }
        else if (stage != "--STATUS--" && stage != '询值记录' && stage != '报单记录' && stage)
        {
            sql += " and os.name = '${stage}'"
        }
        else if (stage == '报单记录')
        {
            sql += " and os.id >= 3 and os.id <> 12"
        }
        sql += " and (p is null or p.name != '新e贷')"
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by o.createdDate")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by o.createdDate desc", [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql + " order by o.createdDate desc")?.size
        ids.each {
            def mapList = []
            def houseInfo = Collateral.executeQuery("select ct.name,c.projectName,c.building,c.unit,c.floor,c.totalFloor,c.roomNumber,c.orientation,c.area,c.houseType,c.assetType,c.specialFactors,c.appliedTotalPrice from Collateral c left join c.opportunity o left join o.user.city ct where o.id = ${it[14]}")
            mapList.add(it[0])
            mapList.add(it[1])
            mapList.add(it[2])
            mapList.add(it[3])
            mapList.add(it[4])
            if (houseInfo[0])
            {
                mapList.add(houseInfo[0][0] + ';' + houseInfo[0][1] + ';' + houseInfo[0][2] + '号楼' + houseInfo[0][3] + '单元' + houseInfo[0][4] + '/' + houseInfo[0][5] + ';' + houseInfo[0][6] + ';' + houseInfo[0][7] + ';' + houseInfo[0][8] + ';' + houseInfo[0][9] + ';')
                mapList.add(houseInfo[0][10])
                mapList.add(houseInfo[0][11])
            }
            else
            {
                mapList.add("")
                mapList.add("")
                mapList.add("")
            }
            mapList.add(it[5])
            if (houseInfo[0])
            {
                mapList.add(houseInfo[0][12])
            }
            else
            {
                mapList.add(0)
            }
            mapList.add(it[6])
            mapList.add(it[7])
            mapList.add(it[8])
            mapList.add(it[9])
            mapList.add(it[10])
            mapList.add(it[11])
            mapList.add(it[12])
            mapList.add(it[13])
            mapList.add(it[14])
            list.add(mapList)
        }
        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "销售员", "客户姓名", "经纪人", "组别", "房屋信息", "房屋类别", "特殊原因", "申请金额", "客户要求", "报单规模", "产品类型", "订单状态", "失败原因", "成交规模", "订单时间", "抵押类别", "失败详情", "订单ID"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count, stageList: stageList]
        }
    }

    /**
     * 报单数据分析，图表展示
     * @author 王超
     * @date 2017/4/21
     * */
    @Transactional
    def dailyReport()
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def list
        def list1
        def list2
        def map = [:]
        def countFaile
        def countFaile1
        def countStep1
        def countStep2
        def countStep3
        def countStep4
        def countQuery
        def countAll
        //基础材料以提供
        def stepCode1 = OpportunityFlow.executeQuery("select f.executionSequence from OpportunityFlow f,OpportunityStage s where s.code = '04' and f.stage.id = s.id order by f.id desc")[0]
        //面谈已完成
        def stepCode2 = OpportunityFlow.executeQuery("select f.executionSequence from OpportunityFlow f,OpportunityStage s where s.code = '26' and f.stage.id = s.id order by f.id desc")[0]
        //审批已完成
        def stepCode3 = OpportunityFlow.executeQuery("select f.executionSequence from OpportunityFlow f,OpportunityStage s where s.code = '08' and f.stage.id = s.id order by f.id desc")[0]
        //放款已完成
        def stepCode4 = OpportunityFlow.executeQuery("select f.executionSequence from OpportunityFlow f,OpportunityStage s where s.code = '10' and f.stage.id = s.id order by f.id desc")[0]
        def sql = " from OpportunityFlow f left join f.opportunity o  RIGHT JOIN o.collaterals cl left join o.user.city c LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.contact uc left join o.stage os where 1=1 "
        if (startTime && endTime)
        {
            sql += " and o.createdDate between '${startTime}' AND '${endTime}' "
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")))
        {
            if (city != "--CITY--" && city)
            {
                sql += " and c.name = '${city}'"
            }
            //询值未转报单图表分析
            list = Opportunity.executeQuery("select count(DISTINCT o.id),o.causeOfFailure ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and os.code in ('02','15') group by o.causeOfFailure")
            //报单未转成交图表分析
            list1 = Opportunity.executeQuery("select count(DISTINCT o.id),o.causeOfFailure ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence < ${stepCode4} and f.executionSequence >= ${stepCode1} group by o.causeOfFailure")
            //询值统计与曲线
            list2 = Opportunity.executeQuery("select count(DISTINCT o.id),DATE_FORMAT(o.createdDate, '%Y-%m-%d') ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and os.code in ('02','15') group by DATE_FORMAT(o.createdDate, '%Y-%m-%d')")
            //询值未转报单数
            countFaile = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence < ${stepCode1} and o.causeOfFailure != ''")
            //报单未转成交数
            countFaile1 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence < ${stepCode4} and f.executionSequence >= ${stepCode1} and o.causeOfFailure != ''")
            //报单数
            countStep1 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode1}")
            //面谈数
            countStep2 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode2}")
            //审批数
            countStep3 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode3}")
            //放款数
            countStep4 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode4}")
            //询值数
            countQuery = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and os.code in ('02','15')")
            //订单总数
            countAll = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql}")
        }
        else
        {
            //查询本地区
            sql += " and c.name = '${user.city.name}'"
            //询值未转报单图表分析
            list = Opportunity.executeQuery("select count(DISTINCT o.id),o.causeOfFailure ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and os.code in ('02','15') group by o.causeOfFailure")
            //报单未转成交图表分析
            list1 = Opportunity.executeQuery("select count(DISTINCT o.id),o.causeOfFailure ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence < ${stepCode4} and f.executionSequence >= ${stepCode1} group by o.causeOfFailure")
            //询值统计与曲线
            list2 = Opportunity.executeQuery("select count(DISTINCT o.id),DATE_FORMAT(o.createdDate, '%Y-%m-%d') ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and os.code in ('02','15') group by DATE_FORMAT(o.createdDate, '%Y-%m-%d')")
            //询值未转报单数
            countFaile = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence < ${stepCode1} and o.causeOfFailure != ''")
            //报单未转成交数
            countFaile1 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence < ${stepCode4} and f.executionSequence >= ${stepCode1} and o.causeOfFailure != ''")
            //报单数
            countStep1 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode1}")
            //面谈数
            countStep2 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode2}")
            //审批数
            countStep3 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode3}")
            //放款数
            countStep4 = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and f.executionSequence >= ${stepCode4}")
            //询值数
            countQuery = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql} and f.stage.id = o.stage.id and o.collaterals.size > 0 and os.code in ('02','15')")
            //订单总数
            countAll = Opportunity.executeQuery("select count(DISTINCT o.id) ${sql}")
        }
        map["countFaile"] = countFaile
        map["countFaile1"] = countFaile1
        map["countStep1"] = countStep1
        map["countStep2"] = countStep2
        map["countStep3"] = countStep3
        map["countStep4"] = countStep4
        map["countQuery"] = countQuery
        map["countAll"] = countAll
        map["list"] = list
        map["list1"] = list1
        map["list2"] = list2
        respond list, model: [map: map]
    }

    /**
     * 报单量和报单规模统计
     * @author 王超
     * @date 2017/4/21
     * */
    @Transactional
    def dailyReportCount(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def userList = []
        def list
        def map = [:]
        def sql = " from Opportunity o RIGHT JOIN o.collaterals c LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.product p LEFT JOIN u.city ct where 1=1 "
        if (startTime && endTime)
        {
            sql += " and o.createdDate between '${startTime}' AND '${endTime}' "
        }
        else
        {
            sql += " and o.createdDate between '2016-11-1 00:00:00' AND '2016-11-8 23:59:59' "
        }
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")))
        {
            if (city != "--CITY--" && city)
            {
                sql += " and ct.name = '${city}'"
            }
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            sql += "and ct.name = '${user?.city?.name}'"
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql += " and u.id in (${user.id})"
        }
        sql += " and (p is null or p.name != '新e贷')"
        def listUser = Opportunity.executeQuery("select DISTINCT u.id ${sql}", [max: max, offset: offset])
        def listSize = Opportunity.executeQuery("select DISTINCT u.id ${sql}")?.size
        listUser.each {
            def mapUser = [:]
            def user1 = Opportunity.executeQuery("select u.fullName,o.actualAmountOfCredit,o.requestedAmount ${sql} and u.id = ${it}")
            //融e贷
            def user2 = Opportunity.executeQuery("select o.actualAmountOfCredit ${sql} and p.id = '2' and u.id = ${it}")
            //速e贷
            def user3 = Opportunity.executeQuery("select o.actualAmountOfCredit ${sql} and p.id = '1' and u.id = ${it}")
            //报单
            def user4 = Opportunity.executeQuery("select c.appliedTotalPrice ${sql} and c.appliedTotalPrice <> '' and u.id = ${it}")
            //user1
            def sumActualAmountOfCredit = 0, countActualAmountOfCredit = 0, sumRequestedAmount = 0, countRequestedAmount = 0
            user1.each {
                if (it[1])
                {
                    countActualAmountOfCredit += 1
                    sumActualAmountOfCredit += it[1]
                }
                if (it[2])
                {
                    countRequestedAmount += 1
                    sumRequestedAmount += it[2]
                }
            }
            mapUser["userId"] = it
            mapUser["fullName"] = user1[0][0]
            mapUser["sumActualAmountOfCredit"] = sumActualAmountOfCredit
            mapUser["countActualAmountOfCredit"] = countActualAmountOfCredit
            mapUser["sumRequestedAmount"] = sumRequestedAmount
            mapUser["countRequestedAmount"] = countRequestedAmount

            def sumActualAmountOfCredit1 = 0, countActualAmountOfCredit1 = 0
            user2.each {
                if (it)
                {
                    countActualAmountOfCredit1 += 1
                    sumActualAmountOfCredit1 += it
                }
            }
            mapUser["sumActualAmountOfCredit1"] = sumActualAmountOfCredit1
            mapUser["countActualAmountOfCredit1"] = countActualAmountOfCredit1
            mapUser["sumActualAmountOfCredit1"] = sumActualAmountOfCredit1
            mapUser["countActualAmountOfCredit1"] = countActualAmountOfCredit1

            def sumActualAmountOfCredit2 = 0, countActualAmountOfCredit2 = 0
            user3.each {
                if (it)
                {
                    countActualAmountOfCredit2 += 1
                    sumActualAmountOfCredit2 += it
                }
            }
            mapUser["sumActualAmountOfCredit2"] = sumActualAmountOfCredit2
            mapUser["countActualAmountOfCredit2"] = countActualAmountOfCredit2
            mapUser["sumActualAmountOfCredit2"] = sumActualAmountOfCredit2
            mapUser["countActualAmountOfCredit2"] = countActualAmountOfCredit2

            def sumAppliedTotalPrice = 0, countAppliedTotalPrice = 0
            user4.each {
                if (it)
                {
                    countAppliedTotalPrice += 1
                    sumAppliedTotalPrice += it
                }
            }
            mapUser["sumAppliedTotalPrice"] = sumAppliedTotalPrice
            mapUser["countAppliedTotalPrice"] = countAppliedTotalPrice
            userList.add(mapUser)
        }
        map["list"] = userList
        map["listSize"] = listSize

        respond userList, model: [map: map]
    }

    @Transactional
    def dailyReportSearch(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def startTime = params.startTime
        def endTime = params.endTime
        def orderList = []
        def map = [:]
        def sql = " where 1=1 "
        if (startTime && endTime)
        {
            sql += " and o.createdDate between '${startTime}' AND '${endTime}' "
        }
        else
        {
            sql += " and o.createdDate between '2016-11-1 00:00:00' AND '2016-11-8 23:59:59' "
        }

        def opportunitys = Opportunity.executeQuery("select DISTINCT o.id from Opportunity o left join o.stage s ${sql} and s.id > '4'", [max: max, offset: offset])
        def opportunitysSize = Opportunity.executeQuery("select count(DISTINCT o.id) from Opportunity o left join o.stage s ${sql} and s.id > '4'")
        opportunitys.each {
            def mapo = [:]
            //订单信息
            def opportunity = Opportunity.executeQuery("select DISTINCT o.id,o.fullName,o.externalId,c.address from Opportunity o right join o.collaterals c where o.id = ${it}")
            //家访信息（多条）
            def activitys = Activity.executeQuery("select a.actualStartTime,u.fullName from Activity a left join a.assignedTo u where a.opportunity.id = ${it}")
            mapo["opportunity"] = opportunity
            mapo["activitys"] = activitys
            orderList.add(mapo)
        }
        map["list"] = orderList
        map["size"] = opportunitysSize[0]
        println opportunitysSize
        respond orderList, model: [map: map]
    }

    //为华夏导阶段数据
    @Transactional
    def dailyReportList1()
    {
        def startTime = "2016-11-08 00:00:00"
        def endTime = "2016-11-21 23:59:59"
        def listReport = []
        def sql = "select DISTINCT o.id from Opportunity o where o.createdDate between '${startTime}' AND '${endTime}' and o.stage.id in(5,6,7,4,26,16,28)"
        def ids = Opportunity.executeQuery(sql + " order by o.id")
        ids.each {
            def mapList = []
            def m = Opportunity.executeQuery("select o.serialNumber from Opportunity o where o.id = ${it}")
            def u = Opportunity.executeQuery("select o.fullName from Opportunity o where o.id = ${it}")
            //信息已完善（房产初审已完成）
            def m1 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 16")
            //房产初审已完成（产调已完成）
            def m2 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 7")
            //产调已完成（征信查询已完成）
            def m3 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 5")
            //基础材料已提供（信息已完善）
            def m4 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 4")
            //面谈已完成（复审已完成）
            def m5 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 26")
            //贷款审批已完成（总部风控经理）（审批完成）
            def m6 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 28")
            //征信查询已完成（面谈已完成）
            def m7 = OpportunityFlow.executeQuery("select f.endTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 6")

            mapList.add(m[0])
            mapList.add(u[0])
            mapList.add(m1[0])
            mapList.add(m2[0])
            mapList.add(m3[0])
            mapList.add(m4[0])
            mapList.add(m5[0])
            mapList.add(m6[0])
            mapList.add(m7[0])
            listReport.add(mapList)
        }
        def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "贷款人", "房产初审已完成", "产调已完成", "征信查询已完成", "信息已完善", "复审已完成", "审批完成", "面谈已完成"))
        response.setContentType("text/csv")
        response.setContentType("application/csv;charset=GBK")
        response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
        OutputStream out = null
        try
        {
            out = response.getOutputStream()
        }
        catch (Exception e)
        {
            e.printStackTrace()
        }
        opportunityStatisticsService.exportExcel(out, listTitle, listReport)
        return null
    }

    //为尹总导阶段数据
    @Transactional
    def dailyReport1(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def report = params.report
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def city = params.city
        def ids
        def sql
        def listReport = []
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")))
        {
            sql = "select DISTINCT o.id from Opportunity o left join o.user.city c where o.stage.id in (129,20,10,89)"
            if (city != "--CITY--" && city)
            {
                sql += " and c.name = '${city}'"
            }
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            sql = "select DISTINCT o.id from Opportunity o left join o.user u left join o.user.city c where o.stage.id in (129,20,10,89) and c.name = '${user?.city?.name}'"
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql = "select DISTINCT o.id from Opportunity o left join o.user u left join o.user.city c where o.stage.id in (129,20,10,89)"
            sql += " and u.id in (${user.id})"
        }
        if (startTime && endTime)
        {
            sql += " and o.createdDate between '${startTime}' AND '${endTime}'"
        }
        else if (startTime && !endTime)
        {
            sql += " and o.createdDate >= '${startTime}' "
        }
        else if (!startTime && !endTime)
        {
            sql += " and o.createdDate >= '2016-10-01 00:00:00' "
        }
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by o.id")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by o.id", [max: max, offset: offset])
        }
        def count = Opportunity.executeQuery(sql + " order by o.id")?.size
        ids.each {
            def mapList = []
            def m = Opportunity.executeQuery("select o.serialNumber from Opportunity o where o.id = ${it}")
            def n = Opportunity.executeQuery("select o.fullName from Opportunity o where o.id = ${it}")
            def t = Opportunity.executeQuery("select o.createdDate from Opportunity o where o.id = ${it}")
            def m1 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 1")
            def m2 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 2")
            def m4 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 4")
            def m6 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 6")
            def m7 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 7")
            def m8 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 8")
            def m9 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 9")
            def m10 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 10")
            def m12 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 12")
            def m16 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 16")
            def m17 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 17")
            def m19 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 19")
            def m20 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 20")
            def m26 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 26")
            def m28 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 28")
            def m29 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 29")
            def m30 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 30")
            def m31 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 31")
            def m33 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 33")
            def m34 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 34")
            def m35 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 35")
            def m36 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 36")
            def m38 = OpportunityFlow.executeQuery("select f.startTime from OpportunityFlow f where f.opportunity.id = ${it} and f.stage.id = 38")

            mapList.add(m[0])
            mapList.add(n[0])
            mapList.add(t[0])
            mapList.add(m1[0])
            mapList.add(m12[0])
            mapList.add(m2[0])
            mapList.add(m4[0])
            mapList.add(m16[0])
            mapList.add(m7[0])
            mapList.add(m6[0])
            mapList.add(m26[0])
            mapList.add(m17[0])
            mapList.add(m28[0])
            mapList.add(m29[0])
            mapList.add(m30[0])
            mapList.add(m31[0])
            mapList.add(m8[0])
            mapList.add(m19[0])
            mapList.add(m9[0])
            mapList.add(m38[0])
            mapList.add(m33[0])
            mapList.add(m34[0])
            mapList.add(m35[0])
            mapList.add(m36[0])
            mapList.add(m10[0])
            mapList.add(m20[0])
            listReport.add(mapList)
        }
        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "贷款人", "订单创建时间", "评房申请已提交", "价格待确认", "评房已完成", "基础材料已提供", "信息已完善", "房产初审已完成", "征信查询已完成", "面谈已完成", "复审已完成", "贷款审批已完成（总部风控经理）", "贷款审批已完成（风控总经理）", "贷款审批已完成（业务总经理）", "贷款审批已完成（CRO）", "审批已完成", "合同已签署", "抵押公证手续已完成", "复审已完成（放款）", "放款审批已完成（总部风控经理）", "放款审批已完成（风控总经理）", "放款审批已完成（业务总经理）", "放款审批已完成（CRO）", "放款已完成", "抵押品已入库"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, listReport)
            return null
        }
        else
        {
            respond listReport, model: [list: listReport, count: count]
        }
    }

    /**
     * 资金部需求
     * @param yuanchao
     * @return
     */
    def dailyReport2(Integer max)
    {
        /**
         * 订单号，城市，产品类型，共同借款人，
         * 批贷金额，放款金额，借款期限，销售主管，销售员，风控主单员，
         * 实际放款日期，状态，放款凭证，
         * 确认路径日期，放款通道，放款账户，*/
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def serialNumber = params.serialNumber
        def fullName = params.fullName
        def city = params.city
        def productType = params.product
        def loanDate = params.loanDate
        //放款时间
        def loanDoc = params.loanDoc
        def pathDate = params.pathDate
        //通道
        def flexField = params["flexField"]
        def flexFieldBankAccount = params["flexFieldBankAccount"]
        def lastDate = params.lastDate
        def report = params.report
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def sql = "select id from Opportunity as o  where 1=1 and status != 'Failed' "
        if (serialNumber)
        {
            sql += "and o.serialNumber like '%${serialNumber}%'"
        }
        if (fullName)
        {
            sql += "and o.id in (select opportunity.id from OpportunityContact where contact.id in (select id from Contact where fullName like '%${fullName}%' ) ) "
        }
        if (city && city != "null")
        {
            sql += "and o.contact.city.name = '${city}'"
        }
        if (productType && productType != 'null')
        {
            sql += "and o.product.name = '${productType}'"
        }
        if (loanDate && loanDate != 'null')
        {
            loanDate = loanDate.substring(0, 10)
            //            sql += "and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '预计放款时间' and f.value like '%${flexField}%'))"
            sql += "and Convert(varchar,o.actualLendingDate,120) like '%${loanDate}%'"
        }
        if (loanDoc && loanDoc != 'null')
        {
            sql += "and o.mortgageCertificateType.name = '${loanDoc}'"
        }

        if (flexField && flexField != "null")
        {
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款通道' and f.value= '${flexField}'))"
        }
        if (flexFieldBankAccount && flexFieldBankAccount != "null")
        {
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
        }

        if (pathDate && pathDate != "null")
        {
            pathDate = pathDate.substring(0, 10)
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlow as p where p.stage.code = 49 and o.id = p.opportunity.id and Convert(varchar,p.startTime,120) like '%${pathDate}%')"
        }
        if (lastDate && lastDate != "null")
        {
            lastDate = lastDate.substring(0, 10)
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlow as p where p.stage.code = 10 and o.id = p.opportunity.id and Convert(varchar,p.startTime,120) like '%${lastDate}%')"
        }

        println sql
        def ids = Opportunity.executeQuery(sql + " order by createdDate DESC", [max: max, offset: offset])
        def ids1 = Opportunity.executeQuery(sql + " order by createdDate DESC")
        def count = Opportunity.executeQuery(sql + " order by createdDate DESC")?.size





        def listReport = []
        def id
        if (report == "yes")
        {
            id = ids1
        }
        else
        {
            id = ids
        }
        id.each {
            def map = []
            def opp = Opportunity.findById(it)
            map.add(opp.serialNumber) //1
            if (opp.contact?.city?.name)
            {
                //2
                map.add(opp.contact?.city?.name)
            }
            else
            {
                map.add("")
            }
            if (opp.product?.name)
            {
                //3
                map.add(opp.product?.name)
            }
            else
            {
                map.add("")
            }
            def opportunityContacts = OpportunityContact.findAllByOpportunity(opp)
            def contactFullName = ""
            if (opportunityContacts)
            {
                for (
                    def opportunityContact in
                        opportunityContacts)
                {
                    contactFullName = contactFullName + opportunityContact?.contact?.fullName + "、"
                }
            }
            if (contactFullName)
            {
                map.add(contactFullName.substring(0, contactFullName.lastIndexOf("、"))) //4
            }
            else
            {
                map.add(contactFullName)
            }
            map.add(opp.actualAmountOfCredit) //5
            map.add(opp.actualAmountOfCredit) //6
            map.add(opp.actualLoanDuration) //7
            map.add(opp.account?.name) //9
            map.add(opp.user?.fullName) //8

            if (opp.contact?.city?.name)
            {
                if (opp.contact.city.name == "北京" || opp.contact.city.name == "上海")
                {
                    def stage1 = OpportunityStage.findByName("面谈已完成")
                    def teamRole = TeamRole.findByName("Approval")
                    def role = OpportunityRole.findByOpportunityAndStageAndTeamRole(opp, stage1, teamRole)?.user?.fullName
                    if (role)
                    {
                        map.add(role) //10
                    }
                    else
                    {
                        map.add("")
                    }

                }
                else
                {
                    def stage1 = OpportunityStage.findByName("房产初审已完成")
                    def teamRole = TeamRole.findByName("Approval")
                    def role = OpportunityRole.findByOpportunityAndStageAndTeamRole(opp, stage1, teamRole)?.user?.fullName
                    if (role)
                    {
                        map.add(role)
                    }
                    else
                    {
                        map.add("")
                    }
                }
            }
            else
            {
                map.add("")
            }


            def fieldsList = OpportunityFlexFieldCategory.findAllByOpportunity(opp).fields
            def door
            def account
            def loanTime
            fieldsList.each {
                it.each {
                    if (it.name == "放款通道")
                    {
                        door = it.value
                    }
                    if (it.name == "放款账号")
                    {
                        account = it.value
                    }
                    //                    if (it.name == "预计放款时间"){
                    //                        loanTime = it.value
                    //                    }

                }

                // door = OpportunityFlexField.findByNameAnd("放款通道",it)
                //account = OpportunityFlexField.findByNameAndCategory("放款账户",it)
            }
            loanTime = opp.actualLendingDate //实际放款时间
            if (loanTime)
            {
                map.add(sdf.format(loanTime)) //11
            }
            else
            {
                map.add("")
            }

            map.add(opp.stage?.name) //12
            if (opp.mortgageCertificateType?.name)
            {
                map.add(opp.mortgageCertificateType.name) //13
            }
            else
            {
                map.add("")
            }

            def stage = OpportunityStage.findByCode("49")
            def endTime = OpportunityFlow.findByOpportunityAndStage(opp, stage)?.endTime
            map.add(endTime) //14

            if (door)
            {
                map.add(door) //15
            }
            else
            {
                map.add("")
            }
            if (account)
            {
                map.add(account)
            }
            else
            {
                map.add("") //16
            }
            listReport.add(map)

        }
        //  render listReport

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "城市", "产品类型", "共同借款人", "批贷金额", "放款金额", "借款期限", "销售主管", "销售员", "风控主单员", "实际放款日期", "订单阶段", "放款凭证", "确认路径时间", "放款通道", "放款账户"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, listReport)
            return null
        }
        else
        {
            respond listReport, model: [list: listReport, count: count, params: params]
        }
    }

    /**
     * @author yuanchao
     * @return
     * 张莹 需求
     */
    def dailyReport3(Integer max)
    {
        def username = springSecurityService.getPrincipal().username
        def usr = User.findByUsername(username)
        def cityName = usr.city.name
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startDate
        def endTime = params.endDate
        def report = params.report
        def sql = "select id  from Opportunity where 1=1  and type.name = '抵押贷款' and user.cellphone not in ('13718807260','18334787364','15801173559','13581657148','15201108192','15811351299','13831182365','18811374407','18513603065') and status != 'Failed'"
        def sqlm = "select sum(actualAmountOfCredit)  from Opportunity where 1=1  and  type.name = '抵押贷款' and  user.cellphone not in ('13718807260','18334787364','15801173559','13581657148','15201108192','15811351299','13831182365','18811374407','18513603065') and status != 'Failed'"
        if ((UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_COO"))) && params.city)
        {
            if (params.city != "--CITY--" && params.city)
            {
                sql += "and contact.city.name = '${params.city}' and user.city.name = '${params.city}'"
                sqlm += "and contact.city.name = '${params.city}' and user.city.name = '${params.city}'"
            }
        }
        else
        {
            sql += "and contact.city.name = '${cityName}' and user.city.name = '${cityName}'"
            sqlm += "and contact.city.name = '${cityName}' and user.city.name = '${cityName}'"
        }
        def stage = params.stage
        println stage
        def stageID = "8"
        if (stage == '批贷已完成')
        {
            stageID = "8"
        }
        else if (stage == "放款已完成")
        {
            stageID = "10"
        }
        else if (stage == "评估")
        {
            stageID = "0"
        }
        else if (stage == "报单")
        {
            stageID = "4"
        }

        if (startTime && endTime && stageID == "8")
        {
            sql += "and id in (select opportunity.id from OpportunityFlow where startTime between '${startTime}' and '${endTime}' and stage.id = ${stageID}) and product.name != '新e贷'"
            sqlm += "and id in (select opportunity.id from OpportunityFlow where startTime between '${startTime}' and '${endTime}' and stage.id = ${stageID}) and product.name != '新e贷'"
        }
        else if (startTime && endTime && stageID == "10")
        {
            sql += "and actualLendingDate between '${startTime}' and '${endTime}' and product.name != '新e贷'"
            sqlm += "and actualLendingDate between '${startTime}' and '${endTime}' and product.name != '新e贷'"
        }
        else if (startTime && endTime && stageID == "0")
        {
            sql += "and createdDate between '${startTime}' and '${endTime}' and importDate is null and externalModifiedDate is null "
            sqlm += "and createdDate between '${startTime}' and '${endTime}'  and importDate is null and externalModifiedDate is null "
        }
        else if (startTime && endTime && stageID == "4")
        {
            sql += "and id in (select opportunity.id from OpportunityFlow where endTime between '${startTime}' and '${endTime}' and stage.id = ${stageID}) "
            sqlm += "and id in (select opportunity.id from OpportunityFlow where endTime between '${startTime}' and '${endTime}' and stage.id = ${stageID}) "
        }
        if (usr.department?.name == '销售组')
        {
            sql += "and id  in (select opportunity.id from OpportunityTeam where user.id = ${usr.id})"
            sqlm += "and id  in (select opportunity.id from OpportunityTeam where user.id = ${usr.id})"
        }
        println sql
        def ids
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by createdDate DESC")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by createdDate DESC", [max: max, offset: offset])
        }
        def count = Opportunity.executeQuery(sql + " order by createdDate DESC")?.size
        def totalMoney = Opportunity.executeQuery(sqlm)[0]
        def listReport = []
        ids.each {
            def list = []
            def opp = Opportunity.findById(it)
            def sdf = new SimpleDateFormat("yyyy-MM-dd")
            //合同编号
            def externalId = opp.externalId
            def contract = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id = ${opp.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
            if (contract)
            {
                list.add(contract)
            }
            else
            {
                list.add(externalId)
            }
            //评估日期
            def createdDate = opp.createdDate
            if (createdDate)
            {
                createdDate = sdf.format(createdDate)
            }
            list.add(createdDate)
            //保单日期
            def stageb = OpportunityStage.findByName("信息已完善")
            def dateb = OpportunityFlow.findByOpportunityAndStage(opp, stageb)?.startTime
            if (dateb)
            {
                dateb = sdf.format(dateb)
            }
            list.add(dateb)
            //审批日期
            def stage1 = OpportunityStage.findByName("审批已完成")
            def date = OpportunityFlow.findByOpportunityAndStage(opp, stage1)?.startTime
            if (date)
            {
                date = sdf.format(date)
            }
            list.add(date)
            //放款日期
            def loanDate = opp.actualLendingDate
            if (loanDate)
            {
                list.add(sdf.format(loanDate))
            }
            else
            {
                list.add("")
            }

            //贷款到期日
            list.add("")
            //抵押类型
            String mortgageType = ""
            Collateral.findAllByOpportunity(opp).each {
                mortgageType += it.mortgageType?.name + "、"
            }
            if (mortgageType)
            {
                mortgageType = mortgageType.substring(0, mortgageType.lastIndexOf("、"))
            }
            list.add(mortgageType == "null" ? "" : mortgageType)
            //渠道来源
            list.add("")
            //  渠道名称
            def qudao = opp.account.name
            list.add(qudao)
            //客户资质
            def level = opp.lender?.level?.description
            list.add(level)
            //借款人姓名
            def fullName = ""
            def birthList = []
            def opportunityContacts = OpportunityContact.findAllByOpportunity(opp)
            println opportunityContacts
            opportunityContacts.each {
                fullName += it.contact.fullName + '、'
                if (it.contact.idNumber)
                {
                    birthList.add(new Date().format("yyyy").toInteger().minus(it.contact.idNumber[6..9].toInteger()))
                }

            }
            if (fullName)
            {
                fullName = fullName.substring(0, fullName.lastIndexOf("、"))
            }
            list.add(fullName)
            //借款人年龄             (最大年龄者）
            //${new Date().format("yyyy").toInteger().minus(it?.contact?.idNumber[6..9].toInteger())}
            def maxAge = birthList.max()
            list.add(maxAge)
            //区县
            def district = ""
            def totalPrice = ""
            def area = ""
            Collateral.findAllByOpportunity(opp).each {
                district += it.district + "、"
                totalPrice += it.totalPrice + "、"
                area += it.area + "、"
            }
            if (district)
            {
                district = district.substring(0, district.lastIndexOf("、"))
            }
            list.add(district)
            // 抵押物地址
            String address = ""
            Collateral.findAllByOpportunity(opp).each {
                address += it.address + "、"
            }
            if (address)
            {
                address = address.substring(0, address.lastIndexOf("、"))
            }
            list.add(address)
            //拟借款金额
            def requestedAmount = opp.requestedAmount
            list.add(requestedAmount)
            //评估总价
            if (totalPrice)
            {
                totalPrice = totalPrice.substring(0, totalPrice.lastIndexOf("、"))
            }
            list.add(totalPrice)
            //房屋面积
            if (area)
            {
                area = area.substring(0, area.lastIndexOf("、"))
            }
            list.add(area)

            // 贷款金额（元）
            def actualAmountOfCredit = opp.actualAmountOfCredit
            list.add(actualAmountOfCredit)
            //贷款期限(月）
            def actualLoanDuration = opp.actualLoanDuration
            list.add(actualLoanDuration)


            def monthlyInterest = 0
            def serviceCharge = 0
            def commissionRate = 0
            def commissionPaymentMethod = ""
            if (opp.interest.size() > 2)
            {
                opp.interest.each {
                    if (it.productInterestType.name == "基本费率")
                    {
                        if (!it.installments)
                        {
                            monthlyInterest += it.rate / actualLoanDuration
                        }
                        else
                        {
                            monthlyInterest += it.rate
                        }

                    }
                    if (it.productInterestType.name == "服务费费率")
                    {
                        if (!it.installments)
                        {
                            serviceCharge += it.rate / actualLoanDuration
                        }
                        else
                        {
                            serviceCharge += it.rate
                        }

                    }
                    if (it.productInterestType.name == "渠道返费费率")
                    {

                        if (!it.installments)
                        {
                            commissionRate += it.rate / actualLoanDuration
                            commissionPaymentMethod = "一次性"
                        }
                        else
                        {
                            commissionRate += it.rate
                            commissionPaymentMethod = "分期"
                        }
                    }
                    if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域")
                    {
                        if (it.contractType?.name == "借款合同")
                        {
                            if (!it.installments)
                            {
                                monthlyInterest += it.rate / actualLoanDuration
                            }
                            else
                            {
                                monthlyInterest += it.rate //费率金额 月
                            }
                        }
                        if (it.contractType?.name == "委托借款代理服务合同")
                        {
                            if (!it.installments)
                            {
                                serviceCharge += it.rate / actualLoanDuration
                            }
                            else
                            {
                                serviceCharge += it.rate //费率金额 月
                            }
                        }

                    }
                }
            }
            else
            {
                monthlyInterest = opp.monthlyInterest / 100
                commissionRate = opp.commissionRate / 100
                serviceCharge = opp.serviceCharge / 100
                commissionPaymentMethod = opp.commissionPaymentMethod?.name
            }

            // 基本利率

            list.add(monthlyInterest + "%")
            //   服务费率
            list.add(serviceCharge + "%")
            // 返费方式
            list.add(commissionPaymentMethod)
            //返费比率
            list.add(commissionRate + "%")
            //  组别
            def acconut = opp.account.name
            list.add(acconut)
            // 销售员
            def user = opp.user.fullName
            list.add(user)
            //  风控主单
            def stage3 = OpportunityStage.findByName("面谈已完成")
            def fengkongUser = OpportunityRole.findByOpportunityAndStage(opp, stage3)?.user?.fullName
            list.add(fengkongUser)
            //  客服专员
            def stage2 = OpportunityStage.findByName("信息已完善")
            def kefuUser = OpportunityRole.findByOpportunityAndStage(opp, stage2)?.user?.fullName
            list.add(kefuUser)
            listReport.add(list)
        }
        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同编号", "评估日期", "报单日期", "审批日期", "放款日期", "贷款到期日", "抵押类型", "渠道来源", "渠道名称", "客户资质", "借款人姓名", "借款人年龄(最大)", "区县", "抵押物地址", "拟借款金额", "评估总价", "房屋面积", "贷款金额（元）", "贷款期限（月）", "基本利率", "服务费率", "返费方式", "返费比率", "组别", "销售员", "风控主单", "客服专员"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, listReport)
            return null
        }
        else
        {
            respond listReport, model: [list: listReport, totalMoney: totalMoney, count: count, params: params]
        }
    }

    /**
     *
     * 吴佳肴需求贷款审批完成
     * @param yuanchao
     */
    def dailyReport4(Integer max)
    {
        def username = springSecurityService.getPrincipal().username
        def usr = User.findByUsername(username)
        def cityName = usr.city.name
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startDate
        def endTime = params.endDate
        def report = params.report
        def sql = "select id  from Opportunity where 1=1 and  product.name != '新e贷' and  user.cellphone not in ('13718807260','18334787364','15801173559','13581657148','15201108192','15811351299','13831182365','18811374407','18513603065') and status != 'Failed'"
        def stage = params.stage
        println stage
        if ((UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(usr, Role.findByAuthority("ROLE_COO"))) && params.city)
        {
            if (params.city != "--CITY--" && params.city)
            {
                sql += "and contact.city.name = '${params.city}' and user.city.name = '${params.city}'"
            }
        }
        else
        {
            sql += "and contact.city.name = '${cityName}' and user.city.name = '${cityName}'"
        }
        def stageID = "8"
        if (stage == '批贷已完成')
        {
            stageID = "8"
        }
        else if (stage == "放款已完成")
        {
            stageID = "10"
        }
        params.stageID = stageID
        if (startTime && endTime && stageID == "8")
        {
            sql += "and id in (select opportunity.id from OpportunityFlow where startTime between '${startTime}' and '${endTime}' and stage.id = ${stageID})"
        }
        if (startTime && endTime && stageID == "10")
        {
            sql += "and actualLendingDate between '${startTime}' and '${endTime}'"
        }
        if (usr.department?.name == '销售组')
        {
            sql += "and id  in (select opportunity.id from OpportunityTeam where user.id = ${usr.id})"
        }
        println sql
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        def ids
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by createdDate DESC")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by createdDate DESC", [max: max, offset: offset])
        }

        def count = Opportunity.executeQuery(sql + " order by createdDate DESC")?.size
        /* 签约日期	贷款金额（万）	销售员	委托协议编号	贷款类型
         客户评级	客户类别	借款人姓名	区域	房屋地址
         客户来源	渠道明细	销售组别	月息	借款期限
         返费说明	扣息方式*/
        def list = []
        ids.each {
            def innerlist = []
            def opp = Opportunity.findById(it)
            if (stageID == "8")
            {
                // 订单号
                innerlist.add(opp.serialNumber)
                //签约日期
                def stage1 = OpportunityStage.findByName("审批已完成")
                def signDate = OpportunityFlow.findByOpportunityAndStage(opp, stage1)?.startTime
                if (signDate)
                {
                    signDate = sdf.format(signDate)
                }
                innerlist.add(signDate)
                //贷款金额
                def actualAmountOfCredit = opp.actualAmountOfCredit
                innerlist.add(actualAmountOfCredit)
                //销售员
                def sales = opp.user.fullName
                innerlist.add(sales)
                //委托协议编号
                def externalId = opp.externalId
                def contract = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id = ${opp.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
                if (contract)
                {
                    innerlist.add(contract)
                }
                else
                {
                    innerlist.add(externalId)
                }
                //贷款类型
                innerlist.add("资金业务")
                //客户评级
                def level = opp.lender?.level?.description
                innerlist.add(level)
                //抵押类型
                String mortgageType = ""
                Collateral.findAllByOpportunity(opp).each {
                    mortgageType += it.mortgageType?.name + "、"
                }
                if (mortgageType)
                {
                    mortgageType = mortgageType.substring(0, mortgageType.lastIndexOf("、"))
                }
                innerlist.add(mortgageType == "null" ? "" : mortgageType)
                //借款人姓名
                def fullName = ""
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opp)
                opportunityContacts.each {
                    fullName += it.contact.fullName + '、'
                }
                if (fullName)
                {
                    fullName = fullName.substring(0, fullName.lastIndexOf("、"))
                }
                innerlist.add(fullName)
                //区域
                innerlist.add("")
                //房屋地址
                String address = ""
                Collateral.findAllByOpportunity(opp).each {
                    address += it.address + "、"
                }
                if (address)
                {
                    address = address.substring(0, address.lastIndexOf("、"))
                }
                innerlist.add(address)
                //客户来源
                innerlist.add("")
                //渠道明细
                innerlist.add("")
                //销售组别
                def account = opp.account.name
                innerlist.add(account)

                def actualLoanDuration = opp.actualLoanDuration
                def monthly_interest = 0
                def serviceCharge = 0
                def commissionRate = 0
                def commissionPaymentMethod = ""
                def ops = OpportunityProduct.findAllByOpportunityAndProduct(opp,opp.productAccount)
                /*if (opp.interest.size() > 2)
                {*/
                ops.each {
                        if (it.productInterestType.name == "基本费率")
                        {
                            if (!it.installments)
                            {
                                monthly_interest += it.rate / actualLoanDuration
                            }
                            else
                            {
                                monthly_interest += it.rate
                            }

                        }
                        if (it.productInterestType.name == "服务费费率")
                        {
                            if (!it.installments)
                            {
                                serviceCharge += it.rate / actualLoanDuration
                            }
                            else
                            {
                                serviceCharge += it.rate
                            }

                        }
                        if (it.productInterestType.name == "渠道返费费率")
                        {

                            if (!it.installments)
                            {
                                commissionRate += it.rate / actualLoanDuration
                                commissionPaymentMethod = "一次性"
                            }
                            else
                            {
                                commissionRate += it.rate
                                commissionPaymentMethod = "分期"
                            }
                        }
                        if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域")
                        {
                            if (it.contractType?.name == "借款合同")
                            {
                                if (!it.installments)
                                {
                                    monthly_interest += it.rate / actualLoanDuration
                                }
                                else
                                {
                                    monthly_interest += it.rate //费率金额 月
                                }
                            }
                            if (it.contractType?.name == "委托借款代理服务合同")
                            {
                                if (!it.installments)
                                {
                                    serviceCharge += it.rate / actualLoanDuration
                                }
                                else
                                {
                                    serviceCharge += it.rate //费率金额 月
                                }
                            }

                        }
                    }
               /* }
                else
                {
                    monthly_interest = opp.monthlyInterest
                    commissionRate = opp.commissionRate
                    serviceCharge = opp.serviceCharge
                    commissionPaymentMethod = opp.commissionPaymentMethod?.name
                }*/

                //月息
                innerlist.add(monthly_interest + "%")
                //借款期限
                innerlist.add(actualLoanDuration)
                //渠道服务费
                innerlist.add(commissionRate + "%")
                //借款服务费
                innerlist.add(serviceCharge + "%")
                //扣息方式
                innerlist.add(commissionPaymentMethod)
                //上扣息月数
                def monthOfAdvancePaymentOfInterest = opp.monthOfAdvancePaymentOfInterest
                innerlist.add(monthOfAdvancePaymentOfInterest)
            }
            else
            {
                // 订单号
                innerlist.add(opp.serialNumber)
                //合同号
                def externalId = opp.externalId
                def contract = com.next.OpportunityContract.find("from OpportunityContract where opportunity.id = ${opp.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
                if (contract)
                {
                    innerlist.add(contract)
                }
                else
                {
                    innerlist.add(externalId)
                }

                //签约日期
                def stage1 = OpportunityStage.findByName("审批已完成")
                def signDate = OpportunityFlow.findByOpportunityAndStage(opp, stage1)?.startTime
                if (signDate)
                {
                    signDate = sdf.format(signDate)
                }
                innerlist.add(signDate)
                //产品品种
                def product = "资金业务"
                innerlist.add(product)
                //客户评级
                def level = opp.lender?.level?.description
                innerlist.add(level)
                //抵押类型
                String mortgageType = ""
                Collateral.findAllByOpportunity(opp).each {
                    mortgageType += it.mortgageType?.name + "、"
                }
                if (mortgageType)
                {
                    mortgageType = mortgageType.substring(0, mortgageType.lastIndexOf("、"))
                }
                innerlist.add(mortgageType == "null" ? "" : mortgageType)
                //业务来源
                innerlist.add("")
                //借款人姓名
                def fullName = ""
                def opportunityContacts = OpportunityContact.findAllByOpportunity(opp)
                opportunityContacts.each {
                    fullName += it.contact.fullName + '、'
                }
                if (fullName)
                {
                    fullName = fullName.substring(0, fullName.lastIndexOf("、"))
                }
                innerlist.add(fullName)
                //区域
                innerlist.add("")
                //物业区域
                def district = ""
                Collateral.findAllByOpportunity(opp).each {
                    district += it.district + "、"
                }
                if (district)
                {
                    district = district.substring(0, district.lastIndexOf("、"))
                }
                innerlist.add(district)
                //物业位置
                def address = ""
                Collateral.findAllByOpportunity(opp).each {
                    address += it.address + "、"
                }
                if (address)
                {
                    address = address.substring(0, address.lastIndexOf("、"))
                }
                innerlist.add(address)
                //申请贷款金额
                def requestedAmount = opp.requestedAmount
                innerlist.add(requestedAmount)
                //经办人
                def jstage = OpportunityStage.findByName("面谈已完成")
                def teamRole = TeamRole.findByName("Approval")
                def person = OpportunityRole.findByOpportunityAndStageAndTeamRole(opp, jstage, teamRole)?.user?.fullName
                innerlist.add(person)
                //销售组别
                def group = opp.account.name
                innerlist.add(group)
                //销售员
                def sales = opp.user.fullName
                innerlist.add(sales)
                //批贷日期
                def pd = OpportunityStage.findByName("审批已完成")
                def pdDate = OpportunityFlow.findByOpportunityAndStage(opp, pd)?.startTime
                if (pdDate)
                {
                    pdDate = sdf.format(pdDate)
                }
                innerlist.add(pdDate)
                //批贷金额
                def actualAmountOfCredit = opp.actualAmountOfCredit
                innerlist.add(actualAmountOfCredit)
                //放款日期
                def actualLendingDate = opp.actualLendingDate
                if (actualLendingDate)
                {
                    actualLendingDate = sdf.format(actualLendingDate)
                }
                innerlist.add(actualLendingDate)
                //放款金额
                innerlist.add(actualAmountOfCredit)
                //客户来源
                innerlist.add("")
                //渠道明细
                innerlist.add("")


                def serviceCharge = 0
                def commissionRate = 0
                def actualLoanDuration = opp.actualLoanDuration
                def monthlyInterest = 0
                def commissionPaymentMethod = ""
                if (opp.interest.size() > 2)
                {
                    opp.interest.each {
                        if (it.productInterestType.name == "基本费率")
                        {
                            if (!it.installments)
                            {
                                monthlyInterest += it.rate / actualLoanDuration
                            }
                            else
                            {
                                monthlyInterest += it.rate
                            }

                        }
                        if (it.productInterestType.name == "服务费费率")
                        {
                            if (!it.installments)
                            {
                                serviceCharge += it.rate / actualLoanDuration
                            }
                            else
                            {
                                serviceCharge += it.rate
                            }

                        }
                        if (it.productInterestType.name == "渠道返费费率")
                        {

                            if (!it.installments)
                            {
                                commissionRate += it.rate / actualLoanDuration
                                commissionPaymentMethod = "一次性"
                            }
                            else
                            {
                                commissionRate += it.rate
                                commissionPaymentMethod = "分期"
                            }
                        }
                        if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域")
                        {
                            if (it.contractType?.name == "借款合同")
                            {
                                if (!it.installments)
                                {
                                    monthlyInterest += it.rate / actualLoanDuration
                                }
                                else
                                {
                                    monthlyInterest += it.rate //费率金额 月
                                }
                            }
                            if (it.contractType?.name == "委托借款代理服务合同")
                            {
                                if (!it.installments)
                                {
                                    serviceCharge += it.rate / actualLoanDuration
                                }
                                else
                                {
                                    serviceCharge += it.rate //费率金额 月
                                }
                            }

                        }
                    }
                }
                else
                {
                    monthlyInterest = opp.monthlyInterest / 100
                    commissionRate = opp.commissionRate / 100
                    serviceCharge = opp.serviceCharge / 100
                    commissionPaymentMethod = opp.commissionPaymentMethod?.name
                }

                //月息
                innerlist.add(monthlyInterest + "%")
                //借款期限
                innerlist.add(actualLoanDuration)
                //渠道服务费
                innerlist.add(commissionRate + "%")
                //借款服务费
                innerlist.add(serviceCharge + "%")
                //扣息方式
                innerlist.add(commissionPaymentMethod)
                //上扣息月数
                def monthOfAdvancePaymentOfInterest = opp.monthOfAdvancePaymentOfInterest
                innerlist.add(monthOfAdvancePaymentOfInterest)
            }
            list.add(innerlist)
        }


        if (report == "yes")
        {
            def listTitle
            if (stageID == "8")
            {
                listTitle = new ArrayList<Map>(Arrays.asList("订单号", "签约日期", "贷款金额（万元）", "销售员", "委托协议编号", "贷款类型", "客户评级", "抵押类型", "借款人姓名", "区域", "房屋地址", "客户来源", "渠道明细", "销售组别", "月息", "借款期限（月）", "渠道服务费", "借款服务费", "扣息方式", "上扣息月数"))

            }
            else
            {
                listTitle = new ArrayList<Map>(Arrays.asList("订单号", "协议编号", "签约日期", "产品品种", "客户评级", "抵押类型", "业务来源", "借款人姓名", "区域", "物业区域", "物业位置", "申请贷款金额", "经办人", "销售组别", "销售员", "批贷日期", "批贷金额", "放款日期/缴息费日期", "放款金额（万）", "客户来源", "渠道明细", "月息", "借款期限(月)", "渠道服务费", "借款服务费", "扣息方式", "上扣息月数"))

            }
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            println list.size()
            respond list, model: [list: list, count: count, params: params]
        }
    }

    /**
     * @author xiaruikun
     * 杜蕾  需求
     */
    def dailyReport5(Integer max)
    {
        /**
         * 订单号，城市，产品类型，共同借款人，
         * 批贷金额，放款金额，借款期限，销售主管，销售员，风控主单员，
         * 实际放款日期，状态，放款凭证，
         * 确认路径日期，放款通道，放款账户，
         * 预计放款日期，确认通道日期，路径失效日期，失效原因，路径有效性（是/否）*/
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def serialNumber = params.serialNumber
        def fullName = params.fullName
        def city = params.city
        def productType = params.product
        def loanDate = params.loanDate
        def estimatedDate = params.estimatedLendingDate
        //放款时间
        def loanDoc = params.loanDoc
        def pathDate = params.pathDate
        //路径
        def passagewayDate = params.passagewayDate
        //通道
        def flexField = params["flexField"]
        def flexFieldBankAccount = params["flexFieldBankAccount"]
        def lastDate = params.lastDate
        def report = params.report
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
        def sql = "select id from Opportunity as o  where 1=1 and status != 'Failed'"
        if (serialNumber)
        {
            sql += "and o.serialNumber like '%${serialNumber}%'"
        }
        if (fullName)
        {
            sql += "and o.id in (select opportunity.id from OpportunityContact where contact.id in (select id from Contact where fullName like '%${fullName}%' ) ) "
        }
        if (city && city != "null")
        {
            sql += "and o.user.city.name = '${city}'"
        }
        if (productType && productType != 'null')
        {
            sql += "and o.product.name = '${productType}'"
        }
        if (loanDate && loanDate != 'null')
        {
            loanDate = loanDate.substring(0, 10)
            //            sql += "and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '预计放款时间' and f.value like '%${flexField}%'))"
            sql += "and Convert(varchar,o.actualLendingDate,120) like '%${loanDate}%'"
        }
        if (estimatedDate && estimatedDate != 'null')
        {
            estimatedDate = estimatedDate.substring(0, 10)
            //            sql += "and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '预计放款时间' and f.value like '%${flexField}%'))"
            sql += "and Convert(varchar,o.estimatedLendingDate,120) like '%${estimatedDate}%'"
        }
        if (loanDoc && loanDoc != 'null')
        {
            sql += "and o.mortgageCertificateType.name = '${loanDoc}'"
        }

        if (flexField && flexField != "null")
        {
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款通道' and f.value= '${flexField}'))"
        }
        if (flexFieldBankAccount && flexFieldBankAccount != "null")
        {
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
        }

        if (pathDate && pathDate != "null")
        {
            pathDate = pathDate.substring(0, 10)
            sql += " and (o.id IN (SELECT opportunity.id FROM OpportunityFlow as p where p.stage.code = 129 and o.id = p.opportunity.id and Convert(varchar,p.endTime,120) like '%${pathDate}%')"
            sql += " or o.id IN (SELECT opportunity.id FROM OpportunityFlow as p where p.stage.code = 23 and o.id = p.opportunity.id and Convert(varchar,p.endTime,120) like '%${pathDate}%'))"
        }

        if (passagewayDate && passagewayDate != "null")
        {
            passagewayDate = passagewayDate.substring(0, 10)
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlow as p where p.stage.code = 21 and o.id = p.opportunity.id and Convert(varchar,p.startTime,120) like '%${passagewayDate}%')"
        }
        if (lastDate && lastDate != "null")
        {
            lastDate = lastDate.substring(0, 10)
            sql += " and o.id IN (SELECT opportunity.id FROM OpportunityFlow as p where p.stage.code = 10 and o.id = p.opportunity.id and Convert(varchar,p.startTime,120) like '%${lastDate}%')"
        }

        println sql
        def ids = Opportunity.executeQuery(sql + " order by createdDate DESC", [max: max, offset: offset])
        def ids1 = Opportunity.executeQuery(sql + " order by createdDate DESC")
        def count = Opportunity.executeQuery(sql + " order by createdDate DESC")?.size





        def listReport = []
        def id
        if (report == "yes")
        {
            id = ids1
        }
        else
        {
            id = ids
        }
        id.each {
            def map = []
            def opp = Opportunity.findById(it)
            map.add(opp.serialNumber) //1
            if (opp?.user?.city?.name)
            {
                //2
                map.add(opp?.user?.city?.name)
            }
            else
            {
                map.add("")
            }
            if (opp.product?.name)
            {
                //3
                map.add(opp.product?.name)
            }
            else
            {
                map.add("")
            }
            def fieldsList = OpportunityFlexFieldCategory.findAllByOpportunity(opp).fields
            def door
            def account
            def faliureTime
            fieldsList.each {
                it.each {
                    if (it.name == "放款通道")
                    {
                        door = it.value
                    }
                    if (it.name == "放款账号")
                    {
                        account = it.value
                    }
                    if (it.name == "放款帐号有效截止时间")
                    {
                        faliureTime = it.value
                    }
                }
            }
            if (door)
            {
                map.add(door) //4
            }
            else
            {
                map.add("")
            }
            if (account)
            {
                map.add(account)
            }
            else
            {
                map.add("") //5
            }
            def opportunityContacts = OpportunityContact.findAllByOpportunity(opp)
            def contactFullName = ""
            if (opportunityContacts)
            {
                for (
                    def opportunityContact in
                        opportunityContacts)
                {
                    contactFullName += opportunityContact?.contact?.fullName + "、"
                }
            }
            if (contactFullName)
            {
                contactFullName = contactFullName.substring(0, contactFullName.lastIndexOf("、")) //6
            }
            map.add(contactFullName)
            map.add(opp.actualAmountOfCredit) //7批贷金额
            map.add(opp.actualAmountOfCredit) //8放款金额
            map.add(opp.actualLoanDuration) //9借款金额
            def estimatedLendingDate = opp.estimatedLendingDate
            def actualLendingDate = opp.actualLendingDate
            if (estimatedLendingDate)
            {
                map.add(sdf.format(estimatedLendingDate)) //10预计放款时间
            }
            else
            {
                map.add("")
            }
            if (actualLendingDate)
            {
                map.add(sdf.format(actualLendingDate)) //11实际放款时间
            }
            else
            {
                map.add("")
            }

            map.add(opp.stage?.name) //12订单阶段

            if (opp.mortgageCertificateType?.name)
            {
                map.add(opp.mortgageCertificateType.name) //13放款凭证
            }
            else
            {
                map.add("")
            }

            def stage = OpportunityStage.findByCode("21")
            //放款通道已选择
            def startTime = OpportunityFlow.findByOpportunityAndStage(opp, stage)?.startTime
            if (startTime)
            {
                map.add(sdf.format(startTime)) //14
            }
            else
            {
                map.add("")
            }


            def stage2 = OpportunityStage.findByCode("129")
            //资金路径确认
            def startTime2 = OpportunityFlow.findByOpportunityAndStage(opp, stage2)?.endTime
            if (startTime2)
            {
                map.add(sdf.format(startTime2)) //15
            }
            else
            {
                stage2 = OpportunityStage.findByCode("23")
                //放款审批已完成
                startTime2 = OpportunityFlow.findByOpportunityAndStage(opp, stage2)?.endTime
                if (startTime2)
                {
                    map.add(sdf.format(startTime2))
                }
                else
                {
                    map.add("")
                }
            }

            map.add(faliureTime) //16路径失效时间

            Date date = new Date()
            if (faliureTime)
            {
                if (faliureTime >= sdf.format(date))
                {
                    map.add("是") //17路径有效性
                }
                else
                {
                    map.add("否")
                }
            }
            else
            {
                map.add("")
            }

            map.add(opp.account?.name) //18
            map.add(opp.user?.fullName) //19

            if (opp.contact?.city?.name)
            {
                if (opp.contact.city.name == "北京" || opp.contact.city.name == "上海")
                {
                    def stage1 = OpportunityStage.findByName("面谈已完成")
                    def teamRole = TeamRole.findByName("Approval")
                    def role = OpportunityRole.findByOpportunityAndStageAndTeamRole(opp, stage1, teamRole)?.user?.fullName
                    if (role)
                    {
                        map.add(role) //20
                    }
                    else
                    {
                        map.add("")
                    }
                }
                else
                {
                    def stage1 = OpportunityStage.findByName("房产初审已完成")
                    def teamRole = TeamRole.findByName("Approval")
                    def role = OpportunityRole.findByOpportunityAndStageAndTeamRole(opp, stage1, teamRole)?.user?.fullName
                    if (role)
                    {
                        map.add(role)
                    }
                    else
                    {
                        map.add("")
                    }
                }
            }
            else
            {
                map.add("")
            }
            listReport.add(map)
        }
        //  render listReport

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单编号", "城市", "产品类型", "放款通道", "放款账户", "共同借款人", "批贷金额", "放款金额（元）", "借款期限（月）", "预计放款日期", "实际放款日期", "订单阶段", "放款凭证", "确认通道时间", "确认路径时间", "路径失效时间", "路径有效性(是/否)", "销售主管", "销售专员", "风控主单员"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, listReport)
            return null
        }
        else
        {
            respond listReport, model: [list: listReport, count: count, params: params]
        }
    }

    /**
     * @description 放款台账
     * @author Nagelan
     */
    def dailyReport6(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def report = params.report
        def count
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def ids
        def list = []
        def sql
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")))
        {
            sql = 'select o.serialNumber,p.name,ll.name,m.name,o.fullName,l.idNumber,a.name,u.fullName,o.actualAmountOfCredit,o.actualLendingDate,o.actualLoanAmount,uc.fullName,o.actualLoanDuration,o.interestPaymentMethod,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.lender l left join l.level ll LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.mortgageType m LEFT JOIN u.city ct LEFT JOIN o.stage os where 1=1'

            if (city != "--CITY--" && city)
            {
                sql += " and ct.name = '${city}'"
            }
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            sql = "select o.serialNumber,p.name,ll.name,m.name,o.fullName,l.idNumber,a.name,u.fullName,o.actualAmountOfCredit,o.actualLendingDate,o.actualLoanAmount,uc.fullName,o.actualLoanDuration,o.interestPaymentMethod,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.lender l left join l.level ll LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.mortgageType m LEFT JOIN u.city ct LEFT JOIN o.stage os where ct.name = '${user?.city?.name}'"
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql = "select o.serialNumber,p.name,ll.name,m.name,o.fullName,l.idNumber,a.name,u.fullName,o.actualAmountOfCredit,o.actualLendingDate,o.actualLoanAmount,uc.fullName,o.actualLoanDuration,o.interestPaymentMethod,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.lender l left join l.level ll LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.mortgageType m LEFT JOIN u.city ct LEFT JOIN o.stage os where 1=1"
            sql += " and u.id in (${user.id})"
        }
        if (startTime && endTime)
        {
            //sql += " and o.id in(select opportunity.id from OpportunityFlow where startTime between '${startTime}' and '${endTime}' and stage.id = 10)"
            sql += " and o.actualLendingDate between '${startTime}' and '${endTime}'"
        }
        else
        {
            //sql += " and o.id in(select opportunity.id from OpportunityFlow where startTime is not null and stage.id = 10)"
            sql += " and o.actualLendingDate is not null"
        }
        sql += " and (p is null or p.name != '新e贷')"
        //sql += "and o.isTest = 0 and os.id in(10,20,89,129)"
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by o.createdDate")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by o.createdDate desc", [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql + " order by o.createdDate desc")?.size
        ids.each {
            def mapList = []
            def houseInfo = Collateral.executeQuery("select c.district,ct.name,c.projectName,c.building,c.unit,c.floor,c.totalFloor,c.roomNumber,cp.name,c.area,c.buildTime,c.loanToValue from Collateral c left join c.opportunity o left join c.projectType cp left join o.user.city ct where o.id = ${it[14]}")
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join c.type t left join oc.opportunity o where o.id = ${it[14]} and t.name = '借款合同'")
            def opportunity = Opportunity.findById(it[14])
            mapList.add(contract[0])
            mapList.add(it[0])
            mapList.add(it[1])
            mapList.add(it[2])
            mapList.add(it[3])
            def user1 = "", user2 = "", user3 = ""
            def contact = opportunity?.lender
            def opportunityContact = OpportunityContact.findByOpportunityAndContact(opportunity, contact)
            if (opportunityContact?.connectedContact)
            {
                user1 = ";" + opportunityContact?.connectedContact?.fullName
            }
            mapList.add(it[4] + user1 + user2 + user3)
            //计算岁数
            def idNumber = it[5].toString()
            if (idNumber != null && idNumber != "" && idNumber.length() == 18)
            {
                mapList.add(new Date().format("yyyy").toInteger().minus(idNumber[6..9].toInteger()))
            }
            else
            {
                mapList.add("不明")
            }
            if (houseInfo[0])
            {
                mapList.add(houseInfo[0][0])
                mapList.add(houseInfo[0][1] + houseInfo[0][2] + houseInfo[0][3] + '号楼' + houseInfo[0][4] + '单元' + houseInfo[0][5] + '/' + houseInfo[0][6] + '房间号' + houseInfo[0][7])
                mapList.add(houseInfo[0][8])
                mapList.add(houseInfo[0][9])
                if (houseInfo[0][10])
                {
                    mapList.add((new Date().format("yyyy").toInteger() - ((Date) houseInfo[0][10]).format("yyyy").toInteger()))
                }
                else
                {
                    mapList.add("不明")
                }
                mapList.add(houseInfo[0][11])
            }
            else
            {
                mapList.add("")
                mapList.add("")
                mapList.add("")
                mapList.add(0)
                mapList.add("不明")
                mapList.add("")
            }
            def opportunityRole = OpportunityRole.findByOpportunityAndStage(opportunity, OpportunityFlow.findByOpportunityAndOpportunityLayout(opportunity, OpportunityLayout.findByDescriptionLike("%主单页%"))?.stage)
            if (opportunityRole?.user?.fullName)
            {
                mapList.add(opportunityRole?.user?.fullName)
            }
            else
            {
                mapList.add("无")
            }
            mapList.add(it[6])
            mapList.add(it[7])
            mapList.add(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("审批已完成"))?.startTime?.format("yyyy-MM-dd"))
            mapList.add(it[8])
            mapList.add(it[9]?.format("yyyy-MM-dd"))
            mapList.add(it[10])
            mapList.add(it[11])
            //基本费率%、服务费费率%、渠道返费费率%、综合息费%
            def op = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity,opportunity.productAccount,ProductInterestType.findByName("基本费率"))
            def op1 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("服务费费率"))
            def op2 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("渠道返费费率"))
            def op3 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("二抵加收费率"))
            def op4 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("信用调整"))
            def ops = OpportunityProduct.findAllByOpportunityAndProductAndProductInterestTypeNotEqual(opportunity,opportunity.productAccount,ProductInterestType.findByName("渠道返费费率"))
            mapList.add(op?.rate)
            mapList.add(op1?.rate)
            mapList.add(op2?.rate)
            mapList.add(op3?.rate)
            mapList.add(op4?.rate)
            def returnFee, rates = 0
            ops?.each {
                if (it?.installments && it?.rate && it?.rate < 100)
                {
                    rates += it?.rate
                }
                else if (!it?.installments && it?.rate && it?.rate < 100)
                {
                    rates += it?.rate / opportunity?.actualLoanDuration
                }
            }
            if (op2?.installments && op2?.rate)
            {
                returnFee = "月月返"
            }
            else if (!op2?.installments && op2?.rate)
            {
                returnFee = "一次性返"
            }
            else if (!op2?.rate)
            {
                returnFee = "没有返费"
            }
            mapList.add(rates)
            mapList.add(it[12])
            //返费说明
            mapList.add(returnFee)
            mapList.add(it[13])
            if (opportunity?.actualLendingDate)
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(opportunity.actualLendingDate)
                calendar.add(Calendar.MONTH, opportunity.actualLoanDuration)
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                mapList.add(calendar.getTime().format("yyyy-MM-dd"))
                mapList.add(calendar.getTime().format("yyyy-MM-dd"))
            }
            else
            {
                mapList.add("没有放款时间")
                mapList.add("没有放款时间")
            }

            list.add(mapList)
        }
        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同编号", "订单号", "产品品种", "客户评级", "抵押类型", "借款人姓名", "借款及抵押人年龄", "抵押物区域", "房屋地址", "房屋立项", "房屋面建筑积", "抵押物房龄", "合计抵押率", "风控员", "销售组别", "销售员", "批贷日期", "批贷金额(万元)", "放款日期", "放款金额(万元)", "渠道姓名/公司名称", "基本费率%", "服务费率%", "渠道返费费率%", "二抵加收费率%", "信用调整%", "综合息费%", "借款期限(月)", "返费说明", "收息方式", "合同还款日期", "最终还款日期"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @description 签约台账
     * @author Nagelan
     */
    def dailyReport7(Integer max)
    {
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def report = params.report
        def count
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def ids
        def list = []
        def sql
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CEO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CRO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")))
        {
            sql = 'select o.serialNumber,p.name,ll.name,m.name,o.fullName,l.idNumber,a.name,u.fullName,o.actualAmountOfCredit,uc.fullName,o.actualLoanDuration,o.interestPaymentMethod,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.lender l left join l.level ll LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.mortgageType m LEFT JOIN u.city ct LEFT JOIN o.stage os where 1=1'

            if (city != "--CITY--" && city)
            {
                sql += " and ct.name = '${city}'"
            }
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            sql = "select o.serialNumber,p.name,ll.name,m.name,o.fullName,l.idNumber,a.name,u.fullName,o.actualAmountOfCredit,uc.fullName,o.actualLoanDuration,o.interestPaymentMethod,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.lender l left join l.level ll LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.mortgageType m LEFT JOIN u.city ct LEFT JOIN o.stage os where ct.name = '${user?.city?.name}'"
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            sql = "select o.serialNumber,p.name,ll.name,m.name,o.fullName,l.idNumber,a.name,u.fullName,o.actualAmountOfCredit,uc.fullName,o.actualLoanDuration,o.interestPaymentMethod,o.id from Opportunity o LEFT JOIN o.user u LEFT JOIN o.account a LEFT JOIN o.lender l left join l.level ll LEFT JOIN o.product p LEFT JOIN o.contact uc LEFT JOIN o.stage os LEFT JOIN o.mortgageType m LEFT JOIN u.city ct LEFT JOIN o.stage os where ct.name = '${user?.city?.name}'"
            sql += " and u.id in (${user.id})"
        }
        if (startTime && endTime)
        {
            sql += " and o.id in(select opportunity.id from OpportunityFlow where startTime between '${startTime}' and '${endTime}' and stage.id = 8)"
        }
        else
        {
            sql += " and o.id in(select opportunity.id from OpportunityFlow where startTime is not null and stage.id = 8)"
        }
        sql += " and (p is null or p.name != '新e贷')"
        //sql += " and o.isTest = 0 and os.id in(33,57,58,87,129,20,89,10,8,19,21,55,63,9,38,65)"
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by o.createdDate")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by o.createdDate desc", [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql + " order by o.createdDate desc")?.size
        ids.each {
            def opportunity = Opportunity.findById(it[12])
            def mapList = []
            def houseInfo = Collateral.executeQuery("select c.district,ct.name,c.projectName,c.building,c.unit,c.floor,c.totalFloor,c.roomNumber,cp.name,c.area,c.buildTime,c.loanToValue from Collateral c left join c.opportunity o left join c.projectType cp left join o.user.city ct where o.id = ${it[12]}")
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join c.type t left join oc.opportunity o where o.id = ${it[12]} and t.name = '借款合同'")
            mapList.add(contract[0])
            mapList.add(it[0])
            mapList.add(it[1])
            mapList.add(it[2])
            mapList.add(it[3])
            def user1 = "", user2 = "", user3 = ""
            def contact = opportunity?.lender
            def opportunityContact = OpportunityContact.findByOpportunityAndContact(opportunity, contact)
            if (opportunityContact?.connectedContact)
            {
                user1 = ";" + opportunityContact?.connectedContact?.fullName
            }
            mapList.add(it[4] + user1 + user2 + user3)
            //计算岁数
            def idNumber = it[5].toString()
            if (idNumber != null && idNumber != "" && idNumber.length() == 18)
            {
                mapList.add(new Date().format("yyyy").toInteger().minus(idNumber[6..9].toInteger()))
            }
            else
            {
                mapList.add("不明")
            }
            if (houseInfo[0])
            {
                mapList.add(houseInfo[0][0])
                mapList.add(houseInfo[0][1] + houseInfo[0][2] + houseInfo[0][3] + '号楼' + houseInfo[0][4] + '单元' + houseInfo[0][5] + '/' + houseInfo[0][6] + '房间号' + houseInfo[0][7])
                mapList.add(houseInfo[0][8])
                mapList.add(houseInfo[0][9])
                if (houseInfo[0][10])
                {
                    mapList.add((new Date().format("yyyy").toInteger() - ((Date) houseInfo[0][10]).format("yyyy").toInteger()))
                }
                else
                {
                    mapList.add("不明")
                }
                mapList.add(houseInfo[0][11])
            }
            else
            {
                mapList.add("")
                mapList.add("")
                mapList.add("")
                mapList.add(0)
                mapList.add("不明")
                mapList.add("")
            }
            def opportunityRole = OpportunityRole.findByOpportunityAndStage(opportunity, OpportunityFlow.findByOpportunityAndOpportunityLayout(opportunity, OpportunityLayout.findByDescriptionLike("%主单页%"))?.stage)
            if (opportunityRole?.user?.fullName)
            {
                mapList.add(opportunityRole?.user?.fullName)
            }
            else
            {
                mapList.add("无")
            }
            mapList.add(it[6])
            mapList.add(it[7])
            mapList.add(OpportunityFlow.findByOpportunityAndStage(opportunity, OpportunityStage.findByName("审批已完成"))?.startTime?.format("yyyy-MM-dd"))
            mapList.add(it[8])
            mapList.add(it[9])
            //基本费率%、服务费费率%、渠道返费费率%、综合息费%
            def op = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity,opportunity.productAccount,ProductInterestType.findByName("基本费率"))
            def op1 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("服务费费率"))
            def op2 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("渠道返费费率"))
            def op3 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("二抵加收费率"))
            def op4 = OpportunityProduct.findByOpportunityAndProductAndProductInterestType(opportunity, opportunity.productAccount,ProductInterestType.findByName("信用调整"))
            def ops = OpportunityProduct.findAllByOpportunityAndProductAndProductInterestTypeNotEqual(opportunity,opportunity.productAccount,ProductInterestType.findByName("渠道返费费率"))
            mapList.add(op?.rate)
            mapList.add(op1?.rate)
            mapList.add(op2?.rate)
            mapList.add(op3?.rate)
            mapList.add(op4?.rate)
            def returnFee, rates = 0
            ops?.each {
                if (it?.installments && it?.rate && it?.rate < 100)
                {
                    rates += it?.rate
                }
                else if (!it?.installments && it?.rate && it?.rate < 100)
                {
                    rates += it?.rate / opportunity?.actualLoanDuration
                }
            }
            if (op2?.installments && op2?.rate)
            {
                returnFee = "月月返"
            }
            else if (!op2?.installments && op2?.rate)
            {
                returnFee = "一次性返"
            }
            else if (!op2?.rate)
            {
                returnFee = "没有返费"
            }
            mapList.add(rates)
            mapList.add(it[10])
            //返费说明
            mapList.add(returnFee)
            mapList.add(it[11])
            if (opportunity?.status == "Failed")
            {
                mapList.add("是")
                if (opportunity?.causeOfFailure)
                {
                    mapList.add(opportunity?.causeOfFailure?.name)
                }
                else
                {
                    mapList.add("无")
                }
            }
            else
            {
                mapList.add("否")
                mapList.add("")
            }
            list.add(mapList)
        }
        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同编号", "订单号", "产品品种", "客户评级", "抵押类型", "借款人姓名", "借款及抵押人年龄", "抵押物区域", "房屋地址", "房屋立项", "房屋面建筑积", "抵押物房龄", "合计抵押率", "风控员", "销售组别", "销售员", "批贷日期", "批贷金额(万元)", "渠道姓名/公司名称", "基本费率%", "服务费率%", "渠道返费费率%", "二抵加收费率%", "信用调整%", "综合息费%", "借款期限(月)", "返费说明", "收息方式", "是否退单", "退单原因"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出佣金基本信息
     * @ ModifiedDate 2017-6-29*/
    @Transactional
    def exportCommissionInfo(Integer max)
    {
        println "========================= exportCommissionInfo: 导出佣金基本信息 ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def report = params.report
        def count = 0

        def list = []
        def sql = "select o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.user.fullName, o.user.id, o.id, o.contact.city.name, o.productAccount.id, o.actualAmountOfCredit , o.serialNumber , o.product.name from Opportunity as o where o.actualLendingDate is not null and (o.id in (select c.opportunity.id from OpportunityContract as c where c.contract.id in (select t.id from Contract as t where t.type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and o.actualLendingDate between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and o.contact.city.name = '${city}'"
        }
        sql += " order by o.externalId desc"

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {

            def list3 = []

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[8]}")
            if (contract)
            {
                list3[0] = contract[0]
            }
            else
            {
                list3[0] = it[0]
            }

            // 借款人
            list3[1] = it[1]

            // 放款金额
            if (it[2])
            {
                list3[2] = it[2]
            }
            else
            {
                list3[2] = it[11]
            }

            def opportunity = Opportunity.findById(it[8])
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }
            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            list3[3] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[4] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[5] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率

            // 放款日期
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                list3[6] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同到期日
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                calendar.add(Calendar.MONTH, it[4])
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                list3[7] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同借款期限（月）
            list3[8] = it[4]

            // 实际还款日期
            if (it[5])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[5])
                list3[9] = calendar.getTime().format("yyyy-MM-dd")
            }

            def managerName
            def managerList = Reporting.executeQuery("select r.manager.fullName from Reporting as r where r.user.id = ${it[7]}")
            if (managerList)
            {
                managerName = managerList[0]
            }

            def reporterList = Reporting.executeQuery("select r.manager.fullName from Reporting as r where r.user.id in (select t.manager.id from Reporting as t where t.user.id=${it[7]})")
            def reporterName
            if (reporterList)
            {
                reporterName = reporterList[0]
            }


            list3[10] = reporterName // 销售经理
            list3[11] = managerName // 销售主管
            list3[12] = it[6] // 销售员
            list3[13] = it[9] // 城市

            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[8]} and c.flexFieldCategory.id = 13) and o.name = '放款通道'"
            def sql2 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[8]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            def oof2 = OpportunityFlexField.executeQuery(sql2)

            list3[14] = oof1[0] // 放款通道
            list3[15] = oof2[0] // 放款账号
            list3.add(0,it[12])
            list3.add(1,it[13])
            list.add(list3)
        }

        println "佣金基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同号","产品类型","合同编号", "借款人", "放款金额", "月利率", "月服务费率", "一次性服务费率", "放款日期", "合同到期日", "合同借款期限（月）", "实际还款日期", "销售经理", "销售主管", "销售员", "城市", "放款通道", "放款账号"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出佣金基本信息（区域经理）
     * @ ModifiedDate 2017-6-29*/
    @Transactional
    def exportCommissionInfo3(Integer max)
    {
        println "========================= exportCommissionInfo: 导出佣金基本信息 ===================================="

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = user.city?.name
        def report = params.report
        def count = 0

        def list = []
        def sql = "select o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.user.fullName, o.user.id, o.id, o.contact.city.name, o.productAccount.id, o.actualAmountOfCredit from Opportunity as o where o.actualLendingDate is not null and (o.id in (select c.opportunity.id from OpportunityContract as c where c.contract.id in (select t.id from Contract as t where t.type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and o.actualLendingDate between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and o.contact.city.name = '${city}'"
        }
        sql += " order by o.externalId desc"

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {

            def list3 = []

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[8]}")
            if (contract)
            {
                list3[0] = contract[0]
            }
            else
            {
                list3[0] = it[0]
            }

            // 借款人
            list3[1] = it[1]

            // 放款金额
            if (it[2])
            {
                list3[2] = it[2]
            }
            else
            {
                list3[2] = it[11]
            }

            def opportunity = Opportunity.findById(it[8])
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }
            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[8]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            list3[3] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[4] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[5] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率

            // 放款日期
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                list3[6] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同到期日
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                calendar.add(Calendar.MONTH, it[4])
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                list3[7] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同借款期限（月）
            list3[8] = it[4]

            // 实际还款日期
            if (it[5])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[5])
                list3[9] = calendar.getTime().format("yyyy-MM-dd")
            }

            def managerName
            def managerList = Reporting.executeQuery("select r.manager.fullName from Reporting as r where r.user.id = ${it[7]}")
            if (managerList)
            {
                managerName = managerList[0]
            }

            def reporterList = Reporting.executeQuery("select r.manager.fullName from Reporting as r where r.user.id in (select t.manager.id from Reporting as t where t.user.id=${it[7]})")
            def reporterName
            if (reporterList)
            {
                reporterName = reporterList[0]
            }

            list3[10] = reporterName // 销售经理
            list3[11] = managerName // 销售主管
            list3[12] = it[6] // 销售员
            list3[13] = it[9] // 城市

            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[8]} and c.flexFieldCategory.id = 13) and o.name = '放款通道'"
            def sql2 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[8]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            def oof2 = OpportunityFlexField.executeQuery(sql2)

            list3[14] = oof1[0] // 放款通道
            list3[15] = oof2[0] // 放款账号

            list.add(list3)
        }

        println "佣金基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同编号", "借款人", "放款金额", "月利率", "月服务费利率", "一次性服务费率", "放款日期", "合同到期日", "合同借款期限（月）", "实际还款日期", "销售经理", "销售主管", "销售员", "城市", "放款通道", "放款账号"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出返费基本信息
     * @ ModifiedDate 2017-5-22*/
    @Transactional
    def exportCommissionInfo2(Integer max)
    {
        println "========================= exportCommissionInfo2: 导出返费基本信息 ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def report = params.report
        def count = 0

        def list = []
        def sql = "select o.externalId, o.fullName, o.actualAmountOfCredit, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, o.contact.city.name, o.productAccount.id from Opportunity as o where o.id in (select opportunity.id from OpportunityFlow as f where f.startTime is not null and f.stage.id = 10) and (o.id in (select c.opportunity.id from OpportunityContract as c where c.contract.id in (select t.id from Contract as t where t.type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and o.actualLendingDate between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and o.contact.city.name = '${city}'"
        }
        sql += " order by o.externalId desc"

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[0] = contract[0]
            }
            else
            {
                list3[0] = it[0]
            }

            // 借款人
            list3[1] = it[1]

            // 放款金额
            list3[2] = it[2]

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and t.product.id = ${it[8]}")
            // 一次性返利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[8]}")
            // 月月返利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[8]}")

            list3[3] = rate1[0]
            list3[4] = rate2[0]
            list3[5] = rate3[0]

            // 放款日期
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                list3[6] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同到期日
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                calendar.add(Calendar.MONTH, it[4])
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                list3[7] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同借款期限（月）
            list3[8] = it[4]

            // 实际还款日期
            if (it[5])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[5])
                list3[9] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 对公渠道名称
            def accountName = Opportunity.executeQuery("select o.contact.account.name from Opportunity as o where o.id = ${it[6]}")
            list3[10] = accountName[0]

            // 对私渠道名称
            def fullName = Opportunity.executeQuery("select o.contact.fullName from Opportunity as o where o.id = ${it[6]}")
            list3[11] = fullName[0]

            // 城市
            list3[12] = it[7]

            list.add(list3)
        }

        println "返费基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同编号", "借款人", "放款金额", "月利率", "一次性返费比率", "月返费比率", "放款日期", "合同到期日", "合同借款期限（月）", "实际还款日期", "对公渠道名称", "对私渠道名称", "城市"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出返费基本信息（区域经理）
     * @ ModifiedDate 2017-5-22*/
    @Transactional
    def exportCommissionInfo4(Integer max)
    {
        println "========================= exportCommissionInfo2: 导出返费基本信息 ===================================="

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = user.city?.name
        def report = params.report
        def count = 0

        def list = []
        def sql = "select o.externalId, o.fullName, o.actualAmountOfCredit, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, o.contact.city.name, o.productAccount.id from Opportunity as o where o.id in (select opportunity.id from OpportunityFlow as f where f.startTime is not null and f.stage.id = 10) and (o.id in (select c.opportunity.id from OpportunityContract as c where c.contract.id in (select t.id from Contract as t where t.type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and o.actualLendingDate between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and o.contact.city.name = '${city}'"
        }
        sql += " order by o.externalId desc"

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[0] = contract[0]
            }
            else
            {
                list3[0] = it[0]
            }

            // 借款人
            list3[1] = it[1]

            // 放款金额
            list3[2] = it[2]

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and t.product.id = ${it[8]}")
            // 一次性返利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[8]}")
            // 月月返利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[8]}")

            list3[3] = rate1[0]
            list3[4] = rate2[0]
            list3[5] = rate3[0]

            // 放款日期
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                list3[6] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同到期日
            if (it[3])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[3])
                calendar.add(Calendar.MONTH, it[4])
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                list3[7] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 合同借款期限（月）
            list3[8] = it[4]

            // 实际还款日期
            if (it[5])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[5])
                list3[9] = calendar.getTime().format("yyyy-MM-dd")
            }

            // 对公渠道名称
            def accountName = Opportunity.executeQuery("select o.contact.account.name from Opportunity as o where o.id = ${it[6]}")
            list3[10] = accountName[0]

            // 对私渠道名称
            def fullName = Opportunity.executeQuery("select o.contact.fullName from Opportunity as o where o.id = ${it[6]}")
            list3[11] = fullName[0]

            // 城市
            list3[12] = it[7]

            list.add(list3)
        }

        println "返费基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("合同编号", "借款人", "放款金额", "月利率", "一次性返费比率", "月返费比率", "放款日期", "合同到期日", "合同借款期限（月）", "实际还款日期", "对公渠道名称", "对私渠道名称", "城市"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出放款基本信息
     * @ ModifiedDate 2017-614*/
    @Transactional
    def exportLoanInfo(Integer max)
    {
        println "========================= exportLoanInfo: 导出放款基本信息 ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def report = params.report
        def count = 0

        def list = []
        def sql = "select distinct o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, c.city.name, o.serialNumber, o.modifiedDate, o.productAccount.id from OpportunityFlow ofo, Opportunity o, Collateral c where ofo.opportunity.id = o.id and o.id = c.opportunity.id and o.status <> 'Failed' and o.isTest = false and ofo.stage.id in (186, 23) and ofo.endTime is not null and (o.id in (select oc.opportunity.id from OpportunityContract as oc where oc.contract.id in (select id FROM Contract where serialNumber is not null and type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and ofo.endTime between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and c.city.name = '${city}'"
        }
        // sql += " order by o.externalId desc"

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 订单号
            list3[0] = it[8]

            // 放款账号确定时间
            if (it[9])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[9])
                list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")

                // def opportunityFlowEndTime
                // def endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 23")
                // if (endTimeTemp[0]) 
                // {
                //     opportunityFlowEndTime = endTimeTemp[0]
                // }
                // else
                // {
                //     endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 186")
                //     if (endTimeTemp[0]) 
                //     {
                //         opportunityFlowEndTime = endTimeTemp[0]
                //     }
                //     else
                //     {
                //         opportunityFlowEndTime = it[9]
                //     }
                // }

                // println opportunityFlowEndTime

                // Calendar calendar = Calendar.getInstance()
                // calendar.setTime(opportunityFlowEndTime)
                // list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")
            }

            // 城市
            list3[2] = it[7]

            // 放款账号
            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[6]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            list3[3] = oof1[0]

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[4] = contract[0]
            }
            else
            {
                list3[4] = it[0]
            }

            // 借款人
            list3[5] = it[1]

            // 放款金额
            if (it[2] && it[2] != 0)
            {
                list3[6] = it[2]
            }
            else
            {
                def actualAmountOfCredit = Opportunity.executeQuery("select o.actualAmountOfCredit from Opportunity as o where o.id = ${it[6]}")
                list3[6] = actualAmountOfCredit[0]
            }

            // 合同借款期限（月）
            list3[7] = it[4]

            def opportunity = Opportunity.findById(it[6])
            // 实际借款期限
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }

            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            // println "lilv:" + lilv
            // println "fuwufeilv1:" + fuwufeilv1
            // println "fuwufeilv2:" + fuwufeilv2

            // 月月返利率
            def rate4 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[10]}")
            // 一次性返利率
            def rate5 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[10]}")
            if (!rate4[0])
            {
                rate4[0] = 0
            }
            if (!rate5[0])
            {
                rate5[0] = 0
            }

            list3[8] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[9] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[10] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率
            list3[11] = rate4[0] // 月月返利率
            list3[12] = rate5[0] // 一次性返利率

            list.add(list3)
        }

        println "放款基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "放款账号确定时间", "城市", "放款账户", "合同号", "借款人姓名", "放款金额(万元)", "放款期限", "月利率", "月服务费比率", "一次性服务费比率", "月返费比率", "一次性返费比率"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出放款基本信息（区域经理）
     * @ ModifiedDate 2017-614*/
    @Transactional
    def exportLoanInfo1(Integer max)
    {
        println "========================= exportLoanInfo: 导出放款基本信息 ===================================="

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = user?.city?.name
        def report = params.report
        def count = 0

        println "city:" + city

        def list = []
        def sql = "select distinct o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, c.city.name, o.serialNumber, o.modifiedDate, o.productAccount.id from OpportunityFlow ofo, Opportunity o, Collateral c where ofo.opportunity.id = o.id and o.id = c.opportunity.id and o.status <> 'Failed' and o.isTest = false and ofo.stage.id in (186, 23) and ofo.endTime is not null and (o.id in (select oc.opportunity.id from OpportunityContract as oc where oc.contract.id in (select id FROM Contract where serialNumber is not null and type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and ofo.endTime between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and c.city.name = '${city}'"
        }
        // sql += " order by o.externalId desc"

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 订单号
            list3[0] = it[8]

            // 放款账号确定时间
            if (it[9])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[9])
                list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")

                // def opportunityFlowEndTime
                // def endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 23")
                // if (endTimeTemp[0]) 
                // {
                //     opportunityFlowEndTime = endTimeTemp[0]
                // }
                // else
                // {
                //     endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 186")
                //     if (endTimeTemp[0]) 
                //     {
                //         opportunityFlowEndTime = endTimeTemp[0]
                //     }
                //     else
                //     {
                //         opportunityFlowEndTime = it[9]
                //     }
                // }

                // println opportunityFlowEndTime

                // Calendar calendar = Calendar.getInstance()
                // calendar.setTime(opportunityFlowEndTime)
                // list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")
            }

            // 城市
            list3[2] = it[7]

            // 放款账号
            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[6]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            list3[3] = oof1[0]

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[4] = contract[0]
            }
            else
            {
                list3[4] = it[0]
            }

            // 借款人
            list3[5] = it[1]

            // 放款金额
            if (it[2] && it[2] != 0)
            {
                list3[6] = it[2]
            }
            else
            {
                def actualAmountOfCredit = Opportunity.executeQuery("select o.actualAmountOfCredit from Opportunity as o where o.id = ${it[6]}")
                list3[6] = actualAmountOfCredit[0]
            }


            // 合同借款期限（月）
            list3[7] = it[4]

            def opportunity = Opportunity.findById(it[6])
            // 实际借款期限
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }

            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            // println "lilv:" + lilv
            // println "fuwufeilv1:" + fuwufeilv1
            // println "fuwufeilv2:" + fuwufeilv2

            // 月月返利率
            def rate4 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[10]}")
            // 一次性返利率
            def rate5 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[10]}")
            if (!rate4[0])
            {
                rate4[0] = 0
            }
            if (!rate5[0])
            {
                rate5[0] = 0
            }

            list3[8] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[9] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[10] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率
            list3[11] = rate4[0] // 月月返利率
            list3[12] = rate5[0] // 一次性返利率

            list.add(list3)
        }

        println "放款基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "放款账号确定时间", "城市", "放款账户", "合同号", "借款人姓名", "放款金额(万元)", "放款期限", "月利率", "月服务费比率", "一次性服务费比率", "月返费比率", "一次性返费比率"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出当天放款基本信息
     * @ ModifiedDate 2017-614*/
    @Transactional
    def exportLoanInfo2(Integer max)
    {
        println "========================= exportLoanInfo3: export Loan Info ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def flag = params.flag
        def report = params.report
        def count = 0

        Calendar calendar1 = Calendar.getInstance()
        calendar1.setTime(new Date())
        def nowDate = calendar1.getTime().format("yyyy-MM-dd")

        def yesterdayDate
        def week = calendar1.get(Calendar.DAY_OF_WEEK) - 1
        if (week == 1)
        {
            calendar1.add(calendar1.DATE, -3)
            yesterdayDate = calendar1.getTime().format("yyyy-MM-dd")
        }
        else
        {
            calendar1.add(calendar1.DATE, -1)
            yesterdayDate = calendar1.getTime().format("yyyy-MM-dd")
        }

        def startTime
        def endTime
        if (flag == 'AM')
        {
            startTime = yesterdayDate + ' 12:00:00'
            endTime = nowDate + ' 08:59:59'
        }
        if (flag == 'PM')
        {
            startTime = nowDate + ' 09:00:00'
            endTime = nowDate + ' 11:59:59'
        }

        println startTime
        println endTime

        def list = []
        def sql = "select distinct o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, c.city.name, o.serialNumber, o.modifiedDate, o.productAccount.id from OpportunityFlow ofo, Opportunity o, Collateral c where ofo.opportunity.id = o.id and o.id = c.opportunity.id and o.status <> 'Failed' and o.isTest = false and ofo.stage.id in (186, 23) and ofo.endTime is not null and (o.id in (select oc.opportunity.id from OpportunityContract as oc where oc.contract.id in (select id FROM Contract where serialNumber is not null and type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and ofo.endTime between '${startTime}' and '${endTime}'"
        }
        else
        {
            if (flag == 'AM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo2'
                return
            }
            if (flag == 'PM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo3'
                return
            }
        }

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 订单号
            list3[0] = it[8]

            // 放款账号确定时间
            if (it[9])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[9])
                list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")

                // def opportunityFlowEndTime
                // def endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 23")
                // if (endTimeTemp[0]) 
                // {
                //     opportunityFlowEndTime = endTimeTemp[0]
                // }
                // else
                // {
                //     endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 186")
                //     if (endTimeTemp[0]) 
                //     {
                //         opportunityFlowEndTime = endTimeTemp[0]
                //     }
                //     else
                //     {
                //         opportunityFlowEndTime = it[9]
                //     }
                // }

                // println opportunityFlowEndTime

                // Calendar calendar = Calendar.getInstance()
                // calendar.setTime(opportunityFlowEndTime)
                // list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")
            }

            // 城市
            list3[2] = it[7]

            // 放款账号
            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[6]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            list3[3] = oof1[0]

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[4] = contract[0]
            }
            else
            {
                list3[4] = it[0]
            }

            // 借款人
            list3[5] = it[1]

            // 放款金额
            if (it[2] && it[2] != 0)
            {
                list3[6] = it[2]
            }
            else
            {
                def actualAmountOfCredit = Opportunity.executeQuery("select o.actualAmountOfCredit from Opportunity as o where o.id = ${it[6]}")
                list3[6] = actualAmountOfCredit[0]
            }

            // 合同借款期限（月）
            list3[7] = it[4]

            def opportunity = Opportunity.findById(it[6])
            // 实际借款期限
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }

            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity?.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            // println "lilv:" + lilv
            // println "fuwufeilv1:" + fuwufeilv1
            // println "fuwufeilv2:" + fuwufeilv2

            // 月月返利率
            def rate4 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[10]}")
            // 一次性返利率
            def rate5 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[10]}")
            if (!rate4[0])
            {
                rate4[0] = 0
            }
            if (!rate5[0])
            {
                rate5[0] = 0
            }

            list3[8] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[9] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[10] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率
            list3[11] = rate4[0] // 月月返利率
            list3[12] = rate5[0] // 一次性返利率

            list.add(list3)
        }

        println "放款基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "放款账号确定时间", "城市", "放款账户", "合同号", "借款人姓名", "放款金额(万元)", "放款期限", "月利率", "月服务费比率", "一次性服务费比率", "月返费比率", "一次性返费比率"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            if (flag == 'AM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo2'
            }
            if (flag == 'PM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo3'
            }
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出当天放款基本信息（区域经理）
     * @ ModifiedDate 2017-614*/
    @Transactional
    def exportLoanInfo4(Integer max)
    {
        println "========================= exportLoanInfo4: export Loan Info ===================================="

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def city = user?.city?.name

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def flag = params.flag
        def report = params.report
        def count = 0

        Calendar calendar1 = Calendar.getInstance()
        calendar1.setTime(new Date())
        def nowDate = calendar1.getTime().format("yyyy-MM-dd")

        def yesterdayDate
        def week = calendar1.get(Calendar.DAY_OF_WEEK) - 1
        if (week == 1)
        {
            calendar1.add(calendar1.DATE, -3)
            yesterdayDate = calendar1.getTime().format("yyyy-MM-dd")
        }
        else
        {
            calendar1.add(calendar1.DATE, -1)
            yesterdayDate = calendar1.getTime().format("yyyy-MM-dd")
        }

        def startTime
        def endTime
        if (flag == 'AM')
        {
            startTime = yesterdayDate + ' 12:00:00'
            endTime = nowDate + ' 08:59:59'
        }
        if (flag == 'PM')
        {
            startTime = nowDate + ' 09:00:00'
            endTime = nowDate + ' 11:59:59'
        }

        println startTime
        println endTime

        def list = []
        def sql = "select distinct o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, c.city.name, o.serialNumber, o.modifiedDate, o.productAccount.id from OpportunityFlow ofo, Opportunity o, Collateral c where ofo.opportunity.id = o.id and o.id = c.opportunity.id and o.status <> 'Failed' and o.isTest = false and ofo.stage.id in (186, 23) and ofo.endTime is not null and (o.id in (select oc.opportunity.id from OpportunityContract as oc where oc.contract.id in (select id FROM Contract where serialNumber is not null and type.id = 1)) or o.externalId is not null)"
        if (startTime && endTime)
        {
            sql += " and ofo.endTime between '${startTime}' and '${endTime}'"
        }
        else
        {
            if (flag == 'AM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo2'
                return
            }
            if (flag == 'PM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo3'
                return
            }
        }
        if (city)
        {
            sql += " and c.city.name = '${city}'"
        }

        def opportunityList = null
        if (report == "yes")
        {
            opportunityList = Opportunity.executeQuery(sql)
        }
        else
        {
            opportunityList = Opportunity.executeQuery(sql, [max: max, offset: offset])
        }
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 订单号
            list3[0] = it[8]

            // 放款账号确定时间
            if (it[9])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[9])
                list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")

                // def opportunityFlowEndTime
                // def endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 23")
                // if (endTimeTemp[0]) 
                // {
                //     opportunityFlowEndTime = endTimeTemp[0]
                // }
                // else
                // {
                //     endTimeTemp = OpportunityFlow.executeQuery("select endTime from OpportunityFlow where opportunity.id = ${it[6]} and stage.id = 186")
                //     if (endTimeTemp[0]) 
                //     {
                //         opportunityFlowEndTime = endTimeTemp[0]
                //     }
                //     else
                //     {
                //         opportunityFlowEndTime = it[9]
                //     }
                // }

                // println opportunityFlowEndTime

                // Calendar calendar = Calendar.getInstance()
                // calendar.setTime(opportunityFlowEndTime)
                // list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")
            }

            // 城市
            list3[2] = it[7]

            // 放款账号
            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[6]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            list3[3] = oof1[0]

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[4] = contract[0]
            }
            else
            {
                list3[4] = it[0]
            }

            // 借款人
            list3[5] = it[1]

            // 放款金额
            if (it[2] && it[2] != 0)
            {
                list3[6] = it[2]
            }
            else
            {
                def actualAmountOfCredit = Opportunity.executeQuery("select o.actualAmountOfCredit from Opportunity as o where o.id = ${it[6]}")
                list3[6] = actualAmountOfCredit[0]
            }

            // 合同借款期限（月）
            list3[7] = it[4]

            def opportunity = Opportunity.findById(it[6])
            // 实际借款期限
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }

            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            // println "lilv:" + lilv
            // println "fuwufeilv1:" + fuwufeilv1
            // println "fuwufeilv2:" + fuwufeilv2

            // 月月返利率
            def rate4 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[10]}")
            // 一次性返利率
            def rate5 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[10]}")
            if (!rate4[0])
            {
                rate4[0] = 0
            }
            if (!rate5[0])
            {
                rate5[0] = 0
            }

            list3[8] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[9] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[10] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率
            list3[11] = rate4[0] // 月月返利率
            list3[12] = rate5[0] // 一次性返利率

            list.add(list3)
        }

        println "放款基本信息记录条数：" + count

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单号", "放款账号确定时间", "城市", "放款账户", "合同号", "借款人姓名", "放款金额(万元)", "放款期限", "月利率", "月服务费比率", "一次性服务费比率", "月返费比率", "一次性返费比率"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            if (flag == 'AM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo2'
            }
            if (flag == 'PM')
            {
                respond list, model: [list: list, count: count], view: 'exportLoanInfo3'
            }
        }
    }

    /**
     * @ Author 张成远
     * @ function 导出贷款审批（导出 审批已完成阶段 后的订单）
     * @ ModifiedDate 2017-7-14*/
    @Transactional
    def exportloanApproval(Integer max)
    {
        println "========================= exportloanApproval: 导出贷款审批基本信息 ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def serialNumber = params.serialNumber
        def report = params.report
        def count = 0

        def list = []
        def sql = "select o.id, o.serialNumber, o.user.city.name, o.fullName, o.lender.level.description, o.product.name, o.account.name, o.requestedAmount, o.actualAmountOfCredit, o.actualLoanAmount, ofo.startTime, o.mortgageCertificateType.name, o.createdDate from OpportunityFlow ofo right join ofo.opportunity o where ofo.stage.id = 8 and ofo.startTime is not null and o.status <> 'Failed' and o.isTest = 0"
        if (startTime && endTime)
        {
            sql += " and ofo.startTime between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and o.user.city.name = '${city}'"
        }
        if (serialNumber)
        {
            sql += " and o.serialNumber like '%${serialNumber}%'"
        }
        sql += " order by o.createdDate desc"

        if (report == "yes")
        {
            list = OpportunityFlow.executeQuery(sql)
        }
        else
        {
            list = OpportunityFlow.executeQuery(sql, [max: max, offset: offset])
        }
        count = OpportunityFlow.executeQuery(sql)?.size()

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单ID", "订单号", "城市", "借款人", "客户级别", "产品", "渠道名称", "申请总价", "实际授信金额", "实际放款金额", "审批日期", "抵押凭证", "申请日期"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * TODO
     * @Description 导出还款计划 for 于鑫
     * @Author Nagelan
     * @DateTime 2017/8/25 0025 19:13
     */
    @Transactional
    def exportBillsItems(Integer max)
    {
        println "========================= exportBillsItems: 导出还款计划 ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def startTime = params.startTime
        def endTime = params.endTime
        def city = params.city
        def serialNumber = params.serialNumber
        def report = params.report
        def count

        def list
        def sql = "select o.id, o.serialNumber, o.user.city.name, o.fullName, o.lender.level.description, o.product.name, o.account.name, o.requestedAmount, o.actualAmountOfCredit, o.actualLoanAmount, ofo.startTime, o.mortgageCertificateType.name, o.createdDate from OpportunityFlow ofo right join ofo.opportunity o where ofo.stage.id = 8 and ofo.startTime is not null and o.status <> 'Failed' and o.isTest = 0"
        if (startTime && endTime)
        {
            sql += " and ofo.startTime between '${startTime}' and '${endTime}'"
        }
        if (city != "--CITY--" && city)
        {
            sql += " and o.user.city.name = '${city}'"
        }
        if (serialNumber)
        {
            sql += " and o.serialNumber like '%${serialNumber}%'"
        }
        sql += " order by o.createdDate desc"

        if (report == "yes")
        {
            list = OpportunityFlow.executeQuery(sql)
        }
        else
        {
            list = OpportunityFlow.executeQuery(sql, [max: max, offset: offset])
        }
        count = OpportunityFlow.executeQuery(sql)?.size()

        if (report == "yes")
        {
            def listTitle = new ArrayList<Map>(Arrays.asList("订单ID", "订单号", "城市", "借款人", "客户级别", "产品", "渠道名称", "申请总价", "实际授信金额", "实际放款金额", "审批日期", "抵押凭证", "申请日期"))
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, list)
            return null
        }
        else
        {
            respond list, model: [list: list, count: count]
        }
    }

    /**
     * @Description 放款台账 贷后
     * @Author yuanchao
     * @Return
     * @DateTime 2017/9/8 10:44
     */
    @Secured(['ROLE_ADMINISTRATOR','ROLE_COO','ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER' ])
    @Transactional
    def loanReport(Integer max)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
       //分页
        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        //下载
        def report = params.report
       //搜索条件
        def loanDate = params.loanDate
        def endDate = params.endDate
        def city = params.city
        def product = params.product
        def fullName = params.fullName
        def contract = params.contract
        def jqStatus = params.jqStatus
        def jqDate = params.jqDate
        def sql = "select distinct o.id  from Opportunity o , Collateral c ,OpportunityContract oc , Bills b where  c.opportunity.id = o.id and oc.opportunity.id = o.id and  b.opportunity.id  = o.id "
        if(loanDate){
            sql+="  and  CONVERT(varchar(100), o.actualLendingDate, 120) like '${loanDate}%'"
        }

        if(fullName){
            sql+=" and o.fullName like '%${fullName}%'"
        }
        if(product&&product!='null'){
            sql+=" and o.product.name = '${product}'"
        }
        //判断是否为贷后经理
        if(UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER"))){
                sql+= "  and c.city.name = '${user.city.name}'  "
        } else {
            if(city&&city!='null'){
                sql+= "  and c.city.name = '${city}'  "
            }
        }

        if(contract){
            sql+= "  and oc.contract.serialNumber = '${contract}'   "
        }
        if(endDate){
            sql+=" and CONVERT(varchar(100), b.endTime, 120) like '${endDate}%'  "
        }
        if(jqDate){
            sql+=" and CONVERT(varchar(100), o.jqDate, 120) like '${jqDate}%'  "
        }
        if(jqStatus && jqStatus!='null'){
            sql+= "  and o.jqStatus = '${jqStatus}'   "
        }
        sql +=" and o.type.name = '抵押贷款' and o.user.cellphone not in ('13718807260','18334787364','15801173559','13581657148','15201108192','15811351299','13831182365','18811374407','18513603065') and o.status != 'Failed' and o.isTest = 0"
        sql += " and oc.contract.type.name='借款合同' and o.actualLendingDate is not null "
        println sql
        def ids
        if (report == "yes")
        {
            ids = Opportunity.executeQuery(sql + " order by o.id")
        }
        else
        {
            ids = Opportunity.executeQuery(sql + " order by o.id", [max: max, offset: offset])
        }
        def count = Opportunity.executeQuery(sql + " order by o.id ").size()
        def listReport = []
        def listAdd = {oo , list1->
            if(oo){
                list1.add(oo)
            }else {
                list1.add("")
            }
        }
        ids.each {
            def list = []
            def opp = Opportunity.findById(it)
            def sdf = new SimpleDateFormat("yyyy-MM-dd")
            //1订单号
            def serialNumber = opp.serialNumber
            //2城市
            def cityName = opp.collaterals[0].city.name
            //3产品类型
            def productTypeName  = opp.product.name
            //4收息方式
            def interestPaymentMethod = opp.interestPaymentMethod.name
            //5借款人姓名
            def allFullName  = opp.fullName+"、"
            opp.contacts.each {
                if(it.type.name != "借款人"){
                    allFullName += it.contact.fullName +"、"
                }

            }
            allFullName = allFullName.substring(0,allFullName.lastIndexOf("、"))
            //6合同编号
            def contractNumber = OpportunityContract.find("from OpportunityContract where opportunity.id = ${opp.id} and contract.type.name = '借款合同'")?.contract?.serialNumber
            //7抵押物位置
            def address = opp.collaterals[0].address
            //8抵押类型
            def loanType =  opp.collaterals[0].mortgageType?.name
            //9抵押率
            def loanToValue = opp.collaterals[0].loanToValue
            //10放款通道
            def door
            //11放款账号
            def account

            def fieldsList = OpportunityFlexFieldCategory.findAllByOpportunity(opp)?.fields
            fieldsList.each {
                it.each {
                    if (it.name == "放款通道")
                    {
                        door = it.value
                    }
                    if (it.name == "放款账号")
                    {
                        account = it.value
                    }
                }
            }
            //12实际放款金额
            def actualLoanAmount= opp.actualLoanAmount == null ? opp.actualAmountOfCredit : opp.actualLoanAmount
            //13实际放款日期
            def loanDate1 = sdf.format(opp.actualLendingDate)
            //14实际借款期限
            def actualLoanDuration = opp.actualLoanDuration
            //15到期日期
            def endDate1 = sdf.format(opp.bills?.endTime[0])
            //16 综合息费/月	利息/月+服务费率/月s
            def combinedRatio = 0
            //17 月利息	基本费率/月+归到月息的加息项/月
            def monthlyInterest = 0
            //18 服务费率/月	按月取
            def serviceCharge = 0
            //19 渠道返费率/月	按月取
            def commissionRate = 0
            //20 渠道费代收方式	费用模块中渠道返费费率的收费方式----按月收、一次性收
            def commissionPaymentMethod = ""

            def bankAccount1 =  OpportunityFlexField.findByNameAndCategory("债转账号",OpportunityFlexFieldCategory.findByOpportunityAndFlexFieldCategory(opp,FlexFieldCategory.findByName("资金渠道")))?.value

            opp.interest.each {
                def productName= it.productInterestType.name
                def notInList = ['意向金','展期费','早偿违约金','本金违约金','利息违约金','本金']
                if(!notInList.contains(productName)){
                    if (productName == "基本费率"){
                        if (!it.installments)
                        {
                            monthlyInterest += it.rate / actualLoanDuration
                        }
                        else
                        {
                            monthlyInterest += it.rate
                        }
                    } else if (productName== "服务费费率"){
                        if (!it.installments)
                        {
                            serviceCharge += it.rate / actualLoanDuration
                        }
                        else
                        {
                            serviceCharge += it.rate
                        }
                    } else if (productName == "渠道返费费率"){

                        if (!it.installments)
                        {
                            commissionRate += it.rate / actualLoanDuration
                            commissionPaymentMethod = "一次性"
                        }
                        else
                        {
                            commissionRate += it.rate
                            commissionPaymentMethod = "按月"
                        }
                    } else {
                        if (it.contractType?.name == "借款合同")
                        {
                            if (!it.installments)
                            {
                                monthlyInterest += it.rate / actualLoanDuration
                            }
                            else
                            {
                                monthlyInterest += it.rate //费率金额 月
                            }
                        } else if (it.contractType?.name == "委托借款代理服务合同")
                        {
                            if (!it.installments)
                            {
                                serviceCharge += it.rate / actualLoanDuration
                            }
                            else
                            {
                                serviceCharge += it.rate //费率金额 月
                            }
                        } else
                        {
                            if (!it.installments)
                            {
                                monthlyInterest += it.rate / actualLoanDuration
                            }
                            else
                            {
                                monthlyInterest += it.rate //费率金额 月
                            }
                        }
                    }

                }


            }
            combinedRatio += monthlyInterest+ serviceCharge
            //保留两位小数
            def toBigDecimal = {rate->
                if(rate=="0"|| rate==0){
                    return "0"
                } else {
                    rate = new BigDecimal(rate).setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                    return rate
                }

            }
            listAdd(serialNumber,list)//1
            listAdd(cityName,list)//1
            listAdd(productTypeName,list)//3
            listAdd(interestPaymentMethod,list)//4
            listAdd(allFullName,list)//5
            listAdd(contractNumber,list)//6
            listAdd(address,list)//7
            listAdd(loanType,list)//8
            listAdd(toBigDecimal(loanToValue),list)//9
            listAdd(door,list)//10
            listAdd(account,list)//11
            listAdd(bankAccount1,list)//23
            listAdd(actualLoanAmount,list)//12
            listAdd(loanDate1,list)//13
            listAdd(actualLoanDuration,list)//14
            listAdd(endDate1,list)//15
            listAdd(toBigDecimal(combinedRatio),list)//16
            listAdd(toBigDecimal(monthlyInterest),list)//17
            listAdd(toBigDecimal(serviceCharge),list)//18
            listAdd(toBigDecimal(commissionRate),list)//19
            listAdd(commissionPaymentMethod,list)//20
            listAdd(opp?.jqStatus,list)//21
            listAdd(opp?.jqDate?.format("yyyy-MM-dd"),list)//22

            listReport.add(list)






        }
        def listTitle = ["订单编号", "区域", "产品类型", "收息方式", "借款人姓名", "合同编号", "抵押物位置", "抵押顺位", "抵押率(%)", "放款模式", "放款账户", "债转账户","借款金额（万元）", "放款日期", "借款期限（月）", "到期日期", "综合息费/月(%)", "月利息(%)", "服务费率/月(%)", "渠道返费率/月(%)", "渠道费代收方式","是否结清","结清日期"]

        if (report == "yes")
        {
            response.setContentType("text/csv")
            response.setContentType("application/csv;charset=GBK")
            response.setHeader("Content-Disposition", "attachment;FileName=report.csv")
            OutputStream out = null
            try
            {
                out = response.getOutputStream()
            }
            catch (Exception e)
            {
                e.printStackTrace()
            }
            opportunityStatisticsService.exportExcel(out, listTitle, listReport)
            return null
        }
        else
        {

            respond listReport, model: [list: listReport, count: count, params: params,listTitle:listTitle]
        }
    }
/**
 * @Description
 * @Author bigyuan
 * @Return 周一上线 融数
 * @DateTime 2017/10/20 21:28
 */
    @Secured(["permitAll"])
    @Transactional
    def rongshuLoanInfo(Integer max)
    {
        println "========================= loanReport2: export Loan Info ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def flag = params.flag
        def report = params.report
        def count = 0
        def fullName = params.fullName
        def contractNumber   = params.contract
        def serialNumber  = params.serialNumber
        def accountStartDate = params.accountStartDate
        def accountEndDate = params.accountEndDate
        def approvalStartDate= params.approvalStartDate
        def approvalEndDate= params.approvalEndDate
        def status = params.rongShuStatus


        def list = []
        def sql = "select distinct o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, c.city.name, o.serialNumber, o.modifiedDate, o.productAccount.id , o.rongShuStatus ,o.rongShuApprovalDate from OpportunityFlow ofo, Opportunity o, Collateral c , OpportunityFlexField off , OpportunityFlexFieldCategory offc "
        if(contractNumber){
            sql += " ,OpportunityContract oct "
        }
        sql += " where offc.id = off.category.id and offc.opportunity.id = o.id and ofo.opportunity.id = o.id and o.id = c.opportunity.id and o.status <> 'Failed' and o.isTest = false and ofo.stage.id in (186, 23) and ofo.endTime is not null and (o.id in (select oc.opportunity.id from OpportunityContract as oc where oc.contract.id in (select id FROM Contract where serialNumber is not null and type.id = 1)) or o.externalId is not null)"
        sql += " and off.value = '外贸555'"
        if(contractNumber){
            sql += " and oct.opportunity.id = o.id and oct.contract.type.name = '借款合同' and oct.contract.serialNumber like '%${contractNumber}%'"
        }
        if(fullName){
            sql += " and o.fullName like '%${fullName}%'"
        }
        if(serialNumber){
            sql += " and o.serialNumber like '%${serialNumber}%'"
        }
        if(accountStartDate){
            sql += "  and o.modifiedDate > '${accountStartDate}'"
        }
        if(accountEndDate){
            sql += "  and o.modifiedDate < '${accountEndDate}'"
        }
        if(approvalStartDate){
            sql += "  and o.rongShuApprovalDate > '${approvalStartDate}'"
        }
        if(approvalEndDate){
            sql += "  and o.rongShuApprovalDate < '${approvalEndDate}'"
        }
        if(status && status!="请选择外贸审核状态") {
            sql += "  and o.rongShuStatus = '${status}'"
        }
        def opportunityList = Opportunity.executeQuery(sql+" order by o.modifiedDate ",[max: max,offset: offset])
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 订单号
            list3[0] = it[8]

            // 放款账号确定时间
            if (it[9])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[9])
                list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")

            }

            // 城市
            list3[2] = it[7]

            // 放款账号
            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[6]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            list3[3] = oof1[0]

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[4] = contract[0]
            }
            else
            {
                list3[4] = it[0]
            }

            // 借款人
            list3[5] = it[1]

            // 放款金额
            if (it[2] && it[2] != 0)
            {
                list3[6] = it[2]
            }
            else
            {
                def actualAmountOfCredit = Opportunity.executeQuery("select o.actualAmountOfCredit from Opportunity as o where o.id = ${it[6]}")
                list3[6] = actualAmountOfCredit[0]
            }

            // 合同借款期限（月）
            list3[7] = it[4]

            def opportunity = Opportunity.findById(it[6])
            // 实际借款期限
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }

            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity?.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            // println "lilv:" + lilv
            // println "fuwufeilv1:" + fuwufeilv1
            // println "fuwufeilv2:" + fuwufeilv2

            // 月月返利率
            def rate4 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[10]}")
            // 一次性返利率
            def rate5 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[10]}")
            if (!rate4[0])
            {
                rate4[0] = 0
            }
            if (!rate5[0])
            {
                rate5[0] = 0
            }

            list3[8] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[9] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[10] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率
            list3[11] = rate4[0] // 月月返利率
            list3[12] = rate5[0] // 一次性返利率
            list3[13] = it[11]
            list3[14] = it[12]
            list.add(list3)
        }

        println "放款基本信息记录条数：" + count
        respond list, model: [list: list, count: count,params: params], view: 'rongshuLoanInfo'

    }
/**
 * @Description
 * @Author bigyuan
 * @Return  华融
 * @DateTime 2017/10/20 21:28
 */
    @Secured(["permitAll"])
    @Transactional
    def huarongLoanInfo(Integer max)
    {
        println "========================= loanReport2: export Loan Info ===================================="

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def flag = params.flag
        def report = params.report
        def count = 0
        def fullName = params.fullName
        def contractNumber   = params.contract
        def serialNumber  = params.serialNumber


        def list = []
        def sql = "select distinct o.externalId, o.fullName, o.actualLoanAmount, o.actualLendingDate, o.actualLoanDuration, o.actuaRepaymentDate, o.id, c.city.name, o.serialNumber, o.modifiedDate, o.productAccount.id from OpportunityFlow ofo, Opportunity o, Collateral c , OpportunityFlexField off , OpportunityFlexFieldCategory offc "
        if(contractNumber){
            sql += " ,OpportunityContract oct "
        }
        sql += " where offc.id = off.category.id and offc.opportunity.id = o.id and ofo.opportunity.id = o.id and o.id = c.opportunity.id and o.status <> 'Failed' and o.isTest = false and ofo.stage.id in (186, 23) and ofo.endTime is not null and (o.id in (select oc.opportunity.id from OpportunityContract as oc where oc.contract.id in (select id FROM Contract where serialNumber is not null and type.id = 1)) or o.externalId is not null)"
        sql += " and off.value = '中航669'"
        if(contractNumber){
            sql += " and oct.opportunity.id = o.id and oct.contract.type.name = '借款合同' and oct.contract.serialNumber like '%${contractNumber}%'"
        }
        if(fullName){
            sql += " and o.fullName like '%${fullName}%'"
        }

        if(serialNumber){
            sql += " and o.serialNumber like '%${serialNumber}%'"
        }
        def opportunityList = Opportunity.executeQuery(sql,[max: max,offset: offset])
        count = Opportunity.executeQuery(sql)?.size()

        opportunityList.each {
            def list3 = []

            // 订单号
            list3[0] = it[8]

            // 放款账号确定时间
            if (it[9])
            {
                Calendar calendar = Calendar.getInstance()
                calendar.setTime(it[9])
                list3[1] = calendar.getTime().format("yyyy-MM-dd HH:mm:ss")

            }

            // 城市
            list3[2] = it[7]

            // 放款账号
            def sql1 = "select o.value from OpportunityFlexField as o where o.category.id = (select c.id from OpportunityFlexFieldCategory as c where c.opportunity.id = ${it[6]} and c.flexFieldCategory.id = 13) and o.name = '放款账号'"
            def oof1 = OpportunityFlexField.executeQuery(sql1)
            list3[3] = oof1[0]

            // 合同编号
            def contract = Contract.executeQuery("select c.serialNumber from OpportunityContract oc left join oc.contract c left join oc.opportunity o where o.id = ${it[6]}")
            if (contract)
            {
                list3[4] = contract[0]
            }
            else
            {
                list3[4] = it[0]
            }

            // 借款人
            list3[5] = it[1]

            // 放款金额
            if (it[2] && it[2] != 0)
            {
                list3[6] = it[2]
            }
            else
            {
                def actualAmountOfCredit = Opportunity.executeQuery("select o.actualAmountOfCredit from Opportunity as o where o.id = ${it[6]}")
                list3[6] = actualAmountOfCredit[0]
            }

            // 合同借款期限（月）
            list3[7] = it[4]

            def opportunity = Opportunity.findById(it[6])
            // 实际借款期限
            def term = opportunity.actualLoanDuration

            // 基本费率
            def rate1 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = true and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                def ss = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 1 and installments = false and t.product.id = ${it[10]}")
                if (ss[0])
                {
                    rate1[0] = ss[0] / term
                }
            }

            // 月服务费利率
            def rate2 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = true and t.product.id = ${it[10]}")
            // 一次性服务费利率
            def rate3 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 2 and installments = false and t.product.id = ${it[10]}")
            if (!rate1[0])
            {
                rate1[0] = 0
            }
            if (!rate2[0])
            {
                rate2[0] = 0
            }
            if (!rate3[0])
            {
                rate3[0] = 0
            }

            // 基础息费 加息费率
            def lilv = 0
            // 月服务费费率 加息费率
            def fuwufeilv1 = 0
            // 一次性服务费费率 加息费率
            def fuwufeilv2 = 0

            def opportunityProductList = OpportunityProduct.findAllByOpportunityAndProduct(opportunity,opportunity?.productAccount)
            opportunityProductList?.each {
                if (it?.productInterestType?.name == "郊县" || it?.productInterestType?.name == "大头小尾" || it?.productInterestType?.name == "信用调整" || it?.productInterestType?.name == "二抵加收费率" || it?.productInterestType?.name == "老人房（65周岁以上）" || it?.productInterestType?.name == "老龄房（房龄35年以上）" || it?.productInterestType?.name == "非7成区域" || it?.productInterestType?.name == "大额（单套大于1200万）")
                {
                    if (it.contractType?.name == "借款合同")
                    {
                        if (!it.installments)
                        {
                            // 加息项类型为一次性加息，需要折合成按月加息
                            java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                            lilv += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                        }
                        else
                        {
                            // 加息项类型为按月加息
                            lilv += it.rate
                        }
                    }
                    if (it.contractType?.name == "委托借款代理服务合同")
                    {
                        if (!it.installments)
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为一次性加息，需要折合成按月加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate / term)
                                fuwufeilv1 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为一次性加息
                                fuwufeilv2 += it.rate
                            }
                        }
                        else
                        {
                            if (rate2[0] != 0)
                            {
                                // 月服务费费率：加息项类型为按月加息
                                fuwufeilv1 += it.rate
                            }
                            if (rate3[0] != 0)
                            {
                                // 一次性服务费费率：加息项类型为按月加息，需要折合成一次性加息
                                java.math.BigDecimal serviceCharge = new java.math.BigDecimal(it.rate * term)
                                fuwufeilv2 += serviceCharge.setScale(9, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                            }
                        }
                    }
                }
            }

            // println "lilv:" + lilv
            // println "fuwufeilv1:" + fuwufeilv1
            // println "fuwufeilv2:" + fuwufeilv2

            // 月月返利率
            def rate4 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = true and t.product.id = ${it[10]}")
            // 一次性返利率
            def rate5 = OpportunityProduct.executeQuery("select t.rate from OpportunityProduct as t where t.opportunity.id = ${it[6]} and t.productInterestType.id = 3 and installments = false and t.product.id = ${it[10]}")
            if (!rate4[0])
            {
                rate4[0] = 0
            }
            if (!rate5[0])
            {
                rate5[0] = 0
            }

            list3[8] = rate1[0] + lilv // 基础费率 + 加息费率
            list3[9] = rate2[0] + fuwufeilv1 // 月服务费利率 + 加息费率
            list3[10] = rate3[0] + fuwufeilv2 // 一次性服务费率 + 加息费率
            list3[11] = rate4[0] // 月月返利率
            list3[12] = rate5[0] // 一次性返利率

            list.add(list3)
        }

        println "华融放款基本信息记录条数：" + count
        respond list, model: [list: list, count: count], view: 'huarongLoanInfo'

    }
    @Transactional
    def show26(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        //权利人入库单
        def notarizationsList = []
        if(opportunity.notarizations){
            notarizationsList = opportunity.notarizations.split(",")
        }
        def transactionRecords
        if (!opportunity?.type || opportunity?.type?.code == "10")
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.id} order by plannedStartTime asc")
        }
        else
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.parent?.id} order by plannedStartTime asc")
        }

        //查询相同房产证的订单（在途非测试）
        //def sameHouseOrders = Opportunity.executeQuery("select * from Collateral c left join c.opportunity o where c.propertySerialNumber = '${opportunity?.collaterals[0].propertySerialNumber}' and o.id != ${opportunity?.id} and ")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }


        // def owner = opportunity?.user
        // def cityName = owner?.city?.name
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
        // if (collaterals?.size() == 1)
        // {
        //     def collateral = Collateral.findByOpportunity(opportunity)
        //     if (!collateral?.propertySerialNumber && opportunity?.propertySerialNumber)
        //     {
        //         collateral.propertySerialNumber = opportunity?.propertySerialNumber
        //     }
        //     if (!collateral?.firstMortgageAmount && opportunity?.firstMortgageAmount)
        //     {
        //         collateral.firstMortgageAmount = opportunity?.firstMortgageAmount
        //     }
        //     if (!collateral?.secondMortgageAmount && opportunity?.secondMortgageAmount)
        //     {
        //         collateral.secondMortgageAmount = opportunity?.secondMortgageAmount
        //     }
        //     if (!collateral?.mortgageType && opportunity?.mortgageType)
        //     {
        //         collateral.mortgageType = opportunity?.mortgageType
        //     }
        //     if (!collateral?.typeOfFirstMortgage && opportunity?.typeOfFirstMortgage)
        //     {
        //         collateral.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
        //     }
        //     collateral.save flush: true
        // }
        def canSpecialApproval = false
        def specialApprovalAttachments = Attachments.findByOpportunityAndType(opportunity, AttachmentType.findByName('特批签呈'))
        if (specialApprovalAttachments && opportunity?.status == 'Failed' && currentRole?.teamRole?.name == 'Approval')
        {
            if (!(opportunity?.subtype))
            {
                canSpecialApproval = true
            }
            else if (opportunity?.subtype?.name == '正常审批')
            {
                canSpecialApproval = true
            }
        }
        def canQueryPrice = true

        def regionalRiskManageStage = OpportunityStage.findByCode("08")
        def regionalRiskManageFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, regionalRiskManageStage)
        if (currentFlow?.executionSequence >= regionalRiskManageFlow?.executionSequence)
        {
            canQueryPrice = false
        }
        def canCreditReportShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            canCreditReportShow = true
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_HEAD_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        {
            if (currentRole?.teamRole?.name == 'Approval')
            {
                canCreditReportShow = true
            }
        }
        def canAttachmentsShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            canAttachmentsShow = true
        }
        else if (currentRole?.teamRole?.name == 'Approval')
        {
            canAttachmentsShow = true
        }
        def canPhotosShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")))
        {
            canPhotosShow = true
        }
        def canLoanReceiptShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            canLoanReceiptShow = true
        }

        def canInterestEdit = true
        def loanCompletionStage = OpportunityStage.findByCode("08")
        def loanCompletionFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, loanCompletionStage)
        if ((opportunity?.parent && opportunity?.type?.code == '30') || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            canInterestEdit = true
        }
        else if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canInterestEdit = false
        }

        def canbillsShow = false
        if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canbillsShow = true
        }
        def users = OpportunityContact.findAllByOpportunity(opportunity?.parent)
        def borrowers = ""
        users.each {
            borrowers += it.contact?.fullName + ","
        }
        if (users)
        {
            borrowers = StringUtils.substringBeforeLast(borrowers, ",")
        }
        def billsItems
        def bills = Bills.findByOpportunity(opportunity)
        if (opportunity?.type?.name == "抵押贷款" && bills)
        {
            billsItems = BillsItem.findAllByBills(bills,[sort:"period"])
        }
        else if (opportunity?.type?.name != "抵押贷款")
        {
            bills = Bills.findByOpportunity(opportunity?.parent)
            if (bills)
            {
                billsItems = BillsItem.findAllByBills(bills,[sort:"period"])
            }
        }
        def opportunityComments = OpportunityComments.findAllByOpportunityAndAction(opportunity, null)
        def opportunityWorkflows = []
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        territoryOpportunityWorkflows?.each {
            opportunityWorkflows.add(it?.workflow)
        }
        //layout
        def opportunityLayout
        if (!opportunityLayout)
        {
            opportunityFlows?.each {
                if (it?.stage == opportunity?.stage)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityRoles?.each {
                if (it?.stage == opportunity?.stage && it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityTeams?.each {
                if (it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }
        }

            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                                         //                creditReport: creditReport,
                                         opportunityContacts: opportunityContacts, borrowers: borrowers,notarizationsList:notarizationsList,
                                         activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, transactionRecords: transactionRecords, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                                         opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, opportunityWorkflows: opportunityWorkflows, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: 'show26'

    }

    def show27(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            // if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            // {
            def history
            if (opportunity && opportunity?.serialNumber)
            {
                history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
            }
            def code = opportunity?.stage?.code
            def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
            def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
            def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
            def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
            def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
            def opportunityStage = OpportunityStage.findByCode("38")
            def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
            //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
            def opportunityContacts = OpportunityContact.findAll("from OpportunityContact where opportunity.id = ${opportunity.id} order by type.id")
            //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
            def activities = Activity.findAllByOpportunity(opportunity)
            def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
            def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
            def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
            def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
            def progressPercent
            def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

            def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
            def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

            def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
            def subUsers = []
            def reportings
            if (currentRole?.teamRole?.name == 'Approval')
            {
                reportings = Reporting.findAllByManager(user)
                reportings?.each {
                    subUsers.add(it?.user)
                }
            }

            def canQueryPrice = false
            def canAttachmentsShow = true
            def canCreditReportShow = true
            def canInterestEdit = false

            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                                         //                    creditReport: creditReport,
                                         opportunityContacts: opportunityContacts,
                                         activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                                         collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow, canQueryPrice: canQueryPrice, canAttachmentsShow: canAttachmentsShow, canCreditReportShow: canCreditReportShow, canInterestEdit: canInterestEdit]

        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }

    }

    @Transactional
    def show28(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def history
        if (opportunity && opportunity?.serialNumber)
        {
            history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
        }
        def code = opportunity?.stage?.code
        def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
        def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
        def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
        def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
        def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
        def opportunityStage = OpportunityStage.findByCode("38")
        def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
        //        def creditReport = CreditReport.findAllByOpportunity(opportunity)
        def opportunityContacts = OpportunityContact.findAllByOpportunity(opportunity)
        //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
        def activities = Activity.findAllByOpportunity(opportunity)
        def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
        def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
        def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
        def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
        def progressPercent
        def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")
        //权利人入库单
        def notarizationsList = []
        if(opportunity.notarizations){
            notarizationsList = opportunity.notarizations.split(",")
        }
        def transactionRecords
        if (!opportunity?.type || opportunity?.type?.code == "10")
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.id} order by plannedStartTime asc")
        }
        else
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.parent?.id} order by plannedStartTime asc")
        }

        //查询相同房产证的订单（在途非测试）
        //def sameHouseOrders = Opportunity.executeQuery("select * from Collateral c left join c.opportunity o where c.propertySerialNumber = '${opportunity?.collaterals[0].propertySerialNumber}' and o.id != ${opportunity?.id} and ")

        def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)

        def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
        def subUsers = []
        def reportings
        if (currentRole?.teamRole?.name == 'Approval')
        {
            reportings = Reporting.findAllByManager(user)
            reportings?.each {
                subUsers.add(it?.user)
            }
        }

        if (totalProgress > 0)
        {
            progressPercent = currentProgress / totalProgress
        }
        else
        {
            progressPercent = 0
        }


        // def owner = opportunity?.user
        // def cityName = owner?.city?.name
        def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")
        // if (collaterals?.size() == 1)
        // {
        //     def collateral = Collateral.findByOpportunity(opportunity)
        //     if (!collateral?.propertySerialNumber && opportunity?.propertySerialNumber)
        //     {
        //         collateral.propertySerialNumber = opportunity?.propertySerialNumber
        //     }
        //     if (!collateral?.firstMortgageAmount && opportunity?.firstMortgageAmount)
        //     {
        //         collateral.firstMortgageAmount = opportunity?.firstMortgageAmount
        //     }
        //     if (!collateral?.secondMortgageAmount && opportunity?.secondMortgageAmount)
        //     {
        //         collateral.secondMortgageAmount = opportunity?.secondMortgageAmount
        //     }
        //     if (!collateral?.mortgageType && opportunity?.mortgageType)
        //     {
        //         collateral.mortgageType = opportunity?.mortgageType
        //     }
        //     if (!collateral?.typeOfFirstMortgage && opportunity?.typeOfFirstMortgage)
        //     {
        //         collateral.typeOfFirstMortgage = opportunity?.typeOfFirstMortgage
        //     }
        //     collateral.save flush: true
        // }
        def canSpecialApproval = false
        def specialApprovalAttachments = Attachments.findByOpportunityAndType(opportunity, AttachmentType.findByName('特批签呈'))
        if (specialApprovalAttachments && opportunity?.status == 'Failed' && currentRole?.teamRole?.name == 'Approval')
        {
            if (!(opportunity?.subtype))
            {
                canSpecialApproval = true
            }
            else if (opportunity?.subtype?.name == '正常审批')
            {
                canSpecialApproval = true
            }
        }
        def canQueryPrice = true

        def regionalRiskManageStage = OpportunityStage.findByCode("08")
        def regionalRiskManageFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, regionalRiskManageStage)
        if (currentFlow?.executionSequence >= regionalRiskManageFlow?.executionSequence)
        {
            canQueryPrice = false
        }
        def canCreditReportShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))
        {
            canCreditReportShow = true
        }
        else if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_HEAD_OFFICE_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_RISK_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")))
        {
            if (currentRole?.teamRole?.name == 'Approval')
            {
                canCreditReportShow = true
            }
        }
        def canAttachmentsShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_GENERAL_MANAGER")))
        {
            canAttachmentsShow = true
        }
        else if (currentRole?.teamRole?.name == 'Approval')
        {
            canAttachmentsShow = true
        }
        def canPhotosShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_CUSTOMER_SERVICE_REPRESENTATIVE")))
        {
            canPhotosShow = true
        }
        def canLoanReceiptShow = false
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ACCOUNT_MANAGER")))
        {
            canLoanReceiptShow = true
        }

        def canInterestEdit = true
        def loanCompletionStage = OpportunityStage.findByCode("08")
        def loanCompletionFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, loanCompletionStage)
        if ((opportunity?.parent && opportunity?.type?.code == '30') || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER")))
        {
            canInterestEdit = true
        }
        else if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canInterestEdit = false
        }

        def canbillsShow = false
        if (currentFlow?.executionSequence >= loanCompletionFlow?.executionSequence)
        {
            canbillsShow = true
        }
        def users = OpportunityContact.findAllByOpportunity(opportunity?.parent)
        def borrowers = ""
        users.each {
            borrowers += it.contact?.fullName + ","
        }
        if (users)
        {
            borrowers = StringUtils.substringBeforeLast(borrowers, ",")
        }
        def billsItems
        def bills = Bills.findByOpportunity(opportunity)
        if (opportunity?.type?.name == "抵押贷款" && bills)
        {
            billsItems = BillsItem.findAllByBills(bills,[sort:"period"])
        }
        else if (opportunity?.type?.name != "抵押贷款")
        {
            bills = Bills.findByOpportunity(opportunity?.parent)
            if (bills)
            {
                billsItems = BillsItem.findAllByBills(bills,[sort:"period"])
            }
        }
        def opportunityComments = OpportunityComments.findAllByOpportunityAndAction(opportunity, null)
        def opportunityWorkflows = []
        def territory = opportunity?.territory
        if (!territory)
        {
            territory = TerritoryAccount.findByAccount(opportunity?.account)?.territory
        }
        def territoryOpportunityWorkflows = TerritoryOpportunityWorkflow.findAllByTerritory(territory)
        territoryOpportunityWorkflows?.each {
            opportunityWorkflows.add(it?.workflow)
        }
        //layout
        def opportunityLayout
        if (!opportunityLayout)
        {
            opportunityFlows?.each {
                if (it?.stage == opportunity?.stage)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityRoles?.each {
                if (it?.stage == opportunity?.stage && it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }

            opportunityTeams?.each {
                if (it?.user == user && it?.opportunityLayout)
                {
                    opportunityLayout = it?.opportunityLayout?.name
                }
            }
        }

        respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                                     //                creditReport: creditReport,
                                     opportunityContacts: opportunityContacts, borrowers: borrowers,notarizationsList:notarizationsList,
                                     activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, transactionRecords: transactionRecords, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                                     opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, opportunityWorkflows: opportunityWorkflows, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: 'show28'

    }

    def show29(Opportunity opportunity)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        def opportunityRole = OpportunityRole.findByOpportunityAndUserAndStage(opportunity, user, opportunity?.stage)
        if (opportunity?.status == "Pending")
        {
            // if (opportunityRole && opportunityRole?.teamRole?.name != "Read")
            // {
            def history
            if (opportunity && opportunity?.serialNumber)
            {
                history = OpportunityHistory.findAll("from OpportunityHistory as o where o.serialNumber = '${opportunity.serialNumber}' order by modifiedDate desc")
            }
            def code = opportunity?.stage?.code
            def opportunityProduct = OpportunityProduct.findAllByOpportunityAndProduct(opportunity, opportunity?.productAccount)
            def opportunityTeams = OpportunityTeam.findAllByOpportunity(opportunity)
            def opportunityRoles = OpportunityRole.findAllByOpportunity(opportunity)
            def opportunityNotifications = OpportunityNotification.findAllByOpportunity(opportunity)
            def opportunityFlows = OpportunityFlow.findAll("from OpportunityFlow where opportunity.id = ${opportunity?.id} order by executionSequence ASC")
            def opportunityStage = OpportunityStage.findByCode("38")
            def opportunityLoanFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunityStage)
            //                def creditReport = CreditReport.findAllByOpportunity(opportunity)
            def opportunityContacts = OpportunityContact.findAll("from OpportunityContact where opportunity.id = ${opportunity.id} order by type.id")
            //        def liquidityRiskReport = LiquidityRiskReport.findAll("from LiquidityRiskReport where opportunity.id = ${opportunity?.id} order by createdDate ASC")
            def activities = Activity.findAllByOpportunity(opportunity)
            def currentFlow = OpportunityFlow.findByOpportunityAndStage(opportunity, opportunity?.stage)
            def currentProgress = OpportunityFlow.countByOpportunityAndExecutionSequenceLessThanEquals(opportunity, currentFlow?.executionSequence) * 100
            def totalProgress = OpportunityFlow.countByOpportunity(opportunity)
            def opportunityFlexFieldCategorys = OpportunityFlexFieldCategory.findAllByOpportunity(opportunity)
            def progressPercent
            def bankAccounts = OpportunityBankAccount.findAll("from OpportunityBankAccount where opportunity.id = ${opportunity?.id} order by type desc")

            def CollateralAuditTrails = CollateralAuditTrail.findAllByOpportunity(opportunity)
            def collaterals = Collateral.findAll("from Collateral where opportunity.id = ${opportunity?.id} order by id asc")

            def currentRole = OpportunityRole.findByUserAndOpportunityAndStage(user, opportunity, opportunity?.stage)
            def subUsers = []
            def reportings
            if (currentRole?.teamRole?.name == 'Approval')
            {
                reportings = Reporting.findAllByManager(user)
                reportings?.each {
                    subUsers.add(it?.user)
                }
            }

            def canQueryPrice = false
            def canAttachmentsShow = true
            def canCreditReportShow = true
            def canInterestEdit = false

            respond opportunity, model: [history: history, opportunityTeams: opportunityTeams, opportunityRoles: opportunityRoles, opportunityNotifications: opportunityNotifications, opportunityFlows: opportunityFlows,
                                         //                    creditReport: creditReport,
                                         opportunityContacts: opportunityContacts,
                                         activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
                                         collaterals: collaterals, opportunityLoanFlow: opportunityLoanFlow, canQueryPrice: canQueryPrice, canAttachmentsShow: canAttachmentsShow, canCreditReportShow: canCreditReportShow, canInterestEdit: canInterestEdit]

        }
        else
        {
            flash.message = message(code: 'opportunity.edit.permission.denied')
            redirect(controller: "opportunity", action: "show", params: [id: opportunity.id])
            return
        }

    }

    @Secured(['permitAll'])
    @Transactional
    def ajaxRongShuApproval()
    {
        def username = springSecurityService.getPrincipal().username
        def map = [:]
        def user = User.findByUsername(username)
        if(!(UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")))){
            map.put("flag","errors")
            map.put("memo","对不起，您没有操作权限")
        }

        def opportunnityId = params.opportunityId
        def status = params.rongShuStatus
        try {
            def opportunity = Opportunity.findById(opportunnityId)
            def loanFile = Attachments.findAllByTypeAndOpportunity(AttachmentType.findByName("融数审批通知书"),opportunity)
            if(loanFile.size()==0){
                map.put("flag","errors")
                map.put("memo","请上传审核通知书")
                render map as JSON
                return
            }
            if(status=="请选择"){
                map.put("flag","errors")
                map.put("memo","请选择审批状态")
                render map as JSON
                return
            }
            opportunity.rongShuStatus = status
            opportunity.rongShuApprovalDate = new Date()
            opportunity.save()

            //todo 发送火凤凰融数审批状态和附件
            def result = rongShuToHuofh(opportunity)
            if(result && result.code == "100")
            {
                map.put("flag","success")
                map.put("memo","提交成功")
                render  map as JSON
                return
            }

            map.put("flag","errors")
            map.put("memo","发送火凤凰失败，请联系管理员")
            render  map as JSON
            return
        }catch (Exception e){
            e.printStackTrace()
        }

    }

    def rongShuToHuofh(Opportunity opportunity)
    {
        def json = [:]
        def loanFile = Attachments.findAllByTypeAndOpportunity(AttachmentType.findByName("融数审批通知书"),opportunity)
        def list = []
        loanFile.each {
            def map = [:]
            map.put("name","融数审批通知书")
            map.put("value",it.fileName == null ? it.fileUrl : it.fileName)
            list.add(map)
        }
        json.put("files",list)
        def status
        switch (opportunity.rongShuStatus)  {
            case  "未审核" : status = "0" ;break ;
            case  "通过" : status = "1" ;break ;
            case  "未通过" : status = "2" ;break ;
        }

        json.put("check_status",status)
        json.put("check_date",opportunity.rongShuApprovalDate.format("yyyy-MM-dd HH:mm:ss"))
        json.put("appno",opportunity.serialNumber)

        String sjson = groovy.json.JsonOutput.toJson(json).toString()
        println  json
        URL url = new java.net.URL(CreditReportProvider.findByCode("HUOFH").apiUrl+"/m/huofhservice/interface?reqCode=rongCheck")
        def result
        try {
            def connection = (java.net.HttpURLConnection) url.openConnection()
            connection.setDoOutput(true)
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.outputStream.withWriter("UTF-8") { java.io.Writer writer -> writer.write sjson }
            connection.setConnectTimeout(10000)
            result = grails.converters.JSON.parse(connection.inputStream.withReader("UTF-8") { java.io.Reader reader -> reader.text })
            println("融数发送火凤凰返回结果：" + result)
            return result
        }
        catch (java.lang.Exception e) {
            e.printStackTrace()
            println e
        }




    }
}
