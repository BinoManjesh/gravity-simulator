package com.bino.gravitation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Gravity Simulator";
        config.width = 960;
        config.height = 540;
        new LwjglApplication(new GravitationGame(), config);
    }
}
