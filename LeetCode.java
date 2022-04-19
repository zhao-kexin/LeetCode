import java.util.*;

class AddTwoNumbers {

	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		// r is the dummy head of the resulting list, c is the current node of the
		// result
		ListNode r = new ListNode(0), p = l1, q = l2, c = r;
		int carry = 0;
		// while there are still nodes in either list
		while (p != null || q != null) {
			// sum = p + q + carry
			int sum = (p != null ? p.val : 0) + (q != null ? q.val : 0) + carry;
			// calculate carry
			carry = sum / 10;
			// create a next node with value of sum modulus 10
			c.next = new ListNode(sum % 10);
			// set current the next node
			c = c.next;
			if (p != null) {
				p = p.next;
			}
			if (q != null) {
				q = q.next;
			}
		}
		// if there is an additional carry
		if (carry > 0) {
			c.next = new ListNode(carry);
		}
		// return the result without the dummy head
		return r.next;
	}

}

class GenerateParenthesis {

	public List<String> generateParenthesis(int n) {
		List<String> res = new ArrayList<>();
		backtrack(res, "", 0, 0, n);
		return res;
	}

	public void backtrack(List<String> res, String cur, int open, int close, int n) {
		if (cur.length() == n * 2) {
			res.add(cur);
		}
		if (open < n) {
			backtrack(res, cur + "(", open + 1, close, n);
		}
		if (close < open) {
			backtrack(res, cur + ")", open, close + 1, n);
		}
	}

}

class LengthOfLongestSubstring {
	public int lengthOfLongestSubstring(String s) {
		int r = 0;
		int[] map = new int[128];
		for (int i = 0, j = 0; j < s.length(); j++) {
			i = Math.max(map[s.charAt(j)], i);
			r = Math.max(r, j - i + 1);
			map[s.charAt(j)] = j + 1;
		}
		return r;
	}
}

class LetterCombinationsOfAPhoneNumber {

	public List<String> letterCombinations(String digits) {
		if (digits.length() == 0) {
			return new ArrayList<>();
		}
		String[] numpad = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
		List<String> res = new ArrayList<>();
		backtrack(res, digits.toCharArray(), "", numpad);
		return res;
	}

	public void backtrack(List<String> res, char[] digits, String s, String[] numpad) {
		if (s.length() == digits.length) {
			res.add(s);
			return;
		}
		int digit = digits[s.length()] - '0';
		for (char letter : numpad[digit].toCharArray()) {
			backtrack(res, digits, s + Character.toString(letter), numpad);
		}
	}

}

class LongestPalindromicSubstring {

	class ManachersSolution {
		// Transform s into t.
		// For example, if s = "abba", then t = "$#a#b#b#a#@"
		// the # are interleaved to avoid even/odd-length palindromes uniformly
		// $ and @ are prepended and appended to each end to avoid bounds checking
		private char[] preprocess(String s) {
			char[] t = new char[s.length() * 2 + 3];
			t[0] = '$';
			t[s.length() * 2 + 2] = '@';
			for (int i = 0; i < s.length(); i++) {
				t[2 * i + 1] = '#';
				t[2 * i + 2] = s.charAt(i);
			}
			t[s.length() * 2 + 1] = '#';
			return t;
		}

		public String longestPalindrome(String s) {
			char[] t = preprocess(s);
			int[] p = new int[t.length];

			int center = 0, right = 0;
			for (int i = 1; i < t.length - 1; i++) {
				int mirror = 2 * center - i;

				if (right > i) {
					p[i] = Math.min(right - i, p[mirror]);
				}

				// attempt to expand palindrome centered at i
				while (t[i + (1 + p[i])] == t[i - (1 + p[i])]) {
					p[i]++;
				}

				// if palindrome centered at i expands past right,
				// adjust center based on expanded palindrome.
				if (i + p[i] > right) {
					center = i;
					right = i + p[i];
				}
			}
			return longestPalindromicSubstring(p, s);
		}

		public String longestPalindromicSubstring(int[] p, String s) {
			int length = 0; // length of longest palindromic substring
			int center = 0; // center of longest palindromic substring
			for (int i = 1; i < p.length - 1; i++) {
				if (p[i] > length) {
					length = p[i];
					center = i;
				}
			}
			return s.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
		}

	}

	class ExpandAroundCenterSolution {

		public String longestPalindrome(String s) {
			if (s == null || s.length() < 1)
				return "";
			int start = 0, end = 0;
			for (int i = 0; i < s.length(); i++) {
				int len1 = expandAroundCenter(s, i, i);
				int len2 = expandAroundCenter(s, i, i + 1);
				int len = Math.max(len1, len2);
				if (len > end - start) {
					start = i - (len - 1) / 2;
					end = i + len / 2;
				}
			}
			return s.substring(start, end + 1);
		}

		private int expandAroundCenter(String s, int l, int r) {
			while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
				l--;
				r++;
			}
			return r - l - 1;
		}

	}

}

