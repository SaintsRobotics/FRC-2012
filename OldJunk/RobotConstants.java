package sr1899.frc;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Huadian
 */
public class RobotConstants
{
    /**
     * How many encoder ticks are equal to one turn of the wheels.
     */
    public static final int DRIVE_ENCODER_CODES_PER_REV = 250;
    /**
     * How many turns are on the potentiometer per 360 degrees.
     */
    public static final int DRIVE_POTENTIOMETER_TURNS = 90;
    public static final double DRIVE_CANJAGUAR_P = 0.500;
    public static final double DRIVE_CANJAGUAR_I = 0.003;
    public static final double DRIVE_CANJAGUAR_D = 0.001;

    public static final CANJaguar.ControlMode DRIVE_CAN_CONTROL_MODE = CANJaguar.ControlMode.kSpeed;
    public static final CANJaguar.SpeedReference DRIVE_CAN_SPEED_REFERENCE = CANJaguar.SpeedReference.kEncoder;
    public static final CANJaguar.NeutralMode DRIVE_CAN_NEUTRAL_MODE = CANJaguar.NeutralMode.kBrake;

    public static final int DRIVE_CANJAGUAR_FRONT_LEFT_DEVICE_ID = 3;
    public static final int DRIVE_CANJAGUAR_FRONT_RIGHT_DEVICE_ID = 4;
    public static final int DRIVE_CANJAGUAR_BACK_LEFT_DEVICE_ID = 2;
    public static final int DRIVE_CANJAGUAR_BACK_RIGHT_DEVICE_ID = 5;

    public static final double DRIVE_MOTOR_CLAMP = 450;

    //public static final boolean DRIVE_RAMPING = false;
    //public static final double DRIVE_RAMP_PERCENT = 0.5;

    public static final boolean DRIVE_MOTOR_FRONT_LEFT_INVERTED = false;
    public static final boolean DRIVE_MOTOR_FRONT_RIGHT_INVERTED = true;
    public static final boolean DRIVE_MOTOR_BACK_LEFT_INVERTED = false;
    public static final boolean DRIVE_MOTOR_BACK_RIGHT_INVERTED = true;

    public static final int JOYSTICK_PORT_ID = 1;
    public static final int JOYSTICK_AXIS_COUNT = 4;
    public static final int JOYSTICK_BUTTON_COUNT = 14;

    public static final int JOYSTICK_PORT_ID2 = 2;
    public static final int JOYSTICK_AXIS_COUNT2 = 4;
    public static final int JOYSTICK_BUTTON_COUNT2 = 14;

    public static final int JOYSTICK_STRAFE_AXIS = 1;
    public static final int JOYSTICK_THROTTLE_AXIS = 2;
    public static final int JOYSTICK_TWIST_AXIS = 4;

    public static final int JOYSTICK_STRAFE_AXIS_HARMAN = 4;
    public static final int JOYSTICK_THROTTLE_AXIS_HARMAN = 3;
    public static final int JOYSTICK_TWIST_AXIS_HARMAN = 1;

    public static final double JOYSTICK_STRAFE_FACTOR = 1;
    public static final double JOYSTICK_THROTTLE_FACTOR = 1;
    public static final double JOYSTICK_TWIST_FACTOR = 0.675;

    public static final double JOYSTICK_DEADZONE = 0.010; //0.007;

    // dont have both drive and joysticking ramping on
    public static final boolean JOYSTICK_RAMPING = true;
    public static final double JOYSTICK_RAMP_PERCENT = .8;

    public static final boolean JOYSTICK_SLOW_TOGGLE = false;
    public static final int[] JOYSTICK_SLOW_BUTTON = {5, 0};
    public static final double JOYSTICK_SLOW_FACTOR = 10;

    public static final double JOYSTICK_LAUNCHER_MIN = 0;
    public static final double JOYSTICK_LAUNCHER_MAX = 1;
    public static final double JOYSTICK_LAUNCHER_INCREMENT = 0.01;
    public static final int[] JOYSTICK_LAUNCHER_INCREASE_BUTTON = {6, 1};
    public static final int[] JOYSTICK_LAUNCHER_DECREASE_BUTTON = {5, 1};

