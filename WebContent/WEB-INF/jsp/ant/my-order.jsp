<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<link type="favicon" rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>我的订单</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <script src="<%=request.getContextPath()%>/js/jquery-1.10.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body class="">
    <div class="index">
        <div class="g-order-list">
            <div class="m-order-title">我的订单</div>
            <div class="m-order-cent">
                <ul>
                    <li>
                        <a href="<%=request.getContextPath()%>/Userinfo/ListDetails.shtml">
                            <div class="z-center">
                                <div class="details">
                                    <p>任务摘要：XXXX</p>
                                    <p>薪资：500</p>
                                    <p>任务时间：2017.8.1-2017-9.1</p>
                                    <p>任务地点：六佰本商业街北区401</p>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/Userinfo/ListDetails.shtml">
                            <div class="z-center">
                                <div class="details">
                                    <p>任务摘要：XXXX</p>
                                    <p>薪资：500</p>
                                    <p>任务时间：2017.8.1-2017-9.1</p>
                                    <p>任务地点：六佰本商业街北区401</p>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/Userinfo/ListDetails.shtml">
                            <div class="z-center">
                                <div class="details">
                                    <p>任务摘要：XXXX</p>
                                    <p>薪资：500</p>
                                    <p>任务时间：2017.8.1-2017-9.1</p>
                                    <p>任务地点：六佰本商业街北区401</p>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
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