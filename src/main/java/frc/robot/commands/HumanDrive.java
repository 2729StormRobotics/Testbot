/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class HumanDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final double m_straightStickSpeed;
  private final double m_reverseSpeed;
  private final double m_forwardSpeed;
  private final double m_turnSpeed;
  private final double m_leftSpeed;
  private final double m_rightSpeed;

  private String m_driveType;

  /**
   * Creates a new HumanDrive.
   */
  public HumanDrive(double left, double right, double straightStick, double turn, double rightTrigger,
      double leftTrigger, Drivetrain subsystem) {
    m_leftSpeed = left;
    m_rightSpeed = right;
    m_straightStickSpeed = straightStick;
    m_turnSpeed = turn;
    m_reverseSpeed = leftTrigger;
    m_forwardSpeed = rightTrigger;
    m_drivetrain = subsystem;

    m_driveType = m_drivetrain.getDriveType();

    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.stopDrive();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveType = m_drivetrain.getDriveType();

    if (m_driveType == "Tank") {
      m_drivetrain.tankDrive(m_leftSpeed, m_rightSpeed, true);
    }

    if (m_driveType == "Arcade") {
      m_drivetrain.arcadeDrive(m_straightStickSpeed, m_turnSpeed, true);
    }

    if (m_driveType == "Trigger") {
      m_drivetrain.triggerDrive(m_forwardSpeed, m_reverseSpeed, m_turnSpeed, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
