package com.back;

import com.back.domain.Util;
import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.*;
import java.io.*;

public class App {
    public App() {

        System.out.println("== Quote app ==");

        while(true) {
            System.out.print("command) ");
            String cmd = Util.sc.nextLine();

            if (cmd.equals("exit")) {
                break;
            }

            else if (cmd.equals("build")) {
                SystemController.build();
            }

            else if (cmd.equals("register")) {
                WiseSayingController.register();
            }

            else if (cmd.equals("list")) {
                SystemController.list();
            }

            else if (cmd.startsWith("delete")) {
                WiseSayingController.delete(cmd);
            }

            else if (cmd.startsWith("modify")) {
                WiseSayingController.modify(cmd);
            }

        }

    }

}
