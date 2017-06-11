<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/video/dreamChildStyle.css"
	type="text/css">
<!-- <link rel="stylesheet"
	href="http://static.youku.com/yk/newtudou/css/pc/home/secpage.256d218d50.css"
	type="text/css"> -->

<title>网易点播</title>
</head>
<body>
	<c:forEach items="${ videolist}" var="NeteaseArr">

		<script language="javascript" for="window" event="onload">
			function openTheIndexPage() {
				openMyURIWithCid(
						true,
						'root',
						'IDX',
						"iframe/dispatch.jsp?url=tdc/zhk/impctrlobjinf/index/index.jsp",
						'首页', 'top.tagmenu', 'top.maintop', true,
						'system/accessPaths.do?currentModuleCode=IDX',
						'mainmenu', true);
			};
			if (document.readyState == "complete") {
				openTheIndexPage();
			}
		</script>

		<div class="video_box">



			<div>
				<a href="<%=request.getContextPath()%>/unis/show.video.htl/${ NeteaseArr.progid}/.php"><img alt="${NeteaseArr.name }" src="${NeteaseArr.imageUrl }"
					width="280" height="255"></a>
			</div>
			<div>
				<a href="<%=request.getContextPath()%>/unis/show.video.htl/${ NeteaseArr.progid}/.php" title="${ NeteaseArr.name}"
					style="color: black;">${ NeteaseArr.name}</a>
			</div>


			<%-- <div class="video_box">
			<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-4445535411111'
					codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0'
					width='400' height='360'>
					<param name='movie' value='Flvplayer.swf' />
					<param name='quality' value='high' />
					<param name='allowFullScreen' value='true' />
					<param name='FlashVars'
						value='vcastr_file=${NeteaseArr.videoPath }&IsAutoPlay=0&IsContinue=1' />
					<embed src='<%=request.getContextPath()%>/video/Flvplayer.swf'
						allowfullscreen='true'
						flashvars='vcastr_file=${NeteaseArr.videoPath }&IsAutoPlay=0&IsContinue=1'
						quality='high'
						pluginspage='http://www.macromedia.com/go/getflashplayer'
						type='application/x-shockwave-flash' width='400' height='360' />
				</object> 
			</div> --%>
		</div>

	</c:forEach>
</body>
</html>