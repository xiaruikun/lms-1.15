<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'opportunityCsvFile.label', default: 'OpportunityCsvFile')}" />
        <title>新增文件</title>
    </head>
    <body>

        <div class="small-header">
          <div class="hpanel">
              <div class="panel-body">
                  <div id="hbreadcrumb" class="pull-right">
                      <ol class="hbreadcrumb breadcrumb">
                          <li>中佳信LMS</li>
                          <li><g:link controller="opportunityCsvFile" action="index">存量订单导入</g:link></li>
                          <li class="active">
                              <span>新增文件</span>
                          </li>
                      </ol>
                  </div>
                  <h2 class="font-light m-b-xs">
                      新增文件
                  </h2>
              </div>
          </div>
        </div>
        <div class="content animate-panel">
          <div class="row">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityCsvFile}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.opportunityCsvFile}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增基础信息文件
                  </div>
                  <div class="panel-body">
                    
                        <g:form controller="opportunityCsvFile" action="uploadBaseInfo" method="post" enctype="multipart/form-data">
                            <input type="file" name="myFile" />
                            %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                            <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
                        </g:form>
                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增银行流水文件
                  </div>
                  <div class="panel-body">
                    
                        <g:form controller="opportunityCsvFile" action="uploadCashFlow" method="post" enctype="multipart/form-data">
                            <input type="file" name="myFile" />
                            %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                            <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
                        </g:form>
                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增退单说明文件
                  </div>
                  <div class="panel-body">
                    
                        <g:form controller="opportunityCsvFile" action="uploadChargeback" method="post" enctype="multipart/form-data">
                            <input type="file" name="myFile" />
                            %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                            <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
                        </g:form>
                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增还清日期文件
                  </div>
                  <div class="panel-body">
                    
                        <g:form controller="opportunityCsvFile" action="uploadFinalRepaymentDate" method="post" enctype="multipart/form-data">
                            <input type="file" name="myFile" />
                            %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                            <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
                        </g:form>
                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增本金逾期文件
                  </div>
                  <div class="panel-body">
                    
                        <g:form controller="opportunityCsvFile" action="uploadPrincipalOverdue" method="post" enctype="multipart/form-data">
                            <input type="file" name="myFile" />
                            %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                            <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
                        </g:form>
                  </div>
              </div>
          </div>
          <div class="row">
              <div class="hpanel hyellow">
                  <div class="panel-heading">
                      新增利息逾期文件
                  </div>
                  <div class="panel-body">
                    
                        <g:form controller="opportunityCsvFile" action="uploadInterestOverdue" method="post" enctype="multipart/form-data">
                            <input type="file" name="myFile" />
                            %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                            <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
                        </g:form>
                  </div>
              </div>
          </div>
        </div>
        <!--<a href="#create-opportunityCsvFile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-opportunityCsvFile" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.opportunityCsvFile}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.opportunityCsvFile}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            %{--<g:form action="save">
                <fieldset class="form">
                    <f:all bean="opportunityCsvFile"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>--}%
            <g:form controller="opportunityCsvFile" action="upload" method="post" enctype="multipart/form-data">
                <input type="file" name="myFile" />
                %{--<g:select name="purCategory.id" from="${purcatelist}" optionKey="id" value="${documentInstance?.purCategory?.id}"  />--}%
                <input type="submit" value="上&nbsp;&nbsp;&nbsp;传" />
            </g:form>
        </div>-->
    </body>
</html>
