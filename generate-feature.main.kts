#!/usr/bin/env kotlin

/**
 * ========================================================================================
 * GENERADOR AUTOMÁTICO DE FEATURES MVI
 * ========================================================================================
 * Este script genera features completas con arquitectura MVI automáticamente
 * Crea la estructura de carpetas (presentation, data, domain) y archivos boilerplate
 * ========================================================================================
 */

import java.io.File
import kotlin.system.exitProcess

// Colores ANSI para la terminal
object Colors {
    const val RESET = "\u001B[0m"
    const val GREEN = "\u001B[32m"
    const val RED = "\u001B[31m"
    const val YELLOW = "\u001B[33m"
    const val BLUE = "\u001B[34m"
    const val CYAN = "\u001B[36m"
    const val MAGENTA = "\u001B[35m"
    const val BOLD = "\u001B[1m"
}

// Configuración de feature
data class FeatureConfig(
    val name: String,
    val packageBase: String,
    val generatePresentation: Boolean,
    val generateData: Boolean,
    val generateDomain: Boolean
)

// Información del proyecto
data class ProjectInfo(
    val packageBase: String,
    val javaSourceDir: File
)

fun main() {
    println("${Colors.CYAN}${Colors.BOLD}")
    println("╔═══════════════════════════════════════════════════════════════╗")
    println("║      GENERADOR DE FEATURES MVI - ANDROID TEMPLATE            ║")
    println("╚═══════════════════════════════════════════════════════════════╝")
    print(Colors.RESET)
    println()

    val projectRoot = File(System.getProperty("user.dir"))

    try {
        // Detectar el package base del proyecto
        val projectInfo = detectProjectInfo(projectRoot)

        if (projectInfo == null) {
            println("${Colors.RED}❌ No se pudo detectar el package base del proyecto${Colors.RESET}")
            println("${Colors.YELLOW}Asegúrate de estar en la raíz del proyecto Android${Colors.RESET}")
            println()
            println("${Colors.CYAN}ℹ️  Información de debug:${Colors.RESET}")
            println("  Directorio actual: ${projectRoot.absolutePath}")
            val buildGradle = File(projectRoot, "app/build.gradle.kts")
            println("  ¿Existe app/build.gradle.kts?: ${if (buildGradle.exists()) "✅ Sí" else "❌ No"}")
            if (buildGradle.exists()) {
                println("  Ruta completa: ${buildGradle.absolutePath}")
            }
            println()
            println("${Colors.YELLOW}💡 Sugerencias:${Colors.RESET}")
            println("  1. Ejecuta el script de diagnóstico: test-package-detection.main.kts")
            println("  2. Verifica el Working Directory en Run → Edit Configurations")
            println("  3. Asegúrate de ejecutar desde la raíz del proyecto")
            println("  4. Ver: Docs/TROUBLESHOOTING_GENERATOR.md")
            println()
            exitProcess(1)
        }

        println("${Colors.GREEN}✅ Package base detectado: ${projectInfo.packageBase}${Colors.RESET}")
        println()

        // Recopilar features a generar
        val features = collectFeatures(projectInfo)

        if (features.isEmpty()) {
            println("${Colors.BLUE}No se generaron features. Saliendo...${Colors.RESET}")
            exitProcess(0)
        }

        // Confirmar antes de generar
        if (!confirmGeneration(features)) {
            println("${Colors.BLUE}Cancelado por el usuario.${Colors.RESET}")
            exitProcess(0)
        }

        println("\n${Colors.CYAN}${Colors.BOLD}🚀 Generando features...${Colors.RESET}\n")

        // Generar cada feature
        features.forEach { feature ->
            generateFeature(projectInfo, feature)
        }

        println("\n${Colors.GREEN}${Colors.BOLD}✅ ¡Features generadas exitosamente!${Colors.RESET}\n")
        println("${Colors.CYAN}Próximos pasos:${Colors.RESET}")
        println("  1. Sincroniza el proyecto con Gradle")
        println("  2. Revisa los archivos generados")
        println("  3. Personaliza según tus necesidades")
        println("  4. ¡Comienza a desarrollar!")
        println()

    } catch (e: Exception) {
        println("\n${Colors.RED}❌ Error durante la generación:${Colors.RESET}")
        println("${Colors.RED}${e.message}${Colors.RESET}")
        e.printStackTrace()
        exitProcess(1)
    }
}

