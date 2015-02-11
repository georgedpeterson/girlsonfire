
package org.usfirst.frc.team5679.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
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
	static final double Kp = .03;
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	
	Encoder rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
	Encoder leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);
	
	boolean runOnce = true;
	boolean reverse = false;
	
	int stepToPerform = 0;
	double speedFactor = 0.5;
	
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
		gyro.setSensitivity(.007);
		gyro.setPIDSourceParameter(PIDSourceParameter.kAngle);
	} 

    /**
     * This function is called periodically during autonomous control
     */
    public void autonomousPeriodic() {
		double angle = gyro.getAngle();	
		boolean nextStep = false;
		
		switch(stepToPerform) {
			case 0: 
				nextStep = moveBase(2, .1, angle);
				break;
//			case 1:
//				nextStep = turnBase(.1, 359);
//				break;
//			case 1:
//				nextStep = moveCarriage(.1);
//			case 2:
//				nextStep = controlClaw(true);
//				break;
//			case 3:
//				nextStep = controlClaw(false);
//				break;
		}
	    
		if (nextStep) {
			stepToPerform++;
		}                 
        
    	SmartDashboard.putNumber("Angle", angle);
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getDistance());
    }
    
    /**
     * This function is for moving forward a set number of feet. 
     * Returns a boolean indicating whether the movement is complete.
     */
    public boolean moveBase (double feet, double speed, double angle){
    	if(rightEncoder.getDistance() >= feet || leftEncoder.getDistance() >= feet){
    		drive.drive(0, 0);
    		return true;
    	}else{
    		drive.drive(speed, angle * Kp); 
    		return false;
    	}   
	}
    
    /**
     * This function is for turning the base at a given speed and angle. 
     * Returns a boolean indicating whether the movement is complete.
     */
    public boolean turnBase(double speed, double desiredAngle) {
    	double currentAngle = gyro.getAngle();
    	double angleDifference = currentAngle - startingAngle;
    	if((angleDifference > 0 && angleDifference <= desiredAngle)
    			|| (angleDifference < 0 && angleDifference >= desiredAngle)) {
			moveBase(0, speed, desiredAngle);
			return false;
		} else {
			moveBase(0, 0, 0);
			return true;
		} 
    }
        
    /**
     * This function is for moving the carriage.
     */
    public boolean moveCarriage (double speed){
    	SmartDashboard.putString("Function", "Entered moveCarriage");
    	boolean moveValid = true;
    	SmartDashboard.putBoolean("limitSwitchTop", limitSwitchTop.get());
    	SmartDashboard.putBoolean("limitSwitchBottom", limitSwitchBottom.get());
    	
        if(speed > 0 && limitSwitchTop.get())
        {
        	SmartDashboard.putString("Function", "speed > 0");
        	moveValid = false;
        }else if (speed < 0 && limitSwitchBottom.get())
        {
        	SmartDashboard.putString("Function", "speed < 0");
        	moveValid = false;
        }
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(moveValid)
        {
        	SmartDashboard.putString("Function", "Setting Speed");
        	uppieDownie0.set(speed);
        	uppieDownie1.set(speed);
        }else
        {
        	SmartDashboard.putString("Function", "Setting Speed to 0");
        	uppieDownie0.set(0);  
            uppieDownie1.set(0); 
        }
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        SmartDashboard.putBoolean("moveValid", moveValid);
        SmartDashboard.putString("Function", "Leaving moveCarriage");
        return !moveValid;
    }
    
    /**
     * This function is for opening and closing the claw.
     */ 
    public boolean controlClaw (boolean open){
        if (open) {
        	clawRelay.set(Relay.Value.kForward); 
        	return true;
        } else {
        	clawRelay.set(Relay.Value.kOff);
        	return true;
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

        
        if(Math.abs(LP)<.1){
        	LP=0;
        	
	        if(Math.abs(RP)<.1){
	        	RP=0;
	        }
        }
        
        setRobotDriveSpeed(drive, LP, RP);
        
        if(Math.abs(UD)<.1){
        	UD=0;
        } 
        
        if(UD > 0 && limitSwitchTop.get())
        {
        	moveValid = false;
        }else if (UD < 0 && limitSwitchBottom.get())
        {
        	moveValid = false;
        }
        
        if(moveValid)
        {
        	setCANTalonSpeed(uppieDownie0,UD);
        	setCANTalonSpeed(uppieDownie1,UD);
        }else
        {
        	setCANTalonSpeed(uppieDownie0,0);  
            setCANTalonSpeed(uppieDownie1,0); 
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
    
    /**
     * This method sets the speed and applies the limiting speed factor for CANTalons
     * @param motor
     * @param speed
     */
    //TODO: ADD ACCELERATION CODE
    public void setCANTalonSpeed(CANTalon motor, double speed)
    {
    	motor.set(speed * speedFactor);
    }
    
    /**
     * This method sets the speed and applies the limiting speed factor for robot Tank Drive
     * @param driveTrain
     * @param leftSpeed
     * @param rightSpeed
     */
    //TODO: ADD ACCELERATION CODE
    public void setRobotDriveSpeed(RobotDrive driveTrain, double leftSpeed, double rightSpeed)
    {
    	driveTrain.tankDrive(leftSpeed * speedFactor, rightSpeed * speedFactor);
    	
    }
}
