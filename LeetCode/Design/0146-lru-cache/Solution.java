/**
 * LeetCode 146 - LRU Cache
 * 
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * 
 * Implement the LRUCache class:
 * - LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * - int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * 
 * The functions get and put must each run in O(1) average time complexity.
 * 
 * Example 1:
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * 
 * Explanation
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 * 
 * Constraints:
 * 1 <= capacity <= 3000
 * 0 <= key <= 10^4
 * 0 <= value <= 10^5
 * At most 2 * 10^5 calls will be made to get and put.
 */
public class Solution {
    
    /**
     * Approach 1: HashMap + Doubly Linked List (Standard O(1) Solution)
     * 
     * Use a HashMap for O(1) key lookup and a doubly linked list for O(1) 
     * insertion/deletion and maintaining access order.
     * Most recently used at head, least recently used at tail.
     * 
     * Time Complexity: O(1) for both get and put
     * Space Complexity: O(capacity)
     */
    static class LRUCache {
        private final int capacity;
        private final java.util.HashMap<Integer, Node> map;
        private final Node head; // Dummy head (most recently used)
        private final Node tail; // Dummy tail (least recently used)
        
        static class Node {
            int key;
            int value;
            Node prev;
            Node next;
            
            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
        
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new java.util.HashMap<>();
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }
        
        public int get(int key) {
            Node node = map.get(key);
            if (node == null) return -1;
            
            // Move to head (most recently used)
            moveToHead(node);
            return node.value;
        }
        
        public void put(int key, int value) {
            Node node = map.get(key);
            if (node != null) {
                // Update existing
                node.value = value;
                moveToHead(node);
            } else {
                // Add new
                Node newNode = new Node(key, value);
                map.put(key, newNode);
                addToHead(newNode);
                
                if (map.size() > capacity) {
                    // Remove LRU (tail.prev)
                    Node lru = removeTail();
                    map.remove(lru.key);
                }
            }
        }
        
        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
        
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        
        private void moveToHead(Node node) {
            removeNode(node);
            addToHead(node);
        }
        
        private Node removeTail() {
            Node lru = tail.prev;
            removeNode(lru);
            return lru;
        }
    }
    
    /**
     * Approach 2: LinkedHashMap (Java Built-in)
     * 
     * Use LinkedHashMap with accessOrder=true which maintains insertion/access order.
     * Override removeEldestEntry to implement LRU eviction.
     * 
     * Time Complexity: O(1) for both get and put
     * Space Complexity: O(capacity)
     */
    static class LRUCacheLinkedHashMap extends java.util.LinkedHashMap<Integer, Integer> {
        private final int capacity;
        
        public LRUCacheLinkedHashMap(int capacity) {
            super(capacity, 0.75f, true); // accessOrder = true
            this.capacity = capacity;
        }
        
        public int get(int key) {
            return super.getOrDefault(key, -1);
        }
        
        public void put(int key, int value) {
            super.put(key, value);
        }
        
        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
    
    /**
     * Approach 3: Array-based (for small capacity, educational)
     * 
     * Use arrays to simulate the cache. Not O(1) but simple.
     * 
     * Time Complexity: O(n) for get and put
     * Space Complexity: O(capacity)
     */
    static class LRUCacheArray {
        private final int capacity;
        private final int[] keys;
        private final int[] values;
        private int size;
        
        public LRUCacheArray(int capacity) {
            this.capacity = capacity;
            this.keys = new int[capacity];
            this.values = new int[capacity];
            this.size = 0;
        }
        
        public int get(int key) {
            for (int i = 0; i < size; i++) {
                if (keys[i] == key) {
                    int value = values[i];
                    // Move to front (most recently used)
                    for (int j = i; j > 0; j--) {
                        keys[j] = keys[j - 1];
                        values[j] = values[j - 1];
                    }
                    keys[0] = key;
                    values[0] = value;
                    return value;
                }
            }
            return -1;
        }
        
        public void put(int key, int value) {
            // Check if key exists
            for (int i = 0; i < size; i++) {
                if (keys[i] == key) {
                    // Update and move to front
                    for (int j = i; j > 0; j--) {
                        keys[j] = keys[j - 1];
                        values[j] = values[j - 1];
                    }
                    keys[0] = key;
                    values[0] = value;
                    return;
                }
            }
            
            // Key doesn't exist
            if (size == capacity) {
                // Remove LRU (last element)
                size--;
            }
            
            // Shift right and add at front
            for (int i = size; i > 0; i--) {
                keys[i] = keys[i - 1];
                values[i] = values[i - 1];
            }
            keys[0] = key;
            values[0] = value;
            size++;
        }
    }
    
