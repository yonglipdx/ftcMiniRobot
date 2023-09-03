package org.firstinspires.ftc.teamcode.opmodes;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard8;
import org.firstinspires.ftc.teamcode.mechanisms.TwoMotorDrive;

@Disabled
@TeleOp()
public class ArcadeDrive extends OpMode {
    NormalizedColorSensor colorSensor;
    final float[] hsvValues = new float[3];
    TwoMotorDrive drive = new TwoMotorDrive();
    ProgrammingBoard8 sensorBoard = new ProgrammingBoard8();
    @Override
    public void init() {
        drive.init(hardwareMap);
        sensorBoard.init(hardwareMap);
    }
    

    @Override
    public void loop() {
        double yaw =  sensorBoard.getYaw(AngleUnit.DEGREES);
        double pitch =  sensorBoard.getPitch(AngleUnit.DEGREES);
        double roll  =  sensorBoard.getRoll(AngleUnit.DEGREES);
        telemetry.addData("imu sensor Yaw（Z）: ", yaw );
        telemetry.addData("imu sensor Pitch（X）: ", pitch);
        telemetry.addData("imu sensor Roll（Y）", roll);
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        telemetry.addLine()
                .addData("Color sensor. Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
        telemetry.addData("Distance Sensor in CM", sensorBoard.getDistance(DistanceUnit.CM));
        //telemetry.addData("Distance Sensor in Inch", sensorBoard.getDistance(DistanceUnit.INCH));
        if (colors.red > colors.green && colors.red > colors.blue){
            telemetry.addData("\n******* Red object is detected.\nRed value: ", colors.red);
        }
        double speedFactor = 1.0;
        double distance = sensorBoard.getDistance(DistanceUnit.CM);
        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x/2;
        if (distance < 60){
            telemetry.addData("\n&&&&&&& Obstacle is detected. Distance(cm): ", distance);
            if (forward > 0) {
                speedFactor = 1 - (60- distance)/ 60;
            }
        }
        drive.setPowers(speedFactor*(forward + right), speedFactor*(forward - right));
    }
}
