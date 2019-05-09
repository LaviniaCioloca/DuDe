package lrg.memoria.importer.mcc.loader;

public interface AccessVisitor {
    void setId(Integer id);
    void setBodyId(String bodyId);
    void setVarId(String varId);
    void setCounter(Integer counter);
    void addAccess();
}
