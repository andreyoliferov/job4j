package ru.oliferov.platform.advt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.oliferov.platform.advt.dao.AdvtRepository;
import ru.oliferov.platform.advt.models.Advt;

import java.util.List;

/**
 * @autor aoliferov
 * @since 25.03.2019
 */
@Service
public class AdvtServiceImpl implements AdvtService {

    @Autowired
    private AdvtRepository advtRepository;


    @Override
    public Advt addAdvt(Advt advt) {
        return advtRepository.saveAndFlush(advt);
    }

    @Override
    public void delete(int id) {
        advtRepository.deleteById(id);
    }

    @Override
    public Advt edidAdvt(Advt advt) {
        return advtRepository.saveAndFlush(advt);
    }

    @Override
    public List<Advt> findAll() {
        return advtRepository.findAll();
    }
}
