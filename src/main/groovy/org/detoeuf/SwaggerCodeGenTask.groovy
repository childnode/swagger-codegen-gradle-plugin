package org.detoeuf

import io.swagger.codegen.DefaultGenerator
import io.swagger.codegen.config.CodegenConfigurator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SwaggerCodeGenTask extends DefaultTask {

    @TaskAction
    def swaggerCodeGen() {
        def swaggerPlugin = project.extensions.findByName('swagger').asType(SwaggerPluginExtension.class)
        CodegenConfigurator config = new CodegenConfigurator()

        // Configuration for language
        config.lang = swaggerPlugin.language

        // Outputdir + clean
        config.setOutputDir(project.file(swaggerPlugin.output ?: 'build/generated-sources/swagger').absolutePath)
        if (swaggerPlugin.cleanOutputDir == true) {
            project.delete(config.getOutputDir())
        }

        // Add additional properties and mappings
        if (swaggerPlugin.additionalProperties) config.additionalProperties = swaggerPlugin.additionalProperties
        if (swaggerPlugin.typeMappings) config.typeMappings = swaggerPlugin.typeMappings
        if (swaggerPlugin.importMappings) config.importMappings = swaggerPlugin.importMappings

        if (swaggerPlugin.apis != null) config.systemProperties?.putAll([ 'apis': swaggerPlugin.apis ])
        if (swaggerPlugin.models != null) config.systemProperties?.putAll([ 'models': swaggerPlugin.models ])
        if (swaggerPlugin.supportingFiles != null) config.systemProperties?.putAll([ 'supportingFiles': swaggerPlugin.supportingFiles ])

        config.inputSpec = swaggerPlugin.inputSpec

        new DefaultGenerator()
                .opts(config.toClientOptInput())
                .generate()
    }
}
