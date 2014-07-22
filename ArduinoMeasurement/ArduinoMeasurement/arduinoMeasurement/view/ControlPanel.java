package arduinoMeasurement.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import arduinoMeasurement.controller.events.ConnectEvent;
import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.controller.events.GetSerialPortsEvent;
import arduinoMeasurement.controller.events.NewProbeEvent;
import arduinoMeasurement.controller.events.ShowConnectionSettingsEvent;
import arduinoMeasurement.controller.events.ShowLogEvent;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = -3003134913308139714L;
	private final BlockingQueue<ControllerEvent> queue;
	private final ConnectButton connectButton;
	private final JButton hidePanelButton;
	private final JTabbedPane tabbedPanel;
	private final JButton testButton; //TODO remove
	private final JButton showLogButton;
	private final JButton connectionSettingsButton;
	
	public ControlPanel(final BlockingQueue<ControllerEvent> queue, final JTabbedPane tabbedPanel)
	{
		super();
		this.queue = queue;
		this.tabbedPanel = tabbedPanel;
		this.setLayout(new MigLayout());
		connectButton = new ConnectButton();
		addConnectListener();
		this.add(connectButton);
		hidePanelButton = new JButton("Show/Hide Panel");
		addHideButtonListener();
		this.add(hidePanelButton);
		showLogButton = new JButton("Show log");
		this.add(showLogButton);
		addLogListener();
		testButton = new JButton("test");
		addTestListener();
		this.add(testButton);
		this.connectionSettingsButton = new JButton("Connection settings");
		addConnectionSettingsButtonListener();
		this.add(connectionSettingsButton);
	}

	
	private void addConnectionSettingsButtonListener()
	{
		this.connectionSettingsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				queue.add(new GetSerialPortsEvent());
				queue.add(new ShowConnectionSettingsEvent());
			}
		});
	}


	private void addHideButtonListener()
	{
		hidePanelButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(final ActionEvent arg0)
			{
				GraphPanel selectedPanel = (GraphPanel)tabbedPanel.getSelectedComponent();
				if(selectedPanel != null)
				{
					selectedPanel.changeControlPanelVisibility();
				}
			}
		});
	}
	
	private void addConnectListener()
	{
		connectButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				connectButton.setConnecting();
				queue.add(new ConnectEvent());
			}
		});
	}
	
	private void addTestListener()
	{
		testButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				queue.add(new NewProbeEvent("temperature:36.6:"));
			}
		});
	}
	
	private void addLogListener()
	{
		showLogButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				queue.add(new ShowLogEvent());
			}
		});
	}
	
	void setConnected(final boolean connected)
	{
		if(connected)
		{
			connectButton.setReconect();
		}
		else
		{
			connectButton.setConnect();
		}
	}

}
