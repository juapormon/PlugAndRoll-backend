package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.ForumRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ForumService {

    private ForumRepository forumRepository;
    private UserEntityRepository userEntityRepository;

    public Set<Forum> findByRoleAuth(User principal){
        Set<Forum> res = new HashSet<>();
        List<Forum> allForums = this.forumRepository.findAll();
            UserEntity userInSession = this.userEntityRepository.findByUsername(principal.getUsername()).orElse(null);
            if (userInSession.getRoles().contains(TypeRol.ADMIN)) {
                res.addAll(allForums);
            } else if (userInSession.getRoles().contains(TypeRol.DM)) {
                res.addAll(allForums.stream().filter(f -> f.getType().contains(TypeRol.DM)).collect(Collectors.toList()));
            }
            if (userInSession.getRoles().contains(TypeRol.PLAYER)) {
                res.addAll(allForums.stream().filter(f -> f.getType().contains(TypeRol.PLAYER)).collect(Collectors.toList()));
            }
        return res;
    }

    public List<Forum> findByRoleNoAuth(){
        List<Forum> res = new ArrayList<>();
        res.add(this.forumRepository.findForumByTitle("Common Forum").orElse(null));

        return res;
    }

    public String editForum(User principal, Forum forum, String forumId) throws NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(NotFoundException::new);
        if(me.getRoles().contains(TypeRol.ADMIN)){
            Forum forumToUpdate = this.forumRepository.findById(forumId).orElseThrow(NotFoundException::new);
            forumToUpdate.setTitle(forum.getTitle());
            this.forumRepository.save(forumToUpdate);
        }else{
            throw new UnauthorizedException();
        }
        return "Forum was successfully edited";
    }
}
