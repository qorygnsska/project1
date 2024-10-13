package com.ss.batch.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import com.ss.batch.entity.BulkPassEntity;
import com.ss.batch.entity.BulkPassStatus;
import com.ss.batch.entity.PassEntity;
import com.ss.batch.entity.PassStatus;
import com.ss.batch.entity.UserGroupMappingEntity;
import com.ss.batch.job.pass.AddPassesTasklet;


// 잔디 라이브러리 그래들에 추가하고 새로고침하고
// 밑에 코드 추가하기!
@ExtendWith(MockitoExtension.class)
public class AddPassTaskletTest {

	// Spring batch
	// 배치 작업의 각 단계에서 얼마나 많은 작업을 했는지
	// 어떤 처리가 성공했는지 등을 기록하는 클래스
	@Mock
	private StepContribution stepContribution;

	// 청크 단위로 처리 될때 사용하는 클래스

	@Mock
	private ChunkContext chunkContext;

	// 데이터베이스
	@Mock
	private PassRepository passRepository;

	@Mock
	private BulkPassRepository bulkPassRepository;

	@Mock
	private UserGroupMappingRepository groupRepo;

	// 위에 Mock (가상 객체) 객체 생성!
	// 객체를 주입 받는다.
	@InjectMocks
	private AddPassesTasklet addPassesTasklet;

	
	@Test
	public void test_execute() throws Exception {

		// given
		String userGroupId = "GROUP";
		String userId = "A1000000";
		Integer count = 10;
		Long packageSeq = 1L;

		LocalDateTime now = LocalDateTime.now();

		BulkPassEntity bulkPassEntity = new BulkPassEntity();

		bulkPassEntity.setPackageSeq(packageSeq);
		bulkPassEntity.setUserGroupId(userGroupId);
		bulkPassEntity.setStatus(BulkPassStatus.READY);
		bulkPassEntity.setStartedAt(now);
		bulkPassEntity.setEndedAt(now.plusDays(60));
		bulkPassEntity.setCount(count);
		
		
		// 대량의 이용권을 발급!
		// 어떤 그룹에 속한 사용자한테 보낼지! 저장
		UserGroupMappingEntity userGroupEntity
					= new UserGroupMappingEntity();
		
		userGroupEntity.setUserGroupId(userGroupId);
		userGroupEntity.setUserId(userId);
		
		// when
		// 그룹에 속한 그룹ID에 사용자 찾기!
		// eq("GROUP") 만약 일치가 되지 않으면 검색 안함
		//             만약 문자가 일치하면 데이터를 검색하도록
		//             설정
		
		// when()
		//  검색을 했을 때 데이터가 있으면 그 데이터를 
		//  thenReturn()메서드로 전달한다.
		
		//  any()
		//   -  특정 타입의 인자가 무엇이든 상관없이 
		//     메서드를 실행 할 수있다. 모든 인자값을 허용
		//     지정한 타입이 맞는지 맞으면 동작하는 설정!
		when(bulkPassRepository
			.findByStatusAndStartedAtGreaterThan(
				eq(BulkPassStatus.READY), any()))
		.thenReturn(List.of(bulkPassEntity));
		
		
		when(groupRepo
			.findByUserGroupId(eq("GROUP")))
			.thenReturn(List.of(userGroupEntity));

		// 매핑 PassModelMapper이용해서 벌크=> 패스 엔티티로
		// 변환
		// 배치 작업시 현재 작업에 대한 상태와 정보를 전달하는
		// 역할로 쓰인다.
		RepeatStatus repeatStatus = addPassesTasklet.execute(stepContribution,
									chunkContext);
				
		// then
		assertEquals(RepeatStatus.FINISHED,
					 		 repeatStatus);
		
		// saveAll() 메서드에 값이 List타입으로 지정
		// PassEntity객체들이 들어있다.
		// verify 특정 메서드가 호출되었는지 여부를 확인할 때 사용
		ArgumentCaptor<List> passEntitiesCaptor = 
				ArgumentCaptor.forClass(List.class);
		verify(passRepository, times(1))
				.saveAll(passEntitiesCaptor.capture());
        
		List<PassEntity> passEntities = passEntitiesCaptor.getValue();

        System.out.println(passEntities);
        
        assertEquals(1,passEntities.size());
        
        PassEntity passEntity = passEntities.get(0);
        assertEquals(packageSeq, passEntity.getPackage_seq());
        assertEquals(userId,passEntity.getUser_id());
        assertEquals(PassStatus.READY,
        			 passEntity.getStatus());
        assertEquals(count, 
        			 passEntity.getRemaining_count());
        
        
        
	}

}
