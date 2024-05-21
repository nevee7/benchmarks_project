package org.lwjglb.game;

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

import static org.lwjgl.opengl.GL11C.*;

public class GPUBenchmarky implements IAppLogic {

    private static final long CUBE_GENERATION_INTERVAL = 1000;

    public static int nrOfCubesToGenerate=1;
    private long lastCubeGenerationTime;

    private Model cubeModel;

    private static final List<Entity> cubes = new ArrayList<>();

    private static final List<Entity> titans = new ArrayList<>();

    private static int TitansToGenerate=5;

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

    private static int totalRuns=2;
    Model[] TitansModels=new Model[5];


    public static void main(String[] args) {
        GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
        GPUBenchmarky.runMain();
    }


    public BenchmarkInfo runMain() {
        System.out.println(nrOfCubesToGenerate);
        GPUBenchmarky GPUBenchmarky = new GPUBenchmarky();
        TotalRuns=totalRuns;
        int batchSize = 1;
        List<Double> allFpsValues = new ArrayList<>();

        Engine warmup = new Engine("GPU-Benchmark: Warm-Up", new Window.WindowOptions(), GPUBenchmarky, 0);
        warmup.start();
        warmup.stop();

        for (int i = 0; i < totalRuns; i++) {
            resetCountersAndEntities();
            Engine gameEng = new Engine("GPU-Benchmark:" + (i + 1) + "/" + totalRuns, new Window.WindowOptions(), GPUBenchmarky, i + 1);
            gameEng.start();
            allFpsValues.addAll(gameEng.getFpsList());
            gameEng.stop();

            updateTotalGeneratedEntities();

            if ((i + 1) % batchSize == 0) {
                List<Double> fpsBatch = allFpsValues.subList(i + 1 - batchSize, i + 1);
                FPSAverage = calculateAverage(fpsBatch);
                System.out.println("\nAverage FPS after " + (i + 1) + " runs: " + FPSAverage);
            }
        }

        double finalScore = calculateFinalScore(allFpsValues);
        FinalScore=finalScore;
        System.out.println("\nFinal score: " + finalScore);
        return new BenchmarkInfo("GPU benchmark", finalScore, nrOfCubesToGenerate);
    }

    private double calculateFinalScore(List<Double> allFpsValues) {
        double avgFPS = calculateAverage(allFpsValues);
        double minFPS = Collections.min(allFpsValues);
        double normalizedAvg = normalize(avgFPS, allFpsValues);

        double generatedStuff=this.TotalGeneratedEntities();
        double stressLevel=(generatedStuff-total_no_cubes)*0.09+total_no_cubes*0.0001;
        double weightAvg = 0.5;
        double weightMin = 0.3;
        double weightAvgDef=0.2;

        return weightAvg * normalizedAvg + weightMin * minFPS + weightAvgDef * avgFPS+stressLevel;
    }


