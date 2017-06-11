<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vidpath.name}</title>
</head>
<body>
	 

 <h3>${vidpath.name}</h3>
 	<div>
				<a href="${vidpath. url}">原链接地址：</a>
				<br>
				<a href="${vidpath. url}">${vidpath. url}</a>
				</div>
				<br>
<object  classid='clsid:D27CDB6E-AE6D-11cf-96B8-4445535411111'
					codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0'
					width='400' height='360'>
					<param name='movie' value='Flvplayer.swf' />
					<param name='quality' value='high' />
					<param name='allowFullScreen' value='true' />
					<param name='FlashVars'
						value='vcastr_file=${vidpath.videoPath }&IsAutoPlay=1&IsContinue=1' />
					<embed   style="width: 800px; height: 600px;" src='<%=request.getContextPath()%>/video/Flvplayer.swf'
						allowfullscreen='true'
						flashvars='vcastr_file=${vidpath.videoPath }&IsAutoPlay=1&IsContinue=1'
						quality='high'
						pluginspage='http://www.macromedia.com/go/getflashplayer'
						type='application/x-shockwave-flash' width='400' height='360' />
				</object> 
			
</body>
</html>