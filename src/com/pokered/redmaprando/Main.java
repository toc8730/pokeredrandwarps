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
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        String ln = new String();
        for (File mapObject : mapObjects.listFiles()) {
            try {
                Scanner reader = new Scanner(mapObject);
                int offset = 0;
                while (ln.substring(0, 13) != "\tdef_warps_to") { // will loop until it finds the definition of the warp name
                    offset += ln.length();
                    ln = reader.nextLine();
                }
                offset += 15; // should offset it exactly at the starting pos of the token
                warpNames.add(ln.substring(15)); // adds the "warps_to" token to the list
                offsets.add(offset);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(warpNames); // randomize
        int i = 0; // iterator
        for (File mapObject : mapObjects.listFiles()) {
            try {
                FileWriter writer = new FileWriter(mapObject);
                writer.write(warpNames.get(i), offsets.get(i), warpNames.get(i).length()); // overwrites the "warps_to" token in the current file
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
