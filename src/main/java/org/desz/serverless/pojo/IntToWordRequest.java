package org.desz.serverless.pojo;

public class IntToWordRequest {
	private int number;
	private String lang;

	public IntToWordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public String toString() {
		return "IntToWordRequest [number=" + number + ", lang=" + lang + "]";
	}

}
