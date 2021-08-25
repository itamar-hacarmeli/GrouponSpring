
package com.jb.GrouponSpring.repositories;
import com.jb.GrouponSpring.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository is a DAOs (Data Access Object) that access the database directly.
 * The repository does all the operations related to the database.
 * repository contains methods for performing CRUD operations, sorting and paginating data.
 */



public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);

    Customer findById(int id);

    Customer getOneById(int customerId);

    Customer findByEmailAndPassword(String email, String password);


}
