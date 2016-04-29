package renderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.Scene.Polygon;

/**
 * The Pipeline class has method stubs for all the major components of the
 * rendering pipeline, for you to fill in.
 * 
 * Some of these methods can get quite long, in which case you should strongly
 * consider moving them out into their own file. You'll need to update the
 * imports in the test suite if you do.
 */
public class Pipeline {

	/**
	 * Returns true if the given polygon is facing away from the camera (and so
	 * should be hidden), and false otherwise.
	 */
	public static boolean isHidden(Polygon poly) {
		Vector3D[] vertices = poly.getVertices();
		Vector3D a = vertices[1].minus(vertices[0]);
		Vector3D b = vertices[2].minus(vertices[1]);
		Vector3D normal = a.crossProduct(b);
		return normal.z > 0 ? true : false;
	}

	/**
	 * Computes the colour of a polygon on the screen, once the lights, their
	 * angles relative to the polygon's face, and the reflectance of the polygon
	 * have been accounted for.
	 * 
	 * @param lightDirection
	 *            The Vector3D pointing to the directional light read in from
	 *            the file.
	 * @param lightColor
	 *            The color of that directional light.
	 * @param ambientLight
	 *            The ambient light in the scene, i.e. light that doesn't depend
	 *            on the direction.
	 */
	public static Color getShading(Polygon poly, Vector3D lightDirection, Color lightColor, Color ambientLight) {
		// TODO fill this in.
		Vector3D[] vertices = poly.getVertices();
		Vector3D a = vertices[1].minus(vertices[0]);
		Vector3D b =  vertices[2].minus(vertices[1]);
		Vector3D normal = a.crossProduct(b);
//		System.out.println(normal);
		float angle = Math.abs(normal.cosTheta(lightDirection));
//		System.out.println(angle);
	
		Color polyColor = poly.getReflectance();
//		System.out.println(ambientLight.getRed());
//		System.out.println(ambientLight.getGreen());
//		System.out.println(ambientLight.getBlue());
//		System.out.println(lightColor.getRed());
//		System.out.println(lightColor.getGreen());
//		System.out.println(lightColor.getBlue());
//		System.out.println(polyColor.getRed()/255.0f);
//		System.out.println(polyColor.getGreen()/255.0f);
//		System.out.println(polyColor.getBlue()/255.0f);
		
		int r = Math.round(polyColor.getRed()/255.0f * (ambientLight.getRed() + lightColor.getRed() * angle));
		int g = Math.round(polyColor.getGreen()/255.0f * (ambientLight.getGreen() + lightColor.getGreen() * angle));
		int bl = Math.round(polyColor.getBlue()/255.0f * (ambientLight.getBlue() + lightColor.getBlue() * angle));
//		System.out.println(r);
//		System.out.println(g);
//		System.out.println(bl);
		Color shade = new Color (r, g, bl);
//		System.outprintln(angle + ", " +r + ", " + g + ", "+ bl);
		return shade;
	}

	/**
	 * This method should rotate the polygons and light such that the viewer is
	 * looking down the Z-axis. The idea is that it returns an entirely new
	 * Scene object, filled with new Polygons, that have been rotated.
	 * 
	 * @param scene
	 *            The original Scene.
	 * @param xRot
	 *            An angle describing the viewer's rotation in the YZ-plane (i.e
	 *            around the X-axis).
	 * @param yRot
	 *            An angle describing the viewer's rotation in the XZ-plane (i.e
	 *            around the Y-axis).
	 * @return A new Scene where all the polygons and the light source have been
	 *         rotated accordingly.
	 */
	public static Scene rotateScene(Scene scene, float xRot, float yRot) {
		// TODO fill this in.
		return null;
	}

	/**
	 * This should translate the scene by the appropriate amount.
	 * 
	 * @param scene
	 * @return
	 */
	public static Scene translateScene(Scene scene, Vector3D step) {
		// TODO fill this in.
		
		List<Polygon> polys = new ArrayList<Polygon>();
		for(Polygon poly : scene.getPolygons()){
			Vector3D a = poly.getVertices()[0].plus(step);
			Vector3D b = poly.getVertices()[1].plus(step);
			Vector3D c = poly.getVertices()[2].plus(step);
			Polygon newPoly = new Polygon(a,b,c, poly.getReflectance());
			polys.add(newPoly);
		}
		Scene newScene = new Scene(polys, scene.getLight());
		return newScene;
	}

	/**
	 * This should scale the scene.
	 * 
	 * @param scene
	 * @return
	 */
	public static Scene scaleScene(Scene scene, Vector3D scale) {
		Transform t = Transform.newScale(scale);
		List<Polygon> polys = new ArrayList<Polygon>();
		for(Polygon poly : scene.getPolygons()){	
			Vector3D a = t.multiply(poly.getVertices()[0]);		
			Vector3D b = t.multiply(poly.getVertices()[1]);
			Vector3D c = t.multiply(poly.getVertices()[2]);
			
			Polygon newPoly = new Polygon(a,b,c, poly.getReflectance());
			polys.add(newPoly);
		}
		Scene newScene = new Scene(polys, scene.getLight());
		return newScene;
	}

	/**
	 * Computes the edgelist of a single provided polygon, as per the lecture
	 * slides.
	 */
	public static EdgeList computeEdgeList(Polygon poly) {
		// TODO fill this in.
		Vector3D a = poly.getVertices()[0];
		Vector3D b = poly.getVertices()[1];
		Vector3D c = poly.getVertices()[2];
		int yMin = (int) Math.min(c.y, Math.min(a.y, b.y));
		int yMax = (int) Math.max(c.y, Math.max(a.y, b.y));
		EdgeList el = new EdgeList(yMin, yMax);
		el.fill(a,b,c);
		return el;
	}

	/**
	 * Fills a zbuffer with the contents of a single edge list according to the
	 * lecture slides.
	 * 
	 * The idea here is to make zbuffer and zdepth arrays in your main loop, and
	 * pass them into the method to be modified.
	 * 
	 * @param zbuffer
	 *            A double array of colours representing the Color at each pixel
	 *            so far.
	 * @param zdepth
	 *            A double array of floats storing the z-value of each pixel
	 *            that has been coloured in so far.
	 * @param polyEdgeList
	 *            The edgelist of the polygon to add into the zbuffer.
	 * @param polyColor
	 *            The colour of the polygon to add into the zbuffer.
	 */
	public static void computeZBuffer(Color[][] zbuffer, float[][] zdepth, EdgeList polyEdgeList, Color polyColor) {
		// TODO fill this in.
//		System.out.println("startInsideZbuffer");
		for(int i=polyEdgeList.getStartY(); i < polyEdgeList.getEndY(); i++){
			int xL = (int) polyEdgeList.getLeftX(i); 
			int xR = (int) polyEdgeList.getRightX(i);
			float zL = polyEdgeList.getLeftZ(i); 
			float zR = polyEdgeList.getRightZ(i); 
			if(xR-xL == 0){
				if(zdepth[i][xL] > zL){
					zdepth[i][xL] = zL;
					zbuffer[i][xL] = polyColor;
				}
			}
			else{
				float zStep = (zR - zL)/(xR-xL);
				float zC = zL;
				for(int x = xL; x < xR; x++ ){
					float z = zL;
					zC += zStep;
					if(zdepth[i][x] > z){
						zdepth[i][x] = z;
						zbuffer[i][x] = polyColor;
					}
				}
			}

		}
	}
}

// code for comp261 assignments
