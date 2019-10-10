import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Timkabor on 11/4/2017.
 */
public class PsoChords {
    private int MAX_LENGTH = 16;				//N number of chords
    private int PARTICLE_COUNT;			//population of particles
    private double V_MAX; 				// Maximum velocity change allowed
    private int MAX_EPOCHS;
    private int TARGET;
    private Tone tone;
    private Random rand;
    private ArrayList<ChordParticle> particles;
    private ArrayList<ChordParticle> solutions;
    private int epoch;

    public PsoChords(Tone tone) {
        this.tone = tone;
        PARTICLE_COUNT = 1000;
        V_MAX = 4;
        MAX_EPOCHS = 10000;
        TARGET = 0;
        epoch = 0;
    }
    public ChordParticle getChordSequence() {
        particles = new ArrayList<>();
        solutions = new ArrayList<>();
        rand = new Random();
        epoch = 0;
        boolean done = false;
        ChordParticle aParticle = null;

        initialize();

        while(epoch<MAX_EPOCHS) {
                for(int i = 0; i < PARTICLE_COUNT; i++)  {
                    aParticle = particles.get(i);
                    aParticle.computeConflicts();
                    if(aParticle.getConflicts() == TARGET){
                        break;
                    }
                }
                Collections.sort(particles); 					// sort particles by their conflicts scores, best to worst.
                ChordParticle t = particles.get(0);
                getVelocity();

                updateParticles();

                epoch++;
        }

        if(epoch == MAX_EPOCHS) {
            System.out.println("chords done");

        }

        for(ChordParticle p: particles) {						//prints the solutions if found within mnc
            if(p.getConflicts() == TARGET)
                return p;
        }

        return null;
    }

    public void initialize() {
        int newParticleIndex = 0;

        for(int i = 0; i < PARTICLE_COUNT; i++) {
            ChordParticle newParticle = new ChordParticle(tone);

            particles.add(newParticle);
            newParticleIndex = particles.indexOf(newParticle);

            particles.get(newParticleIndex).computeConflicts();
        }
    }
    public void getVelocity() {
        double worstResults = 0;
        double vValue = 0.0;
        ChordParticle aParticle = null;

        // after sorting, worst will be last in list.
        worstResults = particles.get(PARTICLE_COUNT - 1).getConflicts();

        for(int i = 0; i < PARTICLE_COUNT; i++) {
            aParticle = particles.get(i);
            vValue = (V_MAX * aParticle.getConflicts()) / worstResults;

            if(vValue > V_MAX)
                aParticle.setVelocity(V_MAX);

            else if(vValue < 0.0)
                aParticle.setVelocity(0.0);

            else
                aParticle.setVelocity(vValue);
        }
    }
    public void change(int index) {
        int positionA = 0;
        ChordParticle thisParticle = particles.get(index);
        Tone t =  thisParticle.getTone();
        int newNote = t.getTonicMidi() + getRandomNumber(0,18);
        boolean conflictFound = false;
        for(int i=0;i<thisParticle.getChords().size();i++)
        {
            int base = thisParticle.getChord(i).getFirstMidiNum();
            if(thisParticle.isNotElegant(base,t)) {
                positionA = i;
                conflictFound = true;
                break;
            }
        }
        if(conflictFound) {
            thisParticle.setBase(positionA,newNote);
            return;
        }
        //CHECK FOR SECOND
        for(int i=0;i<thisParticle.getChords().size();i++)
        {
            int note = thisParticle.getChord(i).getSecondMidiNum();
            if(!thisParticle.isSecondNoteInChordAlright(thisParticle.getChord(i), note)) {
                positionA = i;
                conflictFound = true;
                break;
            }
        }
        if(conflictFound) {
            thisParticle.setSecondNote(positionA, newNote);
            return;
        }
        //CHECK FOR THIRD
        for(int i=0;i<thisParticle.getChords().size();i++)
        {
            int note = thisParticle.getChord(i).getThirdMidiNum();
            if(!thisParticle.isThirdNoteInChordAlright(thisParticle.getChord(i), note)) {
                positionA = i;
                conflictFound = true;
                break;
            }
        }
        if(conflictFound)
            thisParticle.setThirdNote(positionA, newNote);
        //CHECK FOR SIMILARITY
        for(int i=0;i<thisParticle.getChords().size() - 4;i++)
        {
            int first = thisParticle.getChord(i).getThirdMidiNum();
            int second = thisParticle.getChord(i+1).getThirdMidiNum();
            int third = thisParticle.getChord(i+2).getThirdMidiNum();
            int fourth = thisParticle.getChord(i+3).getThirdMidiNum();
            if(first == second && first == third && first == fourth){
                Collections.shuffle(thisParticle.getChords());
            }
        }
    }

    public void updateParticles() {
        ChordParticle source = null;
        ChordParticle destination = null;

        // Best is at index 0, so start from the second best.
        for(int i = 1; i < PARTICLE_COUNT; i++) {
            // The higher the velocity score, the more changes it will need.
            source = particles.get(i-1);
            destination = particles.get(i);

            int changes = (int)Math.floor(Math.abs(destination.getVelocity()));

            for(int j = 0; j < changes; j++) {
                //if(new Random().nextBoolean()) { //exploration
                    change(i);
                //}
                // Push it closer to it's best neighbor.
                copyFromParticle(source, destination); //exploitation
            }

            // Update conflicts value.
            destination.computeConflicts();;
        }
    }
    public void copyFromParticle(ChordParticle best, ChordParticle destination) {
        // push destination's data points closer to source's data points.
        Chord targetA = best.getChord(getRandomNumber(0, MAX_LENGTH - 1)); 			// particle to target.
        Chord targetB = null;
        int indexA = 0;
        int indexB = 0;
        int tempIndex = 0;

        // targetB will be source's neighbor immediately succeeding targetA (circular).
        int i = 0;
        for(; i < MAX_LENGTH; i++) {
            if(best.getChord(i) == targetA) {
                if(i == MAX_LENGTH - 1) {
                    targetB = best.getChord(0); 								// if end of array, take from beginning.
                } else {
                    targetB = best.getChord(i + 1);
                }
                break;
            }
        }

        // Move targetB next to targetA by switching values.
        for(int j = 0; j < MAX_LENGTH; j++) {
            if(destination.getChord(j) == targetA) {
                indexA = j;
            }
            if(destination.getChord(j) == targetB) {
                indexB = j;
            }
        }

        // get temp index succeeding indexA.
        if(indexA == MAX_LENGTH - 1){
            tempIndex = 0;
        }else{
            tempIndex = indexA + 1;
        }

        // Switch indexB value with tempIndex value.
        Chord temp = destination.getChord(tempIndex);
        destination.setChord(tempIndex, destination.getChord(indexB));
        destination.setChord(indexB, temp);

    }
    public int getRandomNumber(int low, int high) {
        return (int)Math.round((high - low) * rand.nextDouble() + low);
    }
}
