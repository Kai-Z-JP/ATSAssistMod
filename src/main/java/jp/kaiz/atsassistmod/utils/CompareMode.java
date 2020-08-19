package jp.kaiz.atsassistmod.utils;

public enum CompareMode {
	EQUAL(0, "=="),
	GREATER_THAN(1, ">"),
	GREATER_EQUAL(2, ">="),
	LESS_THAN(3, "<"),
	LESS_EQUAL(4, "<="),
	NOT_EQUAL(5, "!=");

	public final int id;
	public final String name;

	CompareMode(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static CompareMode getType(int id) {
		for (CompareMode type : CompareMode.values()) {
			if (type.id == id) {
				return type;
			}
		}
		return EQUAL;
	}
}
