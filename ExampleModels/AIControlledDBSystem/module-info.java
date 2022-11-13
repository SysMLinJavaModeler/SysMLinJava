/**
 * The {@code AIControllerDBSSystem} is the SysMLinJava executable model of an
 * AI-based control system that uses deep-brain stimulation (DBS) to control
 * (reduce) a patient's tremor. A minimal block diagram of the domain is as
 * follows:<br>
 * <img src="DBSSystemModel.png" alt="PNG file not
 * available" height="800"><br>
 * The model is highly-simplified and artifically constrained to demonstrate key
 * concepts of using SysMLinJava to model and simulate AI-based systems.
 * <p>
 * The {@code AIControlledDBSSystem} module uses the {@code dbssystem} package
 * and all its sub-packages and requires the {@code sysMLinJava} module as well
 * as the {@code neurophcore} module (jar file) for the AI neural-network
 * framework API. This {@code neurophcore} jar is necessarily an "automatic"
 * module because of its legacy configuration. This may cause an IDE to raise a
 * warning as to the use of this jar file, but is still performs as desired for
 * the executable model.
 * <p>
 * Note that all of the packages in this module are fully exported and opened.
 * This "openness" is consistent with this module being a SysMLinJava model, not
 * just a software module, whereby all the code is what constitutes the model.
 * Exposing all of the model enables its virtually unlimited applications.
 * 
 * @author ModelerOne
 *
 */
module AIControlledDBSSystem
{
	exports dbssystem.sensors;
	exports dbssystem.controller;
	exports dbssystem.actuators;
	exports dbssystem.common;
	exports dbssystem.patient;

	opens dbssystem.sensors;
	opens dbssystem.controller;
	opens dbssystem.actuators;
	opens dbssystem.common;
	opens dbssystem.patient;

	requires java.logging;
	requires transitive sysMLinJava;
	requires neurophcore;
}