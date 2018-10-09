package com.next

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMINISTRATOR'])
class BillsController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def billsService
    def dataSource

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Bills.list(params), model: [billsCount: Bills.count()]
    }

    def show(Bills bills)
    {
        def billsItems = BillsItem.findAll("from BillsItem where bills.id = ${bills.id}) order by period asc")
        respond bills, model: [billsItems: billsItems]
    }

    def create()
    {
        respond new Bills(params)
    }

    @Transactional
    def save(Bills bills)
    {
        if (bills == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bills.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond bills.errors, view: 'create'
            return
        }

        bills.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bills.label', default: 'Bills'), bills.id])
                redirect bills
            }
            '*' { respond bills, [status: CREATED] }
        }
    }

    def edit(Bills bills)
    {
        respond bills
    }

    @Transactional
    def update(Bills bills)
    {
        if (bills == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bills.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond bills.errors, view: 'edit'
            return
        }

        bills.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bills.label', default: 'Bills'), bills.id])
                redirect bills
            }
            '*' { respond bills, [status: OK] }
        }
    }

    @Transactional
    def delete(Bills bills)
    {

        if (bills == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bills.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bills.label', default: 'Bills'), bills.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bills.label',
                                                                                          default: 'Bills'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    //修改还款计划到期时间
    def editEndDate(){
        def opportunity
        def startDate
        def endDate
        def monthMaxDays
        java.util.Calendar calendar1 = java.util.Calendar.getInstance()
        def bills = Bills.findAll()
        bills.each {
            opportunity = it?.opportunity
            if (opportunity&&opportunity?.actualLendingDate&&opportunity?.actualLoanDuration){
                //还款结束时间
                if (!it?.startTime){
                    it.startTime = opportunity.actualLendingDate
                }
                print "billsId"+it.id+"开始时间"+it.startTime?.format("yyyy-MM-dd")+"旧结束时间"+it.endTime?.format("yyyy-MM-dd")
                calendar1.setTime(opportunity?.actualLendingDate)
                startDate = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
                calendar1.add(java.util.Calendar.MONTH, opportunity?.actualLoanDuration)
                monthMaxDays = calendar1.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
                endDate = calendar1.get(java.util.Calendar.DAY_OF_MONTH)
                if (endDate< startDate) {
                }else{
                    calendar1.add(java.util.Calendar.DAY_OF_MONTH,-1)
                }
                if (!it?.startTime){

                }
                it.endTime = calendar1.getTime()
                it.save()
                println "行结束时间"+it.endTime.format("yyyy-MM-dd")
            }
        }
        render "修改完成"
    }

    //为历史订单批量新增event
    @Transactional
    def addNewComponentToOldOrders(){
        def db = new groovy.sql.Sql(dataSource)
        def component = Component.findByName("外贸预审")?.id
        if (component){
        def flowIds = db.rows("SELECT off1.id from opportunity_flow off1 LEFT JOIN opportunity o ON off1.opportunity_id = o.id LEFT join opportunity_stage os ON off1.stage_id = os.id WHERE os.name = '审批已完成' AND o.status = 'Pending' AND off1.end_time IS NULL ")
            flowIds?.each {
                if (db.rows("SELECT id FROM opportunity_flow_condition WHERE component_id = ? AND flow_id = ?",[component,it[0]]).size()==0){
                    db.executeInsert("INSERT INTO opportunity_flow_condition VALUES(0,?,NULL,0,?,NULL,'外贸预审未通过',NULL)",[component,it[0]])
                }
            }
        }
    }
}
