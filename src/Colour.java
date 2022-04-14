import com.diogonunes.jcolor.*;
import org.jetbrains.annotations.NotNull;

public class Colour {
    int R, G, B;

    public  Colour(int r, int g, int b) {
        this.R = r; this.G = g;this.B = b;
    }
    public  Colour(int rgbIn) {
        this.R = (rgbIn & 0xff0000) >> 16;
        this.G = (rgbIn & 0xff00) >> 8;
        this.B = rgbIn & 0xff;
    }

    @Override
    public String toString() {
        Attribute colour = Attribute.BACK_COLOR(this.R, this.G, this.B);
        return "rgb(" + this.R + ", " + this.G + ", " + this.B + ")    " + Ansi.colorize(" ", colour);
    }

    public  float GetDistance(@NotNull Colour other) {
        float deltaR = other.R - this.R; float deltaG = other.G - this.G; float deltaB = other.B - this.B;
        float hypotenuse = (float)Math.sqrt(Math.pow(deltaR, 2f) + Math.pow(deltaG, 2f));
        return  (float)Math.sqrt(Math.pow(hypotenuse, 2) + Math.pow(deltaB, 2));
    }
}
