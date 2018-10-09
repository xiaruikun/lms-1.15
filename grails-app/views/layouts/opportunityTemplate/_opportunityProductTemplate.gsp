<div class="row" id="fifth">
    <div class="hpanel hgreen">
        <div class="panel-heading">
            <div class="panel-tools">
              <g:if test="${this.canInterestEdit}">
                <g:link class="btn btn-success btn-xs" controller="opportunityProduct" action="eventEvaluate"
                        id="${this?.opportunity?.id}"><i
                        class="fa fa-calculator"></i> 计算费用</g:link>
                <g:link class="btn btn-info btn-xs" controller="opportunityProduct"
                        params="[opportunity: this?.opportunity?.id, productAccount: this?.opportunity?.productAccount?.id]"
                        action="create"><i class="fa fa-plus"></i>新增</g:link>
                </g:if>
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            费用
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        <g:sortableColumn property="executionSequence"
                                          title="${message(code: 'opportunityFlow.executionSequence.label', default: '费率类型')}"/>
                        <g:sortableColumn property="executionSequence"
                                          title="${message(code: 'opportunityFlow.executionSequence.label', default: '费率（%）')}"/>
                        <g:sortableColumn property="stage"
                                          title="${message(code: 'opportunityFlow.provider.label', default: '收费方式')}"/>
                        <g:sortableColumn property="stage"
                                          title="${message(code: 'opportunityFlow.createdDate.label', default: '上扣息月份数')}"/>
                        <g:sortableColumn property="contractType" title="合同类型"/>
                        <g:sortableColumn property="createBy"
                                          title="${message(code: 'component.createBy.label', default: '创建人')}"/>
                        <g:sortableColumn property="modifyBy"
                                          title="${message(code: 'component.modifyBy.label', default: '修改人')}"/>
                        <g:sortableColumn property="createdDate" title="创建时间"></g:sortableColumn>
                        <g:sortableColumn property="modifiedDate" title="修改时间"></g:sortableColumn>
                        <g:if test="${this.canInterestEdit}">
                        <g:sortableColumn width="10%" colspan="3" property="buildTime" title="操作"/>
                      </g:if>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${this?.opportunity?.parent && this?.opportunity?.type?.code == '30'}">
                        <g:each in="${com.next.OpportunityProduct.findAllByOpportunityAndProduct(this?.opportunity?.parent, this?.opportunity?.parent?.productAccount)}">
                            <tr>
                                <td>
                                    ${it?.productInterestType?.name}
                                </td>
                                <td><g:formatNumber number="${it.rate}"
                                                    maxFractionDigits="9"/></td>
                                 <td>
                                     <g:if test="${it?.installments}">
                                        按月收
                                    </g:if>
                                    <g:else>
                                         一次性收
                                    </g:else>
                                </td>
                                <td>${it?.firstPayMonthes}</td>
                                <td>${it?.contractType?.name}</td>
                                <td>
                                    ${it?.createBy}
                                </td>
                                <td>
                                    ${it?.modifyBy}
                                </td>
                                <td><g:formatDate date="${it?.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate></td>
                                <td><g:formatDate date="${it?.modifiedDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate></td>
                                <g:if test="${this.canInterestEdit}">
                                    <td>
                                        <g:link class="btn btn-primary btn-outline btn-xs" resource="${it}"
                                                action="edit"
                                                id="${it?.id}"><i
                                                class="fa fa-edit"></i> 编辑</g:link>

                                    </td>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                    class="fa fa-trash-o"></i> 删除</button>
                                        </g:form>
                                    </td>
                            </g:if>
                            </tr>
                        </g:each>
                    </g:if>
                    <g:else>
                        <g:each in="${opportunityProduct}">
                            <tr>
                                <td>
                                    ${it?.productInterestType?.name}
                                </td>
                                <td><g:formatNumber number="${it.rate}"
                                                    maxFractionDigits="9"/></td>
                                <td>
                                    <g:if test="${it?.installments}">
                                        按月收
                                    </g:if>
                                    <g:else>
                                         一次性收
                                    </g:else>
                                </td>
                                <td>${it?.firstPayMonthes}</td>
                                <td>${it?.contractType?.name}</td>
                                <td>
                                    ${it?.createBy}
                                </td>
                                <td>
                                    ${it?.modifyBy}
                                </td>
                                <td><g:formatDate date="${it?.createdDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate></td>
                                <td><g:formatDate date="${it?.modifiedDate}"
                                                  format="yyyy-MM-dd HH:mm:ss"></g:formatDate></td>
                                <g:if test="${this.canInterestEdit}">

                                    <td>
                                    <g:link class="btn btn-primary btn-outline btn-xs" resource="${it}"
                                            action="edit"
                                            id="${it?.id}"><i
                                            class="fa fa-edit"></i> 编辑</g:link>

                                </td>
                                <td>
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </td>
                                </g:if>                 
                                
                            </tr>
                        </g:each>
                    </g:else>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
