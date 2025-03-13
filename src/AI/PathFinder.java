package AI;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {
	GamePanel gp;
	Node [] [] node;
	public ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
	}
	public void instantiateNodes() {
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		// MAP EACH NODE TO A TILE
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			node[col][row] = new Node(col,row);
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
	}
	public void resetNodes() {
		int col = 0;
		int row = 0;
		
		// RESET ALL NODES
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		// CLEAR PREVIOUS DATA
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity)   {
		resetNodes( ); 
			
		// SET START AND GOAL NODE
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		for(int i = 0; i < gp.iTile[1].length; i++) {
			if(gp.iTile[gp.currentMap][i] != null  && gp.iTile[gp.currentMap][i].destructible) {
				int itCol = gp.iTile[gp.currentMap][i].worldX / gp.tileSize;
				int itRow = gp.iTile[gp.currentMap][i].worldY / gp.tileSize;
				node[itCol][itRow].solid = true;
			}
		}
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//SET SOLID NODE
			int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row]; 
			if(gp.tileM.tile[tileNum].collision) {
				node[col][row].solid = true;
			}
			// CHECK INTERACTIVE TILES 
			
			// SET COST
			getCost(node[col][row]);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	public void getCost(Node node) {
		// G COST
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		// H COST
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		// F COST
		node.fCost = node.gCost+node.hCost;
		 
		 
	}
	public boolean search() {
		
		while(goalReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			// CHECK CURRENT NODE
			currentNode.checked = true;
			openList.remove(currentNode);
			
			// OPEN UP NODE
			if(row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			// OPEN LEFT NODE
			if(col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			// OPEN DOWN NODE
			if(row + 1 < gp.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			// OPEN RIGHT NODE 
			if(col + 1 < gp.maxWorldCol) {
				openNode(node[col+1][row]);
			}
			
			// FIND BEST NODE 
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0; i < openList.size(); i++) {
				// CHECK IF NODE'S FCOST IS BETTER
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				} 
				else if (openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// IF NO NODE IS LEFT, END LOOP
			if(openList.size() == 0) {
				break;
			}
			// NEXT STEP 
			 currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == goalNode) {
				 goalReached = true;
				 trackThePath();
			}
			step++;
		}
		return goalReached;
	}
	public void openNode(Node node) {
		if(node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	public void trackThePath() {
		Node current = goalNode;
		
		while(current != startNode) {
			
			pathList.add(0,current);
			current = current.parent;
		}
	}
}
