package com.plugandroll.version1.services;

import com.plugandroll.version1.dtos.GetThreadToCreateDTO;
import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.*;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.repositories.ForumRepository;
import com.plugandroll.version1.repositories.PublicationRepository;
import com.plugandroll.version1.repositories.ThreadRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ThreadService {

    private ThreadRepository threadRepository;
    private UserEntityRepository userEntityRepository;
    private ForumRepository forumRepository;

    public List<Thread> findAll(){
        List<Thread> res = threadRepository.findAll();
        Collections.reverse(res);
        return res;
    }

    public Thread findById(String threadId) throws ChangeSetPersister.NotFoundException {
        return this.threadRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public List<Thread> findByForum(User principal, String forumId){
        List<Thread> res = new ArrayList<>();
        List<Thread> threads = threadRepository.findByForumId(forumId);
        if(principal==null) {
            res.addAll(threads.stream().filter(a -> a.getOnlyAuth() == false).collect(Collectors.toList()));
        }else{
            res.addAll(threads);
        }
        Collections.reverse(res);
        return res;
    }

    public String editThread(User principal, Thread thread, String threadId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Thread threadToUpdate = this.threadRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(threadToUpdate.getCreator().getUsername())){
            threadToUpdate.setTitle(thread.getTitle());
            threadToUpdate.setOnlyAuth(thread.getOnlyAuth());
            this.threadRepository.save(threadToUpdate);
        }else{
            throw new UnauthorizedException();
        }
        return "Thread was successfully edited";
    }

    public String addThread(User principal, GetThreadToCreateDTO getThreadToCreateDTO) throws ChangeSetPersister.NotFoundException{
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Forum forum = this.forumRepository.findById(getThreadToCreateDTO.getForumId()).orElse(null);

        Thread newThread = new Thread(getThreadToCreateDTO.getTitle(),
                0.0,
                LocalDateTime.now(),
                null,
                UserDTOConverter.UserToGetUserDTO(me),
                getThreadToCreateDTO.getOnlyAuth(),
                forum);

        this.threadRepository.save(newThread);
        return "Thread was successfully added";
    }

    public String closeThread(User principal, String threadId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Thread threadToUpdate = this.threadRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(threadToUpdate.getCreator().getUsername())||me.getRoles().contains(TypeRol.ADMIN)){
            threadToUpdate.setCloseDate(LocalDateTime.now());
            this.threadRepository.save(threadToUpdate);
        }else{
            throw new UnauthorizedException();
        }
        return "Thread was successfully closed";
    }

    public String deleteThread(User principal, String threadId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Thread threadToDelete = this.threadRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(threadToDelete.getCreator().getUsername())||me.getRoles().contains(TypeRol.ADMIN)){
            this.threadRepository.deleteById(threadId);
        }else{
            throw new UnauthorizedException();
        }
        return "Thread was successfully deleted";
    }




}
