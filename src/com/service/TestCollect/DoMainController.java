package com.service.TestCollect;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.service.TestCollect.dao.MyorderInfoDao;
import com.service.TestCollect.dao.TaskInfoDao;
import com.service.TestCollect.dao.UserInfoDao;
import com.service.TestCollect.dao.VideoDao;
import com.service.TestCollect.dao.WorkVideoDao;
import com.service.TestCollect.pojo.MyorderInfo;
import com.service.TestCollect.pojo.SNSUserInfo;
import com.service.TestCollect.pojo.TaskInfo;
import com.service.TestCollect.pojo.UserInfo;
import com.service.TestCollect.pojo.UserInfofileVo;
import com.service.TestCollect.pojo.Video;
import com.service.TestCollect.pojo.WeixinOauth2Token;
import com.service.TestCollect.pojo.WorkVideo;
import com.service.TestCollect.service.NeteaseVideoService;
import com.service.TestCollect.weixinutils.AdvancedUtil;
import com.zhan.ex.RuleException;
import com.zhan.ex.UnisException;
import com.zhan.response.CommonResponse;
import com.zhan.response.SuccessResponse;
import com.zhan.response.SystemErrorResponse;
import com.zhan.utils.CommonUtils;
import com.zhan.utils.Configure;
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

	private static Logger LOGGER = LoggerFactory
			.getLogger(DoMainController.class);

	@Resource
	private VideoDao videoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private TaskInfoDao taskInfoDao;
	@Resource
	private MyorderInfoDao myorderInfoDao;
	
	@Resource
	private WorkVideoDao workVideoDao;

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

	@RequestMapping(value = "/unis/category/InUYBOIh8hMHHOHIG-htm", method = RequestMethod.GET, produces = "application/json")
	public String IficationVideo(@RequestParam(value = "token") String token,
			Model model) {

		try {

			List<Video> de = videoDao.querytype();
			model.addAttribute("arraytype", de);
			return "netease/VideoType";
		} catch (UnisException e) {
		} catch (Exception e) {
			model.addAttribute("errormsg", e);
			LOGGER.info("异常", e);
			return "errorpage/error";
		}
		return "errorpage/error";
	}

	@RequestMapping(value = "/unis/videodetail.PHP", method = RequestMethod.GET, produces = "application/json")
	public String getDetail(
			@RequestParam(value = "videotype") String videotype,
			@RequestParam(value = "start") int start,
			@RequestParam(value = "pageSzie") int pageSzie,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {

		try {
			int firstResult = start * pageSzie;
			List<Video> vo = videoDao.queryvideoType(firstResult, pageSzie,
					videotype);
			model.addAttribute("videolist", vo);
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
	 * -----------------{蚂蚁阿奇工程项目接口}-------------------------
	 */

	/**
	 * 注册提交信息content
	 */
	@RequestMapping(value = "/Userinfo/UserRegistr", method = RequestMethod.POST)
	public String Registr(@RequestParam(value = "UserName") String UserName,
			@RequestParam(value = "identity") String Sex,
			@RequestParam(value = "PhoneNum") String PhoneNum,
			@RequestParam(value = "IdcardNum") String IdcardNum,
			@RequestParam(value = "YearNum") String YearNum,
			@RequestParam(value = "WorkType") String WorkType,
			@RequestParam(value = "openId") String openId,
			@RequestParam(value = "headImgUrl") String headImgUrl,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			// model.addAttribute("phone", PhoneNum);
			// System.err.println(UserName);
			// System.err.println(Sex);
			// System.err.println(PhoneNum);
			// System.err.println(IdcardNum);
			// System.err.println(YearNum);
			// System.err.println(WorkType);

			UserInfo user = new UserInfo();
			user.setUserName(UserName);
			user.setSex(String.valueOf(Sex));
			user.setPhoneNum(PhoneNum);
			user.setIdcardNum(IdcardNum);
			user.setYearNum(YearNum);
			user.setWorkType(WorkType);
			user.setOpenid(openId);
			user.setPicImage(headImgUrl);
			user.setCreatetime(CommonUtils.getNowDate());
			model.addAttribute("info", user);
			model.addAttribute("idcardNum", user.getIdcardNum());
			return "ant/idcard";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/index";
	}

	/**
	 * 注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/IndexRe.shtml", method = RequestMethod.GET)
	public String IndexReg(HttpServletRequest request,
			HttpServletResponse response, Model model, Configure conf) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			// 用户同意授权后，能获取到code
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			String ss = "123";
			// // 用户同意授权
			if (!"authdeny".equals(code)) {
				// // 获取网页授权access_token
				WeixinOauth2Token weixinOauth2Token = AdvancedUtil
						.getOauth2AccessToken(conf.getproperties("appid"),
								conf.getproperties("secrn"), code);
				// 网页授权接口访问凭证
				String accessToken = weixinOauth2Token.getAccessToken();
				// 用户标识
				String openId = weixinOauth2Token.getOpenId();
				// 获取用户信息
				SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(
						accessToken, openId);
				// 设置要传递的参数
				request.setAttribute("snsUserInfo", snsUserInfo);
				request.setAttribute("state", state);
				String openid = snsUserInfo.getOpenId();
				UserInfo info = new UserInfo();
				info = userInfoDao.queryId(snsUserInfo.getOpenId(),
						null);
				HttpSession session = request.getSession();
				// userInfoDao.queryId("a78bef1e17804fa68c412b6fcb39385e");
				if (info != null) {
					
//					request.getSession().setAttribute("name",info.getOpenid());
					model.addAttribute("user", info);
					model.addAttribute("idcardNum", info.getIdcardNum());
					session.setAttribute("userInfo", info);
					return "ant/personal-center";
				} else {
					model.addAttribute("snsUserInfo", snsUserInfo);
					return "ant/personal";
					/**
					 * 保留页面
					 */
					// return "ant/index";
				}
			}
			// return "ant/personal";
			// return "ant/info";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
	}

	/**
	 * 注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/testlocal.shtml/{userId}", method = RequestMethod.GET)
	public String Test(@PathVariable("userId") String userId,
			HttpServletRequest request, HttpServletResponse response,HttpSession session,
			Model model, Configure conf) {
		try {
			// 59320f39375c463b8b08d1095d5027c1
			String ss = "123";
			if (!"authdeny".equals(ss)) {
 
				UserInfo dd = userInfoDao.MaxId();
				System.out.println(dd.getPersonNum());
				UserInfo info = new UserInfo();
				info = userInfoDao.queryId(null, userId);
				if (info != null) {
					model.addAttribute("user", info);
					HttpSession httpSession =request.getSession();
					httpSession.setAttribute("username", "张三");
					model.addAttribute("idcardNum", info.getIdcardNum());
					return "ant/personal-center";
				} else {
					model.addAttribute("snsUserInfo", null);
					return "ant/personal";
				}
			}
		} catch (UnisException e) {
			System.err.println(e);
		} catch (Exception e) {
			System.err.println(e);
		}
		return "ant/fail";
	}

	/**
	 * 上传身份证页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/MapAddress/GpsAddress.shtml/{AddressName}", method = RequestMethod.GET)
	public String IndexIdcArd(@PathVariable("AddressName") String AddressName,Model model,HttpSession session) {
		try {
		 
			model.addAttribute("Addressinfo", AddressName);
			return "ant/map";
		} catch (UnisException e) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
	}

	/**
	 * 跳转抢单{list}页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/orderlist.shtml/{userId}", method = RequestMethod.GET)
	public String OrderList(@PathVariable("userId") String userId,
			HttpServletRequest request, HttpServletResponse response,
			Model model,HttpSession session) {
		try {
			HttpSession httpSession =request.getSession();
			//httpSession.setMaxInactiveInterval(60);
			if (httpSession.getAttribute("username")!=null) {
				System.err.println("有session的，session userId等于"+httpSession.getAttribute("username"));
				model.addAttribute("username", httpSession.getAttribute("username"));
			}else {
				System.err.println("没有获取到session");
			}
			if (userId != null) {
				List<TaskInfo> info = taskInfoDao.selectInfo();
				model.addAttribute("task", info);
				if (info.size()<=0) {
					model.addAttribute("skin", info.size());
				}
				model.addAttribute("userId", userId);
				return "ant/order-list";
			}
			return "ant/index";

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
	}
	
	
	/**
	 * 施工页面数据展示
	 */
	
	
	@RequestMapping(value = "/working/orderwork.shtml/{userId}",method = RequestMethod.GET)
	public String WorkingList(@PathVariable("userId") String userId,HttpServletRequest request, 
			HttpServletResponse response, Model model){
		
		try {
			if (userId!=null) {
				List<MyorderInfo> order = myorderInfoDao.queryOrder(userId);
//				if (order == null) {
//					UserInfo info = userInfoDao.queryId(null, userId);
//					model.addAttribute("idcardNum", info.getIdcardNum());
//					model.addAttribute("user", info);
//					return "ant/personal-center";
//				}
				model.addAttribute("orderinfo", order);
				model.addAttribute("skin", order.size());
				if (order.size()>0) {
					model.addAttribute("userId", order.get(0).getUserId());
				}
				
				return "ant/working";
			}else {
				return "ant/index";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
		
	}

	/**
	 * 跳转个人中心
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/PersonalCenter.shtml/{userId}", method = RequestMethod.GET)
	public String PersonalCenter(@PathVariable("userId") String userId,HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			if (userId != null) {
				UserInfo info = userInfoDao.queryId(null, userId);
				model.addAttribute("user", info);
				model.addAttribute("idcardNum", info.getIdcardNum());
				return "ant/personal-center";
			} else {
				return "ant/index";
			}
		} catch (UnisException e) {
			LOGGER.info("异常", e);
			return "ant/index";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/index";
	}

	/**
	 * 跳转个人订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/ListDetails.shtml/{id}/{userId}", method = RequestMethod.GET)
	public String PersonListDetails(@PathVariable("id") String id,
			@PathVariable("userId") String userId, Model model) {
		try {
			if (id != null && userId != null) {
				MyorderInfo orderinfo = myorderInfoDao.selectOrderInfo(id);
				model.addAttribute("info", orderinfo);
				return "ant/my-order-list-details";
			}
			return "ant/index";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/index";
	}
	
	/**
	 * 跳转抢单订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/ListDetailsOrder.shtml/{id}/{userId}", method = RequestMethod.GET)
	public String ListDetails(@PathVariable("id") String id,
			@PathVariable("userId") String userId, Model model) {
		try {
			if (id != null && userId != null) {
				TaskInfo task = taskInfoDao.findId(id);
				model.addAttribute("info", task);
				model.addAttribute("userId", userId);
				return "ant/order-list-details";
			}
			return "ant/index";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/index";
	}

	
	/**
	 * 抢单请求{action---}
	 * @return
	 */
	
	@RequestMapping(value = "/orderInfo/GrabSingle.shtml",method = RequestMethod.POST)
	public String GrabSingle(HttpServletResponse response, HttpServletRequest request,Model model){
		
		String taskid = request.getParameter("taskId");
		String userId = request.getParameter("userId");
		try {
			if (taskid!=null && userId!=null ) {
				TaskInfo task = taskInfoDao.findId(taskid);
				UserInfo user = userInfoDao.queryId(null, userId);
				String[] userList = user.getWorkType().split(",");
				String[] list = task.getListIng().split(",");
				Arrays.sort(userList);
				Arrays.sort(list);
				if (Arrays.equals(userList, list)) {
					
					TaskInfo taskup = new TaskInfo();
					taskup.setOrderState(1);
					taskup.setId(taskid);
					taskup.setUpdateDate(CommonUtils.getNowDate());
					taskInfoDao.updateTaskInfo(taskup);
					MyorderInfo order = new MyorderInfo();
					
					order.setCreateTime(CommonUtils.getNowDate());
					order.setTaskId(taskid);
					order.setUserId(userId);
					order.setListIng(task.getListIng());
					order.setProductionNum(task.getProductionNum());
					order.setSalaryNum(task.getSalaryNum());
					order.setState(2);
					order.setTaskAddress(task.getTaskAddress());
					order.setTaskDate(task.getTaskDate());
					order.setTaskInstruction(task.getTaskInstruction());
					order.setUpdateDate(CommonUtils.getNowDate());
					myorderInfoDao.createTask(order);
					
					model.addAttribute("userId", userId);
					model.addAttribute( "taskId",order.getTaskId());
					return "ant/successorders";
				}else {
					model.addAttribute("userId", userId);
					return "ant/fail";
				}
			
			}else {
				model.addAttribute("userId", userId);
				return "ant/index";
			}	
		} catch (UnisException e) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
		model.addAttribute("userId", userId);
		return "ant/fail";
		
	}

	@RequestMapping(value = "/Userinfo/testre.shtml", method = RequestMethod.GET)
	public String testre() {
		try {
			System.err.println("跳转页面！");
			return "ant/personal";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/order-list";
	}

	/**
	 * 跳转修改个人信息页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/modifyInfo.shtml/{userId}", method = RequestMethod.GET)
	public String modifyInfo(@PathVariable("userId") String userId, Model model) {
		try {
			model.addAttribute("userId", userId);
			return "ant/modify-information";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/index";
	}

	@RequestMapping(value = "/Userinfo/orderlists.shtml/{userId}", method = RequestMethod.GET)
	public String MeOrderLists(@PathVariable("userId") String userId,Model model) {
		try {
			 if (userId!=null) {
				List<MyorderInfo> info  = myorderInfoDao.queryOrder(userId);
				if (info.size()<=0) {
					model.addAttribute("skin", info.size());
				}
				model.addAttribute("task", info);
				model.addAttribute("userId", userId);
				return "ant/my-order";
			}else {
				return "ant/index";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
	}
	
	/**
	 * 上班打卡页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/workin/Clockin.shtml",method = RequestMethod.POST)
	public String Clockin(@RequestParam(value = "userId") String userId,@RequestParam(value = "taskId") String taskId,
			UserInfofileVo vo,Model model) throws Exception{
		try {
			WorkVideo info = new WorkVideo();
			info.setUserId(userId);
			info.setCreateDate(CommonUtils.getNowDate());
			info.setUpdatetime(CommonUtils.getNowDate());
			info.setTaskId(taskId);
			Video2Upload(info, vo);
			model.addAttribute("userId", userId);
			return "ant/index";
		} catch (UnisException e) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
	}

	
	/**
	 * 跳转上班
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Userinfo/workclock.shtml/{userId}/{taskId}", method = RequestMethod.GET)
	public String Details(@PathVariable("userId") String userId,@PathVariable("taskId") String taskId,Model model) {
		try {
			model.addAttribute("userId", userId);
			model.addAttribute("taskId", taskId);
			return "ant/details";
		} catch (UnisException e) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/fail";
	}

	@RequestMapping(value = "/Userinfo/modifyUser.shtml", method = RequestMethod.POST)
	public String getmodifyinfo(
			@RequestParam(value = "UserName") String UserName,
			@RequestParam(value = "identity") String Sex,
			@RequestParam(value = "PhoneNum") String PhoneNum,
			@RequestParam(value = "YearNum") String YearNum,
			@RequestParam(value = "userId") String userId, Model model) {
		try {
			if (userId != null) {
				UserInfo info = new UserInfo();
				info.setUserName(UserName);
				info.setSex(Sex);
				info.setPhoneNum(PhoneNum);
				info.setYearNum(YearNum);
				info.setCreatetime(CommonUtils.getNowDate());
				info.setUserId(userId);
				userInfoDao.updateUserInfo(info);
				info = userInfoDao.queryId(null, userId);
				model.addAttribute("user", info);
				return "ant/personal-center";
			} else {
				return "ant/index";
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "ant/index";
	}

	/**
	 * 上传身份证接收二进制文件流
	 * 
	 * @param vo
	 */
	@RequestMapping(value = "/Userinfo/ImageUpage.shtml", method = RequestMethod.POST)
	public String ImageUp(@RequestParam(value = "userName") String UserName,
			@RequestParam(value = "sex") String Sex,
			@RequestParam(value = "phoneNum") String PhoneNum,
			@RequestParam(value = "idcardNum") String IdcardNum,
			@RequestParam(value = "yearNum") String YearNum,
			@RequestParam(value = "workType") String WorkType,
			@RequestParam(value = "openid") String openid,
			@RequestParam(value = "picImage") String picImage, UserInfo vo,
			UserInfofileVo v1, Model model) {
		try {
			UserInfo in = userInfoDao.MaxId();
			vo.setUserId(CommonUtils.getUUID());
			vo.setUserName(UserName);
			vo.setSex(Sex);
			vo.setPersonNum(in.getPersonNum()+1);
			vo.setPhoneNum(PhoneNum);
			vo.setIdcardNum(IdcardNum);
			vo.setYearNum(YearNum);
			vo.setWorkType(WorkType);
			vo.setOpenid(openid);
			vo.setPicImage(picImage);
			vo.setCreatetime(CommonUtils.getNowDate());
			vo.setUpdateDate(CommonUtils.getNowDate());
			if (v1.getHeadPic().get(0).getSize()<10||v1.
					getHeadPic().get(1).getSize()<10||v1.
					getHeadPic().get(2).getSize()<10) {
				 
				model.addAttribute("userId", vo.getUserId());
				 userInfoDao.create(vo);
				model.addAttribute("info", in);
				return "ant/success";
			}
//			if (v1.getHeadPic().get(0).g) {
//				
//			}
			
			UserInfo info =  ImageUpload(vo, v1);
			System.err.println("上传成功！");
			model.addAttribute("userId", vo.getUserId());
			model.addAttribute("info", info);
			return "ant/success";
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("上传失败！");
		}
		System.out.println("上传成功！");
		return "ant/index";
	}

	/**
	 * 上传施工上班视频
	 * 
	 * @param vo
	 */
	@RequestMapping(value = "/Userinfo/VideoValidation.shtml", method = RequestMethod.POST)
	public String VideoUpload(UserInfo vo, UserInfofileVo v1) {
		try {

			ImageUpload(vo, v1);
			System.err.println("上传成功！");
			return "redirect:/Userinfo/PersonalCenter.shtml";
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("上传失败！");
		}
		return "redirect:/Userinfo/PersonalCenter.shtml";
	}

	@RequestMapping(value = "/common/showIcon", method = RequestMethod.GET)
	public void showIcon(@RequestParam(required = true) String fileName,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			// validate(fileName);
			String filePath = "/FILE/images/" + fileName;
			request.setAttribute("decorator", "none");
			setResponse(fileName, response);
			responseOutputFile(response, filePath, "default_icon.png");
		} catch (Exception e) {
			responseError(response, e.getMessage());
		}
	}

	// @RequestMapping(value = "/common/showFile")
	// public void showFile(@RequestParam(required = true) String fileName,
	// HttpServletResponse response,
	// HttpServletRequest request) {
	// try {
	// //validate(fileName);
	// String filePath = Const.MP3_FILE_URL + fileName;
	// request.setAttribute("decorator", "none");
	// setResponse(fileName, response);
	// responseOutputFile(response, filePath, "noFile.jpg");
	// } catch (Exception e) {
	// responseError(response, e.getMessage());
	// }
	// }

	private void responseOutputFile(HttpServletResponse response,
			String filePath, String defaultFileName) {
		BufferedInputStream bis = null;
		OutputStream outp = null;
		try {
			try {
				bis = new BufferedInputStream(new FileInputStream(filePath));
			} catch (Exception e) {
				bis = new BufferedInputStream(new FileInputStream(
						getNoFile(defaultFileName)));
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
		response.addHeader("Content-Disposition", "attachment;filename="
				+ chopName);
		response.setContentType("APPLICATION/OCTET-STREAM ");
	}

	private String getNoFile(String fileName) {
		String path = this.getClass().getClassLoader().getResource("/")
				.getPath();
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

	private UserInfo ImageUpload(UserInfo vo1, UserInfofileVo vo)
			throws Exception {

		String newFileName = null;

		List<String> image = new ArrayList<>();
		List<MultipartFile> file = vo.getHeadPic();
		for (MultipartFile multipartFile : file) {

			if (vo.getHeadPic() != null) {
				fileSizeOverrun(multipartFile);

				String originalFileName = multipartFile.getOriginalFilename();
				newFileName = CommonUtils.getUUID()
						+ originalFileName.substring(originalFileName
								.lastIndexOf("."));
				File picFile = new File("/FILE/images/" + newFileName);

				try {
					multipartFile.transferTo(picFile);
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuleException("上传失败");
				}
				// write picFile http name into mysql database;
			}
			newFileName = "http://www.antarchi.com/common/showIcon?fileName="
					+ newFileName;
			// vo.setIconUrl(newFileName);
			image.add(newFileName);
			// UserInfo user = modifyHeadPic(vo);
			// userimInfoDao.updateimUserPic(user);

		}
		String dd = listToString(image, ',');
		System.err.println(dd);
		vo1.setCheckIdcardPicUrl(listToString(image, ','));

		userInfoDao.create(vo1);
		// for (int i = 0; i < vo.getCheckIdcardPicUrl().size(); i++) {
		//
		// System.err.println(vo.getCheckIdcardPicUrl().get(i));
		// }

		return vo1;

	}

	private WorkVideo Video2Upload(WorkVideo vo1, UserInfofileVo vo)
			throws Exception {

		String newFileName = null;
try {
	List<String> image = new ArrayList<>();
	List<MultipartFile> file = vo.getHeadPic();
	for (MultipartFile multipartFile : file) {

		if (multipartFile != null &&multipartFile.getSize() > 0) {
			fileSizeOverrun(multipartFile);

			String originalFileName = multipartFile.getOriginalFilename();
			newFileName = CommonUtils.getUUID()
					+ originalFileName.substring(originalFileName
							.lastIndexOf("."));
			File picFile = new File("/FILE/images/" + newFileName);

			try {
				multipartFile.transferTo(picFile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuleException("上传失败");
			}
			// write picFile http name into mysql database;
			newFileName = "http://www.antarchi.com/common/showIcon?fileName="
					+ newFileName;
			 
			image.add(newFileName);
		}
	 
	}
	String dd = listToString(image, ',');
	System.err.println(dd);
	vo1.setFileUrl(listToString(image, ','));

	workVideoDao.createVideo(vo1);
	// for (int i = 0; i < vo.getCheckIdcardPicUrl().size(); i++) {
	//
	// System.err.println(vo.getCheckIdcardPicUrl().get(i));
	// }

	return vo1;

} catch (UnisException e) {
	// TODO: handle exception
}catch (Exception e) {
	// TODO: handle exception
}
return null;
		
	}
	
	public String listToString(@SuppressWarnings("rawtypes") List list,
			char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	private void fileSizeOverrun(MultipartFile file) throws Exception {
		if (file != null && file.getSize() > (10 * 1024 * 1024)) {
			throw new Exception("大小超过10M");
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
					String length = JSONObject.fromObject(array.get(j))
							.get("length").toString();
					int a = Integer.parseInt(length) / 60;
					int b = Integer.parseInt(length) % 60;
					String a1 = String.valueOf(a);
					String b1 = String.valueOf(b);
					String time = a1 + ":" + b1;
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
					System.out.println("时长 ===="
							+ JSONObject.fromObject(array.get(i))
									.get("playlength").toString());
					String length = JSONObject.fromObject(array.get(i))
							.get("playlength").toString();
					int aa = Integer.parseInt(length) / 60;
					int bb = Integer.parseInt(length) % 60;
					String a1 = String.valueOf(aa);
					String b1 = String.valueOf(bb);
					String time = a1 + ":" + b1;
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

	public void tets() {

		// UserInfo info =
		// userInfoDao.queryId("a78bef1e17804fa68c412b6fcb39385e");
		// System.err.println(info.getAddress());
	}

	public static void main(String[] args) {
	 String[] aa = {"搜索","得到","恩恩"};
	 String[] bb = {"搜索","恩恩","得到"};
	 Arrays.sort(aa);
	 Arrays.sort(bb);
	 if (Arrays.equals(aa, bb)) {
		 System.out.println(aa);
		 System.out.println(bb);
		System.err.println("ok");
	}else {
		System.err.println("no");
	}
//
//		DoMainController ff = new DoMainController();
//		ff.tets();
//
//		System.err.println(CommonUtils.getUUID());
	}

}
