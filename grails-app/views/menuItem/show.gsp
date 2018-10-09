<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'menuItem.label', default: 'MenuItem')}"/>
    <title>${this.menuItem?.name}详情</title>
</head>

<body>
<div class="small-header">
    <div class="hpanel">
        <div class="panel-body">
            <div id="hbreadcrumb" class="pull-right">
                <ol class="hbreadcrumb breadcrumb">
                    <li>中佳信LMS</li>
                    <li>
                        <g:link controller="product" action="index">菜单管理</g:link>
                    </li>
                    <li class="active">
                        <span>${this.menuItem?.name}详情</span>
                    </li>
                </ol>
            </div>

            <h2 class="font-light m-b-xs">
                ${this.menuItem?.name}详情
            </h2>
        </div>
    </div>
</div>

<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                ${this.menuItem?.name}详情
            </div>

            <div class="panel-body form-horizontal">
                <div class="form-group">
                    <label class="col-md-4 control-label">菜单名称：</label>

                    <div class="col-md-3">
                       <span class="cont">${this.menuItem?.name}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label">链接：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.menuItem?.linkUrl}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 control-label">顺序：</label>

                    <div class="col-md-3">
                        <span class="cont">${this.menuItem?.displayOrder}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
