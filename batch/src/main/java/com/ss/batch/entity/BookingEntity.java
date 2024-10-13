package com.ss.batch.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "booking")
public class BookingEntity extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingSeq;  // 예약순서
	private Long passSeq;     // 어떤 이용권과 연결되어있는 예약 확인
	private String userId;    // 예약한 사람 id
	
	@Enumerated(EnumType.STRING)
	private BookingStatus status; // 예약의 상태를 관리
	
	private boolean usedPass;     //이용권 사용 여부
	private boolean attended;     //참석 여부(통계,분석)
	
	private LocalDateTime startedAt;	  //시작 시간
	private LocalDateTime endedAt;   	  //종료 시간
	private LocalDateTime cancellendAt;   //취소 시간
	
	// 예약한 사람의 메시지를 보내기 위해서 user 테이블과 조인
	// 여러 예약이 하나의 사용자에게 저장될 수있다. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserEntity userEntity;
		
	
	
}
