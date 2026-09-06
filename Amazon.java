/**
 * This is an Amazon focused collection of leetcode questions.
 */

import java.util.*;

// can be optimized with math instead of if statements
// T: O(n) S: O(1)
class RobotBoundInCircle {
	public boolean isRobotBounded(String inst) {
		int x = 0;
		int y = 0;
		int dir = 0; // 0: n, 1: e, 2: s, 3: s
		for (char c : inst.toCharArray()) {
			switch (c) {
			case 'G':
				x += dir == 1 ? 1 : dir == 3 ? -1 : 0;
				y += dir == 0 ? 1 : dir == 2 ? -1 : 0;
				break;
			case 'L':
				dir = --dir < 0 ? dir + 4 : dir;
				break;
			default:
				dir = ++dir > 3 ? dir - 4 : dir;
			}
		}
		// displacement is 0 or dir not n
		return x == 0 && y == 0 || dir != 0;
	}
}

class LRUCache {

	Node root;
	HashMap<Integer, Node> cache;
	int capacity;

	public LRUCache(int capacity) {
		this.cache = new HashMap<>();
		this.root = new Node(-1, -1);
		this.capacity = capacity;
	}

	class Node {
		int key, val;
		Node prev, next;

		public Node(int k, int v) {
			this.key = k;
			this.val = v;
			this.prev = this;
			this.next = this;
		}
	}

	private void remove(Node n) {
		n.prev.next = n.next;
		n.next.prev = n.prev;
		n = null;
	}

	private void add(Node n) {
		n.prev = root;
		root.next.prev = n;
		n.next = root.next;
		root.next = n;
	}

	public int get(int key) {
		if (cache.containsKey(key)) {
			Node node = cache.get(key);
			remove(node);
			add(node);
			return node.val;
		} else {
			return -1;
		}
	}

	public void put(int key, int value) {
		if (cache.containsKey(key)) {
			Node node = cache.get(key);
			remove(node);
			add(node);
			node.val = value;
		} else {
			if (cache.size() == capacity) {
				Node n = root.prev;
				cache.remove(n.key);
				remove(n);
			}
			Node node = new Node(key, value);
			cache.put(key, node);
			add(node);
		}
	}
}

// O(n^2), O(n)
class NumberOfProvinces {
	public int findCircleNum(int[][] M) {
		int n = M.length, count = n;
		int[] roots = new int[n];
		for (int i = 0; i < n; i++) {
			roots[i] = i;
		}
		// loop through nodes
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (M[i][j] == 1) { // edge exists
					// union find
					int rootI = find(roots, i), rootJ = find(roots, j);
					if (rootI != rootJ) {
						roots[rootJ] = rootI;
						count--;
					}
				}
			}
		}
		return count;
	}

	private int find(int[] roots, int i) {
		while (i != roots[i]) {
			i = roots[roots[i]];
		}
		return i;
	}
}

// O(n log n), O(n)
class MergeIntervals {
	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		LinkedList<int[]> merged = new LinkedList<>();
		merged.add(intervals[0]);
		for (int i = 1; i < intervals.length; i++) {
			if (merged.getLast()[1] < intervals[i][0]) {
				merged.add(intervals[i]);
			} else {
				merged.getLast()[1] = Math.max(merged.getLast()[1], intervals[i][1]);
			}
		}
		return merged.toArray(new int[merged.size()][]);
	}
}

// O(n log n), O(k)
class KClosestPointsToOrigin {
	public int[][] kClosest(int[][] points, int k) {
		Arrays.sort(points, (a, b) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
		return Arrays.copyOf(points, k);
	}
}

// O(n log n), O(1)
class MaximumUnitsOnATruck {
	public int maximumUnits(int[][] boxTypes, int truckSize) {
		Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
		int count = 0;
		for (int[] box : boxTypes) {
			count += Math.min(truckSize, box[0]) * box[1];
			if ((truckSize -= box[0]) <= 0) {
				return count;
			}
		}
		return count;
	}
}

// O(n log n), O(n)
class MeetingRoomsII {
	public int minMeetingRooms(int[][] intervals) {
		int[] starts = new int[intervals.length];
		int[] ends = new int[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			starts[i] = intervals[i][0];
			ends[i] = intervals[i][1];
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int rooms = 0;
		int endsI = 0;
		for (int i = 0; i < starts.length; i++) {
			if (starts[i] < ends[endsI]) {
				rooms++;
			} else {
				endsI++;
			}
		}
		return rooms;
	}
}

// O(m * n), O(n)
class SearchSuggestionSystem {
	public List<List<String>> suggestedProducts(String[] products, String searchWord) {
		Arrays.sort(products);
		int a = 0, b = products.length - 1;
		List<List<String>> res = new ArrayList<>();
		for (int i = 0; i < searchWord.length(); i++) {
			// lower bound
			while (a <= b && (products[a].length() <= i || products[a].charAt(i) != searchWord.charAt(i))) {
				a++;
			}
			// upper bound
			while (a <= b && (products[b].length() <= i || products[b].charAt(i) != searchWord.charAt(i))) {
				b--;
			}
			res.add(new ArrayList<>());
			for (int j = a; j <= b && j < a + 3; j++) {
				res.get(i).add(products[j]);
			}
		}
		return res;
	}
}

// O(log n), O(1)
class MinimumCostToConnectSticks {
	public int connectSticks(int[] sticks) {
		int cost = 0, i, j;
		PriorityQueue<Integer> q = new PriorityQueue<>();
		for (int s : sticks) {
			q.offer(s);
		}
		while (q.size() > 1) {
			i = q.poll();
			j = q.poll();
			cost += i + j;
			q.offer(i + j);
		}
		return cost;
	}
}

class MergeKSortedLists {

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

