package me.kolpa.raspberrymcspigot.core.structure.sign;

import me.kolpa.raspberrymcspigot.core.text.TextBuilder;
import me.kolpa.raspberrymcspigot.core.text.TextBuilderFactory;
import me.kolpa.raspberrymcspigot.core.text.TextEffect;

import java.util.HashMap;
import java.util.Map;

public class InfoSign
{
	private String header;
	private boolean isConnected = true;
	private final Map<String, SignField> fields = new HashMap<>();


	public InfoSign(String header)
	{
		this.header = header;
	}
	
	public void addField(String name, SignField value)
	{
		fields.put(name, value);
	}
	
	public SignField getField(String name)
	{
		return fields.get(name);
	}
	
	public String[] createText(TextBuilderFactory builderFactory)
	{
		TextBuilder builder = builderFactory.create()
				.appendText("-:[")
				.addEffect(TextEffect.Bold)
				.addEffect(TextEffect.Underline)
				.addEffect(getHeaderColor())
				.appendText(header.toUpperCase())
				.resetEffects()
				.appendText("]:-")
				.newLine();
		
		for (Map.Entry<String, SignField> entry : fields.entrySet())
		{
			if(!entry.getValue().isValid())
				builder.addEffect(TextEffect.Bold).addEffect(TextEffect.Red);
			
			String text = entry.getValue().getValue();
			
			if(!entry.getValue().isValid())
				text = "INVALID";
			
			builder.appendText(entry.getKey() + " = " + text);

			if(!entry.getValue().isValid())
				builder.resetEffects();
			
			builder.newLine();
		}
		
		return builder.build();
	}
	
	private TextEffect getHeaderColor()
	{
		for(SignField field : fields.values())
			if(!field.isValid())
				return TextEffect.Red;
		
		
		if(!isConnected)
			return TextEffect.Yellow;
		
		return TextEffect.Green;
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public void setConnected(boolean connected)
	{
		isConnected = connected;
	}
}
