import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Timkabor on 11/19/2017.
 */
public class MidiWriterToFile {

    private static final int VELOCITY = 64;         //	This velocity is used for all notes.

    public void writeToMidiFile(String fileName, ArrayList<Chord> chords, int[] melody, int duration) {

        File outputFile = new File(fileName);
        Sequence sequence = null;
        try {
            sequence = new Sequence(Sequence.PPQ, 1);
        } catch (
                InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Track track = sequence.createTrack();
        int start = 0;
        int k = 0;

        for (int i = 0; i < 16; i++)
        {
            // chords on
            track.add(MidiWriterToFile.createNoteOnEvent(chords.get(i).getFirstMidiNum(), start));
            track.add(MidiWriterToFile.createNoteOnEvent(chords.get(i).getSecondMidiNum(), start));
            track.add(MidiWriterToFile.createNoteOnEvent(chords.get(i).getThirdMidiNum(), start));
            // melody first note on & off
            track.add(MidiWriterToFile.createNoteOnEvent(melody[k], start));
            track.add(MidiWriterToFile.createNoteOffEvent(melody[k], start + duration / 2));
            // melody second note on & off
            track.add(MidiWriterToFile.createNoteOnEvent(melody[k+1], start + duration / 2));
            track.add(MidiWriterToFile.createNoteOffEvent(melody[k+1], start + duration));
            k+=2;
            // chords off
            track.add(MidiWriterToFile.createNoteOffEvent(chords.get(i).getFirstMidiNum(), start + duration));
            track.add(MidiWriterToFile.createNoteOffEvent(chords.get(i).getSecondMidiNum(), start + duration));
            track.add(MidiWriterToFile.createNoteOffEvent(chords.get(i).getThirdMidiNum(), start + duration));
            start += duration;
        }

        try {
            MidiSystem.write(sequence, 0, outputFile);
        } catch (
                IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static MidiEvent createNoteOnEvent(int nKey, long lTick) {
        return createNoteEvent(ShortMessage.NOTE_ON, nKey, VELOCITY, lTick);
    }

    private static MidiEvent createNoteOffEvent(int nKey, long lTick) {
        return createNoteEvent(ShortMessage.NOTE_OFF, nKey, 0, lTick);
    }

    private static MidiEvent createNoteEvent(int nCommand, int nKey, int nVelocity, long lTick) {
        ShortMessage message = new ShortMessage();
        try {
            message.setMessage(nCommand, 0, nKey, nVelocity);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }
        MidiEvent event = new MidiEvent(message, lTick);
        return event;
    }

}

