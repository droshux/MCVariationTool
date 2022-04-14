import java.util.ArrayList;
import java.util.Scanner;
import com.diogonunes.jcolor.*;

public class Program {

    public static final Colour NormalFlat = new Colour(128, 128, 255); //AKA #8080FF
    public static Block[] BlockDataSet;

    public static void main(String[] args) {
        BlockDataSet = FileIO.GetBlocks();
        SetRoughnessPercentages(BlockDataSet);

        //WELCOME
        Scanner userIn = new Scanner(System.in);
        clearScreen();

        //GET COLOUR
        Colour desire = new Colour(0);
        System.out.println("Please enter the desired colour: (0-255)");
        System.out.print(Ansi.colorize("RED:", Attribute.RED_BACK())+" ");desire.R = userIn.nextInt();
        System.out.print(Ansi.colorize("GREEN:", Attribute.GREEN_BACK())+" ");desire.G = userIn.nextInt();
        System.out.print(Ansi.colorize("BLUE:", Attribute.BLUE_BACK())+" ");desire.B = userIn.nextInt();
        System.out.println("Colour Entered:\n" + desire);
        clearScreen();

        //GET ROUGHNESS
        float MaxRP;float MinRP;
        System.out.println("Please enter the roughness range: (0%-100%)");
        System.out.print("    " + Ansi.colorize("Minimum Roughness:", Attribute.BACK_COLOR(64, 64, 64), Attribute.TEXT_COLOR(191, 191, 191)) + " ");
        MinRP = userIn.nextFloat() / 100;
        System.out.print("    " + Ansi.colorize("Maximum Roughness:", Attribute.BACK_COLOR(192, 192, 191), Attribute.TEXT_COLOR(63, 63, 6)) + " ");
        MaxRP = userIn.nextFloat() / 100;
        clearScreen();

        //GET BLOCK COUNT
        int blockCount;
        System.out.println("Please enter the number of blocks you want to use:");
        System.out.print("    " + Ansi.colorize("Block types:", Attribute.YELLOW_TEXT())+ " ");
        blockCount = userIn.nextInt();
        clearScreen();

        //Actually Get Results
        // 1) Bubble Sort by distance to the desired value
        Block temp; boolean DidSwap;
        do {
            DidSwap = false;
            for (int i = 1; i < BlockDataSet.length; i++) {
                if (desire.GetDistance(BlockDataSet[i].MeanColour) < desire.GetDistance(BlockDataSet[i - 1].MeanColour)) {
                    temp = BlockDataSet[i - 1];
                    BlockDataSet[i - 1] = BlockDataSet[i];
                    BlockDataSet[i] = temp;
                    DidSwap = true;
               }
            }
        } while (DidSwap);

        //2) Collect into n valid blocks (withing roughness range)
        ArrayList<Block> ValidBlocks = new ArrayList<>();
        for (Block block : BlockDataSet) {
            if (block.RoughnessPercent >= MinRP && block.RoughnessPercent <= MaxRP) ValidBlocks.add(block);
            if (ValidBlocks.size() == blockCount) break;
        }

        //3) Calculate Percentages
        float SigmaD = 0; for (Block b : ValidBlocks) SigmaD += desire.GetDistance(b.MeanColour);
        ArrayList<Float> Expressions = new ArrayList<>();
        for (Block b : ValidBlocks) Expressions.add(desire.GetDistance(b.MeanColour) / SigmaD);
        for (int i = 0; i < Expressions.size(); i++) ValidBlocks.get(ValidBlocks.size() - 1 - i).ExpressionPercent = Expressions.get(i);
        for (Block b : ValidBlocks) {System.out.print(Math.round(b.ExpressionPercent*100) + "%" + b.filename);if(ValidBlocks.indexOf(b) != ValidBlocks.size()-1) System.out.print(",");}
    }

    public static void SetRoughnessPercentages(Block[] blocks) {
        float MaxR = 0;
        for (Block block : blocks) if (block.Roughness > MaxR) MaxR = block.Roughness;
        for (Block block : blocks) block.RoughnessPercent = block.Roughness / MaxR;
    }

    public static void clearScreen() {
        Scanner userIn = new Scanner(System.in);
        System.out.println("Press enter to continue...");userIn.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Attribute[] HeaderFormat = new Attribute[]{Attribute.BLUE_TEXT(), Attribute.BOLD()};
        System.out.println(Ansi.colorize("droshux's Minecraft block variation tool:", HeaderFormat));
    }
}