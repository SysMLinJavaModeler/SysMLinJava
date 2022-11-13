/**
 * Main package for the DBS System model which is a SysMLinJava model of a system
 * that uses AI-controlled deep brain stimulation (DBS) to reduce a patient's
 * tremor. The system consists of a tremor sensor that senses the tremor motion,
 * a pulse sensor that senses the patients pulse, a DBS actuator that injects a
 * signal into the patients brain, and a controller that uses a trained
 * artificial neural network to transform the tremor and pulse readings into
 * actuator controls for the DBS signal to be injected in the patient's brain.
 * <p>
 * The model demonstrates the concept modeling and simulating AI-based control
 * of a system and the use of the SysMLinJava {@code BlockContainer} to
 * construct and execute a model that is distributed across multiple operating
 * system processes. The {@code BlockContainer} enables virtually unlimited
 * scale and distribution of models to achieve high precsion modeling and
 * simulation of complex systems.
 */
package dbssystem;