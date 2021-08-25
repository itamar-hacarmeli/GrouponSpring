package com.jb.GrouponSpring.controllers;


import com.jb.GrouponSpring.User.UserDetails;
import com.jb.GrouponSpring.Util.JWTutil;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.beans.Customer;
import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.exceptions.GetException;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.loginManager.ClientType;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.repositories.CustomerRepository;
import com.jb.GrouponSpring.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomerController class allows sending and receiving data to its relevant Facade through exposure
 * The functionality as a REST ful Service.
 * RestController - This annotation is applied to a class to mark it as a request handler.
 * Spring RestController annotation is used to create REST ful web services using Spring MVC.
 */

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("groupon/customer")
@RequiredArgsConstructor
public class CustomerController extends ClientController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final JWTutil util;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDate) {
        try {
            System.out.println(userDate);
            if (customerService.login(userDate.getEmail(), userDate.getPassword())) {
                String token = util.generateToken(new UserDetails(userDate.getEmail(), customerService.getCustomerID(), ClientType.CUSTOMER));
                return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     *
     * @return ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * @throws GrouponException
     * Method for purchase coupon be customer.
     */
    @PostMapping("purchaseCoupon")
    @CrossOrigin
    public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon,@RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                System.out.println(customerService.getCustomerID());
                if (!customerRepository.findById(customerService.getCustomerID()).getCoupons().stream().filter(c->c.getId()==coupon.getId()).collect(Collectors.toList()).isEmpty()){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                customerService.getCustomerID(), ClientType.CUSTOMER)))
                        .body(customerService.purchaseCoupon(coupon));
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    /**
     *
     * @return ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * @throws GrouponException if customer not exists.
     * Method that get all customer coupons from database.
     */
    @GetMapping("customers/coupons")
    @CrossOrigin
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token)throws GrouponException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                customerService.getCustomerID(), ClientType.CUSTOMER)))
                        .body(customerService.getCustomerCoupons());
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    /**
     *
     * @param category - The coupon category.
     * @return - List of coupons.
     * @throws GrouponException if customer not exists.
     * @throws GetException if customer not found.
     * This method for get all customer coupons be specific category.
     */
    @GetMapping("customers/coupons/category/{category}")
    @CrossOrigin
    public ResponseEntity<?> getCustomerCouponsByCategory(@PathVariable Category category, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                customerService.getCustomerID(), ClientType.CUSTOMER)))
                        .body(customerService.getCustomerCouponsByCategory(category));
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    /**
     *
     * @param maxPrice - The coupons displayed will be up to this maximum price.
     * @return List of coupons.
     * @throws GetException if customer not found.
     * This method for get all the customer coupons by max price.
     */
    @GetMapping("coupons/customers/max/{maxPrice}")
    @CrossOrigin
    public ResponseEntity<?> getCustomerCouponsByMaxPrice(@PathVariable double maxPrice, @RequestHeader(name = "Authorization") String token) throws GetException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                customerService.getCustomerID(), ClientType.CUSTOMER)))
                        .body(customerService.getCustomerCouponsByMaxPrice(maxPrice));
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     *
     * @return - ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * @throws GrouponException if customer not exists.
     * This method will get one customer details.
     */
    @GetMapping("customerDetails")
    @CrossOrigin
    public ResponseEntity<?> getCustomerDetails( @RequestHeader(name = "Authorization") String token) throws GrouponException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),customerService.getCustomerID(), ClientType.CUSTOMER)))
                        .body(customerService.getCustomerDetails());
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    }






