package me.kolpa.raspberryapi.spring.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberryapi.impl.Pi4JRaspberry;
import me.kolpa.raspberryapi.spring.dto.GpioPinDto;
import me.kolpa.raspberryapi.spring.dto.InputPinDto;
import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.PinState;
import me.kolpa.raspberrymclib.core.model.TemperatureSensorInputPin;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.usecases.GetInputPinInteractor;
import me.kolpa.raspberrymclib.core.usecases.GetOutputPinInteractor;
import me.kolpa.raspberrymclib.core.usecases.UpdateOutputPinInteractor;
import me.kolpa.raspberrymclib.core.usecases.exceptions.EntityNotFoundException;
import me.kolpa.raspberrymclib.impl.repository.inmemory.InMemoryUnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.service.MockRaspberry;
import me.kolpa.raspberrymclib.impl.service.Raspberry;
import me.kolpa.raspberrymclib.impl.service.RaspberryServiceAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController
{

	private final GetOutputPinInteractor getOutputPinInteractor;
	private final UpdateOutputPinInteractor updateOutputPinInteractor;
	private final GetInputPinInteractor getInputPinInteractor;
	
	private RestController()
	{
		InMemoryUnitOfWorkFactory memoryUnitOfWorkFactory = new InMemoryUnitOfWorkFactory();
		RaspberryServiceAdapter raspberryServiceAdapter = new RaspberryServiceAdapter(new MockRaspberry());
		
		memoryUnitOfWorkFactory.create().temperatureSensors().add(new TemperatureSensorInputPin(6, 24, 30, 20));
		
		getOutputPinInteractor = new GetOutputPinInteractor(raspberryServiceAdapter, memoryUnitOfWorkFactory);
		updateOutputPinInteractor = new UpdateOutputPinInteractor(memoryUnitOfWorkFactory, raspberryServiceAdapter);
		getInputPinInteractor = new GetInputPinInteractor(memoryUnitOfWorkFactory);
	}


	@GetMapping("/output-pins")
	public List<GpioPinDto> getAllOutput() throws Exception
	{
		return getOutputPinInteractor.getAll().stream().map(GpioPinDto::new).collect(Collectors.toList());
	}

	@GetMapping("/output-pins/{pinId}")
	public ResponseEntity<GpioPinDto> getOutputById(@PathVariable("pinId") int pinId)
	{
		try
		{
			return ResponseEntity.ok(new GpioPinDto(getOutputPinInteractor.getById(pinId)));
		}
		catch (EntityNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
	}

	public static class UpdateRequest
	{
		@JsonProperty("input_strength")
		public int strength;
	}

	@PutMapping("/output-pins/{pinId}")
	public ResponseEntity<GpioPinDto> updateOutputById(@PathVariable("pinId") int pinId,  @RequestBody UpdateRequest body)
	{
		try
		{
			return ResponseEntity.ok(new GpioPinDto(updateOutputPinInteractor.update(pinId, body.strength)));
		}
		catch (EntityNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		catch (IllegalArgumentException e)
		{
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	@GetMapping("/input-pins")
	public List<InputPinDto> getAllInput() throws Exception
	{
		return getInputPinInteractor.getAll().stream().map(InputPinDto::new).collect(Collectors.toList());
	}

	@GetMapping("/input-pins/{pinId}")
	public ResponseEntity<InputPinDto> getInputById(@PathVariable("pinId") int pinId)
	{
		try
		{
			return ResponseEntity.ok(new InputPinDto(getInputPinInteractor.getById(pinId)));
		}
		catch (EntityNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
	}
}
