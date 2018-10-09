<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
    <title>新增角色</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="product" action="index">角色管理</g:link>
                    </li>
                    <li class="active">
                        <span>新增角色</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                角色管理
            </h2>
        </div>
    </div>
</div>
<div class="content animate-panel">
    <div class="row">
       %{--  <g:form action="save">
               <fieldset class="form">
                   <f:all bean="role"/>
               </fieldset>
               <fieldset class="buttons">
                   <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
               </fieldset>
           </g:form>--}%
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增角色
            </div>
            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.role}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.role}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}" />
                                </li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <label class="col-md-3 control-label">角色名称</label>
                        <div class="col-md-3">
                            <g:textField name="authority" value="${this.role?.authority}" class="form-control" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">角色描述</label>
                        <div class="col-md-3">
                            <g:textField name="description" value="${this.role?.description}" class="form-control" />
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                   <div class="form-group">
                       <label class="col-md-3 control-label">菜单</label>

                       <div class="col-md-3">
                           <g:select id="mortgageType" name='menu.id' noSelection="['': '请选择']"
                                     value="${this.role?.menu?.id}"
                                     from="${com.next.Menu.list()}" optionKey="id" optionValue="name"
                                     class="form-control"></g:select>
                       </div>
                   </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" name="update" value="保存" />
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
</body>

</html>
