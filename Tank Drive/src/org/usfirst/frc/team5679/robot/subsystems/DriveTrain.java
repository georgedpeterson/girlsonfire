package org.usfirst.frc.team5679.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	Talon rightie0=new Talon(0);
	Talon rightie1=new Talon(1);
	Talon leftie0=new Talon(2);
	Talon leftie1=new Talon(3);
	
	Encoder rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
	Encoder leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
