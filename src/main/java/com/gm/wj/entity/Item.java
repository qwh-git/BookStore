package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int oid;

    private int bid;

    private int count;

    private int price;

    private int dj;

    private String title;
}
