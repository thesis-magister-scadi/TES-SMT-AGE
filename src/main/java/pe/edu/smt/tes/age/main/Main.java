package pe.edu.smt.tes.age.main;

import java.io.File;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

public class Main {

	public static final Integer MAX_EVOLUTION_ALLOW = 2200;
	public static final Integer NUM_FEATURES = 205;
	public static final Integer INITIAL_POPULATION = 300;

	public static void main(String args[]) throws Exception {
//		String crde = "0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1";
//		//String cr = "1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0";
//		String[] crt = crde.split(",");
//		int c=0;
//		for(int i=0; i<crt.length; i++) {
//			if(crt[i].trim().equals("1")) {
//				c++;
//			}
//		}
//		System.out.println(c);
		selectFeatures(100);
		
	}
	
	public static void selectFeatures(int accuracy) throws Exception {
		Configuration conf = new DefaultConfiguration();

		conf.setPreservFittestIndividual(true);

		FitnessFunction function = new MyFunctionAptitude(accuracy);
		conf.setFitnessFunction(function);

		Gene[] sampleGenes = new Gene[NUM_FEATURES];
		for (int i = 0; i < NUM_FEATURES; i++) {
			sampleGenes[i] = new IntegerGene(conf, 0, 1);
		}
		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		
		conf.setPopulationSize(INITIAL_POPULATION);
		
		Genotype population;
		population = Genotype.randomInitialGenotype(conf);
		
		long initialTime = System.currentTimeMillis();
		for(int i=0; i<MAX_EVOLUTION_ALLOW; i++) {
			System.out.println("evolucion -> "+i);
			population.evolve();
		}
		long finalTime = System.currentTimeMillis();
		
		System.out.println("Tiempo total de evolucion: "+(finalTime - initialTime)+" ms");
		
		savePopulation(population);
		
		IChromosome betterCromosome = population.getFittestChromosome();
		System.out.println("El cromosoma mas apto encontrado tiene un valor de aptitud de: "+betterCromosome.getFitnessValue());
		System.out.println("Y esta formado por la siguiente distribucion: ");
	}
	
	public static void savePopulation(Genotype population) throws Exception {
		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc = builder.representGenotypeAsDocument(population);
		XMLDocumentBuilder docBuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docBuilder.buildDocument(doc);
		XMLManager.writeFile(xmlDoc, new File("poblacion.xml"));
	}
	
}
