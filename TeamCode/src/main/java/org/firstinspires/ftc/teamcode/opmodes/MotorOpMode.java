package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard3;

@TeleOp
public class MotorOpMode extends OpMode {
    ProgrammingBoard3 board = new ProgrammingBoard3();
    @Override
    public void init(){
        board.init(hardwareMap);
    }

    @Override
    public void loop(){

        board.setLeftMotorSpeed(0.1);
        board.setRightMotorSpeed(0.5);
        //sleep(50000);
        //board.setLeftMotorSpeed(0.0);
        //board.setRightMotorSpeed(0.0);
    }
}
