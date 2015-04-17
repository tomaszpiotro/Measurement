package arduinoMeasurement.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import arduinoMeasurement.controller.events.ChangeSettingsEvent;
import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.mockup.SettingsMockup;
import arduinoMeasurement.transmission.BaudRate;
import arduinoMeasurement.transmission.DataBits;
import arduinoMeasurement.transmission.StopBits;

public class ConnectionSettingsWindow extends JFrame
{
	private static final long serialVersionUID = -1310225923045954845L;
	private final BlockingQueue<ControllerEvent> queue;
	private final static String windowName = "Connection settings";

    private final JLabel serialPortsLabel;
    private final JComboBox<String> serialPorts;
    private final JLabel baudRateLabel;
	private final JComboBox<BaudRate> baudRates;
	private final JLabel dataBitsLabel;
	private final JComboBox<DataBits> dataBits;
	private final JLabel StopBitsLabel;
	private final JComboBox<StopBits> stopBits;
	private final JRadioButton parityRadioButton;
	private final JButton saveButton;
	
	
	public ConnectionSettingsWindow(final BlockingQueue<ControllerEvent> queue)
	{
		super();
		this.queue = queue;
		this.setLayout(new MigLayout());
		setTitle(windowName);
		setVisible(false);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(false);

        this.serialPortsLabel = new JLabel("Serial ports:");
        add(serialPortsLabel);

		this.serialPorts = new JComboBox<>();
		add(serialPorts, "wrap");
		
		this.baudRateLabel = new JLabel("Baud Rate: ");
		add(baudRateLabel);
		this.baudRates = new JComboBox<BaudRate>(BaudRate.values());
		add(baudRates, "wrap");
		
		this.dataBitsLabel = new JLabel("Data Bits: ");
		add(dataBitsLabel);
		this.dataBits = new JComboBox<DataBits>(DataBits.values());
		add(dataBits, "wrap");
		
		this.StopBitsLabel = new JLabel("Stop Bits: ");
		add(StopBitsLabel);
		this.stopBits = new JComboBox<StopBits>(StopBits.values());
		add(stopBits, "wrap");
		
		this.parityRadioButton = new JRadioButton("Parity");
		add(parityRadioButton, "wrap");
		
		this.saveButton = new JButton("Save settings");
		addSaveButtonListener();
		add(saveButton);
		this.pack();

	}
	
	private void addSaveButtonListener()
	{
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				queue.add(new ChangeSettingsEvent(buildSettingsMockup()));
				setVisible(false);
			}
		});
	}
	
	private SettingsMockup buildSettingsMockup()
	{
		SettingsMockup mockup = new SettingsMockup(
				(BaudRate)baudRates.getSelectedItem(), (DataBits)dataBits.getSelectedItem(), 
				(StopBits)stopBits.getSelectedItem(), parityRadioButton.isSelected());
		return mockup;
	}

	void setVisible(final SettingsMockup settingsMockup)
	{
		baudRates.setSelectedItem(settingsMockup.getBaudRate());
		dataBits.setSelectedItem(settingsMockup.getDataBits());
		stopBits.setSelectedItem(settingsMockup.getStopBits());
		parityRadioButton.setSelected(settingsMockup.isParity());
		
		this.pack();
		setVisible(true);
	}
	
	public void setSerialPortsNames(final String[] serialPortNames)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				serialPorts.removeAllItems();
				for(String item: serialPortNames)
				{
					serialPorts.addItem(item);
					pack();
					repaint();
				}
			}
		});
		
	}

}
