<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'opportunityStage.label', default: 'OpportunityStage')}" />
    <title>订单阶段</title>
</head>

<body class="fixed-navbar fixed-sidebar">
    <div class="small-header">
        <div class="hpanel">
            <div class="panel-body">
                <div id="hbreadcrumb" class="pull-right">
                    <ol class="hbreadcrumb breadcrumb">
                        <li>中佳信LMS</li>
                        <li class="active">
                            <span>订单阶段</span>
                        </li>
                    </ol>
                </div>
                <h2 class="font-light m-b-xs">
                    订单阶段
                </h2>
            </div>
        </div>
    </div>
    <div class="content animate-panel">
      <div class="row">
          <g:form method="POST" action="searchOpportunityStage">
              <div class="hpanel hblue">
                  <div class="panel-heading">
                      <div class="panel-tools">
                          <button class="btn btn-primary btn-xs" type="submit"><i class="fa fa-search"></i>查询</button>
                          <button class="btn btn-warning2 btn-xs" type="button" id="resetBtn"><i
                                  class="fa fa-times"></i>重置</button>
                      </div>
                      查询
                  </div>
                  <div class="panel-body seach-group">

                      <div class="col-md-4">
                          <input type="text" class="form-control" placeholder="阶段编号" id="code" name="code"
                                 value="${params?.code}">
                      </div>

                      <div class="col-md-4">
                          <input type="text" class="form-control" placeholder="阶段名称" id="name" name="name"
                                 value="${params?.name}">
                      </div>

                      <div class="col-md-4">
                          <input type="text" class="form-control" placeholder="阶段描述" id="description" name="description"
                                 value="${params?.description}">
                      </div>

                  </div>
              </div>
          </g:form>
      </div>
            <div class="row">
                <div class="hpanel hgreen">
                    <div class="panel-heading">
                        <div class="panel-tools">
                            <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                            <a class="showhide"><i class="fa fa-chevron-up"></i></a>
                        </div>
                        订单阶段列表
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <g:sortableColumn property="code" title="${message(code: 'opportunityStage.code.label', default: '阶段编号')}" />
                                        <g:sortableColumn property="name" title="${message(code: 'opportunityStage.name.label', default: '阶段名称')}" />
                                        <g:sortableColumn property="description" title="${message(code: 'opportunityStage.description.label', default: '阶段描述')}" />
                                        <g:sortableColumn property="type" title="${message(code: 'opportunityStage.type.label', default: '类型')}" />
                                        <g:sortableColumn property="active" title="${message(code: 'opportunityStage.active.label', default: '是否启用')}" />

                                        <g:sortableColumn property="category" title="${message(code: 'opportunityStage.category.label', default: '分类')}" />
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${opportunityStageList}">
                                        <tr>
                                            <td>
                                                <g:link action="show" id="${it.id}" class="firstTd">${it.code}</g:link>
                                            </td>
                                            <td>
                                                ${it?.name}
                                            </td>
                                            <td>
                                                ${it?.description}
                                            </td>
                                            <td>
                                                ${it?.type}
                                            </td>
                                            <td>
                                                ${it?.active}
                                            </td>
                                            <td>
                                                ${it?.category?.name}
                                            </td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="pagination">
                            <g:paginate total="${opportunityStageCount ?: 0}" params="${params}" />
                        </div>
                    </div>
                </div>
            </div>
    </div>
</body>

</html>
