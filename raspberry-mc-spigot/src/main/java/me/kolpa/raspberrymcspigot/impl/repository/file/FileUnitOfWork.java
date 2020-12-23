package me.kolpa.raspberrymcspigot.impl.repository.file;

import me.kolpa.raspberrymcspigot.core.repository.UnitOfWork;
import me.kolpa.raspberrymcspigot.core.repository.domain.InputPinStructureRepository;
import me.kolpa.raspberrymcspigot.core.repository.domain.OutputPinStructureRepository;
import me.kolpa.raspberrymcspigot.impl.repository.memory.domain.InMemoryInputPinStructureRepository;
import me.kolpa.raspberrymcspigot.impl.repository.memory.domain.InMemoryOutputPinStructureRepository;

import java.io.*;

public class FileUnitOfWork implements UnitOfWork
{
	private final Serializer serializer;
	private final File file;
	private final InMemoryOutputPinStructureRepository outputPinStructures;
	private final InMemoryInputPinStructureRepository inputPinStructures;

	public FileUnitOfWork(Serializer serializer, File file, InMemoryOutputPinStructureRepository outputPinStructures, InMemoryInputPinStructureRepository inputPinStructures)
	{
		this.serializer = serializer;
		this.file = file;
		this.outputPinStructures = outputPinStructures;
		this.inputPinStructures = inputPinStructures;
	}

	@Override
	public OutputPinStructureRepository outputPinStructures()
	{
		return outputPinStructures;
	}

	@Override
	public InputPinStructureRepository inputPinStructures()
	{
		return inputPinStructures;
	}

	@Override
	public void save()
	{
		ensureFileExists();

		try (PrintStream out = new PrintStream(new FileOutputStream(file.getAbsoluteFile())))
		{
			String data = serializer.serialize(new Serializer.SerializationData(outputPinStructures.getValues(), inputPinStructures.getValues()));
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
