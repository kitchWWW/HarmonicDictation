import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.DocFlavor.URL;


public class Incrementer<T> {

	ArrayList<T> opts;	//options
	ArrayList<ArrayList<Double>> probs;	//likleyhooods, doubles, each sub array MUST add up to one.
	
	public Incrementer(ArrayList<T> options, ArrayList<ArrayList<Double>> likleyhoods){
		opts = options;
		probs = likleyhoods;
		//do some error checking on likleyhoods
	}
	
	public Incrementer(String OptionsFileName, String likleyhoodsFileName){
		loadOptions(OptionsFileName);
		loadProbs(likleyhoodsFileName);
		//System.out.println(likleyhoodsFileName);
		//System.out.println(probs);
	}
	
	private void loadOptions(String optionsFilename) {
		opts = (ArrayList<T>) loadCSV(optionsFilename);
	}

	private void loadProbs(String probsFilename){
		probs = loadCSV(probsFilename);
		//probs = equalize(probs);
	}
	
	private ArrayList<ArrayList<Double>> equalize( ArrayList<ArrayList<Double>> probs){
		ArrayList<ArrayList<Double>> equalized = new ArrayList<>();
		for(ArrayList<Double> row : probs){
			ArrayList<Double> newRow = new ArrayList<Double>();
			Double sum = 0.0;
			for(Double d : row){
				sum +=d;
			}
			for(Double d : row){
				newRow.add(d/sum);
			}
			equalized.add(newRow);
		}
		return equalized;
	}

	private ArrayList<ArrayList<Double>> loadCSV(String filename) {
		ArrayList<ArrayList<Double>> data = new ArrayList<>();
		try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;
            while ((str = in.readLine()) != null) {
            	data.add(new ArrayList<Double>());
            	String[] ar =str.split(",");
            	for(int i = 0; i<ar.length; i++){
            		data.get(data.size()-1).add(Double.parseDouble(ar[i]));
            	}
            }
            in.close();
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println("File Read Error: "+filename);
        }
		return data;
	}

	public int getNext(int current){
		double ret = Math.random();
		double sum = 0;
		ArrayList<Double> next = probs.get(current);
		for(int i = 0; i<next.size(); i++){
			sum+= next.get(i);
			if(ret<sum){
				return i;
			}
		}
		return -1;
	}
	
	public T getState(int state){
		return opts.get(state);
	}
	
}
