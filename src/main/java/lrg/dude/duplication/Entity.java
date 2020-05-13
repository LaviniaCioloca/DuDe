package lrg.dude.duplication;

import java.io.Serializable;

public interface Entity extends Serializable {
    String getName();

    StringList getCode();

    int getNoOfRelevantLines(); //for clustering reasons

    void setNoOfRelevantLines(int norl);
}
