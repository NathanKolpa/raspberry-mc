package me.kolpa.raspberrymcspigot.impl;

import me.kolpa.raspberrymclib.core.model.GpioPin;
import me.kolpa.raspberrymcspigot.Raspberry;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HttpRaspberry implements Raspberry
{
	private JavaPlugin plugin;
	private HttpClient httpClient = HttpClientBuilder.create().setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE).build();

	public HttpRaspberry(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public void updatePin(GpioPin pin)
	{
		Bukkit.getScheduler().runTask(plugin, () ->
		{
			try
			{
				HttpPut putRequest = new HttpPut("http://localhost:8080/gpio-pins/" + pin.getPinId());
				StringEntity params = new StringEntity("{\"input_strength\":" + pin.getInputSignalLevel() + "}", ContentType.APPLICATION_JSON);
				putRequest.setEntity(params);
				HttpResponse response = httpClient.execute(putRequest);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public List<GpioPin> getPins()
	{

		try
		{
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("http://localhost:8080/gpio-pins/");
			HttpResponse response = httpClient.execute(getRequest);

			String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			JSONArray root = new JSONArray(responseString);

			ArrayList<GpioPin> gpioPins = new ArrayList<>();
			for(int i = 0; i < root.length(); i++)
			{
				JSONObject jsonObject = root.getJSONObject(i);
				
				int id = jsonObject.getInt("id");
				int pinNumber = jsonObject.getInt("pin_number");
				int strength = jsonObject.getInt("input_strength");
				
				GpioPin pin = new GpioPin(pinNumber, strength);
				pin.setPinId(id);
				
				gpioPins.add(pin);
			}
			
			return gpioPins;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
