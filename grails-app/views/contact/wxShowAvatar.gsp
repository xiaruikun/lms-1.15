<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="wechat"/>
    <title>修改头像</title>
</head>

<body>

<div>
    <g:if test="${contact.avatar == '' || contact.avatar == null}">
        <g:img style="border-radius: 50%" id="avatar" dir="images" file="defaultAvater.jpg" width="120" height="150"/>
    </g:if>
    <g:else>
        <g:img style="border-radius: 50%" id="avatar" dir="images" file="${this.contact.avatar}" width="120"
               height="150"/>
    </g:else>

    <input name="cellphone" class="form-control" value="${contact?.cellphone}" type="hidden"/>

    <div id="picker">上传新头像</div>
</div>
%{--<g:uploadForm action="wxUploadAvatar" class="form-horizontal" enctype="multipart/form-data">
    <div class="weui_cell weui_cell_warn">
        <div class="weui_cell">
            <div class="weui_uploader">
                <div class="weui_uploader_hd weui_cell">
                    <div class="weui_cell_bd weui_cell_primary">上传头像</div>
                </div>
                <div class="weui_uploader_bd">
                    <div class="weui_uploader_input_wrp">
                        <input class="weui_uploader_input" type="file" name="file" id="file" required="">
                        <input name="fileType" id="fileType" class="form-control" type="hidden" />
                        <input name="cellphone" class="form-control" value="${contact?.cellphone}" type="hidden"/>
                    </div>
                </div>
                <input id="submit_upload" type="submit" class="load-btn" value="确定"/>
            </div>
        </div>
    </div>
</g:uploadForm>--}%
<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div class="row">
        <g:hasErrors bean="${this.contact}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.contact}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
    </div>
</div>
<script>
    $(function () {
        var uploader = WebUploader.create({
            auto: true,
            swf: "/assets/stylesheets/Uploader.swf",
//            server:"/contact/wxUploadAvatar",
            server: "https://" + window.location.host + "/contact/wxUploadAvatar",
            pick: "#picker",
            formData: {
                "cellphone":${contact?.cellphone},
                "fileType": "jpg"
            },
            accept: {
                title: 'Images',
                extensions: 'jpg,jpeg,png',
                mimeTypes: 'image/*'
            },
            resize: true,
            compress: {
                width: 400,
                // 图片质量，只有type为`image/jpeg`的时候才有效。
                quality: 90,

                // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                allowMagnify: false,

                // 是否允许裁剪。
                crop: false,

                // 是否保留头部meta信息。
                preserveHeaders: true,

                // 如果发现压缩后文件大小比原来还大，则使用原来图片
                // 此属性可能会影响图片自动纠正功能
                noCompressIfLarger: false,

                // 单位字节，如果图片大小小于此值，不会采用压缩。
                compressSize: 128000
            }

        });

        uploader.on('uploadStart', function (file) {
            var name = file.name;
            uploader.options.formData.fileType = name.substr(name.lastIndexOf(".") + 1);
        });

        uploader.on("uploadError", function (file) {
            $(".helpMsg").text("上传出错").show();
//            alert("上传出错");
        });

        uploader.on("uploadSuccess", function (file, json) {
            alert("ssss");
            alert(json.status);
            if (json.status == "error") {
                alert(json.errorMessage);
            }

            if (json.status == "success") {
                alert("sfsfgdssfsfsf");
                $(".helpMsg").text("上传成功").show();
                $("#avatar").attr("src", "/static/images/" + json.contact.avatar);
            }

        });
        uploader.on("uploadError", function (file, json) {
            alert(json.status);
            alert(json.errorMessage);
            if (json.status == "error") {
                alert("上传失敗");
                $(".helpMsg").text("上传失敗").show();
//                alert("上传失敗");
            }

        });
    });
</script>
%{--<g:javascript>
    $(function () {
        var time = 30;
        var interval;
        $('#file').change(function(){
            //检测上传文件的类型
            var imgName = document.all.file.value;
            var ext,idx;
            if (imgName == ''){
                document.all.submit_upload.disabled=true;
                $(".helpMsg").text("请选择文件").show();
                setTimeout(function () {
                    $(".helpMsg").hide(200);
                }, 2000);
                // alert("请选择文件!");
                return;
            } else {
                idx = imgName.lastIndexOf(".");
                if (idx != -1){
                    ext = imgName.substr(idx+1).toUpperCase();
                    ext = ext.toLowerCase( );
                    document.getElementById('fileType').value= ext;
                    // alert("ext="+ext);
                    if (ext != 'jpg' && ext != 'png' && ext != 'jpeg'){
                        document.all.submit_upload.disabled=true;
                        $(".helpMsg").text("支持图片上传格式 .jpg  .png  .jpeg!").show();
                        setTimeout(function () {
                            $(".helpMsg").hide(200);
                        }, 2000);
                        // alert("支持图片上传格式wei .jpg  .png  .jpeg!");
                        return;
                    }
                } else {
                    document.all.submit_upload.disabled=true;
                    $(".helpMsg").text("支持图片上传格式 .jpg  .png  .jpeg!").show();
                    setTimeout(function () {
                        $(".helpMsg").hide(200);
                    }, 2000);
                    // alert("支持图片上传格式 .jpg  .png  .jpeg!");
                    return;
                }
            }
            // 检测上传文件的大小
            var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
            var fileSize = 0;
            var target = document.all.file;
            if (isIE && !target.files){
                var filePath = target.value;
                var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
                var file = fileSystem.GetFile (filePath);
                fileSize = file.Size;
            } else {
                fileSize = target.files[0].size;
            }
            var size = fileSize / 1024*1024;

            if(size>(1024*125)){
                document.all.submit_upload.disabled=true;
                $(".helpMsg").text("图片大小不能超过 125KB").show();
                setTimeout(function () {
                    $(".helpMsg").hide(200);
                }, 2000);
                // alert("图片大小不能超过 125KB");
            }else{
                //   alert("you can upload this file");
                document.all.submit_upload.disabled=false;
            }

        });

        setTimeout(function(){
            $(".message").hide(200);
        },2000);
    });

</g:javascript>--}%
</body>
</html>


