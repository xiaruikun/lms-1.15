<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'menu.label', default: 'Menu')}"/>
    <title>菜单列表</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li class="active">
                        <span>菜单列表</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                菜单列表
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">

    <div class="row">
        <div class="hpanel hgreen">
            <div class="panel-heading">
                <div class="panel-tools">
                    <g:link action="create" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新建</g:link>
                </div>
                菜单列表
            </div>

            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <g:sortableColumn property="name" title="名称"/>
                           <!--  <th width="10%">
                              
                               <input type="checkbox" class="clickFather">
                               <button id="clickBtn" class="btn btn-danger btn-xs" disabled type="button">批量删除</button>
                               
                           </th> -->
                            <th width="15%" class="text-center" colspan="2">操作</th>
                        </thead>
                        <tbody>
                        <g:each in="${menuList}">
                            <tr>
                                <td>
                                    <g:link controller="menu" action="show" id="${it?.id}">${it?.name}
                                    </g:link></td>
                                %{-- <th><input type="checkbox" class="clickSon" value="${it?.id}"></th> --}%
                                <td class="text-center">
                                    <g:link class="btn btn-primary btn-xs btn-outline"
                                            controller="menu" action="edit"
                                            id="${it?.id}">
                                        <i class="fa fa-edit"></i>
                                        编辑
                                    </g:link>

                                </td>
                                <td class="text-center">
                                    <g:form controller="menu" action="delete"
                                            style="display: inline-block"
                                            id="${it.id}"
                                            method="DELETE">
                                        <button class="deleteBtn btn btn-danger btn-xs btn-outline" type="button">
                                            <i class="fa fa-trash-o"></i>
                                            删除
                                        </button>
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
                    <g:paginate total="${menuCount ?: 0}"/>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        $(".clickFather").click(function() {
            var sons = $(".clickSon");
           for (var i = 0; i < sons.length; i++) {
                sons[i].checked = $(this)[0].checked;
                if(sons[i].checked){
                     $("#clickBtn").removeAttr("disabled");
                }else{
                    $("#clickBtn").attr("disabled","disabled")
                }
            }
           
           

        });
        $(".clickSon").click(function(event) {
            var sons = $(".clickSon");
            var num = 0;
            for (var i = 0; i < sons.length; i++) {
                if(sons[i].checked){
                   num++;
                }
            }

           if(sons.length == num){
                $(".clickFather")[0].checked = true;
           }else{
                $(".clickFather")[0].checked = false;
           }

           if(num > 0){
                $("#clickBtn").removeAttr('disabled');
           }else{
                 $("#clickBtn").attr("disabled","disabled");
           }
        });
        $("#clickBtn").click(function () {
            var menuIds = [];
            var sons = $(".clickSon");
            for (var i = 0; i < sons.length; i++) {
                if(sons[i].checked){
                    menuIds.push(sons[i].value);
                }
            }
           /* $.ajax({
                type: "POST",
                dataType: "json",
                url:"/menu/batchDelete",
                data:{
                    menuIds:menuIds.join(",")
                },
                 success: function (data) {
                    alert(data)
                    if (data.status == "success") {
                         window.history.go(0);
                    }
                },
            })*/
            $.post("/menu/batchDelete",{menuIds:menuIds.join(",")},function (data) {
                if(data.status == "success"){
                    window.location.history(0);
                }
            });

        });
        

    });
</script>
</body>
</html>