package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pay_order")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "pay_count")
    private int count;

    @Column(name = "pay_price")
    private int price;

    @Column(name = "pay_time")
    private Date time;

    @Column(name = "pay_type")
    private int  type;

    private int  status;

    private String username;

}
