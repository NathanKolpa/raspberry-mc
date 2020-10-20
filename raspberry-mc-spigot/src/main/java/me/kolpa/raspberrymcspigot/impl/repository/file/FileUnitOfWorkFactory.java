package me.kolpa.raspberrymcspigot.impl.repository.file;

import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymcspigot.impl.repository.memory.domain.InMemoryOutputPinStructureRepository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUnitOfWorkFactory implements UnitOfWorkFactory
{
	private final InMemoryOutputPinStructureRepository outputPinStructures = new InMemoryOutputPinStructureRepository();
	private final Serializer serializer;
	private final File file = new File("plugins/rapsberry-mc/.saved.json");

	public static FileUnitOfWorkFactory readFile(Serializer serializer)
	{
		FileUnitOfWorkFactory fileUnitOfWorkFactory = new FileUnitOfWorkFactory(serializer);
		fileUnitOfWorkFactory.read();
		return fileUnitOfWorkFactory;
	}
	
	public FileUnitOfWorkFactory(Serializer serializer)
	{
		this.serializer = serializer;
	}
	
	public void read()
	{
		if(!file.exists())
			return;
		
		try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			String fileContents = new String(encoded, StandardCharsets.UTF_8);
			
			Serializer.SerializationData data = serializer.deSerialize(fileContents);
			outputPinStructures.setValues(data.outputPinStructures);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public UnitOfWork create()
	{
		return new FileUnitOfWork(serializer, file, outputPinStructures);
	}
}
