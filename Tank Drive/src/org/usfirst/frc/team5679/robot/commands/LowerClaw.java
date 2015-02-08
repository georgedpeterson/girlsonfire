package org.usfirst.frc.team5679.robot.commands;

/**
 * 
 * Handles Lowering the claw.
 *
 */
public class LowerClaw extends CommandBase {
	double speed;
	
	// Constructor
	public LowerClaw(double speed) {
		if (speed >= 0) {
			throw new IllegalArgumentException("Speed must be negative to move down.");
		}
		requires(claw);
		this.speed = speed;
	}
	
	// Initializes the class.
	@Override
	protected void initialize() {

	}

	// Executes the movement.
	@Override
	public void execute() {
		claw.clawDown(speed);
	}

	// Returns whether the movement is finished.
	@Override
	public boolean isFinished() {
		return claw.isLowerLimit();
	}

	// Ends the movement.
	@Override
	public void end() {
		claw.clawStop();
	}

	// Interrupts the movement.
	@Override
	public void interrupted() {
		end();
	}
}
