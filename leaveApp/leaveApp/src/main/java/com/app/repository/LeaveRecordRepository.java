package com.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.LeaveRecord;

public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Long> {
    List<LeaveRecord> findByEmployeeIdAndFromDateGreaterThanEqualAndToDateLessThanEqual(Long employeeId, LocalDate fromDate, LocalDate toDate);

    List<LeaveRecord> findByEmployeeId(Long employeeId);
}
