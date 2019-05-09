package lrg.memoria.core;

public class PointerToFunction extends Function {

    public PointerToFunction(String name) {
        super(name);
    }

    public FunctionPointer getScope() {
        return (FunctionPointer)super.getScope();
    }

    public void accept(ModelVisitor mv) {
        mv.visitPointerToFunction(this);
    }

}
