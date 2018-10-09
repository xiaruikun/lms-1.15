package com.next

import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
@Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])

class AssetPoolOpportunityController
{

    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond AssetPoolOpportunity.list(params), model: [assetPoolOpportunityCount: AssetPoolOpportunity.count()]
    }

    def show(AssetPoolOpportunity assetPoolOpportunity)
    {
        respond assetPoolOpportunity
    }

    def create()
    {
        respond new AssetPoolOpportunity(params)
    }

    @Transactional
    def save(AssetPoolOpportunity assetPoolOpportunity)
    {
        if (assetPoolOpportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetPoolOpportunity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetPoolOpportunity.errors, view: 'create'
            return
        }

        assetPoolOpportunity.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'assetPoolOpportunity.label', default: 'AssetPoolOpportunity'), assetPoolOpportunity.id])
                // redirect assetPoolOpportunity
                redirect controller: 'assetPool', action: 'show', id: assetPoolOpportunity.assetPool.id

            }
            '*' { respond assetPoolOpportunity, [status: CREATED] }
        }
    }

    def edit(AssetPoolOpportunity assetPoolOpportunity)
    {
        respond assetPoolOpportunity
    }

    @Transactional
    def update(AssetPoolOpportunity assetPoolOpportunity)
    {
        if (assetPoolOpportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (assetPoolOpportunity.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond assetPoolOpportunity.errors, view: 'edit'
            return
        }

        assetPoolOpportunity.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'assetPoolOpportunity.label', default: 'AssetPoolOpportunity'), assetPoolOpportunity.id])
                // redirect assetPoolOpportunity
                redirect controller: 'assetPool', action: 'show', id: assetPoolOpportunity.assetPool.id

            }
            '*' { respond assetPoolOpportunity, [status: OK] }
        }
    }

    @Transactional
    def delete(AssetPoolOpportunity assetPoolOpportunity)
    {

        if (assetPoolOpportunity == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        assetPoolOpportunity.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'assetPoolOpportunity.label', default: 'AssetPoolOpportunity'), assetPoolOpportunity.id])
                // redirect action:"index", method:"GET"
                redirect controller: 'assetPool', action: 'show', id: assetPoolOpportunity.assetPool.id

            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'assetPoolOpportunity.label', default: 'AssetPoolOpportunity'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])
    @Transactional
    def assetPoolShow(Opportunity opportunity)
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
        def transactionRecords
        if (!opportunity?.type || opportunity?.type?.code == "10")
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.id} order by createdDate asc")
        }
        else
        {
            transactionRecords = TransactionRecord.findAll("from TransactionRecord where opportunity.id = ${opportunity?.parent?.id} order by createdDate asc")
        }


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
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COMPLIANCE_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_GENERAL_MANAGER")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_COO")))
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
        def loanCompletionStage = OpportunityStage.findByCode("10")
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
            billsItems = BillsItem.findAllByBills(bills)
        }
        else if (opportunity?.type?.name != "抵押贷款")
        {
            bills = Bills.findByOpportunity(opportunity?.parent)
            if (bills)
            {
                billsItems = BillsItem.findAllByBills(bills)
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
            opportunityContacts: opportunityContacts, transactionRecords: transactionRecords, borrowers: borrowers,
            activities: activities, progressPercent: progressPercent, opportunityProduct: opportunityProduct, currentFlow: currentFlow, opportunityFlexFieldCategorys: opportunityFlexFieldCategorys, bankAccounts: bankAccounts, subUsers: subUsers, CollateralAuditTrails: CollateralAuditTrails,
            opportunityLoanFlow: opportunityLoanFlow, canSpecialApproval: canSpecialApproval, user: user, collaterals: collaterals, billsItems: billsItems, canQueryPrice: canQueryPrice, opportunityComments: opportunityComments, canCreditReportShow: canCreditReportShow, canAttachmentsShow: canAttachmentsShow, canPhotosShow: canPhotosShow, canLoanReceiptShow: canLoanReceiptShow, canInterestEdit: canInterestEdit, canbillsShow: canbillsShow], view: 'assetPoolOpportunity'
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])
    def assetPoolIndex(Integer max)
    {

        params.max = 10
        params.offset = params.offset ? params.offset.toInteger() : 0
        max = 10
        def offset = params.offset
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def assetPoolId = params["assetPool"]
        def assetPool = AssetPool.get(assetPoolId)

        def sql = "from AssetPoolOpportunity where 1=1"
        //        sql += " and opportunity.id in (select opportunity.id from OpportunityFlow where stage.id = '08' and startTime is not null)"
        def list = []
        def count

        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_INVESTOR")))
        {
            sql += " and  assetPool.id = ${assetPool?.id} order by opportunity.modifiedDate desc"
            list = AssetPoolOpportunity.findAll(sql, [max: max, offset: offset])
            count = AssetPoolOpportunity.findAll(sql).size()
        }


        respond list, model: [assetPoolOpportunityCount: count, assetPool: assetPool, params: params]
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])
    def searchAssetPoolOpportunity()
    {
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        params.offset = params.offset ? params.offset.toInteger() : 0;

        def max = params.max
        def offset = params.offset

        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)

        def assetPoolId = params["assetPool"]
        def assetPool = AssetPool.get(assetPoolId)

        String sql = "from AssetPoolOpportunity as a where 1=1 and  assetPool.id = ${assetPool?.id}"
        //        sql += " and a.opportunity.id in (select opportunity.id from OpportunityFlow where stage.id = '08' and startTime is not null)"
        def serialNumber = params["serialNumber"]
        if (serialNumber)
        {
            sql += " and a.opportunity.serialNumber like '%${serialNumber}%'"
        }

        def fullName = params["fullName"]
        if (fullName)
        {
            sql += " and a.opportunity.fullName like '%${fullName}%'"
        }

        def city = params["city"]
        if (city && city != "null")
        {
            sql += " and a.opportunity.contact.city.name = '${city}'"
        }

        def contractSerialNumber = params["contractSerialNumber"]
        if (contractSerialNumber && contractSerialNumber != "null")
        {
            sql += " and a.opportunity.id IN (select opportunity.id from OpportunityContract where contract.id in (select id from Contract where serialNumber like '%${contractSerialNumber}%'))"
        }

        def flexField = params["flexField"]
        if (flexField && flexField != "null")
        {
            sql += " and a.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款通道' and f.value= '${flexField}'))"
        }

        def flexFieldBankAccount = params["flexFieldBankAccount"]
        if (flexFieldBankAccount && flexFieldBankAccount != "null")
        {
            sql += " and a.opportunity.id IN (SELECT opportunity.id FROM OpportunityFlexFieldCategory as p WHERE p.id IN (select category.id from OpportunityFlexField as f where f.name = '放款账号' and f.value= '${flexFieldBankAccount}'))"
        }

        def list = []
        def count
        println "-----------------------------"
        println sql
        if (UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_ADMINISTRATOR")) || UserRole.findByUserAndRole(user, Role.findByAuthority("ROLE_INVESTOR")))
        {
            sql += " order by a.opportunity.modifiedDate desc"
            list = AssetPoolOpportunity.findAll(sql, [max: max, offset: offset])
            def list1 = AssetPoolOpportunity.findAll(sql)
            count = list1.size()
        }

        respond list, model: [assetPoolOpportunityCount: count, assetPool: assetPool, params: params], view: 'assetPoolIndex'
    }

    @Secured(['ROLE_ADMINISTRATOR', 'ROLE_INVESTOR'])
    def upload()
    {
        println "----------------" + params["id"]
        //投资方数据csv导入
        def f = request.getFile('csv')
        def assetPool = AssetPool.get(params["id"])
        def opportunityCsvFile = OpportunityCsvFile.findByFilename(f.originalFilename)
        if (!f.empty && opportunityCsvFile == null)
        {
            def webRootDir = servletContext.getRealPath("/")
            println webRootDir
            def userDir = new File(webRootDir, "/assetPoolCsv/")
            userDir.mkdirs()
            def file = new File(userDir, f.originalFilename)
            f.transferTo(file)
            opportunityCsvFile = new OpportunityCsvFile()
            opportunityCsvFile.filename = f.originalFilename
            opportunityCsvFile.filepath = webRootDir + "assetPoolCsv/" + f.originalFilename

            //根据文件创建billsImport对象
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"))
            br.readLine()
            String line = null
            FileOutputStream fs = new FileOutputStream(new File(webRootDir + "assetPoolCsv/" + opportunityCsvFile.filename + ".log"));
            PrintStream p = new PrintStream(fs);
            def st = 0
            def inquote = false
            def count = 0
            StringBuffer readLine = new StringBuffer()
            try
            {
                while ((line = br.readLine()) != null)
                {
                    readLine.append(line)
                    // 考虑单元格中有逗号的情况
                    def maxPosition = readLine.length()
                    while (st < maxPosition)
                    {
                        def ch = readLine.charAt(st)
                        if (inquote && ch == ',')
                        {
                            readLine.setCharAt(st, (char) '，')
                        }
                        if (ch == '"')
                        {
                            inquote = !inquote
                        }
                        st++
                    }

                    line = readLine.toString()
                    // 如果双引号是奇数的时候继续读取，考虑单元格中有换行的情况
                    def splitByDoubleQuotes = line.split('"')
                    def countDoubleQuotes = 0
                    if (line.charAt(line.length() - 1) == '"')
                    {
                        countDoubleQuotes = splitByDoubleQuotes.length
                    }
                    else
                    {
                        countDoubleQuotes = splitByDoubleQuotes.length - 1
                    }
                    if (countDoubleQuotes % 2 == 1)
                    {
                        continue
                    }
                    else
                    {
                        readLine.setLength(0)
                        st = 0
                    }

                    def list = line.split(",")
                    count++
                    println list.size() + "------------------" + list[0]
                    if (list)
                    {
                        if (!AssetPoolOpportunity.findByAssetPoolAndOpportunitySerialNumber(assetPool, list[0]))
                        {
                            def assetPoolOpportunity = new AssetPoolOpportunity()
                            assetPoolOpportunity.assetPoolName = assetPool?.name
                            assetPoolOpportunity.opportunitySerialNumber = list[0]
                            if (assetPoolOpportunity.validate())
                            {
                                assetPoolOpportunity.save flush: true
                            }
                            else
                            {
                                println assetPoolOpportunity.errors
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                log.info "file read wrong"
                p.println("file read wrong")
            }
            finally
            {
                if (br != null)
                {
                    try
                    {
                        br.close()
                        br = null
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace()
                    }
                }
                log.info "file read success!!!"
                p.println("导入完成，本次导入订单总数: " + count)
                p.println("file read success!!!")
            }
            p.close()
        }
        else
        {
            flash.message = "文件不能为空，不选择,or has yet"
        }

        opportunityCsvFile.save()
        opportunityCsvFile.errors.each {
            log.info it.toString()
        }

        if (!opportunityCsvFile.hasErrors() && opportunityCsvFile.save())
        {
            flash.message = "上传成功"
        }
        else
        {
            flash.message = "上传失败"
        }
        redirect(controller: "assetPoolOpportunity", action: "assetPoolIndex", params: [assetPool: assetPool?.id])
    }
/**
 * @Description  资金池导入数据
 * @Author bigyuan
 * @Return
 * @DateTime 2017/10/31 15:31
 */
    //@Secured(['permitAll'])
    def uploadSerial()
    {
        def oppList = ['ZHXTJKHT-BJS-1391','ZHXTJKHT-BJX-0097','ZHXTJKHT-BJX-0142','ZHXTJKHT-CDS-0597','ZHXTJKHT-CDS-0654','ZHXTJKHT-HFS-0445','ZHXTJKHT-JNS-0867','ZHXTJKHT-JNS-0872','ZHXTJKHT-JNS-0876','ZHXTJKHT-JNS-0892','ZHXTJKHT-NJS-0417','ZHXTJKHT-NJS-0441','ZHXTJKHT-QDS-0540','ZHXTJKHT-QDS-0546','ZHXTJKHT-QDS-0555','ZHXTJKHT-QDS-0556','ZHXTJKHT-QDS-0557','ZHXTJKHT-QDS-0558','ZHXTJKHT-QDS-0563','ZHXTJKHT-QDS-0565','ZHXTJKHT-QDS-0574','ZHXTJKHT-SHX-0243','ZHXTJKHT-SHX-0261','ZHXTJKHT-SHX-0291','ZHXTJKHT-SHX-0338','ZHXTJKHT-SHX-0354','ZHXTJKHT-SHX-0358','ZHXTJKHT-SHX-0362','ZHXTJKHT-SHX-0372','ZHXTJKHT-SHX-0376','ZHXTJKHT-SHX-0378','ZHXTJKHT-SHX-0384','ZHXTJKHT-SJZS-0468','ZHXTJKHT-SJZS-0493','ZHXTJKHT-SJZS-0495','ZHXTJKHT-SJZS-0500','ZHXTJKHT-SJZS-0505','ZHXTJKHT-SJZS-0526','ZHXTJKHT-SJZS-0527','ZHXTJKHT-SJZS-0528','ZHXTJKHT-SJZS-0530','ZHXTJKHT-SJZS-0531','ZHXTJKHT-SJZS-0532','ZHXTJKHT-SJZS-0533','ZHXTJKHT-SJZS-0535','ZHXTJKHT-SJZS-0537','ZHXTJKHT-XAS-0583','ZHXTJKHT-XAS-0584','ZHXTJKHT-XAS-0585','ZHXTJKHT-XAS-0586','ZHXTJKHT-XAS-0607','ZHXTJKHT-XAS-0610','ZHXTJKHT-XAS-0616','ZHXTJKHT-BJX-0146','ZHXTJKHT-CDS-0679','ZHXTJKHT-JNS-0954','ZHXTJKHT-JNS-0967','ZHXTJKHT-JNS-0982','ZHXTJKHT-QDS-0601','ZHXTJKHT-SHX-0370','ZHXTJKHT-WHS-0260','ZHXTJKHT-XAS-0640']
        oppList.each {
            def opportunity = OpportunityContract.findByContract(Contract.findBySerialNumber(it))?.opportunity?.serialNumber
            if(opportunity){
                def a = new AssetPoolOpportunity()
                a.opportunitySerialNumber = opportunity
                a.assetPoolName = "中航139户购买方"
                if(a.validate()){
                    a.save()
                }else{
                    println a.errors
                }
            }else {
                println it
            }

        }
        /* def a = new AssetPoolOpportunity()
         a.opportunitySerialNumber = "SVU-4WH-HUK"
         a.assetPoolName = "XX购买方"
         a.save()*/
        render "success"
    }
}
