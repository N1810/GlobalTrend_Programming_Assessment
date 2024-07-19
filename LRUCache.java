import java.util.HashMap;

class LRUCache {

    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final HashMap<Integer, Node> cache;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        
     
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // Move the accessed node to the head
        removeNode(node);
        addNodeAtHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            // Update the value and move the node to the head
            node.value = value;
            removeNode(node);
            addNodeAtHead(node);
        } else {
            if (cache.size() == capacity) {
                // Remove the least recently used node
                Node tailPrev = tail.prev;
                cache.remove(tailPrev.key);
                removeNode(tailPrev);
            }
            
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNodeAtHead(newNode);
        }
    }

    private void addNodeAtHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(1));    
        lruCache.put(3, 3);                    
        System.out.println(lruCache.get(2));    
        lruCache.put(4, 4);                     
        System.out.println(lruCache.get(1));    
        System.out.println(lruCache.get(3));    
        System.out.println(lruCache.get(4));    
    }
}
