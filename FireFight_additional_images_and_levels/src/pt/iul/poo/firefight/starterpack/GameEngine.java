package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;
import java.util.function.Predicate;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	private static GameEngine INSTANCE;
	private ImageMatrixGUI gui;
	private List<ImageTile> tileList;
	private Fireman fireman;
	private int points;
	private String nickname;
	private List<PlayerObject> top5Players;
	private List<String> levels;
	private int currentLevel;

	public GameEngine() {
		this.nickname = JOptionPane.showInputDialog("Insert your nickname:");
		tileList = new ArrayList<>();
		top5Players = new ArrayList<>();
		levels = new ArrayList<>();
		currentLevel = 0;
		points = 0;
		INSTANCE = this;
		gui = ImageMatrixGUI.getInstance();
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);
		gui.registerObserver(this);
		gui.go();
		gui.setStatusMessage("Points: " + points);
	}

	public void start() {
		getAllLevels();
		checkAllLevelsPlayed();
		readFile("levels/" + levels.get(currentLevel));
		loadRanking("levels/" + levels.get(currentLevel));
		sendImagesToGUI();
	}

	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();

		ForestObject.incrementCycles();

		/** ARROW KEYS **/
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			if (!fireman.isOnScreen()) { /** VEHICLES **/
				vehicleOnUse().move();
			} else { /** FIREMAN **/
				fireman.move();
				if (firemanUsesVehicle()) {
					fireman.vanish();
				}
			}
		}

		/** ENTER KEY - BULLDOZER **/
		if (key == KeyEvent.VK_ENTER && !fireman.isOnScreen()) {
			fireman.comeBack();
			vehicleOnUse().setNotUsed();
		}

		/** P KEY - AIRPLANE **/
		for (GameElements p : getObjects(o -> o instanceof Plane))
			((Plane) p).move();

		if (key == KeyEvent.VK_P) {
			Plane.add();
		}
		moveBots();
		ActiveObject.increment();
		ActiveObject.remove();
		gui.setStatusMessage("Points: " + points);

		if (isGameOver()) {
			if (currentLevel < levels.size()) {
				PlayerObject player = new PlayerObject(nickname, points);
				top5Players.add(player);
				ranking(top5Players);
				writeFile("levels/" + levels.get(currentLevel), top5Players);
				nextGame();
			}
		}
		gui.update();
	}

	private void readFile(String file) {
		char letter = ' ';
		String line = "";
		try {
			Scanner scanner = new Scanner(new File(file));

			for (int y = 0; y < GRID_HEIGHT; y++) {
				line = scanner.nextLine();
				for (int x = 0; x < GRID_WIDTH; x++) {
					letter = line.charAt(x);
					String s = "" + letter;
					tileList.add(GameElements.setElement(new Point2D(x, y), s));
				}
			}
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				if (line.startsWith("Player"))
					break;
				String data[] = line.split(" ");
				String element = data[0];
				int px = Integer.parseInt(data[1]);
				int py = Integer.parseInt(data[2]);
				GameElements ge = GameElements.setElement(new Point2D(px, py), element);

				switch (ge.getName()) {
				case "fireman":
					tileList.add((Fireman) ge);
					fireman = (Fireman) ge;
					break;
				case "fire":
					tileList.add(ge);
					getObjects(o -> o instanceof ForestObject).forEach(t -> {
						if (t.getPosition().equals(ge.getPosition()))
							((ForestObject) t).setStatus(Status.BURNING);
					});
					break;
				case "bulldozer":
					tileList.add((Bulldozer) ge);
					break;
				case "firetruck":
					tileList.add((FireTruck) ge);
					break;
				case "firemanbot":
					tileList.add((FiremanBot) ge);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	private void sendImagesToGUI() {
		gui.addImages(tileList);
		gui.update();
	}

	public static GameEngine getInstance() {
		return INSTANCE;
	}

	public void addObject(GameElements ge) {
		gui.addImage(ge);
		tileList.add(ge);
	}

	public void removeObject(GameElements ge) {
		gui.removeImage(ge);
		tileList.remove(ge);
	}

	public void incrementPoints(int add) {
		points += add;
	}

	public void decrementPoints(int sub) {
		points -= sub;
	}

	private boolean isGameOver() {
		return GameEngine.getInstance().getObjects(o -> o instanceof Fire).isEmpty();
	}
	
	/** Getters que necessitei */

	public List<GameElements> getObjects(Predicate<GameElements> predicate) {
		List<GameElements> objects = new ArrayList<>();
		tileList.forEach(p -> {
			if ((predicate.test((GameElements) p))) {
				objects.add((GameElements)p);
			}
		});
		return objects;
	}
	
	public List<Point2D> getPositions(Predicate<GameElements> predicate) {
		List<Point2D> pos = new ArrayList<>();
		tileList.forEach(o -> {
			if (predicate.test((GameElements) o)) {
				pos.add(o.getPosition());
			}
		});
		return pos;
	}
	
	public Point2D getBulldozerPos() {
		for (GameElements o : getObjects(o -> o instanceof Bulldozer)) {
			if (((VehicleObject) o).isBeingUsed())
				return o.getPosition();
		}
		return new Point2D(-5, -5);
	}


	public Fireman getObject() {
		return fireman;
	}

	public Point2D getFiremanPos() {
		return fireman.getPosition();
	}
	
	public void moveBots() {
		getObjects(o -> o instanceof FiremanBot).forEach(b -> ((FiremanBot) b).move());
	}


	public VehicleObject vehicleOnUse() {
		for (GameElements o : getObjects(o -> o instanceof VehicleObject)) {
			if (((VehicleObject) o).isBeingUsed()) {
				return (VehicleObject) o;
			}
		}
		return null;
	}

	public boolean firemanUsesVehicle() {
		for (GameElements o : getObjects(o -> o instanceof VehicleObject)) {
			if (fireman.getPosition().equals(o.getPosition())) {
				((VehicleObject) o).setUsed();
				return true;
			}
		}
		return false;
	}


	/** Escreve no ficheiro as pontuacoes */
	public void writeFile(String file, List<PlayerObject> players) {
		removeLines();
		FileWriter fw;
		try {
			fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (PlayerObject p : players) {
				bw.write(p.toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	/** Ordena os players por pontuacao decrescente */
	public void ranking(List<PlayerObject> players) {
		players.sort((a, b) -> Integer.compare(b.getPoints(), a.getPoints()));
		if (players.size() > 5)
			players.subList(5, players.size()).clear();
	}

	/** Carrega as pontuacoes que estao no ficheiro */
	public void loadRanking(String file) {
		try {
			Scanner scanner = new Scanner(new File(file));
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.startsWith("Player")) {
					String data[] = line.split(" ");
					String nickname = data[1];
					int points = Integer.parseInt(data[3]);
					top5Players.add(new PlayerObject(nickname, points));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}

	}

	/** Remove as pontuacoes do ficheiro */
	public void removeLines() {
		try {
			File file = new File("levels/" + levels.get(currentLevel));
			List<String> out = Files.lines(file.toPath()).filter(line -> !line.contains("Player"))
					.collect(Collectors.toList());
			Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
	}

	/** Carrega todos os levels.txt */
	public void getAllLevels() {
		levels.clear();
		File folder = new File("levels/");
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				levels.add(listOfFiles[i].getName());
			}
		}
	}

	public void nextLevel() {
		currentLevel++;
	}

	public void nextGame() {
		if (currentLevel < levels.size()) {
			JOptionPane.showMessageDialog(null, "Level: " + levels.get(currentLevel) + "\nPoints: " + points);
			points = 0;
			gui.setStatusMessage("Points: " + points);
			gui.removeImages(tileList);
			tileList.clear();
			top5Players.clear();
			gui.update();
			nextLevel();
			start();
		}
	}

	public void checkAllLevelsPlayed() {
		if (currentLevel == levels.size()) {
			currentLevel = 0;
		}
	}

	
	

}