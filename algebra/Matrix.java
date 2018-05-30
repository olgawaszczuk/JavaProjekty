package algebra;

import java.util.ArrayList;

public class Matrix {
	public Matrix(
			ArrayList<ArrayList<Double>> data
			) {
			this.data=data;
	}
	public ArrayList<ArrayList<Double>> getData() {
		return this.data;
	}
	
	public int nrow() {
		return data.size();
	}
	
	public int ncol() {
		return data.get(0).size();
	}
	
	public Matrix dotProduct(Matrix MatrixFactor) {
		ArrayList<ArrayList<Double>> FactorData = MatrixFactor.getData();
		ArrayList<ArrayList<Double>> ResultData = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < nrow(); i++) {
			ResultData.add( new ArrayList<Double>() );
			for (int j = 0; j < MatrixFactor.ncol(); j++) {
				double sum = 0;
				for (int k = 0; k < ncol(); k++) {
					sum += data.get(i).get(k) * FactorData.get(k).get(j);
				}
				ResultData.get(i).add(sum);
			}
		}
			return new Matrix(ResultData);
		
		
	}
	
	public Matrix transpose(){
		ArrayList<ArrayList<Double>> ResultData = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < ncol(); i++) {
			ResultData.add(new ArrayList<Double>());
			for (int j = 0; j < nrow(); j++) {
				ResultData.get(i).add(data.get(j).get(i));
			}
		}
		return new Matrix(ResultData);
		
		
	}
	
	
	public void display() {
		for (ArrayList<Double> vector : data) {
			for (Double element : vector) {
				System.out.print(element.toString() + ", ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	private ArrayList<ArrayList<Double>> data;
	
}
