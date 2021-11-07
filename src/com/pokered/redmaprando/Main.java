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
        for (File mapObject : mapObjects.listFiles()) {
            try {
                Scanner reader = new Scanner(mapObject);
                int offset = 0;
                String ln = new String();
                System.out.println(mapObject.getPath());
                while (!ln.contains("def_warps_to")) { // will loop until it finds the definition of the warp name
                    offset += ln.length();
                    ln = reader.nextLine();
                    offset++; // to account for newline
                }
                offset += 14; // should offset it exactly at the starting pos of the token
                warpNames.add(ln.substring(14)); // adds the "warps_to" token to the list
                offsets.add(offset);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (String s : warpNames)
            System.out.println(s);
        /* Collections.shuffle(warpNames); // randomize
        int i = 0; // iterator of warpNames and offsets
        for (File mapObject : mapObjects.listFiles()) {
            try {
                RandomAccessFile writer = new RandomAccessFile(mapObject, "rw");
                writer.seek(offsets.get(i));
                writer.writeChars(warpNames.get(i)); // overwrites "warps_to" token with randomized one
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
        } */
    }
}
