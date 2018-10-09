<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'transactionRecord.label', default: 'TransactionRecord')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show"
                                id="${this.oid}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                交易记录
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link class="btn btn-info btn-xs" action="edit" params="[opportunity: this.oid]" resource="${this.transactionRecord}"><i
                            class="fa fa-edit"></i>编辑</g:link>
                </div>
                交易记录
            </div>

            <div class="panel-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        %{--<label class="col-md-4 control-label">交易类型：</label>--}%

                        %{--<div class="col-md-4">--}%
                        %{--<span class="cont">${this.transactionRecord?.type?.name}</span>--}%
                        %{--</div>--}%
                        <label class="col-md-4 control-label">金额：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.amount}万元</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">借方账户名：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.debit?.name}</span>
                        </div>

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">借方帐号：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.debit?.numberOfAccount}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">贷方账户名：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.credit?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">贷方帐号：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.credit?.numberOfAccount}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">预计开始时间：</label>

                        <div class="col-md-4">
                            <span class="cont"><g:formatDate date="${this.transactionRecord?.plannedStartTime}"
                                                             format="yyyy-MM-dd HH:mm:ss"></g:formatDate></span>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">预计完成时间：</label>

                        <div class="col-md-4">
                            <span class="cont"><g:formatDate date="${this.transactionRecord?.plannedEndTime}"
                                                             format="yyyy-MM-dd HH:mm:ss"></g:formatDate></span>

                        </div>
                    </div>

                    <!--<div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">开始时间：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled" type="text" value="<g:formatDate
                            date="${this.transactionRecord?.startTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">>
                            </div>
                            <label class="col-md-4 control-label">结束时间：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled" type="text" value="<g:formatDate
                            date="${this.transactionRecord?.endTime}" format="yyyy-MM-dd HH:mm:ss"></g:formatDate>">>
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">交易状态：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.transactionRecord?.status?.name}">
                            </div>
                            <label class="col-md-4 control-label">创建人：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.transactionRecord?.createdBy}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">修改人：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.transactionRecord?.modifiedBy}">
                            </div>
                            <label class="col-md-4 control-label">创建时间：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.transactionRecord?.createdDate}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-md-4 control-label">修改时间：</label>

                            <div class="col-md-4">
                                <input class="form-control" disabled="disabled"
                                       type="text" value="${this.transactionRecord?.modifiedDate}">
                            </div>

                        </div> -->

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">实际完成时间：</label>

                        <div class="col-md-4">
                            <span class="cont"><g:formatDate date="${this.transactionRecord?.endTime}"
                                                             format="yyyy-MM-dd HH:mm:ss"></g:formatDate></span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">清分账号：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.debitAccount}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">扣款方式：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.repaymentMethod?.name}</span>
                        </div>
                    </div>


                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">交易状态：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.status?.name}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">创建人：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.createdBy}</span>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">修改人：</label>

                        <div class="col-md-4">
                            <span class="cont">${this.transactionRecord?.modifiedBy}</span>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">创建时间：</label>

                        <div class="col-md-4">
                            <span class="cont"><g:formatDate date="${this.transactionRecord?.createdDate}"
                                                             format="yyyy-MM-dd HH:mm:ss"></g:formatDate></span>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-4 control-label">修改时间：</label>

                        <div class="col-md-4">
                            <span class="cont"><g:formatDate date="${this.transactionRecord?.modifiedDate}"
                                                             format="yyyy-MM-dd HH:mm:ss"></g:formatDate></span>

                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>