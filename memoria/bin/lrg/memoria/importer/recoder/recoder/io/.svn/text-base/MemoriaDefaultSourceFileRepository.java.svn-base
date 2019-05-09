package lrg.memoria.importer.recoder.recoder.io;

import recoder.ServiceConfiguration;
import recoder.io.DataLocation;
import recoder.io.DefaultSourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.parser.TokenMgrError;

import java.io.FilenameFilter;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: ratiud
 * Date: Apr 21, 2004
 * Time: 12:05:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class MemoriaDefaultSourceFileRepository extends DefaultSourceFileRepository {

    public MemoriaDefaultSourceFileRepository(ServiceConfiguration sc) {
        super(sc);
    }

    public List<CompilationUnit> getAllCompilationUnitsFromPath(FilenameFilter filenameFilter) {
        DataLocation[] locations = getSearchPathList().findAll(filenameFilter);
        ArrayList<CompilationUnit> res =
                new ArrayList<CompilationUnit>(locations.length);
        CompilationUnit cu;
        for (int i = 0; i < locations.length; i++) {
            try {
                cu = getCompilationUnitFromLocation(locations[i]);
                res.add(cu);
            } catch (Exception e) {
                java.lang.System.err.println("Error in compilation unit: " + locations[i].toString());
                java.lang.System.out.println(e);
                e.printStackTrace();
            } catch (TokenMgrError e) {
                java.lang.System.err.println("Error in compilation unit: " + locations[i].toString());
                java.lang.System.out.println(e);
                e.printStackTrace();
            }
        }
        return res;
    }

}
