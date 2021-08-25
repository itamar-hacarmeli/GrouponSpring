package com.jb.GrouponSpring.clr;



import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.loginManager.ClientType;
import com.jb.GrouponSpring.loginManager.LoginManager;
import com.jb.GrouponSpring.repositories.CustomerRepository;
import com.jb.GrouponSpring.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class testing the entire system by reading each of the business logic functions for customer client.
 */
//@Component
@RequiredArgsConstructor
@Order(3)
public class CustomerTest implements CommandLineRunner {
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        try {
            CustomerService customerService = (CustomerService) loginManager.login("tom@gmail.com", "11111", ClientType.CUSTOMER);

//            customerService.purchaseCoupon(1,4);
//            customerService.purchaseCoupon(2,2);
            //System.out.println(customerService.getCustomerCoupons(1));
            //System.out.println(customerService.getCustomerCouponsByCategory(1, Category.FOOD));
            //System.out.println(customerService.getCustomerCouponsByMaxPrice(1,1500.00));
            //System.out.println(customerService.getCustomerDetails(2));


        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
