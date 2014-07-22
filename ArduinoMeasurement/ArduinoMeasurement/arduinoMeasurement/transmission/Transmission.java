package arduinoMeasurement.transmission;

public interface Transmission 
{
	public void connect() throws ConnectionErrorException;
	public String[] getPossibleConnections();
	public void closeConnection();

}
