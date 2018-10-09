package com.next

class BootStrap
{
    def init = { servletContext ->

        if (Role.count() < 1)
        {
            println 'init role data'

            def r = new Role()
            r.authority = 'ROLE_ADMINISTRATOR'
            r.description = '系统管理员'
            r.save()

            r = new Role()
            r.authority = 'ROLE_COMPANY_ADMINISTRATOR'
            r.description = '机构管理员'
            r.save()

            r = new Role()
            r.authority = 'ROLE_ACCOUNT_MANAGER'
            r.description = '客户经理'
            r.save()

            r = new Role()
            r.authority = 'ROLE_AGENT'
            r.description = '经纪人'
            r.save()

            r = new Role()
            r.authority = 'ROLE_CUSTOMER'
            r.description = '客户'
            r.save()

            r = new Role()
            r.authority = 'ROLE_PRODUCT_ADMINISTRATOR'
            r.description = '产品管理员'
            r.save()

            r = new Role()
            r.authority = 'ROLE_GENERAL_RISK_MANAGER'
            r.description = '风控总经理'
            r.save()

            r = new Role()
            r.authority = 'ROLE_BRANCH_OFFICE_RISK_MANAGER'
            r.description = '分公司风控经理'
            r.save()

            r = new Role()
            r.authority = 'ROLE_HEAD_OFFICE_RISK_MANAGER'
            r.description = '总部风控经理'
            r.save()

            r = new Role()
            r.authority = 'ROLE_GENERAL_MANAGER'
            r.description = '业务总经理'
            r.save()

            r = new Role()
            r.authority = 'ROLE_CRO'
            r.description = 'CRO'
            r.save()

            r = new Role()
            r.authority = 'ROLE_CEO'
            r.description = 'CEO'
            r.save()
        }

        if (!Role.findByAuthority("ROLE_COO"))
        {
            def r = new Role()
            r.authority = 'ROLE_COO'
            r.description = 'COO'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_HR_MANAGER'))
        {
            def r = new Role()
            r.authority = 'ROLE_HR_MANAGER'
            r.description = '人员资源经理'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_POST_LOAN_MANAGER'))
        {
            def r = new Role()
            r.authority = 'ROLE_POST_LOAN_MANAGER'
            r.description = '贷后经理'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_COMPLIANCE_MANAGER'))
        {
            def r = new Role()
            r.authority = 'ROLE_COMPLIANCE_MANAGER'
            r.description = '合规则经理'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_BRANCH_GENERAL_MANAGER'))
        {
            def r = new Role()
            r.authority = 'ROLE_BRANCH_GENERAL_MANAGER'
            r.description = '区域总经理'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'))
        {
            def r = new Role()
            r.authority = 'ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER'
            r.description = '区域贷后经理'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_CUSTOMER_SERVICE_REPRESENTATIVE'))
        {
            def r = new Role()
            r.authority = 'ROLE_CUSTOMER_SERVICE_REPRESENTATIVE'
            r.description = '客服专员'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_BRANCH_OFFICE_CASHIER'))
        {
            def r = new Role()
            r.authority = 'ROLE_BRANCH_OFFICE_CASHIER'
            r.description = '区域出纳'
            r.save()
        }

        if (!Role.findByAuthority('SALES_REPRESENTATIVE'))
        {
            def r = new Role()
            r.authority = 'SALES_REPRESENTATIVE'
            r.description = '销售'
            r.save()
        }

        if (!Role.findByAuthority('ROLE_INVESTOR'))
        {
            def r = new Role()
            r.authority = 'ROLE_INVESTOR'
            r.description = '投资人'
            r.save()
        }

        if (AccountType.count() < 1)
        {
            def type = new AccountType(name: "Partner")
            type.save()

            type = new AccountType(name: "Broker Office")
            type.save()

            type = new AccountType(name: "Department")
            type.save()
        }

        if (Account.count() < 1)
        {
            def c = new Account(name: "北京中佳信科技发展有限公司", code: "cdae403a-c468-49ee-b68d-b40b4807e55b", type: AccountType.findByName("Partner"), externalId: 'zjx-1')
            c.save()

            c = new Account(name: "21世纪不动产中国总部", code: "cdae403a-c468-49ee-b68d-b40b4807e55a", type: AccountType
                .findByName("Partner"))
            c.save()

            c = new Account(name: "中佳信-北京分公司", code: "cdae403a-c468-49ee-b68d-b40b4807e55c", type: AccountType
                .findByName("Partner"), parent: Account.findByName("北京中佳信科技发展有限公司"))
            c.save()

            c = new Account(name: "21北京分公司", code: "cdae403a-c468-49ee-b68d-b40b4807e55d", type: AccountType
                .findByName("Partner"), parent: Account.findByName("21世纪不动产中国总部"))
            c.save()

            c = new Account(name: "21北京分店测试门店", code: "cdae403a-c468-49ee-b68d-b40b4807e533", type: AccountType
                .findByName("Partner"), externalId: "21cn-413", parent: Account.findByName("21北京分公司"))
            c.save()
        }

        if (Position.count() < 1)
        {
            def position = new Position(name: "销售经理")
            position.save()

            position = new Position(name: "销售主管")
            position.save()

            position = new Position(name: "销售员")
            position.save()

            position = new Position(name: "销售支持主管")
            position.save()

            position = new Position(name: "销售支持专员")
            position.save()

            position = new Position(name: "风控经理")
            position.save()

            position = new Position(name: "风控主单")
            position.save()

            position = new Position(name: "风控初审")
            position.save()

            position = new Position(name: "外访专员")
            position.save()

            position = new Position(name: "风控助理")
            position.save()

            position = new Position(name: "权证经理")
            position.save()

            position = new Position(name: "高级权证专员")
            position.save()

            position = new Position(name: "权证专员")
            position.save()

            position = new Position(name: "调派员")
            position.save()

            position = new Position(name: "资料取送专员")
            position.save()

            position = new Position(name: "总经理")
            position.save()

            position = new Position(name: "财务经理")
            position.save()

            position = new Position(name: "财务主管")
            position.save()

            position = new Position(name: "行政经理")
            position.save()

            position = new Position(name: "下户专员")
            position.save()

            position = new Position(name: "风控总经理")
            position.save()

            position = new Position(name: "分公司风控经理")
            position.save()

            position = new Position(name: "总部风控经理")
            position.save()

            position = new Position(name: "业务总经理")
            position.save()

            position = new Position(name: "CRO")
            position.save()

            position = new Position(name: "CEO")
            position.save()

        }

        if (User.count() < 1)
        {
            println 'init contact data'

            def account = Account.findByName("北京中佳信科技发展有限公司")
            def c = new User()
            c.username = 'administrator'
            c.password = 'C7ISJ4e&i1nVPXUz'
            c.fullName = "尹何栋"
            c.cellphone = "18513603065"
            c.account = account
            c.save()

            account = Account.findByName("中佳信-北京分公司")
            c = new User()
            c.username = 'lyky'
            c.password = '1ykDLQUeAqkF$vRw'
            c.fullName = "张成远"
            c.cellphone = "18811432134"
            c.account = account
            c.position = Position.findByName("销售员")
            c.save()
        }

        if (UserRole.count() < 1)
        {
            println 'init user role data'

            UserRole.create(User.findByUsername('administrator'), Role.findByAuthority('ROLE_ADMINISTRATOR'))
            UserRole.create(User.findByUsername('lyky'), Role.findByAuthority('ROLE_ADMINISTRATOR'))
        }

        if (Territory.count() < 1)
        {
            def t = new Territory(name: "默认区域")
            t.inheritFlow = false
            t.inheritNotification = false
            t.inheritTeam = false
            t.inheritRole = false
            t.save()

            t = new Territory(name: "中佳信北京测试区")
            t.inheritFlow = true
            t.inheritNotification = true
            t.inheritTeam = true
            t.inheritRole = true
            t.parent = Territory.findByName("默认区域")
            t.save()

            t = new Territory(name: "21世纪区域")
            t.inheritFlow = false
            t.inheritNotification = false
            t.inheritTeam = false
            t.inheritRole = false
            t.save()

            t = new Territory(name: "21世纪北京测试区")
            t.inheritFlow = true
            t.inheritNotification = true
            t.inheritTeam = true
            t.inheritRole = true
            t.parent = Territory.findByName("21世纪区域")
            t.save()
        }

        if (TerritoryAccount.count() < 1)
        {

            def ta = new TerritoryAccount()
            ta.account = Account.findByName("中佳信-北京分公司")
            ta.territory = Territory.findByName("中佳信北京测试区")
            ta.startTime = new Date()
            ta.endTime = new Date()
            ta.save()

            ta = new TerritoryAccount()
            ta.account = Account.findByName("21北京分店测试门店")
            ta.territory = Territory.findByName("21世纪北京测试区")
            ta.startTime = new Date()
            ta.endTime = new Date()
            ta.save()

            //            ta = new TerritoryAccount()
            //            ta.account = Account.findByName("21世纪不动产中国总部")
            //            ta.territory = Territory.findByName("默认区域")
            //            ta.startTime = new Date()
            //            ta.endTime = new Date()
            //            ta.save()
        }

        if (Department.count() < 1)
        {
            def d = new Department()
            d.name = "支持组"
            d.save()

            d = new Department()
            d.name = "风控组"
            d.save()

            d = new Department()
            d.name = "权证组"
            d.save()

            d = new Department()
            d.name = "销售组"
            d.save()

            d = new Department()
            d.name = "财务组"
            d.save()

            d = new Department()
            d.name = "下户组"
            d.save()
        }

        //        if (LiquidityRiskReportTemplate.count() < 1)
        //        {
        //            def template = new LiquidityRiskReportTemplate(name: "北京")
        //            template.save()
        //
        //            def item = new LiquidityRiskReportTemplateItem(name: "是否被占用", weight: 1)
        //            item.template = template
        //            item.save()
        //            def option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //
        //            item = new LiquidityRiskReportTemplateItem(name: "是否有查封", weight: 1)
        //            item.template = template
        //            item.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //
        //            item = new LiquidityRiskReportTemplateItem(name: "是否有抵押", weight: 1)
        //            item.template = template
        //            item.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //
        //            item = new LiquidityRiskReportTemplateItem(name: "地址是否有变更", weight: 1)
        //            item.template = template
        //            item.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //
        //            item = new LiquidityRiskReportTemplateItem(name: "建委系统是否有此栋楼信息", weight: 1)
        //            item.template = template
        //            item.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //
        //            item = new LiquidityRiskReportTemplateItem(name: "抵押人身份证号是否需要升位", weight: 1)
        //            item.template = template
        //            item.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //
        //            item = new LiquidityRiskReportTemplateItem(name: "是否成本价房", weight: 1)
        //            item.template = template
        //            item.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "是", score: 1)
        //            option.item = item
        //            option.save()
        //            option = new LiquidityRiskReportTemplateItemOptions(name: "否", score: 0)
        //            option.item = item
        //            option.save()
        //        }

        if (City.count() < 1)
        {
            def d = new City()
            d.name = "北京"
            d.telephone = "4008882006"
            d.save()

            d = new City()
            d.name = "上海"
            d.telephone = "4006507761"
            d.save()

            d = new City()
            d.name = "成都"
            d.telephone = "4006507762"
            d.save()

            d = new City()
            d.name = "青岛"
            d.telephone = "053280938787"
            d.save()

            d = new City()
            d.name = "济南"
            d.telephone = "4006507764"
            d.save()

            d = new City()
            d.name = "郑州"
            d.telephone = "4006507765"
            d.save()

            d = new City()
            d.name = "南京"
            d.telephone = "4006507766"
            d.save()

            d = new City()
            d.name = "西安"
            d.telephone = "4006507767"
            d.save()

            d = new City()
            d.name = "合肥"
            d.telephone = "4006507769"
            d.save()

            d = new City()
            d.name = "武汉"
            d.telephone = "4006507768"
            d.save()
        }

        if (AccountCity.count() < 1)
        {
            def ac = new AccountCity(city: City.findByName("北京"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("上海"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("成都"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("青岛"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("济南"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("郑州"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("南京"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("西安"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("合肥"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("武汉"), account: Account.findByName("北京中佳信科技发展有限公司"))
            ac.save()
            ac = new AccountCity(city: City.findByName("北京"), account: Account.findByName("21世纪不动产中国总部"))
            ac.save()
            ac = new AccountCity(city: City.findByName("上海"), account: Account.findByName("21世纪不动产中国总部"))
            ac.save()
        }

        if (OpportunityStage.count() < 1)
        {
            def type = OpportunityType.findByName('抵押贷款')

            def stage = new OpportunityStage()
            stage.code = "01"
            stage.name = "评房申请已提交"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "02"
            stage.name = "评房已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "03"
            stage.name = "报单申请已提交"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "04"
            stage.name = "基础材料已提供"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "05"
            stage.name = "产调已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "06"
            stage.name = "征信查询已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "07"
            stage.name = "房产初审已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "08"
            stage.name = "审批已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "09"
            stage.name = "抵押公证手续已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "10"
            stage.name = "放款已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "11"
            stage.name = "返点已完成"
            stage.type = type
            stage.save()

            // stage = new OpportunityStage()
            // stage.code = "12"
            // stage.name = "预约申请已提交"
            // stage.save()

            // stage = new OpportunityStage()
            // stage.code = "13"
            // stage.name = "预约已指派"
            // stage.save()

            // stage = new OpportunityStage()
            // stage.code = "14"
            // stage.name = "下户已完成"
            // stage.save()

            stage = new OpportunityStage()
            stage.code = "15"
            stage.name = "价格待确认"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "16"
            stage.name = "信息已完善"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "17"
            stage.name = "复审已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "18"
            stage.name = "已提交京北方（贷款）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "19"
            stage.name = "合同已签署"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "20"
            stage.name = "抵押品已入库"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "21"
            stage.name = "资金渠道已选择"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "22"
            stage.name = "已提交京北方（放款）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "23"
            stage.name = "放款审批已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "24"
            stage.name = "展期申请已提交"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "25"
            stage.name = "展期审批已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "26"
            stage.name = "面谈已完成"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "27"
            stage.name = "客户征信已上传"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "28"
            stage.name = "贷款审批（总部风控经理）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "29"
            stage.name = "贷款审批（风控总经理）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "30"
            stage.name = "贷款审批（业务总经理）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "31"
            stage.name = "贷款审批（CRO）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "32"
            stage.name = "贷款审批（CEO）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "33"
            stage.name = "放款审批（总部风控经理）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "34"
            stage.name = "放款审批（风控总经理）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "35"
            stage.name = "放款审批（业务总经理）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "36"
            stage.name = "放款审批（CRO）"
            stage.type = type
            stage.save()

            stage = new OpportunityStage()
            stage.code = "37"
            stage.name = "放款审批（CEO）"
            stage.type = type
            stage.save()
        }
        else
        {
            def list = OpportunityStage.findAll()
            def type = OpportunityType.findByName('抵押贷款')

            list.each {
                if (!it.type)
                {
                    it.type = type
                    it.save()
                }
            }
        }


        if (OpportunityType.count() < 1)
        {
            def type = new OpportunityType(code: '10', name: '抵押贷款')
            type.save()

            type = new OpportunityType(code: '20', name: '抵押贷款展期')
            type.save()
        }

        if (OpportunitySubtype.count() < 1)
        {
            def type = OpportunityType.findByName('抵押贷款')

            def subType = new OpportunitySubtype(name: '正常审批')
            subType.type = type
            subType.save()

            subType = new OpportunitySubtype(name: '特殊审批')
            subType.type = type
            subType.save()
        }

        if (ContactLevel.count() < 1)
        {
            def level = new ContactLevel()
            level.name = "A"
            level.description = "优质"
            level.save()

            level = new ContactLevel()
            level.name = "B"
            level.description = "在京二套房产证明；理财协议；股票市值截屏；股票市值截屏；流水、网银截屏；上下游合同；应收账款；行驶证；外地房产；大额存单，包括但不限于以上资料。面审时请至少携带其中一件。"
            level.save()

            level = new ContactLevel()
            level.name = "C"
            level.description = "疑难客户"
            level.save()

            level = new ContactLevel()
            level.name = "D"
            level.description = "不良客户"
            level.save()
        }

        if (CauseOfFailure.count() < 1)
        {
            def cf = new CauseOfFailure()
            cf.name = "评估值不够"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "二抵剩余残值不够"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "考虑中"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "风控降成"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "我司不受理"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "超过公司单笔贷款金额"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "息费高"
            cf.save()
            cf = new CauseOfFailure()
            cf.name = "贷款手续麻烦"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "资金问题已解决（非同行）"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "在同行已成交"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "房本被占用"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "只办理0.6%的业务"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "客户需求不明确"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "客户要求市值的七成"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "正在补资料"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "家人不同意"
            cf.save()

            cf = new CauseOfFailure()
            cf.name = "客户资质不够"
            cf.save()
        }

        if (!CauseOfFailure.findByName("超时"))
        {
            def cf = new CauseOfFailure()
            cf.name = "超时"
            cf.save()
        }

        if (TeamRole.count() < 1)
        {
            def tr = new TeamRole(name: "Read")
            tr.save()

            tr = new TeamRole(name: "Edit")
            tr.save()

            tr = new TeamRole(name: "Administrator")
            tr.save()

            tr = new TeamRole(name: "Approval")
            tr.save()
        }

        if (OpportunityContactType.count() < 1)
        {
            def type = new OpportunityContactType(name: "借款人")
            type.save()

            type = new OpportunityContactType(name: "借款人配偶")
            type.save()

            type = new OpportunityContactType(name: "抵押人")
            type.save()

            type = new OpportunityContactType(name: "抵押人配偶")
            type.save()

            type = new OpportunityContactType(name: "其它借款人")
            type.save()

            type = new OpportunityContactType(name: "借款人父母")
            type.save()

            type = new OpportunityContactType(name: "借款人子女")
            type.save()
        }

        if (InterestPaymentMethod.count() < 1)
        {
            def ipm = new InterestPaymentMethod(name: "扣息差")
            ipm.save()

            ipm = new InterestPaymentMethod(name: "上扣息")
            ipm.save()

            ipm = new InterestPaymentMethod(name: "一次性付息")
            ipm.save()

            ipm = new InterestPaymentMethod(name: "等本等息")
            ipm.save()
        }

        if (CommissionPaymentMethod.count() < 1)
        {
            def cycle = new CommissionPaymentMethod(name: "月月返")
            cycle.save()

            cycle = new CommissionPaymentMethod(name: "一次性返")
            cycle.save()
        }

        if (AttachmentType.count() < 1)
        {
            def at = new AttachmentType(name: "房产证")
            at.save()

            at = new AttachmentType(name: "征信授权")
            at.save()

            at = new AttachmentType(name: "借款人身份证")
            at.save()

            at = new AttachmentType(name: "借款人配偶身份证")
            at.save()

            at = new AttachmentType(name: "抵押人身份证")
            at.save()

            at = new AttachmentType(name: "抵押人配偶身份证")
            at.save()

            at = new AttachmentType(name: "购房合同")
            at.save()

            at = new AttachmentType(name: "出租：抵押物租赁合同、租客告知书")
            at.save()

            at = new AttachmentType(name: "二抵：抵押物银行按揭贷款合同")
            at.save()

            at = new AttachmentType(name: "特殊房产需提供资料")
            at.save()

            at = new AttachmentType(name: "小区外围配套物业")
            at.save()

            at = new AttachmentType(name: "出入口道路")
            at.save()

            at = new AttachmentType(name: "小区名称及物业地址")
            at.save()

            at = new AttachmentType(name: "小区楼栋号、单元号、门牌号")
            at.save()

            //小区规模（由卧室或阳台拍摄小区俯瞰图）
            at = new AttachmentType(name: "小区规模")
            at.save()

            at = new AttachmentType(name: "抵押物各房间情况")
            at.save()

            at = new AttachmentType(name: "家访人员出镜照片")
            at.save()

            at = new AttachmentType(name: "客户出镜的照片")
            at.save()

            at = new AttachmentType(name: "户口本（集体户口需提供额外资料）")
            at.save()

            at = new AttachmentType(name: "特殊资料")
            at.save()

            at = new AttachmentType(name: "附加资料")
            at.save()

            at = new AttachmentType(name: "产调结果")
            at.save()

            at = new AttachmentType(name: "征信报告")
            at.save()

            at = new AttachmentType(name: "征信查询授权书")
            at.save()

            at = new AttachmentType(name: "返点证明")
            at.save()

            at = new AttachmentType(name: "放款证明")
            at.save()

            at = new AttachmentType(name: "他项证明")
            at.save()

            at = new AttachmentType(name: "公证书")
            at.save()

            at = new AttachmentType(name: "签约合同")
            at.save()

            at = new AttachmentType(name: "估值附回材料")
            at.save()

            // at = new AttachmentType(name: "面谈笔录")
            // at.save()

            at = new AttachmentType(name: "被执情况")
            at.save()

            at = new AttachmentType(name: "户口本")
            at.save()

            at = new AttachmentType(name: "婚姻证明")
            at.save()

            at = new AttachmentType(name: "大数据")
            at.save()

            at = new AttachmentType(name: "风险调查报告")
            at.save()

            at = new AttachmentType(name: "抵押公证受理单")
            at.save()

        }
        else
        {
            def at = AttachmentType.findByName("强执公证")
            if (!at)
            {
                at = new AttachmentType(name: "强执公证")
                at.save()
            }
            at = AttachmentType.findByName("售房公证")
            if (!at)
            {
                at = new AttachmentType(name: "售房公证")
                at.save()
            }
            at = AttachmentType.findByName("特批签呈")
            if (!at)
            {
                at = new AttachmentType(name: "特批签呈")
                at.save()
            }
            at = AttachmentType.findByName("普通签呈")
            if (!at)
            {
                at = new AttachmentType(name: "普通签呈")
                at.save()
            }
        }

        if (AddressType.count() < 1)
        {
            def type = new AddressType(name: "建委")
            type.save()

            type = new AddressType(name: "公证处")
            type.save()
        }

        if (AssetType.count() < 1)
        {
            def at = new AssetType(name: "住宅")
            at.save()

            at = new AssetType(name: "商品房")
            at.save()

            at = new AssetType(name: "央产房")
            at.save()

            at = new AssetType(name: "经济适用房")
            at.save()

            at = new AssetType(name: "按经济适用房管理")
            at.save()

            at = new AssetType(name: "优惠价房改房")
            at.save()

            at = new AssetType(name: "标准价房改房")
            at.save()

            at = new AssetType(name: "回迁房")
            at.save()

            at = new AssetType(name: "军产房")
            at.save()

            at = new AssetType(name: "校产房")
            at.save()

            at = new AssetType(name: "其他")
            at.save()

            at = new AssetType(name: "成本价房改房")
            at.save()

            at = new AssetType(name: "商业办公")
            at.save()

            at = new AssetType(name: "房改房")
            at.save()

            at = new AssetType(name: "未知")
            at.save()

            at = new AssetType(name: "公寓")
            at.save()

            at = new AssetType(name: "别墅")
            at.save()

            at = new AssetType(name: "限价房")
            at.save()
        }

        if (TypeOfFirstMortgage.count() < 1)
        {
            def t = new TypeOfFirstMortgage(name: "银行类")
            t.save()

            t = new TypeOfFirstMortgage(name: "非银行类")
            t.save()
        }

        if (HouseType.count() < 1)
        {
            def t = new HouseType(name: "普通住宅")
            t.save()
            t = new HouseType(name: "独栋")
            t.save()
            t = new HouseType(name: "联排")
            t.save()
            t = new HouseType(name: "双拼")
            t.save()
            t = new HouseType(name: "叠拼")
            t.save()
            t = new HouseType(name: "独栋别墅")
            t.save()
            t = new HouseType(name: "联排别墅")
            t.save()
            t = new HouseType(name: "双拼别墅")
            t.save()
            t = new HouseType(name: "叠拼别墅")
            t.save()
            t = new HouseType(name: "商业")
            t.save()
            t = new HouseType(name: "办公")
            t.save()
            t = new HouseType(name: "公寓")
            t.save()
            t = new HouseType(name: "未知")
            t.save()
            t = new HouseType(name: "其他")
            t.save()

        }

        if (SpecialFactors.count() < 1)
        {
            def sf = new SpecialFactors(name: "无")
            sf.save()

            sf = new SpecialFactors(name: "复式")
            sf.save()

            sf = new SpecialFactors(name: "LOFT")
            sf.save()

            sf = new SpecialFactors(name: "跃层")
            sf.save()

            sf = new SpecialFactors(name: "一层赠送")
            sf.save()

            sf = new SpecialFactors(name: "临湖")
            sf.save()

            sf = new SpecialFactors(name: "楼王")
            sf.save()

            sf = new SpecialFactors(name: "临街")
            sf.save()
        }

        if (MortgageType.count() < 1)
        {
            def t = new MortgageType(name: "一抵")
            t.save()

            t = new MortgageType(name: "二抵")
            t.save()

            t = new MortgageType(name: "一抵转单")
            t.save()

            t = new MortgageType(name: "二抵转单")
            t.save()
        }

        if (ActivityType.count() < 1)
        {
            def type = new ActivityType(name: "Appointment")
            type.save()

            type = new ActivityType(name: "Task")
            type.save()

            type = new ActivityType(name: "Call")
            type.save()
        }

        if (ActivitySubtype.count() < 1)
        {
            def subtype = new ActivitySubtype(name: "下户", type: ActivityType.findByName("Appointment"))
            subtype.save()

            subtype = new ActivitySubtype(name: "抵押", type: ActivityType.findByName("Appointment"))
            subtype.save()

            subtype = new ActivitySubtype(name: "公正", type: ActivityType.findByName("Appointment"))
            subtype.save()

            subtype = new ActivitySubtype(name: "面审", type: ActivityType.findByName("Appointment"))
            subtype.save()

            subtype = new ActivitySubtype(name: "Sign In", type: ActivityType.findByName("Call"))
            subtype.save()

            subtype = new ActivitySubtype(name: "产调", type: ActivityType.findByName("Appointment"))
            subtype.save()
        }

        if (CreditReportProvider.count() < 1)
        {
            def p = new CreditReportProvider(code: "TONGDUN", name: "同盾", apiUrl: "http://10.0.8.101/tongdun-2.0" + "/provider/evaluate2")
            p.save()

            p = new CreditReportProvider(code: "BAIRONG", name: "百融", apiUrl: "http://10.0.8.102/bairong-1.0" + ".0/provider/evaluate2")
            p.save()

            p = new CreditReportProvider(code: "HUIFA", name: "汇法", apiUrl: "http://10.0.8.136:8080/huifa-1.0" + "/provider/evaluate2")
            p.save()

            //            p = new CreditReportProvider(code: "TIANXING", name: "天行", apiUrl: "http://10.0.8.135:8080/tianxing-1.0" + "/provider/evaluate2")
            //            p.save()
        }

        //        if (!CreditReportProvider.findByCode("BLACKLIST"))
        //        {
        //            def p = new CreditReportProvider(code: "BLACKLIST", name: "中佳信黑名单", apiUrl: "http://10.0.8.137:8080/blacklist" + "/provider/evaluate2")
        //            p.save()
        //        }
        //        if (!CreditReportProvider.findByCode("PENGYUAN"))
        //        {
        //            def p = new CreditReportProvider(code: "PENGYUAN", name: "鹏元", apiUrl: "http://10.0.8.136:8080/pengyuan-1.0" + "/provider/evaluate2")
        //            p.save()
        //        }

        //        if (!CreditReportProvider.findByCode("QIANHAI"))
        //        {
        //            def p = new CreditReportProvider(code: "QIANHAI", name: "前海", apiUrl: "http://10.0.8.140:8080/qianhai-1.0.0" + "/provider/evaluate2")
        //            p.save()
        //        }

        if (MessageTemplate.count() < 1)
        {
            def message = new MessageTemplate(text: "【黄金屋】您的经纪人 contactName （contactCellphone） 有评房成功订单（serialNumber），请及时关注跟进！")
            message.save()

            message = new MessageTemplate(text: "【黄金屋】您的经纪人 contactName（contactCellphone）已提交报单，请及时提供身份证、房产证图片，敬请配合，感谢！")
            message.save()

            message = new MessageTemplate(text: "【黄金屋】您的经纪人 contactName （contactCellphone） 已提交报单，请及时关注跟进！")
            message.save()

            message = new MessageTemplate(text: "【黄金屋】订单serialNumber（stage）已分配至您处，请及时处理！")
            message.save()

            message = new MessageTemplate(text: "【黄金屋】您的订单（serialNumber）已失败，失败原因为：causeOfFailure。")
            message.save()

        }

        if (TerritoryTeam.count() < 1)
        {
            TerritoryTeam territoryTeam = new TerritoryTeam()
            territoryTeam.startTime = new Date()
            territoryTeam.endTime = new Date()
            territoryTeam.territory = Territory.findByName("默认区域")
            territoryTeam.user = User.findByUsername("lyky")
            territoryTeam.save()

            territoryTeam = new TerritoryTeam()
            territoryTeam.startTime = new Date()
            territoryTeam.endTime = new Date()
            territoryTeam.territory = Territory.findByName("21世纪区域")
            territoryTeam.user = User.findByUsername("lyky")
            territoryTeam.save()
        }

        if (TerritoryRole.count() < 1)
        {
            def opportunityStage = OpportunityStage.findAll()
            opportunityStage.each {
                TerritoryRole territoryRole = new TerritoryRole()
                territoryRole.territory = Territory.findByName("默认区域")
                territoryRole.user = User.findByUsername("lyky")
                territoryRole.stage = it
                territoryRole.teamRole = TeamRole.findByName("Approval")
                territoryRole.save()

                territoryRole = new TerritoryRole()
                territoryRole.territory = Territory.findByName("21世纪区域")
                territoryRole.user = User.findByUsername("lyky")
                territoryRole.stage = it
                territoryRole.teamRole = TeamRole.findByName("Approval")
                territoryRole.save()
            }
        }

        if (TerritoryFlow.count() < 1)
        {
            def territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 1
            territoryFlow.stage = OpportunityStage.findByName("评房申请已提交")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 2
            territoryFlow.stage = OpportunityStage.findByName("评房已完成")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 3
            territoryFlow.stage = OpportunityStage.findByName("报单申请已提交")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 4
            territoryFlow.stage = OpportunityStage.findByName("基础材料已提供")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 5
            territoryFlow.stage = OpportunityStage.findByName("信息已完善")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 6
            territoryFlow.stage = OpportunityStage.findByName("房产初审已完成")
            territoryFlow.save()

            // territoryFlow = new TerritoryFlow()
            // territoryFlow.territory = Territory.findByName("默认区域")
            // territoryFlow.executionSequence = 6
            // territoryFlow.stage = OpportunityStage.findByName("预约申请已提交")
            // territoryFlow.save()

            // territoryFlow = new TerritoryFlow()
            // territoryFlow.territory = Territory.findByName("默认区域")
            // territoryFlow.executionSequence = 7
            // territoryFlow.stage = OpportunityStage.findByName("预约已指派")
            // territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 8
            territoryFlow.stage = OpportunityStage.findByName("产调已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 9
            territoryFlow.stage = OpportunityStage.findByName("征信查询已完成")
            territoryFlow.save()

            // territoryFlow = new TerritoryFlow()
            // territoryFlow.territory = Territory.findByName("默认区域")
            // territoryFlow.executionSequence = 10
            // territoryFlow.stage = OpportunityStage.findByName("下户已完成")
            // territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 11
            territoryFlow.stage = OpportunityStage.findByName("审批已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 12
            territoryFlow.stage = OpportunityStage.findByName("抵押公证手续已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 13
            territoryFlow.stage = OpportunityStage.findByName("放款已完成")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("默认区域")
            territoryFlow.executionSequence = 14
            territoryFlow.stage = OpportunityStage.findByName("返点已完成")
            territoryFlow.save()

            // **********************************

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 1
            territoryFlow.stage = OpportunityStage.findByName("评房申请已提交")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 2
            territoryFlow.stage = OpportunityStage.findByName("评房已完成")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 3
            territoryFlow.stage = OpportunityStage.findByName("报单申请已提交")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 4
            territoryFlow.stage = OpportunityStage.findByName("基础材料已提供")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 5
            territoryFlow.stage = OpportunityStage.findByName("产调已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 6
            territoryFlow.stage = OpportunityStage.findByName("房产初审已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 7
            territoryFlow.stage = OpportunityStage.findByName("征信查询已完成")
            territoryFlow.save()

            //            territoryFlow = new TerritoryFlow()
            //            territoryFlow.territory = Territory.findByName("默认区域")
            //            territoryFlow.executionSequence = 8
            //            territoryFlow.stage = OpportunityStage.findByName("已预约")
            //            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 9
            territoryFlow.stage = OpportunityStage.findByName("审批已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 10
            territoryFlow.stage = OpportunityStage.findByName("抵押公证手续已完成")
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 11
            territoryFlow.stage = OpportunityStage.findByName("放款已完成")
            territoryFlow.canReject = false
            territoryFlow.save()

            territoryFlow = new TerritoryFlow()
            territoryFlow.territory = Territory.findByName("21世纪区域")
            territoryFlow.executionSequence = 12
            territoryFlow.stage = OpportunityStage.findByName("返点已完成")
            territoryFlow.save()
        }


        if (PropertyValuationProvider.count() < 1)
        {
            def provider = new PropertyValuationProvider(code: "FGG", name: "房估估", apiUrl: "http://10.0.8.104/fgg-1.0" + ".0/provider/evaluate")
            provider.save()

            provider = new PropertyValuationProvider(code: "VISS", name: "中估联", apiUrl: "http://10.0.8.103/viss-1.0" + ".0/provider/evaluate")
            provider.save()
        }

        //        if (CreditReportConstraintType.count() < 1)
        //        {
        //            def type = new CreditReportConstraintType(name: 'Blacklist')
        //            type.save()
        //
        //            type = new CreditReportConstraintType(name: 'Suspicious')
        //            type.save()
        //        }

        //        if (CreditReportConstraint.count() < 1)
        //        {
        //            def crc = new CreditReportConstraint(name: '同盾黑名单-拒单')
        //            crc.save()
        //
        //            def item = new CreditReportConstraintItem(name: '身份证命中失联名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中失联名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中犯罪通缉名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中法院执行名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中法院失信名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证_姓名命中法院执行模糊名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中高风险关注名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中高风险关注名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中贷款黑中介库')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中车辆租赁违约名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中车辆租赁违约名单')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            crc = new CreditReportConstraint(name: '同盾黑名单-退回')
        //            crc.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号格式校验错误')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号疑似乱真')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证号格式校验错误')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中虚假号码库')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中通信小号库')
        //            item.creditReportConstraint = crc
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证不是二代身份证')
        //            item.creditReportConstraint = crc
        //            item.save()
        //        }

        //        if (CreditReportConstraint.count() < 3)
        //        {
        //            def crc = new CreditReportConstraint(name: '同盾评估-中')
        //            crc.save()
        //
        //            def item = new CreditReportConstraintItem(name: '身份证命中欠税名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中公司欠税名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中欠款公司法人名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中信贷逾期后还款名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中信贷逾期后还款名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '邮箱命中信贷逾期后还款名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: 'QQ命中信贷逾期后还款名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中欠款公司法人名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中失信还款名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中诈骗骚扰库', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '座机号命中诈骗骚扰库', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证命中信贷逾期名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '手机号命中信贷逾期名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '邮箱命中信贷逾期名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '座机号命中信贷逾期名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: 'QQ命中信贷逾期名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '地址信息命中信贷逾期名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '涉及中、低风险关注名单的', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '涉及法院执行、失信类的（含模糊）（已结案）（金额>5万元）', creditReportConstraint: crc)
        //            item.save()
        //
        //            crc = new CreditReportConstraint(name: '同盾评估-低')
        //            crc.save()
        //
        //            item = new CreditReportConstraintItem(name: '3个月内申请信息关联多个身份证', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '3个月内银行卡-姓名关联多个身份证', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证--姓名命中信贷逾期模糊名单', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '3个月内身份证关联多个申请信息', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '身份证号对应人存在助学贷款逾期历史', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '设备使用过多的身份证进行申请', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '设备使用过多的手机号进行申请', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '多头借贷申请承诺与检测结果匹配不一致', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '涉及多头借贷类的（含7天，1个月，3个月）', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '涉及法院执行、失信类的（含模糊）（已结案）（金额≤5万元）', creditReportConstraint: crc)
        //            item.save()
        //
        //            crc = new CreditReportConstraint(name: '百融黑名单-拒单')
        //            crc.save()
        //
        //            item = new CreditReportConstraintItem(name: '法院执行人（未结案）', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '法院失信人（未结案）', creditReportConstraint: crc)
        //            item.save()
        //
        //            crc = new CreditReportConstraint(name: '百融评估-中')
        //            crc.save()
        //
        //            item = new CreditReportConstraintItem(name: '法院执行人（已结案）（金额>5万元）', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '法院失信人（已结案）（金额>5万元）', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '银行不良或欺诈', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '直系亲属银行不良或欺诈', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '小贷/P2P/非银不良', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '小贷/P2P/非银短时逾期或拒绝', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '在非银行机构多次申请-高度', creditReportConstraint: crc)
        //            item.save()
        //
        //            crc = new CreditReportConstraint(name: '百融评估-低')
        //            crc.save()
        //
        //            item = new CreditReportConstraintItem(name: '法院执行人（已结案）（金额≤5万元）', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '法院失信人（已结案）（金额≤5万元）', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '直系亲属小贷/P2P/非银短时逾期或拒绝', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '直系亲属小贷/P2P/非银不良', creditReportConstraint: crc)
        //            item.save()
        //
        //            item = new CreditReportConstraintItem(name: '银行短期逾期或拒绝', creditReportConstraint: crc)
        //            item.save()
        //        }

        if (CityPropertyValuationProvider.count() < 1)
        {
            def cp = new CityPropertyValuationProvider()
            cp.provider = PropertyValuationProvider.findByCode("FGG")
            cp.city = City.findByName("北京")
            cp.save()

            cp = new CityPropertyValuationProvider()
            cp.provider = PropertyValuationProvider.findByCode("FGG")
            cp.city = City.findByName("西安")
            cp.save()

            cp = new CityPropertyValuationProvider()
            cp.provider = PropertyValuationProvider.findByCode("VISS")
            cp.city = City.findByName("武汉")
            cp.save()

            cp = new CityPropertyValuationProvider()
            cp.provider = PropertyValuationProvider.findByCode("VISS")
            cp.city = City.findByName("上海")
            cp.save()

            cp = new CityPropertyValuationProvider()
            cp.provider = PropertyValuationProvider.findByCode("VISS")
            cp.city = City.findByName("苏州")
            cp.save()

            cp = new CityPropertyValuationProvider()
            cp.provider = PropertyValuationProvider.findByCode("VISS")
            cp.city = City.findByName("南京")
            cp.save()
        }

        if (Collateral.count() < 1)
        {
            def collateral = new Collateral()
            collateral.assetType = "住宅"
            collateral.projectName = "花园"
            collateral.city = City.findByName("北京")
            collateral.district = "花园"
            collateral.address = "花园"
            collateral.floor = "1"
            collateral.orientation = "南北"
            collateral.totalFloor = "2"
            collateral.roomNumber = "102"
            collateral.houseType = "普通住宅"
            collateral.specialFactors = "复式"
            collateral.externalId = 0
            collateral.status = "Pending"
            collateral.opportunity = Opportunity.findById(1)
            collateral.save()
        }

        if (District.count() < 1)
        {
            def district = new District()
            district.name = "朝阳区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "东城区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "西城区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "丰台区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "石景山区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "海淀区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "大兴区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "通州区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "昌平区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "房山区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "顺义区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "怀柔区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "密云县"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "平谷区"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "延庆县"
            district.city = City.findByName("北京")
            district.save()

            district = new District()
            district.name = "徐汇区"
            district.city = City.findByName("上海")
            district.save()
        }

        if (AppVersion.count() < 1)
        {
            def appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0', description: '第一个版本')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0.1', description: '1、极速评房 2、立即保单 3、个人中心 4、修复了少数情况下用户无法登录的情况')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0.2', description: '应用最低支持ios 8.2')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0.3', description: '1、支持在线上传身份证及房产证照片 2、部分流程体验优化，使用更方便')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0.4', description: '恢复上传图片模糊')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0.5', description: '优化界面效果 提高交互体验 修复已知bug')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-ios', appVersion: '1.0.6', description: '添加直接报单功能 优化交互效果')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-android', appVersion: '1.1.6', description: '1、支持在线上传身份证及房产证照片。 2、部分流程体验优化，让您使用更方便。')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-touch-android', appVersion: '1.0', description: '第一个版本')
            appVersion.save()

            appVersion = new AppVersion(appName: 'hjwoo-touch-ios', appVersion: '1.0', description: '第一个版本')
            appVersion.save()
        }

        if (ProductInterestType.count() < 1)
        {
            def type = new ProductInterestType(name: '基本费率',isUsed: true)
            type.save()

            type = new ProductInterestType(name: '服务费费率',isUsed: true)
            type.save()

            type = new ProductInterestType(name: '渠道返费费率',isUsed: true)
            type.save()

            type = new ProductInterestType(name: '二抵加收费率',isUsed: true)
            type.save()

            type = new ProductInterestType(name: '信用调整',isUsed: true)
            type.save()
        }

        if (BillsItemType.count() < 1)
        {
            def type = new BillsItemType(name: '基本费率')
            type.save()

            type = new BillsItemType(name: '服务费费率')
            type.save()

            type = new BillsItemType(name: '渠道返费费率')
            type.save()

            type = new BillsItemType(name: '二抵加收费率')
            type.save()

            type = new BillsItemType(name: '信用调整')
            type.save()

            type = new BillsItemType(name: '本金')
            type.save()

            type = new BillsItemType(name: '郊县')
            type.save()

            type = new BillsItemType(name: '大头小尾')
            type.save()

            type = new BillsItemType(name: '老人房（65周岁以上）')
            type.save()

            type = new BillsItemType(name: '意向金')
            type.save()
        }

        if (PrincipalPaymentMethod.count() < 1)
        {
            def ppm = new PrincipalPaymentMethod(name: "到期还本")
            ppm.save()

            ppm = new PrincipalPaymentMethod(name: "月息年本")
            ppm.save()

            ppm = new PrincipalPaymentMethod(name: "等本等息")
            ppm.save()

            ppm = new PrincipalPaymentMethod(name: "气球贷（60期）")
            ppm.save()

            ppm = new PrincipalPaymentMethod(name: "气球贷（120期）")
            ppm.save()
        }

        if (!PrincipalPaymentMethod.findByName("等额本息"))
        {
            def ppm = new PrincipalPaymentMethod(name: "等额本息")
            ppm.save()
        }

        ProductService productService = new ProductService()
        productService.initProduct

        if (AppConfigurationKey.count() < 1)
        {
            def ak = new AppConfigurationKey(name: '北京邀请码')
            ak.save()

            ak = new AppConfigurationKey(name: '合肥邀请码')
            ak.save()
        }

        if (LoanApplicationProcessType.count() < 1)
        {
            def type = new LoanApplicationProcessType(name: '直接报单')
            type.save()

            type = new LoanApplicationProcessType(name: '先评房再报单')
            type.save()
        }
        //        初始化flexFieldCategory
        FlexFieldCategoryService flexFieldCategoryService = new FlexFieldCategoryService()
        flexFieldCategoryService.initFlexFieldCategory

        if (MortgageCertificateType.count() < 1)
        {
            def type = new MortgageCertificateType(name: '抵押受理单')
            type.save()

            type = new MortgageCertificateType(name: '他项证明')
            type.save()
        }

        if (OpportunityBankAccountType.count() < 1)
        {
            def type = new OpportunityBankAccountType(name: '收款账号')
            type.save()

            type = new OpportunityBankAccountType(name: '还款账号')
            type.save()
        }

        if (OpportunityLayout.count() < 1)
        {
            def opportunityLayout = new OpportunityLayout(name: 'opportunityLayout01', description: '审批页')
            opportunityLayout.save()

            opportunityLayout = new OpportunityLayout(name: 'opportunityLayout02', description: '主单页')
            opportunityLayout.save()

            opportunityLayout = new OpportunityLayout(name: 'opportunityLayout03', description: '审批详情页')
            opportunityLayout.save()
        }

        if (CollateralProjectType.count() < 1)
        {
            def type = new CollateralProjectType(name: '住宅')
            type.save()

            type = new CollateralProjectType(name: '别墅')
            type.save()

            type = new CollateralProjectType(name: '商业')
            type.save()

            type = new CollateralProjectType(name: '商住两用')
            type.save()

            type = new CollateralProjectType(name: '其他')
            type.save()
        }

        if (ContactProfession.count() < 1)
        {
            def profession = new ContactProfession(name: '公检法从业人员')
            profession.save()

            profession = new ContactProfession(name: '现役军人')
            profession.save()

            profession = new ContactProfession(name: '金融同业的法人/股东/隐形股东')
            profession.save()

            profession = new ContactProfession(name: '娱乐(KTV)的法人/股东/隐形股东')
            profession.save()

            profession = new ContactProfession(name: '钢贸/煤炭(上下游)/有色金属的法人/股东/隐形股东')
            profession.save()

            profession = new ContactProfession(name: '房地产开发/建筑公司(承接房地产开发项目的工程总包方)的法人/股东/隐形股东')
            profession.save()

            profession = new ContactProfession(name: '其他职业')
            profession.save()
        }

        if (Country.count() < 1)
        {
            def country = new Country(name: '中国')
            country.save()

            country = new Country(name: '台湾')
            country.save()

            country = new Country(name: '香港')
            country.save()

            country = new Country(name: '澳门')
            country.save()

            country = new Country(name: '美国')
            country.save()
        }

        if (ContactIdentityType.count() < 1)
        {
            def type = new ContactIdentityType(name: '身份证')
            type.save()

            type = new ContactIdentityType(name: '临时身份证')
            type.save()

            type = new ContactIdentityType(name: '护照')
            type.save()
        }

        if (BillsStatus.count() < 1)
        {
            // status inList: ['Estimating', 'Repaying', 'Cleared', 'Overdue', 'Extended', 'Prepaid']

            def status = new BillsStatus(name: '测算')
            status.save()

            status = new BillsStatus(name: '还款中')
            status.save()

            status = new BillsStatus(name: '已结清')
            status.save()

            status = new BillsStatus(name: '逾期未处理')
            status.save()

            status = new BillsStatus(name: '已展期')
            status.save()

            status = new BillsStatus(name: '提前结清')
            status.save()
        }

        if (BillsItemType.count() < 1)
        {
            def type = new BillsItemType(name: '本金')
            type.save()
        }

        if (OpportunityAction.count() < 1)
        {
            def action = new OpportunityAction(name: '同意')
            action.save()

            action = new OpportunityAction(name: '不同意')
            action.save()

            action = new OpportunityAction(name: '特批')
            action.save()

            action = new OpportunityAction(name: '拒单')
            action.save()
        }

        if (TransactionType.count() < 1)
        {
            def type = new TransactionType(name: '放款')
            type.save()

            type = new TransactionType(name: '罚息')
            type.save()

            type = new TransactionType(name: '还本')
            type.save()

            type = new TransactionType(name: '还息')
            type.save()
        }

        if (TransactionRecordStatus.count() < 1)
        {
            def status = new TransactionRecordStatus(name: '未执行')
            status.save()

            status = new TransactionRecordStatus(name: '已成功')
            status.save()

            status = new TransactionRecordStatus(name: '已失败')
            status.save()
        }

        if (CollateralRegion.count() < 1)
        {
            def region = new CollateralRegion(name: '二环内')
            region.save()

            region = new CollateralRegion(name: '二环到三环之间')
            region.save()

            region = new CollateralRegion(name: '三环到四环之间')
            region.save()

            region = new CollateralRegion(name: '四环到五环之间')
            region.save()

            region = new CollateralRegion(name: '五环到六环之间')
            region.save()

            region = new CollateralRegion(name: '六环外')
            region.save()
        }

        if (ExternalDataProvider.count() < 1)
        {
            def provider = new ExternalDataProvider(name: '百融')
            provider.save()

            provider = new ExternalDataProvider(name: '鹏元个人')
            provider.save()

            provider = new ExternalDataProvider(name: '汇法')
            provider.save()

            provider = new ExternalDataProvider(name: '同盾')
            provider.save()

            provider = new ExternalDataProvider(name: '汇法企业')
            provider.save()
        }

        if (!Role.find("from Role where description = '规则引擎'"))
        {
            def r = new Role()
            r.authority = 'ROLE_CONDITION_RULEENGINE'
            r.description = '规则引擎'
            r.save()
        }

        if (ContractType.count() < 1)
        {
            def contractType = new ContractType(name: '借款合同')
            contractType.save()

            contractType = new ContractType(name: '抵押合同')
            contractType.save()

            contractType = new ContractType(name: '委托借款代理服务合同')
            contractType.save()

            contractType = new ContractType(name: '委托保证合同')
            contractType.save()
        }

        if (ContractTemplate.count() < 1)
        {
            def contractType = ContractType.findByName('借款合同')

            def contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-企业经营流动资金'
            contractTemplate.save()
            def contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-新项目启动资金'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-偿还欠款'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-家庭生活消费'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-留学/移民'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-周转应急'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-赎楼'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-买房'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-买车'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()

            contractTemplate = new ContractTemplate(type: contractType)
            contractTemplate.name = '借款用途-其他'
            contractTemplate.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '否'
            contractTemplateOptions.displayOrder = 0
            contractTemplateOptions.save()
            contractTemplateOptions = new ContractTemplateOptions(template: contractTemplate)
            contractTemplateOptions.value = '是'
            contractTemplateOptions.displayOrder = 1
            contractTemplateOptions.save()
        }

        if (ComponentType.count() < 1)
        {
            def type = new ComponentType(name: 'Condition')
            type.save()

            type = new ComponentType(name: 'Event')
            type.save()
        }

        if (ExternalDatasetType.count() < 1)
        {
            def t = new ExternalDatasetType(name: '央行征信')
            t.save()

            t = new ExternalDatasetType(name: '百融特殊名单核查')
            t.save()

            t = new ExternalDatasetType(name: '百融个人不良信息查询高级版')
            t.save()

            t = new ExternalDatasetType(name: '百融身份证核查')
            t.save()
        }

        if (WorkflowInstanceStatus.count() < 1)
        {
            def w = new WorkflowInstanceStatus(name: 'Pending')
            w.save()

            w = new WorkflowInstanceStatus(name: 'Completed')
            w.save()

            w = new WorkflowInstanceStatus(name: 'Failed')
            w.save()
        }

        if (WorkflowAuthority.count() < 1)
        {
            def a = new WorkflowAuthority(name: 'View')
            a.save()

            a = new WorkflowAuthority(name: 'Edit')
            a.save()

            a = new WorkflowAuthority(name: 'Approval')
            a.save()
        }

        if (RepaymentMethod.count() < 1)
        {
            def m = new RepaymentMethod(name: '广银联代扣')
            m.save()

            m = new RepaymentMethod(name: '富友代扣')
            m.save()

            m = new RepaymentMethod(name: '自主还款')
            m.save()
        }
        if(ManagerAccountNumber.count()<1)
        {
            def m = new ManagerAccountNumber(name: '外贸555')
            m.save()
            m = new ManagerAccountNumber(name: '中航669')
            m.save()
        }
    }

    def destroy = {}
}
