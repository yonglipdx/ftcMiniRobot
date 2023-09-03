package org.firstinspires.ftc.teamcode.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;

@Disabled
@TeleOp()
public class GyroOpMode extends OpMode {
    ProgrammingBoard8 board = new ProgrammingBoard8();
    @Override
    public void init() {
        board.init(hardwareMap);
    }

    double left = 0.1;
    double right = 0.5;
    double total_degree = 0.0;
    double previous_yaw = 0.0;
    @Override
    public void loop() {
        board.setLeftMotorSpeed(left);
        board.setRightMotorSpeed(right);

        double yaw =  board.getYaw(AngleUnit.DEGREES);
        double pitch =  board.getPitch(AngleUnit.DEGREES);
        double roll  =  board.getRoll(AngleUnit.DEGREES);
        telemetry.addData("Yaw: ", yaw );
        telemetry.addData("Pitch: ", pitch);
        telemetry.addData("Roll", roll);

        if (previous_yaw > yaw) total_degree+=360;
        previous_yaw = yaw;
        /*
        if (total_degree > 3*360){
            left = 0.0;
            right = 0.0;
        }
        */
    }
}
