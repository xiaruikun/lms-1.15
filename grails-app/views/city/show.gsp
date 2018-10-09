<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'city.label', default: 'City')}" />
    <title>城市：${city.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="city" action="index">城市</g:link>
                        </li>
                        <li class="active">
                            <span>${this.city.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    城市: ${this.city?.name}
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
        <div class="row">
            <g:if test="${flash.message}">
                <div class="message alert alert-info" role="status">${flash.message}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                </div>
            </g:if>
            <div class="hpanel horange">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.city}"><i class="fa fa-edit"></i>编辑</g:link>
                    </div>
                    城市详情
                </div>
                <div class="panel-body">
                    <div class="text-muted font-bold m-b-xs ol-md-6">
                        <div class="col-md-12">
                            <h4><a href=>${this.city?.name}</a></h4>
                        </div>
                    </div>
                </div>
                <div class="panel-footer contact-footer">
                     <div class="row">
                           <div class="col-md-4 border-right" style="">
                                <div class="contact-stat"><span>区域数量:</span> <strong>${this.territoryAccounts?.size()}</strong></div>
                           </div>
                           <div class="col-md-4 border-right" style="">
                                 <div class="contact-stat"><span>公司数量:</span> <strong>${this.accountCities?.size()}</strong></div>
                            </div>
                     </div>
                 </div>
            </div>

        </div>
        <div class="row">
            <div class="hpanel hyellow">
                <div class="panel-heading hbuilt">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" controller="district" action="create" params="[city: this.city.id]"><i class="fa fa-plus"></i>添加</g:link>
                    </div>
                    下属城区
                </div>
                <div class="panel-body no-padding">
                    <ul class="list-group">
                        <g:each in="${this.city.districts}">
                            <li class="list-group-item">
                                <div class="pull-right">
                                    <g:form controller="district" action="delete" id="${it.id}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-outline btn-xs" type="button"><i class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </div>
                                <g:link controller="district" action="show" id="${it.id}">${it.name}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</body>

</html>
