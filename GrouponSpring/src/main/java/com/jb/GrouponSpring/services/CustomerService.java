package com.jb.GrouponSpring.services;


import com.jb.GrouponSpring.beans.Company;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.beans.Customer;
import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.repositories.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Data
@Component
@RequiredArgsConstructor
@Service
public class CustomerService extends ClientService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private  int customerID;

    /**
     *
     * @param email - The email of the customer
     * @param password - The password of the customer
     * @return true if email and password are Matching, and return false if not
     * @throws GrouponException - Invalid email or password
     * Method for customer login
     */
    public boolean login(String email, String password) throws GrouponException {
        try {
            if (customerRepository.findByEmailAndPassword(email, password) == null) {
                throw new GrouponException("Invalid email or password");
            }
           this.customerID = customerRepository.findByEmailAndPassword(email, password).getId();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     *
     * @return true if the coupon successfully purchased by the customer, and return false if not
     * This method uses to purchase coupons by the customer
     */
    public boolean purchaseCoupon(Coupon coupon) {
        try {
            Coupon coupon1=couponRepository.getOneById(coupon.getId());
            if (coupon1 == null) {
                throw new GrouponException("coupon not found in the system");
            }
            if (coupon1.getAmount() <= 0) throw new GrouponException("coupon out off stock sorry....");
            if (coupon1.getEndDate().before(new Date())) throw new GrouponException("coupon is out of date.....");
            coupon1.setAmount(coupon1.getAmount() - 1);
            Customer customer = customerRepository.findById(this.customerID);
            couponRepository.saveAndFlush(coupon1);
            customer.getCoupons().add(coupon1);
            System.out.println(69);
            customerRepository.saveAndFlush(customer);
            System.out.println(76);
            System.out.println("coupon " + coupon1.getTitle() + " was purchased successfully!!");
            return true;
        } catch (GrouponException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }




    /**
     *
     * @return list of customer coupons
     * @throws GrouponException - The user entered the id of a non existent customer
     * Method that gets all customer coupons
     */
    public List<Coupon> getCustomerCoupons() throws GrouponException {

        Customer customer = customerRepository.getOneById(customerID);
        return customer.getCoupons();
    }
    /**
     *
     * @param category - The category type of the coupon
     * @return list of coupons by one customer and category
     * @throws GrouponException
     * Method for receiving a list of coupons from a particular category by one particular customer
     */
    public List<Coupon> getCustomerCouponsByCategory(Category category) throws GrouponException {
        if (customerRepository.getOneById(customerID) == null) {
            throw new GrouponException("Customer not found");
        }
        Customer customer = customerRepository.getOne(customerID);
        List<Coupon> coupons = customer.getCoupons();
        List<Coupon> returnedList = new ArrayList<>();
        for (Coupon item : coupons) {
            if (item.getCategoryId() == category) {
                returnedList.add(item);
            }
        }
        return returnedList;
    }

    /**
     *
     * @param maxPrice - Max price
     * @return list of customer coupons by max price
     * @throws GrouponException - The user entered the id of a non existent customer
     * Method that gets list of coupons of particular customer by max price
     */
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws GrouponException {
        if (customerRepository.getOneById(customerID) == null) {
            throw new GrouponException("Customer not found");
        }
        Customer customer = customerRepository.getOneById(customerID);
        List<Coupon> coupons = customer.getCoupons();
        List<Coupon> returnedList = new ArrayList<>();
        for (Coupon item : coupons) {
            if (item.getPrice() <= maxPrice) {
                returnedList.add(item);

            }
        }
        return returnedList;
    }
    /**
     *
     * @return Customer bean
     * @throws GrouponException - The user entered the id of a non existent customer
     * Method that gets details of one particular company
     */
    public Customer getCustomerDetails() throws GrouponException {
        if (customerRepository.getOneById(this.customerID) == null) {
            throw new GrouponException("Customer not found");
        }
        return customerRepository.getOneById(this.customerID);
    }
}
