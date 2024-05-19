package org.lwjglb.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjglb.engine.graph.Render;
import org.lwjglb.engine.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private List<Double> fpsList = new ArrayList<>(); //to store FPS values

    private int runNumber; //the no. of the current run (returned from main)

    public static final int TARGET_UPS =10000;

    private final long stopTimeMillis; // To track when to stop
    private final IAppLogic appLogic;
    private final Window window;
    private Render render;
    private boolean running;
    private Scene scene;
    private int targetFps;
    private int targetUps;
    private int nrOfFrames;
    public long TimeToClose = 10000;

    public Engine(String windowTitle, Window.WindowOptions opts, IAppLogic appLogic, int runNumber) {
        window = new Window(windowTitle, opts, () -> {
            resize();
            return null;
        });
        targetFps = opts.fps;
        targetUps = opts.ups;
        this.appLogic = appLogic;
        render = new Render(window);
        scene = new Scene(window.getWidth(), window.getHeight());
        appLogic.init(window, scene, render);
        stopTimeMillis = System.currentTimeMillis() + TimeToClose; // 10 seconds from now
        running = true;
        this.runNumber=runNumber;
    }

    private void cleanup() {
        appLogic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    private void resize() {
        int width = window.getWidth();
        int height = window.getHeight();
        scene.resize(width, height);
        render.resize(width, height);
    }

    private void run() {
        System.out.println("\nRunning Benchmark - Run #" + runNumber);
        GLFW.glfwSwapInterval(0); // Disable VSync

        // Benchmarking variables
        int totalFrames = 0;
        long startTime = System.currentTimeMillis();
        long warmUpTime = startTime + 2000; // 2-second warm-up period
        long lastFpsPrintTime = startTime; // Time of last FPS print

        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / targetUps;
        float deltaUpdate = 0;

        long updateTime = initialTime;

        IGuiInstance iGuiInstance = scene.getGuiInstance();

        while (running && !window.windowShouldClose() && System.currentTimeMillis() < stopTimeMillis) {
            window.pollEvents();

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeU;

            if (targetFps <= 0 || deltaUpdate >= 1) {
                window.getMouseInput().input();
                boolean inputConsumed = iGuiInstance != null && iGuiInstance.handleGuiInput(scene, window);
                appLogic.input(window, scene, now - initialTime, inputConsumed);
            }

            if (deltaUpdate >= 1) {
                long diffTimeMillis = now - updateTime;
                appLogic.update(window, scene, diffTimeMillis);
                updateTime = now;
                deltaUpdate--;
            }

            render.render(window, scene);
            totalFrames++; // Increment frame count
            window.update();

            // Calculate and print FPS (excluding warm-up period)
            long currentTime = System.currentTimeMillis();
            if (currentTime > warmUpTime && currentTime - lastFpsPrintTime >= 1000) { // Print FPS every second
                long elapsedTime = currentTime - startTime;
                double fps = (totalFrames * 1000.0) / elapsedTime;
                System.out.println("FPS: " + fps);
                fpsList.add(fps);
                lastFpsPrintTime = currentTime; // Reset last FPS print time
            }

            initialTime = now;
        }

        cleanup();
    }

    public List<Double> getFpsList() {
        return fpsList; //returning the list in main to compute avg
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {

        running = false;
    }

}
