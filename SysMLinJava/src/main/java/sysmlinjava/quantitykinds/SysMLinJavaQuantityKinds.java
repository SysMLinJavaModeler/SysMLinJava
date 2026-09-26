/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.quantitykinds;

import sysmlinjava.javaannotations.attributes.QuantityKind;

/**
 * SysMLinJava representation of a collection of SysML quantityKinds. Included
 * are the quantities of the SI prescribed in the SysML standard as well as a
 * number of other quantities that are useful for SysMLinJava modeling. As
 * static instances, the quantityKinds can be referenced in SysMLinJava code by
 * simply referencing the class scope with the quantityKind name, e.g.
 * 
 * <pre>
 * quantityKind = SysMLinJavaQuantityKinds.Length;
 * </pre>
 * <p>
 * Each {@code SysMLinJavaQuantityKind} instance is a static final instance in
 * this final {@code SysMLinJavaQuantityKind} class to reflect the static nature
 * of the SI quantities. Additional quantityKinds can be created outside of this
 * {@code  SysMLinJavaQuantityKind} class by creating a new class that is an
 * extension of the {@code SysMLQuantityKindsCollection} class and declaring
 * additional static final {@code SysMLQuantityKind} instances in the class,
 * just as done in this class.
 * <p>
 * Note that individual instances of {@code SysMLQuantityKind} are self
 * described by their individual field values, i.e. {@code description} and
 * {@code definitionURI}.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLinJavaQuantityKinds extends SysMLQuantityKindsCollection
{
	/** Instance of quantity for length */
	@QuantityKind
	public static final SysMLQuantityKind Length = new SysMLQuantityKind("length", "l", "Length", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for mass */
	@QuantityKind
	public static final SysMLQuantityKind Mass = new SysMLQuantityKind("mass", "m", "Mass", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for time */
	@QuantityKind
	public static final SysMLQuantityKind Time = new SysMLQuantityKind("time", "t", "Time", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric current */
	@QuantityKind
	public static final SysMLQuantityKind ElectricCurrent = new SysMLQuantityKind("electriccurrent", "i", "Electriccurrent", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for thermo temperature */
	@QuantityKind
	public static final SysMLQuantityKind ThermodynamicTemperature = new SysMLQuantityKind("thermodynamicTemperature", "tt", "ThermodynamicTemperature", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for amount of substance */
	@QuantityKind
	public static final SysMLQuantityKind AmountOfSubstance = new SysMLQuantityKind("amountOfSubstance", "as", "AmountOfSubstance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for luminous intensity */
	@QuantityKind
	public static final SysMLQuantityKind LuminousIntensity = new SysMLQuantityKind("luminousIntensity", "li", "LuminousIntensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for plan angle */
	@QuantityKind
	public static final SysMLQuantityKind PlaneAngle = new SysMLQuantityKind("planeAngle", "pa", "PlaneAngle", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for solid angle */
	@QuantityKind
	public static final SysMLQuantityKind SolidAngle = new SysMLQuantityKind("solidAngle", "sa", "SolidAngle", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for area */
	@QuantityKind
	public static final SysMLQuantityKind Area = new SysMLQuantityKind("area", "area", "Area", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for volume */
	@QuantityKind
	public static final SysMLQuantityKind Volume = new SysMLQuantityKind("volume", "vol", "Volume", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for speed */
	@QuantityKind
	public static final SysMLQuantityKind Speed = new SysMLQuantityKind("speed", "spd", "Speed", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for acceleration */
	@QuantityKind
	public static final SysMLQuantityKind Acceleration = new SysMLQuantityKind("acceleration", "acc", "Acceleration", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for jerk */
	@QuantityKind
	public static final SysMLQuantityKind Jerk = new SysMLQuantityKind("jerk", "j", "Jerk", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for wave number */
	@QuantityKind
	public static final SysMLQuantityKind WaveNumber = new SysMLQuantityKind("waveNumber", "wn", "WaveNumber", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for density */
	@QuantityKind
	public static final SysMLQuantityKind Density = new SysMLQuantityKind("density", "den", "Density", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for specific volume */
	@QuantityKind
	public static final SysMLQuantityKind SpecificVolume = new SysMLQuantityKind("specificVolume", "spv", "SpecificVolume", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for current density */
	@QuantityKind
	public static final SysMLQuantityKind CurrentDensity = new SysMLQuantityKind("currentDensity", "id", "CurrentDensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for magnetic field strength */
	@QuantityKind
	public static final SysMLQuantityKind MagneticFieldStrength = new SysMLQuantityKind("magneticFieldStrength", "mfsXX", "MagneticFieldStrength", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for concentration */
	@QuantityKind
	public static final SysMLQuantityKind Concentration = new SysMLQuantityKind("concentration", "con", "Concentration", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for luminance */
	@QuantityKind
	public static final SysMLQuantityKind Luminance = new SysMLQuantityKind("luminance", "lumin", "Luminance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for frequency */
	@QuantityKind
	public static final SysMLQuantityKind Frequency = new SysMLQuantityKind("frequency", "fq", "Frequency", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for force */
	@QuantityKind
	public static final SysMLQuantityKind Force = new SysMLQuantityKind("force", "XX", "f", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for pressure */
	@QuantityKind
	public static final SysMLQuantityKind Pressure = new SysMLQuantityKind("pressure", "p", "Pressure", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for energy */
	@QuantityKind
	public static final SysMLQuantityKind Energy = new SysMLQuantityKind("energy", "e", "Energy", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for power */
	@QuantityKind
	public static final SysMLQuantityKind Power = new SysMLQuantityKind("power", "pwr", "Power", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric charge */
	@QuantityKind
	public static final SysMLQuantityKind ElectricCharge = new SysMLQuantityKind("electricCharge", "e", "ElectricCharge", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric potential */
	@QuantityKind
	public static final SysMLQuantityKind ElectricPotential = new SysMLQuantityKind("electricPotential", "v", "ElectricPotential", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for capacitance */
	@QuantityKind
	public static final SysMLQuantityKind Capacitance = new SysMLQuantityKind("capacitance", "c", "Capacitance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric resistance */
	@QuantityKind
	public static final SysMLQuantityKind ElectricResistance = new SysMLQuantityKind("electricResistance", "r", "ElectricResistance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric conductance */
	@QuantityKind
	public static final SysMLQuantityKind ElectricConductance = new SysMLQuantityKind("electricConductance", "con", "ElectricConductance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for magnetic flux */
	@QuantityKind
	public static final SysMLQuantityKind MagneticFlux = new SysMLQuantityKind("magneticFlux", "flux", "MagneticFlux", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for magnetic flux density */
	@QuantityKind
	public static final SysMLQuantityKind MagenticFluxDensity = new SysMLQuantityKind("magenticFluxDensity", "fd", "MagenticFluxDensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for inductance */
	@QuantityKind
	public static final SysMLQuantityKind Inductance = new SysMLQuantityKind("inductance", "I", "Inductance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for temperature */
	@QuantityKind
	public static final SysMLQuantityKind Temperature = new SysMLQuantityKind("temperature", "T", "Temperature", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for luminous flux */
	@QuantityKind
	public static final SysMLQuantityKind LuminousFlux = new SysMLQuantityKind("luminousFlux", "lf", "LuminousFlux", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for illuminance */
	@QuantityKind
	public static final SysMLQuantityKind Illuminance = new SysMLQuantityKind("illuminance", "il", "Illuminance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for activity radio nuclide */
	@QuantityKind
	public static final SysMLQuantityKind ActivityRadioNuclide = new SysMLQuantityKind("activityRadioNuclide", "XX", "ActivityRadioNuclide", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for absorbed dose */
	@QuantityKind
	public static final SysMLQuantityKind AbsorbedDose = new SysMLQuantityKind("absorbedDose", "XX", "AbsorbedDose", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for dose equivalent */
	@QuantityKind
	public static final SysMLQuantityKind DoseEquivalent = new SysMLQuantityKind("doseEquivalent", "XX", "DoseEquivalent", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for moment of force */
	@QuantityKind
	public static final SysMLQuantityKind MomentOfForce = new SysMLQuantityKind("momentOfForce", "XX", "MomentOfForce", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for surface tension */
	@QuantityKind
	public static final SysMLQuantityKind SurfaceTension = new SysMLQuantityKind("surfaceTension", "XX", "SurfaceTension", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for heat flux density */
	@QuantityKind
	public static final SysMLQuantityKind HeatFluxDensity = new SysMLQuantityKind("heatFluxDensity", "XX", "HeatFluxDensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for heat capacity */
	@QuantityKind
	public static final SysMLQuantityKind HeatCapacity = new SysMLQuantityKind("heatCapacity", "XX", "HeatCapacity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for specific heat capacity */
	@QuantityKind
	public static final SysMLQuantityKind SpecificHeatCapacity = new SysMLQuantityKind("specificHeatCapacity", "XX", "SpecificHeatCapacity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for specific energy */
	@QuantityKind
	public static final SysMLQuantityKind SpecificEnergy = new SysMLQuantityKind("specificEnergy", "XX", "SpecificEnergy", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for thernal conductivity */
	@QuantityKind
	public static final SysMLQuantityKind ThermalConductivity = new SysMLQuantityKind("thermalConductivity", "XX", "ThermalConductivity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for energy density */
	@QuantityKind
	public static final SysMLQuantityKind EnergydDnsity = new SysMLQuantityKind("energydDnsity", "XX", "EnergydDnsity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric field strength */
	@QuantityKind
	public static final SysMLQuantityKind ElectricFieldStrength = new SysMLQuantityKind("electricFieldStrength", "XX", "ElectricFieldStrength", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric charge density */
	@QuantityKind
	public static final SysMLQuantityKind ElectricChargeDensity = new SysMLQuantityKind("electricChargeDensity", "XX", "ElectricChargeDensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for electric flux density */
	@QuantityKind
	public static final SysMLQuantityKind ElectricFluxDensity = new SysMLQuantityKind("electricFluxDensity", "XX", "ElectricFluxDensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for permittivity */
	@QuantityKind
	public static final SysMLQuantityKind Permittivity = new SysMLQuantityKind("permittivity", "XX", "Permittivity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for permeability */
	@QuantityKind
	public static final SysMLQuantityKind Permeability = new SysMLQuantityKind("permeability", "XX", "Permeability", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for molar energy */
	@QuantityKind
	public static final SysMLQuantityKind MolarEnergy = new SysMLQuantityKind("molarEnergy", "XX", "MolarEnergy", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for molar heat capacity */
	@QuantityKind
	public static final SysMLQuantityKind MolarHeatCapacity = new SysMLQuantityKind("molarHeatCapacity", "XX", "MolarHeatCapacity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for exposure */
	@QuantityKind
	public static final SysMLQuantityKind Exposure = new SysMLQuantityKind("exposure", "XX", "Exposure", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for absorbed dose rate */
	@QuantityKind
	public static final SysMLQuantityKind AbsorbedDoseRate = new SysMLQuantityKind("absorbedDoseRate", "XX", "AbsorbedDoseRate", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for angular velocity */
	@QuantityKind
	public static final SysMLQuantityKind AngularVelocity = new SysMLQuantityKind("angularVelocity", "XX", "AngularVelocity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for anguar acceleration */
	@QuantityKind
	public static final SysMLQuantityKind AngularAcceleration = new SysMLQuantityKind("angularAcceleration", "XX", "AngularAcceleration", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for radiant intensity */
	@QuantityKind
	public static final SysMLQuantityKind RadiantIntensity = new SysMLQuantityKind("radiantIntensity", "XX", "RadiantIntensity", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for radiance */
	@QuantityKind
	public static final SysMLQuantityKind Radiance = new SysMLQuantityKind("radiance", "XX", "Radiance", "https://www.nist.gov/pml/special-publication-330");
	/** Instance of quantity for coordinate */
	@QuantityKind
	public static final SysMLQuantityKind Coordinate = new SysMLQuantityKind("coordinate", "coord", "Coordinate", "");
	/** Instance of quantity for current */
	@QuantityKind
	public static final SysMLQuantityKind Current = new SysMLQuantityKind("current", "current", "Current", "");
	/** Instance of quantity for direction */
	@QuantityKind
	public static final SysMLQuantityKind Direction = new SysMLQuantityKind("direction", "dir", "Direction", "");
	/** Instance of quantity for information */
	@QuantityKind
	public static final SysMLQuantityKind Information = new SysMLQuantityKind("information", "info", "Information", "");
	/** Instance of quantity for latent heat */
	@QuantityKind
	public static final SysMLQuantityKind LatentHeat = new SysMLQuantityKind("latent heat", "lh", "Latent Heat", "");
	/** Instance of quantity for distance */
	@QuantityKind
	public static final SysMLQuantityKind Distance = new SysMLQuantityKind("distance", "dist", "Distance", "");
	/** Instance of quantity for money */
	@QuantityKind
	public static final SysMLQuantityKind Money = new SysMLQuantityKind("money", "money", "Money", "");
	/** Instance of quantity for potential */
	@QuantityKind
	public static final SysMLQuantityKind Potential = new SysMLQuantityKind("potential", "potential", "Potential", "");
	/** Instance of quantity for position */
	@QuantityKind
	public static final SysMLQuantityKind Position = new SysMLQuantityKind("position", "pos", "Position", "");
	/** Instance of quantity for quantity */
	@QuantityKind
	public static final SysMLQuantityKind Quantity = new SysMLQuantityKind("quantity", "qty", "Quantity", "");
	/** Instance of quantity for flow */
	@QuantityKind
	public static final SysMLQuantityKind Flow = new SysMLQuantityKind("throughput", "thru", "Throughput", "");
	/** Instance of quantity for torque */
	@QuantityKind
	public static final SysMLQuantityKind Torque = new SysMLQuantityKind("torque", "torque", "Torque", "");
	/** Instance of quantity for velocity */
	@QuantityKind
	public static final SysMLQuantityKind Velocity = new SysMLQuantityKind("velocity", "XX", "Velocity", "");
	/** Instance of quantity for viscosity */
	@QuantityKind
	public static final SysMLQuantityKind Viscosity = new SysMLQuantityKind("viscosity", "visc", "Viscosity", "");
	/** Instance of quantity for weight */
	@QuantityKind
	public static final SysMLQuantityKind Weight = new SysMLQuantityKind("weight", "wt", "Weight", "");
	/** Instance of quantity for work */
	@QuantityKind
	public static final SysMLQuantityKind Work = new SysMLQuantityKind("work", "work", "Work", "");
}
