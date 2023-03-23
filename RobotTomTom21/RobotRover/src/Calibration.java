//Calibration du senseur de 8 position

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.MindsensorsLineLeader;
import lejos.utility.Delay;

public class Calibration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MindsensorsLineLeader lineleader = new MindsensorsLineLeader(SensorPort.S1);
		Delay.msDelay(1000);
		LCD.drawString("Start Calibration",0,0);
		Delay.msDelay(1000);
		lineleader.wakeUp();
		Delay.msDelay(1000);
		lineleader.calibrateBlack();
		Delay.msDelay(2000);
		LCD.drawString("Calibration Done",0,0);
		Sound.setVolume(50);
		Sound.twoBeeps();
	}

}
