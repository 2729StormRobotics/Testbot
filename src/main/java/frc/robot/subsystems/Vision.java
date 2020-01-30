/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  private boolean m_limelightHasValidTarget = false;
  private double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
  private double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  private double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
  private double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

  /**
   * Creates a new Vision.
   */
  public Vision() {
    updateLimelightTracking();
  }

  private void updateLimelightTracking() {
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    if (tv < 1.0) {
      m_limelightHasValidTarget = false;
    }
    else {
      m_limelightHasValidTarget = true;
    }
  }

  public boolean visibleTarget() {
    return m_limelightHasValidTarget;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateLimelightTracking();
  }
}
