<!DOCTYPE html>
<html>

<head>
    <meta name="layout" content="wechat"/>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <g:set var="entityName" value="${message(code: 'attachments.label', default: 'Attachments')}"/>
    <title>上传材料</title>
    <style>
    /*图片上传*/
    .file-title {
        font-size: 1.2rem;
        height: 3.5rem;
        line-height: 3.5rem;
        background-color: #e1e1e1;
    }

    .file-title .status {
        color: #CD001A;
    }
    .title-desc {
        padding: 0 15px;
    }

    .preview {
        height: 77px;
        width: 77px;
    }

     .weui_uploader_input_wrp {
        margin-bottom: 0;
    }
    .weui_uploader_input_wrp:after, .weui_uploader_input_wrp:before {
        z-index: -1;
    }
    </style>
</head>

<body>
<header class="create-header">
    <ul class="creat-flowList flex-box">
        <li data-id="01" class="flex1 active">
            <span></span>

            <h3>借款信息</h3>
        </li>
        <li data-id="02" class="flex1 active">
            <span></span>

            <h3>上传材料</h3>
        </li>
        <li data-id="03" class="flex1">
            <span></span>

            <h3>报单成功</h3>
        </li>
    </ul>
</header>
<div class="message-box">
    <div class="helpMsg hide"></div>
    <g:if test="${flash.message}">
        <div class="message" role="status">
            ${flash.message}
        </div>
    </g:if>
</div>

<div class="file-title">
        <div class="title-desc">
            请上传<span class="status">借款人</span>身份证正反面
        </div>
    </div>

    <div class="weui_cell">
        <div class="weui_uploader_input_wrp">
            <img id="marialFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
            <input type="button" class="weui_uploader_input" onclick="uploadImg('marialFile')">
        </div>
        <div class="weui_uploader_input_wrp">
            <img id="maritalReverseFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
            <input type="button" class="weui_uploader_input" onclick="uploadImg('maritalReverseFile')">
        </div>
    </div>

    <g:if test="${params['maritalStatus'] in ['已婚', '再婚']}">
        <div class="hasSpouse">
            <div class="file-title">
                <div class="title-desc">
                    请上传<span class="status">借款人配偶</span>身份证正反面
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_uploader_input_wrp">
                    <img id="spouseFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
                    <input type="button" class="weui_uploader_input" onclick="uploadImg('spouseFile')">
                </div>
                <div class="weui_uploader_input_wrp">
                    <img id="spouseReverseFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
                    <input type="button" class="weui_uploader_input" onclick="uploadImg('spouseReverseFile')">
                </div>
            </div>
        </div>
    </g:if>
    
    <g:if test="${params['myGroup'] == '0'}">
        <div class="hasMotgagor">
            <div class="file-title">
                <div class="title-desc">
                    请上传<span class="status">抵押人</span>身份证正反面
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_uploader_input_wrp">
                    <img id="mortgagorFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
                    <input type="button" class="weui_uploader_input" onclick="uploadImg('mortgagorFile')">
                </div>
                <div class="weui_uploader_input_wrp">
                    <img id="mortgagorReverseFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
                    <input type="button" class="weui_uploader_input" onclick="uploadImg('mortgagorReverseFile')">
                </div>
            </div>
        </div>
    </g:if>

   <g:if test="${params['mortgagorMaritalStatus'] in ['已婚', '再婚']}">
        <div class="hasMortgagorSpouse">
            <div class="file-title">
                <div class="title-desc">
                    请上传<span class="status">抵押人配偶</span>身份证正反面
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_uploader_input_wrp">
                    <img id="mortgagorSpouseFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
                    <input type="button" class="weui_uploader_input" onclick="uploadImg('mortgagorSpouseFile')">
                </div>
                <div class="weui_uploader_input_wrp">
                    <img id="mortgagorSpouseReverseFileImg" src="" alt="" style="width:75px; height:75px; display:none;" />
                    <input type="button" class="weui_uploader_input" onclick="uploadImg('mortgagorSpouseReverseFile')">
                </div>
            </div>
        </div>
    </g:if>

    <div class="file-title">
        <div class="title-desc">
            请上传<span class="status">房产证</span>
        </div>
    </div>

    <div class="weui_cell">
        <div class="weui_uploader_input_wrp">
            <img id="propertiesImg" src="" alt="" style="width:75px; height:75px; display:none;" />
            <input type="button" class="weui_uploader_input" onclick="uploadPropertyImg()" />
        </div>
        <div>已上传<span style="color:#CD001A" id="propertyCounts">0</span>张房产证</div>
    </div>

