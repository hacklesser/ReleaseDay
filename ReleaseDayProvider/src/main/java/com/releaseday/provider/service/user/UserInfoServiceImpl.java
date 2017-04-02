package com.releaseday.provider.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.releaseday.api.config.ResultStatusCode;
import com.releaseday.api.entity.ResultMsg;
import com.releaseday.api.entity.user.UserInfoCoEntity;
import com.releaseday.api.service.user.UserInfoService;
import com.releaseday.provider.entityTrans.UserInfoEntityTrans;
import com.releaseday.provider.pojo.user.UserInfoEntity;
import com.releaseday.provider.repositories.user.UserInfoRepository;

@Service(value = "userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserInfoCoEntity getUser(Long id) {
		
		return UserInfoEntityTrans.entity2coEntity(userInfoRepository.findOne(id));
	}

	@Override
	public List<UserInfoCoEntity> getUsers(String role) {
		List<UserInfoEntity> userInfoEntities = userInfoRepository.findUserInfoByRole(role);
		List<UserInfoCoEntity> userInfoCoEntities = new ArrayList<>();
		for (UserInfoEntity userInfoEntity : userInfoEntities) {
			UserInfoCoEntity userInfoCoEntity = UserInfoEntityTrans.entity2coEntity(userInfoEntity);
			userInfoCoEntities.add(userInfoCoEntity);
		}
		return userInfoCoEntities;
	}

	@Override
	public UserInfoCoEntity registerUser(UserInfoCoEntity userInfo) {
		UserInfoEntity userInfoEntity = UserInfoEntityTrans.coEntity2entity(userInfo);
		return UserInfoEntityTrans.entity2coEntity(userInfoRepository.save(userInfoEntity));
	}

	@Override
	public ResultMsg modifyUser(UserInfoCoEntity userInfo) {
		try {
			System.out.println((userInfo).getId());
			UserInfoEntity user = userInfoRepository.findOne((userInfo).getId());
			if (user != null) {
				user.setName((userInfo).getName());
				userInfoRepository.save(user);
				return new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), user);
			} else {
				return new ResultMsg(ResultStatusCode.BLANK_ERR.getCode(), ResultStatusCode.BLANK_ERR.getMsg(), userInfo);
			}
		} catch (Exception e) {
			return new ResultMsg(ResultStatusCode.NORMAL_ERR.getCode(), ResultStatusCode.NORMAL_ERR.getMsg(), userInfo);
		}
	}

	@Override
	public ResultMsg deleteUser(Long id) {
		try {
			userInfoRepository.delete(id);
			return new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
		} catch (Exception e) {
			return new ResultMsg(ResultStatusCode.NORMAL_ERR.getCode(), ResultStatusCode.NORMAL_ERR.getMsg(), null);
		}
	}

	@Override
	public UserInfoCoEntity getUserByUsername(String username) {
		return UserInfoEntityTrans.entity2coEntity(userInfoRepository.findUserInfoByUsername(username));
	}

	@Override
	public UserInfoCoEntity getUserByEmail(String email) {
		return UserInfoEntityTrans.entity2coEntity(userInfoRepository.findUserInfoByEmail(email));
	}

}
