<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'district.label', default: 'District')}" />
    <title>城区：${district.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">

                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li>
                            <g:link controller="district" action="index">城区管理</g:link>
                        </li>
                        <li class="active">
                            <span>${this.district.name}</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    城区: ${this.district?.name}
                </h2>

            </div>
        </div>
    </div>
    <div class="content animate-panel">
            <div class="row">
                <div class="hpanel hred contact-panel">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" action="edit" resource="${this.district}"><i class="fa fa-edit"></i>编辑</g:link>
                        </div>
                        城区基本信息
                    </div>
                    <div class="panel-body">
                        <div class="text-muted font-bold m-b-xs ol-md-6">
                            <div class="col-md-12">
                                <h3><a href=>${this.district.name}</a></h3></div>
                        </div>
                    </div>
                    <div class="panel-footer contact-footer">
                        <div class="row">
                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>所属城市:</span> <strong>${this?.district?.city?.name}</strong></div>
                            </div>
                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>城区编码:</span> <strong>${this?.district?.code}</strong></div>
                            </div>
                            <div class="col-md-3 border-right" style="">
                                <div class="contact-stat"><span>出它项时间:</span> <strong>${this.district?.daysOfOtherRights} （天）</strong></div>
                            </div>
                            <div class="col-md-3">
                                <div class="contact-stat"><span>地址数量:</span> <strong>${com.next.Address.countByDistrict(this.district)}</strong></div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="hpanel hyellow">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link class="btn btn-info btn-xs" controller="address" action="create" params="[district: this.district.id]"><i class="fa fa-plus"></i>添加</g:link>
                        </div>
                        辖区单位
                    </div>
                    <div class="panel-body no-padding">
                        <ul class="list-group">
                            <g:each in="${this.district.addresses}">
                                <li class="list-group-item">
                                    <div class="pull-right">
                                        <g:form controller="address" action="delete" id="${it.id}" method="DELETE">
                                            <input class="btn btn-danger btn-xs btn-outline" type="submit" value="删除" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                                        </g:form>
                                    </div>
                                    <g:link controller="address" action="show" id="${it.id}">${it.name}</g:link>
                                </li>
                            </g:each>
                        </ul>
                    </div>
                </div>
            </div>
    </div>
</body>

</html>
