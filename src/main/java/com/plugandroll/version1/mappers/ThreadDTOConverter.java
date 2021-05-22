package com.plugandroll.version1.mappers;

import com.plugandroll.version1.dtos.GetThreadDTO;
import com.plugandroll.version1.models.Thread;
import org.springframework.stereotype.Component;

@Component
public class ThreadDTOConverter {

    public static GetThreadDTO ThreadToGetThreadDTO(Thread thread) {

        return GetThreadDTO.builder().id(thread.getId())
                .title(thread.getTitle())
                .build();
    }
}
