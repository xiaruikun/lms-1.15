<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'collateral.label', default: 'Collateral')}"/>
    <title>${this.collateral?.opportunity?.serialNumber}信息</title>
    <asset:stylesheet src="homer/vendor/blueimp-gallery/css/blueimp-gallery.min.css"/>
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
                        <span>房产信息</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                订单号
            </h2>
            <small>
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                ${this.collateral?.opportunity?.serialNumber}
            </small>
        </div>
    </div>
</div>

<div class="content animate-panel form-horizontal">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.collateral?.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.collateral?.opportunity,this.collateral?.opportunity.stage)?.executionSequence}">
                    </g:if>
                    <g:else>
                        <g:link class="btn btn-info btn-xs" action="edit"
                                resource="${this.collateral}"><i class="fa fa-edit"></i>编辑</g:link>
                    </g:else>


                </div>
                房产信息
            </div>

            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label">外部唯一ID：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.externalId}">
                    </div>
                    <label class="col-md-2 control-label">城市：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.city}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">区县：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.district}">
                    </div>
                    <label class="col-md-2 control-label">小区名称：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.projectName}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">地址：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.address}">
                    </div>
                    <label class="col-md-2 control-label">朝向：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.orientation}">
                    </div>

                    <div class="hr-line-dashed"></div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label">建筑面积：</label>

                    <div class="col-md-3">
                        <span class="cont pl12">${this.collateral?.area} m<sup>2</sup></span>
                    </div>

                    <label class="col-md-2 control-label">楼栋：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.building}">
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">单元：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.unit}">
                    </div>
                    <label class="col-md-2 control-label">户号：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.roomNumber}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">总楼层：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.totalFloor}">
                    </div>

                    <label class="col-md-2 control-label">所在楼层：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.floor}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">房产类型：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.assetType}">
                    </div>
                    <label class="col-md-2 control-label">房屋类型：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.houseType}">
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">单价：</label>

                    <div class="col-md-3">
                        <span class="cont pl12"><g:formatNumber number="${this.collateral?.unitPrice}" type="number"
                                                                maxFractionDigits="2"
                                                                minFractionDigits="2"/> 元</span>

                    </div>
                    <label class="col-md-2 control-label">总价：</label>

                    <div class="col-md-3">
                        <span class="cont pl12"><g:formatNumber number="${this.collateral?.totalPrice}"
                                                                type="number" maxFractionDigits="2"
                                                                minFractionDigits="2"/>万元</span>

                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">特殊因素：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled" id="specialFactors"
                               type="text" value="${this.collateral?.specialFactors}">
                    </div>
                    <label class="col-md-2 control-label">状态：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.status}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">订单号：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled" type="hidden"
                               value="${this.collateral?.opportunity?.id}">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.opportunity?.serialNumber}">
                    </div>
                    <label class="col-md-2 control-label">借款金额：</label>

                    <div class="col-md-3">
                        <span class="cont pl12">${this.collateral?.appliedTotalPrice} 万元</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">房产证编号：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled" type="text"
                               value="${this.collateral?.propertySerialNumber}">

                    </div>
                    <label class="col-md-2 control-label">抵押率：</label>

                    <div class="col-md-3">
                        <span class="cont pl12"><g:formatNumber number="${this.collateral?.loanToValue}"
                                                                type="number" maxFractionDigits="2"
                                                                minFractionDigits="2"/>%</span>

                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">抵押类型：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled" type="text"
                               value="${this.collateral?.mortgageType?.name}">

                    </div>
                    <label class="col-md-2 control-label">一抵性质：</label>

                    <div class="col-md-3">

                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.typeOfFirstMortgage?.name}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">一抵金额：</label>

                    <div class="col-md-3">
                        <span class="cont  pl12">${this.collateral?.firstMortgageAmount} 万元</span>
                    </div>
                    <label class="col-md-2 control-label">二抵金额：</label>

                    <div class="col-md-3">
                        <span class="cont  pl12">${this.collateral?.secondMortgageAmount} 万元</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">立项类型：</label>

                    <div class="col-md-3">
                        <span class="cont  pl12">${this.collateral?.projectType?.name}</span>
                    </div>
                    <label class="col-md-2 control-label">建成时间：</label>

                    <div class="col-md-3">
                        <span class="cont  pl12"><g:formatDate format="yyyy"
                                                               date="${this.collateral?.buildTime}"/>年</span>

                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">所在区域：</label>

                    <div class="col-md-3">
                        <span class="cont  pl12">${this.collateral?.region?.name}</span>
                    </div>

                    <label class="col-md-2 control-label">反馈意见：</label>

                    <div class="col-md-3">
                        <input class="form-control" disabled="disabled"
                               type="text" value="${this.collateral?.reasonOfPriceAdjustment}">
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group ">
                    <label class="col-md-2 control-label">邮政编码:：</label>

                    <div class="col-md-3">
                        <span class="cont">
                            ${this.collateral?.postcode}
                        </span>
                    </div>

                    <div class="atticAreaInfo">
                        <label class="col-md-2 control-label">阁楼面积:：</label>

                        <div class="col-md-3">
                            <span class="cont">${this.collateral?.atticArea}平米</span>
                            <input class="form-control" name="atticArea" type="number" disabled="disabled">
                        </div>
                    </div>

                </div>

                <div class="form-group ">
                    <label class="col-md-2 control-label">外访值:：</label>

                    <div class="col-md-3">
                        <span class="cont  pl12">
                            <g:formatNumber number="${this.collateral?.fastUnitPrice}" type="number"
                                                                maxFractionDigits="2"
                                                                minFractionDigits="2"/> 
                            万元</span>
                        
                    </div>

                </div>
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

                    <label class="col-md-2 control-label">持证时间：</label>

                    <div class="col-md-3">
                        <span class="cont">
                            <g:formatDate format="yyyy-MM-dd" date="${this.collateral?.propertyCertificateHoldDate}"/>
                        </span>
                    </div>
                    <label class="col-md-2 control-label">土地使用年限：</label>

                    <div class="col-md-3">
                        <g:if test="${this.collateral?.landUsageTerm}">
                            <span class="cont">
                                ${this.collateral?.landUsageTerm}年
                            </span>
                        </g:if>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label">是否新房：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            <g:if test="${this.collateral?.newHouse == true}">
                                是
                            </g:if>
                            <g:if test="${this.collateral?.newHouse == false}">
                                否
                            </g:if>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                产调信息
            </div>

            <div class="panel-body">
                <div class="form-group">

                    <label class="col-md-2 control-label">产调状态：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.propertyOwnershipInvestigationStatus}</span>
                    </div>
                    <label class="col-md-2 control-label">产权归属：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.propertyOwnership}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">是否有查封记录：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            ${this.collateral?.propertySealedup}
                        </span>
                    </div>
                    <label class="col-md-3 control-label">查封日期：</label>

                    <div class="col-md-4">
                        <span class="cont">
                            <g:formatDate format="yyyy-MM-dd" date="${this.collateral?.propertySealedupDate}"/></span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>


                <div class="form-group">
                    <label class="col-md-3 control-label">是否为查封他人房产而提供财产担保导致查封：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.collateral?.propertySealedupReason}</span>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                其他
            </div>

            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label">二手房来源：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.houseOrigin}</span>
                    </div>
                    <label class="col-md-2 control-label">租户类型：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.tenantType}</span>
                    </div>
                </div>

            </div>

        </div>

    </div>

    <div class="row">
        <div class="hpanel hblue collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                外访预警
            </div>

            <div class="panel-body">
                <div class="form-group">

                    <div class="form-group">
                        <label class="col-md-4 control-label">抵押物内是否有70岁以上老人：</label>

                        <div class="col-md-1">
                            <span class="cont">
                                ${this.collateral?.seventyYearsElder}
                            </span>
                        </div>
                        <label class="col-md-4 control-label">抵押物内是否有卧床不起、精神障碍等特殊人群：</label>

                        <div class="col-md-1">
                            <span class="cont">${this.collateral?.specialPopulation}</span>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>
                    <label class="col-md-4 control-label">抵押物格局是否被破坏：</label>

                    <div class="col-md-1">
                        <span class="cont">
                            ${this.collateral?.propertyStructure}
                        </span>
                    </div>
                    <label class="col-md-4 control-label">隔断数目：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.collateral?.partitionNumber}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-4 control-label">抵押物是否具备居住使用条件：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.propertyLivingCondition}</span>
                    </div>
                    <label class="col-md-4 control-label">抵押物内有火灾情况：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.marksOfFire}</span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-4 control-label">抵押物是顶层有漏水痕迹：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.topFloorWithWaterLeakage}</span>
                    </div>
                    <label class="col-md-4 control-label">抵押物噪音较大（临街、临高速、临城铁）：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.noiseEnvironment}</span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-4 control-label">抵押物为筒子楼：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.tubeShapedApartment}</span>
                    </div>
                    <label class="col-md-4 control-label">抵押物有拆迁可能：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.housingDemolition}</span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-4 control-label">抵押物立项为住宅实际为办公：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.actualUsageIsOffice}</span>
                    </div>
                    <label class="col-md-4 control-label">抵押物立项为住宅实际为住宅但整栋楼或多数楼层为办公：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.mostOfFloorsAreOffices}</span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-4 control-label">抵押物有租户未披露：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.undisclosedTenant}</span>
                    </div>
                    <label class="col-md-4 control-label">抵押物为半地下、地下室或紧邻高压线：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.basementOrHighVoltage}</span>
                    </div>

                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-4 control-label">抵押物内有大型动物：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.dangerousAnimal}</span>
                    </div>
                    <label class="col-md-4 control-label">借款人婚姻状况存疑：</label>

                    <div class="col-md-1">
                        <span class="cont">${this.collateral?.doubtfulMarriageStatus}</span>
                    </div>

                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                抵押物其它情况
            </div>

            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label">商业成熟度：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.businessSophistication}</span>
                    </div>
                    <label class="col-md-2 control-label">交通情况：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.traffic}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">医院：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.hospital}</span>
                    </div>
                    <label class="col-md-2 control-label">学区类型：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.schoolDistrictType}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">幼儿园：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.kindergarten}</span>
                    </div>
                    <label class="col-md-2 control-label">小学：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.primarySchool}</span>

                    </div>
                </div>

                <div class="hr-line-dashed"></div>


                <div class="form-group">
                    <label class="col-md-2 control-label">初中：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.middleSchool}</span>
                    </div>
                    <label class="col-md-2 control-label">高中：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.highSchool}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">银行数量：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.numberOfBanks}</span>
                    </div>
                    <label class="col-md-2 control-label">是否有超市：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.supermarket}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">是否有农贸市场：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.farmersMarket}</span>
                    </div>
                    <label class="col-md-2 control-label">经纪公司：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.landAgency}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">小区绿化：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.residentialGreen}</span>
                    </div>
                    <label class="col-md-2 control-label">停车位：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.parkingSpace}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">楼体外观：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.buildingAppreance}</span>
                    </div>
                    <label class="col-md-2 control-label">电梯数量：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.numberOfElevators}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">

                    <label class="col-md-2 control-label">健身器材：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.gymEquipment}</span>
                    </div>
                    <label class="col-md-2 control-label">社区规模：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.communitySize}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-2 control-label">房屋使用状态：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.houseUsageStats}</span>
                    </div>
                    <label class="col-md-2 control-label">装修情况：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.collateral?.decoration}</span>
                    </div>

                </div>
            </div>
        </div>
    </div>


    <div class="row">

        <div class="hpanel hblue collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                反馈材料
            </div>

            <div class="panel-body lightBoxGallery">
                <g:each in="${this.photoes}">
                    <div class="col-lg-2">
                        <a href="${it?.fileName}" title="${it?.type?.name}" data-gallery="">
                            <img class="img-responsive" src="${it?.fileName}" alt="${it?.type?.name}"/>
                        </a>
                    </div>
                </g:each>
            </div>
        </div>
    </div>

    <div id="blueimp-gallery" class="blueimp-gallery">
        <div class="slides"></div>

        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
    </div>
</div>
<asset:javascript src="homer/vendor/blueimp-gallery/js/jquery.blueimp-gallery.min.js"/>
<script>
    $(function () {
        var specialFactors = $("#specialFactors").val();
        if (specialFactors == "顶楼带阁楼" || specialFactors == "跃层") {
            $(".atticAreaInfo").removeClass("hide");
        } else {
            $(".atticAreaInfo").addClass("hide");
        }

        $.each($("span.cont"), function (i, obj) {
            if ($(obj).text().trim() == "true") {
                $(obj).text("是");
            }
            if ($(obj).text().trim() == "false") {
                $(obj).text("否");
            }
        });
    });

</script>
</body>
</html>
