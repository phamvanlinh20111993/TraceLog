package tracelog.phamlinh.example.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
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

	/**
	 * 
	 * @author PhamVanLinh
	 *
	 */
	public static class StringUtils {

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
					: StringUtils.isEmpty(string) ? "" + loop + repeatStart(string, loop, total - 1)
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
					: StringUtils.isEmpty(string) ? "" + repeatEnd(string, loop, total - 1) + loop
							: repeatEnd(string, loop, total - 1) + loop;
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
	}

	/**
	 * 
	 * @author PhamVanLinh
	 *
	 */
	public static class CheckJavaUtils {
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
			for (String type : TraceLogConstants.LIST_PRIMITIVE_TYPE) {
				if (typeCheck.equals(type) || typeCheck.equals(type.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
					return true;
				}
			}

			return false;
		}

		/**
		 * 
		 * @param check
		 * @return
		 */
		public static <E> boolean isJavaPrimitiveA(E check) {
			return check.getClass().getComponentType().isPrimitive();
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
		 * Reference https://www.javatpoint.com/collections-in-java
		 * 
		 * @param argument
		 * @return
		 */
		public static <E> boolean isJavaUtilCollection(E argument) {
			return argument instanceof Collection<?> && (argument instanceof List || argument instanceof Set
					|| argument instanceof Queue || argument instanceof Stack || argument instanceof Vector
					|| argument instanceof Deque<?> || argument instanceof SortedSet<?>);
		}

		/**
		 * Reference https://www.javatpoint.com/collections-in-java
		 * 
		 * @param check
		 * @return
		 */
		public static <E> boolean isJavaUtilCollection(String check) {
			return check.matches("^java\\.util\\.(?:" + TraceLogConstants.COLLECTION_TYPE_LIST + "|"
					+ TraceLogConstants.COLLECTION_TYPE_QUEUE + "|" + TraceLogConstants.COLLECTION_TYPE_QUEUE + "|"
					+ TraceLogConstants.COLLECTION_TYPE_STACK + "|" + TraceLogConstants.COLLECTION_TYPE_VECTOR
					+ TraceLogConstants.COLLECTION_TYPE_DEQUEUE + TraceLogConstants.COLLECTION_TYPE_SORTEDSET + ")$");
		}

		/**
		 * 
		 * @param check
		 * @return
		 */
		public static <E> boolean isJavaUtilMap(String check) {
			return check.matches("^java\\.util\\." + TraceLogConstants.COLLECTION_TYPE_MAP + "$");
		}

		/**
		 * 
		 * @param argument
		 * @return
		 */
		public static <E> boolean isJavaUtilMap(E argument) {
			return argument instanceof Map<?, ?>;
		}
	}

	/**
	 * 
	 * @param argument
	 * @return
	 */
	private static <E> List<String> convertPrimitiveTypeToString(E argument) {
		List<String> response = new ArrayList<String>();
		String typeCheck = argument.getClass().getTypeName();

		if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_SHORT)) {
			short sho = (short) argument;
			response.add(Short.toString(sho));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_SHORT.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			short[] shoL = (short[]) argument;
			for (short sho : shoL)
				response.add(Short.toString(sho));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_INTEGER)) {
			int in = (int) argument;
			response.add(Integer.toString(in));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_INTEGER.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			int[] inL = (int[]) argument;
			for (int in : inL)
				response.add(Integer.toString(in));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_CHAR)) {
			char ch = (char) argument;
			response.add(Character.toString(ch));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_CHAR.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			char[] chL = (char[]) argument;
			for (char ch : chL)
				response.add(Character.toString(ch));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE)) {
			double dou = (double) argument;
			response.add(Double.toString(dou));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			double[] douL = (double[]) argument;
			for (double dou : douL)
				response.add(Double.toString(dou));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT)) {
			float flo = (float) argument;
			response.add(Float.toString(flo));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			float[] folL = (float[]) argument;
			for (float fol : folL)
				response.add(Float.toString(fol));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_LONG)) {
			long lo = (long) argument;
			response.add(Long.toString(lo));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_LONG.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			long[] loL = (long[]) argument;
			for (long lo : loL)
				response.add(Long.toString(lo));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BYTE)) {
			byte byt = (byte) argument;
			response.add(Byte.toString(byt));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_BYTE.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			byte[] bytL = (byte[]) argument;
			for (byte byt : bytL)
				response.add(Float.toString(byt));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BOOLEAN)) {
			boolean bool = (boolean) argument;
			response.add(Boolean.toString(bool));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_BOOLEAN.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
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
	private static <E> List<String> convertWrapperTypeToString(E argument, RegexCondition condition)
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
	 * @param key
	 * @param object
	 * @return
	 */
	public static <T> Boolean checkObjectType(String key, T object) {

		Boolean res = false;
		String typeCheck = object.getClass().getTypeName();
		/**
		 * have some problems with primitive type :(((
		 */
		if (object.getClass().isArray() && !object.getClass().getComponentType().isPrimitive()) {
			Object[] arrayObject = (Object[]) object;
			object = (T) arrayObject[0];
		}

		if (CheckJavaUtils.isJavaUtilCollection(object)) {
			Collection<?> listObject = (Collection<?>) object;
			object = (T) listObject.iterator().next();
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
	private static String addPrefixNumber(Number number, Integer previouDecimalPoint, Integer afterDecimalPoint)
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
		if (argument == null)
			response.add("null");

		if (!CheckJavaUtils.isJavaLangObject(argument)) {
			if (argument.getClass().isArray()) {
				Object[] objectList = (Object[]) argument;
				for (Object object : objectList) {
					if (object == null)
						response.add("null");
					else
						response.add(convertObjectToString(object));
				}
			} else {
				if (argument instanceof Collection<?>)
					response.addAll(convertCollectionToString((Collection<?>) argument));
				else if (argument instanceof Map<?, ?>)
					response.addAll(convertMapToString((Map<Object, Object>) argument));
				else
					response.add(convertObjectToString(argument));
			}
		} else {
			if (CheckJavaUtils.isJavaPrimitive(argument)) {
				response.addAll(convertPrimitiveTypeToString(argument));
			} else if (CheckJavaUtils.isJavaLangObject(argument)) {
				response.addAll(convertWrapperTypeToString(argument, condition));
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
	public static String putValueOnSingle(String key, String[] value) {
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
	private static <T> String convertObjectToString(T object) throws NoSuchFieldException, IllegalAccessException,
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
						&& (CheckJavaUtils.isJavaUtilCollection(field.getType().getName())
								|| CheckJavaUtils.isJavaUtilMap(field.getType().getName()))) {
					isArray = true;
					// special case Map isnt collection in java 8
					if (CheckJavaUtils.isJavaUtilMap(field.getType().getName())) {
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
						if (CheckJavaUtils.isJavaUtilCollection(collection)) {
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
					if (obj != null && !CheckJavaUtils.isJavaLangObject(obj)) {
						if (!obj.getClass().isArray())
							value += TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES;
						value += convertObjectToString(obj) + TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES + ", ";
					} else {
						value += (obj != null ? obj.toString() : "null") + ", ";
					}
				}
			} else {
				value = isArray ? TraceLogConstants.REGEX_ARRAY_OPEN_PARRENTHESES : "null";
			}
			int length = value.length();

			value = value.substring(0,
					length < 2 * TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES.length() ? length : length - 2);
			value += isArray ? TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES : "";
			res = res.append("`" + key + "`".concat(" : \"" + value + "\"").concat(", "));
		}

		int length = res.toString().length();
		return res.toString().substring(0,
				(length < 2 * TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES.length()
						? 2 * TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES.length()
						: length) - 2);
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
	private static <T> List<String> convertCollectionToString(Collection<?> object) throws NoSuchFieldException,
			IllegalAccessException, NullPointerException, UnsupportedDataTypeException, IllegalArgumentException {
		List<String> stringCollection = new ArrayList<String>();

		Collection<?> collection = object;
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (!CheckJavaUtils.isJavaLangObject(obj)) {
				stringCollection.add(convertObjectToString(obj));
			} else {
				if (CheckJavaUtils.isJavaUtilMap(obj)) {
					stringCollection.addAll(convertMapToString((Map<Object, Object>) obj));
				} else if (CheckJavaUtils.isJavaUtilCollection(obj)) {
					stringCollection.addAll(convertCollectionToString((Collection<?>) obj));
				} else {
					stringCollection.add(obj.toString());
				}
			}
		}

		return stringCollection;
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
	private static <T> List<String> convertMapToString(Map<Object, Object> object) throws NoSuchFieldException,
			IllegalAccessException, NullPointerException, UnsupportedDataTypeException, IllegalArgumentException {
		List<String> stringMap = new ArrayList<String>();
		Map<Object, Object> collection = object;
		Iterator<Entry<Object, Object>> iterator = collection.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Object, Object> obj = iterator.next();
			TransferMapToObject mapToObject = new TransferMapToObject(obj.getKey(), obj.getValue());
			stringMap.add(convertObjectToString(mapToObject));
		}

		return stringMap;
	}

}
