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

	private static Logger LOGGER = LoggerFactory
			.getLogger(DoMainController.class);

	/**
	 * 网易直播URL抓取
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unis/NeteaseLive", method = RequestMethod.GET, produces = "application/json")
	public CommonResponse GetVideo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = "http://data.live.126.net/livechannel/previewlist.json 汇总";

			List<News> bd = GetNeteaseLiveList(url,request,response);

			return new CommonSuccessResponse(bd);

		} catch (UnisException e) {
			LOGGER.info("异常", e);
		} catch (Exception e) {
			LOGGER.info("异常", e);
		}
		return new SystemErrorResponse();
	}
	
	@RequestMapping(value = "/unis/NeteaseLive/app-htm", method = RequestMethod.GET)
	public String GetVideoapp(Model model,HttpServletRequest request, HttpServletResponse response) {
		try {
			String url = "http://data.live.126.net/livechannel/previewlist.json 汇总";

			List<News> bd = GetNeteaseLiveList(url,request,response);
			model.addAttribute("data-app", bd);

			return "app-h";

		} catch (UnisException e) {
			LOGGER.info("异常", e);
		} catch (Exception e) {
			LOGGER.info("异常", e);
		}
		return "";
	}

	public static List<News> GetNeteaseLiveList(String por,HttpServletRequest request, HttpServletResponse response) {

		List<News> nsn = new ArrayList<News>();
		News ns = new News();
		try {
			String[] portype = por.split(" ");
			String porltype = portype[1];
			ns.setProgType(porltype);
			System.err.println("直播类型：" + porltype);
			String jsonsource = Tools.source(portype[0], "utf-8");

			String js = JSONObject.fromObject(jsonsource).get("live_review")
					.toString();
			JSONArray ja = JSONArray.fromObject(js);

			for (int i = 0; i < ja.size(); i++) {
				Integer stat = Integer.parseInt(JSONObject
						.fromObject(ja.get(i)).get("liveStatus").toString());
				int status = (int) (stat);

				if (status == 1) {
					String roomId = JSONObject.fromObject(ja.get(i))
							.get("roomId").toString();
					String detailurl = "http://data.live.126.net/liveAll/"
							+ roomId + ".json";
					String videodata = Tools.source(detailurl, "utf-8");
//					Thread.sleep(2000);
					JSONObject ob = JSONObject.fromObject(videodata);
					boolean bannerurl2 = JSONObject.fromObject(ob).containsKey(
							"banner");
					if (bannerurl2) {

						String roomId2 = ob.getString("roomId").toString();
						ns.setProgid(roomId2);
						System.out.println("房间id：" + roomId);
						System.out.println("ob等于：" + ob);
						String roomName = ob.getString("roomName");
						System.out.println("房间名：" + roomName);
						String startDate = ob.getString("startDate");
						ns.setPublishDate(startDate);
						System.out.println("直播开始时间：" + startDate);
						System.out.println("直播结束时间：" + ob.getString("endDate"));
						ns.setName(roomName);
						if (ob.containsKey("roomDes")) {

							String roomDes = ob.getString("roomDes");
							ns.setDescription(roomDes);
							System.out.println("直播说明：" + roomDes);
						} else {
							ns.setDescription("");
						}
						boolean ct = JSONObject.fromObject(ob).containsKey(
								"sourceinfo");
						if (!ct) {
							String ct2 = JSONObject.fromObject(ob)
									.get("chatConfig").toString();
							int ctq = JSONObject.fromObject(ct2).getInt(
									"chatRoomCount");
							ns.setPlayCount(String.valueOf(ctq));
							System.out.println("观众人数：" + ctq);
						} else {
							String ct1 = JSONObject.fromObject(ob)
									.getString("sourceinfo").toString();
							int ct0 = JSONObject.fromObject(ct1).getInt(
									"tcount");
							ns.setPlayCount(String.valueOf(ct0));
							System.out.println("观众人数：" + ct0);
						}
						String bannerurl = JSONObject.fromObject(ob)
								.get("banner").toString();
						bannerurl = JSONObject.fromObject(bannerurl).getString(
								"url");
						if (ct) {

						}
						System.out.println("直播封面：" + bannerurl);
						ns.setImageUrl(bannerurl);

						boolean videoTO = JSONObject.fromObject(ob)
								.containsKey("video");

						if (videoTO) {
							String videoUrl = JSONObject.fromObject(ob)
									.get("video").toString();
							videoUrl = JSONObject.fromObject(videoUrl)
									.getString("flvUrl");
							if (videoUrl == null) {
								videoUrl = JSONObject.fromObject(videoUrl)
										.getString("videoUrl");
								ns.setVideoPath(videoUrl);
								System.out.println(videoUrl);
							} else {
								ns.setVideoPath(videoUrl);
								System.out.println("直播视频流：" + videoUrl);
							}

						} else {
							String videoto = JSONObject.fromObject(ob)
									.get("mutilVideo").toString();
							String ay = JSONArray.fromObject(videoto).get(0)
									.toString();
							JSONObject ct4 = JSONObject.fromObject(ay);

							if (ct4.containsKey("url1")) {
								System.out.println("@@@@@@"
										+ ct4.getString("url1"));
								ns.setVideoPath(ct4.getString("url1"));
							} else {
								System.out.println("@@@@@@"
										+ ct4.getString("url"));
								ns.setVideoPath(ct4.getString("url"));
							}

						}

						nsn.add(ns);

					}
				}
			}

			System.out.println("全部直播个数：" + nsn.size());
			return nsn;
		} catch (Exception e) {
			System.out.println("异常为：" + e.getMessage());
		}
		return null;
	}

	public static void main(String[] args) {
		//GetNeteaseLiveList("http://data.live.126.net/livechannel/previewlist.json 汇总");

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
