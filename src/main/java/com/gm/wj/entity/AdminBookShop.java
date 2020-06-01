package com.gm.wj.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "admin_book_shop")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminBookShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int uid;

    private int bid;

    private int count;

    @Transient
    private List<Book> books1;

}
