package org.usfirst.frc.team5679.robot.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {

	private final double motorSpeed = 0.5;
	
	//TODO: When our switches are plugged in enter the actual channel #
	DigitalInput upperLimit = new DigitalInput(4);
	Talon motor = new Talon(4);
	Counter upCounter = new Counter(upperLimit);
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	public void clawUp() {
		motor.set(motorSpeed);
	}
	
	public void clawDown() {
		motor.set(-motorSpeed);
	}
	
	public void clawStop() {
		motor.set(0);
	}
	
	public boolean isUpperLimitSet() {
		return upCounter.get() > 0;
	}
	
	public void resetUpperLimitCount() {
		upCounter.reset();
	}
}
