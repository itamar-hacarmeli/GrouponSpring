package com.jb.GrouponSpring.controllers;

import com.jb.GrouponSpring.User.UserDetails;
import com.jb.GrouponSpring.beans.Customer;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.services.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("groupon/guest")
@RequiredArgsConstructor
public class GuestController {
    private final CouponRepository couponRepository;
    private final AdministratorService administratorService;
    private final CustomerController customerController;


    @GetMapping("coupons")
    @CrossOrigin
    public ResponseEntity<?> getAllCoupons() {
        try {
            return new ResponseEntity<>(couponRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("register")
    @CrossOrigin
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        try {
            System.out.println(customer);
            administratorService.addCustomer(customer);
            return customerController.login(new UserDetails(customer.getEmail(), customer.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
