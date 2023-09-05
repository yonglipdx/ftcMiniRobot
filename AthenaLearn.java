package overcharged.opmode;
import static overcharged.config.RobotConstants.TAG_SL;
import static overcharged.config.RobotConstants.TAG_T;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.HashMap;
import java.util.Map;

import overcharged.components.Button;
import overcharged.components.MecanumDrive;
import overcharged.components.RobotMecanum;
import overcharged.components.hSlides;

/**
 * Overcharged Team #12599
 * Teleop for Mecanum robot blue alliance
 */
@Config
@TeleOp(name="AthenaLearn", group="Game")
public class AthenaLearn extends OpMode {

    private boolean retractdeposit = false;
    float robotXMove;
    float robotYMove;
    float robotRotationMove;
    boolean reverseRotation;
    float vSlidesMove;
    float turretRotationMove;
    public enum ClawState { OPEN, GRAB; }
    ClawState clawState;
    boolean clawSwitch;
    float speedFactor;
    RobotMecanum robot;

    public void readAndParseGamePad() {

        // 1. robot X|Y move  (joystick L)
        robotXMove = -gamepad1.left_stick_x;
        robotXMove *= 1.1;
        robotYMove = gamepad1.left_stick_y;

        // 2. robot rotation  (left trigger)
        robotRotationMove = -gamepad1.left_trigger;

        // 3. vslides move (joystick Y right)
        vSlidesMove = gamepad1.right_stick_y;

        // 4. turret rotation (joystick X left)
        turretRotationMove = gamepad1.right_stick_x;

        // 5. claw switch
        clawSwitch = false;
        if (gamepad1.right_bumper) {
            clawSwitch = true;
            telemetry.addData("toggling claw", clawSwitch);
        }

        reverseRotation = gamepad1.x;
        if (reverseRotation) robotRotationMove *= -1;

        speedFactor = gamepad1.right_trigger;
        robotXMove *= speedFactor;
        robotYMove *= speedFactor;
        robotRotationMove *= speedFactor;
        vSlidesMove *= speedFactor;
        turretRotationMove *= speedFactor;

        telemetry.addData("speedFactor", speedFactor);
    }

    public void resetPower(){
        robot.driveRightFront.setPower(0);
        robot.driveRightBack.setPower(0);
        robot.driveLeftFront.setPower(0);
        robot.driveLeftBack.setPower(0);
        robot.vSlides.slideMiddle.setPower(0);
        robot.vSlides.slideLeft.setPower(0);
        robot.vSlides.slideRight.motor.setPower(0);
        robot.turret.turret.motor.setPower(0);
        vSlidesMove = 0;
        clawSwitch = false;
   }

    @Override
    public void init() {
        try {
            telemetry.addData("Starting AthenaLearn", "");
            telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
            robot = new RobotMecanum(this, false, false);
            robot.setBulkReadManual();
            retractdeposit = false;
            telemetry.addData("Status", "Initialized");
            telemetry.update();
            robot.turret.turret.setTargetPositionPIDFCoefficients(16, 0, 0, 0);
            resetPower();
            robot.clawOpen();
            clawState = ClawState.OPEN;
        } catch (Exception e) {
            telemetry.addData("Init Failed", e.getMessage());
            telemetry.update();
        }
    }

    @Override
    public void loop() {

        resetPower();
        readAndParseGamePad();
        if (Math.abs(robotXMove) > 1e-2 || Math.abs(robotYMove) > 1e-2 || Math.abs(robotRotationMove) > 1e-2) { // robot
            double x = robotXMove;
            double y = robotYMove;
            double rx = robotRotationMove;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator);
            double backLeftPower = ((y - x + rx) / denominator);
            double frontRightPower = ((y - x - rx) / denominator);
            double backRightPower = ((y + x - rx) / denominator);
            robot.driveLeftFront.setPower(frontLeftPower);
            robot.driveLeftBack.setPower(backLeftPower);
            robot.driveRightFront.setPower(frontRightPower);
            robot.driveRightBack.setPower(backRightPower);
        } else if (Math.abs(vSlidesMove) > 1e-2) { // vslide
            // robot.vSlides.slideLeft.setPower(vSlidesMove);
            // robot.vSlides.slideMiddle.setPower(vSlidesMove);
            // robot.vSlides.slideRight.setPower(vSlidesMove);
            robot.vSlides.slideLeft.motor.setPower(vSlidesMove);
            robot.vSlides.slideMiddle.motor.setPower(vSlidesMove);
            robot.vSlides.slideRight.motor.setPower(vSlidesMove);
            telemetry.addData("vSlides power2", vSlidesMove );
        } else if (Math.abs(turretRotationMove) > 1e-2) {  // turret
            robot.turret.turret.setPower(turretRotationMove);
        } else if (clawSwitch) {  // claw
            telemetry.addData("At clawSwitch", "");
            telemetry.addData("clawState: ", clawState);
            telemetry.addData("ClawState.OPEN: ", ClawState.OPEN);
            if (clawState == ClawState.OPEN) {
                telemetry.addData("Try to grab", "");
                robot.clawGrab();
                clawState = ClawState.GRAB;
            } else if (clawState == ClawState.GRAB) {
                telemetry.addData("Try to OPEN", "");
                robot.clawOpen();
                clawState = ClawState.OPEN;
            }
        }
    }
};

/*
gamepad1:
- joystick_left(x|y straight move) + left_trigger(rotation) + right_bumper(reverse rotation): arcade drive
- joystick_right(y vSlide)
- joystick_right(x Turret)
- right bumper = claw toggle switch
- right trigger = speed control
*/
