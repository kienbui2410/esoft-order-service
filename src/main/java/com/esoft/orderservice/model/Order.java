package com.esoft.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "esoft_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ref;
    @Column(name = "user_id")
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Long quantity;
    @Column(name = "service_name")
    @Enumerated(EnumType.STRING)
    private ServiceName serviceName;
    @Column(name = "create_at")
    private Date createdAt;
    @Column(name="description")
    private String desc;
    private String note;
    private BigDecimal price;

    public enum Category {
        LUXURY, SUPER_LUXURY, SUPREME_LUXURY;
    }

    public enum ServiceName {
        PHOTO_EDITING, VIDEO_EDITING;
    }
}
