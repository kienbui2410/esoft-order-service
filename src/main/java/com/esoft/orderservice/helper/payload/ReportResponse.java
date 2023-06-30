package com.esoft.orderservice.helper.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long noOfOrder;
    private Long revenue;
}
