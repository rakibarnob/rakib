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
                }
            }
        }

        return lst;
    }

    public void drawLine(List<Point> points, Color color) {
        Graphics2D g2d = (Graphics2D) this.image.getGraphics();
        g2d.setColor(color);

        for (int i = 0; i < points.size(); i++) {
            int prevIndex = (i - 1);
            Point prevPoint = points.get((prevIndex < 0) ? (points.size() - 1) : prevIndex);
            Point curPoint = points.get(i);
            g2d.drawLine((int) prevPoint.getX(), (int) prevPoint.getY(), (int) curPoint.getX(), (int) curPoint.getY());
        }
        g2d.finalize();
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
