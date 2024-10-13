package com.ss.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.batch.entity.NotificationEntity;

public interface NotificationRepository 
    extends JpaRepository<NotificationEntity, Long>{

}
