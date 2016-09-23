package org.detoeuf

class SwaggerPluginExtension {
    String inputSpec
    String output
    String language
    Map<String,Object> additionalProperties
    String models
    String apis
    String supportingFiles
    Boolean cleanOutputDir = true
    Map<String,String> typeMappings
    Map<String,String> importMappings
}
