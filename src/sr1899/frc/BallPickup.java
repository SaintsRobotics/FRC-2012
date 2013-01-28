package sr1899.frc;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Huadian
 */
public class BallPickup
{
    private Relay[] lifterMotors;
    private Relay[] feederMotors;

    public BallPickup()
    {
        this(RobotConstants.PICKUP_LIFTER_CHANNELS,
             RobotConstants.PICKUP_FEEDER_CHANNELS,
             RobotConstants.PICKUP_RELAY_MODULEID,
             RobotConstants.PICKUP_LIFTER_DIRECTION,
             RobotConstants.PICKUP_FEEDER_DIRECTION);
    }

    public BallPickup(int[] lifterChannels, int[] feederChannels, int moduleID,
                      Relay.Direction lifterDirection, Relay.Direction feederDirection)
    {
        lifterMotors = new Relay[lifterChannels.length];
        feederMotors = new Relay[feederChannels.length];

        for (int i = 0; i < lifterMotors.length; i++)
        {
            lifterMotors[i] = new Relay(moduleID, lifterChannels[i], lifterDirection);
        }
        for (int i = 0; i < feederMotors.length; i++)
        {
            feederMotors[i] = new Relay(moduleID, feederChannels[i], feederDirection);
        }
    }

    public void setLifterOn(boolean on)
    {
        for (int i = 0; i < lifterMotors.length; i++)
        {
            if (on)
            {
                lifterMotors[i].setDirection(RobotConstants.PICKUP_LIFTER_DIRECTION);
                lifterMotors[i].set(Relay.Value.kOn);
            }
            else
            {
                lifterMotors[i].set(Relay.Value.kOff);
            }
        }
    }

    public void setFeederOn(boolean on)
    {
        for (int i = 0; i < feederMotors.length; i++)
        {
            if (on)
            {
                lifterMotors[i].setDirection(RobotConstants.PICKUP_FEEDER_DIRECTION);
                feederMotors[i].set(Relay.Value.kOn);
            }
            else
            {
                feederMotors[i].set(Relay.Value.kOff);
            }
        }
    }

    public void allOff()
    {
        setLifterOn(false);
        setFeederOn(false);
    }
}
