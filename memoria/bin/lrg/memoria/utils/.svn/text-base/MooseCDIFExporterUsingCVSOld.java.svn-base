package lrg.memoria.utils;

import lrg.memoria.exporter.cdif.MooseCDIFExporter;

import java.io.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by IntelliJ IDEA.
 * User: daniel
 * Date: June, 2005
 * To change this template use File | Settings | File Templates.
 */

/**
 * modified files: build.sh, build.bat ,build.xml, this file!
 */


class InputStreamHandler extends Thread {
    /**
     * Stream being read
     */

    private InputStream m_stream;

    /**
     * The StringBuffer holding the captured output
     */

    private StringBuffer m_captureBuffer;

    /**
     * Constructor.
     *
     * @param captureBuffer , stream
     */

    InputStreamHandler(StringBuffer captureBuffer, InputStream stream) {
        m_stream = stream;
        m_captureBuffer = captureBuffer;

        start();
    }

    /**
     * Stream the data.
     */

    public void run() {
        try {
            int nextChar;
            while ((nextChar = m_stream.read()) != -1) {
                m_captureBuffer.append((char) nextChar);
            }
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }
}


public class MooseCDIFExporterUsingCVSOld {

    private String cvs_address = null;

    private static int beginning_day = -1;
    private static int beginning_mounth = -1;
    private static int beginning_year = -1;

    private int sampling_rate = -1;

    private String working_dir = null;

    private static String cvsRoot = null;

    private Date todayDate = null;

    public MooseCDIFExporterUsingCVSOld(String cvs_address, String beginning_date, String sampling_rate, String working_dir) {
        this.cvs_address = cvs_address;

        try {
            int firstDateSeparatorPosition = beginning_date.indexOf('/');
            int seccondDateSeparatorPosition = beginning_date.indexOf('/', firstDateSeparatorPosition + 1);

            beginning_mounth = Integer.parseInt(beginning_date.substring(0, firstDateSeparatorPosition));
            //make the preparations for the gregorian calendar
            beginning_mounth -= 1;


            beginning_day = Integer.parseInt(beginning_date.substring(firstDateSeparatorPosition + 1, seccondDateSeparatorPosition));

            if (beginning_date.length() - (seccondDateSeparatorPosition + 1) == 4)
                beginning_year = Integer.parseInt(beginning_date.substring(seccondDateSeparatorPosition + 1, beginning_date.length()));
            else
                throw new Exception("year");

        } catch (Exception excDate) {
            System.err.println("[MooseCDIFExporterUsingCVS] Bad date!\nExample: mm/dd/yyyy");
            System.exit(1);
        }

        this.sampling_rate = Integer.parseInt(sampling_rate);

        this.working_dir = working_dir;

        todayDate = new Date();
    }

    private String getTodayDate() {
        DateFormat todayDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        return todayDateFormat.format(todayDate);
    }

    public void computeData(int beginning_year, int beginning_mounth, int beginning_day) {
        GregorianCalendar cvs_co_evidence = new GregorianCalendar(beginning_year, beginning_mounth, beginning_day);

        Date cvs_co_evidence_date = cvs_co_evidence.getTime();

        while (cvs_co_evidence_date.before(todayDate)) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

            if (this.checkOutFromCVS(cvs_address, working_dir, df.format(cvs_co_evidence_date))) {
                this.convertProjectToCDIF(cvs_address, working_dir, cvs_co_evidence.get(Calendar.YEAR) + "-" + (cvs_co_evidence.get(Calendar.MONTH) + 1) + "-" + (cvs_co_evidence.get((Calendar.DATE))));
                if (!delete(working_dir)) {
                    System.out.println("[MooseCDIFExporterUsingCVS] Directory " + working_dir + " was not successfully deleted !");
                    System.exit(1);
                }
                System.out.println();   //just a dummy instruction to keep te output readble ;)
            }

            cvs_co_evidence.add(GregorianCalendar.DATE, sampling_rate);
            cvs_co_evidence_date = cvs_co_evidence.getTime();
        }
        ;
    }

