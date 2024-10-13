package com.ss.batch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.batch.entity.UserGroupMappingEntity;

public interface UserGroupMappingRepository 
	extends JpaRepository<UserGroupMappingEntity,Long>{

	// 유저 그룹 아이디를 넣게 되면 유저 그룹의 매핑 엔티티
	// 를 가져올 수있고 유저 아이디를 가져올 수있기 때문에
	List<UserGroupMappingEntity> findByUserGroupId(
						String userGroupId);
	
	
}
