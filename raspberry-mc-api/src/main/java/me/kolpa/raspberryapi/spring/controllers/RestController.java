package me.kolpa.raspberryapi.spring.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberryapi.impl.Pi4JRaspberry;
import me.kolpa.raspberryapi.spring.dto.GpioPinDto;
import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.PinState;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
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
	
	private RestController()
	{
		InMemoryUnitOfWorkFactory memoryUnitOfWorkFactory = new InMemoryUnitOfWorkFactory();
		RaspberryServiceAdapter raspberryServiceAdapter = new RaspberryServiceAdapter(new MockRaspberry());
		
		getOutputPinInteractor = new GetOutputPinInteractor(raspberryServiceAdapter, memoryUnitOfWorkFactory);
		updateOutputPinInteractor = new UpdateOutputPinInteractor(memoryUnitOfWorkFactory, raspberryServiceAdapter);
	}


	@GetMapping("/output-pins")
	public List<GpioPinDto> getAll() throws Exception
	{
		return getOutputPinInteractor.getAll().stream().map(GpioPinDto::new).collect(Collectors.toList());
	}

	@GetMapping("/output-pins/{pinId}")
	public ResponseEntity<GpioPinDto> getById(@PathVariable("pinId") int pinId)
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
	public ResponseEntity<GpioPinDto> updateById(@PathVariable("pinId") int pinId,  @RequestBody UpdateRequest body)
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
}