/**
 * Detecta la información del proyecto (package base y directorio fuente)
 */
fun detectProjectInfo(projectRoot: File): ProjectInfo? {
    val buildGradle = File(projectRoot, "app/build.gradle.kts")

    if (!buildGradle.exists()) {
        return null
    }

    val content = buildGradle.readText()

    // Buscar la línea de namespace
    val namespaceLines = content.lines().filter { it.contains("namespace") && it.contains("=") }

    var packageBase: String? = null

    for (line in namespaceLines) {
        // Caso 1: namespace con variable interpolada: namespace = "org.override.$nameProject"
        if (line.contains("$")) {
            // Extraer el patrón completo
            val cleanLine = line.trim()
            // Buscar namespace = "prefix$variable" o namespace = "prefix${variable}"
            val pattern1 = """namespace\s*=\s*"([^"$]+)\$(\w+)"""".toRegex()
            val pattern2 = """namespace\s*=\s*"([^"$]+)\$\{(\w+)\}"""".toRegex()

            val match = pattern1.find(cleanLine) ?: pattern2.find(cleanLine)

            if (match != null) {
                val prefix = match.groupValues[1]
                val varName = match.groupValues[2]

                // Buscar el valor de la variable en el contenido
                val varPattern = """val\s+$varName\s*=\s*"([^"]+)"""".toRegex()
                val varMatch = varPattern.find(content)

                if (varMatch != null) {
                    packageBase = "$prefix${varMatch.groupValues[1]}"
                    break
                }
            }
        } else {
            // Caso 2: namespace simple: namespace = "com.example.app"
            val simplePattern = """namespace\s*=\s*"([^"]+)"""".toRegex()
            val match = simplePattern.find(line)

            if (match != null) {
                packageBase = match.groupValues[1]
                break
            }
        }
    }

    if (packageBase == null) {
        return null
    }

    val javaSourceDir = File(projectRoot, "app/src/main/java")

    if (!javaSourceDir.exists()) {
        return null
    }

    return ProjectInfo(packageBase, javaSourceDir)
}

/**
 * Recopila las features que se van a generar
 */
fun collectFeatures(projectInfo: ProjectInfo): List<FeatureConfig> {
    val features = mutableListOf<FeatureConfig>()

    println("${Colors.BOLD}¿Cuántas features deseas generar?${Colors.RESET}")
    print("Cantidad (1-10): ")
    val count = readln().trim().toIntOrNull()?.coerceIn(1, 10) ?: 1

    println()

    repeat(count) { index ->
        println("${Colors.CYAN}${Colors.BOLD}═══ Feature ${index + 1}/$count ═══${Colors.RESET}")

        // Nombre de la feature
        print("${Colors.CYAN}Nombre de la feature${Colors.RESET} (ej: profile, products, cart): ")
        val name = readln().trim().takeIf { it.isNotEmpty() }

        if (name == null) {
            println("${Colors.YELLOW}⚠️  Nombre vacío, saltando...${Colors.RESET}\n")
            return@repeat
        }

        // Validar nombre
        if (!isValidFeatureName(name)) {
            println("${Colors.RED}❌ Nombre inválido. Usa solo letras minúsculas${Colors.RESET}\n")
            return@repeat
        }

        // Preguntar qué capas generar
        println("\n${Colors.BOLD}Selecciona las capas a generar:${Colors.RESET}")

        print("  ${Colors.CYAN}[1]${Colors.RESET} Presentation (UI + ViewModel + MVI) ${Colors.GREEN}[Recomendado]${Colors.RESET} (s/N): ")
        val generatePresentation = readln().trim().lowercase().let {
            it == "s" || it == "si" || it == "yes" || it == "y"
        }

        print("  ${Colors.CYAN}[2]${Colors.RESET} Domain (Models + Repository Interface) (s/N): ")
        val generateDomain = readln().trim().lowercase().let {
            it == "s" || it == "si" || it == "yes" || it == "y"
        }

        print("  ${Colors.CYAN}[3]${Colors.RESET} Data (DataStore + Serializer) (s/N): ")
        val generateData = readln().trim().lowercase().let {
            it == "s" || it == "si" || it == "yes" || it == "y"
        }

        if (!generatePresentation && !generateDomain && !generateData) {
            println("${Colors.YELLOW}⚠️  No se seleccionó ninguna capa, saltando...${Colors.RESET}\n")
            return@repeat
        }

        features.add(
            FeatureConfig(
                name = name,
                packageBase = projectInfo.packageBase,
                generatePresentation = generatePresentation,
                generateData = generateData,
                generateDomain = generateDomain
            )
        )

        println("${Colors.GREEN}✓ Feature '${name}' configurada${Colors.RESET}\n")
    }

    return features
}

