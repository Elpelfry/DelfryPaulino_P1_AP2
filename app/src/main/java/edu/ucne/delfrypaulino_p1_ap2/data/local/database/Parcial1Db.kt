package edu.ucne.delfrypaulino_p1_ap2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.delfrypaulino_p1_ap2.data.local.dao.VentaDao
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.VentaEntity

@Database(

    entities = [
        VentaEntity::class
    ],
    version = 3,
    exportSchema = false
)

abstract class Parcial1Db : RoomDatabase(){
    abstract fun ventaDao(): VentaDao
}