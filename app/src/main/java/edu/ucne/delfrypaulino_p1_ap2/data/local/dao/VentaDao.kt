package edu.ucne.delfrypaulino_p1_ap2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.VentaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VentaDao {
    @Upsert
    suspend fun save(venta: VentaEntity)

    @Query(
        """
        SELECT * 
        FROM Ventas
        WHERE ventaId =:ventaId
        LIMIT 1
        """
    )
    suspend fun find(ventaId: Int): VentaEntity?

    @Query("SELECT * FROM Ventas")
    fun getall(): Flow<List<VentaEntity>>

    @Delete
    suspend fun delete(venta: VentaEntity)
}