import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lines;

    private int segmentNum = 0;

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

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        int len = points.length;
        double lineInfo[] = new double[len]; // stores slope info of line segments
        Point[] start = new Point[len]; // stores starting point for segments
        Point[] sorted = new Point[len];

        lines = new LineSegment[len];

        for (int i = 0; i < len; i++) {
            for (int n = 0; n < len; n++) {
                sorted[n] = points[n];
            }

            Arrays.sort(sorted, 0, len, points[i].slopeOrder());
/*
            // DEBUG

            StdOut.println(sorted);
            for (int k = 0; k < len; k++) {
                double test = points[i].slopeTo(sorted[k]);
                StdOut.println(test);
            }
*/
            // Test if the segment is already tested




            double currentSlope = +0.0;
            double nextSlope = 0;
            int pointNum = 2;
            int j = 0;
            boolean tested = false;

            Point min = points[i];
            Point max = points[i];

            while (j < len) {
                currentSlope = points[i].slopeTo(sorted[j]);

                for (int x = 0; x < segmentNum; x++) {
                    if (currentSlope == lineInfo[x] &&
                            (points[i].slopeTo(start[x]) == currentSlope || points[i].slopeTo(start[x]) == Double.NEGATIVE_INFINITY)) {
                        tested = true;
                    }
                }

                if (tested) {
                    j++;
                    continue;
                }

                if (j < len - 1) nextSlope = points[i].slopeTo(sorted[j + 1]);

                if ((j == len - 1 || nextSlope != currentSlope) && pointNum >= 4){
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
                }

                j++;
            }
        }
        resize(segmentNum);

    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentNum;
    }

    // the line segments
    public LineSegment[] segments() {
        return lines;
    }
}