<g:form name="reportBillForm" action="wxUpdate2">
    <g:if test="${this.opportunity.id}">
        <input type="hidden" value="${this.opportunity.id}" name="opportunity.id" id="opportunity"/>
    </g:if>
    <g:else>
        <input type="hidden" value="${this.opportunity.id}" name="" id="opportunity"/>
    </g:else>

    <input type="hidden" name="requestedAmount" value="${this.opportunity.requestedAmount}"/>
    <input type="hidden" name="loanDuration" value="${this.opportunity.loanDuration}"/>
    <input type="hidden" name="mortgageType" value="${this.opportunity?.mortgageType?.id}">
    <input type="hidden" name="firstMortgageAmount" value="${this.opportunity?.firstMortgageAmount}">
    <input type="hidden" name="secondMortgageAmount" value="${this.opportunity?.secondMortgageAmount}">
    <input type="hidden" name="typeOfFirstMortgage" value="${this.opportunity?.typeOfFirstMortgage?.id}">
    <input type="hidden" name="fullName" value="${this.opportunity?.fullName}">
    <input type="hidden" name="maritalStatus" value="${this.opportunity?.maritalStatus}">
    <input type="hidden" name="cellphone" value="${this.opportunity?.cellphone}">
    <input type="hidden" value="${this.timestamp}" name="timestamp" id="timestamp"/>
    <input type="hidden" value="${this.nonceStr}" name="nonceStr" id="nonceStr"/>
    <input type="hidden" value="${this.signature}" name="signature" id="signature"/>
    <input type="hidden" value="${this.accessToken}" name="accessToken" id="accessToken"/>
    <input type="hidden" value="${params['myGroup']}" name="myGroup" id="myGroup"/>
    <input type="hidden" value="${params['mortgagorMaritalStatus']}" name="mortgagorMaritalStatus" id="mortgagorMaritalStatus"/>
    <input type="hidden" value="${params['openId']}" name="openId" id="openId"/>

    <input type="hidden" id="marialFile" name="marialFile" value="">
    <input type="hidden" id="maritalReverseFile" name="maritalReverseFile" value="">
    <input type="hidden" id="spouseFile" name="spouseFile" value="">
    <input type="hidden" id="spouseReverseFile" name="spouseReverseFile" value="">
    <input type="hidden" id="mortgagorFile" name="mortgagorFile" value="">
    <input type="hidden" id="mortgagorReverseFile" name="mortgagorReverseFile" value="">
    <input type="hidden" id="mortgagorSpouseFile" name="mortgagorSpouseFile" value="">
    <input type="hidden" id="mortgagorSpouseReverseFile" name="mortgagorSpouseReverseFile" value="">
    <input type="hidden" id="properties" name="properties" value="">
    <input type="hidden" id="fileNames" name="fileNames" value="">

    <div class="bigBtn" style="margin-bottom: 20px">
        <input class="linkBtn weui_btn weui_btn_plain_default" value="下一步" id="reportBillBtn" type="button"/>
    </div>
</g:form>

<script>
        var timestamp = $("#timestamp").val();
        var nonceStr = $("#nonceStr").val();
        var signature = $("#signature").val();

        // alert(location.href.split('#')[0])

        // alert(nonceStr);
        // alert(timestamp);
        // alert(signature);

        wx.config({
            debug: false,
            appId: 'wx464de39cbfe33d14',
            // appId: 'wxe49dcb507643c1cd',
            timestamp: timestamp,
            nonceStr: nonceStr,
            signature: signature,
            jsApiList: [
                  "chooseImage",
                  "previewImage",
                  "uploadImage",
                  "downloadImage"
            ]
        });

        function uploadImg(str) {
            wx.chooseImage ({
                count: 1, 
                sizeType: ['original', 'compressed'],
                sourceType: ['album', 'camera'], 
                success : function(res) {
                    var localId = res.localIds.shift();
                    if (str == "marialFile") {
                        document.getElementById("marialFileImg").src = localId;
                        $("#marialFileImg").css('display','block');
                    }
                    if (str == "maritalReverseFile") {
                        document.getElementById("maritalReverseFileImg").src = localId;
                        $("#maritalReverseFileImg").css('display','block');
                    }
                    if (str == "spouseFile") {
                        document.getElementById("spouseFileImg").src = localId;
                        $("#spouseFileImg").css('display','block');
                    }
                    if (str == "spouseReverseFile") {
                        document.getElementById("spouseReverseFileImg").src = localId;
                        $("#spouseReverseFileImg").css('display','block');
                    }
                    if (str == "mortgagorFile") {
                        document.getElementById("mortgagorFileImg").src = localId;
                        $("#mortgagorFileImg").css('display','block');
                    }
                    if (str == "mortgagorReverseFile") {
                        document.getElementById("mortgagorReverseFileImg").src = localId;
                        $("#mortgagorReverseFileImg").css('display','block');
                    }
                    if (str == "mortgagorSpouseFile") {
                        document.getElementById("mortgagorSpouseFileImg").src = localId;
                        $("#mortgagorSpouseFileImg").css('display','block');
                    }
                    if (str == "mortgagorSpouseReverseFile") {
                        document.getElementById("mortgagorSpouseReverseFileImg").src = localId;
                        $("#mortgagorSpouseReverseFileImg").css('display','block');
                    }

                    wx.uploadImage({
                        localId: localId,
                        isShowProgressTips: 1,
                        success: function (res) {
                            if (str == "marialFile") {
                                $("#marialFile").val(res.serverId);
                            }
                            if (str == "maritalReverseFile") {
                                $("#maritalReverseFile").val(res.serverId);
                            }
                            if (str == "spouseFile") {
                                $("#spouseFile").val(res.serverId);
                            }
                            if (str == "spouseReverseFile") {
                                $("#spouseReverseFile").val(res.serverId);
                            }
                            if (str == "mortgagorFile") {
                                $("#mortgagorFile").val(res.serverId);
                            }
                            if (str == "mortgagorReverseFile") {
                                $("#mortgagorReverseFile").val(res.serverId);
                            }
                            if (str == "mortgagorSpouseFile") {
                                $("#mortgagorSpouseFile").val(res.serverId);
                            }
                            if (str == "mortgagorSpouseReverseFile") {
                                $("#mortgagorSpouseReverseFile").val(res.serverId);
                            }
                        }
                    });
                }
            });
        }

        var propertyCounts = 1
        var property = ""
        function uploadPropertyImg() {
            wx.chooseImage({
                count: 9, 
                sizeType: ['original', 'compressed'],
                sourceType: ['album', 'camera'], 
                success: function (res) {
                    var localIds = res.localIds;
                    propertyCounts = 1
                    property = ""
                    syncUpload(localIds);
                }
            });
        }

        function syncUpload(localIds) {
            var localId = localIds.pop();
            document.getElementById("propertiesImg").src = localId;
            $("#propertiesImg").css('display','block');
            wx.uploadImage({
                localId: localId,
                isShowProgressTips: 1,
                success: function (res) {
                    var serverId = res.serverId;
                    property = property + res.serverId + ",";
                    $("#properties").val(property);
                    $("#propertyCounts").text(propertyCounts);
                    propertyCounts++;
                    if(localIds.length > 0){
                        syncUpload(localIds);
                    }
                }
            });
        };

