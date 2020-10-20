package me.kolpa.raspberryapi.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberryapi.dto.GpioPinDto;
import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.repository.inmemory.InMemoryUnitOfWorkFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController
{
	private final UnitOfWorkFactory unitOfWorkFactory;

	private RestController()
	{
		InMemoryUnitOfWorkFactory memoryUnitOfWorkFactory = new InMemoryUnitOfWorkFactory();
		unitOfWorkFactory = memoryUnitOfWorkFactory;

		for (int i = 0; i < 7; i++)
		{
			memoryUnitOfWorkFactory.getOutputPinRepository().add(new OutputPin(i, 0));
		}
	}


	@GetMapping("/output-pins")
	public List<GpioPinDto> getAll() throws Exception
	{
		try (UnitOfWork unitOfWork = unitOfWorkFactory.create())
		{
			List<OutputPin> pins = unitOfWork.outputPins().getAll();

			return pins.stream().map(GpioPinDto::new).collect(Collectors.toList());
		}
	}

	@GetMapping("/output-pins/{pinId}")
	public ResponseEntity<GpioPinDto> getById(@PathVariable("pinId") int pinId) throws Exception
	{
		try (UnitOfWork unitOfWork = unitOfWorkFactory.create())
		{
			OutputPin outputPin = unitOfWork.outputPins().getById(pinId);

			if (outputPin == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.ok(new GpioPinDto(outputPin));
		}
	}

	public static class UpdateRequest
	{
		@JsonProperty("input_strength")
		public int strength;
	}

	@PutMapping("/output-pins/{pinId}")
	public ResponseEntity<GpioPinDto> updateById(@PathVariable("pinId") int pinId,  @RequestBody UpdateRequest body) throws Exception
	{
		try (UnitOfWork unitOfWork = unitOfWorkFactory.create())
		{
			OutputPin outputPin = unitOfWork.outputPins().getById(pinId);

			if (outputPin == null)
				return ResponseEntity.notFound().build();
			
			try
			{
				outputPin.setInputSignalLevel(body.strength);
			}
			catch (IllegalArgumentException e)
			{
				return ResponseEntity.unprocessableEntity().build();
			}
			

			return ResponseEntity.ok(new GpioPinDto(outputPin));
		}
	}
}
