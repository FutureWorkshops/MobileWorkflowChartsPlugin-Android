// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

buildscript {
    val kotlin_version by extra("1.4.30")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven {
            name = "Mobile Workflow"
            url = uri("https://raw.githubusercontent.com/FutureWorkshops/MobileWorkflowCore-Android-Distribution/main")
            credentials(HttpHeaderCredentials::class) {
                val properties = java.util.Properties()
                properties.load(project.rootProject.file("local.properties").inputStream())
                name = "Authorization"
                value = "token ${properties.getProperty("project.githubPAT")}"
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}