	public ListNode mergeKLists(ListNode[] lists) {
		Queue<ListNode> q = new PriorityQueue<ListNode>((a, b) -> a.val - b.val);
		for (ListNode l : lists) {
			if (l != null) {
				q.add(l);
			}
		}
		ListNode head = new ListNode(0);
		ListNode point = head;
		while (!q.isEmpty()) {
			point.next = q.poll();
			point = point.next;
			ListNode next = point.next;
			if (next != null) {
				q.add(next);
			}
		}
		return head.next;
	}
}

class AnalyzeUserWebsiteVisitPattern {

	class Pair {
		int time;
		String web;

		public Pair(int time, String web) {
			this.time = time;
			this.web = web;
		}
	}

	public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
		Map<String, List<Pair>> map = new HashMap<>();
		int n = username.length;
		// collect the website info for every user, key: username, value: (timestamp,
		// website)
		for (int i = 0; i < n; i++) {
			map.putIfAbsent(username[i], new ArrayList<>());
			map.get(username[i]).add(new Pair(timestamp[i], website[i]));
		}
		// count map to record every 3 combination occurring time for the different
		// user.
		Map<String, Integer> count = new HashMap<>();
		String res = "";
		for (String key : map.keySet()) {
			Set<String> set = new HashSet<>();
			// this set is to avoid visit the same 3-seq in one user
			List<Pair> list = map.get(key);
			Collections.sort(list, (a, b) -> (a.time - b.time)); // sort by time
			// brutal force O(N ^ 3)
			for (int i = 0; i < list.size(); i++) {
				for (int j = i + 1; j < list.size(); j++) {
					for (int k = j + 1; k < list.size(); k++) {
						String str = list.get(i).web + " " + list.get(j).web + " " + list.get(k).web;
						if (!set.contains(str)) {
							count.put(str, count.getOrDefault(str, 0) + 1);
							set.add(str);
						}
						if (res.equals("") || count.get(res) < count.get(str)
								|| (count.get(res) == count.get(str) && res.compareTo(str) > 0)) {
							// make sure the right lexi order
							res = str;
						}
					}
				}
			}
		}
		// grab the right answer
		String[] r = res.split(" ");
		List<String> result = new ArrayList<>();
		for (String str : r) {
			result.add(str);
		}
		return result;
	}
}

// O(m log m) + O(n log n)
class MaximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts {
	public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
		Arrays.sort(horizontalCuts);
		Arrays.sort(verticalCuts);
		int n = horizontalCuts.length;
		int m = verticalCuts.length;
		long maxHeight = Math.max(horizontalCuts[0], h - horizontalCuts[n - 1]);
		for (int i = 1; i < n; i++) {
			maxHeight = Math.max(maxHeight, horizontalCuts[i] - horizontalCuts[i - 1]);
		}
		long maxWidth = Math.max(verticalCuts[0], w - verticalCuts[m - 1]);
		for (int i = 1; i < m; i++) {
			maxWidth = Math.max(maxWidth, verticalCuts[i] - verticalCuts[i - 1]);
		}
		return (int) ((maxWidth * maxHeight) % (1000000007));
	}
}

// O(n), O(1)
class MaximumSubarray {
	public int maxSubArray(int[] nums) {
		int sum = 0, max = Integer.MIN_VALUE;
		for (int i : nums) {
			sum = Integer.max(i, sum + i);
			max = Integer.max(max, sum);
		}
		return max;
	}
}

// sliding window O(n), O(1)
class LongestSubstringWithoutRepeatingCharacters {
	public int lengthOfLongestSubstring(String s) {
		int r = 0;
		// ascii 128 map
		int[] map = new int[128];
		for (int i = 0, j = 0; j < s.length(); j++) {
			// set i to be where char at j has already appeared
			i = Math.max(map[s.charAt(j)], i);
			// compute substring length
			r = Math.max(r, j - i + 1);
			// put current index of char into map
			map[s.charAt(j)] = j + 1;
		}
		return r;
	}
}

// O(max(m, n))
class AddTwoNumbers {

	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		int sum = 0;
		ListNode r = new ListNode(-1), c = r;
		while (l1 != null || l2 != null) {
			sum += (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
			c.next = new ListNode(sum % 10);
			c = c.next;
			sum /= 10;
			l1 = l1 == null ? null : l1.next;
			l2 = l2 == null ? null : l2.next;
		}
		if (sum > 0) {
			c.next = new ListNode(sum);
		}
		return r.next;
	}
}

