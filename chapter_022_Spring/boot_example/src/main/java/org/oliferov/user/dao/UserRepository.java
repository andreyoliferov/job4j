package org.oliferov.user.dao;

import org.oliferov.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
public interface UserRepository extends // CrudRepository<User, Integer> {
        JpaRepository<User, Integer> {

    @Query("from User where name = :name")
    User findByName(@Param("name") String name);

}
