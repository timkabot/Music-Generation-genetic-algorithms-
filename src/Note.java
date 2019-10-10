/**
 * Created by Timkabor on 10/20/2017.
 */
public enum Note {
    C, C_Sharp,
    D, D_Sharp,
    E,
    F, F_Sharp,
    G, G_Sharp,
    A, A_Sharp,
    B;

    @Override
    public String toString() {
        switch (this){
            case A:         return "A";
            case C:         return "C";
            case A_Sharp:   return "A_Sharp";
            case B:         return "B";
            case C_Sharp:   return "C_Sharp";
            case D:         return "D";
            case D_Sharp:   return "D_Sharp";
            case E:         return "E";
            case F:         return "F";
            case F_Sharp:   return "F_Sharp";
            case G:         return "G";
            case G_Sharp:   return "G_Sharp";
        }
        return "NONE";
    }
}
