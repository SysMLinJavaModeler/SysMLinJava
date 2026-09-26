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
package sysmlinjava.analysis;

import java.util.logging.Logger;

import sysmlinjava.attributetypes.AttributeObserver;
import sysmlinjava.attributetypes.ObservableAttribute;
import sysmlinjava.states.StateBehaviorContext;

/**
 * SysMLinJava representation of the SysML trade-offs analysis case. Extensions
 * of the class should create the subjects of the analysis case as well ws the
 * parametric analysis cases that will perform the trade-offs analyses on the
 * subjects. The {@code ParametricAnalysisCase}'s {@code perform()} action
 * should be overriden to perform each of the parametric analysis cases on the
 * subjects. An example follows:
 * 
 * <pre>{@code
	public class MyTradeOffs extends TradeOffAnalysisCase
	{
		&#64;Subject
		public SystemCheapModel cheapSys;
		&#64;Subject
		public SystemCheaperModel cheaperSys;
		&#64;Subject
		public SystemCheapestModel cheapestSys;

		&#64;ParametricAnalysis
		public SystemCheapAnalysis cheapSysAnalysis;
		&#64;ParametricAnalysis
		public SystemCheaperAnalysis cheaperSysAnalysis;
		&#64;ParametricAnalysis
		public SystemCheapestAnalysis cheapestSysAnalysis;
			
		&64;AnalysisAction
		&64;Override
		protected void perform()
		{
			cheapSysAnalysis.perform();
			cheaperSysAnalysis.perform();
			cheapestSysAnalysis.perform();
		}

		&64;Override
		protected void createSubjects()
		{
			cheapSys = new SystemCheapModel();
			cheaperSys = new SystemCheaperModel();
			cheapestSys = new SystemCheapestModel();
		}

		&64;Override
		protected void createParametricAnalysisCases()
		{
			cheapSysAnalysis = new SystemCheapAnalysis(cheapSys);
			cheaperSysAnalysis = new SystemCheaperAnalysis(cheapSerys);
			cheapestSysAnalysis = new SystemCheapestAnalysis(cheapestSys);
		}
	}}</pre>
 */
public abstract class TradeOffAnalysisCase extends SysMLAnalysisCase implements StateBehaviorContext, ObservableAttribute, AttributeObserver
{
	/**
	 * Constructor to be invoked by extended class's constructor to invoke creation
	 * of the trade-offs analysis case.
	 * 
	 * @param name Unique name of the analysis case.
	 * @param id   Unique ID for this analysis case
	 */
	public TradeOffAnalysisCase(String name, Long id)
	{
		super(name, id);
		logger = Logger.getLogger(getClass().getSimpleName());

		createSubjects();
		createParametricAnalysisCases();
	}

	/**
	 * Abstract method that should create the subjects of the trade-off analysis. An
	 * example follows.
	 * 
	 * <pre>{@code
		public class MyTradeOffs extends TradeOffAnalysisCase
		{
				:
			&64;Override
			protected void createSubjects()
			{
				cheapSys = new SystemCheapModel();
				cheaperSys = new SystemCheaperModel();
				cheapestSys = new SystemCheapestModel();
			}
				:
		}}</pre>
	 */
	protected abstract void createSubjects();

	/**
	 * Abstract method that should create the parametric analyses to be applied to
	 * each of the subjects of the trade-off analysis. An example follows.
	 * 
	 * <pre>{@code
		public class MyTradeOffs extends TradeOffAnalysisCase
		{
				:
			&64;Override
			protected void createParametricAnalysisCases()
			{
				cheapSysAnalysis = new SystemCheapAnalysis(cheapSys);
				cheaperSysAnalysis = new SystemCheaperAnalysis(cheapSerys);
				cheapestSysAnalysis = new SystemCheapestAnalysis(cheapestSys);
			}
				:
		}}</pre>
	 */
	protected abstract void createParametricAnalysisCases();

	/**
	 * Name of method to create the subjects, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createSubjectsMethodName = "createSubjects";
	/**
	 * Name of method to create the parametric analysis cases, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createParametricAnalysisCasesMethodName = "createParametricAnalysisCases";
}