    private static double calculateAverage(List<Double> values) {
        double sum = 0;
        double max=-1;
        double min=10000000;
        for (double value : values) {
            sum += value;
            if(value>max)
                max=value;
            if(value < min)
                min=value;
        }

        System.out.println("Max:"+max);
        System.out.println("Min:"+min);
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


    private void resetCountersAndEntities() {
        // Reset counters
        total_no_cubes = 0;
        total_no_robins = 0;
        total_no_ravens = 0;
        total_no_cyborgs = 0;
        total_no_bboys = 0;
        total_no_sfires = 0;

        // Clear entities lists
        cubes.clear();
        titans.clear();
    }


    private void updateTotalGeneratedEntities() {
        int total_no_entities = total_no_bboys + total_no_cyborgs + total_no_ravens + total_no_sfires + total_no_robins;
        if(total_no_entities==0) TotalGeneratedEntities = TotalGeneratedEntities +  total_no_cubes;
        else TotalGeneratedEntities = TotalGeneratedEntities + total_no_cubes + total_no_entities;
    }


    @Override
    public void init(Window window, Scene scene, Render render) {
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        if(nrOfCubesToGenerate != 0){
            cubeModel = ModelLoader.loadModel("cube-model", "resources/models/cube/cube.obj", scene.getTextureCache());
            scene.addModel(cubeModel);

            Entity cubeEntity = new Entity("cube-entity", cubeModel.getId());
            cubeEntity.setPosition(0, 0f, -10);
            cubeEntity.updateModelMatrix();
            scene.addEntity(cubeEntity);

            cubes.add(cubeEntity);
        }

        if(TitansToGenerate!=0){
            Model beastBoyModel = ModelLoader.loadModel("beast-model", "resources/models/Beastboy/untitled.obj", scene.getTextureCache());
            scene.addModel(beastBoyModel);
            TitansModels[0]= beastBoyModel;

            Model ravenModel = ModelLoader.loadModel("raven-model", "resources/models/Raven/Raven.obj", scene.getTextureCache());
            scene.addModel(ravenModel);
            TitansModels[1]= ravenModel;

            Model cyborgModel = ModelLoader.loadModel("cyborg-model", "resources/models/Cyborg/cyborg.obj", scene.getTextureCache());
            scene.addModel(cyborgModel);
            TitansModels[2]= cyborgModel;

            Model starModel = ModelLoader.loadModel("star-model", "resources/models/Starfire/theteentitansGamecube.obj", scene.getTextureCache());
            scene.addModel(starModel);
            TitansModels[3]= starModel;

            Model robinModel = ModelLoader.loadModel("robin-model", "resources/models/Robin/Robin.obj", scene.getTextureCache());
            scene.addModel(robinModel);
            TitansModels[4]= robinModel;

            Entity RobinEntity = new Entity("robi-entity", robinModel.getId());
            RobinEntity.setPosition(0, -4, -10);
            RobinEntity.setScale(2.2f);
            RobinEntity.updateModelMatrix();
            scene.addEntity(RobinEntity);

            Entity RavenEntity = new Entity("raven-entity", ravenModel.getId());
            RavenEntity.setPosition(8, -4, -10);
            RavenEntity.setScale(3);
            RavenEntity.updateModelMatrix();
            scene.addEntity(RavenEntity);

            Entity StarfireEntity = new Entity("raven-entity", starModel.getId());
            StarfireEntity.setPosition(-8, 0, -10);
            StarfireEntity.setScale(2.4f);
            StarfireEntity.updateModelMatrix();
            scene.addEntity(StarfireEntity);

            Entity beastBoyEntity = new Entity("beast-entity", beastBoyModel.getId());
            beastBoyEntity.setPosition(-4, -1, -10);
            beastBoyEntity.updateModelMatrix();
            scene.addEntity(beastBoyEntity);

            Entity Cyborg = new Entity("Cyborg-entity", cyborgModel.getId());
            Cyborg.setPosition(4, 2, -10);
            Cyborg.setScale(1.25f);
            Cyborg.updateModelMatrix();
            scene.addEntity(Cyborg);

            titans.add(beastBoyEntity);
            titans.add(RavenEntity);
            titans.add(Cyborg);
            titans.add(StarfireEntity);
            titans.add(RobinEntity);
        }

        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.3f);
        scene.setSceneLights(sceneLights);

        sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 1.0f));

        Vector3f coneDir = new Vector3f(0, 0, -1);
        sceneLights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140.0f));

        lastCubeGenerationTime = System.currentTimeMillis();


        // Initialize skybox
        SkyBox skyBox = new SkyBox("resources/models/skybox/skybox.obj", scene.getTextureCache());
        skyBox.getSkyBoxEntity().setScale(50);
        scene.setSkyBox(skyBox);

        //fog on objects
        scene.setFog(new Fog(true, new Vector3f(0.5f, 0.5f, 0.5f), 0.01f));

        scene.getCamera().moveUp(0.1f);

        lightAngle = -35;
    }


    @Override
    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        SceneLights sceneLights = scene.getSceneLights();
        DirLight dirLight = sceneLights.getDirLight();
        double angRad = Math.toRadians(lightAngle);
        dirLight.getDirection().x = (float) Math.sin(angRad);
        dirLight.getDirection().y = (float) Math.cos(angRad);
    }

    private void generateEntities(Scene scene) {
        generateCubes(scene);
        for(int i=0;i<TitansToGenerate;i++)
        {
            generateTitan(scene,TitansModels[i%5]);
        }
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
            generateEntities(scene);
            lastCubeGenerationTime = currentTime;
        }
    }


    private void generateCubes(Scene scene) {
        for (int i = 0; i < nrOfCubesToGenerate; i++) {
            total_no_cubes++;
            Entity cubeEntity = createEntity("cube-entity", cubeModel, scene);
            cubes.add(cubeEntity);
        }
    }


    private void generateTitan(Scene scene,Model model)
    {
        Entity Titan=createEntity("Titan", model, scene);
        titans.add(Titan);
        if(model.getId().contains("robin")){
            total_no_robins++;
        } else if (model.getId().contains("raven")) {
            total_no_ravens++;
        } else if (model.getId().contains("star")) {
            total_no_sfires++;
        } else if (model.getId().contains("beast")) {
            total_no_bboys++;
        }else
        {
            total_no_cyborgs++;
        }
    }


    private Entity createEntity(String baseName, Model model, Scene scene) {
        String entityName = baseName + System.currentTimeMillis();
        Entity entity = new Entity(entityName, model.getId());
        float x = (float) Math.random() * 21 - 11;
        float y = (float) Math.random() * 12 - 6;
        float z = (float) Math.random() * 20 - 30;
        entity.setPosition(x, y, z);
        float scale = (float) Math.random() * 5 - 1;
        entity.setScale(scale);
        entity.updateModelMatrix();
        scene.addEntity(entity);
        return entity;
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

    public void setTitansToGenerate(int TitansToGenerate) {
        GPUBenchmarky.TitansToGenerate=TitansToGenerate;
    }

    public int TotalGeneratedEntities() {
        return TotalGeneratedEntities;
    }

    public void SetTotalRuns(int totalRuns) {
        GPUBenchmarky.totalRuns=totalRuns;
    }
}
