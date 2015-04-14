package arduinoMeasurement.transmission;

public enum BaudRate
{
    b9600(9600),
    b57600(57600);

    private final int value;

    private BaudRate(int value){this.value = value;}
    public int getValue(){return value;}
}
