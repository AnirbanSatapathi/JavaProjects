package projects;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

public class MazePathfinder {
    static char[][] maze = {
            {'#', ' ', '#', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', '#', 'O', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'},
            {'#', ' ', '#', '#', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#'},
            {'#', '#', '#', '#', '#', '#', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', 'X', '#', '#', '#', '#', '#', '#', '#'}
    };

    public static void main(String[] args) {
        MazePathfinder pathfinder = new MazePathfinder();
        LinkedList<int[]> path = pathfinder.findPath();
        pathfinder.printMaze(path);
    }

    public void printMaze(LinkedList<int[]> path) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (isInPath(path, i, j)) {
                    System.out.print('X' + " ");
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean isInPath(LinkedList<int[]> path, int row, int col) {
        for (int[] p : path) {
            if (p[0] == row && p[1] == col) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<int[]> findPath() {
        int[] start = findStart('O');
        int[] end = findStart('X');
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        LinkedList<int[]> path = new LinkedList<>();

        queue.add(new Node(start, path));
        visited.add(start[0] + "," + start[1]);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int[] pos = current.pos;
            path = current.path;

            if (pos[0] == end[0] && pos[1] == end[1]) {
                return path;
            }

            printMaze(path);
            try {
                Thread.sleep(400); // Delay to observe the algorithm
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();

            for (int[] neighbor : findNeighbors(pos)) {
                if (!visited.contains(neighbor[0] + "," + neighbor[1]) && maze[neighbor[0]][neighbor[1]] != '#') {
                    visited.add(neighbor[0] + "," + neighbor[1]);
                    LinkedList<int[]> newPath = new LinkedList<>(path);
                    newPath.add(neighbor);
                    queue.add(new Node(neighbor, newPath));
                }
            }
        }
        return path;
    }

    public int[] findStart(char start) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == start) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public int[][] findNeighbors(int[] pos) {
        int row = pos[0];
        int col = pos[1];
        int[][] neighbors = new int[4][2];
        int index = 0;

        if (row > 0) neighbors[index++] = new int[]{row - 1, col}; // up
        if (row < maze.length - 1) neighbors[index++] = new int[]{row + 1, col}; // down
        if (col > 0) neighbors[index++] = new int[]{row, col - 1}; // left
        if (col < maze[0].length - 1) neighbors[index++] = new int[]{row, col + 1}; // right

        int[][] result = new int[index][2];
        System.arraycopy(neighbors, 0, result, 0, index);
        return result;
    }

    static class Node {
        int[] pos;
        LinkedList<int[]> path;

        Node(int[] pos, LinkedList<int[]> path) {
            this.pos = pos;
            this.path = path;
        }
    }
}