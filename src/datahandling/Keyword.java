package datahandling;

public enum Keyword {
	PUBLIC,
	PRIVATE,
	STATIC,
	FINAL,
	PROTECTED,
	ABSTRACT;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
