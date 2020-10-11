package me.kolpa.raspberrymcspigot.impl.repository.file;

import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;

import java.util.List;

public interface Serializer
{
	class SerializationData
	{
		public SerializationData(List<OutputPinStructure> outputPinStructures)
		{
			this.outputPinStructures = outputPinStructures;
		}

		public List<OutputPinStructure> outputPinStructures;
	}
	
	String serialize(SerializationData data);
	SerializationData deSerialize(String data);
}
