<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>外访报告-附件</title>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <h2 class="font-light">
                合同编号：${this.opportunity?.externalId}
                <span>/</span>
                订单号: ${this.opportunity.serialNumber}

            </h2>

        </div>
    </div>
</div>

<div class="content animate-panel">
    <g:each in="${this.opportunityFlexFieldCategorys}">
        <g:if test="${it?.flexFieldCategory?.name in ['抵押物评估值', '抵押物其他情况', '外访预警']}">

            <div class="row" id="">
                <div class="hpanel hyellow collapsed">
                    <div class="panel-heading hbuilt">
                        <div class="panel-tools">

                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        ${it?.flexFieldCategory?.name}
                    </div>

                    <div class="panel-body no-padding">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <g:sortableColumn property="executionSequence"
                                                      title="${message(code: 'opportunityFlexField.name.label', default: '名称')}"/>
                                    %{--<g:sortableColumn property="executionSequence" title="${message(code: 'opportunityFlexField.description.label', default: '描述')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityFlexField.dataType.label', default: '数据类型')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityFlexField.defaultValue.label', default: '默认值')}" />
                                    <g:sortableColumn property="stage" title="${message(code: 'opportunityFlexField.valueConstraints.label', default: '约束')}" />--}%
                                    <g:sortableColumn property="stage"
                                                      title="${message(code: 'opportunityFlexField.value.label', default: '值')}"/>

                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${it?.fields}" var="field">
                                    <tr>
                                        <td width="20%">
                                            <g:link controller="opportunityFlexField" action="show" id="${field?.id}"
                                                    class="firstTd">${field?.name}</g:link>
                                        </td>
                                        %{--<td width="15%">${it?.description}</td>
                                        <td width="15%">${it?.dataType}</td>
                                        <td width="15%">${it?.defaultValue}</td>
                                        <td width="20%">${it?.valueConstraints}</td>--}%
                                        <td width="72%">${field?.value}</td>

                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </g:if>
    </g:each>
    <g:if test="${this.canAttachmentsShow}">
        <div class="row">
            <div class="hpanel hgreen">
                <div class="panel-heading">
                    外访附件
                </div>

                <div class="panel-body float-e-margins">
                    <!-- <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                                 action="show" id="${this.opportunity.id}"
                                 params="[attachmentTypeName: '小区外围商业']">小区外围商业</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '小区出入口']">小区出入口</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '小区内配套设置']">小区内配套设置</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '小区楼栋单元门牌']">小区楼栋单元门牌</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '抵押物各房间照片']">抵押物各房间照片</g:link>
                    <g:link target=" _blank" class="btn btn-outline btn-primary" controller="attachments"
                            action="show" id="${this.opportunity.id}"
                            params="[attachmentTypeName: '家访人员出镜照片']">家访人员出镜照片</g:link> -->
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '小区外围配套物业']">小区外围配套物业</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '出入口道路']">出入口道路</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '小区名称及物业地址']">小区名称及物业地址</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show"
                            params="[attachmentTypeName: '小区楼栋号、单元号、门牌号']">小区楼栋号、单元号、门牌号</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '小区规模']">小区规模</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '抵押物各房间情况']">抵押物各房间情况</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '家访人员出镜照片']">家访人员出镜照片</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '客户出镜的照片']">客户出镜的照片</g:link>
                    <g:link class="btn btn-outline btn-primary" controller="attachments"
                            id="${this.opportunity?.id}"
                            action="show" params="[attachmentTypeName: '附加资料']">附加资料</g:link>

                </div>
            </div>
        </div>
    </g:if>

</div>

<script>
    $(function () {
        $("body").addClass("fixed-small-header");
    })
</script>
</body>

</html>
