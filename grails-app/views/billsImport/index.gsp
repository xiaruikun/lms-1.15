<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'billsImport.label', default: 'BillsImport')}"/>
    <title>导入流水</title>
    <style>
    .fileinput-button {
        position: relative;
        overflow: hidden;
        display: inline-block;
    }

    .fileinput-button input {
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        margin: 0;
        opacity: 0;
        width: 100%;
    }
    </style>
</head>

<body>
<div class="small-header transition animated fadeIn">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">银行流水列表</li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                银行流水
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <g:if test="${flash.message}">
        <div class="row">
            <div class="message alert alert-info" role="status">${flash.message}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
            </div>
        </div>
    </g:if>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="hpanel">

                <div class="panel-heading">
                    导入银行回盘数据
                </div>

                <div class="panel-body">

                    <g:form controller="billsImport" action="upload" method="post" enctype="multipart/form-data"
                            class="csvUpload">
                        <a href="javascript:;" class="fileinput-button btn btn-xs btn-success">
                            <i class="fa fa-plus"></i>  选择文件
                            <input type="file" name="csv" id="csvFile"/>
                        </a>
                        <label class="fileName"></label>
                        <input type="button" id="savaBtn" value="导入" class="btn btn-primary btn-xs"/>
                        <span><code>文件大小不能超过15M，文件格式为csv</code></span>
                    </g:form>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <g:form method="POST" action="searchBills">
            <div class="hpanel hblue">
                <div class="panel-heading">
                    <div class="panel-tools">
                        <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                        <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                class="fa fa-times"></i>重置</button>
                    </div>
                    查询
                </div>

                <div class="panel-body seach-group">
                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="账户名" name="name" id="name" value="${billsImport?.name}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="清算日期" name="commitTime"       id="commitTime" value="${billsImport?.commitTime}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="合同编号" name="serialNumber" id="serialNumber" value="${billsImport?.serialNumber}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="清分账号" name="debitAccount" id="debitAccount" value="${billsImport?.debitAccount}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="反馈原因" name="resultReason"       id="resultReason" value="${billsImport?.resultReason}">
                    </div>

                    <div class="col-md-3">
                        <input type="text" class="form-control" placeholder="失败原因" name="failReason"       id="failReason" value="${billsImport?.failReason}">
                    </div>

                    <div class="col-md-3">
                    <g:select class="form-control" name="status" id="status"
                              from="${['成功', '失败', '待处理']}"
                              valueMessagePrefix="status"  noSelection="${['': '请选择状态']}"/>
                    </div>
                </div>
            </div>
        </g:form>
    </div>

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="deal" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>执行</g:link>
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                    <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                </div>
                银行流水
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name"
                                              title="${message(code: 'billsImport.name.label', default: '账户名')}"/>
                            <!--<g:sortableColumn property="numberOfCertificate"
                                                  title="${message(code: 'billsImport.numberOfCertificate.label', default: '开户身份证')}"/> -->
                            <g:sortableColumn property="numberOfAccount"
                                              title="${message(code: 'billsImport.numberOfAccount.label', default: '银行卡号')}"/>
                            <g:sortableColumn property="actualAmountOfCredit"
                                              title="${message(code: 'billsImport.actualAmountOfCredit.label', default: '交易金额（元）')}"/>
                            <g:sortableColumn property="commitTime"
                                              title="${message(code: 'billsImport.commitTime.label', default: '清算日期')}"/>
                            <g:sortableColumn property="serialNumber"
                                              title="${message(code: 'billsImport.serialNumber.label', default: '合同编号')}"/>
                            <g:sortableColumn property="opportunityNumber"
                                              title="${message(code: 'billsImport.opportunityNumber.label', default: '订单编号')}"/>
                            <g:sortableColumn property="debitAccount"
                                              title="${message(code: 'billsImport.debitAccount.label', default: '清分账号')}"/>
                            <g:sortableColumn property="resultReason"
                                              title="${message(code: 'billsImport.resultReason.label', default: '反馈原因')}"/>
                            <g:sortableColumn property="status"
                                              title="${message(code: 'billsImport.status.label', default: '状态')}"/>
                            <g:sortableColumn property="resultReason"
                                              title="${message(code: 'billsImport.resultReason.label', default: '失败原因')}"/>
                            <g:sortableColumn property="status"
                                              title="操作"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${billsImportList}">
                            <tr>
                                <td><g:link action="show" id="${it.id}" class="firstTd">${it.name}</g:link></td>
                                <!--<td>${it.numberOfCertificate}</td> -->
                                <td>${it.numberOfAccount}</td>
                                <td>${it.actualAmountOfCredit}</td>
                                <td><g:formatDate date="${it?.commitTime}" format="yyyy-MM-dd"></g:formatDate></td>
                                <td>${it.serialNumber}</td>
                                <td>
                                    <g:if test="${it.opportunityNumber == null}">${it.opportunityNumber}</g:if>
                                    <g:else><g:link controller="opportunity" action="show" id="${com.next.Opportunity.findBySerialNumber(it.opportunityNumber)?.id}">${it.opportunityNumber}</g:link></g:else>
                                </td>
                                <td>${it.debitAccount}</td>
                                <td>${it.resultReason}</td>
                                <td>${it.status}</td>
                                <td>${it.failReason}</td>
                                <td>
                                    <g:form controller="billsImport" action="delete" id="${it.id}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-outline btn-xs" type="button">
                                            <i class="fa fa-trash-o"></i> 删除
                                        </button>
                                    </g:form>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </div>


            <div class="panel-footer">
                <div class="pagination">
                    <g:paginate total="${billsImportCount ?: 0}" params="${params}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('#csvFile').change(function () {
            var file = document.getElementById("billsFile");
            var fileName = $("#csvFile").val();
            var pos = fileName.lastIndexOf("\\");
            var fileText = fileName.substring(pos + 1);
            $(".fileName").text(fileText);
            var pos2 = fileName.lastIndexOf(".");
            var fileType = fileName.substring(pos2 + 1);
            if (!(fileType == "csv")) {
                swal("文件格式为csv", '', "error");
                $("#savaBtn").attr('disabled', 'disabled')
                return;
            } else {
                $("#savaBtn").removeAttr('disabled', 'disabled')
            }
        });
        $("#savaBtn").click(function () {
            var file = document.getElementById("billsFile");
            var fileName = $("#csvFile").val();
            if (!fileName) {
                swal("请先上传csv文件", '', "error");
                return;
            }
            $(".csvUpload").submit();

        });

    })
</script>
</body>
</html>