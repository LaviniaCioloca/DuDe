package lrg.memoria.utils;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Apr 3, 2004
 * Time: 2:09:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Logger extends PrintStream {
    private PrintStream oldStdout;
    private PrintStream oldStderr;

    public Logger(OutputStream os) {
        super(os);
        oldStdout = System.out;
        oldStderr = System.err;
        System.setErr(this);
        System.setOut(this);
    }

    public void write(int b) {
        try {
            super.write(b);
            oldStdout.write(b);
        } catch (Exception e) {
            e.printStackTrace();
            setError();
        }
        super.write(b);
    }

    public void write(byte buf[], int off, int len) {
        try {
            super.write(buf, off, len);
            oldStdout.write(buf, off, len);
        } catch (Exception e) {
            e.printStackTrace();
            setError();
        }
        super.write(buf, off, len);
    }

    public void close() {
        System.setErr(oldStderr);
        System.setOut(oldStdout);
        super.close();
    }

}
