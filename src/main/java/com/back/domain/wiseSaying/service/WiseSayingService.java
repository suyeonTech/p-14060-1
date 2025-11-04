package com.back.domain.wiseSaying.service;

import com.back.Quote;
import com.back.domain.Util;
import com.back.domain.wiseSaying.entity.WiseSaying;

import java.io.*;

public class WiseSayingService {

    public static String extractId(String cmd) {
        return cmd.substring(cmd.indexOf("=") + 1);
    }

    public static boolean isDataFile(String name) {
        return !name.equals("lastId.txt") && !name.equals("data.json");
    }

    public static WiseSaying getWiseSaying(File file) {
        String line;
        String content = "";
        String author = "";
        String id = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"content\":")) {
                    content = line.split(":")[1].trim();
                    content = content.substring(1, content.length() - 2);
                } else if (line.startsWith("\"author\":")) {
                    author = line.split(":")[1].trim();
                    author = author.substring(1, author.length() - 1);
                } else if (line.startsWith("\"id\":")) {
                    id = line.split(":")[1].trim();
                    id = id.substring(0, id.length() - 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new WiseSaying(Integer.parseInt(id), content, author);
    }

    public static String makeJson(WiseSaying wiseSaying) {
        return "{\n" +
                " \"id\": " + wiseSaying.getId() + ",\n" +
                "  \"content\": \"" + wiseSaying.getContent() + "\",\n" +
                "  \"author\": \"" + wiseSaying.getAuthor() + "\"\n" +
                "}";
    }
}
