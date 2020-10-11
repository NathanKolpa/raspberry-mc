package me.kolpa.raspberrymcspigot.core.structure.sign;

public class SignField
{
	private boolean isValid = true;
	private String value;

	public SignField(String value)
	{
		this.value = value;
	}

	public boolean isValid()
	{
		return isValid;
	}

	public void setValid(boolean valid)
	{
		isValid = valid;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
