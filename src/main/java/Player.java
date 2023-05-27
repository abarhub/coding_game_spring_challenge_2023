import java.util.*;

class Case {

    int no;
    int type;
    int initialResources;
    int neigh0 = -1;
    int neigh1 = -1;
    int neigh2 = -1;
    int neigh3 = -1;
    int neigh4 = -1;
    int neigh5 = -1;

    @Override
    public String toString() {
        return "no=" + no + ",initialResources=" + initialResources + ",neigh0=" + neigh0 +
                ",neigh1=" + neigh1 + ",neigh2=" + neigh2 + ",neigh3=" + neigh3 + ",neigh4=" + neigh4 + ",neigh5=" + neigh5;
    }
}

class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Graph.class.getSimpleName() + "[", "]")
                .add("nodes=" + nodes)
                .toString();
    }
}

class Node {

    private String name;

    private LinkedList<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("shortestPath=" + shortestPath)
                .add("distance=" + distance)
                .add("adjacentNodes=" + adjacentNodes)
                .toString();
    }
}

class Dijkstra {

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int numberOfCells = in.nextInt(); // amount of hexagonal cells in this map   
        Map<Integer, Case> map = new HashMap<Integer, Case>();
        List<Integer> caseBase = new ArrayList<>();
        List<Integer> caseBaseOpposant = new ArrayList<>();


        for (int i = 0; i < numberOfCells; i++) {
            int type = in.nextInt(); // 0 for empty, 1 for eggs, 2 for crystal
            int initialResources = in.nextInt(); // the initial amount of eggs/crystals on this cell
            int neigh0 = in.nextInt(); // the index of the neighbouring cell for each direction
            int neigh1 = in.nextInt();
            int neigh2 = in.nextInt();
            int neigh3 = in.nextInt();
            int neigh4 = in.nextInt();
            int neigh5 = in.nextInt();
            Case c = new Case();
            c.no = i;
            c.initialResources = initialResources;
            c.type = type;
            c.neigh0 = neigh0;
            c.neigh1 = neigh1;
            c.neigh2 = neigh2;
            c.neigh3 = neigh3;
            c.neigh4 = neigh4;
            c.neigh5 = neigh5;
            map.put(i, c);
        }
        int numberOfBases = in.nextInt();
        for (int i = 0; i < numberOfBases; i++) {
            int myBaseIndex = in.nextInt();
            caseBase.add(myBaseIndex);
        }
        for (int i = 0; i < numberOfBases; i++) {
            int oppBaseIndex = in.nextInt();
            caseBaseOpposant.add(oppBaseIndex);
        }

        System.err.println("liste cases:" + map);
//        System.err.println("caseBase:" + caseBase);
//        System.err.println("caseBaseOpposants:" + caseBaseOpposant);

