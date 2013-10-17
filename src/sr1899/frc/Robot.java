/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package sr1899.frc;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
    SaintsDrive drive;
    ControlSystem control;
    DriverStationComm comm;
    ReboundLauncher launcher;
    BallPickup pickup;
    Autonomous auton;
    Tipper tip;

    int timer;

    /* This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        try
        {
            drive = new SaintsDrive();
            control = new ControlSystem();
            comm = new DriverStationComm();
            launcher = new ReboundLauncher();
            pickup = new BallPickup();
            tip = new Tipper();
            auton = new Autonomous(drive, control, comm, launcher, pickup, tip);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        timer = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        auton.execute_Routine(timer);
        ++timer;
        //System.out.println(timer);
    }

    public void autonomousInit()
    {
        try
        {
            drive.enableAll();
            drive.setPID();

            launcher.enableAll();
            launcher.setPID();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        timer = 0;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        try
        {
            control.updateValues();
            drive.setDriveMecanum(control.getMecanum());
            launcher.setLauncher(control.getLauncher());
            pickup.setLifterOn(control.getLifter());
            pickup.setFeederOn(control.getFeeder());
            comm.UpdateDB(control.getLifter(), control.getFeeder(), control.getSlowed(), (int)launcher.getSpeed());
            tip.deploy(control.getTipper());

            updateLCD();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void teleopInit()
    {
        try
        {
            drive.enableAll();
            drive.setPID();

            launcher.enableAll();
            launcher.setPID();

            tip.deploy(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void disabledInit()
    {
        try
        {
            drive.disableAll();
            launcher.disableAll();
            pickup.allOff();
            control.reset();
            tip.deploy(false);

            updateLCD();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void updateLCD()
    {
        comm.printBoolean("Slow: ", control.getSlowed(), DriverStationLCD.Line.kMain6);
        comm.printUserMsg(DriverStationLCD.Line.kUser2, 1, "Launcher Power: " + ((int)(100 * control.getLauncher())) + "%");
        comm.printUserMsg(DriverStationLCD.Line.kUser3, 1, "Speed: " + (int)launcher.getSpeed() + "rpm");
        //comm.printUserMsg(DriverStationLCD.Line.kUser4, 1, "L: " + control.getLifter() + " F: " + control.getFeeder() + " D: " + control.getTipper());
        //comm.printBoolean("Tipper Extend: ", control.getTipper(), DriverStationLCD.Line.kUser5);
        //comm.printUserMsg(DriverStationLCD.Line.kUser5, 1, launcher.getVoltages());
        comm.printUserMsg(DriverStationLCD.Line.kUser5, 1, drive.getCurrents());
        comm.printUserMsg(DriverStationLCD.Line.kUser6, 1, launcher.getCurrents());
    }
}
