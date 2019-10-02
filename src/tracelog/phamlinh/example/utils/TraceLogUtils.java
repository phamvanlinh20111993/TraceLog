package tracelog.phamlinh.example.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.Vector;
import java.util.Map.Entry;

import javax.activation.UnsupportedDataTypeException;

import tracelog.phamlinh.example.object.TransferMapToObject;

public class TraceLogUtils {

	public static final String FORMAT_DATE_HHMMSS = "HH:mm:ss";
	
	public static final String FORMAT_DATE_YYYYMMDDHHMMSSA = "yyyy-MM-dd hh:mm:ss a";

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
		 * @return
		 */
		public static boolean isEmpty(String[] string) {
			return string != null && string.length > 0;
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
				if (value[index] == "null")
					res = res.append(value[index].concat(", "));
				else
					res = res.append(open + value[index].concat(close).concat(", "));
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
				if (typeCheck.equals(type) || typeCheck.equals(type.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY)))
					return true;
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
	 * @throws NumberFormatException
	 * @throws UnsupportedDataTypeException
	 * @throws NullPointerException
	 */
	public static <E> List<String> primitiveTypeToListStr(E argument)
			throws NumberFormatException, UnsupportedDataTypeException, NullPointerException {
		List<String> response = new ArrayList<String>();
		String typeCheck = argument == null ? "" : argument.getClass().getTypeName();

		if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_SHORT)) {
			short sho = (short) argument;
			response.add(Short.toString(sho));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_SHORT.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (short sho : (short[]) argument)
				response.add(Short.toString(sho));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_INTEGER)) {
			int in = (int) argument;
			response.add(Integer.toString(in));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_INTEGER.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (int in : (int[]) argument)
				response.add(Integer.toString(in));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_CHAR)) {
			char ch = (char) argument;
			response.add(Character.toString(ch));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_CHAR.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (char ch : (char[]) argument)
				response.add(Character.toString(ch));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE)) {
			double dou = (double) argument;
			response.add(Double.toString(dou));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_DOUBLE.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (double dou : (double[]) argument)
				response.add(Double.toString(dou));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT)) {
			float flo = (float) argument;
			response.add(Float.toString(flo));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_FLOAT.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (float flo : (float[]) argument)
				response.add(Float.toString(flo));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_LONG)) {
			long lo = (long) argument;
			response.add(Long.toString(lo));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_LONG.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (long lo : (long[]) argument)
				response.add(Long.toString(lo));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BYTE)) {
			byte byt = (byte) argument;
			response.add(Byte.toString(byt));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_BYTE.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (byte byt : (byte[]) argument)
				response.add(Float.toString(byt));
		} else if (typeCheck.equals(TraceLogConstants.PRIMITIVE_TYPE_BOOLEAN)) {
			boolean bool = (boolean) argument;
			response.add(Boolean.toString(bool));
		} else if (typeCheck
				.equals(TraceLogConstants.PRIMITIVE_TYPE_BOOLEAN.concat(TraceLogConstants.PRIMITIVE_TYPE_ARRAY))) {
			for (boolean bool : (boolean[]) argument)
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
	public static <E> List<String> wrapperTypeToListStr(E argument)
			throws NumberFormatException, UnsupportedDataTypeException, NullPointerException {
		List<String> response = new ArrayList<String>();

		if (argument instanceof Number) {
			response.add(argument.toString());
		} else if (argument instanceof Number[]) {
			for (Number in : (Number[]) argument)
				response.add(in.toString());
		} else if (argument instanceof Character) {
			Character ch = (Character) argument;
			response.add(Character.toString(ch));
		} else if (argument instanceof Character[]) {
			for (Character ch : (Character[]) argument)
				response.add(Character.toString(ch));
		} else if (argument instanceof Byte) {
			Byte byt = (Byte) argument;
			response.add(Byte.toString(byt));
		} else if (argument instanceof Byte[]) {
			for (Byte byt : (Byte[]) argument)
				response.add(Float.toString(byt));
		} else if (argument instanceof Boolean) {
			Boolean bool = (Boolean) argument;
			response.add(Boolean.toString(bool));
		} else if (argument instanceof Boolean[]) {
			for (Boolean bool : (Boolean[]) argument)
				response.add(Boolean.toString(bool));
		} else if (argument instanceof String) {
			String string = (String) argument;
			response.add(string);
		} else if (argument instanceof String[]) {
			for (String string : (String[]) argument)
				response.add(string);
		}

		return response;
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
	public static <T> String objectToStr(T object) throws NoSuchFieldException, IllegalAccessException,
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
						Map<?, ?> collection = (Map<?, ?>) field.get(object);
						transferObjectListToArray = new Object[collection.size()];

						Iterator<?> iterator = collection.entrySet().iterator();
						TransferMapToObject[] mapToObject = new TransferMapToObject[collection.size()];
						while (iterator.hasNext()) {
							Map.Entry<?, ?> obj = (Entry<?, ?>) iterator.next();
							mapToObject[position] = new TransferMapToObject(obj.getKey(), obj.getValue());
							transferObjectListToArray[position] = mapToObject[position++];
						}
					} else {
						Collection<?> collection = (Collection<?>) field.get(object);
						transferObjectListToArray = new Object[collection.size()];
						if (CheckJavaUtils.isJavaUtilCollection(collection)) {
							Iterator<?> iterator = collection.iterator();
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
						value += objectToStr(obj) + TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES + ", ";
					} else 
						value += (obj != null ? obj.toString() : "null") + ", ";
				}
			} else 
				value = isArray ? TraceLogConstants.REGEX_ARRAY_OPEN_PARRENTHESES : "null";
			
			int length = value.length();

			value = value.substring(0,
					length < 2 * TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES.length() ? length : length - 2);
			value += isArray ? TraceLogConstants.REGEX_ARRAY_CLOSE_PARRENTHESES : "";
			res = res.append("`" + key + "`".concat(": \"" + value + "\"").concat(", "));
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
	public static <T> List<String> collectionToListStr(Collection<?> object) throws NoSuchFieldException,
			IllegalAccessException, NullPointerException, UnsupportedDataTypeException, IllegalArgumentException {
		List<String> stringCollection = new ArrayList<String>();
		Collection<?> collection = object;
		Iterator<?> iterator = collection.iterator();
		
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (!CheckJavaUtils.isJavaLangObject(obj)) {
				stringCollection.add(objectToStr(obj));
			} else {
				if (CheckJavaUtils.isJavaUtilMap(obj)) {
					stringCollection.addAll(mapToListStr((Map<?, ?>) obj));
				} else if (CheckJavaUtils.isJavaUtilCollection(obj)) {
					stringCollection.addAll(collectionToListStr((Collection<?>) obj));
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
	public static <T> List<String> mapToListStr(Map<?, ?> object) throws NoSuchFieldException,
			IllegalAccessException, NullPointerException, UnsupportedDataTypeException, IllegalArgumentException {
		List<String> stringMap = new ArrayList<String>();
		Map<?, ?> collection = object;
		Iterator<?> iterator = collection.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<?, ?> obj = (Entry<?, ?>) iterator.next();
			TransferMapToObject mapToObject = new TransferMapToObject(obj.getKey(), obj.getValue());
			stringMap.add(objectToStr(mapToObject));
		}

		return stringMap;
	}

}
