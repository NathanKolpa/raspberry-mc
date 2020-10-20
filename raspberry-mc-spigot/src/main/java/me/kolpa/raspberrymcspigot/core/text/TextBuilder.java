package me.kolpa.raspberrymcspigot.core.text;

public interface TextBuilder
{
	TextBuilder addEffect(TextEffect effect);
	TextBuilder resetEffects();
	TextBuilder appendText(String text);
	TextBuilder newLine();
	
	String[] build();
}
