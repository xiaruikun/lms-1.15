<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><g:layoutTitle default="Some Title"/></title>
    <link rel="icon" href="${createLinkTo(dir: "images", file: "favicon.ico")}" type="image/x-icon">
    <link rel="shortcut icon" href="${createLinkTo(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <asset:stylesheet src="homer/vendor/fontawesome/css/font-awesome.min.css"/>
    <asset:stylesheet src="homer/vendor/metisMenu/dist/metisMenu.min.css"/>
    <asset:stylesheet src="homer/vendor/bootstrap/dist/css/bootstrap.min.css"/>
    <asset:stylesheet src="homer/style.css"/>
    <asset:stylesheet src="homer/vendor/sweetalert/lib/sweet-alert.css"/>
    <asset:stylesheet src="homer/vendor/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css"/>
    <asset:stylesheet src="highlight/styles/atelier-seaside-light.css"/>
    <asset:stylesheet src="homer/vendor/select2-3.5.2/select2.min.css"/>
    <asset:stylesheet src="homer/vendor/select2-3.5.2/select2-bootstrap.css"/>
    <asset:stylesheet src="homer/vendor/ladda/ladda-themeless.min.css"/>
    <asset:stylesheet src="homer/vendor/datetimepicker/bootstrap-datetimepicker.min.css"/>
    <asset:stylesheet src="homer/vendor/summernote/summernote.css"/>
    <asset:stylesheet src="homer/vendor/bootstrap-touchspin/jquery.bootstrap-touchspin.min.css"/>
    <asset:javascript src="homer/vendor/jquery/dist/jquery.min.js"/>

    <g:layoutHead/>

    <style type="text/css">
    html {
        height: 100%;
    }

    body {
        font-family: "Microsoft YaHei", "Microsoft Sans Serif", "Helvetica Neue", Helvetica, Arial, "Hiragino Sans GB", sans-serif;
        background-color: #f1f3f6;
    }

    .table {
        margin-bottom: 0;
    }

    .pagination {
        margin: 0 auto;
        text-align: center;
    }

    .pagination a,
    .pagination .currentStep {
        color: #666;
        display: inline-block;
        margin: 0 0.1em;
        padding: 0.25em 0.7em;
        text-decoration: none;
        -moz-border-radius: 0.3em;
        -webkit-border-radius: 0.3em;
        border-radius: 0.3em;
    }

    .pagination a:hover, .pagination a:focus,
    .pagination .currentStep {
        background-color: #999;
        color: #fff;
        outline: none;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.8);
    }

    .panel-tools i {
        margin-right: 4px;
    }

    .errors {
        position: absolute;
        right: 10%;
        z-index: 10;
        top: 10px;
        color: #e74c3c;
        margin: 5px 0 0 0;
        font-weight: 400;
    }

    .errors li {
        padding: 10px 0;
    }

    .contact-stat span {
        font-size: 13px;
        color: #88898c;
        margin-bottom: 10px;
    }

    .contact-stat strong {
        font-size: 13px;
        word-wrap: break-word;
    }
    .tooltip{
        word-wrap: break-word;
    }

    .form-group span.cont {
        padding-top: 7px;
        margin-bottom: 0;
        display: block;
        font-size: 14px;
        color: #555;
    }

    pre {
        padding: 0px;
        border: none;
        background-color: transparent;
    }

    .hljs, .CodeMirror-code {
        font-size: 14px !important;
        font-family: Courier, Courier New;
    }
    .attchmentBody{
        padding: 0 !important;
        height: 160px;
    }
    .attchmentBody .imgBox {
        display: block;
        height: 130px;
        position: relative;
        box-sizing: border-box;
        padding: 5px 0;
    }

    .attchmentBody .imgBox img,.attchmentBody .imgBox .file-title {
        max-width: 100%;
        max-height: 100%;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    .attchmentBody .imgBox .file-title{
        width: 100%;
        padding: 0 15px;
        text-align: center;

    }

    .attchmentBody .imgBox .file-title:hover {
        text-decoration: underline;
    }
    .attchmentBody .description-edit {
        box-sizing: border-box;
        padding: 0 15px 10px 15px;
        display: block;
        width: 100%;
        height: 30px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;

    }

    #side-menu li a i {
        margin-right: 4px;
    }

    #side-menu li a {
        padding: 15px 20px 15px 10px;
    }

    .profile-picture i {
        margin-right: 10px;
    }

    #menu {
        background-color: #fff;
    }

    .new-dropdown {
        min-width: 100px;
    }

    .new-dropdown li i {
        margin-right: 6px;
    }

    .mobile-navbar .navbar-nav > li, .mobile-navbar .navbar-nav > li > a {
        height: 36px;
        line-height: 36px;
    }

    .mobile-navbar .navbar-nav > li > a {
        padding: 0 15px;
        font-size: 13px;
    }

    .dropdown-link {
        height: 56px !important;
        line-height: 56px !important;
        padding: 0 20px !important;
        display: block;
    }

    .hpanel {
        margin-bottom: 12px;
    }

    .select2-container .select2-choice {
        font-size: 14px;
        font-family: inherit;
    }

    .select2-search input {
        font-family: inherit;
    }

    .hr-line-dashed {
        margin: 15px 0;
    }

    .content {
        padding: 10px 40px 16px 40px;
    }

    .fixed-small-header .content {
        padding: 60px 40px;
    }

    .content.active {
        padding: 60px 40px 80px 40px !important;
    }

    .

    @media (max-width: 720px) {
        .content.active {
            padding: 140px 40px 80px 40px !important;
        }
    }

    #logo {
        padding: 18px;
    }

    .vertical-timeline-content h2 {
        font-size: 15px !important;
    }

    .vertical-timeline-content h2 span {
        font-size: 13px !important;
    }

    .modal textarea, .textarea, textarea {
        resize: vertical;
    }

    .form-group input[type='submit'] {
        width: 100%;
    }

    .form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control {
        background-color: transparent;
        opacity: 1;
        border: none;
        color: black;
    }

    .form-control[disabled], fieldset[disabled] .form-control {
        cursor: default;
    }

    .form-horizontal .control-label {
        padding-top: 8px;
    }

    .daily-b {
        cursor: pointer;
        border: 1px solid #e4e5e7 !important;
    }

    .alert {
        margin-bottom: 10px;
    }

    .comment-area {
        margin: 0;
        line-height: 22px;
        font-size: 13px;
        word-wrap: break-word;
        word-break: break-all;
    }

    .footer.bg-success {
        background-color: #dff0d8;
    }

    .href-link .page-scroll {
        font-weight: 600;
        font-size: 14px;
        padding: 0 10px;
        color: #455463;
    }

    .nav.navbar-nav li a {
        color: #666;
    }

    .href-link li.active .page-scroll {
        color: #5bc0de;
    }

    .href-link .nav li {
        float: left;
    }

    .href-link .navbar-nav > li > a:hover,
    .href-link .navbar-nav > li > a:focus {
        background-color: #fff;
        border-bottom: none;
        color: #5bc0de;
    }

    .text-comment {
        overflow: auto;
        max-height: 120px;
        line-height: 20px;
        margin-bottom: 0;
    }

    .panel-body.active {
        border: none;
    }

    .document-body {
        min-height: 200px;
        max-height: 500px;
        overflow: scroll;
    }

    .note-editable.panel-body {
        border-top: 1px solid #e4e5e7 !important;
    }

    .footBtn {
        display: inline-block;
        padding: 0 5px
    }

    .footBtn select {
        height: 30px;
    }

    table.text-center th {
        text-align: center;
    }

    .collateral .item p {
        padding: 10px 10px 0;
    }

    .fixed-small-header .small-header h2 .btn {
        margin-top: -2px;
    }

    .failReason {
        margin-left: 10px;
        font-weight: normal;
    }

    .productInterest .border-b:nth-child(n+7) {
        border-bottom: none;
    }

    .seach-group .col-md-3 {
        margin-bottom: 6px;
    }

    #s2id_actualLendingDate_year, #s2id_actualLendingDate_month, #s2id_actualLendingDate_day {
        width: 100px;
    }

    .checkbox input[type="checkbox"] {
        opacity: 1;
    }

    .label {
        padding: .2em .6em .2em;
    }

    .inlineBlock {
        display: inline-block;
    }

    .input-group[class*="col-"] {
        float: left;
        padding-right: 15px;
        padding-left: 15px;
    }

    .pl12 {
        padding-left: 12px;
    }



    </style>

