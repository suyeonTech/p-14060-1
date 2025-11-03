package com.back;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== Quote app ==");
        Scanner sc = new Scanner(System.in);

        ArrayList<String[]> quotes = new ArrayList<>();

        int num = 1;

        while(true) {
            System.out.print("command) ");
            String cmd = sc.nextLine();

            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("register")) {
                System.out.print("content : ");
                String content = sc.nextLine();
                System.out.print("author : ");
                String author = sc.nextLine();

                quotes.add(new String[]{Integer.toString(num), author, content});
                System.out.println("Quote number" + num + " registered.");
                num++;
            }

            if (cmd.equals("list")) {
                System.out.println("----------------------");
                for (String[] quote : quotes) {
                    System.out.println(quote[0] + " / " + quote[1] + " / " + quote[2]);
                }
            }

            if (cmd.startsWith("delete")) {
                String what = cmd.substring(cmd.indexOf("=") + 1);
                boolean removed = quotes.removeIf(arr -> what.equals(arr[0]));

                if (removed) {
                    System.out.println("Quote number " + what + " removed.");
                } else {
                    System.out.println("No such quote number: " + what);
                }
            }

            if (cmd.startsWith("modify")) {
                String what = cmd.substring(cmd.indexOf("=") + 1);
                for (String[] quote : quotes) {
                    if (what.equals(quote[0])) {
                        System.out.println("content(before) : " + quote[2]);
                        System.out.print("content : ");
                        String content = sc.nextLine();
                        System.out.println("author(before) : " + quote[1]);
                        System.out.print("author : ");
                        String author = sc.nextLine();
                        quote[1] = author;
                        quote[2] = content;
                    }
                }
            }
        }
    }
}