// O(m * n), can be improved with adjacency list 
class CourseSchedule {
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		int[] indegree = new int[numCourses];
		for (int[] pre : prerequisites) {
			indegree[pre[0]]++;
		}
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0)
				queue.offer(i);
		}
		int count = queue.size();
		while (!queue.isEmpty()) {
			int prereq = queue.poll();
			for (int[] pre : prerequisites) {
				if (pre[1] == prereq) {
					indegree[pre[0]]--;
					if (indegree[pre[0]] == 0) {
						queue.add(pre[0]);
						count++;
					}
				}
			}
		}
		return count == numCourses;
	}
}

// O(m log n)
class ReorderDataInLogFiles {
	public String[] reorderLogFiles(String[] logs) {
		Comparator<String> logCmp = new Comparator<>() {
			@Override
			public int compare(String l1, String l2) {
				// split logs into [identifier, content] arrays
				String[] s1 = l1.split(" ", 2), s2 = l2.split(" ", 2);
				// check if logs are digit logs or not
				boolean d1 = Character.isDigit(s1[1].charAt(0)), d2 = Character.isDigit(s2[1].charAt(0));
				// both letter logs
				if (!d1 && !d2) {
					int r = s1[1].compareTo(s2[1]);
					if (r != 0) { // content different
						return r;
					} // content same, compare identifier
					return s1[0].compareTo(s2[0]);
				} else if (!d1 && d2) { // l1 is letter, l2 is digit
					return -1; // l1 comes before l2
				} else if (d1 && !d2) { // l1 is digit, l2 is letter
					return 1; // l1 comes after l2
				} // both are digit
				return 0; // order does not change
			}
		};

		Arrays.sort(logs, logCmp);
		return logs;
	}
}

// O(n), O(1)
class CountBinarySubstrings {
	public int countBinarySubstrings(String s) {
		int prevRunLength = 0, curRunLength = 1, res = 0;
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == s.charAt(i - 1)) {
				curRunLength++;
			} else {
				prevRunLength = curRunLength;
				curRunLength = 1;
			}
			if (prevRunLength >= curRunLength) {
				res++;
			}
		}
		return res;
	}
}

// O(n ^ 2), O(1)
class SumOfSubarrayRanges {
	public long subArrayRanges(int[] A) {
		long res = 0;
		for (int i = 0; i < A.length; i++) {
			int max = A[i], min = A[i];
			for (int j = i + 1; j < A.length; j++) {
				max = Math.max(max, A[j]);
				min = Math.min(min, A[j]);
				res += max - min;
			}
		}
		return res;
	}
}

// O(sqrt(n)), O(min(k, sqrt(n)))
class TheKthFactorOfN {
	public int kthFactor(int n, int k) {
		int sqrtN = (int) Math.sqrt(n);
		List<Integer> divisors = new ArrayList<>(Math.min(k, sqrtN));
		for (int x = 1; x < sqrtN + 1; ++x) {
			if (n % x == 0) {
				divisors.add(x);
				if (--k == 0) {
					return x;
				}
			}
		}
		// if n is perfect sq
		if (sqrtN * sqrtN == n) {
			k++; // skip dupe sqrt
		}
		int nDiv = divisors.size();
		return (k <= nDiv) ? n / divisors.get(nDiv - k) : -1;
	}
}

// O(k + n)
class RangeAddition {
	public int[] getModifiedArray(int length, int[][] updates) {
		int[] arr = new int[length];
		for (int[] update : updates) {
			// set up starts and ends to change
			arr[update[0]] += update[2];
			if (update[1] < length - 1) {
				arr[update[1] + 1] -= update[2];
			}
		}
		// carry over the sum of changes into each pos
		for (int i = 0, sum = 0; i < length; i++) {
			arr[i] += sum;
			sum = arr[i];
		}
		return arr;
	}
}

// O(n), O(1)
class MinimumSwapsToGroupAll1sTogether {
	public int minSwaps(int[] data) {
		int ones = 0, cnt_one = 0, max_one = 0, left = 0, right = 0;
		for (int i : data) {
			ones += i;
		}
		while (right < data.length) {
			// updating the number of 1's by adding the new element
			cnt_one += data[right++];
			// maintain the length of the window to ones
			if (right - left > ones) {
				// updating the number of 1's by removing the oldest element
				cnt_one -= data[left++];
			}
			// record the maximum number of 1's in the window
			max_one = Math.max(max_one, cnt_one);
		}
		return ones - max_one;
	}
}

// O(n), O(1)
class FlipStringToMonotoneIncreasing {
	public int minFlipsMonoIncr(String s) {
		int ones = 0, flips = 0;
		for (char c : s.toCharArray()) {
			if (c == '1') {
				ones++;
			} else {
				flips++;
			}
			flips = flips < ones ? flips : ones;
		}
		return flips;
	}
}
