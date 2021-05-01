package conquer.enums;

public enum CategoryEnum {

	SALVINI("SALVINI", "Salviniano"), RENZI("RENZI", "Renziano");

	private String code;
	private String text;

	CategoryEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String getCode() {
		return this.code;
	}

	public static CategoryEnum fromString(String text) {
		for (CategoryEnum b : CategoryEnum.values()) {
			if (b.text.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}

	public static CategoryEnum fromCode(String code) {
		for (CategoryEnum b : CategoryEnum.values()) {
			if (b.code.equalsIgnoreCase(code)) {
				return b;
			}
		}
		return null;
	}

}
