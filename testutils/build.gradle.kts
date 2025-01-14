import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

val coroutineVersion: String by rootProject.extra

kotlin {
    jvm()
    iosArm32()
    iosArm64()
    iosX64()

    sourceSets {
        commonMain {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain.get())
            dependencies {
            }
        }
        val nativeTest by creating {
            dependsOn(commonTest.get())
        }

        for (target in targets.withType<KotlinNativeTarget>()) {
            val main = getByName("${target.name}Main")
            main.dependsOn(nativeMain)

            val test = getByName("${target.name}Test")
            test.dependsOn(nativeTest)
        }
    }
}
