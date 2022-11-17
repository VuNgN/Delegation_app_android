package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.util.Log
import com.vti.android.delegatedscopemanagement.testapp.data.model.Counter
import com.vti.android.delegatedscopemanagement.testapp.data.repository.FilmRepo
import com.vungn.android.mybase.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogMakerUseCase @Inject constructor(
    private val filmRepo: FilmRepo,
    private val counter: Counter
) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int) {
        callApi(params)
    }

    private suspend fun callApi(params: Int) {
        for (i in (1..params)) {
            val films = coroutineScope {
                withContext(Dispatchers.IO) {
                    try {
                        filmRepo.getFilmById("2baf70d1-42bb-4437-b551-e5fed5a87abe")
                    } catch (e: Exception) {
                        Log.d(TAG, "callApi: ${e.message}")
                    }
                }
            }
            counter.updateCounter()
        }
    }

    companion object {
        private val TAG = LogMakerUseCase::class.simpleName
    }
}