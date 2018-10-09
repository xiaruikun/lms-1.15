<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main"/>

    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>抵押合同全委-copy4</title>
    <asset:stylesheet src="homer/vendor/viewer/viewer.min.css"/>
</head>

<body>
<input type="hidden" value="${this.opportunity?.id}" id="opportunityId">

<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <h2 class="font-light">
                合同编号：${this.opportunity?.externalId}
                <span>/</span>
                订单号: ${this.opportunity.serialNumber}
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel docs-pictures">
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
                    暂无他项证明
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.loanPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('借款合同')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                借款合同(${this.loanPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.loanPhotoes}">
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
                                借款合同
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
                <g:if test="${this.loanPhotoes?.size() == 0}">
                    暂无借款合同
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.mortgagePhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('抵押合同')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                抵押合同(${this.mortgagePhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.mortgagePhotoes}">
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
                                抵押合同
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
                <g:if test="${this.mortgagePhotoes?.size() == 0}">
                    暂无抵押合同
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.loanProxyPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('委托借款代理服务合同')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                委托借款代理服务合同(${this.loanProxyPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.loanProxyPhotoes}">
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
                                委托借款代理服务合同
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
                <g:if test="${this.loanProxyPhotoes?.size() == 0}">
                    暂无委托借款代理服务合同
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.sellPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('售房公证')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                售房公证(${this.sellPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.sellPhotoes}">
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
                                售房公证
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
                <g:if test="${this.sellPhotoes?.size() == 0}">
                    暂无售房公证
                </g:if>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="hpanel">

            <div class="panel-heading">
                <div class="panel-tools">
                    <g:if test="${this.notarizingPhotoes?.size() >= 2}">
                        <g:link class="btn btn-info btn-xs" controller="Attachments" action="prepareSetDisplayOrder"
                                params="[opportunity: this.opportunity?.id, attachmentType: com.next.AttachmentType.findByName('公证受理单')?.id]"><i
                                class="fa fa-refresh"></i>排序</g:link>
                        <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                    </g:if>
                </div>
                公证受理单(${this.notarizingPhotoes?.size()})
            </div>

            <div class="panel-body p-xs">
                <g:each in="${this.notarizingPhotoes}">
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
                                公证受理单
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
                    暂无公证受理单
                </g:if>
            </div>
        </div>
    </div>

</div>
<asset:javascript src="homer/vendor/viewer/viewer.min.js"/>
<asset:javascript src="homer/vendor/viewer/main.js"/>
</body>

</html>
