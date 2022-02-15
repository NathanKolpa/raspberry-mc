package me.kolpa.raspberrymcspigot.impl.repository.file.json;

import com.google.gson.Gson;
import me.kolpa.raspberrymcspigot.core.structure.InputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;
import me.kolpa.raspberrymcspigot.impl.repository.file.Serializer;
import me.kolpa.raspberrymcspigot.impl.repository.file.json.file.InputPinJsonObject;
import me.kolpa.raspberrymcspigot.impl.repository.file.json.file.OutputPinJsonObject;
import me.kolpa.raspberrymcspigot.impl.repository.file.json.file.PositionJsonObject;
import me.kolpa.raspberrymcspigot.impl.repository.file.json.file.RootJsonObject;
import me.kolpa.raspberrymcspigot.impl.structure.SpigotInputPinStructure;
import me.kolpa.raspberrymcspigot.impl.structure.SpigotOutputPinStructure;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonSerializer implements Serializer
{
	private Gson gson = new Gson();
	
	@Override
	public String serialize(SerializationData data)
	{
		RootJsonObject rootJsonObject = new RootJsonObject();
		
		rootJsonObject.output_pin_structures = new ArrayList<>(data.outputPinStructures.size());
		for(OutputPinStructure outputPinStructure : data.outputPinStructures)
		{
			OutputPinJsonObject outputPinJsonObject = new OutputPinJsonObject();
			outputPinJsonObject.pin = outputPinStructure.getPinNumber();
			outputPinJsonObject.world = outputPinStructure.getWorld();
			outputPinJsonObject.block = new PositionJsonObject(outputPinStructure.getBlockPosition());
			outputPinJsonObject.sign = new PositionJsonObject(outputPinStructure.getSignPosition());
			rootJsonObject.output_pin_structures.add(outputPinJsonObject);
		}

		rootJsonObject.input_pin_structures = new ArrayList<>(data.inputPinStructures.size());
		for(InputPinStructure inputPinStructure : data.inputPinStructures)
		{
			InputPinJsonObject inputPinJsonObject = new InputPinJsonObject();
			inputPinJsonObject.pin = inputPinStructure.getPinNumber();
			inputPinJsonObject.world = inputPinStructure.getWorld();
			inputPinJsonObject.block = new PositionJsonObject(inputPinStructure.getBlockPosition());
			inputPinJsonObject.sign = new PositionJsonObject(inputPinStructure.getSignPosition());
			inputPinJsonObject.redstone = new PositionJsonObject(inputPinStructure.getRedstonePosition());
			rootJsonObject.input_pin_structures.add(inputPinJsonObject);
		}
		
		return gson.toJson(rootJsonObject);
	}

	// TODO: try to remove bukkit dependency
	@Override
	public SerializationData deSerialize(String data)
	{
		RootJsonObject rootJsonObject = gson.fromJson(data, RootJsonObject.class);
		
		List<OutputPinStructure> outputPinStructures = new ArrayList<>(rootJsonObject.output_pin_structures.size());
		for(OutputPinJsonObject jsonObject : rootJsonObject.output_pin_structures)
		{
			World world = Bukkit.getWorld(UUID.fromString(jsonObject.world));
			Block block = world.getBlockAt(jsonObject.block.x, jsonObject.block.y, jsonObject.block.z);
			Block sign = world.getBlockAt(jsonObject.sign.x, jsonObject.sign.y, jsonObject.sign.z);
			
			outputPinStructures.add(new SpigotOutputPinStructure(jsonObject.pin, jsonObject.world, block, sign));
		}

		List<InputPinStructure> inputPinStructures = new ArrayList<>(rootJsonObject.input_pin_structures.size());
		for(InputPinJsonObject jsonObject : rootJsonObject.input_pin_structures)
		{
			World world = Bukkit.getWorld(UUID.fromString(jsonObject.world));
			Block block = world.getBlockAt(jsonObject.block.x, jsonObject.block.y, jsonObject.block.z);
			Block sign = world.getBlockAt(jsonObject.sign.x, jsonObject.sign.y, jsonObject.sign.z);
			Block redstone = world.getBlockAt(jsonObject.redstone.x, jsonObject.redstone.y, jsonObject.redstone.z);

			inputPinStructures.add(new SpigotInputPinStructure(jsonObject.pin, jsonObject.world, block, sign, redstone));
		}
		
		return new SerializationData(outputPinStructures, inputPinStructures);
	}
}
