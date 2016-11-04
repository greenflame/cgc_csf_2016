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
import sample.models.framework.structures.Size2d;


public class MainController {
    public Label time;
    public Canvas canvas;
    public TextArea state;

    private TowerWars world;

    public void test(ActionEvent actionEvent) {
        world = new TowerWars();

        // Main loop
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 25), ae -> {
            // Simulate
            world.process(1f / 25f);

            // Render
            renderMap(world);

            // Update time label
            Clock clock = (Clock) (world.findByName("World").get(0).getComponents(Clock.class).get(0));
            time.setText("Simulation time: " + clock.getTime());

            // Update state label
            state.setText(world.toString());
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void renderMap(GameWorld world) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear
        gc.setFill(Color.rgb(210, 210, 230));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Solve scale
        Size2d worldSize = ((WorldSize) world.firstByName("World").firstComponentOfType(WorldSize.class)).getSize();
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
        Size2d worldSize = ((WorldSize) world.firstByName("World").firstComponentOfType(WorldSize.class)).getSize();

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
}