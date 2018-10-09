%{--无新增按钮--}%
<div class="row" id="third">
    <div class="hpanel hyellow">
        <div class="panel-heading">
            <div class="panel-tools">
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            房产信息
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover text-center">
                    <thead>
                    <tr>
                        <g:sortableColumn property="externalId" title="外部唯一ID"></g:sortableColumn>
                        <g:sortableColumn property="propertySerialNumber" title="房产证编号"></g:sortableColumn>
                        <g:sortableColumn property="city" title="城市"></g:sortableColumn>
                        <g:sortableColumn property="district" title="区县"></g:sortableColumn>
                        <g:sortableColumn property="district" title="所在区域"></g:sortableColumn>

                        <g:sortableColumn property="projectName" title="小区名称"></g:sortableColumn>
                        <g:sortableColumn property="address" title="地址"></g:sortableColumn>
                        <g:sortableColumn property="orientation" title="朝向"></g:sortableColumn>
                        <g:sortableColumn property="area" title="面积（m2）"></g:sortableColumn>
                        <g:sortableColumn property="building" title="楼栋"></g:sortableColumn>
                        <g:sortableColumn property="unit" title="单元"></g:sortableColumn>
                        <g:sortableColumn property="roomNumber" title="户号"></g:sortableColumn>
                        <g:sortableColumn property="floor" title="所在楼层"></g:sortableColumn>
                        <g:sortableColumn property="totalFloor" title="总楼层"></g:sortableColumn>
                        <g:sortableColumn property="assetType" title="房产类型"></g:sortableColumn>
                        <g:sortableColumn property="houseType" title="物业类型"></g:sortableColumn>
                        <g:sortableColumn property="specialFactors" title="特殊因素"></g:sortableColumn>
                        <g:sortableColumn property="unitPrice" title="批贷房产单价（元）"></g:sortableColumn>
                        <g:sortableColumn property="totalPrice" title="批贷房产总价（万元）"></g:sortableColumn>
                        <g:sortableColumn property="status" title="状态"></g:sortableColumn>
                        <g:sortableColumn property="loanToValue" title="抵押率（%）"></g:sortableColumn>
                        <g:sortableColumn property="firstMortgageAmount" title="一抵金额(万元)"></g:sortableColumn>
                        <g:sortableColumn property="secondMortgageAmount" title="二抵金额(万元)"></g:sortableColumn>
                        <g:sortableColumn property="typeOfFirstMortgage" title="一抵性质"></g:sortableColumn>
                        <g:sortableColumn property="mortgageType" title="抵押类型"></g:sortableColumn>
                        <g:sortableColumn property="projectType" title="立项类型"></g:sortableColumn>
                        <g:sortableColumn property="buildTime" title="建成时间"></g:sortableColumn>
                        <g:sortableColumn property="buildTime" title="房龄(年)"></g:sortableColumn>
                        <g:sortableColumn property="buildTime" title="最新总价(万元)"></g:sortableColumn>
                        <g:sortableColumn property="buildTime" title="估值等级"></g:sortableColumn>
                        <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(this.opportunity,this.opportunity.stage)?.executionSequence}">
                            <g:sortableColumn property="buildTime" title="操作"></g:sortableColumn>
                        </g:if>
                        <g:else>
                            <g:sortableColumn colspan="2" property="buildTime" title="操作"></g:sortableColumn>
                        </g:else>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${this.collaterals}">
                        <tr>
                            <td class="collateralsExternalId">
                                %{--<g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.endTime != null}">
                                    ${it.externalId}
                                </g:if>
                                <g:else>
                                    <g:link controller="collateral" action="show"
                                            id="${it.id}">${it.externalId}</g:link>

                                </g:else>--}%
                                <g:link controller="collateral" action="show"
                                        id="${it.id}">${it.externalId}</g:link>
                            </td>
                            <td>
                                ${it?.propertySerialNumber}
                            </td>
                            <td>
                                ${it.city?.name}
                            </td>

                            <td>
                                ${it.district}
                            </td>
                            <td>${it.region?.name}</td>
                            <td>
                                ${it.projectName}
                            </td>
                            <td>
                                ${it.address}
                            </td>
                            <td>
                                ${it.orientation}
                            </td>
                            <td class="area">
                                ${it.area}
                            </td>
                            <td>
                                ${it.building}
                            </td>
                            <td>
                                ${it.unit}
                            </td>
                            <td>
                                ${it.roomNumber}
                            </td>
                            <td>
                                ${it.floor}
                            </td>
                            <td>
                                ${it.totalFloor}
                            </td>
                            <td>
                                ${it.assetType}
                            </td>
                            <td>
                                ${it.houseType}
                            </td>
                            <td>
                                ${it.specialFactors}
                            </td>
                            <td>
                                <g:formatNumber number="${it?.unitPrice}" type="number" maxFractionDigits="2"
                                                minFractionDigits="2"/>

                            </td>
                            <td>
                                <g:formatNumber number="${it?.totalPrice}" type="number" maxFractionDigits="2"
                                                minFractionDigits="2"/>
                            </td>
                            <td>
                                ${it.status}
                            </td>
                            <td>
                                <g:formatNumber number="${it?.loanToValue}" type="number" maxFractionDigits="2"
                                                minFractionDigits="2"/>

                            </td>
                            <td>
                                ${it?.firstMortgageAmount}
                            </td>
                            <td>
                                ${it?.secondMortgageAmount}
                            </td>
                            <td>
                                ${it?.typeOfFirstMortgage?.name}
                            </td>
                            <td>
                                ${it?.mortgageType?.name}
                            </td>
                            <td>
                                ${it?.projectType?.name}
                            </td>
                            <td>
                                <g:formatDate format="yyyy" date="${it.buildTime}"/>
                            </td>
                            <td>
                                <g:if test="${it?.buildTime}">
                                    ${new Date().format("yyyy").toInteger().minus(it?.buildTime.format("yyyy").toInteger())}
                                </g:if>
                                <g:else>
                                    ${it?.buildTime}
                                </g:else>
                            </td>
                            <td class="latestCollateralPrice"></td>
                            <td class="valuationReliability"></td>
                            %{--<g:if test="${this.canQueryPrice}">
                                <td>

                                    <g:link class="btn btn-primary btn-outline btn-xs" controller="collateral"
                                            action="pvQuery"
                                            id="${it?.id}">询值</g:link>

                                </td>
                            </g:if>--}%
                            <td>
                                <g:link class="btn btn-primary btn-xs btn-outline" controller="opportunity"
                                        action="historyShow2"
                                        id="${it?.id}">查询明细</g:link>
                            <button class="m-t-xs btn btn-primary btn-xs btn-outline" data-toggle="modal" data-target="#applyForAmendment">申请修改</button>
                            </td>
                            <g:if test="${this.canQueryPrice}">
                                <g:if test="${com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,com.next.OpportunityStage.findByName('审批已完成'))?.executionSequence < com.next.OpportunityFlow.findByOpportunityAndStage(it.opportunity,it.opportunity.stage)?.executionSequence}">
                                </g:if>
                                <g:else>
                                    <td>
                                        <g:form resource="${it}" method="DELETE">
                                            <button class="deleteBtn btn btn-danger btn-xs btn-outline"
                                                    type="button">删除</button>
                                        </g:form>

                                    </td>
                                </g:else>
                            </g:if>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>