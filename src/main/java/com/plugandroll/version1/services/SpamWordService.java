package com.plugandroll.version1.services;

import com.plugandroll.version1.dtos.GetPublicationDTO;
import com.plugandroll.version1.dtos.GetThreadDTO;
import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.models.SpamWord;
import com.plugandroll.version1.repositories.SpamWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpamWordService {

    @Autowired
    private SpamWordRepository spamWordRepository;

    public List<SpamWord> findAllNoAllowed(){
        List<SpamWord> res = spamWordRepository.findAll();
        return res;
    }

    public Boolean isSpam(String text) {
        boolean isSpam = false;

        List<String> splitted = Arrays.asList(text.split("\\s|\\p{Punct}"));

        splitted = splitted.stream().filter(s -> s != "").collect(Collectors.toList());

        int n = splitted.size();
        int count = 0;

        List<SpamWord> spam = findAllNoAllowed();

        String lowerCaseString = text.toLowerCase().replaceAll("\\s+{2,}", " ");

        for (SpamWord s : spam) {
            s.setWord(s.getWord().trim());
            count += StringUtils.countMatches(lowerCaseString, s.getWord());
            isSpam = count >= 0.2 * n;
            if (isSpam) {
                break;
            }
        }

        return isSpam;
    }

    public Boolean CheckThread(GetThreadDTO threadDTO) {
        Boolean res=false;
        if(isSpam(threadDTO.getTitle())) {
            res = true;
        }
        return res;
    }

    public Boolean CheckPublication(GetPublicationDTO publicationDTO) {
        Boolean res=false;
        if(isSpam(publicationDTO.getText())) {
            res = true;
        }
        return res;
    }

}
