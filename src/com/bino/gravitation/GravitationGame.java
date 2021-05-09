package com.bino.gravitation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

public class GravitationGame extends Game {

    private static final int MIN_WORLD_SIZE = 720;

    private ExtendViewport viewport;
    private OrthographicCamera camera;
    private ArrayList<Planet> planets;

    private boolean paused;
    private ShapeRenderer renderer;

    @Override
    public void create() {
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(MIN_WORLD_SIZE, MIN_WORLD_SIZE);
        camera = (OrthographicCamera) viewport.getCamera();
        planets = new ArrayList<>();
        paused = true;
        Gdx.input.setInputProcessor(new InputManager(this));
    }

    // Called whenever the screen is resized
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    // Called once each frame
    @Override
    public void render() {
        // Clears the screen
        ScreenUtils.clear(Color.DARK_GRAY);

        // Gets the time span between the current frame and the last frame in seconds
        float delta = Gdx.graphics.getDeltaTime();
        delta = Math.min(delta, 1/30f);

        if ((Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))
            && Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            planets.clear();
        }

        if (!paused) {
            for (Planet planet : planets) {
                planet.updateAcceleration(planets);
            }
            for (Planet planet : planets) {
                planet.update(delta);
            }
        }

        for (Planet planet1 : planets) {
            for (Planet planet2 : planets) {
                if (planet1 != planet2) {
                    planet1.collide(planet2);
                }
            }
        }

        renderer.setProjectionMatrix(camera.combined);
        viewport.apply();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Planet planet : planets) {
            planet.draw(renderer);
        }
        renderer.end();
    }

    void addPlanet(Planet planet) {
        planets.add(planet);
    }

    // Converts the given screen coordinates to world coordinates
    Vector2 unproject(Vector2 v) {
        return viewport.unproject(v);
    }

    void moveCamera(Vector2 v) {
        camera.translate(v);
    }

    void zoom(float f) {
        camera.zoom *= f;
    }

    Planet getPlanetAt(Vector2 v) {
        for (Planet planet : planets) {
            if (planet.contains(v)) {
                return planet;
            }
        }
        return null;
    }

    void removeSelected() {
        planets.remove(Planet.selected);
    }

    void togglePaused() {
        paused = !paused;
    }
}
