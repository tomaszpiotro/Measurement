#include <Time.h>
#include "DHT.h"

#define DHTTYPE DHT22 
#define DHTPIN 2

DHT dht(DHTPIN, DHTTYPE);
float temp;
int tempIn = 0;

void setup()  
{ 
  analogReference(DEFAULT);
  Serial.begin(57600);
  dht.begin();

} 

void loop()  
{ 
  printTemp();
  delay(50);
  printDHT();
  delay(500);                 
}


void printTemp()
{
  Serial.print("Temperature:");
  temp = analogRead(tempIn);
  temp = temp*5/1024.0;
  temp = temp - 0.5;
  temp = temp / 0.01;
  Serial.println(temp);
}

void printDHT()
{
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  
  if (isnan(h) || isnan(t))
  {
    return;
  }

  Serial.print("Humidity:"); 
  Serial.println(h);
  Serial.print("Temperature (DHT):"); 
  Serial.println(t);
}

