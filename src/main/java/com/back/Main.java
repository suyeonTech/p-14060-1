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
                int index2 = cmd.indexOf("=");
                String what = cmd.substring(index2 + 1);
                quotes.removeIf(arr -> what.equals(arr[0]));
                System.out.println("Quote number" + what + " deleted.");
            }
        }
    }
}