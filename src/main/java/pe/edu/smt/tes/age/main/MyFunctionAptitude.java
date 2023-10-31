package pe.edu.smt.tes.age.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import pe.edu.smt.tes.rbc.main.RBCMain;

public class MyFunctionAptitude extends FitnessFunction {


	private static final long serialVersionUID = 1L;

//	private final int accuracyTarget;
	private static final int NUMBER_FEATURES = 205;
	private static final int MAX_ACCURACY = 100;
	
	public MyFunctionAptitude(int accuracy) throws Exception {
		if(accuracy<80 && accuracy>MAX_ACCURACY) {
			throw new IllegalArgumentException("El precision debe ser un numero entre 80 y "+MAX_ACCURACY+"%");
		}
		//accuracyTarget = accuracy;
	}


	@Override
	protected double evaluate(IChromosome cromosome) {
		double result = 0.0;
		try {
			int[] cromosomeInt = convertCromosome(cromosome);
			result = RBCMain.testCrossValidation(cromosomeInt);
			System.out.println("Result "+result);
			System.out.println(Arrays.toString(cromosomeInt));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private int[] convertCromosome(IChromosome cromosome) {
		int[] cromosomeInt = new int[NUMBER_FEATURES];
		for(int i=0; i<NUMBER_FEATURES; i++) {
			cromosomeInt[i] = (int) cromosome.getGene(i).getAllele();
		}
		return cromosomeInt;
	}

}
