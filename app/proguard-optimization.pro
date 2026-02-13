# ========================================================================================
# PROGUARD OPTIMIZATION CONFIGURATION
# ========================================================================================
# Archivo de configuración adicional para optimizaciones avanzadas.
# Este archivo se puede incluir opcionalmente para builds altamente optimizados.
# ========================================================================================

# ========================================================================================
# OPTIMIZACIONES AGRESIVAS (OPCIONAL)
# ========================================================================================
# Descomentar estas líneas para mayor optimización (puede aumentar tiempo de build)

# Permitir inlining más agresivo
#-optimizations code/simplification/cast,code/simplification/field,code/removal/advanced,field/removal/writeonly,field/marking/private,method/marking/private,method/removal/parameter,method/propagation/parameter,method/propagation/returnvalue,method/inlining/short,method/inlining/unique,method/inlining/tailrecursion,code/merging,code/simplification/arithmetic

# Más pasadas de optimización (default es 5)
#-optimizationpasses 10

# ========================================================================================
# CONFIGURACIÓN DE LOGS
# ========================================================================================
# Imprimir semillas (clases que se mantienen)
-printseeds build/outputs/mapping/release/seeds.txt

# Imprimir uso (clases que se eliminan)
-printusage build/outputs/mapping/release/usage.txt

# Imprimir configuración
-printconfiguration build/outputs/mapping/release/configuration.txt

# ========================================================================================
# DEBUGGING EN RELEASE (TEMPORAL)
# ========================================================================================
# Descomentar estas líneas si necesitas debug en release build

# Verbose logging
#-verbose

# Mantener nombres de clases originales (NO ofuscar)
#-dontobfuscate

# Mantener atributos de debugging completos
#-keepattributes LocalVariableTable,LocalVariableTypeTable

# ========================================================================================
# SOPORTE PARA REFLECTION
# ========================================================================================
# Si tu app usa reflexión extensivamente, descomentar:
#-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# ========================================================================================
# REDUCCIÓN DE ADVERTENCIAS
# ========================================================================================
# Suprimir advertencias específicas que son seguras de ignorar

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

# ========================================================================================
# FIN DE CONFIGURACIÓN OPCIONAL
# ========================================================================================

