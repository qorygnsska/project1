package com.ss.batch.job.pass;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 대량 이용권을 사용자 그룹에 추가하고 
// 발송할 때 사용하는 Job,Step 구성!
// User 테이블
// UserGroup테이블
// Bulk_Pass테이블
@Configuration
public class AddPassesJobConfig {

	// JOB을 생성하는 팩토리(클래스)를 생성한다.
	private final JobBuilderFactory jobBuilderFactory;

	// Step을 생성할 수있는 팩토리 (배치 작업의 단계)
	private final StepBuilderFactory stepBuilderFactory;

	// 실제 처리하는 내용을 가지고 있는 Tasklet 객체를
	// 생성!
	private final AddPassesTasklet taskletq;
	
	
	public AddPassesJobConfig(JobBuilderFactory jobBuilderFactory
							, StepBuilderFactory stepBuilderFactory, 
							  AddPassesTasklet tasklet) {
		
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.taskletq = tasklet;
	}
	
	@Bean
	public Job addPassesJob() {
		return this.jobBuilderFactory
				   .get("addPassesJob")
				   .start(addPassStep()) //단일 스레드
				   .build();
	}

	@Bean
	public Step addPassStep() {
		
		return this.stepBuilderFactory
				   .get("addPassStep")
				   .tasklet(taskletq)
				   .build();
	}

}
