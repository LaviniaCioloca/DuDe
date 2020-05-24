package lrg.dude.duplication.model;

import java.io.Serializable;
import java.util.List;

public class CodeFragment implements Serializable {

    private static final long serialVersionUID = 2577184349530702246L;

    private Entity entity;
    private long beginLine;
    private long endLine;
    private List<String> linesOfCleanedCode;
    private List<String> linesOfCode;

    public CodeFragment(Entity entity, long beginLine, long endLine, List<String> linesOfCleanedCode, List<String> linesOfCode) {
        this.entity = entity;
        this.beginLine = beginLine;
        this.endLine = endLine;
        this.linesOfCleanedCode = linesOfCleanedCode;
        this.linesOfCode = linesOfCode;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getEntityName() {
        return entity.getName();
    }

    public long getBeginLine() {
        return beginLine;
    }

    public long getEndLine() {
        return endLine;
    }

    public int getLength() {
        return (int) (endLine - beginLine + 1);
    }

    public List<String> getLinesOfCleanedCode() {
        return linesOfCleanedCode;
    }

    public List<String> getLinesOfCode() {
        return linesOfCode;
    }

    @Override
    public String toString() {
        final String delimiter = "\n";
        return String.join(delimiter, linesOfCode);
    }
}
