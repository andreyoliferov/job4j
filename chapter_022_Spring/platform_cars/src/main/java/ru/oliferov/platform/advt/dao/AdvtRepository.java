package ru.oliferov.platform.advt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oliferov.platform.advt.models.Advt;

/**
 * @autor aoliferov
 * @since 25.03.2019
 */
public interface AdvtRepository extends JpaRepository<Advt, Integer> {
}
