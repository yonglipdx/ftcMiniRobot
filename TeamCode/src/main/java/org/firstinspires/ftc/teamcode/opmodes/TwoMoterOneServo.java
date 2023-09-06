package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;

@TeleOp()
public class TwoMoterOneServo extends OpMode {

    ProgrammingBoard8 robot = new ProgrammingBoard8();
    enum ClawState {OPEN, GRAB;};
    ClawState clawState;

    boolean ideal = true;
    boolean move;
    boolean switchClaw;

    float robotLeftMove;
    float robotRightMove;
    float speedFactor;

    int loopCount = -1;

    Gamepad currGamepad1 = new Gamepad();
    Gamepad prevGamepad1 = new Gamepad();

    public void resetMotor(){
      robot.setLeftMotorSpeed(0.0);
      robot.setRightMotorSpeed(0.0);
    }

    public void readAndParseGamePad() {
        float scaleAL = 1.0f;

        ideal = true;
        move = false;
        switchClaw = false;

        prevGamepad1.copy(currGamepad1);
        currGamepad1.copy(gamepad1);
        robotLeftMove = -currGamepad1.left_stick_y;
        robotRightMove = -currGamepad1.right_stick_y;
        speedFactor = gamepad1.right_trigger;

        if (Math.abs(robotLeftMove) > 0.01 || Math.abs(robotRightMove) > 0.01 ) {
            move = true;
            robotLeftMove = robotLeftMove * speedFactor * scaleAL;
            robotRightMove = robotRightMove * speedFactor * scaleAL;
            ideal = false;
            return;
        }
        switchClaw = !prevGamepad1.right_bumper && currGamepad1.right_bumper;
        ideal = !switchClaw;
    }

    @Override
    public void init() {

        telemetry.addData("Init starting...","");
        robot.init(hardwareMap);
        clawState = ClawState.OPEN;
        move = false;
        switchClaw = false;
        resetMotor();
        //robot.motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {

       if (++loopCount % 1000 == 0)
       telemetry.addData("Current loop: ", loopCount);

       robot.leftFrontLed.enableLight(false);
       robot.rightFrontLed.enableLight(false);

       readAndParseGamePad();

        if (ideal){
          resetMotor();
        }
        else if (move) {
            robot.setLeftMotorSpeed(robotLeftMove);
            robot.setRightMotorSpeed(robotRightMove);
            robot.leftFrontLed.enableLight(true);
        }
        else if (switchClaw) {  // claw
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
                // Athena: when claw opens, both Leds should light
                robot.leftFrontLed.enableLight(true);
            }
            robot.rightFrontLed.enableLight(true);
        }
    }
}
