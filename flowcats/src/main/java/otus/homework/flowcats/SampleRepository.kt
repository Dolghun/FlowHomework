package otus.homework.flowcats

import kotlinx.coroutines.flow.Flow

interface SampleRepository {
    fun produceNumbers(): Flow<Int>

    fun produceColors(): Flow<String>

    fun produceForms(): Flow<String>

    fun completed()
}