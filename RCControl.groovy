// code here

import org.apache.commons.io.IOUtils;
import  com.neuronrobotics.bowlerstudio.physics.*;
import com.neuronrobotics.bowlerstudio.scripting.ScriptingEngine
import com.neuronrobotics.bowlerstudio.threed.*;
import com.neuronrobotics.sdk.addons.kinematics.DHParameterKinematics
import com.neuronrobotics.sdk.addons.kinematics.MobileBase
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR
import com.neuronrobotics.sdk.common.DeviceManager
import com.neuronrobotics.sdk.util.ThreadUtil
MobileBase base;
//Check if the device already exists in the device Manager
if(args==null){
	base=DeviceManager.getSpecificDevice( "VexVitaminsRobot",{ScriptingEngine.gitScriptRun(	
		"https://github.com/madhephaestus/VexVitaminsRobot.git",
		"VexVitaminsRobot.xml", 
		null )})
}else
	base=args.get(0)


println "Now we will move just one arm"
DHParameterKinematics arm = base.getAppendages().get(0)
double zLift=25
println "Start from where the arm already is and move it from there with absolute location"
TransformNR current = arm.getCurrentPoseTarget();
current.translateZ(zLift);
arm.setDesiredTaskSpaceTransform(current,  2.0);
ThreadUtil.wait(2000)// wait for the arm to fully arrive

TransformNR move = new TransformNR(-30,1,0,new RotationNR())
double toSeconds=1//100 ms for each increment
for(int i=0;i<10;i++){
	base.DriveArc(move, toSeconds);
	ThreadUtil.wait((int)toSeconds*1000)
}
//TransformNR move2 = new TransformNR(0,-30,0,new RotationNR())
//double toSeconds2=1//100 ms for each increment
//for(int j=0;j<10;j++){
//	base.DriveArc(move2, toSeconds2);
//	ThreadUtil.wait((int)toSeconds2*1000)
//}