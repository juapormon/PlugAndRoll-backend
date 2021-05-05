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
        return forumRepository.findAll();
    }
}
