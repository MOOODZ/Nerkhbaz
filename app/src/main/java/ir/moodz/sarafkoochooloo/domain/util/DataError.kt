package ir.moodz.sarafkoochooloo.domain.util

sealed interface DataError: Error {
    enum class Network: DataError{
        REQUEST_TIMEOUT,
        CONNECT_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUEST,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
        NOT_FOUND
    }

    enum class Local: DataError{
        DISK_FULL,
        UNKNOWN
    }
}