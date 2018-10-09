<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'territoryFlexFieldCategory.label', default: 'TerritoryFlexFieldCategory')}" />
        <title>新增区域-弹性域模块</title>
        <g:javascript>
            $(function () {
                $("#opportunityType").change(function(){
                    $.ajax({
                        type:"POST",
                        data:{opportunityType:$("#opportunityType").val(),territory:$("#territory").val()},
                        cache:false,
                        url: "/territoryFlexFieldCategory/selectFlexFieldCategory",
                        //url: "http://s53.zhongjiaxin.com/territoryFlexFieldCategory/selectFlexFieldCategory",
                        dataType:"JSON",
                        success:function (datas) {
                            var data = datas["flexFieldCategoryList"];
                            var filed = $("#flexFieldCategory");
                            filed.html("");
                            filed.append('<option value="null">请选择</option>');
                            for (var i = 0;i<=data.length-1;i++){
                                filed.append('<option value='+data[i].id+'>'+data[i].name+'</option>');
                            }
                        },
                    });
                });
            });
        </g:javascript>
    </head>
    <body class="fixed-navbar fixed-sidebar">
        <div class="small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <div id="hbreadcrumb" class="pull-right">
                        <ol class="hbreadcrumb breadcrumb">
                            <li>中佳信LMS</li>
                            <li>
                                <g:link controller="territoryFlexFieldCategory" action="index">区域-弹性域模块</g:link>
                            </li>
                            <li class="active">
                                <span>新增区域-弹性域模块</span>
                            </li>
                        </ol>
                    </div>

                    <h2 class="font-light m-b-xs">
                        新增区域-弹性域模块
                    </h2>
                </div>
            </div>
        </div>
        <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        新增区域弹性域模块
                    </div>
                    <div class="panel-body">
                        <div id="create-territoryFlexFieldCategory" class="content scaffold-create" role="main">
                            <g:if test="${flash.message}">
                                <div class="message" role="status">${flash.message}</div>
                            </g:if>
                            <g:hasErrors bean="${this.territoryFlexFieldCategory}">
                                <ul class="errors" role="alert">
                                <g:eachError bean="${this.territoryFlexFieldCategory}" var="error">
                                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                            </ul>
                            </g:hasErrors>
                            <g:form action="save"  class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">区域名称:</label>
                                    <div class="col-md-3">
                                        <g:if test="${this.territoryFlexFieldCategory?.territory != null && this.territoryFlexFieldCategory?.territory != ''}">
                                            <g:textField name="territory.id" required="required" id="territory" value="${this.territoryFlexFieldCategory?.territory?.id}" class="hide" />
                                            <g:textField name="territoryName" value="${this.territoryFlexFieldCategory?.territory?.name}" readonly="readonly" class="form-control" />
                                        </g:if>
                                        <g:else>
                                            <g:select name="territory.id" required="required" id="territory" value="${this.territoryFlexFieldCategory?.territory}" from="${com.next.Territory.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                        </g:else>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">订单类型</label>
                                    <div class="col-md-3">
                                        <g:select class="form-control" name="opportunityType.id" required="required" id="opportunityType" value="${this.territoryFlexFieldCategory?.opportunityType}" from="${com.next.OpportunityType.list()}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>
                                    </div>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">弹性域模块</label>
                                    <div class="col-md-3">
                                        %{--<g:select class="form-control" name="flexFieldCategory.id" required="required" id="flexFieldCategory" value="${this.territoryFlexFieldCategory?.flexFieldCategory}" from="${flexFieldCategoryList}" optionKey="id" optionValue="name" noSelection="${['null':'请选择']}"></g:select>--}%
                                        <select class="form-control" name="flexFieldCategory.id" required="required" id="flexFieldCategory">
                                            <option value="null">请选择</option>
                                            <g:each in="${flexFieldCategoryList}">
                                                <option value="${it.id}">${it?.name}</option>
                                            </g:each>
                                        </select>
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
        </div>
    </body>
</html>

