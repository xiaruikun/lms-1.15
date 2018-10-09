<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'attachments.label', default: 'Attachments')}"/>
    <title>附件：${attachments?.type?.name}</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="attachments" action="index">附件管理</g:link>
                    </li>
                    <li class="active">
                        <span>${this.attachments?.opportunity?.serialNumber}</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                附件: ${this.attachments?.type?.name}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${this.attachments}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.attachments}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                        <g:message error="${error}"/>
                    </li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <div class="hpanel hblue">
            <div class="panel-heading">
                附件描述编辑
            </div>

            <div class="panel-body">
                <g:form resource="${this.attachments}" action="ajaxUpdate" class="form-horizontal attachmentForm">

                    <div class="form-group">

                        <label class="col-md-3 control-label">图片描述</label>

                        <div class="col-md-4 input-group">
                            %{--<input type="hidden" name="attachmentTypeName" value="${this.attachmentTypeName}">--}%
                            <input type="hidden" name="attachmentTypeName" value="${this.attachmentTypeName}">
                            <g:textField class="form-control" name="description"
                                         value="${this.attachments?.description}"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-4">
                            <button type="button" class="btn btn-info" id="descriptionBtn">保存</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#descriptionBtn").click(function () {
            $.ajax({
                type: "POST",
                dataType: "json",
                url: $(this).closest(".attachmentForm").attr("action"),
                data: {
                    description: $("#description").val().trim(),
                },

                success: function (data) {
                    if (data.status == "success") {
                        window.location.href = document.referrer
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        })
    })
</script>
</body>
</html>