/**
 * Valida que el nombre de la feature sea correcto
 */
fun isValidFeatureName(name: String): Boolean {
    val regex = Regex("^[a-z][a-z0-9_]*$")
    return regex.matches(name)
}

/**
 * Confirma la generación de features
 */
fun confirmGeneration(features: List<FeatureConfig>): Boolean {
    println("\n${Colors.YELLOW}${Colors.BOLD}Resumen de features a generar:${Colors.RESET}")

    features.forEach { feature ->
        println("\n  ${Colors.CYAN}${Colors.BOLD}${feature.name}${Colors.RESET}")
        val layers = mutableListOf<String>()
        if (feature.generatePresentation) layers.add("Presentation")
        if (feature.generateDomain) layers.add("Domain")
        if (feature.generateData) layers.add("Data")
        println("    Capas: ${layers.joinToString(", ")}")
    }

    println()
    print("¿Continuar con la generación? (S/n): ")
    val response = readln().trim().lowercase()
    return response != "n" && response != "no"
}

/**
 * Genera una feature completa
 */
fun generateFeature(projectInfo: ProjectInfo, config: FeatureConfig) {
    println("${Colors.MAGENTA}${Colors.BOLD}━━━ Generando feature: ${config.name} ━━━${Colors.RESET}")

    val featureName = config.name
    val featureNameCapitalized = featureName.replaceFirstChar { it.uppercase() }

    // Crear directorio base de la feature
    val packagePath = config.packageBase.replace(".", "/")
    val featureBaseDir = File(projectInfo.javaSourceDir, "$packagePath/feature/$featureName")

    // Generar capa Presentation
    if (config.generatePresentation) {
        println("  ${Colors.CYAN}📱 Generando capa Presentation...${Colors.RESET}")
        generatePresentationLayer(featureBaseDir, config, featureNameCapitalized)
        println("    ${Colors.GREEN}✓ Presentation generada${Colors.RESET}")
    }

    // Generar capa Domain
    if (config.generateDomain) {
        println("  ${Colors.CYAN}🎯 Generando capa Domain...${Colors.RESET}")
        generateDomainLayer(featureBaseDir, config, featureNameCapitalized)
        println("    ${Colors.GREEN}✓ Domain generada${Colors.RESET}")
    }

    // Generar capa Data
    if (config.generateData) {
        println("  ${Colors.CYAN}💾 Generando capa Data...${Colors.RESET}")
        generateDataLayer(featureBaseDir, config, featureNameCapitalized)
        println("    ${Colors.GREEN}✓ Data generada${Colors.RESET}")
    }

    println("  ${Colors.GREEN}✅ Feature '${config.name}' completada${Colors.RESET}\n")
}

/**
 * Genera la capa de Presentación (MVI)
 */
fun generatePresentationLayer(featureBaseDir: File, config: FeatureConfig, featureName: String) {
    val presentationDir = File(featureBaseDir, "presentation")
    presentationDir.mkdirs()

    // Generar State
    generateStateFile(presentationDir, config, featureName)

    // Generar Action
    generateActionFile(presentationDir, config, featureName)

    // Generar ViewModel
    generateViewModelFile(presentationDir, config, featureName)

    // Generar Root y Screen
    generateRootFile(presentationDir, config, featureName)
}

/**
 * Genera el archivo State
 */
fun generateStateFile(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}State.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.presentation

data class ${featureName}State(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // TODO: Agregar propiedades del estado
)
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera el archivo Action
 */
fun generateActionFile(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}Action.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.presentation

sealed interface ${featureName}Action {
    // TODO: Definir acciones específicas
    // Ejemplo:
    // data object OnButtonClick : ${featureName}Action
    // data class OnInputChange(val value: String) : ${featureName}Action
}
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera el archivo ViewModel
 */
fun generateViewModelFile(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}ViewModel.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ${featureName}ViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(${featureName}State())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // TODO: Cargar datos iniciales aquí
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ${featureName}State()
        )

    fun onAction(action: ${featureName}Action) {
        when (action) {
            // TODO: Manejar acciones
            else -> Unit
        }
    }
}
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera el archivo Root y Screen
 */
