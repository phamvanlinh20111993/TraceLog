package tracelog.phamlinh.example.utils;

import java.lang.reflect.Field;
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
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.UnsupportedDataTypeException;

import tracelog.phamlinh.example.object.KeyPairValue;
import tracelog.phamlinh.example.object.TransferMapToObject;

public class TraceLogUtils {

	public static final String FORMAT_DATE_HHMMSS = "HH:mm:ss";
	public static final String FORMAT_DATE_YYYYMMDDHHMMSSA = "yyyy-MM-dd hh:mm:ss a";

	/**
	 * 
	 * @param check
	 * @return
	 */
	public static boolean isJavaLangObject(Object check) {
		return check.getClass().getName().startsWith("java.lang")
				|| check.getClass().getName().startsWith("[Ljava.lang");
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
				+ TraceLogConstants.COLLECTION_TYPE_SET + ")$");
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
	public static String preStart(String string, String loop, Integer total) {
		return total < 1 ? string
				: TraceLogUtils.isEmpty(string) ? "" + loop + preStart(string, loop, total - 1)
						: loop + preStart(string, loop, total - 1);
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
		if (number instanceof Integer && previouDecimalPoint > 0 && afterDecimalPoint > 0) {
			throw new UnsupportedDataTypeException("Unsupported for type Integer");
		} else if (number instanceof Float) {

		}

		return null;
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
	public static Map<Integer, KeyPairValue> getPrefixOnString(String string, String key) {

		Map<Integer, KeyPairValue> getOrderPrefix = new TreeMap<Integer, KeyPairValue>();
		Boolean isArray = key.charAt(0) == TraceLogConstants.PREFIX_ARRAY_OPEN_PARRENTHESES.charAt(0)
				&& key.charAt(key.length() - 1) == TraceLogConstants.PREFIX_ARRAY_CLOSE_PARRENTHESES.charAt(0);
		String prefixKey = key;

		if (isArray) {
			int length = key.length();
			prefixKey = "\\" + key.substring(0, length - 1) + "\\" + key.substring(length - 1, length);
			key = key.substring(1, length - 1);
		}
		Pattern p = Pattern.compile(TraceLogConstants.REGEX_PREFIX.concat(prefixKey));
		Matcher m = p.matcher(string);
		while (m.find()) {
			getOrderPrefix.put(m.start(), new KeyPairValue(key,
					isArray ? TraceLogConstants.REGEX_TYPE_ARRAY : TraceLogConstants.REGEX_TYPE_OBJECT));
		}

		return getOrderPrefix;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws UnsupportedDataTypeException
	 * @throws NullPointerException
	 */
	public static String[] convertCollectionTypeToString(Object object)
			throws UnsupportedDataTypeException, NullPointerException {

		if (object instanceof Map) {
			Iterator<Map.Entry<Object, Object>> collectionMap = ((Map) object).entrySet().iterator();
			while (collectionMap.hasNext()) {
				Map.Entry<Object, Object> obj = collectionMap.next();
				System.out.println(obj);
			}
		} else if (object instanceof List || object instanceof Set || object instanceof Queue) {
			Iterator iterator = object.iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				System.out.println(obj);
			}
		} else {
			throw new UnsupportedDataTypeException(
					"Not support for type " + collection.getClass().getName() + " collection.");
		}

		return null;
	}

	/**
	 * 
	 * @param argument
	 * @return
	 * @throws UnsupportedDataTypeException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static <E> String[] convertElementArrayTypeToString(E[] arguments)
			throws UnsupportedDataTypeException, NullPointerException, NoSuchFieldException, IllegalAccessException {

		ArrayList<String> response = new ArrayList<>();

		for (E argument : arguments) {
			// object
			if (argument == null) {
				response.add("null");
				continue;
			}
			if (!TraceLogUtils.isJavaLangObject(argument)) {
				response.add(convertObjectToString(argument));
			} else {
				if (argument instanceof Integer) {
					response.add(Integer.toString((Integer) argument));
				} else if (argument instanceof Float) {
					response.add(Float.toString((Float) argument));
				} else if (argument instanceof Double) {
					response.add(Double.toString((Double) argument));
				} else if (argument instanceof String) {
					response.add((String) argument);
				} else if (argument instanceof Character) {
					response.add(Character.toString((Character) argument));
				} else if (argument instanceof Boolean) {
					response.add(Boolean.toString((Boolean) argument));
				} else {
					throw new UnsupportedDataTypeException(
							"Unsupported this DataType: " + argument.getClass().getName().toLowerCase());
				}
			}
		}

		return response.toArray(new String[response.size()]);
	}

	/**
	 * 
	 * @param argument
	 * @return
	 * @throws UnsupportedDataTypeException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public static <E> String[] convertElementTypeToString(E argument)
			throws UnsupportedDataTypeException, NullPointerException, NoSuchFieldException, IllegalAccessException {

		ArrayList<String> response = new ArrayList<>();
		if (argument == null) {
			response.add("null");
		}
		if (!TraceLogUtils.isJavaLangObject(argument)) {
			response.add(convertObjectToString(argument));
		} else {
			if (argument instanceof Integer) {
				response.add(Integer.toString((Integer) argument));
			} else if (argument instanceof Float) {
				response.add(Float.toString((Float) argument));
			} else if (argument instanceof Double) {
				response.add(Double.toString((Double) argument));
			} else if (argument instanceof String) {
				response.add((String) argument);
			} else if (argument instanceof Character) {
				response.add(Character.toString((Character) argument));
			} else if (argument instanceof Boolean) {
				response.add(Boolean.toString((Boolean) argument));
			} else {
				throw new UnsupportedDataTypeException(
						"Unsupported this DataType: " + argument.getClass().getName().toLowerCase());
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
			res = res.append(open + value[index].concat(close).concat(", "));
		}

		return res.append(open + value[value.length - 1] + close).toString();
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
			res = object instanceof Double;
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
			res = res.append(TraceLogUtils.joinString(value, "😈\\(", "\\)😈"));
			break;
		case TraceLogConstants.REGEX_TYPE_ARGUMENT:
			res = res.append(TraceLogUtils.joinString(value, "😊\\{", "\\}😊"));
			break;
		case TraceLogConstants.REGEX_TYPE_NUMBER:
			res = res.append(TraceLogUtils.joinString(value, "😎\\$\\{", "\\}😎"));
			break;
		case TraceLogConstants.REGEX_TYPE_STRING:
			res = res.append(TraceLogUtils.joinString(value, "😉\"", "\"😉"));
			break;
		case TraceLogConstants.REGEX_TYPE_CHAR:
			res = res.append(TraceLogUtils.joinString(value, "😵'", "'😵"));
			break;
		case TraceLogConstants.REGEX_TYPE_OBJECT:
			res = res.append(TraceLogUtils.joinString(value, "😈\\@\\{", "\\}😈"));
			break;
		case TraceLogConstants.REGEX_TYPE_BOOLEAN:
			res = res.append(TraceLogUtils.joinString(value, "😄<<", ">>😄"));
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
		int i, fieldLength = fields.length, position = 0;
		Object[] transferObjectListToArray = null;
		boolean isArray;

		for (i = 0; i < fieldLength; i++) {
			key = fields[i].getName().toString();
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
						if (collection instanceof List || collection instanceof Set || collection instanceof Queue) {
							Iterator iterator = collection.iterator();
							while (iterator.hasNext()) {
								Object obj = iterator.next();
								transferObjectListToArray[position++] = obj;
							}
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
						if (obj != null) {
							value += obj.toString() + ", ";
						} else {
							value += "null, ";
						}
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
