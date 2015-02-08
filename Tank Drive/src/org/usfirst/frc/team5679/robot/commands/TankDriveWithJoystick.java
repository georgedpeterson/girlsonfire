package org.usfirst.frc.team5679.robot.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * Handles driving with a joystick
 *
 */
public class TankDriveWithJoystick extends CommandBase {
	Joystick wibblyWobblyDrive = new Joystick(0);
	
	// Constructor.
	public TankDriveWithJoystick() {
		requires(drive);
	}
	
	// Initializes the class.
	@Override
	protected void initialize() {
		
	}
	
	// Executes the movement.
	@Override
	public void execute() {
		// Retrieve values from joysticks
		double LP = -wibblyWobblyDrive.getRawAxis(1);
		double RP = -wibblyWobblyDrive.getRawAxis(5);
		
		// Joystick error correction
		if (Math.abs(LP) < .1) {
			LP = 0;

			if (Math.abs(RP) < .1) {
				RP = 0;
			}
		}
		
		drive.drive(LP, RP);
	}

	// Returns whether movement is finished.
	@Override
	public boolean isFinished() {
		return false;
	}

	// Ends the movement.
	@Override
	public void end() {
		drive.drive(0, 0);
	}

	// Interrupts the movement.
	@Override
	public void interrupted() {
		end();
	}
}
