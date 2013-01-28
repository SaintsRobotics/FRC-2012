package sr1899.frc;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Huadian
 */
public class DriverStationComm
{
    private DriverStation DS;
    private DriverStationEnhancedIO DS_IO;
    private DriverStationLCD LCD;
    private Dashboard DB;
    private NetworkTable NT;
    //private ZombDashboard zb;

    public DriverStationComm()
    {
        DS = DriverStation.getInstance();
        DS_IO = DS.getEnhancedIO();
        LCD = DriverStationLCD.getInstance();
        DB = DS.getDashboardPackerLow();
        NT = new NetworkTable();
    }

    public void printUserMsg(DriverStationLCD.Line line, int startingColumn, String message)
    {
        // truncate string if too long
        if(message.length() > DriverStationLCD.kLineLength)
        {
            LCD.println(line, startingColumn, message.substring(0, DriverStationLCD.kLineLength-1));
        }
        else
        {
            LCD.println(line, startingColumn, pad(message));
        }
        LCD.updateLCD();
    }

    public void printBoolean(String label, boolean bool, DriverStationLCD.Line line)
    {
        printUserMsg(line, 1, label + booleanToString(bool));
    }

    private String booleanToString(boolean bool)
    {
        if (bool)
        {
            return "True";
        }
        else
        {
            return "False";
        }
    }

    private String pad(String string)
    {
        String blank = new String(new byte[DriverStationLCD.kLineLength]);
        return string + blank.substring(string.length());
    }

    public void setEnhancedLED(boolean on, int channel)
    {
        try
        {
            DS_IO.setLED(channel, on);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void UpdateDB(boolean lifter, boolean feeder, boolean slow, int launchRPM)
    {
        SmartDashboard.putBoolean("Lifter", lifter);
        SmartDashboard.putBoolean("Feeder", feeder);
        SmartDashboard.putBoolean("Slow", slow);
        SmartDashboard.putString("Launch", launchRPM + "");
        SmartDashboard.putInt("ShooterRPM", launchRPM);
        Timer.delay(0.01);

        /*NT.beginTransaction();
        NT.putBoolean("Lifter", lifter);
        NT.putBoolean("Feeder", feeder);
        NT.putBoolean("Slow", slow);
        NT.putString("Launch", launchRPM + "");
        NT.putInt("ShooterRPM", launchRPM);
        NT.endTransaction();*/
    }
}
