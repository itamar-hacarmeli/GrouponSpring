package com.jb.GrouponSpring.controllers;


import com.jb.GrouponSpring.User.UserDetails;
import com.jb.GrouponSpring.Util.JWTutil;
import com.jb.GrouponSpring.beans.Company;
import com.jb.GrouponSpring.beans.Customer;
import com.jb.GrouponSpring.exceptions.AddException;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.exceptions.UpdateException;
import com.jb.GrouponSpring.loginManager.ClientType;
import com.jb.GrouponSpring.repositories.CompanyRepository;
import com.jb.GrouponSpring.repositories.CustomerRepository;
import com.jb.GrouponSpring.services.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController class allows sending and receiving data to its relevant Facade through exposure
 * The functionality as a REST ful Service.
 * RestController - This annotation is applied to a class to mark it as a request handler.
 * Spring RestController annotation is used to create REST ful web services using Spring MVC.
 */

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("groupon/admin")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdministratorService administratorService;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final JWTutil util;

    @PostMapping("login")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestBody UserDetails myData) {
        System.out.println(myData);
        try {
            if (administratorService.login(myData.getEmail(), myData.getPassword())) {
                //go to database, get user by it's password, email, clientType
                String myToken = util.generateToken(new UserDetails(myData.getEmail(), 0, ClientType.ADMINISTRATOR));
                return new ResponseEntity<>(myToken, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("WHO ARE YOU?", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * @param company - Company bean.
     * @throws AddException if the name or email already exist.
     *                      Method for adding company to database, and return response to a request by the user.
     */
    @PostMapping("companies/addCompany")
    @CrossOrigin
    public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                if (companyRepository.findByName(company.getName()) != null || companyRepository.findByEmail(company.getEmail()) != null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.addCompany(company));
            }
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param company - Company bean.
     * @return - ResponseEntity.
     * @throws UpdateException if user try to update id or name.
     *                         Method for update company by request to APIs.
     *                         return HTTP status -  ACCEPTED if Succeeded, ALREADY_REPORTED if not.
     */
    @PutMapping("companies/update")
    @CrossOrigin
    public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestHeader(name = "Authorization") String token) throws UpdateException {
        try {
            if (util.validateToken(token)) {
                Company company1 = administratorService.forUpdateCompany(company.getId());
                company1.setPassword(company.getPassword());
                company1.setEmail(company.getEmail());
                return ResponseEntity.ok().header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),0, ClientType.ADMINISTRATOR))).
                        body(administratorService.updateCompany(company1));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param id - The id of the company.
     * @return ResponseEntity.
     * Method for delete company by request to APIs.
     * return HTTP status - OK if Succeeded, BAD_REQUEST if not.
     */
    @DeleteMapping("companies/{id}")
    @CrossOrigin
    public ResponseEntity<?> deleteCompany(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        try {
            System.out.println(token+"         test");
            if (util.validateToken(token)) {
                return ResponseEntity.ok().header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token), 0, ClientType.ADMINISTRATOR)))
                        .body(administratorService.deleteCompany(id));
            }
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    /**
     * @return - return HTTP status - OK.
     * Method for Get all companies with there details from database, and return response to a request by the user.
     */
    @GetMapping("companies")
    @CrossOrigin
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.getAllCompanies());
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    /**
     * @param id - The id of the company.
     * @return - ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * Method that get single company from database by request to APIs.
     */
    @GetMapping("companies/{id}")
    @CrossOrigin
    public ResponseEntity<?> getSingleCompany(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                if (!companyRepository.existsById(id)) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.getOneCompanyById(id));
            }
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param customer - customer bean.
     * @throws AddException if there is a customer with the same email.
     *                      Method for adding customer to the database, and return response to a request by the user
     *                      HTTP status - CREATED if Succeeded, BAD_REQUEST if not.
     */
    @PostMapping("customers/addCustomer")
    @ResponseStatus(code = HttpStatus.CREATED)
    @CrossOrigin
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestHeader(name = "Authorization") String token) {
        System.out.println(customer);
        try {
            if (util.validateToken(token)) {
                if (customerRepository.findByEmail(customer.getEmail()) != null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.addCustomer(customer));
            }

        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param customer - Customer bean.
     * @return - ResponseEntity : HTTP status -  ACCEPTED if Succeeded, ALREADY_REPORTED if not.
     * @throws UpdateException if the user try to change the customer id.
     *                         Method for update customer details, and return response to a request by the user.
     */
    @PutMapping("customers/updateCustomer")
    @CrossOrigin
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.updateCustomer(customer));
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @param id - The id the of the customer.
     * @return ResponseEntity : HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * Method for delete customer from database, and return response to a request by the user.
     */
    @DeleteMapping("customers/{id}")
    @CrossOrigin
    public ResponseEntity<?> deleteCustomer(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.deleteCustomer(id));
            }
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    /**
     * @return - ResponseEntity - HTTP status - OK.
     * Method for Get all customers with there details from database, and return response to a request by the user.
     */
    @GetMapping("customers")
    @CrossOrigin
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.getAllCustomers());
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * @param id - The id of the customer.
     * @return ResponseEntity :  HTTP status - OK if Succeeded, BAD_REQUEST if not.
     * Method that get single customer from database by request to APIs.
     */
    @GetMapping("customers/{id}")
    @CrossOrigin
    public ResponseEntity<?> getSingleCustomer(@PathVariable int id, @RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                if (!customerRepository.existsById(id)) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.getCustomerById(id));
            }
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("coupons")
    @CrossOrigin
    public ResponseEntity<?> getAllCoupons (@RequestHeader(name = "Authorization") String token) {
        try {
            if (util.validateToken(token)) {
                return ResponseEntity.ok()
                        .header("Authorization", util.generateToken(new UserDetails(util.extractUserEmail(token),
                                0,
                                ClientType.ADMINISTRATOR
                        )))
                        .body(administratorService.getAllCoupons());
            }
        } catch (Exception err) {

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}