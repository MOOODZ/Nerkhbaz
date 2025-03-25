package ir.moodz.sarafkoochooloo.domain.util

import ir.moodz.core.domain.util.Error

sealed interface DataError: Error {
    enum class Network: DataError{
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUEST,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataError{
        DISK_FULL,
        UNKNOWN
    }
}