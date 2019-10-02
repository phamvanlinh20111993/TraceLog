package tracelog.phamlinh.example.logfile;

public interface LoggerFile {
	/**
	 * 
	 * @param content
	 * @param arg
	 */
	public <E> void logFileError(String content, @SuppressWarnings("unchecked") E... arg);
	
	/**
	 * 
	 * @param content
	 * @param arg
	 */
	public <E> void logFileWarning(String content, @SuppressWarnings("unchecked") E... arg);
	
	/**
	 * 
	 * @param content
	 * @param arg
	 */
	public <E> void logFileInfor(String content, @SuppressWarnings("unchecked") E... arg);
}
