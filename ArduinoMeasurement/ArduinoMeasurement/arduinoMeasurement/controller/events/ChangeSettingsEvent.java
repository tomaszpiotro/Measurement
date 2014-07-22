package arduinoMeasurement.controller.events;

import arduinoMeasurement.mockup.SettingsMockup;

public class ChangeSettingsEvent extends ControllerEvent
{
	private final SettingsMockup settingsMockup;
	
	public ChangeSettingsEvent(final SettingsMockup settingsMockup)
	{
		this.settingsMockup = settingsMockup;
	}

	public SettingsMockup getSettingsMockup()
	{
		return settingsMockup;
	}

}
