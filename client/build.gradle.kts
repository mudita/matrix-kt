import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    `maven-publish`
}

val ktorVersion = "1.3.1"
val serialVersion = "0.14.0"

kotlin {
    jvm()
    js {
        nodejs()
    }
    linuxX64()
    mingwX64()
    macosX64()
    iosArm32()
    iosArm64()
    iosX64()

    sourceSets {
        commonMain {
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")

            dependencies {
                implementation(kotlin("stdlib-common"))

                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
        commonTest {
            kotlin.srcDir("src/test/kotlin")
            resources.srcDir("src/test/resources")

            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation("io.ktor:ktor-client-mock:$ktorVersion")
                implementation(project(":testutils"))
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
                implementation("io.ktor:ktor-client-logging-jvm:$ktorVersion")
            }
        }
        named("jvmTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))

                implementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")
            }
        }
        named("jsMain") {
            dependencies {
                implementation(kotlin("stdlib-js"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialVersion")
                implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")

                // https://github.com/Kotlin/kotlinx-io/issues/57
                api(npm("text-encoding"))
            }
        }
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))

                implementation("io.ktor:ktor-client-mock-js:$ktorVersion")
            }
        }
    }

    targets.withType<KotlinNativeTarget> {
        compilations.named("main") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serialVersion")
                implementation("io.ktor:ktor-client-serialization-native:$ktorVersion")
            }
        }
        compilations.named("test") {
            dependencies {
                implementation("io.ktor:ktor-client-mock-native:$ktorVersion")
            }
        }
    }
}
