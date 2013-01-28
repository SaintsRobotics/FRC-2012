package sr1899.frc;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Huadian
 */
public class SaintsDrive
{
    private CANJaguar m_frontleft;
    private CANJaguar m_frontright;
    private CANJaguar m_backleft;
    private CANJaguar m_backright;

    private static double[] acurrent;
    //private Solenoid s_leftpiston;
    //private Solenoid s_rightpiston;

    public SaintsDrive()
    {
        this(RobotConstants.DRIVE_CANJAGUAR_FRONT_LEFT_DEVICE_ID,
             RobotConstants.DRIVE_CANJAGUAR_FRONT_RIGHT_DEVICE_ID,
             RobotConstants.DRIVE_CANJAGUAR_BACK_LEFT_DEVICE_ID,
             RobotConstants.DRIVE_CANJAGUAR_BACK_RIGHT_DEVICE_ID);

        acurrent = new double[4];
    }
    public SaintsDrive(int frontleftdevice, int frontrightdevice,
                       int backleftdevice, int backrightdevice)
    {
        try
        {
            m_frontleft = new CANJaguar(frontleftdevice);
            m_frontright = new CANJaguar(frontrightdevice);
            m_backleft = new CANJaguar(backleftdevice);
            m_backright = new CANJaguar(backrightdevice);

            initialize();
            disableAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initialize()
    {
        try
        {
            m_frontleft.configNeutralMode(RobotConstants.DRIVE_CAN_NEUTRAL_MODE);
            m_frontright.configNeutralMode(RobotConstants.DRIVE_CAN_NEUTRAL_MODE);
            m_backleft.configNeutralMode(RobotConstants.DRIVE_CAN_NEUTRAL_MODE);
            m_backright.configNeutralMode(RobotConstants.DRIVE_CAN_NEUTRAL_MODE);

            m_frontleft.setSpeedReference(RobotConstants.DRIVE_CAN_SPEED_REFERENCE);
            m_frontright.setSpeedReference(RobotConstants.DRIVE_CAN_SPEED_REFERENCE);
            m_backleft.setSpeedReference(RobotConstants.DRIVE_CAN_SPEED_REFERENCE);
            m_backright.setSpeedReference(RobotConstants.DRIVE_CAN_SPEED_REFERENCE);
            //Configure encoder ticks per revolution for CAN
            m_frontleft.configEncoderCodesPerRev(RobotConstants.DRIVE_ENCODER_CODES_PER_REV);
            m_frontright.configEncoderCodesPerRev(RobotConstants.DRIVE_ENCODER_CODES_PER_REV);
            m_backleft.configEncoderCodesPerRev(RobotConstants.DRIVE_ENCODER_CODES_PER_REV);
            m_backright.configEncoderCodesPerRev(RobotConstants.DRIVE_ENCODER_CODES_PER_REV);
            //Configure potentiometer turns per revolution
            m_frontleft.configPotentiometerTurns(RobotConstants.DRIVE_POTENTIOMETER_TURNS);
            m_frontright.configPotentiometerTurns(RobotConstants.DRIVE_POTENTIOMETER_TURNS);
            m_backleft.configPotentiometerTurns(RobotConstants.DRIVE_POTENTIOMETER_TURNS);
            m_backright.configPotentiometerTurns(RobotConstants.DRIVE_POTENTIOMETER_TURNS);
            //Set PID values for Jaguar controllers
            setPID();
            //Set motor controller mode
            changeCANControlMode();
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
            m_frontleft.disableControl();
            m_frontright.disableControl();
            m_backleft.disableControl();
            m_backright.disableControl();

            //s_leftpiston.set(false);
            //s_rightpiston.set(false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void enableAll()
    {
        try
        {
            m_frontleft.enableControl();
            m_frontright.enableControl();
            m_backleft.enableControl();
            m_backright.enableControl();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void changeCANControlMode()
    {
        changeCANControlMode(RobotConstants.DRIVE_CAN_CONTROL_MODE);
    }

    public void changeCANControlMode(CANJaguar.ControlMode mode)
    {
        try
        {
            m_frontleft.changeControlMode(mode);
            m_frontright.changeControlMode(mode);
            m_backleft.changeControlMode(mode);
            m_backright.changeControlMode(mode);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setPID()
    {
        setPID(RobotConstants.DRIVE_CANJAGUAR_P,
               RobotConstants.DRIVE_CANJAGUAR_I,
               RobotConstants.DRIVE_CANJAGUAR_D);
    }

    public void setPID(double p, double i, double d)
    {
        try
        {
            m_frontleft.setPID(p, i, d);
            m_frontright.setPID(p, i, d);
            m_backleft.setPID(p, i, d);
            m_backright.setPID(p, i, d);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setDriveMecanum(double throttle, double strafe, double twist)
    {
        double[] motorValues = new double[4];

        motorValues[0] = RobotConstants.DRIVE_MOTOR_CLAMP * (throttle - strafe - twist);
        motorValues[1] = RobotConstants.DRIVE_MOTOR_CLAMP * (throttle + strafe + twist);
        motorValues[2] = RobotConstants.DRIVE_MOTOR_CLAMP * (throttle + strafe - twist);
        motorValues[3] = RobotConstants.DRIVE_MOTOR_CLAMP * (throttle - strafe + twist);

        motorValues = normalize(motorValues);
        setDrive(motorValues);
    }

    private double[] normalize(double[] motorValues)
    {
        double maxMagnitude = 0;
        double clampRatio;

        for(int i = 0; i < motorValues.length; i++)
        {
            if(java.lang.Math.abs(motorValues[i]) > maxMagnitude)
            {
                maxMagnitude = java.lang.Math.abs(motorValues[i]);
            }
        }

        if(maxMagnitude > RobotConstants.DRIVE_MOTOR_CLAMP)
        {
            clampRatio = (RobotConstants.DRIVE_MOTOR_CLAMP / maxMagnitude);

            for(int i = 0; i < motorValues.length; i++)
            {
                motorValues[i] *= clampRatio;
            }
        }

        return motorValues;
    }

    public void setDriveMecanum(double[] target)
    {
        setDriveMecanum(target[0], target[1], target[2]);
    }

    public void setDrive(double[] motorValues)
    {
        try
        {
            //System.out.println("FL: " + motorValues[0] + " FR: " + motorValues[1] + " BL: " + motorValues[2] + " BR: " + motorValues[3]);
            double[] curValues = {m_frontleft.getX(),
                                  m_frontright.getX(),
                                  m_backleft.getX(),
                                  m_backright.getX()};


            double[] targets = {convertToDouble(RobotConstants.DRIVE_MOTOR_FRONT_LEFT_INVERTED) * motorValues[0],
                                convertToDouble(RobotConstants.DRIVE_MOTOR_FRONT_RIGHT_INVERTED) * motorValues[1],
                                convertToDouble(RobotConstants.DRIVE_MOTOR_BACK_LEFT_INVERTED) * motorValues[2],
                                convertToDouble(RobotConstants.DRIVE_MOTOR_BACK_RIGHT_INVERTED) * motorValues[3]};

            //System.out.println("FL: " + curValues[0] + " FR: " + curValues[1] + " BL: " + curValues[2] + " BR: " + curValues[3]);
            m_frontleft.setX(targets[0]);
            m_frontright.setX(targets[1]);
            m_backleft.setX(targets[2]);
            m_backright.setX(targets[3]);
            //System.out.println("lolz");

            /*if(DriverStation.getInstance().getDigitalIn(1))
            {
                m_frontleft.setX(0);
                m_frontright.setX(0);
                m_backleft.setX(0);
                m_backright.setX(0);
            }*/

            //System.out.println("FL: " + m_frontleft.getX() + " FR: " + m_frontright.getX() + " BL: " + m_backleft.getX() + " BR: " + m_backright.getX());
            //System.out.println("FL: " + m_frontleft.getP() + " FR: " + m_frontright.getP() + " BL: " + m_backleft.getP() + " BR: " + m_backright.getP());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setDrive(double speed)
    {
        try
        {
            m_frontleft.setX(speed);
            m_frontright.setX(speed);
            m_backleft.setX(speed);
            m_backright.setX(speed);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setDrive(double speed, int motornumber)
    {
        try
        {
            switch(motornumber)
            {
                case RobotConstants.DRIVE_CANJAGUAR_FRONT_LEFT_DEVICE_ID:
                    m_frontleft.setX(speed);
                    break;
                case RobotConstants.DRIVE_CANJAGUAR_FRONT_RIGHT_DEVICE_ID:
                    m_frontright.setX(speed);
                    break;
                case RobotConstants.DRIVE_CANJAGUAR_BACK_LEFT_DEVICE_ID:
                    m_backleft.setX(speed);
                    break;
                case RobotConstants.DRIVE_CANJAGUAR_BACK_RIGHT_DEVICE_ID:
                    m_backright.setX(speed);
                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

    public void twist(double magnitude)
    {
        double[] motorValues = new double[4];
        motorValues[0] = -magnitude;
        motorValues[1] = magnitude;
        motorValues[2] = -magnitude;
        motorValues[3] = magnitude;

        setDrive(motorValues);
    }

    public double getSpeed(int motornumber)
    {
        try
        {
            switch(motornumber)
            {
                case RobotConstants.DRIVE_CANJAGUAR_FRONT_LEFT_DEVICE_ID:
                    return m_frontleft.getSpeed();
                case RobotConstants.DRIVE_CANJAGUAR_FRONT_RIGHT_DEVICE_ID:
                    return m_frontright.getSpeed();
                case RobotConstants.DRIVE_CANJAGUAR_BACK_LEFT_DEVICE_ID:
                    return m_backleft.getSpeed();
                case RobotConstants.DRIVE_CANJAGUAR_BACK_RIGHT_DEVICE_ID:
                    return m_backright.getSpeed();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public double getX(int motornumber)
    {
        try
        {
            switch(motornumber)
            {
                case RobotConstants.DRIVE_CANJAGUAR_FRONT_LEFT_DEVICE_ID:
                    return m_frontleft.getX();
                case RobotConstants.DRIVE_CANJAGUAR_FRONT_RIGHT_DEVICE_ID:
                    return m_frontright.getX();
                case RobotConstants.DRIVE_CANJAGUAR_BACK_LEFT_DEVICE_ID:
                    return m_backleft.getX();
                case RobotConstants.DRIVE_CANJAGUAR_BACK_RIGHT_DEVICE_ID:
                    return m_backright.getX();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public String getCurrents()
    {
        String ret = "D";
        try
        {
            // apply some averaging to the currrent values so they dont bounce around too much
            acurrent[0] = (acurrent[0]*7 + m_frontleft.getOutputCurrent())/8;
            ret += Math.floor(acurrent[0] * 10)/10 + ",";
            acurrent[1] = (acurrent[1]*7 + m_frontright.getOutputCurrent())/8;
            ret += Math.floor(acurrent[1] * 10)/10 + ",";
            acurrent[2] = (acurrent[2]*7 + m_backleft.getOutputCurrent())/8;
            ret += Math.floor(acurrent[2] * 10)/10 + ",";
            acurrent[3] = (acurrent[3]*7 + m_backright.getOutputCurrent())/8;
            ret += Math.floor(acurrent[3] * 10)/10;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
}
