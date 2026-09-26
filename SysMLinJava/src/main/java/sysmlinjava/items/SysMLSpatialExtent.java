package sysmlinjava.items;

/**
 * SysMLinJava representation of the spatial extent of the SysML item. This
 * current version does not support the extensive definition of spatial extent
 * for graphical images. Instead, the current version provides a simplified
 * definition consisting of a few basic "shapes" that can be used to define the
 * vast majority of spateal extents of a part or port or item.
 */
public class SysMLSpatialExtent
{
	/**
	 * Point at which the assumed base of the item is located in 3D space
	 */
	public Point position;
	/**
	 * Vector for the rotation ot the item within each X, Y, Z plane
	 */
	public Vector rotation;
	/**
	 * Whether this spatial extent is a "void", i.e. is an empty space item
	 */
	boolean isVoid;

	/**
	 * Constructor for the basic shape
	 * 
	 * @param position Point at which the assumed base of the item is located in 3D
	 *                 space
	 * @param rotation Vector for the rotation of the item relative to the base
	 * @param isVoid   Whether this spatial extent is a "void", i.e. is an empty
	 *                 space item
	 */
	public SysMLSpatialExtent(Point position, Vector rotation, boolean isVoid)
	{
		super();
		this.position = position;
		this.rotation = rotation;
		this.isVoid = isVoid;
	}

	/**
	 * 3D point used to define the spatial extent
	 */
	public static final class Point
	{
		/**
		 * X value of the point
		 */
		public double x;
		/**
		 * Y value of the point
		 */
		public double y;
		/**
		 * Z value of the point
		 */
		public double z;

		/**
		 * Constructor of the point
		 * 
		 * @param x X value of the point
		 * @param y Y value of the point
		 * @param z Z value of the point
		 */
		public Point(double x, double y, double z)
		{
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	/**
	 * 3D vector to define a rotation
	 */
	public static final class Vector
	{
		/**
		 * Rotation within the X plane
		 */
		public double xRadians;
		/**
		 * Rotation within the Y plane
		 */
		public double yRadians;
		/**
		 * Rotation within the Z plane
		 */
		public double zRadians;

		/**
		 * Construct of the vector
		 * 
		 * @param xRadians Rotation within the X plane
		 * @param yRadians Rotation within the Y plane
		 * @param zRadians Rotation within the Z plane
		 */
		public Vector(double xRadians, double yRadians, double zRadians)
		{
			super();
			this.xRadians = xRadians;
			this.yRadians = yRadians;
			this.zRadians = zRadians;
		}
	}

	/**
	 * Spatial extent of a box shape
	 */
	public static final class Box extends SysMLSpatialExtent
	{
		/**
		 * Height of the box along z axis
		 */
		public double zHeight;
		/**
		 * Width of the box along z axis
		 */
		public double xWidth;
		/**
		 * Depth of the box along z axis
		 */
		public double yDepth;

		/**
		 * Constructor of the Box
		 * 
		 * @param position Point at which the box (corner nearest 0,0,0 coordinate) is
		 *                 located
		 * @param rotation Vector defining the box rotation within each X, Y Z plane
		 * @param zHeight  Height of the box along z axis
		 * @param xWidth   Width of the box along x axis
		 * @param yDepth   Depth of the box along y axis
		 * @param isVoid   True if this a void, false otherwise
		 */
		public Box(Point position, Vector rotation, double zHeight, double xWidth, double yDepth, boolean isVoid)
		{
			super(position, rotation, isVoid);
			this.zHeight = zHeight;
			this.xWidth = xWidth;
			this.yDepth = yDepth;
		}
	}

	/**
	 * Spatial extent of a cylinder shape
	 */
	public static final class Cylinder extends SysMLSpatialExtent
	{
		/**
		 * Radius of the cylinder
		 */
		public double radius;
		/**
		 * Height of the cylinder
		 */
		public double zHeight;

		/**
		 * Constructor of the Box
		 * 
		 * @param position Point at which the cylinder (center of end) is located
		 * @param rotation Vector defining the cylinder rotation within each X, Y, Z
		 *                 plane
		 * @param radius   Radius of the cylinder
		 * @param zHeight  Height of the box along z axis
		 * @param isVoid   True if this a void, false otherwise
		 */
		public Cylinder(Point position, Vector rotation, double radius, double zHeight, boolean isVoid)
		{
			super(position, rotation, isVoid);
			this.radius = radius;
			this.zHeight = zHeight;
		}
	}

	/**
	 * Spatial extent of a sphere shape
	 */
	public static final class Sphere extends SysMLSpatialExtent
	{
		/**
		 * Radius of the sphere
		 */
		public double radius;

		/**
		 * Constructor of the Sphere
		 * 
		 * @param position Point at which the sphere (center) is located
		 * @param rotation Vector defining the sphere rotation within the X, Y, and Z
		 *                 planes
		 * @param radius   Radius of the sphere
		 * @param isVoid   True if this a void, false otherwise
		 */
		public Sphere(Point position, Vector rotation, double radius, boolean isVoid)
		{
			super(position, rotation, isVoid);
			this.radius = radius;
		}
	}

	/**
	 * Spatial extent as a mesh
	 */
	public static class Mesh extends SysMLSpatialExtent
	{
		/**
		 * Points of the mesh
		 */
		double[] points;
		/**
		 * Faces of the mesh
		 */
		int[][] faces;

		/**
		 * Constructor of the mesh shape
		 * 
		 * @param position Point at which the "base" of the mesh is located
		 * @param rotation Vector of the rotation of the mesh within the X, Y, Z planes
		 * @param points   doubles for the points of the mesh
		 * @param faces    integer representation the type of faces of the mesh
		 * @param isVoid   true if this mesh represents a void, false otherwise
		 */
		public Mesh(Point position, Vector rotation, double[] points, int[][] faces, boolean isVoid)
		{
			super(position, rotation, isVoid);
			this.points = points;
			this.faces = faces;
		}
	}
}
