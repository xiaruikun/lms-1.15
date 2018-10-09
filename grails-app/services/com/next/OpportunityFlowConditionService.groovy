package com.next

import grails.transaction.Transactional

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Transactional
class OpportunityFlowConditionService
{
    ComponentService componentService

    def evaluate(Opportunity opportunity)
    {
        //{opportunity -> if (opportunity.period > 0 && opportunity.startTime != null) {return true}else {return false}}
        def shell = new GroovyShell()
        def closure
        def map = [:]
        Boolean flag = true
        def message
        def result
        def nextStage

        def opportunityFlow = OpportunityFlow.find("from OpportunityFlow where opportunity.id = ? and stage.id = ?",
                                                   [opportunity?.id, opportunity?.stage?.id])
        if (opportunityFlow)
        {
            for (
                it in
                    opportunityFlow?.conditions)
            {
                try
                {
                    if (it?.component)
                    {
                        result = componentService.evaluate it?.component, opportunity

                        it.log = result
                        it.save flush: true

                        if (result instanceof Exception)
                        {
                            message = "联系管理员：" + result
                            flag = false
                            break
                        }
                        else if (result instanceof Boolean)
                        {
                            if (!result)
                            {
                                message = it?.component?.message
                                flag = false
                                break
                            }
                        }
                        else if (result instanceof HashMap)
                        {
                            if (!result['flag'])
                            {
                                message = it?.component?.message
                                flag = false
                                break
                            }
                            else if (result['nextStage'])
                            {
                                nextStage = result['nextStage']
                            }
                        }
                        else if (result instanceof String)
                        {
                            message = result
                            flag = false
                            break
                        }
                    }
                    else
                    {
                        closure = shell.evaluate(it?.condition)
                        result = closure(opportunity)

                        it.log = result
                        it.save flush: true

                        if (result instanceof Boolean)
                        {
                            if (!result)
                            {
                                message = it?.message
                                flag = false
                                break
                            }
                        }
                        else if (result instanceof HashMap)
                        {
                            if (!result['flag'])
                            {
                                message = it?.message
                                flag = false
                                break
                            }
                            else if (result['nextStage'])
                            {
                                nextStage = result['nextStage']
                            }
                        }
                        else if (result instanceof String)
                        {
                            message = result
                            flag = false
                            break
                        }
                    }
                }
                catch (Exception e)
                {
                    it.log = e
                    it.save flush: true

                    message = "联系管理员：" + e
                    flag = false
                    break
                }
            }
        }
        else
        {
            message = "订单信息不完善，请联系管理员"
            flag = false
        }

        if (message)
        {
            map['message'] = message
        }
        if (nextStage)
        {
            map['nextStage'] = nextStage
        }
        map['flag'] = flag
        return map
    }

    // def evaluate(OpportunityFlow opportunityFlow)
    // {
    //     //{opportunity -> if (opportunity.period > 0 && opportunity.startTime != null) {return true}else {return false}}
    //     def shell = new GroovyShell()
    //     def closure
    //     def result
    //     def map = [:]
    //     map['flag'] = true
    //
    //     def opportunityFlowConditions = OpportunityFlowCondition.findAll("from OpportunityFlowCondition where flow.id = ${opportunityFlow?.id} order by executeSequence asc")
    //     for (it in opportunityFlowConditions)
    //     {
    //         try
    //         {
    //             if (it?.component)
    //             {
    //               closure = shell.evaluate(it?.component?.script)
    //             }
    //             else if (it?.condition)
    //             {
    //               closure = shell.evaluate(it?.condition)
    //             }
    //             else
    //             {
    //               map['flag'] = false
    //               map['message'] = "校验配置异常，请联系管理员"
    //               break
    //             }
    //
    //             result = closure(opportunityFlow?.opportunity)
    //
    //             if (result instanceof Boolean)
    //             {
    //               if (result)
    //               {
    //                 if (it?.nextStage)
    //                 {
    //                   map['nextStage'] = it?.nextStage
    //                   break
    //                 }
    //               }
    //               else
    //               {
    //                 if (!it?.nextStage)
    //                 {
    //                   map['flag'] = false
    //                   if (it?.component)
    //                   {
    //                     map['message'] = it?.component?.message
    //                   }
    //                   else
    //                   {
    //                     map['message'] = it?.message
    //                   }
    //                   break
    //                 }
    //               }
    //             }
    //             else if ((it?.executeSequence == 0) && (result instanceof HashMap))
    //             {
    //               if (!result['flag'])
    //               {
    //                 map['flag'] = false
    //                 if (it?.component)
    //                 {
    //                   map['message'] = it?.component?.message
    //                 }
    //                 else
    //                 {
    //                   map['message'] = it?.message
    //                 }
    //                 break
    //               }
    //               else if (result['nextStage'])
    //               {
    //                 map['nextStageCode'] == result['nextStage']
    //               }
    //             }
    //             else
    //             {
    //               map['flag'] = false
    //               map['message'] = "校验异常，请联系管理员"
    //               break
    //             }
    //         }
    //         catch (Exception e)
    //         {
    //             map['flag'] = false
    //             map['message'] = "联系管理员：" + e
    //             break
    //         }
    //     }
    //
    //     if (map['nextStageCode'])
    //     {
    //       map['nextStage'] = map['nextStageCode']
    //     }
    //     return map
    // }
}
