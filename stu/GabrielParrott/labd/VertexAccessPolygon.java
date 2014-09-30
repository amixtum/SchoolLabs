package stu.GabrielParrott.labd;

import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Simple wrapper for a java.awt.Polygon object that allows simple access to a
 * polygon's vertices as opposed to using a awt.geom.PathIterator
 * 
 * @author Gabriel Parrott
 *
 */
public class VertexAccessPolygon {
	private Polygon polygon;
	private ArrayList<Vertex> vertices = new ArrayList<>();

	/**
	 * Constructs the polygon and fills the Vertex ArrayList. asserts that the
	 * number of vertices given is truthful
	 * 
	 * @param xVertices
	 *            the x coordinates of the polygon
	 * @param yVertices
	 *            the y coordinates of the polygon
	 * @param nVertices
	 *            the number of vertices in the polygon : must match the length
	 *            of the xVertices and yVertices arrays
	 */
	public VertexAccessPolygon(int[] xVertices, int[] yVertices, int nVertices) {
		assert (nVertices == xVertices.length && nVertices == yVertices.length);

		polygon = new Polygon(xVertices, yVertices, nVertices);

		for (int i = 0; i < nVertices; ++i) {
			vertices.add(new Vertex(xVertices[i], yVertices[i]));
		}
	}

	/**
	 * 
	 * @return The number of vertices in the polygon
	 */
	public int getVertexCount() {
		return vertices.size();
	}

	/**
	 * Gets the vertex at the specified index
	 * 
	 * @param index
	 *            Starts at 0, ends at length - 1. Should be used with
	 *            getVertexCount() to avoid out-of-bounds access
	 * @return The vertex at the given index, if the index is valid
	 */
	public Vertex getVertex(int index) {
		return vertices.get(index);
	}

	/**
	 * Gets the polygon associated with the object
	 * 
	 * @return The polygon object associated with this object
	 */
	public Polygon getPolygon() {
		return polygon;
	}

	/**
	 * Tiny member class to hold the (x,y) coordinates of the polygon's vertices
	 * 
	 * @author Gabriel Parrott
	 *
	 */
	public class Vertex {
		private int x;
		private int y;

		/**
		 * 
		 * @param x
		 *            The x coordinate of the vertex
		 * @param y
		 *            The y coordinate of the vertex
		 */
		private Vertex(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * 
		 * @return The x coordinate of the vertex
		 */
		public int getX() {
			return x;
		}

		/**
		 * 
		 * @return The y coordinate of the vertex
		 */
		public int getY() {
			return y;
		}
	}
}
