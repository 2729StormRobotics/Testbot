/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import static edu.wpi.first.wpilibj.GenericHID.Hand.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import static frc.robot.Constants.OIConstants.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain;
  private final Vision m_vision;

  private final XboxController m_drivePad;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_drivePad = new XboxController(kDrivePadPort);

    m_drivetrain = new Drivetrain();
    m_vision = new Vision();

    SmartDashboard.putData("Drivetrain Subsystem", m_drivetrain);
    //SmartDashboard.putData("Vision Subsystem", m_vision);
    //SmartDashboard.putNumber("Received Angle", m_drivetrain.getTargetAngle());

    //SmartDashboard.putData(new PointTurn(180, m_drivetrain));
    SmartDashboard.putNumber("Target Angle", 180);
    SmartDashboard.putData("Turn to Angle", new PointTurn(() -> m_drivetrain.getTargetAngle(), m_drivetrain));

    SmartDashboard.putData("Reset Gyro", new InstantCommand(m_drivetrain::resetGyro, m_drivetrain));

    m_drivetrain.setDefaultCommand(
      new HumanDrive(
        m_drivePad.getY(kLeft), m_drivePad.getY(kRight),
        m_drivePad.getY(kLeft), m_drivePad.getX(kRight),
        m_drivePad.getTriggerAxis(kRight), m_drivePad.getTriggerAxis(kLeft),
        m_drivetrain
      )
    );



    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
