<!DOCTYPE html>
<html>
<head>
    <title>图片排序</title>
    <meta name="layout" content="main"/>

    <style>
    .panel-body {
        height: 100%;
    }

    .item_content ul {
        list-style: none;
    }

    .item_content ul li {
        width: 250px;
        height:270px;
        float: left;
        margin: 10px;
    }

    .item_content {
        width: 100%;
        height: 100%;
        margin: 0 auto;
    }

    .item_content .item {
        width: 250px;
        height:250px;
        line-height: 20px;
        text-align: center;
        cursor: pointer;
        background: #ccc;

    }

    .item_content .item img {
        width: 250px;
        height:250px;
    }
    /*@media (max-width: 720px)
    {
        .item_content ul li,.item_content,.item_content .item,.item_content .item img {
            width:100px;
            height:100px;
        }
    }*/
    </style>
</head>

<body>
<div class="content animate-panel">
    <div class="row">
        <div class="hpanel hblue">
            <div class="panel-heading">
                <div class="panel-tools">
                    <button id="submitBtn" class="btn btn-info btn-xs" type="button"><i
                            class="fa fa-check"></i>保存</button>
                </div>
                ${this.attachmentType?.name}排序
            </div>

            <div class="item_content">
                <ul class="panel-body no-padding">
                    <g:each in="${this.attachments}">
                        <li class="">
                            <g:if test="${it?.fileName?.split('\\.')[-1] in ['doc', 'docx', 'pdf','xlsx','xls']}">
                                <div class="item" data-id=${it?.id} style="padding-top: 100px">
                                    <a href="${it?.fileName}" target="_blank">附件信息：${it?.fileName?.split('/')[-1]}
                                        <h5 class="text-info">点击查看</h5>
                                    </a>
                                </div>
                            </g:if>
                            <g:if test="${it?.fileName?.split('\\.')[-1] in ['jpg', 'png', 'jpeg','JPG','PNG','JPEG']}">
                                <a class="item" href="javascript:;" title="${it?.type?.name}" data-id=${it?.id}>
                                   <g:if test="${it?.thumbnailUrl}">
                                                 <img src="${it?.thumbnailUrl}" alt="${it?.type?.name}" dataId= "${it?.id}"/>
                                            </g:if>
                                            <g:else>
                                                   <img src="${it?.fileName}" alt="${it?.type?.name}"/>
                                            </g:else>
                                    <h5><g:if test="${it?.description}">${it?.description}</g:if></h5>
                                </a>
                            </g:if>
                        </li>

                    </g:each>

                </ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        function Pointer(x, y) {
            this.x = x;
            this.y = y;
        }

        function Position(left, top) {
            this.left = left;
            this.top = top;
        }

        $(".item_content .item").each(function (i) {
            this.init = function () { // 初始化
                this.box = $(this).parent();
                $(this).attr("index", i).css({
                    position: "absolute",
                    left: this.box.offset().left-180,
                    top: this.box.offset().top-62
                }).appendTo(".item_content");
                this.drag();
            },
                    this.move = function (callback) {  // 移动
                        $(this).stop(true).animate({
                            left: this.box.offset().left-180,
                            top: this.box.offset().top-62
                        }, 500, function () {
                            if (callback) {
                                callback.call(this);
                            }
                        });
                    },
                    this.collisionCheck = function () {
                        var currentItem = this;
                        var direction = null;
                        $(this).siblings(".item").each(function () {
                            if (
                                    currentItem.pointer.x > this.box.offset().left &&
                                    currentItem.pointer.y > this.box.offset().top &&
                                    (currentItem.pointer.x < this.box.offset().left + this.box.width()) &&
                                    (currentItem.pointer.y < this.box.offset().top + this.box.height())
                            ) {
                                // 返回对象和方向
                                if (currentItem.box.offset().top < this.box.offset().top) {
                                    direction = "down";
                                } else if (currentItem.box.offset().top > this.box.offset().top) {
                                    direction = "up";
                                } else {
                                    direction = "normal";
                                }
                                this.swap(currentItem, direction);
                            }
                        });
                    },
                    this.swap = function (currentItem, direction) { // 交换位置
                        if (this.moveing) return false;
                        var directions = {
                            normal: function () {
                                var saveBox = this.box;
                                this.box = currentItem.box;
                                currentItem.box = saveBox;
                                this.move();
                                $(this).attr("index", this.box.index());
                                $(currentItem).attr("index", currentItem.box.index());
                            },
                            down: function () {
                                // 移到上方
                                var box = this.box;
                                var node = this;
                                var startIndex = currentItem.box.index();
                                var endIndex = node.box.index();
                                ;
                                for (var i = endIndex; i > startIndex; i--) {
                                    var prevNode = $(".item_content .item[index=" + (i - 1) + "]")[0];
                                    node.box = prevNode.box;
                                    $(node).attr("index", node.box.index());
                                    node.move();
                                    node = prevNode;
                                }
                                currentItem.box = box;
                                $(currentItem).attr("index", box.index());
                            },
                            up: function () {
                                // 移到上方
                                var box = this.box;
                                var node = this;
                                var startIndex = node.box.index();
                                var endIndex = currentItem.box.index();
                                ;
                                for (var i = startIndex; i < endIndex; i++) {
                                    var nextNode = $(".item_content .item[index=" + (i + 1) + "]")[0];
                                    node.box = nextNode.box;
                                    $(node).attr("index", node.box.index());
                                    node.move();
                                    node = nextNode;
                                }
                                currentItem.box = box;
                                $(currentItem).attr("index", box.index());
                            }
                        }
                        directions[direction].call(this);
                    },
                    this.drag = function () { // 拖拽
                        var oldPosition = new Position();
                        var oldPointer = new Pointer();
                        var isDrag = false;
                        var currentItem = null;
                        $(this).mousedown(function (e) {
                            e.preventDefault();
                            oldPosition.left = $(this).position().left;
                            oldPosition.top = $(this).position().top;
                            oldPointer.x = e.clientX;
                            oldPointer.y = e.clientY;
                            isDrag = true;

                            currentItem = this;

                        });
                        $(document).mousemove(function (e) {
                            var currentPointer = new Pointer(e.clientX, e.clientY);
                            if (!isDrag) return false;
                            $(currentItem).css({
                                "opacity": "0.8",
                                "z-index": 999
                            });
                            var left = currentPointer.x - oldPointer.x + oldPosition.left;
                            var top = currentPointer.y - oldPointer.y + oldPosition.top;
                            $(currentItem).css({
                                left: left,
                                top: top
                            });
                            currentItem.pointer = currentPointer;
                            // 开始交换位置

                            currentItem.collisionCheck();


                        });
                        $(document).mouseup(function () {
                            if (!isDrag) return false;
                            isDrag = false;
                            currentItem.move(function () {
                                $(this).css({
                                    "opacity": "1",
                                    "z-index": 0
                                });
                            });
                        });
                    }
            this.init();
        });
    });
</script>
<script>
    $(function(){

        var imgIdList = [];
        var itmeIndex = $(".item_content .item");
        for(var i = 0;i<itmeIndex.length;i++){
            var index = itmeIndex.eq(i).attr("data-id");
            imgIdList.push(index);
        }

        $("#submitBtn").click(function(){
            var indexList= [];
            var itmeIndex = $(".item_content .item");
            for(var i = 0;i<itmeIndex.length;i++){
                var index = itmeIndex.eq(i).attr("index");
                indexList.push(index);
            }
            var strList = [];
            for (var i = 0; i < imgIdList.length; i++) {
                strList[indexList[i]] = imgIdList[i];
            }
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "/attachments/setDisplayOrder",
                data: {
                    opportunityId: $("#opportunityId").val(),
                    imgIdList:strList
                },

                success: function (data) {
                    if (data.status == "success") {
                        swal("排序成功", "", "success");
                       $(".sweet-alert .confirm").click(function(){
                            window.location.href = document.referrer
                        })
                    }
                },
                error: function () {
                    swal("获取失败，请稍后重试", "", "error");
                }
            });

        });
    })
</script>
</body>

</html>
