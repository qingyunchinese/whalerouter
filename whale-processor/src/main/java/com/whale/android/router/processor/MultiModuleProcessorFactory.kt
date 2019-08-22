package com.whale.android.router.processor

import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@SupportedAnnotationTypes(value = [Constants.ROUTER_ANNOTATION_TYPE])
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(value = [Constants.KOTLIN_GENERATED_OPTION_NAME, Constants.KEY_MODULE_NAME, Constants.KEY_DEPENDENCY_ROUTER_NAMES])
class MultiModuleProcessorFactory : AbstractProcessor() {
    //文件生成器
    lateinit var filer: Filer
    //日志打印类
    lateinit var gradleLogger: GradleLogger
    lateinit var types: Types
    lateinit var elementUtils: Elements

    var debug = true

    private val processorImpls = mutableListOf<MultiModuleAnnotationProcessor>()

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)

        filer = processingEnv.filer
        types = processingEnv.typeUtils
        elementUtils = processingEnv.elementUtils

        gradleLogger = GradleLogger(processingEnvironment.messager, debug)

        processorImpls.add(MultiModuleRouterProcessor(gradleLogger, elementUtils, types))
    }

    @Synchronized
    override fun process(typeElements: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {

        val kotlinGeneratedDir = processingEnv.options[Constants.KOTLIN_GENERATED_OPTION_NAME] ?: run {
            gradleLogger.errorMessage("Can't find the target directory for generated Kotlin files.")
            return false
        }

        val moduleName = processingEnv.options[Constants.KEY_MODULE_NAME] ?: run {
            gradleLogger.errorMessage("Can't find module name.")
            return false
        }

        var mainModule = false
        var dependencyModuleNameList = UniqueValueList()

        if (processingEnv.options.containsKey(Constants.KEY_DEPENDENCY_ROUTER_NAMES)) {
            var dependencyModuleNames = processingEnv.options[Constants.KEY_DEPENDENCY_ROUTER_NAMES]
            dependencyModuleNames = dependencyModuleNames ?: ""
            mainModule = dependencyModuleNames.isNotEmpty()
            dependencyModuleNames.split(",").forEach {
                dependencyModuleNameList.add(it)
            }
        }

        gradleLogger.debugMessage(kotlinGeneratedDir)
        gradleLogger.debugMessage(moduleName)

        processorImpls.forEach { annotationProcessor ->
            val annotatedElements =
                roundEnvironment.getElementsAnnotatedWith(annotationProcessor.getSupportAnnotation())
            if (annotatedElements.isEmpty()) {
                return false
            }
            annotatedElements?.let { notNullElements ->
                notNullElements.isNotEmpty().apply {
                    annotationProcessor.startProcessAnnotation(moduleName, mainModule, dependencyModuleNameList)
                    notNullElements.filter { pTypeElement ->
                        pTypeElement is TypeElement
                    }.map { aTypeElement ->
                        aTypeElement as TypeElement
                    }.forEach { typeElement ->
                        annotationProcessor.processAnnotation(
                            moduleName,
                            mainModule,
                            dependencyModuleNameList,
                            typeElement
                        )
                    }
                    annotationProcessor.endProcessAnnotation(
                        moduleName,
                        mainModule,
                        dependencyModuleNameList,
                        kotlinGeneratedDir
                    )
                }
            }
        }


        return false
    }

}
