package me.kolpa.raspberrymcspigot.core.repository;

import me.kolpa.raspberrymcspigot.core.repository.domain.InputPinStructureRepository;
import me.kolpa.raspberrymcspigot.core.repository.domain.OutputPinStructureRepository;

public interface RepositoryCollection
{
	OutputPinStructureRepository outputPinStructures();
	InputPinStructureRepository inputPinStructures();
}
