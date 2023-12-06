import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest", "kotest-assertions-core", "5.5.4")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation(kotlin("stdlib-jdk8"))
}


tasks {
    wrapper {
        gradleVersion = "7.6"
    }

    test {
        useJUnitPlatform()
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}