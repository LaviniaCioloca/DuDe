//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import lrg.memoria.importer.recoder.DefaultModelRepository;
import lrg.memoria.core.factories.BodyFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * This class holds all the elements of all the systems (more than one) from memory.
 */
public class ModelElementsRepository implements Serializable {

    private static ModelElementsRepository currentModelElementsRepository;
    private static Hashtable<Integer, ModelElementsRepository> modelElementsRepositories = new Hashtable<Integer, ModelElementsRepository>();
    private static int numberOfModels = 0;

    public static ModelElementsRepository getCurrentModelElementsRepository() {
        return currentModelElementsRepository;
    }

    public static void setCurrentModelElementsRepository(Integer num) {
        ModelElementsRepository temp = (ModelElementsRepository) modelElementsRepositories.get(num);
        if (currentModelElementsRepository == null) {
            java.lang.System.err.println("ModelElementsRepository ERROR: The requested ModelElementsRepository with number " + num + " does not exist !");
            java.lang.System.exit(1);
        } else
            currentModelElementsRepository = temp;
    }

    public static Integer addNewModelElementsRepository() {
        numberOfModels++;
        int tmp;
        for (tmp = 0; tmp < numberOfModels; tmp++)
            if (modelElementsRepositories.get(new Integer(tmp)) == null)
                break;
        Integer currentId = new Integer(tmp);
        currentModelElementsRepository = new ModelElementsRepository();
        modelElementsRepositories.put(currentId, currentModelElementsRepository);
        return currentId;
    }

    /**
     * Deletes a specified model repository.
     *
     * @param id - the id of the ModelElementsRepository to be deleted
     * @return the id of the new current ModelElementsRepository
     */
    public static Integer deleteModelElementsRepository(Integer id) {
        ModelElementsRepository temp = (ModelElementsRepository) modelElementsRepositories.get(id);
        Integer newId;
        if (temp != null) {
            if (numberOfModels == 1) {
                currentModelElementsRepository = null;
                newId = new Integer(-1);
                numberOfModels--;
                modelElementsRepositories.remove(id);
                return newId;
            }
            if (temp == currentModelElementsRepository) {
                if (id.intValue() == 1) {
                    newId = new Integer(2);
                } else
                    newId = new Integer(1);
            } else
                newId = ((System) currentModelElementsRepository.byElementID(new Long(0))).getSystemId();
            modelElementsRepositories.remove(id);
            currentModelElementsRepository = modelElementsRepositories.get(newId);
            numberOfModels--;
            return newId;
        }
        return ((System) currentModelElementsRepository.byElementID(new Long(0))).getSystemId();
    }

    /**
     * Used only for serialization.
     */
    static int addNewModelElementsRepository(ModelElementsRepository mer) {
        int id = ++numberOfModels;
        currentModelElementsRepository = mer;
        modelElementsRepositories.put(new Integer(id), currentModelElementsRepository);
        return id;
    }

    public static void cleanUp() {
        modelElementsRepositories = null;
        currentModelElementsRepository = null;
    }


/* --------------------------------------------------------------------- */

    private long elementCount = 0;
    private HashMap<Long, ModelElement> modelElemets = new HashMap<Long, ModelElement>();
    private HashMap<String, Long> allClasses = new HashMap<String, Long>(); 
    private ModelElementsRepository() {

    }

    public ModelElement byElementID(Long elementID) {
        return (ModelElement) modelElemets.get(elementID);
    }

    public HashMap<Long, ModelElement> getModelElements() {
        return modelElemets;
    }

    public void setModelElements(HashMap<Long, ModelElement> mdlElems) {
        modelElemets = mdlElems;
        elementCount = modelElemets.keySet().size();
    }

    public long getElementCount() {
        return elementCount;
    }

    public Long addElement(ModelElement me) {
        Long newID;
     /*   
        if(me instanceof lrg.memoria.core.Class) {
        	java.lang.System.out.println(">>> " + me.getName());
        	String fullClassName = ((NamedModelElement)me).getFullName();
        	if(allClasses.containsKey(fullClassName) == false) {
                newID = new Long(elementCount++);        
                modelElemets.put(newID, me);
                allClasses.put(fullClassName, newID);        		
        	}
        	else return allClasses.get(fullClassName);
        }
       */ 
        newID = new Long(elementCount++);        
        modelElemets.put(newID, me);
        return newID;
    }

