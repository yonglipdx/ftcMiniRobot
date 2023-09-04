package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;


@TeleOp()
public class TwoMoterOneServo extends OpMode {

    ProgrammingBoard8 robot = new ProgrammingBoard8();

    enum ClawState {OPEN, GRAB;};
    ClawState clawState = ClawState.OPEN;
    boolean clawSwitch = false;

    float robotLeftMove;
    float robotRightMove;
    float speedFactor;

    public void readAndParseGamePad() {

        robotLeftMove = -gamepad1.left_stick_y;
        robotRightMove = -gamepad1.right_stick_y;

        clawSwitch = gamepad1.right_bumper;

        speedFactor = gamepad1.right_trigger;
        robotLeftMove *= speedFactor;
        robotRightMove *= speedFactor;
    }


    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {

       robot.leftFrontLed.enableLight(false);
       robot.rightFrontLed.enableLight(false);

       robot.setLeftMotorSpeed(0.0);
       robot.setRightMotorSpeed(0.0);

       readAndParseGamePad();

       if (Math.abs(robotLeftMove) > 1e-5 || Math.abs(robotRightMove) > 1e-5 ){
            robot.setLeftMotorSpeed(robotLeftMove);
            robot.setRightMotorSpeed(robotRightMove);
            robot.leftFrontLed.enableLight(true);
        } else if (clawSwitch) {  // claw
            if (clawState == ClawState.OPEN) {
                telemetry.addData("Before clawGrap ", robot.servo.getPosition());
                robot.clawGrab();
                telemetry.addData("After clawGrap ", robot.servo.getPosition());
                clawState = ClawState.GRAB;
            } else if (clawState == ClawState.GRAB) {
                telemetry.addData("Before clawOpen ", robot.servo.getPosition());
                robot.clawOpen();
                telemetry.addData("After clawOpen ", robot.servo.getPosition());
                clawState = ClawState.OPEN;
            }
            robot.rightFrontLed.enableLight(true);
        }
    }
}
