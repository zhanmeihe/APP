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
    <title>Document</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <script src="<%=request.getContextPath()%>/js/jquery-1.10.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body class="">
    <div class="index">
        <div class="g-order-list">
            <div class="m-order-title">订单详情</div>
            <div class="m-order-cent">
                <div class="m-details">
                    <div class="details">
                        <p>施工任务：XXXX</p>
                        <p>施工量：XXX</p>
                        <p>薪资：500</p>
                        <p>施工时间：2017.8.1-2017.9.1</p>
                    </div>
                </div>
                <div class="m-details">
                    <div class="details">
                        <span class="qingdan">请自带以下工具清单：</span>
                        <p>1：电锤，砂轮机，角磨机</p>
                        <p>2：电刨子，台钻，水平仪</p>
                        <p>3：水钻，曲线锯，直钉枪</p>
                        <p>4：马钉枪，钢钉枪，纹钉枪</p>

                    </div>
                </div>
                <div class="m-details aggregate">
                    <span>集合地点：</span><a href="">六佰本商业中心</a>
                </div>
              <!--  <div class="g-field order-derail"><input type="submit" value="我要抢单"></input></div> -->
            </div>
        </div>
        <div class="g-lj-bottom">
            <a href="<%=request.getContextPath()%>/Userinfo/orderlist.shtml">抢单</a>
            <a href="###">施工</a>
            <a href="<%=request.getContextPath()%>/Userinfo/PersonalCenter.shtml">个人中心</a>
        </div>
    </div>
</body>
</html>