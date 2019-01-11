package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		boolean isBought = false;
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		Camera camera = new Camera();
		
		float[] vertices = {
			    -0.5f, 0.5f, 0f,   //v0
			    -0.5f, -0.5f, 0f,  //v1
			    0.5f, -0.5f, 0f,   //v2
			    0.5f, 0.5f, 0f     //v3
			  };
		
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] textureCoords = {
				0, 0,
				0, 1,
				1, 1,
				1, 0
		};
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3f(0, -3, -20), 0, 180, 0, 1);
		
		RawModel model_1 = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture_1 = new ModelTexture(loader.loadTexture("Download"));
		TexturedModel texturedModel_1 = new TexturedModel(model_1, texture_1);
		Entity entity_1 = new Entity(texturedModel_1, new Vector3f(0, 0, -2), 0, 0, 0, 1);
		
		while(!Display.isCloseRequested()) {
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
