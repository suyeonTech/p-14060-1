package com.back;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("== Quote app ==");
        Scanner sc = new Scanner(System.in);
        String folderName = "src/main/java/com/back/db/wiseSaying";
        File folder = new File(folderName);

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

                Quote quote = new Quote(num, content, author);

                try (FileWriter writer = new FileWriter(folderName + "/" + num + ".json")) {
                    writer.write(makeJson(quote));
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
                        if (isDataFile(file.getName())) {
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

                    quote = new Quote(Integer.parseInt(what), content, author);
                    try (FileWriter writer = new FileWriter(folderName + "/" + what + ".json")) {
                        writer.write(makeJson(quote));
                    }

                } else {
                    System.out.println("No such quote number: " + what);
                }
            }

            else if (cmd.equals("build")) {
                StringBuilder sb = new StringBuilder();
                sb.append("[\n");

                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (isDataFile(file.getName())) {
                            Quote quote = getQuote(file);
                            if (sb.charAt(sb.length() - 1) == '}') {
                                sb.append(",");
                            }
                            sb.append(makeJson(quote));
                        }
                    }
                }
                sb.append("]");
                try (FileWriter writer = new FileWriter(folderName + "/data.json")) {
                    writer.write(sb.toString());
                }
            }

        }


    }

    public static boolean isDataFile(String name) {
        return !name.equals("lastId.txt") && !name.equals("data.json");
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

    public static String makeJson(Quote quote) {
        return "{\n" +
                " \"id\": " + quote.id + ",\n" +
                "  \"content\": \"" + quote.content + "\",\n" +
                "  \"author\": \"" + quote.author + "\"\n" +
                "}";
    }
}