<div class="row">
    <div class="hpanel hgreen collapsed">
        <div class="panel-heading hbuilt">
            <div class="panel-tools">
                <a class="showhide">
                    <i class="fa fa-chevron-up"></i>
                </a>
            </div>
            风险调查报告
        </div>

        <div class="panel-body form-horizontal field">
            <g:each in="${this.opportunityFlexFieldCategorys}" var="category">
                <g:if test="${category?.flexFieldCategory?.name in ['抵押物情况', '借款人资质小结', '征信小结', '大数据小结', '借款用途', '还款来源', '风险结论', '放款前要求', '罚息约定']}">
                    <div class="fieldBox border-bottom">
                        <h5>${category?.flexFieldCategory?.name}</h5>
                        <g:each in="${category.fields}" var="field">
                            <div class="form-group">
                                <label class="col-md-2 control-label">${field?.name}</label>

                                <div class="col-md-8">
                                    <p class="form-control-static">${field?.value}</p>
                                </div>
                            </div>
                        </g:each>
                    </div>
                </g:if>
            </g:each>

        </div>
    </div>
</div>