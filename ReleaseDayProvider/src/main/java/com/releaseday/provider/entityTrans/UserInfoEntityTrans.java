package com.releaseday.provider.entityTrans;

import com.releaseday.api.entity.user.UserInfoCoEntity;
import com.releaseday.provider.pojo.user.UserInfoEntity;

public class UserInfoEntityTrans {
	public static UserInfoCoEntity entity2coEntity(UserInfoEntity userInfoEntity){
		UserInfoCoEntity userInfoCoEntity = new UserInfoCoEntity();
		userInfoCoEntity.setId(userInfoEntity.getId());
		userInfoCoEntity.setEmail(userInfoEntity.getEmail());
		userInfoCoEntity.setName(userInfoEntity.getName());
		userInfoCoEntity.setPassword(userInfoEntity.getPassword());
		userInfoCoEntity.setRegisterTime(userInfoCoEntity.getRegisterTime());
		userInfoCoEntity.setRole(userInfoEntity.getRole());
		userInfoCoEntity.setSalt(userInfoEntity.getSalt());
		userInfoCoEntity.setStatus(userInfoEntity.getStatus());
		userInfoCoEntity.setUsername(userInfoEntity.getUsername());
		userInfoCoEntity.setValidateCode(userInfoEntity.getValidateCode());
		return userInfoCoEntity;
	}
	
	public static UserInfoEntity coEntity2entity(UserInfoCoEntity userInfoCoEntity){
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		userInfoEntity.setId(userInfoCoEntity.getId());
		userInfoEntity.setEmail(userInfoCoEntity.getEmail());
		userInfoEntity.setName(userInfoCoEntity.getName());
		userInfoEntity.setPassword(userInfoCoEntity.getPassword());
		userInfoEntity.setRegisterTime(userInfoCoEntity.getRegisterTime());
		userInfoEntity.setRole(userInfoCoEntity.getRole());
		userInfoEntity.setSalt(userInfoCoEntity.getSalt());
		userInfoEntity.setStatus(userInfoCoEntity.getStatus());
		userInfoEntity.setUsername(userInfoCoEntity.getUsername());
		userInfoEntity.setValidateCode(userInfoCoEntity.getValidateCode());
		return userInfoEntity;
	}
}
