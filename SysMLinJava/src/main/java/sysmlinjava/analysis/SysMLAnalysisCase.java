package sysmlinjava.analysis;

import sysmlinjava.actions.SysMLCalculationFunction;
import sysmlinjava.actions.SysMLCase;
import sysmlinjava.javaannotations.actions.AnalysisCaseAction;

/**
 * SysMLinJava representation of the SysML analysis case. The analysis case is
 * declared as a specialization of the {@code SysMLAnalysisCase} with members of
 * the {@code SysMLCase} plus members that are unique to the analysis case, i.e.
 * the analysis results, the actions used to evaluate the results, and any
 * sub-analysis cases. An example follow.
 * 
 * <pre>{@code
				public class MyAnalysis extends SysMLAnalysisCase
				{
						:
					&#64;AnalysisResult
					DistanceMeters finalDistance;
					&#64;AnalysisResult
					KeyValueMap<String, Point2D> waypoints;
						:
					&#64;AnalysisResultEvalution
					FirstResultEvaluation firstEval;
					&#64;AnalysisResultEvalution
					LastResultEvaluation lastEval;
						:
					&#64;AnalysisCase
					InitialAnalysisCase initialAnalysis;
					&#64;AnalysisCase
					BasicAnalysisCase basicAnalysis;
					&#64;AnalysisCase
					FinalAnalysisCase finalAnalysis;
						:
					&#64;Override
					protected void createResult()
					{
						distance = new DistanceMeters(0);
						waypoints = new KeyValueMap<String, Point2D>();
					}
						:
					&#64;Override
					protected void createResultEvaluation()
					{
						firstEval = new FirstResultEvaluation(this);
						lastEval = new LastResultEvaluation(this);
					}
						:
					&#64;Override
					protected void createAnalysisCases()
					{
						initialAnalyisis = new InitialAnalysisCase(this);
						basicAnalyisis = new BasicAnalysisCase(this);
						finalAnalyisis = new FinalAnalysisCase(this);
					}
						:
					&#64;Override
					protected void createSubject()
					{
						...
					}
						:
					&#64;Override
					protected void createAttributes()
					{
						...
					}
						:
					&#64;Override
					protected void createSupportingInfoLinks()
					{
						...
					}
						:
						etc. ...
					
				}}</pre>
 */
public abstract class SysMLAnalysisCase extends SysMLCase
{
	protected SysMLAnalysisCase(String name, Long id)
	{
		super(name, id);

		createResult();
		createResultEvaluation();
		createAnalysisCases();
	}

	/**
	 * Overridable method to perform the analysis case action. The {@code perform()}
	 * method can be overridden to perform a custom action for the analysis case as
	 * needed. Default is to perform the {@code function} of the case action, if
	 * present and if it is of type {@code SysMLCalculationFunction}. The case
	 * action function is defined in an override of the base @{@code SysMLAction}'s
	 * {@code createFunction()} method.
	 * 
	 * @see sysmlinjava.actions.SysMLAction#createActionFunctions
	 */
	@AnalysisCaseAction
	@Override
	protected void perform()
	{
		if (function != null && function instanceof SysMLCalculationFunction analysisCaseFunction)
			analysisCaseFunction.perform();
		else
			logger.severe("missing/unrecognized calculation function to perform analysis case " + this.getClass().getSimpleName());
	}

	/**
	 * Creates/initializes the action functions of the verification case. Note
	 * verification case action functions can be defined by simple methods annotated
	 * with &#64;{@code AnalysisCaseAction} in lieu of this use of the
	 * {@code SysMLCalculation} and {@code SysMLCalculationFunction}. An example
	 * follows.
	 * 
	 * <pre>{@code
				public class MyAnalysisCase extends SysMLAnalysisCase
				{
					MySystem analysisSystem;
							:
					public MyAnalysisCase(String name, Long id, Optional<StateBehaviorContext> context)
					{
						super(name, id, context);
						analysisSystem = new MySystem();
					}
						:
					&#64;AnalysisCaseActionFunction
					SysMLCalculationFunction firstAnalysisFunction;
					&#64;AnalysisCaseActionFunction
					SysMLCalculationFunction secondAnalysisFunction;
						:
					&#64;AnalysisCaseAction
					SysMLCalculation firstAnalysis;
					&#64;AnalysisCaseAction
					SysMLCalculation secondAnalysis;
						:
					&#64;Override
					protected void createSubject()
					{
						subject = Optional.of(MySystem.class);
					}
				
					&#64;Override
					protected void createObjective()
					{
						:
					}
				
					&#64;Override
					protected void createActors()
					{
						:
					}
				
					&#64;Override
					protected void createAnalysisCaseActionFunctions()
					{
						firstAnalysisFunction = () ->
						{
							analysisSystem.start();
							delay(30);
							analysisSystem.stop();
							analyzeComponents();
						};

						secondAnalysisFunction = () ->
						{
							analysisSystem.start();
							delay(300);
							analysisSystem.stop();
							analyzeComponents();
						};
					}
							:
					&#64;Override
					protected void createAnalysisCaseActions()
					{
						firstAnalysis = new SysMLCalculation(firstAnalysisFunction, "FirstAnalysis", 0L);
						secondAnalysis = new SysMLCalculation(secondAnalysisFunction, "SecondAnalysis", 1L);
					}
							:
				}
				}</pre>
	 */
	protected void createAnalysisCaseActionFunctions()
	{
	}

