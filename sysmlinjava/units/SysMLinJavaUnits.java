package sysmlinjava.units;

import java.util.List;
import java.util.Optional;
import sysmlinjava.annotations.Unit;
import sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds;

/**
 * SysMLinJava represention of SysML's prescribed units and supplementary units.
 * Included are the SI standard units prescribed by the SysML standard, as well
 * as a number of additional units useful for SysMLinJava modeling. Individual
 * instances of {@code SysMLUnit} are self described by their individual field
 * values, i.e. {@code name}, {@code symbol}, {@code description},
 * {@code definitionURI}, and {@code quantityKind}.
 * <p>
 * Each {@code SysMLUnit} instance is a static final instance, i.e. the units
 * declared here must be used as is. If different or additional units are
 * needed, they should be instantiated in a new class that extends the
 * {@code SysMLUnits} class similar to this class. For example, to create a new
 * unit outside of the unit collection in this {@code SysMLinJavaUnits} class,
 * simply instantiate the {@code SysMLUnit} for the new unit in an extending
 * class as follows:
 * 
 * <pre>
 * public class MySysMLUnits extends SysMLUnits
 * {
 * 	&#64;Unit
 * 	public static final SysMLUnit MyNewUnit = new SysMLUnit("newUnit", "nu", "My new unit description", "http://adomain.com/units", Optional.of(SysMLQuantityKinds.Each));
 * 
 * 	static
 * 	{
 * 		instances.addAll(List.of(MyNewUnit));
 * 	}
 * }
 * </pre>
 * 
 * References to the new unit would then be as follows:
 * 
 * <pre>
    public class MyValueType extends SysMLValueType
    {
            :
        &#64;Override
        protected void createUnits()
        {
            units = MySysMLUnits.MyNewUnit;
        }
            :
    }
 * </pre>
 * 
 * Note that the SI units can be identified by their {@code definitionURI}
 * argument in their constructors. This argument for SI units contains the
 * "https" link to the "NIST Special Publication 330" for the SI units.
 * 
 * @author ModelerOne
 *
 */
public class SysMLinJavaUnits extends SysMLUnits
{

