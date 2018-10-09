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
    <asset:stylesheet src="homer/vendor/select2-3.5.2/select2.min.css"/>
    <asset:stylesheet src="homer/vendor/select2-3.5.2/select2-bootstrap.css"/>
    <asset:stylesheet src="homer/vendor/datetimepicker/bootstrap-datetimepicker.min.css"/>
    <asset:javascript src="homer/vendor/jquery/dist/jquery.min.js"/>
    <asset:stylesheet src="homer/vendor/viewer/viewer.min.css"/>

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
    }

    .form-group span.cont {
        padding-top: 7px;
        margin-bottom: 0;
        display: block;
        font-size: 14px;
        color: #555;
    }
    .imgBox a img {
        max-width: 100%;
        max-height: 100%;
    }

    .imgBox {
        text-align: center;
        padding: 0 !important;
    }

    .imgBox a {
        margin: 5px;
        display: inline-block;
        height: 320px;
    }

    .imgBox a h5:hover {
        text-decoration: underline;
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

    #logo {
        padding: 18px;
    }
    .modal textarea {
        resize: none;
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


    .alert {
        margin-bottom: 10px;
    }
    .secondNav li {
        float: left;
        height: 60px;
        width: 50%;
        border: none !important;
    }

    .secondNav li a {
        height: 60px;
        padding: 5px !important;
        text-align: center;
    }

    .secondNav li img {
        width: 100%;
        height: 100%;
    }
    .secondNav i{
        font-size: 30px;
        margin-right: 0 !important;
        margin-bottom: 2px;
    }
    #side-menu .secondNav:nth-child(1), #side-menu .secondNav:nth-child(2) {
        border-top: 1px solid #e4e5e7;
    }
    .checkBtn{
        font-weight: normal;
        display: block;
        font-size: 13px;
        color: #3498db;
        margin-top: 4px;
    }
    .checkBtn:hover{
        text-decoration: underline;
    }
    </style>

</head>

<body class="fixed-navbar fixed-sidebar">

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
            中佳信
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
                    <sec:ifNotGranted roles="ROLE_ADMINISTRATOR">
                        <li class="dropdown">
                            <a class="dropdown-toggle label-menu-corner" href="#" data-toggle="dropdown">
                                <i class="fa fa-envelope-o"></i>
                                <span class="label label-info">${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id])?.size()}</span>
                            </a>
                            <ul class="dropdown-menu hdropdown animated flipInX">
                                <div class="title">
                                    您有<b
                                        class="text-info">${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id])?.size()}</b>个代办事项

                                </div>
                                <g:each in="${com.next.OpportunityStage.list()}">
                                    <g:if test="${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id and stage.id = ?", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id, it?.id])?.size() > 0}">
                                        <li>
                                            <g:link controller="OpportunityRole" action="searchOpportunity"
                                                    params="[stage: it?.name]">
                                                ${it?.name} (${com.next.OpportunityRole.findAll("from OpportunityRole where user.id = ? and stage.id = opportunity.stage.id and stage.id = ?", [com.next.User.findByUsername(sec.loggedInUserInfo(field: 'username'))?.id, it?.id])?.size()})
                                            </g:link>
                                        </li>
                                    </g:if>
                                </g:each>
                            </ul>
                        </li>
                    </sec:ifNotGranted>
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
    <input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

    <aside id="menu" class="docs-pictures">
        <div id="navigation">
            <div class="p-sm font-extra-bold">
                附件资料（${com.next.Attachments.findAllByOpportunity(this.opportunity)?.size()}）
            </div>
            <ul class="nav" id="side-menu">
                <g:each in="${com.next.AttachmentType.list()}">
                    <g:if test="${com.next.Attachments.findAllByOpportunityAndType(this.opportunity, it)?.size() > 0 }">
                        <li class="navItem">
                            <a href="#">
                                <span class="nav-label">
                                    ${it?.name}
                                </span>
                                <span>(${com.next.Attachments.findAllByOpportunityAndType(this.opportunity, it)?.size()})</span>
                                <g:if test="${com.next.Attachments.findAllByOpportunityAndType(this.opportunity, it)?.size() > 0}">
                                    <span class="fa arrow"></span>
                                </g:if>
                            </a>
                            <ul class="nav nav-second-level secondNav">
                            </ul>
                        </li>
                    </g:if>
                </g:each>

            </ul>
        </div>
    </aside>
