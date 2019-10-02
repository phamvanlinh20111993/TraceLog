package tracelog.phamlinh.example.console;

import tracelog.phamlinh.example.utils.TraceLogConstants;
import tracelog.phamlinh.example.utils.TraceLogUtils;

public class ConsoleLogImpl extends TransformDataConsole implements ConsoleLog {

	public ConsoleLogImpl() {}

	public ConsoleLogImpl(String content) {
		System.err.println(TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_YYYYMMDDHHMMSSA) + " "
				+ System.getProperty("user.dir"));
		System.err.println("\t\t\t##### Start trace log. #####\t\t\t");
		System.err.println("\t\t\t##### " + content + " #####\t\t\t");
	}

	@Override
	public <E> void logWarning(String warning, @SuppressWarnings("unchecked") E... arg) {
		String rewrite = getLogWarning(warning, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logInfo(String infor, @SuppressWarnings("unchecked") E... arg) {
		String rewrite = getLogInfor(infor, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logError(String error, @SuppressWarnings("unchecked") E... arg) {
		String rewrite = getLogError(error, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> String getLogError(String error, @SuppressWarnings("unchecked") E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " ERROR ] ".concat(formatToConsoleLog(error, argument)));
		return rewrite.toString();
	}

	@Override
	public <E> String getLogWarning(String warning, @SuppressWarnings("unchecked") E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " WARNING ] ".concat(this.formatToConsoleLog(warning, argument)));
		return rewrite.toString();
	}

	@Override
	public <E> String getLogInfor(String infor, @SuppressWarnings("unchecked") E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " INFOR ] ".concat(this.formatToConsoleLog(infor, argument)));
		return rewrite.toString();
	}

}
