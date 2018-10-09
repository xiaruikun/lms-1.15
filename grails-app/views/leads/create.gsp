<!DOCTYPE html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'leads.label', default: 'leads')}"/>
    <title>新建leads</title>
</head>
<body>
<a href="#edit-leads" class="skip" tabindex="-1">
    <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
</a>

<div class="nav" role="navigation">
    <ul>
        %{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
    </ul>
</div>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:hasErrors bean="${this.leads}">
    <ul class="errors" role="alert">
        <g:eachError bean="${this.leads}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                    error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>

<div class="row">
    <div class="container">
        <div class="hpanel">
            <div class="panel">
                <div class="panel-body">

                    <h2 style="display:inline;">Leads管理-新建leads</h2>

                    <g:link class="list" style="display:inline;float:right;" action="index"
                            resource="${this.leads}">返回列表</g:link>
                    <hr/>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="container">
        <div class="panel panel-default">
            <div class="panel">
                <div class="panel-heading">Leads管理--信息新建</div>

                <div class="panel-body">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.leads}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.leads}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <g:form action="save">

                        <table class="table">
                            <tr>
                                <td>城市</td>
                                <td><g:textField type="text" name="city" id="city"/></td>
                                <td>区县</td>
                                <td><g:textField type="text" name="district" id="district"/></td>
                            </tr>
                            <tr>
                                <td>楼栋信息</td>
                                <td><g:textField type="text" name="building" id="building"/></td>
                                <td>物业类型</td>
                                <td><g:textField type="text" name="houseType" id="houseType"/></td>
                                %{--<td><g:select name="city" optionKey="id" optionValue="name" from="${com.next.City.list()}"/></td>--}%
                            </tr>
                            <tr>
                                <td>楼层</td>
                                <td><g:textField type="text" name="floor" id="floor"/></td>
                                <td>总楼层</td>
                                <td><g:textField type="text" name="totalFloor" id="totalFloor"/></td>
                            </tr>
                            <tr>
                                <td>户号</td>
                                <td><g:textField type="text" name="roomNumber" id="roomNumber"/></td>
                                <td>特殊因素</td>
                                <td><g:textField type="text" name="specialFactors" id="specialFactors"/></td>
                            </tr>
                            <tr>
                                <td>朝向</td>
                                <td><g:textField type="text" name="orientation" id="orientation"/></td>
                                <td>建筑面积</td>
                                <td><g:textField type="text" name="area" id="area"/></td>
                            </tr>
                            <tr>
                                <td>居室</td>
                                <td><g:textField type="text" name="numberOfLivingRoom" id="numberOfLivingRoom"/></td>
                                <td>厅</td>
                                <td><g:textField type="text" name="numberOfReceptionRoom" id="numberOfReceptionRoom"/></td>
                            </tr>
                            <tr>
                                <td>询值总价</td>
                                <td><g:textField type="text" name="loanAmount" id="loanAmount"/></td>
                                <td>询值单价</td>
                                <td><g:textField type="text" name="unitPrice" id="unitPrice"/></td>
                            </tr>
                        </table>
                        <fieldset class="buttons">
                            <g:submitButton name="create" class="save" value="提交"/>
                        </fieldset>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>