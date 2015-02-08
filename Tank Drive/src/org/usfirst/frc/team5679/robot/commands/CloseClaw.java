package org.usfirst.frc.team5679.robot.commands;

/**
 * 
 * Handles closing the claw.
 *
 */
public class CloseClaw extends CommandBase {
	boolean isFinished = false;
	
	// Constructor.
	public CloseClaw() {
		requires(claw);
	}
	
	// Initializes the movement.
	@Override
	protected void initialize() {

	}

	// Executes the movement.
	@Override
	public void execute() {
		claw.clawClose();
		this.isFinished = true;
	}

	// Returns whether the movement is finished.
	@Override
	public boolean isFinished() {
		return this.isFinished;
	}

	// Ends the movement.
	@Override
	public void end() {
		claw.clawStop();
		this.isFinished = true;
	}

	// Interrupts the movement.
	@Override
	public void interrupted() {
		end();
		this.isFinished = true;
	}
}
