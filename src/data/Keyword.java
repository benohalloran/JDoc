package data;

public enum Keyword {
	PUBLIC, PRIVATE, STATIC, FINAL, PROTECTED, INTERFACE, CLASS, ABSTRACT, VOLATILE, TRANSIENT;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

	public static Keyword getEnum(String s) {
		return Keyword.valueOf(s.trim().toUpperCase());
	}

}
