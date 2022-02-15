package me.kolpa.raspberrymcspigot.core.structure.sign;

import java.util.HashMap;
import java.util.Map;

public class SignData
{
	private String header;
	private final Map<String, String> data;

	public SignData(String header, Map<String, String> data)
	{
		this.header = header;
		this.data = data;
	}

	public static SignData parse(String[] lines)
	{
		if (lines.length < 1)
			return null;

		String header = parseHeader(lines[0]);

		if(header == null || header.length() == 0)
			return null;

		Map<String, String> map = parseData(lines);

		if(map == null)
			return null;

		return new SignData(header, map);
	}

	private static Map<String, String> parseData(String[] lines)
	{
		HashMap<String, String> map = new HashMap<>();

		for(int i = 1; i < lines.length; i++)
		{
			String line = lines[i];

			if(line.length() == 0)
				continue;

			String[] splitted = line.split("=");

			if(splitted.length != 2)
				return null;

			map.put(splitted[0].trim(), splitted[1].trim());
		}

		return map;
	}

	private static String parseHeader(String headerLine)
	{
		for(int i = 1, n = headerLine.length() - 1; i < n ; i++) {
			char character = headerLine.charAt(i);

			if(character == '[' || character == ']')
				return null;
		}

		if(!headerLine.startsWith("[") || !headerLine.endsWith("]"))
			return null;

		return headerLine.substring(1, headerLine.length() - 1);
	}

	public String getHeader()
	{
		return header;
	}

	public void setHeader(String header)
	{
		this.header = header;
	}

	public Map<String, String> getData()
	{
		return data;
	}
}
