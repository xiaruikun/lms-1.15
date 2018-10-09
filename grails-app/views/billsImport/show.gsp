<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'billsImport.label', default: 'BillsImport')}"/>
    <title>新增银行流水详情</title>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li><g:link controller="billsImport" action="index">银行流水列表</g:link></li>
                    <li class="active">银行流水详情</li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
               银行流水详情
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hyellow">
            <div class="panel-heading">
                <div class="panel-tools">
                    <div class="panel-tools">
                        <g:link class="btn btn-info btn-xs" action="edit" resource="${this.billsImport}"><i
                                class="fa fa-edit"></i>编辑</g:link>
                        <g:form resource="${this.billsImport}" method="DELETE" style="display: inline-block">
                            <button class="deleteBtn btn btn-danger btn-xs" type="button">
                                <i class="fa fa-trash-o"></i>删除
                            </button>
                        </g:form>
                    </div>
                </div>
               银行流水详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-4 control-label">银行账号名：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.name}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">银行卡号：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.numberOfAccount}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">清算日期：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.commitTime}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">订单合同编号：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.serialNumber}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">清分账号：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.debitAccount}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">结果反馈码：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.resultCode}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">状态：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.status}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">交易金额：</label>

                    <div class="col-md-4 input-group">
                        <span class="cont">${this.billsImport?.actualAmountOfCredit}元</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">证件号：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.numberOfCertificate}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label">结果原因：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.resultReason}</span>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

                <div class="form-group">
                    <label class="col-md-4 control-label">失败原因：</label>

                    <div class="col-md-4">
                        <span class="cont">${this.billsImport?.failReason}</span>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-4 control-label">创建日期：</label>

                    <div class="col-md-4">
                        <span class="cont">
                            <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${this.billsImport?.createdDate}"/>
                        </span>
                    </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <label class="col-md-4 control-label">修改日期：</label>

                    <div class="col-md-4">

                        <span class="cont">
                            <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${this.billsImport?.modifiedDate}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

