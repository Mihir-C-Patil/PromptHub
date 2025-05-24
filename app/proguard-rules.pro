# --- Strip everything not needed ---
-dontusemixedcaseclassnames
-dontpreverify
-dontoptimize               # Optional: comment this to enable optimization
-dontwarn android.support.**  # Avoid warnings on unused support libraries

# --- Remove logging and debug info ---
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# --- Rename everything that can be renamed ---
-repackageclasses ''
-overloadaggressively
-useuniqueclassmembernames
-flattenpackagehierarchy

# --- Obfuscate class, method, and field names ---
-obfuscationdictionary obf_dict.txt
-classobfuscationdictionary obf_classes.txt
-packageobfuscationdictionary obf_packages.txt

# --- Shrink everything (except what must be kept) ---
-allowaccessmodification
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-shrink
-obfuscate
-printmapping mapping.txt
-verbose

# --- Keep your entry points (update these based on your app) ---
-keep class com.example.prompthub.MainActivity { *; }
-keep class androidx.lifecycle.ViewModel { *; }

# --- Preserve classes accessed via reflection ---
-keepclassmembers class * {
    *** get*();
    void set*(***);
}

# --- Keep Serializable/Parcelable classes ---
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
}

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep all native methods (called from native code)
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class * {
    static void loadLibrary(java.lang.String);
}

# Keep all classes that have native methods (JNI bridge classes)
-keep class com.example.prompthub.security.OpenSSLHelper { *; }
-keep class com.example.prompthub.security.TamperCheck { *; }
-keep class com.example.prompthub.security.TamperCheck2 { *; }
-keep class com.example.prompthub.utils.KeyLoader { *; }
-keep class com.example.prompthub.security.TamperCheck { *; }
-keep class com.example.prompthub.security.TamperCheck2 { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}