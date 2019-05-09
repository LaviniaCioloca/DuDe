/*
 * Created by IntelliJ IDEA.
 * User: dan
 * Date: Jul 15, 2002
 * Time: 4:05:25 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package lrg.memoria.importer.mcc;

import lrg.common.utils.ProgressObserver;
import lrg.memoria.importer.AbstractModelLoader;
import lrg.memoria.importer.mcc.javacc.ParseException;
import lrg.memoria.importer.mcc.javacc.TablesParser;
import lrg.memoria.importer.mcc.loader.Loader;

import java.io.FileNotFoundException;

public class TablesLoader extends AbstractModelLoader {

    public TablesLoader(ProgressObserver po, String sourcePath, String cachePath) throws Exception {
        super(po);
        Loader.getInstance().setSystemName(sourcePath);
        loadModel(sourcePath, cachePath);
    }

    protected void loadModelFromSources(String path) throws FileNotFoundException {
        TablesParser tp = new TablesParser(path, loadingProgressObserver);

        try {
            tp.parseTables();
        } catch (ParseException e1) {
            System.out.println(e1);
            System.err.println("\nThe loading was aborted. An error occured while parsing.\n");
            System.exit(25);
        }         
        Loader.getInstance().createLazyLinks();
        system = Loader.getInstance().getSystem();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TablesLoader tables_path !!");
            System.exit(1);
        }

        try {
            TablesLoader tl = new TablesLoader(null, args[0], null);
            java.lang.System.out.println("Loading mcc from the path: " + args[0]);
            System.out.println(tl.getSystem());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
