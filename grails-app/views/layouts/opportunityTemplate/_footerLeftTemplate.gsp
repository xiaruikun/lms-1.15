<div class="pull-left m-b-xs">
    <g:if test="${this.subUsers?.size() > 0}">
        <g:form controller="opportunity" action="transferRole">
            <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
            <g:select name="user" id="user" from="${this.subUsers}" optionKey="id"/>
            <button class="btn btn-sm btn-success m-l-sm" type="submit" name="type" value="temp"><i
                    class="fa fa-exchange"></i> 临时委派</button>
            <button class="btn btn-sm btn-info m-l-sm" type="submit" name="type" value="total"><i
                    class="fa fa-exchange"></i> 完全委派</button>
        </g:form>
    </g:if>
</div>
<div class="pull-left m-b-xs m-r-sm">
    <g:if test="${this.opportunity?.stage?.name == "减免审核已完成（结清）"}">
        <g:form class="rollbackOpportunityStage" controller="opportunity" action="rollbackOpportunityStage">
            <input class="form-control" name="opportunity" value="${this.opportunity?.id}" type="hidden">
            <button class="btn btn-sm btn-success m-l-sm" type="button" id="rollbackBtn">订单阶段回退</button>
        </g:form>
    </g:if>
</div>
<script>
    $("#rollbackBtn").click(function () {
        swal({
                title: "划扣未成功，退回贷后部重新确定还款日?",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确认",
                closeOnConfirm: false,
                cancelButtonText: "取消",
            },
            function(isConfirm){
                if (isConfirm) {
                    $(".rollbackOpportunityStage").submit();
                }

                /*function(){
                swal("回退成功!", "订单回退成功", "success");
                $("#rollbackOpportunityStage").submit();*/
            });
    })
</script>