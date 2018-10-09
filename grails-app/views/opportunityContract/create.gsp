<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'opportunityContract.label', default: 'OpportunityContract')}"/>
    <title>合同</title>
</head>

<body class="fixed-navbar fixed-sidebar">
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="opportunity" action="show"
                                id="${this.opportunityContract?.opportunity?.id}">订单详情</g:link>
                    </li>
                    <li class="active">
                        <span>新增</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                新增合同
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                新增合同
            </div>

            <div class="panel-body">
                <g:form action="save" class="form-horizontal" name="myForm">
                    <g:if test="${flash.message}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${flash.message}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                                    <g:message error="${error}"/>
                                </li>
                            </g:eachError>
                        </ul>
                    </g:if>
                    <g:hasErrors bean="${this.opportunityContract}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.opportunityContract}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                        error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <input class="form-control" name="opportunity.id" type="hidden" id="opportunity"
                           value="${this.opportunityContract?.opportunity?.id}">

                    <div class="form-group">
                        <label class="col-md-3 control-label">合同编号</label>

                        <div class="col-md-3">
                            <g:textField name="contract.serialNumber"  value=""  class="form-control serialNmuber"/>

                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">合同类型</label>

                        <div class="col-md-3">
                            <g:select name="contract.type.id" id="contractType"
                                      value="${this.opportunityContract?.contract?.type?.id}"
                                      from="${com.next.ContractType.list()}" optionKey="id"
                                      optionValue="name" class="form-control"/>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="contractItem">

                    </div>

                    <div class="hr-line-dashed"></div>

                    <div class="form-group">
                        <div class="col-md-3 col-md-offset-3">
                            %{--<g:submitButton class="btn btn-info" name="create" value="保存"/>--}%
                            <input type="button" class="btn btn-info" name="create" value="保存" onclick="saveContractNumber()"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>

<g:javascript>
    $(function () {
        //$("#userName").val($(".userInfo").text());
        function getContractItems() {
            $.ajax({
                type: "POST",
                url: "/contract/getContractItems",
                data: {
                    contractType: $("#contractType").val(),
                },

                success: function (data) {
                    var contractItemList = data.contractItemList;
                    var content;
                    if (data.status == "success") {
                        $('.contractItem').html("");
                        for (var i = 0; i < contractItemList.length; i++) {
                            content = '<div class="form-group"><label class="col-md-3 control-label">' + contractItemList[i].name + '</label>'
                            if (contractItemList[i].options.length > 0) {
                                content += '<div class="col-md-3"><select class="form-control" name="' + contractItemList[i].name + '">'
                                for (var j = 0; j < contractItemList[i].options.length; j++) {
                                    content += '<option value="' + contractItemList[i].options[j].value + '">' + contractItemList[i].options[j].value + '</option>'
                                }
                                content += '</select></div>'
                            }
                            else {
                                content += '<div class="col-md-3"><textarea rows="5" class="form-control" name="' + contractItemList[i].name + '" value=""></textarea></div>'
                            }
                            content += '<div class="hr-line-dashed"></div></div>';

                            $(".contractItem").append(content)
                        }
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        }

        function getContractNumber() {
            $.ajax({
                type: "POST",
                url: "/opportunityContract/createContractNumber",
                data: {
                    contractType: $("#contractType").val(),
                    opportunity: $("#opportunity").val(),
                },

                success: function (data) {

                    var contractNumber = data.contractNumber;
                    $(".serialNmuber").val(contractNumber)
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });
        }

        getContractItems();
        getContractNumber();
        // 动态子项
        $("#contractType").change(function () {
            getContractItems();
             getContractNumber();
        });

    })
    function saveContractNumber() {
        $.ajax({
            type: "POST",
            url: "/contract/verifyContractNumber",
            data: {
                contractNumber: $(".serialNmuber").val(),
            },

            success: function (data) {
                if (data.status == "success") {
                    $("#myForm").submit()
                } else {
                    swal("合同序号已存在", "", "error");
                }
            },
            error: function () {
                swal("获取失败，请稍后重试", "", "error");
            }
        });
    }
</g:javascript>
</body>
</html>
