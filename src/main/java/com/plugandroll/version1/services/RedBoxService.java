package com.plugandroll.version1.services;

import com.mongodb.client.*;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.plugandroll.version1.dtos.GetUserDTO;
import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.LoadFile;
import com.plugandroll.version1.models.RedBox;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.RedBoxRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedBoxService {

    private UserEntityRepository userEntityRepository;
    private RedBoxRepository redBoxRepository;
    // private FileService fileService;

    public List<RedBox> findAll() {
        /* List<RedBox> res = new ArrayList<>();
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("plugandroll");
        MongoCollection<Document> collectionName = mongoDatabase.getCollection("fs.files");
        FindIterable<Document> fi = collectionName.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                Document currentCursor = cursor.next();
                LoadFile file = fileService.downloadFile(currentCursor.get("_id").toString());
                RedBox currentRedBox = new RedBox();
                currentRedBox.setTitle(file.getFile().toString());
                System.out.println(file);
                // res.add((RedBox) file);
                // System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        } */
        List<RedBox> res = redBoxRepository.findAll();
        Collections.reverse(res);
        return res;
    }

    public RedBox findById(String id) {
        // MultipartFile file = (MultipartFile) fileService.downloadFile(id);
        // RedBox res = (RedBox) file;
        RedBox res = redBoxRepository.findById(id).orElse(null);
        return res;
    }

    public String add(User principal, RedBox redBox) throws IllegalArgumentException, ChangeSetPersister.NotFoundException {
        Assert.notNull(redBox);
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        redBox.setCreator(UserDTOConverter.UserToGetUserDTO(me));
        // fileService.addFile(new MockMultipartFile(redBox.getTitle(), redBox.getTitle(), "application/json", IOUtils.toByteArray(redBox.toString())));
        redBoxRepository.save(redBox);
        return "Red Box created successfully!";
    }

    public String update(User principal, String id, RedBox redBox) throws IllegalArgumentException, ChangeSetPersister.NotFoundException {
        Assert.notNull(redBox);
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        RedBox redBoxToUpdate = this.redBoxRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (me.getUsername().equals(redBoxToUpdate.getCreator().getUsername())) {
            redBoxToUpdate.setTitle(redBox.getTitle());
            redBoxToUpdate.setStory(redBox.getStory());
            redBoxToUpdate.setMaps(redBox.getMaps());
            redBoxToUpdate.setMusic(redBox.getMusic());
            redBoxToUpdate.setNpcs(redBox.getNpcs());
            redBoxToUpdate.setPcs(redBox.getPcs());
            redBoxToUpdate.setTokens(redBox.getTokens());
            redBoxRepository.save(redBoxToUpdate);
        } else {
            throw new IllegalArgumentException();
        }
        return "Red Box updated successfully!";
    }

    public String delete(User principal, String id) throws IllegalArgumentException, ChangeSetPersister.NotFoundException {
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        RedBox redBoxToDelete = this.redBoxRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (me.getUsername().equals(redBoxToDelete.getCreator().getUsername()) || me.getRoles().contains(TypeRol.ADMIN))
            redBoxRepository.deleteById(id);
        else
            throw new IllegalArgumentException("You're not allowed to delete this item!");
        return "Red Box deleted successfully!";
    }

}
