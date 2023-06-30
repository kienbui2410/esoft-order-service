package com.esoft.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "esoft_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref;
    @Column(name = "user_id")
    private Long userId;
    private String category;
    private Long quantity;
    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "create_at")
    private Date createdAt;
    private String desc;
    private String note;

}