        int caseFinale = -1;
        // game loop
        while (true) {
            int noCase = -1;

            Map<Integer, Node> map2 = new HashMap<Integer, Node>();
            Set<Integer> set = new HashSet<Integer>();

            int totalMyAnts = 0;

            for (int i = 0; i < numberOfCells; i++) {
                Node n = new Node("" + i);
                map2.put(i, n);
                int resources = in.nextInt(); // the current amount of eggs/crystals on this cell
                int myAnts = in.nextInt(); // the amount of your ants on this cell
                int oppAnts = in.nextInt(); // the amount of opponent ants on this cell
                //System.err.println("case:" + i + ",ressource:" + resources + ",myAnts:" + myAnts + ",oppAnts:" + oppAnts);
                totalMyAnts += myAnts;
                if (resources > 0 && noCase == -1) {
                    noCase = i;
                }
                if (caseFinale == -1 && myAnts > 0) {
                    caseFinale = i;
                }
                if (resources > 0) {
                    if (map.get(i).type > 0) {
                        set.add(i);
                    }
                }
            }

//            System.err.println("map=" + map);
//            System.err.println("totalMyAnts=" + totalMyAnts);
//            System.err.println("map2=" + map2);
//            System.err.println("set=" + set);


            Graph graph = new Graph();
            for (int i = 0; i < numberOfCells; i++) {
                Node n = map2.get(i);
                Case c = map.get(i);
                if (c != null) {
                    addDestination(map2, n, c.neigh0);
                    addDestination(map2, n, c.neigh1);
                    addDestination(map2, n, c.neigh2);
                    addDestination(map2, n, c.neigh3);
                    addDestination(map2, n, c.neigh4);
                    addDestination(map2, n, c.neigh5);
                }
                graph.addNode(n);
            }

//            System.err.println("graph=" + graph);

            if (set.isEmpty()) {
                System.out.println("WAIT");
            } else {
                Integer base = caseBase.get(0);
                Map<Integer, List<Integer>> map3 = new TreeMap<Integer, List<Integer>>();
                Node n = map2.get(base);
                graph = Dijkstra.calculateShortestPathFromSource(graph, n);

                for (Integer no : set) {
                    Optional<Node> n2 = graph.getNodes().stream().filter(x -> Objects.equals(x.getName(), "" + no)).findFirst();
                    if (n2.isPresent()) {
                        Integer len = n2.get().getDistance();
                        if (len < 100_000) {
                            if (map3.containsKey(len)) {
                                map3.get(len).add(no);
                            } else {
                                map3.put(len, new ArrayList<>());
                                map3.get(len).add(no);
                            }
                        }
                    }
                }
//                System.err.println("map3="+map3);
                String s = "";
                boolean fin = false;
                int nb = 0;
                int max = 5;
                int nbAnts = totalMyAnts;
                List<Integer> list = new ArrayList<>();
                for (Map.Entry<Integer, List<Integer>> entry : map3.entrySet()) {
                    int len = entry.getKey();
                    if (len < 100_000) {
                        int len2 = len;
                        List<Integer> liste2 = new ArrayList<>();
                        liste2.addAll(entry.getValue());
//                        for (Integer no : entry.getValue()) {
//                            Case c = map.get(no);
//                            if (c.type == 1) {// les oeufs au debut
//                                liste2.add(0, no);
//                            } else if (c.type == 2) {// les resources à la fin
//                                liste2.add(no);
//                            }
//                        }
                        for (Integer no : liste2) {
                            int poids = 0;
                            Case c = map.get(no);
                            int type = c.type;
                            if (type == 1) {
                                poids = 2;
                                //poids = 1;
                            } else if (type == 2) {
                                poids = 1;
                            }
                            if (poids > 0) {
                                list.add(no);
                                int src = base;
                                if (len2 > 1) {
                                    for (int n2 : list) {
                                        if (c.neigh0 == n2 || c.neigh1 == n2 ||
                                                c.neigh2 == n2 || c.neigh3 == n2 ||
                                                c.neigh4 == n2 || c.neigh5 == n2) {
                                            src = n2;
                                            // la distance est de 1 en plus
                                            //len2 = 1;
                                            break;
                                        }
                                    }
                                }

                                if (s.length() > 0) {
                                    s += ";";
                                }
                                s += "LINE " + no + " " + src + " " + poids;
                                nb++;
                            }

                            nbAnts -= len2;

                            if (nbAnts <= 0) {
                                fin = true;
                                System.err.println("fin, nbAnts=" + nbAnts + ",totalMyAnts=" + totalMyAnts);
                                break;
                            }
                        }
                    } else {
                        fin = true;
                        System.err.println("fin, len=" + len);
                    }
                    if (fin) {
                        break;
                    }
                }
                if (s == null || s.isEmpty()) {
                    s = "WAIT";
                }
                System.out.println(s);
            }

        }
    }

    private static void addDestination(Map<Integer, Node> map2, Node n, int neigh) {
        if (neigh >= 0) {
            Node n2 = map2.get(neigh);
            n.addDestination(n2, 1);
        }
    }


}