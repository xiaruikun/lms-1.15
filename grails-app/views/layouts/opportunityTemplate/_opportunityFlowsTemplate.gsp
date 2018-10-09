<div class="row">
    <div class="hpanel hgreen collapsed">
        <div class="panel-heading hbuilt">
            <div class="panel-tools">
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            订单跟踪
        </div>

        <div class="panel-body no-padding">
            <div class="v-timeline vertical-container animate-panel" data-child="vertical-timeline-block"
                 data-delay="1">
                <g:each in="${this.opportunityFlows}">
                    <g:each in="${this.opportunityRoles}" var="role">
                        <g:if test="${it?.executionSequence < currentFlow?.executionSequence && it?.endTime}">
                            <g:if test="${it?.stage == role?.stage && role?.teamRole?.name == 'Approval'}">
                                <div class="vertical-timeline-block">
                                    <div class="vertical-timeline-icon navy-bg">
                                        <i class="fa fa-calendar text-primary"></i>
                                    </div>

                                    <div class="vertical-timeline-content">
                                        <div class="p-sm">
                                            <span class="vertical-date pull-right">${role?.user} <br/> <small><g:formatDate
                                                    format="yyyy-MM-dd HH:mm:ss" date="${it?.endTime}"/></small>
                                            </span>

                                            <h2>${it?.stage?.name}</h2>
                                        </div>
                                    </div>
                                </div>
                            </g:if>
                        </g:if>
                    </g:each>

                </g:each>
            </div>
        </div>
    </div>
</div>