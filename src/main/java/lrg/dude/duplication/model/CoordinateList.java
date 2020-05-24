package lrg.dude.duplication.model;

import java.util.ArrayList;
import java.util.List;

public class CoordinateList {
    private List<Coordinate> list = new ArrayList<Coordinate>();

    public void add(Coordinate c) {
        list.add(c);
    }

    public Coordinate get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public void remove(int index) {
        list.remove(index);
    }
}
