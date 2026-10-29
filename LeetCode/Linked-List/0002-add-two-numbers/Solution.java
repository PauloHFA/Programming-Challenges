import java.util.Arrays;

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

/**
 * LeetCode 0002: Add Two Numbers
 * Difficulty: Medium
 * Category: Linked List
 * 
 * Approach: Elementary Math Simulation with Carry
 * Time Complexity: O(max(N, M))
 * Space Complexity: O(max(N, M))
 */
public class Solution {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;
        int carry = 0;

        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }

        return dummyHead.next;
    }

    // Helper method to create a linked list from an array of integers
    public static ListNode fromArray(int... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int v : values) {
            current.next = new ListNode(v);
            current = current.next;
        }
        return dummy.next;
    }

    // Helper method to convert a linked list to a formatted string representation
    public static String toListString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper method to verify if two linked lists have identical values
    public static boolean isEqual(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();

        // Test Case 1: Standard example (same length)
        // l1 = [2, 4, 3], l2 = [5, 6, 4] -> 342 + 465 = 807 -> [7, 0, 8]
        ListNode l1_1 = fromArray(2, 4, 3);
        ListNode l2_1 = fromArray(5, 6, 4);
        ListNode expected1 = fromArray(7, 0, 8);
        ListNode result1 = solver.addTwoNumbers(l1_1, l2_1);
        System.out.println("Test 1: " + toListString(result1) + " (Expected: [7, 0, 8])");
        if (!isEqual(result1, expected1)) {
            throw new AssertionError("Test 1 failed! Got " + toListString(result1) + ", expected [7, 0, 8]");
        }

        // Test Case 2: Zero values
        // l1 = [0], l2 = [0] -> 0 + 0 = 0 -> [0]
        ListNode l1_2 = fromArray(0);
        ListNode l2_2 = fromArray(0);
        ListNode expected2 = fromArray(0);
        ListNode result2 = solver.addTwoNumbers(l1_2, l2_2);
        System.out.println("Test 2: " + toListString(result2) + " (Expected: [0])");
        if (!isEqual(result2, expected2)) {
            throw new AssertionError("Test 2 failed! Got " + toListString(result2) + ", expected [0]");
        }

        // Test Case 3: Unequal list lengths with multiple carries
        // l1 = [9, 9, 9, 9, 9, 9, 9], l2 = [9, 9, 9, 9] -> [8, 9, 9, 9, 0, 0, 0, 1]
        ListNode l1_3 = fromArray(9, 9, 9, 9, 9, 9, 9);
        ListNode l2_3 = fromArray(9, 9, 9, 9);
        ListNode expected3 = fromArray(8, 9, 9, 9, 0, 0, 0, 1);
        ListNode result3 = solver.addTwoNumbers(l1_3, l2_3);
        System.out.println("Test 3: " + toListString(result3) + " (Expected: [8, 9, 9, 9, 0, 0, 0, 1])");
        if (!isEqual(result3, expected3)) {
            throw new AssertionError("Test 3 failed! Got " + toListString(result3) + ", expected [8, 9, 9, 9, 0, 0, 0, 1]");
        }

        // Test Case 4: Carry propagating to a new most-significant digit
        // l1 = [5], l2 = [5] -> 5 + 5 = 10 -> [0, 1]
        ListNode l1_4 = fromArray(5);
        ListNode l2_4 = fromArray(5);
        ListNode expected4 = fromArray(0, 1);
        ListNode result4 = solver.addTwoNumbers(l1_4, l2_4);
        System.out.println("Test 4: " + toListString(result4) + " (Expected: [0, 1])");
        if (!isEqual(result4, expected4)) {
            throw new AssertionError("Test 4 failed! Got " + toListString(result4) + ", expected [0, 1]");
        }

        // Test Case 5: Unequal length where one list is zero
        // l1 = [1, 8], l2 = [0] -> 81 + 0 = 81 -> [1, 8]
        ListNode l1_5 = fromArray(1, 8);
        ListNode l2_5 = fromArray(0);
        ListNode expected5 = fromArray(1, 8);
        ListNode result5 = solver.addTwoNumbers(l1_5, l2_5);
        System.out.println("Test 5: " + toListString(result5) + " (Expected: [1, 8])");
        if (!isEqual(result5, expected5)) {
            throw new AssertionError("Test 5 failed! Got " + toListString(result5) + ", expected [1, 8]");
        }

        // Test Case 6: Cascading carries ending with a new digit
        // l1 = [9, 9], l2 = [1] -> 99 + 1 = 100 -> [0, 0, 1]
        ListNode l1_6 = fromArray(9, 9);
        ListNode l2_6 = fromArray(1);
        ListNode expected6 = fromArray(0, 0, 1);
        ListNode result6 = solver.addTwoNumbers(l1_6, l2_6);
        System.out.println("Test 6: " + toListString(result6) + " (Expected: [0, 0, 1])");
        if (!isEqual(result6, expected6)) {
            throw new AssertionError("Test 6 failed! Got " + toListString(result6) + ", expected [0, 0, 1]");
        }

        // Test Case 7: Different lengths where l2 is longer than l1
        // l1 = [1], l2 = [9, 9] -> 1 + 99 = 100 -> [0, 0, 1]
        ListNode l1_7 = fromArray(1);
        ListNode l2_7 = fromArray(9, 9);
        ListNode expected7 = fromArray(0, 0, 1);
        ListNode result7 = solver.addTwoNumbers(l1_7, l2_7);
        System.out.println("Test 7: " + toListString(result7) + " (Expected: [0, 0, 1])");
        if (!isEqual(result7, expected7)) {
            throw new AssertionError("Test 7 failed! Got " + toListString(result7) + ", expected [0, 0, 1]");
        }

        // Test Case 8: Maximum constraint (100 nodes each with value 9)
        // 99...9 (100 digits) + 99...9 (100 digits) = 199...98 (101 digits)
        // In reverse order: [8, 9, 9, ..., 9, 1] (8 followed by 99 nines, then 1)
        int[] nines100 = new int[100];
        Arrays.fill(nines100, 9);
        ListNode l1_8 = fromArray(nines100);
        ListNode l2_8 = fromArray(nines100);

        int[] expectedDigits8 = new int[101];
        expectedDigits8[0] = 8;
        for (int i = 1; i < 100; i++) {
            expectedDigits8[i] = 9;
        }
        expectedDigits8[100] = 1;
        ListNode expected8 = fromArray(expectedDigits8);

        ListNode result8 = solver.addTwoNumbers(l1_8, l2_8);
        if (!isEqual(result8, expected8)) {
            throw new AssertionError("Test 8 failed! Max constraint 100-digit addition mismatch");
        }
        System.out.println("Test 8: Max constraint (100 digits each) passed (Result length: 101 digits)");

        // Test Case 9: Cascading carry across 100 digits (100 digits of 9 + 1)
        // 99...9 (100 digits) + 1 = 100...0 (101 digits: 1 followed by 100 zeros)
        // In reverse order: [0, 0, ..., 0, 1] (100 zeros followed by 1)
        ListNode l1_9 = fromArray(nines100);
        ListNode l2_9 = fromArray(1);

        int[] expectedDigits9 = new int[101];
        expectedDigits9[100] = 1; // indices 0..99 are 0
        ListNode expected9 = fromArray(expectedDigits9);

        ListNode result9 = solver.addTwoNumbers(l1_9, l2_9);
        if (!isEqual(result9, expected9)) {
            throw new AssertionError("Test 9 failed! Cascading carry across 100 digits mismatch");
        }
        System.out.println("Test 9: Cascading carry across 100 digits passed (Result length: 101 digits)");

        // Test Case 10: Null and empty input robustness
        ListNode result10a = solver.addTwoNumbers(null, null);
        if (result10a != null) {
            throw new AssertionError("Test 10a failed! Expected null for (null, null), got " + toListString(result10a));
        }
        ListNode result10b = solver.addTwoNumbers(null, fromArray(5));
        if (!isEqual(result10b, fromArray(5))) {
            throw new AssertionError("Test 10b failed! Expected [5] for (null, [5]), got " + toListString(result10b));
        }
        ListNode result10c = solver.addTwoNumbers(fromArray(5), null);
        if (!isEqual(result10c, fromArray(5))) {
            throw new AssertionError("Test 10c failed! Expected [5] for ([5], null), got " + toListString(result10c));
        }
        System.out.println("Test 10: Null/empty input robustness passed");

        // Test Case 11: Single digit sum without carry
        // l1 = [2], l2 = [3] -> 2 + 3 = 5 -> [5]
        ListNode l1_11 = fromArray(2);
        ListNode l2_11 = fromArray(3);
        ListNode expected11 = fromArray(5);
        ListNode result11 = solver.addTwoNumbers(l1_11, l2_11);
        System.out.println("Test 11: " + toListString(result11) + " (Expected: [5])");
        if (!isEqual(result11, expected11)) {
            throw new AssertionError("Test 11 failed! Got " + toListString(result11) + ", expected [5]");
        }

        System.out.println("\nAll test cases passed successfully!");
    }
}
