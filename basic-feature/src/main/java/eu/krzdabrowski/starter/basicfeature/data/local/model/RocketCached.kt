package eu.krzdabrowski.starter.basicfeature.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RocketCached(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "cost_per_launch")
    val costPerLaunch: Int,

    @ColumnInfo(name = "first_flight_date")
    val firstFlightDate: String,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "weight")
    val weight: Int,

    @ColumnInfo(name = "wiki_url")
    val wikiUrl: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)
