package edu.ucne.delfrypaulino_p1_ap2.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.delfrypaulino_p1_ap2.data.repository.AlgoRepository
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: AlgoRepository
): ViewModel() {

}