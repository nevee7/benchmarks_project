package org.lwjglb.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.*;
import org.lwjglb.engine.graph.*;
import org.lwjglb.engine.scene.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import benchmark.*;
import org.lwjglb.engine.scene.lights.DirLight;
import org.lwjglb.engine.scene.lights.PointLight;
import org.lwjglb.engine.scene.lights.SceneLights;
import org.lwjglb.engine.scene.lights.SpotLight;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;

public class GPUBenchmarky implements IAppLogic {

    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    private static final long CUBE_GENERATION_INTERVAL = 1000;

    private static final int NUM_CHUNKS = 4;
    private Entity[][] terrainEntities;

    public static int nrOfCubesToGenerate=1;
    private long lastCubeGenerationTime;

    private Model cubeModel;
    private Model BeastBoyModel;

    private Model RavenModel;
    private Model CyborgModel;


    private static final List<Entity> cubes = new ArrayList<>();

    private static final List<Entity> titans = new ArrayList<>();

    private static int nrOfCubes = 0;
    private int nrofBeastBoys=0;
    private int nrofRavens=0;

    private int nrofCybotgs=0;

    private  int nrofCyborgsToGenerate=1;
    private int nrofBeastBoysToGenerate=1;

    private int nrofRavensToGenerate=1;

    private float rotation;
    private float lightAngle;

    private double FinalScore;
    private double FPSAverage;
    private int TotalRuns;

   public static void main(String[] args) {
        GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
        GPUBenchmarky.runMain();
    }
 //commented out for no confussion
    public BenchmarkInfo runMain() {
        System.out.println(nrOfCubesToGenerate);
        GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
        int totalRuns = 6;
        TotalRuns=totalRuns;
        int batchSize = 2;
        List<Double> allFpsValues = new ArrayList<>();

        Engine warmup = new Engine("GPU-Benchmark: Warm-Up", new Window.WindowOptions(), GPUBenchmarky, 0);
        warmup.start();
        warmup.stop();
        //System.out.println("Number of generated cubes in warm-up:  "+ total_no_cubes);
        cubes.clear();
        nrOfCubes = 0;

        for (int i = 0; i < totalRuns; i++) {
            Engine gameEng = new Engine("GPU-Benchmark:" + (i + 1) + "/" + totalRuns, new Window.WindowOptions(), GPUBenchmarky, i + 1);
            gameEng.start();
            allFpsValues.addAll(gameEng.getFpsList());
            gameEng.stop();
            //System.out.println("Number of generated cubes:  "+ total_no_cubes);
            cubes.clear();
            nrOfCubes = 0;

            if ((i + 1) % batchSize == 0) {
                List<Double> fpsBatch = allFpsValues.subList(i + 1 - batchSize, i + 1);
                FPSAverage= calculateAverage(fpsBatch);
                //System.out.println("\nAverage FPS after " + (i + 1) + " runs: " + averageFps);
            }
        }

        double finalScore = calculateFinalScore(allFpsValues);
        FinalScore=finalScore;
        //System.out.println("\nFinal score: " + finalScore);
        return new BenchmarkInfo("GPU benchmark", finalScore, nrOfCubesToGenerate);
    }

    private static double calculateFinalScore(List<Double> allFpsValues) {
        double avgFPS = calculateAverage(allFpsValues);
        double minFPS = Collections.min(allFpsValues);
        double maxFPS = Collections.max(allFpsValues);

        double normalizedAvg = normalize(avgFPS, allFpsValues);

        double weightAvg = 0.6;
        double weightMin = 0.3;
        double weightMax = 0.1;
        int CubeFactor=getHighestPowerOfTen(nrOfCubesToGenerate)*100;

        return weightAvg * normalizedAvg + weightMin * minFPS + weightMax * maxFPS+CubeFactor;
    }


    private static int getHighestPowerOfTen(int number) {
        if (number == 0) {
            return 0; // Special case: 0 doesn't have a power of 10
        }

        int highestPower = 0;
        while (number % 10 == 0) {
            highestPower++;
            number /= 10;
        }
        return highestPower;
    }

