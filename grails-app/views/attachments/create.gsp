<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'attachments.label', default: 'Attachments')}"/>
    <title>新增附件</title>
    <asset:stylesheet src="homer/vendor/jQuery-File-Upload/css/jquery.fileupload.css"/>
    <asset:stylesheet src="homer/vendor/blueimp-gallery/css/blueimp-gallery.min.css"/>

    <style>

    .img {
        width: 80px;
        height: 58px;
    }

    .fileupload table > tbody > tr > td {
        vertical-align: middle;
    }
    .fileupload-buttonbar button,.files button{
        margin-top: 2px;
    }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show" id="${attachments?.opportunity?.id}">订单</g:link>
                    </li>
                    <li class="active">
                        <span>新增附件</span>
                    </li>
                </ol>
            </div>
            <h2 class="font-light m-b-xs">
                附件管理
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">

        <div class="alert alert-info hide" role="status" id="alert-danger">提醒</div>
        <g:if test="${flash.message}">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
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
            <div class="panel-heading no-padding">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#tab-1">上传图片</a></li>
                    <li class=""><a data-toggle="tab" href="#tab-2">上传文件</a></li>
                    <li class=""><a data-toggle="tab" href="#tab-3">上传视频</a></li>
                </ul>
            </div>
            <div class="tab-content panel-body">
                <div id="tab-1" class="tab-pane active">
                    <g:form class="fileupload1 form-horizontal" controller="attachments" action="uploadAvatar" method="post"
                            enctype="multipart/form-data">
                        <input type="hidden" id="opportunityId" name="opportunityId"
                               value="${attachments?.opportunity?.id}"/>

                        <div class="form-group">
                            <label class="col-md-2 control-label">附件类型</label>

                            <div class="col-md-3">
                                <g:select class="form-control" name="type" id="type" optionKey="id" optionValue="name"
                                          from="${this.attachmentTypeList}" value="${this.attachments?.type}"/>

                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">上传图片</label>

                            <div class="col-md-8">

                                <div class="row fileupload-buttonbar">
                                    <div class="col-lg-8">
                                        <!-- The fileinput-button span is used to style the file input field as button -->
                                        <span class="btn btn-info fileinput-button">
                                            <i class="glyphicon glyphicon-plus"></i>
                                            <span>选择文件</span>
                                            <input type="file" name="file" id="file1" multiple>
                                        </span>
                                        <button type="submit" class="btn btn-primary start">
                                            <i class="glyphicon glyphicon-upload"></i>
                                            <span>开始上传</span>
                                        </button>
                                        <button type="reset" class="btn btn-warning cancel">
                                            <i class="glyphicon glyphicon-ban-circle"></i>
                                            <span>取消上传</span>
                                        </button>

                                        <span class="fileupload-process"></span>
                                    </div>
                                    <!-- The global progress state -->
                                    <div class="col-lg-4 fileupload-progress fade">
                                        <!-- The global progress bar -->
                                        <div class="progress full progress-striped active" role="progressbar"
                                             aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                        </div>
                                        <!-- The extended global progress state -->
                                        <div class="progress-extended">&nbsp;</div>
                                    </div>
                                </div>
                                <!-- The table listing the files available for upload/download -->
                                <p style="margin-top: 10px;">
                                    <code>图片大小不能超过 5M，图片格式为 jpg、png、jpeg</code>
                                </p>
                                <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>

                            </div>
                        </div>
                    </g:form>
                </div>
                <div id="tab-2" class="tab-pane">
                    <g:form class="fileupload2 form-horizontal" controller="attachments" action="uploadAvatar" method="post"
                            enctype="multipart/form-data">
                        <input type="hidden" id="opportunityId" name="opportunityId"
                               value="${attachments?.opportunity?.id}"/>

                        <div class="form-group">
                            <label class="col-md-2 control-label">附件类型</label>

                            <div class="col-md-3">
                                <g:select class="form-control" name="type" id="type" optionKey="id" optionValue="name"
                                          from="${this.attachmentTypeList}" value="${this.attachments?.type}"/>

                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">文件附件</label>

                            <div class="col-md-8">

                                <div class="row fileupload-buttonbar">
                                    <div class="col-lg-8">
                                        <!-- The fileinput-button span is used to style the file input field as button -->
                                        <span class="btn btn-info fileinput-button">
                                            <i class="glyphicon glyphicon-plus"></i>
                                            <span>选择文件</span>
                                            <input type="file" name="file" id="file2" multiple>
                                        </span>
                                        <button type="submit" class="btn btn-primary start">
                                            <i class="glyphicon glyphicon-upload"></i>
                                            <span>开始上传</span>
                                        </button>
                                        <button type="reset" class="btn btn-warning cancel">
                                            <i class="glyphicon glyphicon-ban-circle"></i>
                                            <span>取消上传</span>
                                        </button>

                                        <span class="fileupload-process"></span>
                                    </div>
                                    <!-- The global progress state -->
                                    <div class="col-lg-4 fileupload-progress fade">
                                        <!-- The global progress bar -->
                                        <div class="progress full progress-striped active" role="progressbar"
                                             aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                        </div>
                                        <!-- The extended global progress state -->
                                        <div class="progress-extended">&nbsp;</div>
                                    </div>
                                </div>
                                <!-- The table listing the files available for upload/download -->
                                <p style="margin-top: 10px;">
                                    <code>附件大小不能超过 500k,附件格式doc、docx、xlsx、xls</code>
                                </p>
                                <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>

                            </div>
                        </div>
                    </g:form>
                </div>
                <div id="tab-3" class="tab-pane">
                    <g:form class="fileupload3 form-horizontal" controller="attachments" action="uploadAvatar" method="post"
                            enctype="multipart/form-data">
                        <input type="hidden" id="opportunityId" name="opportunityId"
                               value="${attachments?.opportunity?.id}"/>

                        <div class="form-group">
                            <label class="col-md-2 control-label">附件类型</label>

                            <div class="col-md-3">
                                <g:select class="form-control" name="type" id="type" optionKey="id" optionValue="name"
                                          from="${this.attachmentTypeList}" value="${this.attachments?.type}"/>

                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">视频附件</label>

                            <div class="col-md-8">

                                <div class="row fileupload-buttonbar">
                                    <div class="col-lg-8">
                                        <!-- The fileinput-button span is used to style the file input field as button -->
                                        <span class="btn btn-info fileinput-button">
                                            <i class="glyphicon glyphicon-plus"></i>
                                            <span>选择文件</span>
                                            <input type="file" name="file" id="file3" multiple>
                                        </span>
                                        <button type="submit" class="btn btn-primary start">
                                            <i class="glyphicon glyphicon-upload"></i>
                                            <span>开始上传</span>
                                        </button>
                                        <button type="reset" class="btn btn-warning cancel">
                                            <i class="glyphicon glyphicon-ban-circle"></i>
                                            <span>取消上传</span>
                                        </button>

                                        <span class="fileupload-process"></span>
                                    </div>
                                    <!-- The global progress state -->
                                    <div class="col-lg-4 fileupload-progress fade">
                                        <!-- The global progress bar -->
                                        <div class="progress full progress-striped active" role="progressbar"
                                             aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                        </div>
                                        <!-- The extended global progress state -->
                                        <div class="progress-extended">&nbsp;</div>
                                    </div>
                                </div>
                                <!-- The table listing the files available for upload/download -->
                                <p style="margin-top: 10px;">
                                    <code>视频大小不能超过 30M，视频格式mov、mp4、avi、rm、3gp、mkv、wmv</code>
                                </p>
                                <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>

                            </div>
                        </div>
                    </g:form>
                </div>
            </div>

        </div>
    </div>
