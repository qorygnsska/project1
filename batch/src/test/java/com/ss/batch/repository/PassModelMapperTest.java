package com.ss.batch.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.ss.batch.entity.BulkPassEntity;
import com.ss.batch.entity.BulkPassStatus;
import com.ss.batch.entity.PassEntity;
import com.ss.batch.entity.PassStatus;
import com.ss.batch.modelmapper.PassModelMapper;

public class PassModelMapperTest {

	@Test
	public void test_toPassEntity() {
		
		// given
		String userId = "A1000000";
		LocalDateTime now = LocalDateTime.now();
		
		BulkPassEntity bulkPassEntity = 
						new BulkPassEntity();
		
		bulkPassEntity.setPackageSeq(1L);
		bulkPassEntity.setUserGroupId("GROUP");
		bulkPassEntity.setStatus(BulkPassStatus
								.COMPLETED);
		bulkPassEntity.setStartedAt(now.minusDays(60));
		bulkPassEntity.setEndedAt(now);
		
		// when
		PassEntity passEntity = PassModelMapper
								.toPassEntity(userId,
									bulkPassEntity);
		
		System.out.println(passEntity.getPackage_seq());
		
		// then
		
		assertEquals(Long.valueOf(1),passEntity.getPackage_seq());
		assertEquals(PassStatus.READY,
					 passEntity.getStatus());
		
		assertEquals(now.minusDays(60),
					 passEntity.getStarted_at());
		
		assertEquals(now,
				     passEntity.getEnded_at());	
		
	}		
	
}
