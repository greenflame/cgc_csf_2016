package sample.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sample.models.PlayerType;
import sample.models.geometry.Point;
import sample.models.World;
import sample.models.result.Obstacle;
import sample.models.result.Troop;
import sample.models.result.troops.Knight;


public class MainController {
    public Label label;
    public Canvas canvas;

    private World world;

    public void test(ActionEvent actionEvent) {
        world = new World();

        // Test troop
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(300),
                (ae) -> world.getTroops().add(new Knight(new Point(2, 1 + Math.random() * 0.001), world, PlayerType.FIRST))));
        t1.setCycleCount(Animation.INDEFINITE);
        t1.play();

        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(900),
                (ae) -> world.getTroops().add(new Knight(new Point(10, 15), world, PlayerType.SECOND))));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();

        // Obstacle
        world.getObstacles().add(new Obstacle(new Point(4, 6), 2, world));

        // Main loop
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 25), ae -> tick()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void tick() {
        world.process(1f / 25f);
        renderMap(world);
        label.setText("Simulation time: " + world.getTime());
    }

    public void renderMap(World world) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Point cellSize = new Point(canvas.getWidth() / world.getWidth(),
                canvas.getHeight() / world.getHeight());

        // Background
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.LIGHTGRAY);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(j * cellSize.getX(), i * cellSize.getY(), cellSize.getX(), cellSize.getY());
            }
        }

        // Units
        for (Troop troop : world.getTroops()) {
            if (troop.getOwner() == PlayerType.FIRST) {
                gc.setFill(Color.SEAGREEN);
            } else {
                gc.setFill(Color.DARKBLUE);
            }

            double k = troop.getDeployer().isFinished()
                    ? 1f * troop.getLifeCrystal().getHealthRest() / troop.getLifeCrystal().getTotalHealth()
                    : (1 - troop.getDeployer().getTimeRemain() / troop.getDeployer().getInterval());

            gc.fillArc((troop.getPosition().getX() - 0.5) * cellSize.getX(),
                    (troop.getPosition().getY() - 0.5) * cellSize.getY(),
                    cellSize.getX(),
                    cellSize.getY(),
                    0,
                    360 * k,
                    ArcType.ROUND);

            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
//            gc.fillText("Knight, 200hp, p1",
//                    (troop.getPosition().getX()) * cellSize.getX(),
//                    (troop.getPosition().getY() - 0.8) * cellSize.getY());
        }

        // Obstacles
        for (Obstacle obstacle : world.getObstacles()) {
            gc.setFill(Color.DARKSLATEGRAY);

            gc.strokeRect((obstacle.getPosition().getX() - obstacle.getRadius()) * cellSize.getX(),
                    (obstacle.getPosition().getY() - obstacle.getRadius()) * cellSize.getY(),
                    obstacle.getRadius() * 2 * cellSize.getX(),
                    obstacle.getRadius() * 2 * cellSize.getY());
        }
    }
}