class MaxProfit {
	public int maxProfit(int[] prices) {
		int max = 0, min = prices[0];
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] < min) {
				min = prices[i];
			} else if (prices[i] - min > max) {
				max = prices[i] - min;
			}
		}
		return max;
	}
}

class MaxSubArray {
	public int maxSubArray(int[] nums) {
		int sum = 0, max = Integer.MIN_VALUE;
		for (int i : nums) {
			sum = Integer.max(i, sum + i);
			max = Integer.max(max, sum);
		}
		return max;
	}
}

class MedianOfTwoSortedArrays {
	public double findMedianSortedArrays(int[] A, int[] B) {
		int m = A.length;
		int n = B.length;
		if (m > n) { // to ensure m<=n
			int[] temp = A;
			A = B;
			B = temp;
			int tmp = m;
			m = n;
			n = tmp;
		}
		int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
		while (iMin <= iMax) {
			int i = (iMin + iMax) / 2;
			int j = halfLen - i;
			if (i < iMax && B[j - 1] > A[i]) {
				iMin = i + 1; // i is too small
			} else if (i > iMin && A[i - 1] > B[j]) {
				iMax = i - 1; // i is too big
			} else { // i is perfect
				int maxLeft = 0;
				if (i == 0) {
					maxLeft = B[j - 1];
				} else if (j == 0) {
					maxLeft = A[i - 1];
				} else {
					maxLeft = Math.max(A[i - 1], B[j - 1]);
				}
				if ((m + n) % 2 == 1) {
					return maxLeft;
				}
				int minRight = 0;
				if (i == m) {
					minRight = B[j];
				} else if (j == n) {
					minRight = A[i];
				} else {
					minRight = Math.min(B[j], A[i]);
				}
				return (maxLeft + minRight) / 2.0;
			}
		}
		return 0;
	}
}

class Merge2SortedLists {

	class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1 == null && l2 == null) {
			return null;
		}
		if (l1 == null && l2 != null) {
			return l2;
		}
		if (l1 != null && l2 == null) {
			return l1;
		}
		ListNode res = new ListNode(), cur = res;
		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				cur.next = l1;
				l1 = l1.next;
			} else {
				cur.next = l2;
				l2 = l2.next;
			}
			cur = cur.next;
		}
		cur.next = l1 == null ? l2 : l1;
		return res.next;
	}

}

class NumIslands {

	public int numIslands(char[][] grid) {
		if (grid == null || grid.length == 0) {
			return 0;
		}
		int nr = grid.length, nc = grid[0].length, res = 0;
		for (int r = 0; r < nr; r++) {
			for (int c = 0; c < nc; c++) {
				if (grid[r][c] == '1') {
					res++;
					dfs(grid, r, c, nr, nc);
				}
			}
		}
		return res;
	}

	public void dfs(char[][] grid, int r, int c, int nr, int nc) {
		if (r < 0 || r >= nr || c < 0 || c >= nc || grid[r][c] == '0') {
			return;
		}
		grid[r][c] = '0';
		dfs(grid, r + 1, c, nr, nc);
		dfs(grid, r - 1, c, nr, nc);
		dfs(grid, r, c + 1, nr, nc);
		dfs(grid, r, c - 1, nr, nc);
	}

}

class ProductExceptSelf {
	public int[] productExceptSelf(int[] nums) {
		int l = nums.length, R = 1;
		int[] r = new int[l];
		r[0] = 1;
		for (int i = 1; i < l; i++) {
			r[i] = nums[i - 1] * r[i - 1];
		}
		for (int i = l - 1; i >= 0; i--) {
			r[i] *= R;
			R *= nums[i];
		}
		return r;
	}
}

class ReduceArraySizeToTheHalf {
	public int minSetSize(int[] arr) {
		if (arr.length <= 1) {
			return arr.length;
		} else if (arr.length == 2) {
			return 1;
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int x : arr) {
			map.put(x, map.getOrDefault(x, 0) + 1);
		}
		int[] freq = new int[map.values().size()];
		int i = 0;
		for (int f : map.values())
			freq[i++] = f;
		Arrays.sort(freq);
		int ans = 0, removed = 0, half = arr.length / 2;
		i = freq.length - 1;
		while (removed < half) {
			ans += 1;
			removed += freq[i--];
		}
		return ans;
	}
}

class ReverseInteger {
	public int reverse(int x) {
		int l, r = 0;
		for (; x != 0; x /= 10) {
			l = x % 10;
			if (r > Integer.MAX_VALUE / 10 || (r == Integer.MAX_VALUE / 10 && l > 7)) {
				return 0;
			}
			if (r < Integer.MIN_VALUE / 10 || (r == Integer.MIN_VALUE / 10 && l < -8)) {
				return 0;
			}
			r = r * 10 + l;
		}
		return r;
	}
}

class RomanToInteger {

	public static void main(String[] args) {
		System.out.println(romanToInt("iv"));
	}

