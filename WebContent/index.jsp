<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <link type="favicon" rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>Document</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/jquery-1.10.1.min.js"></script>
    <script src="js/common.js"></script>
</head>
<body>
    <div class="index">
        <div class="g-index-bg">
            <div class="join"><a href="<%=request.getContextPath()%>/Userinfo/IndexRe.shtml">申请加入</a></div>
        </div>
        <div class="g-lj-bottom">
            <a href="<%=request.getContextPath()%>/Userinfo/orderlist.shtml">抢单</a>
            <a href="###">施工</a>
            <a href="<%=request.getContextPath()%>/Userinfo/PersonalCenter.shtml">个人中心</a>
        </div>
    </div>
</body>
</html>