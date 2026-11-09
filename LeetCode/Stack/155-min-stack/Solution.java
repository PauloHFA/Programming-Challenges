/**
 * LeetCode 155 - Min Stack
 * 
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * 
 * Implement the MinStack class:
 * - MinStack() initializes the stack object.
 * - void push(int val) pushes the element val onto the stack.
 * - void pop() removes the element on the top of the stack.
 * - int top() gets the top element of the stack.
 * - int getMin() retrieves the minimum element in the stack.
 * 
 * You must implement a solution with O(1) time complexity for each function.
 * 
 * Example 1:
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * Output
 * [null,null,null,null,-3,null,0,-2]
 * 
 * Explanation
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 * 
 * Constraints:
 * -2^31 <= val <= 2^31 - 1
 * Methods pop, top and getMin operations will always be called on non-empty stacks.
 * At most 3 * 10^4 calls will be made to push, pop, top, and getMin.
 */
public class Solution {
    
    /**
     * Approach 1: Two Stacks - O(1) time, O(n) space
     * 
     * Maintain two stacks: one for values, one for minimums.
     * When pushing, if the new value is <= current min, push to min stack.
     * When popping, if the popped value equals current min, pop from min stack.
     * 
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n) worst case
     */
        static class MinStackTwoStacks implements MinStack {
        private java.util.Stack<Integer> stack;
        private java.util.Stack<Integer> minStack;
        
        public MinStackTwoStacks() {
            stack = new java.util.Stack<>();
            minStack = new java.util.Stack<>();
        }
        
        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }
        
        public void pop() {
            if (stack.isEmpty()) return;
            int val = stack.pop();
            if (val == minStack.peek()) {
                minStack.pop();
            }
        }
        
        public int top() {
            return stack.peek();
        }
        
