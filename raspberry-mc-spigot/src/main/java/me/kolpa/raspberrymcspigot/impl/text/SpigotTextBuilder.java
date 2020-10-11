package me.kolpa.raspberrymcspigot.impl.text;

import me.kolpa.raspberrymcspigot.core.text.TextBuilder;
import me.kolpa.raspberrymcspigot.core.text.TextEffect;
import org.bukkit.ChatColor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SpigotTextBuilder implements TextBuilder
{
	
	private List<String> lines = new ArrayList<>();
	private StringBuilder currentLine = new StringBuilder();

	@Override
	public TextBuilder resetEffects()
	{
		currentLine.append(ChatColor.RESET);
		
		return this;
	}

	@Override
	public TextBuilder appendText(String text)
	{
		currentLine.append(text);
		
		return this;
	}

	@Override
	public TextBuilder newLine()
	{
		lines.add(currentLine.toString());
		currentLine.setLength(0);
		
		return this;
	}

	@Override
	public String[] build()
	{
		List<String> actualLines = new ArrayList<>(lines);
		if(currentLine.length() > 0)
			actualLines.add(currentLine.toString());
		
		return actualLines.toArray(new String[0]);
	}
	
	@Override
	public TextBuilder addEffect(TextEffect effect)
	{
		ChatColor color;
		switch (effect)
		{
			case Red:
				color = ChatColor.RED;
				break;
			case Green:
				color = ChatColor.GREEN;
				break;
			case Yellow:
				color = ChatColor.YELLOW;
				break;
			case Aqua:
				color = ChatColor.AQUA;
				break;
			case DarkAqua:
				color = ChatColor.DARK_AQUA;
				break;
			case Black:
				color = ChatColor.BLACK;
				break;
			case DarkBlue:
				color = ChatColor.DARK_BLUE;
				break;
			case DarkGray:
				color = ChatColor.DARK_GRAY;
				break;
			case DarkGreen:
				color = ChatColor.DARK_GREEN;
				break;
			case DarkRed:
				color = ChatColor.DARK_RED;
				break;
			case Gold:
				color = ChatColor.GOLD;
				break;
			case LightPurple:
				color = ChatColor.LIGHT_PURPLE;
				break;
			case White:
				color = ChatColor.WHITE;
				break;

			case Obfuscated:
				color = ChatColor.MAGIC;
				break;
			case Strikethrough:
				color = ChatColor.STRIKETHROUGH;
				break;
			case Underline:
				color = ChatColor.UNDERLINE;
				break;
			case Italic:
				color = ChatColor.ITALIC;
				break;
			case Bold:
				color = ChatColor.BOLD;
				break;
			default:
				throw new NotImplementedException();
		}
		
		currentLine.append(color);

		return this;
	}
}
