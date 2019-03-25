package ru.oliferov.platform.advt.service;

import ru.oliferov.platform.advt.models.Advt;

import java.util.List;

/**
 * @autor aoliferov
 * @since 25.03.2019
 */
public interface AdvtService {

    Advt addAdvt(Advt advt);
    public void delete(int id);
    Advt edidAdvt(Advt advt);
    List<Advt> findAll();

}
