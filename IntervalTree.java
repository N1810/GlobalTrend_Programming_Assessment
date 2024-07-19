import java.util.ArrayList;
import java.util.List;

class Interval {
    int start, end;
    
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

class IntervalTreeNode {
    Interval interval;
    int maxEnd;
    IntervalTreeNode left, right;
    
    public IntervalTreeNode(Interval interval) {
        this.interval = interval;
        this.maxEnd = interval.end;
        this.left = this.right = null;
    }
}

public class IntervalTree {
    private IntervalTreeNode root;
    
    public IntervalTree() {
        this.root = null;
    }
    
    public void insertInterval(int start, int end) {
        Interval newInterval = new Interval(start, end);
        root = insert(root, newInterval);
    }
    
    private IntervalTreeNode insert(IntervalTreeNode node, Interval interval) {
        if (node == null) {
            return new IntervalTreeNode(interval);
        }
        
        if (interval.start < node.interval.start) {
            node.left = insert(node.left, interval);
        } else {
            node.right = insert(node.right, interval);
        }
        
        if (node.maxEnd < interval.end) {
            node.maxEnd = interval.end;
        }
        
        return node;
    }
    
    public void deleteInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        root = delete(root, interval);
    }
    
    private IntervalTreeNode delete(IntervalTreeNode node, Interval interval) {
        if (node == null) {
            return null;
        }
        
        if (interval.start < node.interval.start) {
            node.left = delete(node.left, interval);
        } else if (interval.start > node.interval.start) {
            node.right = delete(node.right, interval);
        } else if (interval.end == node.interval.end) {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            IntervalTreeNode minNode = getMin(node.right);
            node.interval = minNode.interval;
            node.right = delete(node.right, minNode.interval);
        }
        
        if (node != null) {
            node.maxEnd = Math.max(node.interval.end, Math.max(getMaxEnd(node.left), getMaxEnd(node.right)));
        }
        
        return node;
    }
    
    private IntervalTreeNode getMin(IntervalTreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    private int getMaxEnd(IntervalTreeNode node) {
        return node == null ? Integer.MIN_VALUE : node.maxEnd;
    }
    
    public List<Interval> findOverlappingIntervals(int start, int end) {
        Interval queryInterval = new Interval(start, end);
        List<Interval> result = new ArrayList<>();
        findOverlappingIntervals(root, queryInterval, result);
        return result;
    }
    
    private void findOverlappingIntervals(IntervalTreeNode node, Interval queryInterval, List<Interval> result) {
        if (node == null) {
            return;
        }
        
        if (doOverlap(node.interval, queryInterval)) {
            result.add(node.interval);
        }
        
        if (node.left != null && node.left.maxEnd >= queryInterval.start) {
            findOverlappingIntervals(node.left, queryInterval, result);
        }
        
        findOverlappingIntervals(node.right, queryInterval, result);
    }
    
    private boolean doOverlap(Interval i1, Interval i2) {
        return i1.start <= i2.end && i2.start <= i1.end;
    }
    
    public static void main(String[] args) {
        IntervalTree tree = new IntervalTree();
        
        tree.insertInterval(15, 20);
        tree.insertInterval(10, 30);
        tree.insertInterval(17, 19);
        tree.insertInterval(5, 20);
        tree.insertInterval(12, 15);
        tree.insertInterval(30, 40);
        
        System.out.println("Interval overlapping with [14, 16]:");
        List<Interval> overlaps = tree.findOverlappingIntervals(14, 16);
        for (Interval interval : overlaps) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
        
        tree.deleteInterval(10, 30);
        System.out.println("Interval overlapping with [14, 16] after deletion:");
        overlaps = tree.findOverlappingIntervals(14, 16);
        for (Interval interval : overlaps) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
    }
}
