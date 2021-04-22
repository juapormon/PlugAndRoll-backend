package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.repositories.ForumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ForumService {

    private ForumRepository forumRepository;

    public List<Forum> findAll(){
        List<Forum> res = forumRepository.findAll();
        return res;
    }

    public Forum findByType(String type){
        List<Forum> aux = findAll();
        //no me gusta el null me gustaria cambiarlo por otra cosa pero no se el que,
        //En principio no ponqo que esto sea lista por que mi idea es que solo halla un foro por cada typo
        Forum res = aux.stream().filter(f -> f.getType().toString().equals(type)).findFirst().orElse(null);
        return res;
    }
}