    public static final int[] JOYSTICK_PICKUP_LIFTER_BUTTON = {1, 1};
    public static final boolean JOYSTICK_PICKUP_LIFTER_TOGGLE = true;

    public static final int[] JOYSTICK_PICKUP_FEEDER_BUTTON = {2, 1};
    public static final boolean JOYSTICK_PICKUP_FEEDER_TOGGLE = false;

    public static final int[] JOYSTICK_TIPPER_DEPLOY_BUTTON = {3, 1};
    public static final boolean JOYSTICK_TIPPER_DEPLOY_TOGGLE = true;

    public static final int[] JOYSTICK_HARMAN_BUTTON = {3, 0};
    public static final boolean JOYSTICK_HARMAN_TOGGLE = true;

    public static final int[] LAUNCHER_DEVICE_IDS = {6, 7, 8, 9};
    public static final boolean[] LAUNCHER_MOTOR_INVERTED = {true, true, true, true};

    public static final int LAUNCHER_ENCODER_CODES_PER_REV = 200;

    public static final int LAUNCHER_POTENTIOMETER_TURNS = 1;

    public static final double LAUNCHER_CANJAGUAR_P = 0.500;
    public static final double LAUNCHER_CANJAGUAR_I = 0.003;
    public static final double LAUNCHER_CANJAGUAR_D = 0.001;

    public static final double LAUNCHER_P = 3.000;
    public static final double LAUNCHER_I = 0.000;
    public static final double LAUNCHER_D = 0.000;

    public static final CANJaguar.ControlMode LAUNCHER_CAN_PID_CONTROL_MODE = CANJaguar.ControlMode.kSpeed;
    public static final CANJaguar.ControlMode LAUNCHER_CAN_CONTROL_MODE = CANJaguar.ControlMode.kPercentVbus;
    public static final CANJaguar.SpeedReference LAUNCHER_CAN_SPEED_REFERENCE = CANJaguar.SpeedReference.kEncoder;
    public static final CANJaguar.NeutralMode LAUNCHER_CAN_NEUTRAL_MODE = CANJaguar.NeutralMode.kBrake;

    public static final int LAUNCHER_ENCODER_MODULE = 1;
    public static final int[] LAUNCHER_ENCODER_PIDS = {1, 2};
    public static final boolean LAUNCHER_ENCODER_MODULE_DIRECTION = false;

    public static final double LAUNCHER_MOTOR_CLAMP = 1.0;

    public static final boolean LAUNCHER_RAMPING = true;
    public static final double LAUNCHER_RAMP_PERCENT = 0.9;

    // 55% is setup for 2700rpm, so 100% 4900
    public static final double LAUNCHER_SPEED_CONVERT = 4900;

    // How close the Software PID should control the RPM, e.g.. for 2700RPM error is 13.5RPM
    public static final double LAUNCHER_SPEED_TOLERANCE = 0.005;

    // Jag is running pid, dont forget to change CAN_CONTROL_MODE if using jag PID
    public static final boolean LAUNCHER_MOTOR_JAGUAR_PID_MODE = false;
    // software is running pid
    public static final boolean LAUNCHER_MOTOR_PID_MODE = false;

    public static final int PICKUP_RELAY_MODULEID = 1;

    public static final int[] PICKUP_LIFTER_CHANNELS = {1, 3};
    public static final Relay.Direction PICKUP_LIFTER_DIRECTION = Relay.Direction.kForward;

    public static final int[] PICKUP_FEEDER_CHANNELS = {2};
    public static final Relay.Direction PICKUP_FEEDER_DIRECTION = Relay.Direction.kForward;

    public static final int AUTONOMOUS_ROUTINE = 0;

    public static final int TIPPER_SERVO_CHANNEL = 1;
    public static final int TIPPER_SERVO_DEPLOY_ANGLE = 10;
    public static final int TIPPER_SERVO_RETRACT_ANGLE = 170;
}