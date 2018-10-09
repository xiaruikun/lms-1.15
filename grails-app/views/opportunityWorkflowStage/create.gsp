<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName"
           value="${message(code: 'opportunityWorkflowStage.label', default: 'OpportunityWorkflowStage')}"/>
    <title>新增区域-工作流</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="territoryFlow" action="index">区域-工作流</g:link>
                    </li>
                    <li class="active">
                        <span>新增区域-工作流</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增区域-工作流
            </h2>
        </div>
    </div>
</div>
%{--<f:all bean="opportunityWorkflowStage"/>--}%

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增区域工作流
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal">
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityWorkflowStage}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityWorkflowStage}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>
                    <div class="form-group">
                        <g:textField name="workflow.id" required="required" id="workflow"
                                     value="${this.opportunityWorkflowStage?.workflow?.id}" class="hide"/>

                        <label class="col-md-2 control-label">订单阶段</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="stage.id" required="required" id="stage"
                                      value="${this.opportunityWorkflowStage?.stage}"
                                      from="${this.opportunityStages}"
                                      optionKey="id"></g:select>
                        </div>
                        <label class="col-md-2 control-label">执行次序</label>

                        <div class="col-md-3">
                            <g:textField name="executionSequence"
                                         value="${this.opportunityWorkflowStage?.executionSequence}"
                                         class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">

                        <label class="col-md-2 control-label">能否退回</label>

                        <div class="col-md-3">
                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="canReject" value="true" checked="">
                                <label for="radio1">true</label>
                            </div>

                            <div class="radio radio-info radio-inline">
                                <input type="radio" name="canReject" value="false">
                                <label for="radio2">false</label>
                            </div>
                        </div>
                        <label class="col-md-2 control-label">布局</label>

                        <div class="col-md-3">
                            <g:select class="form-control" name="opportunityLayout.id"
                                      value="${this.opportunityWorkflowStage?.opportunityLayout?.id}"
                                      required="required" id="opportunityLayout"
                                      from="${com.next.OpportunityLayout.findAllByActive(true)}" optionKey="id"
                                      optionValue="description" noSelection="${['null': '请选择']}"></g:select>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <!-- <g:field name="doucmentText" id="doucmentText"
                                     value="${this.opportunityWorkflowStage?.document?.text}"
                                    type="hidden"/> -->
                        <label class="col-md-2 control-label">操作说明</label>

                        <div class="col-md-3">
                          <g:select class="form-control" name="document.id"
                                    value="${this.opportunityWorkflowStage?.document?.id}"
                                    id="document"
                                    from="${this.documentList}" optionKey="id"
                                    optionValue="title" noSelection="${['null': '请选择']}"></g:select>
                            <!-- <div id="summernote"></div> -->
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            <g:submitButton class="btn btn-info" id="btn" name="update" value="保存"/>
                            <!-- <button type="button" class="btn btn-info" id="submitBtn">保存</button> -->
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>
<!-- <script>
    $(function () {
        $('#summernote').summernote({
            height: 200,                 // set editor height
            minHeight: null,             // set minimum height of editor
            maxHeight: null,             // set maximum height of editor
            toolbar: [
                ['edit', ['undo', 'redo']],
                ['para', ['style', 'ul', 'ol', 'paragraph', 'height']],
                ['font', ['fontname', 'fontsize', 'color', 'bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
                ['insert', ['link', 'table', 'hr']],
                ['fullscreen', ['fullscreen']]
            ]
        });

        $("#submitBtn").click(function () {
            var markupStr = $('#summernote').summernote('code');
            $("#doucmentText").val(markupStr);
            alert($("#doucmentText").val());
            $("form").submit();
        });
    })
</script> -->
</body>
</html>
