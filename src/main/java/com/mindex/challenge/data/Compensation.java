package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Compensation {
    private Employee employee;
    private double salary;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;
}
