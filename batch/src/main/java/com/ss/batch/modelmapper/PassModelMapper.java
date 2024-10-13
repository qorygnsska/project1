package com.ss.batch.modelmapper;

import java.util.List;

import com.ss.batch.entity.BulkPassEntity;
import com.ss.batch.entity.PassEntity;
import com.ss.batch.entity.PassStatus;

// BulkPassEntity -> PassEntity로 변환
public class PassModelMapper {
	
	public static PassEntity toPassEntity(
			String userId,
			BulkPassEntity bulkPassEntity) {
		
		PassEntity passEntity = new PassEntity();
		
		// 각각의 필드를 이용해서 매핑
		System.out.println(bulkPassEntity.getPackageSeq());
		
		Long num = bulkPassEntity.getPackageSeq();
		System.out.println(num);
		passEntity.setPackage_seq(1L);
		passEntity.setUser_id(userId);
		passEntity.setStatus(PassStatus.READY);
		passEntity.setRemaining_count(bulkPassEntity.getCount());
		passEntity.setStarted_at(bulkPassEntity.getStartedAt());
		passEntity.setEnded_at(bulkPassEntity.getEndedAt());
		
		System.out.println(passEntity.getPackage_seq());
		
		return passEntity; 
	}
	

}
