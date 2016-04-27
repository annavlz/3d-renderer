package renderer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import renderer.Scene.Polygon;

public class Renderer extends GUI {
	public Scene scene;
	public List<Polygon> polygons = new ArrayList<Polygon>();
	public Vector3D lightPos;
	@Override
	protected void onLoad(File file) {
		// TODO fill this in.

		/*
		 * This method should parse the given file into a Scene object, which
		 * you store and use to render an image.
		 */

		String  thisLine = null;
	      try{
	         BufferedReader br = new BufferedReader(new FileReader(file));
	         String[] lightLine = br.readLine().split("\\s+");
	         float [] light = new float[3];
        	 for(int i=0; i<3; i++){
        		 light[i] = Float.parseFloat(lightLine[i]);
        	 }
        	 lightPos = new Vector3D(light[0], light[1], light[2]);
	        
	         while ((thisLine = br.readLine()) != null) {
	        	String [] parts = thisLine.split("\\s+");
	        	float [] coords = new float[9];
	        	int [] color = new int[3];
	        	for(int i=0; i<9; i++){
	        		coords[i] = Float.parseFloat(parts[i]);
	        	}
	        	for(int i = 0; i<3; i++){
	        		color[i] = Integer.parseInt(parts[i+9]);
	        	}
	        	
	        	polygons.add(new Polygon(coords, color));
	         }       
	         br.close();
	      } catch(Exception e){
	         e.printStackTrace();
	      }
			List<Polygon> visiblePolygons = new ArrayList<Polygon>();
			for(Polygon poly : polygons){
				if(!Pipeline.isHidden(poly)){
					poly.setReflectance(Pipeline.getShading(poly, lightPos, new Color(100, 255, 100), new Color(0, 0, 0))); 
					visiblePolygons.add(poly);
				}
			}
		    scene = new Scene(visiblePolygons, lightPos);
		    System.out.println(scene.getMaxX());
		    System.out.println(scene.getMaxY());
		    System.out.println(scene.getMinX());
		    System.out.println(scene.getMinY());

	      render();
	      
	}

	@Override
	protected void onKeyPress(KeyEvent ev) {
		// TODO fill this in.

		/*
		 * This method should be used to rotate the user's viewpoint.
		 */
	}

	@Override
	protected BufferedImage render() {
		// TODO fill this in.
		System.out.println(scene == null);
		Color[][] bitmap = new Color[CANVAS_WIDTH][CANVAS_HEIGHT];
		float[][] zDepth = new float[CANVAS_WIDTH][CANVAS_HEIGHT];	
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				bitmap[x][y] = new Color(100,100,100);
			}
		}
		if(scene == null){
			return convertBitmapToImage(bitmap);
		}
		/*
		 * This method should put together the pieces of your renderer, as
		 * described in the lecture. This will involve calling each of the
		 * static method stubs in the Pipeline class, which you also need to
		 * fill in.
		 */
		else {
		    for(Polygon poly : scene.getPolygons()){
		    	EdgeList el = Pipeline.computeEdgeList(poly);
				Pipeline.computeZBuffer(bitmap, zDepth, el, poly.getReflectance());
		    }
			return convertBitmapToImage(bitmap);
		}
	}

	/**
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}
	

	public static void main(String[] args) {
		new Renderer();
	}
}

// code for comp261 assignments
