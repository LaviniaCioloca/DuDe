package lrg.memoria.exporter.cdif;

import lrg.memoria.core.*;
import lrg.memoria.core.Class;
import lrg.memoria.core.Package;
import lrg.memoria.core.System;
import lrg.memoria.utils.Logger;
import lrg.memoria.utils.MEMORIABreadthIterator;
import lrg.common.abstractions.entities.AbstractEntity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MooseCDIFExporter extends ModelVisitor {
    private static final int PACKAGE = 0;
    private static final int NAMESPACE = 0;
    private lrg.memoria.core.System system;
    private PrintStream os;
    private long counter;
    private ArrayList additionalPackages;

    public MooseCDIFExporter(System sys) {
        system = sys;
        counter = ModelElementsRepository.getCurrentModelElementsRepository().getElementCount();
        additionalPackages = new ArrayList();
    }

    public void exportToStream(PrintStream os) {
        this.os = os;
        computeDuplication();
        printCDIFHeader();
        Iterator it = new MEMORIABreadthIterator(system);
        for (; it.hasNext();)
            ((ModelElement) it.next()).accept(this);
        printCDIFFooter();
    }

    private void computeDuplication() {
        // java.lang.System.out.println("Compute Duplication...");

		// new DudeTool().runDude(system);
    }
    private void printCDIFHeader() {
        java.lang.System.out.println("Writing Header...");        
        os.println("CDIF, SYNTAX \"SYNTAX.1\" \"02.00.00\", ENCODING \"ENCODING.1\" \"01.05.04\"");
        os.println("(:HEADER");
        os.println("\t(:SUMMARY ");
        os.println("\t\t(ExporterName  \"MEMORIA\")");
        os.println("\t\t(ExporterVersion  \"1.00\")");
        Calendar cld = Calendar.getInstance();
        os.println("\t\t(ExporterDate  \"" + cld.get(Calendar.YEAR) + "//" + cld.get(Calendar.MONTH) + "//" + cld.get(Calendar.DAY_OF_MONTH) + "\")");
        os.println("\t\t(ExporterTime  \"" + cld.get(Calendar.HOUR) + "//" + cld.get(Calendar.MINUTE) + "//" + cld.get(Calendar.SECOND) + "\")");
        os.println("\t\t(PublisherName  \"Unknown\")");
        os.println("\t)");
        os.println(")");

        os.println("(:META-MODEL");
        os.println("\t(:SUBJECTAREAREFERENCE Foundation");
        os.println("\t\t(:VERSIONNUMBER \"01.00\") ");
        os.println("\t)");
        os.println("\t(:SUBJECTAREAREFERENCE MEEMORIA");
        os.println("\t\t(:VERSIONNUMBER \"1.0\")");
        os.println("\t)");
        os.println(")");

        os.println("(:MODEL");
    }

    private void printCDIFFooter() {
        os.println(")");
    }

    private String printScope(String packageName, ModelElementList allPackages) {
        String containerPackageName = "";
        int indexOfLastScopeSeparator = packageName.lastIndexOf("::");
        if (indexOfLastScopeSeparator <= 0) return containerPackageName;

        containerPackageName = packageName.substring(0, indexOfLastScopeSeparator);
        String containedNameInModel = containerPackageName.replaceAll("::", ".");

        for (Iterator it = allPackages.iterator(); it.hasNext();)
            if (((ModelElement) it.next()).getName().compareTo(containedNameInModel) == 0)
                return containerPackageName;

        for (Iterator it = additionalPackages.iterator(); it.hasNext();)
            if (((String) it.next()).compareTo(containerPackageName) == 0)
                return containerPackageName;

        additionalPackages.add(containerPackageName);
        String furtherContainerName = printScope(containerPackageName, allPackages);

        String aName = allPackages.get(0).getClass().getName();
        aName = aName.substring(aName.lastIndexOf(".") + 1);
        os.println("(" + aName + " " + counter++);
        os.println("\t(name \"" + getSimplePackageName(containerPackageName) + "\")");
        os.println("\t(statute " + 77 + " )");
        if (furtherContainerName.length() > 0)
            os.println("\t(packagedIn \"" + furtherContainerName + "\")");
        os.println(")");
        os.println();


        return containerPackageName;
    }

    private String getSimplePackageName(String fullname) {
        int index = fullname.lastIndexOf("::");
        if (index <= 0) return fullname;
        return fullname.substring(index + 2);
    }

/*    public void visitPackage(Package p) {
        String packageName = p.getName().replaceAll("\\Q.\\E", "::");

        ModelElementList allPackages = p.getSystem().getPackages();
        String containerName = printScope(packageName, allPackages);
        os.println("(Package " + p.getElementID());
        os.println("\t(name \"" + getSimplePackageName(packageName) + "\")");
        os.println("\t(statute " + p.getStatute() + " )");
        if (containerName.length() > 0)
            os.println("\t(packagedIn \"" + containerName + "\")");
        os.println(")");
        os.println();
    }*/

    public void visitNamespace(Namespace nspace) {
        // if (nspace.getAbstractDataTypes().size() < 1) return;
        String namespaceName = nspace.getName().replaceAll("\\Q.\\E", "::");

        ModelElementList allNamespaces = nspace.getSystem().getNamespaces();
        String containerName = printScope(namespaceName, allNamespaces);
        os.println("(Namespace " + nspace.getElementID());
        os.println("\t(name \"" + getSimplePackageName(namespaceName) + "\")");
        os.println("\t(statute " + nspace.getStatute() + " )");
        if (containerName.length() > 0)
            os.println("\t(belongsTo \"" + containerName + "\")");
        os.println(")");
        os.println();

    }

    public void visitClass(Class c) {
        Scope temp = c.getScope();
        while (!(temp instanceof Namespace)) temp = temp.getScope();
        int len = ((Namespace) temp).getFullName().length();
        os.println("(Class " + c.getElementID());
        os.println("\t(name \"" + c.getFullName().substring(len + 1) + "\")");
        os.println("\t(uniqueName \"" + convertFullClassNameToCDIF(c.getFullName()) + "\")");
        os.println("\t(belongsTo \"" + convertFullClassNameToCDIF(((Namespace) temp).getFullName()) + "\")");
        //os.println("\t(packagedIn \"" + convertFullClassNameToCDIF(c.getPackage().getFullName()) + "\")");
        os.println("\t(isAbstract -" + new Boolean(c.isAbstract()).toString().toUpperCase() + "- )");
        os.println("\t(isInterface -" + new Boolean(c.isInterface()).toString().toUpperCase() + "- )");
        os.println("\t(statute " + c.getStatute() + " )");
        os.println("\t(file_name \"" + c.getLocation().getFile().getFullName() + "\")");
        os.println("\t(start_line " + c.getLocation().getStartLine() + " )");
        os.println("\t(end_line " + c.getLocation().getEndLine() + " )");
        if (c.getStatute() != Statute.NORMAL)
            os.println("\t(stub -TRUE- )");

        ArrayList<String> propertComputers = c.getEntityType().nameAllPropertyComputers();
        for (String propertyName : propertComputers) {
            Object result = c.getProperty(propertyName).getValue();
            if (result instanceof Double) {
                double doubleResult = ((Double) result).doubleValue();
                printClassMeasurement(c, propertyName.replace('#','_'), doubleResult);
            }
        }

        printClassMeasurement(c, "WLOC", compute(c, "WLOC"));
        printClassMeasurement(c, "WNOS", compute(c, "WNOS"));
        printClassMeasurement(c, "WNOCond", compute(c, "WNOCond"));
        printClassMeasurement(c, "WNOCmts", compute(c, "WNOCmts"));

        os.println(")");
        os.println();

        for (Iterator it = c.getAncestorsList().iterator(); it.hasNext();) {
            Object anObject = it.next();
            if (anObject instanceof Class == false) continue;

            os.println("(InheritanceDefinition " + counter++);
            os.println("\t(subclass \"" + convertFullClassNameToCDIF(c.getFullName()) + "\")");
            os.println("\t(superclass \"" + convertFullClassNameToCDIF(((DataAbstraction) anObject).getFullName()) + "\")");
            os.println(")");
            os.println();
        }
    }

    private double compute(DataAbstraction c, String metricName) {
        ModelElementList<Method> ml = c.getMethodList();
        int temp = 0;
        Body currentBody;
        for (Method currentMethod : ml) {
            currentBody = currentMethod.getBody();
            if (currentBody != null)
                temp += returnMetricValue(currentBody, metricName);
        }
        return temp;
    }

    private int returnMetricValue(Body b, String metricName) {
        if (metricName.equals("WLOC"))
            return b.getNumberOfLines();
        if (metricName.equals("WMC"))
            return b.getCyclomaticNumber();
        if (metricName.equals("WNOS"))
            return b.getNumberOfStatements();
        if (metricName.equals("WNOCond"))
            return b.getNumberOfDecisions();
        if (metricName.equals("WNOCmts"))
            return b.getNumberOfComments();
        return 0;
    }


    private void printClassMeasurement(DataAbstraction currentClass, String measurementName, double value) {
        os.println("\t(" + measurementName + " " + "\"" + value + "\")");
    }

    private void printMeasurement(String entityCDIFName, String measurementName, double value) {
        os.println("\t(" + measurementName + " " + "\"" + value + "\")");
    }
        
    public void visitMethod(Method m) {
        os.println("(Method " + m.getElementID());
        Location loc = m.getLocation();
        if (loc != null)
            os.println("\t(sourceAnchor \"" + loc.getFile().getFullName() + "\")");
        else
            os.println("\t(sourceAnchor \"" + "library" + "\")");
        os.println("\t(name \"" + m.getName() + "\")");
        String fullName = m.getFullName();
        os.println("\t(uniqueName \"" + convertFullMethodNameToCDIF(m.getFullName()) + "\")");
        os.println("\t(accessControlQualifier \"" + accessQualifierToString(m) + "\")");
        int index = fullName.substring(0, fullName.indexOf("(")).lastIndexOf(".");
        os.println("\t(signature \"" + fullName.substring(index + 1).replaceAll("\\Q.\\E", "::") + "\")");
        os.println("\t(belongsTo \"" + convertFullClassNameToCDIF(m.getScope().getFullName()) + "\")");
        os.println("\t(hasClassScope -" + new Boolean(m.isStatic()).toString().toUpperCase() + "- )");
        os.println("\t(isAbstract -" + new Boolean(m.isAbstract()).toString().toUpperCase() + "-)");
        os.println("\t(isConstructor -" + new Boolean(m.isConstructor()).toString().toUpperCase() + "-)");
        os.println("\t(isPureAccessor -" + new Boolean(isAccessor(m)).toString().toUpperCase() + "-)");

//        ArrayList<String> propertComputers = m.getEntityType().nameAllPropertyComputers();
//        for (String propertyName : propertComputers) {
//            try{
//                Object result = m.getProperty(propertyName).getValue();
//                if (result instanceof Double) {
//                    double doubleResult = ((Double) result).doubleValue();
//                    printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), propertyName, doubleResult);
//                }
//            }
//            catch(Exception e){}
//        }

        if (m.getBody() != null) {
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "LOC", m.getBody().getNumberOfLines());
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "NOS", m.getBody().getNumberOfStatements());
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "NOCond", m.getBody().getNumberOfDecisions());
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "NMAA", m.getBody().getAccessList().size());
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "NI", m.getBody().getCallList().size());
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "NOCmts", m.getBody().getNumberOfComments());
			printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "CYCLO", m.getBody().getCyclomaticNumber());
		}
        printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "PCR", (Double)m.getProperty("PCR").getValue());
        printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "SCR", (Double)m.getProperty("SCR").getValue());
        printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "_TotalUniform_", (Double)m.getProperty("#TotalUniform#").getValue());
        printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "_PartialUniform_", (Double)m.getProperty("#PartialUniform#").getValue());
        printMeasurement(convertFullMethodNameToCDIF(m.getFullName()), "_TotalNonUniform_", (Double)m.getProperty("#TotalNonUniform#").getValue());

        os.println(")");
        os.println();
    }

    public void visitAttribute(Attribute a) {
        os.println("(Attribute " + a.getElementID());
        os.println("\t(name \"" + a.getName() + "\")");
        os.println("\t(uniqueName \"" + convertFullClassNameToCDIF(a.getScope().getFullName()) + "." + a.getName() + "\")");
        os.println("\t(declaredType \"" + a.getType().getName() + "\")");
        os.println("\t(declaredClass \"" + a.getType().getName() + "\")");
        os.println("\t(belongsTo \"" + convertFullClassNameToCDIF(a.getScope().getFullName()) + "\")");
        os.println("\t(hasClassScope -" + new Boolean(a.isStatic()).toString().toUpperCase() + "- )");
        os.println("\t(accessControlQualifier \"" + attributeAccessQualifierToString(a) + "\")");
        
        ArrayList<String> propertComputers = a.getEntityType().nameAllPropertyComputers();
        for (String propertyName : propertComputers) {
            Object result = a.getProperty(propertyName).getValue();
            if (result instanceof Double) {
                double doubleResult = ((Double) result).doubleValue();
                printMeasurement((convertFullClassNameToCDIF(a.getScope().getFullName()) + "." + a.getName()), propertyName, doubleResult);
            }
        }        
        os.println(")");
        os.println();
    }

    public void visitParameter(Parameter p) {
        os.println("(FormalParameter " + p.getElementID());
        os.println("\t(name \"" + p.getName() + "\")");
        os.println("\t(uniqueName \"" + convertFullMethodNameToCDIF(p.getScope().getFullName()) + "." + p.getName() + "\")");
        os.println("\t(declaredType \"" + p.getType().getName() + "\")");
        os.println("\t(declaredClass \"" + p.getType().getName() + "\")");
        os.println("\t(belongsTo \"" + convertFullMethodNameToCDIF(p.getScope().getFullName()) + "\")");
        os.println("\t(position #d" + getParameterPosition(p) + ")");
        os.println(")");
        os.println();
    }

    public void visitGlobalFunction(GlobalFunction f) {

        os.println("(Function " + f.getElementID());
        Location loc = f.getLocation();
        os.println("\t(name \"" + f.getName() + "\")");
        String fullName = f.getFullName();
        os.println("\t(uniqueName \"" + convertFullGlobalFunctionNameToCDIF(f.getFullName()) + "\")");
        int index = fullName.substring(0, fullName.indexOf("(")).lastIndexOf(".");
        os.println("\t(signature \"" + fullName.substring(index + 1).replaceAll("\\Q.\\E", "::") + "\")");
        if (loc != null)
            os.println("\t(sourceAnchor \"" + loc.getFile().getFullName() + "\")");
        else
            os.println("\t(sourceAnchor \"" + "library" + "\")");
        os.println("\t(packagedIn \"" + convertFullClassNameToCDIF(f.getPackage().getFullName()) + "\")");
        os.println("\t(declaredType \"" + f.getReturnType().getName() + "\")");
        os.println("\t(declaredClass \"" + f.getReturnType().getName() + "\")");
        os.println("\t(accessControlQualifier \"" + "" + "\")");
        os.println("\t(belongsTo \"" + convertFullClassNameToCDIF(f.getScope().getFullName()) + "\")");


        if (f.getBody() != null) {
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "LOC", f.getBody().getNumberOfLines());
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "CYCLO", f.getBody().getCyclomaticNumber());
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "NOS", f.getBody().getNumberOfStatements());
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "NOCond", f.getBody().getNumberOfDecisions());
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "NMAA", f.getBody().getAccessList().size());
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "NI", f.getBody().getCallList().size());
			printMeasurement(convertFullMethodNameToCDIF(f.getFullName()), "NOCmts", f.getBody().getNumberOfComments());
		}
		printMeasurement("", "NOP", f.getParameterList().size());
        os.println(")");
        os.println();

    }

    public void visitLocalVar(LocalVariable l) {
        os.println("(LocalVariable " + l.getElementID());
        os.println("\t(name \"" + l.getName() + "\")");
        os.println("\t(uniqueName \"" + l.getName() + "\")");
        os.println("\t(declaredType \"" + l.getType().getName() + "\")");
        os.println("\t(declaredClass \"" + l.getType().getName() + "\")");
        if (((Method) l.belongsTo("method")) != null) {
            os.println("\t(belongsTo \"" + convertFullMethodNameToCDIF(((Method) l.belongsTo("method")).getFullName()) + "\")");
            os.println(")");
            os.println();
        } else {
            AbstractEntity tmp = l.belongsTo("global function");
            if(tmp != null)
				os.println("\t(belongsTo \"" + convertFullGlobalFunctionNameToCDIF(((GlobalFunction) tmp).getFullName()) + "\")");
            os.println(")");
            os.println();
        }
    }

    public void visitGlobalVar(GlobalVariable l) {
        os.println("(GlobalVariable " + l.getElementID());
        os.println("\t(name \"" + l.getName() + "\")");
        os.println("\t(uniqueName \"" + l.getName() + "\")");
        os.println("\t(declaredType \"" + l.getType().getName() + "\")");
        os.println("\t(declaredClass \"" + l.getType().getName() + "\")");
		AbstractEntity tmp =  l.belongsTo("namespace");
		if(tmp != null) 
			os.println("\t(belongsTo \"" + convertFullClassNameToCDIF(((Namespace) tmp).getFullName()) + "\")");
        os.println(")");
        os.println();
    }

    public void visitAccess(Access a) {
        os.println("(Access " + a.getElementID());
        Variable accessedVariable = a.getVariable();
        String accesses, accessedIn;

        if (accessedVariable instanceof GlobalVariable) {
            Namespace theNamespace = ((GlobalVariable) accessedVariable).getScope();
            accesses = theNamespace.getFullName() + "." + accessedVariable.getName();
        } else if (accessedVariable instanceof Attribute)
            accesses = convertFullClassNameToCDIF(((Attribute) accessedVariable).getScope().getFullName()) + "." + accessedVariable.getName();
        else {
            if (accessedVariable instanceof Parameter)
                accesses = convertFullMethodNameToCDIF(((Parameter) accessedVariable).getScope().getFullName()) + "." + accessedVariable.getName();
            else {
                Body body = ((LocalVariable) accessedVariable).getScope();
                if (body instanceof FunctionBody)
                    accesses = convertFullMethodNameToCDIF(((FunctionBody) body).getScope().getFullName()) + "." + accessedVariable.getName();
                else
                    accesses = convertFullClassNameToCDIF(((InitializerBody) body).getScope().getFullName()) + "." + accessedVariable.getName();
            }
        }
        os.println("\t(accesses \"" + accesses + "\")");
        Body body = a.getScope();
        if (body instanceof FunctionBody)
            accessedIn = convertFullMethodNameToCDIF(((FunctionBody) body).getScope().getFullName());
        else
            accessedIn = convertFullClassNameToCDIF(((InitializerBody) body).getScope().getFullName());
        os.println("\t(accessedIn \"" + accessedIn + "\")");
        os.println("\t(receivingClass \"" + convertFullClassNameToCDIF(a.getVariable().getType().getFullName()) + "\")");
        os.println(")");
        os.println();
    }

    public void visitCall(Call c) {
        if (c.getScope() instanceof FunctionBody && c.getFunction().getScope() != null) {
            Function scope = (Function) ((FunctionBody) c.getScope()).getScope();
            os.println("(Invocation " + c.getElementID());
            os.println("\t(invokedBy \"" + convertFullMethodNameToCDIF(scope.getFullName()) + "\")");
            String methodFullName = convertFullMethodNameToCDIF(c.getFunction().getFullName());
            String methodName = methodFullName.substring(methodFullName.lastIndexOf(".") + 1);
            os.println("\t(invokes \"" + methodName + "\")");
            os.println("\t(candidates #[" + methodFullName + "]#)");
            os.println(")");
            os.println();
        }
    }

    private String convertFullClassNameToCDIF(String fullName) {
        return fullName.replaceAll("\\Q.\\E", "::");
    }

    private String convertFullMethodNameToCDIF(String fullName) {
        int index = fullName.substring(0, fullName.indexOf("(")).lastIndexOf(".");
        String signature = fullName.substring(index + 1).replaceAll("\\Q.\\E", "::");
        return convertFullClassNameToCDIF(fullName.substring(0, index)) + "." + signature;
    }

    private String convertFullGlobalFunctionNameToCDIF(String fullName) {
        int index = fullName.substring(0, fullName.indexOf("(")).lastIndexOf(".");
        String signature = fullName.substring(index + 1).replaceAll("\\Q.\\E", "::");
        return convertFullClassNameToCDIF(fullName.substring(0, index)) + "." + signature;
    }

    private int getParameterPosition(Parameter p) {
        Function m = p.getScope();
        ArrayList pl = m.getParameterList();
        int pos = 0;
        for (int i = 0; i < pl.size(); i++)
            if (pl.get(i) != p)
                pos++;
            else
                break;
        return pos;
    }

    private String accessQualifierToString(Method m) {
        if (m.isPublic())
            return "public";
        if (m.isPrivate())
            return "private";
        if (m.isProtected())
            return "protected";
        return "package";
    }

    private String attributeAccessQualifierToString(Attribute a) {
        if (a.isPublic())
            return "public";
        if (a.isPrivate())
            return "private";
        if (a.isProtected())
            return "protected";
        return "package";
    }

    public static void main(String args[]) {
        if (args.length != 5) {
            java.lang.System.err.println("Usage: MooseCDIFExporter source_path cache_path additional_library_path cidf_file_name error_file");
            java.lang.System.exit(1);
        }

        String source_path = args[0];
        String cache_path = args[1];
        String additional_library_path = args[2];
        String cdif_file_name = args[3];
        java.lang.System.setOut(java.lang.System.err);
        String error_file = args[4];
        java.io.File err = new java.io.File(error_file);
        try {
            err.createNewFile();
            Logger errorLogger = new Logger(new FileOutputStream(err));
            java.lang.System.setOut(errorLogger);
            java.lang.System.setErr(errorLogger);
            java.lang.System.err.println("Building: JavaModelLoader for source_path = " + source_path);
            lrg.memoria.importer.recoder.JavaModelLoader model = new lrg.memoria.importer.recoder.JavaModelLoader(source_path, cache_path, additional_library_path, null);
            lrg.memoria.core.System mySystem;
            mySystem = model.getSystem();
            MooseCDIFExporter exporter = new MooseCDIFExporter(mySystem);
            java.io.File file = new java.io.File(cdif_file_name);
            java.lang.System.out.println("Writing the CDIF file for the path: " + source_path);
            exporter.exportToStream(new PrintStream(new FileOutputStream(file)));
            errorLogger.close();
        } catch (Exception pex) {
            java.lang.System.out.println("Error !!!\nCDIF file generation aborted !!!");
            pex.printStackTrace();
            java.lang.System.exit(6);
        }
    }

    private static String readLine(BufferedReader br) {
        String line = null;
        try {
            line = br.readLine();
            while (line != null && line.startsWith("-"))
                line = br.readLine();
        } catch (IOException e) {
            java.lang.System.out.println(e);
        }
        return line;
    }

    private boolean isAccessor(Method aMethod) {
        if ((!aMethod.getName().startsWith("get")) && (!aMethod.getName().startsWith("Get")) &&
                (!aMethod.getName().startsWith("set")) && (!aMethod.getName().startsWith("Set")))
            return false;

        if (aMethod.getBody() == null) return false;

        return (aMethod.getBody().getCyclomaticNumber() < 2);
    }

}