	/** Instance of {@code SysMLUnit} for meter */
	@Unit
	public static final SysMLUnit Meter = new SysMLUnit("meter", "m", "Meter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for kilogram */
	@Unit
	public static final SysMLUnit Kilogram = new SysMLUnit("kilogram", "kg", "Kilogram", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Mass));
	/** Instance of {@code SysMLUnit} for second */
	@Unit
	public static final SysMLUnit Second = new SysMLUnit("second", "s", "Second", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Time));
	/** Instance of {@code SysMLUnit} for ampere */
	@Unit
	public static final SysMLUnit Ampere = new SysMLUnit("ampere", "A", "Ampere", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricCurrent));
	/** Instance of {@code SysMLUnit} for kelvin */
	@Unit
	public static final SysMLUnit Kelvin = new SysMLUnit("kelvin", "K", "Kelvin", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ThermodynamicTemperature));
	/** Instance of {@code SysMLUnit} for mole */
	@Unit
	public static final SysMLUnit Mole = new SysMLUnit("mole", "mol", "Mole", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.AmountOfSubstance));
	/** Instance of {@code SysMLUnit} for candela */
	@Unit
	public static final SysMLUnit Candela = new SysMLUnit("candela", "cd", "Candela", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.LuminousIntensity));
	/** Instance of {@code SysMLUnit} for radian */
	@Unit
	public static final SysMLUnit Radian = new SysMLUnit("radian", "rad", "Radian", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.PlaneAngle));
	/** Instance of {@code SysMLUnit} for steradian */
	@Unit
	public static final SysMLUnit Steradian = new SysMLUnit("steradian", "sr", "Steradian", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.SolidAngle));
	/** Instance of {@code SysMLUnit} for square meter */
	@Unit
	public static final SysMLUnit SquareMeter = new SysMLUnit("squareMeter", "m2", "SquareMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for cubic meter */
	@Unit
	public static final SysMLUnit CubicMeter = new SysMLUnit("cubicMeter", "m3", "CubicMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for meters per second */
	@Unit
	public static final SysMLUnit MeterPerSecond = new SysMLUnit("meterPerSecond", "m/s", "MeterPerSecond", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for meters per second per second */
	@Unit
	public static final SysMLUnit MeterPerSecondSquared = new SysMLUnit("meterPerSecondSquared", "m/s2", "MeterPerSecondSquared", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Acceleration));
	/** Instance of {@code SysMLUnit} for reciprocal meter */
	@Unit
	public static final SysMLUnit ReciprocalMeter = new SysMLUnit("reciprocalMeter", "m-1", "ReciprocalMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.WaveNumber));
	/** Instance of {@code SysMLUnit} for kilogram per meter cubed */
	@Unit
	public static final SysMLUnit KilogramPerMeterCubed = new SysMLUnit("kilogramPerMetercCubed", "kg/m3", "KilogramPerMetercCubed", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Density));
	/** Instance of {@code SysMLUnit} for cubic meter per kilogram */
	@Unit
	public static final SysMLUnit CubicMeterPerKilogram = new SysMLUnit("cubicMeterPerKilogram", "m3/kg", "CubicMeterPerKilogram", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.SpecificVolume));
	/** Instance of {@code SysMLUnit} for ampere per square meter */
	@Unit
	public static final SysMLUnit AmperePerSquareMeter = new SysMLUnit("amperePerSquareMeter", "A/m2", "AmperePerSquareMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.CurrentDensity));
	/** Instance of {@code SysMLUnit} for ampere per meter */
	@Unit
	public static final SysMLUnit AmperePerMeter = new SysMLUnit("amperePerMeter", "A/m", "AmperePerMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.MagneticFieldStrength));
	/** Instance of {@code SysMLUnit} for mole per cubic meter */
	@Unit
	public static final SysMLUnit MolePerMeterCubed = new SysMLUnit("molePerMeterCubed", "mol/m3", "MolePerMeterCubed", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Concentration));
	/** Instance of {@code SysMLUnit} for candela per square meter */
	@Unit
	public static final SysMLUnit CandelaPerMeterSquared = new SysMLUnit("candelaPerMeterSquared", "cd/m2", "CandelaPerMeterSquared", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Luminance));
	/** Instance of {@code SysMLUnit} for hertz */
	@Unit
	public static final SysMLUnit Hertz = new SysMLUnit("hertz", "Hz", "Hertz", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Frequency));
	/** Instance of {@code SysMLUnit} for kilohertz */
	@Unit
	public static final SysMLUnit KiloHertz = new SysMLUnit("kilohertz", "khz", "", "", Optional.of(SysMLinJavaQuantityKinds.Frequency));
	/** Instance of {@code SysMLUnit} for */
	@Unit
	public static final SysMLUnit MegaHertz = new SysMLUnit("megahertz", "mhz", "", "", Optional.of(SysMLinJavaQuantityKinds.Frequency));
	/** Instance of {@code SysMLUnit} for */
	@Unit
	public static final SysMLUnit GigaHertz = new SysMLUnit("gigahertz", "ghz", "", "", Optional.of(SysMLinJavaQuantityKinds.Frequency));
	/** Instance of {@code SysMLUnit} for newton */
	@Unit
	public static final SysMLUnit Newton = new SysMLUnit("newton", "N", "Newton", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Force));
	/** Instance of {@code SysMLUnit} for pascal */
	@Unit
	public static final SysMLUnit Pascal = new SysMLUnit("pascal", "Pa", "Pascal", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Pressure));
	/** Instance of {@code SysMLUnit} for joule */
	@Unit
	public static final SysMLUnit Joule = new SysMLUnit("joule", "J", "Joule", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Energy));
	/** Instance of {@code SysMLUnit} for watt */
	@Unit
	public static final SysMLUnit Watt = new SysMLUnit("watt", "W", "Watt", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Power));
	/** Instance of {@code SysMLUnit} for coulomb */
	@Unit
	public static final SysMLUnit Coulomb = new SysMLUnit("coulomb", "C", "Coulomb", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricCharge));
	/** Instance of {@code SysMLUnit} for volt */
	@Unit
	public static final SysMLUnit Volt = new SysMLUnit("volt", "V", "Volt", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricPotential));
	/** Instance of {@code SysMLUnit} for farad */
	@Unit
	public static final SysMLUnit Farad = new SysMLUnit("farad", "F", "Farad", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Capacitance));
	/** Instance of {@code SysMLUnit} for ohm */
	@Unit
	public static final SysMLUnit Ohm = new SysMLUnit("ohm", "Ohm", "Ohm", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricResistance));
	/** Instance of {@code SysMLUnit} for siemens */
	@Unit
	public static final SysMLUnit Siemens = new SysMLUnit("siemens", "S", "Siemens", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricConductance));
	/** Instance of {@code SysMLUnit} for weber */
	@Unit
	public static final SysMLUnit Weber = new SysMLUnit("weber", "Wb", "Weber", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.MagneticFlux));
	/** Instance of {@code SysMLUnit} for tesla */
	@Unit
	public static final SysMLUnit Tesla = new SysMLUnit("tesla", "T", "Tesla", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.MagenticFluxDensity));
	/** Instance of {@code SysMLUnit} for henry */
	@Unit
	public static final SysMLUnit Henry = new SysMLUnit("henry", "H", "Henry", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Inductance));
	/** Instance of {@code SysMLUnit} for degree C */
	@Unit
	public static final SysMLUnit DegreeCelsius = new SysMLUnit("degreeCelsius", "C", "DegreeCelsius", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Temperature));
	/** Instance of {@code SysMLUnit} for lumen */
	@Unit
	public static final SysMLUnit Lumen = new SysMLUnit("lumen", "lm", "Lumen", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.LuminousFlux));
	/** Instance of {@code SysMLUnit} for lux */
	@Unit
	public static final SysMLUnit Lux = new SysMLUnit("lux", "lx", "Lux", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Illuminance));
	/** Instance of {@code SysMLUnit} for becquerel */
	@Unit
	public static final SysMLUnit Becquerel = new SysMLUnit("becquerel", "Bq", "Becquerel", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ActivityRadioNuclide));
	/** Instance of {@code SysMLUnit} for gray */
	@Unit
	public static final SysMLUnit Gray = new SysMLUnit("gray", "Gy", "Gray", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.AbsorbedDose));
	/** Instance of {@code SysMLUnit} for sievert */
	@Unit
	public static final SysMLUnit Sievert = new SysMLUnit("sievert", "Sv", "Sievert", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.DoseEquivalent));
	/** Instance of {@code SysMLUnit} for pascal-second */
	@Unit
	public static final SysMLUnit PascalSecond = new SysMLUnit("pascalSecond", "Pa*s", "PascalSecond", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Viscosity));
	/** Instance of {@code SysMLUnit} for newton-meter */
	@Unit
	public static final SysMLUnit NewtonMeter = new SysMLUnit("newtonmeter", "N*m", "Newtonmeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.MomentOfForce));
	/** Instance of {@code SysMLUnit} for newton per meter */
	@Unit
	public static final SysMLUnit NewtonPerMeter = new SysMLUnit("newtonPerMeter", "N/m", "NewtonPerMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.SurfaceTension));
	/** Instance of {@code SysMLUnit} for watts per square meter */
	@Unit
	public static final SysMLUnit WattsPerMeterSquared = new SysMLUnit("wattsPerMeterSquared", "W/m2", "WattsPerMeterSquared", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.HeatFluxDensity));
	/** Instance of {@code SysMLUnit} for joule per kelvin */
	@Unit
	public static final SysMLUnit JoulePerKelvin = new SysMLUnit("joulePerKelvin", "J/K", "JoulePerKelvin", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.HeatCapacity));
	/** Instance of {@code SysMLUnit} for joule per kilogram-kelvin */
	@Unit
	public static final SysMLUnit JoulePerKilogramKelvin = new SysMLUnit("joulePerKilogramKelvin", "J(kg*K)", "JoulePerKilogramKelvin", "https://www.nist.gov/pml/special-publication-330",
		Optional.of(SysMLinJavaQuantityKinds.SpecificHeatCapacity));
	/** Instance of {@code SysMLUnit} for joule per kilogram */
	@Unit
	public static final SysMLUnit JoulePerKilogram = new SysMLUnit("joulePerKilogram", "J/kg", "JoulePerKilogram", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.SpecificEnergy));
	/** Instance of {@code SysMLUnit} for watts per meter kelvin */
	@Unit
	public static final SysMLUnit WattsPerMeterKelvin = new SysMLUnit("wattsPerMeterKelvin", "W/(m*K)", "WattsPerMeterKelvin", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ThermalConductivity));
	/** Instance of {@code SysMLUnit} for joule per meter cubed */
	@Unit
	public static final SysMLUnit JoulePerMeterCubed = new SysMLUnit("joulePerMeterCubed", "J/M3", "JoulePerMeterCubed", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.EnergydDnsity));
	/** Instance of {@code SysMLUnit} for volt per meter */
	@Unit
	public static final SysMLUnit VoltPerMeter = new SysMLUnit("voltPerMeter", "V/m", "VoltPerMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricFieldStrength));
	/** Instance of {@code SysMLUnit} for coulomb per cubic meter */
	@Unit
	public static final SysMLUnit CoulombPerMeterCubed = new SysMLUnit("coulombPerMeterCubed", "C/m3", "CoulombPerMeterCubed", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.ElectricChargeDensity));
	/** Instance of {@code SysMLUnit} for coulomb per square meter */
	@Unit
	public static final SysMLUnit CoulombPerMeterSquared = new SysMLUnit("coulombPerMeterSquared", "C/m2", "CoulombPerMeterSquared", "https://www.nist.gov/pml/special-publication-330",
		Optional.of(SysMLinJavaQuantityKinds.ElectricFluxDensity));
	/** Instance of {@code SysMLUnit} for farad per meter */
	@Unit
	public static final SysMLUnit FaradPerMeter = new SysMLUnit("faradPerMeter", "F/m", "FaradPerMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Permittivity));
	/** Instance of {@code SysMLUnit} for hentry per meter */
	@Unit
	public static final SysMLUnit HenryPerMeter = new SysMLUnit("henryPerMeter", "H/m", "HenryPerMeter", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Permeability));
	/** Instance of {@code SysMLUnit} for joule per mole */
	@Unit
	public static final SysMLUnit JoulePerMole = new SysMLUnit("joulePerMole", "J/mol", "JoulePerMole", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.MolarEnergy));
	/** Instance of {@code SysMLUnit} for joule per mole kelvin */
	@Unit
	public static final SysMLUnit JoulePerMoleKelvin = new SysMLUnit("joulePerMoleKelvin", "J/(mol*K)", "JoulePerMoleKelvin", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.MolarHeatCapacity));
	/** Instance of {@code SysMLUnit} for coulomb per kilogram */
	@Unit
	public static final SysMLUnit CoulombPerKilogram = new SysMLUnit("coulombPerKilogram", "C/kg", "CoulombPerKilogram", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.Exposure));
	/** Instance of {@code SysMLUnit} for gray per second */
	@Unit
	public static final SysMLUnit GrayPerSecond = new SysMLUnit("grayPerSecond", "Gy/s", "GrayPerSecond", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.AbsorbedDoseRate));
	/** Instance of {@code SysMLUnit} for radian per second */
	@Unit
	public static final SysMLUnit RadianPerSecond = new SysMLUnit("radianPerSecond", "rad/s", "RadianPerSecond", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.AngularVelocity));
	/** Instance of {@code SysMLUnit} for radian per second per second */
	@Unit
	public static final SysMLUnit RadianPerSecondSquared = new SysMLUnit("radianPerSecondSquared", "rad/s2", "RadianPerSecondSquared", "https://www.nist.gov/pml/special-publication-330",
		Optional.of(SysMLinJavaQuantityKinds.AngularAcceleration));
	/** Instance of {@code SysMLUnit} for watts per steradian */
	@Unit
	public static final SysMLUnit WattsPerSteradian = new SysMLUnit("wattsPerSteradian", "W/sr", "WattsPerSteradian", "https://www.nist.gov/pml/special-publication-330", Optional.of(SysMLinJavaQuantityKinds.RadiantIntensity));
	/** Instance of {@code SysMLUnit} for watts per square meter steradian */
	@Unit
	public static final SysMLUnit WattsPerSquareMeterSteradian = new SysMLUnit("wattsPerSquareMeterSteradian", "W/(m2*sr)", "WattsPerSquareMeterSteradian", "https://www.nist.gov/pml/special-publication-330",
		Optional.of(SysMLinJavaQuantityKinds.Radiance));
	/** Instance of {@code SysMLUnit} for numeric */
	@Unit
	public static final SysMLUnit Numeric = new SysMLUnit("numeric", "none", "", "", Optional.of(SysMLinJavaQuantityKinds.Quantity));
	/** Instance of {@code SysMLUnit} for each */
	@Unit
	public static final SysMLUnit Each = new SysMLUnit("each", "ea", "", "", Optional.of(SysMLinJavaQuantityKinds.Quantity));
	/** Instance of {@code SysMLUnit} for percent */
	@Unit
	public static final SysMLUnit Percent = new SysMLUnit("percent", "%", "", "", Optional.of(SysMLinJavaQuantityKinds.Quantity));
	/** Instance of {@code SysMLUnit} for meters */
	@Unit
	public static final SysMLUnit Meters = new SysMLUnit("meters", "m", "", "", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for millimeters */
	@Unit
	public static final SysMLUnit Millimeters = new SysMLUnit("millimeters", "mm", "", "", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for centimeters */
	@Unit
	public static final SysMLUnit Centimeters = new SysMLUnit("centimeters", "cm", "", "", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for kilometers */
	@Unit
	public static final SysMLUnit Kilometers = new SysMLUnit("kilometers", "km", "", "", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for miles */
	@Unit
	public static final SysMLUnit Miles = new SysMLUnit("miles", "mi", "", "", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for feet */
	@Unit
	public static final SysMLUnit Feet = new SysMLUnit("feet", "ft", "", "", Optional.of(SysMLinJavaQuantityKinds.Length));
	/** Instance of {@code SysMLUnit} for square centimeters */
	@Unit
	public static final SysMLUnit CentimetersSquare = new SysMLUnit("centimeters-square", "cm2", "", "", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for square meters */
	@Unit
	public static final SysMLUnit MetersSquare = new SysMLUnit("meters-square", "m2", "", "", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for square kilometers */
	@Unit
	public static final SysMLUnit KilometersSquare = new SysMLUnit("kilometers-square", "km2", "", "", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for square inches */
	@Unit
	public static final SysMLUnit InchesSquare = new SysMLUnit("inches-square", "in2", "", "", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for square feet */
	@Unit
	public static final SysMLUnit FeetSquare = new SysMLUnit("feet-square", "ft2", "", "", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for square miles */
	@Unit
	public static final SysMLUnit MilesSquare = new SysMLUnit("miles-square", "mi2", "", "", Optional.of(SysMLinJavaQuantityKinds.Area));
	/** Instance of {@code SysMLUnit} for cubic centimeters */
	@Unit
	public static final SysMLUnit CentimetersCubic = new SysMLUnit("centimeters-cubic", "cm3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for cubic meters */
	@Unit
	public static final SysMLUnit MetersCubic = new SysMLUnit("meters-cubic", "m3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for cubic kilometers */
	@Unit
	public static final SysMLUnit KilometersCubic = new SysMLUnit("kilometers-cubic", "km3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for cubic inches */
	@Unit
	public static final SysMLUnit InchesCubic = new SysMLUnit("inches-cubic", "in3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for cubic feet */
	@Unit
	public static final SysMLUnit FeetCubic = new SysMLUnit("feet-cubic", "ft3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for cubic miles */
	@Unit
	public static final SysMLUnit MilesCubic = new SysMLUnit("miles-cubic", "mi3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for liters */
	@Unit
	public static final SysMLUnit Liters = new SysMLUnit("liters", "ltr", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for gallons */
	@Unit
	public static final SysMLUnit Gallons = new SysMLUnit("gallons", "gal", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
	/** Instance of {@code SysMLUnit} for nanoseconds */
	@Unit
	public static final SysMLUnit Nanoseconds = new SysMLUnit("nanoseconds", "ns", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
	/** Instance of {@code SysMLUnit} for milliseconds */
	@Unit
	public static final SysMLUnit Milliseconds = new SysMLUnit("milliseconds", "ms", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
	/** Instance of {@code SysMLUnit} for seconds */
	@Unit
	public static final SysMLUnit Seconds = new SysMLUnit("seconds", "sec", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
	/** Instance of {@code SysMLUnit} for minutes */
	@Unit
	public static final SysMLUnit Minutes = new SysMLUnit("minutes", "min", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
	/** Instance of {@code SysMLUnit} for hours */
	@Unit
	public static final SysMLUnit Hours = new SysMLUnit("hours", "hr", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
	/** Instance of {@code SysMLUnit} for feet per second */
	@Unit
	public static final SysMLUnit FeetPerSecond = new SysMLUnit("feet/second", "fps", "", "", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for revolutions per minute */
	@Unit
	public static final SysMLUnit RevolutionsPerMinute = new SysMLUnit("revolutions/second", "rpm", "", "", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for kilometers per hour */
	@Unit
	public static final SysMLUnit KilometersPerHour = new SysMLUnit("kilometers/hour", "km/hr", "", "", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for miles per hour */
	@Unit
	public static final SysMLUnit MilesPerHour = new SysMLUnit("miles/hour", "mph", "", "", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for miles per hour degrees */
	@Unit
	public static final SysMLUnit MilesPerHourDegrees = new SysMLUnit("miles/hour-degrees", "mph-deg", "", "", Optional.of(SysMLinJavaQuantityKinds.Velocity));
	/** Instance of {@code SysMLUnit} for knots */
	@Unit
	public static final SysMLUnit Knots = new SysMLUnit("knots", "kn", "", "", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for kilometers per hour per second */
	@Unit
	public static final SysMLUnit KilometersPerHourPerSecond = new SysMLUnit("kilometers/hour/second", "km/hr/sec", "", "", Optional.of(SysMLinJavaQuantityKinds.Acceleration));
	/** Instance of {@code SysMLUnit} for miles per hour per second */
	@Unit
	public static final SysMLUnit MilesPerHourPerSecond = new SysMLUnit("miles/hour/second", "mi/hr/sec", "", "", Optional.of(SysMLinJavaQuantityKinds.Acceleration));
	/** Instance of {@code SysMLUnit} for meters per second per second per second */
	@Unit
	public static final SysMLUnit MetersPerSecondCubed = new SysMLUnit("meters/second/second/second", "m/sec3", "", "", Optional.of(SysMLinJavaQuantityKinds.Jerk));
	/** Instance of {@code SysMLUnit} for megabytes per seconds */
	@Unit
	public static final SysMLUnit MegabytesPerSecond = new SysMLUnit("megabytes/second", "mBps", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for radians per second */
	@Unit
	public static final SysMLUnit RadiansPerSecond = new SysMLUnit("radians/second", "rd/sec", "", "", Optional.of(SysMLinJavaQuantityKinds.Speed));
	/** Instance of {@code SysMLUnit} for watts */
	@Unit
	public static final SysMLUnit Watts = new SysMLUnit("watts", "W", "", "", Optional.of(SysMLinJavaQuantityKinds.Power));
	/** Instance of {@code SysMLUnit} for killowatts */
	@Unit
	public static final SysMLUnit KiloWatts = new SysMLUnit("kilowatts", "kW", "", "", Optional.of(SysMLinJavaQuantityKinds.Power));
	/** Instance of {@code SysMLUnit} for megawatts */
	@Unit
	public static final SysMLUnit MegaWatts = new SysMLUnit("megawatts", "mW", "", "", Optional.of(SysMLinJavaQuantityKinds.Power));
	/** Instance of {@code SysMLUnit} for revolutions */
	@Unit
	public static final SysMLUnit Revolutions = new SysMLUnit("revolutions", "rev", "", "", Optional.of(SysMLinJavaQuantityKinds.Distance));
	/** Instance of {@code SysMLUnit} for volts */
	@Unit
	public static final SysMLUnit Volts = new SysMLUnit("volts", "v", "", "", Optional.of(SysMLinJavaQuantityKinds.Potential));
	/** Instance of {@code SysMLUnit} for kilovolts */
	@Unit
	public static final SysMLUnit Kilovolts = new SysMLUnit("kilovolts", "kv", "", "", Optional.of(SysMLinJavaQuantityKinds.Potential));
	/** Instance of {@code SysMLUnit} for megavolts */
	@Unit
	public static final SysMLUnit Megavolts = new SysMLUnit("megavolts", "mgv", "", "", Optional.of(SysMLinJavaQuantityKinds.Potential));
	/** Instance of {@code SysMLUnit} for amperes */
	@Unit
	public static final SysMLUnit Amps = new SysMLUnit("amps", "amps", "", "", Optional.of(SysMLinJavaQuantityKinds.Current));
	/** Instance of {@code SysMLUnit} for milliamperes */
	@Unit
	public static final SysMLUnit Milliamps = new SysMLUnit("milliamps", "mamps", "", "", Optional.of(SysMLinJavaQuantityKinds.Current));
	/** Instance of {@code SysMLUnit} for grams */
	@Unit
	public static final SysMLUnit Grams = new SysMLUnit("grams", "gm", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for milligrams */
	@Unit
	public static final SysMLUnit MilliGrams = new SysMLUnit("milligrams", "mgm", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for kilograms */
	@Unit
	public static final SysMLUnit Kilograms = new SysMLUnit("kilograms", "kgm", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for ounces */
	@Unit
	public static final SysMLUnit Ounces = new SysMLUnit("punces", "oz", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for pounds */
	@Unit
	public static final SysMLUnit Pounds = new SysMLUnit("pounds", "lb", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for tons */
	@Unit
	public static final SysMLUnit Tons = new SysMLUnit("tons", "tons", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for kilotons */
	@Unit
	public static final SysMLUnit Kilotons = new SysMLUnit("kilotons", "KTons", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for tons */
	@Unit
	public static final SysMLUnit Megatons = new SysMLUnit("megatons", "MTons", "", "", Optional.of(SysMLinJavaQuantityKinds.Weight));
	/** Instance of {@code SysMLUnit} for newtons */
	@Unit
	public static final SysMLUnit Newtons = new SysMLUnit("newtons", "N", "", "", Optional.of(SysMLinJavaQuantityKinds.Force));
	/** Instance of {@code SysMLUnit} for joules */
	@Unit
	public static final SysMLUnit Joules = new SysMLUnit("joules", "J", "", "", Optional.of(SysMLinJavaQuantityKinds.Energy));
	/** Instance of {@code SysMLUnit} for degrees */
	@Unit
	public static final SysMLUnit Degrees = new SysMLUnit("degrees", "deg", "", "", Optional.of(SysMLinJavaQuantityKinds.Direction));
	/** Instance of {@code SysMLUnit} for radians */
	@Unit
	public static final SysMLUnit Radians = new SysMLUnit("radians", "rad", "", "", Optional.of(SysMLinJavaQuantityKinds.Direction));
	/** Instance of {@code SysMLUnit} for bytes */
	@Unit
	public static final SysMLUnit Bytes = new SysMLUnit("bytes", "B", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for kilobytes */
	@Unit
	public static final SysMLUnit KiloBytes = new SysMLUnit("kilobytes", "kB", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for megabytes */
	@Unit
	public static final SysMLUnit MegaBytes = new SysMLUnit("megabytes", "mB", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for gigabytes */
	@Unit
	public static final SysMLUnit GigaBytes = new SysMLUnit("gigabytes", "gB", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for terabytes */
	@Unit
	public static final SysMLUnit TeraBytes = new SysMLUnit("terabytes", "tB", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for petabytes */
	@Unit
	public static final SysMLUnit PetaBytes = new SysMLUnit("petabytes", "pB", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for logical */
	@Unit
	public static final SysMLUnit Logical = new SysMLUnit("logical", "Logical", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for object */
	@Unit
	public static final SysMLUnit Object = new SysMLUnit("object", "obj", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for characters */
	@Unit
	public static final SysMLUnit Characters = new SysMLUnit("characters", "char", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for bits per second */
	@Unit
	public static final SysMLUnit BitsPerSecond = new SysMLUnit("bits/second", "bps", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for enumerated */
	@Unit
	public static final SysMLUnit Enumerated = new SysMLUnit("enum", "enum", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for dollars (US) */
	@Unit
	public static final SysMLUnit DollarsUS = new SysMLUnit("dollars", "$US", "", "", Optional.of(SysMLinJavaQuantityKinds.Money));
	/** Instance of {@code SysMLUnit} for euros */
	@Unit
	public static final SysMLUnit Euros = new SysMLUnit("euros", "EUR", "", "", Optional.of(SysMLinJavaQuantityKinds.Money));
	/** Instance of {@code SysMLUnit} for quantity per second */
	@Unit
	public static final SysMLUnit QuantityPerSecond = new SysMLUnit("quantity/second", "qty/sec", "", "", Optional.of(SysMLinJavaQuantityKinds.Flow));
	/** Instance of {@code SysMLUnit} for newton-meters */
	@Unit
	public static final SysMLUnit NewtonMeters = new SysMLUnit("newton-meters", "Nm", "", "", Optional.of(SysMLinJavaQuantityKinds.Torque));
	/** Instance of {@code SysMLUnit} for pound-feet */
	@Unit
	public static final SysMLUnit PoundFeet = new SysMLUnit("pound-feet", "lb-ft", "", "", Optional.of(SysMLinJavaQuantityKinds.Torque));
	/** Instance of {@code SysMLUnit} for newtons per square meter */
	@Unit
	public static final SysMLUnit NewtonsPerMeterSquare = new SysMLUnit("newtons/m2", "N/m2", "", "", Optional.of(SysMLinJavaQuantityKinds.Pressure));
	/** Instance of {@code SysMLUnit} for newtons per square meter seconds */
	@Unit
	public static final SysMLUnit NewtonsPerMeterSquareSeconds = new SysMLUnit("newtons/m2-second", "N/m2-second", "", "", Optional.of(SysMLinJavaQuantityKinds.Viscosity));
	/** Instance of {@code SysMLUnit} for pounds per square inch */
	@Unit
	public static final SysMLUnit PoundsPerInchSquare = new SysMLUnit("pounds/in2", "psi", "", "", Optional.of(SysMLinJavaQuantityKinds.Pressure));
	/** Instance of {@code SysMLUnit} for killograms per cubic meter */
	@Unit
	public static final SysMLUnit KilogramsPerMeterCubic = new SysMLUnit("kilograms/meter-cubic", "kgm/m3", "", "", Optional.of(SysMLinJavaQuantityKinds.Density));
	/** Instance of {@code SysMLUnit} for pounds per cubic foot */
	@Unit
	public static final SysMLUnit PoundsPerFootCubic = new SysMLUnit("pounds/foot-cubic", "lb/ft3", "", "", Optional.of(SysMLinJavaQuantityKinds.Density));
	/** Instance of {@code SysMLUnit} for kilowatt-hours */
	@Unit
	public static final SysMLUnit KiloWattHours = new SysMLUnit("kilowatt-hours", "kW-hr", "", "", Optional.of(SysMLinJavaQuantityKinds.Energy));
	/** Instance of {@code SysMLUnit} for kilowatt-hours per kilometer */
	@Unit
	public static final SysMLUnit KiloWattHoursPerKilometer = new SysMLUnit("kilowatt-hours/kilometer", "kW-hr/km", "", "", Optional.of(SysMLinJavaQuantityKinds.Energy));
	/** Instance of {@code SysMLUnit} for volt-amps */
	@Unit
	public static final SysMLUnit VoltAmperes = new SysMLUnit("volt-amps", "VA", "", "", Optional.of(SysMLinJavaQuantityKinds.Power));
	/** Instance of {@code SysMLUnit} for degrees C */
	@Unit
	public static final SysMLUnit DegreesC = new SysMLUnit("degrees-centigrade", "degC", "", "", Optional.of(SysMLinJavaQuantityKinds.Temperature));
	/** Instance of {@code SysMLUnit} for degrees F */
	@Unit
	public static final SysMLUnit DegreesF = new SysMLUnit("degrees-fahrenheit", "degF", "", "", Optional.of(SysMLinJavaQuantityKinds.Temperature));
	/** Instance of {@code SysMLUnit} for kilojoules per kilogram */
	@Unit
	public static final SysMLUnit KilojoulesPerKilogram = new SysMLUnit("kilojoules/kilogram", "Kj/Kg", "", "", Optional.of(SysMLinJavaQuantityKinds.LatentHeat));
	/** Instance of {@code SysMLUnit} for point */
	@Unit
	public static final SysMLUnit Point = new SysMLUnit("point", "pt", "", "", Optional.of(SysMLinJavaQuantityKinds.Position));
	/** Instance of {@code SysMLUnit} for key-value pair */
	@Unit
	public static final SysMLUnit KeyValuePair = new SysMLUnit("key-value pair", "key-value", "", "", Optional.of(SysMLinJavaQuantityKinds.Information));
	/** Instance of {@code SysMLUnit} for cubic meters per hour */
	@Unit
	public static final SysMLUnit MetersCubicPerHour = new SysMLUnit("meters-cubic/hour", "m3/hr", "", "", Optional.of(SysMLinJavaQuantityKinds.Flow));
	/** Instance of {@code SysMLUnit} for cubic meters per seconds */
	@Unit
	public static final SysMLUnit MetersCubicPerSecond = new SysMLUnit("meters-cubic/second", "m3/sec", "", "", Optional.of(SysMLinJavaQuantityKinds.Flow));

	static
	{
		instances.addAll(List.of(Meter, Kilogram, Second, Ampere, Kelvin, Mole, Candela, Radian, Steradian, SquareMeter, CubicMeter, MeterPerSecond, MeterPerSecondSquared, ReciprocalMeter, KilogramPerMeterCubed, CubicMeterPerKilogram,
			AmperePerSquareMeter, AmperePerMeter, MolePerMeterCubed, CandelaPerMeterSquared, Hertz, KiloHertz, MegaHertz, GigaHertz, Newton, Pascal, Joule, Watt, Coulomb, Volt, Farad, Ohm, Siemens, Weber, Tesla, Henry, DegreeCelsius, Lumen,
			Lux, Becquerel, Gray, Sievert, PascalSecond, NewtonMeter, NewtonPerMeter, WattsPerMeterSquared, JoulePerKelvin, JoulePerKilogramKelvin, JoulePerKilogram, WattsPerMeterKelvin, JoulePerMeterCubed, VoltPerMeter,
			CoulombPerMeterCubed, CoulombPerMeterSquared, FaradPerMeter, HenryPerMeter, JoulePerMole, JoulePerMoleKelvin, CoulombPerKilogram, GrayPerSecond, RadianPerSecond, RadianPerSecondSquared, WattsPerSteradian,
			WattsPerSquareMeterSteradian, Numeric, Each, Percent, Meters, Millimeters, Centimeters, Kilometers, Miles, Feet, CentimetersSquare, MetersSquare, KilometersSquare, InchesSquare, FeetSquare, MilesSquare, CentimetersCubic,
			MetersCubic, KilometersCubic, InchesCubic, FeetCubic, MilesCubic, Liters, Gallons, Nanoseconds, Milliseconds, Seconds, Minutes, Hours, FeetPerSecond, RevolutionsPerMinute, KilometersPerHour, MilesPerHour, Knots,
			KilometersPerHourPerSecond, MilesPerHourPerSecond, MetersPerSecondCubed, MegabytesPerSecond, RadiansPerSecond, Watts, KiloWatts, MegaWatts, Revolutions, Volts, Kilovolts, Megavolts, Milliamps, Amps, MilliGrams, Grams, Kilograms,
			Ounces, Pounds, Tons, Kilotons, Megatons, Newtons, Joules, Degrees, Radians, Bytes, KiloBytes, MegaBytes, GigaBytes, TeraBytes, PetaBytes, Logical, Object, Characters, BitsPerSecond, Enumerated, DollarsUS, Euros,
			QuantityPerSecond, NewtonMeters, PoundFeet, NewtonsPerMeterSquare, PoundsPerInchSquare, KilogramsPerMeterCubic, PoundsPerFootCubic, KiloWattHours, KiloWattHoursPerKilometer, VoltAmperes, DegreesC, DegreesF,
			KilojoulesPerKilogram, Point, KeyValuePair));
	}
}