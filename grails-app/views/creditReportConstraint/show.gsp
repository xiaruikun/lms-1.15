<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'opportunity')}"/>
    <title>查看大数据</title>
    <asset:stylesheet src="tree.css"/>
    <style>
    .tree ul li a {
        color: #337ab7 !important;
    }

    .label {
        font-size: 100%;
    }

    .parent_li em {
        font-style: normal;
        margin-right: 4px;
    }

    .label-success {
        background-color: #5cb85c;
    }
    </style>
</head>

<body>
%{-- <g:if test="${flash.message}">
     <div class="message" role="status">${flash.message}</div>
 </g:if>--}%
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${this.opportunity?.id}">订单</g:link>
                    </li>
                    <li class="active">
                        <span>大数据</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                大数据
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel" style="background-color: #fff; padding-left:0px;">
    <div class="row" style="padding-top: 10px;">
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </g:if>
        <g:hasErrors bean="${this.cList}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.cList}" var="error">
                    <li>
                        <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <style>
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{padding:3px;}
        </style>
        <div class="tree col-md-12">
            <g:each in="${this.cList}">
                <ul role="tree"  style="margin-left: -25px;">
                    <li class="parent_li" role="treeitem">
                        <span class="label label-info" title="Collapse this branch">
                            <i class="fa fa-lg fa-minus-circle"></i>
                            <em>姓名:${it?.fullName}</em>
                            <em>身份证号: ${it?.idNumber}</em>
                            <em>手机号码:
                                <span class="cellphoneFormat">${it?.cellphone}</span>
                            </em>

                        </span>
                        <ul role="group">
                            <g:each in="${it?.details}">
                                <li>
                                    <span class="label label-success" title="Collapse this branch">
                                        <i class="fa fa-lg fa-plus-circle"></i> ${it?.name}
                                    </span>
                                    <g:if test="${it?.name == '央行征信（中佳信）'}">
                                        <ul role="group" class="group">
                                            <g:if test="${it?.items?.items}">
                                                <g:each in="${it?.items?.items}">
                                                    <li style="display: none">
                                                        <span class="label label-warning" title="Expand this branch">
                                                            <i class="fa fa-lg fa-minus-circle"></i>
                                                            <g:if test="${it?.name}">
                                                                ${it?.name}
                                                            </g:if>
                                                            <g:else>
                                                                ${it?.type}
                                                            </g:else>
                                                        </span>
                                                            <div role="group" class="group active" style="margin-top: 10px; line-height: 32px;">
                                                            %{--<li>
                                                                <span>命中项目</span> –
                                                                <a href="javascript:void(0);">${it?.name}</a>
                                                            </li>--}%

                                                            %{--<g:if test="${it?.value}">
                                                               --}%%{-- ${it?.type}--}%%{--
                                                            --}%%{--</g:if>--}%%{--
                                                            --}%%{--<g:else>--}%%{--
                                                           <li>
                                                                <span>值</span> –
                                                                <a href="javascript:void(0);">
                                                                ${it?.value}
                                                                </a>
                                                           </li>
                                                            </g:if>--}%
                                                                <g:each in="${it?.items}">
                                                                %{--<li>--}%
                                                                        <div style="display: inline-table; margin-bottom: 5px;">
                                                                            <table class="table table-striped table-bordered table-hover">
                                                                                <tr>
                                                                                    <td>${it?.name}</td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td style="height: 35px;">
                                                                                        ${it?.value}
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </div>
                                                                        <g:if test="${it?.name in ['信息更新日期', '评分月份', '保证人代偿余额', '贷款最长逾期月数（贷记卡最高逾期月数，准贷记卡60天以上透支最长透支月数）', '贷款余额', '担保本金余额', '余额', '最近24个月还款状态（从最早的开始排）', '最近24个月还款状态（从最早的开始排）', '最近24个月还款状态（从最早的开始排）', '结算日期', '欠税统计日期', '诉讼标的金额', '已执行标的金额', '行政复议结果', '中断或终止缴费原因', '机构所在地', '截止日期', '最近24个月缴费记录（从最早开始排）', '标识', '查询次数', '查询原因', '明细记录', '逾期/透支金额', '明细记录']}">
                                                                            <br/>
                                                                        </g:if>
                                                                    %{--<span>${it?.name}</span>
                                                                    : <a>${it?.value}；</a>--}%
                                                                %{--</li>--}%
                                                                </g:each>
                                                            </div>
                                                    </li>
                                                </g:each>
                                            </g:if>
                                        </ul>
                                    </g:if>
                                    <g:else>
                                        <ul role="group" class="group">
                                            <g:if test="${it?.items?.items}">
                                                <g:each in="${it?.items?.items}">
                                                    <li style="display: none">
                                                        <span class="label label-warning" title="Expand this branch">
                                                            <i class="fa fa-lg fa-minus-circle"></i>
                                                            <g:if test="${it?.name}">
                                                                ${it?.name}
                                                            </g:if>
                                                            <g:else>
                                                                ${it?.type}
                                                            </g:else>
                                                        </span>
                                                        <g:if test="${it?.name in ['企业对外投资信息','法定代表人在其他机构任职信息']}">
                                                            <div role="group" class="group active" style="margin-top: 10px; line-height: 32px;">
                                                            %{--<li>
                                                                <span>命中项目</span> –
                                                                <a href="javascript:void(0);">${it?.name}</a>
                                                            </li>--}%

                                                            %{--<g:if test="${it?.value}">
                                                               --}%%{-- ${it?.type}--}%%{--
                                                            --}%%{--</g:if>--}%%{--
                                                            --}%%{--<g:else>--}%%{--
                                                           <li>
                                                                <span>值</span> –
                                                                <a href="javascript:void(0);">
                                                                ${it?.value}
                                                                </a>
                                                           </li>
                                                            </g:if>--}%
                                                                <g:each in="${it?.items}">
                                                                    <div style="display:inline-block; margin-bottom: -10px; width: 109px; height: 88px;">
                                                                        <table class="table table-striped table-bordered table-hover"  style="width: 109px; height: 100%;">
                                                                            <tr>
                                                                                <td>${it?.name}</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td style="word-break:break-all">${it?.value}</td>
                                                                            </tr>
                                                                        </table>
                                                                    </div>
                                                                    <g:if test="${it?.name =='邮编'||it?.name =='变更后内容'||it?.name =='处罚决定书签发日期'||it?.name =='职务名称'||it?.name =='币种'}">
                                                                        <div style="height: 3px; line-height:1px; padding:0px; margin: 0px;"></div>
                                                                    </g:if>
                                                                </g:each>
                                                            </div>
                                                        </g:if>
                                                        <g:else>
                                                            <div role="group" class="group active" style="margin-top: 10px; line-height: 32px;">
                                                            %{--<li>
                                                                <span>命中项目</span> –
                                                                <a href="javascript:void(0);">${it?.name}</a>
                                                            </li>--}%

                                                            %{--<g:if test="${it?.value}">
                                                               --}%%{-- ${it?.type}--}%%{--
                                                            --}%%{--</g:if>--}%%{--
                                                            --}%%{--<g:else>--}%%{--
                                                           <li>
                                                                <span>值</span> –
                                                                <a href="javascript:void(0);">
                                                                ${it?.value}
                                                                </a>
                                                           </li>
                                                            </g:if>--}%
                                                                <g:if test="${it?.items!=null}">
                                                                    <g:each in="${it?.items}">
                                                                    %{--<li>--}%
                                                                        <g:if test="${it?.name =='身份证照片' || it?.name =='详情信息'}">
                                                                            <div style="display: inline-table; margin-bottom: 5px;">
                                                                                <table class="table table-striped table-bordered table-hover">
                                                                                    <tr>
                                                                                        <td>${it?.name}</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="height: 35px;"><g:link target="_blank" url="${it?.value}">${it?.value}</g:link></td>
                                                                                    </tr>
                                                                                </table>
                                                                            </div>
                                                                        %{--<span>${it?.name}</span>--}%
                                                                        %{--: <a target="_blank" href="${it?.value}">${it?.value}；</a>--}%
                                                                        </g:if>
                                                                        <g:else>
                                                                            <g:if test="${it?.name =='司法执行信息'||it?.name =='司法失信信息'||it?.name =='司法案例信息'||it?.name =='税务行政执法信息'||it?.name =='催欠公告信息'||it?.name =='网贷逾期信息'}">
                                                                                <br/>
                                                                            </g:if>
                                                                            <div style="display: inline-table; margin-bottom: 5px;">
                                                                                <table class="table table-striped table-bordered table-hover">
                                                                                    <tr>
                                                                                        <td>${it?.name}</td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td style="height: 35px;">
                                                                                            <g:if test="${it?.value == "-99"}">未公布股东出资额信息</g:if>
                                                                                            <g:else>${it?.value}</g:else>
                                                                                        </td>
                                                                                    </tr>
                                                                                </table>
                                                                            </div>
                                                                            <g:if test="${it?.name =='邮编'||it?.name =='变更后内容'||it?.name =='处罚决定书签发日期'||it?.name =='币种'||it?.name =='退出日期'||it?.name =='执行标的（元）'||it?.name =='企业状态'||it?.name =='成立日期'||it?.name =='认缴出资额(万元)'||it?.name =='处罚执行情况'}">
                                                                                <br/>
                                                                            </g:if>
                                                                        %{--<span>${it?.name}</span>
                                                                        : <a>${it?.value}；</a>--}%
                                                                        </g:else>
                                                                    %{--</li>--}%

                                                                    </g:each>
                                                                </g:if>
                                                                <g:else>
                                                                    <g:if test="${it?.value == '0' }">
                                                                        <table class="table table-striped table-bordered table-hover">
                                                                            <tr>本人直接命中</tr>
                                                                        </table>
                                                                    </g:if>

                                                                </g:else>
                                                            </div>
                                                        </g:else>
                                                    </li>
                                                </g:each>
                                            </g:if>
                                        </ul>
                                    </g:else>
                                </li>
                            </g:each>
                        </ul>
                    </li>
                </ul>
            </g:each>
        </div>
    </div>
</div>

<script>
    $(function () {

        $('.tree > ul').attr('role', 'tree').find('ul').attr('role', 'group');
        $('.tree').find('li:has(ul)').addClass('parent_li').attr('role', 'treeitem').find(' > span').attr('title', 'Collapse this branch').on('click', function (e) {
            var children = $(this).parent('li.parent_li').find(' > ul > li');
            if (children.is(':visible')) {
                children.hide('fast');
                $(this).attr('title', 'Expand this branch').find(' > i').removeClass().addClass('fa fa-lg fa-plus-circle');
            } else {
                children.show('fast');
                $(this).attr('title', 'Collapse this branch').find(' > i').removeClass().addClass('fa fa-lg fa-minus-circle');
            }
            e.stopPropagation();
        });
    })
</script>
</body>
</html>
