package com.back;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("== Quote app ==");
        Scanner sc = new Scanner(System.in);
        String folderName = "src/main/java/com/back/db/wiseSaying";
        File folder = new File(folderName);

        ArrayList<String[]> quotes = new ArrayList<>();

        int num = 1;

        while(true) {
            System.out.print("command) ");
            String cmd = sc.nextLine();

            if (cmd.equals("exit")) {
                break;
            }

            else if (cmd.equals("register")) {
                System.out.print("content : ");
                String content = sc.nextLine();
                System.out.print("author : ");
                String author = sc.nextLine();


                String json = "{\n" +
                        " \"id\": " + num + ",\n" +
                        "  \"content\": \"" + content + "\",\n" +
                        "  \"author\": \"" + author + "\"\n" +
                        "}";

                try (FileWriter writer = new FileWriter(folderName + "/" + num + ".json")) {
                    writer.write(json);
                }

                try (FileWriter writer = new FileWriter(folderName + "/lastId.txt")) {
                    writer.write(Integer.toString(num));
                }
                System.out.println("Quote number" + num + " registered.");
                num++;
            }

            else if (cmd.equals("list")) {
                System.out.println("----------------------");

                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (!file.getName().equals("lastId.txt")){
                            Quote quote = getQuote(file);
                            System.out.println(quote.id + " / " + quote.author + " / " + quote.content);
                        }
                    }
                }
            }

            else if (cmd.startsWith("delete")) {
                String what = cmd.substring(cmd.indexOf("=") + 1);
                File file = new File(folderName + "/" + what + ".json");
                if (file.exists()) {
                    file.delete();
                } else {
                    System.out.println("No such quote number: " + what);
                }
            }

            else if (cmd.startsWith("modify")) {
                String what = cmd.substring(cmd.indexOf("=") + 1);
                File file = new File(folderName + "/" + what + ".json");
                if (file.exists()) {
                    Quote quote = getQuote(file);
                    System.out.println("content(before) : " + quote.content);
                    System.out.print("content : ");
                    String content = sc.nextLine();
                    System.out.println("author(before) : " + quote.author);
                    System.out.print("author : ");
                    String author = sc.nextLine();

                    String json = "{\n" +
                            " \"id\": " + what + ",\n" +
                            "  \"content\": \"" + content + "\",\n" +
                            "  \"author\": \"" + author + "\"\n" +
                            "}";
                    try (FileWriter writer = new FileWriter(folderName + "/" + what + ".json")) {
                        writer.write(json);
                    }

                } else {
                    System.out.println("No such quote number: " + what);
                }
            }
        }


    }

    public static Quote getQuote(File file) {
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

        return new Quote(Integer.parseInt(id), content, author);
    }
}