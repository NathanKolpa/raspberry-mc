package me.kolpa.raspberrymcspigot;

import org.bukkit.ChatColor;

public class GpioSign
{
	private boolean hasValidPin = true;
	private int pin;

	public GpioSign(int pin)
	{
		this.pin = pin;
	}
	
	public static GpioSign fromData(SignData data)
	{
		int pin;
		
		try
		{
			pin = Integer.parseInt(data.getData().get("pin"));
		}
		catch (Exception e)
		{
			GpioSign gpioSign = new GpioSign(-1);
			gpioSign.hasValidPin = false;
			return gpioSign;
		}
		
		return new GpioSign(pin);
	}

	public String[] createColorText()
	{
		String[] lines = new String[2];
		
		lines[0] = ChatColor.BOLD + "[" + getStatusColor() + "GPIO" + ChatColor.BLACK + "]" + ChatColor.RESET;
		
		if(hasValidPin)
			lines[1] = ChatColor.ITALIC + "pin = " + pin + ChatColor.RESET;
		else 
			lines[1] = ChatColor.BOLD + "" + ChatColor.RED + "pin = INVALID" + ChatColor.RESET;
		
		return lines;
	}
	
	public boolean isValid()
	{
		return hasValidPin;
	}
	
	private ChatColor getStatusColor()
	{
		if(!isValid())
			return ChatColor.RED;
		
		return ChatColor.GREEN;
	}
}
