package tracelog.phamlinh.example.object;

public class RegexCondition {

	private String regex; // $${ag}
	private boolean isArray; // true else object
	private String type;// argument
	private String signalPrefix; // ag
	private String format;

	private String naturePath;// only using for number
	private String decimal;

	public RegexCondition() {
	}

	/**
	 * @param regex
	 * @param isArray
	 * @param type
	 * @param signalPrefix
	 * @param format
	 */
	public RegexCondition(String regex, boolean isArray, String type, String signalPrefix, String format) {
		super();
		this.regex = regex;
		this.isArray = isArray;
		this.type = type;
		this.signalPrefix = signalPrefix;
		this.format = format;
	}

	/**
	 * @param regex
	 * @param isArray
	 * @param type
	 * @param signalPrefix
	 * @param format
	 * @param naturePath
	 * @param decimal
	 */
	public RegexCondition(String regex, boolean isArray, String type, String signalPrefix, String format,
			String naturePath, String decimal) {
		super();
		this.regex = regex;
		this.isArray = isArray;
		this.type = type;
		this.signalPrefix = signalPrefix;
		this.format = format;
		this.naturePath = naturePath;
		this.decimal = decimal;
	}

	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * @param regex
	 *            the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

	/**
	 * @return the isArray
	 */
	public boolean isArray() {
		return isArray;
	}

	/**
	 * @param isArray
	 *            the isArray to set
	 */
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the signalPrefix
	 */
	public String getSignalPrefix() {
		return signalPrefix;
	}

	/**
	 * @param signalPrefix
	 *            the signalPrefix to set
	 */
	public void setSignalPrefix(String signalPrefix) {
		this.signalPrefix = signalPrefix;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the naturePath
	 */
	public String getNaturePath() {
		return naturePath;
	}

	/**
	 * @param naturePath
	 *            the naturePath to set
	 */
	public void setNaturePath(String naturePath) {
		this.naturePath = naturePath;
	}

	/**
	 * @return the decimal
	 */
	public String getDecimal() {
		return decimal;
	}

	/**
	 * @param decimal
	 *            the decimal to set
	 */
	public void setDecimal(String decimal) {
		this.decimal = decimal;
	}

}
