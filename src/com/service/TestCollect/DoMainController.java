package com.service.TestCollect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.service.TestCollect.dao.VideoDao;
import com.service.TestCollect.pojo.UserInfo;
import com.service.TestCollect.pojo.Video;
import com.service.TestCollect.service.NeteaseVideoService;
import com.zhan.ex.RuleException;
import com.zhan.ex.UnisException;
import com.zhan.response.CommonResponse;
import com.zhan.response.SuccessResponse;
import com.zhan.response.SystemErrorResponse;
import com.zhan.utils.CommonUtils;
import com.zhan.utils.PropertiesFactory;
import com.zhan.utils.RegexConst;
import com.zhan.utils.Tools;


/**
 * 
 * @author zhanmeihe
 * 
 */
@Controller
public class DoMainController implements Runnable {

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
					 saveNeteaseVideo(portal);
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
	
	/**
	 * 获取所有分类total数接口
	 */
	 
	@RequestMapping(value = "/unis/category/InUYBOIh8hMHHOHIG-htm",method = RequestMethod.GET,produces = "application/json")
	public String IficationVideo(@RequestParam(value = "token") String token,Model model){
		
		try {
			
			List<Video> de = videoDao.querytype();
			model.addAttribute("arraytype", de);
			return "netease/VideoType";
		} catch (UnisException e) {
		}catch (Exception e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
		}
		return "errorpage/error";
	}
	
	
	@RequestMapping(value = "/unis/videodetail.PHP",method = RequestMethod.GET,produces = "application/json") 
	public String getDetail(@RequestParam(value = "videotype") String videotype,@RequestParam(value = "start") int start,
			@RequestParam(value = "pageSzie") int pageSzie,HttpServletRequest request, HttpServletResponse response,
			Model model){
		
		try {
			int firstResult = start * pageSzie;
			List<Video> vo = videoDao.queryvideoType(firstResult, pageSzie, videotype);
			model.addAttribute("videolist", vo);
			return "netease/NeteasteVideo";
		} catch (UnisException e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
		}catch (Exception e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
			}
		}
	/*
	 * ,@RequestParam(value = "Sex") String Sex,
			@RequestParam(value = "PhoneNum") String  PhoneNum,@RequestParam(value = "IdcardNum") String IdcardNum,
			@RequestParam(value = "YearNum") String YearNum,
			@RequestParam(value = "WorkType") String WorkType,
	 */
	@RequestMapping(value ="/Userinfo/UserRegistr" ,method  = RequestMethod.POST)
	public String Registr(@RequestParam(value = "UserName") String UserName,
			@RequestParam(value = "identity") int Sex,
			@RequestParam(value = "PhoneNum") String  PhoneNum,@RequestParam(value = "IdcardNum") String IdcardNum,
			@RequestParam(value = "YearNum") String YearNum,
			@RequestParam(value = "WorkType") String WorkType,
			HttpServletRequest request, HttpServletResponse response,
			Model model){
		try {
			model.addAttribute("phone", PhoneNum);
			System.err.println(UserName);
			System.err.println(Sex);
			System.err.println(PhoneNum);
			System.err.println(IdcardNum);
			System.err.println(YearNum);
			System.err.println(WorkType);
			
			return "ant/idcard";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
		
	@RequestMapping(value = "/Userinfo/IndexRe.shtml",method =RequestMethod.GET)
	public String IndexReg(){
		try {
			System.err.println("机智的跳转页面！");
			return "ant/personal";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/personal";
	}
	
	@RequestMapping(value = "/Userinfo/Idcard.shtml",method =RequestMethod.GET)
	public String IndexIdcArd(Model model){
		try {
			
//			model.addAttribute("phone", "13521294806");
			System.err.println("跳转页面！");
			return "ant/idcard";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/idcard";
	}
	
	@RequestMapping(value = "/Userinfo/orderlist.shtml",method =RequestMethod.GET)
	public String OrderList(){
		try {
			System.err.println("跳转页面！");
			return "ant/order-list";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/order-list";
	}
	
	@RequestMapping(value = "/Userinfo/PersonalCenter.shtml",method =RequestMethod.GET)
	public String PersonalCenter(Model model){
		try {
			model.addAttribute("phone", "13521294806");
			System.err.println("跳转页面！");
			return "ant/personal-center";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/order-list";
	}
	
	
	@RequestMapping(value = "/Userinfo/ListDetails.shtml",method =RequestMethod.GET)
	public String ListDetails(){
		try {
			System.err.println("跳转页面！");
			return "ant/order-list-details";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/order-list";
	}
	
	
	@RequestMapping(value = "/Userinfo/Details.shtml",method =RequestMethod.GET)
	public String Details(){
		try {
			System.err.println("跳转页面！");
			return "ant/details";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/order-list";
	}
	
	@RequestMapping(value = "/Userinfo/ImageUpage.shtml",method = RequestMethod.POST)
	public void ImageUp(UserInfo vo){
		try {
			
			ImageUpload(vo);
			System.err.println("上传成功！");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("上传失败！");
		}
		System.out.println("上传成功！");
	}
	
	@RequestMapping(value = "/common/showIcon",method = RequestMethod.GET)
	public void showIcon(@RequestParam(required = true) String fileName, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			validate(fileName);
			String filePath = "/home/zhan/ee/" + fileName;
			request.setAttribute("decorator", "none");
			setResponse(fileName, response);
			responseOutputFile(response, filePath, "default_icon.png");
		} catch (Exception e) {
			responseError(response, e.getMessage());
		}
	}

//	@RequestMapping(value = "/common/showFile")
//	public void showFile(@RequestParam(required = true) String fileName, HttpServletResponse response,
//			HttpServletRequest request) {
//		try {
//			//validate(fileName);
//			String filePath = Const.MP3_FILE_URL + fileName;
//			request.setAttribute("decorator", "none");
//			setResponse(fileName, response);
//			responseOutputFile(response, filePath, "noFile.jpg");
//		} catch (Exception e) {
//			responseError(response, e.getMessage());
//		}
//	}

	private void responseOutputFile(HttpServletResponse response, String filePath, String defaultFileName) {
		BufferedInputStream bis = null;
		OutputStream outp = null;
		try {
			try {
				bis = new BufferedInputStream(new FileInputStream(filePath));
			} catch (Exception e) {
				bis = new BufferedInputStream(new FileInputStream(getNoFile(defaultFileName)));
			}
			outp = response.getOutputStream();
			byte[] b = new byte[1024];
			int length = 0;
			if (null != outp) {
				while ((length = bis.read(b)) != -1) {
					outp.write(b, 0, length);
				}
				outp.flush();
			}
		} catch (Throwable e) {
			LOGGER.debug("", e);
		} finally {
			IOUtils.closeQuietly(outp);
			IOUtils.closeQuietly(bis);
		}
	}

	private void setResponse(String chopName, HttpServletResponse response) {
		response.reset();
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.addHeader("Content-Disposition", "attachment;filename=" + chopName);
		response.setContentType("APPLICATION/OCTET-STREAM ");
	}

	private String getNoFile(String fileName) {
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		path = StringUtils.replace(path, "\\", "/");
		path = StringUtils.replace(path, "/WEB-INF/classes/", "/style/imgs/");
		path = path + fileName;
		return path;
	}

	private void validate(String fileName) throws Exception {
		if (StringUtils.isBlank(fileName)) {
			throw new Exception("File name cannot be empty!");
		}
		Pattern pattern = Pattern.compile(RegexConst.PHOTO);
		Matcher matcher = pattern.matcher(fileName.toLowerCase());
		if (!matcher.matches()) {
			throw new Exception("File name error!");
		}
	}

	private void responseError(HttpServletResponse response, String message) {
		PrintWriter fs = null;
		try {
			response.setCharacterEncoding("UTF-8");
			fs = response.getWriter();
			fs.write("{\"code\":\"300\",\"msg\":\"" + message + "\"}");
			fs.flush();
		} catch (IOException e) {
			IOUtils.closeQuietly(fs);
		}

	}

	
 
	private UserInfo ImageUpload(UserInfo vo) throws Exception {
		 
			String newFileName = null;

			
			List<MultipartFile> file = vo.getHeadPic();
			for (MultipartFile multipartFile : file) {
				
				if (vo.getHeadPic() != null) {
					fileSizeOverrun(multipartFile);

					String originalFileName = multipartFile.getOriginalFilename();
					newFileName = CommonUtils.getUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
					File picFile = new File("/home/zhan/ee/" + newFileName);

					try {
						multipartFile.transferTo(picFile);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuleException("上传头像失败");
					}
					// write picFile http name into mysql database;
				}
				newFileName = "http://192.168.124.4:8877/APP/common/showIcon?fileName=" + newFileName;
				//vo.setIconUrl(newFileName);

				//UserInfo user = modifyHeadPic(vo);
				//userimInfoDao.updateimUserPic(user);

			}

			return vo;

		 
			
	}
	
	private void fileSizeOverrun(MultipartFile file) throws Exception {
		if (file != null && file.getSize() > (3 * 1024 * 1024)) {
			throw new Exception("大小超过3M");
		}
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
					System.out.println("时长："
							+ JSONObject.fromObject(array.get(j)).get("length")
									.toString());
					String length = JSONObject.fromObject(array.get(j)).get("length")
							.toString();
					int a = Integer.parseInt(length)/60;
					int b = Integer.parseInt(length)%60;
					String a1 = String.valueOf(a);
					String b1 = String.valueOf(b);
					String time = a1+":"+b1;
					ns.setTimeLength(time);
					ns.setImageUrl(JSONObject.fromObject(array.get(j))
							.get("cover").toString());
					ns.setProgid(JSONObject.fromObject(array.get(j)).get("vid")
							.toString());
					ns.setPublishDate(JSONObject.fromObject(array.get(j))
							.get("ptime").toString());
					ns.setName(JSONObject.fromObject(array.get(j)).get("title")
							.toString());
					ns.setSysDate(CommonUtils.getNowDate());
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
					ns.setSysDate(CommonUtils.getNowDate());
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
					System.out.println("时长 ====" + JSONObject.fromObject(array.get(i))
							.get("playlength").toString());
					String length = JSONObject.fromObject(array.get(i)).get("playlength")
							.toString();
					int aa = Integer.parseInt(length)/60;
					int bb = Integer.parseInt(length)%60;
					String a1 = String.valueOf(aa);
					String b1 = String.valueOf(bb);
					String time = a1+":"+b1;
					ns.setTimeLength(time);
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


	
	@Override
	public void run() {
		List<String> lines = PropertiesFactory.loadAllWebs();
		for (String line : lines) {
			List<String> portals = PropertiesFactory.loadPortalsByWeb(line);
			for (String portal : portals) {
				 try {
					saveNeteaseVideo(portal);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		
		 System.err.println(CommonUtils.getUUID());
	}

}
