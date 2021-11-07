package com.pokered.redmaprando;

import java.io.*;
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
                while ((ln.length() > 13 ? ln.substring(0, 13) != "\tdef_warps_to" : true) && reader.hasNextLine()) { // will loop until it finds the definition of the warp name
                    offset += ln.length();
                    ln = reader.nextLine();
                    offset++; // to account for newline
                }
                offset += 15; // should offset it exactly at the starting pos of the token
                warpNames.add(ln.substring(15)); // adds the "warps_to" token to the list
                offsets.add(offset);
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(warpNames); // randomize
        int i = 0; // iterator of warpNames and offsets
        for (File mapObject : mapObjects.listFiles()) {
            try {
                FileWriter writer = new FileWriter(mapObject);
                writer.write(warpNames.get(i), offsets.get(i), warpNames.get(i).length()); // overwrites the "warps_to" token in the current file
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"C:\\cygwin64\\bin\\bash.exe", "-s"});
            InputStream outStream = p.getInputStream(); // normal output of the shell
            InputStream errStream = p.getInputStream(); // error output of the shell

            PrintStream ps = new PrintStream(p.getOutputStream());
            ps.println("cd " + args[0]);
            ps.println("make");
            ps.println("exit");
            ps.close();
        } catch (IOException e) {
            try {
                Process p = Runtime.getRuntime().exec(new String[]{"C:\\cygwin64\\bin\\bash.exe", "-s"});
                InputStream outStream = p.getInputStream(); // normal output of the shell
                InputStream errStream = p.getInputStream(); // error output of the shell

                PrintStream ps = new PrintStream(p.getOutputStream());
                ps.println("cd " + args[0]);
                ps.println("make");
                ps.println("exit");
                ps.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}
