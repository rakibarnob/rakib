package image.processing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by Rakib on 1/24/2016.
 */
public abstract class PointProcessor {
    public static final int TOLERANCE = 1;

    public static java.util.List<Point> groupPoints(List<Point> list) {
        java.util.List<Point> result = new ArrayList<Point>();

        java.util.List<Point> tmpLst = new ArrayList<Point>();

        for (Point p : list) {
            if (tmpLst.size() > 0) {
                int index = tmpLst.size() - 1;
                Point lastPoint = tmpLst.get(index);
                int diffX = (int) Math.abs(lastPoint.getX() - p.getX());
                int diffY = (int) Math.abs(lastPoint.getY() - p.getY());

                if ((diffX + diffY) <= TOLERANCE) {
                    tmpLst.add(p);
                } else {
                    result.addAll(tmpLst);
                    tmpLst = new ArrayList<Point>();
                }
            } else {
                tmpLst.add(p);
            }
        }

        return result;
    }

    public static java.util.List<java.util.List<Point>> detectDistantPoints(List<Point> list){
        sortByXCoordinates(list);

        List<List<Point>> result = new ArrayList<List<Point>>();
        result.add(list);

        return result;
    }

    private static void sortByXCoordinates(List<Point> list) {
        Collections.sort(list, new PointCompare());
    }

    public static java.util.List<Point> getEdge(BufferedImage image, float level) {
        List<Point> result = new ArrayList<Point>();

        for (int y = 0; y < (image.getHeight() - 1); y++) {
            for (int x = 0; x < (image.getWidth() - 1); x++) {
                int colorCurPx = image.getRGB(x, y);
                int colorRightPx = image.getRGB(x + 1, y);
                int colorBelowPx = image.getRGB(x, y + 1);

                float pxLum = getLuminance(colorCurPx);
                float rightLum = getLuminance(colorRightPx);
                float belowLum = getLuminance(colorBelowPx);

                if ((rightLum - pxLum) > level && (belowLum - pxLum) > level) {
                    result.add(new Point(x, y));
                }
            }
        }

        return result;
    }

    private static float getLuminance(int color) {
        // extract each color component
        int red = (color >>> 16) & 0xFF;
        int green = (color >>> 8) & 0xFF;
        int blue = (color >>> 0) & 0xFF;

        // calc luminance in range 0.0 to 1.0; using SRGB luminance constants
        float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
        return luminance;
    }
}

class PointCompare implements Comparator<Point> {

    public int compare(final Point a, final Point b) {
        if (a.x < b.x) {
            return -1;
        }
        else if (a.x > b.x) {
            return 1;
        }
        else {
            return 0;
        }
    }
}