<!DOCTYPE html>
<html>
<head>
    <title>附件信息-copy05</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="homer/vendor/viewer/viewer.min.css"/>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="content animate-panel docs-pictures">
    <g:if test="${attachmentTypeName == "征信"}">
        <div class="row">
            <div class="hpanel">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:if test="${this.notarizingPhotoes?.size() >= 2}">
                            <g:link class="btn btn-info btn-xs" controller="Attachments"
                                    action="prepareSetDisplayOrder"
                                    params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('征信报告')?.id]"><i
                                    class="fa fa-refresh"></i>排序</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </g:if>
                    </div>
                    征信报告(${this.notarizingPhotoes?.size()})
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${this.notarizingPhotoes}">
                        <div class="col-lg-3">
                            <div class="hpanel">

                                <div class="panel-heading">
                                    <div class="panel-tools">
                                        <sec:ifAnyGranted
                                                roles="ROLE_ADMINISTRATOR,ROLE_BRANCH_OFFICE_RISK_MANAGER,ROLE_HEAD_OFFICE_RISK_MANAGER,ROLE_GENERAL_RISK_MANAGER">
                                            <g:form resource="${it}" action="riskDelete">
                                                <input type="hidden" name="attachmentTypeName"
                                                       value="${this.attachmentTypeName}">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                        class="fa fa-trash-o"></i>删除</button>
                                            </g:form>
                                        </sec:ifAnyGranted>
                                    </div>
                                    征信报告
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
                    <g:if test="${this.notarizingPhotoes?.size() == 0}">
                        暂无征信报告照片
                    </g:if>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="hpanel">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:if test="${this.donePhotoes?.size() >= 2}">
                            <g:link class="btn btn-info btn-xs" controller="Attachments"
                                    action="prepareSetDisplayOrder"
                                    params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('被执情况')?.id]"><i
                                    class="fa fa-refresh"></i>排序</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </g:if>
                    </div>
                    被执情况(${this.donePhotoes?.size()})
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${this.donePhotoes}">
                        <div class="col-lg-3">
                            <div class="hpanel">

                                <div class="panel-heading">
                                    <div class="panel-tools">
                                        <sec:ifAnyGranted
                                                roles="ROLE_ADMINISTRATOR,ROLE_BRANCH_OFFICE_RISK_MANAGER,ROLE_HEAD_OFFICE_RISK_MANAGER,ROLE_GENERAL_RISK_MANAGER">
                                            <g:form resource="${it}" action="riskDelete">
                                                <input type="hidden" name="attachmentTypeName"
                                                       value="${this.attachmentTypeName}">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                        class="fa fa-trash-o"></i>删除</button>
                                            </g:form>
                                        </sec:ifAnyGranted>
                                    </div>
                                    被执情况
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
                    <g:if test="${this.donePhotoes?.size() == 0}">
                        暂无被执情况照片
                    </g:if>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="hpanel">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:if test="${this.decimalPhotoes?.size() >= 2}">
                            <g:link class="btn btn-info btn-xs" controller="Attachments"
                                    action="prepareSetDisplayOrder"
                                    params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('大数据')?.id]"><i
                                    class="fa fa-refresh"></i>排序</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </g:if>
                    </div>
                    大数据(${this.decimalPhotoes?.size()})
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${this.decimalPhotoes}">
                        <div class="col-lg-3">
                            <div class="hpanel">

                                <div class="panel-heading">
                                    <div class="panel-tools">
                                        <sec:ifAnyGranted
                                                roles="ROLE_ADMINISTRATOR,ROLE_BRANCH_OFFICE_RISK_MANAGER,ROLE_HEAD_OFFICE_RISK_MANAGER,ROLE_GENERAL_RISK_MANAGER">
                                            <g:form resource="${it}" action="riskDelete">
                                                <input type="hidden" name="attachmentTypeName"
                                                       value="${this.attachmentTypeName}">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                        class="fa fa-trash-o"></i>删除</button>
                                            </g:form>
                                        </sec:ifAnyGranted>
                                    </div>
                                    大数据
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
                    <g:if test="${this.decimalPhotoes?.size() == 0}">
                        暂无大数据照片
                    </g:if>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="hpanel">

                <div class="panel-heading">
                    <div class="panel-tools">
                        <g:if test="${this.centralBankCreditPhotoes?.size() >= 2}">
                            <g:link class="btn btn-info btn-xs" controller="Attachments"
                                    action="prepareSetDisplayOrder"
                                    params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('央行征信（中佳信）')?.id]"><i
                                    class="fa fa-refresh"></i>排序</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </g:if>
                    </div>
                    央行征信（中佳信）(${this.centralBankCreditPhotoes?.size()})
                </div>

                <div class="panel-body p-xs">
                    <g:each in="${this.centralBankCreditPhotoes}">
                        <div class="col-lg-3">
                            <div class="hpanel">

                                <div class="panel-heading">
                                    <div class="panel-tools">
                                        <sec:ifAnyGranted
                                                roles="ROLE_ADMINISTRATOR,ROLE_BRANCH_OFFICE_RISK_MANAGER,ROLE_HEAD_OFFICE_RISK_MANAGER,ROLE_GENERAL_RISK_MANAGER">
                                            <g:form resource="${it}" action="riskDelete">
                                                <input type="hidden" name="attachmentTypeName"
                                                       value="${this.attachmentTypeName}">
                                                <button class="deleteBtn btn btn-danger btn-xs" type="button"><i
                                                        class="fa fa-trash-o"></i>删除</button>
                                            </g:form>
                                        </sec:ifAnyGranted>
                                    </div>
                                    央行征信（中佳信）
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
                    <g:if test="${this.centralBankCreditPhotoes?.size() == 0}">
                        暂无央行征信（中佳信）照片
                    </g:if>
                </div>
            </div>
        </div>
    </g:if>
</div>
<asset:javascript src="homer/vendor/viewer/viewer.min.js"/>
<asset:javascript src="homer/vendor/viewer/main.js"/>
</body>
</html>
