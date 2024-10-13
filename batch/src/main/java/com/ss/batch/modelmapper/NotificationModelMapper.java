package com.ss.batch.modelmapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.ss.batch.entity.BookingEntity;
import com.ss.batch.entity.NotificationEntity;
import com.ss.batch.entity.NotificationEvent;
import com.ss.batch.util.LocalDateTimeUtils;


//  MapStruct가 매핑 인터페이스를 인식하도록 설정함
// 매핑하지 않는 필드가 있으면 그냥 무시하고 넘어가도록 설정
@Mapper(unmappedTargetPolicy = 
			ReportingPolicy.IGNORE)
public interface NotificationModelMapper {
	
	// MapStruct가 자동으로 생성한
	// NotificationModelMapper 객체를 사용할 수있도록 
	// INSTACE 이용해서 매퍼 사용할 수있음
	NotificationModelMapper INSTACE = 
			Mappers.getMapper(NotificationModelMapper.class);
	
	// 필드명이 같지 않거나 커스텀하게 매핑해주기 위해서는
	// 어노테이션  @Mapping을 추가해주면 된다.
	@Mapping(target = "uuid", source = "bookingEntity.userEntity.uuid")
	@Mapping(target = "text",
			 source = "bookingEntity.startedAt",
			 qualifiedByName = "text")
	
	NotificationEntity toNotificationEntity(
			BookingEntity bookingEntity,
			NotificationEvent event
			);

	// 알람 보낼 메시지를 생성 
	
	@Named("text")
	default String text(LocalDateTime startedAt) {
		return String.format("안녕하세요. %s 수업 시작합니다. 수업전 출석 체크 부탁드립니다."
							,LocalDateTimeUtils.format(startedAt));
	}
	
	
	// 매핑을 하는 이유는 예약정보(Booking)를 기반으로 알림
	// 을 생성하고 발송하기 위해서 이 과정에서 예약과 관련된 
	// 중요한 정보가 있다. 중요정보를 (Notification) 알림
	// 전달한다.
	
	
}
