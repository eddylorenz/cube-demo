package com.example.demo;

import com.example.demo.model.FaceCoordinate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		try {
			// MODIFY to increst cube size tests
			int cubeTests = 10;

			for(int i=1; i<=cubeTests; i++) {
				int[][] cube = new int[i][i];
				cube = generateFilledCube(i);
				rotateCube(cube);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int[][] rotateCube(int[][] cube) {
		int[][] cubeRotated = new int[cube.length][cube.length];
		try {
			System.out.println("----------------------------------");
			System.out.println("Input cube");
			System.out.println("----------------------------------");
			displayCube(cube);
			List<FaceCoordinate> faceCoordinates = generateFaceCoordinates(new FaceCoordinate(0, 0), new FaceCoordinate(cube.length -1, cube.length-1));
			rotateLevels(cube, cubeRotated, faceCoordinates, new FaceCoordinate(0, 0), new FaceCoordinate(cube.length-1, cube.length-1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cubeRotated;
	}

	private static List<FaceCoordinate> generateFaceCoordinates(FaceCoordinate startPosition, FaceCoordinate endPosition){

		int cubeWidth = endPosition.getY() - startPosition.getX();
		List<FaceCoordinate> faceCoordinates = new ArrayList<>();

		List<FaceCoordinate> topFaceCoordinates = new ArrayList<>();
		List<FaceCoordinate> rightFaceCoordinates = new ArrayList<>();
		List<FaceCoordinate> bottomFaceCoordinates = new ArrayList<>();
		List<FaceCoordinate> leftFaceCoordinates = new ArrayList<>();

		int y = startPosition.getY();
		for (int xTop=startPosition.getX(); xTop<=endPosition.getX(); xTop++){
			topFaceCoordinates.add(new FaceCoordinate(y, xTop));
		}

		int x = endPosition.getX();
		for (int yRight=startPosition.getY()+1; yRight<=endPosition.getY(); yRight++){
			rightFaceCoordinates.add(new FaceCoordinate(yRight, x));
		}

		y = endPosition.getY();
		for (int xBottom=endPosition.getX()-1; xBottom>=startPosition.getX() ; xBottom--){
			bottomFaceCoordinates.add(new FaceCoordinate(y, xBottom));
		}

		x = startPosition.getX();
		for (int yLeft=endPosition.getY()-1; yLeft>startPosition.getY() ; yLeft--){
			leftFaceCoordinates.add(new FaceCoordinate(yLeft, x));
		}

		faceCoordinates.addAll(topFaceCoordinates);
		faceCoordinates.addAll(rightFaceCoordinates);
		faceCoordinates.addAll(bottomFaceCoordinates);
		faceCoordinates.addAll(leftFaceCoordinates);


		return faceCoordinates;
	}

	private static void rotateLevels(int[][] cube, int[][] cubeRotated, List<FaceCoordinate> faceCoordinates, FaceCoordinate startCoordinate, FaceCoordinate endCoordinate){
		if (endCoordinate.getX() - startCoordinate.getX() >= startCoordinate.getX()) {
			FaceCoordinate startCoordinateRecursive = new FaceCoordinate(startCoordinate.getY() + 1, startCoordinate.getX() + 1);
			FaceCoordinate endCoordinateRecursive = new FaceCoordinate(endCoordinate.getY() - 1, endCoordinate.getX() - 1);
			List<FaceCoordinate> faceCoordinatesRecursive = generateFaceCoordinates(startCoordinateRecursive, endCoordinateRecursive);
			rotateLevels(cube, cubeRotated, faceCoordinatesRecursive,  startCoordinateRecursive, endCoordinateRecursive);
		}

		int width = endCoordinate.getX() - startCoordinate.getX() + 1;
		int jumps = (width-1)*3;

		for(int xCoordinates=0; xCoordinates<faceCoordinates.size(); xCoordinates++) {
			FaceCoordinate sourceFaceCoordinate = faceCoordinates.get(xCoordinates);

			int destinationPosition = xCoordinates + jumps;
			if (destinationPosition >= faceCoordinates.size())
				destinationPosition = destinationPosition - faceCoordinates.size();
			FaceCoordinate destinationFaceCoordinate = faceCoordinates.get(destinationPosition);
			cubeRotated[destinationFaceCoordinate.getY()][destinationFaceCoordinate.getX()] = cube[sourceFaceCoordinate.getY()][sourceFaceCoordinate.getX()];
			System.out.println("After movement");
			displayCube(cubeRotated);
		}

	}

	private static void displayCube(int[][] cube){
		for(int y=0; y<cube.length; y++) {
			for (int x = 0; x < cube[y].length; x++)
				System.out.print(cube[y][x] + " ");
			System.out.println("");
		}
	}

	private static int[][] generateFilledCube(int width){
		int[][] cube = new int[width][width];
		int counter=1;
		for(int y=0; y<cube.length;y++)
			for(int x=0; x<cube.length;x++){
				cube[y][x] = counter++;
			}
		return cube;
	}
}
