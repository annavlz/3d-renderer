import java.util.ArrayList;
import java.util.List;

// code for comp261 assignments
int yLength = yMax - yMin + 1;
List<List<Float>>arrays = new ArrayList<List<Float>>();
arrays.add(getEdgeArray(a,b));
arrays.add(getEdgeArray(b,c));
arrays.add(getEdgeArray(a,c));
int[][] edgeList = new int[yLength][5];
for(List<Float> arr : arrays){
	int sy = Math.round(arr.get(0));
	int fy = Math.round(arr.get(1));
	float sx = arr.get(2);
	float sz = arr.get(3);
	float mx = arr.get(4);
	float mz = arr.get(5);
	edgeList[sy - 1][0] = sy;
	edgeList[sy - 1][1] = Math.round(arr.get(2));
	edgeList[sy - 1][2] = Math.round(arr.get(3));
	edgeList[sy - 1][3] = Math.round(arr.get(2));
	edgeList[sy - 1][4] = Math.round(arr.get(3));

	for(int i = sy; i < fy; i++){
		edgeList[i][0] = edgeList[i-1][0] + 1;
		edgeList[i][1] = (int) Math.min((edgeList[i-1][1] + mx), edgeList[i][1]);
		edgeList[i][2] = (int) Math.min((edgeList[i-1][2] + mz), edgeList[i][2]);
		edgeList[i][3] = (int) Math.max((edgeList[i-1][3] + mx), edgeList[i][3]);
		edgeList[i][4] = (int) Math.min((edgeList[i-1][4] + mz), edgeList[i][4]);

	}
}
System.out.println(edgeList);
return edgeList;
}
public static List<Float> getEdgeArray (Vector3D one, Vector3D two){
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
else{
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
return array;
}
// code for comp261 assignments
