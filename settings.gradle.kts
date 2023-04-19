rootProject.name = "woorizip-server"

include("woorizip-main")
include("woorizip-core")
include("woorizip-common")
include("woorizip-adapter:persistence")
include("woorizip-adapter:web")
include("woorizip-adapter:security")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
