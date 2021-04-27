package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.repositories.PublicationRepository;
import io.jsonwebtoken.lang.Assert;
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

    public Publication findById(String id){
        List<Publication> list = publicationRepository.findAll();
        Publication res = list.stream().filter(p -> p.getId().equals(id)).findAny().get();
        return res;
    }
    public String addPublication(Publication publication){
        Assert.notNull(publication);
        this.publicationRepository.save(publication);
        return "Added publication of: " + publication.getCreator().getUsername();
    }
}
