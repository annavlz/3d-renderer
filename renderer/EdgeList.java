package renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * EdgeList should store the data for the edge list of a single polygon in your
 * scene. A few method stubs have been provided so that it can be tested, but
 * you'll need to fill in all the details.
 *
 * You'll probably want to add some setters as well as getters or, for example,
 * an addRow(y, xLeft, xRight, zLeft, zRight) method.
 */
public class EdgeList {
	private int startY;
	private int endY;
	private float [][] edgeList;
	
	public EdgeList(int startY, int endY) {
		this.startY = startY;
		this.endY = endY;
		createEdgeList();
	}

	public int getStartY() {
		// TODO fill this in.
		return this.startY;
	}

	public int getEndY() {
		// TODO fill this in.
		return this.endY;
	}

	public float getLeftX(int y) {
		// TODO fill this in.
		return edgeList[y - startY][1];
	}

	public float getRightX(int y) {
		// TODO fill this in.
		return edgeList[y - startY][3];
	}

	public float getLeftZ(int y) {
		// TODO fill this in.
		return edgeList[y - startY][2];
	}

	public float getRightZ(int y) {
		// TODO fill this in.
		return edgeList[y - startY][4];
	}
	private void createEdgeList(){
		edgeList = new float[endY - startY + 1][5];
		for(int i = 0; i < endY - startY + 1; i++){
			edgeList[i][0] = startY + i;
			edgeList[i][1] = Float.POSITIVE_INFINITY;
			edgeList[i][2] = Float.POSITIVE_INFINITY;
			edgeList[i][3] = Float.NEGATIVE_INFINITY;
			edgeList[i][4] = Float.POSITIVE_INFINITY;
		}
	}
	public void fill(Vector3D a, Vector3D b, Vector3D c) {
		List<List<Float>>arrays = new ArrayList<List<Float>>();
		arrays.add(getEdgeArray(a,b));
		arrays.add(getEdgeArray(b,c));
		arrays.add(getEdgeArray(a,c));
		
		for(List<Float> arr : arrays){
			int sy = Math.round(arr.get(0));
			int fy = Math.round(arr.get(1));
			float sx = arr.get(2);
			float sz = arr.get(3);
			float mx = arr.get(4);
			float mz = arr.get(5);
			float stepx = sx;
			float stepz = sz;
//			System.out.println(a + " " + b + " " + c+" "+edgeList.length);
//			System.out.println(startY + " " +sy + " " +fy + " " +sx + " " +sz + " " +mx + " " +mz);
			for(int i = sy - startY; i < (fy - startY); i++){
//				System.out.println("i " + i);
				edgeList[i][1] = Math.min(stepx, edgeList[i][1]);
				if(stepx <= edgeList[i][1]){
					edgeList[i][2] = stepz;	
				}
				edgeList[i][3] = Math.max(stepx, edgeList[i][3]);
				if(stepx >= edgeList[i][3]){
					edgeList[i][4] = stepz;
				}
				
				stepx += mx;
				stepz += mz;
			}
		}
	}
	private List<Float> getEdgeArray (Vector3D one, Vector3D two){
		List<Float> array = new ArrayList<Float>();
		if(one.y > two.y){
			float my = one.y - two.y;
			float mx = (one.x - two.x)/my;
			float mz = (one.z - two.z)/my;
			array.add(two.y);
			array.add(one.y);
			array.add(two.x);
			array.add(two.z);
			array.add(mx);
			array.add(mz);
		}
		else if (one.y < two.y){
			float my = two.y - one.y;
			float mx = (two.x - one.x)/my;
			float mz = (two.z - one.z)/my;
			array.add(one.y);
			array.add(two.y);
			array.add(one.x);
			array.add(one.z);
			array.add(mx);
			array.add(mz);		
		}
		else {
			if(one.x > two.x){
				float mx = 0;
				float mz = 0;
				array.add(two.y);
				array.add(one.y);
				array.add(one.x);
				array.add(one.z);
				array.add(mx);
				array.add(mz);
			}
			else if (one.x < two.x){
				float mx = 0;
				float mz = 0;
				array.add(one.y);
				array.add(two.y);
				array.add(two.x);
				array.add(two.z);
				array.add(mx);
				array.add(mz);		
			}
		}
		return array;
		
	}

	public int getLength() {
		// TODO Auto-generated method stub
		return edgeList.length;
	}	
		
}
