package org.usfirst.frc.team5679.robot.commands;

import org.usfirst.frc.team5679.robot.subsystems.Claw;
import org.usfirst.frc.team5679.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command {
	
	protected Claw claw = new Claw();
	protected DriveTrain drive = new DriveTrain();
	
	public CommandBase() {
		super();
	}
	
	CommandBase(String name) {
		super(name);
	}

}
