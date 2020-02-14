/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.DriveConstants.*;

public class Drivetrain extends SubsystemBase {
  // Declare our motor objects
  private final WPI_TalonSRX m_leftMotor1;
  private final WPI_TalonSRX m_leftMotor2;
  private final WPI_TalonSRX m_rightMotor1;
  private final WPI_TalonSRX m_rightMotor2;

  // Declare our SpeedControllerGroup objects to combine the motors per side
  private final SpeedControllerGroup m_leftMotors;
  private final SpeedControllerGroup m_rightMotors;

  // Declare our DifferentialDrive object to drive the robot
  private final DifferentialDrive m_drivetrain;

  // Create the encoder objects
  private final Encoder m_leftEncoder;
  private final Encoder m_rightEncoder;

  // Add the NavX for gyroscope feedback
  private final AHRS m_navX;

  // Add a String object to store the current drive type from SmartDashboard
  private String m_driveType = "Tank";

  SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * Creates a new Drivetrain Subsystem.
   */
  public Drivetrain() {
    // Instantiate our motor objects
    m_leftMotor1 = new WPI_TalonSRX(kLeftMotor1Port);
    m_leftMotor2 = new WPI_TalonSRX(kLeftMotor2Port);
    m_rightMotor1 = new WPI_TalonSRX(kRightMotor1Port);
    m_rightMotor2 = new WPI_TalonSRX(kRightMotor2Port);

    // Reset all motor controllers to facotry defaults, just in case
    m_leftMotor1.configFactoryDefault();
    m_leftMotor2.configFactoryDefault();
    m_rightMotor1.configFactoryDefault();
    m_rightMotor2.configFactoryDefault();

    // Instantiate our SpeedControllerGroups
    m_leftMotors = new SpeedControllerGroup(m_leftMotor1, m_leftMotor2);
    m_rightMotors = new SpeedControllerGroup(m_rightMotor1, m_rightMotor2);

    // Instantiate our DifferentialDrive
    m_drivetrain = new DifferentialDrive(m_leftMotors, m_rightMotors);

    // Instantiate our encoders
    m_leftEncoder = new Encoder(kLeftEncoderPort, kLeftEncoderPort + 1, false);
    m_rightEncoder = new Encoder(kRightEncoderPort, kRightEncoderPort + 1, true);

    // Set our encoder conversions
    m_leftEncoder.setDistancePerPulse(kDistancePerPulse);
    m_rightEncoder.setDistancePerPulse(kDistancePerPulse);

    // Instantiate our NavX
    m_navX = new AHRS(Port.kMXP);

    // Add options to the drive type chooser
    m_chooser.addOption("Tank Drive", "Tank");
    m_chooser.addOption("Arcade Drive", "Arcade");
    m_chooser.addOption("Trigger Drive", "Trigger");
  }

  public void tankDrive(double left, double right, boolean squared) {
    m_drivetrain.tankDrive(left, right, squared);
  }

  public void arcadeDrive(double straight, double turn, boolean squared) {
    m_drivetrain.arcadeDrive(straight, turn, squared);
  }

  public void triggerDrive(double forward, double reverse, double turn, boolean squared) {
    arcadeDrive(forward - reverse, turn, squared);
  }

  public void stopDrive() {
    tankDrive(0, 0, false);
  }

  private void updateDriveType() {
    m_driveType = m_chooser.getSelected();
  }

  public String getDriveType() {
    return m_driveType;
  }

  public double getLeftPosition() {
    return m_leftEncoder.getDistance();
  }

  public double getRightPosition() {
    return m_rightEncoder.getDistance();
  }

  public double getAverageDistance() {
    double leftPosition = getLeftPosition();
    double rightPosition = getRightPosition();
    double avgDistance = (leftPosition + rightPosition) / 2.0;
    return avgDistance;
  }

  public double getLeftSpeed() {
    return m_leftEncoder.getRate();
  }

  public double getRightSpeed() {
    return m_rightEncoder.getRate();
  }

  public double getAverageSpeed() {
    double leftSpeed = getLeftSpeed();
    double rightSpeed = getRightSpeed();
    double avgSpeed = (leftSpeed + rightSpeed) / 2.0;
    return avgSpeed;
  }

  public void resetLeftEncoder() {
    m_leftEncoder.reset();
  }

  public void resetRightEncoder() {
    m_rightEncoder.reset();
  }

  public void resetEncoders() {
    resetLeftEncoder();
    resetRightEncoder();
  }

  public double getRobotHeading() {
    return m_navX.getAngle();
  }

  public void resetRobotHeading() {
    m_navX.zeroYaw();
  }

  public void log() {
    SmartDashboard.putData("Left Motor 1", m_leftMotor1);
    SmartDashboard.putData("Left Motor 2", m_leftMotor2);
    SmartDashboard.putData("Right Motor 1", m_rightMotor1);
    SmartDashboard.putData("Right Motor 2", m_rightMotor2);
    SmartDashboard.putData("Drivetrain Status", m_drivetrain);
    SmartDashboard.putData("Left Encoder", m_leftEncoder);
    SmartDashboard.putData("Right Encoder", m_rightEncoder);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    log();
    updateDriveType();
  }
}
