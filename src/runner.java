import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class runner {

	static int KEY = 4;
	static boolean MAJOR = true;//	(Math.random()>.5);
	static int BAR_SIZE = 4;
	static boolean SHARP_KEY = false;
	static double JUMP_PROB = .8; //(JUMP if Math.Rand() > JumpProb)
	static int[] sharps = {3,-2,5,0,7,2,-3,4,-1,6,1,-4};
	
	public static void main(String[] args) {
		while(KEY == 4 || KEY== 2 ||KEY== 2 ||KEY== 8 || KEY==9|| KEY== 11){
			KEY = (int) (Math.random()*12);	
		}

		if(sharps[KEY] - ((MAJOR)?0:3) >0 ){
			SHARP_KEY = true;
		}
		
		ArrayList<Chord> chordStructure = new ArrayList<>();
		do{
			System.out.println("Key: "+ noteToString(KEY,SHARP_KEY)+" "+ (MAJOR?"M":"m"));
			//generate rythems
			ArrayList<ArrayList<Double>> rythms = generatePhrase(2,"inc/rythmOpts.csv","inc/rythmProbs.csv");
			//System.out.println(rythms);
			//generate chord series
			ArrayList<ArrayList<Double>> chords = generatePhrase(rythms.size()*4,"inc/ComplexChordsOpts.csv","inc/ComplexChordsProbs.csv");
			//put the two together into chords
			chordStructure = buildChords(KEY,MAJOR,chords,rythms);
			//trims off ending to make it close nicely
			makePresentable(chordStructure);
		}while(chordStructure.size()<8 && chordStructure.size()>4);

		//Finish filling out the chords with the smoothest possible voice leading
		fleshChords(chordStructure,(Math.random()>.5?0:1));
		
		ArrayList<Integer> bassLine = buildBassLine(chordStructure);


		//form the chords into four lines (String Quartet?)
		ArrayList<String> parts = chordsToParts(chordStructure);
		
		//System.out.println(parts);

		//output the stuff to lilypond
		produceLilypond(parts,"output/output.ly");
		
	}

	private static ArrayList<Integer> buildBassLine(ArrayList<Chord> chordStructure){
		ArrayList<Integer> bassLine = new ArrayList<>();
		for(int i = 0; i<chordStructure.size(); i++){
			Chord c  = chordStructure.get(i);
			int bassNote = c.tones[c.inversion];
			bassLine.add(bassNote);
		}
		return addOctavesBass(bassLine);
	}

	private static ArrayList<Integer> addOctavesBass(ArrayList<Integer> bassNotes){
		for(int i = 0; i< bassNotes.size(); i++){
			int res = bassNotes.get(i) - KEY;
			if(res < 0){res += 12;}
			bassNotes.set(i,res);
		}
		System.out.println(bassNotes);
		ArrayList<Integer> bassLine = new ArrayList<>();
		bassLine.add(bassNotes.get(0));
		for(int i = 1; i<bassNotes.size(); i ++){
			int note = bassNotes.get(i);
			int prevNote = bassLine.get(i-1);
			if(prevNote > note+6){
				bassLine.add(note+12);
			}else if(prevNote < note-6){
				bassLine.add(note-12);
			}else{
				bassLine.add(note);
			}
		}
		System.out.println(bassLine);
		return bassLine;
	}

	private static void makePresentable(ArrayList<Chord> chordStructure){
		//trim off before the cadence
		for(int i = chordStructure.size()-1; i>0; i--){
			if(chordStructure.get(i).root!=KEY || chordStructure.get(i).inversion>0){
				chordStructure.remove(i);
			}else{
				break;
			}
		}
		//make it end nice
		int leftover = chordStructure.size()%4;
		if(leftover == 1){
			chordStructure.get(chordStructure.size()-1).duration = 4;
		}else if(leftover == 2){
			chordStructure.get(chordStructure.size()-2).duration = 2;
			chordStructure.get(chordStructure.size()-1).duration = 2;
		}else if(leftover == 3){
			chordStructure.get(chordStructure.size()-3).duration = 2;
			chordStructure.get(chordStructure.size()-2).duration = 2;
			chordStructure.get(chordStructure.size()-1).duration = 4;
		}else if(leftover == 0){
			chordStructure.get(chordStructure.size()-2).duration = 2;
			chordStructure.get(chordStructure.size()-1).duration = 4;
		}
	}

	private static void produceLilypond(ArrayList<String> lines, String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.println("%Hello");
			writer.println("\\header { tagline = \"\" } upper = \\relative c'' { \\clef treble \\key");
			writer.println(" "+noteToString(KEY,SHARP_KEY) +" \\major \\time 4/4");
			writer.println(lines.get(0));	//treble
			writer.println("} lower = \\relative c { \\clef bass \\key");
			writer.println(" "+noteToString(KEY,SHARP_KEY) +" \\major \\time 4/4");
			writer.println(lines.get(1));	//bass
			writer.println(" \\bar \"|.\"  } \\score { \\new PianoStaff << \\set PianoStaff.instrumentName = #\"  \" \\new Staff = \"upper\" \\upper    \\new Staff = \"lower\" \\lower  >>  \\layout { }  \\midi { }}");
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("I guess we give up now...");
		}
	}


	private static void fleshChords(ArrayList<Chord> chordStructure, int startingSaprano) {
		ArrayList<Chord> cs = chordStructure;
		cs.get(0).voices = new int[2];
		cs.get(0).voices[0] = cs.get(0).tones[cs.get(0).inversion];
		cs.get(0).voices[1] = cs.get(0).tones[startingSaprano];
		for(int i = 1; i<chordStructure.size(); i++){
			Chord cur = cs.get(i);
			Chord prev = cs.get(i-1);
			boolean jumps = i >2 && i < chordStructure.size()-3;
			fleshGiven(cur,prev,i%4,jumps,true);
		}
	}

	
	private static void fleshGiven(Chord cur, Chord prev,int beat, boolean jumps, boolean decend) {
		cur.voices[0] = cur.tones[cur.inversion];
		int prevSap = prev.voices[1];
		boolean jump = (Math.random()>JUMP_PROB);
		if(prev.inversion>1){jump = false;}
		if(cur.tones[0] == prev.tones[0]){jump =true;}
		if(!jumps){jump = false;}
		for(int i = 0; i< cur.tones.length; i++){
			int curSap = cur.tones[i];
			if(decend){
				curSap = cur.tones[cur.tones.length - i -1];
			}
			int diff = getDiff(curSap, prevSap);
			if(!jump){
				if(diff < 3){
					cur.voices[1] = curSap;
				}
			}else {
				if(diff > 3 && diff < 8){
					cur.voices[1] = curSap;
					System.out.println("Jumping!");
				}
			}
			
		}

		//System.out.println(cur +" "+prev);
		return;
	}

	//one = cur, two = prev
	private static int getDiff(int one, int two){
		int diff = one - two;
		if(diff < 0){
			diff += 12;
		}
		if(diff > 6){
			diff = 12 - diff;
		}
		return diff;
	}
	
	private static ArrayList<String> chordsToParts( ArrayList<Chord> chordStructure) {
		ArrayList<String> lines= new ArrayList<>();
		//Bass
		String bass = "";
		String rh = "";
		String octaveCheck = "";
		String message = "";
		int bassN = 3;
		int prevBassN = 3;
		int octaveChecks = 0;
		for(int i = 0; i<chordStructure.size(); i++){
			message = "";
			Chord c = chordStructure.get(i);
			bassN = c.tones[c.inversion];
			if(i>3){
				if(chordStructure.get(i-1).tones[0]==KEY
					&& prevBassN == KEY
					&& octaveChecks < 1){
					octaveCheck = ",";
					message = "^\"OC!\"";
					octaveChecks++;
				}else{
					octaveCheck = "";
				}
			}
			if(i == chordStructure.size()-1 &&	//last chord && prev is 5
				chordStructure.get(i-1).tones[0]==(KEY+7)%12){
				//if this is the final 1 chord
				octaveCheck = (Math.random()>.5?"":",");
			}
			bass += " "+noteToString(bassN,SHARP_KEY)+octaveCheck+durToLily(c.duration)+message;
			
			rh += " <"
				+" "+ fillDown(c) +" "

				+">"+durToLily(c.duration);
			prevBassN = bassN;
		}

		

		lines.add(rh);
		lines.add(bass);
		return lines;
	}

	private static String fillDown(Chord c){
		String ret = "";
		ret += noteToString(c.voices[1],SHARP_KEY);
		System.out.println("HI: "+ret);
		for(int i = 1; i<12; i ++){
			int pobNote = c.voices[1] - i;
			if(pobNote<0){pobNote+=12;}
			boolean added = false;
			for(int t = 0; t<c.tones.length; t++){
				if(!added && c.tones[t] == pobNote){
					ret+= " " + noteToString(pobNote, SHARP_KEY);
					//System.out.println("Adding note: "+ ret);
					//System.out.println(" "+c.tones[t]);
					added = true;
				}
			}
		}
		//System.out.println("Filling down: "+ret);
		return ret;
	}


	private static String durToLily(int duration) {
		switch(duration){
		case 1 : return "4";
		case 2 : return "2";
		case 3 : return "2.";
		case 4 : return "1";
		}
		return null;
	}

	public static String noteToString(int number, boolean sharpKey){
		number = number%12;
		if(sharpKey){
			String[] names = {"a","ais","b","c","cis","d","dis","e","f","fis","g","gis"};
			return names[number];
		}else{
			String[] names = {"a","bes","b","c","des","d","ees","e","f","bes","g","aes"};
			return names[number];
		}
	}
	
	private static ArrayList<Chord> buildChords(int key, boolean major, ArrayList<ArrayList<Double>> chords, ArrayList<ArrayList<Double>> rythms) {
		//System.out.println("Building Chords");
		ArrayList<Chord> structure = new ArrayList<>();
		int c = 5;
		for(int i = 0; i < chords.size(); i ++){
			ArrayList<Double> tc = chords.get(i);
			int root = 		(int) tc.get(1).intValue() + KEY;
			int inversion = (int) tc.get(2).intValue();
			//System.out.println("Hellop: "+inversion);
			int quality = 	(int) tc.get(3).intValue();
			int add = 		(int) tc.get(4).intValue();
			int duration = 	1;
			Chord newChord = new Chord(root, quality,inversion,add,duration,4);
			structure.add(newChord);
		}
		return structure;
	}

	private static ArrayList<ArrayList<Double>> generatePhrase(int length,String optionsFile, String probsFile) {
		ArrayList<ArrayList<Double>> bigPhrase = new ArrayList<>();
		Incrementer<ArrayList<Double>> inc = new Incrementer<>(optionsFile,probsFile);
		int cs = 0;
		bigPhrase.add(inc.getState(cs));
		for(int i = 0; i<length;i++){
			cs = inc.getNext(cs);
			bigPhrase.add(inc.getState(cs));
		}
		return bigPhrase;
	}

}
