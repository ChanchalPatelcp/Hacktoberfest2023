import java.util.*;

public class WordLadderII {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        List<List<String>> result = new ArrayList<>();
        if (!wordSet.contains(endWord)) return result;

        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> distance = new HashMap<>();

        bfs(beginWord, endWord, wordSet, graph, distance);
        List<String> path = new ArrayList<>();
        dfs(beginWord, endWord, graph, distance, path, result);
        return result;
    }

    private void bfs(String start, String end, Set<String> wordSet, 
                     Map<String, List<String>> graph, Map<String, Integer> distance) {
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        distance.put(start, 0);

        for (String word : wordSet) graph.put(word, new ArrayList<>());
        graph.put(start, new ArrayList<>());

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currDist = distance.get(curr);
            for (String next : getNextWords(curr, wordSet)) {
                graph.get(curr).add(next);
                if (!distance.containsKey(next)) {
                    distance.put(next, currDist + 1);
                    queue.offer(next);
                }
            }
        }
    }

    private void dfs(String curr, String end, Map<String, List<String>> graph,
                     Map<String, Integer> distance, List<String> path, List<List<String>> result) {
        path.add(curr);
        if (curr.equals(end)) result.add(new ArrayList<>(path));
        else {
            for (String next : graph.get(curr)) {
                if (distance.get(next) == distance.get(curr) + 1)
                    dfs(next, end, graph, distance, path, result);
            }
        }
        path.remove(path.size() - 1);
    }

    private List<String> getNextWords(String word, Set<String> wordSet) {
        List<String> res = new ArrayList<>();
        char[] arr = word.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char old = arr[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == old) continue;
                arr[i] = c;
                String newWord = new String(arr);
                if (wordSet.contains(newWord)) res.add(newWord);
            }
            arr[i] = old;
        }
        return res;
    }
}