</sec:ifLoggedIn>
<div id="wrapper">

    <g:layoutBody/>
</div>

<asset:javascript src="homer/vendor/jquery-ui/jquery-ui.min.js"/>
<asset:javascript src="homer/vendor/bootstrap/dist/js/bootstrap.min.js"/>
<asset:javascript src="homer/vendor/metisMenu/dist/metisMenu.min.js"/>
<asset:javascript src="homer/vendor/sweetalert/lib/sweet-alert.min.js"/>
<asset:javascript src="homer/vendor/blueimp-gallery/js/jquery.blueimp-gallery.min.js"/>
<asset:javascript src="homer/vendor/select2-3.5.2/select2.min.js"/>
<asset:javascript src="homer/vendor/select2-3.5.2/select2_locale_zh-CN.js"/>
<asset:javascript src="homer/vendor/viewer/viewer.min.js"/>
<asset:javascript src="homer/vendor/iCheck/icheck.min.js"/>
<asset:javascript src="homer/homer.js"/>

<script>
    $(function () {
//        查看图片
        var viewer = new Viewer(document.querySelector('.docs-pictures'));
        $.each($("#menu .nav .navItem"), function(i, obj) {
//            var viewer;
            var isFirst = false;
            var times = 1;
            $(obj).click(function(){

                if (isFirst) {
                    times++;
                } else {
                    var attachmentTypeName = $(this).find(".nav-label").text().trim();
                    var opportunityId = $("#opportunityId").val().trim();
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "/attachments/ajaxShow",
                        data: {
                            opportunityId: opportunityId,
                            attachmentTypeName: attachmentTypeName
                        },

                        success: function (data) {

                            if (data.status == "success") {

                                var photoes = data.photoes;
                                var content;
                                $(obj).children(".secondNav").html("");
                                for (var i = 0; i < photoes.length; i++) {
                                    var pos = photoes[i].fileUrl.lastIndexOf(".");
                                    var type = photoes[i].fileUrl.substring(pos+1);

                                    if(pos > -1){
                                        if(type == "doc"){
                                            content='<li><a target="_blank" href="'+photoes[i].fileUrl+'"><i class="fa fa-file-word-o text-info"></i><span class="checkBtn">点击查看</span></a></li>';
                                        } else if(type == "docx"){
                                            content='<li><a target="_blank" href="'+photoes[i].fileUrl+'"><i class="fa fa-file-word-o text-info"></i><span class="checkBtn">点击查看</span></a></li>';
                                        } else if(type == "xlsx"){
                                            content='<li><a target="_blank" href="'+photoes[i].fileUrl+'"><i class="fa fa-file-excel-o text-success"></i><span class="checkBtn btn-link">点击查看</span></a></li>';
                                        }else if(type == "xls"){
                                            content='<li><a target="_blank" href="'+photoes[i].fileUrl+'"><i class="fa fa-file-excel-o text-success"></i><span class="checkBtn btn-link">点击查看</span></a></li>';
                                        } else{
                                            if(!photoes[i].thumbnailUrl){
                                                photoes[i].thumbnailUrl = photoes[i].fileUrl;
                                            }
                                            content = '<li><a href="javascript:;"><img data-original="' + photoes[i].fileUrl + '" src="' + photoes[i].thumbnailUrl + '" alt="' + photoes[i].type.name + '"/></a></li>';
                                        }
                                        $(obj).children(".secondNav").append(content);
                                    }

                                }
                                if(viewer){
                                    viewer.destroy();
                                    viewer = new Viewer(document.querySelector('.docs-pictures'),{
                                        url: 'data-original',
                                        navbar:false
                                    });
                                }
                            }
                        },
                        error: function () {
                            swal("获取失败，请稍后重试", "", "error");
                        }
                    })
                    isFirst = true;
                }
            });
        });

        $(".viewer-button").click(function(){
            $(".viewer-container").remove();
        });

//        折叠
        $(".collapsed .showhide").click(function () {
            $(this).parent().parent().toggleClass("hbuilt");
            $(this).parent().parent().parent().toggleClass("panel-collapse");
        });
        $("select").select2();
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
                            swal({
                                title: "删除成功!",
                                type: "success",
                                confirmButtonText: false,
                                closeOnConfirm: true
                            });
                            currentForm.submit();


                        }
                    });

        });
    });
</script>

</body>
</html>
