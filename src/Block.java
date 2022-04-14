import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import org.jetbrains.annotations.NotNull;

public class Block {
    String filename;
    String Name;
    BlockImage texture;
    BlockImage normal;
    Colour MeanColour;
    float Roughness;
    float RoughnessPercent;
    float ExpressionPercent;

    public  Block(String file_name) {
        this.filename = file_name;
        this.texture = FileIO.GetTexture(this.filename);
        this.normal = FileIO.GetNormal(this.filename);
        this.Name = toName(this.filename);
        this.MeanColour = GetMeanColour();
        this.Roughness = GetRoughNess();
    }
    private @NotNull String toName(@NotNull String file_name) {
        String output = file_name.replace('_', ' ');
        char[] cArray = output.toCharArray();
        cArray[0] = Character.toUpperCase(cArray[0]);
        for (int i = 0; i < cArray.length; i++) {
            if (cArray[i] == ' ' && i != 0) {
                cArray[i+1] = Character.toUpperCase(cArray[i+1]);
            }
        }
        output = String.valueOf(cArray);
        return  output;
    }

    private Colour GetMeanColour() {
        int meanR=0; int meanG=0; int meanB=0;

        for (int x = 0; x < texture.Pixels.length; x++) {
            for (int y = 0; y < texture.Pixels[x].length; y++) {
                meanR += texture.Pixels[x][y].R;
                meanG += texture.Pixels[x][y].G;
                meanB += texture.Pixels[x][y].B;
            }
        }
        int totalPixels = texture.Pixels.length * texture.Pixels[0].length;
        meanR/=totalPixels;meanG/=totalPixels;meanB/=totalPixels;

        return new Colour(meanR, meanG, meanB);
    }
    private float GetRoughNess() {
        float TotalRoughness = 0;

        for (int x = 0; x < normal.Pixels.length; x++) {
            for (int y = 0; y < normal.Pixels[x].length; y++) {
                TotalRoughness += normal.Pixels[x][y].GetDistance(Program.NormalFlat);
            }
        }

        int totalPixels = normal.Pixels.length * normal.Pixels[0].length;
        return  TotalRoughness / totalPixels;
    }

    @Override
    public  String toString() {
        return String.format("%s:\n    File name: %s\n    Mean colour: %s\n    Roughness value: %s (%s percent %s)", Name, filename, GetMeanColour(), GetRoughNess(), RoughnessPercent*100, Ansi.colorize(" ", Attribute.BACK_COLOR((int)(255*RoughnessPercent), (int)(255*RoughnessPercent), (int)(255*RoughnessPercent))));
    }
}
