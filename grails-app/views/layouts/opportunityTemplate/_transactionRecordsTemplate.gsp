<div class="row">
    <div class="hpanel hgreen">
        <div class="panel-heading">
            <div class="panel-tools">
                <sec:ifAnyGranted roles="ROLE_ADMINISTRATOR">
                    <g:if test="${transactionRecords?.size() > 0}">
                    <g:link class="btn btn-info btn-xs" controller="transactionRecord"
                            params="[opportunity: this.opportunity?.id]"
                            onclick="return confirm('确定删除？');"  action="deleteAll"><i class="fa fa-plus-square-o"></i>删除</g:link>
                    </g:if>
                    </sec:ifAnyGranted>
                <g:link class="btn btn-info btn-xs" controller="transactionRecord" action="create"
                        params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            交易记录
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        %{--<g:sortableColumn property="type" title="交易类型"></g:sortableColumn>--}%
                        <g:sortableColumn property="repaymentMethod" title="扣款方式"></g:sortableColumn>
                        <g:sortableColumn property="amount" title="金额（万元）"></g:sortableColumn>
                        <g:sortableColumn property="debit.name" title="借方账户名"></g:sortableColumn>
                        <g:sortableColumn property="debit.numberOfAccount" title="借方帐号"></g:sortableColumn>

                        <g:sortableColumn property="credit.name" title="贷方账户名"></g:sortableColumn>
                        <g:sortableColumn property="credit.numberOfAccount" title="贷方帐号"></g:sortableColumn>

                        <g:sortableColumn property="plannedStartTime" title="预计开始时间"></g:sortableColumn>
                        <g:sortableColumn property="plannedEndTime" title="预计完成时间"></g:sortableColumn>
                        <!-- <g:sortableColumn property="startTime" title="开始时间"></g:sortableColumn> -->
                        <g:sortableColumn property="endTime" title="实际完成时间"></g:sortableColumn>
                        <g:sortableColumn property="debitAccount" title="清分账号"></g:sortableColumn>
                        <g:sortableColumn property="status" title="交易状态"></g:sortableColumn>

                        <g:sortableColumn property="createdBy" title="创建人"></g:sortableColumn>
                        <g:sortableColumn property="modifiedBy" title="修改人"></g:sortableColumn>
                        <g:sortableColumn property="createdDate" title="创建时间"></g:sortableColumn>
                        <g:sortableColumn property="modifiedDate" title="修改时间"></g:sortableColumn>

                        <g:sortableColumn property="operation" title="操作"></g:sortableColumn>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${this.transactionRecords}">
                        <g:if test="${it?.status.name != '已失效'}">
                        <tr>
                           %{-- <td>
                                <g:link controller="transactionRecord" action="show"
                                        id="${it?.id}">${it?.type?.name}</g:link>
                                <!-- ${it?.type?.name} -->
                            </td>--}%
                            <td>
                                <g:link controller="transactionRecord" action="show"
                                        params="[opportunity: this.opportunity?.id]" id="${it?.id}">${it?.repaymentMethod?.name}</g:link>
                            </td>
                            <td>
                                ${it?.amount}
                            </td>
                            <td>
                                ${it?.debit?.name}
                            </td>
                            <td>
                                ${it?.debit?.numberOfAccount}
                            </td>
                            <td>
                                ${it?.credit?.name}
                            </td>
                            <td>
                                ${it?.credit?.numberOfAccount}
                            </td>

                            <td>
                                <g:formatDate date="${it?.plannedStartTime}"
                                              format="yyyy-MM-dd"></g:formatDate>
                            </td>
                            <td>
                                <g:formatDate date="${it?.plannedEndTime}"
                                              format="yyyy-MM-dd"></g:formatDate>
                            </td>
                            <!-- <td>
                                    ${it?.startTime}
                                </td> -->
                            <td>
                                <g:formatDate date="${it?.endTime}"
                                              format="yyyy-MM-dd"></g:formatDate>
                            </td>
                            <td>
                                ${it?.debitAccount}
                            </td>
                            <td>
                                ${it?.status?.name}
                            </td>
                            <td>
                                ${it?.createdBy}
                            </td>
                            <td>
                                ${it?.modifiedBy}
                            </td>
                            <td>
                                <g:formatDate date="${it?.createdDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </td>
                            <td>
                                <g:formatDate date="${it?.modifiedDate}"
                                              format="yyyy-MM-dd HH:mm:ss"></g:formatDate>
                            </td>

                            <td>
                                <g:form resource="${it}" method="DELETE">
                                    <input type="hidden" name="oid" value="${this?.opportunity?.id}">
                                    <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                            class="fa fa-trash-o"></i> 删除</button>
                                </g:form>
                            </td>
                        </tr>
                        </g:if>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>