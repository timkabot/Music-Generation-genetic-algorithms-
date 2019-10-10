import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Timkabor on 10/20/2017.
 */
public class Tone {
    private Note base;
    private boolean major;
    private Note tonic, dominant, subdominant;
    private int tonicMidi, dominantMidi, subdominantMidi;
    private Octave octave;
    private int octaveNum = 2;
    private ArrayList<Integer> midiNotes;
    public Tone(Note base, boolean major) {
        this.base = base;
        this.major = major;
    }
    public Tone(){
        base = getRandomNote();
        major = generateRandomTonality();
        octave = Octave.FOURTH;
        midiNotes = new ArrayList<>();
        setTonic();
        setNotes();
    }
    private void setTonic(){
        tonic = base;
        tonicMidi = MIDI.getMidiByNote(base, MIDI.getOctaveNumber(octave.toString()));
        dominantMidi = tonicMidi + 7;
        subdominantMidi = major ? (tonicMidi + 9) : (tonicMidi + 8);
        System.out.print(tonicMidi+ " " + dominantMidi + " " + subdominantMidi + "\n") ;
    }
    private Note getRandomNote() {
        int pick = new Random().nextInt(Note.values().length);
        return Note.values()[pick];
    }
    boolean generateRandomTonality(){
        int pick = new Random().nextInt(2);
        return pick > 0;
    }
    private void setNotes(){
        int[] majorIntervals =  {2,4,5,7,9,11,12};
        int[] minorIntervals =  {2,3,5,7,8,10,12};
        midiNotes.add(tonicMidi);
        if(major)
            for(int i=0; i < 7;i++) midiNotes.add(tonicMidi + majorIntervals[i]);
        else
            for(int i=0; i < 7;i++) midiNotes.add(tonicMidi + minorIntervals[i]);
    }

    public Note getBase() {
        return base;
    }

    public ArrayList<Integer> getMidiNotes() {
        return midiNotes;
    }

    public Note getTonic() {
        return tonic;
    }

    public Note getDominant() {
        return dominant;
    }

    public Note getSubdominant() {
        return subdominant;
    }

    public int getTonicMidi() {
        return tonicMidi;
    }

    public int getDominantMidi() {
        return dominantMidi;
    }

    public int getSubdominantMidi() {
        return subdominantMidi;
    }

    public Octave getOctave() {
        return octave;
    }

    public int getOctaveNum() {
        return octaveNum;
    }

    public boolean isMajor() {
        return major;
    }
}
