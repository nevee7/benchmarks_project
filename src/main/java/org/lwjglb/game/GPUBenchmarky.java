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
    private static final long CUBE_GENERATION_INTERVAL = 10000;

    private static final int NUM_CHUNKS = 4;
    private Entity[][] terrainEntities;

    public static int nrOfCubesToGenerate=1;
    private long lastCubeGenerationTime;

    private Model cubeModel;
    private Model BeastBoyModel;

    private Model RavenModel;
    private Model CyborgModel;

    private Model StarModel;

    private Model RobinModel;

    private static final List<Entity> cubes = new ArrayList<>();

    private static final List<Entity> titans = new ArrayList<>();

    private static int nrOfCubes = 0;
    private static int nrofBeastBoys=0;
    private static int nrofRavens=0;
    private static int nrofRobins=0;
    private  static int nrofStars=0;

    private int nrofCybotgs=0;

    private  static int nrofCyborgsToGenerate=1;
    private static int nrofBeastBoysToGenerate=1;
    private static int nrofRavensToGenerate=1;
    private static int nrofRobinsToGenerate=1;
    private static int nrofStarFireToGenerate=1;


    private float rotation;
    private float lightAngle;

    private double FinalScore;
    private double FPSAverage;
    private int TotalRuns;
    private int TotalGeneratedEntities=0;

    private static int total_no_cubes=0;
    private static int total_no_robins=0;
    private static int total_no_cyborgs=0;
    private static int total_no_ravens=0;
    private static int total_no_sfires=0;
    private static int total_no_bboys=0;


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
        System.out.println("Number of generated cubes:  "+ total_no_cubes);
        System.out.println("Number of generated robins:  "+ total_no_robins);
        System.out.println("Number of generated ravens:  "+ total_no_ravens);
        System.out.println("Number of generated stars:  "+ total_no_cyborgs);
        System.out.println("Number of generated cyborgs:  "+ total_no_sfires);
        System.out.println("Number of generated bboys:  "+ total_no_bboys);
        total_no_cubes=0;
        total_no_robins=0;
        total_no_ravens=0;
        total_no_cyborgs=0;
        total_no_bboys=0;
        total_no_sfires=0;
        cubes.clear();
        titans.clear();
        nrOfCubes = 0;
        nrofBeastBoys=0;
        nrofRavens=0;
        nrofCybotgs=0;
        nrofStars=0;
        nrofRobins=0;

        for (int i = 0; i < totalRuns; i++) {
            Engine gameEng = new Engine("GPU-Benchmark:" + (i + 1) + "/" + totalRuns, new Window.WindowOptions(), GPUBenchmarky, i + 1);
            gameEng.start();
            allFpsValues.addAll(gameEng.getFpsList());
            gameEng.stop();
            System.out.println("Number of generated cubes:  "+ total_no_cubes);
            System.out.println("Number of generated robins:  "+ total_no_robins);
            System.out.println("Number of generated ravens:  "+ total_no_ravens);
            System.out.println("Number of generated stars:  "+ total_no_cyborgs);
            System.out.println("Number of generated cyborgs:  "+ total_no_sfires);
            System.out.println("Number of generated bboys:  "+ total_no_bboys);
            int total_no_entities = total_no_bboys + total_no_cyborgs + total_no_ravens + total_no_sfires + total_no_robins;
            TotalGeneratedEntities=TotalGeneratedEntities+total_no_cubes+ total_no_entities;
            total_no_robins=0;
            total_no_ravens=0;
            total_no_cyborgs=0;
            total_no_bboys=0;
            total_no_sfires=0;
            total_no_entities =0;
            total_no_cubes=0;
            cubes.clear();
            titans.clear();
            nrOfCubes = 0;
            nrofBeastBoys=0;
            nrofRavens=0;
            nrofCybotgs=0;
            nrofStars=0;
            nrofRobins=0;

            if ((i + 1) % batchSize == 0) {
                List<Double> fpsBatch = allFpsValues.subList(i + 1 - batchSize, i + 1);
                FPSAverage= calculateAverage(fpsBatch);
                System.out.println("\nAverage FPS after " + (i + 1) + " runs: " + FPSAverage);
            }
        }

        double finalScore = calculateFinalScore(allFpsValues);
        FinalScore=finalScore;
        System.out.println("\nFinal score: " + finalScore);
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

        RavenModel = ModelLoader.loadModel("raven-model", "resources/models/Raven/Raven.obj", scene.getTextureCache());
        scene.addModel(RavenModel);

        CyborgModel = ModelLoader.loadModel("cyborg-model", "resources/models/Cyborg/cyborg.obj", scene.getTextureCache());
        scene.addModel(CyborgModel);

        StarModel = ModelLoader.loadModel("star-model", "resources/models/Starfire/theteentitansGamecube.obj", scene.getTextureCache());
        scene.addModel(StarModel);

        RobinModel = ModelLoader.loadModel("robi-model", "resources/models/Robin/Robin.obj", scene.getTextureCache());
        scene.addModel(RobinModel);

        Entity RobinEntity = new Entity("robi-entity", RobinModel.getId());
        RobinEntity.setPosition(0, -4, -10);
        RobinEntity.setScale(2.2f);
        RobinEntity.updateModelMatrix();
        scene.addEntity(RobinEntity);

        Entity RavenEntity = new Entity("raven-entity", RavenModel.getId());
        RavenEntity.setPosition(8, -4, -10);
        RavenEntity.setScale(3);
        RavenEntity.updateModelMatrix();
        scene.addEntity(RavenEntity);

        Entity StarfireEntity = new Entity("raven-entity", StarModel.getId());
        StarfireEntity.setPosition(-8, 0, -10);
        StarfireEntity.setScale(2.4f);
        StarfireEntity.updateModelMatrix();
        scene.addEntity(StarfireEntity);

        Entity beastBoyEntity = new Entity("beast-entity", BeastBoyModel.getId());
        beastBoyEntity.setPosition(-4, -1, -10);
        beastBoyEntity.updateModelMatrix();
        scene.addEntity(beastBoyEntity);

        Entity Cyborg = new Entity("Cyborg-entity", CyborgModel.getId());
        Cyborg.setPosition(4, 2, -10);
        Cyborg.setScale(1.25f);
        Cyborg.updateModelMatrix();
        scene.addEntity(Cyborg);

        titans.add(beastBoyEntity);
        titans.add(RavenEntity);
        titans.add(Cyborg);
        titans.add(StarfireEntity);
        titans.add(RobinEntity);

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
            generateStarFires(scene);
            generateRobins(scene);

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
            total_no_cubes=nrOfCubes;
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
            total_no_bboys=nrofBeastBoys;
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
            total_no_ravens=nrofRavens;
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
            total_no_cyborgs=nrofCybotgs;
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

    private void generateStarFires(Scene scene) {
        for (int i = 0; i < nrofStarFireToGenerate; i++) {
            total_no_sfires=nrofStars;
            nrofStars++;
            float x = (float) Math.random() * 21 - 11;  // Random between -11 to 10
            float y = (float) Math.random() * 12 - 6;  // Random between -6 to 5
            float z = (float) Math.random() * 20 - 30; // Random between -30 to -10
            float scale = (float) Math.random() * 5 - 1;
            Entity Starfire = new Entity("Star-entity" + nrofStars, StarModel.getId());
            Starfire.setPosition(x, y, z);
            Starfire.setScale(scale);
            Starfire.updateModelMatrix();
            scene.addEntity(Starfire);
            titans.add(Starfire);
        }
    }

    private void generateRobins(Scene scene) {
        for (int i = 0; i < nrofRobinsToGenerate; i++) {
            total_no_robins=nrofRobins;
            nrofRobins++;
            float x = (float) Math.random() * 21 - 11;  // Random between -11 to 10
            float y = (float) Math.random() * 12 - 6;  // Random between -6 to 5
            float z = (float) Math.random() * 20 - 30; // Random between -30 to -10
            float scale = (float) Math.random() * 5 - 1;
            Entity Robin = new Entity("Robin-entity" + nrofStars, RobinModel.getId());
            Robin.setPosition(x, y, z);
            Robin.setScale(scale);
            Robin.updateModelMatrix();
            scene.addEntity(Robin);
            titans.add(Robin);
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

    public void setRobinsToGenerate(int nrOfRobinsToGenerate) {
        GPUBenchmarky.nrofRobinsToGenerate=nrOfRobinsToGenerate;
    }
    public void setCyborgsToGenerate(int nrOfCyborgsToGenerate) {
        GPUBenchmarky.nrofCyborgsToGenerate=nrOfCyborgsToGenerate;
    }
    public void setRavensToGenerate(int nrOfRavensToGenerate) {
        GPUBenchmarky.nrofRavensToGenerate=nrOfRavensToGenerate;
    }
    public void setBboysToGenerate(int nrOfBboysToGenerate) {
        GPUBenchmarky.nrofBeastBoysToGenerate=nrOfBboysToGenerate;
    }
    public void setSarsToGenerate(int nrOfStarsToGenerate) {
        GPUBenchmarky.nrofStarFireToGenerate=nrOfStarsToGenerate;
    }

    public int TotalGeneratedEntities(){
        return TotalGeneratedEntities;
    }
}
