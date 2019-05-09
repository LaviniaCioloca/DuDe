package lrg.memoria.importer.recoder;

import lrg.common.utils.ProgressObserver;
import lrg.memoria.core.DataAbstraction;
import lrg.memoria.core.ModelElementsRepository;
import lrg.memoria.importer.AbstractModelLoader;
import lrg.memoria.importer.recoder.recoder.MeMoJCCrossReferenceServiceConfiguration;
import lrg.memoria.importer.recoder.recoder.service.FailedDepErrorHandler;
import recoder.CrossReferenceServiceConfiguration;
import recoder.ParserException;
import recoder.convenience.ASTIterator;
import recoder.io.PropertyNames;
import recoder.io.SourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.service.CrossReferenceSourceInfo;
import recoder.service.NameInfo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.HashMap;

//package com.intooitus.memoria.importer.recoder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.LogManager;

import recoder.CrossReferenceServiceConfiguration;
import recoder.abstraction.ParameterizedType;
import recoder.convenience.ASTIterator;
import recoder.io.PropertyNames;
import recoder.io.SourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.service.CrossReferenceSourceInfo;
import recoder.service.NameInfo;

//import com.intooitus.memoria.core.DataAbstraction;
//import com.intooitus.memoria.core.ModelElementsRepository;
//import com.intooitus.memoria.importer.AbstractModelLoader;
//import com.intooitus.memoria.importer.recoder.recoder.MeMoJCCrossReferenceServiceConfiguration;
//import com.intooitus.metameta.utils.ProgressObserver;

public class JavaModelLoader extends AbstractModelLoader {

	protected static CrossReferenceSourceInfo sourceInfo;

	public static CrossReferenceSourceInfo getSourceInfo() {
		return sourceInfo;
	}

	public static CrossReferenceServiceConfiguration crsc;

	public static CrossReferenceServiceConfiguration getCrossReferenceServiceConfiguration() {
		return crsc;
	}

	public JavaModelLoader(String sourcePathList, String cachePath, ProgressObserver observer) throws Exception {    	
		super(observer);
		addJars(sourcePathList);
		loadModel(sourcePathList, cachePath);
		System.gc();
	}

	public JavaModelLoader(String sourcePathList, String cachePath, String libraryPathList, ProgressObserver observer) throws Exception {
		super(observer);

		addJars(sourcePathList+File.pathSeparator+libraryPathList);
		loadModel(sourcePathList, cachePath);
		System.gc();
	}

	private void addJars(String pathList) {

		String newClassPath = System.getProperty("java.class.path");
		pathList = System.getProperty("java.home") + File.pathSeparator + pathList;

		StringTokenizer st = new StringTokenizer(pathList, File.pathSeparator);
		String currentLibraryPath;
		while (st.hasMoreTokens()) {
			currentLibraryPath = st.nextToken();
			newClassPath = newClassPath + File.pathSeparator + findAllJarsFromPath(currentLibraryPath);
		}
		System.setProperty("java.class.path", newClassPath);
		// System.out.println(">> " + newClassPath);
	}
	
	public boolean isNotSymlink(File file) throws IOException {
		File canonicalFile;
		if (file.getParent() == null) {
			canonicalFile = file;
		} else {
			File canonDir = file.getParentFile().getCanonicalFile();
			canonicalFile = new File(canonDir, file.getName());
		}
		return canonicalFile.getCanonicalFile().equals(canonicalFile.getAbsoluteFile());
	}


