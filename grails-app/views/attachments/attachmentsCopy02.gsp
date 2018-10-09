<!DOCTYPE html>
<html>
<head>
    <title>附件信息-copy02</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="homer/vendor/viewer/viewer.min.css"/>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="content animate-panel docs-pictures">
    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.maritalPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('借款人身份证')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                借款人身份证(${this.maritalPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.maritalPhotoes}">
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
                                借款人身份证
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
                <g:if test="${this.maritalPhotoes?.size() == 0}">
                    暂无借款人身份证
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.maritalSpousePhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('借款人配偶身份证')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                借款人配偶身份证(${this.maritalSpousePhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.maritalSpousePhotoes}">

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
                                借款人配偶身份证
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
                <g:if test="${this.maritalSpousePhotoes?.size() == 0}">
                    暂无借款人配偶身份证
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.mortgagorPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('抵押人身份证')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                抵押人身份证(${this.mortgagorPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.mortgagorPhotoes}">
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
                                抵押人身份证
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
                <g:if test="${this.mortgagorPhotoes?.size() == 0}">
                    暂无抵押人身份证
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.mortgagorSpousePhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('抵押人配偶身份证')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                抵押人配偶身份证(${this.mortgagorSpousePhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.mortgagorSpousePhotoes}">
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
                                抵押人配偶身份证
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
                <g:if test="${this.mortgagorSpousePhotoes?.size() == 0}">
                    暂无抵押人配偶身份证
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.housePhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('房产证')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                房产证(${this.housePhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.housePhotoes}">
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
                                房产证
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
                <g:if test="${this.housePhotoes?.size() == 0}">
                    暂无房产证照片
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.bookPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('户口本')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                户口本(${this.bookPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.bookPhotoes}">
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
                                户口本
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
                <g:if test="${this.bookPhotoes?.size() == 0}">
                    暂无户口本照片
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.maritalIncPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('婚姻证明')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                婚姻证明(${this.maritalIncPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.maritalIncPhotoes}">
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
                                婚姻证明
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
                <g:if test="${this.maritalIncPhotoes?.size() == 0}">
                    暂无婚姻证明
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.acceptancePhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('抵押登记受理单')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>

                </div>
                抵押登记受理单(${this.acceptancePhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.acceptancePhotoes}">
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
                                抵押登记受理单
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
                <g:if test="${this.acceptancePhotoes?.size() == 0}">
                    暂无抵押登记受理单
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.otherPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('他项证明')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                他项证明(${this.otherPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.otherPhotoes}">
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
                                他项证明
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
                <g:if test="${this.otherPhotoes?.size() == 0}">
                    暂无他项证明照片
                </g:if>
            </div>
        </div>
    </div>
</div>
<asset:javascript src="homer/vendor/viewer/viewer.min.js"/>
<asset:javascript src="homer/vendor/viewer/main.js"/>
</body>
</html>
