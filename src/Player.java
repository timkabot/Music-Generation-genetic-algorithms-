
import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Timkabor on 11/4/2017.
 */
public class Player {
    private static final int	VELOCITY = 64;

    private  int tempo = 120;
    private MidiChannel[] channels = null;
    private Synthesizer synth = null;
    private ArrayList<Chord> chordSequence;
    private int[] noteSequence;
    private MidiWriterToFile midiWriter;
    public void makeNoteSequence(NotesParticle p) {
        noteSequence = p.getNotes();
        midiWriter.writeToMidiFile("song.mid",chordSequence,noteSequence,2);
    }
    public Player(ChordParticle p) throws MidiUnavailableException, InvalidMidiDataException {
        synth = MidiSystem.getSynthesizer();
        channels = synth.getChannels();
        channels[0].programChange(1);
        midiWriter = new MidiWriterToFile();
        makeChordSequence(p);
    }
    public void playChords() throws InterruptedException, IOException {
        int k = 0;

        System.out.println(chordSequence.size());
        for(int i=0;i<chordSequence.size();i++){
            int[] g = new int[2];
            g[0] = noteSequence[k];
            g[1] = noteSequence[k+1] ;
            k+=2;
            int first = chordSequence.get(i).getFirstMidiNum(),
                    second = chordSequence.get(i).getSecondMidiNum(),
                    third = chordSequence.get(i).getThirdMidiNum();
            //playSound(0, 1000, 40,g, first, second, third);

        }
    }
    public void makeChordSequence(ChordParticle p) {
        chordSequence = p.getChords();

    }
    public void playNotes(int first, int second,int duration) throws InterruptedException {
        channels[1].noteOn(first, 40);
        Thread.sleep(duration/2);
        channels[1].noteOff(first, 40);
        channels[1].noteOn(second, 40);
        Thread.sleep(duration/2);
        channels[1].noteOff(second, 40);

    }
    public void playSound(int channel, int duration, int volume,int[] ara, int... notes) throws InterruptedException {

        for (int note : notes) {
            channels[channel].noteOn(note, volume);
            System.out.print( note + " ");
        }
        playNotes(ara[0],ara[1],duration);
        for (int note : notes) {
            channels[channel].noteOff(note);

        }
    }
    public void close() {
        synth.close();
    }
    private static MidiEvent createNoteOnEvent(int nKey, long lTick)
    {
        return createNoteEvent(ShortMessage.NOTE_ON,
                nKey,
                VELOCITY,
                lTick);
    }



    private static MidiEvent createNoteOffEvent(int nKey, long lTick)
    {
        return createNoteEvent(ShortMessage.NOTE_OFF,
                nKey,
                0,
                lTick);
    }
    private static MidiEvent createNoteEvent(int nCommand,
                                             int nKey,
                                             int nVelocity,
                                             long lTick)
    {
        ShortMessage	message = new ShortMessage();
        try
        {
            message.setMessage(nCommand,
                    0,	// always on channel 1
                    nKey,
                    nVelocity);
        }
        catch (InvalidMidiDataException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        MidiEvent	event = new MidiEvent(message,
                lTick);
        return event;
    }
}
