package busterGUI;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GenerateTestCase {

	private Analyzer a;
	private ArrayList<String> files;
	private Scanner s;
	private ArrayList<String> list1, list2;
	private ArrayList<Reader> readers;
	private ArrayList<FuncDivider> fd;
	private Hashtable<String, ArrayList<String>> funcJStable;

	public GenerateTestCase() {
		a = new Analyzer();
		files = new ArrayList<String>();
		list1 = new ArrayList<String>();// libteki funtionlar
		list2 = new ArrayList<String>();// test filedaki testcaseler
	}

	public void checktestCase() throws IOException {
		files = a.getFileList();
		list1 = a.getFileList();
		String path = "./test";
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				if (files.endsWith("-test.js") || files.endsWith("-test.JS")) {
					list2.add(files);
				}
			}
		}

		ArrayList<String> wantedtestcase = new ArrayList<String>();

		int size1, size2, flag;
		String[] parts;
		String part1 = null;

		for (size1 = 0; size1 < list1.size(); size1++) {
			flag = 0;

			parts = list1.get(size1).split("\\.");

			part1 = parts[0];
			part1 = part1 + "-test.js";

			for (size2 = 0; size2 < list2.size(); size2++) {

				if (part1.equals(list2.get(size2))) {
					flag = 1;
					break;
				}
			}
			if (flag == 0) {

				wantedtestcase.add(list1.get(size1));
			}
		}

		fd = new ArrayList<FuncDivider>();
		funcJStable = new Hashtable<String, ArrayList<String>>();
		readers = new ArrayList<Reader>();
		for (int i = 0; i < wantedtestcase.size(); i++) {
			readers.add(new Reader("./lib/" + wantedtestcase.get(i)));

			fd.add(new FuncDivider(readers.get(i).read()));
			funcJStable.put(wantedtestcase.get(i), fd.get(i).divide());

		}
		@SuppressWarnings("rawtypes")
		Enumeration names;
		String str;
		names = funcJStable.keys();
		while (names.hasMoreElements()) {

			str = (String) names.nextElement();
			System.out.println("fileName: " + str + "\tfunctionName: "
					+ funcJStable.get(str));
		}
	}

	public Hashtable<String, ArrayList<String>> getFuncJStable() {
		return funcJStable;
	}

}