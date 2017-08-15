<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>施工详情</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
   <script src="<%=request.getContextPath()%>/js/jquery-1.10.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>
   <%--  <script src="<%=request.getContextPath()%>/js/swipeslider.min.js"></script> --%>
</head>
<body class="goal_bg">
    <div class="index">
        <div class="">
            <div class="m-order-title">施工详情</div>
        </div>
        <form action="<%=request.getContextPath()%>/Userinfo/VideoValidation.shtml" method="post" enctype="multipart/form-data">
        <div class="g-details">
            <div class="m-clock-in">
                <span class="in">上班打卡</span>
                <span>2017-7-7</span>
            </div>
            <div class="m-check-audio">
                <!--<div class="check-arrive">验收视频</div>-->
                <div class="work">
                    <span class="go-work">抵达现场</span>
                    <div class="s-scene">
                        <ul>
                            <li><input name="headPic" type="file"/>请录制现场视频</li>
                            <li><input name="headPic" type="file"/>请录制现场视频</li>
                            <li><input name="headPic" type="file" />请录制现场视频</li>
                        </ul>
                    </div>
                </div>
                <div class="work">
                    <span class="go-work">施工过程</span>
                    <div class="s-scene">
                        <ul>
                            <li><input name="headPic" type="file"/>施工过程视频</li>
                            <li><input name="headPic" type="file"/>施工过程视频</li>
                            <li><input name="headPic" type="file"/>施工过程视频</li>
                        </ul>
                    </div>
                </div>
                <div class="work">
                    <span class="go-work">当日工作结束</span>
                    <div class="s-scene">
                        <ul>
                            <li><input name="headPic" type="file"/>工作结束视频</li>
                            <li><input name="headPic" type="file"/>工作结束视频</li>
                            <li><input name="headPic" type="file"/>工作结束视频</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="g-field m-fieldBottom"><input type="submit" value="确定"  ></div>
        </div>
        </form>
    </div>
 
</body>
</html>