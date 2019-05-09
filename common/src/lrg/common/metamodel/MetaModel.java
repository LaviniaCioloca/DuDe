package lrg.common.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lrg.common.abstractions.entities.AbstractEntity;
import lrg.common.abstractions.entities.AbstractEntityInterface;
import lrg.common.abstractions.entities.GroupEntity;
import lrg.common.abstractions.entities.ResultEntity;

public class MetaModel {
    private static MetaModel theMM;
    private static String currentSourcePath;
    private ModelBuilder modelBuilder = null;
    private HashMap addressMap;

    public static MetaModel instance() {
        return theMM;
    }

    public static void createFrom(ModelBuilder builder, String sourcePath) throws Exception {
        if (theMM == null || currentSourcePath.compareTo(sourcePath) != 0) {
            currentSourcePath = sourcePath;
            theMM = new MetaModel(builder);
        }
    }
    /*
    public static void createFrom(String sourcePath, String cachePath, int projectType, ProgressObserver observer) throws Exception {
        if (theMM == null || currentSourcePath.compareTo(sourcePath) != 0) {
            currentSourcePath = sourcePath;
            theMM = new MetaModel(sourcePath, cachePath, projectType, observer);
        }
    }
    */
    public static void closeMetaModel() {
        if (theMM != null)
            theMM.modelBuilder.cleanModel();
        theMM = null;
    }


    /*
       private void initializeSAIL() {
           if (modelBuilder instanceof MemoriaModelBuilder) {
               try {
                   SAILMeMoJAdapter.getInstance().init(((MemoriaModelBuilder) modelBuilder).getCurrentSystem());
               } catch (RuntimeException e) {
                   java.lang.System.out.println("SAIL already loaded");
               }
               SAILConfiguration conf = new SAILConfiguration("sail-config.txt");

               try {
                   conf.configure();
               } catch (IOException ex) {
                   java.lang.System.out.println(ex);
               }
           }
       }
     */
    public MetaModel(ModelBuilder builder) throws Exception {
        modelBuilder = builder;
        if (modelBuilder != null) {
            modelBuilder.buildModel();
            addressMap = modelBuilder.getAddressMap();
        }
    }
    /*
    public MetaModel(String sourcePath, String cachePath, int projectType, ProgressObserver observer) throws Exception {
        if (projectType == JAVA)
            modelBuilder = new MemoriaJavaModelBuilder(sourcePath, cachePath, observer);
        if (projectType == CPP)
            modelBuilder = new MemoriaCPPModelBuilder(sourcePath, cachePath, observer);
        if (projectType == CACHE)
            modelBuilder = new MemoriaCacheModelBuilder(sourcePath, observer);
        if (projectType == HISMO)
            modelBuilder = new HismoModelBuilder(sourcePath, "hismo", observer);
        if (projectType == EXTENDED_JAVA)
            modelBuilder = new XMemoriaJavaModelBuilder(sourcePath, cachePath, observer);

        if (modelBuilder != null) {
            modelBuilder.buildModel();
            addressMap = modelBuilder.getAddressMap();
        }

        //    if (InsiderGUIMain.withSAIL()) initializeSAIL();

    }
    */
    public AbstractEntityInterface findEntityByAddress(String anAddress) {
        return (AbstractEntityInterface) addressMap.get(anAddress);
    }


    public ArrayList findAddressesThatStartWith(String prefix) {
        ArrayList matchingAddress = new ArrayList();

        Iterator addressIterator = addressMap.keySet().iterator();
        while (addressIterator.hasNext()) {
            String currentAddress = (String) addressIterator.next();
            if (currentAddress.startsWith(prefix))
                matchingAddress.add(currentAddress);
        }

        return matchingAddress;
    }

    public void addGroupToAddressMap(GroupEntity anEntity) {
        ResultEntity aResultEntity = anEntity.getProperty("Address");
        if (aResultEntity != null) {
            String tmp = (String) aResultEntity.getValue();
            addressMap.put(tmp, anEntity);
        }
    }

    public void addEntityToAddressMap(AbstractEntity anEntity) {
        ResultEntity aResultEntity = anEntity.getProperty("Address");
        if (aResultEntity != null) {
            String tmp = (String) aResultEntity.getValue();
            addressMap.put(tmp, anEntity);
        }
    }

    public String getCacheFilename() {
    	return modelBuilder.cachePath;
    }

    /*final public static int JAVA = 1;
    final public static int CPP = 2;
    final public static int CACHE = 3;
    final public static int HISMO = 4;
    final public static int EXTENDED_JAVA = 5;
    */
}
