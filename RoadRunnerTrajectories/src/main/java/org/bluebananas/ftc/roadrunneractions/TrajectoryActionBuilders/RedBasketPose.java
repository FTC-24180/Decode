package org.bluebananas.ftc.roadrunneractions.TrajectoryActionBuilders;

import com.acmerobotics.roadrunner.Pose2d;

public class RedBasketPose {

    public final static Pose2d basket_init_old = new Pose2d(-31, -61.875, Math.toRadians(180));
    public final static Pose2d init = new Pose2d(-47, -59.5, Math.toRadians(45));
    //Special drop pose for the initialDrop since it's coming from a different location and the tolerance is causing problems
    public final static Pose2d initialDrop = new Pose2d(-56.2, -55.75, Math.toRadians(45));
    public final static Pose2d drop = new Pose2d(-56.9, -56.275, Math.toRadians(45));
    public final static Pose2d inner_sample = new Pose2d(-43.10, -43.59, Math.toRadians(102.8)); //inner closest to center
    public final static Pose2d middle_sample = new Pose2d(-50.70, -42.05, Math.toRadians(113.59));
    public final static Pose2d outer_sample = new Pose2d(-55.65, -39.50, Math.toRadians(127.75)); //outer closest to wall
    public final static Pose2d observation_park = new Pose2d(38, -60, Math.toRadians(90));
    public final static Pose2d submersible_park = new Pose2d(-34.13, -13.49, Math.toRadians(28.69));
    public final static Pose2d fifth_sample = new Pose2d(31, 61.875, Math.toRadians(180));
    //fifth sample needs to be set at 8,61 parallel to alliance wall
    public final static Pose2d manual_teleop_init = drop; //This is the pose to use for a manual drive to and button press style reset
}