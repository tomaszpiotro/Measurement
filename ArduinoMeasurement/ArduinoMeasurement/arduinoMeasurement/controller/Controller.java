package arduinoMeasurement.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import arduinoMeasurement.controller.events.ChangeSettingsEvent;
import arduinoMeasurement.controller.events.ConnectEvent;
import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.controller.events.DisconnectEvent;
import arduinoMeasurement.controller.events.GetSerialPortsEvent;
import arduinoMeasurement.controller.events.NewProbeEvent;
import arduinoMeasurement.controller.events.ShowConnectionSettingsEvent;
import arduinoMeasurement.controller.events.ShowLogEvent;
import arduinoMeasurement.mockup.SingleProbeMockup;
import arduinoMeasurement.model.Model;
import arduinoMeasurement.model.NoLastProbeExeption;
import arduinoMeasurement.transmission.ConnectionErrorException;
import arduinoMeasurement.transmission.SerialTransmission;
import arduinoMeasurement.transmission.Transmission;
import arduinoMeasurement.view.View;

public class Controller 
{
	private final BlockingQueue<ControllerEvent> queue;
	private final Model model;
	private final View view;
	private final Map<Class<?extends ControllerEvent>, ControllerReactionStrategy > strategyMap;
	private Transmission transmission;
	
	public Controller(final BlockingQueue<ControllerEvent> queue, final Model model, final View view)
	{
		this.queue = queue;
		this.model = model;
		this.view = view;
		HashMap<Class<?extends ControllerEvent>, ControllerReactionStrategy> map = new HashMap<>();
		map.put(ConnectEvent.class, new ConnectStrategy());
		map.put(NewProbeEvent.class, new NewProbeStrategy());
		map.put(ShowLogEvent.class, new ShowLogStrategy());
		map.put(ShowConnectionSettingsEvent.class, new ShowConnectionSettingsStrategy());
		map.put(ChangeSettingsEvent.class, new ChangeSettingsStrategy());
		map.put(GetSerialPortsEvent.class, new GetSerialPortsStrategy());
		map.put(DisconnectEvent.class, new DisconnectReactionStrategy());
		strategyMap = Collections.unmodifiableMap(map);
	}
	
	private abstract class ControllerReactionStrategy
	{
		public abstract void react(final ControllerEvent event);
	}
	
	private class DisconnectReactionStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(final ControllerEvent event)
		{
			if(transmission != null)
			{
				transmission.closeConnection();
			}
			view.setConnected(false);
			view.addNewLogMessage("Disconnected");
		}
	}
	
	private class GetSerialPortsStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(final ControllerEvent event)
		{
			if(transmission != null)
			{
				view.setSerialPortsNames(transmission.getPossibleConnections());
			}
		}	
	}
	
	private class ChangeSettingsStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(ControllerEvent event)
		{
			ChangeSettingsEvent settingsEvent = (ChangeSettingsEvent) event;
			model.changeSettings(settingsEvent.getSettingsMockup());
		}
	}
	
	private class ShowConnectionSettingsStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(ControllerEvent event)
		{
			view.showConnectionSettingsWindow(model.BuildSettingsMockup());	
		}	
	}
	
	private class ShowLogStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(ControllerEvent event)
		{
			view.showLog();
		}
	}
	
	private class ConnectStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(final ControllerEvent event)
		{
			try
			{
				if(!model.BuildSettingsMockup().isWireless())
				{
					if(transmission != null)
					{
						transmission.closeConnection();
					}
					transmission = new SerialTransmission(queue);
					transmission.connect();
					view.setConnected(true);
				}
			} 
			catch (ConnectionErrorException e)
			{
				System.out.println(e);
				System.out.println("Connection refused");
				view.setConnected(false);
			}
		}
	}
	
	private class NewProbeStrategy extends ControllerReactionStrategy
	{
		@Override
		public void react(final ControllerEvent event)
		{
			NewProbeEvent probeEvent = (NewProbeEvent) event;
			String[] splitted = probeEvent.getData().split(":");
			try
			{
				Float value = Float.parseFloat(splitted[1]);
				String seriesName = splitted[0];
				model.addProbe(value, seriesName);
				try
				{
					SingleProbeMockup singleProbeMockup = model.BuildLastProbeMockup(seriesName);
					view.addSingleProbe(singleProbeMockup);
				}
				catch(NoLastProbeExeption e){}
				view.addNewLogMessage(probeEvent.getData());
			}
			catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
			{
				view.addNewLogMessage("ERROR -> " + probeEvent.getData());
			}
		}
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				ControllerEvent event = queue.take();
				strategyMap.get(event.getClass()).react(event);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
		
			}
		}
	}
}