fun generateRootFile(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}Root.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ${config.packageBase}.core.ui.theme.AppTheme

@Composable
fun ${featureName}Root(
    viewModel: ${featureName}ViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ${featureName}Screen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ${featureName}Screen(
    state: ${featureName}State,
    onAction: (${featureName}Action) -> Unit,
) {
    // TODO: Implementar UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${featureName} Screen")
    }
}

@Preview
@Composable
private fun ${featureName}ScreenPreview() {
    AppTheme {
        ${featureName}Screen(
            state = ${featureName}State(),
            onAction = {}
        )
    }
}
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera la capa de Dominio
 */
fun generateDomainLayer(featureBaseDir: File, config: FeatureConfig, featureName: String) {
    val domainDir = File(featureBaseDir, "domain")

    // Crear subdirectorios
    val modelDir = File(domainDir, "model")
    val repositoryDir = File(domainDir, "repository")

    modelDir.mkdirs()
    repositoryDir.mkdirs()

    // Generar Model
    generateModelFile(modelDir, config, featureName)

    // Generar Repository Interface
    generateRepositoryInterface(repositoryDir, config, featureName)
}

/**
 * Genera el archivo de Model
 */
fun generateModelFile(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.domain.model

import kotlinx.serialization.Serializable

/**
 * ${featureName.uppercase()} MODEL
 *
 * Modelo de dominio que representa ${featureName} en la aplicación
 * Utiliza kotlinx.serialization para ser guardado en DataStore
 *
 * @Serializable: Permite serializar/deserializar automáticamente a JSON
 */
@Serializable
data class ${featureName}(
    val id: String,
    // TODO: Agregar propiedades del modelo
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        /**
         * Valor vacío por defecto
         */
        fun empty() = ${featureName}(
            id = ""
        )

        /**
         * Verifica si el objeto es válido
         */
        fun ${featureName}.isValid(): Boolean {
            return id.isNotEmpty()
        }
    }
}
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera la interfaz del Repository
 */
fun generateRepositoryInterface(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}Repository.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.domain.repository

import kotlinx.coroutines.flow.Flow
import ${config.packageBase}.feature.${config.name}.domain.model.${featureName}

/**
 * ${featureName.uppercase()} REPOSITORY INTERFACE
 *
 * Define el contrato para la gestión de persistencia de ${featureName}
 * Esta interfaz pertenece al dominio y no depende de implementaciones concretas
 * Sigue el principio de inversión de dependencias (SOLID)
 */
interface ${featureName}Repository {

    /**
     * Flow que emite el ${featureName} actual
     * Se actualiza automáticamente cuando cambia
     */
    val ${config.name}Flow: Flow<${featureName}>

    /**
     * Guarda el ${featureName}
     *
     * @param ${config.name} ${featureName} a guardar
     */
    suspend fun save${featureName}(${config.name}: ${featureName})

    /**
     * Obtiene el ${featureName} actual
     *
     * @return ${featureName} actual o ${featureName}.empty() si no existe
     */
    suspend fun get${featureName}(): ${featureName}

    /**
     * Elimina el ${featureName} guardado
     */
    suspend fun clear${featureName}()
}
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera la capa de Data
 */
fun generateDataLayer(featureBaseDir: File, config: FeatureConfig, featureName: String) {
    val dataDir = File(featureBaseDir, "data")

    // Crear subdirectorio local
    val localDir = File(dataDir, "local")
    localDir.mkdirs()

    // Generar Serializer
    generateSerializerFile(localDir, config, featureName)

    // Generar Repository Implementation
    generateRepositoryImplementation(localDir, config, featureName)
}

/**
 * Genera el archivo Serializer
 */
fun generateSerializerFile(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}Serializer.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.data.local

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ${config.packageBase}.feature.${config.name}.domain.model.${featureName}
import java.io.InputStream
import java.io.OutputStream

/**
 * ${featureName.uppercase()} SERIALIZER - Serializador personalizado para DataStore
 *
 * Permite guardar y leer objetos ${featureName} en DataStore usando kotlinx.serialization
 * DataStore utiliza este serializer para convertir ${featureName} a bytes y viceversa
 */
object ${featureName}Serializer : Serializer<${featureName}> {

    /**
     * Valor por defecto cuando no hay datos guardados
     */
    override val defaultValue: ${featureName}
        get() = ${featureName}.empty()

