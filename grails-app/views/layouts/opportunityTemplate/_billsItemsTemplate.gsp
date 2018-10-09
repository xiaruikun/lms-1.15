<div class="row">
    <div class="hpanel hgreen collapsed">
        <div class="panel-heading hbuilt">
            <div class="panel-tools billsItem">
                <g:if test="${billsItems?.size() > 0}">
                    <g:link class="btn btn-info btn-xs" controller="billsItem" id="${this?.opportunity?.id}"
                            action="download"><i class="fa fa-plus-square-o"></i>还款告知书</g:link>
                    <g:link class="btn btn-info btn-xs" controller="billsItem"
                            params="[opportunity: this.opportunity?.id, bills: billsItems[0]?.bills?.id]"
                            action="create"><i class="fa fa-plus-square-o"></i>添加</g:link>
                    <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR">
                        <g:link class="btn btn-info btn-xs" controller="billsItem"
                                params="[opportunity: this.opportunity?.id]"
                                action="deleteAll"><i class="fa fa-plus-square-o"></i>删除</g:link>
                        <g:link class="btn btn-info btn-xs" controller="billsItem"
                                params="[opportunity: this.opportunity?.id]"
                                action="resetBillsItems"><i class="fa fa-plus-square-o"></i>重置</g:link>
                    </sec:ifAnyGranted>
                </g:if>

                <g:link class="btn btn-info btn-xs" controller="billsItem" id="${this?.opportunity?.id}"
                        action="eventEvaluate"><i class="fa fa-plus-square-o"></i>生成</g:link>
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            还款计划
        </div>


        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        <g:sortableColumn property="period" title="期数"/>
                        <g:sortableColumn property="period" title="开始时间"/>
                        <g:sortableColumn property="period" title="结束时间"/>
                        <g:sortableColumn property="period" title="应还时间（测）"/>
                        <g:sortableColumn property="period" title="实际还款时间"/>
                        <g:sortableColumn property="period" title="应付金额(万元)"/>
                        <g:sortableColumn property="period" title="实收(万元)"/>
                        <g:sortableColumn property="period" title="余额(万元)"/>
                        <g:sortableColumn property="period" title="借方"/>
                        <g:sortableColumn property="period" title="贷方"/>
                        <g:sortableColumn property="period" title="费用类型"/>
                        <g:sortableColumn property="period" title="还款状态"/>
                        <g:sortableColumn property="period" title="是否拆分"/>
                        <g:sortableColumn property="period" title="是否早偿"/>
                        <g:sortableColumn property="period" title="是否逾期"/>
                        <g:sortableColumn colspan="2" property="period" title="操作"/>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${billsItems}">
                        <tr>
                            <td>${it?.period}</td>
                            <td><g:formatDate date="${it?.startTime}" format="yyyy-MM-dd"></g:formatDate></td>
                            <td><g:formatDate date="${it?.endTime}" format="yyyy-MM-dd"></g:formatDate></td>
                            <td><g:formatDate date="${it?.payTime}" format="yyyy-MM-dd"></g:formatDate></td>
                            <td><g:formatDate date="${it?.actualEndTime}" format="yyyy-MM-dd"></g:formatDate></td>
                            <td><g:formatNumber number="${it.receivable}" minFractionDigits="2"
                                                maxFractionDigits="9"/></td>
                            <td><g:formatNumber number="${it.receipts}" minFractionDigits="2"
                                                maxFractionDigits="9"/></td>
                            <td><g:formatNumber number="${it.balance}" minFractionDigits="2"
                                                maxFractionDigits="9"/></td>
                            <td>${it?.debit?.name}</td>
                            <td>${it?.credit?.name}</td>
                            <td>${it?.type?.name}</td>
                            <td>${it?.status}</td>
                            <input type="hidden" value="${it?.id}" name="billItemId">
                            <td><g:checkBox class="billsItemsChangeStatus" name="split" value="${it?.split}"/></td>
                            <td><g:checkBox class="billsItemsChangeStatus" name="prepayment" value="${it?.prepayment}"/></td>
                            <td><g:checkBox class="billsItemsChangeStatus" name="overdue" value="${it?.overdue}"/></td>
                            <td>
                                <g:link controller="billsItem" action="edit"
                                        class="btn btn-xs btn-primary btn-outline"
                                        params="[id: it?.id, opportunity: this?.opportunity?.id]">
                                    <i class="fa fa-edit"></i> 编辑
                                </g:link>
                            </td>
                            <td>
                                <g:form resource="${it}" method="DELETE">
                                    <input type="hidden" name="oid" value="${this?.opportunity?.id}">
                                    <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                            class="fa fa-trash-o"></i> 删除</button>
                                </g:form>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
