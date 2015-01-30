package org.usfirst.frc.team5679.robot.commands;

public class RaiseClaw extends CommandBase {

	public RaiseClaw() {
		requires(claw);
	}
	
	@Override
	protected void initialize() {
		claw.resetUpperLimitCount();

	}

	@Override
	protected void execute() {
		claw.clawUp();

	}

	@Override
	protected boolean isFinished() {
		return claw.isUpperLimitSet();
	}

	@Override
	protected void end() {
		claw.clawStop();

	}

	@Override
	protected void interrupted() {
		end();
	}

}
