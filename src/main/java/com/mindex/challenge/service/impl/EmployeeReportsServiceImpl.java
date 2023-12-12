package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeDirectReportsService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.List;

@Service
public class EmployeeReportsServiceImpl implements EmployeeDirectReportsService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeReportsServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure generateDirectReports(String employeeId) {
        return this.getAllEmployeeDirectReports(employeeId);
    }

    private ReportingStructure getAllEmployeeDirectReports(String employeeId) {
        Employee employee = null;
        ReportingStructure reporting = new ReportingStructure();
        employee = getEmployee(employeeId, employee);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }else {
            reporting.setEmployee(employee);
            List<Employee> directReports = employee.getDirectReports();

            AtomicInteger employeeCount = new AtomicInteger(0);
            this.countAllDirectReports(directReports, employeeCount);

            reporting = ReportingStructure.builder()
                    .numberOfReports(employeeCount.get())
                    .employee(employee)
                    .build();
        }
        return reporting;
    }

    private void countAllDirectReports(List<Employee> directReports, AtomicInteger employeeCount) {

        if (CollectionUtils.isEmpty(directReports)){
            return;
        }

        for (Employee directReportsEmployee: directReports) {
            employeeCount.getAndIncrement();
            Employee employee = null;

            employee = getEmployee(directReportsEmployee.getEmployeeId(), employee);

            if(employee != null) {
                directReportsEmployee.setDepartment(employee.getDepartment());
                directReportsEmployee.setDirectReports(employee.getDirectReports());
                directReportsEmployee.setPosition(employee.getPosition());
                directReportsEmployee.setFirstName(employee.getFirstName());
                directReportsEmployee.setLastName(employee.getLastName());

                //Loop through all employees and DirectReports until all are counted.
                this.countAllDirectReports(employee.getDirectReports(), employeeCount);
            }
        }
    }

    private Employee getEmployee(String employeeId, Employee employee) {
        try {
            employee = employeeService.read(employeeId);
        }catch(Exception ex){
            LOG.debug("Error processing employeeId "+ employeeId);
        }
        return employee;
    }
}
