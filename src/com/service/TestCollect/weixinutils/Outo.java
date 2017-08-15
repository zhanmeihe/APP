package com.service.TestCollect.weixinutils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.TestCollect.pojo.SNSUserInfo;
import com.service.TestCollect.pojo.WeixinOauth2Token;

public class Outo {

	public Outo() {
		// TODO Auto-generated constructor stub
	}
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.setCharacterEncoding("utf-8");
	        response.setCharacterEncoding("utf-8");

	        // 用户同意授权后，能获取到code
	        String code = request.getParameter("code");
	        String state = request.getParameter("state");
	        
	        // 用户同意授权
	        if (!"authdeny".equals(code)) {
	            // 获取网页授权access_token
	            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("wxe34a90ac7bxxcab85c", "1207d566090a8344xxxd6224c02c", code);
	            // 网页授权接口访问凭证
	            String accessToken = weixinOauth2Token.getAccessToken();
	            // 用户标识
	            String openId = weixinOauth2Token.getOpenId();
	            // 获取用户信息
	            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

	            // 设置要传递的参数
	            request.setAttribute("snsUserInfo", snsUserInfo);
	            request.setAttribute("state", state);
	        }
	        // 跳转到index.jsp
	        request.getRequestDispatcher("index.jsp").forward(request, response);
	    }

}
