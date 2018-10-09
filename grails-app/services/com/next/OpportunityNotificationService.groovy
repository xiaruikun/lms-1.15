package com.next

import grails.transaction.Transactional

import java.text.DecimalFormat

@Transactional
class OpportunityNotificationService
{

    MessageService messageService

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-4-21
     */
    def initNotification(Opportunity opportunity)
    {
        Boolean inheritNotification = true
        def territoryAccount
        def territory
        def territoryNotifications
        OpportunityNotification opportunityNotification
        def stage
        def message

        territoryAccount = TerritoryAccount.find("from TerritoryAccount where account.id = ?", [opportunity?.account?.id])
        territory = territoryAccount?.territory
        while (territory)
        {
            inheritNotification = territory?.inheritNotification
            if (!inheritNotification)
            {
                territoryNotifications = TerritoryNotification.findAll("from TerritoryNotification where territory.id" + " = ?", [territory?.id])
                for (
                    notification in
                        territoryNotifications)
                {
                    opportunityNotification = OpportunityNotification.find("from OpportunityNotification where " + "opportunity.id = ? and user.id = ? and " + "stage.id = ?", [opportunity?.id,
                        notification?.user?.id,
                        notification?.stage?.id])
                    if (!opportunityNotification)
                    {
                        opportunityNotification = new OpportunityNotification()
                        opportunityNotification.opportunity = opportunity
                        opportunityNotification.user = notification?.user
                        opportunityNotification.stage = notification?.stage
                        opportunityNotification.messageTemplate = notification?.messageTemplate
                        opportunityNotification.toManager = notification?.toManager
                        if (opportunityNotification.validate())
                        {
                            opportunityNotification.save flush: true
                        }
                        else
                        {
                            log.info opportunityNotification.errors
                        }
                    }
                }
                break
            }
            else
            {
                territory = territory?.parent
            }
        }
    }

    /**
     * @Author 班旭娟
     * @ModifiedDate 2017-5-18
     */
    def sendNotification(Opportunity opportunity)
    {
        def message
        def cellphone
        def reportings = []

        def shell = new GroovyShell()
        def closure

        if (opportunity?.status == "Failed")
        {
            message = "【中佳信】您的订单（${opportunity?.serialNumber}）(借款人：${opportunity?.fullName})已失败，失败原因为：${opportunity?.causeOfFailure?.name}。"
            if (opportunity?.contact?.cellphone)
            {
                cellphone = opportunity?.contact?.cellphone
                messageService.sendMessage2(cellphone, message)
            }
            if (opportunity?.user?.cellphone)
            {
                cellphone = opportunity?.user?.cellphone
                messageService.sendMessage2(cellphone, message)
            }
        }
        else
        {
            def opportunityNotifications = OpportunityNotification.findAll("from OpportunityNotification where " + "opportunity.id = ? and stage.id = ?",
                                                                           [opportunity?.id, opportunity?.stage?.id])
            opportunityNotifications.each {
                try
                {
                    //消息模板处理
                    if (it?.user)
                    {
                        cellphone = it?.user?.cellphone
                    }
                    else if (it?.cellphone)
                    {
                        closure = shell.evaluate(it?.cellphone)
                        cellphone = closure(opportunity)
                    }
                    else
                    {
                        log.error "opportunityNotification cellphone configuration error!"
                    }

                    if (it?.messageTemplate?.template)
                    {
                        closure = shell.evaluate(it?.messageTemplate?.template)
                        message = closure(opportunity)
                    }
                    else
                    {
                        message = it.messageTemplate?.text
                    }

                    messageService.sendMessage2(cellphone, message)

                    if (it?.toManager && it?.user)
                    {
                        reportings = Reporting.findAll("from Reporting where user.id = ?", [it?.user])
                        reportings?.each {
                            cellphone = it?.manager?.cellphone
                            messageService.sendMessage2(cellphone, message)
                        }
                    }
                }
                catch (Exception e)
                {
                    log.error "opportunityNotification script execute error: " + e
                }
            }

        }

    }

    def sendAssignNotification(Activity activity)
    {
        def opportunity = activity?.opportunity
        def message
        def allAddress = ""
        opportunity?.collaterals?.each {
            allAddress += it?.address + ","
        }
        message = "【中佳信】订单（${opportunity?.serialNumber}）(借款人：${opportunity?.fullName})的下户任务已分配至您处，下户地址为：${allAddress}请及时处理。"
        if (activity?.assignedTo?.cellphone)
        {
            messageService.sendMessage2(activity?.assignedTo?.cellphone, message)
        }
        else
        {
            log.info "下户人手机号缺失"
        }
    }

    def sendMessageToUser(Collateral collateral)
    {
        def df = new DecimalFormat("0.0")
        def totalPrice = df.format(collateral?.totalPrice)
        if (collateral?.status == "Completed")
        {
            messageService.sendMessage2(collateral?.opportunity?.contact?.cellphone, "【中佳信】您的订单（${collateral?.opportunity?.serialNumber}）评房结果出来了。评房结果：${totalPrice}万元。详情请登录中佳信端app评估记录中查看")
        }
        else if (collateral?.status == "Pending")
        {
            messageService.sendMessage2(collateral?.opportunity?.contact?.cellphone, "【中佳信】您的订单（${collateral?.opportunity?.serialNumber}）评房结果出来了。参考评房结果：${totalPrice}万元。房产评估结果需人工核实，我们正在火速处理。详情请登录中佳信端app评估记录中查看")
        }
        else if (collateral?.status == "Failed")
        {
            messageService.sendMessage2(collateral?.opportunity?.contact?.cellphone, "【中佳信】您的订单（${collateral?.opportunity?.serialNumber}）评房结果出来了。评房结果：待确认。房产评估结果需人工核实，我们正在火速处理。详情请登录中佳信端app评估记录中查看")
        }

    }
}
