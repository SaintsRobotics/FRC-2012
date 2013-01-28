package sr1899.frc;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Huadian
 */
public class ControlSystem extends Joystick
{
    private Joystick secondcontroller;

    private boolean[] lastbuttons;
    private boolean[] toggled;

    private boolean slow_toggle;
    private boolean lifter_toggle;
    private boolean feeder_toggle;
    private boolean harman_toggle;
    private boolean deploy_toggle;
    private double launcher_power;

    public ControlSystem()
    {
        super(RobotConstants.JOYSTICK_PORT_ID,
              RobotConstants.JOYSTICK_AXIS_COUNT,
              RobotConstants.JOYSTICK_BUTTON_COUNT);

        secondcontroller = new Joystick(RobotConstants.JOYSTICK_PORT_ID2);

        lastbuttons = new boolean[RobotConstants.JOYSTICK_BUTTON_COUNT + RobotConstants.JOYSTICK_BUTTON_COUNT2];
        toggled = new boolean[RobotConstants.JOYSTICK_BUTTON_COUNT + RobotConstants.JOYSTICK_BUTTON_COUNT2];
    }

    public void updateValues()
    {
        trackToggle();
        updateLauncher();
        updateToggles();
    }

    public void reset()
    {
        for(int i = 0; i < RobotConstants.JOYSTICK_BUTTON_COUNT + RobotConstants.JOYSTICK_BUTTON_COUNT2; i++)
        {
            toggled[i] = false;
            lastbuttons[i] = false;
        }
        launcher_power = 0;
        slow_toggle = false;
        lifter_toggle = false;
        feeder_toggle = false;
        harman_toggle = false;
        deploy_toggle = false;
    }

    private void updateToggles()
    {
        if (RobotConstants.JOYSTICK_SLOW_TOGGLE)
        {
            if (checkToggle(RobotConstants.JOYSTICK_SLOW_BUTTON))
            {
                slow_toggle = !slow_toggle;
            }
        }
        if (RobotConstants.JOYSTICK_PICKUP_LIFTER_TOGGLE)
        {
            if (checkToggle(RobotConstants.JOYSTICK_PICKUP_LIFTER_BUTTON))
            {
                lifter_toggle = !lifter_toggle;
            }
        }
        if (RobotConstants.JOYSTICK_PICKUP_FEEDER_TOGGLE)
        {
            if (checkToggle(RobotConstants.JOYSTICK_PICKUP_FEEDER_BUTTON))
            {
                feeder_toggle = !feeder_toggle;
            }
        }
        if (RobotConstants.JOYSTICK_HARMAN_TOGGLE)
        {
            if (checkToggle(RobotConstants.JOYSTICK_HARMAN_BUTTON))
            {
                harman_toggle = !harman_toggle;
            }
        }
        if (RobotConstants.JOYSTICK_TIPPER_DEPLOY_TOGGLE)
        {
            if (checkToggle(RobotConstants.JOYSTICK_TIPPER_DEPLOY_BUTTON))
            {
                deploy_toggle = !deploy_toggle;
            }
        }
    }
    public double getStrafe()
    {
        if(harman_toggle)
            return slow(deadzone(getRawAxis(RobotConstants.JOYSTICK_STRAFE_AXIS_HARMAN))) * RobotConstants.JOYSTICK_STRAFE_FACTOR;
        return slow(deadzone(getRawAxis(RobotConstants.JOYSTICK_STRAFE_AXIS))) * RobotConstants.JOYSTICK_STRAFE_FACTOR;
    }

    public double getThrottle()
    {
        if(harman_toggle)
            return slow(deadzone(getRawAxis(RobotConstants.JOYSTICK_THROTTLE_AXIS_HARMAN))) * RobotConstants.JOYSTICK_STRAFE_FACTOR;
        return slow(deadzone(getRawAxis(RobotConstants.JOYSTICK_THROTTLE_AXIS))) * RobotConstants.JOYSTICK_THROTTLE_FACTOR;
    }

    public double getTwist()
    {
        if(harman_toggle)
            return slow(deadzone(getRawAxis(RobotConstants.JOYSTICK_TWIST_AXIS_HARMAN))) * RobotConstants.JOYSTICK_STRAFE_FACTOR;
        return slow(deadzone(getRawAxis(RobotConstants.JOYSTICK_TWIST_AXIS))) * RobotConstants.JOYSTICK_TWIST_FACTOR;
    }

    public double[] getMecanum()
    {
        //System.out.println("Th: " + getThrottle() + " S: " + getStrafe() + " Tw: " + getTwist());
        double[] target = {getThrottle(), getStrafe(), getTwist()};
        return target;
    }

