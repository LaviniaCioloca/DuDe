package lrg.dude.duplication.model;

import java.io.Serializable;
import java.util.ArrayList;

public class StringList implements Serializable {

    private static final long serialVersionUID = -1166287406562295012L;
    private ArrayList<String> list = null;

    public StringList(String[] tab) {
        this();
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
    }

    public StringList() {
        list = new ArrayList<>();
    }

    public StringList(final ArrayList<String> list) {
        this.list = list;
    }

    public void add(String s) {
        list.add(s);
    }

    public void addAll(StringList aList) {
        int size = aList.size();
        for (int i = 0; i < size; i++) {
            list.add(aList.get(i));
        }
    }

    public String get(int index) {
        return list.get(index);
    }

    public void set(int i, String value) {
        list.set(i, value);
    }

    public int size() {
        return list.size();
    }

    public ArrayList<String> getList() {
        return list;
    }
}
