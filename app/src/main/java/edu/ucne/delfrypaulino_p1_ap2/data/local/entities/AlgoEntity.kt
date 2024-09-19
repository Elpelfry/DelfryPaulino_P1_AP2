package edu.ucne.delfrypaulino_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Algos")
data class AlgoEntity (
    @PrimaryKey
    val id: Int? = null
)
