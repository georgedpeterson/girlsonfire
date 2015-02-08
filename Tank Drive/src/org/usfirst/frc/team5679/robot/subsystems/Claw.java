package org.usfirst.frc.team5679.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * Represents a claw and its movement.
 *
 */
public class Claw extends Subsystem {
	CANTalon uppieDownie0=new CANTalon(2);
	CANTalon uppieDownie1=new CANTalon(3);
		
	DigitalInput limitSwitchTop = new DigitalInput(4);
	DigitalInput limitSwitchBottom = new DigitalInput(5);
	
	Relay clawRelay = new Relay(0);
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

	// Moves the claw up.
	public void clawUp(double speed) {
		if(!isUpperLimit()){
			uppieDownie0.set(speed);
	    	uppieDownie1.set(speed);		
		}
	}
	
	// Moves the claw down.
	public void clawDown(double speed) {
		if(!isLowerLimit()){
			uppieDownie0.set(speed);
	    	uppieDownie1.set(speed);
		}
	}
	
	// Stops the claw.
	public void clawStop() {
		uppieDownie0.set(0);
    	uppieDownie1.set(0);
	}
	
	// Checks upper limit switch.
	public boolean isUpperLimit() {
		return limitSwitchTop.get();
	}
	
	// Checks lower limit switch.
	public boolean isLowerLimit() {
		return limitSwitchBottom.get();
	}
	
	// Opens the claw.
	public void clawOpen(){
		clawRelay.set(Relay.Value.kForward);
	}
	
	// Closes the claw.
	public void clawClose(){
		clawRelay.set(Relay.Value.kOff);
	}
}
