package tracelog.phamlinh.example.utils;

public class TraceLogConstants {

	/**
	 * set color for console
	 */
	public final static String ANSI_RESET = "\u001B[0m";

	public final static String ANSI_RED = "\u001B[31m";
	public final static String ANSI_BLUE = "\u001B[34m";
	public final static String ANSI_CYAN = "\u001B[36m";

	public final static String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	/**
	 * regex type check for string parameter
	 */
	public final static String REGEX_VALUE = "va"; // ()
	public final static String REGEX_TYPE_VALUE = "Value";

	public final static String REGEX_ARGUMENT = "ag"; // {}
	public final static String REGEX_TYPE_ARGUMENT = "Argument";

	public final static String REGEX_NUMBER = "n(?:(\\d+)?\\.(\\d+))?(?:d|f)?"; // ${}
	public final static String REGEX_TYPE_NUMBER = "Number";

	public final static String REGEX_TYPE_LONG_NUMBER = "Long";

	public final static String REGEX_TYPE_SHORT_NUMBER = "Short";

	public final static String REGEX_STRING = "s"; // ""
	public final static String REGEX_TYPE_STRING = "String";

	public final static String REGEX_CHAR = "c"; // ''
	public final static String REGEX_TYPE_CHAR = "Character";

	public final static String REGEX_OBJECT = "ob"; // @{}
	public final static String REGEX_TYPE_OBJECT = "Object";

	public final static String REGEX_BYTE = "by";// &()
	public final static String REGEX_TYPE_BYTE = "Byte";

	public final static String REGEX_BITSET = "bi";// &()
	public final static String REGEX_TYPE_BITSET = "BitSet";

	public final static String REGEX_BOOLEAN = "bl"; // << >>
	public final static String REGEX_TYPE_BOOLEAN = "Boolean";

	public final static String REGEX_ARRAY_OPEN_PARRENTHESES = "#[";// "😢「"; // 「 value �?
	public final static String REGEX_ARRAY_CLOSE_PARRENTHESES = "]#";// "�?😢";

	public final static String REGEX_TYPE_ARRAY = "Array";

	public final static String PREFIX_ARRAY_OPEN_PARRENTHESES = "{";
	public final static String PREFIX_ARRAY_CLOSE_PARRENTHESES = "}";

	public final static String REGEX_PREFIX = "\\%\\%";

	public final static String[] REGEX_LIST = { REGEX_VALUE, REGEX_ARGUMENT, REGEX_CHAR, REGEX_OBJECT, REGEX_BOOLEAN,
			REGEX_NUMBER, REGEX_STRING, REGEX_BYTE, REGEX_BITSET };

	public final static String[] REGEX_TYPE = { REGEX_TYPE_VALUE, REGEX_TYPE_ARGUMENT, REGEX_TYPE_CHAR,
			REGEX_TYPE_OBJECT, REGEX_TYPE_BOOLEAN, REGEX_TYPE_NUMBER, REGEX_TYPE_STRING, REGEX_TYPE_BYTE,
			REGEX_TYPE_BITSET };

	/**
	 * java collection type (not include map)
	 */
	public final static String COLLECTION_TYPE_LIST = "List";

	public final static String COLLECTION_TYPE_SET = "Set";

	public final static String COLLECTION_TYPE_MAP = "Map";
	
	public final static String COLLECTION_TYPE_DICTIONARY = "Dictionary";

	public final static String COLLECTION_TYPE_QUEUE = "Queue";

	public final static String COLLECTION_TYPE_VECTOR = "Vector";

	public final static String COLLECTION_TYPE_STACK = "Stack";

	public final static String COLLECTION_TYPE_DEQUEUE = "Dequeue";

	public final static String COLLECTION_TYPE_SORTEDSET = "SortedSet";

	/**
	 * java primitive type
	 */
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
	 * folder storage data
	 */
	public static final String FOLDER_STORAGE_LOG = "\\log\\";

	public static final String LOG_PROJECT_STRUCTURE = "logProjectStructure";

	public static final String LOG_FILE_FORMAT = "yyyy-MM-dd";

}
