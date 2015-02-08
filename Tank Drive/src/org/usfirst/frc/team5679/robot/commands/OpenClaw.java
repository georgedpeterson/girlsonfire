package org.usfirst.frc.team5679.robot.commands;

/**
 * 
 * Handles opening the claw.
 *
 */
public class OpenClaw extends CommandBase {
	boolean isFinished = false;
	
	// Constructor.
	public OpenClaw() {
		requires(claw);
	}
	
	// Initializes the class.
	@Override
	protected void initialize() {

	}

	// Executes the movement.
	@Override
	public void execute() {
		claw.clawOpen();
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
