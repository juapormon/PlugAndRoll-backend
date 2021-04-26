package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.repositories.PublicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationService {

    private PublicationRepository publicationRepository;

    public List<Publication> findAll(){
        List<Publication> res = publicationRepository.findAll();
        Collections.reverse(res);
        return res;
    }
}
