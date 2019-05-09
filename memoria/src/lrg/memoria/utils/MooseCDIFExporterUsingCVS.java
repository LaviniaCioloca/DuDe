package lrg.memoria.utils;

import lrg.memoria.exporter.cdif.MooseCDIFExporter;
import org.netbeans.lib.cvsclient.Client;
import org.netbeans.lib.cvsclient.admin.StandardAdminHandler;
import org.netbeans.lib.cvsclient.command.CommandException;
import org.netbeans.lib.cvsclient.command.GlobalOptions;
import org.netbeans.lib.cvsclient.command.checkout.CheckoutCommand;
import org.netbeans.lib.cvsclient.connection.AuthenticationException;
import org.netbeans.lib.cvsclient.connection.PServerConnection;
import org.netbeans.lib.cvsclient.connection.StandardScrambler;
import org.netbeans.lib.cvsclient.event.CVSAdapter;
import org.netbeans.lib.cvsclient.event.MessageEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


class BasicListener extends CVSAdapter {
    /**
     * Stores a tagged line
     */
    private final StringBuffer taggedLine = new StringBuffer();

    /**
     * Called when the server wants to send a message to be displayed to
     * the user. The message is only for information purposes and clients
     * can choose to ignore these messages if they wish.
     *
     * @param e the event
     */
    public void messageSent(MessageEvent e) {
        String line = e.getMessage();
        PrintStream stream = e.isError() ? System.err : System.out;

        if (e.isTagged()) {
            String message = e.parseTaggedMessage(taggedLine, line);
            // if we get back a non-null line, we have something
            // to output. Otherwise, there is more to come and we
            // should do nothing yet.
            if (message != null) {
                stream.println(message);
            }
        } else {
            stream.println(line);
        }
    }
}


public class MooseCDIFExporterUsingCVS {
    private Client client = null;
    private GlobalOptions globalOptions = null;
    private String cvs_address = null;

    private static int beginning_day = -1;
    private static int beginning_mounth = -1;
    private static int beginning_year = -1;

    private int sampling_rate = -1;

    private String working_dir = null;

    private static String cvsRoot = null;

    private Date todayDate = null;

    public MooseCDIFExporterUsingCVS(String cvs_address, String beginning_date, String sampling_rate, String working_dir) {
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
            java.lang.System.err.println("[MooseCDIFExporterUsingCVS] Bad date!\nExample: mm/dd/yyyy");
            java.lang.System.exit(1);
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
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);	//in order to make the data representation unique, independent on the regional settings of the computer

            if (this.checkOutFromCVS(cvs_address, working_dir, df.format(cvs_co_evidence_date))) {
                this.convertProjectToCDIF(cvs_address, working_dir, cvs_co_evidence.get(Calendar.YEAR) + "-" + (cvs_co_evidence.get(Calendar.MONTH) + 1) + "-" + (cvs_co_evidence.get((Calendar.DATE))));
                if (!delete(working_dir)) {
                    java.lang.System.out.println("[MooseCDIFExporterUsingCVS] Directory " + working_dir + " was not successfully deleted !");
                    java.lang.System.exit(1);
                }
                System.out.println();   //just a dummy instruction to keep te output readble ;)
            }

