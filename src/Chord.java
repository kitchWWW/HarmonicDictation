
public class Chord {
	int root;			//root of chord note, 0-11
	int quality;		//0: Major
						//1: Minor
						//2: Dim
						//3: Aug
	int inversion;		//0: Root is bass
						//1: 1st inversion
						//2: 2nd inversion
						//3: 3rd inversion (used for 7ths)
	int duration;		//beats of duration
	
	int[] voices;
	int[] tones;		//the notes that are part of the chord. Always in order
	
	
	public Chord(int root, int quality, int inversion, int add, int duration, int voices) {
		this.root = root;
		this.quality = quality;
		this.duration = duration;
		this.voices = new int[voices];
		this.tones = new int[4];
		this.inversion = inversion;

		if(quality == 0){	//major
			this.tones[0] = root;
			this.tones[1] = (root+4)%12;
			this.tones[2] = (root+7)%12;
		}
		if(quality == 1) {	//minor
			this.tones[0] = root;
			this.tones[1] = (root+3)%12;
			this.tones[2] = (root+7)%12;
		}
		if(quality== 2){	//Dim
			this.tones[0] = root;
			this.tones[1] = (root+3)%12;
			this.tones[2] = (root+6)%12;
		}
		if(quality== 3){	//Aug
			this.tones[0] = root;
			this.tones[1] = (root+4)%12;
			this.tones[2] = (root+8)%12;
		}
		this.tones[3] = (root + add) % 12;
		//System.out.println("New Chord! "+root+" "+quality+" "+inversion);
		//for(int i = 0; i< tones.length; i ++){
		//	System.out.println("T:"+i+" "+tones[i]);
		//}
	}
	
	public String toString(){
		String ret = "("+runner.noteToString(root,false)+" "+ quality + " "+inversion+" -";
		for(int i = 0; i< voices.length; i ++){
			ret+=runner.noteToString(voices[i],false)+" ";
		}
		ret+=")";
		return ret;
	}
	
}
