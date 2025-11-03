package com.back;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== Quote app ==");
        Scanner sc = new Scanner(System.in);

        int num = 1;

        while(true) {
            System.out.print("command) ");
            String cmd = sc.nextLine();

            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("register")) {
                System.out.print("context : ");
                String content = sc.nextLine();
                System.out.print("author : ");
                String author = sc.nextLine();
                System.out.println("Quote number" + num + " registered.");
                num++;
            }
        }
    }
}