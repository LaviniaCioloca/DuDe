package lrg.memoria.importer.recoder;

import lrg.memoria.core.DataAbstraction;
import recoder.abstraction.*;
import recoder.abstraction.Package;
import recoder.java.declaration.ParameterDeclaration;
import recoder.java.declaration.VariableSpecification;
import recoder.java.expression.operator.New;
import recoder.java.reference.*;
import recoder.java.statement.Catch;
import recoder.parser.TokenMgrError;
import recoder.service.SourceInfo;

public class ReferenceConverter {
    private static SourceInfo srcInfo = null;
    private static ModelRepository mr = null;

    static void cleanUp() {
        setSrcInfo(null);
        setMr(null);
    }

    public static lrg.memoria.core.Package getPackage(PackageReference pr) {
        Package pack = getSrcInfo().getPackage(pr);
        String packageName;
        if (pack == null) {
            //FailedDep --- THIS is not TRUE !!!!
            packageName = pr.getName();
        } else {
            // Normal or BrokenLibraryLimitations
            packageName = pack.getFullName();
        }
        lrg.memoria.core.Package mmmp = getMr().addPackage(pack, packageName);
        return mmmp;
    }

    public static lrg.memoria.core.Class getClassType(TypeReference tr) {
        lrg.memoria.core.Class mmmc;
        ClassType ct = null;
        try {
            ct = (ClassType) getSrcInfo().getType(tr);
        } catch (Exception e) { //added for Failed Dependencies
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Failed Dependency Error; Exception occured while getting the ClassType\"" + tr.getName() + "\" from TypeReference");
        } catch (TokenMgrError e) {
            java.lang.System.out.println(e);
            e.printStackTrace();
        }
        if (ct == null) {
            // FailedDep
            mmmc = getMr().addClass(tr, tr.getName());
        } else
            mmmc = getMemoriaClass(ct);
        return mmmc;
    }

    public static lrg.memoria.core.Class getMemoriaClass(ClassType ct) {
        lrg.memoria.core.Class mmmc;

        // Normal or BrokenLibraryLimitations
        //StringBuffer getName = new StringBuffer(ct.getName());
        ClassType scope = ct.getContainingClassType();
        mmmc = getMr().getClass(ct);

        if (scope != null && (scope.getName().compareTo("<unknownClassType>") !=0) && mmmc == null) {
            DataAbstraction classScope = getMemoriaClass(scope);
            mmmc = getMr().addClass(ct, ct.getName());
            mmmc.setScope(classScope);
            classScope.addInnerType(mmmc);
            return mmmc;
        }
        mmmc = getMr().addClass(ct, ct.getName());
        return mmmc;
    }

    public static lrg.memoria.core.Type getTypeFromTypeReference(TypeReference tr) {
        Type ct = null;
        try {
            ct = getSrcInfo().getType(tr);
        } catch (Exception e) { //added for Failed Dependencies
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Failed Dependency Error; Exception occured while getting the type\"" + tr.getName() + "\" from TypeReference");
        } catch (TokenMgrError e) {
            java.lang.System.out.println(e);
            e.printStackTrace();
        }
        if (ct == null) {
            // FailedDep
            if (tr.getName().equals("void"))
                return getMr().addPrimitiveType(tr.getName(), tr.getName());
            else {
                return getMr().addClass(tr, tr.getName());
            }
        } else
            return getType(ct);
    }

    //a new method
    public static lrg.memoria.core.Type getType(Type ct) {
        while (ct instanceof ArrayType) {
            ct = ((ArrayType) ct).getBaseType();
        }
        if (ct instanceof PrimitiveType) {
            return getMr().addPrimitiveType(((PrimitiveType) ct).getFullName(), ((PrimitiveType) ct).getName());
        } else if (ct == null) {
            return getMr().addPrimitiveType("void", "void");
        } else {
            return getMemoriaClass((ClassType) ct);
        }
    }

    public static int getArrayDimension(TypeReference tr) {
        Type ct = null;
        try {
            ct = getSrcInfo().getType(tr);
        } catch (Exception e) { //added for Failed Dependencies
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Failed Dependency ERROR: Exception while testing if \"" + tr.getName() + "\" is an array !");
        } catch (TokenMgrError e) {
            java.lang.System.out.println(e);
            e.printStackTrace();
        }
        return getArrayDimension(ct);
    }

    public static int getArrayDimension(Type ct) {
        int dimensions = 0;
        while (ct instanceof ArrayType) {
            ct = ((ArrayType) ct).getBaseType();
            dimensions++;
        }
        return dimensions;
    }

    public static lrg.memoria.core.Attribute getField(FieldReference fr) {
        Field fld = getSrcInfo().getField(fr);
        if (fld == null) {
            return getMr().addAttribute(fr, fr.getName());
        } else {
            return getMr().addAttribute(fld, fld.getName());
        }
    }

    public static lrg.memoria.core.Variable getVariable(VariableReference vr) {
        lrg.memoria.core.Variable mmmv;
        VariableSpecification var = (VariableSpecification) getSrcInfo().getVariable(vr);
        if (var == null) {
            return getMr().addLocalVar(vr, vr.getName(), getMr().getCurrentStripe());
        } else {
            if (var.getParent() instanceof ParameterDeclaration &&
                    !(var.getParent().getASTParent() instanceof Catch))
                mmmv = getMr().addParameter(var, var.getName());
            else
                mmmv = getMr().addLocalVar(var, var.getName(),null); // this means that
                // once there was a variableSpecification and now I want to get the LocalVar
                // so there should be no need for seting a scope
            return mmmv;
        }
    }

    public static lrg.memoria.core.Method getMethod(MethodReference metr) {
        Method met = null;
        try {
           met = getSrcInfo().getMethod(metr);
        } catch (Exception e) { //added for Failed Dependencies
            // System.out.println(e);
            // e.printStackTrace();
            System.out.println("Failed Dependency Error in the \"MethodReference\": " + metr.getName() + "from position: " + metr.getStartPosition());
        } catch (TokenMgrError e) {
            // java.lang.System.out.println(metr + " " + e);
            // e.printStackTrace();
        }
        if (met == null) {
            lrg.memoria.core.Method meth = getMr().addMethod(metr, metr.getName());
            return meth;
        } else {
            lrg.memoria.core.Method mmmm = getMr().addMethod(met, met.getName());
            //ToDo: add the signature as well

            /*TypeList signature = met.getSignature();
            Type currentType;
            for (int i = 0; i < signature.size(); i++) {
                currentType = signature.getTypeFromTypeReference(i);
                getMr().addType(currentType. currentType.getName());
            } */
            return mmmm;
        }
    }

    public static lrg.memoria.core.Method getConstructor(New cr) {
        Method met = null;
        	try {
        		met = getSrcInfo().getConstructor(cr);
        	} catch (Exception e) { //added for Failed Dependencies
        		System.out.println(e);
        		System.out.println("Failed Dependency Error in the \"New\" from position: " + cr.getStartPosition());
        		met = null;
        	} catch (TokenMgrError e) {
        		java.lang.System.out.println(e);
        	}
        if (met == null) {
            return getMr().addMethod(cr, cr.getTypeReference().getName());
        } else {
            lrg.memoria.core.Method mmmm = getMr().addMethod(met, met.getName());
            return mmmm;
        }
    }

    public static SourceInfo getSrcInfo() {
        if (srcInfo == null)
            srcInfo = lrg.memoria.importer.recoder.JavaModelLoader.getSourceInfo();
        return srcInfo;
    }

    public static void setSrcInfo(SourceInfo srcInfo) {
        ReferenceConverter.srcInfo = srcInfo;
    }

    public static ModelRepository getMr() {
        if (mr == null)
            mr = DefaultModelRepository.getModelRepository(null);
        return mr;
    }

    public static void setMr(ModelRepository mr) {
        ReferenceConverter.mr = mr;
    }
}

