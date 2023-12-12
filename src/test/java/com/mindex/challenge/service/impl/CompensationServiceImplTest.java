package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {


    @Autowired
    private CompensationService compensationService;

    @Test
    public void shouldCreateCompensation_WithValidData() throws ParseException {

        Compensation compensation = this.getCompensation();

        compensationService.createCompensation(compensation);

        assertNotNull(compensation.getEmployee().getEmployeeId());
     }

    @Test(expected = RuntimeException.class)
    public void shouldReturn_NoCompensationRecord_WithInValidEmployeeIds() {

        compensationService.getCompensation("invalidId");

    }

    @Test
    public void shouldReturn_OneRecord_WithValidEmployeeIds() throws ParseException {

        Compensation compensation = this.getCompensation();

        compensationService.createCompensation(compensation);

        Compensation compensationFromDB = compensationService.getCompensation(compensation.getEmployee().getEmployeeId());

        assertEquals(compensation, compensationFromDB);

    }

    private Compensation getCompensation() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return Compensation.builder()
                .effectiveDate(format.parse("2023-01-01"))
                .salary(200000)
                .employee(Employee.builder()
                        .department("Engineering")
                        .firstName("Jane")
                        .lastName("Doe")
                        .position("Director")
                        .build())
                .build();
    }

}
