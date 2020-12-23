package me.kolpa.raspberrymcspigot.impl.repository.file;

import me.kolpa.raspberrymcspigot.core.structure.InputPinStructure;
import me.kolpa.raspberrymcspigot.core.structure.OutputPinStructure;

import java.util.List;

public interface Serializer
{
	class SerializationData
	{
		public SerializationData(List<OutputPinStructure> outputPinStructures, List<InputPinStructure> inputPinStructures)
		{
			this.outputPinStructures = outputPinStructures;
			this.inputPinStructures = inputPinStructures;
		}

		public List<OutputPinStructure> outputPinStructures;
		public List<InputPinStructure> inputPinStructures;
	}
	
	String serialize(SerializationData data);
	SerializationData deSerialize(String data);
}
