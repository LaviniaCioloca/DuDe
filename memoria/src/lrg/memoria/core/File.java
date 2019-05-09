//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

public class File extends NamedModelElement {
    private String pathName;
    private String fileName;
    //private System system;
    //private ModelElementList<GlobalVariable> variables;
    //private ModelElementList<GlobalFunction> functions;

    private static File unknownFile;
    public static File getUnknownFile() {
        if (unknownFile == null)
            unknownFile = new File("_UNKNOWN_PATH_", "_UNKNOWN_FILE_");
        return unknownFile;
    }

    public File(String path, String name) {
    	super(name);
        fileName = name;
        pathName = path;
        //variables = new ModelElementList<GlobalVariable>();
        //functions = new ModelElementList<GlobalFunction>();
    }

    //public void setSystem(System s) {
      //  system = s;
    //}

    //public System getSystem() {
        //return system;
//    }

    public String getFullName() {
        if (pathName.indexOf("/") > 0)
            return pathName + "/" + fileName;
        else
            return pathName + "\\" + fileName;
    }

    public String getName() {
        return fileName;
    }

  /*  public Scope getScope() {
        return this;
    }

    public void addVariable(GlobalVariable gv) {
        variables.add(gv);
    }

    public ModelElementList<GlobalVariable> getVariablesList() {
        return variables;
    }

    public void addFunction(GlobalFunction gf) {
        functions.add(gf);
    }

    public ModelElementList<GlobalFunction> getFunctionsList() {
        return functions;
    }
    */

    public void accept(ModelVisitor v) {
        v.visitFile(this);
    }

    public String toString() {
        return new String(getFullName());
    }
    
    boolean restore() {
        if (super.restore()) {
            if (bugs != null)
            	bugs.restore();
            return true;
        }
        return false;
    }
}
