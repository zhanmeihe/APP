package com.service.TestCollect.pojo;

import java.io.Serializable;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2456113726319248157L;

	public UserInfo() {
		// TODO Auto-generated constructor stub
	}
	
	private  String userId;
	
	private String introducer;
	
	private String address;
	
	private String openid;
	
	private String BankCard;
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getBankCard() {
		return BankCard;
	}

	public void setBankCard(String bankCard) {
		BankCard = bankCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 用户名
	 */
	private String UserName;
	
	/**
	 * 性别 1：男  0：女
	 */
	private int Sex;
	
	/**
	 * 手机号
	 */
	private int PhoneNum;
	
	/**
	 * 身份证号
	 */
	private String IdcardNum;

	/**
	 * 工作年限
	 */
	private String YearNum;
	
	/**
	 * 工作种类
	 */
	private  String WorkType;
	
	/**
	 * 头像图片
	 */
	private String PicImage;
	
	/**
	 * 接收的图片二进制文件
	 */
	private List<MultipartFile> headPic;
	
	/**
	 * 身份证照片
	 */
	private List<String> CheckIdcardPicUrl;
	
	 

	public List<String> getCheckIdcardPicUrl() {
		return CheckIdcardPicUrl;
	}

	public void setCheckIdcardPicUrl(List<String> checkIdcardPicUrl) {
		CheckIdcardPicUrl = checkIdcardPicUrl;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public int getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(int phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getIdcardNum() {
		return IdcardNum;
	}

	public void setIdcardNum(String idcardNum) {
		IdcardNum = idcardNum;
	}

	public String getYearNum() {
		return YearNum;
	}

	public void setYearNum(String yearNum) {
		YearNum = yearNum;
	}

	 
	public String getWorkType() {
		return WorkType;
	}

	public void setWorkType(String workType) {
		WorkType = workType;
	}

	public String getPicImage() {
		return PicImage;
	}

	public void setPicImage(String picImage) {
		PicImage = picImage;
	}

	public List<MultipartFile> getHeadPic() {
		return headPic;
	}

	public void setHeadPic(List<MultipartFile> headPic) {
		this.headPic = headPic;
	}

	 

}
