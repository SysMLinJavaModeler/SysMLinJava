package sysmlinjava.analysis.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import sysmlinjava.valuetypes.Point2D;
import sysmlinjava.valuetypes.Polyline2D;

public class CartesianCoordinateTransform implements Serializable
{
	/** Serial version id */
	private static final long serialVersionUID = 7445686295101109355L;

	Optional<Double> translated;
	Optional<Double> scaled;
	Optional<Double> yInverted;

	public CartesianCoordinateTransform(Optional<Double> translated, Optional<Double> scaled, Optional<Double> yInverted)
	{
		super();
		this.translated = translated;
		this.scaled = scaled;
		this.yInverted = yInverted;
	}

	public XY xyValueOf(Point2D point2D)
	{
		XY result = new XY(point2D);
		if(translated.isPresent())
		{
			result.xValue += translated.get();
			result.yValue += translated.get();
		}
		if(scaled.isPresent())
		{
			result.xValue *= scaled.get();
			result.yValue *= scaled.get();
		}
		if(yInverted.isPresent())
		{
			result.yValue = yInverted.get() - result.yValue;
		}
		return result;
	}

	public ArrayList<XY> xyListValueOf(Polyline2D polyline)
	{
		ArrayList<XY> result = new ArrayList<>();
		for(Point2D point2D: polyline.value)
			result.add(xyValueOf(point2D));
		return result;
	}
}