    // Test helper
    private static void testLRUCache(String name, Object cache, String[] operations, Object[][] params, Object[] expected) {
        System.out.println("\n" + name + ":");
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            Object[] param = params[i];
            Object exp = expected[i];
            
            if (op.equals("LRUCache")) continue; // Constructor
            
            if (cache instanceof LRUCache) {
                LRUCache lru = (LRUCache) cache;
                if (op.equals("put")) {
                    lru.put((int)param[0], (int)param[1]);
                    System.out.println("  put(" + param[0] + ", " + param[1] + ") -> null");
                } else if (op.equals("get")) {
                    int result = lru.get((int)param[0]);
                    String status = (result == (int)exp) ? "✓" : "✗";
                    System.out.println("  get(" + param[0] + ") -> " + result + " (expected " + exp + ") " + status);
                }
            } else if (cache instanceof LRUCacheLinkedHashMap) {
                LRUCacheLinkedHashMap lru = (LRUCacheLinkedHashMap) cache;
                if (op.equals("put")) {
                    lru.put((int)param[0], (int)param[1]);
                    System.out.println("  put(" + param[0] + ", " + param[1] + ") -> null");
                } else if (op.equals("get")) {
                    int result = lru.get((int)param[0]);
                    String status = (result == (int)exp) ? "✓" : "✗";
                    System.out.println("  get(" + param[0] + ") -> " + result + " (expected " + exp + ") " + status);
                }
            } else if (cache instanceof LRUCacheArray) {
                LRUCacheArray lru = (LRUCacheArray) cache;
                if (op.equals("put")) {
                    lru.put((int)param[0], (int)param[1]);
                    System.out.println("  put(" + param[0] + ", " + param[1] + ") -> null");
                } else if (op.equals("get")) {
                    int result = lru.get((int)param[0]);
                    String status = (result == (int)exp) ? "✓" : "✗";
                    System.out.println("  get(" + param[0] + ") -> " + result + " (expected " + exp + ") " + status);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== LRU Cache Tests ===\n");
        
        // Test 1: Example from problem
        System.out.println("Test 1: Example from LeetCode");
        String[] ops1 = {"LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"};
        Object[][] params1 = {{2}, {1, 1}, {2, 2}, {1}, {3, 3}, {2}, {4, 4}, {1}, {3}, {4}};
        Object[] expected1 = {null, null, null, 1, null, -1, null, -1, 3, 4};
        
        LRUCache lru1 = new LRUCache(2);
        testLRUCache("HashMap + DLL", lru1, ops1, params1, expected1);
        
        LRUCacheLinkedHashMap lru1b = new LRUCacheLinkedHashMap(2);
        testLRUCache("LinkedHashMap", lru1b, ops1, params1, expected1);
        
        LRUCacheArray lru1c = new LRUCacheArray(2);
        testLRUCache("Array", lru1c, ops1, params1, expected1);
        
        // Test 2: Basic operations
        System.out.println("\n\nTest 2: Basic operations");
        LRUCache lru2 = new LRUCache(3);
        lru2.put(1, 10);
        lru2.put(2, 20);
        lru2.put(3, 30);
        System.out.println("  get(1) = " + lru2.get(1) + " (expected 10) " + (lru2.get(1) == 10 ? "✓" : "✗"));
        System.out.println("  get(2) = " + lru2.get(2) + " (expected 20) " + (lru2.get(2) == 20 ? "✓" : "✗"));
        lru2.put(4, 40); // Evicts 3
        System.out.println("  get(3) = " + lru2.get(3) + " (expected -1) " + (lru2.get(3) == -1 ? "✓" : "✗"));
        System.out.println("  get(4) = " + lru2.get(4) + " (expected 40) " + (lru2.get(4) == 40 ? "✓" : "✗"));
        
        // Test 3: Update existing key
        System.out.println("\nTest 3: Update existing key");
        LRUCache lru3 = new LRUCache(2);
        lru3.put(1, 1);
        lru3.put(2, 2);
        lru3.put(1, 10); // Update 1
        System.out.println("  get(1) = " + lru3.get(1) + " (expected 10) " + (lru3.get(1) == 10 ? "✓" : "✗"));
        System.out.println("  get(2) = " + lru3.get(2) + " (expected 2) " + (lru3.get(2) == 2 ? "✓" : "✗"));
        
        // Test 4: Capacity 1
        System.out.println("\nTest 4: Capacity 1");
        LRUCache lru4 = new LRUCache(1);
        lru4.put(1, 1);
        System.out.println("  get(1) = " + lru4.get(1) + " (expected 1) " + (lru4.get(1) == 1 ? "✓" : "✗"));
        lru4.put(2, 2); // Evicts 1
        System.out.println("  get(1) = " + lru4.get(1) + " (expected -1) " + (lru4.get(1) == -1 ? "✓" : "✗"));
        System.out.println("  get(2) = " + lru4.get(2) + " (expected 2) " + (lru4.get(2) == 2 ? "✓" : "✗"));
        
        // Test 5: Cross verification
        System.out.println("\nTest 5: Cross verification of all approaches");
        String[] ops5 = {"LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"};
        Object[][] params5 = {{2}, {1, 1}, {2, 2}, {1}, {3, 3}, {2}, {4, 4}, {1}, {3}, {4}};
        Object[] expected5 = {null, null, null, 1, null, -1, null, -1, 3, 4};
        
        LRUCache lru5a = new LRUCache(2);
        LRUCacheLinkedHashMap lru5b = new LRUCacheLinkedHashMap(2);
        LRUCacheArray lru5c = new LRUCacheArray(2);
        
        boolean allMatch = true;
        for (int i = 1; i < ops5.length; i++) {
            String op = ops5[i];
            Object[] param = params5[i];
            Object exp = expected5[i];
            
            int r1 = -2, r2 = -2, r3 = -2;
            if (op.equals("put")) {
                lru5a.put((int)param[0], (int)param[1]);
                lru5b.put((int)param[0], (int)param[1]);
                lru5c.put((int)param[0], (int)param[1]);
            } else if (op.equals("get")) {
                r1 = lru5a.get((int)param[0]);
                r2 = lru5b.get((int)param[0]);
                r3 = lru5c.get((int)param[0]);
            }
            
            if (op.equals("get")) {
                boolean m1 = (r1 == (int)exp);
                boolean m2 = (r2 == (int)exp);
                boolean m3 = (r3 == (int)exp);
                boolean match = m1 && m2 && m3;
                if (!match) allMatch = false;
                System.out.println("  get(" + param[0] + "): " + (match ? "MATCH" : "MISMATCH") + 
                    " (DLL:" + r1 + " LHM:" + r2 + " Arr:" + r3 + " Exp:" + exp + ")");
            }
        }
        
        System.out.println("\n=== " + (allMatch ? "ALL TESTS PASSED" : "SOME TESTS FAILED") + " ===");
    }
}