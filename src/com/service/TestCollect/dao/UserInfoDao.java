package com.service.TestCollect.dao;

import org.apache.ibatis.annotations.Param;

import com.service.TestCollect.pojo.UserInfo;

public interface UserInfoDao {
	
	
	UserInfo FinadInfo(@Param("userId") String userId,@Param("phonenum") String phonenum);
	
	void CreateUserInfo(UserInfo info);

}
