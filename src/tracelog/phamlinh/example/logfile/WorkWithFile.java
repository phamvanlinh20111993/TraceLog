package tracelog.phamlinh.example.logfile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import tracelog.phamlinh.example.utils.TraceLogConstants;
import tracelog.phamlinh.example.utils.TraceLogUtils;
import tracelog.phamlinh.example.utils.TraceLogUtils.StringUtils;

public class WorkWithFile {

	private File file;
	protected String logFolder;

	/**
	 * 
	 */
	public WorkWithFile() {
		this.file = new File(this.getCurrentPath());

		String pathRoot = this.getCurrentPath();
		pathRoot = this.createFolder(TraceLogConstants.FOLDER_STORAGE_LOG, pathRoot);
		this.logFolder = pathRoot;

		String nameLogFile = nameOfLogFile();
		this.createFile(nameLogFile, pathRoot);

		this.writeToFile(nameLogFile, pathRoot,
				" " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_YYYYMMDDHHMMSSA) + " "
						+ System.getProperty("user.dir"),
				true);
		this.writeToFile(nameLogFile, pathRoot, "\t\t\t##### Start trace log. #####\t\t\t", true);

		List<String> projectStructure = getTreeStructureFile(this.getCurrentPath());
		this.createFile(TraceLogConstants.LOG_PROJECT_STRUCTURE, pathRoot);
		this.writeToFile(TraceLogConstants.LOG_PROJECT_STRUCTURE, pathRoot,
				projectStructure.toArray(new String[projectStructure.size()]), false);
	}

	/**
	 * 
	 * @return
	 */
	protected String nameOfLogFile() {
		return StringUtils.StringformatDate(TraceLogConstants.LOG_FILE_FORMAT);
	}

	/**
	 * 
	 * @param name
	 * @param path
	 * @return
	 */
	protected String createFile(String name, String path) {
		String pathName = path.concat(name + ".txt");
		this.file = new File(pathName);
		// Create the file
		try {
			if (file.createNewFile()) {
				System.out.println("File " + pathName + " is created!");
			}
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
		}

		return pathName;
	}

	/**
	 * 
	 * @param folder
	 * @param path
	 * @return
	 */
	protected String createFolder(String folder, String path) {
		String pathName = path + folder;
		this.file = new File(pathName);
		try {
			if (file.mkdirs()) {
				System.out.println("Folder " + pathName + " is created");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		return pathName;
	}

	/**
	 * 
	 * @param path
	 * @param level
	 * @return
	 */
	private List<String> getTreeStructureFile(String path) {
		List<String> listFile = new ArrayList<String>();
		Stack<String> preventRecursive = new Stack<>();
		Stack<Integer> preventRecursiveLevel = new Stack<>();

		preventRecursive.add(path);
		preventRecursiveLevel.add(1);

		while (preventRecursive.size() > 0) {
			String currentPath = preventRecursive.pop(), space = "";
			int level = preventRecursiveLevel.pop();

			for (int i = 0; i < currentPath.length(); i++)
				space = space.concat("*");

			if (level == 1) {
				listFile.add(level + ", " + currentPath);
			} else {
				String[] stringList = currentPath.split("\\\\");
				listFile.add(level + ", "
						+ space.substring(0, space.length() - stringList[stringList.length - 1].length() - 1)
						+ stringList[stringList.length - 1] + "\\");
			}

			File file = new File(currentPath);
			String[] list = file.list();

			for (String name : list) {
				File fileTmp = new File(currentPath.concat("\\" + name));
				if (!fileTmp.isHidden()) {
					if (fileTmp.isDirectory()) {
						preventRecursiveLevel.push(level + 1);
						preventRecursive.push(currentPath.concat("\\" + name));
					} else {
						listFile.add((level + 1) + ", " + space + name);
					}
				}
			}
		}

		return listFile;
	}

	/**
	 * 
	 * @param name
	 * @param data
	 */
	protected void writeToFile(String name, String path, String data, boolean isAppend) {

		String pathName = path + name + ".txt";
		this.file = new File(pathName);

		if (file.exists() && !file.isDirectory()) {
			// do something
			FileWriter writer;
			try {
				writer = new FileWriter(file, isAppend);
				writer.write("\n");
				writer.write(data);
				writer.write("\n");
				// out.newLine();
				writer.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param name
	 * @param data
	 */
	protected void writeToFile(String name, String path, String[] datas, boolean isAppend) {

		String pathName = path + name + ".txt";
		this.file = new File(pathName);
		if (file.exists() && !file.isDirectory()) {
			// do something
			FileWriter writer;
			try {
				writer = new FileWriter(file, isAppend);
				// BufferedWriter out = new BufferedWriter(writer);
				writer.write("\n");
				for (String data : datas) {
					writer.write(data);
					writer.write("\n");
				}
				writer.write("\n");
				// out.newLine();
				writer.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private String getCurrentPath() {
		return Paths.get("").toAbsolutePath().toString();
	}

}
