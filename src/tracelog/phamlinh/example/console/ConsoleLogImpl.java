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
		System.err.println(TraceLogUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_YYYYMMDDHHMMSSA) + " "
				+ System.getProperty("user.dir"));
		System.err.println("\t\t\t## Start trace log. ##\t\t\t");
	}

	public ConsoleLogImpl(String content) {
		System.err.println(TraceLogUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_YYYYMMDDHHMMSSA) + " "
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
	 * @param prefixMap
	 * @param argurment
	 */
	private <E> void checkMapPrefixAndObjectType(Map<Integer, RegexCondition> prefixListOrder, E[]... arguments)
			throws NullPointerException, NoSuchObjectException {

		if (arguments == null) {
			throw new NoSuchObjectException("Argument can not be null.");
		}

		if (prefixListOrder.size() != arguments.length) {
			throw new NoSuchObjectException("Prefix and Argument Are Not The Same Length.");
		}

		int position = 0;
		for (Map.Entry<Integer, RegexCondition> entry : prefixListOrder.entrySet()) {
			for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
				if (TraceLogConstants.REGEX_LIST[index].equals(entry.getValue().getSignalPrefix())) {
					if (!TraceLogUtils.checkObjectType(TraceLogConstants.REGEX_TYPE[index], arguments[position][0])) {
						throw new NoSuchObjectException(
								"Prefix " + TraceLogConstants.REGEX_PREFIX + TraceLogConstants.REGEX_LIST[index]
										+ " Not Match For Type " + arguments[position][0].getClass());
					}
					if ((entry.getValue().isArray() && arguments[position].length < 1)
							|| (!entry.getValue().isArray() && arguments[position].length != 1)) {
						throw new NoSuchObjectException("Prefix and Argument Is Not Incorrect.");
					}
					position++;
				}
			}
		}
	}

	/**
	 * 
	 * @param prefixMap
	 * @param argurment
	 */
	private <E> void checkMapPrefixAndObjectType(Map<Integer, RegexCondition> prefixListOrder, E... argument)
			throws NullPointerException, NoSuchObjectException {

		if (argument == null) {
			throw new NoSuchObjectException("Argument can not be null.");
		}

		if (prefixListOrder.size() != argument.length) {
			throw new NoSuchObjectException("Prefix and Argument Are Not The Same Length.");
		}

		int position = 0;
		for (Map.Entry<Integer, RegexCondition> entry : prefixListOrder.entrySet()) {
			for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
				if (TraceLogConstants.REGEX_LIST[index].equals(entry.getValue().getSignalPrefix())) {
				//	System.out.println(TraceLogConstants.REGEX_TYPE[index] + " -- " + argument[position].getClass().getTypeName());
					if (!TraceLogUtils.checkObjectType(TraceLogConstants.REGEX_TYPE[index], argument[position])) {
						throw new NoSuchObjectException(
								"Prefix " + entry.getValue().getRegex()
										+ " Not Match For Type " + argument[position].getClass().getTypeName());
					}
					position++;
				}
			}
		}
	}

	/**
	 * 
	 * @param string
	 * @param arg
	 * @return
	 */
	private <E> String formatToConsoleLog(String string, E[]... arguments) {
		String res = new String(string);

		Map<Integer, RegexCondition> prefixListOrder = getPrefixListOnString(string);
		try {

			checkMapPrefixAndObjectType(prefixListOrder, arguments);
			// get string will replace
			int position = 0;
			String[] values;
			String stringWillReplace;

			for (Map.Entry<Integer, RegexCondition> entry : prefixListOrder.entrySet()) {
				for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {

					if (TraceLogConstants.REGEX_LIST[index].equals(entry.getValue().getSignalPrefix())) {
						values = TraceLogUtils.convertElementArrayTypeToString(arguments[position++], entry.getValue());
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
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (UnsupportedDataTypeException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return res;
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

			checkMapPrefixAndObjectType(prefixListOrder, argument);
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
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (UnsupportedDataTypeException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	public <E> String getLogInfor(String infor, E[]... arguments) {
		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " INFOR ] ".concat(this.formatToConsoleLog(infor, arguments)));
		return rewrite.toString();
	}

	@Override
	public <E> String getLogWarning(String warning, E[]... arguments) {
		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " WARNING ] ".concat(this.formatToConsoleLog(warning, arguments)));
		return rewrite.toString();
	}

	@Override
	public <E> String getLogError(String error, E[]... arguments) {
		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " ERROR ] ".concat(this.formatToConsoleLog(error, arguments)));
		return rewrite.toString();
	}

	@Override
	public <E> void logWarning(String warning, E[]... arguments) {
		String rewrite = getLogWarning(warning, arguments);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_CYAN + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logInfo(String infor, E[]... arguments) {
		String rewrite = getLogInfor(infor, arguments);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logError(String error, E[]... arguments) {
		String rewrite = getLogError(error, arguments);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_RED + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logWarning(String warning, E... arg) {

		// Object t = new String[] {"fsdfsdf", "fsfsdfsdf"};
		// System.out.println(t.getClass().isArray());
		//
		// Object c = new String("fsdfsdf");
		// System.out.println(c.getClass().isArray());

		// for(int index = 0; index < arg.length; index++) {
		// System.out.println("Value " + arg.getClass().getTypeName());
		// }
		//
		// System.out.println("E0" + arg.getClass().isArray());

	}

	@Override
	public <E> void logInfo(String infor, E... arg) {

		String rewrite = getLogInfor(infor, arg);
		System.err.println(TraceLogConstants.ANSI_WHITE_BACKGROUND + TraceLogConstants.ANSI_BLUE + rewrite + " "
				+ TraceLogConstants.ANSI_RESET);
	}

	@Override
	public <E> void logError(String error, E... arg) {

	}

	@Override
	public <E> String getLogError(String error, E... arguments) {

		return null;
	}

	@Override
	public <E> String getLogWarning(String warning, E... arguments) {

		return null;
	}

	@Override
	public <E> String getLogInfor(String infor, E... argument) {

		StringBuilder rewrite = new StringBuilder(
				" [ " + TraceLogUtils.StringformatDate(TraceLogUtils.FORMAT_DATE_HHMMSS)
						+ " INFOR ] ".concat(this.formatToConsoleLog(infor, argument)));
		return rewrite.toString();
	}

}
