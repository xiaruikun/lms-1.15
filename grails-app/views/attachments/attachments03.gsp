<!DOCTYPE html>
<html>
<head>
    <title>附件信息-03</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="homer/vendor/viewer/viewer.min.css"/>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="content animate-panel docs-pictures">

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.normalApprovalPhotos?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('普通签呈')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                普通签呈(${this.normalApprovalPhotos?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.normalApprovalPhotos}">

                    <div class="col-lg-3">
                        <div class="hpanel">
                            <div class="panel-heading">
                                <div class="panel-tools">
                                    <g:if test="${opportunityLoanFlow?.executionSequence <= currentFlow?.executionSequence}">
                                        <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                            <g:form resource="${it}" method="DELETE">
                                                <input type="hidden" name="attachmentTypeName"
                                                       value="${this.attachmentTypeName}">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                        class="fa fa-trash-o"></i>删除</button>
                                            </g:form>
                                        </sec:ifAllGranted>
                                    </g:if>
                                    <g:else>
                                        <g:form resource="${it}" method="DELETE">
                                            <input type="hidden" name="attachmentTypeName"
                                                   value="${this.attachmentTypeName}">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i>删除</button>
                                        </g:form>
                                    </g:else>
                                </div>
                                普通签呈
                            </div>

                            <div class="panel-body attchmentBody">
                                <div class="imgBox">
                                    <g:if test="${it?.fileName?.split('\\.')[-1] in ['doc', 'docx', 'pdf', 'xlsx', 'xls']}">
                                        <a class="file-title" href="${it?.fileName}">${it?.fileName?.split('/')[-1]}
                                            <h5 class="text-info">点击查看</h5>
                                        </a>
                                    </g:if>
                                    <g:if test="${it?.fileName?.split('\\.')[-1] in ['jpg', 'png', 'jpeg','JPG','PNG','JPEG']}">

                                        <g:if test="${it?.thumbnailUrl}">
                                            <img src="${it?.thumbnailUrl}" data-original="${it?.fileName}"
                                                 alt="${it?.type?.name}"
                                                 data/>
                                        </g:if>
                                        <g:else>
                                            <img src="${it?.fileName}" data-original="${it?.fileName}"
                                                 alt="${it?.type?.name}">
                                        </g:else>

                                    </g:if>
                                </div>

                                <div class="tooltip-demo">
                                    <g:link class="btn-link description-edit" controller="attachments"
                                            action="edit"
                                            params="[id: it?.id, attachmentTypeName: attachmentTypeName]"
                                            data-toggle="tooltip" data-placement="left"
                                            title="${it?.description}">编辑描述：
                                        <g:if test="${it?.description}">${it?.description}</g:if>
                                    </g:link>
                                </div>
                            </div>

                            <div class="panel-footer ">
                                <g:formatDate date="${it.createdDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </div>
                        </div>
                    </div>
                </g:each>
                <g:if test="${this.normalApprovalPhotos?.size() == 0}">
                    暂无普通签呈
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.specialApprovalPhotos?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('特批签呈')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                特批签呈(${this.specialApprovalPhotos?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.specialApprovalPhotos}">
                    <div class="col-lg-3">
                        <div class="hpanel">
                            <div class="panel-heading">
                                <div class="panel-tools">
                                    <g:if test="${opportunityLoanFlow?.executionSequence <= currentFlow?.executionSequence}">
                                        <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                            <g:form resource="${it}" method="DELETE">
                                                <input type="hidden" name="attachmentTypeName"
                                                       value="${this.attachmentTypeName}">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                        class="fa fa-trash-o"></i>删除</button>
                                            </g:form>
                                        </sec:ifAllGranted>
                                    </g:if>
                                    <g:else>
                                        <g:form resource="${it}" method="DELETE">
                                            <input type="hidden" name="attachmentTypeName"
                                                   value="${this.attachmentTypeName}">
                                            <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                    class="fa fa-trash-o"></i>删除</button>
                                        </g:form>
                                    </g:else>
                                </div>
                                特批签呈
                            </div>

                            <div class="panel-body attchmentBody">
                                <div class="imgBox">
                                    <g:if test="${it?.fileName?.split('\\.')[-1] in ['doc', 'docx', 'pdf', 'xlsx', 'xls']}">
                                        <a class="file-title" href="${it?.fileName}">${it?.fileName?.split('/')[-1]}
                                            <h5 class="text-info">点击查看</h5>
                                        </a>
                                    </g:if>
                                    <g:if test="${it?.fileName?.split('\\.')[-1] in ['jpg', 'png', 'jpeg','JPG','PNG','JPEG']}">

                                        <g:if test="${it?.thumbnailUrl}">
                                            <img src="${it?.thumbnailUrl}" data-original="${it?.fileName}"
                                                 alt="${it?.type?.name}"
                                                 data/>
                                        </g:if>
                                        <g:else>
                                            <img src="${it?.fileName}" data-original="${it?.fileName}"
                                                 alt="${it?.type?.name}">
                                        </g:else>

                                    </g:if>
                                </div>

                                <div class="tooltip-demo">
                                    <g:link class="btn-link description-edit" controller="attachments"
                                            action="edit"
                                            params="[id: it?.id, attachmentTypeName: attachmentTypeName]"
                                            data-toggle="tooltip" data-placement="left"
                                            title="${it?.description}">编辑描述：
                                        <g:if test="${it?.description}">${it?.description}</g:if>
                                    </g:link>
                                </div>
                            </div>

                            <div class="panel-footer ">
                                <g:formatDate date="${it.createdDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </div>
                        </div>
                    </div>
                </g:each>
                <g:if test="${this.specialApprovalPhotos?.size() == 0}">
                    暂无特批签呈
                </g:if>
            </div>
        </div>
    </div>

</div>

<asset:javascript src="homer/vendor/viewer/viewer.min.js"/>
<asset:javascript src="homer/vendor/viewer/main.js"/>
</body>
</html>
