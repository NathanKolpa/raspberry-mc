package me.kolpa.raspberrymcspigot.impl.raspberry;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymclib.core.model.SensorInputPin;
import me.kolpa.raspberrymcspigot.core.model.ValueSensorInputPin;
import me.kolpa.raspberrymcspigot.core.raspberry.Raspberry;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteRaspberry extends StompSessionHandlerAdapter implements Raspberry, StompFrameHandler
{
	private List<OutputPin> pins = new ArrayList<>();
	private List<ValueSensorInputPin> inputs = new ArrayList<>();

	private ExecutorService executor = Executors.newCachedThreadPool();

	private final String base = "192.168.0.124:8080";
	WebSocketStompClient stompClient = null;
	StompSession currentSession = null;
	private InputUpdateHandler updateHandler;
	
	// TODO: fix the http client

	public void connect() throws IOException
	{
		WebSocketClient client = new StandardWebSocketClient();
		stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new StringMessageConverter());

		stompClient.connect("ws://" + base + "/ws", this).addCallback(stompSession ->
		{
			currentSession = stompSession;
		}, Throwable::printStackTrace);
	}

	public void fetchState() throws IOException
	{
		fetchOutputState();
		fetchInputState();
	}

	private void fetchInputState() throws IOException
	{
		HttpClient httpClient =  HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://" + base + "/output-pins/");
		HttpResponse response = httpClient.execute(getRequest);

		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONArray root = new JSONArray(responseString);

		for (int i = 0; i < root.length(); i++)
		{
			JSONObject jsonObject = root.getJSONObject(i);

			int id = jsonObject.getInt("id");
			int pinNumber = jsonObject.getInt("pin_number");
			int strength = jsonObject.getInt("input_strength");

			OutputPin pin = new OutputPin(pinNumber, strength);
			pin.setPinId(id);

			pins.add(pin);
		}
	}

	private void fetchOutputState() throws IOException
	{
		HttpClient httpClient =  HttpClientBuilder.create().build();

		HttpGet getRequest = new HttpGet("http://" + base + "/input-pins/");
		HttpResponse response = httpClient.execute(getRequest);

		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONArray root = new JSONArray(responseString);

		for (int i = 0; i < root.length(); i++)
		{
			JSONObject jsonObject = root.getJSONObject(i);

			int id = jsonObject.getInt("id");
			int pinNumber = jsonObject.getInt("pin_number");
			int strength = jsonObject.getInt("input_strength");
			String name = jsonObject.getString("sensor_type");
			String value = jsonObject.getString("input_value");

			ValueSensorInputPin valueSensorInputPin = new ValueSensorInputPin(pinNumber, strength, name, value);
			valueSensorInputPin.setPinId(id);

			inputs.add(valueSensorInputPin);
		}
	}

	public void shutdown()
	{
		if (currentSession != null && stompClient != null)
		{
			currentSession.disconnect();
			stompClient.stop();
		}

		executor.shutdown();
	}

	@Override
	public List<OutputPin> getOutputPins()
	{
		return pins;
	}

	@Override
	public void update(OutputPin pin)
	{
		executor.submit(() ->
		{
			try
			{
				HttpClient httpClient =  HttpClientBuilder.create().build();
				HttpPut putRequest = new HttpPut("http://" + base + "/output-pins/" + pin.getPinId());
				StringEntity params = new StringEntity("{\"input_strength\":" + pin.getInputSignalLevel() + "}",
						ContentType.APPLICATION_JSON);
				putRequest.setEntity(params);
				HttpResponse response = httpClient.execute(putRequest);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<SensorInputPin> getInputPins()
	{
		return new ArrayList<>(inputs);
	}

	@Override
	public void setUpdateHandler(InputUpdateHandler updateHandler)
	{
		this.updateHandler = updateHandler;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders)
	{
		session.subscribe("/topic/input-pins", this);
	}

	@Override
	public Type getPayloadType(StompHeaders headers)
	{
		return String.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload)
	{
		String json = (String) payload;
		JSONObject jsonObject = new JSONObject(json);

		int id = jsonObject.getInt("id");
		int pinId = jsonObject.getInt("pin_number");
		int inputStrength = jsonObject.getInt("input_strength");
		String sensorType = jsonObject.getString("sensor_type");
		String value = jsonObject.getString("input_value");

		ValueSensorInputPin valueSensorInputPin = inputs.stream()
				.filter(x -> x.getPinId() == id)
				.findFirst()
				.orElse(null);

		if (valueSensorInputPin == null)
		{
			valueSensorInputPin = new ValueSensorInputPin(pinId, inputStrength, sensorType, value);
			valueSensorInputPin.setPinId(id);
			inputs.add(valueSensorInputPin);
		}
		else
		{
			valueSensorInputPin.setValue(value);
			valueSensorInputPin.setInputSignalLevel(inputStrength);
			valueSensorInputPin.setName(sensorType);
		}

		if (updateHandler != null)
			updateHandler.onUpdate(valueSensorInputPin);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception)
	{
		System.err.println(exception);
	}
}
