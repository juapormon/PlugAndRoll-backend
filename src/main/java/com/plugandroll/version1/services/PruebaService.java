package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Prueba;
import com.plugandroll.version1.repositories.PruebaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PruebaService {

    private PruebaRepository pruebaRepository;

    public List<Prueba> findAll(){
        List<Prueba> res = pruebaRepository.findAll();
        Collections.reverse(res);
        return res;
    }

}
