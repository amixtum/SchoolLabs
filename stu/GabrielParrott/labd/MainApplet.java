package stu.GabrielParrott.labd;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;

/**
 * All the work is done here. Recursively draws polygons at each vertex in a
 * sequence of polygon types.
 * 
 * See drawPolygonSequence for the assigned algorithm
 * 
 * @author Gabriel Parrott
 *
 */
public class MainApplet extends JApplet {

	// warns me if I don't include this
	// I assume it has to do with web security
	private static final long serialVersionUID = 1L;

	// required fields
	private final int initialSize = 300;
	private final int minSize = 10;
	private final float shrinkFactor = 0.5f;

	@Override
	public void init() {
		// set the starting size to a sane proportion
		this.setSize(600, 600);
	}

	public void paint(Graphics g) {
		// polygon types for the sequence
		// see drawPolygonSequence
		PolygonFactory.Type[] types = { PolygonFactory.Type.Triangle,
				PolygonFactory.Type.Hexagon };

		// call it and draw it
		drawPolygonSequence(g, types, initialSize, -1, null);
	}

	/**
	 * Draws polygons at each larger polygon's vertices in the given sequence
	 * until the size is less than the specified minimum field
	 * 
	 * @param g
	 *            Graphics context to draw to the screen
	 * @param types
	 *            This is the sequence of polygon types to be drawn
	 * @param size
	 *            The size of the polygon to be drawn.
	 * @param iteration
	 *            Used to check if it is the first iteration and to specify
	 *            which polygon type to draw
	 * @param polygons
	 *            The polygons which may or may not have their corners drawn
	 *            upon
	 */
	private void drawPolygonSequence(Graphics g, PolygonFactory.Type[] types,
			int size, int iteration, VertexAccessPolygon[] polygons) {

		// do nothing if there is no sequence specified
		if (types == null) {
			return;
		}

		// **base case**
		// exit if the size of the polygon is less than the minimum size
		if (size < minSize) {
			return;
		}

		// This is the first call
		// It draws a polygon at the center, and then at its corners. It passes
		// the corner polygons to the method for the recursion to start.
		if (iteration == -1) {
			// first polygon in the middle
			VertexAccessPolygon firstPoly = PolygonFactory.getPolygonAt(
					(int) (this.getSize().width / 2),
					(int) (this.getSize().height / 2), size, types[0]);

			// fills the polygon with blue
			g.setColor(Color.blue);
			g.fillPolygon(firstPoly.getPolygon());

			// outlines it with green
			g.setColor(Color.green);
			g.drawPolygon(firstPoly.getPolygon());

			// makes sure there's more than one element in the types arrray
			int nextType = (types.length > 1) ? 1 : 0;

			// draws the next polygon in the sequence around the corner vertices
			// and stores it in the polygons array
			polygons = drawPolygonsOnVertices(g, firstPoly, types[nextType],
					(int) (size * shrinkFactor));

			// gets the next type in the polygon sequence to begin recursion
			nextType = (nextType >= types.length - 1) ? 0 : nextType + 1;

			// recursion is go
			drawPolygonSequence(g, types, (int) (size * shrinkFactor),
					nextType, polygons);

		}

		else {

			// iterate over the corner polygons passed in the last call
			for (VertexAccessPolygon poly : polygons) {

				// change the polygon type. Simple "ring" e.g. it advances the
				// array index, or moves back to 0 if it reaches the end.
				int nextType = (iteration >= types.length - 1) ? 0
						: iteration + 1;

				// draw the corner polygons and store them in an array for the
				// next call
				VertexAccessPolygon[] cornerPolys = drawPolygonsOnVertices(g,
						poly, types[iteration], (int) (size * shrinkFactor));

				// call it again
				// it will end when (size * shrinkFactor) is less than the
				// specified minimum size.
				// see class fields
				drawPolygonSequence(g, types, (int) (size * shrinkFactor),
						nextType, cornerPolys);
			}
		}

	}

	/**
	 * Helper method for the main recursive algorithm. Draws polygons of the
	 * specified type on the corners of the given base polygon
	 * 
	 * @param g
	 *            The graphics context
	 * @param base
	 *            The base polygon to draw polygons on
	 * @param toDraw
	 *            The type of the polygon to draw on the corners. See
	 *            PolygonFactory for possible types
	 * @param size
	 *            The size of the corner polygons to draw.
	 * @return
	 */
	private VertexAccessPolygon[] drawPolygonsOnVertices(Graphics g,
			VertexAccessPolygon base, PolygonFactory.Type toDraw, int size) {

		// The polygons to return
		VertexAccessPolygon[] polygons = new VertexAccessPolygon[base
				.getVertexCount()];

		// draws the polygons specified by the toDraw parameter around the
		// vertices of the base polygon
		for (int i = 0; i < base.getVertexCount(); ++i) {
			polygons[i] = PolygonFactory.getPolygonAt(base.getVertex(i).getX(),
					base.getVertex(i).getY(), size, toDraw);

			// fills the polygon with blue
			g.setColor(Color.blue);
			g.fillPolygon(PolygonFactory.getPolygonAt(base.getVertex(i).getX(),
					base.getVertex(i).getY(), size, toDraw).getPolygon());

			// outlines it with green
			g.setColor(Color.green);
			g.drawPolygon(PolygonFactory.getPolygonAt(base.getVertex(i).getX(),
					base.getVertex(i).getY(), size, toDraw).getPolygon());
		}

		// here's your polygons
		return polygons;
	}
}
