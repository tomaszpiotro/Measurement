package arduinoMeasurement.transmission;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import arduinoMeasurement.controller.events.ControllerEvent;
import arduinoMeasurement.controller.events.DisconnectEvent;
import arduinoMeasurement.controller.events.NewProbeEvent;
import arduinoMeasurement.mockup.SettingsMockup;
import arduinoMeasurement.model.ConnectionSettings;

public class SerialTransmission implements Transmission
{
	private final BlockingQueue<ControllerEvent> queue;
	private ConnectionSettings connectionSettings;
	private SerialPort serialPort;
	
	public SerialTransmission(final BlockingQueue<ControllerEvent> queue)
	{
		this.queue = queue;
		this.connectionSettings = new ConnectionSettings();
	}
	
	public void changeSettings(final SettingsMockup settingsMockup)
	{
		this.connectionSettings.setSettings(settingsMockup);
	}
	
	@Override
	public void connect() throws ConnectionErrorException
	{
		try
		{
            serialPort = new SerialPort(SerialPortList.getPortNames()[0]);
            try
			{
                serialPort.openPort();
				serialPort.setParams(connectionSettings.getBaudRate().getValue(), connectionSettings.getDataBits().getBits(),
                        connectionSettings.getStopBits().getStopBits(), connectionSettings.isParity() ? 1 : 0);
				int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
	            serialPort.setEventsMask(mask);//Set mask
	            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
			}
			catch (SerialPortException e) 
			{
				throw new ConnectionErrorException();
			}
		}
		catch(IndexOutOfBoundsException i)
		{
			throw new ConnectionErrorException();
		}
	}
	
	public String[] getSerialPortsNames()
	{
		return SerialPortList.getPortNames();
	}

	private class SerialPortReader implements SerialPortEventListener 
	{
        private Pattern pattern = Pattern.compile("[\\w]:\\d+.\\d+");
        String string = "";
        int counter = 0;

        public void serialEvent(SerialPortEvent event) 
        {
            if(event.isRXCHAR())
            {//If data is available
                int length = event.getEventValue();

                {//Check bytes count in the input buffer
                    //Read data, if 10 bytes available
                    try
                    {
                        string += serialPort.readString(length);
                        Matcher m = pattern.matcher(string);
                        System.out.println(string);
                        if(m.find() && string.contains("\n"))
                        {
                            if(counter == 0)
                            {
                                counter++;
                                string = "";
                                return;
                            }
                            System.out.println("-------------------FOUND------------------------");
                            System.out.println(string);
                            String[] parts;
                            parts = string.split("\\n");
                            if(parts.length > 1)
                            {
                                String[] list = Arrays.copyOfRange(parts, 1, parts.length);
                                string = StringUtils.join(Arrays.asList(list), "\n");
                            }
                            else
                            {
                                string = "";
                            }
                            queue.add(new NewProbeEvent(parts[0]));
                        }
                    }
                    catch (SerialPortException ex) 
                    {
                        System.out.println(ex);
                    }
                }
            }
            else if(event.isCTS())
            {//If CTS line has changed state
                if(event.getEventValue() != 1)
                {
                	queue.add(new DisconnectEvent());
                }
            }
            else if(event.isDSR())
            {///If DSR line has changed state
                if(event.getEventValue() == 1)
                {//If line is OFF
                    queue.add(new DisconnectEvent());
                }
            }
        }
    }

	@Override
	public String[] getPossibleConnections()
	{
		return SerialPortList.getPortNames();
	}
	
	@Override
	public void closeConnection()
	{
		try
		{
            if(serialPort != null)
            {
                serialPort.closePort();
            }
		}
		catch (SerialPortException e)
		{
            System.out.println("error - port not opened");
		}
	}
}
