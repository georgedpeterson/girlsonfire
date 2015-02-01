
package org.usfirst.frc.team5679.robot;

import org.usfirst.frc.team5679.robot.commands.RaiseClaw;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.Relay;
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
	//TODO: carriage joystick id
	Joystick wibblyWobblyCarriage=new Joystick(1);
	
	DigitalInput limitSwitchTop = new DigitalInput(4);
	DigitalInput limitSwitchBottom = new DigitalInput(5);
	
	Relay clawRelay = new Relay(0);
	
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
	}

    /**
     * This function is called periodically during autonomous control
     */
    public void autonomousPeriodic() {

    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(runOnce)
    	{
    		runOnce = false;
        	leftie0.set(.4);
            leftie1.set(.4);
            rightie0.set(-.4);
            rightie1.set(-.4);	
    	}   
    	
    	if (!reverse && (rightEncoder.getDistance() >= 1.5 || leftEncoder.getDistance() >=1.5)) {  
            leftie0.set(.2);
            leftie1.set(.2);
            rightie0.set(-.2);
            rightie1.set(-.2); 
    	}
    	
    	if (!reverse && (rightEncoder.getDistance() >= 3 || leftEncoder.getDistance() >=3)) {  
            leftie0.set(-.4);
            leftie1.set(-.4);
            rightie0.set(.4);
            rightie1.set(.4);             

            rightEncoder.reset();
            leftEncoder.reset();
            
            rightEncoder.setReverseDirection(true);
            leftEncoder.setReverseDirection(true);
            
            reverse = true;
        }
    	
    	if (reverse && (rightEncoder.getDistance() >= 1.5 || leftEncoder.getDistance() >=1.5)) {
    		leftie0.set(-.2);
            leftie1.set(-.2);
            rightie0.set(.2);
            rightie1.set(.2); 
    	}
    	
    	if (reverse && (rightEncoder.getDistance() >= 3 || leftEncoder.getDistance() >=3)) {
    		leftie0.set(0);
            leftie1.set(0);
            rightie0.set(0);
            rightie1.set(0); 
            
            reverse = false;
    	}                  
    	
    	     

        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder",-1 * leftEncoder.getDistance());
		
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double LP=-wibblyWobblyDrive.getRawAxis(1);
        double RP=wibblyWobblyDrive.getRawAxis(5);
        double UD = wibblyWobblyCarriage.getRawAxis(1);
        boolean clawButton = wibblyWobblyCarriage.getRawButton(1);
        boolean moveValid = true;
        
        if(Math.abs(LP)<.1){
        	LP=0;
        	
	        if(Math.abs(RP)<.1){
	        	RP=0;
	        }
        }
        
        if(Math.abs(UD)<.1){
        	UD=0;
        }
        
        leftie0.set(LP);
        leftie1.set(LP);
        rightie0.set(RP);
        rightie1.set(RP); 
        
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
        SmartDashboard.putNumber("Operator Left", LP);
        SmartDashboard.putNumber("Operator Right", RP);
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
