package lrg.memoria.core;

import java.util.ArrayList;

import lrg.common.abstractions.entities.AbstractEntityInterface;

public class Subsystem extends NamedModelElement {
	private ArrayList<Package> thePackages;
	
	public Subsystem(String subsystemName, ArrayList packages) {
		super(subsystemName);
		thePackages = packages;
	}

	public String getName() {
		return "subsytem_"+name;
	}
	
	public ArrayList<Package> getPackages() {
		return thePackages;
	}
	
	public void accept(ModelVisitor v) {
        v.visitSubsystem(this);
	}

}
