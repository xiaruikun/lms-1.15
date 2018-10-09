<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'timingTask.label', default: 'TimingTask')}"/>
    <title>定时任务</title>
  </head>
  <body>
    <div class="small-header">
      <div class="hpanel">
        <div class="panel-body">
          <div id="hbreadcrumb" class="pull-right">
            <ol class="hbreadcrumb breadcrumb">
              <li>中佳信LMS</li>
              <li class="active">
                <span>定时任务</span>
              </li>
            </ol>
          </div>

          <h2 class="font-light m-b-xs">
            定时任务
          </h2>
        </div>
      </div>
    </div>

    <div class="content animate-panel">
      <div class="row">
        <div class="hpanel hgreen">
          <div class="panel-heading">
            <div class="panel-tools">
              <button class="btn btn-info btn-xs resend" data-style="zoom-out">
                <span class=""><i class="fa fa-paper-plane"></i>结清数据重发</span><span
                      class="ladda-spinner"></span>
              </button>
              %{--<g:link class="btn btn-info btn-xs"   onclick="reSend()">--}%
                %{--<i class="fa fa-paper-plan"></i>结清数据重发</g:link>--}%
              <g:link class="btn btn-info btn-xs" controller="timingTask" action="create">
                <i class="fa fa-plus"></i>新增</g:link>
            </div>
            定时任务
          </div>
          <div class="panel-body">
            <div class="table-responsive">
              <table class="table table-striped table-bordered table-hover">
                <thead>
                  <tr>
                    <g:sortableColumn property="component" title="组件"/>
                    <g:sortableColumn property="duration" title="周期"/>
                    <g:sortableColumn property="start" title="执行次数"/>
                    <g:sortableColumn property="operation" title="操作" width="10%" class="text-center"/>
                  </tr>
                </thead>
                <tbody>
                  <g:each in="${this.timingTaskList}">
                    <tr>
                      <td>${it?.component?.name}</td>
                      <td>${it?.duration}</td>
                      <td>
                        ${it?.start}
                      </td>
                      <td width="10%" class="text-center">
                        <g:link class="btn btn-primary btn-xs btn-outline m-b-xs inlineBlock" controller="timingTask" action="execute" id="${it?.id}">

                          执行
                        </g:link>
                        <g:link class="btn btn-primary btn-xs btn-outline m-b-xs inlineBlock" controller="timingTask" action="show" id="${it?.id}">

                          查看
                        </g:link>
                        <g:form resource="${it}" method="DELETE" class="inlineBlock">
                          <button class="deleteBtn btn btn-danger btn-xs btn-outline m-b-xs" type="button">
                            <i class="fa fa-trash-o"></i>
                            删除</button>
                        </g:form>
                      </td>
                    </tr>
                  </g:each>
                </tbody>
              </table>
            </div>
          </div>

          <div class="panel-footer">
            <div class="pagination">
              <g:paginate total="${timingTaskCount ?: 0}" params="${params}"/>
            </div>
          </div>
        </div>
      </div>
    </div>
<script>
  $(".resend").click(function () {
      $.ajax({
          type: "POST",
          dataType: "json",
          url: "/JieqingHFH/sendDataTohuo",
          success: function (data) {
              if (data.status == "success") {
                  swal("发送火凤凰成功", "", "success");
              }
              else {
                  swal("发送火凤凰失败", "", "error");
              }
          },
          error: function () {
              swal("获取失败，请稍后重试", "", "error");
          }
      });
  })


</script>
  </body>
</html>
