package org.bot.script.loader;

import org.bot.Engine;
import org.bot.classloader.ASMClassLoader;
import org.bot.classloader.ClassArchive;
import org.bot.provider.ServerProvider;
import org.bot.provider.manifest.NullManifestException;
import org.bot.script.scriptdata.ScriptData;
import org.bot.script.scriptdata.ScriptManifest;
import org.bot.script.types.LoopScript;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by Ethan on 7/13/2017.
 */

public class ScriptLoader {
	private static final List<ScriptData> scripts = new ArrayList<>();

	public static List<ScriptData> getScripts() {
		scripts.clear();
		for (ScriptData scriptData : getLocalJarScripts()) {
			scripts.add(scriptData);
		}
		/**
		 * Another for loop here for localClassScripts, once we add.
		 */
		return scripts;
	}

	private static ArrayList<ScriptData> getLocalJarScripts() {
		final ArrayList<ScriptData> scripts = new ArrayList<>();
		JarInputStream inputStream = null;
		try {
			for (File file : Engine.getDirectoryManager().getRootDirectory().getSubDirectory("Scripts")
					.getFiles()) {
				Engine.setClassArchive(new ClassArchive());
				Engine.getClassArchive().addJar((new File(file.getAbsolutePath()).toURI().toURL()));
				ASMClassLoader cl = new ASMClassLoader(Engine.getClassArchive());
				inputStream = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				while ((jarEntry = inputStream.getNextJarEntry()) != null) {
					if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
						Class<?> clazz;
						String classPackage = jarEntry.getName().replace(".class", "");
						clazz = cl.loadClass(classPackage.replaceAll("/", "."));
						if (clazz.isAnnotationPresent(ScriptManifest.class)) {
							System.out.println("Found a script");
							final ScriptManifest manifest = clazz.getAnnotation(ScriptManifest.class);
							if (manifest == null) {
								throw new NullManifestException();
							}
							ScriptData scriptData = new ScriptData(classPackage.replaceAll("/", "."), manifest.name(), manifest.server(), manifest.description(), manifest.version(), manifest.author(), manifest.category(), new File(file.getAbsolutePath()));
							scripts.add(scriptData);
							System.out.println("added a script");

						}
					}
				}
			}
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException | ClassNotFoundException
				| InvalidDirectoryNameException e) {
			e.printStackTrace();
		} catch (NullManifestException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		return scripts;
	}

	public static LoopScript loadScript(ScriptData scriptData) {
		LoopScript loopScript = null;
		try {
			Engine.getClassArchive().addJar(scriptData.scriptPath.toURI().toURL());
			for (Map.Entry<String, ServerProvider> providerEntry : Engine.getServerProviders().entrySet()) {
				if (providerEntry.getValue().getManifest().serverName().equals(Engine.getServerProvider().getManifest().serverName())) {
					Engine.getClassArchive().addJar(Engine.getDirectoryManager().getRootDirectory().getSubDirectory(DirectoryManager.SERVER_PROVIDERS).getFile(providerEntry.getKey()).toURI().toURL());
				}
			}
			ASMClassLoader classLoader = new ASMClassLoader(Engine.getClassArchive());
			loopScript = (LoopScript) classLoader.loadClass(scriptData.clazz).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loopScript;
	}
}
