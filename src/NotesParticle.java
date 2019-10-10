import java.util.Random;

/**
 * Created by Timkabor on 11/4/2017.
 */
public class NotesParticle implements Comparable<NotesParticle> {
    private int NUMBER_OF_NOTES = 32;
    private int notes[];
    private double velocity;
    private int octaveNumber;
    private Tone tone;
    private ChordParticle chordParticle;
    private double fitness;
    Random random;
    public NotesParticle(Tone tonality, ChordParticle chordParticle) {
        this.velocity = 0.0;
        random = new Random();
        tone = tonality;
        this.octaveNumber = tone.getOctaveNum();
        notes = new int[NUMBER_OF_NOTES];
        this.chordParticle = chordParticle;
        initData();

    }
    public void setNote(int index, int value) {
        this.notes[index] = value;
    }
    public int getNote(int index)  {
        return this.notes[index];
    }

    public int[] getNotes() {
        return notes;
    }

    public void initData() {
       for (int i = 0; i < NUMBER_OF_NOTES; i++) {
           notes[i] = getRandomNote(i);
        }
    }
    public double getFitness() {
        calculateFitness();
        return fitness;
    }

    public void calculateFitness() {
        fitness = 0;
        for(int i=0;i<NUMBER_OF_NOTES;i+=2)
        {
            if(chordParticle.getChords().get(i/2).getFirstMidiNum() != notes[i])
            {
                fitness += 0.01;
            }
        }
        double dif = 0;
        for(int i=1;i<NUMBER_OF_NOTES;i++){
            dif += Math.abs(notes[i] - notes[i-1]);
        }
        dif/=NUMBER_OF_NOTES;
        dif = Math.abs(1 - dif);

        fitness += (1/(dif + 0.000001));
    }

    public double getVelocity()  {
        return this.velocity;
    }

    /* Sets the velocity of the particle.
	 *
	 * @param: new velocity of particle
	 */
    private int getRandomNote(int index)
    {
        if(index%2 == 0){
            return chordParticle.getChord(index/2).getFirstMidiNum() + 12;
        }
        return tone.getMidiNotes().get(random.nextInt(8)) + 12 ;
    }
    public void setVelocity(double velocityScore) {
        this.velocity = velocityScore;
    }

    public int getOctaveNumber() {
        return octaveNumber;
    }

    public Tone getTone() {
        return tone;
    }

    @Override
    public int compareTo(NotesParticle o) {
        if(o.getFitness()> this.getFitness()) return -1;
        if(o.getFitness() < this.getFitness()) return 1;
        return 0;

    }
}
