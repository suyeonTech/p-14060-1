package com.back.domain.wiseSaying.repository;

import com.back.domain.Util;
import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.io.*;

public class WiseSayingRepository {
    public static void updateWiseSaying(WiseSaying wiseSaying) {
        try (FileWriter writer = new FileWriter(Util.folderName + "/" + wiseSaying.getId() + ".json")) {
            writer.write(WiseSayingService.makeJson(wiseSaying));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateLastId(int num) {
        try (FileWriter writer = new FileWriter(Util.folderName + "/lastId.txt")) {
            writer.write(Integer.toString(num));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteWiseSaying(String id) {
        File file = new File(Util.folderName + "/" + id + ".json");
        if (file.exists()) {
            file.delete();
        } else {
            System.out.println("No such quote number: " + id);
        }
    }

    public static int getLastId() {
        File lastFile = new File(Util.folderName + "/lastId.txt");
        if (lastFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(lastFile))) {
                String line = reader.readLine();
                return Integer.parseInt(line) + 1;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else return 1;
    }

    public static void updateData(String data) {
        try (FileWriter writer = new FileWriter(Util.folderName + "/data.json")) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
