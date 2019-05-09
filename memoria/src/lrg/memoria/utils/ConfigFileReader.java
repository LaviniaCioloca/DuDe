package lrg.memoria.utils;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Apr 3, 2004
 * Time: 4:34:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigFileReader {
    private BufferedReader br;
    private String fileName;

    public ConfigFileReader(String fileName) {
        this.fileName = fileName;
        try {
            br = new BufferedReader(new FileReader(new File(fileName)));
        } catch(FileNotFoundException e) {
            java.lang.System.out.println("ERROR: File \"" + fileName + "\" does not exist !!!");
            e.printStackTrace();
        }
    }

    /**
     * Reads a line from the file according to the following rules:
     *      1) the lines starting with '-' are ignored
     *      2) the lines starting with '#' are simply print at the System.out
     * @return the line read
     */
    public String readLine() {
        String line = null;
        try {
            boolean bol;
            do {
                bol = false;
                line = br.readLine();
                if (line != null && line.startsWith("-"))
                    bol = true;
                if (line != null && line.startsWith("#")) {
                    bol = true;
                    System.out.println("Echo > " + line);
                }
            } while(bol);
        }
        catch(IOException e) {
            System.out.println("ERROR: reading from " + fileName);
            java.lang.System.out.println(e);
        }
        return line;
    }

    public void close() {
        try {
            br.close();
        } catch(IOException e) {
            System.out.println("ERROR: Unable to close file: " + fileName);
        }
    }
}
