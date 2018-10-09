<!DOCTYPE html>
<html>

<head>
    %{--<meta name="layout" content="main"/>--}%
    <g:set var="entityName" value="${message(code: 'billsItem.label', default: 'BillsItem')}" />
    <title>结清证明</title>
    <asset:stylesheet src="homer/vendor/fontawesome/css/font-awesome.min.css"/>
    <asset:stylesheet src="homer/vendor/bootstrap/dist/css/bootstrap.min.css"/>
    <asset:stylesheet src="homer/style.css"/>
    <style>
    body{
        color:#333;
    }
    .underline{
        text-decoration: underline;
    }
    .tab-content{
        line-height: 34px;
        font-size: 22px;
    }
    .footer-left{
        margin-right: 200px;
    }
    .tab-content p{
         text-indent:20px;
    }
    </style>
</head>

<body>

<div class="content animate-panel">
    <div class="hpanle-container">
        <div class="hpanel">
        <ul class="nav nav-tabs">
            <li><button id="printBtn" class="btn btn-sm btn-primary"><i class="fa fa-print"></i> 打印</button></li>
        </ul>
        <div class="tab-content">
            <h2 class="text-center">中佳信-（<span class="font-uppercase">${map?.city}</span>）结清证明</h2>
            <p class="m-t-lg">
                 借款人
            <span class="underline">${map?.fullName}</span>于
            <span class="underline">${map?.actualLendingDate}</span>通过我司向出借人申请贷款人民币<span class="underline">${map?.principal}</span>，
            合同编号：<span class="underline">${map?.serialNumber}</span> 。
            现借款人已将该笔贷款本息全部偿还完毕。
            </p>
            <h3 class="m-t-md">特此证明！</h3>
            <h3 class="m-t-xxxl">
                <span class="footer-left">财务经办人：</span>
                <span class="">财务负责人：</span>
            </h3>
        </div>
    </div>
    </div>
    

</div>
<asset:javascript src="homer/vendor/jquery/dist/jquery.min.js"/>
<asset:javascript src="homer/vendor/bootstrap/dist/js/bootstrap.min.js"/>
<script>
    $(function(){
        $("#printBtn").click(function(){
            $(this).closest("ul.nav").addClass("hide");
            javascript:window.print();
            $(this).closest("ul.nav").removeClass("hide");
        })
    })
</script>
</body>

</html>
