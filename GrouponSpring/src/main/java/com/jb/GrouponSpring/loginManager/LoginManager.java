package com.jb.GrouponSpring.loginManager;
import com.jb.GrouponSpring.exceptions.GrouponException;
import com.jb.GrouponSpring.services.AdministratorService;
import com.jb.GrouponSpring.services.ClientService;
import com.jb.GrouponSpring.services.CompanyService;

import com.jb.GrouponSpring.services.CustomerService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * LoginManager - This class is a singleton class that contains a login function that allows
 * users to connect to the system.
 */

@Service
@RequiredArgsConstructor
public class LoginManager {


    private final AdministratorService administratorService;

    private final CompanyService companyService;

    private final CustomerService customerService;

    /**
     *
     * @param email - the email of the user
     * @param password - the password of the user
     * @param clientType - client types (administrator,company,customer)
     * @return ClientService
     * @throws GrouponException - Login failed, no such user
     * This function allows users to connect to the system.
     * For a client who entered correctly will be returned ClientService
     * For each incorrect entry will be returned null
     */
    public ClientService login(String email, String password, ClientType clientType) throws GrouponException {
        ClientService result;
        switch (clientType) {

            case ADMINISTRATOR:
                result = (administratorService.login(email, password)) ? administratorService : null;
                break;


            case COMPANY:
                result = (companyService.login(email, password)) ? companyService : null;
                break;


            case CUSTOMER:

                result = (customerService.login(email, password)) ? customerService : null;
                break;

            default:
                throw new GrouponException("Login failed!");
        }
        if (result == null) {
            throw new GrouponException("There is no such user");
        }
        return result;
    }
}