	public static int romanToInt(String s) {
		char[] a = s.toUpperCase().toCharArray();
		int n = 0, l = a.length;
		for (int i = 0; i < l; i++) {
			if (a[i] == 'M') {
				n += 1000;
			} else if (i < l - 1 && a[i] == 'C' && a[i + 1] == 'M') {
				n += 900;
				i++;
			} else if (i < l - 1 && a[i] == 'C' && a[i + 1] == 'D') {
				n += 400;
				i++;
			} else if (a[i] == 'D') {
				n += 500;
			} else if (a[i] == 'C') {
				n += 100;
			} else if (i < l - 1 && a[i] == 'X' && a[i + 1] == 'C') {
				n += 90;
				i++;
			} else if (i < l - 1 && a[i] == 'X' && a[i + 1] == 'L') {
				n += 40;
				i++;
			} else if (a[i] == 'L') {
				n += 50;
			} else if (a[i] == 'X') {
				n += 10;
			} else if (i < l - 1 && a[i] == 'I' && a[i + 1] == 'X') {
				n += 9;
				i++;
			} else if (i < l - 1 && a[i] == 'I' && a[i + 1] == 'V') {
				n += 4;
				i++;
			} else if (a[i] == 'V') {
				n += 5;
			} else if (a[i] == 'I') {
				n += 1;
			}
		}
		return n;
	}

}

class StringToIntegerAtoi {
	public int myAtoi(String str) {
		char[] a = str.toCharArray(); // convert to char array
		int i = 0; // index
		for (; i < a.length && a[i] == ' '; i++)
			; // get index of first non ' ' character
		if (i == a.length) {
			return 0; // if index = length, string is full of white spaces
		}
		boolean neg = false; // negative flag
		if (a[i] == '-') { // if first non ' ' is '-'
			neg = true; // set negative flag to true
			i++;// move on to next index which should be a number
		} else if (a[i] == '+') { // if first non ' ' is the optional '+'
			i++; // move on to next index
		} else if (a[i] < '0' || a[i] > '9') {
			return 0; // if not a digit
		}

		int r = 0; // result
		for (; i < a.length && a[i] >= '0' && a[i] <= '9'; i++) {
			if (r > (Integer.MAX_VALUE - a[i] + '0') / 10) { // check for possible overflow
				return (neg ? Integer.MIN_VALUE : Integer.MAX_VALUE);
			}
			r = r * 10 + a[i] - '0'; // shift r to the left and add the current digit
		}
		return neg ? -r : r; // return result with its right sign
	}
}

class ThreeSum {
	
	public List<List<Integer>> threeSum(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < nums.length && nums[i] <= 0; i++) {
			if (i == 0 || nums[i - 1] != nums[i]) {
				threeSum(nums, res, i);
			}
		}
		return res;
	}

	private void threeSum(int[] nums, List<List<Integer>> res, int i) {
		int lo = i + 1, hi = nums.length - 1;
		while (lo < hi) {
			int sum = nums[i] + nums[lo] + nums[hi];
			if (sum < 0) {
				lo++;
			} else if (sum > 0) {
				hi--;
			} else {
				res.add(Arrays.asList(nums[i], nums[lo++], nums[hi--]));
				while (lo < hi && nums[lo] == nums[lo - 1]) {
					lo++;
				}
			}
		}
	}

}

class TwoSum {
	public int[] twoSum(int[] nums, int target) {
        // hash map with (value, index) pairs
        HashMap<Integer, Integer> map = new HashMap<>();
        // loop through the array
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // if complement is a key in the map
            if (map.containsKey(complement)) {
                // return the index of the complement, and index of current num as an array
                return new int[] {map.get(complement), i};
            }
            // else add the current (number, index) pair to the map
            map.put(nums[i], i);
        }
        return null;
	}
}

class ValidParentheses {
	public boolean isValid(String s) {
		if (s.equals("")) {
			return true;
		}
		HashMap<Character, Character> map = new HashMap<>();
		map.put(')', '(');
		map.put(']', '[');
		map.put('}', '{');
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (map.containsKey(c)) {
				if (!map.get(c).equals(stack.isEmpty() ? '0' : stack.pop())) {
					return false;
				}
			} else {
				stack.push(c);
			}
		}
		return stack.isEmpty();
	}
}

class ZigZagConversion {
	public String convert(String s, int n) {
		String[] strs = new String[n];
		for (int i = 0; i < n; i++) {
			strs[i] = "";
		}
		int idx = 0;
		while (idx < s.length()) {
			for (int i = 0; i < n && idx < s.length(); i++) {
				strs[i] += s.charAt(idx++);
			}
			for (int i = n - 2; i > 0 && idx <s.length(); i--) {
				strs[i] += s.charAt(idx++);
			}
		}
		String r = "";
		for (int i = 0; i < n; i++) {
			r += strs[i];
		}
		return r;
	}
}