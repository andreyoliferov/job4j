import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraphBuilder

/**
 * @autor aoliferov* @since 21.07.2019
 * Алгоритм Дейкстры, поиск кратчайшего пути для взвещенных направленных графов.
 */
class Dijkstra {

    Integer resultValue
    Deque<String> resultWay

    List<String> execute(String n1, String n2, MutableValueGraph<String, Integer> grahp) {
        def parents = new HashMap<String, String>()
        def costs = new HashMap<String, Integer>()
        def processed = new ArrayList<String>()

        def neighbors = grahp.successors(n1)
        neighbors.forEach {
            parents.put(it, n1)
            costs.put(it, grahp.edgeValue(n1, it).get())
        }
        processed.add(n1)
        processed.add(n2)
        def node = findLowestCostNone(costs, processed)

        while (node != null && !processed.contains(node)) {
            def cost = costs.get(node)
            neighbors = grahp.successors(node)
            neighbors.forEach {
                def newCost = cost + grahp.edgeValue(node, it).get()
                if (!costs.containsKey(it) || costs.get(it) > newCost) {
                    costs.put(it, newCost)
                    parents.put(it, node)
                }
            }
            processed.add(node)
            node = findLowestCostNone(costs, processed)
        }

        resultValue = costs.get(n2)
        if (resultValue != null) {
            resultWay = new LinkedList<>()
            def next = n2
            resultWay.add(n2)
            while (next != null && next != n1) {
                next = parents.get(next)
                resultWay.add(next)
            }
        }
        resultWay
    }

    String findLowestCostNone(HashMap<String, Integer> costs, ArrayList<String> proccessed) {
        def lowestCost = Integer.MAX_VALUE
        def lowestCostNode = null
        costs.keySet().forEach {
            def cost = costs.get(it)
            if (cost < lowestCost && !proccessed.contains(it)) {
                lowestCost = cost
                lowestCostNode = it
            }
        }
        return lowestCostNode
    }

    static void main(String[] args) {
        /**
         * Иницализация направленного графа
         * ValueGraph
         * Network - граф с уникальными гранями
         */
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                .allowsSelfLoops(false)
                .build()

        /**
         * Добавление данных в граф
         */
        graph.putEdgeValue("A", "B", 1)
        graph.putEdgeValue("A", "C", 3)
        graph.putEdgeValue("A", "D", 4)
        graph.putEdgeValue("B", "C", 1)
        graph.putEdgeValue("C", "E", 1)
        graph.putEdgeValue("E", "F", 1)
        graph.putEdgeValue("B", "F", 9)
        graph.putEdgeValue("D", "F", 7)


        def d = new Dijkstra()
        def result = d.execute("A", "F", graph)
        assert d.resultValue == 5
    }
}
