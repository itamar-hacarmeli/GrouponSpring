package com.jb.GrouponSpring.controllers;


import com.jb.GrouponSpring.User.UserDetails;
import com.jb.GrouponSpring.Util.JWTutil;
import com.jb.GrouponSpring.beans.Company;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.exceptions.AddException;
import com.jb.GrouponSpring.exceptions.GetException;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.exceptions.UpdateException;
import com.jb.GrouponSpring.loginManager.ClientType;
import com.jb.GrouponSpring.repositories.CompanyRepository;
import com.jb.GrouponSpring.repositories.CouponRepository;

import com.jb.GrouponSpring.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CompanyController class allows sending and receiving data to its relevant Facade through exposure
 * The functionality as a REST ful Service.
 * RestController - This annotation is applied to a class to mark it as a request handler.
 * Spring RestController annotation is used to create REST ful web services using Spring MVC.
 */

@RestController
@RequestMapping("groupon/company")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")

public class CompanyController extends ClientController {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CompanyService companyService;
    private final JWTutil util;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDate) {
        try {
            if (companyService.login(userDate.getEmail(), userDate.getPassword())) {
                String token = util.generateToken(new UserDetails(userDate.getEmail(), companyService.getCompanyID(), ClientType.COMPANY));
                return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param coupon - coupon bean
     * @throws AddException if there is in database coupon with the same title for this company.
     *                      Method for adding coupon to database, and return response to a request by the user.
     */
    @PostMapping("coupons/add")
    @CrossOrigin
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), companyService.getCompanyID())) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    return ResponseEntity.accepted()
                            .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                    companyService.getCompanyID(), ClientType.COMPANY
                            )))
                            .body(companyService.addCoupon(coupon));
                }
            }
        } catch (Exception err) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param coupon - Coupon bean.
     * @return - ResponseEntity : HTTP status -  ACCEPTED if Succeeded, ALREADY_REPORTED if not.
     * @throws UpdateException if the user try to change coupon id or company id.
     *                         Method for update coupon, and return response to a request by the user.
     */
    @PutMapping("coupon/update")
    @CrossOrigin
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader(name = "Authorization") String token) throws UpdateException {
        System.out.println(coupon);
        try {
            if (util.validateToken(token)) {
                if (couponRepository.findOneByIdAndCompanyId(coupon.getId(), coupon.getCompanyId()) == null) {
                    throw new GrouponException();
                }
                return ResponseEntity.accepted()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                companyService.getCompanyID(), ClientType.COMPANY
                        )))
                        .body(companyService.updateCoupon(coupon));
            }

        } catch (Exception err) {
            throw new UpdateException("Unable update id or company id");
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param id - The id fo the coupon.
     * @return - ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * Method for delete company by request to APIs.
     */
    @DeleteMapping("coupons/{id}")
    @CrossOrigin
    public ResponseEntity<?> deleteCoupon(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                companyService.getCompanyID(),
                                ClientType.ADMINISTRATOR
                        )))
                        .body(companyService.deleteCoupon(id));
            }
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    /**
     * @return - ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * @throws GrouponException if the company not exist.
     *                          this method gets all company coupons from database.
     */
    @GetMapping("companies/coupons/")
    @CrossOrigin
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token) throws GrouponException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                companyService.getCompanyID(), ClientType.COMPANY)))
                        .body(companyService.getCompanyCoupons());
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("companies/thisCoupon/{id}")
    @CrossOrigin
    public ResponseEntity<?> getCompanyCoupons(@PathVariable int id, @RequestHeader(name = "Authorization") String token) throws GrouponException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                companyService.getCompanyID(), ClientType.COMPANY)))
                        .body(companyService.getCompanyCouponById(id));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param category - The coupon category.
     * @return - List of coupons.
     * @throws GrouponException if company non exists.
     * @throws GetException     if company not found.
     *                          This method for get all company coupons be specific category.
     */
    @GetMapping("companies/coupons/category/{category}")
    @CrossOrigin
    public ResponseEntity<?> getCompanyCouponsByCategory(@PathVariable Category category, @RequestHeader(name = "Authorization") String token) throws GrouponException, GetException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                companyService.getCompanyID(), ClientType.COMPANY)))
                        .body(companyService.getCompanyCouponsByCategory(category));
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param maxPrice - The coupons displayed will be up to this maximum price.
     * @return List of coupons.
     * @throws GetException if company not found.
     *                      This method for get all the company coupons by max price.
     */
    @GetMapping("coupons/companies/{maxPrice}")
    @CrossOrigin
    public ResponseEntity<?> getCompanyCouponsByMaxPrice(@PathVariable double maxPrice, @RequestHeader(name = "Authorization") String token) throws GetException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                companyService.getCompanyID(), ClientType.COMPANY)))
                        .body(companyService.getCompanyCouponsByMaxPrice(maxPrice));
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    /**
     * @return - ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * @throws GrouponException if company not exists.
     *                          This method will get one company details.
     */
    @GetMapping("companyDetails")
    @CrossOrigin
    public ResponseEntity<?> getCompanyDetails( @RequestHeader(name = "Authorization") String token) throws GrouponException {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),companyService.getCompanyID(), ClientType.COMPANY)))
                        .body(companyService.getCompanyDetails());
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

