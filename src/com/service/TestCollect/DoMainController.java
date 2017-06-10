package com.service.TestCollect;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.TestCollect.pojo.News;
import com.zhan.ex.RuleException;
import com.zhan.ex.UnisException;
import com.zhan.response.CommonResponse;
import com.zhan.response.CommonSuccessResponse;
import com.zhan.response.SystemErrorResponse;
import com.zhan.utils.Tools;

/**
 * 
 * @author zhanmeihe
 * 
 */
@Controller
public class DoMainController {

	private static Logger LOGGER = LoggerFactory.getLogger(DoMainController.class);

	@RequestMapping(value = "/unis/NeteaseLive/app-htm", method = RequestMethod.GET)
	public String GetVideoapp(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			 
		 
		} catch (UnisException e) {
			LOGGER.info("异常", e);
		} catch (Exception e) {
			LOGGER.info("异常", e);
		}
		return "";
	}

	public static void main(String[] args) {
		News dc = new News();

		/**
		 * 房间id：134251 房间名：【点掌财经】昨天你又错过了一个交易机会 直播说明：俗话说，百日横盘成大牛，意思是“横有多长，竖有多长”。
		 * 观众人数：12799 直播封面：http://dingyue.nosdn.127.net/
		 * Nj8nSEMbKcbNYG0yTdyu8sYfCGnSmYOcCPh8e0qwVlh9v1495769224920.jpg
		 * 直播视频流：http
		 * ://flv4619ce99.live.126.net/live/415e5963a0584d64894b08f96f11f2cb
		 * .flv?netease=flv4619ce99.live.126.net
		 */
	}

}
