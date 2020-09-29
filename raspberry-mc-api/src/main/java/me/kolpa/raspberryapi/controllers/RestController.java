package me.kolpa.raspberryapi.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.kolpa.raspberryapi.dto.GpioPinDto;
import me.kolpa.raspberrymclib.core.model.GpioPin;
import me.kolpa.raspberrymclib.core.model.PinState;
import me.kolpa.raspberrymclib.core.repository.UnitOfWork;
import me.kolpa.raspberrymclib.core.repository.UnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.repository.inmemory.InMemoryUnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.repository.inmemory.domain.InMemoryGpioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController
{
	private UnitOfWorkFactory unitOfWorkFactory;

	private RestController()
	{
		InMemoryUnitOfWorkFactory memoryUnitOfWorkFactory = new InMemoryUnitOfWorkFactory();
		unitOfWorkFactory = memoryUnitOfWorkFactory;

		for (int i = 0; i < 7; i++)
		{
			memoryUnitOfWorkFactory.getGpioRepository().add(new GpioPin(i, PinState.Low));
		}
	}


	@GetMapping("/gpio-pins")
	public List<GpioPinDto> getAll() throws Exception
	{
		try (UnitOfWork unitOfWork = unitOfWorkFactory.create())
		{
			List<GpioPin> pins = unitOfWork.gpioPins().getAll();

			return pins.stream().map(GpioPinDto::new).collect(Collectors.toList());
		}
	}

	@GetMapping("/gpio-pins/{pinId}")
	public ResponseEntity<GpioPinDto> getById(@PathVariable("pinId") int pinId) throws Exception
	{
		try (UnitOfWork unitOfWork = unitOfWorkFactory.create())
		{
			GpioPin gpioPin = unitOfWork.gpioPins().getById(pinId);

			if (gpioPin == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.ok(new GpioPinDto(gpioPin));
		}
	}

	public static class UpdateRequest
	{
		@JsonProperty("pin_state")
		public String pinState;
	}

	@PutMapping("/gpio-pins/{pinId}")
	public ResponseEntity<GpioPinDto> updateById(@PathVariable("pinId") int pinId,  @RequestBody UpdateRequest body) throws Exception
	{
		try (UnitOfWork unitOfWork = unitOfWorkFactory.create())
		{
			GpioPin gpioPin = unitOfWork.gpioPins().getById(pinId);

			if (gpioPin == null)
				return ResponseEntity.notFound().build();

			PinState newState;
			switch (body.pinState)
			{
				case "HIGH": newState = PinState.High; break;
				case "LOW": newState = PinState.Low; break;
				default:
					return ResponseEntity.unprocessableEntity().build();
			}
			
			gpioPin.setPinState(newState);

			return ResponseEntity.ok(new GpioPinDto(gpioPin));
		}
	}
}
