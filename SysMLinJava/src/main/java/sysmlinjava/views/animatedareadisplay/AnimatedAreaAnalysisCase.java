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

import java.util.Map;
import java.util.Optional;

import sysmlinjava.analysis.ParametricAnalysisCase;
import sysmlinjava.javaannotations.actions.AnalysisCaseAction;
import sysmlinjava.javaannotations.analysis.AnalysisResult;

/**
 * {@code AnimatedAreaAnalysisCase} is an executable model of a SysML analysis
 * case that produces an animated display of objects whose states and positions
 * are represented by the current values of the analysis parameters.
 * <p>
 * {@code AnimatedAreaAnalysisCase} extends the basic
 * {@code ParametericAnalysisCase}. As such, the analysis case defines a set of
 * parameters which are "bound" to the part attributes that correspond to the
 * parameters. The analysis calculation calculates the area display from these
 * analysis parameters.
 * <p>
 * As an abstract class, the {@code AnimatedAreaAnalysisCase} must be
 * extended/specialized for the particular area display that is to be generated.
 * Specializations may use the parameters defined in this base class, i.e. the
 * Point2D-type and Points2D-type parameters for the most commonly used
 * parameters for an area display, i.e. object positions, but may also define
 * other anslysis parameters in the specialized class as needed to further
 * specialize the area display. In any case, the specalized
 * {@code AnimatedAreaAnalysisCase} must override and invoke the
 * {@code AnalysisCaseAction} operation to calculate the {@code displayData} for
 * the animated display.
 * <p>
 * As described in the {@code ParametricAnalysisCase} comments, the analysis
 * parameters must be connected (bound) to the part attributes that correspond
 * to the analysis parameters via the {@code SysMLBindingConnector}. These
 * binding connectors must be created in the {@code createBindingConnectors()}
 * method in a part that contains the {@code AnimatedAreaAnalysisCase}
 * specialization as well as the specialized {@code SysMLPart} that contains the
 * bound attriutes.
 * 
 * @author ModelerOne
 */
public abstract class AnimatedAreaAnalysisCase extends ParametricAnalysisCase
{
	/**
	 * Definition of the area display
	 */
	protected AnimatedAreaDisplayDefinition displayDefinition;

	/**
	 * Data for the area display, i.e. the result of the analysis case
	 */
	@AnalysisResult
	protected AnimatedAreaDisplayData displayData;

	/**
	 * Transmitter of the area display's definition and data to the area display
	 */
	private AnimatedAreaDisplayTransmitter displayTransmitter;

	/**
	 * Map of the parameter IDs to animation objects on the area display
	 */
	protected Map<String, String> paramIDAObjectIDMap;

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
	public AnimatedAreaAnalysisCase(int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "AnimatedAreaDisplay", 0L);
		displayDefinition = new AnimatedAreaDisplayDefinition();
		createDisplayDefinition();
		displayTransmitter = new AnimatedAreaDisplayTransmitter(udpPort, logToConsole);
		displayTransmitter.transmitDefinition(displayDefinition);
	}

	/**
	 * Overridable method to perform the analysis on the current values of the set
	 * of analysis parameters. The method should be overidden to perform the actual
	 * analysis and/or invoke this {@code perform()} method to perform the action
	 * function declared to perform the analysis. In any case, the override method
	 * should lastly invoke the {@code transmitChartData()} operation to calculate
	 * and transmit the animated area display data for display.
	 */
	@Override
	@AnalysisCaseAction
	public void perform()
	{
		super.perform();
		transmitDisplayData();
	}

	/**
	 * Overridable operation that creates the result of the analysis case, i.e. the
	 * data to be displayed by the animated area display. Specializations should
	 * override this method to initialize the values of the display data for
	 * tranmission to the area display.
	 */
	@Override
	protected void createResult()
	{
		displayData = new AnimatedAreaDisplayData();
	}

	@Override
	protected void createResultEvaluation()
	{
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

	@Override
	public void stop()
	{
		displayTransmitter.stop();
		super.stop();
	}
}
