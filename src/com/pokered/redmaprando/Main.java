package com.pokered.redmaprando;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("Please input filepath of the decompiled ROM (pokered).");
            return;
        }
        if (args.length > 1) {
            System.out.println("Invalid input(s). Please input filepath of the decompiled ROM (pokered).");
            return;
        }
        File mapObjects = new File(args[0] + "/data/maps/objects");
        File mapHeaders = new File(args[0] + "/data/maps/headers");
        ArrayList<String> warpNames = new ArrayList<String>();
        ArrayList<String> oldWarpNames = new ArrayList<String>();
        ArrayList<StringBuilder> fileCreators = new ArrayList<StringBuilder>();
        for (File mapObject : mapObjects.listFiles()) {
            try {
                StringBuilder warpTokenRemoved = new StringBuilder();
                Scanner reader = new Scanner(mapObject);
                String ln = new String();
                System.out.println(mapObject.getPath());
                while (!ln.contains("def_warps_to")) { // will loop until it finds the definition of the warp name
                    warpTokenRemoved.append(ln + "\n");
                    ln = reader.nextLine();
                }
                warpTokenRemoved.append("\tdef_warps_to ");
                warpNames.add(ln.substring(14)); // adds the "warps_to" token to the list
                oldWarpNames.add(ln.substring(14));
                fileCreators.add(warpTokenRemoved);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(warpNames); // randomize
        int i = 0; // iterator of warpNames and offsets
        for (File mapObject : mapObjects.listFiles()) {
            try {
                FileWriter writer = new FileWriter(mapObject);
                fileCreators.get(i).append(warpNames.get(i));
                System.out.println(fileCreators.get(i).toString());
                writer.write(fileCreators.get(i).toString());
                writer.close();
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        i = 0; // reusing the iterator
        for (File mapHeader : mapHeaders.listFiles()) { // round 2, this time with the file headers
            try {
                Scanner reader = new Scanner(mapHeader);
                StringBuilder fileCreator = new StringBuilder();
                FileWriter writer = new FileWriter(mapHeader);
                String ln = new String();
                while (reader.hasNextLine()) {
                    ln = reader.nextLine();
                    fileCreator.append(ln + '\n');
                }
                reader.close();
                System.out.println(fileCreator.toString());
                writer.write(fileCreator.toString().replaceAll(oldWarpNames.get(i), warpNames.get(i)));
                writer.close();
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
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
        } catch (IOException e) {
            try {
                Process p = Runtime.getRuntime().exec(new String[]{"C:\\cygwin\\bin\\bash.exe", "-s"});
                InputStream outStream = p.getInputStream(); // normal output of the shell
                InputStream errStream = p.getInputStream(); // error output of the shell

                PrintStream ps = new PrintStream(p.getOutputStream());
                ps.println("cd " + args[0]);
                ps.println("make");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}
