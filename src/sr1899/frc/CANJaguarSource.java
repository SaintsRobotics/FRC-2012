/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sr1899.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Huadian
 */
public class CANJaguarSource implements PIDSource
{
    private CANJaguar[] motors;

    private static double lastspeed;
    private static double aspeed;

    public CANJaguarSource(CANJaguar[] motors)
    {
        this.motors = motors;
        lastspeed = 0;
    }

    public double pidGet()
    {
        double total = 0;
        try
        {
            total = motors[0].getSpeed();
        }
        catch (Exception e)
        {
            // if we get an exception use last speed
//            total = lastspeed;
            e.printStackTrace();
        }
        aspeed = (aspeed*7 + total)/8;
        total = aspeed;
//        lastspeed = total;
        // convert from RPM to 0 - 1
        //System.out.println("GETspeed: " + total / RobotConstants.LAUNCHER_SPEED_CONVERT);

        return total / RobotConstants.LAUNCHER_SPEED_CONVERT;
    }
}
