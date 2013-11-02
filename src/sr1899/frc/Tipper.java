/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sr1899.frc;

import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author Developer
 */
public class Tipper
{
    private final Servo tipServo;
    private final int deployAngle = RobotConstants.TIPPER_SERVO_DEPLOY_ANGLE;
    private final int retractAngle = RobotConstants.TIPPER_SERVO_RETRACT_ANGLE;

    public Tipper()
    {
        tipServo = new Servo(RobotConstants.TIPPER_SERVO_CHANNEL);
    }

    public void deploy(boolean bVal)
    {
        if (bVal)
            tipServo.setAngle(deployAngle);
        else
            tipServo.setAngle(retractAngle);
    }
}
