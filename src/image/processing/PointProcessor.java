package image.processing;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Raihan on 1/24/2016.
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
}
