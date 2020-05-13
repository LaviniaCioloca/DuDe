  
  package lrg.dude.duplication;

import java.io.*;


public class SourceFile implements Entity {

		private static final long serialVersionUID = -1935777691950363375L;
		private String fileName;
	    private StringList codelines;

	    private int noOfRelevantLines = 0;

	    /**
	     * Constructor
	     *
	     * @param file File
	     */
	    public SourceFile(File file, String shortName) {
	        fileName = shortName;
	        codelines = new StringList();
	        
//	        System.out.println(file);  
	        try {
	            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	            String linie = null;
	            while ((linie = in.readLine()) != null) {
	                codelines.add(linie);
	            }
	            in.close();
	        } catch (FileNotFoundException fe) {
	            System.out.println("Nu exista fisierul " + file + ": " + fe);
	        } catch (IOException ioe) {
	            System.out.println("Eroare citire fisier : " + ioe);
	        }
	    }

	    public String getName() {
	        return fileName;
	    }

	    public StringList getCode() {
	        return codelines;
	    }

	    public int getNoOfRelevantLines() {
	        return noOfRelevantLines;
	    }

	    public void setNoOfRelevantLines(int norl) {
	        noOfRelevantLines = norl;
	    }
	}
