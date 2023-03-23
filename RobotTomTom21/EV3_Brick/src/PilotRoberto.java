

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.MindsensorsLineLeader;

import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

		public class PilotRoberto {
		
		public EV3LargeRegulatedMotor motorD = new EV3LargeRegulatedMotor(MotorPort.A);
		public EV3LargeRegulatedMotor motorG = new EV3LargeRegulatedMotor(MotorPort.B);
		public MovePilot pilot;
		public MindsensorsLineLeader lineleader;
		public double rotateSpeed = 45;
		public double distanceApproach = 110; //nécessaire pour ne pas faire de confusion de ligne
		public double distanceParcourue = 0.0;
		public double dist = 0.0;
		
		public PilotRoberto(){
			super();
			pilot = this.chass();
			lineleader = new MindsensorsLineLeader(SensorPort.S1);
		}
		
		public MovePilot chass(){
			
			Wheel wheel1 = WheeledChassis.modelWheel(motorD, 55).offset(63);
			Wheel wheel2 = WheeledChassis.modelWheel(motorG, 55).offset(-63);
			Chassis chassis = new WheeledChassis(new Wheel[]{wheel1, wheel2},WheeledChassis.TYPE_DIFFERENTIAL);
			MovePilot pilotis = new MovePilot(chassis);
			return pilotis;
		}
			
		/* getDistanceParcourue() : recupération de la distance parcourue*/
		public double getDistanceParcourue() {
			return distanceParcourue;
		}
	
		public double getDist() {
			return dist;
		}
		
		/*
		 * Rotation du robot d'un angle donne
		 * @param angle
		 */
		public void rotate(int angle) {
			pilot.setAngularSpeed(rotateSpeed);
			distanceParcourue = 0.0;
			pilot.rotate(angle); 
			distanceParcourue = pilot.getMovement().getDistanceTraveled(); 
		}

		
		/*CapteurLigne permet de donner une sortie numérique (entier) aux données brutes du capteur.
		 *centrée ==0
		 *décalé dte== négatif
		 *décalé gche== positif
		 */
		public float CapteurLigne (float[] sample){
			
			float sum = 0;
			for(int i=0; i<8; i++){
				if(sample[i] == 0){
					sample[i] = 1;
				}else{
					sample[i] = 0;
				}
			}
			
			
			sum = 8*sample[0]+4*sample[1]+2*sample[2]+1*sample[3]+(-1)*sample[4]+
				(-2)*sample[5]+(-4)*sample[6]+(-8)*sample[7];
			
			
		
			return sum;
		}
		
		/*public int CapteurLigne (float[] sample, int speed){
			int soma = 0;
			float sum = 0;
			for(int i=0; i<8; i++){
				if(sample[i] == 0){
					sample[i] = 1;
				}else{
					sample[i] = 0;
				}
			}
			
			sum = 4*sample[0]+3*sample[1]+2*sample[2]+1*sample[3]+(-1)*sample[4]+
				(-2)*sample[5]+(-3)*sample[6]+(-4)*sample[7];
			soma = (int)sum;
			if(soma <= -3){				//tourner à gauche
				soma = speed/2;
			}else if (soma >= +3){		//tourner à droite
				soma = -speed/2;
			}else{
				soma = 0;				// continuer tout droit
			}
			return soma;
		}*/
		
		
		
		public void travels(float distance, int speed) {
			float linePosition;
			float erreur;
			float kp = 10;
			float Cde ;
			//speed = 3*speed;
			float[] aux;
		
			pilot.setLinearSpeed(speed);
			pilot.setAngularSpeed(rotateSpeed);
			
			
			// créer variable pour stocker les données du senseur de couleur (blanc/noir)
			SampleProvider Miss = lineleader.getRedMode();
			float[] sample = new float[Miss.sampleSize()];
			
			lineleader.wakeUp();
			
			distanceParcourue = 0;
		//	LCD.drawString("distance = " + distanceParcourue, 0,0);
			
			motorD.suspendRegulation();
			motorG.suspendRegulation();;
			pilot.forward();
			
			while(distanceParcourue < (distance - distanceApproach)){
				
				Miss.fetchSample(sample,0); //lecture du senseur
				aux = sample; //auxiliaire par la lecture
				linePosition = this.CapteurLigne(aux); // lecture du senseur
				
				
				// Boucle de régulation Proportionnelle
				 ////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////
				//A compléter
				erreur = 0 + linePosition;
				Cde = erreur*kp;
				LCD.drawString("linepos6 = " + Cde, 0,0);
				motorD.setSpeed(speed - Cde); // speed - value in degrees/sec 
			
				motorG.setSpeed(speed + Cde); // speed - value in degrees/sec 
			//	motorD.forward();
			//	motorG.forward();
			//	motorD.setSpeed(60); // speed - value in degrees/sec 
			//	motorG.setSpeed(30); // speed - value in degrees/sec 
				 ////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////
				
				
				distanceParcourue = pilot.getMovement().getDistanceTraveled();
				
				LCD.drawString("distance = " + distanceParcourue, 0,2);
				
			}
			while(distanceParcourue < distance){
				
				motorG.setSpeed(speed); 
				motorD.setSpeed(speed);
				
				distanceParcourue = pilot.getMovement().getDistanceTraveled();
				LCD.drawString("distance = " + distanceParcourue, 0,3);
			}
			pilot.stop();		
		}
	}
		
	

