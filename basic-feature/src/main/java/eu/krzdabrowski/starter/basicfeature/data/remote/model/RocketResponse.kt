package eu.krzdabrowski.starter.basicfeature.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketResponse(
    @SerialName("id")
    val id: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("cost_per_launch")
    val costPerLaunch: Int = 0,

    @SerialName("first_flight")
    val firstFlightDate: String = "",

    @SerialName("height")
    val height: Height = Height(),

    @SerialName("mass")
    val weight: Weight = Weight(),

    @SerialName("wikipedia")
    val wikiUrl: String = "",

    @SerialName("flickr_images")
    val imageUrls: List<String> = emptyList(),
) {
    @Serializable
    data class Height(
        val meters: Double = 0.0,
        val feet: Double = 0.0,
    )

    @Serializable
    data class Weight(
        val kg: Int = 0,
        val lb: Int = 0,
    )
}
