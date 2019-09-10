package com.whale.android.router.processor

import javax.annotation.processing.Filer
import javax.lang.model.element.TypeElement

interface MultiModuleAnnotationProcessor {

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
        generatedFiler: Filer
    )

    fun getSupportAnnotation(): Class<out Annotation>
}