import net.minecraftforge.gradle.userdev.UserDevExtension
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val author = "favoslav"
val modIdName = "energizexp"

group = "mods.$author.$modIdName"
version = "0.1.0"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        //classpath("com.guardsquare:proguard-gradle:7.5.+")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}

plugins {
    id("net.minecraftforge.gradle")
    kotlin("jvm")
}

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository {
            maven("https://cursemaven.com")
        }
        filter {
            includeGroup("curse.maven")
        }
    }
}

val shadow: Configuration by configurations.creating {
    exclude("org.jetbrains", "annotations")
}

jarJar.enable()

dependencies {
    minecraft("net.minecraftforge:forge:1.12.2-14.23.5.2860")
    shadow("org.jetbrains.kotlin:kotlin-stdlib:${kotlin.coreLibrariesVersion}")
    shadow("org.jetbrains.kotlin:kotlin-stdlib-common:${kotlin.coreLibrariesVersion}")
    implementation(kotlin("stdlib"))
    compileOnly(files("libs/Mekanism-1.12.2-9.8.3.390.jar"))
    //implementation("curse.maven:industrialcraft-242638:3078604")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjvm-default=all")
    }
    jvmToolchain(8)
}

configure<UserDevExtension> {
    mappings("snapshot", "20171003-1.12")
    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
            property("forge.logging.console.level", "debug")
            property("forge.enabledGameTestNamespaces", "true")
            mods {
                create(modIdName) {
                    source(sourceSets.main.get())
                }
            }
        }
        create("server") {
            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
            property("forge.logging.console.level", "debug")
        }
    }
}

tasks {
    val outputFileName = modIdName

    withType<Jar> {
        exclude("META-INF/versions/**")
        exclude("module-info.class")
        archiveBaseName.set(outputFileName)
        manifest {
            attributes(
                "Specification-Title" to modIdName,
                "Specification-Vendor" to author,
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Version" to version,
                "Implementation-Vendor" to author,
                "Implementation-Timestamp" to LocalDateTime.now()
                    .atOffset(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))
            )
        }
    }

    jarJar.configure {
        from(provider { shadow.map(::zipTree).toTypedArray() })
        exclude("META-INF/versions/**")
        exclude("module-info.class")
    }
}

sourceSets.all {
    output.resourcesDir = output.classesDirs.files.iterator().next()
}
