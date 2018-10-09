<!DOCTYPE html>
<html lang="en">

<head>
    %{--<meta name="layout" content="main"/>--}%
    <asset:stylesheet src="homer/vendor/bootstrap/dist/css/bootstrap.min.css"/>
    <asset:stylesheet src="homer/style.css"/>
    <g:set var="entityName" value="${message(code: 'opportunity.label', default: 'Opportunity')}"/>
    <title>借款申请表</title>
    <style>
    body {
        font-family: '宋体';
        color: #000;
    }

    .table-bordered {
        border: 2px solid #1f4e78;
    }

    tr.title {
        font-size: 14px;
        background-color: #9cc2e5;
        font-weight: bold;
    }

    .border-none {
        border: none;
    }

    tr.active {
        height: 50px;

    }

    tr.active td {
        border: none !important;
        background-color: #fff !important;
    }
        td{
            line-height: 1 !important;
        }
    </style>
</head>

<body class="fixed-navbar fixed-sidebar">

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel">
            <div class="panel-heading text-center" style="font-size: 20px">
                借款申请表
            </div>

            <div class="panel-body no-padding">
                <table class="table table-bordered">
                    <tbody>
                    <tr class="title"><td colspan="6">员工信息</td></tr>
                    <tr>
                        <td colspan="1" class="col-md-2">客户经理姓名</td>
                        <td colspan="2"  class="col-md-4">${this.opportunity?.user?.fullName}</td>
                        <td colspan="1"  class="col-md-2">客户经理员工号</td>
                        <td colspan="2"  class="col-md-4">***</td>

                    </tr>
                    <tr class="title"><td colspan="6">申请信息</td></tr>
                    <tr>
                        <td  class="col-md-2">产品名称</td>
                        <td colspan="2"  class="col-md-4">${this.opportunity?.product?.name}</td>
                        <td  class="col-md-2">产品期限</td>
                        <td colspan="2"  class="col-md-4">${this.opportunity?.loanDuration}月</td>
                    </tr>
                    <tr>
                        <td  class="col-md-2">借款用途</td>
                        <td  class="col-md-2">
                            <g:each in="${this.opportunityFlexFieldCategorys}">
                                <g:if test="${it?.flexFieldCategory?.name == '借款用途'}">
                                    <g:each in="${it?.fields}" var="field">
                                        ${field?.value}
                                    </g:each>
                                </g:if>
                            </g:each>
                        </td>
                        <td class="col-md-2">资金需求</td>
                        <td class="col-md-2">${this.opportunity?.requestedAmount}万元</td>
                        <td class="col-md-2">进件城市</td>
                        <td class="col-md-2">${this.opportunity?.collaterals[0]?.city?.name}</td>
                    </tr>
                    <tr class="title">
                        <td colspan="6">个人资料</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">姓名</td>
                        <td class="col-md-2">${this.opportunity?.lender?.fullName}</td>
                        <td class="col-md-2">性别</td>
                        <td class="col-md-2">男</td>
                        <td class="col-md-2">身份证号</td>
                        <td class="col-md-2">${this.opportunity?.lender?.idNumber}</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">婚姻状况</td>
                        <td class="col-md-2">${this.opportunity?.lender?.maritalStatus}</td>
                        <td class="col-md-2">教育程度</td>
                        <td class="col-md-2">**</td>
                        <td class="col-md-2">邮箱</td>
                        <td class="col-md-2">***</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">本市房产状况</td>
                        <td class="col-md-6" colspan="3">**</td>
                        <td class="col-md-2">本市生活年限</td>
                        <td class="col-md-2">**年</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">住宅地址</td>
                        <td class="col-md-6" colspan="3">***</td>
                        <td class="col-md-2">邮编</td>
                        <td class="col-md-2">***</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">户籍地址</td>
                        <td class="col-md-6" colspan="3">***</td>
                        <td class="col-md-2">住宅电话</td>
                        <td class="col-md-2">***</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">手机号码1</td>
                        <td class="col-md-4" colspan="2">${this.opportunity?.lender?.cellphone}</td>
                        <td class="col-md-2">手机号码2</td>
                        <td class="col-md-4" colspan="2">***</td>
                    </tr>
                    <tr class="title">
                        <td colspan="6">还款银行卡信息</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">所属银行</td>
                        <td class="col-md-4" colspan="2">${this.opportunity?.bankAccounts[0]?.type?.name}</td>
                        <td class="col-md-2">银行账号</td>
                        <td class="col-md-4" colspan="2">%{--${this.opportunity?.bankAccounts[0]?.numberOfAccount}--}%</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">开户行所在地</td>
                        <td class="col-md-10" colspan="5">%{--${this.opportunity?.bankAccounts[0]?.bankAddress}--}%</td>
                    </tr>
                    <tr class="title">
                        <td colspan="6">工作信息</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">公司名称</td>
                        <td class="col-md-10" colspan="5">中佳信</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">公司地址</td>
                        <td class="col-md-10" colspan="5">酒仙桥</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">公司类型</td>
                        <td class="col-md-2">软件</td>
                        <td class="col-md-2">公司固话</td>
                        <td class="col-md-2">010-58122145</td>
                        <td class="col-md-2">职位</td>
                        <td class="col-md-2">销售经理</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">雇佣关系</td>
                        <td class="col-md-4" colspan="2"></td>
                        <td class="col-md-2">进入现职公司时间</td>
                        <td class="col-md-4" colspan="2">2016-2-15</td>
                    </tr>
                    <tr class="title">
                        <td colspan="6">收支情况</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">年收入/万元</td>
                        <td class="col-md-4" colspan="2">50</td>
                        <td class="col-md-2">每月工作收入/万元</td>
                        <td class="col-md-4" colspan="2">10</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">每月其他收入/元</td>
                        <td class="col-md-2">20000</td>
                        <td class="col-md-2">来源</td>
                        <td class="col-md-2"></td>
                        <td class="col-md-2">供养人数</td>
                        <td class="col-md-2">4</td>
                    </tr>
                    <tr class="title">
                        <td colspan="6">联系人资料</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">联系人1</td>
                        <td class="col-md-2"></td>
                        <td class="col-md-2">姓名</td>
                        <td class="col-md-2"></td>
                        <td class="col-md-2">手机号码</td>
                        <td class="col-md-2"></td>
                    </tr>
                    <tr>
                        <td class="col-md-2">联系人2</td>
                        <td class="col-md-2">李四</td>
                        <td class="col-md-2">姓名</td>
                        <td class="col-md-2">李四</td>
                        <td class="col-md-2">手机号码</td>
                        <td class="col-md-2">1325510055</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">联系人3</td>
                        <td class="col-md-2">李四</td>
                        <td class="col-md-2">姓名</td>
                        <td class="col-md-2">李四</td>
                        <td class="col-md-2">手机号码</td>
                        <td class="col-md-2">1325510055</td>
                    </tr>

                    <tr class="title">
                        <td colspan="6">客户情况备注</td>
                    </tr>
                    <tr>
                        <td colspan="6" style="height: 60px"></td>
                    <tr class="active">
                        <td colspan="6">备注：本人已经仔细阅读填写本文件，已知悉贷款方为中国对外经济贸易信托有限公司</td>
                    </tr>
                    <tr class="active">
                        <td class="col-md-6" colspan="3">申请人签名：</td>
                        <td class="col-md-6" colspan="3">日期</td>
                    </tr>
                    <tr class="active">
                        <td class="col-md-6" colspan="3">客户经理签名：</td>
                        <td class="col-md-6" colspan="3">日期</td>
                    </tr>
                    </tr>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
