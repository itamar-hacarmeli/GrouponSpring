package com.jb.GrouponSpring.clr;

import com.jb.GrouponSpring.beans.Company;
import com.jb.GrouponSpring.beans.Customer;
import com.jb.GrouponSpring.loginManager.ClientType;
import com.jb.GrouponSpring.loginManager.LoginManager;
import com.jb.GrouponSpring.services.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class testing the entire system by reading each of the business logic functions for administrator client.
 */
@Component
@RequiredArgsConstructor
@Order(1)
public class AdministratorTest implements CommandLineRunner {

    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        try {
            AdministratorService administratorService = (AdministratorService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);


            Company company1 = Company.builder()
                    .name("Intel")
                    .email("intel@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCompany(company1);


            Company company2 = Company.builder()
                    .name("Microsoft")
                    .email("microsoft@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCompany(company2);

            Company company3 = Company.builder()
                    .name("Amazon")
                    .email("amazon@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCompany(company3);

            Company company4 = Company.builder()
                    .name("Tnova")
                    .email("tnova@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCompany(company4);

            Company company5 = Company.builder()
                    .name("Apple")
                    .email("apple@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCompany(company5);





            Customer customer1 = Customer.builder()
                    .firstName("Tom")
                    .lastName("Hacarmeli")
                    .email("tom@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCustomer(customer1);

            Customer customer2 = Customer.builder()
                    .firstName("Amir")
                    .lastName("Hacarmeli")
                    .email("amir@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCustomer(customer2);

            Customer customer3 = Customer.builder()
                    .firstName("Shimon")
                    .lastName("Ben-shimon")
                    .email("shimon@gmail.com")
                    .password("12345")
                    .build();
            administratorService.addCustomer(customer3);


            //Company company = administratorService.forUpdateCompany(1);
            //company.setPassword("boom");
            //administratorService.updateCompany(company);

            //administratorService.deleteCompany(1);
            //administratorService.getAllCompanies().forEach(System.out::println);
            //System.out.println(administratorService.getOneCompanyById(2));


            //Customer customer = administratorService.forUpdateCustomer(1);
            //customer.setLastName("Nana-Banana");
            //administratorService.updateCustomer(customer);

            //administratorService.deleteCustomer(1);
            //administratorService.getAllCustomers().forEach(System.out::println);
            //System.out.println(administratorService.getCustomerById(2));
            //System.out.println(administratorService.getAllCoupons());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
