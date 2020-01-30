/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

import static frc.robot.Constants.VisionConstants.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoAlign extends PIDCommand {
  private final Vision m_vision;
  private final Drivetrain m_drivetrain;

  /**
   * Creates a new AutoAlign.
   */
  public AutoAlign(Vision limesub, Drivetrain drivesub) {
    super(
        // The controller that the command will use
        new PIDController(kP, kI, kD),
        // This should return the measurement
        () -> limesub.getDeltaX(),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          // Use the output here
          drivesub.arcadeDrive(0, output, false);
        });

    m_drivetrain = drivesub;
    m_vision = limesub;
    addRequirements(m_drivetrain, m_vision);

    getController().setTolerance(kPositionTolerance, kVelocityTolerance);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
