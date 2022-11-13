/**
 * Sensors that sense the patient's tremor and pulse and provide signals to the
 * DBSController for control of the SBS signal injected into the patient's
 * brain. The package includes the TremorSensor and PulseSensor model elements.
 * It also includes the {@code DBSSensorsContainer} that encapsulates the two
 * sensors allowing them to execute in their own operating system process,
 * thereby more closely simulating the operating environment of actual sensors.
 */
package dbssystem.sensors;