    /**
     * Configuración de JSON para serialización
     */
    private val json = Json {
        ignoreUnknownKeys = true      // Ignora campos desconocidos
        encodeDefaults = true          // Incluye valores por defecto
        isLenient = true               // Permite JSON menos estricto
        coerceInputValues = true       // Convierte null a valores por defecto
        prettyPrint = false            // JSON compacto
    }

    /**
     * Lee datos del InputStream y los deserializa a ${featureName}
     *
     * @param input Stream de entrada con los datos serializados
     * @return Objeto ${featureName} deserializado
     */
    override suspend fun readFrom(input: InputStream): ${featureName} {
        return try {
            // Leer bytes del input stream
            val bytes = input.readBytes()

            // Si no hay datos, retornar valor por defecto
            if (bytes.isEmpty()) {
                return defaultValue
            }

            // Deserializar JSON a objeto ${featureName}
            json.decodeFromString(
                deserializer = ${featureName}.serializer(),
                string = bytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    /**
     * Serializa el objeto ${featureName} y lo escribe al OutputStream
     *
     * @param t Objeto ${featureName} a serializar
     * @param output Stream de salida donde escribir los datos
     */
    override suspend fun writeTo(t: ${featureName}, output: OutputStream) {
        try {
            // Serializar ${featureName} a JSON
            val jsonString = json.encodeToString(
                serializer = ${featureName}.serializer(),
                value = t
            )

            // Escribir bytes al output stream
            withContext(Dispatchers.IO) {
                output.write(jsonString.encodeToByteArray())
            }
        } catch (e: SerializationException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
""".trimIndent()

    file.writeText(content)
}

/**
 * Genera la implementación del Repository
 */
fun generateRepositoryImplementation(dir: File, config: FeatureConfig, featureName: String) {
    val file = File(dir, "${featureName}PreferencesRepository.kt")
    val content = """
package ${config.packageBase}.feature.${config.name}.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import ${config.packageBase}.feature.${config.name}.domain.model.${featureName}
import ${config.packageBase}.feature.${config.name}.domain.repository.${featureName}Repository
import java.io.IOException

/**
 * ${featureName.uppercase()} PREFERENCES REPOSITORY IMPLEMENTATION
 *
 * Implementación concreta de ${featureName}Repository usando DataStore
 * Gestiona la persistencia de datos de ${featureName} usando DataStore con serialización
 * Esta clase pertenece a la capa de datos (data layer)
 */
class ${featureName}PreferencesRepository(private val context: Context) : ${featureName}Repository {

    /**
     * DataStore extension para ${featureName}
     * Crea un archivo "${config.name}_prefs.json" en el almacenamiento interno
     */
    private val Context.${config.name}DataStore: DataStore<${featureName}> by dataStore(
        fileName = "${config.name}_prefs.json",
        serializer = ${featureName}Serializer
    )

    /**
     * Flow que emite el ${featureName} actual
     * Se actualiza automáticamente cuando cambia
     */
    override val ${config.name}Flow: Flow<${featureName}> = context.${config.name}DataStore.data
        .catch { exception ->
            // Manejar errores de lectura
            if (exception is IOException) {
                println("Error leyendo ${featureName} DataStore: ${'$'}{exception.message}")
                emit(${featureName}.empty())
            } else {
                throw exception
            }
        }

    /**
     * Guarda el ${featureName} completo en DataStore
     *
     * @param ${config.name} ${featureName} a guardar
     */
    override suspend fun save${featureName}(${config.name}: ${featureName}) {
        context.${config.name}DataStore.updateData { ${config.name} }
    }

    /**
     * Obtiene el ${featureName} actual
     *
     * @return ${featureName} actual o ${featureName}.empty() si no existe
     */
    override suspend fun get${featureName}(): ${featureName} {
        return try {
            var current${featureName} = ${featureName}.empty()
            context.${config.name}DataStore.data.collect { ${config.name} ->
                current${featureName} = ${config.name}
            }
            current${featureName}
        } catch (e: Exception) {
            ${featureName}.empty()
        }
    }

    /**
     * Elimina el ${featureName} guardado
     */
    override suspend fun clear${featureName}() {
        context.${config.name}DataStore.updateData { ${featureName}.empty() }
    }
}
""".trimIndent()

    file.writeText(content)
}

// Ejecutar el script
main()

