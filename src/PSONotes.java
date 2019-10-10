import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Timkabor on 11/4/2017.
 */
public class PSONotes {
    private int MAX_LENGTH = 32;				//N number notes
    private int PARTICLE_COUNT;			//population of particles
    private double V_MAX; 				// Maximum velocity change allowed
    private int MAX_EPOCHS;

    private Tone tone;
    private Random rand;
    private ArrayList<NotesParticle> particles;
    private ArrayList<NotesParticle> solutions;
    private int epoch;
    private ChordParticle chordParticle;

    public PSONotes(Tone tone, ChordParticle chordParticle) {
        this.tone = tone;
        PARTICLE_COUNT = 750;
        V_MAX = 4;
        MAX_EPOCHS = 12200;
        epoch = 0;
        this.chordParticle = chordParticle;
    }
    public NotesParticle getNoteSequence() {
        particles = new ArrayList<>();
        solutions = new ArrayList<>();
        rand = new Random();
        epoch = 0;

        initialize();

        while(epoch<MAX_EPOCHS) {

                Collections.sort(particles); 					// sort particles by their conflicts scores, best to worst.

                NotesParticle t = particles.get(0);

                getVelocity();

                updateParticles();

                epoch++;
            System.out.println(particles.get(0).getFitness());
        }

        System.out.println("notes done");
        return particles.get(0);
    }

    public void initialize() {
        int newParticleIndex = 0;

        for(int i = 0; i < PARTICLE_COUNT; i++) {
            NotesParticle newParticle = new NotesParticle(tone, chordParticle);

            particles.add(newParticle);
            newParticleIndex = particles.indexOf(newParticle);

            particles.get(newParticleIndex).getFitness();
        }
    }
    public void getVelocity() {
        double worstResults = 0;
        double vValue = 0.0;
        NotesParticle aParticle = null;

        // after sorting, worst will be last in list.
        worstResults = particles.get(particles.size()-1).getFitness();

        for(int i = 0; i < PARTICLE_COUNT; i++) {
            aParticle = particles.get(i);
            vValue = (V_MAX * aParticle.getFitness()) / worstResults;

            if(vValue > V_MAX)
                aParticle.setVelocity(V_MAX);

            else if(vValue < 0.0)
                aParticle.setVelocity(0.0);

            else
                aParticle.setVelocity(vValue);
        }
    }
    public void change(int index) { //randomly changes 2 notes in
        int positionA = 0;
        int positionB = getRandomNumber(0, 7);
        NotesParticle thisParticle = particles.get(index);
        while(true)
        {
            positionA = new Random().nextInt(32);
            if(positionA%2!=0)break;
        }
        thisParticle.setNote(positionA, thisParticle.getTone().getMidiNotes().get(positionB) + 12);
    }

    public void updateParticles() {
        NotesParticle source = null;
        NotesParticle destination = null;

        // Best is at index 0, so start from the second best.
        for(int i = 1; i < PARTICLE_COUNT; i++) {
            // The higher the velocity score, the more changes it will need.
            source = particles.get(i-1);
            destination = particles.get(i);

            int changes = (int)Math.floor(Math.abs(destination.getVelocity()));

            for(int j = 0; j < changes; j++) {
                if(new Random().nextBoolean()) { //exploration
                    change(i);
                }
                // Push it closer to it's best neighbor.
                copyFromParticle(source, destination); //exploitation
            }

            // Update conflicts value.
            destination.getFitness();;
        }
    }
    public void copyFromParticle(NotesParticle best, NotesParticle destination) {
        // push destination's data points closer to source's data points.
        int targetA = getRandomNumber(0, MAX_LENGTH - 1); 					// particle to target.
        int targetB = 0;
        int indexA = 0;
        int indexB = 0;
        int tempIndex = 0;

        // targetB will be source's neighbor immediately succeeding targetA (circular).
        int i = 0;
        for(; i < MAX_LENGTH; i++) {
            if(best.getNote(i) == targetA) {
                if(i == MAX_LENGTH - 1) {
                    targetB = best.getNote(0); 								// if end of array, take from beginning.
                } else {
                    targetB = best.getNote(i + 1);
                }
                break;
            }
        }

        // Move targetB next to targetA by switching values.
        for(int j = 0; j < MAX_LENGTH; j++) {
            if(destination.getNote(j) == targetA) {
                indexA = j;
            }
            if(destination.getNote(j) == targetB) {
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
        int temp = destination.getNote(tempIndex);
        destination.setNote(tempIndex, destination.getNote(indexB));
        destination.setNote(indexB, temp);

    }
    public int getRandomNumber(int low, int high) {
        return (int)Math.round((high - low) * rand.nextDouble() + low);
    }
}
