package org.firstinspires.ftc.teamcode.mechanisms;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ProgrammingBoard3 {
    private DcMotor motorLeft, motorRight;
    public void init(HardwareMap hwMap){
        motorLeft = hwMap.get(DcMotor.class, "CoreHexMotorLeft");
        motorRight = hwMap.get(DcMotor.class, "CoreHexMotorRight");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.FORWARD);
    }

    public void setLeftMotorSpeed(double speed){
        motorLeft.setPower(speed);
    }
    public void setRightMotorSpeed(double speed){
        motorRight.setPower(speed);
    }
}
