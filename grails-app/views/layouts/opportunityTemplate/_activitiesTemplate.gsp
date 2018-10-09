<div class="row" id="seventh">
    <div class="hpanel hgreen collapsed">
        <div class="panel-heading hbuilt">
            <div class="panel-tools">
                <g:if test="${this.opportunity?.contacts.size() > 0}">
                    <g:link class="btn btn-info btn-xs" controller="activity"
                            params="[opportunityId: this.opportunity.id]"
                            action="create"><i class="fa fa-plus"></i>新增</g:link>
                </g:if>
                <a class="showhide"><i class="fa fa-chevron-up"></i></a>
            </div>
            任务指派
        </div>

        <div class="panel-body no-padding">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>

                        <g:sortableColumn property="type"
                                          title="${message(code: 'activity.type.label', default: '活动类型')}"/>
                        <g:sortableColumn property="subtype"
                                          title="${message(code: 'activity.subtype.label', default: '子类型')}"/>
                        <g:sortableColumn property="startTime"
                                          title="${message(code: 'activity.startTime.label', default: '开始时间')}"/>
                        <g:sortableColumn property="endTime"
                                          title="${message(code: 'activity.endTime.label', default: '结束时间')}"/>
                        <g:sortableColumn property="actualStartTime"
                                          title="${message(code: 'activity.actualStartTime.label', default: '实际开始时间')}"/>
                        <g:sortableColumn property="actualEndTime"
                                          title="${message(code: 'activity.actualEndTime.label', default: '实际结束时间')}"/>
                        <g:sortableColumn property="contact"
                                          title="${message(code: 'activity.contact.label', default: '抵押人姓名')}"/>
                        <g:sortableColumn property="user"
                                          title="${message(code: 'activity.user.label', default: '所有者')}"/>
                        <g:sortableColumn property="assignedTo"
                                          title="${message(code: 'activity.assignedTo.label', default: '下户人')}"/>
                        <g:sortableColumn property="status"
                                          title="${message(code: 'activity.status.label', default: '状态')}"/>
                        <g:sortableColumn property="city"
                                          title="${message(code: 'activity.city.label', default: '城市')}"/>
                        <g:sortableColumn property="address"
                                          title="${message(code: 'activity.address.label', default: '地址')}"/>
                        <g:sortableColumn property="longitude"
                                          title="${message(code: 'activity.longitude.label', default: '经度')}"/>
                        <g:sortableColumn property="latitude"
                                          title="${message(code: 'activity.latitude.label', default: '维度')}"/>
                        <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                            <g:sortableColumn class="text-center" width="5%" property="operation"
                                              title="操作"></g:sortableColumn>
                        </sec:ifAllGranted>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${this.activities}">
                        <tr>
                            <td>
                                <g:link controller="activity" action="show" id="${it.id}"
                                        class="firstTd">${it?.type?.name}</g:link>
                            </td>
                            <td>${it?.subtype?.name}</td>
                            <td>
                                <g:formatDate class="weui_input" date="${it?.startTime}"
                                              format="yyyy-MM-dd HH:mm:ss" name="startTime" autocomplete="off"
                                              readonly="true"></g:formatDate>
                            </td>
                            <td>
                                <g:formatDate class="weui_input" date="${it?.endTime}" format="yyyy-MM-dd HH:mm:ss"
                                              name="endTime" autocomplete="off" readonly="true"></g:formatDate>
                            </td>
                            <td>
                                <g:formatDate class="weui_input" date="${it?.actualStartTime}"
                                              format="yyyy-MM-dd HH:mm:ss" name="actualStartTime" autocomplete="off"
                                              readonly="true"></g:formatDate>
                            </td>
                            <td>
                                <g:formatDate class="weui_input" date="${it?.actualEndTime}"
                                              format="yyyy-MM-dd HH:mm:ss" name="actualEndTime" autocomplete="off"
                                              readonly="true"></g:formatDate>
                            </td>
                            <td>${it?.contact?.fullName}</td>
                            <td>${it?.user}</td>
                            <td>${it?.assignedTo}</td>
                            <td>${it?.status}</td>
                            <td>${it?.city}</td>
                            <td>${it?.address}</td>
                            <td>${it?.longitude}</td>
                            <td>${it?.latitude}</td>
                            <sec:ifAllGranted roles="ROLE_ADMINISTRATOR">
                                <td class="text-center" width="5%">
                                    <g:form resource="${it}" method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除</button>
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