	/**
	 * Creates/initializes the actions of the verification case. Note verification
	 * case actions can be defined by simple methods annotated with
	 * &#64;{@code AnalysisCaseAction} in lieu of this use of the
	 * {@code SysMLCalculation} and {@code SysMLCalculationFunction}. An example
	 * follows.
	 * 
	 * <pre>{@code
				public class MyAnalysisCase extends SysMLAnalysisCase
				{
					MySystem analysisSystem;
							:
					public MyAnalysisCase(String name, Long id, Optional<StateBehaviorContext> context)
					{
						super(name, id, context);
						analysisSystem = new MySystem();
					}
						:
					&#64;AnalysisCaseActionFunction
					SysMLCalculationFunction firstAnalysisFunction;
					&#64;AnalysisCaseActionFunction
					SysMLCalculationFunction secondAnalysisFunction;
						:
					&#64;AnalysisCaseAction
					SysMLCalculation firstAnalysis;
					&#64;AnalysisCaseAction
					SysMLCalculation secondAnalysis;
						:
					&#64;Override
					protected void createSubject()
					{
						subject = Optional.of(MySystem.class);
					}
				
					&#64;Override
					protected void createObjective()
					{
						:
					}
				
					&#64;Override
					protected void createActors()
					{
						:
					}
				
					&#64;Override
					protected void createAnalysisCaseActionFunctions()
					{
						firstAnalysisFunction = () ->
						{
							analysisSystem.start();
							delay(30);
							analysisSystem.stop();
							analyzeComponents();
						};
						secondAnalysisFunction = () ->
						{
							analysisSystem.start();
							delay(300);
							analysisSystem.stop();
							analyzeComponents();
						};
					}
							:
					&#64;Override
					protected void createAnalysisCaseActions()
					{
						firstAnalysis = new SysMLCalculation(firstAnalysisFunction, "FirstAnalysis", 0L);
						secondAnalysis = new SysMLCalculation(secondAnalysisFunction, "SecondAnalysis", 1L);
					}
							:
				}
				}</pre>
	 */
	protected void createAnalysisCaseActions()
	{
	}

	/**
	 * Overridable method to create the results. An example follows.
	 * 
	 * <pre>{@code
				public class MyAnalysis extends SysMLAnalysisCase
				{
						:
					&#64;AnalysisResult
					DistanceMeters finalDistance;
					&#64;AnalysisResult
					KeyValueMap<String, Point2D> waypoints;
					
					&#64;Override
					protected void createResult()
					{
						distance = new DistanceMeters(0);
						waypoints = new KeyValueMap<String, Point2D>();
					}
				}}</pre>
	 */
	protected void createResult()
	{
	}

	/**
	 * Overridable operation that creates the result evaluation of the analysis
	 * case, i.e. the action to be performed to evaluate the result. An example
	 * follows:
	 * 
	 * <pre>{@code
				public class MyAnalysisCase extends SysMLAnalysisCase
				{
						:
					&#64;AnalysisResultEvalution
					FirstResultEvaluation firstEval;
					&#64;AnalysisResultEvalution
					LastResultEvaluation lastEval;
						:
					protected void createResultEvaluation()
					{
						firstEval = new FirstResultEvaluation(this);
						lastEval = new LastResultEvaluation(this);
					}
				}}</pre>
	 * 
	 * Note the example uses two result evaluations, both of which are presuemd to
	 * be specializations of the {@code SysMLAction}. However, evaluation could be
	 * performed by methods declared in the {@code SysMLAnalysisCase} and annotated
	 * as &#64;{@code AnalysisResultEvaluation}s, in lieue of annotated method(s) in
	 * lieu of creating a {@code SysMLAction} as shown here.
	 */
	protected void createResultEvaluation()
	{
	}

	/**
	 * Overridable operation that creates the sub-analysis cases, i.e cases that are
	 * invoked by this analysis case. An example follows.
	 * 
	 * <pre>{@code
				public class MyAnalysisCase extends SysMLAnalysisCase
				{
						:
					&#64;AnalysisCase
					InitialAnalysisCase initialAnalysis;
					&#64;AnalysisCase
					BasicAnalysisCase basicAnalysis;
					&#64;AnalysisCase
					FinalAnalysisCase finalAnalysis;
						:
					protected void createResultEvaluation()
					{
						initialAnalyisis = new InitialAnalysisCase(this);
						basicAnalyisis = new BasicAnalysisCase(this);
						finalAnalyisis = new FinalAnalysisCase(this);
					}
				}}</pre>
	 */
	protected void createAnalysisCases()
	{

	}

	/**
	 * Name of method to create the analysis case action functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createAnalysisCaseActionFunctionsMethodName = "createAnalysisCaseActionFunctions";
	/**
	 * Name of method to create the analysis case actions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createAnalysisCaseActionsMethodName = "createAnalysisCaseActions";
	/**
	 * Name of method to create the result of analysis, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createResultMethodName = "createResult";
	/**
	 * Name of method to create the evalution of the result of analysis, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createResultEvaluationMethodName = "createResultEvaluation";
	/**
	 * Name of method to create the analysis sub-cases, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createAnalysisCasesMethodName = "createAnalysisCases";
}
