package tracelog.phamlinh.example.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.UnsupportedDataTypeException;

import tracelog.phamlinh.example.object.RegexCondition;
import tracelog.phamlinh.example.object.TransferMapToObject;

public class TraceLogUtils {

	public static final String FORMAT_DATE_HHMMSS = "HH:mm:ss";
	public static final String FORMAT_DATE_YYYYMMDDHHMMSSA = "yyyy-MM-dd hh:mm:ss a";
	public static final Integer MAX_PREVIOUS_DECIMAL_POINT = 100;
	public static final Integer MAX_AFTER_DECIMAL_POINT = 100;

	public static final String PRIMITIVE_TYPE_INTEGER = "int";
	public static final String PRIMITIVE_TYPE_CHAR = "char";
	public static final String PRIMITIVE_TYPE_BYTE = "byte";
	public static final String PRIMITIVE_TYPE_LONG = "long";
	public static final String PRIMITIVE_TYPE_FLOAT = "float";
	public static final String PRIMITIVE_TYPE_SHORT = "short";
	public static final String PRIMITIVE_TYPE_DOUBLE = "double";
	public static final String PRIMITIVE_TYPE_BOOLEAN = "boolean";
	public static final String PRIMITIVE_TYPE_ARRAY = "[]";

	public static final String[] LIST_PRIMITIVE_TYPE = { PRIMITIVE_TYPE_INTEGER, PRIMITIVE_TYPE_CHAR,
			PRIMITIVE_TYPE_BYTE, PRIMITIVE_TYPE_LONG, PRIMITIVE_TYPE_FLOAT, PRIMITIVE_TYPE_DOUBLE, PRIMITIVE_TYPE_SHORT,
			PRIMITIVE_TYPE_BOOLEAN };

	/**
	 * 
	 * @param check
	 * @return
	 */
	public static boolean isJavaLangObject(Object check) {
		return isJavaPrimitive(check) || check.getClass().getName().startsWith("java.lang")
				|| check.getClass().getName().startsWith("[Ljava.lang");
	}

