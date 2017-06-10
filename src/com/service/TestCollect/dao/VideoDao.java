package com.service.TestCollect.dao;

 import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.service.TestCollect.pojo.Video;
 

public interface VideoDao {
	
	void create(Video ve);
	
	List<Video> querydata();
	
	Video queryId(@Param("thirdId") String thirdId);
	 
}
