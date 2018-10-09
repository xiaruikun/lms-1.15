<div class="row">
    <div class="hpanel hgreen collapsed">
        <div class="panel-heading hbuilt">
            <div class="panel-tools">
                <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                    <g:link class="btn btn-info btn-xs" controller="opportunityTeam" action="create"
                            params="[opportunity: this.opportunity?.id]"><i class="fa fa-plus"></i>新增</g:link>
                </sec:ifAllGranted>
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            用户
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <g:sortableColumn property="user"
                                          title="${message(code: 'opportunityTeam.user.label', default: '用户名')}"></g:sortableColumn>
                        <g:sortableColumn property="createDate"
                                          title="${message(code: 'opportunityTeam.createDate.label', default: '创建时间')}"></g:sortableColumn>

                        <g:sortableColumn property="modifiedDate"
                                          title="${message(code: 'opportunityTeam.modifiedDate.label', default: '修改时间')}"></g:sortableColumn>
                        <g:sortableColumn property="opportunityLayout"
                                          title="${message(code: '布局')}"></g:sortableColumn>
                        <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                            <g:sortableColumn width="8%" class="text-center" property="option"
                                              title="${message(code: '操作')}"></g:sortableColumn>
                        </sec:ifAllGranted>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${this.opportunityTeams}">
                        <tr>
                            <td>${it.user}</td>
                            <td>
                                <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.createdDate}"/>
                            </td>
                            <td>
                                <g:formatDate format="yyyy-MM-dd HH:mm:ss" date="${it.modifiedDate}"/>
                            </td>
                            <td>${it?.opportunityLayout?.description}</td>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <td width="8%" class="text-center">
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button"><i
                                                class="fa fa-trash-o"></i> 删除</button>
                                    </g:form>
                                </td>
                            </sec:ifAllGranted>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>