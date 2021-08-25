package com.jb.GrouponSpring.beans;



import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  //@Entity annotation specifies that the class is an entity and is mapped to a database table.
@Builder
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(updatable = false)
    private String name;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Singular
    private List<Coupon> coupons=new ArrayList<>();


}