</div>


<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
    <div class="slides"></div>

    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress full progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>上传</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.thumbnailUrl%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img class="img" src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name lightBoxGallery">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span>{%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=file.size%}</span>
        </td>
        <td>
         {% if (file.error) { %}
          <button class="btn btn-success cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消上传</span>
                </button>
          {% } else { %}
               <button class="btn btn-success" type="button">
                    <i class="glyphicon glyphicon-ok"></i>
                    <span>上传成功</span>
               </button>
          {% } %}
        </td>
    </tr>
{% } %}
</script>
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"/>
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/tmpl.min.js"/>
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/load-image.all.min.js"/>
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/canvas-to-blob.min.js"/>

<asset:javascript src="homer/vendor/jQuery-File-Upload/js/jquery.iframe-transport.js"/>
<!-- The basic File Upload plugin -->
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/jquery.fileupload.js"/>
<!-- The File Upload processing plugin -->
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/jquery.fileupload-process.js"/>
<!-- The File Upload image preview & resize plugin -->
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/jquery.fileupload-image.js"/>
<!-- The File Upload validation plugin -->
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/jquery.fileupload-validate.js"/>
<!-- The File Upload user interface plugin -->
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/jquery.fileupload-ui.js"/>
<!-- The main application script -->
<asset:javascript src="homer/vendor/jQuery-File-Upload/js/cors/jquery.xdr-transport.js"/>
<asset:javascript src="homer/vendor/blueimp-gallery/js/jquery.blueimp-gallery.min.js"/>

<script>
    $(function () {
        $('#type option').each(function () {
            var obj = $(this);
            var text = obj.text();
            $(this).attr('text', text);
        });

        $("#type option[text='征信授权']").remove();
        $("#type option[text='返点证明']").remove();
        $("#type option[text='付款证明']").remove();
        $("#type option[text='估值付回材料']").remove();


        $('#file1').change(function ()
        {
            var files = document.getElementById('file1');
            $("#alert-danger").addClass('hide').text("")
            for (var i = 0; i < files.files.length; i++)
            {
                var file = files.files[i];
                if (file.size > 1024*1024*5 + 100) {
                    $("#alert-danger").removeClass('hide').text( file.name+ "图片大于5M，请修改后上传")
                    throw new Error("Something went badly wrong!");

                }
            }

        });



        $('.fileupload1').fileupload({
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            imageMaxWidth: 1200,
            imageMaxHeight: 1200,
            maxFileSize: 5242880,//5M
            maxNumberOfFiles: 10,
            acceptFileTypes: /(\.|\/)(jpeg|jpg|png)$/i
        });

        $('.fileupload2').fileupload({
            maxFileSize: 512000,//15M
            maxNumberOfFiles: 10,
            acceptFileTypes: /(\.|\/)(doc|docx|xlsx|xls)$/i
        });
        $('.fileupload3').fileupload({
            maxFileSize: 31457280,//30M
            maxNumberOfFiles: 5,
            acceptFileTypes: /(\.|\/)(mov|mp4|avi|rm|3gp|mkv|wmv)$/i
        });
    });
</script>
</body>
</html>
