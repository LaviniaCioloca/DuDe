//This file is part of the MeMoJC library and is protected by LGPL.

package lrg.memoria.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Location implements Serializable {
    private static Location unknownLocation;
    public static String unknow_file_name = "unknown_file";

    public static Location getUnknownLocation() {
        if (unknownLocation == null) {
            unknownLocation = new Location(File.getUnknownFile());
            unknownLocation.setStartLine(-1);
            unknownLocation.setEndLine(-1);
            unknownLocation.setStartChar(-1);
            unknownLocation.setEndChar(-1);
        }
        return unknownLocation;
    }

    static ArrayList<Location> extractLocationsWithin(Location range,ArrayList<Location> instances) {
        ArrayList<Location> inst=new ArrayList<Location>();
        for (Iterator<Location> itl=instances.iterator();itl.hasNext();) {
            Location l=itl.next();
            if (range.contains(l)) {
                inst.add(range.relativeOf(l));
                itl.remove();
            }
        }
        return inst;
    }

    /**
     *
     * @param range
     * @param instances
     * @return 0 if all of instances are outside range
     *          -1 if at least 1 of instances intersect location but are not fully contained
     *          1 if at least 1 of instances are fully contained and all others are outside
     *          (not intersecting) range
     *
     */
    static int checkLocationsWithin(Location range,ArrayList<Location> instances) {
        int res=0;
        for (Iterator<Location> itl=instances.iterator();itl.hasNext();) {
            Location l=itl.next();
            if (range.contains(l)) {
                res=1;
            } else if (range.intersects(l))
                return -1;
        }
        return res;
    }

    /**
     *
     * @param range
     * @param instances
     * @return 0 if none of instances contain range
     *      -1 if at least 1 intersects range
     *      1 if at least 1 containes range and all others do not intersect range
     */
    static int checkIfAnyContain(Location range,ArrayList<Location> instances) {
        int res=0;
        for (Iterator<Location> itl=instances.iterator();itl.hasNext();) {
            Location l=itl.next();
            if ((l.contains(range))) {
                res=1;
            } else if (range.intersects(l))
                return -1;
        }
        return res;
    }

    static void shiftLocations(int lines,ArrayList<Location> instances) {
        for (Location l:instances) {
            l.startLine+=lines;
            l.endLine+=lines;
        }
    }
    /*--------------------------------------------------------*/

    private File file;
    private int startLine, startChar, endLine, endChar;

    public Location(File file) {
        this.file = file;
    }    

    public File getFile() {
        return file;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }

    public int getStartChar() {
        return startChar;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndChar(int endChar) {
        this.endChar = endChar;
    }

    public int getEndChar() {
        return endChar;
    }

    public Location relativeOf(int absLine,int absChar) {
        Location nl=new Location(file);
        nl.setStartLine(absLine-startLine);
        if (absLine!=startLine)
            nl.setStartChar(absChar);
         else
            nl.setStartChar(absChar-startChar);

        nl.setEndLine(Integer.MIN_VALUE);
        nl.setEndChar(Integer.MIN_VALUE);
        return nl;
    }

    public Location absoluteOf(int relLine,int relChar) {
        Location nl=new Location(file);
        nl.setStartLine(startLine+relLine);
        if (relLine!=0)
            nl.setStartChar(relChar);
         else
            nl.setStartChar(startChar+relChar);

        nl.setEndLine(Integer.MIN_VALUE);
        nl.setEndChar(Integer.MIN_VALUE);
        return nl;
    }

    public Location relativeOf(Location abs) {
        Location nl=relativeOf(abs.getStartLine(),abs.getStartChar());
        Location temp=relativeOf(abs.getEndLine(),abs.getEndChar());
        nl.setEndLine(temp.getStartLine());
        nl.setEndChar(temp.getStartChar());
        return nl;
    }

    public Location absoluteOf(Location rel) {
        Location nl=absoluteOf(rel.getStartLine(),rel.getStartChar());
        Location temp=absoluteOf(rel.getEndLine(),rel.getEndChar());
        nl.setEndLine(temp.getStartLine());
        nl.setEndChar(temp.getStartChar());
        return nl;
    }


    boolean startsAfter(int line,int col) {
        return (line < startLine) || ((line == startLine) && (col < startChar));
    }
    /**
     * the line/col param is within the range of this location (margins don't count)
     */
    private boolean includes(int line,int col) {
        return ((line > startLine) && (line < endLine)) ||
               ((line > startLine) && (line == endLine) && (col < endChar)) ||
               ((line == startLine) && (line < endLine) && (col > startChar)) ||
               ((line == startLine) && (line == endLine) && (col > startChar) && (col < endChar));
    }

    public boolean includes(Location l) {
        return includes(l.startLine,l.startChar) && includes(l.endLine,l.endChar);
    }

    /**
     * the line/col param is within the range of this location (including margins)
     */
    private boolean contains(int line,int col) {
        return ((line > startLine) && (line < endLine)) ||
               ((line > startLine) && (line == endLine) && (col <= endChar)) ||
               ((line == startLine) && (line < endLine) && (col >= startChar)) ||
               ((line == startLine) && (line == endLine) && (col >= startChar) && (col <= endChar));
    }

    public boolean contains(Location l) {
        return contains(l.startLine,l.startChar) && contains(l.endLine,l.endChar);
    }

    public boolean intersects(Location l) {
        return contains(l.startLine,l.startChar) ||
               contains(l.endLine,l.endChar) ||
               l.contains(startLine, startChar); 
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(file.getFullName());
        buf.append(":").append(startLine);
        return buf.toString();
    }
}
