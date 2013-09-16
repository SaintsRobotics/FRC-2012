/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sr1899.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Developer
 */
class CANJaguarOutput implements PIDOutput
{
    ReboundLauncher parent;

    public CANJaguarOutput()
    {

    }

    public void setParent(ReboundLauncher parent)
    {
        this.parent = parent;
    }

    public void pidWrite(double power)
    {
        parent.setJaguarSpeed(power);
    }
}
