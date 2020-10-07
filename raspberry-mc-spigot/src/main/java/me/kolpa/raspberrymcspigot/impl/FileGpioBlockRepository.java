package me.kolpa.raspberrymcspigot.impl;

import com.google.gson.Gson;
import me.kolpa.raspberrymcspigot.repository.GpioBlockRepository;
import me.kolpa.raspberrymcspigot.world.GpioBlock;
import me.kolpa.raspberrymcspigot.world.SavedBlock;
import me.kolpa.raspberrymcspigot.world.PinSign;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileGpioBlockRepository implements GpioBlockRepository
{
	private static Gson gson = new Gson();
	public static File dataFile = new File("plugins/raspberry-mc/saved_blocks.json");

	private ArrayList<GpioBlock> values = new ArrayList<>();

	@Override
	public void add(GpioBlock block)
	{
		values.add(block);
	}

	@Override
	public void remove(GpioBlock block)
	{
		values.remove(block);
	}

	@Override
	public List<GpioBlock> getAll()
	{
		return new ArrayList<>(values);
	}

	@Override
	public void save()
	{
		ensureFileExistence();

		try (PrintStream out = new PrintStream(new FileOutputStream(dataFile.getAbsoluteFile())))
		{
			String json = gson.toJson(mapToRoot());
			out.print(json);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void load()
	{
		ensureFileExistence();

		try
		{
			JsonRoot root = gson.fromJson(new FileReader(dataFile), JsonRoot.class);

			for (JsonBlockRoot blockRoot : root.blocks)
			{
				World world = Bukkit.getWorld(UUID.fromString(blockRoot.world));
				Block block = world.getBlockAt(blockRoot.block.x, blockRoot.block.y, blockRoot.block.z);
				Block sign = world.getBlockAt(blockRoot.sign.x, blockRoot.sign.y, blockRoot.sign.z);
				PinSign pinSign = new PinSign(blockRoot.pin);

				values.add(new GpioBlock(block, pinSign, sign));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private JsonRoot mapToRoot()
	{
		JsonRoot root = new JsonRoot();
		root.blocks = new ArrayList<>();

		for (SavedBlock block : values)
		{
			JsonBlockRoot blockRoot = new JsonBlockRoot();
			blockRoot.pin = block.getPin();
			blockRoot.world = block.getWorldUuid();
			blockRoot.block = mapToBlock(block.getBlockPosition());
			blockRoot.sign = mapToBlock(block.getSignPosition());

			root.blocks.add(blockRoot);
		}

		return root;
	}

	private JsonBlock mapToBlock(SavedBlock.BlockPosition position)
	{
		JsonBlock block = new JsonBlock();
		block.x = position.x;
		block.y = position.y;
		block.z = position.z;
		return block;
	}


	private void ensureFileExistence()
	{
		if (!dataFile.exists())
		{
			if (!dataFile.getParentFile().exists())
			{
				if (!dataFile.getParentFile().mkdirs())
					throw new RuntimeException("Cannot create directory");
			}

			try
			{
				dataFile.createNewFile();
			}
			catch (IOException e)
			{
				throw new RuntimeException("Cannot create new file");
			}
		}
	}

	private static class JsonBlock
	{
		int x, y, z;
	}

	private static class JsonBlockRoot
	{
		JsonBlock block;
		JsonBlock sign;
		int pin;
		String world;
	}

	private static class JsonRoot
	{
		List<JsonBlockRoot> blocks = new ArrayList<>();
	}
}
