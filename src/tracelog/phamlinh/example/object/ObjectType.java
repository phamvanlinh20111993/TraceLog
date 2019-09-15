package tracelog.phamlinh.example.object;

import java.util.ArrayList;

public class ObjectType {
	private String type;

	private ArrayList<KeyPairValue> keyPairValue;

	public ObjectType() {

	}

	/**
	 * @param type
	 * @param keyPairValue
	 */
	public ObjectType(String type, ArrayList<KeyPairValue> keyPairValue) {
		super();
		this.type = type;
		this.keyPairValue = keyPairValue;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the keyPairValue
	 */
	public ArrayList<KeyPairValue> getKeyPairValue() {
		return keyPairValue;
	}

	/**
	 * @param keyPairValue the keyPairValue to set
	 */
	public void setKeyPairValue(ArrayList<KeyPairValue> keyPairValue) {
		this.keyPairValue = keyPairValue;
	}
}
