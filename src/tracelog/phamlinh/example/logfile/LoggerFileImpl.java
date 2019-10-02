package tracelog.phamlinh.example.logfile;

import tracelog.phamlinh.example.console.ConsoleLogImpl;

public class LoggerFileImpl extends WorkWithFile implements LoggerFile {

	private static ConsoleLogImpl console = new ConsoleLogImpl();

	@Override
	public <E> void logFileError(String error, @SuppressWarnings("unchecked") E... arg) {
		String content = console.getLogError(error, arg);
		this.writeToFile(this.nameOfLogFile(), this.logFolder, content, true);
	}

	@Override
	public <E> void logFileWarning(String warning, @SuppressWarnings("unchecked") E... arg) {
		String content = console.getLogError(warning, arg);
		this.writeToFile(this.nameOfLogFile(), this.logFolder, content, true);
	}

	@Override
	public <E> void logFileInfor(String infor, @SuppressWarnings("unchecked") E... arg) {
		String content = console.getLogError(infor, arg);
		this.writeToFile(this.nameOfLogFile(), this.logFolder, content, true);
	}

}
