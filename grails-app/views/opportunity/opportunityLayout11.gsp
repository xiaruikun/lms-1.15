<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>贷款放款审批详情页2.0-11</title>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb m-t-none">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunityTeam" action="index">订单管理</g:link>
                    </li>
                    <li class="active">
                        <span>信息查看</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light">
                订单号: ${this.opportunity.serialNumber}
            <span style="font-weight: normal" class="text-info">
                【${this.opportunity?.stage?.description}】
                【保护期： <g:if test="${this.opportunity?.protectionEndTime > new Date()}">
                ${this.opportunity?.protectionEndTime - this.opportunity?.protectionStartTime}天
                </g:if>】
                <g:if test="${this.opportunity?.status == 'Failed'}">【订单结果：<span
                        class="text-danger">失败</span>】</span></g:if>
                <g:elseif test="${this.opportunity?.status == 'Completed'}">【订单结果：成功】</g:elseif>
                <g:else>【订单结果：进行中】</g:else>
            </span>
                <g:if test="${this.opportunity?.isTest}">
                    <span class="label label-danger"><i class="fa fa-star"></i> 测试 <i class="fa fa-star"></i></span>
                </g:if>
            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel" style="padding-bottom: 16px">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                业务概要
                <g:if test="${this.opportunity?.status == 'Failed'}">
                    <span class="failReason text-danger">订单失败：${this.opportunity?.causeOfFailure?.name}</span>
                </g:if>
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>业务区域</th>
                            <th>业务来源</th>
                            <th>抵押类型</th>
                            <th>
                                房产总价（万元）
                            </th>
                            <th>借款金额（万元）</th>
                            <th>借款期数（月）</th>

                            <th>产品类型</th>
                            <th>共同借款人</th>
                            <th>客户类型</th>
                            <th>息费（%）</th>
                            <th>借款服务费（%）</th>
                            <th>渠道服务费（%）</th>
                            <th>付息方式</th>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <th>上扣息月份数（月）</th>
                            </g:if>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.collaterals[0]?.city?.name}</td>
                            <td>${this.opportunity?.contact?.account?.name}</td>
                            <td>
                                <g:each in="${this.collaterals}">
                                    ${it?.mortgageType?.name}、
                                </g:each>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.loanAmount}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>


                            <td>
                                <g:formatNumber number="${this.opportunity?.actualAmountOfCredit}" minFractionDigits="2"
                                                maxFractionDigits="2"/>
                            </td>
                            <td>
                                ${this.opportunity?.loanDuration}
                            </td>
                            <td>${this.opportunity?.product?.name}</td>
                            <td>
                                <g:each in="${this.opportunityContacts}">${it?.contact?.fullName}、</g:each>
                            </td>
                            <td>${this.opportunity?.lender?.level?.description}</td>

                            <td>
                                <g:formatNumber number="${this.opportunity?.monthlyInterest}" 
                                                maxFractionDigits="9"/>
                            </td>
                            <td>
                                <g:formatNumber number="${this.opportunity?.serviceCharge}" 
                                                maxFractionDigits="9"/>
                            </td>

                            <td><g:formatNumber number="${this.opportunity?.commissionRate}" 
                                                maxFractionDigits="9"/></td>
                            <td>${this.opportunity?.interestPaymentMethod?.name}</td>
                            <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                                <td>${this.opportunity?.monthOfAdvancePaymentOfInterest}</td>
                            </g:if>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hred">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                经纪人信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center">
                        <thead>
                        <tr>
                            <th>经纪人姓名</th>
                            <th>经纪人电话</th>
                            <th>支持经理姓名</th>
                            <th>
                                支持经理电话
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${this.opportunity?.contact?.fullName}</td>
                            <td class="cellphoneFormat">${this.opportunity?.contact?.cellphone}</td>
                            <td>${this.opportunity?.user}</td>
                            <td class="cellphoneFormat">${this.opportunity?.user?.cellphone}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                房产信息
            </div>

            <div class="panel-body collateral">
                <g:each in="${this.collaterals}">

                    <div class="item">
                        <p>
                            第<span class="collateralOrder"></span>套房产信息（ 评估单价：
                            <span class="text-info"><g:formatNumber number="${it?.unitPrice}" type="number"
                                                                    maxFractionDigits="2"
                                                                    minFractionDigits="2"/></span>元 ；
                        评估总价： <span class="text-info"><g:formatNumber number="${it?.totalPrice}" type="number"
                                                                      maxFractionDigits="2"
                                                                      minFractionDigits="2"/></span>万元；
                        询值状态：<span class="text-info collateralStatus">${it?.status}</span> ）
                        </p>

                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover text-center">
                                <thead>
                                <tr>
                                    <g:sortableColumn property="externalId" title="外部唯一ID"></g:sortableColumn>
                                    <g:sortableColumn property="city" title="城市"></g:sortableColumn>
                                    <g:sortableColumn property="district" title="区县"></g:sortableColumn>
                                    <g:sortableColumn property="region" title="所在区域"></g:sortableColumn>
                                    <g:sortableColumn property="projectName" title="小区名称"></g:sortableColumn>
                                    <g:sortableColumn property="address" title="房本地址"></g:sortableColumn>
                                    <g:sortableColumn property="building" title="楼栋"></g:sortableColumn>
                                    <g:sortableColumn property="unit" title="单元"></g:sortableColumn>
                                    <g:sortableColumn property="roomNumber" title="户号"></g:sortableColumn>
                                    <g:sortableColumn property="floor" title="所在楼层"></g:sortableColumn>
                                    <g:sortableColumn property="totalFloor" title="总楼层"></g:sortableColumn>

                                    <g:sortableColumn property="area" title="面积（m2）"></g:sortableColumn>
                                    <g:sortableColumn property="orientation" title="朝向"></g:sortableColumn>

                                    <g:sortableColumn property="houseType" title="物业类型"></g:sortableColumn>
                                    <g:sortableColumn property="assetType" title="房产类型"></g:sortableColumn>
                                    <g:sortableColumn property="specialFactors" title="特殊因素"></g:sortableColumn>
                                    <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                                    <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                                    <g:sortableColumn property="firstMortgageAmount"
                                                      title="一抵金额(万元)"></g:sortableColumn>
                                    <g:sortableColumn property="secondMortgageAmount"
                                                      title="二抵金额(万元)"></g:sortableColumn>
                                    <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                                    <g:sortableColumn property="loanToValue" title="房产编号"></g:sortableColumn>

                                    <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                                    <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                                    <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                        <g:sortableColumn colspan="2" property="buildTime" title="操作"></g:sortableColumn>
                                    </g:if>
                                    <g:else>
                                        <g:sortableColumn colspan="3" property="buildTime" title="操作"></g:sortableColumn>
                                    </g:else>

                                </tr>
                                </thead>
                                <tbody>

                                <tr>
                                    <td>
                                        <g:link controller="collateral" action="show"
                                                id="${it.id}">${it.externalId}</g:link>
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
                                        ${it.area}
                                    </td>
                                    <td>
                                        ${it.orientation}
                                    </td>

                                    <td>
                                        ${it.houseType}
                                    </td>
                                    <td>
                                        ${it.assetType}
                                    </td>
                                    <td>
                                        ${it.specialFactors}
                                    </td>
                                    <td>
                                        ${it?.mortgageType?.name}
                                    </td>
                                    <td>
                                        ${it?.typeOfFirstMortgage?.name}
                                    </td>
                                    <td>
                                        ${it?.firstMortgageAmount}
                                    </td>
                                    <td>
                                        ${it?.secondMortgageAmount}
                                    </td>
                                    <td>
                                        <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                        minFractionDigits="2"/>

                                    </td>
                                    <td>
                                        ${it?.propertySerialNumber}
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
                                    <td>
                                        <g:link class="btn btn-primary btn-outline btn-xs" controller="collateral"
                                                action="pvQuery"
                                                id="${it?.id}">询值</g:link>

                                    </td>
                                    <td>
                                        <g:link class="btn btn-primary btn-xs btn-outline" controller="opportunity"
                                                action="historyShow2"
                                                id="${it?.id}">明细</g:link>
                                        <button class="btn btn-info btn-xs" data-toggle="modal" data-target="#applyForAmendment"><i
                                                class="fa fa-edit"></i>申请修改</button>
                                    </td>
                                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                    </g:if>
                                    <g:else>
                                        <td>
                                            <g:form resource="${it}" method="DELETE">
                                                <button class="deleteBtn btn btn-danger btn-xs btn-outline"
                                                        type="button">删除</button>
                                            </g:form>

                                        </td>
                                    </g:else>
                                </tr>

                                </tbody>
                            </table>
                        </div>

                    </div>

                </g:each>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link target=" _blank" class="btn btn-info btn-xs" controller="creditReportConstraint"
                            action="creditReportShow" id="${this.opportunity.id}"><i
                            class="fa fa-database"></i>大数据</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                共同借款人
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="type" title="联系人类型"></g:sortableColumn>
                            <g:sortableColumn property="fullName" title="姓名"></g:sortableColumn>
                            <g:sortableColumn property="idNumber" title="身份证号"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus" title="年龄（岁）"></g:sortableColumn>
                            <g:sortableColumn property="cellphone" title="手机号"></g:sortableColumn>
                            <g:sortableColumn property="maritalStatus" title="婚姻状态"></g:sortableColumn>
                            <g:sortableColumn property="country" title="国籍"></g:sortableColumn>
                            <g:sortableColumn property="identityType" title="身份证件类型"></g:sortableColumn>
                            <g:sortableColumn property="profession" title="职业"></g:sortableColumn>
                            <g:sortableColumn property="connnectedContact" title="关联人"></g:sortableColumn>
                            <g:sortableColumn property="connectedType" title="关联关系"></g:sortableColumn>
                            %{--<sec:ifAllGranted roles="ROLE_ADMINISTRATOR">--}%
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <g:sortableColumn width="8%" class="text-center" property="operation"
                                                  title="操作"></g:sortableColumn>
                            </g:else>

                            %{--</sec:ifAllGranted>--}%
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunityContacts}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityContact" action="show"
                                            id="${it?.id}">${it?.type?.name}</g:link>
                                </td>
                                <td>
                                    ${it?.contact?.fullName}
                                </td>
                                <td>
                                    ${it?.contact?.idNumber}
                                </td>
                                <td>
                                    <g:if test="${it?.contact?.idNumber}">
                                        ${new Date().format("yyyy").toInteger().minus(it?.contact?.idNumber[6..9].toInteger())}
                                    </g:if>
                                    <g:else>
                                        ${it?.contact?.idNumber}
                                    </g:else>
                                </td>
                                <td class="cellphoneFormat">
                                    ${it?.contact?.cellphone}
                                </td>
                                <td>
                                    ${it?.contact?.maritalStatus}
                                </td>
                                <td>
                                    ${it?.contact?.country?.name}
                                </td>
                                <td>
                                    ${it?.contact?.identityType?.name}
                                </td>
                                <td>
                                    ${it?.contact?.profession?.name}
                                </td>
                                <td>
                                    ${it?.connectedContact?.fullName}
                                </td>
                                <td>
                                    ${it?.connectedType?.name}
                                </td>
                                %{--<sec:ifAllGranted roles="ROLE_ADMINISTRATOR">--}%
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <td width="8%" class="text-center">
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </g:else>

                                %{--</sec:ifAllGranted>--}%
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <g:each in="${this.opportunityFlexFieldCategorys}">
            <g:if test="${it?.flexFieldCategory?.name == '产调结果'}">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        产调信息
                    </div>

                    <div class="panel-body no-padding">
                        <div class="table-responsive ">
                            <table class="table table-striped table-bordered table-hover text-center">
                                <thead>
                                <tr>
                                    <g:sortableColumn width="30%" property="executionSequence"
                                                      title="${message(code: 'opportunityFlexField.name.label', default: '产调核查内容')}"/>
                                    <g:sortableColumn width="70%" property="stage"
                                                      title="${message(code: 'opportunityFlexField.value.label', default: '审核结果')}"/>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${it?.fields}">
                                    <tr>
                                        <td>
                                            <g:link controller="opportunityFlexField" action="show" id="${it?.id}"
                                                    class="firstTd">${it?.name}</g:link>
                                        </td>
                                        <td>${it?.value}</td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </g:if>
        </g:each>
    </div>

    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="attachments" action="create"
                            id="${this.opportunity.id}"><i class="fa fa-upload"></i>新增附件</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                附件信息
            </div>

            <div class="panel-body float-e-margins">
                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show" id="${this.opportunity.id}"
                        params="[attachmentTypeName: '基础材料']">基础材料</g:link>
                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show" id="${this.opportunity.id}"
                        params="[attachmentTypeName: '其他资料']">其他资料</g:link>
                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show"
                        id="${this.opportunity.id}"
                        params="[attachmentTypeName: '征信']">征信</g:link>
                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show" id="${this.opportunity.id}"
                        params="[attachmentTypeName: '大数据']">大数据</g:link>

                <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show" id="${this.opportunity.id}"
                        params="[attachmentTypeName: '产调结果']">产调结果</g:link>
                <g:link target="_blank" class="btn btn-outline btn-primary" controller="attachments"
                        action="show"
                        id="${this.opportunity.id}"
                        params="[attachmentTypeName: '签呈']">签呈</g:link>
                <g:link class="btn btn-outline btn-primary" controller="opportunityFlexField"
                        id="${this.opportunity?.id}"
                        action="opportunityFlexField01" target=" _blank"><i class="fa"></i>外访报告</g:link>
                <g:link target=" _blank" controller="attachments"
                        action="show"
                        id="${this.opportunity?.id}"
                        params="[attachmentTypeName: '抵押合同全委']" class="btn btn-outline btn-primary">抵押、合同、全委</g:link>

                <g:link target=" _blank" controller="attachments" action="show" id="${this.opportunity?.id}"
                        params="[attachmentTypeName: '其他放款要求资料']"
                        class="btn btn-outline btn-primary">其他放款要求资料</g:link>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                主审结果
            </div>

            <div class="panel-body no-padding form-horizontal">
                <div class="form-group">
                    <label class="col-md-3 control-label">审核可贷金额：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.actualAmountOfCredit}
                        <g:if test="${this.opportunity?.actualAmountOfCredit != null}">
                            万元
                        </g:if>
                        </span>
                    </div>

                    <label class="col-md-3 control-label">月息：</label>

                    <div class="col-md-2">
                        <span class="cont">
                        <g:formatNumber number="${this.opportunity?.monthlyInterest}" 
                                                maxFractionDigits="9"/>
                        <g:if test="${this.opportunity?.monthlyInterest != null}">
                            %
                        </g:if>
                        </span>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">借款服务费：</label>

                    <div class="col-md-2">
                        <span class="cont"> <g:formatNumber number="${this.opportunity?.serviceCharge}" 
                                                maxFractionDigits="9"/>
                                                 <g:if test="${this.opportunity?.serviceCharge != null}">
                            %
                        </g:if>
                                                </span>
                       
                    </div>

                    <label class="col-md-3 control-label">付息方式：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.interestPaymentMethod?.name}</span>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">渠道服务费：</label>

                    <div class="col-md-2">
                        <span class="cont"><g:formatNumber number="${this.opportunity?.commissionRate}" 
                                                maxFractionDigits="9"/>
                        <g:if test="${this.opportunity?.commissionRate != null}">
                            %
                        </g:if>
                        </span>
                    </div>
                    <label class="col-md-3 control-label">返佣方式：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.commissionPaymentMethod?.name}</span>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">渠道返费：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.commission}
                        <g:if test="${this.opportunity?.commission != null}">
                            万元
                        </g:if>
                        </span>
                    </div>

                    <label class="col-md-3 control-label">客户级别：</label>

                    <div class="col-md-3">
                        <span class="cont">
                            <g:if test="${this.opportunity?.lender?.level?.name == 'A'}">优质</g:if>
                            <g:if test="${this.opportunity?.lender?.level?.name == 'B'}">次优</g:if>
                            <g:if test="${this.opportunity?.lender?.level?.name == 'C'}">疑难</g:if>
                            <g:if test="${this.opportunity?.lender?.level?.name == 'D'}">不良</g:if>
                            <g:if test="${this.opportunity?.lender?.level?.name == '待评级'}">待评级</g:if>
                        </span>

                    </div>
                </div>

                <div class="form-group">

                    <label class="col-md-3 control-label">借款合同单号：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.externalId}</span>
                    </div>
                    <label class="col-md-3 control-label">意向金：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.advancePayment}
                        <g:if test="${this.opportunity?.advancePayment != null}">
                            元
                        </g:if>
                        </span>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label">实际贷款期限：</label>

                    <div class="col-md-2">
                        <span class="cont">${this.opportunity?.actualLoanDuration}月</span>
                    </div>
                    <label class="col-md-3 control-label">抵押凭证类型：</label>

                    <div class="col-md-2">
                        <span class="cont">
                            ${this.opportunity?.mortgageCertificateType?.name}
                        </span>
                    </div>
                </div>
                <g:if test="${this.opportunity?.interestPaymentMethod?.name == '上扣息'}">
                    <div class="form-group">
                        <label class="col-md-3 control-label">上扣息月份数：</label>

                        <div class="col-md-2">
                            <span class="cont">
                                ${this.opportunity?.monthOfAdvancePaymentOfInterest}月
                            </span>
                        </div>
                    </div>

                </g:if>

            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence >= com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                        <g:link class="btn btn-info btn-xs" controller="opportunityContract" action="create"
                                params="[opportunity: this.opportunity.id]"><i class="fa fa-plus"></i>新增</g:link>
                    </g:if>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                合同信息
            </div>

            <div class="panel-body no-padding">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <g:sortableColumn property="contract.serialNumber" title="合同编号"></g:sortableColumn>
                            <g:sortableColumn property="contract.type" title="合同类别"></g:sortableColumn>
                            <g:sortableColumn property="contract.createdDate" title="创建时间"></g:sortableColumn>
                            <g:sortableColumn property="contract.modifiedDate" title="修改时间"></g:sortableColumn>
                            <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            </g:if>
                            <g:else>
                                <g:sortableColumn property="operation" title="操作"></g:sortableColumn>
                            </g:else>


                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${this.opportunity?.contracts}">
                            <tr>
                                <td>
                                    <g:link controller="opportunityContract" action="show"
                                            id="${it?.id}">${it?.contract?.serialNumber}</g:link>
                                </td>
                                <td>
                                    ${it?.contract?.type?.name}
                                </td>
                                <td>
                                    ${it?.createdDate}
                                </td>
                                <td>
                                    ${it?.modifiedDate}
                                </td>
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('抵押已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                                </g:else>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" controller="opportunityProduct" id="${this.opportunity?.id}"
                            action="batchEdit"><i class="fa fa-edit"></i>编辑</g:link>
                    <g:link class="btn btn-info btn-xs" controller="opportunity" id="${this.opportunity?.id}"
                            action="initOpportunityProduct"><i class="fa fa-calculator"></i>初始化费用</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                费用
            </div>

            <div class="panel-body no-padding productInterest">
                <g:each in="${opportunityProduct}">
                    <div class="col-md-2 border-right border-bottom">
                        <div class="contact-stat">
                            <span>${it?.productInterestType?.name}</span>
                            <strong>费率 ${it?.rate}；上扣息月份数 ${it?.firstPayMonthes}</strong>
                            <g:form resource="${it}" method="DELETE">
                                <button class="deleteBtn btn btn-danger btn-xs m-t-sm" type="button"><i
                                        class="fa fa-trash-o"></i> 删除</button>
                            </g:form>
                        </div>
                    </div>
                </g:each>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="hpanel collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                风险调查报告
            </div>

            <div class="panel-body no-padding" style="border-top: 2px solid #3498db">
                <input type="hidden" name="opportunity" id="opportunity" value="${this.opportunity?.id}">
                <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                    <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求', '罚息约定']}">
                        <div class="hpanel">
                            <div class="panel-heading hbuilt" style="border-width:1px 0">
                                ${category?.flexFieldCategory?.name}
                            </div>

                            <div class="panel-body" style="border-width:1px 0;padding: 10px 20px">
                                <g:each in="${category.fields}" var="field">
                                    <div style="padding: 2px 0">${field?.name}：${field?.value}</div>
                                </g:each>
                            </div>
                        </div>
                    </g:if>
                </g:each>

            </div>

        </div>
    </div>
    <div class="row">
        <div class="hpanel hgreen collapsed">
            <div class="panel-heading hbuilt">
                <div class="panel-tools">
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                订单跟踪
            </div>


            <div class="panel-body no-padding">
                <div class="v-timeline vertical-container animate-panel" data-child="vertical-timeline-block"
                     data-delay="1">
                    <g:each in="${this.opportunityFlows}">
                        <g:each in="${this.opportunityRoles}" var="role">
                            <g:if test="${it?.executionSequence < currentFlow?.executionSequence && it?.endTime}">
                                <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval'}">
                                    <div class="vertical-timeline-block">
                                        <div class="vertical-timeline-icon navy-bg">
                                            <i class="fa fa-calendar text-primary"></i>
                                        </div>

                                        <div class="vertical-timeline-content">
                                            <div class="p-sm">
                                                <span class="vertical-date pull-right">${role?.user} <br/> <small><g:formatDate
                                                        format="yyyy-MM-dd HH:mm:ss" date="${it?.endTime}"/></small>
                                                </span>

                                                <h2>${it?.stage?.name}</h2>
                                            </div>
                                        </div>
                                    </div>
                                </g:if>
                            </g:if>
                        </g:each>

                    </g:each>
                </div>
            </div>
        </div>
    </div>
</div>

</div>

</body>

</html>
