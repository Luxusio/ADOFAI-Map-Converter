package io.luxus.adofai;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static List<String> findRecursive(String directory, String regex) {
		return findRecursive(new File(directory), regex);
	}
	
	public static List<String> findRecursive(File directory, String regex) {
		return findRecursive(directory, regex, new ArrayList<>());
	}
	
	private static List<String> findRecursive(File directory, String regex, List<String> fileList) {
		File[] fileArray = directory.listFiles();
		for(File file : fileArray) {
			if(file.isDirectory()) findRecursive(file, regex, fileList);
			if(file.getName().matches(regex)) fileList.add(file.getAbsolutePath());
		}
		return fileList;
	}
}
