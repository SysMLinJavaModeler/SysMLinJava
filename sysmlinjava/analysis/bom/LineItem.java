package sysmlinjava.analysis.bom;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * SysMLinJava representation of a line item in a bill-of-materials. The
 * {@code LineItem} itemizes a "part" of a system or component in terms of a
 * name/title, quantity/multiplicity, a set of values that further specify the
 * part, and an arbitrary set of &lt;name&gt;:&lt;value&gt; pairs as a comment
 * that specify additional "columns" of information about the part.
 * <p>
 * The elements of the {@code LineItem} are obtained from elements of the
 * SysMLinJava model. The model elements for the {@code LineItem} are designated
 * by annotations for the line item/part and for the values and comments. See
 * the annotations references below for how to designate the model elements that
 * are to constitute the line item.
 * <p>
 * Generation of the bill-of-materials as a set of {@code LineItem}s can be
 * performed by extracting the line item information from the annotated model
 * elements and entering this information into a {@code LineItem} instance. The
 * set of {@code LineItem} instances can then be written to a file in the form
 * of a table for the bill-of-materials. However, the SysMLinJava
 * TaskMaster&trade; application, available for purchase/download at
 * SysMLinJava.com, includes a task operation that performs this process by
 * parsing selected SysMLinJava model files and generating the {@code LineItem}s
 * of the bill-of-materials from those and other model files as necessary to
 * generate the BOM line items as a formatted .CSV file. This .CSV file can then
 * be easily imported into a spreadsheet or other tabular application for
 * further refinement, calculation, and presentation as desired. Go to
 * SysMLinJava.com for more details.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.bom.annotations.BOMLineItem
 * @see sysmlinjava.analysis.bom.annotations.BOMLineItemValue
 * @see sysmlinjava.analysis.bom.annotations.BOMLineItemComment
 *
 */
public class LineItem
{
	/**
	 * Integer number of the line item, typically a sequence number 0, 1, 2, etc.
	 */
	public Integer number;
	/**
	 * Title of the line item, typically the name of the java "type" of the part or
	 * port represented by the line item
	 */
	public String title;
	/**
	 * Muliplicity representation for the quantity of the line item.
	 */
	public String multiplicity;
	/**
	 * Set of value representations that further specify the line item, typically
	 * each is represented as a value name, units, and initial value, e.g.
	 * 
	 * <pre>
	 {@code
	 size (feetCubic) = 1.5
	 weight (pounds) = 50
	 }
	 * </pre>
	 */
	public List<String> values;
	/**
	 * Set of &lt;name&gt;:&lt;value&gt; pairs that constitute unique information
	 * for the line item in separate columns in the line items table, e.g.
	 * 
	 * <pre>
	 {@code
	 Description:Switch router component for the local network
	 Source:SwitchesRUs.com
	 Comment:Supports IEEE 802.11 for gigabit ethernet
	 }
	 * </pre>
	 * 
	 */
	public List<String> commentLines;

	/**
	 * Constructor for default initialization
	 */
	public LineItem()
	{
		super();
		number = 0;
		title = "not found";
		multiplicity = "not found";
		values = new ArrayList<>();
		commentLines = new ArrayList<>();
	}

	/**
	 * Constructor for all initializations
	 * 
	 * @param number       number of line item
	 * @param title        title of the line item
	 * @param multiplicity multiplicity (quantity) of the line item
	 * @param values       values that further specify the line item
	 * @param commentLines &lt;name&gt;:&lt;value&gt; pairs that provide further
	 *                     information on the line item
	 */
	public LineItem(Integer number, String title, String multiplicity, List<String> values, List<String> commentLines)
	{
		super();
		this.number = number;
		this.title = title;
		this.multiplicity = multiplicity;
		this.values = values;
		this.commentLines = commentLines;
	}

	@Override
	public String toString()
	{
		StringJoiner valuesJoiner = new StringJoiner("], [", "[", "]");
		values.forEach(value -> valuesJoiner.add(value));
		StringJoiner commentsJoiner = new StringJoiner("], [", "[", "]");
		commentLines.forEach(commentLine -> commentsJoiner.add(commentLine));

		return String.format("%s|%s|%s|%s|%s", number, title, multiplicity, valuesJoiner.toString(), commentsJoiner.toString());
	}
}
