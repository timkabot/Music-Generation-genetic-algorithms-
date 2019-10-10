/**
 * Created by Timkabor on 10/20/2017.
 */
public final class MIDI {

    public static int getOctaveNumber(String name) {
        switch (name){
            case "FIRST"  : return 1;
            case "SECOND" : return 2;
            case "THIRD"  : return 3;
            case "FOURTH" : return 4;
            case "FIFTH"  : return 5;
            case "SIXTH"  : return 6;
            case "SEVENTH": return 7;
            case "EIGHTH" : return 8;
            case "NINETH" : return 9;
            case "TENTH"  : return 10;
        }
        return -1;
    }
    public static  int getNoteNumber(String name, int octaveNumber){
        int result = octaveNumber * 12;
        switch (name){
            case "C"       : return result;
            case "C_Sharp" : return result + 1;
            case "D"       : return result + 2;
            case "D_Sharp" : return result + 3;
            case "E"       : return result + 4;
            case "F"       : return result + 5;
            case "F_Sharp" : return result + 6;
            case "G"       : return result + 7;
            case "G_Sharp" : return result + 8;
            case "A"       : return result + 9;
            case "A_Sharp" : return result + 10;
            case "B"       : return result + 11;
        }
        return -1;
    }
    public static Note getNoteByMidiNumber(int number){
        switch (number % 12){
            case 0 : return Note.C;
            case 1 : return Note.C_Sharp;
            case 2 : return Note.D;
            case 3 : return Note.D_Sharp;
            case 4 : return Note.E;
            case 5 : return Note.F;
            case 6 : return Note.F_Sharp;
            case 7 : return Note.G;
            case 8 : return Note.G_Sharp;
            case 9 : return Note.A;
            case 10: return Note.A_Sharp;
            case 11: return Note.B;
        }
        return null;
    }
    public static int getMidiByNote(Note note, int octave){
        int res = 0;
        switch (note){
            case C : res = 0; break;
            case D : res = 2 ; break;
            case E : res = 4; break;
            case F : res = 5; break;
            case G : res = 7; break;
            case A : res = 9; break;
            case B : res = 11; break;

            case C_Sharp :  res = 1; break;
            case D_Sharp :  res = 3; break;
            case F_Sharp :  res = 6; break;
            case G_Sharp :  res = 8; break;
            case A_Sharp :  res = 10; break;
        }
        return res + (octave * 12);
    }

}
