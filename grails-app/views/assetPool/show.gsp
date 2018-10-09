<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'assetPool.label', default: 'assetPool')}"/>
    <title>资产池详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="assetPool" action="index">资产池管理</g:link>
                    </li>
                    <li class="active">
                        <span>资产池详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                资产池详情
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
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_INVESTOR">
                    <g:link class="btn btn-info btn-xs" action="edit" resource="${this.assetPool}"><i
                      class="fa fa-edit"></i>编辑</g:link>
                  </sec:ifNotGranted>
                    <g:link class="btn btn-info btn-xs" action="assetPoolIndex" controller="assetPoolOpportunity" params="[assetPool: this.assetPool.id]"><i class="fa fa-database"></i>查看明细</g:link>
                   %{-- <g:form resource="${this.assetPool}" method="DELETE" style="display: inline-block">
                        <button class="deleteBtn btn btn-danger btn-xs" type="button">
                            <i class="fa fa-trash-o"></i>删除
                        </button>
                    </g:form>--}%
                </div>
                资产池详情
            </div>

            <div class="panel-body">
                <div class="col-md-12">
                    <h4>
                        ${this.assetPool?.name}
                    </h4>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                  <sec:ifNotGranted roles="ROLE_INVESTOR">
                    <g:link action="create" controller="AssetPoolTeam" params="[assetPool: this.assetPool.id]" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增</g:link>
                  </sec:ifNotGranted>
                </div>
                资产池Team
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn width="20%" property="assetPoolName" title="名称"/>
                            <g:sortableColumn width="20%" property="username" title="用户名"/>
                            %{--<g:sortableColumn width="20%" property="user" title="用户"/>--}%
                            <sec:ifNotGranted roles="ROLE_INVESTOR">
                              <th width="10%" class="text-center" colspan="2">操作</th>
                            </sec:ifNotGranted>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${com.next.AssetPoolTeam.findAllByAssetPool(this.assetPool)}">
                            <tr>
                                <td>${it.assetPoolName}</td>
                                <td>${it?.user?.fullName}</td>
                                %{--<td>${it?.user?.fullName}</td>--}%
                                <sec:ifNotGranted roles="ROLE_INVESTOR">
                                      <td class="text-center">
                                        <g:link class="btn btn-primary btn-xs btn-outline"
                                        controller="AssetPoolTeam" action="edit"
                                        id="${it?.id}">
                                        <i class="fa fa-edit"></i>
                                        编辑
                                      </g:link>

                                    </td>
                                    <td class="text-center">
                                      <g:form controller="AssetPoolTeam" action="delete"
                                      style="display: inline-block"
                                      id="${it.id}"
                                      method="DELETE">
                                      <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                        <i class="fa fa-trash-o"></i>
                                        删除
                                      </button>
                                    </g:form>
                                  </td>
                                </sec:ifNotGranted>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    %{--<div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" controller="assetPoolOpportunity" params="[assetPool: this.assetPool.id]" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增</g:link>
                </div>
                资产池订单
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="opportunitySerialNumber" title="资产池订单号"/>
                            <g:sortableColumn property="assetPoolName" title="资产池名称"/>
                            <th width="15%" class="text-center" colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${com.next.AssetPoolOpportunity.findAllByAssetPool(this.assetPool)}">
                            <tr>
                                <td>${it.opportunitySerialNumber}</td>
                                <td>${it?.assetPoolName}</td>
                                <td class="text-center">
                                    <g:link class="btn btn-primary btn-xs btn-outline"
                                            controller="assetPoolOpportunity" action="edit"
                                            id="${it?.id}">
                                        <i class="fa fa-edit"></i>
                                        编辑
                                    </g:link>

                                </td>
                                <td class="text-center">
                                    <g:form controller="assetPoolOpportunity" action="delete"
                                            style="display: inline-block"
                                            id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除
                                        </button>
                                    </g:form>
                                </td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>--}%

</div>
</body>
</html>
