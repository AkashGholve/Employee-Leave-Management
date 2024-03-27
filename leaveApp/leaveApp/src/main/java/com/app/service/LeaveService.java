package com.app.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.entity.Employee;
import com.app.entity.Holiday;
import com.app.entity.LeaveForm;
import com.app.entity.LeaveRecord;
import com.app.entity.LeaveResponse;
import com.app.repository.EmployeeRepository;
import com.app.repository.HolidayRepository;
import com.app.repository.LeaveRecordRepository;

@Service
public class LeaveService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private LeaveRecordRepository leaveRecordRepository;

    @Value("${total.allowed.leaves}")
    private int totalAllowedLeaves;

    public LeaveResponse applyLeave(LeaveForm leaveForm) throws Exception {
        // Fetch employee details
        Employee employee = employeeRepository.findById(leaveForm.getEmployeeId()).orElse(null);
        if (employee == null) {
            throw new Exception("Employee not found");
        }
       
        // Check if leave already exists for the same date
        LocalDate fromDate = LocalDate.parse(leaveForm.getFromDate());
        LocalDate toDate = LocalDate.parse(leaveForm.getToDate());
        LocalDate currentDate = LocalDate.now();
        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("From date cannot be after to date");
        }
        if(fromDate.isBefore(currentDate)) {
        	throw new IllegalArgumentException("From date cannot be after to date");
        }
        List<LeaveRecord> existingLeaves = leaveRecordRepository.findByEmployeeIdAndFromDateGreaterThanEqualAndToDateLessThanEqual(leaveForm.getEmployeeId(), fromDate, toDate);
        if (!existingLeaves.isEmpty()) {
            throw new Exception("Leave already exists for the selected date");
        }

        // Delegate leave application handling to the service layer
        LeaveResponse leaveResponse = calculateLeave(leaveForm, employee);

        // Save leave record to the database
        LeaveRecord leaveRecord = createLeaveRecord(leaveForm, leaveResponse);
        leaveRecord = leaveRecordRepository.save(leaveRecord);

        return leaveResponse;
    }

    public LeaveResponse calculateLeave(LeaveForm leaveForm, Employee employee) throws Exception {
        LocalDate fromDate = LocalDate.parse(leaveForm.getFromDate());
        LocalDate toDate = LocalDate.parse(leaveForm.getToDate());
        
        List<Holiday> holidays = holidayRepository.findByHolidayDateBetween(fromDate, toDate);
        int workingDays = calculateWorkingDays(fromDate, toDate);
        List<LeaveRecord> recordList = leaveRecordRepository.findByEmployeeId(leaveForm.getEmployeeId());
        int totalLeavesTaken = 0;
        for (LeaveRecord record : recordList) {
            totalLeavesTaken += record.getTotalLeavesTaken();
        }
        int balanceLeaves = calculateBalanceLeaves(totalLeavesTaken);
        if (balanceLeaves < 0) {
            throw new Exception("Cannot Approve Leave: Insufficient balance leaves");
        }

        return new LeaveResponse(employee, workingDays, totalLeavesTaken, balanceLeaves);
    }

    private boolean isHoliday(LocalDate date, List<Holiday> holidays) {
        for (Holiday holiday : holidays) {
            if (holiday.getHolidayDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    private LeaveRecord createLeaveRecord(LeaveForm leaveForm, LeaveResponse leaveResponse) {
        LeaveRecord leaveRecord = new LeaveRecord();
        leaveRecord.setEmployeeId(leaveForm.getEmployeeId());
        leaveRecord.setFromDate(LocalDate.parse(leaveForm.getFromDate()));
        leaveRecord.setToDate(LocalDate.parse(leaveForm.getToDate()));
        int totalLeaves = calculateWorkingDays(leaveRecord.getFromDate(), leaveRecord.getToDate());
        leaveRecord.setTotalLeavesTaken(totalLeaves);

        return leaveRecord;
    }

    public int calculateWorkingDays(LocalDate fromDate, LocalDate toDate) {
        int year = fromDate.getYear();
        List<Holiday> holidays = holidayRepository.findByHolidayDateBetween(LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 31));

        int workingDays = 0;
        LocalDate date = fromDate;
        while (!date.isAfter(toDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY && !isHoliday(date, holidays)) {
                workingDays++;
            }
            date = date.plusDays(1);
        }
        return workingDays;
    }

    public int calculateBalanceLeaves(int totalLeavesTaken) {
        return totalAllowedLeaves - totalLeavesTaken;
    }
}
