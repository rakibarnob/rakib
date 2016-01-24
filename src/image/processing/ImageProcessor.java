package image.processing;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raihan on 24-Jan-16.
 */
public class ImageProcessor {
    private Image image;

    public ImageProcessor(Image img){
        this.image = img;
    }

    public List<Point> getPoints(){
        List<Point> lst = new ArrayList();

        return lst;
    }

    public String getSrcData() throws IOException {
        byte[] imageBytes = getImgBytes(this.image);

        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(imageBytes);
        return "data:image/jpeg;base64," + base64;
    }

    public void applyFilter() {
        final Color color = new Color(255, 255, 255);

        ImageFilter filter = new RGBImageFilter() {
            public int markerRGB = color.getRGB() | 0xFF000000;
            @Override
            public int filterRGB(int x, int y, int rgb) {
                if ( ( rgb | 0xFF000000 ) == markerRGB ) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else {
                    return rgb;
                }
            }
        };
        ImageProducer imageProducer = new FilteredImageSource(this.image.getSource(), filter);
        this.image = Toolkit.getDefaultToolkit().createImage(imageProducer);
    }

    public void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

    public List<Point> getPixelsByColor(Color color) {
        int colorRGB = color.getRGB() | 0xFF000000;
        BufferedImage img = getBufferedImage(this.image);

        List<Point> lst = new ArrayList<Point>();

        int w = img.getWidth();
        int h = img.getHeight();
        System.out.println("width, height: " + w + ", " + h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = img.getRGB(j, i);

                if(pixel == colorRGB) {
                    lst.add(new Point(j, i));

                    System.out.println("x,y: " + j + ", " + i);
                    System.out.println(pixel);
                    printPixelARGB(pixel);
                    System.out.println("");
                }
            }
        }

        return lst;
    }

    private byte [] getImgBytes(Image image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(getBufferedImage(image), "jpeg", baos);
        } catch (IOException ex) {
            //handle it here.... not implemented yet...
        }
        return baos.toByteArray();
    }

    private BufferedImage getBufferedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        return bi;
    }
}
