package tracelog.phamlinh.example.utils;

public class TraceLogConstants {

	public final static String ANSI_RESET = "\u001B[0m";

	public final static String ANSI_RED = "\u001B[31m";
	public final static String ANSI_BLUE = "\u001B[34m";
	public final static String ANSI_CYAN = "\u001B[36m";

	public final static String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public final static String REGEX_VALUE = "va"; // ()
	public final static String REGEX_TYPE_VALUE = "Value";

	public final static String REGEX_ARGUMENT = "ag"; // {}
	public final static String REGEX_TYPE_ARGUMENT = "Argument";

	public final static String REGEX_NUMBER = "n(?:(\\d+)?\\.(\\d+)?)?"; // ${}
	public final static String REGEX_TYPE_NUMBER = "Number";

	public final static String REGEX_FLOAT_NUMBER = "n(?:(\\d+)?\\.(\\d+)?)?f"; // ${}
	public final static String REGEX_TYPE_FLOAT_NUMBER = "Float";

	public final static String REGEX_DOUBLE_NUMBER = "n(?:(\\d+)?\\.(\\d+)?)?d"; // ${}
	public final static String REGEX_TYPE_DOUBLE_NUMBER = "Double";
	
	public final static String REGEX_LONG_NUMBER = "n(?:(\\d+)?\\.(\\d+)?)?l"; // ${}
	public final static String REGEX_TYPE_LONG_NUMBER = "Long";

	public final static String REGEX_STRING = "s"; // ""
	public final static String REGEX_TYPE_STRING = "String";

	public final static String REGEX_CHAR = "c"; // ''
	public final static String REGEX_TYPE_CHAR = "Character";

	public final static String REGEX_OBJECT = "ob"; // @{}
	public final static String REGEX_TYPE_OBJECT = "Object";

	public final static String REGEX_BOOLEAN = "bl"; // << >>
	public final static String REGEX_TYPE_BOOLEAN = "Boolean";

	public final static String REGEX_ARRAY_OPEN_PARRENTHESES = "[";// "😢「"; // 「 value �?
	public final static String REGEX_ARRAY_CLOSE_PARRENTHESES = "]";// "�?😢";

	public final static String REGEX_TYPE_ARRAY = "Array";

	public final static String PREFIX_ARRAY_OPEN_PARRENTHESES = "{";
	public final static String PREFIX_ARRAY_CLOSE_PARRENTHESES = "}";

	public final static String REGEX_PREFIX = "\\%\\%";

	public final static String[] REGEX_LIST = { REGEX_VALUE, REGEX_ARGUMENT, REGEX_CHAR, REGEX_OBJECT, REGEX_BOOLEAN,
			REGEX_NUMBER, REGEX_STRING, REGEX_FLOAT_NUMBER, REGEX_TYPE_DOUBLE_NUMBER, REGEX_LONG_NUMBER };
	public final static String[] REGEX_TYPE = { REGEX_TYPE_VALUE, REGEX_TYPE_ARGUMENT, REGEX_TYPE_CHAR,
			REGEX_TYPE_OBJECT, REGEX_TYPE_BOOLEAN, REGEX_TYPE_NUMBER, REGEX_TYPE_STRING, REGEX_TYPE_FLOAT_NUMBER,
			REGEX_TYPE_DOUBLE_NUMBER, REGEX_TYPE_LONG_NUMBER };

	/**
	 *  check parameter on list
	 */
	public final static String COLLECTION_TYPE_LIST = "List";

	public final static String COLLECTION_TYPE_SET = "Set";

	public final static String COLLECTION_TYPE_MAP = "Map";

	public final static String COLLECTION_TYPE_QUEUE = "Queue";
	
	public final static String COLLECTION_TYPE_VECTOR = "Vector";

	public final static String COLLECTION_TYPE_STACK = "Stack";
	
	

}
