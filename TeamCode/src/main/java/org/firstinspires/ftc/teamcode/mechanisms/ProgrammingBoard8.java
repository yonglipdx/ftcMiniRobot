package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ProgrammingBoard8 {
        private ColorSensor colorSensor;
        private DistanceSensor distanceSensor;
        private DcMotor motorLeft, motorRight;
        private IMU imu;
        public Servo servo;
        public LED Led;

        public void init(HardwareMap hwMap){
            colorSensor = hwMap.get(ColorSensor.class, "ColorSensor");
            distanceSensor = hwMap.get(DistanceSensor.class, "ColorSensor");
            imu = hwMap.get(IMU.class, "imu");

            motorLeft = hwMap.get(DcMotor.class, "CoreHexMotorLeft");
            motorRight = hwMap.get(DcMotor.class, "CoreHexMotorRight");
            motorLeft.setDirection(DcMotor.Direction.REVERSE);
            motorRight.setDirection(DcMotor.Direction.FORWARD);

            RevHubOrientationOnRobot RevOrientation =
                    new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
            imu.initialize(new IMU.Parameters(RevOrientation));
            servo = hwMap.get(Servo.class, "Servo");
            Led = hwMap.get(LED.class, "Led");
        }

        public int getAmountRed(){
            return colorSensor.red();
        }
        public double  getDistance(DistanceUnit du){
            return distanceSensor.getDistance(du);
        }

        public double getYaw(AngleUnit angleUnit) {
            double degree =  imu.getRobotYawPitchRollAngles().getYaw(angleUnit);
            return degree;
        }

        public double getPitch(AngleUnit angleUnit) {
             double degree =  imu.getRobotYawPitchRollAngles().getPitch(angleUnit);
             return degree;
        }

        public double getRoll(AngleUnit angleUnit) {
         double degree =  imu.getRobotYawPitchRollAngles().getRoll(angleUnit);
         return degree;
        }

        public void setLeftMotorSpeed(double speed){
        motorLeft.setPower(speed);
    }
        public void setRightMotorSpeed(double speed){
        motorRight.setPower(speed);
    }


}
