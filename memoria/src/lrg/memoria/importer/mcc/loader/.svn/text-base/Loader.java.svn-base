package lrg.memoria.importer.mcc.loader;

//import lrg.memoria.lrg.insider.lrg.insider.core.*;

import lrg.memoria.core.*;
import lrg.memoria.core.Package;
import lrg.memoria.importer.ImporterTools;

import java.util.HashMap;
import java.util.Stack;

public class Loader {
    private lrg.memoria.core.System system;
    private static Loader loader = null;

    protected HashMap<Integer, lrg.memoria.core.Package> packagesMap;
    protected HashMap<Integer, Namespace> namespacesMap;
    protected HashMap<Integer, Primitive> primitiveMap;
    protected HashMap<Integer, Body> bodiesMap;
    protected HashMap<Integer, Type> typesMap;
    protected HashMap<Integer, Variable> variablesMap;
    protected HashMap<LocalVariable, Integer> localVars2BodiesID;
    private HashMap<Integer, Type> tp2t;
    protected HashMap<Integer, Function> functionsMap;
    protected HashMap<String, File> projectFiles;
    protected HashMap<String, UnnamedNamespace> unnamedNamespaces;
    protected HashMap<FunctionBody, Package> bodyToPackageMap;

    public void setLazyFuncsScopesStack(Stack lazyFuncsScopesStack) {
        this.lazyFuncsScopesStack = lazyFuncsScopesStack;
    }

    private Stack lazyFuncsScopesStack;


    private Loader() {
        packagesMap = new HashMap<Integer, lrg.memoria.core.Package>();
        namespacesMap = new HashMap<Integer, Namespace>();
        typesMap = new HashMap<Integer, Type>();
        primitiveMap = new HashMap<Integer, Primitive>();
        bodiesMap = new HashMap<Integer, Body>();
        variablesMap = new HashMap<Integer, Variable>();
        functionsMap = new HashMap<Integer, Function>();
        localVars2BodiesID = new HashMap<LocalVariable, Integer>();
        tp2t = new HashMap<Integer, Type>();
        projectFiles = new HashMap<String, File>();
        unnamedNamespaces = new HashMap<String, UnnamedNamespace>();
        bodyToPackageMap = new HashMap<FunctionBody, Package>();

    }

    public static Loader getInstance() {
        if (loader == null) {
            loader = new Loader();
        }
        return loader;
    }

    public void setSystemName(String name) {
        Integer id = ModelElementsRepository.addNewModelElementsRepository();
        system = new lrg.memoria.core.System(name);
        system.setSystemId(id);
    }

    public void addPackage(Integer key, Package pack) {
        system.addPackage(pack);
        packagesMap.put(key, pack);
    }

    public void addNamespace(Integer key, Namespace namespace) {
        system.addNamespace(namespace);
        namespacesMap.put(key, namespace);
    }

    public Namespace getNamespace(Integer key) {
        return (Namespace) namespacesMap.get(key);
    }

    public UnnamedNamespace getUnnamedNamespace(String key) {
        UnnamedNamespace temp = unnamedNamespaces.get(key);
        int statute = Statute.FAILEDDEP;
        if (temp == null) {
            File file = projectFiles.get(key);
            if (file == null)
                file = File.getUnknownFile();
            else
                statute = Statute.NORMAL;
            temp = new UnnamedNamespace(file);
            temp.setStatute(statute);
            unnamedNamespaces.put(key, temp);
            system.addNamespace(temp);
        }
        return temp;
    }

    public void addType(Integer key, Type type) {
        typesMap.put(key, type);
    }

    public Type getType(Integer key) {
        return (Type) typesMap.get(key);
    }

    public void addBody(Integer key, FunctionBody body) {
        bodiesMap.put(key, body);
    }

    public FunctionBody getBody(Integer key) {
        return (FunctionBody) bodiesMap.get(key);
    }

    public Package getPackage(Integer key) {
        return (Package) packagesMap.get(key);
    }

    public lrg.memoria.core.System getSystem() {
        return system;
    }

    public void addVariable(Integer key, Variable variable) {
        variablesMap.put(key, variable);
    }

    public Variable getVariable(Integer key) {
        return variablesMap.get(key);
    }

    public void addFunction(Integer key, Function function) {
        functionsMap.put(key, function);
    }

    public Function getFunction(Integer key) {
        return functionsMap.get(key);
    }

    public void addLocalVar2BodyMapEntry(LocalVariable localVar, Integer bodyScopeId) {
        localVars2BodiesID.put(localVar, bodyScopeId);
    }

    public void createLazyLinks() {
        //createScopeLinks();
        //createParameters2MethodLinks();
    }

    public void addTemplateParameterToType(Integer id, Type type) {
        tp2t.put(id, type);
    }

    public Type getTemplateParameterType(Integer id) {
        return tp2t.get(id);
    }

    public File getFileByName(String fileFullName) {
        String fileName = ImporterTools.getFileName(fileFullName);
        String pathName = ImporterTools.getPathName(fileFullName);
        File temp = projectFiles.get(fileFullName);
        if (temp == null) {
            temp = new File(pathName, fileName);
            projectFiles.put(fileFullName, temp);
        }
        return temp;
    }

    public void addBodyToPackage(FunctionBody currentBody, Package currentPackage) {
        bodyToPackageMap.put(currentBody, currentPackage);
    }

    public Package getPackageForBody(FunctionBody currentBody) {
        return bodyToPackageMap.get(currentBody);
    }
}
