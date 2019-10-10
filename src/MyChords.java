import java.util.*;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.*;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

/**
 * Created by Timkabor on 11/4/2017.
 */
public class MyChords {
    private Chord tonic, //1 ступень лада
            dominant,  //4 ступень лада
            subdominant; // 5 ступень лада
    private Tone tonality;
    public MyChords() {
        tonality = new Tone();
    }

    public Chord getTonic() {
        return tonic;
    }

    public Chord getDominant() {
        return dominant;
    }

    public Chord getSubdominant() {
        return subdominant;
    }

    public Tone getTonality() {
        return tonality;
    }
}