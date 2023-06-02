import java.io.*;
import java.util.*;
import java.awt.*;
import java.util.List;

public class IMECEPathFinder{
	  public int[][] grid;
	  public int height, width;
	  public int maxFlyingHeight;
	  public double fuelCostPerUnit, climbingCostPerUnit;

	  public IMECEPathFinder(String filename, int rows, int cols, int maxFlyingHeight, double fuelCostPerUnit, double climbingCostPerUnit){

		  grid = new int[rows][cols];
		  this.height = rows;
		  this.width = cols;
		  this.maxFlyingHeight = maxFlyingHeight;
		  this.fuelCostPerUnit = fuelCostPerUnit;
		  this.climbingCostPerUnit = climbingCostPerUnit;
		  try {
			  String[] line;
			  BufferedReader reader = new BufferedReader(new FileReader(filename));
			  for(int row = 0; row < rows; row++){
				  line = reader.readLine().split(" +");
				  for(int col = 0; col < cols; col++){
					  grid[row][col] = Integer.parseInt(line[col + 1]);
				  }
			  }
		  } catch(IOException e) {
			  e.printStackTrace();
		  }

	  }


	  /**
	   * Draws the grid using the given Graphics object.
	   * Colors should be grayscale values 0-255, scaled based on min/max elevation values in the grid
	   */
	  public void drawGrayscaleMap(Graphics g){
		  StringBuilder scaleMapTextBuilder = new StringBuilder();
		  int minElevation = Integer.MAX_VALUE;
		  int maxElevation = Integer.MIN_VALUE;
		  for (int[] ints : grid) {
			  for (int elevation : ints) {
				  minElevation = Math.min(elevation, minElevation);
				  maxElevation = Math.max(elevation, maxElevation);
			  }
		  }
		  for (int i = 0; i < grid.length ; i++) {
			  for (int j = 0; j < grid[0].length; j++) {
				  int elevation = grid[i][j];
				  int scaledElevationValue = scaleElevation(elevation, minElevation, maxElevation);
				  scaleMapTextBuilder.append(scaledElevationValue);
				  if (j < grid[0].length - 1) {
					  scaleMapTextBuilder.append(" ");
				  }
				  g.setColor(new Color(scaledElevationValue, scaledElevationValue, scaledElevationValue));
				  g.fillRect(j, i, 1, 1);
			  }
			  scaleMapTextBuilder.append("\n");
		  }
		  try {
			  BufferedWriter writer = new BufferedWriter(new FileWriter("grayscaleMap.dat"));
			  writer.write(scaleMapTextBuilder.toString());
			  writer.close();
		  }catch (IOException e){
			  e.printStackTrace();
		  }
	  }

	public int scaleElevation(double elevation, double minElevation, double maxElevation) {
		return (int) ((elevation - minElevation) / (maxElevation - minElevation) * 255);
	}

	  private double calculateCost(Point source, Point destination, boolean isDiagonal){
		  double heightImpact = grid[source.y][source.x] > grid[destination.y][destination.x] ? 0 : grid[destination.y][destination.x] - grid[source.y][source.x];
		  if(isDiagonal){
			  return (Math.sqrt(2) * fuelCostPerUnit) + (climbingCostPerUnit * heightImpact);
		  } else {
			  return fuelCostPerUnit + (climbingCostPerUnit * heightImpact);
		  }
	  }


