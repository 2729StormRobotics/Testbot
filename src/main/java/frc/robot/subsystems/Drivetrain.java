/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.PointTurn;

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

  //Declare our gyro object
  private final ADIS16470_IMU m_imu;

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

    //Instantiate our gyro object
    m_imu = new ADIS16470_IMU();

    // Add options to the drive type chooser
    m_chooser.addOption("Tank Drive", "Tank");
    m_chooser.addOption("Arcade Drive", "Arcade");
    m_chooser.addOption("Trigger Drive", "Trigger");

    SmartDashboard.putNumber("Received Angle", getTargetAngle());
  }

  public void tankDrive(double left, double right, boolean squared) {
    m_drivetrain.tankDrive(left, right, squared);
  }

  public void arcadeDrive(double straight, double turn, boolean squared) {
    m_drivetrain.arcadeDrive(straight, turn, squared);
  }

  public void triggerDrive(double forward, double reverse, double turn, boolean squared) {
    arcadeDrive(forward-reverse, turn, squared);
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

  public double getAngle() {
    return m_imu.pidGet();
    
  }

  public void resetGyro() {
    m_imu.reset();
  }

  public double getTargetAngle() {
    return SmartDashboard.getNumber("Target Angle", 0.0);
  }

  public void log() {
    /*SmartDashboard.putData("Left Motor 1", m_leftMotor1);
    SmartDashboard.putData("Left Motor 2", m_leftMotor2);
    SmartDashboard.putData("Right Motor 1", m_rightMotor1);
    SmartDashboard.putData("Right Motor 2", m_rightMotor2);*/
    SmartDashboard.putData("Left Motors", m_leftMotors);
    SmartDashboard.putData("Right Motors", m_rightMotors);
    SmartDashboard.putData("Drivetrain Status", m_drivetrain);
    SmartDashboard.putData("Gyro", m_imu);
    //SmartDashboard.putNumber("Robot Angle", getAngle());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    log();
    updateDriveType();
  }
}
