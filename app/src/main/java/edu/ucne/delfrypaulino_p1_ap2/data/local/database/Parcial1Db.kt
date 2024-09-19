package edu.ucne.delfrypaulino_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.delfrypaulino_p1_ap2.data.local.dao.AlgoDao
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.AlgoEntity

@Database(

    entities = [
        AlgoEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class Parcial1Db : RoomDatabase(){
    abstract fun algoDao(): AlgoDao
}