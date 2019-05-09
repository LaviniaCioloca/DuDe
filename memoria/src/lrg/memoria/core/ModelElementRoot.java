package lrg.memoria.core;

/**
 * Every element from the meta-model implements this interface.
 */
public interface ModelElementRoot {

    /**
     * Each model element can accept a visitor.
     */
    void accept(ModelVisitor v);

}
