package com.service.TestCollect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.TestCollect.dao.VideoDao;
import com.service.TestCollect.pojo.Video;
import com.zhan.ex.UnisException;
import com.zhan.utils.PropertiesFactory;
import com.zhan.utils.Tools;

/**
 * 
 * @author zhanmeihe
 * 
 */
@Controller
public class DoMainController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(DoMainController.class);

	@Resource
	private VideoDao videoDao;

	@RequestMapping(value = "/unis/NeteaseLive/app-htm", method = RequestMethod.GET)
	public String GetVideoapp(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<String> lines = PropertiesFactory.loadAllWebs();
			for (String line : lines) {
				List<String> portals = PropertiesFactory.loadPortalsByWeb(line);
				for (String portal : portals) {
					saveNeteaseVideo(portal);
				}
			}

			return "app-h";

		} catch (UnisException e) {
			LOGGER.info("异常", e);
		} catch (Exception e) {
			LOGGER.info("异常", e);
		}
		return "";
	}
	
	@RequestMapping( value  = "/unis/show.video.htl/{vid}/.php",method = RequestMethod.GET)
	public String VideoShow(@PathVariable("vid") String vid,Model model){
		
		try {
			
			Video vo = videoDao.queryId(vid);
			
			model.addAttribute("vidpath",vo);
			
			return "netease/Showvideo";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	@RequestMapping(value = "/video/netease.py/{PageONE}/{MaxRows}", method = RequestMethod.GET)
	public String videoList(@PathVariable("PageONE") int PageONE,
			@PathVariable("MaxRows") int MaxRows, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			int firstResult = PageONE * MaxRows;
			List<Video> de = videoDao.querydata(firstResult, MaxRows);
			model.addAttribute("videolist", de);
			return "netease/NeteasteVideo";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";

	}

	public void saveNeteaseVideo(String portal) throws InterruptedException {
		String porType = null;
		String[] url = portal.split(" ");
		porType = url[1];
		System.err.println("抓取视频类型：" + porType);
		if (porType.equals("BoBo")) {

			for (int i = 0; i < 11; i++) {
				String ui = url[0].substring(0, url[0].lastIndexOf("/")) + "/"
						+ i + "-10.html";
				String source = Tools.getSource(ui, "utf-8");
				JSONObject obo = JSONObject.fromObject(source);
				JSONArray array = JSONArray.fromObject(obo.get("VBK3JKUIF"));
				for (int j = 0; j < array.size(); j++) {
					Video ns = new Video();
					ns.setAppId("16");
					ns.setProgType(porType);
					System.out.println("图片："
							+ JSONObject.fromObject(array.get(j)).get("cover")
									.toString());
					ns.setImageUrl(JSONObject.fromObject(array.get(j))
							.get("cover").toString());
					ns.setProgid(JSONObject.fromObject(array.get(j)).get("vid")
							.toString());
					ns.setPublishDate(JSONObject.fromObject(array.get(j))
							.get("ptime").toString());
					ns.setName(JSONObject.fromObject(array.get(j)).get("title")
							.toString());
					ns.setSysDate(getNowDate());
					System.out.println("详细地址："
							+ "http://3g.163.com/v/video/"
							+ JSONObject.fromObject(array.get(j)).get("vid")
									.toString() + ".html");
					ns.setUrl("http://3g.163.com/v/video/"
							+ JSONObject.fromObject(array.get(j)).get("vid")
									.toString() + ".html");
					String vid = JSONObject.fromObject(array.get(j)).get("vid")
							.toString();
					System.out.println("标题："
							+ JSONObject.fromObject(array.get(j)).get("title")
									.toString());
					System.out.println("说明："
							+ JSONObject.fromObject(array.get(j))
									.get("description").toString());
					System.out.println("播放地址："
							+ JSONObject.fromObject(array.get(j))
									.get("mp4_url").toString());
					System.out.println("主键id："
							+ JSONObject.fromObject(array.get(j)).get("vid")
									.toString());
					System.out.println("发布时间："
							+ JSONObject.fromObject(array.get(j)).get("ptime")
									.toString());
					ns.setDescription(JSONObject.fromObject(array.get(j))
							.get("description").toString());
					ns.setArea("");
					ns.setVideoPath(JSONObject.fromObject(array.get(j))
							.get("mp4_url").toString());
					ns.setActor("");
					ns.setAppName("网易新闻.点播");
					ns.setDirector("");
					ns.setPlayCount("");
					if (videoDao.queryId(vid) == null) {
						videoDao.create(ns);
					}
				}
			}

		} else {

			String vid = Tools.source(url[0], "GBK")
					.replace("callback_video(", "").replace(")", "");

			System.err.println("点播视频vid" + vid);

			try {
				JSONObject ob = JSONObject.fromObject(vid);
				JSONArray array = JSONArray.fromObject(ob.get("list"));
				for (int i = 0; i < array.size(); i++) {
					Video ns = new Video();
					ns.setAppId("");
					ns.setProgType(porType);
					ns.setSysDate(getNowDate());
					String title = JSONObject.fromObject(array.get(i))
							.get("title").toString();
					ns.setName(title);
					System.out.println("标题 ====" + title);
					String weburl = JSONObject.fromObject(array.get(i))
							.get("url").toString();
					ns.setUrl(weburl);
					System.out.println("网址 ====" + weburl);
					String imgurl = JSONObject.fromObject(array.get(i))
							.get("img").toString();
					ns.setImageUrl(imgurl);
					System.out.println("图片 ====" + imgurl);
					String vid2 = JSONObject.fromObject(array.get(i))
							.get("vid").toString();
					ns.setProgid(vid2);

					String comment = JSONObject.fromObject(array.get(i))
							.get("comment").toString();
					if (comment.equals("{}")) {
						System.out.println("空的");
						// JSONArray comment = JSONArray.fromObject();
						ns.setPublishDate("");

					} else {

						JSONObject JO = JSONObject.fromObject(comment);
						String date = JO.getString("createTime");
						System.out.println("创建时间++++"
								+ JO.getString("createTime"));
						ns.setPublishDate(date);
					}
					ns.setDescription(title);
					ns.setArea("");

					String a = vid2.substring(vid2.length() - 2,
							vid2.length() - 1);
					String b = vid2.substring(vid2.length() - 1);
					String xml = "http://xml.ws.126.net/video/" + a + "/" + b
							+ "/1000_" + vid2 + ".xml";
					System.out.println(xml);
					String data = Tools.getSource(xml, "GBK");
					Matcher m = Pattern.compile("<flv>([^<]+)</flv>").matcher(
							data);
					if (m.find()) {
						String url2 = m.group(1);
						System.out.println("正则解析数据" + url2);
						ns.setVideoPath(url2);
						ns.setActor("");
						ns.setAppName("网易新闻.点播");
						ns.setDirector("");
						ns.setPlayCount("");

						System.err.println("没问题！");
					} else {
						ns.setVideoPath("");
						ns.setActor("");
						ns.setAppName("网易新闻.点播");
						ns.setDirector("");
						ns.setPlayCount("");

						System.err.println("不要走这！！");
					}
					if (videoDao.queryId(vid2) == null) {
						videoDao.create(ns);
					}
				}
				System.out.println("总数 ====" + array.size());
			} catch (Exception e) {
				System.err.println("异常为：" + e);
			}

		}
	}

	public String getNowDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return (df.format(new Date()));// new Date()为获取当前系统时间
	}

	public static void main(String[] args) {
		Video dc = new Video();

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
