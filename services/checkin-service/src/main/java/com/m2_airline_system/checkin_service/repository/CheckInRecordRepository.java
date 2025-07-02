package com.m2_airline_system.checkin_service.repository;

import com.m2_airline_system.checkin_service.entity.CheckInRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CheckInRecordRepository extends JpaRepository<CheckInRecord, Long> {}