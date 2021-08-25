package com.jb.GrouponSpring.clr;



import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.enums.Category;
import com.jb.GrouponSpring.loginManager.ClientType;
import com.jb.GrouponSpring.loginManager.LoginManager;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.sql.Date;

/**
 * This class testing the entire system by reading each of the business logic functions for company client.
 */
@Component
@RequiredArgsConstructor
@Order(2)
public class CompanyTest implements CommandLineRunner {

    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        try {
            CompanyService companyService = (CompanyService) loginManager.login("amazon@gmail.com", "12345", ClientType.COMPANY);


            Coupon coupon1 = Coupon.builder()
                    .categoryId(Category.FOOD)
                    .title("Best buy")
                    .description("1 kg red meat")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2022-04-28"))
                    .amount(50)
                    .price(50.00).image("CouponsImages/Ribsteak.jpg")
                    .build();


            Coupon coupon2 = Coupon.builder()
                    .categoryId(Category.VACATION)
                    .title("Tom hotel")
                    .description("Double or Twin Room –  No prepayment needed")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2022-06-28"))
                    .amount(50)
                    .price(1500.00).image("CouponsImages/tomhotel.jpg")
                    .build();

            Coupon coupon3 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Electric shop")
                    .description("75″ Class TU8000 Crystal UHD 4K Smart TV (2020)")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2022-06-28"))
                    .amount(50)
                    .price(2000.00).image("CouponsImages/crystal.jpg")
                    .build();

            Coupon coupon4 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Big Electric")
                    .description("LG 70 Class 4K UHD 2160P Smart TV with HDR - 70UN7070PUA 2020 Model")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2022-06-28"))
                    .amount(50)
                    .price(2500.00).image("CouponsImages/lg_tv.jpg")
                    .build();


            Coupon coupon41 = Coupon.builder()
                    .categoryId(Category.VACATION)
                    .title("Skydiving No pursuit included")
                    .description("Jump from the airplain at 500 KMPH no insurance")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-10-28"))
                    .amount(50)
                    .price(2500.00).image("CouponsImages/skydiving.jpg")
                    .build();

            Coupon coupon42 = Coupon.builder()
                    .categoryId(Category.VACATION)
                    .title("SPA")
                    .description("Free massage with boiling canola oil ")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-09-28"))
                    .amount(50)
                    .price(2500.00).image("CouponsImages/spa.jpg")
                    .build();

            Coupon coupon43 = Coupon.builder()
                    .categoryId(Category.VACATION)
                    .title("Travel around the world")
                    .description("Travel around the world only green countries and cities ")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-09-24"))
                    .amount(50)
                    .price(2500.00).image("CouponsImages/travel.jpg")
                    .build();

            System.out.println(8);
            companyService.addCoupon(coupon1);
            companyService.addCoupon(coupon2);
            companyService.addCoupon(coupon3);
            companyService.addCoupon(coupon4);
            companyService.addCoupon(coupon41);
            companyService.addCoupon(coupon42);
            companyService.addCoupon(coupon43);

            companyService = (CompanyService) loginManager.login("tnova@gmail.com", "12345", ClientType.COMPANY);
            Coupon coupon5 = Coupon.builder()
                    .categoryId(Category.FOOD)
                    .title("Eat Buffa")
                    .description("Eat Until you can't")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-08-28"))
                    .amount(50)
                    .price(150.00).image("CouponsImages/eatbuffa.jpg")
                    .build();

            Coupon coupon51 = Coupon.builder()
                    .categoryId(Category.FOOD)
                    .title("Pizza Pizza Matrizza")
                    .description("Pizza napoletana fresh from the wood fired oven just like italy!!!")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-08-28"))
                    .amount(50)
                    .price(150.00).image("CouponsImages/pizza.jpg")
                    .build();






            companyService.addCoupon(coupon51);
            companyService.addCoupon(coupon5);

            Coupon coupon6 = Coupon.builder()
                    .categoryId(Category.VACATION)
                    .title("Tal hotel")
                    .description("Double or Twin Room – • No prepayment needed bad service good place")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-09-28"))
                    .amount(50)
                    .price(1500000.00).image("CouponsImages/talhotel.jpg")
                    .build();

            companyService = (CompanyService) loginManager.login("microsoft@gmail.com", "12345", ClientType.COMPANY);
            Coupon coupon7 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Microsoft Magic Mouse")
                    .description("New Awesome shiny luxury mouse")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-08-28"))
                    .amount(50)
                    .price(20.00).image("CouponsImages/microsoftmouse.jpg")
                    .build();

            Coupon coupon8 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Microsoft XBOX")
                    .description("New Awesome shiny luxury XBOX console For Free ")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-09-28"))
                    .amount(50)
                    .price(25000.00).image("CouponsImages/microsoft.jpg")
                    .build();

            Coupon coupon9 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Microsoft Key board")
                    .description("if you are not a programmer this is the keyboard for you")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-08-28"))
                    .amount(50)
                    .price(75.00).image("CouponsImages/microsoftkeyboard.png")
                    .build();




            companyService.addCoupon(coupon7);
            companyService.addCoupon(coupon8);
            companyService.addCoupon(coupon9);


            companyService = (CompanyService) loginManager.login("apple@gmail.com", "12345", ClientType.COMPANY);


            Coupon coupon11 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Apple watch")
                    .description(" shiny luxury apple watch with apple pay (you pay to apple)")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-08-28"))
                    .amount(50)
                    .price(2330.00).image("CouponsImages/applewatch.jpg")
                    .build();

            Coupon coupon12 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Apple Macbook")
                    .description(" Apple Macbook  programmers only (showing diploma at store)")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-09-28"))
                    .amount(50)
                    .price(78000.00).image("CouponsImages/applemackbook.jpg")
                    .build();

            Coupon coupon13 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("IphoneX")
                    .description("forget your X and buy your self the new AppleX ")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-08-28"))
                    .amount(50)
                    .price(900.00).image("CouponsImages/appleiphone.png")
                    .build();


            Coupon coupon14 = Coupon.builder()
                    .categoryId(Category.ELECTRICITY)
                    .title("Apple Airpods")
                    .description("New Awesome shiny luxury Airpods (pods that in the air)")
                    .startDate(Date.valueOf("2021-04-28"))
                    .endDate(Date.valueOf("2021-09-28"))
                    .amount(50)
                    .price(1.00).image("CouponsImages/lg_tv.jpg")
                    .build();



            companyService.addCoupon(coupon11);
            companyService.addCoupon(coupon12);
            companyService.addCoupon(coupon13);
            companyService.addCoupon(coupon14);
            //Coupon coupon = companyService.forUpdateCoupon(1);
            //coupon.setAmount(800);
            //companyService.updateCoupon(coupon);

            //companyService.deleteCoupon(2);

            //System.out.println(companyService.getCompanyCoupons(3));

            //System.out.println(companyService.getCompanyCouponsByCategory(2,Category.VACATION));

            //System.out.println(companyService.getCompanyCouponsByMaxPrice(1,2500.00));

            //System.out.println(companyService.getCompanyDetails(1));




        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