        public int getMin() {
            return minStack.peek();
        }
    }
    
    /**
     * Approach 2: Single Stack with Pairs - O(1) time, O(n) space
     * 
     * Store pairs of (value, currentMin) in a single stack.
     * Each element knows the minimum at the time it was pushed.
     * 
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n)
     */
        static class MinStackPairs implements MinStack {
        private java.util.Stack<int[]> stack;
        
        public MinStackPairs() {
            stack = new java.util.Stack<>();
        }
        
        public void push(int val) {
            int currentMin = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
            stack.push(new int[]{val, currentMin});
        }
        
        public void pop() {
            if (!stack.isEmpty()) {
                stack.pop();
            }
        }
        
        public int top() {
            return stack.peek()[0];
        }
        
        public int getMin() {
            return stack.peek()[1];
        }
    }
    
    /**
     * Approach 3: Single Stack with Difference Encoding - O(1) time, O(n) space
     * 
     * Store the difference between value and current minimum.
     * When val < min, store val - min (negative) and update min.
     * When popping, if diff < 0, restore previous min = min - diff.
     * 
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n)
     */
        static class MinStackDiff implements MinStack {
        private java.util.Stack<Long> stack;
        private long min;
        
        public MinStackDiff() {
            stack = new java.util.Stack<>();
            min = 0;
        }
        
        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = val;
            } else {
                long diff = (long)val - min;
                stack.push(diff);
                if (diff < 0) {
                    min = val;
                }
            }
        }
        
        public void pop() {
            if (stack.isEmpty()) return;
            long diff = stack.pop();
            if (diff < 0) {
                min = min - diff; // restore previous min
            }
        }
        
        public int top() {
            long diff = stack.peek();
            if (diff < 0) {
                return (int)min;
            }
            return (int)(min + diff);
        }
        
        public int getMin() {
            return (int)min;
        }
    }
    
    /**
     * Approach 4: Array-based Implementation - O(1) time, O(n) space
     * 
     * Use arrays instead of Stack for better performance.
     * 
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n)
     */
        static class MinStackArray implements MinStack {
        private int[] values;
        private int[] mins;
        private int size;
        
        public MinStackArray() {
            values = new int[30000];
            mins = new int[30000];
            size = 0;
        }
        
        public void push(int val) {
            values[size] = val;
            if (size == 0) {
                mins[size] = val;
            } else {
                mins[size] = Math.min(val, mins[size - 1]);
            }
            size++;
        }
        
        public void pop() {
            if (size > 0) {
                size--;
            }
        }
        
        public int top() {
            return values[size - 1];
        }
        
        public int getMin() {
            return mins[size - 1];
        }
    }
    
    // Test all approaches
    public static void main(String[] args) {
        System.out.println("=== Min Stack Tests ===\n");
        
        // Test 1: Example from problem
        System.out.println("Test 1: Example from problem");
        testMinStack(new MinStackTwoStacks(), "Two Stacks");
        testMinStack(new MinStackPairs(), "Pairs");
        testMinStack(new MinStackDiff(), "Difference");
        testMinStack(new MinStackArray(), "Array");
        
        // Test 2: Push, pop, push, getMin
        System.out.println("\nTest 2: Push, pop, push, getMin");
        testMinStack2(new MinStackTwoStacks(), "Two Stacks");
        testMinStack2(new MinStackPairs(), "Pairs");
        testMinStack2(new MinStackDiff(), "Difference");
        testMinStack2(new MinStackArray(), "Array");
        
        // Test 3: Negative numbers
        System.out.println("\nTest 3: Negative numbers");
        testMinStack3(new MinStackTwoStacks(), "Two Stacks");
        testMinStack3(new MinStackPairs(), "Pairs");
        testMinStack3(new MinStackDiff(), "Difference");
        testMinStack3(new MinStackArray(), "Array");
        
        // Test 4: Duplicate minimums
        System.out.println("\nTest 4: Duplicate minimums");
        testMinStack4(new MinStackTwoStacks(), "Two Stacks");
        testMinStack4(new MinStackPairs(), "Pairs");
        testMinStack4(new MinStackDiff(), "Difference");
        testMinStack4(new MinStackArray(), "Array");
        
        // Test 5: Large values
        System.out.println("\nTest 5: Large values (Integer.MAX_VALUE, Integer.MIN_VALUE)");
        testMinStack5(new MinStackTwoStacks(), "Two Stacks");
        testMinStack5(new MinStackPairs(), "Pairs");
        testMinStack5(new MinStackDiff(), "Difference");
        testMinStack5(new MinStackArray(), "Array");
        
        System.out.println("\n=== ALL TESTS PASSED ===");
    }
    
    // Interface for testing
    interface MinStack {
        void push(int val);
        void pop();
        int top();
        int getMin();
    }
    
    static void testMinStack(MinStack ms, String name) {
        ms.push(-2);
        ms.push(0);
        ms.push(-3);
        assert ms.getMin() == -3 : name + " getMin() failed: expected -3, got " + ms.getMin();
        ms.pop();
        assert ms.top() == 0 : name + " top() failed: expected 0, got " + ms.top();
        assert ms.getMin() == -2 : name + " getMin() failed: expected -2, got " + ms.getMin();
        System.out.println("  " + name + ": ✓");
    }
    
    static void testMinStack2(MinStack ms, String name) {
        ms.push(5);
        ms.push(3);
        ms.push(7);
        assert ms.getMin() == 3 : name + " getMin() failed";
        ms.pop();
        ms.push(2);
        assert ms.getMin() == 2 : name + " getMin() failed after push 2";
        ms.pop();
        assert ms.getMin() == 3 : name + " getMin() failed after pop";
        System.out.println("  " + name + ": ✓");
    }
    
    static void testMinStack3(MinStack ms, String name) {
        ms.push(-5);
        ms.push(-10);
        ms.push(-3);
        assert ms.getMin() == -10 : name + " getMin() failed";
        ms.pop();
        assert ms.getMin() == -10 : name + " getMin() failed after pop";
        ms.pop();
        assert ms.getMin() == -5 : name + " getMin() failed after second pop";
        System.out.println("  " + name + ": ✓");
    }
    
    static void testMinStack4(MinStack ms, String name) {
        ms.push(2);
        ms.push(2);
        ms.push(1);
        ms.push(1);
        assert ms.getMin() == 1 : name + " getMin() failed";
        ms.pop();
        assert ms.getMin() == 1 : name + " getMin() failed after pop";
        ms.pop();
        assert ms.getMin() == 2 : name + " getMin() failed after second pop";
        ms.pop();
        assert ms.getMin() == 2 : name + " getMin() failed after third pop";
        System.out.println("  " + name + ": ✓");
    }
    
    static void testMinStack5(MinStack ms, String name) {
        ms.push(Integer.MAX_VALUE);
        assert ms.getMin() == Integer.MAX_VALUE : name + " getMin() failed";
        ms.push(Integer.MIN_VALUE);
        assert ms.getMin() == Integer.MIN_VALUE : name + " getMin() failed";
        ms.pop();
        assert ms.getMin() == Integer.MAX_VALUE : name + " getMin() failed after pop";
        System.out.println("  " + name + ": ✓");
    }
}