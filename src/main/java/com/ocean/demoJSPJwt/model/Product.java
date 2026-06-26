package com.ocean.demoJSPJwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "prod_name")
    private String prod_name;
    
    @Column(name = "prod_desc")
    private String prod_desc;
    
    @Column(name = "prod_price")
    private BigDecimal prod_price;
    
    @Column(name = "release_date")
    private Date release_date;
}
