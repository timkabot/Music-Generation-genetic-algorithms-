import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Timkabor on 10/20/2017.
 */
public class Chord {
        //Троезвучие
        private int firstMidiNum, secondMidiNum, thirdMidiNum;
        private Note firstNote, secondNote, thirdNote;
        //Generates random chord in given tonality
        public Chord(Tone tone) {
                Random random = new Random();
                setFirstMidiNum(tone.getMidiNotes().get(random.nextInt(8)));
                setSecondMidiNum(tone.getMidiNotes().get(random.nextInt(8)));
                setThirdMidiNum(tone.getMidiNotes().get(random.nextInt(8)));

        }
        public Chord(int firstMidiNum, int secondMidiNum, int thirdMidiNum) {
                this.firstMidiNum = firstMidiNum;
                this.secondMidiNum = secondMidiNum;
                this.thirdMidiNum = thirdMidiNum;


        }

        public Chord(Note firstNote, Note secondNote, Note thirdNote) {
                this.firstNote = firstNote;
                this.secondNote = secondNote;
                this.thirdNote = thirdNote;

        }

        public Chord(int base, boolean major) {
                int sum1 = 3, sum2 = 4;
                if(major) { sum1 = 4; sum2 = 3; }
                this.firstMidiNum  = base;
                this.secondMidiNum = firstMidiNum + sum1;
                this.thirdMidiNum  = secondMidiNum + sum2;

        }

        public int getFirstMidiNum() {
                return firstMidiNum;
        }

        public int getSecondMidiNum() {
                return secondMidiNum;
        }

        public int getThirdMidiNum() {
                return thirdMidiNum;
        }

        public Note getFirstNote() {
                return firstNote;
        }

        public Note getSecondNote() {
                return secondNote;
        }

        public Note getThirdNote() {
                return thirdNote;
        }

        public void setFirstMidiNum(int firstMidiNum) {
                this.firstMidiNum = firstMidiNum;
        }

        public void setSecondMidiNum(int secondMidiNum) {
                this.secondMidiNum = secondMidiNum;
        }

        public void setThirdMidiNum(int thirdMidiNum) {
                this.thirdMidiNum = thirdMidiNum;
        }

        public void setFirstNote(Note firstNote) {
                this.firstNote = firstNote;
        }

        public void setSecondNote(Note secondNote) {
                this.secondNote = secondNote;
        }

        public void setThirdNote(Note thirdNote) {
                this.thirdNote = thirdNote;
        }
        public String toString() {
                return "" + getFirstMidiNum() + "," + getSecondMidiNum() + "," + getThirdMidiNum() + " ";
        }

}
