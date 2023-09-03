package org.firstinspires.ftc.teamcode.opmodes;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;
import org.firstinspires.ftc.teamcode.mechanisms.TwoMotorDrive;

@TeleOp()

public class ArcadeDriveWithServo extends OpMode {

    TwoMotorDrive wheel = new TwoMotorDrive();
    ProgrammingBoard8 body = new ProgrammingBoard8();
    Servo claw;
    LED Led;

    @Override
    public void init() {
        wheel.init(hardwareMap);
        body.init(hardwareMap);
        claw = body.servo;
        Led = body.Led;
    }

    @Override
    public void loop() {

        double clawPosition = 0.0;
        double powerFactor = gamepad1.right_trigger;
        Led.enableLight(false);

        if (gamepad1.a) clawPosition = 0.2;
        else if (gamepad1.b) clawPosition = 0.4;
        else if (gamepad1.x) clawPosition = 0.6;
        else if (gamepad1.y) clawPosition = 0.8;

        if (clawPosition > 0.001){  // claw. for example to grab and release cone
           telemetry.addData("claw postion: ", clawPosition);
           claw.setPosition(clawPosition);
          Led.enableLight(true);

        } else {  // wheel to move robot
            double forward = -gamepad1.left_stick_y;
            double right = gamepad1.left_stick_x / 2;
            wheel.setPowers(powerFactor * (forward + right), powerFactor * (forward - right));
            telemetry.addData("Left stick x", gamepad1.left_stick_x);
        }
    }
}
