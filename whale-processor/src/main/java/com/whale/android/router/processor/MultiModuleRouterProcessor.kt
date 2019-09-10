package com.whale.android.router.processor

import com.squareup.kotlinpoet.*
import com.whale.android.router.annotation.Router
import com.whale.android.router.mapping.RouterType
import java.io.File
import java.lang.StringBuilder
import javax.annotation.processing.Filer
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import kotlin.collections.HashSet


class MultiModuleRouterProcessor(
    private val gradleLogger: GradleLogger,
    private val elementUtils: Elements,
    private val types: Types
) : MultiModuleAnnotationProcessor {

    private val androidActivity: TypeMirror? by lazy {
        elementUtils.getTypeElement(RouterType.ACTIVITY)?.asType()
    }
    private val androidSupportFragment: TypeMirror? by lazy {
        elementUtils.getTypeElement(RouterType.FRAGMENT)?.asType()
    }

    private var ROUTER_MAPPING_CLASS = "WhaleRouterMapping"
    private var ROUTER_MODULE_MAPPING_CLASS = "Whale_{moduleName}_RouterMapping"
    private val ROUTER_MAPPING_INIT_NAME = "initRouter"

    private lateinit var generatedModuleKtFile: FileSpec.Builder

    private val generatedRouterInitKtFile: FileSpec.Builder by lazy {
        FileSpec.builder(Constants.ROUTER_ANNOTATION_CLASS_PACKAGE, ROUTER_MAPPING_CLASS)
    }

    private val generatedRouterInitKtMethod: FunSpec.Builder by lazy {
        FunSpec.builder(ROUTER_MAPPING_INIT_NAME)
    }


    private val generatedKtMethod: FunSpec.Builder by lazy {
        FunSpec.builder(ROUTER_MAPPING_INIT_NAME)
    }


    override fun startProcessAnnotation(
        moduleName: String,
        mainModule: Boolean,
        dependencyModuleNames: UniqueValueList
    ) {

        generatedModuleKtFile =
            FileSpec.builder(Constants.ROUTER_ANNOTATION_CLASS_PACKAGE, getModuleMappingClassName(moduleName))

        gradleLogger.debugMessage("$moduleName:MultiModuleRouterProcessor--->startProcessAnnotation")

        generatedModuleKtFile
            .addImport("com.whale.android.router", "WhaleService")
            .addImport("com.whale.android.router.mapping", "RouteMapping")
            .addImport("com.whale.android.router.mapping", "RouterType")
    }

    override fun processAnnotation(
        moduleName: String,
        mainModule: Boolean,
        dependencyModuleNames: UniqueValueList,
        typeElement: TypeElement
    ) {

        gradleLogger.debugMessage("$moduleName:MultiModuleRouterProcessor--->processAnnotation")

        val routerAnnotation = typeElement.getAnnotation(Router::class.java)
        val routerPathArray = routerAnnotation.path
        val routerType = getRouterType(typeElement.asType())

        val requiredAuthor = routerAnnotation.requiredAuthor

        val requiredParams = routerAnnotation.requiredParams
        val requiredParamsFiledText = StringBuilder()
        requiredParams.forEach {
            if (requiredParamsFiledText.isEmpty()) {
                requiredParamsFiledText.append("\"$it\"")
            } else {
                requiredParamsFiledText.append(",\"$it\"")
            }
        }

        routerPathArray.forEach {
            val packageName = "${typeElement.qualifiedName}".replace(".${typeElement.simpleName}", "")
            val routerClass = ClassName(packageName, "${typeElement.simpleName}")
            var statement =
                "WhaleService.add(RouteMapping(%S,%S,%T::class.java,%S,arrayOf($requiredParamsFiledText),%L))"
            if (requiredParamsFiledText.isEmpty()) {
                statement = "WhaleService.add(RouteMapping(%S,%S,%T::class.java,%S,arrayOf<String>(),%L))"
            }
            generatedKtMethod.addStatement(
                statement,
                routerType,
                it,
                routerClass,
                moduleName,
                requiredAuthor
            )
        }
    }

    override fun endProcessAnnotation(
        moduleName: String,
        mainModule: Boolean,
        dependencyModuleNames: UniqueValueList,
        generatedFiler: Filer
    ) {

        gradleLogger.debugMessage("$moduleName:MultiModuleRouterProcessor--->endProcessAnnotation")

        generatedModuleKtFile.addType(
            TypeSpec.classBuilder(getModuleMappingClassName(moduleName))
                .addSuperinterfaces(getRouterMappingImpl())
                .addFunction(generatedKtMethod.addModifiers(KModifier.OVERRIDE).build()).build()
        )

        generatedModuleKtFile.build().writeTo(generatedFiler)

        buildRouterMappingInitFile(moduleName, mainModule, dependencyModuleNames, generatedFiler)
    }

    override fun getSupportAnnotation(): Class<out Annotation> {
        return Router::class.java
    }

    private fun getRouterType(typeMirror: TypeMirror): String {
        return when (true) {
            types.isSubtype(typeMirror, androidActivity) -> {
                return RouterType.ACTIVITY
            }
            types.isSubtype(typeMirror, androidSupportFragment) -> {
                return RouterType.FRAGMENT
            }
            else -> {
                ""
            }
        }
    }

    private fun getModuleMappingClassName(moduleName: String): String {
        val moduleClassName = moduleName.replace("-", "_")
        return ROUTER_MODULE_MAPPING_CLASS.replace("{moduleName}", moduleClassName)
    }

    private fun getRouterMappingImpl(): HashSet<TypeName> {
        val implementsInterfaces = HashSet<TypeName>()

        implementsInterfaces.add(
            ClassName("com.whale.android.router.mapping", "RouterMappingImpl")
        )

        return implementsInterfaces
    }

    private fun buildRouterMappingInitFile(
        moduleName: String,
        mainModule: Boolean,
        dependencyModuleNames: UniqueValueList,
        generatedFiler: Filer
    ) {

        if (!mainModule) return

        dependencyModuleNames.plusLast(moduleName)

        dependencyModuleNames.apply {
            reverse()
        }.forEach {
            var moduleClassName = getModuleMappingClassName(it)
            moduleClassName = moduleClassName.replace("-", "_")
            generatedRouterInitKtMethod.addStatement(
                "$moduleClassName().$ROUTER_MAPPING_INIT_NAME()"
            )
        }

        generatedRouterInitKtFile.addType(
            TypeSpec.classBuilder(ROUTER_MAPPING_CLASS)
                .addSuperinterfaces(getRouterMappingImpl())
                .addFunction(generatedRouterInitKtMethod.addModifiers(KModifier.OVERRIDE).build())
                .build()
        )

        generatedRouterInitKtFile.build().writeTo(generatedFiler)
    }

}
