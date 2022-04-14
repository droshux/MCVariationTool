public class BlockImage {
    Colour[][] Pixels;

    public BlockImage(int width, int height) {
        this.Pixels = new Colour[width][height];
    }

    @Override
    public  String toString() {
        StringBuilder output = new StringBuilder();

        for (int x = 0; x < Pixels.length; x++) {
            for (int y = 0; y < Pixels[x].length; y++) {
                output.append(Pixels[x][y].toString());
            }
            output.append("\n");
        }

        return output.toString();
    }
}
