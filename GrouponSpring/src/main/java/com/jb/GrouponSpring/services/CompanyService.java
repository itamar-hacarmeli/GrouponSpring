package com.jb.GrouponSpring.services;

import com.jb.GrouponSpring.beans.Company;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.repositories.CompanyRepository;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Data
@Component
@RequiredArgsConstructor
@Service
public class CompanyService extends ClientService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final AdministratorService administratorService;
    private int companyID = 1;

    /**
     *
     * @param email - The email of the company
     * @param password - The password of the company
     * @return true if email and password are Matching, and return false if not
     * Method for company login
     */
    public boolean login(String email, String password) {
        try {
            if (companyRepository.findByEmailAndPassword(email, password) == null) {
                throw new GrouponException("Invalid email or password");
            }
            this.companyID = companyRepository.findByEmailAndPassword(email, password).getId();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     *
     *
     * @param coupon - Coupon bean
     * @return true if insertion was successful, and return false if not
     * The method is used to add a coupon to the database by particular company
     */
    public boolean addCoupon(Coupon coupon) throws GrouponException {

        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompanyId())) {
            throw new GrouponException("There is already a coupon with that title belongs to that company!");
        }
        coupon.setCompanyId(companyID);
        couponRepository.save(coupon);
        Company company = companyRepository.getOne(companyID);
        //Company company = companyRepository.getOne(coupon.getCompanyId());
        company.getCoupons().add(coupon);
        administratorService.updateCompany(company);
        return true;

    }
    /**
     *
     * @param couponId - The id of the coupon
     * @return Coupon bean
     * @throws GrouponException - The user entered the id of a non existent coupon
     * this method for update coupon, for not externalizing the repository
     */
    public Coupon forUpdateCoupon(int couponId) throws GrouponException {
        if (!couponRepository.existsById(couponId)) {
            throw new GrouponException("There is no such coupon");
        }
        return couponRepository.getOneById(couponId);
    }

    /**
     *
     * @param coupon - Coupon bean
     * @throws GrouponException
     * Method for update coupon details
     */
    public boolean updateCoupon(Coupon coupon) throws GrouponException {
        try {
            couponRepository.saveAndFlush(coupon);
            return true;
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     *
     * @param couponId - The id of the coupon
     * @return true if successfully deleted, return false if not
     * Method for delete coupon from db
     */
    public boolean deleteCoupon(int couponId) {
        try {
            if (!couponRepository.existsById(couponId)) {
                throw new GrouponException("Coupon not found");
            }
            Coupon coupon = couponRepository.getOneById(couponId);
            couponRepository.deleteFromCustomer_couponsByCouponId(coupon.getId());
            couponRepository.deleteFromCompany_couponsByCouponId(couponId);
            couponRepository.deleteById(couponId);
            return true;
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return false;
    }

    /**
     *
     * @return list of all coupons by particular company
     * @throws GrouponException - The user entered the id of a non existent company
     * Method that gets all company coupons
     */
    public List<Coupon> getCompanyCoupons() throws GrouponException {
        if (!companyRepository.existsById(this.companyID)) {
            throw new GrouponException("Company not found");
        }
        Company company = companyRepository.getOne(this.companyID);
        return company.getCoupons();

    }
    public Coupon getCompanyCouponById(int id) throws GrouponException {
        return couponRepository.getOneById(id);

    }
    /**
     *
     * @param category - The category type of the coupon
     * @return list of coupons by one company and category
     * @throws GrouponException
     * Method for receiving a list of coupons from a particular category by one particular company
     */
    public List<Coupon> getCompanyCouponsByCategory(Category category)  {
        return couponRepository.findByCompanyIdAndCategoryId(this.companyID, category);
    }

    /**
     *

     * @param maxPrice - Max price
     * @return list of company coupons by max price
     * @throws GrouponException - The user entered the id of a non existent company
     * Method that gets list of coupons of particular company by max price
     */
    public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) throws GrouponException {
        Company company = companyRepository.getOne(companyID);
        List<Coupon> coupons = company.getCoupons();
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
     * @return Company bean
     * @throws GrouponException - The user entered the id of a non existent company
     * Method that gets details of one particular company
     */
    public Company getCompanyDetails() throws GrouponException {
        try {
            if (!companyRepository.existsById(this.companyID)) {
                throw new GrouponException("Company not found");
            }
            return companyRepository.findById(this.companyID);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return companyRepository.findById(this.companyID);
    }


}
