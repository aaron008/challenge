package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

public interface EmployeeDirectReportsService {


    ReportingStructure generateDirectReports(String employeeId);
}