</head>

<body class="fixed-navbar fixed-sidebar fixed-footer">

<div class="splash"><div class="color-line"></div>

    <div class="splash-title"><h1>中佳信LMS</h1>

        <div class="spinner"><div class="rect1"></div>

            <div class="rect2"></div>

            <div class="rect3"></div>

            <div class="rect4"></div>

            <div class="rect5"></div>
        </div>
    </div>
</div>

<div id="header">
    <div class="color-line">
    </div>

    <div id="logo" class="light-version">
        <span>

            <a href="/welcome">中佳信</a>
        </span>
    </div>
    <nav role="navigation">
        <div class="header-link hide-menu"><i class="fa fa-bars"></i></div>

        <div class="small-logo">
            <span class="text-primary">中佳信</span>
        </div>

        <div class="mobile-menu">
            <button type="button" class="navbar-toggle mobile-menu-toggle" data-toggle="collapse" style="margin-top:0"
                    data-target="#mobile-collapse">
                <sec:ifLoggedIn>
                    <asset:image src="defaultAvater.png" class="img-circle" alt="logo" width="32" height="32"/><b
                        class="caret"></b>
                </sec:ifLoggedIn>
            </button>

            <div class="collapse mobile-navbar" id="mobile-collapse">
                <ul class="nav navbar-nav new-dropdown">
                    %{--<li><a href="#"><i class="fa fa-edit"></i>更换头像</a></li>--}%
                    <li>
                        <g:link controller="user" action="changePassword">
                            <i class="fa fa-gear"></i>修改口令
                        </g:link>
                    </li>
                    <li>
                        <g:link controller="logoff">
                            <i class="fa fa-sign-out"></i>退出</g:link>
                    </li>
                </ul>
            </div>
        </div>

        <div class="navbar-right" id="header-right" style="margin-right: 20px;">

            <ul class="nav navbar-nav no-borders">
                <sec:ifLoggedIn>
                    %{-- <sec:ifNotGranted roles="ROLE_ADMINISTRATOR, ROLE_GENERAL_MANAGER, ROLE_COO ">
                        <li class="dropdown">
                            <a class="dropdown-toggle label-menu-corner" href="#" data-toggle="dropdown">
                                <i class="fa fa-envelope-o"></i>
                                <span class="label label-info">${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id and opportunity.status = 'Pending'", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id])?.size()}</span>
                            </a>
                            <ul class="dropdown-menu hdropdown animated flipInX">
                                <div class="title">
                                    您有<b
                                        class="text-info">${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id and opportunity.status = 'Pending'", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id])?.size()}</b>个代办事项

                                </div>
                                <g:each in="${com.next.OpportunityStage.list()}">
                                    <g:if test="${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id and stage.id = ? and opportunity.status = 'Pending'", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id, it?.id])?.size() > 0}">
                                        <li>
                                            <g:link controller="OpportunityRole" action="searchOpportunity"
                                                    params="[stage: it?.name]">
                                                ${it?.name} (${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id and stage.id = ? and opportunity.status = 'Pending'", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id, it?.id])?.size()})
                                            </g:link>
                                        </li>
                                    </g:if>
                                </g:each>
                            </ul>
                        </li>
                    </sec:ifNotGranted> --}%
                    <li class="dropdown">
                        <a class="dropdown-toggle dropdown-link" href="#" data-toggle="dropdown">
                            <asset:image src="defaultAvater.png" class="img-circle" alt="logo" width="32" height="32"/>
                            <span style="font-size: 16px"><sec:loggedInUserInfo field="username"/></span><b
                                style="margin-top: 3px;" class="caret"></b>
                        </a>
                        <ul class="dropdown-menu new-dropdown hdropdown animated flipInX">
                            %{--<li><a href="#"><i class="fa fa-lg fa-edit"></i>更换头像</a></li>--}%
                            <li>
                                <g:link controller="user" action="changePassword">
                                    <i class="fa fa-lg fa-gear"></i>修改口令
                                </g:link>
                            </li>
                            <li>
                                <g:link controller="logoff">
                                    <i class="fa fa-lg fa-sign-out"></i>退出</g:link>
                            </li>
                        </ul>
                    </li>

                </sec:ifLoggedIn>
            </ul>
        </div>

    </nav>
</div>
<!-- Navigation -->
<sec:ifLoggedIn>

    <aside id="menu">
        <!-- <div id="navigation">
      <ul class="nav" id="side-menu">
        <g:each in="${com.next.UserRole.executeQuery("select distinct item.linkUrl, item.icon, item.name, item.displayOrder from UserRole userRole join userRole.user user join userRole.role role join role.menu menu left join menu.items item where user.username = ? order by item.displayOrder asc", [sec.loggedInUserInfo(field: 'username')])}">
            <li>
              <a href="${it[0]}">
              <span class="nav-label">
                <i class="${it[1]}"></i>
            ${it[2]}
            </span>
          </a>
        </li>
        </g:each>
      </ul>
    </div> -->
        <div id="navigation">
            <ul class="nav" id="side-menu">
                <g:if test="${sec.loggedInUserInfo(field: 'username') in ["rongshulms","zijinlms"]}">
                    <li>
                        <g:link controller="opportunityStatistics" action="rongshuLoanInfo">
                            <i class="fa-lg fa-fw glyphicon glyphicon-euro" aria-hidden="true"></i>融数审批列表
                        </g:link>
                    </li>
                </g:if>
                <g:if test="${sec.loggedInUserInfo(field: 'username') in ["huaronglms","zijinlms"]}">
                    <li>
                        <g:link controller="opportunityStatistics" action="huarongLoanInfo">
                            <i class="fa-lg fa-fw glyphicon glyphicon-euro" aria-hidden="true"></i>华融审批列表
                        </g:link>
                    </li>
                </g:if>
                <g:if test="${sec.loggedInUserInfo(field: 'username')!="rongshulms"&&sec.loggedInUserInfo(field: 'username')!="zijinlms"&&sec.loggedInUserInfo(field: 'username')!="huaronglms"}">
                    <sec:ifAllGranted roles="ROLE_INVESTOR">
                        <li>
                            <g:link controller="opportunity" action="indexByInvestor">
                                <i class="fa-lg fa-fw fa fa-user-circle-o"></i>查找
                            </g:link>
                        </li>
                    </sec:ifAllGranted>
                    <sec:ifAnyGranted
                            roles="ROLE_ADMINISTRATOR, ROLE_ACCOUNT_MANAGER, ROLE_CUSTOMER_SERVICE_REPRESENTATIVE, ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER, ROLE_BRANCH_OFFICE_RISK_MANAGER, ROLE_HEAD_OFFICE_RISK_MANAGER, ROLE_GENERAL_RISK_MANAGER, ROLE_BRANCH_OFFICE_CASHIER, ROLE_BRANCH_GENERAL_MANAGER, ROLE_CONDITION_RULEENGINE, ROLE_CRO, ROLE_CEO, ROLE_GENERAL_MANAGER, ROLE_COO , ROLE_COMPLIANCE_MANAGER, ROLE_POST_LOAN_MANAGER, ROLE_EVENT_CONFIGURATION">
                        <li>
                            <a href="#">
                                <span class="nav-label ">
                                    <i class="fa fa-lg fa-fw fa-indent"></i>订单</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <g:link controller="opportunityTeam" action="searchOpportunity"
                                            params="[search: 'false']">
                                        查找
                                    </g:link>
                                </li>
                                <sec:ifAnyGranted
                                        roles="ROLE_ACCOUNT_MANAGER, ROLE_CUSTOMER_SERVICE_REPRESENTATIVE, ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER, ROLE_BRANCH_OFFICE_RISK_MANAGER, ROLE_HEAD_OFFICE_RISK_MANAGER, ROLE_GENERAL_RISK_MANAGER, ROLE_BRANCH_OFFICE_CASHIER, ROLE_BRANCH_GENERAL_MANAGER, ROLE_CONDITION_RULEENGINE, ROLE_CRO, ROLE_CEO, ROLE_GENERAL_MANAGER, ROLE_COO , ROLE_COMPLIANCE_MANAGER, ROLE_POST_LOAN_MANAGER, ROLE_EVENT_CONFIGURATION">
                                    <li>
                                        <g:link controller="opportunityRole" action="index">
                                            待办
                                        </g:link>
                                    </li>
                                    <g:if test="${com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username')).department?.name == "投资理财部"}">
                                        <li>
                                            <g:link controller="opportunityRole" action="index"
                                                    params="[prepareCity: 'shanghai']">
                                                上海区待办
                                            </g:link>
                                        </li>
                                    </g:if>
                                    <li>
                                        <g:link controller="opportunityRole" action="index" params="[status: 'already']">
                                            已办理
                                        </g:link>
                                    </li>
                                    <li>
                                        <g:link controller="opportunityTeam" action="index" params="[status: 'Failed']">
                                            已失败
                                        </g:link>
                                    </li>

                                </sec:ifAnyGranted>

                                <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR">
                                    <g:each in="${com.next.OpportunityStage.findAllByActive(true)}">
                                        <li>
                                            <g:link controller="opportunityTeam" action="index"
                                                    params="[stage: it.code, status: 'Pending']">
                                                ${it?.name}
                                            </g:link>
                                        </li>
                                    </g:each>
                                    <li>
                                        <g:link controller="opportunityTeam" action="index" params="[status: 'Completed']">
                                            已完成
                                        </g:link>
                                    </li>
                                    <li>
                                        <g:link controller="opportunityTeam" action="index" params="[status: 'Failed']">
                                            已失败
                                        </g:link>
                                    </li>
                                </sec:ifAnyGranted>
                            </ul>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_COO">
                        <li>
                            <g:link controller="collateral" action="index">
                                <i class="fa-lg fa-fw fa fa-university" aria-hidden="true"></i>房产
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="opportunityStatistics" action="rongshuLoanInfo">
                                <i class="fa-lg fa-fw glyphicon glyphicon-euro" aria-hidden="true"></i>融数审批列表
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="opportunityStatistics" action="huarongLoanInfo">
                                <i class="fa-lg fa-fw glyphicon glyphicon-euro" aria-hidden="true"></i>华融审批列表
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted
                            roles="ROLE_ADMINISTRATOR, ROLE_ACCOUNT_MANAGER, ROLE_CUSTOMER_SERVICE_REPRESENTATIVE, ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER, ROLE_BRANCH_OFFICE_RISK_MANAGER, ROLE_HEAD_OFFICE_RISK_MANAGER, ROLE_GENERAL_RISK_MANAGER, ROLE_BRANCH_OFFICE_CASHIER, ROLE_BRANCH_GENERAL_MANAGER, ROLE_CONDITION_RULEENGINE, ROLE_CRO, ROLE_CEO, ROLE_GENERAL_MANAGER, ROLE_COO , ROLE_COMPLIANCE_MANAGER, ROLE_POST_LOAN_MANAGER, ROLE_EVENT_CONFIGURATION">
                        <li>
                            <g:link controller="contact" action="indexByClient">
                                <i class="fa-lg fa-fw fa fa-user-circle-o"></i>贷款人
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="contact" action="indexByAgent">
                                <i class="fa fa-lg fa-fw fa-user-o"></i>经纪人
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted
                            roles="ROLE_ADMINISTRATOR, ROLE_BRANCH_OFFICE_CASHIER, ROLE_BRANCH_GENERAL_MANAGER, ROLE_GENERAL_MANAGER, ROLE_COO ">
                        <li>
                            <g:link controller="contactTeam" action="index">
                                <i class="fa fa-users" aria-hidden="true"></i>团队经纪人
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted
                            roles="ROLE_ADMINISTRATOR, ROLE_ACCOUNT_MANAGER, ROLE_CUSTOMER_SERVICE_REPRESENTATIVE, ROLE_BRANCH_OFFICE_RISK_MANAGER, ROLE_HEAD_OFFICE_RISK_MANAGER, ROLE_GENERAL_RISK_MANAGER, ROLE_BRANCH_OFFICE_CASHIER, ROLE_BRANCH_GENERAL_MANAGER">
                        <li>
                            <g:link controller="activity" action="index">
                                <i class="fa fa-lg fa-fw fa-calendar-check-o"></i>活动管理
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_INVESTOR">
                        <li>
                            <g:link controller="assetPool" action="index">
                                <i class="fa fa-lg fa-fw fa-sitemap"></i>资产池
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>

                    <sec:ifAnyGranted
                            roles="ROLE_ADMINISTRATOR, ROLE_COO, ROLE_BRANCH_OFFICE_CASHIER, ROLE_BRANCH_GENERAL_MANAGER">
                        <g:if test="${com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username')).department?.name != "投资理财部"}">
                            <li>
                                <a href="#">
                                    <span class="nav-label ">
                                        <i class="fa fa-lg fa-fw fa-usd"></i>佣金返费</span>
                                    <span class="fa arrow"></span>
                                </a>
                                <ul class="nav nav-second-level">
                                    <sec:ifAnyGranted roles="ROLE_BRANCH_GENERAL_MANAGER, ROLE_BRANCH_OFFICE_CASHIER">
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportCommissionInfo3">
                                                佣金基础信息
                                            </g:link>
                                        </li>
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportCommissionInfo4">
                                                返费基础信息
                                            </g:link>
                                        </li>
                                        <sec:ifAnyGranted roles="ROLE_BRANCH_GENERAL_MANAGER">
                                            <li>
                                                <g:link controller="opportunityStatistics" action="exportLoanInfo1">
                                                    放款基础信息
                                                </g:link>
                                            </li>
                                        </sec:ifAnyGranted>
                                        <sec:ifAnyGranted roles="ROLE_BRANCH_OFFICE_CASHIER">
                                            <li>
                                                <g:link controller="opportunityStatistics" action="exportLoanInfo4"
                                                        params="[flag: 'AM']">
                                                    今日一批次放款列表
                                                </g:link>
                                            </li>
                                            <li>
                                                <g:link controller="opportunityStatistics" action="exportLoanInfo4"
                                                        params="[flag: 'PM']">
                                                    今日二批次放款列表
                                                </g:link>
                                            </li>
                                        </sec:ifAnyGranted>
                                    </sec:ifAnyGranted>

                                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_COO">
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportCommissionInfo">
                                                佣金基础信息
                                            </g:link>
                                        </li>
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportCommissionInfo2">
                                                返费基础信息
                                            </g:link>
                                        </li>
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportloanApproval">
                                                审批基础信息
                                            </g:link>
                                        </li>
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportLoanInfo">
                                                放款基础信息
                                            </g:link>
                                        </li>
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportLoanInfo2"
                                                    params="[flag: 'AM']">
                                                今日一批次放款列表
                                            </g:link>
                                        </li>
                                        <li>
                                            <g:link controller="opportunityStatistics" action="exportLoanInfo2"
                                                    params="[flag: 'PM']">
                                                今日二批次放款列表
                                            </g:link>
                                        </li>
                                    </sec:ifAnyGranted>
                                </ul>
                            </li>
                        </g:if>
                    </sec:ifAnyGranted>

                    <g:if test="${com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username')).department?.name == "投资理财部"}">
                        <li>
                            <a href="#">
                                <span class="nav-label ">
                                    <i class="fa fa-lg fa-fw fa-usd"></i>佣金返费</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <g:link controller="opportunityStatistics" action="exportCommissionInfo">
                                        佣金基础信息
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="exportCommissionInfo2">
                                        返费基础信息
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="exportLoanInfo">
                                        放款基础信息
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="exportLoanInfo2"
                                            params="[flag: 'AM']">
                                        今日一批次放款列表
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="exportLoanInfo2"
                                            params="[flag: 'PM']">
                                        今日二批次放款列表
                                    </g:link>
                                </li>
                            </ul>
                        </li>
                    </g:if>

                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_COO, ROLE_BRANCH_OFFICE_POST_LOAN_MANAGER">
                        <li>
                            <a href="#">
                                <span class="nav-label ">
                                    <i class="fa fa-lg fa-fw fa-book"></i>贷后台账</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <g:link controller="opportunityStatistics" action="loanReport">
                                        放款台账
                                    </g:link>
                                </li>
                            </ul>
                        </li>
                    </sec:ifAnyGranted>

                    <sec:ifAnyGranted
                            roles="ROLE_ADMINISTRATOR, ROLE_HEAD_OFFICE_RISK_MANAGER, ROLE_GENERAL_RISK_MANAGER, ROLE_BRANCH_GENERAL_MANAGER, ROLE_CRO, ROLE_CEO, ROLE_GENERAL_MANAGER, ROLE_COO , ROLE_COMPLIANCE_MANAGER">
                        <li>
                            <a href="#">
                                <span class="nav-label ">
                                    <i class="fa fa-lg fa-fw fa-book"></i>台账管理</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReportList">
                                        台账列表
                                    </g:link>
                                </li>
                                %{--<li>
                                  <g:link controller="opportunityStatistics" action="dailyReport">
                                    台账图表
                                  </g:link>
                                </li>--}%
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReportCount">
                                        规模统计
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReport1">
                                        订单阶段时间查询
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReport6">
                                        放款台账
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReport7">
                                        签约台账
                                    </g:link>
                                </li>
                                <g:if test="${com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username')).department?.name == "投资理财部"}">
                                    <li>
                                        <g:link controller="opportunityStatistics" action="dailyReport2">
                                            资金部需求统计
                                        </g:link>
                                    </li>
                                    <li>
                                        <g:link controller="opportunityStatistics" action="dailyReport5">
                                            资金部需求统计2
                                        </g:link>
                                    </li>
                                </g:if>
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReport4">
                                        审批台账导出
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStatistics" action="dailyReport3">
                                        客服部报表导出
                                    </g:link>
                                </li>
                            </ul>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_PRODUCT_ADMINISTRATOR">
                        <li>
                            <g:link controller="product" action="index">
                                <i class="fa fa-lg fa-fw fa-product-hunt"></i>产品管理
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_HR_MANAGER">
                        <li>
                            <g:link controller="userTeam" action="index">
                                <i class="fa fa-lg fa-fw fa-user-circle"></i>人员管理
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                    <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                        <li>
                            <g:link controller="user" action="index">
                                <i class="fa fa-lg fa-fw fa-user"></i>用户管理
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="loginHistory" action="index">
                                <i class="fa fa-lg fa-fw fa-history"></i>登录明细
                            </g:link>
                        </li>
                        <li>
                            <a href="#">
                                <span class="nav-label ">
                                    <i class="fa fa-lg fa-fw fa-table"></i>区域管理</span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <g:link controller="territory" action="index">
                                        区域
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="account" action="index">
                                        机构
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="city" action="index">
                                        城市
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="district" action="index">
                                        城区
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="address" action="index">
                                        单位
                                    </g:link>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#">
                                <span class="nav-label">
                                    <i class="fa fa-lg fa-fw fa-gear"></i>系统管理</span>
                                <span class="fa arrow"></span>
                            </a>

                            <ul class="nav nav-second-level">

                                <li>
                                    <g:link controller="causeOfFailure" action="index">
                                        失败原因
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStage" action="index">
                                        订单阶段
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityStageCategory" action="index">
                                        订单阶段分类
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="interestPaymentMethod" action="index">
                                        还款方式
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="addressType" action="index">
                                        单位类别
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="messageTemplate" action="index">
                                        消息推送
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="houseType" action="index">
                                        物业类型
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="typeOfFirstMortgage" action="index">
                                        一抵类型
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="specialFactors" action="index">
                                        特殊因素
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="creditReportProvider" action="index">
                                        征信配置
                                    </g:link>
                                </li>
                                %{--<li>
                                  <g:link controller="liquidityRiskReportTemplate" action="index">
                                    产调结果
                                  </g:link>
                                </li>--}%

                                <li>
                                    <g:link controller="role" action="index">
                                        角色
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="department" action="index">
                                        部门
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="position" action="index">
                                        岗位
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="message" action="index">
                                        消息
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="accountContact" action="index">
                                        离职记录
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="SecurityProfile" action="index">
                                        权限
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="bank" action="index">
                                        银行
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityType" action="index">
                                        订单类型
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityLayout" action="index">
                                        布局
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="opportunityCsvFile" action="index">
                                        存量订单导入
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="document" action="index">
                                        帮助文档
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="externalDataMapping" action="index">外部数据映射</g:link>
                                </li>
                                <li>
                                    <g:link controller="ExternalDataReceiver" action="index">外部数据接收</g:link>
                                </li>
                                <li>
                                    <g:link controller="contractType" action="index">合同类型</g:link>
                                </li>
                                <li>
                                    <g:link controller="contactLevel" action="index">
                                        客户级别
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="timingTask" action="index">
                                        定时任务
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="Menu" action="index">
                                        菜单
                                    </g:link>
                                </li>

                            </ul>
                        </li>
                        <li>
                            <g:link controller="leads" action="index">
                                <i class="fa fa-lg fa-fw fa-info-circle"></i>匿名评房
                            </g:link>
                        </li>

                        <li>
                            <a href="#">
                                <span class="nav-label ">
                                    <i class="fa fa-lg fa-fw fa-cube"></i>App配置</span>
                                <span class="fa arrow"></span>
                            </a>

                            <ul class="nav nav-second-level">
                                <li>
                                    <g:link controller="AppVersion" action="index">
                                        App版本控制
                                    </g:link>
                                </li>
                                <li>
                                    <g:link controller="AppConfigurationKey" action="index">
                                        App邀请码
                                    </g:link>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <g:link controller="flexFieldCategory" action="index">
                                <i class="fa fa-lg fa-fw fa-pencil"></i>弹性域
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="AppVersion" action="touchDownload">
                                <i class="fa fa-lg fa-fw fa-qrcode"></i>APP二维码
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="transactionRecord" action="search">
                                <i class="fa fa-lg fa-fw fa-cny "></i>交易记录
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="billsImport" action="index">
                                <i class="fa fa-lg fa-fw fa-database"></i>银行回盘数据
                            </g:link>
                        </li>
                    </sec:ifAllGranted>
                    <sec:ifNotGranted roles="ROLE_ADMINISTRATOR">
                        <g:if test="${com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.department?.name == '贷后管理部' || com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.department?.name == '财务组' || com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.department?.name == '研发部'}">
                            <li>
                                <g:link controller="transactionRecord" action="search">
                                    <i class="fa fa-lg fa-fw fa-cny "></i>交易记录
                                </g:link>
                            </li>
                            <li>
                                <g:link controller="billsImport" action="index">
                                    <i class="fa fa-lg fa-fw fa-database "></i>银行回盘数据
                                </g:link>
                            </li>
                        </g:if>
                    </sec:ifNotGranted>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR, ROLE_CONDITION_RULEENGINE, ROLE_EVENT_CONFIGURATION">
                        <li>
                            <g:link controller="opportunityWorkflow" action="index">
                                <i class="fa fa-lg fa-fw fa-arrow-circle-o-down"></i>工作流
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="Workflow" action="index">
                                <i class="fa fa-lg fa-fw fa-arrow-circle-o-down"></i>工作流2.0
                            </g:link>
                        </li>
                        <li>
                            <g:link controller="component" action="index">
                                <i class="fa fa-lg fa-fw fa-life-ring"></i>组件
                            </g:link>
                        </li>
                    </sec:ifAnyGranted>
                </g:if>
            </ul>
        </div>
    </aside>
</sec:ifLoggedIn>

<div id="wrapper">

    <g:layoutBody/>
</div>

<div class="modal fade" id="myModaDocument" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="color-line"></div>

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"
                                                                                                  class="text-info">×</span>
                </button>
                <h4 class="modal-title">帮助文档</h4>
            </div>

            <div class="modal-body document-body" id="document-body">
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal" controller="opportunity" action="cancel"
                    id="${this.opportunity?.id}">
                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">失败原因</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">失败原因</label>

                        <div class="col-sm-10">
                            <g:select class="form-control" name="causeOfFailure"
                                      value="${this.opportunity?.causeOfFailure?.id}"
                                      noSelection="${['null': '无']}"
                                      from="${com.next.CauseOfFailure.list()}" optionKey="id"
                                      optionValue="name"></g:select>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">备注</label>

                        <div class="col-sm-10">
                            <g:textArea class="form-control" name="descriptionOfFailure" required=""
                                        value="${this.opportunity?.descriptionOfFailure}"
                                        rows="6"/>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </g:form>
        </div>
    </div>
</div>

<div class="modal fade" id="rejectReason" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal" controller="opportunity" action="reject">
                <input type="hidden" name="opportunity" value="${this.opportunity?.id}" id="opportunity">

                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">返回原因</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">返回原因</label>

                        <div class="col-sm-10">
                            <g:each in="${this.opportunityFlows}">
                                <g:if test="${it?.stage == this.opportunity?.stage}">
                                    <g:textArea class="form-control" name="comments" required=""
                                                value="${it?.comments}"
                                                rows="6"/>
                                </g:if>
                            </g:each>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </g:form>
        </div>
    </div>
</div>

<div class="modal fade" id="rejectReason2" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal" controller="opportunity" action="ajaxReject">
                <input type="hidden" name="opportunity" value="${this.opportunity?.id}" id="opportunity">

                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">返回原因</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">返回原因</label>

                        <div class="col-sm-10">
                            <g:each in="${this.opportunityFlows}">
                                <g:if test="${it?.stage == this.opportunity?.stage}">
                                    <g:textArea class="form-control" name="comments" required="" id="comments"
                                                value="${it?.comments}"
                                                rows="6"/>
                                </g:if>
                            </g:each>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary rejectBtn">保存</button>
                </div>
            </g:form>
        </div>
    </div>
</div>

<div class="modal fade" id="isJieqing" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal" >
                <input type="hidden" name="opportunity" value="${this.opportunity?.id}" id="opportunity">

                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">结清状态</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否结清</label>

                        <div class="col-sm-10">
                            <g:select class="form-control " name="jqStatus" id="jqStatus"
                                      from="${["请选择", "手动结清"] }"
                                      valueMessagePrefix="jqStatus" value="${this?.opportunity?.jqStatus}"/>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary jqSave" data-dismiss="modal">保存</button>
                </div>
            </g:form>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form class="form-horizontal approveForm" controller="opportunity" id="${this.opportunity?.id}"
                    action="approve">
                <input type="hidden" name="nextStage" value="" id="nextStage">
                <input type="hidden" name="nextOperator" value="" id="nextOperator">

                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">提交下一步</h4>
                </div>

                <div class="modal-body form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">下一阶段名称：</label>

                        <div class="col-sm-9">
                            <span id="nextStepInfo" class="cont"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">下一阶段操作人：</label>

                        <div class="col-sm-9">
                            <span id="nextOperatorInfo" class="cont"></span>
                        </div>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary">提交</button>
                </div>
            </g:form>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="applyForAmendment" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <g:form controller="collateral" id="${this.opportunity?.id}" action="modifyCollateralInformation">
                <div class="color-line"></div>

                <div class="modal-header">
                    <h4 class="modal-title">申请修改房产信息</h4>
                </div>

                <div class="modal-body">
                    <div class="form-group">
                        <label>请输入修改信息和修改原因</label>
                        <g:textArea class="textarea form-control" rows="5" name="applyReasonAndContent" value=""
                                    id="applyReasonAndContent"/>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </g:form>
        </div>
    </div>
</div>
<asset:javascript src="homer/vendor/jquery-ui/jquery-ui.min.js"/>
<asset:javascript src="homer/vendor/bootstrap/dist/js/bootstrap.min.js"/>
<asset:javascript src="homer/vendor/jquery-validation/jquery.validate.min.js"/>
<asset:javascript src="homer/vendor/metisMenu/dist/metisMenu.min.js"/>
<asset:javascript src="homer/vendor/sweetalert/lib/sweet-alert.min.js"/>
<asset:javascript src="homer/vendor/select2-3.5.2/select2.min.js"/>
<asset:javascript src="homer/vendor/select2-3.5.2/select2_locale_zh-CN.js"/>
<asset:javascript src="homer/vendor/iCheck/icheck.min.js"/>
<asset:javascript src="homer/vendor/ladda/spin.min.js"/>
<asset:javascript src="homer/vendor/ladda/ladda.min.js"/>
<asset:javascript src="homer/vendor/ladda/ladda.jquery.min.js"/>
<asset:javascript src="homer/homer.js"/>
<asset:javascript src="highlight.pack.js"/>
<asset:javascript src="homer/vendor/datetimepicker/bootstrap-datetimepicker.min.js"/>
<asset:javascript src="homer/vendor/datetimepicker/bootstrap-datetimepicker.zh-CN.js"/>
<asset:javascript src="homer/vendor/summernote/summernote.min.js"/>
<asset:javascript src="homer/vendor/bootstrap-touchspin/jquery.bootstrap-touchspin.min.js"/>
<script>
    $(function () {
        //获取房产价格
        $.each($(".collateralsExternalId"), function (i, obj) {
            var externalId = $(obj).text().trim();
            if (externalId) {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/collateral/queryValuationReliability",
                    data: {
                        externalId: $(obj).text().trim()
                    },
                    success: function (data) {
                        if (data.status == "success") {
                            var unitPrice = data.pvCollateral.unitPrice;
                            var valuationReliability = data.pvCollateral.valuationReliability;
                            var area = $(obj).nextAll(".area").text().trim();
                            var pvTotalPrice = unitPrice * area / 10000;
                            $(obj).nextAll(".latestCollateralPrice").text(pvTotalPrice.toFixed(2));
                            $(obj).nextAll(".valuationReliability").text(valuationReliability);

                        }
                    },
                });
            }

        });


        function formatCellphone(cellphone) {
            if (typeof cellphone == 'number') {
                cellphone = cellphone.toString();
            }
            return cellphone.substr(0, 3) + '****' + cellphone.substr(7, 11);
        }

        $.each($(".cellphoneFormat"), function (i, obj) {
            if ($(obj).text().trim()) {
                $(obj).text(formatCellphone($(obj).text().trim()));
            }
        });
        $.each($(".cellphoneFormat2"), function (i, obj) {
            if ($(obj).val().trim()) {
                $(obj).val(formatCellphone($(obj).val().trim()));
            }

        });
        var selectRole = $(".selectRole");
        for (var i = 0; i < selectRole.length; i++) {
            var n = selectRole.eq(i).children("option").length;
            if (n <= 1) {
                selectRole.eq(i).addClass("hide");
            }
        }
        $(".nextStepBtn").click(function () {
            var nextStepBtnText = $(this).val();
            $("#nextStage").val(nextStepBtnText);
            var nextOperatorBtnText = $(this).prevAll(".selectRole").val();

            $("#nextOperator").val(nextOperatorBtnText);
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "/opportunity/prepareApprove",
                data: {
                    opportunityId: $("#opportunityId").val(),
                    nextStageId: $(this).val(),
                    nextOperatorId: $(this).prevAll(".selectRole").val()
                },

                success: function (data) {
                    if (data.status == "success") {
                        $("#nextStepInfo").text(data.nextStep);
                        $("#nextOperatorInfo").text(data.nextOperator);
                        $(".approveForm").submit();
                    }
                    else {
                        swal(data.errMsg, "", "error");
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        });

        $(".nextStepBtn2").click(function () {
            var nextStage = $(this).val();
            var nextOperator = $(this).prevAll(".selectRole").val();
            /*var name = $(this).prevAll(".selectRole").children(["option"]).html();
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "/opportunity/nextOperator",
                data: {
                    opportunityId: $("#opportunityId").val()
                },
                success: function (data) {
                    if (data.status == "success") {
                        name = data.nextOperator;*/
                        swal({
                                title: "是否确认提交",
                                type: "warning",
                                showCancelButton: true,
                                showConfirmButton: false,
                                confirmButtonColor: "#3aa9dd",
                                confirmButtonText: "确认",
                                closeOnConfirm: false,
                                cancelButtonText: "取消",
                                showLoaderOnConfirm:true,
                            },
                            function(isConfirm){
                                if (isConfirm) {
                                    var laddaBtn = document.querySelector('button.ladda-button');
                                    if (laddaBtn) {
                                        var spinner = Ladda.create(laddaBtn);
                                    }
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: "/opportunity/ajaxApprove",
                                        data: {
                                            opportunityId: $("#opportunityId").val(),
                                            nextStage: nextStage,
                                            nextOperator: nextOperator
                                        },
                                        beforeSend: function () {
                                            if (laddaBtn && spinner) {
                                                spinner.start();
                                            }

                                        },
                                        success: function (data) {
                                            if (data.status == "success") {
                                                if (laddaBtn && spinner) {
                                                    spinner.stop();
                                                }
                                                swal("提交成功", "下一阶段责任人： " + data.nextOperator, "success");
                                                $(".sweet-alert .confirm").click(function () {
                                                    window.history.go(0);
                                                })

                                            }
                                            else {
                                                if (laddaBtn && spinner) {
                                                    spinner.stop();
                                                }
                                                swal(data.errMsg, "", "error");
                                            }
                                        },
                                        error: function () {
                                            if (laddaBtn && spinner) {
                                                spinner.stop();
                                            }
                                            swal("获取失败，请稍后重试", "", "error");
                                        }
                                    });
                                }

                                /*function(){
                                 swal("回退成功!", "订单回退成功", "success");
                                 $("#rollbackOpportunityStage").submit();*/
                            });
                    /*}
                    else if (date.status == "fail"){
                        swal(data.errMsg, "", "error");
                    }
                    else{
                        swal("提示", "没有获取到下一阶段操作人", "error");
                    }
                }
            });*/


        });

        $(".rejectBtn").click(function () {

            $.ajax({
                type: "POST",
                dataType: "json",
                url: $(this).closest("form").attr("action"),
                data: {
                    opportunityId: $("#opportunityId").val(),
                    comments: $(this).closest("form").find("#comments").val()
                },

                success: function (data) {
                    if (data.status == "success") {

                        swal("提交成功", "上一阶段责任人： " + data.foreOperator, "success");
                        $(".sweet-alert .confirm").click(function () {
                            window.history.go(0);
                        })

                    }
                    else {
                        swal("回退失败", data.errMsg, "error");
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        });

//        帮助文档
        var text = $("#document").val();
        $("#document-body").html(text);
//        折叠
        $(".collapsed .showhide").click(function () {
            $(this).parent().parent().toggleClass("hbuilt");
            $(this).parent().parent().parent().toggleClass("panel-collapse");
        });
        $("select").select2();
//        代码高亮
        hljs.initHighlightingOnLoad();
//删除数据
        $(document).delegate(".deleteBtn", "click", function () {
            var currentForm = $(this).parent("form");
            swal({
                    title: "您确认删除该数据吗?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    cancelButtonText: "取消",
                    confirmButtonText: "删除",
                    closeOnConfirm: false,
                    closeOnCancel: true

                },
                function (isConfirm) {
                    if (isConfirm) {
                        currentForm.submit();


                    }
                });

        });
    });
    //    日期插件
    $('.form_datetime').datetimepicker({
        minView: "day", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd hh:00:00", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    });
    $('.form_datetime3').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    });
    $('.form_datetime2').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd 00:00:00", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    });
    $('.form_endtime').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd 23:59:59", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true //选择日期后自动关闭
    });
    $('a.page-scroll').bind('click', function (event) {
        var link = $(this);
        $('html, body').stop().animate({
            scrollTop: $(link.attr('href')).offset().top - 115
        }, 500);
        event.preventDefault();
    });
    $('body').scrollspy({
        target: '#navbar-example',
        offset: 125
    });
    //房产
    $.each($(".collateral .collateralOrder"), function (i, obj) {
        $(obj).text(i + 1);
    });
    var status = $(".collateralStatus").text();
    $.each($(".collateral .collateralStatus"), function (i, obj) {
        var status = $(obj).text();
        if (status == "Failed") {
            $(obj).removeClass("text-info").addClass("text-danger");
        }
    });
    //订单审批意见隐藏
    if ($(".commentsContent").children().length < 1) {
        $(".commentsContent").closest(".commentsRow").remove();
    }
    $(".table-responsive").parent(".panel-body").addClass("active");
    //执行顺序
    $("input[name='executeSequence']").TouchSpin({
        verticalbuttons: true,
        min: -100000,
    });
    $("input[name='executionSequence']").TouchSpin({
        verticalbuttons: true,
        min: 1,
        max: 10000
    });
    //还款计划修改
    $(".billsItemsChangeStatus").click(function () {
        var billItemId = $(this).parent().prevAll("input[name='billItemId']").val();
        var dataName = $(this).attr("name");
        $.ajax({
            type: "POST",
            data: {
                name: dataName,
                dataName: $(this)[0].checked,
                billItemId: billItemId
            },
            cache: false,
            url: "/billsItem/updateStatus",
            dataType: "JSON",
            success: function (data) {
                if (data.status == "success") {
                    swal(data.msg, "", "success");
                } else {
                    swal(data.msg, "", "error");
                }
            },
            error: function () {
                swal("获取失败，请稍后重试", "", "error");
            }
        });

    });
    //结清状态编辑提交
    $(".jqSave").click(function () {
        var jqStatus = $("#jqStatus").val();
        $.ajax({
            type: "POST",
            data: {
                jqStatus: jqStatus,
                opportunityId: $("#opportunityId").val(),
            },
            cache: false,
            url: "/opportunity/editJqStatus",
            dataType: "JSON",
            success: function (data) {
                $("#isJieqing").attr("aria-hidden","true");
                if (data.status == "success") {
                    swal(data.message, data.message, "success");
                    $(".sweet-alert .confirm").click(function () {
                        window.history.go(0);
                    })
                } else {
                    swal(data.message, data.message, "error");
                }
            },
            error: function () {
                swal("获取失败，请稍后重试", "", "error");
            }
        });
    });
</script>

</body>
</html>
