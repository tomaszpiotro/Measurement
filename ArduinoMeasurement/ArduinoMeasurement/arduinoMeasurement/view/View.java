package arduinoMeasurement.view;

import java.util.concurrent.BlockingQueue;

import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.mockup.SettingsMockup;
import arduinoMeasurement.mockup.SingleProbeMockup;

public class View 
{
	private final Screen screen;
	private final LogWindow logWindow;
	private final ConnectionSettingsWindow connectionSettingsWindow;
	
	public View(final BlockingQueue<ControllerEvent> queue)
	{
		this.screen = new Screen(queue);
		this.logWindow = new LogWindow();
		this.connectionSettingsWindow = new ConnectionSettingsWindow(queue);
	}
	
	public void addSingleProbe(final SingleProbeMockup singleProbeMockup)
	{
		screen.addSingleProbe(singleProbeMockup);
	}
	
	public void addNewLogMessage(final String message)
	{
		logWindow.addNewEntrance(message);
	}
	
	public void showLog()
	{
		logWindow.setVisible();
	}
	
	public void showConnectionSettingsWindow(final SettingsMockup settingsMockup)
	{
		connectionSettingsWindow.setVisible(settingsMockup);
	}
	
	public void setSerialPortsNames(final String[] serialPortNames)
	{
		connectionSettingsWindow.setSerialPortsNames(serialPortNames);
	}
	
	public void setConnected(final boolean connected)
	{
		screen.setConnected(connected);
	}
}
