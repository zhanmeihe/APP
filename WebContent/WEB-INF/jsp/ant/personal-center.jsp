<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
<link type="favicon" rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<meta charset="UTF-8">
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>个人中心</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
<script src="<%=request.getContextPath()%>/js/jquery-1.10.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
</head>
<body class="">
	<div class="index">
		<div class="">
			<div class="m-order-title">个人中心</div>
		</div>
		<div class="g-per-center">
			<div class="m-per">
				<div class="titile-img">
					<img id = "preview" style="border-radius: 45px;"   src="<%=request.getContextPath()%>/images/head-portrait.png"
						alt="">
						<!-- <img id="preview" width=-1 height=-1 style="diplay:none" />	 -->
						<input type="file"  onchange="viewImage(this)">
					
				</div>
				<div class="m-personal-center">
					<div class="bank-card">
						<div>姓名</div>
						<div class="right">张三</div>
					</div>
					<div class="bank-card">
						<div>性别</div>
						<div class="right">男</div>
					</div>
					<div class="bank-card">
						<div>现居住地址</div>
						<div class="right">北京市朝阳区XXX路2号院1号1室</div>
					</div>
					<div class="bank-card">
						<div>工种</div>
						<div class="right">
							<span>电工</span>
						</div>
					</div>
					<div class="bank-card">
						<div>介绍人</div>
						<div class="right">李四</div>
					</div>
					<div class="bank-card">
						<div>钱包</div>
						<div class="right">1000元</div>
					</div>
					<div class="bank-card">
						<div>身份证</div>
						<div class="right">${fn:substring(phone,0,3)}****${fn:substring(phone,7,11)}</div>
					</div>
					<div class="bank-card">
						<div>我的订单</div>
						<div class="right">
							<a href="<%=request.getContextPath()%>/Userinfo/orderlists.shtml/${userId}">点击查看</a>
						</div>
					</div>
					<div class="g-field">
						<a href="<%=request.getContextPath()%>/Userinfo/modifyInfo.shtml/${userId}">修改个人信息</a>
					</div>
				</div>
			</div>
			<div class="g-lj-bottom">
				<a href="<%=request.getContextPath()%>/Userinfo/orderlist.shtml">抢单</a>
				<a href="###">施工</a> <a
					href="<%=request.getContextPath()%>/Userinfo/PersonalCenter.shtml">个人中心</a>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function viewImage(file) {
			var preview = document.getElementById('preview');
			if (file.files && file.files[0]) {
				//火狐下
				/* preview.style.display = "block";
				preview.style.width = "300px";
				preview.style.height = "120px";
 */				preview.src = window.URL.createObjectURL(file.files[0]);
			} else {
				//ie下，使用滤镜
				file.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag");
				//必须设置初始大小 
				/* localImagId.style.width = "250px";
				localImagId.style.height = "200px"; */
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					locem("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				preview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
	</script>
	
<script type="text/javascript">
$(document).ready(function(){
	var  phone = ${phone};
	//var userId = ${userId};
	//var url = <%=request.getContextPath()%>+"/Userinfo/orderlists.shtml/"+userId;
	//alert(phone);
});

/* $(function(){ 
    var phone = "13521294806";
    var mphone =phone.substr(3,4);
    var lphone = phone.replace(mphone,"****");
   phone.attr("value", lphone); 
phone.attr("value", phone ); //隐藏域
  }); */
</script>
</body>
</html>