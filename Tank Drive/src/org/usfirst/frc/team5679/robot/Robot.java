package org.usfirst.frc.team5679.robot;

import org.usfirst.frc.team5679.robot.commands.Autonomous;
import org.usfirst.frc.team5679.robot.commands.CloseClaw;
import org.usfirst.frc.team5679.robot.commands.LowerClaw;
import org.usfirst.frc.team5679.robot.commands.OpenClaw;
import org.usfirst.frc.team5679.robot.commands.RaiseClaw;
import org.usfirst.frc.team5679.robot.commands.TankDriveWithJoystick;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {

	Talon leftie0 = new Talon(2);
	Talon leftie1 = new Talon(3);
	Talon rightie0 = new Talon(0);
	Talon rightie1 = new Talon(1);
	CANTalon uppieDownie0 = new CANTalon(2);
	CANTalon uppieDownie1 = new CANTalon(3);
	Joystick wibblyWobblyDrive = new Joystick(0);

	Joystick wibblyWobblyCarriage = new Joystick(1);

	RobotDrive robotDrive = new RobotDrive(leftie0, leftie1, rightie0, rightie1);
	TankDriveWithJoystick tankDrive = new TankDriveWithJoystick();

	Gyro gyro = new Gyro(0);
	double startingAngle = 0;
	static final double Kp = 1.0;

	BuiltInAccelerometer accel = new BuiltInAccelerometer();

	RaiseClaw raiseClaw = new RaiseClaw(.5);
	LowerClaw lowerClaw = new LowerClaw(-.5);

	Encoder rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
	Encoder leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);

	int stepToPerform = 0;
	
	Command autonomous;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		//
		rightEncoder.setDistancePerPulse(Math.PI * .5 / 250);
		leftEncoder.setDistancePerPulse(Math.PI * .5 / 250);

		SmartDashboard.putString("robot init", "robot init");

		rightEncoder.reset();
		leftEncoder.reset();
		
		autonomous = new Autonomous();
	}

	/**
	 * This function sets up any necessary data before the autonomous control
	 * loop.
	 */
	public void autonomousinit() {
		SmartDashboard.putString("autonomous init", "autonomous init");
		gyro.reset();
		autonomous.start();
	}

	/**
	 * This function is called periodically during autonomous control
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
//		double angle = gyro.getAngle();
//		boolean nextStep = false;
//
//		switch (stepToPerform) {
//		case 0:
//			nextStep = moveBase(2, .2, angle);
//			break;
//		case 1:
//			nextStep = turnBase(.1, 359);
//			break;
//		case 2:
//			nextStep = raiseCarriage(.1);
//		case 3:
//			nextStep = moveBase(2, .2, angle);
//			break;
//		case 4:
//			nextStep = openClaw();
//			break;
//		case 5:
//			nextStep = closeClaw();
//			break;
//		}
//
//		if (nextStep) {
//			stepToPerform++;
//		}

		SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
		SmartDashboard.putNumber("Left Encoder", leftEncoder.getDistance());
	}

	/**
	 * This function is for moving forward a set number of feet. Returns a
	 * boolean indicating whether the movement is complete.
	 */
	public boolean moveBase(double feet, double speed, double angle) {
		if (rightEncoder.getDistance() <= feet
				|| leftEncoder.getDistance() <= feet) {
			robotDrive.drive(0, 0);
			return true;
		} else {
			robotDrive.drive(speed, angle * Kp);
			return false;
		}
	}

	/**
	 * This function is for turning the base at a given speed and angle. Returns
	 * a boolean indicating whether the movement is complete.
	 */
	public boolean turnBase(double speed, double desiredAngle) {
		double currentAngle = gyro.getAngle();
		double angleDifference = currentAngle - startingAngle;
		if ((angleDifference > 0 && angleDifference <= desiredAngle)
				|| (angleDifference < 0 && angleDifference >= desiredAngle)) {
			moveBase(0, speed, desiredAngle);
			return false;
		} else {
			moveBase(0, 0, 0);
			return true;
		}
	}

	/**
	 * This function is for raising the carriage. Returns a boolean indicating
	 * whether the movement is complete.
	 */
	public boolean raiseCarriage(double speed) {
		raiseClaw.execute();
		return raiseClaw.isFinished();
	}

	/**
	 * This function is for lowering the carriage. Returns a boolean indicating
	 * whether the movement is complete.
	 */
	public boolean lowerCarriage(double speed) {
		lowerClaw.execute();
		return lowerClaw.isFinished();
	}

	/**
	 * This function is for opening the claw.
	 */
	public boolean openClaw() {
		OpenClaw openClaw = new OpenClaw();
		openClaw.execute();

		return openClaw.isFinished();
	}

	/**
	 * This function is for closing the claw.
	 */
	public boolean closeClaw() {
		CloseClaw closeClaw = new CloseClaw();
		closeClaw.execute();

		return closeClaw.isFinished();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// Retrieve values from joysticks
		double UD = wibblyWobblyCarriage.getRawAxis(1);
		tankDrive.execute();

		// Retrieve button pressed value.
		boolean clawButton = wibblyWobblyCarriage.getRawButton(1);

		// Joystick error correction
		if (Math.abs(UD) < .1) {
			UD = 0;
		}

		// Raise and lower carriage with joystick input.

		if (UD > 0) {
			raiseCarriage(UD);
		} else {
			lowerCarriage(UD);
		}

		// Open and close claw with joystick button input.

		if (clawButton) {
			openClaw();
		} else {
			closeClaw();
		}

		SmartDashboard.putNumber("AccelX", accel.getX());
		SmartDashboard.putNumber("AccelY", accel.getY());
		SmartDashboard.putNumber("AccelZ", accel.getZ());
		SmartDashboard.putBoolean("Claw Button", clawButton);
		SmartDashboard.putNumber("Operator UpDown", UD);

		SmartDashboard.putNumber("Right Encoder", rightEncoder.getDistance());
		SmartDashboard
				.putNumber("Left Encoder", -1 * leftEncoder.getDistance());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
