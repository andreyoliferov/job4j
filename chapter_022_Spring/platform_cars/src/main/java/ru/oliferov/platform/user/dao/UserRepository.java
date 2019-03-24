package ru.oliferov.platform.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.oliferov.platform.user.models.User;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User where name = :name")
    User findByName(@Param("name") String name);

}