	/**
	 * Get the most cost-efficient path from the source Point start to the destination Point end
	 * using Dijkstra's algorithm on pixels.
	 * @return the List of Points on the most cost-efficient path from start to end
	 */
	public List<Point> getMostEfficientPath(Point start, Point end) {
		int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}, {-1,1}, {-1,-1}, {1,1}, {1,-1}};
		List<Point> path = new ArrayList<>();
		Double[][] distTo = new Double[height][width];
		Point[][] edgeFrom = new Point[height][width];
		HashMap<String, Boolean> isVisited = new HashMap<>();
		for(int i = 0; i < height; i++){
			Arrays.fill(distTo[i], Double.MAX_VALUE);
		}
		distTo[start.y][start.x] = 0.0;
		PriorityQueue<Point> pointPQ = new PriorityQueue<>();
		pointPQ.add(start);
		while(!pointPQ.isEmpty()){
			Point sourcePoint = pointPQ.poll();
			isVisited.put(sourcePoint.toString(), true);
				for(int directionIndex = 0; directionIndex < 8; directionIndex++){
					int destinationY = sourcePoint.y + directions[directionIndex][1];
					int destinationX = sourcePoint.x + directions[directionIndex][0];
					if(destinationY < height && destinationY >= 0 && destinationX < width && destinationX >= 0){
						Point destinationPoint = new Point(destinationX, destinationY);
						double destinationCost = calculateCost(sourcePoint, destinationPoint, directionIndex > 3) + distTo[sourcePoint.y][sourcePoint.x];
						if(destinationCost < distTo[destinationY][destinationX] && (isVisited.get(destinationPoint.toString()) == null) && grid[destinationY][destinationX] <= maxFlyingHeight){
							distTo[destinationY][destinationX] = destinationCost;
							edgeFrom[destinationY][destinationX] = sourcePoint;
							destinationPoint.distTo = destinationCost;
							pointPQ.add(destinationPoint);
						}
					}
				}
		}
		end.distTo = distTo[end.y][end.x];
		path.add(end);
		Point iterator = edgeFrom[end.y][end.x];
		while(iterator != null){
			path.add(iterator);
			iterator = edgeFrom[iterator.y][iterator.x];
		}
		return path;
	}

	/**
	 * Calculate the most cost-efficient path from source to destination.
	 * @return the total cost of this most cost-efficient path when traveling from source to destination
	 */
	public double getMostEfficientPathCost(List<Point> path){
		return path.get(0).distTo;
	}


	/**
	 * Draw the most cost-efficient path on top of the grayscale map from source to destination.
	 */
	public void drawMostEfficientPath(Graphics g, List<Point> path){
		for (Point p : path){
			g.setColor(Color.green);
			g.fillRect(p.x,p.y,1,1);
		}
	}

	/**
	 * Find an escape path from source towards East such that it has the lowest elevation change.
	 * Choose a forward step out of 3 possible forward locations, using greedy method described in the assignment instructions.
	 * @return the list of Points on the path
	 */
	public List<Point> getLowestElevationEscapePath(Point start){
		List<Point> pathPointsList = new ArrayList<>();
		pathPointsList.add(start);
		Point currentPoint = start;
		while (currentPoint.x + 1 < width){
			double currentElevation = grid[currentPoint.y][currentPoint.x];
			int nextX;
			int nextY = 0;
			double minElevationChange = Double.MAX_VALUE;
			if(Math.abs(currentElevation - grid[currentPoint.y + 1][currentPoint.x + 1]) < minElevationChange){
				nextY = currentPoint.y + 1;
				minElevationChange = Math.abs(currentElevation - grid[currentPoint.y + 1][currentPoint.x + 1]);
			}
			if(Math.abs(currentElevation - grid[currentPoint.y - 1][currentPoint.x + 1]) <= minElevationChange){
				minElevationChange = Math.abs(currentElevation - grid[currentPoint.y - 1][currentPoint.x + 1]);
				nextY = currentPoint.y - 1;
			}
			if(Math.abs(currentElevation - grid[currentPoint.y][currentPoint.x + 1]) <= minElevationChange){
				nextY = currentPoint.y;
			}
			nextX = currentPoint.x + 1;
			Point nextPoint = new Point(nextX, nextY);
			nextPoint.prevPoint = currentPoint;
			pathPointsList.add(nextPoint);
			currentPoint = nextPoint;
		}
		return pathPointsList;
	}


	/**
	 * Calculate the escape path from source towards East such that it has the lowest elevation change.
	 * @return the total change in elevation for the entire path
	 */
	public int getLowestElevationEscapePathCost(List<Point> pathPointsList){
		int totalChange = 0;
		Point iterator = pathPointsList.get(pathPointsList.size() - 1);
		while(iterator.prevPoint != null){
			totalChange += Math.abs(grid[iterator.y][iterator.x] - grid[iterator.prevPoint.y][iterator.prevPoint.x]);
			iterator = iterator.prevPoint;
		}
		return totalChange;
	}


	/**
	 * Draw the escape path from source towards East on top of the grayscale map such that it has the lowest elevation change.
	 */
	public void drawLowestElevationEscapePath(Graphics g, List<Point> pathPointsList){
		for (Point p : pathPointsList){
			g.setColor(Color.YELLOW);
			g.fillRect(p.x,p.y,1,1);
		}
	}


}