    /*--------------------------------------------------------------------*/

    private Package unknownPackage;

    Package getUnknownPackage() {
        if (unknownPackage == null) {
            unknownPackage = new Package(Package.UNKNOWN_PACKAGE_NAME);
            unknownPackage.setStatute(Statute.FAILEDDEP);
        }
        return unknownPackage;
    }

    private Package anonymousPackage;

    Package getAnonymousPackage() {
        if (anonymousPackage == null) {
            anonymousPackage = new Package(Package.ANONYMOUS_PACKAGE_NAME);
            anonymousPackage.setStatute(Statute.NORMAL);
        }
        return anonymousPackage;
    }

    private Namespace unknonwnNamespace;

    public Namespace getUnknownNamespace() {
        if (unknonwnNamespace == null) {
            unknonwnNamespace = new Namespace(Namespace.UNKNOWN_NAMESPACE_NAME);
            unknonwnNamespace.setStatute(Statute.FAILEDDEP);
        }
        return unknonwnNamespace;
    }

    private Namespace anonymousNamespace;

    public Namespace getAnonymousNamespace() {
        if (anonymousNamespace == null) {
            anonymousNamespace = new Namespace(Namespace.ANONYMOUS_NAMESPACE_NAME);
            anonymousNamespace.setStatute(Statute.NORMAL);
        }
        return anonymousNamespace;
    }

    private Namespace globalNamespace;

    public Namespace getGlobalNamespace() {
        if (globalNamespace == null) {
            globalNamespace = new Namespace(Namespace.GLOBAL_NAMESPACE_NAME);
        }
        return globalNamespace;
    }

    private DataAbstraction unknownClass;

    DataAbstraction getUnknownClass() {
        if (unknownClass == null) {
            unknownClass = new Class(Class.UNKNOWN_CLASS_NAME);
            unknonwnNamespace.addType(unknownClass);
            unknownPackage.addType(unknownClass);
        }
        return unknownClass;
    }

    private DataAbstraction hierarchyRootClass;

    void setHierarchyRootClass(DataAbstraction cls) {
        hierarchyRootClass = cls;
        hierarchyRootClass.setStatute(Statute.LIBRARY);
    }

    DataAbstraction getHierarchyRootClass() {
        return hierarchyRootClass;
    }

    private GlobalFunction unknownFunction;

    Function getUnknownFunction() {
        if (unknownFunction == null) {
            unknownFunction = new GlobalFunction(Function.unknownFunctionName);
            unknonwnNamespace.addGlobalFunction(unknownFunction);
            unknownPackage.addGlobalFunction(unknownFunction);
        }
        return unknownFunction;
    }

    private Method unknownMethod;

    public Method getUnknownMethod() {
        if (unknownMethod == null) {
            unknownMethod = new Method(Method.UNKNOWN_METHOD_NAME);
            unknownMethod.setAccessMode(AccessMode.PUBLIC);
            unknownMethod.setStatute(Statute.FAILEDDEP);
            getUnknownClass().addMethod(unknownMethod);
        }
        return unknownMethod;
    }

    private TemplateParameterType unknown_type = null;

    public TemplateParameterType getUnknownTemplateParameterType() {
        if (unknown_type == null) {
            unknown_type = new TemplateParameterType(TemplateParameterType.UNKNOWN_TPT_NAME);
            unknown_type.setScope(Namespace.getUnknownNamespace());
            unknown_type.setStatute(Statute.FAILEDDEP);
        }
        return unknown_type;
    }

    private GlobalVariable unknownVariable;

    public Variable getUnknownVariable() {
        if (unknownVariable == null) {
            unknownVariable = new GlobalVariable(Variable.UNKNOWN_VARIABLE_NAME);
            unknonwnNamespace.addGlobalVariable(unknownVariable);
            unknownPackage.addGlobalVariable(unknownVariable);
        }
        return unknownVariable;
    }

    private Body unknownBody;

    public Body getUnknownBody() {
        if (unknownBody == null) {
            unknownBody = BodyFactory.getInstance().produceBody(Method.getUnknownMethod());
        }
        return unknownBody;
    }
}