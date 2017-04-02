package com.releaseday.api.service.user;

import java.util.List;

import com.releaseday.api.entity.ResultMsg;
import com.releaseday.api.entity.user.UserInfoCoEntity;

/**
 * 用户信息操作接口类
 * 
 * @author hackless
 *
 */
public interface UserInfoService {
	
	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param id
	 * 
	 * @return UserInfo
	 */
	public UserInfoCoEntity getUser(Long id);
	
	/**
	 * 根据用户分组获取用户信息列表
	 * 
	 * @param role
	 * 
	 * @return List<UserInfo>
	 */
	public List<UserInfoCoEntity> getUsers(String role);
	
	/**
	 * 向数据库中增加新用户信息
	 * 
	 * @param userInfo
	 * 
	 * @return UserInfo
	 */
	public UserInfoCoEntity registerUser(UserInfoCoEntity userInfo);
	
	/**
	 * 修改用户信息
	 * 
	 * @param userInfo
	 * 
	 * @return ResultMsg
	 */
	public ResultMsg modifyUser(UserInfoCoEntity userInfo);
	
	/**
	 * 根据用户ID删除指定用户
	 * 
	 * @param id
	 * 
	 * @return ResultMsg
	 */
	public ResultMsg deleteUser(Long id);
	
	/**
	 * 根据用户名查找用户信息
	 * 
	 * @param username
	 * 
	 * @return UserInfo
	 */
	public UserInfoCoEntity getUserByUsername(String username);
	
	/**
	 * 根据邮箱查找用户信息
	 * 
	 * @param email
	 * 
	 * @return UserInfo
	 */
	public UserInfoCoEntity getUserByEmail(String email);
	
}
