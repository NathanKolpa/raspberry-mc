package me.kolpa.raspberrymcspigot.impl.repository.file;

import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.domain.OutputPinStructureRepository;
import me.kolpa.raspberrymcspigot.impl.repository.memory.domain.InMemoryOutputPinStructureRepository;

import java.io.*;

public class FileUnitOfWork implements UnitOfWork
{
	private final Serializer serializer;
	private final File file;
	private final InMemoryOutputPinStructureRepository outputPinStructures;

	public FileUnitOfWork(Serializer serializer, File file, InMemoryOutputPinStructureRepository outputPinStructures)
	{
		this.serializer = serializer;
		this.file = file;
		this.outputPinStructures = outputPinStructures;
	}

	@Override
	public OutputPinStructureRepository outputPinStructures()
	{
		return outputPinStructures;
	}
	
	@Override
	public void save()
	{
		ensureFileExists();

		try (PrintStream out = new PrintStream(new FileOutputStream(file.getAbsoluteFile())))
		{
			String data = serializer.serialize(new Serializer.SerializationData(outputPinStructures.getValues()));
			out.print(data);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void ensureFileExists()
	{
		if (!file.exists())
		{
			if (!file.getParentFile().exists())
			{
				if (!file.getParentFile().mkdirs())
					throw new RuntimeException("Cannot create directory");
			}

			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				throw new RuntimeException("Cannot create new file");
			}
		}
	}
}
