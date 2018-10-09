<!DOCTYPE html>
<html>
<head>
    <title>附件信息-copy05</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="homer/vendor/viewer/viewer.min.css"/>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="content animate-panel docs-pictures">
        <div class="row">
            <div class="hpanel">

                <div class="panel-heading">
                    <div class="panel-tools">

                    </div>
                    ${this.type?.name}(${this.photoes?.size()})
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${this.photoes}">
                        <div class="col-lg-3">
                            <div class="hpanel">

                                <div class="panel-heading">
                                    <div class="panel-tools">


                                    </div>
                                      ${this.type?.name}
                                </div>

                                <div class="panel-body attchmentBody">
                                    <div class="imgBox">
                                        <g:if test="${it?.fileUrl?.split('\\.')[-1] in ['doc', 'docx', 'pdf', 'xlsx', 'xls']}">
                                            <a class="file-title" href="${it?.fileUrl}">${it?.fileUrl?.split('/')[-1]}
                                                <h5 class="text-info">点击查看</h5>
                                            </a>
                                        </g:if>
                                        <g:if test="${it?.fileUrl?.split('\\.')[-1] in ['jpg', 'png', 'jpeg','JPG','PNG','JPEG']}">

                                            <g:if test="${it?.thumbnailUrl}">
                                                <img src="${it?.thumbnailUrl}" data-original="${it?.fileUrl}"
                                                     alt="${it?.type?.name}"
                                                     data/>
                                            </g:if>
                                            <g:else>
                                                <img src="${it?.fileUrl}" data-original="${it?.fileUrl}"
                                                     alt="${it?.type?.name}">
                                            </g:else>

                                        </g:if>
                                    </div>

                                    <div class="tooltip-demo description-edit">

                                            <g:if test="${it?.description}">${it?.description}</g:if>

                                    </div>
                                </div>

                                <div class="panel-footer ">
                                    <g:formatDate date="${it.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                                </div>
                            </div>
                        </div>
                    </g:each>
                    <g:if test="${this.photoes?.size() == 0}">
                        暂无${this.type?.name}照片
                    </g:if>
                </div>
            </div>
        </div>


</div>
<asset:javascript src="homer/vendor/viewer/viewer.min.js"/>
<asset:javascript src="homer/vendor/viewer/main.js"/>
</body>
</html>
