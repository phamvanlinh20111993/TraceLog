/**
 * 
 */
package tracelog.phamlinh.example.object;

import java.util.List;

/**
 * @author PhamVanLinh
 *
 */
public class KeyPairValue {

	private String value;
	private String type;
	private String key;
	
	private Integer[] test;

	private RegexCondition testObject;
	
	private RegexCondition[] testLisObject;
	
	private List<Integer> testCollectionObject;
	
	private List<RegexCondition> testListCollectionObject;
	
	/**
	 * 
	 */
	public KeyPairValue() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param key
	 * @param value
	 */
	public KeyPairValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;

	}
	
	

	/**
	 * @param value
	 * @param key
	 * @param testLisObject
	 */
	public KeyPairValue(String value, String key, RegexCondition[] testLisObject) {
		this.value = value;
		this.key = key;
		this.testLisObject = testLisObject;
	}

	/**
	 * @param value
	 * @param key
	 * @param testObject
	 */
	public KeyPairValue(String value, String key, RegexCondition testObject) {
		super();
		this.value = value;
		this.key = key;
		this.testObject = testObject;
	}

	/**
	 * @param key
	 * @param value
	 * @param type
	 */
	public KeyPairValue(String key, String value, String type) {
		super();
		this.key = key;
		this.value = value;
		this.type = type;
	}

	/**
	 * @param value
	 * @param type
	 * @param key
	 * @param test
	 */
	public KeyPairValue(String value, String type, String key, Integer[] test) {
		super();
		this.value = value;
		this.type = type;
		this.key = key;
		this.test = test;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @return the test
	 */
	public Integer[] getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(Integer[] test) {
		this.test = test;
	}

	/**
	 * @return the testObject
	 */
	public RegexCondition getTestObject() {
		return testObject;
	}

	/**
	 * @param testObject the testObject to set
	 */
	public void setTestObject(RegexCondition testObject) {
		this.testObject = testObject;
	}

	/**
	 * @return the testLisObject
	 */
	public RegexCondition[] getTestLisObject() {
		return testLisObject;
	}

	/**
	 * @param testLisObject the testLisObject to set
	 */
	public void setTestLisObject(RegexCondition[] testLisObject) {
		this.testLisObject = testLisObject;
	}

	/**
	 * @param testListCollectionObject
	 */
	public KeyPairValue(List<RegexCondition> testListCollectionObject) {
		super();
		this.testListCollectionObject = testListCollectionObject;
	}

	/**
	 * @param testCollectionObject
	 */
	public KeyPairValue(String value, List<Integer> testCollectionObject) {
		super();
		this.value = value;
		this.testCollectionObject = testCollectionObject;
	}

	/**
	 * @return the testCollectionObject
	 */
	public List<Integer> getTestCollectionObject() {
		return testCollectionObject;
	}

	/**
	 * @param testCollectionObject the testCollectionObject to set
	 */
	public void setTestCollectionObject(List<Integer> testCollectionObject) {
		this.testCollectionObject = testCollectionObject;
	}

	/**
	 * @return the testListCollectionObject
	 */
	public List<RegexCondition> getTestListCollectionObject() {
		return testListCollectionObject;
	}

	/**
	 * @param testListCollectionObject the testListCollectionObject to set
	 */
	public void setTestListCollectionObject(List<RegexCondition> testListCollectionObject) {
		this.testListCollectionObject = testListCollectionObject;
	}
	
}
