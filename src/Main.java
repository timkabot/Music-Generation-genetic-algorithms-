import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException, InvalidMidiDataException, IOException {
	// write your code here
        long startTime = System.nanoTime();

        MyChords chords = new MyChords();
        PsoChords pso = new PsoChords(chords.getTonality());
        ChordParticle chordSequence = pso.getChordSequence();

        Player player = new Player(chordSequence);
        PSONotes psoNotes = new PSONotes(chords.getTonality(),chordSequence);
        NotesParticle notes = psoNotes.getNoteSequence();

        player.makeNoteSequence(notes);
        player.playChords();
        long endTime = System.nanoTime();

        double duration = (endTime - startTime)/1000000;

        System.out.println("Full time is " + duration);

    }
}
