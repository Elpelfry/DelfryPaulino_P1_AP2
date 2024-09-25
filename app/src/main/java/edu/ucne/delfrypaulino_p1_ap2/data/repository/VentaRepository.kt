package edu.ucne.delfrypaulino_p1_ap2.data.repository

import edu.ucne.delfrypaulino_p1_ap2.data.local.dao.VentaDao
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.VentaEntity
import javax.inject.Inject

class VentaRepository @Inject constructor(
    private val ventaDao: VentaDao
) {
    suspend fun save(venta: VentaEntity) = ventaDao.save(venta)
    suspend fun delete(venta: VentaEntity) = ventaDao.delete(venta)
    fun getAll() = ventaDao.getall()
    suspend fun find(id: Int) = ventaDao.find(id)
}