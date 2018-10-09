<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'collateral.label', default: 'Collateral')}"/>
    <title>新增房产信息</title>
    <style>
    .input-group[class*="col-"] {
        float: left;
        padding-right: 15px;
        padding-left: 15px;
    }
    </style>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show"
                                id="${this.collateral?.opportunity?.id}">房产信息详情</g:link>
                    </li>
                    <li class="active">
                        <span>新增房产信息</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增房产信息
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:form class="form-horizontal" action="save">
        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    新增房产信息
                </div>

                <div class="panel-body">

                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.collateral}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.collateral}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">

                        <label class="col-md-2 control-label">订单号</label>

                        <div class="col-md-3">
                            <input class="form-control" name="opportunity.id" type="hidden"
                                   value="${this.collateral?.opportunity?.id}">
                            <input class="form-control" disabled="disabled"
                                   type="text" value="${this.collateral?.opportunity?.serialNumber}">
                        </div>
                        <label class="col-md-2 control-label">房产证编号</label>

                        <div class="col-md-3">

                            <input class="form-control" name="propertySerialNumber"
                                   type="text" value="${this.collateral?.propertySerialNumber}">
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">城市</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="city" id="city" optionKey="id"
                                      optionValue="name"
                                      from="${com.next.City.list()}"
                                      value="${this.collateral?.city?.id}"/>
                        </div>
                        <label class="col-md-2 control-label">区县</label>

                        <div class="col-md-3">
                            <input class="form-control" name="district"
                                   type="text" value="${this.collateral?.district}">
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">小区名称</label>

                        <div class="col-md-3">
                            <input class="form-control" name="projectName"
                                   type="text" value="${this.collateral?.projectName}">
                        </div>
                        <label class="col-md-2 control-label">地址</label>

                        <div class="col-md-3">
                            <input class="form-control" name="address"
                                   type="text" value="${this.collateral?.address}">
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">朝向</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="orientation"
                                      from="${["东", "南", "西", "北", "东西", "南北", "东南", "东北", "西南", "西北"]}"
                                      value="${this.collateral?.orientation}"/>
                        </div>
                        <label class="col-md-2 control-label">建筑面积</label>

                        <div class="col-md-3">
                            <div class="input-group">
                                <input class="form-control" name="area"
                                       type="text" value="${this.collateral?.area}">
                                <span class="input-group-addon">平米</span>
                            </div>

                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">楼栋</label>

                        <div class="col-md-3">
                            <input class="form-control" name="building"
                                   type="text" value="${this.collateral?.building}">
                        </div>
                        <label class="col-md-2 control-label">单元</label>

                        <div class="col-md-3">
                            <input class="form-control" name="unit"
                                   type="text" value="${this.collateral?.unit}">
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">户号</label>

                        <div class="col-md-3">
                            <input class="form-control" name="roomNumber"
                                   type="text" value="${this.collateral?.roomNumber}">
                        </div>
                        <label class="col-md-2 control-label">总楼层</label>

                        <div class="col-md-3">
                            <input class="form-control" name="totalFloor"
                                   type="text" value="${this.collateral?.totalFloor}">
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">所在楼层</label>

                        <div class="col-md-3">
                            <input class="form-control" name="floor"
                                   type="text" value="${this.collateral?.floor}">
                        </div>
                        <label class="col-md-2 control-label">房产类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" noSelection="${['': '请选择']}" name="assetType" id="assetType"
                                      optionKey="name" optionValue="name" from="${com.next.AssetType.list()}"
                                      value="${this.collateral?.assetType}"/>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">物业类型</label>

                        <div class="col-md-3">
                            <g:select class="form-control" noSelection="${['': '请选择']}" name="houseType" id="houseType"
                                      optionKey="name" optionValue="name" from="${com.next.HouseType.list()}"
                                      value="${this.collateral?.houseType}"/>
                        </div>
                        <label class="col-md-2 control-label">特殊因素</label>

                        <div class="col-md-3">
                            <g:select class="form-control" noSelection="${['': '请选择']}" name="specialFactors"
                                      id="specialFactors" optionKey="name" optionValue="name"
                                      from="${com.next.SpecialFactors.list()}"
                                      value="${this.collateral?.specialFactors}"/>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">状态</label>

                        <div class="col-md-3">

                            <g:select class="form-control" name="status"
                                      from="${['Pending', 'Completed', 'Failed']}"
                                      value="${this.collateral?.status}"/>
                        </div>
                        <label class="col-md-2 control-label">抵押率</label>

                        <div class="col-md-3 input-group">

                            <input class="form-control" name="loanToValue"
                                   type="text" value="${this.collateral?.loanToValue}">
                            <span class="input-group-addon">%</span>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">借款金额</label>

                        <div class="col-md-3">
                            <div class="input-group">
                                <input class="form-control" name="appliedTotalPrice" type="text"
                                       value="${this.collateral?.appliedTotalPrice}">
                                <span class="input-group-addon">万元</span>
                            </div>
                        </div>
                        <label class="col-md-2 control-label">反馈特殊因素</label>

                        <div class="col-md-3">
                            <input class="form-control" name="reasonOfPriceAdjustment"
                                   type="text" value="${this.collateral?.reasonOfPriceAdjustment}">
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">立项类型</label>

                        <div class="col-md-3">
                            <g:select id="projectType" name='projectType.id' value="${this.collateral?.projectType?.id}"
                                      from="${com.next.CollateralProjectType.findAll("from CollateralProjectType as c where c.name <> '商住两用'")}"
                                      optionKey="id" optionValue="name" class="form-control"></g:select>
                        </div>
                        <label class="col-md-2 control-label">建成时间</label>

                        <div class="col-md-3">
                            <g:datePicker name="buildTime" default="none" noSelection="['': '---请选择---']"
                                          value="${this.collateral?.buildTime}" precision="year"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">

                        <label class="col-md-2 control-label">抵押类型</label>

                        <div class="col-md-3">
                            <g:select id="mortgageType" name='mortgageType.id'
                                      value="${this.collateral?.mortgageType?.id}"
                                      from="${com.next.MortgageType.list()}" optionKey="id" optionValue="name"
                                      class="form-control"></g:select>
                        </div>
                        <label class="col-md-2 control-label">一抵性质</label>

                        <div class="col-md-3">
                            <g:select id="typeOfFirstMortgage" name='typeOfFirstMortgage.id'
                                      value="${this.collateral?.typeOfFirstMortgage?.id}" noSelection="['': '无']"
                                      from="${com.next.TypeOfFirstMortgage.list()}" optionKey="id" optionValue="name"
                                      class="form-control"></g:select>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">

                        <label class="col-md-2 control-label">一抵金额</label>

                        <div class="col-md-3">
                            <div class="input-group">
                                <input class="form-control" name="firstMortgageAmount" type="text"
                                       value="${this.collateral?.firstMortgageAmount}">
                                <span class="input-group-addon">万元</span>
                            </div>
                        </div>
                        <label class="col-md-2 control-label">二抵金额</label>

                        <div class="col-md-3">
                            <div class="input-group">
                                <input class="form-control" name="secondMortgageAmount" type="secondMortgageAmount"
                                       value="${this.collateral?.secondMortgageAmount}">
                                <span class="input-group-addon">万元</span>
                            </div>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">

                        <label class="col-md-2 control-label">所在区域</label>

                        <div class="col-md-3">
                            <g:select id="region" name='region.id' noSelection="${['': '请选择']}"
                                      value="${this.collateral?.region?.id}" from="${com.next.CollateralRegion.list()}"
                                      optionKey="id" optionValue="name" class="form-control"></g:select>
                        </div>
                        <label class="col-md-2 control-label">邮政编码</label>

                        <div class="col-md-3">
                            <input class="form-control" name="postcode" maxlength="18"
                                   type="text" value="${this.collateral?.postcode}">
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group atticAreaInfo hide">
                        <label class="col-md-2 control-label">阁楼面积</label>

                        <div class="col-md-3 input-group">
                            <input class="form-control" name="atticArea"
                                   type="text" value="${this.collateral?.atticArea}">
                            <span class="input-group-addon">平米</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                </div>
            </div>
        </div>

        <div class="row">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </div>
                    规则引擎信息
                </div>

                <div class="panel-body">
                    <div class="form-group">

                        <label class="col-md-2 control-label">持证时间</label>

                        <div class="col-md-3">
                            <g:datePicker precision="day" default="none"
                                          value="${this.collateral?.propertyCertificateHoldDate}"
                                          noSelection="['': '--请选择--']"
                                          name="propertyCertificateHoldDate"/>
                        </div>
                        <label class="col-md-2 control-label">土地使用年限</label>
                        <div class="col-md-3">
                            <g:select class="form-control" name="landUsageTerm" noSelection="${['': '--请选择--']}"
                                      value="${this.collateral?.landUsageTerm}" from="${['70', '50', '40']}"></g:select>
                        </div>


                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">是否新房</label>

                        <div class="col-md-2">
                            <span class="cont">
                                <g:checkBox class="i-checks" name="newHouse" value="${this.collateral?.newHouse}"/>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-3 col-md-offset-4">
                <g:submitButton class="btn btn-info" name="update" value="保存"/>
            </div>
        </div>
    </g:form>
</div>
<script>
    $(function () {
        $("#specialFactors").change(function () {
            var specialFactors = $(this).val();
            if (specialFactors == "顶楼带阁楼" || specialFactors == "跃层") {
                $(".atticAreaInfo").removeClass("hide");
            } else {
                $(".atticAreaInfo").addClass("hide").find("input").val("");
            }
        })
    })
</script>
</body>
</html>
