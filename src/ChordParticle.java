import java.util.ArrayList;

import java.util.Random;

/**
 * Created by Timkabor on 11/4/2017.
 */
public class ChordParticle implements Comparable<ChordParticle>{
    private int NUMBER_OF_CHORDS = 16;
    private int NUMBER_OF_NOTES = 48;
    private ArrayList<Chord> chords;
    private double velocity;
    private int conflicts;
    private int octaveNumber;
    private Tone tone;
    public ChordParticle(Tone tonality) {
        this.velocity = 0.0;
        this.conflicts = 0;
        tone = tonality;
        this.octaveNumber = tone.getOctaveNum();
        chords = new ArrayList<>();
        initData();
    }
    public void setChord(int i, Chord c){
        chords.set(i,c);
    }
    public void setBase(int i,int newBase){
        chords.get(i).setFirstMidiNum(newBase);
    }
    public void setSecondNote(int i,int newNote){
        chords.get(i).setSecondMidiNum(newNote);
    }
    public void setThirdNote(int i,int newNote){
        chords.get(i).setThirdMidiNum(newNote);
    }
    public Chord getChord(int index)  {
        return this.chords.get(index);
    }
    public boolean isNotElegant(int base, Tone tone){
        return base != tone.getTonicMidi() && base != tone.getDominantMidi() && base != tone.getSubdominantMidi();
    }
    public boolean isSecondNoteInChordAlright(Chord c, int note){
        boolean major = tone.isMajor();
        int dif = 3;
        if(major) dif = 4;
        return  (note - c.getFirstMidiNum()) == dif;
    }
    public boolean isThirdNoteInChordAlright(Chord c, int note){
        int dif = 7;
        return  (note - c.getFirstMidiNum()) == dif;
    }
    private void checkFitness() {
        checkBase();
        //check for second part of chord
        for (int i = 0; i < NUMBER_OF_CHORDS; i++) {
            int secondNote = chords.get(i).getSecondMidiNum();
            if (!isSecondNoteInChordAlright(chords.get(i),secondNote))
                conflicts++;
        }
        //check for third part of chord
        for (int i = 0; i < NUMBER_OF_CHORDS; i++) {
            int thirdNote = chords.get(i).getThirdMidiNum();
            if (!isThirdNoteInChordAlright(chords.get(i),thirdNote))
                conflicts++;
        }
        checkRepitition();
    }
    private void checkBase(){
        //check for bases
        for (int i = 0; i < NUMBER_OF_CHORDS; i++) {
            int chordBase = chords.get(i).getFirstMidiNum();
            if (isNotElegant(chordBase,tone))
                conflicts++;
        }
    }

    public void computeConflicts() {
        conflicts = 0;
        checkFitness();
    }
    public void initData() {
        for (int i = 0; i < NUMBER_OF_CHORDS; i++)
            chords.add( new Chord(tone) );
    }

    public int getConflicts() {
        return conflicts;
    }

    private void checkRepitition() {
        for (int i = 0; i < NUMBER_OF_CHORDS - 4; i++) {
            if(chords.get(i).getFirstMidiNum() == chords.get(i+1).getFirstMidiNum()
                    && chords.get(i).getFirstMidiNum() == chords.get(i+2).getFirstMidiNum()
                    && chords.get(i).getFirstMidiNum() == chords.get(i+3).getFirstMidiNum()
                    && chords.get(i).getFirstMidiNum() == chords.get(i+4).getFirstMidiNum()
                    )
                conflicts++;
        }
    }
    @Override
    public int compareTo(ChordParticle o) {
        return this.getConflicts() - o.getConflicts();
    }
    public double getVelocity()  {
        return this.velocity;
    }

    public void setVelocity(double velocityScore) {
        this.velocity = velocityScore;
    }

    public ArrayList<Chord> getChords() {
        return chords;
    }

    public int getOctaveNumber() {
        return octaveNumber;
    }

    public Tone getTone() {
        return tone;
    }

}
