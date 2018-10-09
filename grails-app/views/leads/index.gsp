<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>匿名评房</title>
</head>
<body class="fixed-navbar fixed-sidebar">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>Leads</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                Leads
            </h2>
            <small><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> 所有leads</small>
        </div>
    </div>
</div>

<div class="content animate-panel">
        <g:if test="${flash.message}">
            <div class="alert alert-success alert-dismissible" role="alert">
                ${flash.message}
            </div>
        </g:if>
        <div class="row">
            <g:form method="POST" action="searchLeads">
                <div class="hpanel hblue">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                            <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i class="fa fa-times"></i>重置</button>
                        </div>
                        查询
                    </div>
                    <div class="panel-body">
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="所在城市" name="city" id="city"
                                   value="${leads?.city}">
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="区县" name="district" id="district"
                                   value="${leads?.district}">
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="物业类型" name="houseType" id="houseType"
                                   value="${leads?.houseType}">
                        </div>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" placeholder="特殊因素" name="specialFactors" id="specialFactors"
                                   value="${leads?.specialFactors}">
                        </div>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        %{--<g:link action="create" class="btn btn-default btn-xs">新建</g:link>--}%
                    </div>
                    全部leads
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <g:sortableColumn property="city"
                                                  title="${message(code: 'leads.city.label', default: '城市')}"></g:sortableColumn>
                                <g:sortableColumn property="district"
                                                  title="${message(code: 'leads.district.label', default: '区县')}"></g:sortableColumn>
                                <g:sortableColumn property="projectName"
                                                  title="${message(code: 'leads.projectName.label', default: '小区名称')}"></g:sortableColumn>
                                <g:sortableColumn property="building"
                                                  title="${message(code: 'leads.building.label', default: '楼栋信息')}"></g:sortableColumn>
                                <g:sortableColumn property="floor"
                                                  title="${message(code: 'leads.floor.label', default: '楼层信息')}"></g:sortableColumn>
                                <g:sortableColumn property="roomNumber"
                                                  title="${message(code: 'leads.roomNumber.label', default: '户号')}"></g:sortableColumn>
                                <g:sortableColumn property="orientation"
                                                  title="${message(code: 'leads.orientation.label', default: '朝向')}"></g:sortableColumn>
                                <g:sortableColumn property="area"
                                                  title="${message(code: 'leads.area.label', default: '建筑面积（m<sup>2</sup>）')}"></g:sortableColumn>
                                <g:sortableColumn property="address"
                                                  title="${message(code: 'leads.address.label', default: '房产地址')}"></g:sortableColumn>
                                <g:sortableColumn property="houseType"
                                                  title="${message(code: 'leads.houseType.label', default: '物业类型')}"></g:sortableColumn>
                                <g:sortableColumn property="assetType"
                                                  title="${message(code: 'leads.assetType.label', default: '房产类型')}"></g:sortableColumn>
                                <g:sortableColumn property="specialFactors"
                                                  title="${message(code: 'leads.specialFactors.label', default: '特殊因素')}"></g:sortableColumn>
                                <g:sortableColumn property="loanAmount"
                                                  title="${message(code: 'leads.loanAmount.label', default: '询值总价(万元)')}"></g:sortableColumn>
                                <g:sortableColumn property="unitPrice"
                                                  title="${message(code: 'leads.unitPrice.label', default: '询值单价(元)')}"></g:sortableColumn>
                                <g:sortableColumn property="createdDate"
                                                  title="${message(code: 'leads.createdDate.label', default: '申请时间')}"></g:sortableColumn>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${leadsList}">
                                <tr>
                                    <td>${it?.city}</td>
                                    <td>${it?.district}</td>
                                    <td>${it?.projectName}</td>
                                    <td>${it?.building}</td>
                                    <td>${it?.floor}/${it?.totalFloor}</td>
                                    <td>${it?.roomNumber}</td>
                                    <td>${it?.orientation}</td>
                                    <td>${it?.area}</td>
                                    <td>${it?.address}</td>
                                    <td>${it?.houseType?.name}</td>
                                    <td>${it?.assetType?.name}</td>
                                    <td>${it?.specialFactors?.name}</td>
                                    <td>${it?.loanAmount}</td>
                                    <td>${it?.unitPrice}</td>
                                    <td><g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it?.createdDate}"/></td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="pagination">
                        <g:paginate total="${leadsCount ?: 0}" params="${params}"/>
                    </div>
                </div>
            </div>
        </div>
</div>
<g:javascript>
    $(function(){
        $("#resetBtn").click(function () {
            $("#city").val("");
            $("#district").val("");
            $("#houseType").val("");
            $("#specialFactors").val("");
        })
    });

</g:javascript>
</body>
</html>
