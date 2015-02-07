
package org.usfirst.frc.team5679.robot;

import org.usfirst.frc.team5679.robot.commands.RaiseClaw;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Robot extends IterativeRobot {

	Talon leftie0=new Talon(2);
	Talon leftie1=new Talon(3);
	Talon rightie0=new Talon(0);
	Talon rightie1=new Talon(1);
	CANTalon uppieDownie0=new CANTalon(2);
	CANTalon uppieDownie1=new CANTalon(3);
	Joystick wibblyWobblyDrive=new Joystick(0);
	
	Joystick wibblyWobblyCarriage=new Joystick(1);
	
	RobotDrive drive = new RobotDrive(leftie0, leftie1, rightie0, rightie1);
	
	DigitalInput limitSwitchTop = new DigitalInput(4);
	DigitalInput limitSwitchBottom = new DigitalInput(5);
	
	Relay clawRelay = new Relay(0);
	
	Gyro gyro = new Gyro(0);
	double startingAngle = 0;
	static final double Kp = 1.0;
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	

	Encoder rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
	Encoder leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);
	
	boolean runOnce = true;
	boolean reverse = false;
	
	boolean busy = false;
	
	Timer timer = new Timer();
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	// 
    	rightEncoder.setDistancePerPulse(Math.PI * .5/250);
    	leftEncoder.setDistancePerPulse(Math.PI * .5/250);

		SmartDashboard.putString("robot init", "robot init");
		
		rightEncoder.reset();
		leftEncoder.reset();	
    }
    
    /**
     * This function sets up any necessary data before the autonomous control loop.
     */
    public void autonomousinit() {

		SmartDashboard.putString("autonomous init", "autonomous init");
		gyro.reset();
	}

    /**
     * This function is called periodically during autonomous control
     */
    public void autonomousPeriodic() {
		double angle = gyro.getAngle();
		moveBase(2, .2, angle);
//		if(angle - startingAngle <= 359) {
//			moveBase(0, .1, 359);
//		} else {
//			moveBase(0, 0, 0);
//		}   
//		controlClaw(true);
//		controlClaw(false);
//		rightEncoder.reset();
//		leftEncoder.reset();
//		rightEncoder.setReverseDirection(true);
//		leftEncoder.setReverseDirection(true);
//		moveBase(2, .2, angle);
		
//    	if(runOnce)
//    	{
//    		runOnce = false;
//    		startingAngle = angle;	
//    	}       	
//    	
//    		
//		if(angle - startingAngle <= 90) {
//			drive.drive(.1, 90 * Kp); 
//		} else {
//    		drive.drive(0, angle * Kp);
//		}   		                 
//        
    	SmartDashboard.putNumber("Angle", angle);
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getDistance());
    }
    
    /**
     * This function is for moving forward a set number of feet.
     */
    public void moveBase (double feet, double speed, double angle){
    	if(rightEncoder.getDistance() >= feet || leftEncoder.getDistance() >= feet){
    		drive.drive(0, 0);
    	}else{
    		drive.drive(speed, angle * Kp); 
    	}   
	}
    
    /**
     * This function is for moving the carriage.
     */
    public void moveCarriage (double speed){
    	boolean moveValid = true;
        if(speed > 0 && !limitSwitchTop.get())
        {
        	moveValid = false;
        }else if (speed < 0 && !limitSwitchBottom.get())
        {
        	moveValid = false;
        }
        
        if(moveValid)
        {
        	uppieDownie0.set(speed);
        	uppieDownie1.set(speed);
        }else
        {
        	uppieDownie0.set(0);  
            uppieDownie1.set(0); 
        }
    }
    
    /**
     * This function is for opening and closing the claw.
     */ 
    public void controlClaw (boolean open){
        if (open) {
        	clawRelay.set(Relay.Value.kForward); 
        } else {
        	clawRelay.set(Relay.Value.kOff);
        }
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double LP=-wibblyWobblyDrive.getRawAxis(1);
        double RP=-wibblyWobblyDrive.getRawAxis(5);
        double UD = wibblyWobblyCarriage.getRawAxis(1);
        boolean clawButton = wibblyWobblyCarriage.getRawButton(1);
        boolean moveValid = true;

		drive.tankDrive(LP, RP);
//	        
//	        if(Math.abs(LP)<.1){
//	        	LP=0;
//	        	
//		        if(Math.abs(RP)<.1){
//		        	RP=0;
//		        }
//	        }
//	        
//	        if(Math.abs(UD)<.1){
//	        	UD=0;
//	        }
//	        
//	        leftie0.set(LP);
//	        leftie1.set(LP);
//	        rightie0.set(RP);
//	        rightie1.set(RP); 
        
        if(UD > 0 && !limitSwitchTop.get())
        {
        	moveValid = false;
        }else if (UD < 0 && !limitSwitchBottom.get())
        {
        	moveValid = false;
        }
        
        if(moveValid)
        {
        	uppieDownie0.set(UD);
        	uppieDownie1.set(UD);
        }else
        {
        	uppieDownie0.set(0);  
            uppieDownie1.set(0); 
        }
        
        if (clawButton) {
        	clawRelay.set(Relay.Value.kForward); 
        } else {
        	clawRelay.set(Relay.Value.kOff);
        }
                

        SmartDashboard.putNumber("AccelX", accel.getX());
        SmartDashboard.putNumber("AccelY", accel.getY());
        SmartDashboard.putNumber("AccelZ", accel.getZ());
        SmartDashboard.putBoolean("Claw Button", clawButton);
        SmartDashboard.putBoolean("Upper Limit", limitSwitchTop.get());
        SmartDashboard.putBoolean("Lower Limit", limitSwitchBottom.get());
        SmartDashboard.putBoolean("Move Valid", moveValid);
        SmartDashboard.putNumber("Operator UpDown", UD);
        
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder",-1 * leftEncoder.getDistance());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
