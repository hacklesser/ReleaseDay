package com.releaseday.provider.repositories.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.releaseday.provider.pojo.user.UserInfoEntity;

public interface UserInfoRepository extends CrudRepository<UserInfoEntity, Long>, PagingAndSortingRepository<UserInfoEntity, Long>{
	public List<UserInfoEntity> findUserInfoByRole(String role);
	public UserInfoEntity findUserInfoByUsername(String username);
	public UserInfoEntity findUserInfoByEmail(String email);
}
