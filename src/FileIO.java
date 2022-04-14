import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileIO {
    private  static  Colour[][] LoadFile(String filepath) {
        Colour[][] output;

        try {
            URL url = new File(filepath).toURI().toURL();
            Image image = Toolkit.getDefaultToolkit().createImage(url);
            BufferedImage img = toBufferedImage(image);

            int w = img.getWidth(); int h = img.getHeight();
            output = new Colour[w][h];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    output[x][y] = new Colour(img.getRGB(x, y));
                }
            }
            return output;
        } catch (Exception ignored) {
            System.out.printf("FILE %s NOT FOUND\nPress enter to exit :(%n", filepath);
            try {System.out.println(System.in.read());} catch (IOException ignored2) {}
            System.exit(1);
        }

        return  null;
    }
    public  static @NotNull BlockImage GetTexture(String block_name) {
        BlockImage output = new BlockImage(1, 1);
        output.Pixels = LoadFile("images/texture/" + block_name + ".png");
        return output;
    }
    public static @NotNull BlockImage GetNormal(String block_name) {
        BlockImage output = new BlockImage(1,1);
        output.Pixels = LoadFile("images/normal/" + block_name + "_n.png");
        return output;
    }

    public static Block[] GetBlocks() {
        Block[] output;
        File texturesFolder = new File("images/texture");
        File[] textures = texturesFolder.listFiles();
        assert textures != null;
        output = new Block[textures.length];
        for (int i = 0; i < textures.length; i++) {
            output[i] = new Block(textures[i].getName().replace(".png",""));
        }
        return output;
    }

    private  static @NotNull
    BufferedImage toBufferedImage(Image image) {
        if (image instanceof  BufferedImage) {return (BufferedImage)image;}

        image = new ImageIcon(image).getImage();

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return  bufferedImage;
    }
}