</script>
<script>

    $(function () {
        $("#reportBillBtn").click(function () {

            var marialFile = $("#marialFile").val();
            var maritalReverseFile = $("#maritalReverseFile").val();

            if (!marialFile) {
                helpMessage("请上传借款人身份证正面");
                return;
            }

            if (!maritalReverseFile) {
                helpMessage("请上传贷款人身份证反面");
                return;
            }

            if ($(".hasSpouse").css("display") == "block") {
                var spouseFile = $("#spouseFile").val();
                var spouseReverseFile = $("#spouseReverseFile").val();
                if (!spouseFile) {
                    helpMessage("请上传借款人配偶身份证正面");
                    return;
                }
                if (!spouseReverseFile) {
                    helpMessage("请上传借款人配偶身份证反面");
                    return;
                }

            }
            if ($(".hasMotgagor").css("display") == "block") {
                var mortgagorFile = $("#mortgagorFile").val();
                var mortgagorReverseFile = $("#mortgagorReverseFile").val();
                if (!mortgagorFile) {
                    helpMessage("请上传抵押人身份证正面");
                    return;
                }

                if (!mortgagorReverseFile) {
                    helpMessage("请上传抵押人身份证反面");
                    return;
                }

            }
            if ($(".hasMortgagorSpouse").css("display") == "block") {
                var mortgagorSpouseFile = $("#mortgagorSpouseFile").val();
                var mortgagorSpouseReverseFile = $("#mortgagorSpouseReverseFile").val();
                if (!mortgagorSpouseFile) {
                    helpMessage("请上传抵押人配偶身份证正面");
                    return;
                }

                if (!mortgagorSpouseReverseFile) {
                    helpMessage("请上传抵押人配偶身份证反面");
                    return;
                }
            }

            var properties = $("#properties").val();
            if (!properties) {
                helpMessage("请上传房产证");
                return;
            }
            var accessToken = $("#accessToken").val();
            $('#reportBillBtn').addClass("btn_disabled").attr('disabled', 'disabled');
            $.ajax({
                type: "POST",
                url:  "https://" + window.location.host + "/wechatOpportunity/wxGetImgServerId",
                data: {
                    marialFile: marialFile,
                    maritalReverseFile: maritalReverseFile,
                    spouseFile: spouseFile,
                    spouseReverseFile: spouseReverseFile,
                    mortgagorFile: mortgagorFile,
                    mortgagorReverseFile: mortgagorReverseFile,
                    mortgagorSpouseFile: mortgagorSpouseFile,
                    mortgagorSpouseReverseFile: mortgagorSpouseReverseFile,
                    properties: properties,
                    accessToken: accessToken
                },
                beforeSend:function() {
                    $(".helpMsg").text("正在上传报单信息，请稍候").fadeIn(200);
                },
                success: function (data) {
                    if (data.status == "success") {
                        var fileNames = data.fileNameList
                        $("#fileNames").val(fileNames);
                        $("#reportBillForm").submit();
                    } else {
                        helpMessage(data.errorMsg);
                        return;
                    }
                }
            });
        });

        function helpMessage(message) {
            $(".helpMsg").text(message).fadeIn(200);
            setTimeout(function () {
                $(".helpMsg").fadeOut(200);
            }, 2000);
        }
    });
</script>
</body>
</html>