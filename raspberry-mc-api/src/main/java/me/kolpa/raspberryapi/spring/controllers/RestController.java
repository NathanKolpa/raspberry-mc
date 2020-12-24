package me.kolpa.raspberryapi.spring.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kolpa.raspberryapi.impl.Pi4JRaspberry;
import me.kolpa.raspberryapi.spring.dto.GpioPinDto;
import me.kolpa.raspberryapi.spring.dto.InputPinDto;
import me.kolpa.raspberrymclib.core.model.ButtonSensorInputPin;
import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.TemperatureSensorInputPin;
import me.kolpa.raspberrymclib.core.usecases.GetInputPinInteractor;
import me.kolpa.raspberrymclib.core.usecases.GetOutputPinInteractor;
import me.kolpa.raspberrymclib.core.usecases.UpdateOutputPinInteractor;
import me.kolpa.raspberrymclib.core.usecases.exceptions.EntityNotFoundException;
import me.kolpa.raspberrymclib.impl.repository.inmemory.InMemoryUnitOfWorkFactory;
import me.kolpa.raspberrymclib.impl.service.MockRaspberry;
import me.kolpa.raspberrymclib.impl.service.RaspberryServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Controller
@org.springframework.web.bind.annotation.RestController
public class RestController
{
	@Autowired
	private SimpMessagingTemplate webSocket;

	private final GetOutputPinInteractor getOutputPinInteractor;
	private final UpdateOutputPinInteractor updateOutputPinInteractor;
	private final GetInputPinInteractor getInputPinInteractor;
	private final RaspberryServiceAdapter raspberryServiceAdapter;

	private RestController()
	{
		InMemoryUnitOfWorkFactory memoryUnitOfWorkFactory = new InMemoryUnitOfWorkFactory();
		raspberryServiceAdapter = new RaspberryServiceAdapter(new Pi4JRaspberry());

		raspberryServiceAdapter.getOutputPins().add(new OutputPin(1, 0));
		raspberryServiceAdapter.getOutputPins().add(new OutputPin(2, 0));
		raspberryServiceAdapter.getOutputPins().add(new OutputPin(3, 0));
		raspberryServiceAdapter.getOutputPins().add(new OutputPin(4, 0));

		raspberryServiceAdapter.getTemperatureSensors().add(new TemperatureSensorInputPin(7, 20, 25, 20));
		raspberryServiceAdapter.getButtonSensorInputPins().add(new ButtonSensorInputPin(10, false));

		getOutputPinInteractor = new GetOutputPinInteractor(raspberryServiceAdapter, memoryUnitOfWorkFactory);
		updateOutputPinInteractor = new UpdateOutputPinInteractor(memoryUnitOfWorkFactory, raspberryServiceAdapter);
		getInputPinInteractor = new GetInputPinInteractor(memoryUnitOfWorkFactory, raspberryServiceAdapter);


		raspberryServiceAdapter.setOnUpdate(inputPin ->
		{
			try
			{
				ObjectMapper objectMapper = new ObjectMapper();
				webSocket.convertAndSend("/topic/input-pins", objectMapper.writeValueAsString(new InputPinDto(inputPin)));
			}
			catch (JsonProcessingException e)
			{
				e.printStackTrace();
			}
		});
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup()
	{
		raspberryServiceAdapter.updateAllOutput();
	}

	@Scheduled(fixedRate = 250)
	public void reportCurrentTime()
	{
		raspberryServiceAdapter.pollForUpdates();
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
	public ResponseEntity<GpioPinDto> updateOutputById(@PathVariable("pinId") int pinId, @RequestBody UpdateRequest body)
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
