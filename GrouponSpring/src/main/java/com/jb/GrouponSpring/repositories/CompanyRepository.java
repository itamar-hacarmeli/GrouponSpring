
package com.jb.GrouponSpring.repositories;
import com.jb.GrouponSpring.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository is a DAOs (Data Access Object) that access the database directly.
 * The repository does all the operations related to the database.
 * repository contains methods for performing CRUD operations, sorting and paginating data.
 */

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findById(int id);

    Company findByName(String name);

    Company findByEmail(String email);

    Company findByEmailAndPassword(String email, String password);

    @Override
    void deleteById(Integer id);
}
