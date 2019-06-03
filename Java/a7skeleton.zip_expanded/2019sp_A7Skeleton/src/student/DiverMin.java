package student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import game.FindState;
import game.FleeState;
import game.Node;
import game.NodeStatus;
import game.SewerDiver;

public class DiverMin extends SewerDiver {

	/** Data structure for containing all visited node identifiers */
	public HashSet<Long> visitedNodes = new HashSet<>();

	/**
	 * Get to the ring in as few steps as possible. Once you get there, <br>
	 * you must return from this function in order to pick<br>
	 * it up. If you continue to move after finding the ring rather <br>
	 * than returning, it will not count.<br>
	 * If you return from this function while not standing on top of the ring, <br>
	 * it will count as a failure.
	 *
	 * There is no limit to how many steps you can take, but you will receive<br>
	 * a score bonus multiplier for finding the ring in fewer steps.
	 *
	 * At every step, you know only your current tile's ID and the ID of all<br>
	 * open neighbor tiles, as well as the distance to the ring at each of <br>
	 * these tiles (ignoring walls and obstacles).
	 *
	 * In order to get information about the current state, use functions<br>
	 * currentLocation(), neighbors(), and distanceToRing() in state.<br>
	 * You know you are standing on the ring when distanceToRing() is 0.
	 *
	 * Use function moveTo(long id) in state to move to a neighboring<br>
	 * tile by its ID. Doing this will change state to reflect your new position.
	 *
	 * A suggested first implementation that will always find the ring, but <br>
	 * likely won't receive a large bonus multiplier, is a depth-first walk. <br>
	 * Some modification is necessary to make the search better, in general.
	 */
	@Override
	public void find(FindState state) {
		// TODO : Find the ring and return.
		// DO NOT WRITE ALL THE CODE HERE. DO NOT MAKE THIS METHOD RECURSIVE.
		// Instead, write your method elsewhere, with a good specification,
		// and call it from this one.
		modifiedDFSWalk(state);
	}

	/**
	 * Helper method modifiedDFSWalk to conduct a depth first search for find <br>
	 * method. <br>
	 * Diver Min is standing on a Node startNode given by FindState state. <br>
	 * Visit every node reachable along paths of unvisited nodes from node <br>
	 * startNode, <br>
	 * until Min reaches the ring. <br>
	 * Modified dfs walk so that it visits nodes in order of shortest distance <br>
	 * to ring.
	 */
	public void modifiedDFSWalk(FindState state) {
		Heap<NodeStatus> minHeap = new Heap<>(true);
		ArrayList<NodeStatus> sortedNeighbors = new ArrayList<>();
		long startNode = state.currentLocation(); // Locates id of node where Min is at
		visitedNodes.add(startNode); // Adds startNode to visitedNodes hashset if unique
		for (NodeStatus currentNode : state.neighbors()) {
			minHeap.add(currentNode, currentNode.getDistanceToTarget());
		}
		while (minHeap.size() != 0) {
			sortedNeighbors.add(minHeap.poll());
		}
		for (NodeStatus minNode : sortedNeighbors) { // Goes through neighbors of startnode by NodeStatus
			if (!visitedNodes.contains(minNode.getId())) { // Checks if the neighbor is already visited
				state.moveTo(minNode.getId()); // Moves Min to node
				if (state.distanceToRing() != 0) // If ring is located, return
					modifiedDFSWalk(state); // Since ring was not located, recurse
				else
					return;
				if (state.distanceToRing() != 0) // move Min back to startNode since ring was not found
					state.moveTo(startNode);
				else
					return; // Else ring is located after walk, return

			}
		}
	}

	/**
	 * Flee the sewer system before the steps are all used, trying to <br>
	 * collect as many coins as possible along the way. Your solution must ALWAYS
	 * <br>
	 * get out before the steps are all used, and this should be prioritized
	 * above<br>
	 * collecting coins.
	 *
	 * You now have access to the entire underlying graph, which can be accessed<br>
	 * through FleeState. currentNode() and getExit() will return Node objects<br>
	 * of interest, and getNodes() will return a collection of all nodes on the
	 * graph.
	 *
	 * You have to get out of the sewer system in the number of steps given by<br>
	 * getStepsRemaining(); for each move along an edge, this number is <br>
	 * decremented by the weight of the edge taken.
	 *
	 * Use moveTo(n) to move to a node n that is adjacent to the current node.<br>
	 * When n is moved-to, coins on node n are automatically picked up.
	 *
	 * You must return from this function while standing at the exit. Failing <br>
	 * to do so before steps run out or returning from the wrong node will be<br>
	 * considered a failed run.
	 *
	 * Initially, there are enough steps to get from the starting point to the<br>
	 * exit using the shortest path, although this will not collect many coins.<br>
	 * For this reason, a good starting solution is to use the shortest path to<br>
	 * the exit.
	 */
	@Override
	public void flee(FleeState state) {
		// TODO: Get out of the sewer system before the steps are used up.
		// DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
		// with a good specification, and call it from this one.
		fleeWithCoins(state);
	}

