package se.idoapps.processor

import com.google.auto.service.AutoService
import se.idoapps.annotations.Test
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(TestProcessor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedAnnotationTypes("se.idoapps.annotations.Test")
class TestProcessor: AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        println("KALLE_ANKA 1")
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "KALLE_ANKA 2")

        roundEnv.getElementsAnnotatedWith(Test::class.java).forEach {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "${it.simpleName} is interesting.")
        }
        return true
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}

//@SupportedOptions(TestProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)