package com.vungn.android.mybase.usecase

interface UseCase<PARAMS, RESULT> {
    suspend fun execute(params: PARAMS): RESULT
}