    public boolean setCVSROOT(String cvsRoot) {
        System.out.println("Setting the CVSROOT=" + cvsRoot + " ...");

        try {
            Process cvsRootSetProcess = Runtime.getRuntime().exec("cvs -d " + cvsRoot + " login");

            StringBuffer inBuffer = new StringBuffer();
            InputStream inStream = cvsRootSetProcess.getInputStream();
            new InputStreamHandler(inBuffer, inStream);

            StringBuffer errBuffer = new StringBuffer();
            InputStream errStream = cvsRootSetProcess.getErrorStream();
            new InputStreamHandler(errBuffer, errStream);

            if (cvsRootSetProcess.waitFor() == 0) {
                inStream.close();
                errStream.close();
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            System.out.println("\n\n[MooseCDIFExporterUsingCVS] Error when setting CVSROOT!\n");
            return false;
        }

    }

    private boolean checkOutFromCVS(String cvs_address, String working_dir, String dateString) {
        System.out.println("Checking out from cvs the version from " + dateString + " ...");

        try {
            Process currentCVSCheckout = Runtime.getRuntime().exec("cvs -d " + cvsRoot + " checkout -D " + dateString + " -f -P -d " + working_dir + " " + cvs_address);

            StringBuffer inBuffer = new StringBuffer();
            InputStream inStream = currentCVSCheckout.getInputStream();
            new InputStreamHandler(inBuffer, inStream);

            StringBuffer errBuffer = new StringBuffer();
            InputStream errStream = currentCVSCheckout.getErrorStream();
            new InputStreamHandler(errBuffer, errStream);

            if (currentCVSCheckout.waitFor() == 0) {
                inStream.close();
                errStream.close();
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            System.out.println("\n\n[MooseCDIFExporterUsingCVS] Error when executing: cvs co for the " + cvs_address + " (" + dateString + ") !!!\n");
            return false;
        }

    }

    private void convertProjectToCDIF(String path, String working_dir, String time_stamp) {
        System.out.println("Export " + working_dir + " from " + time_stamp + " to cdif format ...");

        String cache_path = working_dir + "_" + time_stamp + ".tempCache";

        String additional_library_path = "";

        String cdif_file_name = working_dir + "_" + time_stamp + ".cdif";


        System.setOut(System.err);

        String error_file = working_dir + "_" + time_stamp + ".errLog";
        File err = new File(error_file);

        try {
            err.createNewFile();
            Logger errorLogger = new Logger(new FileOutputStream(err));
            System.setOut(errorLogger);
            System.setErr(errorLogger);

            System.err.println("Building: JavaModelLoader for source_path = " + working_dir);
            lrg.memoria.importer.recoder.JavaModelLoader model = new lrg.memoria.importer.recoder.JavaModelLoader(working_dir, cache_path, additional_library_path, null);
            lrg.memoria.core.System mySystem;
            mySystem = model.getSystem();
            MooseCDIFExporter exporter = new MooseCDIFExporter(mySystem);
            File file = new File(cdif_file_name);
            System.out.println("Writing the CDIF file for the path: " + working_dir);
            System.out.println("For further details please consult: " + error_file);
            exporter.exportToStream(new PrintStream(new FileOutputStream(file)));

            errorLogger.close();

        } catch (Exception pex) {
            System.out.println("[MooseCDIFExporterUsingCVS] Error !!!\nCDIF file generation aborted !!!");
            pex.printStackTrace();
            System.exit(6);
        }

        System.out.println("Done!");
    }

    private boolean delete(String working_dir) {
        System.out.println("Deleting the " + working_dir + " directory ...");

        return this.delete(new File(working_dir));
    }

    private boolean delete(File dir) {
        boolean success = true;

        File files[] = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isDirectory() == true) {
                    // delete the directory and all of its contents.
                    if (delete(f) == false) {
                        success = false;
                        System.out.println("[MooseCDIFExporterUsingCVS] Failed to delete dir: " + f.getAbsolutePath());
                    }
                }
                // delete each file in the directory
                else if (f.delete() == false) {
                    success = false;
                    System.out.println("[MooseCDIFExporterUsingCVS] Failed to delete file: " + f.getAbsolutePath());
                }
            }
        }


        if (dir.delete() == false) {
            success = false;
            System.out.println("Failed to delete dir: " + dir.getAbsolutePath());
        }

        return success;
    }

    public static void main(String[] args) {

        if (args.length != 4) {
            System.err.println("Usage: MooseCDIFExporterUsingCVS cvs_address beginning_date sampling_rate working_dir");
            System.exit(1);
        }

        MooseCDIFExporterUsingCVSOld mooseCDIFExporterUsingCVS = new MooseCDIFExporterUsingCVSOld(args[0], args[1], args[2], args[3]);

        System.out.println("Today is " + mooseCDIFExporterUsingCVS.getTodayDate() + ".\n");

        cvsRoot = new String(":pserver:guest@cvs.tigris.org:/cvs");

        if (mooseCDIFExporterUsingCVS.setCVSROOT(cvsRoot)) {
            mooseCDIFExporterUsingCVS.computeData(beginning_year, beginning_mounth, beginning_day);
        } else {
            System.out.println("[MooseCDIFExporterUsingCVS] Error while setting CVSROOT!\n");
            System.exit(1);
        }

    }

}
