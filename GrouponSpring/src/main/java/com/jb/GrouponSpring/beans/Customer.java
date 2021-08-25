package com.jb.GrouponSpring.beans;


import lombok.*;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //@Entity annotation specifies that the class is an entity and is mapped to a database table.
@Builder
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Singular
    private List<Coupon> coupons=new ArrayList<>();


}
