package com.climaconvento.data.model

data class Location(
    val name: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        // Ubicaciones favoritas en Chile
        val FAVORITE_LOCATIONS = listOf(
            // La ubicación "Convento" fue actualizada: coordenadas revisadas
            Location("Convento", "Convento", -33.776986, -71.5966961),
            Location("Campo Alegre", "Campo Alegre", -33.871788, -71.6968129),
            Location("Santo Domingo", "Santo Domingo", -33.6528, -71.6139),
            Location("Rengo", "Rengo", -34.4069, -70.8639),
            Location("Rancagua", "Rancagua", -34.1708, -70.7406)
        )
        
        fun getLocationByName(name: String): Location? {
            return FAVORITE_LOCATIONS.find { it.name.equals(name, ignoreCase = true) }
        }
    }
}
