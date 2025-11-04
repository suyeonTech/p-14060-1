package com.back.domain.wiseSaying.controller;

import com.back.Quote;
import com.back.domain.Util;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.io.File;
import java.io.FileWriter;

public class WiseSayingController {

    public static void register() {
        System.out.print("content : ");
        String content = Util.sc.nextLine();
        System.out.print("author : ");
        String author = Util.sc.nextLine();

        int num = WiseSayingRepository.getLastId();
        WiseSaying wiseSaying = new WiseSaying(num, content, author);
        WiseSayingRepository.updateWiseSaying(wiseSaying);
        WiseSayingRepository.updateLastId(num);

        System.out.println("Quote number" + num + " registered.");

    }

    public static void delete(String cmd) {
        String id = WiseSayingService.extractId(cmd);
        WiseSayingRepository.deleteWiseSaying(id);
    }

    public static void modify(String cmd) {
        String id = WiseSayingService.extractId(cmd);
        File file = new File(Util.folderName + "/" + id + ".json");
        if (file.exists()) {
            WiseSaying wiseSaying = WiseSayingService.getWiseSaying(file);

            System.out.println("content(before) : " + wiseSaying.getContent());
            System.out.print("content : ");
            String content = Util.sc.nextLine();
            System.out.println("author(before) : " + wiseSaying.getAuthor());
            System.out.print("author : ");
            String author = Util.sc.nextLine();

            wiseSaying = new WiseSaying(Integer.parseInt(id), content, author);
            WiseSayingRepository.updateWiseSaying(wiseSaying);

        } else {
            System.out.println("No such quote number: " + id);
        }
    }
}
