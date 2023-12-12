package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeDirectReportsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeReportsServiceImplTest {


    @Autowired
    EmployeeDirectReportsService directReportsService;

    @Test
    public void shouldReturn_DirectReportCount_WithValidEmployeeIds(){
        ReportingStructure reporting = directReportsService.generateDirectReports("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(reporting);
        assertEquals("John", reporting.getEmployee().getFirstName());
        assertEquals("Lennon", reporting.getEmployee().getLastName());
        assertEquals("Development Manager", reporting.getEmployee().getPosition());
        assertEquals("Engineering", reporting.getEmployee().getDepartment());
        assertEquals("b7839309-3348-463b-a7e3-5de1c168beb3", reporting.getEmployee().getDirectReports().get(0).getEmployeeId());
        assertEquals("Paul", reporting.getEmployee().getDirectReports().get(0).getFirstName());
        assertEquals("McCartney", reporting.getEmployee().getDirectReports().get(0).getLastName());
        assertEquals("Engineering", reporting.getEmployee().getDirectReports().get(0).getDepartment());
        assertEquals("Developer I", reporting.getEmployee().getDirectReports().get(0).getPosition());
        assertEquals("03aa1462-ffa9-4978-901b-7c001562cf6f", reporting.getEmployee().getDirectReports().get(1).getEmployeeId());
        assertEquals("Ringo", reporting.getEmployee().getDirectReports().get(1).getFirstName());
        assertEquals("Starr", reporting.getEmployee().getDirectReports().get(1).getLastName());
        assertEquals("Engineering", reporting.getEmployee().getDirectReports().get(1).getDepartment());
        assertEquals("Developer V", reporting.getEmployee().getDirectReports().get(1).getPosition());
        assertEquals(4, reporting.getNumberOfReports());

        reporting = directReportsService.generateDirectReports("03aa1462-ffa9-4978-901b-7c001562cf6f");
        assertNotNull(reporting);
        assertEquals("Ringo", reporting.getEmployee().getFirstName());
        assertEquals("Starr", reporting.getEmployee().getLastName());
        assertEquals("Developer V", reporting.getEmployee().getPosition());
        assertEquals("Engineering", reporting.getEmployee().getDepartment());
        assertEquals("62c1084e-6e34-4630-93fd-9153afb65309", reporting.getEmployee().getDirectReports().get(0).getEmployeeId());
        assertEquals("c0c2293d-16bd-4603-8e08-638a9d18b22c", reporting.getEmployee().getDirectReports().get(1).getEmployeeId());
        assertEquals(2, reporting.getNumberOfReports());

        reporting = directReportsService.generateDirectReports("62c1084e-6e34-4630-93fd-9153afb65309");
        assertNotNull(reporting);
        assertEquals("Pete", reporting.getEmployee().getFirstName());
        assertEquals("Best", reporting.getEmployee().getLastName());
        assertEquals("Developer II", reporting.getEmployee().getPosition());
        assertEquals("Engineering", reporting.getEmployee().getDepartment());
        assertEquals(0, reporting.getNumberOfReports());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowException_WithInValidEmployeeIds() {
        directReportsService.generateDirectReports("employeeId1");

    }

}
