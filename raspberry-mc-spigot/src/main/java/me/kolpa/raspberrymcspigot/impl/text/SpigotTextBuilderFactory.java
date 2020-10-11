package me.kolpa.raspberrymcspigot.impl.text;

import me.kolpa.raspberrymcspigot.core.text.TextBuilder;
import me.kolpa.raspberrymcspigot.core.text.TextBuilderFactory;

public class SpigotTextBuilderFactory implements TextBuilderFactory
{
	@Override
	public TextBuilder create()
	{
		return new SpigotTextBuilder();
	}
}
