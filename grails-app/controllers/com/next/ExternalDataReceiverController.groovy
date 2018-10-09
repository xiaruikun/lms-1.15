package com.next

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*

/**
 * @Author 班旭娟
 * @ModifiedDate 2017-4-21
 */
@Secured(['ROLE_ADMINISTRATOR'])
@Transactional(readOnly = true)
class ExternalDataReceiverController
{

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def springSecurityService

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond ExternalDataReceiver.list(params), model: [externalDataReceiverCount: ExternalDataReceiver.count()]
    }

    def show(ExternalDataReceiver externalDataReceiver)
    {
        def externalDataReceiverMessages = ExternalDataReceiverMessage.findAllByReceiver(externalDataReceiver)
        def externalDataReceiverAutitTrails = ExternalDataReceiverAutitTrail.findAll("from ExternalDataReceiverAutitTrail where receiver = ${externalDataReceiver?.id} order by startTime desc")
        respond externalDataReceiver, model: [externalDataReceiverMessages: externalDataReceiverMessages, externalDataReceiverAutitTrails: externalDataReceiverAutitTrails]
    }

    def create()
    {
        respond new ExternalDataReceiver(params)
    }

    @Transactional
    def save(ExternalDataReceiver externalDataReceiver)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        externalDataReceiver.createdBy = user

        if (externalDataReceiver == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataReceiver.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataReceiver.errors, view: 'create'
            return
        }

        externalDataReceiver.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver'), externalDataReceiver.id])
                redirect externalDataReceiver
            }
            '*' { respond externalDataReceiver, [status: CREATED] }
        }
    }

    def edit(ExternalDataReceiver externalDataReceiver)
    {
        respond externalDataReceiver
    }

    @Transactional
    def update(ExternalDataReceiver externalDataReceiver)
    {
        def username = springSecurityService.getPrincipal().username
        def user = User.findByUsername(username)
        externalDataReceiver.modifiedBy = user

        if (externalDataReceiver == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (externalDataReceiver.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond externalDataReceiver.errors, view: 'edit'
            return
        }

        externalDataReceiver.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver'), externalDataReceiver.id])
                redirect externalDataReceiver
            }
            '*' { respond externalDataReceiver, [status: OK] }
        }
    }

    @Transactional
    def delete(ExternalDataReceiver externalDataReceiver)
    {

        if (externalDataReceiver == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        externalDataReceiver.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver'), externalDataReceiver.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'externalDataReceiver.label', default: 'ExternalDataReceiver'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['permitAll'])
    @Transactional
    def evaluate()
    {
        def json = request.JSON
        def bindings = new Binding()

        json.each { key, value -> bindings.setVariable(key, value)
        }
        def shell = new GroovyShell(bindings)
        def closure
        def externalDataReceiverAutitTrail
        def externalDataReceiver = ExternalDataReceiver.findByNameAndActive(json['name'], true)
        def externalDataReceiverMessage

        try
        {
            externalDataReceiverAutitTrail = new ExternalDataReceiverAutitTrail()
            externalDataReceiverAutitTrail.receiver = externalDataReceiver
            externalDataReceiverAutitTrail.createdBy = externalDataReceiver?.createdBy
            externalDataReceiverAutitTrail.startTime = new Date()
            closure = shell.evaluate(externalDataReceiver?.script)
            externalDataReceiverMessage = ExternalDataReceiverMessage.findByReceiverAndCode(externalDataReceiver, closure)
            render externalDataReceiverMessage as JSON
        }
        catch (Exception e)
        {
            externalDataReceiverAutitTrail.log = e
            externalDataReceiverAutitTrail.endTime = new Date()
            if (externalDataReceiverAutitTrail.validate())
            {
                externalDataReceiverAutitTrail.save flush: true
            }
            else
            {
                log.info externalDataReceiverAutitTrail.errors
            }

            render 400
        }
    }

    // @Secured(['permitAll'])
    // def testSendData()
    // {
    //   def params = [:]
    //   params['name'] = "hfhInterface"
    //   params['serialNumber'] = 'SXE-HEB-GE8'
    //   params['status'] = 'Cancel'
    //   // params['paymentChannel'] = '418ZGL'
    //   params['responseMessage'] = 'invalidate serialNumber'
    //   params = JsonOutput.toJson(params)
    //
    //   URL url = new URL("http://localhost:8080/externalDataReceiver/evaluate")
    //   HttpURLConnection connection = (HttpURLConnection) url.openConnection()
    //   connection.setDoOutput(true)
    //   connection.setRequestMethod("POST")
    //   connection.setRequestProperty("Content-Type", "application/json")
    //   connection.outputStream.withWriter { Writer writer -> writer.write params }
    //   def response
    //   try
    //   {
    //     if (connection.getResponseCode() == 200)
    //     {
    //       response = connection.inputStream.withReader("UTF-8") { Reader reader -> reader.text }
    //       def result = JSON.parse(response)
    //       println result['code'] + ":" + result['message']
    //       render 200
    //       }
    //   }
    //   catch (Exception e)
    //   {
    //     render 400
    //   }
    //   return
    // }
}
