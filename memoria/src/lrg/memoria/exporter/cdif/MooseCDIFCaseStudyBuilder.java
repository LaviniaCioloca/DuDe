package lrg.memoria.exporter.cdif;

import lrg.memoria.utils.ConfigFileReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MooseCDIFCaseStudyBuilder {

    public static void main(String[] args) {
        if (args.length != 1) {
            java.lang.System.out.println("Usage: java MooseCDIFCaseStudyBuilder config_file");
            java.lang.System.exit(1);
        }
        try {
            ConfigFileReader cfr = new ConfigFileReader(args[0]);
            String sources, cache, libraries, cdif_file, error_file;
            String mainClass = cfr.readLine();
            String classPath = cfr.readLine();
            sources = cfr.readLine();
            while (sources != null) {
                cache = cfr.readLine();
                libraries = cfr.readLine();
                cdif_file = cfr.readLine();
                error_file = cfr.readLine();

                java.lang.System.out.println("Running " + "java -classpath " + classPath + " " + mainClass + " " + sources + " " + cache + " " + libraries + " " + cdif_file + error_file);
                try {
                    Process currentCDIFExporter = Runtime.getRuntime().exec("java -Xmx350M -Xss16m -classpath " + classPath + " " + mainClass + " " + sources + " " + cache + " " + libraries + " " + cdif_file + " " + error_file);
                    InputStream stderr = currentCDIFExporter.getErrorStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(stderr));
                    String line = null;
                    while ((line = br.readLine()) != null)
                        System.out.println(line);
                    InputStream stdout = currentCDIFExporter.getInputStream();
                    br = new BufferedReader(new InputStreamReader(stdout));
                    line = null;
                    while ((line = br.readLine()) != null)
                        System.out.println(line);
                    currentCDIFExporter.waitFor();
                } catch (Throwable t) {
                    System.out.println("\n\nbububububub\n\n\n");
                }
                sources = cfr.readLine();
            }
            cfr.close();
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e);
        }
    }

}
