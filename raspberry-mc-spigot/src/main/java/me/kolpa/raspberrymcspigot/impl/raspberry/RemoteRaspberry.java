package me.kolpa.raspberrymcspigot.impl.raspberry;

import me.kolpa.raspberrymclib.core.model.OutputPin;
import me.kolpa.raspberrymcspigot.core.raspberry.Raspberry;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteRaspberry implements Raspberry
{
	
	private List<OutputPin> pins = new ArrayList<>();
	private ExecutorService executor = Executors.newCachedThreadPool();


	public void fetchState() throws IOException
	{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet("http://localhost:8080/output-pins/");
		HttpResponse response = httpClient.execute(getRequest);

		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		JSONArray root = new JSONArray(responseString);

		for(int i = 0; i < root.length(); i++)
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
	
	public void shutdown()
	{
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
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpPut putRequest = new HttpPut("http://localhost:8080/output-pins/" + pin.getPinId());
				StringEntity params = new StringEntity("{\"input_strength\":" + pin.getInputSignalLevel() + "}", ContentType.APPLICATION_JSON);
				putRequest.setEntity(params);
				HttpResponse response = httpClient.execute(putRequest);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}
}
