package tracelog.phamlinh.example.console;

import java.rmi.NoSuchObjectException;
import java.util.Map;
import java.util.TreeMap;

import javax.activation.UnsupportedDataTypeException;

import tracelog.phamlinh.example.object.RegexCondition;
import tracelog.phamlinh.example.utils.TraceLogConstants;
import tracelog.phamlinh.example.utils.TraceLogUtils;

public class ConsoleLogImpl implements ConsoleLog {

	public ConsoleLogImpl() {
		System.err.println(TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_YYYYMMDDHHMMSSA) + " "
				+ System.getProperty("user.dir"));
		System.err.println("\t\t\t## Start trace log. ##\t\t\t");
	}

	public ConsoleLogImpl(String content) {
		System.err.println(TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_YYYYMMDDHHMMSSA) + " "
				+ System.getProperty("user.dir"));
		System.err.println("\t\t\t## Start trace log. ##\t\t\t");
		System.err.println("\t\t\t## " + content);
	}

	/**
	 * 
	 * @param string
	 * @param key
	 * @return
	 */
	private Map<Integer, RegexCondition> getPrefixListOnString(String string) {
		Map<Integer, RegexCondition> getPosition = new TreeMap<Integer, RegexCondition>();
		Map<Integer, RegexCondition> temp = new TreeMap<Integer, RegexCondition>();
		// key value on object and array
		for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
			// add to object
			temp = TraceLogUtils.getPrefixOnString(string, TraceLogConstants.REGEX_LIST[index]);
			if (temp.size() > 0) {
				getPosition.putAll(temp);
			}
			// add to array
			String keyPrefix = TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES + TraceLogConstants.REGEX_LIST[index]
					+ TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES;
			temp = TraceLogUtils.getPrefixOnString(string, keyPrefix);
			if (temp.size() > 0) {
				getPosition.putAll(temp);
			}
		}

		return getPosition;
	}

	/**
	 * 
	 * @param string
	 * @param argument
	 * @return
	 */
	private <E> String formatToConsoleLog(String string, E... argument) {
		String res = new String(string);

		Map<Integer, RegexCondition> prefixListOrder = getPrefixListOnString(string);
		try {

			if (argument == null) {
				throw new NoSuchObjectException("Argument can not be null.");
			}
			if (prefixListOrder.size() != argument.length) {
				throw new NoSuchObjectException("Prefix and Argument Are Not The Same Length.");
			}
			
			// get string will replace
			int position = 0;
			String[] values;
			String stringWillReplace;

			for (Map.Entry<Integer, RegexCondition> entry : prefixListOrder.entrySet()) {
				for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
					if (TraceLogConstants.REGEX_LIST[index].equals(entry.getValue().getSignalPrefix())) {
						
						values = TraceLogUtils.convertElementTypeToString(argument[position++], entry.getValue());
						// object is single
						String regex = "";
						if (!entry.getValue().isArray()) {
							stringWillReplace = TraceLogUtils.putValueOnSingle(TraceLogConstants.REGEX_TYPE[index],
									values);
							regex = TraceLogConstants.REGEX_PREFIX.concat(TraceLogConstants.REGEX_LIST[index]);
						} else {// object is array
							stringWillReplace = TraceLogUtils.putValueOnArr(TraceLogConstants.REGEX_TYPE[index],
									values);
							regex = TraceLogConstants.REGEX_PREFIX
									.concat("\\" + TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES
											+ TraceLogConstants.REGEX_LIST[index] + "\\"
											+ TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES);
						}
						// replace string
						res = res.replaceFirst(regex, stringWillReplace);
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (UnsupportedDataTypeException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public <E> void logWarning(String warning, E... arg) {
		String rewrite = getLogWarning(warning, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logInfo(String infor, E... arg) {
		String rewrite = getLogInfor(infor, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logError(String error, E... arg) {
		String rewrite = getLogError(error, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> String getLogError(String error, E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " ERROR ] ".concat(this.formatToConsoleLog(error, argument)));
		return rewrite.toString();
	}

	@Override
	public <E> String getLogWarning(String warning, E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " WARNING ] ".concat(this.formatToConsoleLog(warning, argument)));
		return rewrite.toString();
	}

	@Override
	public <E> String getLogInfor(String infor, E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " INFOR ] ".concat(this.formatToConsoleLog(infor, argument)));
		return rewrite.toString();
	}

}
