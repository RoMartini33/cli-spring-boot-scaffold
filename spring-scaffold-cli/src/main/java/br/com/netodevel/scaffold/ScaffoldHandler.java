package br.com.netodevel.scaffold;

import java.io.IOException;
import java.util.Arrays;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.springframework.boot.cli.command.options.OptionHandler;
import org.springframework.boot.cli.command.status.ExitStatus;

import br.com.generate.SupportTypes;
import br.com.generate.java.command.controller.ControllerGenerateJava;
import br.com.generate.java.command.model.ModelGenerateJava;
import br.com.generate.java.command.repository.RepositoryGenerateJava;
import br.com.generate.java.command.service.ServiceGenerateJava;
import br.com.generate.kotlin.command.ControllerGenerateKotlin;
import br.com.generate.kotlin.command.ModelGenerateKotlin;
import br.com.generate.kotlin.command.RepositoryGenerateKotlin;
import br.com.generate.kotlin.command.ServiceGenerateKotlin;
import br.com.generate.thymeleaf.ThymeleafGenerate;

/**
 * @author NetoDevel
 * @since 0.0.1
 */
public class ScaffoldHandler extends OptionHandler {

	@SuppressWarnings("unused")
	private OptionSpec<String> nameEntity;

	@SuppressWarnings("unused")
	private OptionSpec<String> parametersEntity;
	
	@SuppressWarnings("unused")
	private OptionSpec<String> language;
	
	@Override
	protected void options() {
		this.nameEntity = option(Arrays.asList("nameEntity", "n"), "Name of entity to generate scaffold").withRequiredArg();
		this.parametersEntity = option(Arrays.asList("parameterEntity", "p"), "Parameter of entity to generate scaffold").withRequiredArg();
		this.language = option(Arrays.asList("language", "l"), "language generate java or kotlin").withOptionalArg();
	}
	
	@Override
	protected ExitStatus run(OptionSet options) throws Exception {
		String nameClass = (String) options.valueOf("n");
		String parametersClass = (String) options.valueOf("p");
		String language = (String) options.valueOf("l");
		
		
		if (language == null) {
			generateJava(nameClass.trim(), parametersClass.trim());
		} else if (language.trim().equals("java")) {
			generateJava(nameClass.trim(), parametersClass.trim());
		} else if (language.trim().equals("kotlin")) {
			generateScaffoldKotlin(nameClass.trim(), parametersClass.trim());
		}
		return ExitStatus.OK;
	}

	private void generateJava(String nameClass, String parametersClass) throws IOException {
		generateScaffoldJava(nameClass, parametersClass);
	}

	private void generateScaffoldKotlin(String nameClass, String parametersClass) {
		new ModelGenerateKotlin(nameClass, parametersClass);
		new RepositoryGenerateKotlin(nameClass);
		new ServiceGenerateKotlin(nameClass);
		new ControllerGenerateKotlin(nameClass);
	}
	
	private void generateScaffoldJava(String nameClass, String parametersClass) throws IOException {
		new ModelGenerateJava().generate(nameClass, parametersClass, "template-model.txt");
		new RepositoryGenerateJava().generate(nameClass, null, "template-repository.txt");
		new ServiceGenerateJava().generate(nameClass, null, "template-service.txt");
		new ControllerGenerateJava().generate(nameClass, null, "template-controller.txt");
		new ThymeleafGenerate(nameClass, parametersClass);
	}
	
}
