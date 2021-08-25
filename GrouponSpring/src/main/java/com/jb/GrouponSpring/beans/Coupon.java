package com.jb.GrouponSpring.beans;


import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.exceptions.GrouponException;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //@Entity annotation specifies that the class is an entity and is mapped to a database table.
@Builder
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Enumerated(EnumType.STRING)
    private Category categoryId;
    private int companyId;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;


}
