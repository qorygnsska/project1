package com.ss.batch.entity;

public enum PassStatus {
	//상태값들을 저장을 하는것
	//jpa에서 enum 숫자로 자동 저장되도록 설정!
	READY,PROGRESSED,EXPIRED;
}