	private String findAllJarsFromPath(String path) {
		String cp = new String();
		File f = new File(path);
		try {
			if (f.isDirectory()&&isNotSymlink(f)) {
				String[] filesName = f.list();
				String dirName = f.getAbsolutePath();
				for (int i = 0; i < filesName.length; i++) {
					String name = path + File.separator + filesName[i];
					File testFile = new File(name);
					String cfn="";
					cfn = testFile.getAbsolutePath();

					if(dirName.startsWith(cfn)) continue;

					String cp1 = findAllJarsFromPath(path + File.separator + filesName[i]);
					if (!cp1.equals(""))
						if (cp.equals(""))
							cp = cp1;
						else
							cp = cp + File.pathSeparator + cp1;
				}
			} else {
				if (f.getAbsolutePath().endsWith(".jar"))
					cp = f.getAbsolutePath();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cp;
	}

	protected void loadModelFromSources(String pathList) throws Exception {
		LogManager.getLogManager().reset();

		HashMap prefixes = new HashMap();
		StringTokenizer st = new StringTokenizer(pathList, File.pathSeparator);
		String currentClassPath = java.lang.System.getProperty("java.class.path");
		String oldClassPath = currentClassPath;
		String path = "";
		while (st.hasMoreTokens()) {
			path = st.nextToken();
			prefixes.put(path, null);
			currentClassPath = currentClassPath + File.pathSeparator + path;
		}
		java.lang.System.setProperty("java.class.path", currentClassPath);
		System.out.println("CLASSPATH=" + currentClassPath);
		JavaFilenameFilter jfltr = new JavaFilenameFilter(prefixes);

		ModelRepository mr;
		mr = DefaultModelRepository.getModelRepository(pathList);
		system = mr.getSystem();

		crsc = new MeMoJCCrossReferenceServiceConfiguration();
		crsc.getProjectSettings().setErrorHandler(new FailedDepErrorHandler());
//		InfusionFailedDepErrorHandler handler = new InfusionFailedDepErrorHandler();
//		crsc.getProjectSettings().setErrorHandler(handler);
		//crsc.getProjectSettings().setProperty(PropertyNames.OVERWRITE_PARSE_POSITIONS, "true");
		crsc.getProjectSettings().setProperty(PropertyNames.OVERWRITE_PARSE_POSITIONS, "false");
		crsc.getProjectSettings().setProperty(PropertyNames.OVERWRITE_INDENTATION, "false");

		//crsc.getProjectSettings().setProperty(PropertyNames.GLUE_INFIX_OPERATORS, "false");
		//crsc.getProjectSettings().setProperty(PropertyNames.GLUE_EXPRESSION_PARENTHESES, "false");
		//crsc.getProjectSettings().setProperty(PropertyNames.GLUE_PARAMETER_LISTS, "false");
		//crsc.getProjectSettings().setProperty(PropertyNames.GLUE_STATEMENT_BLOCKS, "false");
		//crsc.getProjectSettings().setProperty(PropertyNames.GLUE_SEQUENTIAL_BRANCHES, "false");

		SourceFileRepository sfr = crsc.getSourceFileRepository();
		sourceInfo = (CrossReferenceSourceInfo) crsc.getSourceInfo();

		List<CompilationUnit> cul = sfr.getAllCompilationUnitsFromPath(jfltr);
		if (cul.size() == 0) {
			System.err.println("This folder contains no source files!");
			throw new Exception("No files in folder");
		}

		NameInfo nameInfo = crsc.getNameInfo();
		DataAbstraction hierarchyRootClass = mr.addClass(nameInfo.getJavaLangObject(), "Object");
		lrg.memoria.core.Class.setHierarchyRootClass(hierarchyRootClass);

		CompilationUnit cu;
		ASTIterator asti = new ASTIterator();
		asti.setListener(new ModelConstructor());
		int size = cul.size();
		if (loadingProgressObserver != null)
			loadingProgressObserver.setMaxValue(size);
		for (int i = 0; i < size; i++) {
			cu = cul.get(i);
			try{
				asti.iterate(cu);
			}catch(Exception e) { 
				//System.err.println("ERROR in parsing "+cu.getDataLocation().toString().substring(5));}
//				handler.reportError(e, "ERROR in parsing "+cu.getDataLocation().toString().substring(5));
			}
			if (loadingProgressObserver != null) {
				loadingProgressObserver.increment();
			}
			if((cu != null) && (cu.getDataLocation() != null)) 
				System.err.println("File " + i + " - building model from " + cu.getDataLocation().toString().substring(5));
		}
		cleanUp(oldClassPath);
		crsc = new MeMoJCCrossReferenceServiceConfiguration(); // derived from recoder.CrossReferenceServiceConfiguration
		sfr.getAllCompilationUnitsFromPath(jfltr);   /* calls DefaultSourceFileRepository.getCompilationUnitFromLocation(locations[i]); for every location in
														DataLocation[] locations = getSearchPathList().findAll(filenameFilter) */
		//JavaCCParser.prefix = null;
		//JavaCCParser.suffix =null;// made suffix and prefix 'public' in order to be able to access them
		//ParameterizedType.clearPTypes(); // clears the hasmap Parameterized.ptypes
		//ParameterizedType.clearPTypes();
		try{
			//	JavaProgramFactory.getInstance().initialize(null);
			//	((MeMoJCDefaultCrossReferenceSourceInfo)sourceInfo).reset();
			//JavaCCParser.initialize(null)

		}
		catch (NullPointerException e){
//			handler.reportError(e);
		}
		//handler.exit();
//		ParseLogger.getParseLogger().exit();
		cul=null;
		System.gc();
		System.out.println("CLASSPATH=" + currentClassPath);

	}

	protected static void cleanUp(String oldClassPath) {
		ReferenceConverter.cleanUp();
		DefaultMetricRepository.cleanUp();
		DefaultModelRepository.cleanUp();
		ModelConstructor.cleanUp();
		crsc = null;
		sourceInfo = null;
		java.lang.System.gc();
		java.lang.System.setProperty("java.class.path", oldClassPath);
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java com.intooitus.memoria.importer.recoder.JavaModelLoader <path list> <cache>");
			return;
		}
		try {
			String sourcePathList = args[0];
			lrg.memoria.core.System system = buildModel(sourcePathList, args[1]);

			system = null;
			ModelElementsRepository.cleanUp();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static lrg.memoria.core.System buildModel(String sourcePathList, String cache) throws IOException, Exception {
		lrg.memoria.core.System system;
		Runtime runtime = Runtime.getRuntime();

		long timeBefore, memoryBefore;
		runtime.gc();
		timeBefore = System.currentTimeMillis();
		memoryBefore = runtime.totalMemory() - runtime.freeMemory();

		JavaModelLoader currentModel = new JavaModelLoader(sourcePathList, cache, null);
		system = currentModel.getSystem();

//		printStatistics(memoryBefore, system, timeBefore);

		return system;
	}

	private static void printStatistics(long memoryBefore, lrg.memoria.core.System system, long timeBefore) {
		Runtime runtime = Runtime.getRuntime();
		long timeAfter, memoryAfter, memorySize;

		timeAfter = System.currentTimeMillis();
		runtime.gc();
		memoryAfter = runtime.totalMemory() - runtime.freeMemory();
		memorySize = memoryAfter - memoryBefore;
		System.out.println("\n\nFact extraction:");
		System.out.println("\n\t - execution time = " + String.valueOf(timeAfter - timeBefore) + " milliseconds");
		System.out.println("\n\t - source model size = " + String.valueOf(memorySize) + " bytes");
		System.out.println("\n\t - number of model objects = " + String.valueOf(ModelElementsRepository.getCurrentModelElementsRepository().getElementCount()));
		runtime.gc();
	}

	protected static class JavaFilenameFilter implements FilenameFilter {
		private HashMap m_prefixes;

		public JavaFilenameFilter(HashMap pref) {
			m_prefixes = pref;
		}

		public boolean accept(File dir, String name) {
			File fi = dir;
			boolean found;
			if (dir == null || name == null || !name.endsWith(".java")) return false;

			while (!(found = m_prefixes.containsKey(fi.getPath())) && (fi = fi.getParentFile()) != null) ;
			if (found){
				return true;
			}
			else
				return false;
		}
	}
}