	/**
	 * Flee while picking up coins when safe. <br>
	 * Checks for the location with most coin value with regards to their<br>
	 * distance from current position, and the coins on the path to that coin.
	 *
	 * After determining the best coin tile to move to, it moves Min to that<br>
	 * coin, and continues to do so until it is no longer safe.
	 *
	 * To judge safety, the method checks for the path from next coin tile to<br>
	 * exit, and compares it with the steps needed to exit. If there are not <br>
	 * enough steps to collect more coins, it moves Min to the exit via <br>
	 * the shortest path.
	 */
	public void fleeWithCoins(FleeState state) {
		boolean escape = false; // boolean escape -> false if Min should hunt coins, true if Min should leave
		Node exit = state.getExit(); // exit Node
		Node current = state.currentNode(); // current position Node
		ArrayList<Node> coinTiles = new ArrayList<>();
		List<Node> shortestPath = Paths.shortest(current, exit); // Shortest Path from current to exit

		addCoinTiles(state, coinTiles); // Add all tiles that have coins to ArrayList coinTiles via helper

		while (escape == false) {
			Node nextCoin = current; // Initialize placeholder node variable for while loop
			double maxCoin = 0; // coin tile with largest worth
			for (Node coinTile : coinTiles) { // For each tile in tiles with coins
				current = state.currentNode(); // current position Node set for each while loop iteration
				List<Node> pathToCoin = Paths.shortest(current, coinTile); // shortest path from current to the tile
				double coins = 0;
				for (Node coinNode : pathToCoin) { // Adds all coins on the shortest path to tile to coins
					coins += coinNode.getTile().coins();
				}
				double stepsToCoin = Paths.pathSum(Paths.shortest(current, coinTile)); // Sum of steps to coinTile
				// Coin value on path, end, and distance combined
				double worth = coins / stepsToCoin * coinTile.getTile().coins();
				if (maxCoin < worth) { // if the maxCoin so far in the loop is less than worth of new tile,
					nextCoin = coinTile; // set nextCoin to the new coin tile
					maxCoin = worth; // set maxCoin to worth of new tile
				}
			}
			List<Node> pathToCoin = Paths.shortest(current, nextCoin); // shortest path from current to selected tile
			Node nextSegmentToCoin = pathToCoin.get(1); // Get 2nd segment as first segment is on current tile
			int nextSegmentSteps = current.getEdge(nextSegmentToCoin).length(); // Find steps needed to traverse segment
			// If steps to next coin tile greater than steps needed to escape, end loop
			if (state.stepsLeft() - nextSegmentSteps <= Paths.pathSum(shortestPath) + nextSegmentSteps) {
				escape = true;
			} else {
				// Not escaping
				state.moveTo(nextSegmentToCoin); // move to next segment towards coin
				current = state.currentNode();
				shortestPath = Paths.shortest(current, exit);
				if (Paths.shortest(current, nextCoin).get(0) == nextCoin) {
					coinTiles.remove(nextCoin); // remove tile from coinTiles if picked up
				}
			}
		}

		moveToExit(state, shortestPath); // helper method used to flee
		return;
	}

	/**
	 * Helper 1 for fleeWithCoins:
	 *
	 * Takes in an ArrayList of coinTiles nodes, and adds all tiles <br>
	 * with coins to it.
	 */
	public void addCoinTiles(FleeState state, ArrayList<Node> coinTiles) {
		for (Node tile : state.allNodes()) {
			if (tile.getTile().coins() > 0) {
				coinTiles.add(tile);
			}
		}
	}

	/**
	 * Helper 2 for fleeWithCoins:
	 *
	 * Takes in current node and the next tile with coins to move to. <br>
	 * Creates shortest path between the two. <br>
	 * Accumulates and then returns the value of coins for each tile in the <br>
	 * shortest path.
	 *
	 */
	public int pathCoins(Node current, Node nextCoinTile) {
		List<Node> pathToCoin = Paths.shortest(current, nextCoinTile);
		int coins = 0;
		for (Node coinNode : pathToCoin) {
			coins += coinNode.getTile().coins();
		}

		return coins;
	}

	/**
	 * Helper 3 for fleeWithCoins:
	 *
	 * Takes in shortestPath from current position to exit. <br>
	 * For each segment in the shortestPath, it moves to that segment.
	 */
	public void moveToExit(FleeState state, List<Node> shortestPath) {
		for (Node pathSegment : shortestPath) {
			if (pathSegment != state.currentNode()) {
				state.moveTo(pathSegment);
			}
		}
	}

	/**
	 * Helper method dfsWalk to conduct a depth first search for find method. <br>
	 * Diver Min is standing on a Node u given by FindState state. <br>
	 * Visit every node reachable along paths of unvisited nodes from node u, <br>
	 * until Min reaches the ring.
	 *
	 * Non-greedy version, UNUSED in find method.
	 */
	public void dfsWalk(FindState state) {
		long startNode = state.currentLocation(); // Locates id of node where Min is at
		visitedNodes.add(startNode); // Adds startNode to visitedNodes hashset if unique
		for (NodeStatus currentNode : state.neighbors()) { // Goes through neighbors of startnode by NodeStatus
			if (!visitedNodes.contains(currentNode.getId())) { // Checks if the neighbor is already visited
				state.moveTo(currentNode.getId()); // Moves Min to node
				if (state.distanceToRing() == 0) // If ring is located, return
					return;
				dfsWalk(state); // Since ring was not located, recurse
				if (state.distanceToRing() == 0) // If ring is located after walk, return
					return;
				else // Else move Min back to startNode since ring was not found
					state.moveTo(startNode);
			}
		}
	}

	/**
	 * Find the shortest path via Djistra's shortest path algorithm. <br>
	 * Move Min along this shortest path to the exit. Base solution.
	 *
	 * Does not look for coins. UNUSED in flee method.
	 */
	public void moveAlongShortest(FleeState state) {
		Node exit = state.getExit();
		Node current = state.currentNode();
		List<Node> shortestPath = Paths.shortest(current, exit);

		for (Node pathSegment : shortestPath) {
			if (pathSegment != current) {
				state.moveTo(pathSegment);
			}
		}
	}

}
