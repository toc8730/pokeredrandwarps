package com.pokered.redmaprando;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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
        ArrayList<String> warpNames = new ArrayList<String>();
        String ln;
        for (File mapObject : mapObjects.listFiles()) {
            try {
                Scanner reader = new Scanner(mapObject);
                while (ln.substring(0, 13) != "\tdef_warps_to") // will loop until it finds the definition of the warp name
                    ln = reader.nextLine();
                warpNames.add(ln.substring(15)); // adds the "warps_to" token to the list
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(warpNames); // randomize
        for (File mapObject : mapObjects.listFiles()) {
            try {
                FileWriter writer = new FileWriter(mapObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
