package com.pokered.redmaprando;

import java.io.File;
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please input filepath of the decompiled ROM (pokered).");
            return;
        }
        if (args.length > 1) {
            System.out.println("Invalid input(s). Please input filepath of the decompiled ROM (pokered).");
            return;
        }
        File mapObjects = new File(args[0] + "/data/maps/objects");
        for (File mapObject : mapObjects.listFiles()) {

        }
    }
}
