package com.jb.GrouponSpring.services;


import com.jb.GrouponSpring.beans.Company;
import com.jb.GrouponSpring.beans.Coupon;
import com.jb.GrouponSpring.beans.Customer;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.repositories.CompanyRepository;
import com.jb.GrouponSpring.repositories.CouponRepository;
import com.jb.GrouponSpring.repositories.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Service
public class AdministratorService extends ClientService {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    private static final String email = "admin@admin.com";
    private static final String password = "admin";

    /**
     * @param email    - Administrator email
     * @param password - Administrator password
     * @return true if email and password are Matching, and return false if not
     * @throws GrouponException - Invalid email or password
     *                          The method is used for admin login
     */
    public boolean login(String email, String password) throws GrouponException {
        if (!(email.equals(AdministratorService.email) && password.equals(AdministratorService.password))) {
            throw new GrouponException("Invalid email or password");
        }
        return true;
    }

    /**
     * @param company - Company bean
     * @return true if insertion was successful, and return false if not
     * The method is used to add a company to the database in to companies table
     */
    public boolean addCompany(Company company) {
        try {
            if (companyRepository.findByName(company.getName()) != null || companyRepository.findByEmail(company.getEmail()) != null) {
                throw new GrouponException("Cannot add company with the same name or email");
            }
            companyRepository.save(company);
            return true;
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * @param companyId - The id of the company.
     * @return - Company bean
     * @throws GrouponException - The user entered the id of a non existent company
     *                          Method for update company, for not externalizing the repository
     */
    public Company forUpdateCompany(int companyId) throws GrouponException {
        if (!companyRepository.existsById(companyId)) {
            throw new GrouponException("There is no such company");
        }
        return companyRepository.getOne(companyId);
    }

    /**
     * @param company - Company bean
     * @throws GrouponException Method for update company details
     */

    public boolean updateCompany(Company company) throws GrouponException {
        try {
            companyRepository.saveAndFlush(company);
            return true;
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return false;
        }
    }


    /**
     * @param companyId - The id of the company.
     * @return true if successfully deleted, return false if not
     * Method for delete company from db
     */
    public Object deleteCompany(int companyId) {
        try {
            if (!companyRepository.existsById(companyId)) {
                throw new GrouponException("Company not found");
            }
            Company company = companyRepository.getOne(companyId);
            List<Coupon> coupons = company.getCoupons();
            for (Coupon item : coupons) {
                couponRepository.deleteFromCustomer_couponsByCouponId(item.getId());
                couponRepository.deleteFromCompany_coupons(companyId);
            }
            companyRepository.deleteById(companyId);
            System.out.println("inside the delete3");
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    /**
     * @return list of all companies for show from db
     * shows all companies with there details
     */
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    /**
     * @param id - Company id
     * @return Company bean from db
     * Method that gets one company details
     */
    public Company getOneCompanyById(int id) {
        try {
            if (!companyRepository.existsById(id)) {
                throw new GrouponException("Company not found");
            }
            return companyRepository.findById(id);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return companyRepository.findById(id);
    }

    /**
     * @param customer - Customer bean
     * @return true if insertion was successful, and return false if not
     * The method is used to add a customer to the database in to customers table
     */
    public boolean addCustomer(Customer customer) {
        try {
            if (customerRepository.findByEmail(customer.getEmail()) != null) {
                throw new GrouponException("Can not add Customer with the same email");
            }
            customerRepository.save(customer);
            return true;

        } catch (Exception err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * @param customerId - The id of the customer
     * @return Customer bean
     * @throws GrouponException - The user entered the id of a non existent customer
     *                          this method for update customer, for not externalizing the repository
     */
    public Customer forUpdateCustomer(int customerId) throws GrouponException {
        if (!customerRepository.existsById(customerId)) {
            throw new GrouponException("There is no such customer");
        }
        return customerRepository.getOne(customerId);
    }

    /**
     * @param customer - Customer bean
     * @throws GrouponException Method for update customer details
     */
    public boolean updateCustomer(Customer customer) {
        try {
            customerRepository.saveAndFlush(customer);
            return true;
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * @param customerID - The id of the customer
     * @return true if successfully deleted, return false if not
     * Method for delete customer from db
     */
    public boolean deleteCustomer(int customerID) {
        try {
            if (!customerRepository.existsById(customerID)) {
                throw new GrouponException("Customer no found");
            }
            couponRepository.deleteFromCustomer_coupons(customerID);
            customerRepository.deleteById(customerID);
            return true;
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return false;
    }

    /**
     * @return list of all customers for show from db
     * shows all customers with there details
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * @param customerId - The id of the customer
     * @return Customer bean from db
     * Method that gets one customer details
     */
    public Customer getCustomerById(int customerId) {
        try {
            if (!customerRepository.existsById(customerId)) {
                throw new GrouponException("Customer not found");
            }
            return customerRepository.findById(customerId);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return customerRepository.findById(customerId);
    }

    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }
}
