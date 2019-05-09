package lrg.memoria.importer.mcc.loader;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: May 11, 2004
 * Time: 10:03:38 AM
 * To change this template use Options | File Templates.
 */
public interface InheritanceVisitor {
    void setId(Integer id);
    void setDescendentId(String descendentId);
    void setParentId(String parentId);
    void setAttribute(String attribute);
    void setDepth(Integer depth);
    void addInh();
}
