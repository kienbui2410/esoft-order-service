package com.esoft.orderservice.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse  implements Serializable {
    private Long noOfOrder;
    private Long revenue;
}
