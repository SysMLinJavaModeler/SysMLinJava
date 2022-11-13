package sysmlinjava.common;

/**
 * SysMLinJava representation of the SysML activity. {@code SysMLActivity} is a
 * functional interface specification. The {@code perform()} operation performs
 * the Java statements that define an activity within the context of the
 * {@code SysMLBlock} in which it is declared.
 * <p>
 * The {@code SysMLActivity} should be declared as a field in the extended
 * {@code SysMLBlock} class. The field should be annotated with the
 * {@code Activity} annotation. It should then be implemented as an instance of
 * a Lambda function in the override of the {@code SysMLBlock}'s
 * {@code createActivies()} operation. An example is as follows:
 * 
 * <pre>
         :
     &#64;Activity
     protected SysMLActivity targetActivity;
         :
     &#64;Override
     protected void createActivities()
     {
         targetActivity = () ->
         {
             do
             {
                 findTarget();
                 trackTarget();
                 if(engagementAuthorized())
                 {
                     engageTarget();
                     assessEngagement();
                 }
             }
             while(!(targetDestroyed() || engagementDenied()));
         });
     }
         :
 * </pre>
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLActivity extends SysMLInterface
{
	/**
	 * Perform the implemented activity in the context of the {@code SysMLBlock}
	 * that contains the implementation of the functional interface.
	 */
	void perform();
}
