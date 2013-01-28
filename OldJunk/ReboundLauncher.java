package sr1899.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Huadian
 */
public class ReboundLauncher extends PIDController
{
    private static CANJaguar[] motors;

    private static double lastspeed;

    private static double[] acurrent;

    public ReboundLauncher(int[] motorIDs)
    {
        super(0, 0, 0, new CANJaguarSource(generateMotorArray(motorIDs)), new CANJaguarOutput(ReboundLauncher.motors));
        initialize();
    }

    private static CANJaguar[] generateMotorArray(int[] motorIDs)
    {
        ReboundLauncher.motors = new CANJaguar[motorIDs.length];
        try
        {
            for (int i = 0; i < motorIDs.length; i++)
            {
                ReboundLauncher.motors[i] = new CANJaguar(motorIDs[i]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return motors;
    }

    public ReboundLauncher()
    {
        this(RobotConstants.LAUNCHER_DEVICE_IDS);
        acurrent = new double[4];
        acurrent[0] = acurrent[1] = acurrent[2] = acurrent[3] = 0;
    }

    private void initialize()
    {
        try
        {
            for (int i = 0; i < motors.length; i++)
            {
                motors[i].configNeutralMode(RobotConstants.LAUNCHER_CAN_NEUTRAL_MODE);
                motors[i].setSpeedReference(RobotConstants.LAUNCHER_CAN_SPEED_REFERENCE);
                motors[i].configEncoderCodesPerRev(RobotConstants.LAUNCHER_ENCODER_CODES_PER_REV);
                motors[i].configPotentiometerTurns(RobotConstants.LAUNCHER_POTENTIOMETER_TURNS);
            }

            changeCANControlMode();
            disableAll();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        setTolerance(RobotConstants.LAUNCHER_SPEED_TOLERANCE);
    }

    public void disableAll()
    {
        try
        {
            for (int i = 0; i < motors.length; i++)
            {
                motors[i].disableControl();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        disable();
    }

    public void enableAll()
    {
        try
        {
            // start jag controller if using jag pid
            if(RobotConstants.LAUNCHER_MOTOR_JAGUAR_PID_MODE)
            {
                for (int i = 0; i < motors.length; i++)
                {
                    motors[i].enableControl();
                }
            }
            // start software pid controller if using software pid
            if(RobotConstants.LAUNCHER_MOTOR_PID_MODE)
            {
                enable();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void changeCANControlMode()
    {
        if(RobotConstants.LAUNCHER_MOTOR_JAGUAR_PID_MODE)
        {
            changeCANControlMode(RobotConstants.LAUNCHER_CAN_PID_CONTROL_MODE);
        }
        else
        {
            changeCANControlMode(RobotConstants.LAUNCHER_CAN_CONTROL_MODE);
        }
    }

    public void changeCANControlMode(CANJaguar.ControlMode mode)
    {
        try
        {
            for (int i = 0; i < motors.length; i++)
            {
                motors[i].changeControlMode(mode);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setPID()
    {
        if(RobotConstants.LAUNCHER_MOTOR_JAGUAR_PID_MODE)
        {
            // if pid is on jag, then load pid constants into jaguar
            try
            {
                for (int i = 0; i < motors.length; i++)
                {
                    motors[i].setPID(RobotConstants.LAUNCHER_CANJAGUAR_P, RobotConstants.LAUNCHER_CANJAGUAR_I, RobotConstants.LAUNCHER_CANJAGUAR_D);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // else set pid constants into PIDController superclass
            super.setPID(RobotConstants.LAUNCHER_P, RobotConstants.LAUNCHER_I, RobotConstants.LAUNCHER_D);
        }
    }

    public void setLauncher(double speed)
    {
        if(RobotConstants.LAUNCHER_RAMPING)
        {
            // ramp up/down launcher speed
            lastspeed += (speed - lastspeed) * RobotConstants.LAUNCHER_RAMP_PERCENT;
        }
        else
        {
            lastspeed = speed;
        }
        if(RobotConstants.LAUNCHER_MOTOR_PID_MODE)
        {
            // set software pid
            setSpeedSoftwarePID(lastspeed);
        }
        else
        {
            // no PID or jaguar PID, both directly set motor speed
            setSpeedNoSoftwarePID(lastspeed);
        }
    }

    public void setSpeedNoSoftwarePID(double motorValue)
    {
        try
        {
            for (int i = 0; i < motors.length; i++)
            {
                if(RobotConstants.LAUNCHER_MOTOR_JAGUAR_PID_MODE)
                {
                    // if pid is on jag then setX is in RPM
                    // so convert 0-1 to 0-MAX_MOTOR_RPM
                    motors[i].setX(convertToDouble(RobotConstants.LAUNCHER_MOTOR_INVERTED[i]) * motorValue * RobotConstants.LAUNCHER_SPEED_CONVERT);
                    //Code for sync group update
                    //motors[i].setX(convertToDouble(RobotConstants.LAUNCHER_MOTOR_INVERTED[i]) * motorValue * RobotConstants.LAUNCHER_SPEED_CONVERT, (byte)1);
                }
                else
                {
                    // if pid is off or in software then Jag is 0-1 since other direction is not used
                    motors[i].setX(convertToDouble(RobotConstants.LAUNCHER_MOTOR_INVERTED[i]) * motorValue);
                }
            }
            //CANJaguar.updateSyncGroup((byte) 1);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setSpeedSoftwarePID(double motorValue)
    {
        // jags are in vbus mode so PID needs set values of 0-1
        setSetpoint(motorValue);
    }

    public double getSpeed(int motornumber)
    {
        double i = 0;
        try
        {
            i = motors[motornumber].getSpeed();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return i;
    }

    public String getVoltages()
    {
        String ret = "V";
        try
        {
            for (int i = 0; i < motors.length; i++)
            {
                ret += Math.floor(motors[i].getOutputVoltage() * 10) / 10;
                // no comma after last motor
                if(i < motors.length-1)
                    ret += ",";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    public String getCurrents()
    {
        String ret = "S";
        try
        {
            for (int i = 0; i < motors.length; i++)
            {
                // apply some averaging to the currrent values so they dont bounce around too much
                acurrent[i] = (acurrent[i] * 7 + motors[i].getOutputCurrent())/8;
                ret += Math.floor(acurrent[i] * 10) / 10;
                // no comma after last motor
                if(i < motors.length-1)
                    ret += ",";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    private double convertToDouble(boolean input)
    {
        if(input)
        {
            return 1.0;
        }
        else
        {
            return -1.0;
        }
    }
}