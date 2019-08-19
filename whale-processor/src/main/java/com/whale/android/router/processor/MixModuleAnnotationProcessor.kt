package com.whale.android.router.processor

import javax.lang.model.element.TypeElement

interface MixModuleAnnotationProcessor {

    fun startProcessAnnotation(moduleName: String, mainModule: Boolean, dependencyModuleNames: UniqueValueList)

    fun processAnnotation(
        moduleName: String,
        mainModule: Boolean,
        dependencyModuleNames: UniqueValueList,
        typeElement: TypeElement
    )

    fun endProcessAnnotation(
        moduleName: String,
        mainModule: Boolean,
        dependencyModuleNames: UniqueValueList,
        generatedDirPath: String
    )

    fun getSupportAnnotation(): Class<out Annotation>
}