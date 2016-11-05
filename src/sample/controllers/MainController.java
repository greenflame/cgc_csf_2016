package sample.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.models.TowerWars;
import sample.models.framework.GameWorld;
import sample.models.framework.components.Clock;
import sample.models.framework.components.Renderer;
import sample.models.framework.components.WorldSize;
import sample.models.framework.geometry.Point2d;
import sample.models.framework.geometry.Size2d;


public class MainController {
    public Label time;
    public Canvas canvas;
    public TextArea state;

    private TowerWars world;

    private Timeline mainLoop;

    public void start(ActionEvent actionEvent) {
        world = new TowerWars();

        Timeline troop = new Timeline(new KeyFrame(Duration.millis(200), ae -> {
            world.spawnTroop("d", new Point2d(5 + Math.random() * 0.1, 5), 1, 200, "second", 1);
        }));
        troop.setCycleCount(10);
        troop.play();

        Timeline troop2 = new Timeline(new KeyFrame(Duration.millis(220), ae -> {
            world.spawnTroop("d", new Point2d(15 + Math.random() * 0.1, 15), 1, 200, "first", 1);
        }));
        troop2.setCycleCount(10);
        troop2.play();

        // Main loop
        mainLoop = new Timeline(new KeyFrame(Duration.millis(1000 / 25), ae -> {
            // Simulate
            world.process(1f / 25f);

            // Render
            renderMap(world);

            // Update time label
            Clock clock = (Clock) (world.findByName("Global").get(0).getComponents(Clock.class).get(0));
            time.setText("Simulation time: " + clock.getTime());
        }));

        mainLoop.setCycleCount(Animation.INDEFINITE);
        mainLoop.play();
    }

    private void renderMap(GameWorld world) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear
        gc.setFill(Color.rgb(210, 210, 230));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Solve scale
        Size2d worldSize = ((WorldSize) world.firstByName("Global").firstComponentOfType(WorldSize.class)).getSize();
        double scale = Math.min(canvas.getWidth(), canvas.getHeight()) / Math.max(worldSize.width, worldSize.height);

        // Grid
        renderGrid(gc, world, scale);

        // Render all renderer components
        world.findByType(Renderer.class).forEach(gameObject -> {
            gameObject.getComponents(Renderer.class).forEach(component -> {
                ((Renderer) component).render(gc, scale);
            });
        });
    }

    private void renderGrid(GraphicsContext gc, GameWorld world, double scale) {
        Size2d worldSize = ((WorldSize) world.firstByName("Global").firstComponentOfType(WorldSize.class)).getSize();

        gc.setStroke(Color.WHITE);

        for (int i = 0; i < worldSize.width; i++) {
            gc.strokeLine(i * scale + 0.5,
                    0,
                    i * scale + 0.5,
                    worldSize.height * scale);
        }

        for (int i = 0; i < worldSize.height; i++) {
            gc.strokeLine(0,
                    i * scale + 0.5,
                    worldSize.width * scale,
                    i * scale + 0.5);
        }
    }

    public void pause(ActionEvent actionEvent) {
        mainLoop.pause();
    }

    public void resume(ActionEvent actionEvent) {
        mainLoop.play();
    }

    public void captureState(ActionEvent actionEvent) {
        state.setText(world.toString());
    }
}
