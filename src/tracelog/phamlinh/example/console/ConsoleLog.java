package tracelog.phamlinh.example.console;

public interface ConsoleLog {
	/**
	 * 
	 * @param warning
	 * @param arg
	 */
	public <E> void logWarning(String warning, E... arg);

	/**
	 * 
	 * @param infor
	 * @param arg
	 */
	public <E> void logInfo(String infor, E... arg);

	/**
	 * 
	 * @param error
	 * @param arg
	 */
	public <E> void logError(String error, E... arg);

	/**
	 * 
	 * @param error
	 * @param arguments
	 * @return
	 */
	public <E> String getLogError(String error, E... arguments);

	/**
	 * 
	 * @param warning
	 * @param arguments
	 * @return
	 */
	public <E> String getLogWarning(String warning, E... arguments);

	/**
	 * 
	 * @param infor
	 * @param arguments
	 * @return
	 */
	public <E> String getLogInfor(String infor, E... arguments);

}
