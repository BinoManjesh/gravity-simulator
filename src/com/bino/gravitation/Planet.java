package com.bino.gravitation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

class Planet {

    static final float VELOCITY_SCALE = 8;
    private static final Color NORMAL_COLOR = new Color(0);
    private static final Color SELECTED_COLOR = new Color(0x20202000);
    private static final Color VELOCITY_COLOR = new Color(0x568c0000);
    private static final float G = 60;
    static Planet selected;

    final Vector2 position;
    private final Vector2 velocity;
    private final Vector2 acceleration;
    private float radius;
    private float mass;

    private Planet() {
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
        radius = 100;
        mass = radius*radius*radius;
    }

    Planet(Vector2 position) {
        this();
        this.position.set(position);
    }

    void scaleRadius(float scl) {
        radius *= scl;
        mass = radius*radius*radius;
    }

    void setPosition(Vector2 position) {
        this.position.set(position);
    }

    void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    boolean contains(Vector2 v) {
        return v.dst2(position) < radius*radius;
    }

    void updateAcceleration(ArrayList<Planet> planets) {
        acceleration.set(0, 0);
        for (Planet planet : planets) {
            if (planet != this) {
                float dst = planet.position.dst(this.position);
                float magnitude = G*planet.mass/(dst*dst);
                acceleration.add(planet.position.cpy().sub(this.position).setLength(magnitude));
            }
        }
    }

    void update(float delta) {
        velocity.add(acceleration.scl(delta));
        position.add(velocity.cpy().scl(delta));
    }

    void collide(Planet planet2) {
        // if the planet2 overlaps with this planet
        if (this.position.dst2(planet2.position) < (this.radius + planet2.radius)*(this.radius + planet2.radius)) {
            Planet planet1 = this;
            Vector2 p1 = planet1.position, p2 = planet2.position;
            Vector2 u1 = planet1.velocity, u2 = planet2.velocity;
            float m1 = planet1.mass, m2 = planet2.mass;

            // Moves planets away from each other so that they just touch
            float dst = p1.dst(p2);
            float distToMove = (planet1.radius + planet2.radius - dst)/2;
            Vector2 displacement = p2.cpy().sub(p1).scl(distToMove/dst);
            p1.sub(displacement);
            p2.add(displacement);

            // Calculates the new velocities of the planets
            Vector2 v1 =
                u1.cpy().add(p2.cpy().sub(p1).scl(u2.cpy().sub(u1).dot(p2.cpy().sub(p1))*m2/((m1 + m2)*dst*dst)));
            Vector2 v2 =
                u2.cpy().add(p1.cpy().sub(p2).scl(u1.cpy().sub(u2).dot(p1.cpy().sub(p2))*m1/((m1 + m2)*dst*dst)));

            planet1.velocity.set(v1);
            planet2.velocity.set(v2);
        }
    }

    void draw(ShapeRenderer renderer) {
        if (this == selected) {
            renderer.setColor(SELECTED_COLOR);
        } else {
            renderer.setColor(NORMAL_COLOR);
        }
        renderer.circle(position.x, position.y, radius, 100);
        renderer.setColor(VELOCITY_COLOR);
        renderer.rectLine(position, position.cpy().add(velocity.cpy().scl(1/VELOCITY_SCALE)), radius/10);
    }
}
