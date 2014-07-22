package arduinoMeasurement.view;

import javax.swing.JButton;

public class ConnectButton extends JButton
{
	private static final long serialVersionUID = -6931797506928535865L;
	private final static String connect = "Connect";
	private final static String connecting = "Connecting";
	private final static String reconnect = "Reconnect";
	
	public ConnectButton()
	{
		super();
		this.setText(connect);
	}
	
	void setConnecting()
	{
		this.setText(connecting);
	}
	
	void setReconect()
	{
		this.setText(reconnect);
	}
	
	void setConnect()
	{
		this.setText(connect);
	}

}
