<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'collateral.label', default: 'Collateral')}"/>
    <title>房产</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">

            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>房产</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                房产
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="message alert alert-info hide" role="status" id="alert">

        </div>

        <g:form method="POST" action="searchCollateral">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body seach-group">
                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="订单编号" id="serialNumber" name="serialNumber"
                               value="${params?.serialNumber}">
                    </div>
                        
                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="外部唯一ID" id="externalId" name="externalId"
                               value="${params?.externalId}">
                    </div>
                    
                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="小区名称" id="projectName" name="projectName"
                               value="${params?.projectName}">
                    </div>

                    <div class="col-md-3">
                        <g:select class="form-control" name="city" id="city"
                                  from="${com.next.City.list()}"
                                  valueMessagePrefix="stage" optionKey="name" optionValue="name" value="${params?.city}"
                                  noSelection="${['null': '请选择城市']}"/>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="开始时间" type="text" name="startTime" id="startTime" value="${params?.startTime}" readonly
                                   class="form-control daily-b" placeholder="开始时间">
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="input-group date form_datetime">
                            <span class="input-group-addon">
                                <span class="fa fa-calendar"></span>
                            </span>
                            <input title="结束时间" type="text" name="endTime" id="endTime" placeholder="结束时间"
                                   value="${params?.endTime}" readonly class="form-control daily-b">
                        </div>
                    </div>
                    
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    %{-- <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link> --}%
                </div>
                全部房产
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="externalId" title="外部唯一ID"></g:sortableColumn>
                            <g:sortableColumn property="serialNumber" title="订单号"></g:sortableColumn>
                            <g:sortableColumn property="propertySerialNumber" title="房产证编号"></g:sortableColumn>
                            <g:sortableColumn property="city" title="城市"></g:sortableColumn>
                            <g:sortableColumn property="district" title="区县"></g:sortableColumn>
                            <g:sortableColumn property="region" title="所在区域"></g:sortableColumn>
                            <g:sortableColumn property="projectName" title="小区名称"></g:sortableColumn>
                            <g:sortableColumn property="address" title="地址"></g:sortableColumn>
                            <g:sortableColumn property="orientation" title="朝向"></g:sortableColumn>
                            <g:sortableColumn property="area" title="面积（m2）"></g:sortableColumn>
                            <g:sortableColumn property="building" title="楼栋"></g:sortableColumn>
                            <g:sortableColumn property="unit" title="单元"></g:sortableColumn>
                            <g:sortableColumn property="roomNumber" title="户号"></g:sortableColumn>
                            <g:sortableColumn property="floor" title="所在楼层"></g:sortableColumn>
                            <g:sortableColumn property="totalFloor" title="总楼层"></g:sortableColumn>
                            <g:sortableColumn property="assetType" title="房产类型"></g:sortableColumn>
                            <g:sortableColumn property="houseType" title="物业类型"></g:sortableColumn>
                            <g:sortableColumn property="specialFactors" title="特殊因素"></g:sortableColumn>
                            <g:sortableColumn property="unitPrice" title="批贷房产单价（元）"></g:sortableColumn>
                            <g:sortableColumn property="totalPrice" title="批贷房产总价（万元）"></g:sortableColumn>
                            <g:sortableColumn property="status" title="状态"></g:sortableColumn>
                            <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                            <g:sortableColumn property="firstMortgageAmount" title="一抵金额(万元)"></g:sortableColumn>
                            <g:sortableColumn property="secondMortgageAmount" title="二抵金额(万元)"></g:sortableColumn>
                            <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                            <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                            <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="最新总价(万元)"></g:sortableColumn>
                            <g:sortableColumn property="buildTime" title="估值等级"></g:sortableColumn>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${collateralList}">
                            <tr>
                            <td class="collateralsExternalId">
                                <g:link controller="collateral" action="show" id="${it.id}">${it.externalId}</g:link>
                            </td>
                            <td>
                                <g:link controller="opportunity" action="show" id="${it.opportunity?.id}">${it?.opportunity?.serialNumber}</g:link>
                            </td>
                            <td>
                                ${it?.propertySerialNumber}
                            </td>
                            <td>
                                ${it.city?.name}
                            </td>

                            <td>
                                ${it.district}
                            </td>
                            <td>${it.region?.name}</td>
                            <td>
                                ${it.projectName}
                            </td>
                            <td>
                                ${it.address}
                            </td>
                            <td>
                                ${it.orientation}
                            </td>
                            <td class="area">
                                ${it.area}
                            </td>
                            <td>
                                ${it.building}
                            </td>
                            <td>
                                ${it.unit}
                            </td>
                            <td>
                                ${it.roomNumber}
                            </td>
                            <td>
                                ${it.floor}
                            </td>
                            <td>
                                ${it.totalFloor}
                            </td>
                            <td>
                                ${it.assetType}
                            </td>
                            <td>
                                ${it.houseType}
                            </td>
                            <td>
                                ${it.specialFactors}
                            </td>
                            <td>
                                <g:formatNumber number="${it?.unitPrice}" type="number" maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                <g:formatNumber number="${it?.totalPrice}" type="number" maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                ${it.status}
                            </td>
                            <td>
                                <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2" minFractionDigits="2"/>
                            </td>
                            <td>
                                ${it?.firstMortgageAmount}
                            </td>
                            <td>
                                ${it?.secondMortgageAmount}
                            </td>
                            <td>
                                ${it?.typeOfFirstMortgage?.name}
                            </td>
                            <td>
                                ${it?.mortgageType?.name}
                            </td>
                            <td>
                                ${it?.projectType?.name}
                            </td>
                            <td>
                                <g:formatDate format="yyyy" date="${it.buildTime}"/>
                            </td>
                            <td>
                                <g:if test="${it?.buildTime}">
                                    ${new Date().format("yyyy").toInteger().minus(it?.buildTime.format("yyyy").toInteger())}
                                </g:if>
                                <g:else>
                                    ${it?.buildTime}
                                </g:else>
                            </td>
                            <td class="latestCollateralPrice"></td>
                            <td class="valuationReliability"></td>
                        </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${collateralCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>

<g:javascript>
    $("#resetBtn").click(function () {
        $("#serialNumber").val("");
        $("#externalId").val("");
        $("#projectName").val("");
        $("#city").val("");
        $("#startTime").val("");
        $("#endTime").val("");
    })
</g:javascript>

</body>
</html>