            cvs_co_evidence.add(GregorianCalendar.DATE, sampling_rate);
            cvs_co_evidence_date = cvs_co_evidence.getTime();
        }
        ;
    }

    private PServerConnection establishConnection() throws AuthenticationException {
        PServerConnection connection = new PServerConnection();
        connection.setUserName("guest");
        connection.setEncodedPassword(StandardScrambler.getInstance().scramble("guest"));
        connection.setHostName("cvs.tigris.org");
        connection.setRepository("/cvs");
        connection.setPort(2401);
        try {
            connection.open();
        }catch(Exception e){}
        return connection;
    }

    private void createClient(PServerConnection connection) {
        client = new Client(connection, new StandardAdminHandler());
        client.getEventManager().addCVSListener(new BasicListener());
    }

    public boolean setCVSROOT(String cvsRoot) {
        System.out.println("Setting the CVSROOT=" + cvsRoot + " ...");
        globalOptions = new GlobalOptions();
        globalOptions.setCVSRoot(cvsRoot);
        return true;
    }

    private boolean checkOutFromCVS(String cvs_address, String working_dir, String dateString) {
        System.out.println("Checking out from cvs the version from " + dateString + " ...");

	/*
        int firstIndex = dateString.indexOf("/"), lastIndex = dateString.lastIndexOf("/");
        String month = dateString.substring(0, firstIndex);
        String day = dateString.substring(firstIndex + 1, lastIndex);
        String year = dateString.substring(lastIndex + 1, dateString.length());
        String CVSFormatedDate = "" + month + "/" + day + "/" + year;
	*/
        System.out.println(dateString);

        CheckoutCommand checkoutCommand = new CheckoutCommand(true, cvs_address);
        checkoutCommand.setRecursive(true);
        checkoutCommand.setCheckoutByDate(dateString);
        checkoutCommand.setPruneDirectories(true);

        try {
            createClient(establishConnection());
            client.setLocalPath(working_dir);
            client.executeCommand(checkoutCommand, globalOptions);
        } catch (CommandException e) {
            e.printStackTrace();
            return false;

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void convertProjectToCDIF(String path, String working_dir, String time_stamp) {
        System.out.println("Export " + working_dir + " from " + time_stamp + " to cdif format ...");

        String cache_path = working_dir + "_" + time_stamp + ".dat";

        String additional_library_path = "";

        String cdif_file_name = working_dir + "_" + time_stamp + ".cdif";


        java.lang.System.setOut(java.lang.System.err);

        // String error_file = working_dir+"_"+time_stamp+".errLog";
        // java.io.File err = new java.io.File(error_file);

        try {
            // err.createNewFile();
            // Logger errorLogger = new Logger(new FileOutputStream(err));
            // java.lang.System.setOut(errorLogger);
            // java.lang.System.setErr(errorLogger);

            java.lang.System.err.println("Building: JavaModelLoader for source_path = " + working_dir);
            lrg.memoria.importer.recoder.JavaModelLoader model = new lrg.memoria.importer.recoder.JavaModelLoader(working_dir, cache_path, additional_library_path, null);
            lrg.memoria.core.System mySystem;
            mySystem = model.getSystem();
            MooseCDIFExporter exporter = new MooseCDIFExporter(mySystem);
            java.io.File file = new java.io.File(cdif_file_name);
            java.lang.System.out.println("Writing the CDIF file for the path: " + working_dir);
            // java.lang.System.out.println("For further details please consult: " + error_file);
            exporter.exportToStream(new PrintStream(new FileOutputStream(file)));

            java.lang.System.out.println("Unloading System from Memory!");
            lrg.memoria.core.System.unloadSystemFromMemory(mySystem);
            // errorLogger.close();

        } catch (Exception pex) {
            java.lang.System.out.println("[MooseCDIFExporterUsingCVS] Error !!!\nCDIF file generation aborted !!!");
            pex.printStackTrace();
            java.lang.System.exit(6);
        }

        System.out.println("Done!");
    }

    private boolean delete(String working_dir) {
        java.lang.System.out.println("Deleting the " + working_dir + " directory ...");

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
                        java.lang.System.out.println("[MooseCDIFExporterUsingCVS] Failed to delete dir: " + f.getAbsolutePath());
                    }
                }
                // delete each file in the directory
                else if (f.delete() == false) {
                    success = false;
                    java.lang.System.out.println("[MooseCDIFExporterUsingCVS] Failed to delete file: " + f.getAbsolutePath());
                }
            }
        }


        if (dir.delete() == false) {
            success = false;
            java.lang.System.out.println("Failed to delete dir: " + dir.getAbsolutePath());
        }

        return success;
    }

    public static void main(String[] args) {

        if (args.length != 4) {
            java.lang.System.err.println("Usage: MooseCDIFExporterUsingCVS cvs_address beginning_date sampling_rate working_dir");
            java.lang.System.exit(1);
        }

        MooseCDIFExporterUsingCVS mooseCDIFExporterUsingCVS = new MooseCDIFExporterUsingCVS(args[0], args[1], args[2], args[3]);

        java.lang.System.out.println("Today is " + mooseCDIFExporterUsingCVS.getTodayDate() + ".\n");

        cvsRoot = new String(":pserver:guest@cvs.tigris.org:/cvs");

        if (mooseCDIFExporterUsingCVS.setCVSROOT(cvsRoot)) {
            mooseCDIFExporterUsingCVS.computeData(beginning_year, beginning_mounth, beginning_day);
        } else {
            java.lang.System.out.println("[MooseCDIFExporterUsingCVS] Error while setting CVSROOT!\n");
            java.lang.System.exit(1);
        }

    }

}