    private double deadzone(double raw)
    {
        if (java.lang.Math.abs(raw) < RobotConstants.JOYSTICK_DEADZONE)
        {
            //System.out.println("Raw: " + raw + " Deadzoned");
            return 0;
        }
        else
        {
            //System.out.println("Raw: " + raw + " Passed");
            return raw;
        }
    }

    private void trackToggle()
    {
        for (int i = 0; i < RobotConstants.JOYSTICK_BUTTON_COUNT; i++)
        {
            if (getRawButton(i + 1) != lastbuttons[i] && !lastbuttons[i])
            {
                toggled[i] = true;
            }
            else
            {
                toggled[i] = false;
            }

            lastbuttons[i] = getRawButton(i + 1);
        }
        for (int i = 0; i < RobotConstants.JOYSTICK_BUTTON_COUNT2; i++)
        {
            if (getRawButton(new int[]{i + 1, 1}) != lastbuttons[i + RobotConstants.JOYSTICK_BUTTON_COUNT] && !lastbuttons[i + RobotConstants.JOYSTICK_BUTTON_COUNT])
            {
                toggled[i + RobotConstants.JOYSTICK_BUTTON_COUNT] = true;
            }
            else
            {
                toggled[i + RobotConstants.JOYSTICK_BUTTON_COUNT] = false;
            }
            lastbuttons[i + RobotConstants.JOYSTICK_BUTTON_COUNT] = getRawButton(new int[]{i + 1, 1});
        }
    }

    private boolean checkToggle(int[] buttonID)
    {
        if(buttonID[1] == 1)
        {
            if(toggled[RobotConstants.JOYSTICK_BUTTON_COUNT + buttonID[0] - 1])
                return true;
        }
        else
        {
            if(toggled[buttonID[0] - 1])
                return true;
        }
        return false;
    }

    private double slow(double raw)
    {
        if (getSlowed())
        {
            return (raw / RobotConstants.JOYSTICK_SLOW_FACTOR);
        }
        else
        {
            return raw;
        }
    }

    private boolean getToggled(boolean toggleControlled, boolean toggledVariable, int[] buttonID)
    {
        if (toggleControlled)
        {
            return toggledVariable;
        }
        else
        {
            if(getRawButton(buttonID))
                return true;
        }
        return false;
    }

    public boolean getSlowed()
    {
        return getToggled(RobotConstants.JOYSTICK_SLOW_TOGGLE, slow_toggle,
                          RobotConstants.JOYSTICK_SLOW_BUTTON);
    }

    private void updateLauncher()
    {
        if (getRawButton(RobotConstants.JOYSTICK_LAUNCHER_INCREASE_BUTTON))
        {
            launcher_power += RobotConstants.JOYSTICK_LAUNCHER_INCREMENT;
        }
        if (getRawButton(RobotConstants.JOYSTICK_LAUNCHER_DECREASE_BUTTON))
        {
            launcher_power -= RobotConstants.JOYSTICK_LAUNCHER_INCREMENT;
        }
        if (getRawButton(RobotConstants.JOYSTICK_LAUNCHER_INCREASE_BUTTON))
        {
            launcher_power += RobotConstants.JOYSTICK_LAUNCHER_INCREMENT;
        }
        if (getRawButton(RobotConstants.JOYSTICK_LAUNCHER_DECREASE_BUTTON))
        {
            launcher_power -= RobotConstants.JOYSTICK_LAUNCHER_INCREMENT;
        }

        launcher_power = clamp(launcher_power,
                               RobotConstants.JOYSTICK_LAUNCHER_MAX,
                               RobotConstants.JOYSTICK_LAUNCHER_MIN);
    }

    private double clamp(double value, double max, double min)
    {
        double clampedValue = value;

        if (value > max)
        {
            clampedValue = max;
        }
        if (value < min)
        {
            clampedValue = min;
        }

        return clampedValue;
    }

    public double getLauncher()
    {
        return launcher_power;
    }

    public boolean getLifter()
    {
        return getToggled(RobotConstants.JOYSTICK_PICKUP_LIFTER_TOGGLE, lifter_toggle,
                          RobotConstants.JOYSTICK_PICKUP_LIFTER_BUTTON);
    }

    public boolean getFeeder()
    {
        return getToggled(RobotConstants.JOYSTICK_PICKUP_FEEDER_TOGGLE, feeder_toggle,
                          RobotConstants.JOYSTICK_PICKUP_FEEDER_BUTTON);
    }

    public boolean getTipper()
    {
        return getToggled(RobotConstants.JOYSTICK_TIPPER_DEPLOY_TOGGLE, deploy_toggle,
                          RobotConstants.JOYSTICK_TIPPER_DEPLOY_BUTTON);
    }

    private boolean getRawButton(int[] buttonConfig)
    {
        if(buttonConfig[1] == 1)
        {
            return secondcontroller.getRawButton(buttonConfig[0]);
        }
        else
        {
            return getRawButton(buttonConfig[0]);
        }
    }
}