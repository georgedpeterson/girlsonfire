
package org.usfirst.frc.team5679.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.LinkedList;

import org.usfirst.frc.team5679.robot.subsystems.DriveTrain;
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
	Talon uppieDownie0=new Talon(4);
	Talon uppieDownie1=new Talon(5);
	Joystick wibblyWobbly=new Joystick(0);

	StopWatch sw = new StopWatch();

	Encoder rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
	Encoder leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);
	
	boolean busy = false;
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	rightEncoder.setDistancePerPulse(Math.PI * 0.5 * 360);
    	leftEncoder.setDistancePerPulse(Math.PI * 0.5 * 360);

    	sw.start();
    }
    
    /**
     * This function sets up any necessary data before the autonomous control loop.
     */
    public void autonomousinit() {
    			
	}

    /**
     * This function is called periodically during autonomous control
     */
    public void autonomousPeriodic() {
    	Encoder encoder = new Encoder(1,2,false,EncodingType.k4X);
    	double distance = encoder.getDistance();
    	DriveTrain train = new DriveTrain();
    	while(sw.getElapsedTimeSecs() < 1){
    		SmartDashboard.putNumber("Distance", distance);
    		
    		leftie0.set(.2);
            leftie1.set(.2);
            rightie0.set(.2);
            rightie1.set(.2);
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        double LP=-wibblyWobbly.getRawAxis(1);
        double RP=wibblyWobbly.getRawAxis(5);
        
        if(Math.abs(LP)<.1){
        	LP=0;
        	
	        if(Math.abs(RP)<.1){
	        	RP=0;
	        }
        }
        leftie0.set(LP);
        leftie1.set(LP);
        rightie0.set(RP);
        rightie1.set(RP); 
        
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getDistance());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
