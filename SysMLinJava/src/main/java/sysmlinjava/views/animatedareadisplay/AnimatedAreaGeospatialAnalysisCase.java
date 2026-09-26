/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.animatedareadisplay;

import java.util.Optional;

import sysmlinjava.analysis.ParametricAnalysisCase;
import sysmlinjava.javaannotations.actions.AnalysisCaseAction;
import sysmlinjava.javaannotations.analysis.AnalysisResult;
import sysmlinjava.views.common.GeospatialCoordinateTransform;

/**
 * {@code AnimatedAreaGeospatialAnalysisCase} is an executable model of a SysML
 * analysis case that produces an animated display of objects whose states and
 * geo-spatial positions are represented by the current values of the analysis
 * parameters.
 * <p>
 * {@code AnimatedAreaGeospatialAnalysisCase} extends the basic
 * {@code ParametericAnalysisCase}. As such, the analysis case defines a set of
 * parameters which are "bound" to the part attributes that correspond to the
 * parameters. The analysis calculation calculates the area display from these
 * analysis parameters.
 * <p>
 * As an abstract class, the {@code AnimatedAreaAnalysisCase} must be
 * extended/specialized for the particular area display that is to be generated.
 * Specializations may use the parameters defined in this base class, i.e. the
 * PointGeospatial-type and PolylineGeospatial-type parameters for the most
 * commonly used parameters for an area display, i.e. object positions and
 * paths/waypoints, but may also define other anslysis parameters in the
 * specialized class as needed to further specialize the area display. In any
 * case, the specalized {@code AnimatedAreaAnalysisCase} must override and
 * invoke the {@code AnalysisCaseAction} operation to calculate the
 * {@code displayData} for the animated display.
 * <p>
 * As described in the {@code ParametricAnalysisCase} comments, the analysis
 * parameters must be connected (bound) to the part attributes that correspond
 * to the analysis parameters via the {@code SysMLBindingConnector}. These
 * binding connectors must be created in the {@code createBindingConnectors()}
 * method in a part that contains the {@code AnimatedAreaGeospatialAnalysisCase}
 * specialization as well as the specialized {@code SysMLPart} that contains the
 * bound attriutes.
 * 
 * @author ModelerOne
 */
public abstract class AnimatedAreaGeospatialAnalysisCase extends ParametricAnalysisCase
{
	/**
	 * Definition of the area display to be calculated/constrained and displayed
	 */
	public AnimatedAreaDisplayDefinition displayDefinition;

	/**
	 * Result of the analysis, i.e. the animation data that is to be displayed by
	 * the animation display
	 */
	@AnalysisResult
	public AnimatedAreaDisplayData displayData;

	/**
	 * Transmitter of the area display's definition and data to the area display
	 */
	private AnimatedAreaDisplayTransmitter displayTransmitter;

	/**
	 * Geospatial coordinate transform utility for transforming geospatial
	 * coordinates to 2D cartesian and/or pixel space coordinates.
	 */
	protected GeospatialCoordinateTransform transform;

	/**
	 * Constructor
	 * 
	 * @param udpPort      number of UDP port on which to receive the display
	 *                     definition and data
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public AnimatedAreaGeospatialAnalysisCase(int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "AnimatedAreaGeospatialDisplay", 0L);
		displayDefinition = new AnimatedAreaDisplayDefinition();

		createDisplayDefinition();

		displayTransmitter = new AnimatedAreaDisplayTransmitter(udpPort, logToConsole);
		displayTransmitter.transmitDefinition(displayDefinition);
	}

	/**
	 * Set the geospatial coordinate transform utility for transforming geospatial
	 * coordinates to cartesian coordinates.
	 * 
	 * @param transform GeospatialCoordinateTransform instance initialized for this
	 *                  specific model parametric analysis case
	 */
	public void setTransform(GeospatialCoordinateTransform transform)
	{
		this.transform = transform;
	}

	@Override
	public void stop()
	{
		displayTransmitter.stop();
		super.stop();
	}

	/**
	 * Overridable method to perform the analysis on the current values of the set
	 * of analysis parameters. The method should be overridden to translate analysis
	 * parameter values into the {@code result} {@code AnimatedAreaDisplayData} and
	 * then either invoke the {@code transmitChartData()} operation to calculate and
	 * transmit the bar chart data for display, or simply invoke this method (via
	 * {@code super.perform()}) to invoke the {@code transmitChartData()} method.
	 */
	@AnalysisCaseAction
	@Override
	public void perform()
	{
		super.perform();
		transmitDisplayData();
	}

	/**
	 * Transmits the display data for the area display
	 */
	protected void transmitDisplayData()
	{
		displayTransmitter.transmitData(displayData);
	}

	/**
	 * Overridable operation that creates/constructs the area display
	 * definition. Specializations must override this method to
	 * initialize the values of the {@code displayDefinition} for tranmission to the
	 * area display.
	 */
	protected abstract void createDisplayDefinition();

	/**
	 * Overridable abstract operation that creates the result of the anaysis, i.e.
	 * the area display data. Specializations should override this method to assign
	 * an instance of the {@code AnimatedAreaDisplayData} or an extension thereof
	 * for transmission to the area display.
	 */
	@Override
	protected abstract void createResult();
}
