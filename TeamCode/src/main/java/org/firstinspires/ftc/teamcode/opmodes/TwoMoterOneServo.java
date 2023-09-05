package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;


@TeleOp()
public class TwoMoterOneServo extends OpMode {

    ProgrammingBoard8 robot = new ProgrammingBoard8();
    enum ClawState {OPEN, GRAB;};
    ClawState clawState;
    boolean switchClaw;

    float robotLeftMove;
    float robotRightMove;
    float speedFactor;

    Gamepad currGamepad1 = new Gamepad();
    Gamepad prevGamepad1 = new Gamepad();

    public void readAndParseGamePad() {

        prevGamepad1.copy(currGamepad1);
        currGamepad1.copy(gamepad1);
        robotLeftMove = -currGamepad1.left_stick_y;
        robotRightMove = -currGamepad1.right_stick_y;
        switchClaw = !prevGamepad1.right_bumper && currGamepad1.right_bumper;
        speedFactor = gamepad1.right_trigger;
        robotLeftMove *= speedFactor;
        robotRightMove *= speedFactor;

    }


    @Override
    public void init() {
        robot.init(hardwareMap);
        clawState = ClawState.OPEN;
        boolean switchClaw = false;
    }

    @Override
    public void loop() {

       robot.leftFrontLed.enableLight(false);
       robot.rightFrontLed.enableLight(false);
       robot.setLeftMotorSpeed(0.0);
       robot.setRightMotorSpeed(0.0);

       readAndParseGamePad();

       if (Math.abs(robotLeftMove) > 0.01 || Math.abs(robotRightMove) > 0.01 ){
            robot.setLeftMotorSpeed(robotLeftMove);
            robot.setRightMotorSpeed(robotRightMove);
            robot.leftFrontLed.enableLight(true);
        } else if (switchClaw) {  // claw
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
