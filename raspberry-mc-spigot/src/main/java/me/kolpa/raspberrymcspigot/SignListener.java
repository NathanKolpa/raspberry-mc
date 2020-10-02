package me.kolpa.raspberrymcspigot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener
{
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getBlock())
	}
}
