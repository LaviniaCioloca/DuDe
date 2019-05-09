package lrg.memoria.importer.recoder;

import lrg.memoria.core.CodeStripe;
import lrg.memoria.core.Location;
import lrg.memoria.core.File;

import java.util.Stack;

import recoder.java.ProgramElement;
import recoder.java.JavaSourceElement;
import recoder.java.SourceElement;
import recoder.java.statement.Case;
import recoder.java.statement.Default;
import recoder.java.statement.Switch;
import recoder.java.statement.Branch;

public abstract class StripeContainerListener implements Listener {

    private Stack oldStripes;

    public StripeContainerListener() {
        oldStripes=new Stack();
    }

    private static Location startRelativeOf(CodeStripe cs,ProgramElement elem,File f) {
        Location l=new Location(f);
        if (elem!=null) {
            SourceElement jse=((JavaSourceElement)elem).getFirstElement();
            if (cs!=null) {
                Location rs=cs.getRelPosOf(
                        jse.getStartPosition().getLine(),
                        jse.getStartPosition().getColumn());
                l.setStartLine(rs.getStartLine());
                l.setStartChar(rs.getStartChar());
            } else {
                l.setStartLine(jse.getStartPosition().getLine());
                l.setStartChar(jse.getStartPosition().getColumn());
            }
        } else {
            l.setStartLine(0);
            l.setStartChar(0);
        }
        return l;
    }

    protected void setActiveStripe(CodeStripe cs,ModelRepository mr,
                                   ProgramElement stripe,
                                     ProgramElement firstInStripe,
                                     ProgramElement lastInStripe) {
        CodeStripe os=mr.getCurrentStripe();
        if (cs!=null) {
            cs.setLocation(startRelativeOf(os,stripe,mr.getCurrentFile()));

            cs.setContentLocation(startRelativeOf(cs,firstInStripe,mr.getCurrentFile()));

            endRelativeOf(os,stripe,cs.getLocation());

            endRelativeOf(cs,lastInStripe,cs.getContentLocation());
            if (os!=null)
                cs.getSourceCodeFromStripe(os);
        }
        oldStripes.push(os);
        mr.setCurrentStripe(cs);
    }

    /**
     * @see endRelativeOf(CodeStripe, ProgramElement, Location)
     */
    private static SourceElement getLastOf(SourceElement elem) {
        if (elem instanceof Case || elem instanceof Default) {
            Branch b=((Branch)elem);
            if (b.getStatementCount()>0)
                return getLastOf(b.getStatementAt(b.getStatementCount()-1));
            else
                return b;
/*        } else if (elem instanceof Switch) {
            Switch sw=((Switch)elem);
            if (sw.getBranchCount()>0) {
                Branch b=sw.getBranchAt(sw.getBranchCount()-1);
                return getLastOf(b);
            } else
                return sw;*/
        } else return elem.getLastElement();
    }


    private static void endRelativeOf(CodeStripe cs,ProgramElement elem,Location l) {
        if (elem!=null) {
            SourceElement jse=((JavaSourceElement)elem).getLastElement();
            /*
             * this is to work around bug 1195810 of recoder on SourceForge
             */
            jse=getLastOf(elem);
            // end of workaround

            if (cs!=null) {
                Location rs=cs.getRelPosOf(
                        jse.getEndPosition().getLine(),
                        jse.getEndPosition().getColumn());
                l.setEndLine(rs.getStartLine());
                l.setEndChar(rs.getStartChar());
            } else {
                l.setEndLine(jse.getEndPosition().getLine());
                l.setEndChar(jse.getEndPosition().getColumn());
            }
        } else {
            l.setEndLine(0);
            l.setEndChar(0);
        }
    }
    protected void restoreStripe(ModelRepository mr) {
        mr.setCurrentStripe((CodeStripe)oldStripes.pop());
    }

}
