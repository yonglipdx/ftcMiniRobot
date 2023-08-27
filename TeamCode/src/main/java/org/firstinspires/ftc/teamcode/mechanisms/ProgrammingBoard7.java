

package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ProgrammingBoard7 {
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    public void init(HardwareMap hwMap){
        colorSensor = hwMap.get(ColorSensor.class, "ColorSensor");
        distanceSensor = hwMap.get(DistanceSensor.class, "ColorSensor");
    }

    public int getAmountRed(){
        return colorSensor.red();
    }

    public double getDistance(DistanceUnit du){
        return  distanceSensor.getDistance(du);
    }
}

