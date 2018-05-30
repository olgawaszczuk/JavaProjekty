package algebra;

import java.util.ArrayList;
import java.util.Random;
public class tempMain {

	public static void main(String[] args) {
		
	ArrayList<ArrayList<Double>> data1 = new ArrayList<ArrayList<Double>>();
	Random generator = new Random();
	for (int i=0;i<10;i++) {
		data1.add(new ArrayList<Double>());
		for (int j=0; j<5; j++) {
			Double element = new Double(generator.nextInt(2));
			data1.get(i).add(element);
		}
	}
	System.out.println(data1.get(3).size());
	
	Matrix X = new Matrix(data1);
	X.display();
	
	Matrix Xt = X.transpose();
	Matrix XtX = Xt.dotProduct(X);
	X.display();
	Xt.display();
	XtX.display();
		
	}

}
