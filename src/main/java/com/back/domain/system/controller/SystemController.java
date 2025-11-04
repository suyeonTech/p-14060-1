package com.back.domain.system.controller;

import com.back.domain.Util;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.back.domain.wiseSaying.service.WiseSayingService.isDataFile;

public class SystemController {

    public static void build() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        File[] files = Util.folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (WiseSayingService.isDataFile(file.getName())) {
                    WiseSaying wiseSaying = WiseSayingService.getWiseSaying(file);
                    if (sb.charAt(sb.length() - 1) == '}') {
                        sb.append(",");
                    }
                    sb.append(WiseSayingService.makeJson(wiseSaying));
                }
            }
        }
        sb.append("]");
        WiseSayingRepository.updateData(sb.toString());
    }

    public static void list() {
        System.out.println("----------------------");

        File[] files = Util.folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isDataFile(file.getName())) {
                    WiseSaying wiseSaying = WiseSayingService.getWiseSaying(file);
                    System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());

                }
            }
        }
    }

}
