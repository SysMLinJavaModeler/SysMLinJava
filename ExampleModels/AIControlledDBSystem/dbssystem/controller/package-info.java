/**
 * Model elements that comprise the DBS Controller part of the DBS System model.
 * It includes the {@code DBSControllerContainer} that encapsulates the
 * DBSController allowing it (and it's AI-based control mechanism) to execute in
 * its own operating system process. The package includes the DBSController's
 * DBSANNConstraintBlock that has as its constraint the DBSANN which is an
 * artificial neural network trained to use patient monitoring data to control
 * the deep-brain stimulator that controls the patient's tremor.
 */
package dbssystem.controller;