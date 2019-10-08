package tracelog.phamlinh.example.console;

import java.math.BigDecimal;
import java.rmi.NoSuchObjectException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.UnsupportedDataTypeException;

import tracelog.phamlinh.example.object.RegexCondition;
import tracelog.phamlinh.example.utils.TraceLogConstants;
import tracelog.phamlinh.example.utils.TraceLogUtils;
import tracelog.phamlinh.example.utils.TraceLogUtils.CheckJavaUtils;
import tracelog.phamlinh.example.utils.TraceLogUtils.StringUtils;

public class TransformDataConsole {

	protected final Integer MAX_PREVIOUS_DECIMAL_POINT = 100;
	protected final Integer MAX_AFTER_DECIMAL_POINT = 100;

	protected TransformDataConsole() {

	}

	/**
	 * 
	 * @param argument
	 * @return
	 * @throws NullPointerException
	 * @throws UnsupportedDataTypeException
	 * @throws NumberFormatException
	 */
	protected <E> List<String> primitiveTypeToListStr(E argument, RegexCondition condition)
			throws NumberFormatException, UnsupportedDataTypeException, NullPointerException {
		List<String> response = new ArrayList<String>();
		String typeCheck = argument.getClass().getTypeName();

		if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE)) {
			double dou = (double) argument;
			response.add(addPrefixNumber((Number) dou, Integer.valueOf(condition.getNaturePath()),
					Integer.valueOf(condition.getDecimal())));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (double dou : (double[]) argument)
				response.add(addPrefixNumber((Number) dou, Integer.valueOf(condition.getNaturePath()),
						Integer.valueOf(condition.getDecimal())));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT)) {
			float flo = (float) argument;
			response.add(addPrefixNumber((Number) flo, Integer.valueOf(condition.getNaturePath()),
					Integer.valueOf(condition.getDecimal())));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (float flo : (float[]) argument)
				response.add(addPrefixNumber((Number) flo, Integer.valueOf(condition.getNaturePath()),
						Integer.valueOf(condition.getDecimal())));
		} else {
			response.addAll(TraceLogUtils.primitiveTypeToListStr(argument));
		}

		return response;
	}

	/**
	 * 
	 * @param argument
	 * @return
	 * @throws NullPointerException
	 * @throws UnsupportedDataTypeException
	 * @throws NumberFormatException
	 */
	protected <E> List<String> wrapperTypeToListStr(E argument, RegexCondition condition)
			throws NumberFormatException, UnsupportedDataTypeException, NullPointerException {
		List<String> response = new ArrayList<String>();

		if (argument instanceof Number) {
			response.add(addPrefixNumber((Number) argument, Integer.valueOf(condition.getNaturePath()),
					Integer.valueOf(condition.getDecimal())));
		} else if (argument instanceof Number[]) {
			for (Number in : (Number[]) argument)
				response.add(addPrefixNumber(in, Integer.valueOf(condition.getNaturePath()),
						Integer.valueOf(condition.getDecimal())));
		} else {
			response.addAll(TraceLogUtils.wrapperTypeToListStr(argument));
		}

		return response;
	}

	/**
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	protected <T> Boolean checkObjectType(String key, Object object) {

		Boolean res = false;
		String typeCheck = object.getClass().getTypeName();
		/**
		 * have some problems with primitive type :(((
		 */
		if (object.getClass().isArray() && !object.getClass().getComponentType().isPrimitive()) {
			Object[] arrayObject = (Object[]) object;
			object = (Object) arrayObject[0];
		}
		if (CheckJavaUtils.isJavaUtilCollection(object)) {
			Collection<?> listObject = (Collection<?>) object;
			object = (Object) listObject.iterator().next();
		}
		if (key == TraceLogConstants.REGEX_TYPE_VALUE || TraceLogConstants.REGEX_TYPE_ARGUMENT == key)
			res = true;

		if (TraceLogConstants.REGEX_TYPE_NUMBER == key)
			res = object instanceof Double || object instanceof Float || object instanceof Long
					|| object instanceof Short || object instanceof Number
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_SHORT)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_SHORT + TraceLogConstants.PRIMITIVE_TYPE_ARRAY)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_LONG)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_LONG + TraceLogConstants.PRIMITIVE_TYPE_ARRAY)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_INTEGER)
					|| typeCheck
							.equals(TraceLogConstants.PRIMITIVE_TYPE_INTEGER + TraceLogConstants.PRIMITIVE_TYPE_ARRAY)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT + TraceLogConstants.PRIMITIVE_TYPE_ARRAY)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE) || typeCheck
							.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE + TraceLogConstants.PRIMITIVE_TYPE_ARRAY);

		if (key == TraceLogConstants.REGEX_TYPE_STRING)
			res = object instanceof String || object instanceof String[];

		if (key == TraceLogConstants.REGEX_TYPE_CHAR)
			res = object instanceof Character || typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_CHAR)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_CHAR + TraceLogConstants.PRIMITIVE_TYPE_ARRAY);

		if (key == TraceLogConstants.REGEX_TYPE_OBJECT)
			res = !CheckJavaUtils.isJavaLangObject(object);

		if (key == TraceLogConstants.REGEX_TYPE_BOOLEAN)
			res = object instanceof Boolean || typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BOOLEAN) || typeCheck
					.equals(TraceLogConstants.PRIMITIVE_TYPE_BOOLEAN + TraceLogConstants.PRIMITIVE_TYPE_ARRAY);

		if (key == TraceLogConstants.REGEX_TYPE_BYTE)
			res = object instanceof Byte || typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BYTE)
					|| typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BYTE + TraceLogConstants.PRIMITIVE_TYPE_ARRAY);
		
		if(key == TraceLogConstants.REGEX_TYPE_BITSET)
			res = object instanceof BitSet;
		
		return res;
	}

	/**
	 * 
	 * @param number
	 * @param previouDecimalPoint
	 * @param afterDecimalPoint
	 * @return
	 * @throws UnsupportedDataTypeException
	 */
	protected String addPrefixNumber(Number number, Integer previouDecimalPoint, Integer afterDecimalPoint)
			throws UnsupportedDataTypeException, NullPointerException {
		String res = "";
		if ((number instanceof Integer || number instanceof Long || number instanceof Short || number instanceof Byte)
				&& (previouDecimalPoint > 0 || afterDecimalPoint > 0)) {
			throw new UnsupportedDataTypeException("Unsupported for type " + number.getClass().getTypeName());
		} else if (!(number instanceof Integer || number instanceof Long || number instanceof Short)
				&& (previouDecimalPoint > 0 || afterDecimalPoint > 0)) {
			previouDecimalPoint = previouDecimalPoint > MAX_PREVIOUS_DECIMAL_POINT ? MAX_PREVIOUS_DECIMAL_POINT
					: previouDecimalPoint;
			afterDecimalPoint = afterDecimalPoint > MAX_AFTER_DECIMAL_POINT ? MAX_AFTER_DECIMAL_POINT
					: afterDecimalPoint;

			if (number instanceof Float || number instanceof Double) {
				String formattingFloatingPoint = "";
				if (previouDecimalPoint <= 25 || afterDecimalPoint <= 25) {
					formattingFloatingPoint = StringUtils.repeatStart("", "#", previouDecimalPoint) + "."
							+ StringUtils.repeatStart("", "#", afterDecimalPoint);
					DecimalFormat formatDecimal = new DecimalFormat(formattingFloatingPoint);
					res = formatDecimal.format(number);
				} else {
					formattingFloatingPoint = "%" + previouDecimalPoint + "." + afterDecimalPoint + "f";
					res = String.format(formattingFloatingPoint, number);
				}
			} else {
				throw new UnsupportedDataTypeException("Unsupported for type " + number.getClass().getTypeName());
			}
		} else {
			res = number.toString();
		}

		return res;
	}

	/**
	 * 
	 * @param string
	 * @param key
	 * @return
	 */
	protected Map<Integer, RegexCondition> getPrefixOnString(String string, String key) {

		Map<Integer, RegexCondition> getOrderPrefix = new TreeMap<Integer, RegexCondition>();
		Boolean isArray = key.charAt(0) == TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES.charAt(0)
				&& key.charAt(key.length() - 1) == TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES.charAt(0);
		String prefixKey = key;
		if (isArray) {
			int length = key.length();
			prefixKey = "\\" + key.substring(0, length - 1) + "\\" + key.substring(length - 1, length);
			key = key.substring(1, length - 1);
		}
		Pattern p = Pattern.compile(TraceLogConstants.REGEX_PREFIX.concat(prefixKey));
		String naturePath, decimalPath;
		Matcher m = p.matcher(string);

		while (m.find()) {
			naturePath = "0";
			decimalPath = "0";
			if (m.groupCount() > 0) {
				naturePath = m.group(1) != null ? m.group(1).toString() : naturePath;
				decimalPath = m.group(2) != null ? m.group(2).toString() : decimalPath;
			}
			getOrderPrefix.put(m.start(), new RegexCondition(m.group(), isArray, "", key, "", naturePath, decimalPath));
		}

		return getOrderPrefix;
	}

	/**
	 * 
	 * @param argument
	 * @return
	 * @throws UnsupportedDataTypeException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	protected <E> String[] elementTypeToStr(E argument, RegexCondition condition) throws UnsupportedDataTypeException,
			NullPointerException, NoSuchFieldException, ClassCastException, IllegalAccessException {
		ArrayList<String> response = new ArrayList<>();
		if (argument == null)
			response.add("null");

		if (!CheckJavaUtils.isJavaLangObject(argument)) {
			if (argument.getClass().isArray()) {
				Object[] objectList = (Object[]) argument;
				for (Object object : objectList) {
					if (object == null)
						response.add("null");
					else
						response.add(TraceLogUtils.objectToStr(object));
				}
			} else {
				if (argument instanceof Collection<?>)
					response.addAll(TraceLogUtils.collectionToListStr((Collection<?>) argument));
				else if (argument instanceof Map<?, ?>)
					response.addAll(TraceLogUtils.mapToListStr((Map<?, ?>) argument));
				else if (argument instanceof Dictionary<?, ?>)
					response.addAll(TraceLogUtils.dictionaryToListStr((Dictionary<?, ?>) argument));
				else if(argument instanceof BigDecimal) {
					BigDecimal decimal = (BigDecimal) argument; // the value you get
					response.add(String.valueOf(decimal.doubleValue()));
				}else  {
					if (TraceLogUtils.CheckJavaUtils.isJavaUtilObject(argument) || argument instanceof BitSet) {
						response.add(argument.toString());
					} else {
						response.add(TraceLogUtils.objectToStr(argument));
					}
				}
			}
		} else {
			if (CheckJavaUtils.isJavaPrimitive(argument)) {
				response.addAll(primitiveTypeToListStr(argument, condition));
			} else if (CheckJavaUtils.isJavaLangObject(argument)) {
				response.addAll(wrapperTypeToListStr(argument, condition));
			} else {
				throw new UnsupportedDataTypeException("Unsupported this DataType: " + argument.getClass().getName());
			}
		}

		return response.toArray(new String[response.size()]);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	protected String putDataOnSingle(String key, String[] value) {
		StringBuilder res = new StringBuilder("");
		switch (key) {
		case TraceLogConstants.REGEX_TYPE_VALUE:
			res = res.append(StringUtils.joinString(value, "\\(", "\\)"));
			break;
		case TraceLogConstants.REGEX_TYPE_ARGUMENT:
			res = res.append(StringUtils.joinString(value, "\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_NUMBER:
			res = res.append(StringUtils.joinString(value, "\\$\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_STRING:
			res = res.append(StringUtils.joinString(value, "\"", "\""));
			break;
		case TraceLogConstants.REGEX_TYPE_CHAR:
			res = res.append(StringUtils.joinString(value, "'", "'"));
			break;
		case TraceLogConstants.REGEX_TYPE_OBJECT:
			res = res.append(StringUtils.joinString(value, "\\@\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_BYTE:
			res = res.append(StringUtils.joinString(value, "\\#\\(", "\\)"));
			break;
		case TraceLogConstants.REGEX_TYPE_BOOLEAN:
			res = res.append(StringUtils.joinString(value, "<<", ">>"));
			break;
		case TraceLogConstants.REGEX_TYPE_BITSET:
			res = res.append(StringUtils.joinString(value, "~|", "|~"));
			break;
		default:
			res = res.append(StringUtils.joinString(value, "|<", ">|"));
			break;
		}

		return res.toString();
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	protected String putDataOnArr(String key, String[] value) {
		StringBuilder res = new StringBuilder(TraceLogConstants.REGEX_ARRAY_OPEN_PARRENTHESES
				.concat(putDataOnSingle(key, value)).concat(TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES));
		return res.toString();
	}

	/**
	 * 
	 * @param string
	 * @param key
	 * @return
	 */
	protected Map<Integer, RegexCondition> getPrefixListOnString(String string) {
		Map<Integer, RegexCondition> getPosition = new TreeMap<Integer, RegexCondition>();
		Map<Integer, RegexCondition> temp = new TreeMap<Integer, RegexCondition>();
		// key value on object and array
		for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
			// add to object
			temp = getPrefixOnString(string, TraceLogConstants.REGEX_LIST[index]);
			if (temp.size() > 0)
				getPosition.putAll(temp);
			// add to array
			String keyPrefix = TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES + TraceLogConstants.REGEX_LIST[index]
					+ TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES;
			temp = getPrefixOnString(string, keyPrefix);
			if (temp.size() > 0)
				getPosition.putAll(temp);
		}

		return getPosition;
	}

	/**
	 * 
	 * @param prefixMap
	 * @param argurment
	 */
	protected <E> void checkMapPrefixAndObjectType(Map<Integer, RegexCondition> prefixListOrder,
			@SuppressWarnings("unchecked") E... argument) throws NullPointerException, NoSuchObjectException, IllegalArgumentException {

		if (argument == null)
			throw new NoSuchObjectException("Argument can not be null.");

		if(prefixListOrder.size() == 0 && argument.length > 0)
			throw new IllegalArgumentException("Prefix is not recognize.");
		
		if (prefixListOrder.size() != argument.length)
			throw new NoSuchObjectException("Prefix and Argument Are Not The Same Length.");

		int position = 0;
		for (Map.Entry<Integer, RegexCondition> entry : prefixListOrder.entrySet()) {
			for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
				if (TraceLogConstants.REGEX_LIST[index].equals(entry.getValue().getSignalPrefix())) {
					if (!checkObjectType(TraceLogConstants.REGEX_TYPE[index], argument[position])
							|| (argument[position].getClass().isArray()
									|| TraceLogUtils.CheckJavaUtils.isJavaUtilCollection(argument[position])
									|| TraceLogUtils.CheckJavaUtils.isJavaUtilMap(argument[position])) != entry
											.getValue().isArray()) {
						throw new NoSuchObjectException("Prefix " + entry.getValue().getRegex() + " Not Match For Type "
								+ argument[position].getClass().getTypeName());
					}
					position++;
				}
			}
		}
	}

	/**
	 * 
	 * @param string
	 * @param argument
	 * @return
	 */
	protected <E> String formatToConsoleLog(String string, @SuppressWarnings("unchecked") E... argument) {
		String res = new String(string);

		Map<Integer, RegexCondition> prefixListOrder = getPrefixListOnString(string);
		try {
			// check condition before execute translate
			checkMapPrefixAndObjectType(prefixListOrder, argument);
			// get string will replace
			int position = 0;
			String[] values;
			String stringWillReplace;

			for (Map.Entry<Integer, RegexCondition> entry : prefixListOrder.entrySet()) {
				for (int index = 0; index < TraceLogConstants.REGEX_LIST.length; index++) {
					if (TraceLogConstants.REGEX_LIST[index].equals(entry.getValue().getSignalPrefix())) {
						values = elementTypeToStr(argument[position++], entry.getValue());
						// object is single
						String regex = "";
						if (!entry.getValue().isArray()) {
							stringWillReplace = putDataOnSingle(TraceLogConstants.REGEX_TYPE[index], values);
							regex = TraceLogConstants.REGEX_PREFIX.concat(TraceLogConstants.REGEX_LIST[index]);
						} else {// object is array
							stringWillReplace = putDataOnArr(TraceLogConstants.REGEX_TYPE[index], values);
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

}
