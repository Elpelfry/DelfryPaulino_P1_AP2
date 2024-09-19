package edu.ucne.delfrypaulino_p1_ap2.data.repository

import edu.ucne.delfrypaulino_p1_ap2.data.local.dao.AlgoDao
import edu.ucne.delfrypaulino_p1_ap2.data.local.entities.AlgoEntity
import javax.inject.Inject

class AlgoRepository @Inject constructor(
    private val algoDao: AlgoDao
) {
     suspend fun save(algo: AlgoEntity) = algoDao.save(algo)
     suspend fun delete(algo: AlgoEntity) = algoDao.delete(algo)
     fun getAll() = algoDao.getall()
     suspend fun find(id: Int) = algoDao.find(id)
}