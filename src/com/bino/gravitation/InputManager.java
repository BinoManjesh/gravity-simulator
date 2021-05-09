package com.bino.gravitation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

class InputManager extends InputAdapter {

    private final GravitationGame game;
    private final Vector2 lastTouch;
    private boolean touchedPlanet;

    InputManager(GravitationGame game) {
        this.game = game;
        lastTouch = new Vector2();
    }

    // Called when a mouse button is pressed
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastTouch.set(screenX, screenY);
        game.unproject(lastTouch);
        Planet touched = game.getPlanetAt(lastTouch);
        touchedPlanet = touched != null;
        Planet.selected = touched;
        if (!touchedPlanet && button == Input.Buttons.RIGHT) {
            Planet planet = new Planet(lastTouch);
            game.addPlanet(planet);
            Planet.selected = planet;
        }

        return true;
    }

    // Called when the mouse is dragged
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (touchedPlanet) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                Vector2 thisTouch = game.unproject(new Vector2(screenX, screenY));
                Planet.selected.setPosition(thisTouch);
            }
            if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                Vector2 thisTouch = game.unproject(new Vector2(screenX, screenY));
                Planet.selected.setVelocity(Planet.selected.position.cpy().sub(thisTouch).scl(Planet.VELOCITY_SCALE));
            }
        } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector2 thisTouch = game.unproject(new Vector2(screenX, screenY));
            game.moveCamera(lastTouch.cpy().sub(thisTouch));
        }

        return true;
    }

    // Called when the mouse is scrolled
    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY == 1) {
            game.zoom(1.1f);
        } else {
            game.zoom(1/1.1f);
        }

        return true;
    }

    // Called when a key is pressed
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                game.togglePaused();
                break;
            case Input.Keys.BACKSPACE:
                if (Planet.selected != null) {
                    game.removeSelected();
                }
                break;
        }

        return true;
    }

    // Called when a key is typed
    @Override
    public boolean keyTyped(char character) {
        if (Planet.selected != null) {
            if (character == '+') {
                Planet.selected.scaleRadius(1.1f);
            } else if (character == '-') {
                Planet.selected.scaleRadius(1/1.1f);
            }
        }

        return true;
    }
}
