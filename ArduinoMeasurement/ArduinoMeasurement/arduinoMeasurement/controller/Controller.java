package arduinoMeasurement.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.model.Model;
import arduinoMeasurement.view.View;

public class Controller 
{
	private final BlockingQueue<ControllerEvent> queue;
	private final Model model;
	private final View view;
	private final Map<Class<?extends ControllerEvent>, ControllerReactionStrategy > strategyMap;
	
	public Controller(final BlockingQueue<ControllerEvent> queue, final Model model, final View view)
	{
		this.queue = queue;
		this.model = model;
		this.view = view;
		HashMap<Class<?extends ControllerEvent>, ControllerReactionStrategy> map = new HashMap<>();
		strategyMap = Collections.unmodifiableMap(map);
	}
	
	private abstract class ControllerReactionStrategy
	{
		public abstract void react(final ControllerEvent event);
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
