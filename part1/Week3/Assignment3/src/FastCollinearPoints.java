import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lines;

    private int segmentNum = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("EMPTY ARRAY");

        int len = points.length;
        double[] lineInfo = new double[len * 2]; // stores slope info of line segments
        Point[] start = new Point[len * 2]; // stores starting point for segments
        Point[] sorted = new Point[len];

        lines = new LineSegment[len];

        for (int i = 0; i < len; i++) {
            if (points[i] == null) throw new NullPointerException("NULL POINT");

            for (int n = 0; n < len; n++) {
                sorted[n] = points[n];
            }

            Arrays.sort(sorted, 0, len, points[i].slopeOrder());

            double currentSlope = 0;
            double nextSlope = 0;
            double prevSlope = 0;
            double checkDuplicate = 0;
            int pointNum = 2;
            int j = 0;
            boolean tested = false;

            Point min = points[i];
            Point max = points[i];

            while (j < len) {
                currentSlope = points[i].slopeTo(sorted[j]);

                if (currentSlope == Double.NEGATIVE_INFINITY && checkDuplicate >= 1) {
                    throw new IllegalArgumentException("DUPLICATE POINTS");
                } else if (currentSlope == Double.NEGATIVE_INFINITY) {
                    checkDuplicate++;
                }

                for (int x = 0; x < segmentNum; x++) {
                    if (currentSlope == lineInfo[x] &&
                            (points[i].slopeTo(start[x]) == currentSlope ||
                                    points[i].slopeTo(start[x]) == Double.NEGATIVE_INFINITY)) {
                        tested = true;
                    }
                }

                if (tested) {
                    j++;
                    continue;
                }

                if (j < len - 1) nextSlope = points[i].slopeTo(sorted[j + 1]);

                if (j > 0) prevSlope = points[i].slopeTo(sorted[j - 1]);

                if ((j == len - 1 || nextSlope != currentSlope) && pointNum >= 4) {
                    if (sorted[j].compareTo(min) < 0 && prevSlope == currentSlope) {
                        min = sorted[j];
                    } else if (sorted[j].compareTo(max) > 0 && prevSlope == currentSlope) {
                        max = sorted[j];
                    }


                    lineInfo[segmentNum] = currentSlope;
                    start[segmentNum] = min;
                    lines[segmentNum] = new LineSegment(min, max);
                    segmentNum++;

                    pointNum = 2;
                    min = points[i];
                    max = points[i];
                    j++;

                    continue;
                } else if (j == len - 1) {
                    j++;
                    continue;
                } else if (nextSlope != currentSlope) {
                    pointNum = 2;
                    min = points[i];
                    max = points[i];
                    j++;

                    continue;
                }

                if (sorted[j].compareTo(min) < 0) {
                    min = sorted[j];
                    pointNum++;
                } else if (sorted[j].compareTo(max) > 0) {
                    max = sorted[j];
                    pointNum++;
                } else {
                    pointNum++;
                }

                j++;
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
