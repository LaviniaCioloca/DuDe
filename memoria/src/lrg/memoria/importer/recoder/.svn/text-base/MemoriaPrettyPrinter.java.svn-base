package lrg.memoria.importer.recoder;

import recoder.java.*;
import recoder.java.statement.Switch;

import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Apr 28, 2004
 * Time: 12:06:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class MemoriaPrettyPrinter extends recoder.java.PrettyPrinter {

    static MemoriaPrettyPrinter singleton;

    public static MemoriaPrettyPrinter getMemoriaPrettyPrinter() {
        if (singleton == null)
            singleton = new MemoriaPrettyPrinter();
        singleton.reset();
        return singleton;
    }

    private MemoriaPrettyPrinter() {
        super(new StringWriter(), JavaModelLoader.getCrossReferenceServiceConfiguration().getProjectSettings().getProperties());
    }

    private Writer w;
    private void reset() {
        w=new StringWriter();
        setWriter(w);
    }

    public String getSource(ProgramElement pe) {
        pe.accept(this);
        return w.toString();
    }
}
