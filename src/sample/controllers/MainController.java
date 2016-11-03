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
import sample.models.geometry.primitives.Point2d;
import sample.models.World;
import sample.models.result.Obstacle;
import sample.models.result.Tower;
import sample.models.result.Troop;
import sample.models.result.troops.Knight;


public class MainController {
    public Label label;
    public Canvas canvas;

    private World world;

    public void test(ActionEvent actionEvent) {
        world = new World();

        // Test troop
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(1000),
                (ae) -> world.getTroops().add(new Knight(new Point2d(2, 1 + Math.random() * 0.001), world, PlayerType.FIRST))));
        t1.setCycleCount(Animation.INDEFINITE);
        t1.play();

        Timeline t2 = new Timeline(new KeyFrame(Duration.millis(2000),
                (ae) -> world.getTroops().add(new Knight(new Point2d(10, 17 + Math.random() * 0.001), world, PlayerType.SECOND))));
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();

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

    private Color colorForPlayer(PlayerType player) {
        switch (player) {
            case FIRST:
                return Color.rgb(60, 130, 100);
            case SECOND:
                return Color.rgb(130, 120, 60);
        }
        return Color.BLACK;
    }

    public void renderMap(World world) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear
        gc.setFill(Color.rgb(210, 210, 230));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double scale = Math.min(canvas.getWidth(), canvas.getHeight()) / Math.max(world.getWidth(), world.getHeight());

//        renderChess(gc, world, scale);
        renderObstacles(gc, world, scale);
        renderTowers(gc, world, scale);
        renderGrid(gc, world, scale);
        renderUnits(gc, world, scale);
    }

    private void renderGrid(GraphicsContext gc, World world, double scale) {
        gc.setStroke(Color.WHITE);

        for (int i = 0; i < world.getWidth(); i++) {
            gc.strokeLine(i * scale + 0.5,
                    0,
                    i * scale + 0.5,
                    world.getHeight() * scale);
        }

        for (int i = 0; i < world.getHeight(); i++) {
            gc.strokeLine(0,
                    i * scale + 0.5,
                    world.getWidth() * scale,
                    i * scale + 0.5);
        }
    }

    private void renderTowers(GraphicsContext gc, World world, double scale) {
        for (Tower tower : world.getTowers()) {
            gc.setFill(colorForPlayer(tower.getOwner()));

            gc.fillRect((tower.getPosition().x - tower.getHalfSide()) * scale,
                    (tower.getPosition().y - tower.getHalfSide()) * scale,
                    tower.getHalfSide() * 2 * scale,
                    tower.getHalfSide() * 2 * scale);
        }
    }

    private void renderObstacles(GraphicsContext gc, World world, double scale) {
        for (Obstacle obstacle : world.getObstacles()) {
            gc.setFill(Color.rgb(50, 100, 150));

            gc.fillRect((obstacle.getPosition().x - obstacle.getHalfSide()) * scale,
                    (obstacle.getPosition().y - obstacle.getHalfSide()) * scale,
                    obstacle.getHalfSide() * 2 * scale,
                    obstacle.getHalfSide() * 2 * scale);
        }
    }

    private void renderUnits(GraphicsContext gc, World world, double scale) {
        for (Troop troop : world.getTroops()) {
            gc.setFill(colorForPlayer(troop.getOwner()));

            double k = troop.getDeployer().isFinished()
                    ? 1f * troop.getLifeCrystal().getHealthRest() / troop.getLifeCrystal().getTotalHealth()
                    : (1 - troop.getDeployer().getTimeRemain() / troop.getDeployer().getInterval());

            gc.fillArc((troop.getPosition().x - 0.5) * scale,
                    (troop.getPosition().y - 0.5) * scale,
                    scale,
                    scale,
                    0,
                    360 * k,
                    ArcType.ROUND);

            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
        }
    }

    private void renderChess(GraphicsContext gc, World world, double scale) {
        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.LIGHTGRAY);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(j * scale, i * scale, scale, scale);
            }
        }
    }
}
