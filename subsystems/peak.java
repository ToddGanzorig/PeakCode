/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.PeakCommand;

/**
 * Add your docs here.
 */
public class peak extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

WPI_TalonSRX TalonL = new WPI_TalonSRX(32);
WPI_VictorSPX VictorR = new WPI_VictorSPX(44);

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  setDefaultCommand(new PeakCommand());
  }
  double speed2;
  double minuslimit;
double speed;
double cyclelimit = 0;
public void Encoder(Joystick stick) {

TalonL.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

TalonL.setSensorPhase(true);

int encLeftPos = TalonL.getSelectedSensorPosition();

boolean resetButton = stick.getRawButton(2);

if (stick.getRawButton(1) == true) {

  speed = 0.1 + cyclelimit;
  //I added 0.1 so that it will start from 0.1
  // cycle limit is the value that will increase every time the method runs
  cyclelimit += 0.002*5;
  // the above is the amount the cycle limit value will increase each time this function is run
    if (speed > 0.55) {
      speed = 0.55;
      // deadlock
    }
  TalonL.set(speed);
  VictorR.set(speed);
  // setting the speed for the talons
}

else if (encLeftPos > 1000) {
//if (stick.getRawButton(3) == true) {

  minuslimit += 0.002;
  // this is another value that will increase every time this function runs in the method. 
  // this value will increase by 0.002 for a steady and slow decrease in speed
  
  double speed2 = 0.4 - minuslimit;
  // this is my basic calculation for the speed

  TalonL.set(speed2);
  VictorR.set(speed2);
  // setting the talons to speed2 links the talons to the calculated speed
    if (speed2 < 0) {
      TalonL.set(0);
      VictorR.set(0);
            // this function stops the robot at 0 so that it wont keep on going negative
    }
}
else {
  TalonL.set(0);
  VictorR.set(0);
minuslimit = 0;
speed2 = 0;
cyclelimit = 0;
speed = 0;
// else function so that the button function will stop once the button isnt pressed anymore
}
if (resetButton == true) {
TalonL.setSelectedSensorPosition(0);
// reset button
}
SmartDashboard.putNumber("speed1", speed);
// speed is the output from the calculation of the inceasing value. it was put in the talons when button1 was pressed

SmartDashboard.putNumber("cyclelimit", cyclelimit);
// this is the number that increased the longer button1 is pressed

SmartDashboard.putNumber("encoder value", encLeftPos);
// my encoder value

SmartDashboard.putNumber("speed2", speed2);
//  speed2 is the output of the calculation for the decreasing value

SmartDashboard.putNumber("minuslimit" , minuslimit);
// minuslimit is basically cyclelimit for the decreasing value
}
}

