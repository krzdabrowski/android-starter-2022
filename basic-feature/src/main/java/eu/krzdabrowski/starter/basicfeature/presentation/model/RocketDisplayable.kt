package eu.krzdabrowski.starter.basicfeature.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class RocketDisplayable(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val costPerLaunchInMillions: Int,
    val firstFlightDate: String,
    val heightInMeters: Int,
    val weightInTonnes: Int,
    val wikiUrl: String,
    val imageUrl: String
) : Parcelable
