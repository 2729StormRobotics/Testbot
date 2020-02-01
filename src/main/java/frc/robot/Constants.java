/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int kLeftMotor1Port = 0;
        public static final int kLeftMotor2Port = 1;
        public static final int kRightMotor1Port = 15;
        public static final int kRightMotor2Port = 14;

        /**
         * Our PID values for Point Turn. Must be determined experimentally.
         */
        public static final class PointTurnPID {
            public static final double kP = 0.002;
            public static final double kI = 0.0;
            public static final double kD = 0.0001;
            public static final double kTolerance = 2.0; // The tolerance in degrees
        }
    }

    public final class OIConstants {
        public static final int kDrivePadPort = 0;
    }

    public final class VisionConstants {
        public static final double kP = 1.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;

        public static final double kPositionTolerance = 0.1;
        public static final double kVelocityTolerance = 0.0;
    }
}
