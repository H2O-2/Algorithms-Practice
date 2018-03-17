import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lines;
    private int segmentNum = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] inPoints) {
        if (inPoints == null) throw new NullPointerException("EMPTY ARRAY");

        Point max;
        Point maxPrev;
        Point min;
        Point minPrev;

        int len = inPoints.length;
        double[] lineInfo = new double[len * 2]; // stores slope info of line segments
        Point[] start = new Point[len * 2]; // stores starting point for segments
        Point[] points = new Point[len];

        for (int n = 0; n < len; n++) {
            points[n] = inPoints[n];
        }

        Arrays.sort(points, 0, len);
        lines = new LineSegment[len * 2];

        for (int i = 0; i < len; i++) {
            if (points[i] == null) throw new NullPointerException("EMPTY POINT");

            for (int j = i + 1; j < len; j++) {
                if (isSame(points[i], points[j])) throw new IllegalArgumentException("REPEATED POINTS");
                if (points[j] == null) throw new NullPointerException("EMPTY POINT");

                min = null;
                minPrev = null;
                max = null;
                maxPrev = null;

                double newSlope = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < len; k++) {
                    if (isSame(points[i], points[k]) || isSame(points[j], points[k]))
                        throw new IllegalArgumentException("REPEATED POINTS");
                    if (points[k] == null) throw new NullPointerException("EMPTY POINT");

                    if (newSlope != points[j].slopeTo(points[k])) continue;

                    boolean tested = false; // check if the line is already connected

                    for (int x = 0; x < segmentNum; x++) {
                        if (lineInfo[x] == newSlope &&
                                (points[i].slopeTo(start[x]) == newSlope ||
                                        points[i].slopeTo((start[x])) == Double.NEGATIVE_INFINITY)) {
                            tested = true;
                        }
                    }

                    if (tested) continue;


                    for (int l = k + 1; l < len; l++) {
                        if (isSame(points[i], points[l]) || isSame(points[j], points[l])
                                || isSame(points[k], points[j])) throw new IllegalArgumentException("REPEATED POINTS");
                        if (points[l] == null) throw new NullPointerException("EMPTY POINT");

                        if (points[i].slopeTo(points[j]) != points[j].slopeTo(points[l])) {
                            continue;
                        }

                        if (max == null && newSlope < 0) {
                            max = points[i];
                        } else if (max == null) {
                            max = points[l];
                        }

                        if (min == null && newSlope < 0) {
                            min = points[l];
                        } else if (min == null) {
                            min = points[i];
                        }

                        if ((newSlope < 0 && points[l].compareTo(max) < 0)
                                || (newSlope >= 0 && points[l].compareTo(max) > 0)) {
                            max = points[l];
                        } else if ((newSlope < 0 && points[l].compareTo(min) > 0)
                                || (newSlope >= 0 && points[l].compareTo(min) < 0)) {
                            min = points[l];
                        }

                    }

                    if (max != null && min != null && ((minPrev == null || maxPrev == null)
                            || ((newSlope < 0 && max.compareTo(maxPrev) < 0) || (newSlope >= 0 && max.compareTo(maxPrev) > 0)
                            || (newSlope < 0 && min.compareTo(minPrev) > 0) ||
                            (newSlope >= 0 && min.compareTo(minPrev) < 0)))) {
                        lines[segmentNum] = new LineSegment(min, max);
                        lineInfo[segmentNum] = newSlope;
                        start[segmentNum] = points[i];
                        segmentNum++;
                    }

                    minPrev = min;
                    maxPrev = max;
                }
            }
        }
        resize(segmentNum);
    }

    // the number of line segments
    public int numberOfSegments() {
        int returnSegNum = segmentNum;

        return returnSegNum;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] returnSeg = new LineSegment[segmentNum];

        for (int i = 0; i < segmentNum; i++) {
            returnSeg[i] = lines[i];
        }

        return returnSeg;
    }

    // check if p1 and p2 are same points
    private boolean isSame(Point p1, Point p2) {
        return p1.compareTo(p2) == 0;
    }

    private void resize(int l) {
        LineSegment[] newArray = new LineSegment[l];
        int newArrayN = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] != null) {
                newArray[newArrayN] = lines[i];
                newArrayN++;
            }
        }
        lines = newArray;
    }


}