    private static double calculateAverage(List<Double> values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private static double normalize(double value, List<Double> values) {
        double min = Collections.min(values);
        double max = Collections.max(values);
        return 100 * (value - min) / (max - min);
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        cubeModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj", scene.getTextureCache());
        scene.addModel(cubeModel);

        BeastBoyModel = ModelLoader.loadModel("beast-model", "resources/models/Beastboy/untitled.obj", scene.getTextureCache());
        scene.addModel(BeastBoyModel);

        RavenModel = ModelLoader.loadModel("raven-model", "resources/models/Raven/INJ_iOS_HERO_Rachel_Roth_Raven_Teen_Titans.obj", scene.getTextureCache());
        scene.addModel(RavenModel);

        CyborgModel = ModelLoader.loadModel("cyborg-model", "resources/models/Cyborg/cyborg.obj", scene.getTextureCache());
        scene.addModel(CyborgModel);


        Entity RavenEntity = new Entity("raven-entity", RavenModel.getId());
        RavenEntity.setPosition(4, -5, -10);
        RavenEntity.setScale(4.0f);
        RavenEntity.updateModelMatrix();
        scene.addEntity(RavenEntity);

        Entity beastBoyEntity = new Entity("beast-entity", BeastBoyModel.getId());
        beastBoyEntity.setPosition(-4, 0f, -10);
        beastBoyEntity.updateModelMatrix();
        scene.addEntity(beastBoyEntity);

        Entity Cyborg = new Entity("beast-entity", CyborgModel.getId());
        Cyborg.setPosition(0, 0f, -10);
        Cyborg.updateModelMatrix();
        scene.addEntity(Cyborg);

        titans.add(beastBoyEntity);
        titans.add(RavenEntity);
        titans.add(Cyborg);

        Entity cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 0f, -10);
        cubeEntity.updateModelMatrix();
        scene.addEntity(cubeEntity);


        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.3f);
        scene.setSceneLights(sceneLights);

        sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 1.0f));

        Vector3f coneDir = new Vector3f(0, 0, -1);
        sceneLights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140.0f));


        lastCubeGenerationTime = System.currentTimeMillis();
        nrOfCubes++;
        cubes.add(cubeEntity);

        String quadModelId = "quad-model";
        Model quadModel = ModelLoader.loadModel("quad-model", "resources/models/quad/quad.obj", scene.getTextureCache());
        scene.addModel(quadModel);

        int numRows = NUM_CHUNKS * 2 + 1;
        terrainEntities = new Entity[numRows][numRows];
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numRows; i++) {
                Entity entity = new Entity("TERRAIN_" + j + "_" + i, quadModelId);
                // Adjust Y position to be lower than the cubes
                entity.setPosition((j - NUM_CHUNKS) * 10, -10, (i - NUM_CHUNKS) * 10);
                terrainEntities[j][i] = entity;
                scene.addEntity(entity);
            }
        }

        // Initialize skybox
        SkyBox skyBox = new SkyBox("resources/models/skybox/skybox.obj", scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(50);
        scene.setSkyBox(skyBox);

        //fog on objects
        scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.5f), 0.01f));

        scene.getCamera().moveUp(0.1f);

        lightAngle = -35;

        updateTerrain(scene);
    }


    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
       /* if (inputConsumed) {
            return;
        }
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
        if (window.isKeyPressed(GLFW_KEY_PAGE_UP)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_PAGE_DOWN)) {
            camera.moveDown(move);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            lightAngle -= 2.5f;
            if (lightAngle < -90) {
                lightAngle = -90;
            }
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            lightAngle += 2.5f;
            if (lightAngle > 90) {
                lightAngle = 90;
            }
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY), (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
*/
        SceneLights sceneLights = scene.getSceneLights();
        DirLight dirLight = sceneLights.getDirLight();
        double angRad = Math.toRadians(lightAngle);
        dirLight.getDirection().x = (float) Math.sin(angRad);
        dirLight.getDirection().y = (float) Math.cos(angRad);
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        //updateTerrain(scene);
        rotation += 1.5F;
        if (rotation > 360) {
            rotation = 0;
        }

        for (Entity cube : cubes) {
            cube.setRotation(5, 10, 12, (float) Math.toRadians(rotation));
            cube.updateModelMatrix();
        }

        for (Entity titan : titans) {
            titan.setRotation(0, 5, 0, (float) Math.toRadians(rotation));
            titan.updateModelMatrix();
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCubeGenerationTime >= CUBE_GENERATION_INTERVAL) {
            generateCubes(scene);
            generateBeastBoys(scene);
            generateRavens(scene);
            generateCyborgs(scene);
            lastCubeGenerationTime = currentTime;
        }
    }

    public void updateTerrain(Scene scene) {
        int cellSize = 10;
        Camera camera = scene.getCamera();
        Vector3f cameraPos = camera.getPosition();
        int cellCol = (int) (cameraPos.x / cellSize);
        int cellRow = (int) (cameraPos.z / cellSize);

        int numRows = NUM_CHUNKS * 2 + 1;
        int zOffset = -NUM_CHUNKS;
        float scale = cellSize / 2.0f;
        for (int j = 0; j < numRows; j++) {
            int xOffset = -NUM_CHUNKS;
            for (int i = 0; i < numRows; i++) {
                Entity entity = terrainEntities[j][i];
                entity.setScale(scale);
                entity.setPosition((cellCol + xOffset) * 2.0f, -10, (cellRow + zOffset) * 2.0f); // Y position set to -10
                entity.getModelMatrix().identity().scale(scale).translate(entity.getPosition());
                xOffset++;
            }
            zOffset++;
        }
    }

    private void generateCubes(Scene scene) {
        for (int i = 0; i < nrOfCubesToGenerate; i++) {
            nrOfCubes++;
            float x = (float) Math.random() * 21 - 11;  // Random between -11 to 10
            float y = (float) Math.random() * 12 - 6;  // Random between -6 to 5
            float z = (float) Math.random() * 20 - 30; // Random between -30 to -10

            Entity cubeEntity = new Entity("cube-entity" + nrOfCubes, cubeModel.getId());
            cubeEntity.setPosition(x, y, z);
            cubeEntity.updateModelMatrix();
            scene.addEntity(cubeEntity);
            cubes.add(cubeEntity);
        }
    }

    private void generateBeastBoys(Scene scene) {
        for (int i = 0; i < nrofBeastBoysToGenerate; i++) {
            nrofBeastBoys++;
            float x = (float) Math.random() * 21 - 11;  // Random between -11 to 10
            float y = (float) Math.random() * 12 - 6;  // Random between -6 to 5
            float z = (float) Math.random() * 20 - 30; // Random between -30 to -10
            float scale = (float) Math.random() * 5 - 1;
            Entity beastBoy = new Entity("beastBoy-entity" + nrofBeastBoys, BeastBoyModel.getId());
            beastBoy.setPosition(x, y, z);
            beastBoy.setScale(scale);
            beastBoy.updateModelMatrix();
            scene.addEntity(beastBoy);
            titans.add(beastBoy);
        }
    }

    private void generateRavens(Scene scene) {
        for (int i = 0; i < nrofRavensToGenerate; i++) {
            nrofRavens++;
            float x = (float) Math.random() * 21 - 11;  // Random between -11 to 10
            float y = (float) Math.random() * 12 - 6;  // Random between -6 to 5
            float z = (float) Math.random() * 20 - 30; // Random between -30 to -10
            float scale = (float) Math.random() * 5 - 1+4;
            Entity Raven = new Entity("Raven-entity" + nrofBeastBoys, RavenModel.getId());
            Raven.setPosition(x, y, z);
            Raven.setScale(scale);
            Raven.updateModelMatrix();
            scene.addEntity(Raven);
            titans.add(Raven);
        }
    }


    private void generateCyborgs(Scene scene) {
        for (int i = 0; i < nrofCyborgsToGenerate; i++) {
            nrofCybotgs++;
            float x = (float) Math.random() * 21 - 11;  // Random between -11 to 10
            float y = (float) Math.random() * 12 - 6;  // Random between -6 to 5
            float z = (float) Math.random() * 20 - 30; // Random between -30 to -10
            float scale = (float) Math.random() * 5 - 1;
            Entity Cybo = new Entity("Cyborg-entity" + nrofCybotgs, CyborgModel.getId());
            Cybo.setPosition(x, y, z);
            Cybo.setScale(scale);
            Cybo.updateModelMatrix();
            scene.addEntity(Cybo);
            titans.add(Cybo);
        }
    }
    public double GetFinalScore(){
        return FinalScore;
    }

    public double GetFPSAverage(){
        return FPSAverage;
    }

    public int GetRunsNumber(){
        return TotalRuns;
    }

    public void setCubesToGenerate(int nrOfCubesToGenerate) {
        GPUBenchmarky.nrOfCubesToGenerate =nrOfCubesToGenerate;
    }
}
