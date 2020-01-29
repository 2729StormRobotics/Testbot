/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
