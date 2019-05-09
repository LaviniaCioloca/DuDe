package lrg.memoria.importer.mcc.loader;

import lrg.memoria.core.*;
import lrg.memoria.core.Package;

public class DefaultBodyVisitor extends DefaultVisitorRoot implements BodyVisitor {
    private Integer id;
    private Package currentPackage;
    private int noDecisions;
    private int noAnd;
    private int noOr;
    private int noReturn;
    private int noCatch;
    private int noLoops;
    private int maxNesting;
    private int noStatements;
    private int noCodeLine;
    private Location location;
    private int cyclo;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocation(String fileFullName, Integer startPosition, Integer stopPosition) {
        if (fileFullName.compareTo("NULL") == 0)
            location = Location.getUnknownLocation();
        else {
            File file = Loader.getInstance().getFileByName(fileFullName);
            location = new Location(file);
            location.setStartLine(startPosition.intValue());
            location.setEndLine(stopPosition.intValue());
        }
    }

    public void setPackageId(Integer packageId) {
        currentPackage = Loader.getInstance().getPackage(packageId);
    }

    public void setNoDecisions(Integer noDecisions) {
        this.noDecisions = noDecisions.intValue();
    }

    public void setNoLoops(Integer noLoops) {
        this.noLoops = noLoops.intValue();
    }

    public void setNoAnd(Integer noAnd) {
        this.noAnd = noAnd.intValue();
    }

    public void setNoOr(Integer noOr) {
        this.noOr = noOr.intValue();
    }

    public void setCyclomaticNumber(Integer cyclo) {
        this.cyclo = cyclo.intValue();
    }

    public void setNoReturn(Integer noReturn) {
        this.noReturn = noReturn.intValue();
    }

    public void setNoCatch(Integer noCatch) {
        this.noCatch = noCatch.intValue();
    }

    public void setMaxNesting(Integer maxNesting) {
        this.maxNesting = maxNesting.intValue();
    }

    public void setNoStatements(Integer noStatements) {
        this.noStatements = noStatements.intValue();
    }

    public void setNoCodeLine(Integer noCodeLine) {
        this.noCodeLine = noCodeLine.intValue();
    }

    public void addBody() {
        FunctionBody currentBody = new FunctionBody();
        currentBody.setCodeStripe(new CodeStripe(currentBody));
       
        currentBody.setLocation(location);
        currentBody.setMaxNestingLevel(maxNesting);
        currentBody.setNumberOfDecisions(noDecisions);
        //currentBody.setNumberOfExceptions(no);
        currentBody.setNumberOfExits(noReturn);
        currentBody.setNumberOfLines(noCodeLine);
        currentBody.setNumberOfLoops(noLoops);
        currentBody.setNumberOfStatements(noStatements);
        currentBody.setCyclomaticNumber(cyclo);
        Loader.getInstance().addBody(id, currentBody);
        Loader.getInstance().addBodyToPackage(currentBody, currentPackage);
        //ToDo: number of Catch ...
    }
}
