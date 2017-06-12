package com.service.TestCollect;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.service.TestCollect.dao.VideoDao;
import com.service.TestCollect.pojo.Video;
import com.service.TestCollect.service.NeteaseVideoService;
import com.zhan.ex.UnisException;
import com.zhan.response.CommonResponse;
import com.zhan.response.SuccessResponse;
import com.zhan.response.SystemErrorResponse;
import com.zhan.utils.PropertiesFactory;

/**
 * 
 * @author zhanmeihe
 * 
 */
@Controller
public class DoMainController {

	private static Logger LOGGER = LoggerFactory.getLogger(DoMainController.class);

	@Resource
	private VideoDao videoDao;

	/**
	 * 抓取网易端视频接口
	 */
	@ResponseBody
	@RequestMapping(value = "/unis/NeteaseLive/app-htm", method = RequestMethod.GET, produces = "application/json")
	public CommonResponse GetVideoapp(Model model, NeteaseVideoService service,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			List<String> lines = PropertiesFactory.loadAllWebs();
			for (String line : lines) {
				List<String> portals = PropertiesFactory.loadPortalsByWeb(line);
				for (String portal : portals) {
					service.saveNeteaseVideo(portal);
				}
			}
			return new SuccessResponse();
		} catch (UnisException e) {
			LOGGER.info("异常", e);
			return new CommonResponse(e.getCode(), e.getMessage());
		} catch (Exception e) {
			LOGGER.info("异常", e);
		}
		return new SystemErrorResponse();
	}

	/**
	 * 展示和播放网易视频接口
	 */
	
	@RequestMapping(value = "/unis/show.video.htl/{vid}/.php", method = RequestMethod.GET)
	public String VideoShow(@PathVariable("vid") String vid, Model model) {

		try {
			Video vo = videoDao.queryId(vid);
			model.addAttribute("vidpath", vo);
			return "netease/Showvideo";
		} catch (UnisException e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
		} catch (Exception e) {
			model.addAttribute("errormsg", e);
			LOGGER.error("异常", e);
			return "errorpage/error";
		}
	}

	/**
	 * 查询网易视频接口
	 */
	
	@RequestMapping(value = "/video/netease.py/{PageONE}/{MaxRows}", method = RequestMethod.GET)
	public String videoList(@PathVariable("PageONE") int PageONE,
			@PathVariable("MaxRows") int MaxRows, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			int firstResult = PageONE * MaxRows;
			List<Video> de = videoDao.querydata(firstResult, MaxRows);
			model.addAttribute("videolist", de);
			return "netease/NeteasteVideo";
		} catch (UnisException e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
		} catch (Exception e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
		}
	}
}
