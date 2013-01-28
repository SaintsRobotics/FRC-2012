package sr1899.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author Huadian
 */
public class ReboundLauncher extends PIDController
{
    private static Encoder encoder;
    private static CANJaguar[] motors;
    private static CANJaguarOutput output;
    private static double lastspeed;

    public ReboundLauncher()
    {
        this(RobotConstants.LAUNCHER_DEVICE_IDS);
    }

    public ReboundLauncher(int[] motorIDs)
    {
        super(0, 0, 0, createEncoder(), createOutput());
        generateMotorArray(motorIDs);
        output.setParent(this);
        initialize();
    }

    private static Encoder createEncoder()
    {
        ReboundLauncher.encoder = //new Encoder(RobotConstants.LAUNCHER_ENCODER_MODULE, RobotConstants.LAUNCHER_ENCODER_PIDS[0],
                                  // RobotConstants.LAUNCHER_ENCODER_MODULE, RobotConstants.LAUNCHER_ENCODER_PIDS[1],
                                  // RobotConstants.LAUNCHER_ENCODER_MODULE_DIRECTION, Encoder.EncodingType.k1X);
                                  //new Encoder(RobotConstants.LAUNCHER_ENCODER_PIDS[0], RobotConstants.LAUNCHER_ENCODER_PIDS[1], RobotConstants.LAUNCHER_ENCODER_MODULE_DIRECTION, Encoder.EncodingType.k4X);
                                  new Encoder(3, 4, false);
        return encoder;
    }

    private static CANJaguarOutput createOutput()
    {
        ReboundLauncher.output = new CANJaguarOutput();
        return ReboundLauncher.output;
    }

    private static CANJaguar[] generateMotorArray(int[] motorIDs)
    {
        ReboundLauncher.motors = new CANJaguar[motorIDs.length];
        try
        {
            for(int i = 0; i < motorIDs.length; i++)
            {
                ReboundLauncher.motors[i] = new CANJaguar(motorIDs[i]);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return motors;
    }

    private void initialize()
    {
        try
        {
            for(int i = 0; i < motors.length; i++)
            {
                motors[i].configNeutralMode(RobotConstants.LAUNCHER_CAN_NEUTRAL_MODE);
                motors[i].setSpeedReference(RobotConstants.LAUNCHER_CAN_SPEED_REFERENCE);
                motors[i].changeControlMode(RobotConstants.LAUNCHER_CAN_CONTROL_MODE);
            }
            disableAll();
            setTolerance(RobotConstants.LAUNCHER_SPEED_TOLERANCE);
            encoder.setDistancePerPulse(RobotConstants.LAUNCHER_ENCODER_CODES_PER_REV);
            encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
            encoder.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void disableAll()
    {
        try
        {
            encoder.stop();
            encoder.reset();
            reset();
            disable();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void enableAll()
    {
        encoder.start();
        try
        {
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

    public void setPID()
    {
        super.setPID(RobotConstants.LAUNCHER_P, RobotConstants.LAUNCHER_I, RobotConstants.LAUNCHER_D);
    }

    // input speed in 0-100%
    public void setLauncher(double speed)
    {
        double motorvalue;

        // ramp up/down launcher speed
        lastspeed += (speed - lastspeed) * RobotConstants.LAUNCHER_RAMP_PERCENT;

        // convert to RPM
        motorvalue = RobotConstants.LAUNCHER_MOTOR_CLAMP * lastspeed;
        if(RobotConstants.LAUNCHER_MOTOR_PID_MODE)
        {
            // set software pid
            setSetpoint(motorvalue);
        }
        else
        {
            // set the jaguar directly
            setJaguarSpeed(motorvalue);
        }
    }

    // used by PIDOutput and when the pid loop is not being used
    // input is RPM
    public void setJaguarSpeed(double motorValue)
    {
        // convert RPM to VBUS
        motorValue = motorValue / RobotConstants.LAUNCHER_MOTOR_CLAMP;
        try
        {
            for(int i = 0; i < motors.length; i++)
            {
                // set motor speed
                motors[i].setX(convertToDouble(RobotConstants.LAUNCHER_MOTOR_INVERTED[i]) * motorValue);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // get speed from encoder
    public double getSpeed()
    {
        return encoder.getDistance();
    }

    public String getVoltages()
    {
        String ret = "V: ";
        try
        {
            for(int i = 0; i < motors.length; i++)
            {
                ret += Math.floor(motors[i].getOutputVoltage() * 10) / 10 + ",";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    public String getCurrents()
    {
        String ret = "C: ";
        try
        {
            for(int i = 0; i < motors.length; i++)
            {
                ret += Math.floor(motors[i].getOutputCurrent() * 10) / 10 + ",";
            }
        }
        catch(Exception e)
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