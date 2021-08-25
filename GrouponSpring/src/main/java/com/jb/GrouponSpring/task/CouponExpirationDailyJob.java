package com.jb.GrouponSpring.task;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

/**
 * This class enable scheduling jobs
 * delete expired coupons on a daily basis
 */

@EnableScheduling
@Component
public class CouponExpirationDailyJob {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CompanyService companyService;

    @Scheduled(cron = "0 0 16 * * *")
    public void deleteCoupon() {

        List<Coupon> coupons = couponRepository.findAll();
        for (Coupon item : coupons) {
            if (LocalDate.now().isAfter(item.getEndDate().toLocalDate())) {
                companyService.deleteCoupon(item.getId());
            }
        }
    }
}
