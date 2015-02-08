package org.usfirst.frc.team5679.robot.commands;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * 
 * Handles turning the drivetrain base.
 *
 */
public class TurnBase extends CommandBase {
	double speed;
	double distance;
	Encoder rightEncoder = new Encoder(0, 1, false, EncodingType.k4X);
	Encoder leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);
	Talon leftie0 = new Talon(2);
	Talon leftie1 = new Talon(3);
	Talon rightie0 = new Talon(0);
	Talon rightie1 = new Talon(1);
	Gyro gyro = new Gyro(0);
	double Kp = 1;
	boolean finished = false;
	double desiredAngle;
	double startingAngle;
	RobotDrive robotDrive = new RobotDrive(leftie0, leftie1, rightie0, rightie1);
	
	// Constructor
	public TurnBase(double speed, double angle) {
        this.speed = speed;
        this.desiredAngle = angle;
        requires(drive);
    }
	
	// Initializes the class.
	@Override
	protected void initialize() {	
		startingAngle = gyro.getAngle();
	}

	// Executes the movement.
	@Override
	public void execute() {
		double currentAngle = gyro.getAngle();
		double angleDifference = currentAngle - startingAngle;
		if ((angleDifference > 0 && angleDifference <= desiredAngle)
				|| (angleDifference < 0 && angleDifference >= desiredAngle)) {
			robotDrive.drive(speed, desiredAngle * Kp);
			this.finished = false;
		} else {
			robotDrive.drive(0, 0);
			this.finished = true;
		}
	}
    
	// Returns whether the movement is finished.
	@Override
	public boolean isFinished() {
		return this.finished;
	}
	
	// Ends the movement.
	@Override
	public void end() {
		drive.drive(0, 0);
		this.finished = true;
	}

	// Interrupts the movement.
	@Override
	public void interrupted() {
		end();
	}
}
