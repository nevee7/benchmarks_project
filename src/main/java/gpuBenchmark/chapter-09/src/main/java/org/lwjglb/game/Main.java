package org.lwjglb.game;

import org.joml.Vector2f;
import org.lwjglb.engine.*;
import org.lwjglb.engine.graph.*;
import org.lwjglb.engine.scene.*;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IAppLogic {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;

    private int frameCount = 0;
    private long lastTime;  // Declare lastTime here



    private Entity cubeEntity;
    private Entity cubeEntity2;

    private Entity cubeEntity3;
    private Model cubeModel;

    private List<Entity> cubes = new ArrayList<>();

    private int NrOfCubes=2;

    private long lastCubeGenerationTime;
    private final long cubeGenerationInterval = 5000; // 2000 milliseconds = 2 second

    private float rotation;

    public static void main(String[] args) {
        Main main = new Main();
        Engine gameEng = new Engine("chapter-09", new Window.WindowOptions(), main);
        gameEng.start();


    }






    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
         cubeModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj",
                scene.getTextureCache());
        scene.addModel(cubeModel);








        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 0, -2);
        scene.addEntity(cubeEntity);
        lastCubeGenerationTime = System.currentTimeMillis();

        cubeEntity2 = new Entity("cube-entity2", cubeModel.getId());
        cubeEntity2.setPosition(1, 1, -2);
        scene.addEntity(cubeEntity2);
        lastCubeGenerationTime = System.currentTimeMillis();

        cubeEntity3 = new Entity("cube-entity3", cubeModel.getId());
        cubeEntity3.setPosition(2, 2, -2);
        scene.addEntity(cubeEntity3);
        lastCubeGenerationTime = System.currentTimeMillis();

        cubes.add(cubeEntity);
        cubes.add(cubeEntity2);
        cubes.add(cubeEntity3);



    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(move);
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY),
                    (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        rotation += 1.5;
        if (rotation > 360) {
            rotation = 0;
        }




        for (Entity cube : cubes) {

            float rotationX = (float)Math.random() * 3;
            float rotationY = (float)Math.random() * 3;
            float rotationZ = (float)Math.random() * 3;
            cube.setRotation(5, 10, 12, (float) Math.toRadians(rotation));
            cube.updateModelMatrix();
        }


        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCubeGenerationTime >= cubeGenerationInterval && NrOfCubes<500000){

            for(int i=0;i<100000;i++) {
                NrOfCubes++;
                Entity cubeEntity4;
                float x = (float) Math.random() * 20 - 10;  // Random between -10 to 10
                float y = (float) Math.random() * 20 - 10;
                float z = (float) Math.random() * 20 - 10;
                cubeEntity4 = new Entity("cube-entity4", cubeModel.getId());
                cubeEntity4.setPosition(x, y, z);


                lastCubeGenerationTime = System.currentTimeMillis();


                cubeEntity4.updateModelMatrix();
                scene.addEntity(cubeEntity4);
                cubes.add(cubeEntity4);
            }
        }

        currentTime = System.currentTimeMillis(); // Get the current time *first*
        frameCount++;

        if (currentTime - lastTime >= 1000) { // Update every second
            System.out.println("FPS: " + frameCount);
            frameCount = 0;
            lastTime = currentTime; // Update lastTime *after* printing
        }

    }
}
