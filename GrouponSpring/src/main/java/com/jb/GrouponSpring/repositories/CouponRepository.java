package com.jb.GrouponSpring.repositories;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * The repository is a DAOs (Data Access Object) that access the database directly.
 * The repository does all the operations related to the database.
 * repository contains methods for performing CRUD operations, sorting and paginating data.
 */

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Coupon getOneById(int couponId);

    Coupon findOneByIdAndCompanyId(int id, int companyId);

    List<Coupon> findByCompanyIdAndCategoryId(int companyId, Category categoryId);

    boolean existsByTitleAndCompanyId(String title, int companyID);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE customer_id=:customerId", nativeQuery = true)
    void deleteFromCustomer_coupons(int customerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE coupons_id=:couponId", nativeQuery = true)
    void deleteFromCustomer_couponsByCouponId(int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM companies_coupons WHERE company_id=:companyId", nativeQuery = true)
    void deleteFromCompany_coupons(int companyId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM companies_coupons WHERE coupons_id=:couponId", nativeQuery = true)
    void deleteFromCompany_couponsByCouponId(int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE company_id=:companyId", nativeQuery = true)
    void deleteCouponByCompanyId(int companyId);

    Coupon findById(int id);
}