	/**
	 * 
	 * @param check
	 * @return
	 */
	public static <E> boolean isJavaPrimitive(E check) {
		String typeCheck = check.getClass().getTypeName();
		for (String type : LIST_PRIMITIVE_TYPE) {
			if (typeCheck.equals(type) || typeCheck.equals(type.concat(PRIMITIVE_TYPE_ARRAY))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @param argument
	 * @return
	 */
	public static <E> List<String> convertPrimitiveTypeToString(E argument) {
		List<String> response = new ArrayList<String>();
		String typeCheck = argument.getClass().getTypeName();

		if (typeCheck.equals(PRIMITIVE_TYPE_SHORT)) {
			short sho = (short) argument;
			response.add(Short.toString(sho));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_SHORT.concat(PRIMITIVE_TYPE_ARRAY))) {
			short[] shoL = (short[]) argument;
			for (short sho : shoL)
				response.add(Short.toString(sho));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_INTEGER)) {
			int in = (int) argument;
			response.add(Integer.toString(in));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_INTEGER.concat(PRIMITIVE_TYPE_ARRAY))) {
			int[] inL = (int[]) argument;
			for (int in : inL)
				response.add(Integer.toString(in));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_CHAR)) {
			char ch = (char) argument;
			response.add(Character.toString(ch));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_CHAR.concat(PRIMITIVE_TYPE_ARRAY))) {
			char[] chL = (char[]) argument;
			for (char ch : chL)
				response.add(Character.toString(ch));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_DOUBLE)) {
			double dou = (double) argument;
			response.add(Double.toString(dou));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_DOUBLE.concat(PRIMITIVE_TYPE_ARRAY))) {
			double[] douL = (double[]) argument;
			for (double dou : douL)
				response.add(Double.toString(dou));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_FLOAT)) {
			float flo = (float) argument;
			response.add(Float.toString(flo));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_FLOAT.concat(PRIMITIVE_TYPE_ARRAY))) {
			float[] folL = (float[]) argument;
			for (float fol : folL)
				response.add(Float.toString(fol));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_LONG)) {
			long lo = (long) argument;
			response.add(Long.toString(lo));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_LONG.concat(PRIMITIVE_TYPE_ARRAY))) {
			long[] loL = (long[]) argument;
			for (long lo : loL)
				response.add(Long.toString(lo));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_BYTE)) {
			byte byt = (byte) argument;
			response.add(Byte.toString(byt));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_BYTE.concat(PRIMITIVE_TYPE_ARRAY))) {
			byte[] bytL = (byte[]) argument;
			for (byte byt : bytL)
				response.add(Float.toString(byt));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_BOOLEAN)) {
			boolean bool = (boolean) argument;
			response.add(Boolean.toString(bool));
		} else if (typeCheck.equals(PRIMITIVE_TYPE_BOOLEAN.concat(PRIMITIVE_TYPE_ARRAY))) {
			boolean[] boolL = (boolean[]) argument;
			for (boolean bool : boolL)
				response.add(Boolean.toString(bool));
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
	public static <E> List<String> convertWrapperTypeToString(E argument, RegexCondition condition)
			throws NumberFormatException, UnsupportedDataTypeException, NullPointerException {
		List<String> response = new ArrayList<String>();

		if (argument instanceof Number) {
			response.add(TraceLogUtils.addPrefixNumber((Number) argument, Integer.valueOf(condition.getNaturePath()),
					Integer.valueOf(condition.getDecimal())));
		} else if (argument instanceof Number[]) {
			Number[] inL = (Number[]) argument;
			for (Number in : inL)
				response.add(TraceLogUtils.addPrefixNumber(in, Integer.valueOf(condition.getNaturePath()),
						Integer.valueOf(condition.getDecimal())));
		} else if (argument instanceof Character) {
			Character ch = (Character) argument;
			response.add(Character.toString(ch));
		} else if (argument instanceof Character[]) {
			Character[] chL = (Character[]) argument;
			for (Character ch : chL)
				response.add(Character.toString(ch));
		} else if (argument instanceof Byte) {
			Byte byt = (Byte) argument;
			response.add(Byte.toString(byt));
		} else if (argument instanceof Byte[]) {
			Byte[] bytL = (Byte[]) argument;
			for (Byte byt : bytL)
				response.add(Float.toString(byt));
		} else if (argument instanceof Boolean) {
			Boolean bool = (Boolean) argument;
			response.add(Boolean.toString(bool));
		} else if (argument instanceof Boolean[]) {
			Boolean[] boolL = (Boolean[]) argument;
			for (Boolean bool : boolL)
				response.add(Boolean.toString(bool));
		} else if (argument instanceof String) {
			String string = (String) argument;
			response.add(string);
		} else if (argument instanceof String[]) {
			String[] stringL = (String[]) argument;
			for (String string : stringL)
				response.add(string);
		}

		return response;
	}

	/**
	 * 
	 * @param check
	 * @return
	 */
	public static boolean isJavaUtilObject(Object check) {
		return check.getClass().getName().startsWith("java.util")
				|| check.getClass().getName().startsWith("[Ljava.util");
	}

	/**
	 * 
	 * @param check
	 * @return
	 */
	public static <E> boolean isJavaUtilCollection(String check) {
		return check.matches("^java\\.util\\.(?:" + TraceLogConstants.COLLECTION_TYPE_LIST + "|"
				+ TraceLogConstants.COLLECTION_TYPE_MAP + "|" + TraceLogConstants.COLLECTION_TYPE_QUEUE + "|"
				+ TraceLogConstants.COLLECTION_TYPE_QUEUE + "|" + TraceLogConstants.COLLECTION_TYPE_STACK + "|"
				+ TraceLogConstants.COLLECTION_TYPE_VECTOR + ")$");
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return string != null && string != "";
	}

	/**
	 * 
	 * @param string
	 * @param loop
	 * @param total
	 * @return
	 */
	public static String repeatStart(String string, String loop, Integer total) {
		return total < 1 ? string
				: TraceLogUtils.isEmpty(string) ? "" + loop + repeatStart(string, loop, total - 1)
						: loop + repeatStart(string, loop, total - 1);
	}

	/**
	 * 
	 * @param string
	 * @param loop
	 * @param total
	 * @return
	 */
	public static String repeatEnd(String string, String loop, Integer total) {
		return total < 1 ? string
				: TraceLogUtils.isEmpty(string) ? "" + repeatEnd(string, loop, total - 1) + loop
						: repeatEnd(string, loop, total - 1) + loop;
	}

	/**
	 * 
	 * @param number
	 * @param previouDecimalPoint
	 * @param afterDecimalPoint
	 * @return
	 * @throws UnsupportedDataTypeException
	 */
	public static String addPrefixNumber(Number number, Integer previouDecimalPoint, Integer afterDecimalPoint)
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
					formattingFloatingPoint = repeatStart("", "#", previouDecimalPoint) + "."
							+ repeatStart("", "#", afterDecimalPoint);
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
	 * @param format
	 * @return
	 * @throws IllegalFormatException
	 */
	public static String StringformatDate(String format) throws IllegalFormatException {
		String pattern = format;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		return date;
	}

	/**
	 * 
	 * @param string
	 * @param key
	 * @return
	 */
	public static Map<Integer, RegexCondition> getPrefixOnString(String string, String key) {

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
	public static <E> String[] convertElementTypeToString(E argument, RegexCondition condition)
			throws UnsupportedDataTypeException, NullPointerException, NoSuchFieldException, IllegalAccessException {

		ArrayList<String> response = new ArrayList<>();
		if (argument == null) {
			response.add("null");
		}
		if (!TraceLogUtils.isJavaLangObject(argument)) {
			if (argument.getClass().isArray()) {
				Object[] objectList = (Object[]) argument;
				for (Object object : objectList) {
					if (object == null) {
						response.add("null");
					} else {
						response.add(convertObjectToString(object));
					}
				}
			} else {
				response.add(convertObjectToString(argument));
			}
		} else {
			if (isJavaPrimitive(argument)) {
				response.addAll(convertPrimitiveTypeToString(argument));
			} else if (TraceLogUtils.isJavaLangObject(argument)) {
				response.addAll(convertWrapperTypeToString(argument, condition));
			} else {
				throw new UnsupportedDataTypeException("Unsupported this DataType: " + argument.getClass().getName());
			}
		}

		return response.toArray(new String[response.size()]);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String joinString(String[] value, String open, String close) {
		StringBuilder res = new StringBuilder("");
		open = open == null ? "" : open;
		close = close == null ? "" : close;
		for (int index = 0; index < value.length - 1; index++) {
			if (value[index] == "null") {
				res = res.append(value[index].concat(", "));
			} else {
				res = res.append(open + value[index].concat(close).concat(", "));
			}
		}
		return value[value.length - 1] == "null" ? res.append(value[value.length - 1]).toString()
				: res.append(open + value[value.length - 1] + close).toString();
	}

	/**
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public static <T> Boolean checkObjectType(String key, T object) {

		Boolean res;
		switch (key) {
		case TraceLogConstants.REGEX_TYPE_VALUE:
			res = true;
			break;
		case TraceLogConstants.REGEX_TYPE_ARGUMENT:
			res = true;
			break;
		case TraceLogConstants.REGEX_TYPE_NUMBER:
			res = object instanceof Number;
			break;
		case TraceLogConstants.REGEX_TYPE_STRING:
			res = object instanceof String;
			break;
		case TraceLogConstants.REGEX_TYPE_CHAR:
			res = object instanceof Character;
			break;
		case TraceLogConstants.REGEX_TYPE_OBJECT:
			res = !TraceLogUtils.isJavaLangObject(object);
			break;
		case TraceLogConstants.REGEX_TYPE_BOOLEAN:
			res = object instanceof Boolean;
			break;
		// update version
		case TraceLogConstants.REGEX_TYPE_FLOAT_NUMBER:
			res = object instanceof Float;
			break;
		case TraceLogConstants.REGEX_TYPE_DOUBLE_NUMBER:
			res = object instanceof Double;
			break;
		case TraceLogConstants.COLLECTION_TYPE_LIST:
			res = object instanceof List<?>;
			break;
		case TraceLogConstants.COLLECTION_TYPE_MAP:
			res = object instanceof Map<?, ?>;
			break;
		case TraceLogConstants.COLLECTION_TYPE_SET:
			res = object instanceof Set<?>;
			break;
		case TraceLogConstants.COLLECTION_TYPE_QUEUE:
			res = object instanceof Queue<?>;
			break;
		case TraceLogConstants.COLLECTION_TYPE_VECTOR:
			res = object instanceof Vector<?>;
			break;
		case TraceLogConstants.COLLECTION_TYPE_STACK:
			res = object instanceof Stack<?>;
			break;
		default:
			res = false;
			break;
		}

		return res;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String putValueOnSingle(String key, String[] value) {
		StringBuilder res = new StringBuilder("");
		switch (key) {
		case TraceLogConstants.REGEX_TYPE_VALUE:
			res = res.append(TraceLogUtils.joinString(value, "\\(", "\\)"));
			break;
		case TraceLogConstants.REGEX_TYPE_ARGUMENT:
			res = res.append(TraceLogUtils.joinString(value, "\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_NUMBER:
			res = res.append(TraceLogUtils.joinString(value, "\\$\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_FLOAT_NUMBER:
			res = res.append(TraceLogUtils.joinString(value, "\\$\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_DOUBLE_NUMBER:
			res = res.append(TraceLogUtils.joinString(value, "\\$\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_STRING:
			res = res.append(TraceLogUtils.joinString(value, "\"", "\""));
			break;
		case TraceLogConstants.REGEX_TYPE_CHAR:
			res = res.append(TraceLogUtils.joinString(value, "'", "'"));
			break;
		case TraceLogConstants.REGEX_TYPE_OBJECT:
			res = res.append(TraceLogUtils.joinString(value, "\\@\\{", "\\}"));
			break;
		case TraceLogConstants.REGEX_TYPE_BOOLEAN:
			res = res.append(TraceLogUtils.joinString(value, "<<", ">>"));
			break;
		default:
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
	public static String putValueOnArr(String key, String[] value) {
		StringBuilder res = new StringBuilder(TraceLogConstants.REGEX_ARRAY_OPEN_PARRENTHESES
				.concat(putValueOnSingle(key, value)).concat(TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES));
		return res.toString();
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws NullPointerException
	 * @throws UnsupportedDataTypeException
	 * @throws IllegalArgumentException
	 */
	public static <T> String convertObjectToString(T object) throws NoSuchFieldException, IllegalAccessException,
			NullPointerException, UnsupportedDataTypeException, IllegalArgumentException {

		StringBuilder res = new StringBuilder("");
		Field[] fields = object.getClass().getDeclaredFields();
		String key, value;
		int index, fieldLength = fields.length, position = 0;
		Object[] transferObjectListToArray = null;
		boolean isArray;

		for (index = 0; index < fieldLength; index++) {
			key = fields[index].getName().toString();
			Field field = object.getClass().getDeclaredField(key);
			position = 0;
			isArray = false;
			// can be check Modifier.isPrivate(method.getModifiers())
			field.setAccessible(true);
			if (field.getType().isArray()) {
				isArray = true;
				transferObjectListToArray = (Object[]) field.get(object);
			} else {

				if (field.get(object) != null && field.getType() != null
						&& isJavaUtilCollection(field.getType().getName())) {
					isArray = true;
					// special case Map isnt collection in java 8
					if (field.getType().getName().equals("java.util.Map")) {
						Map<Object, Object> collection = (Map<Object, Object>) field.get(object);
						transferObjectListToArray = new Object[collection.size()];

						Iterator<Entry<Object, Object>> iterator = collection.entrySet().iterator();
						TransferMapToObject[] mapToObject = new TransferMapToObject[collection.size()];
						while (iterator.hasNext()) {
							Map.Entry<Object, Object> obj = iterator.next();
							mapToObject[position] = new TransferMapToObject(obj.getKey(), obj.getValue());
							transferObjectListToArray[position] = mapToObject[position++];
						}
					} else {
						Collection<Object> collection = (Collection<Object>) field.get(object);
						transferObjectListToArray = new Object[collection.size()];
						if (collection instanceof List || collection instanceof Set || collection instanceof Queue
								|| collection instanceof Stack || collection instanceof Vector) {
							Iterator iterator = collection.iterator();
							while (iterator.hasNext()) {
								Object obj = iterator.next();
								transferObjectListToArray[position++] = obj;
							}
						} else {
							throw new UnsupportedDataTypeException("Do not support for type " + field.getType());
						}
					}
				} else {
					isArray = false;
					transferObjectListToArray = new Object[] { field.get(object) };
				}
			}

			field.setAccessible(false);

			// set to string
			if (transferObjectListToArray != null) {
				value = isArray ? TraceLogConstants.REGEX_ARRAY_OPEN_PARRENTHESES : "";
				for (Object obj : transferObjectListToArray) {
					if (obj != null && !isJavaLangObject(obj)) {
						if (!obj.getClass().isArray()) {
							value += TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES;
						}
						value += convertObjectToString(obj) + TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES + ", ";
					} else {
						value += (obj != null ? obj.toString() : "null") + ", ";
					}
				}
			} else {
				value = isArray ? TraceLogConstants.REGEX_ARRAY_OPEN_PARRENTHESES : "null";
			}

			int length = value.length();
			value = value.substring(0, length < 2 ? length : length - 2);
			value += isArray ? TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES : "";
			res = res.append("`" + key + "`".concat(" : \"" + value + "\"").concat(", "));
		}

		int length = res.toString().length();
		return res.toString().substring(0, (length < 2 ? 2 : length) - 2);
	}

}
