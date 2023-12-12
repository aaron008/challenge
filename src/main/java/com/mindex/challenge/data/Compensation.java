package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Compensation {
    private Employee employee;
    private double salary;

    //Date field format
    @JsonFormat(pattern = "yyyy-MM-dd",lenient = OptBoolean.FALSE)
    private Date effectiveDate;
}
