<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'menuItem.label', default: 'MenuItem')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="menu" action="index">menuItem列表</g:link>
                        </li>
                        <li class="active">
                            <span>编辑menuItem</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    menuItem
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
           %{-- <g:form resource="${this.menuItem}" method="PUT">
                  <fieldset class="form">
                      <f:all bean="menuItem"/>
                  </fieldset>
                  <fieldset class="buttons">
                      <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                  </fieldset>
              </g:form>--}%
            <div class="hpanel hblue">
                <div class="panel-heading">
                    编辑menuItem
                </div>
                <div class="panel-body">
                    <g:form resource="${this.menuItem}" method="PUT" class="form-horizontal">
                        <g:if test="${flash.message}">
                            <div class="message" menu="status">${flash.message}</div>
                        </g:if>
                        <g:hasErrors bean="${this.menuItem}">
                            <ul class="errors" menu="alert">
                                <g:eachError bean="${this.menuItem}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                        <g:message error="${error}" />
                                    </li>
                                </g:eachError>
                            </ul>
                        </g:hasErrors>
                        <div class="form-group">
                            <input type="hidden" name="menu.id" value="${this.menuItem?.menu?.id}">

                            <label class="col-md-4 control-label">名称</label>
                            <div class="col-md-4">
                                <g:textField name="name" class="form-control" value="${this.menuItem?.name}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">链接</label>
                            <div class="col-md-4">
                                <g:textField name="linkUrl" class="form-control" value="${this.menuItem?.linkUrl}"/>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">顺序</label>
                            <div class="col-md-4">
                                <g:textField name="displayOrder" class="form-control" value="${this.menuItem?.displayOrder}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">类型</label>
                            <div class="col-md-4">
                                <g:select class="form-control" name="type" 
                                          value="${this.menuItem?.type}" from="${['Folder', 'Item']}"></g:select>

                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">父级</label>
                            <div class="col-md-4">
                                <g:select class="form-control" name="parent.id" optionKey="id" optionValue="name"
                                        value="${this.menuItem?.parent}"  from="${com.next.MenuItem.list()}"></g:select>

                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">图标</label>
                            <div class="col-md-4">
                                <g:textField name="icon" class="form-control" value="${this.menuItem?.icon}"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-md-3 col-md-offset-5">
                                <button class="btn btn-info">保存</button>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
    <script>
        $(function(){
            $("input[name='displayOrder']").TouchSpin({
                verticalbuttons: true,
                min: 0,
                max:10000
            });
        })
    </script>

    </body>
</html>
