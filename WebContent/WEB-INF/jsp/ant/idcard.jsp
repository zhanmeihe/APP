<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>上传身份证</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
<script src="<%=request.getContextPath()%>/js/jquery-1.10.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
<script src="<%=request.getContextPath()%>/js/uploadPreview.js"></script>


</head>
<body>

	<div class="index">
		<form action="<%=request.getContextPath()%>/Userinfo/ImageUpage.shtml"
			method="post" enctype="multipart/form-data"
			id = "myform" onsubmit="return check();">
			<div class="g-personal">
				<div class="m-bg-id">
					<div class="g-upload" id="warp">
						<div class="id">上传身份证：${phone}</div>
						<div class="m-upload-id">
							<img src="<%=request.getContextPath()%>/images/Positive-ID.png"
								alt="" id="imgShow_WU_FILE_0"> <input class="headPic"
								type="file" name="headPic" id="up_img_WU_FILE_0">
						</div>

						<div class="m-upload-id">
							<img src="<%=request.getContextPath()%>/images/Positive-ID-2.png"
								alt="" id="imgShow_WU_FILE_1"> <input class="headPic"
								type="file" name="headPic" id="up_img_WU_FILE_1">
						</div>
						<div class="m-upload-id">
							<img src="<%=request.getContextPath()%>/images/Positive-ID-3.png"
								alt="" id="imgShow_WU_FILE_2"> <input class="headPic"
								type="file" name="headPic" id="up_img_WU_FILE_2">
						</div>
					</div>
				</div>
				<!--  <div class="g-field m-fieldBottom"><input type="submit" value="下一步" ></div> -->
				<div class="g-field m-ok">
					<input type="submit" value="完成">
				</div>
				<!-- <div class="g-field m-fieldBottom"><input type="submit" value="完成" ></div> -->
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function check() {
			var myform = document.getElementById("myform"); //获得form表单对象
			for (var i = 0; i < myform.length; i++) { //循环form表单
				if (myform.elements[i].value == "") { //判断每一个元素是否为空
					alert(myform.elements[i].value + "三张图片都不能为空！");
					myform.elements[i].focus(); //元素获得焦点
					return false;
				}
			}
			myform.submit();
		}
	</script>
	<script type="text/javascript">
		function checkform(obj) {
			for (i == 0; i < obj.headPic.length; i++)
				if (obj.headPic[i].checked == true)
					return true;
			alert("请选择");
			return false;
		}
	</script>
	<script type="text/javascript">
		function formValidate() {
			window.location.href = "/APP/index.jsp";
		}
	</script>
</body>
</html>