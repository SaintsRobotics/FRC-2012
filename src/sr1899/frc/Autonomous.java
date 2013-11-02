package sr1899.frc;

/**
 *
 * @author Developer
 */
public class Autonomous
{
    private final SaintsDrive drive;
    private final ControlSystem control;
    private final DriverStationComm comm;
    private final ReboundLauncher launcher;
    private final BallPickup pickup;
    private final Tipper tip;

    private final int routine = RobotConstants.AUTONOMOUS_ROUTINE;

    public Autonomous(SaintsDrive mDrive,
                      ControlSystem mControl,
                      DriverStationComm mComm,
                      ReboundLauncher mLauncher,
                      BallPickup mPickup,
                      Tipper mTip)
    {
        drive = mDrive;
        control = mControl;
        comm = mComm;
        launcher = mLauncher;
        pickup = mPickup;
        tip = mTip;
    }

    public void execute_Routine(int cycles)
    {
        switch (routine)
        {
            case 0:
                Routine0(cycles); return;
            case 1:
                Routine1(cycles); return;
            case 2:
                Routine2(cycles); return;
            case 3:
                Routine3(cycles); return;
            case 4:
                Routine4(cycles); return;
            case 5:
                Routine5(cycles); return;
        }
    }

    private void Routine0(int cycles)
    {
        if (cycles == 0)
        {
            launcher.setLauncher(0.50);
        }
        else if (cycles == 60)
        {
            pickup.setFeederOn(true);
            pickup.setLifterOn(true);
        }
        else if (cycles == 100)
        {
            pickup.setFeederOn(false);
            launcher.setLauncher(0.44);
        }
        else if (cycles == 140)
        {
            pickup.setFeederOn(true);
        }
        else if (cycles == 180)
        {
            pickup.allOff();
            launcher.disableAll();
            drive.setDrive(-0.75);
            tip.deploy(true);
        }
        else if (cycles == 200)
        {
            tip.deploy(false);
        }
        else if (cycles == 260)
        {
            drive.disableAll();
        }
    }

    private void Routine1(int cycles)
    {
        if (cycles == 0)
        {
            launcher.setLauncher(0.50);
        }
        else if (cycles == 60)
        {
            pickup.setFeederOn(true);
            pickup.setLifterOn(true);
        }
        else if (cycles == 100)
        {
            pickup.setFeederOn(false);
        }
        else if (cycles == 140)
        {
            pickup.setFeederOn(true);
        }
        else if (cycles == 180)
        {
            pickup.allOff();
            launcher.disableAll();
        }
    }

    private void Routine2(int cycles)
    {
        if (cycles == 0)
        {
            launcher.setLauncher(0.50);
        }
        else if (cycles == 60)
        {
            pickup.setFeederOn(true);
            pickup.setLifterOn(true);
        }
        else if (cycles == 100)
        {
            pickup.setFeederOn(false);
        }
        else if (cycles == 140)
        {
            pickup.setFeederOn(true);
        }
        else if (cycles == 180)
        {
            pickup.setFeederOn(false);
        }
        else if (cycles == 220)
        {
            pickup.setFeederOn(true);
        }
        else if (cycles == 260)
        {
            pickup.setFeederOn(false);
        }
        else if (cycles == 300)
        {
            pickup.setFeederOn(true);
        }
        else if (cycles == 340)
        {
            pickup.allOff();
            launcher.disableAll();
        }
    }

    private void Routine3(int cycles)
    {
        if (cycles == 0)
        {
            launcher.setLauncher(0.42);
        }
        else if (cycles == 60)
        {
            pickup.setFeederOn(true);
            pickup.setLifterOn(true);
        }
        else if (cycles == 240)
        {
            launcher.setLauncher(0);
            drive.setDriveMecanum(-0.5,0,0);
            tip.deploy(true);
        }
    }

    private void Routine4(int cycles)
    {
        if (cycles == 0)
        {
            drive.setDriveMecanum(-1,0,0);
            tip.deploy(true);
        }
        else if (cycles == 40)
        {
            drive.setDriveMecanum(-0.5,0,0);
        }
        else if (cycles == 100)
        {
            launcher.setLauncher(0.42);
            drive.setDriveMecanum(1,0,0);
        }
        else if (cycles == 140)
        {
            drive.setDriveMecanum(0,0,0);
        }
        else if (cycles == 160)
        {
            pickup.setFeederOn(true);
            pickup.setLifterOn(true);
        }
        else if (cycles == 220)
        {
            launcher.setLauncher(0);
            drive.setDriveMecanum(0, 0, 1);
        }
        else if (cycles == 260)
        {
            drive.setDriveMecanum(0, 0, 0);
        }
    }

    private void Routine5(int cycles)
    {
        // Poop out balls from intake
    }
}
