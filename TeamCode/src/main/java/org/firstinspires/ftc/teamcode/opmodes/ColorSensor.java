package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;

@Disabled
@TeleOp
public class ColorSensor extends OpMode {
    ProgrammingBoard8 board = new ProgrammingBoard8(); 
    @Override
    public void init(){
        board.init(hardwareMap);
    }

    @Override
    public void loop(){
        telemetry.addData("Amount of red5", board.getAmountRed());
        telemetry.addData("Distance in  (CM)", board.getDistance(DistanceUnit.CM));
        telemetry.addData("Distance in (Inch)", board.getDistance(DistanceUnit.INCH));

    }
}
